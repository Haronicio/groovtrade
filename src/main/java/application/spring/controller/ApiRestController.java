package application.spring.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.spring.entities.Historique;
import application.spring.entities.Panier;
import application.spring.entities.Produit;
import application.spring.entities.Utilisateur;
import application.spring.postModel.register.SimpleUser;
import application.spring.repository.ProduitRepository;
import application.spring.repository.UtilisateurRepository;

@RequestMapping("/api/produits")
@RestController
public class ApiRestController {
	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private ProduitRepository produitRepository;

	@GetMapping("/liste")
	public List<Produit> getProduits(){
		return produitRepository.findAll();
	}

	@GetMapping("/panier")
	public List<Produit> getPanier(@AuthenticationPrincipal UserDetails userDetails){
		// Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// if(authentication.isAuthenticated()){
		// 	//TODO sachant utilisateur est authentifié
		// }
		Utilisateur u = utilisateurRepository.findByUsername(userDetails.getUsername());
		Panier panier = u.getPanier();
		return panier.getProduits();
	}

	@GetMapping("/historique")
	public List<Historique> getHistorique(@AuthenticationPrincipal UserDetails userDetails){
		Utilisateur u = utilisateurRepository.findByUsername(userDetails.getUsername());
		List<Historique> historiques = u.getHistoriques();
		return historiques;
	}

	@GetMapping("/add")
	public void add(){
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		Long id = (long) 0;
		String username = "createdadmin";
		String password = b.encode("admin");
		String role = "ADMIN";
		String email = "c@gmail.com";
		List<Historique> historiques = new ArrayList<>();
		Panier panier = new Panier();
		Utilisateur u = new Utilisateur(id, username, password, role, email, historiques,panier);
		utilisateurRepository.save(u);
	}


	@GetMapping
	public String userInfo(@AuthenticationPrincipal UserDetails userDetails){
		Utilisateur u = utilisateurRepository.findByUsername(userDetails.getUsername());
		String description = u.toString();
		return "Welcome "+u.getUsername()+"\n"+description;
	}
	

	@PostMapping(value ="/addPanier", consumes =  MediaType.APPLICATION_JSON_VALUE)
	public HttpStatus addPanier(@AuthenticationPrincipal UserDetails userDetails,@RequestBody Produit produit){
		try{
			Utilisateur user = utilisateurRepository.findByUsername(userDetails.getUsername());
			System.out.println("voici++"+produit.toString());
			Panier panier = user.getPanier();
			panier.add(produit);
			utilisateurRepository.save(user);
			return HttpStatus.OK;
		}catch(Exception e){
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}

	@PostMapping("/addHistorique")
	public HttpStatus addHistorique(@AuthenticationPrincipal UserDetails userDetails,@RequestBody Historique newHistorique){
		try{
			Utilisateur user = utilisateurRepository.findByUsername(userDetails.getUsername());
			List<Historique> historique = user.getHistoriques();
			historique.add(newHistorique);
			System.out.println("addHistorique "+newHistorique.toString());
			utilisateurRepository.save(user);
			return HttpStatus.OK;
		}catch(Exception e){
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@AuthenticationPrincipal UserDetails userDetails, @RequestBody SimpleUser newUser){
		try{
			if(utilisateurRepository.findByUsername(newUser.getUsername()) == null
				||newUser.getUsername().equals("")){
				Utilisateur user = new Utilisateur(newUser.getUsername(),newUser.getPassword(),"USER",newUser.getEmail(),new ArrayList<Historique>(),new Panier());
				System.out.println(user.toString());
				utilisateurRepository.save(user);
				return ResponseEntity.ok("creation reussi");
			}
			return new ResponseEntity<>("duplication",HttpStatus.NOT_ACCEPTABLE);
		}catch(Exception e){
			return new ResponseEntity<>("erreur creation",HttpStatus.BAD_REQUEST);
		}
	}
}
