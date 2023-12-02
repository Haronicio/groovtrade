package application.spring.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.spring.model.Historique;
import application.spring.model.Panier;
import application.spring.model.Produit;
import application.spring.model.SimplerUser;
import application.spring.model.Utilisateur;
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
	public Historique getHistorique(@AuthenticationPrincipal UserDetails userDetails){
		Utilisateur u = utilisateurRepository.findByUsername(userDetails.getUsername());
		Historique historiques = u.getHistoriques().get(0);
		
		return historiques;
	}

	//private OAuth2AuthorizedClientService authorizedClientService;
	// public Controller(OAuth2AuthorizedClientService authorizedClientService) {
	// 	this.authorizedClientService = authorizedClientService;
	//  }
	
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
		return "Welcome "+u.getUsername();
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
	public HttpStatus addHistorique(@AuthenticationPrincipal UserDetails userDetails,Historique Historique){
		try{
			Utilisateur user = utilisateurRepository.findByUsername(userDetails.getUsername());
			List<Historique> historique = user.getHistoriques();
			historique.addAll(historique);
			return HttpStatus.OK;
		}catch(Exception e){
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}

	@PostMapping("/register")
	public HttpStatus register(@AuthenticationPrincipal UserDetails userDetails, Utilisateur newUser){
		try{
			utilisateurRepository.save(newUser);
			return HttpStatus.OK;
		}catch(Exception e){
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}

}
