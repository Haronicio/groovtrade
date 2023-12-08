package application.spring.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.spring.Model.commentaire.SimpleCommentaire;
import application.spring.Model.livraison.SimpleLivraison;
import application.spring.Model.produit.SimpleProduit;
import application.spring.Model.register.SimpleUser;
import application.spring.entities.Historique;
import application.spring.entities.Panier;
import application.spring.entities.PanierItem;
import application.spring.entities.Produit;
import application.spring.entities.ProduitImg;
import application.spring.entities.Utilisateur;
import application.spring.repository.ProduitRepository;
import application.spring.repository.UtilisateurRepository;


import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;


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

	//retoune list des produits dans panier 
	@GetMapping("/panier")
	public List<PanierItem> getPanier(@AuthenticationPrincipal UserDetails userDetails){
		// Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// if(authentication.isAuthenticated()){
		// 	//TODO sachant utilisateur est authentifié
		// }
		Utilisateur u = utilisateurRepository.findByUsername(userDetails.getUsername());
		Panier panier = u.getPanier();
		return panier.getProduits();
	}

	//retourne list historique
	@GetMapping("/historique")
	public List<Historique> getHistorique(@AuthenticationPrincipal UserDetails userDetails){
		Utilisateur u = utilisateurRepository.findByUsername(userDetails.getUsername());
		List<Historique> historiques = u.getHistoriques();
		return historiques;
	}

	// @GetMapping("/add")
	// public void add(){
	// 	BCryptPasswordEncoder b = new BCryptPasswordEncoder();
	// 	Long id = (long) 0;
	// 	String username = "createdadmin";
	// 	String password = b.encode("admin");
	// 	String role = "ADMIN";
	// 	String email = "c@gmail.com";
	// 	List<Historique> historiques = new ArrayList<>();
	// 	Panier panier = new Panier();
		
	// 	Utilisateur u = new Utilisateur(id, username, password, role, email, historiques,panier);
	// 	utilisateurRepository.save(u);
	// }


	@GetMapping
	public Utilisateur userInfo(@AuthenticationPrincipal UserDetails userDetails){
		Utilisateur u = utilisateurRepository.findByUsername(userDetails.getUsername());
		return u;
	}
	

	// @PostMapping(value ="/addPanier", consumes =  MediaType.APPLICATION_JSON_VALUE)
	// public HttpStatus addPanier(@AuthenticationPrincipal UserDetails userDetails,@RequestBody Produit produit){
	// 	try{
	// 		Utilisateur user = utilisateurRepository.findByUsername(userDetails.getUsername());
	// 		System.out.println("voici++"+produit.toString());
	// 		Panier panier = user.getPanier();
	// 		panier.add(produit);
	// 		utilisateurRepository.save(user);
	// 		return HttpStatus.OK;
	// 	}catch(Exception e){
	// 		return HttpStatus.NOT_ACCEPTABLE;
	// 	}
	// }

	// @PostMapping("/addHistorique")
	// public HttpStatus addHistorique(@AuthenticationPrincipal UserDetails userDetails,@RequestBody Historique newHistorique){
	// 	try{
	// 		Utilisateur user = utilisateurRepository.findByUsername(userDetails.getUsername());
	// 		List<Historique> historique = user.getHistoriques();
	// 		historique.add(newHistorique);
	// 		System.out.println("addHistorique "+newHistorique.toString());
	// 		utilisateurRepository.save(user);
	// 		return HttpStatus.OK;
	// 	}catch(Exception e){
	// 		return HttpStatus.NOT_ACCEPTABLE;
	// 	}
	// }

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

	//supprimer un produit
	@PostMapping(value ="/removeOnePanier", consumes =  MediaType.APPLICATION_JSON_VALUE)
	public HttpStatus removeOnePanier(@AuthenticationPrincipal UserDetails userDetails,@RequestBody SimpleProduit targetProduit){
		try{
			Utilisateur user = utilisateurRepository.findByUsername(userDetails.getUsername());
			Panier panier = user.getPanier();
			Produit p = produitRepository.findByProduitid(targetProduit.getProduitid());
			panier.removeOne(p);
			utilisateurRepository.save(user);
			return HttpStatus.OK;
		}catch(Exception e){
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}
	@PostMapping(value ="/removePanier", consumes =  MediaType.APPLICATION_JSON_VALUE)
	public HttpStatus removePanier(@AuthenticationPrincipal UserDetails userDetails,@RequestBody SimpleProduit targetProduit){
		try{
			Utilisateur user = utilisateurRepository.findByUsername(userDetails.getUsername());
			Panier panier = user.getPanier();
			Produit p = produitRepository.findByProduitid(targetProduit.getProduitid());
			panier.remove(p);
			utilisateurRepository.save(user);
			return HttpStatus.OK;
		}catch(Exception e){
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}

	//ajoute un produit dans panier
	@PostMapping(value ="/addPanier", consumes =  MediaType.APPLICATION_JSON_VALUE)
	public HttpStatus addPanier(@AuthenticationPrincipal UserDetails userDetails,@RequestBody SimpleProduit newProduit){
		System.out.println("hello "+newProduit.toString());
		try{
			Utilisateur user = utilisateurRepository.findByUsername(userDetails.getUsername());
			Panier panier = user.getPanier();
			Produit p = produitRepository.findByProduitid(newProduit.getProduitid());
			panier.add(new PanierItem(p,newProduit.getQuantite()));
		
			utilisateurRepository.save(user);
			return HttpStatus.OK;
		}catch(Exception e){
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}
	@PostMapping("/addCommentaire")
	public HttpStatus getMethodName(@AuthenticationPrincipal UserDetails userDetails,@RequestBody SimpleCommentaire simpleCommentaire) {
		Utilisateur user = utilisateurRepository.findByUsername(userDetails.getUsername());
		Panier panier = user.getPanier();
		Produit p = produitRepository.findByProduitid(simpleCommentaire.getProduitid());
		for (PanierItem item : panier.getProduits()) {
			if (item.getProduit().equals(p)) {
				// Augmenter la quantité et retourner
				item.setCommentaire(simpleCommentaire.getCommentaire());
				utilisateurRepository.save(user);
				return HttpStatus.OK;
			}
		}
		return HttpStatus.NOT_ACCEPTABLE;
	}
	

	// ajouter panier dans historique
	@PostMapping("/addHistorique")
	public ResponseEntity<String> addHistorique(@AuthenticationPrincipal UserDetails userDetails,@RequestBody SimpleLivraison livaison){

		try{
			Utilisateur user = utilisateurRepository.findByUsername(userDetails.getUsername());
			List<Historique> historiques = user.getHistoriques();
			Panier panier = user.getPanier();
			List<PanierItem> panierItems = panier.getProduits();
			
			if(!panier.getProduits().isEmpty()){
				//  Historique(boolean archived, Date date, List<Produit> produits)
				Historique newHistorique = new Historique(false,new Date(System.currentTimeMillis()), livaison.getInfo(), panierItems);
				historiques.add(newHistorique);
				panier.setProduits(new ArrayList<>());

				utilisateurRepository.save(user);
				return ResponseEntity.ok("succès");
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	//ajouter un nouveau produit
	@PostMapping("/addProduit")
	public ResponseEntity<String> addProduit(@AuthenticationPrincipal UserDetails userDetails,@RequestBody Produit newProduit){
		try{
			Utilisateur user = utilisateurRepository.findByUsername(userDetails.getUsername());
			newProduit.setUtilisateurId(user.getUserid());
			produitRepository.save(newProduit);
			return ResponseEntity.ok("succès");
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}	
	@PostMapping("/image")
    public ResponseEntity<byte[]> getImage(@RequestBody ProduitImg image) throws IOException {
		  // Construisez le chemin du fichier d'image dans le répertoire des ressources statiques
		  String imagePath = "static/images/"  +image.getPath()+ ".jpg";

		  // Chargez la ressource
		  Resource resource = new ClassPathResource(imagePath);
  
		  // Vérifiez si la ressource existe
		  if (!resource.exists()) {
			  // Gérez le cas où l'image n'existe pas
			  return ResponseEntity.notFound().build();
		  }
  
		  // Lisez les bytes depuis la ressource
		  byte[] imageBytes = Files.readAllBytes(resource.getFile().toPath());
  
		  // Retournez le tableau de bytes en tant que réponse avec le type de média approprié
		  return ResponseEntity.ok()
				  .contentType(MediaType.IMAGE_JPEG) // Ajustez le type de média en fonction du type de votre image
				  .body(imageBytes);
	}
}
