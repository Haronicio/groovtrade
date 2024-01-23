package fr.haron.groovtrade.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.haron.groovtrade.dao.HistoriqueRepository;
import fr.haron.groovtrade.dao.ProduitRepository;
import fr.haron.groovtrade.dao.UtilisateurRepository;
import fr.haron.groovtrade.dto.DTOInterface;
import fr.haron.groovtrade.dto.GlobalDTO;
import fr.haron.groovtrade.dto.JwtDTO;
import fr.haron.groovtrade.dto.RedirectDTO;
import fr.haron.groovtrade.dto.ErrorDTO;
import fr.haron.groovtrade.dto.MessageDTO;
import fr.haron.groovtrade.dto.LoginDTO;
import fr.haron.groovtrade.dto.produit.AjouterProduitDTO;
import fr.haron.groovtrade.dto.produit.ModifierProduitDTO;
import fr.haron.groovtrade.dto.produit.ProduitDetailsDTO;
import fr.haron.groovtrade.dto.produit.ProduitListeDTO;
import fr.haron.groovtrade.dto.utilisateur.AjouterPanierDTO;
import fr.haron.groovtrade.dto.utilisateur.CheckoutDTO;
import fr.haron.groovtrade.dto.utilisateur.HistoriqueAchatsDTO;
import fr.haron.groovtrade.dto.utilisateur.ModifierPanierDTO;
import fr.haron.groovtrade.dto.utilisateur.ProfileDTO;
import fr.haron.groovtrade.dto.utilisateur.UtilisateurDTO;
import fr.haron.groovtrade.entities.Historique;
import fr.haron.groovtrade.entities.PanierItem;
import fr.haron.groovtrade.entities.Produit;
import fr.haron.groovtrade.entities.ProduitMeta;
import fr.haron.groovtrade.entities.Utilisateur;
import fr.haron.groovtrade.security.JwtService;

