package application.spring.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import application.spring.model.Historique;
import application.spring.model.Panier;
import application.spring.model.Produit;
import application.spring.model.ProduitImg;
import application.spring.model.ProduitMeta;
import application.spring.model.ProduitType;
import application.spring.model.Utilisateur;
import application.spring.repository.ProduitRepository;
import application.spring.repository.UtilisateurRepository;

@RestController
@EnableWebMvc
@RequestMapping("/prodtuis")
public class AppController {
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	

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

	@GetMapping("/user")
	public String getUser() {
		return "Welcome, User";
	}
	
	@GetMapping("/admin")
	public String getAdmin() {
		return "Welcome_, Admin";
	}

	@GetMapping("/")
	public String getUserInfo(Principal user) {
		StringBuffer userInfo= new StringBuffer();
		 if(user instanceof UsernamePasswordAuthenticationToken){
			userInfo.append(getUserNamePasswordLoginInfo(user));
		} 
		// else if(user instanceof OAuth2AuthenticationToken){
		// 	userInfo.append(getOauth2LoginInfo(user));
		// }
		return userInfo.toString();
	}

	//pour obtenir userName
	private StringBuffer getUserNamePasswordLoginInfo(Principal user){
		StringBuffer userNameInfo = new StringBuffer();
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)user;
		if(token.isAuthenticated()){
			User u = (User)token.getPrincipal();
			userNameInfo.append("Welcome "+u.getUsername());
		}else{
			userNameInfo.append("NA");
		}
		return userNameInfo;
	}

	@GetMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false); 
		session.invalidate();
		
	}
	@GetMapping("/test")
	public String test(){
		try{

			return "";
		}catch(Exception e){
			return "erreur?: "+e.getMessage();
		}
		
	}
	//pour obtenir token
	// private StringBuffer getOauth2LoginInfo(Principal user){

	// 	StringBuffer protectedInfo = new StringBuffer();
		
	// 	OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
	// 	OAuth2AuthorizedClient authClient = this.authorizedClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());
	// 	if(authToken.isAuthenticated()){
		
	// 	Map<String,Object> userAttributes = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();
	// 	String userToken = authClient.getAccessToken().getTokenValue();
	// 	protectedInfo.append("Welcome, " + userAttributes.get("name")+"<br><br>");
	// 	protectedInfo.append("e-mail: " + userAttributes.get("email")+"<br><br>");
	// 	protectedInfo.append("Access Token: " + userToken+"<br><br>");
	// 	}
	// 	else{
	// 	protectedInfo.append("NA");
	// 	}
	//  return protectedInfo;
	//  }

	    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping("/liste")
    public String liste(Model model,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "artiste", required = false) String artiste,
            @RequestParam(name = "nom", required = false) String nom,
            @RequestParam(name = "album", required = false) String album,
            @RequestParam(name = "genres", required = false) String genres,
            @RequestParam(name = "annee_inf", required = false, defaultValue = "0") Integer annee_inf,
            @RequestParam(name = "annee_sup", required = false, defaultValue = "2100") Integer annee_sup) {

        List<Produit> produits;

        boolean isAdvancedSearch = (artiste != null && !artiste.isEmpty()) ||
                (nom != null && !nom.isEmpty()) ||
                (album != null && !album.isEmpty()) ||
                (genres != null && !genres.isEmpty()) ||
                annee_inf != null ||
                annee_sup != null;

        if (isAdvancedSearch) {
            // Utiliser la recherche par critères
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

        model.addAttribute("listProduits", produits);
        return "produits";
    }

    @GetMapping("/details/{id}")
    public String detailsProduit(@PathVariable Long id, Model model) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit invalide avec l'id:" + id));

        model.addAttribute("produit", produit);
        return "detailsProduit";
    }

    @GetMapping("/delete")
    public String delete(Long id, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        produitRepository.deleteById(id);
        return "redirect:/produits/liste?keyword=" + keyword;
    }

    @GetMapping("/ajouterProduit")
    public String ajouterProduitForm() {
        return "ajouterProduit";
    }

    @PostMapping("/ajouter")
    public String ajouterProduit(@RequestParam String description,
            @RequestParam double prix,
            @RequestParam ProduitType type,
            @RequestParam List<String> genres,
            @RequestParam String nom,
            @RequestParam String artiste,
            @RequestParam String album,
            @RequestParam int annee,
            @RequestParam("coverImage") MultipartFile coverImage) {
        StringJoiner joiner = new StringJoiner(",");
        for (String genre : genres) {
            joiner.add(genre);
        }

        ProduitMeta meta = new ProduitMeta(nom, artiste, album, annee, joiner.toString());
        Produit nouveauProduit = new Produit(prix, description, type, meta);

        // TODO : Gestion de plusieurs images
        // Logique pour traiter l'image de couverture
        if (!coverImage.isEmpty()) {
            // Définir le chemin où vous voulez stocker les images
            // Exemple: "static/images/"
            String uploadDir = "src/main/resources/static/images";

            // // Obtenir le nom du fichier original
            // String originalFilename = coverImage.getOriginalFilename();

            // // Construire un nouveau nom de fichier pour éviter les conflits
            // String filename = StringUtils.cleanPath(originalFilename);

            // TODO : générer un nom + ajouter vérifications solide
            String filename = album + "." + getExtensionByStringHandling(coverImage.getOriginalFilename()).get();

            // Sauvegarder le fichier sur le disque dur
            try {
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                try (InputStream inputStream = coverImage.getInputStream()) {
                    Path filePath = uploadPath.resolve(filename);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                e.printStackTrace();
                // TODO : Gérer l'exception ici
            }

            // TODO : Ajouter le chemin de l'image à l'entité Produit
            nouveauProduit.addImg(new ProduitImg(filename));
            // Exemple : produit.setCoverImagePath(filename);
        }

        produitRepository.save(nouveauProduit);
        return "redirect:/produits/liste";
    }

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
	
}