@RestController
// @CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class APIRestController {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private ProduitRepository produitRepository;

	@Autowired
	private HistoriqueRepository historiqueRepository;

	public GlobalDTO createGlobalDTO(Authentication authentication, DTOInterface dto) {
		GlobalDTO gDTO = new GlobalDTO();
		gDTO.setDto(dto);

		String username;

		if (authentication == null) {
			username = "anonymousUser";
		} else
			username = authentication.getName();
		gDTO.setUsername(username);

		String userPP = null;
		Long userid = null;
		if (authentication != null && authentication.isAuthenticated() && username != "anonymousUser") {
			// System.out.println("user " + username);
			// Pose problème si "anonymousUser"
			Utilisateur utilisateurCourrant = utilisateurRepository.findByUsername(username);
			userid = utilisateurCourrant.getUserid();
			userPP = utilisateurCourrant.getImgPath();
		} // si anonymous user (buuug)
		gDTO.setUserPP(userPP);
		gDTO.setUserid(userid);

		return gDTO;
	}



	// Pour le Controller Produit

	// Méthode pour obtenir la liste des produits
	@GetMapping("/produits/liste")
	public ResponseEntity<GlobalDTO> listProduits(Authentication authentication,
			@RequestParam(name = "keyword", defaultValue = "") String keyword,
			@RequestParam(name = "artiste", required = false) String artiste,
			@RequestParam(name = "nom", required = false) String nom,
			@RequestParam(name = "album", required = false) String album,
			@RequestParam(name = "genres", required = false) String genres,
			@RequestParam(name = "annee_inf", required = false) Integer annee_inf,
			@RequestParam(name = "annee_sup", required = false) Integer annee_sup) {
		
		List<Produit> produits;

		boolean isAdvancedSearch = (artiste != null && !artiste.isEmpty()) ||
				(nom != null && !nom.isEmpty()) ||
				(album != null && !album.isEmpty()) ||
				(genres != null && !genres.isEmpty()) ||
				annee_inf != null ||
				annee_sup != null;

		if (isAdvancedSearch) {
			// Utiliser la recherche par critères
			annee_inf = (annee_inf == null) ? 0 : annee_inf;annee_sup = (annee_sup == null) ? 2100 : annee_sup;
			produits = produitRepository.findByCombinedCriteria(
					"%" + keyword + "%",
					"%" + (artiste != null ? artiste : "") + "%",
					"%" + (nom != null ? nom : "") + "%",
					"%" + (album != null ? album : "") + "%",
					"%" + (genres != null ? genres : "") + "%",
					annee_inf,
					annee_sup);
		} else {
			// Utiliser une recherche globale
			produits = produitRepository.findGlobal("%" + keyword + "%");
		}
		return ResponseEntity.ok(createGlobalDTO(authentication, new ProduitListeDTO(produits)));
	}

	// Méthode pour obtenir les détails d'un produit
	@GetMapping("/produits/details/{id}")
	public ResponseEntity<GlobalDTO> detailsProduit(@PathVariable Long id, Authentication authentication) {
		Optional<Produit> produit = produitRepository.findById(id);
		if (!produit.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Utilisateur vendorUser = utilisateurRepository.findByUserid(produit.get().getUtilisateurId());

		// Permet de dire à la vue c'est l'objet peut être acheté (plus simple)
		boolean buyable;

		if (authentication != null) // Si le client n'est pas connecté il sera amené a ce connecter
		{
			buyable = !(vendorUser.getUsername().equals(authentication.getName()));
		} else
			buyable = true;

		return ResponseEntity.ok(createGlobalDTO(authentication,
				new ProduitDetailsDTO(vendorUser.getUsername(), buyable, produit.get())));
	}

	// TODO : test
	@PostMapping("/produits/ajouter")
	public ResponseEntity<?> ajouterProduit(@RequestBody AjouterProduitDTO ajouterProduitDTO,
			Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié");
		}

		// Logique pour créer un nouveau produit
		Produit nouveauProduit = new Produit(
				ajouterProduitDTO.getPrix(),
				ajouterProduitDTO.getDescription(),
				ajouterProduitDTO.getType(),
				new ProduitMeta(
						ajouterProduitDTO.getNom(),
						ajouterProduitDTO.getArtiste(),
						ajouterProduitDTO.getAlbum(),
						ajouterProduitDTO.getAnnee(),
						String.join(",", ajouterProduitDTO.getGenres())));
		nouveauProduit.setNbProduit(ajouterProduitDTO.getNb());

		Long utilisateurId = utilisateurRepository.findByUsername(authentication.getName()).getUserid();
		nouveauProduit.setUtilisateurId(utilisateurId);

		// TODO Logique pour traiter les images de couverture
		// ...

		produitRepository.save(nouveauProduit);
		return ResponseEntity.ok("Produit " + nouveauProduit.getNom() + " ajouté au catalogue");
	}

	// TODO : test
	@PostMapping("/produits/modifier")
	public ResponseEntity<GlobalDTO> modifierProduit(@RequestBody ModifierProduitDTO modifierProduitDTO,
			Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(createGlobalDTO(authentication, new ErrorDTO("Utilisateur non authentifié")));
		}

		Produit produit = produitRepository.findById(modifierProduitDTO.getId())
				.orElseThrow(
						() -> new IllegalArgumentException("Produit invalide avec l'id:" + modifierProduitDTO.getId()));

		Utilisateur currentUtilisateur = utilisateurRepository.findByUsername(authentication.getName());

		// Vérification de la propriété du produit
		if (!produit.getUtilisateurId().equals(currentUtilisateur.getUserid())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(createGlobalDTO(authentication,
					new ErrorDTO("Ce n'est pas votre article !")));
		}

		// Mise à jour des informations du produit
		produit.setDescription(modifierProduitDTO.getDescription());
		produit.setPrix(modifierProduitDTO.getPrix());
		produit.setType(modifierProduitDTO.getType());
		produit.setGenres(String.join(",", modifierProduitDTO.getGenres()));
		produit.setNom(modifierProduitDTO.getNom());
		produit.setArtiste(modifierProduitDTO.getArtiste());
		produit.setAlbum(modifierProduitDTO.getAlbum());
		produit.setAnnee(modifierProduitDTO.getAnnee());
		produit.setNbProduit(modifierProduitDTO.getNb());

		// TODO Logique pour traiter les images de couverture
		// ...

		produitRepository.save(produit);
		return ResponseEntity.ok(createGlobalDTO(authentication,
				new MessageDTO("Produit " + produit.getNom() + " modifié avec succès")));
	}

	// Pour le controller Utilisateur
 
	@GetMapping("/utilisateur/{username}")
	public ResponseEntity<GlobalDTO> utilisateur(@PathVariable String username, Authentication authentication) {
		// Récupérer et ajouter les informations de l'utilisateur au modèle
		return ResponseEntity
				.ok(createGlobalDTO(authentication, new RedirectDTO("/utilisateur/" + authentication.getName())));
	}

	@GetMapping("/utilisateur/profile/{username}")
	public ResponseEntity<GlobalDTO> profile(@PathVariable String username, Authentication authentication) {
		Utilisateur user = utilisateurRepository.findByUsername(username);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createGlobalDTO(authentication,
					new ErrorDTO("Utilisateur " + username + " introuvable")));
		}

		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setEmail(user.getEmail());
		profileDTO.setUsername(username);
		profileDTO.setProfilePicturePath(user.getImgPath());

		GlobalDTO globalDTO = createGlobalDTO(authentication, profileDTO);
		return ResponseEntity.ok(globalDTO);
	}

	// ventes de l'utilisateur
	@GetMapping("/utilisateur/{username}/ventes")
	public ResponseEntity<GlobalDTO> listeVentes(@PathVariable String username, Authentication authentication) {
		// Fournir le modèle avec la liste des produits en vente
		Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());

		return ResponseEntity.ok(createGlobalDTO(authentication,
				new UtilisateurDTO(currenUtilisateur.getVentes(produitRepository.findAll()), "ventes")));
	}

	// Panier de l'utilisateur
	@GetMapping("/utilisateur/{username}/panier")
	public ResponseEntity<GlobalDTO> panierUtilisateur(@PathVariable String username, Authentication authentication) {
		// Fournir le modèle avec le panier de l'utilisateur
		Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());

		// System.out.println("\n\npanierrr"+currenUtilisateur.getPanier()+"\n\n");

		return ResponseEntity.ok(createGlobalDTO(authentication,
				new UtilisateurDTO(currenUtilisateur.getPanier().getProduits(), "panier")));

	}

	// Détails historique
	@GetMapping("/utilisateur/{username}/historique/{id}/details")
	public ResponseEntity<GlobalDTO> historiqueDetails(@PathVariable String username, @PathVariable Long id,
			Authentication authentication) {
		// Fournir le modèle avec l'historique des achats
		Historique historique = historiqueRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Historique invalide avec l'id:" + id));

		return ResponseEntity
				.ok(createGlobalDTO(authentication, new UtilisateurDTO(historique.getPanierItems(), "utilisateur")));

	}


	//TODO : test
	@PostMapping("/utilisateur/{username}/ajouterPanier")
	public ResponseEntity<GlobalDTO> ajouterPanier(@PathVariable String username,
			@RequestBody AjouterPanierDTO ajoutPanierDTO,
			Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(username)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(createGlobalDTO(authentication, new ErrorDTO("Veuillez vous connectez !")));
		}

		Utilisateur currentUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
		Produit produit = produitRepository.findById(ajoutPanierDTO.getProduitId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Produit invalide avec l'id:" + ajoutPanierDTO.getProduitId()));

		int nbProduit = ajoutPanierDTO.getNbProduit();
		Long produitId = ajoutPanierDTO.getProduitId();
		String commentaire = ajoutPanierDTO.getCommentaire();

		int nbInPanier = 0;
		PanierItem article;
		// Vérification dans le controller du bon nombre d'article
		if ((article = currentUtilisateur.getPanier().getItemByProduitId(produitId)) != null) {
			nbInPanier = article.getQuantite();
		}

		// Gestion erreurs
		try {
			if (nbProduit < 1)
				throw new IllegalArgumentException("Il faut au moins un article dans le panier"); // évidemment
																									// impossible
			if (nbProduit + nbInPanier > produit.getNbProduit())// acheter un trop grand nombre de produit ou déjà trop
																// dans panier
				throw new IllegalArgumentException("Le nombre d'articles proposés n'est pas suffisant");
			if (currentUtilisateur.getUserid().equals(produit.getUtilisateurId()))//
				throw new IllegalArgumentException("Ce n'est pas votre article !"); // Utilisateur == vendeur
			if (username == "anonymousUser") // Utilisateur non authentifié
				throw new IllegalArgumentException("Vous n'êtes pas connecté");

		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(createGlobalDTO(authentication, new ErrorDTO("Veuillez vous connectez !")));
			// redirect
		}

		article = new PanierItem(produit, nbProduit);
		article.setCommentaire(commentaire);
		currentUtilisateur.getPanier().add(article);// add fait déjà la somme des articles déjà présent

		utilisateurRepository.save(currentUtilisateur);

		// rediriger vers la page des détails du produit après l'ajout
		// String redirectUrl = "/produits/details/" + ajoutPanierDTO.getProduitId();
		// RedirectDTO redirectDTO = new RedirectDTO(redirectUrl);

		// GlobalDTO globalDTO = createGlobalDTO(authentication, redirectDTO);

		GlobalDTO globalDTO = createGlobalDTO(authentication,
				new MessageDTO(article.getProduit().getNom() + " ajouté au panier"));

		return ResponseEntity.ok(globalDTO);
	}

	@GetMapping("/utilisateur/{username}/historique")
	public ResponseEntity<GlobalDTO> historiqueAchats(@PathVariable String username, Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(username)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(createGlobalDTO(authentication, new ErrorDTO("Veuillez vous connectez !")));
		}

		Utilisateur currentUtilisateur = utilisateurRepository.findByUsername(authentication.getName());

		// List<Historique> hist = currentUtilisateur.getHistoriques().stream()
		// 		.filter(h -> !h.isArchived())
		// 		.collect(Collectors.toList());

		List<Historique> hist = new ArrayList<>();

		for (Historique h : currentUtilisateur.getHistoriques()) {
            if(!h.isArchived())
                hist.add(h);
        }



		HistoriqueAchatsDTO historiqueAchatsDTO = new HistoriqueAchatsDTO(hist);

		GlobalDTO globalDTO = createGlobalDTO(authentication, historiqueAchatsDTO);

		return ResponseEntity.ok(globalDTO);
	}


	//TODO : test

	@PostMapping("/utilisateur/{username}/supprimerHistorique")
	public ResponseEntity<GlobalDTO> deleteHistorique(@PathVariable String username,
			@RequestParam Long id,
			Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		Utilisateur currentUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
		Historique historique = historiqueRepository.findById(id)
				.orElseThrow(
						() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Historique invalide avec l'id:" + id));

		historique.setArchived(true);
		// si l'état de l'historique était process, il devient canceled (achat annulé),
		// s'il était paid il reste
		if (historique.getEtat().equals("process")) {
			historique.setEtat("canceled");
		}

		historiqueRepository.save(historique);
		utilisateurRepository.save(currentUtilisateur);

		MessageDTO messageDTO = new MessageDTO("Historique masqué avec succès");
		GlobalDTO globalDTO = createGlobalDTO(authentication, messageDTO);

		return ResponseEntity.ok(globalDTO);
	}

	//TODO : test

	@PostMapping("/utilisateur/{username}/supprimerPanier")
	public ResponseEntity<GlobalDTO> deletePanier(@PathVariable String username,
			@RequestParam Long produitId,
			Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		Utilisateur currentUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
		Produit article = produitRepository.findById(produitId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Produit invalide avec l'id:" + produitId));

		currentUtilisateur.getPanier().remove(article);
		utilisateurRepository.save(currentUtilisateur);

		MessageDTO messageDTO = new MessageDTO("Le produit " + article.getNom() + " a bien été supprimé du panier");
		GlobalDTO globalDTO = createGlobalDTO(authentication, messageDTO);

		return ResponseEntity.ok(globalDTO);
	}

	//TODO :test
	@PostMapping("/utilisateur/{username}/supprimerVente")
	public ResponseEntity<GlobalDTO> deleteVente(@PathVariable String username,
			@RequestParam Long produitId,
			Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		Utilisateur currentUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
		if (!currentUtilisateur.getUsername().equals(username)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}

		Produit article = produitRepository.findById(produitId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Produit invalide avec l'id:" + produitId));

		article.setArchived(true);
		produitRepository.save(article);

		MessageDTO messageDTO = new MessageDTO(article.getNom() + " a été supprimé du catalogue");
		GlobalDTO globalDTO = createGlobalDTO(authentication, messageDTO);

		return ResponseEntity.ok(globalDTO);
	}

	//TODO : test

	@PostMapping("/utilisateur/{username}/modifierPanier")
	public ResponseEntity<GlobalDTO> modifyPanier(@PathVariable String username,
			@RequestBody ModifierPanierDTO modifierPanierDTO,
			Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(createGlobalDTO(authentication, new ErrorDTO("Utilisateur non authentifié")));
		}

		int nbProduit = modifierPanierDTO.getQuantite();
		Long produitId = modifierPanierDTO.getProduitId();
		String commentaire = modifierPanierDTO.getCommentaire();

		Utilisateur currentUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
		PanierItem article = currentUtilisateur.getPanier().getItemByProduitId(produitId);

		// Gestion erreurs
		try {
			if (nbProduit < 1)
				throw new IllegalArgumentException("Il faut au moins un article dans le panier"); // évidemment
																									// impossible
			if (nbProduit > article.getProduit().getNbProduit())// acheter un trop grand nombre de produit ou déjà trop
																// dans panier
				throw new IllegalArgumentException("Le nombre d'articles proposés n'est pas suffisant");
			if (currentUtilisateur.getUserid().equals(article.getProduit().getUtilisateurId()))//
				throw new IllegalArgumentException("Ce n'est pas votre article !"); // Utilisateur == vendeur
			if (username == "anonymousUser") // Utilisateur non authentifié
				throw new IllegalArgumentException("Vous n'êtes pas connecté");

		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(createGlobalDTO(authentication, new ErrorDTO("Veuillez vous connectez !")));
			// redirect
		}

		article.setCommentaire(commentaire);
		article.setQuantite(nbProduit);
		utilisateurRepository.save(currentUtilisateur);

		MessageDTO messageDTO = new MessageDTO(
				"Le produit " + article.getProduit().getNom() + " a bien été modifié dans le panier");
		GlobalDTO globalDTO = createGlobalDTO(authentication, messageDTO);

		return ResponseEntity.ok(globalDTO);
	}

	//TODO : test

	@PostMapping("/utilisateur/{username}/validate")
	public ResponseEntity<GlobalDTO> checkout(@PathVariable String username,
			@RequestBody CheckoutDTO checkoutDTO,
			Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(createGlobalDTO(authentication, new ErrorDTO("Utilisateur non authentifié")));
		}

		Utilisateur currentUser = utilisateurRepository.findByUsername(authentication.getName());

		if (currentUser.getPanier().getProduits().isEmpty()) {
			return ResponseEntity.badRequest()
					.body(createGlobalDTO(authentication, new ErrorDTO("Message d'erreur approprié")));
		}

		if (currentUser.getPanier().getProduits().isEmpty()) {
			return ResponseEntity.badRequest()
					.body(createGlobalDTO(authentication, new ErrorDTO("Votre panier est vide.")));
		}

		// Verifie si les achats sont valide (nombre de produit ok,produit non archivé)
		for (PanierItem pi : currentUser.getPanier().getProduits()) {
			if (pi.getQuantite() > pi.getProduit().getNbProduit()) {
				return ResponseEntity.badRequest()
						.body(createGlobalDTO(authentication,
								new ErrorDTO("Oops plus de stock pour " + pi.getProduit().getNom())));

			}
			if (pi.getProduit().getArchived() == true) {
				return ResponseEntity.badRequest()
						.body(createGlobalDTO(authentication, new ErrorDTO(
								"Oops il semblerait que " + pi.getProduit().getNom() + "ne soit pas disponible")));

			}
		}

		// ICI le panier est validé

		// décrémente le nombre de produit et les sauvegarde
		for (PanierItem pi : currentUser.getPanier().getProduits()) {
			int quantite = pi.getQuantite();
			pi.getProduit().decreaseNb(quantite);
			produitRepository.save(pi.getProduit());
		}

		// Historique copie le panier de l'utilisateur

		List<PanierItem> historyItems = currentUser.getPanier().copyPanierItems();
		Historique historique = new Historique();
		historique.setPanierItems(historyItems);
		historique.setEtat("process");
		historique.setUtilisateur(currentUser);
		historique.setDate(LocalDateTime.now().toString());
		historique.setLivraison(checkoutDTO.getAdresse() + " " + checkoutDTO.getVille() + " " + checkoutDTO.getZip()
				+ " " + checkoutDTO.getPays());

		// TODO : Notification de Commande

		// Vider le panier après l'achat
		currentUser.getPanier().clearProduits();

		utilisateurRepository.save(currentUser);
		historiqueRepository.save(historique);

		return ResponseEntity.ok(createGlobalDTO(authentication, new MessageDTO("Achat envoyé !")));
	}

	// TODO: Form

	// SIGNUP LOGIN
	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
    private JwtService jwtService;

	@PostMapping("/login")
    public ResponseEntity<GlobalDTO> authenticateUser(@RequestBody LoginDTO LoginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
			LoginDTO.getUsername(), LoginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.GenerateToken(LoginDTO.getUsername());
		
		return ResponseEntity.ok(createGlobalDTO(authentication, new JwtDTO(token)));
    }

	@GetMapping("/getOrigin")
    public String getOrigin(HttpServletRequest request) {
		System.out.println(request.getHeader("Origin"));
        return "Origin: " + request.getHeader("Origin");
    }

	// @GetMapping("/checkout")
	// public String checkoutForm(@PathVariable String username, Model model,
	// Authentication authentication) {
	// Utilisateur currenUtilisateur =
	// utilisateurRepository.findByUsername(authentication.getName());
	// model.addAttribute("listProduits",
	// currenUtilisateur.getPanier().getProduits());
	// model.addAttribute("panier", currenUtilisateur.getPanier());
	// return "checkout";
	// }

	// méthodes pour l'importation d'image

	public static Optional<String> getExtensionByStringHandling(String filename) {
		return Optional.ofNullable(filename)
				.filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}

	public static String getFileNameWithoutExtension(String fileName) {
		if (fileName == null) {
			return null;
		}

		int indexOfLastDot = fileName.lastIndexOf('.');

		if (indexOfLastDot > 0) {
			return fileName.substring(0, indexOfLastDot);
		}

		return fileName;
	}

	/**
	 * Tronque une chaîne de caractères jusqu'à un maximum de caractères.
	 *
	 * @param input     La chaîne de caractères à tronquer.
	 * @param maxLength La longueur maximale de la chaîne tronquée.
	 * @return La chaîne tronquée.
	 */
	public static String truncate(String input, int maxLength) {
		if (input == null) {
			return null;
		}
		if (input.length() <= maxLength) {
			return input;
		}
		return input.substring(0, maxLength);
	}

}
