package application.spring.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.NameNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import application.spring.model.Historique;
import application.spring.model.Panier;
import application.spring.model.Produit;
import application.spring.model.Utilisateur;
import application.spring.repository.ProduitRepository;
import application.spring.repository.UtilisateurRepository;
import ch.qos.logback.classic.pattern.Util;

@RestController
@RequestMapping("/")
public class Controller {
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
		return panier.getPrduits();
	}

	@GetMapping("/historique")
	public List<List<Produit>> getHistorique(@AuthenticationPrincipal UserDetails userDetails){
		Utilisateur u = utilisateurRepository.findByUsername(userDetails.getUsername());
		List<Historique> historiques = u.getHistoriques();
		List<List<Produit>> res = new ArrayList<>();
		for(Historique h:historiques){
			res.add(h.getProduits());
		}
		return res;
	}

	


	//private OAuth2AuthorizedClientService authorizedClientService;
	// public Controller(OAuth2AuthorizedClientService authorizedClientService) {
	// 	this.authorizedClientService = authorizedClientService;
	//  }
	
	@GetMapping("/add")
	public void add(){
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		Long id = (long) 0;
		String username = "myadmin";
		String password = b.encode("admin");
		String role = "ADMIN";
		String email = "c@gmail.com";
		List<Historique> historiques = new ArrayList<>();
		Panier panier = new Panier();
		Utilisateur u = new Utilisateur(id, username, password, role, email, historiques,panier);
		utilisateurRepository.save(u);
	}


	@GetMapping("/")
	public String userInfo(@AuthenticationPrincipal UserDetails userDetails){
		Utilisateur u = utilisateurRepository.findByUsername(userDetails.getUsername());
		return "Welcome "+u.getUsername();
	}
	// @GetMapping("/")
	// public String getUserInfo(Principal user) {
	// 	StringBuffer userInfo= new StringBuffer();
	// 	 if(user instanceof UsernamePasswordAuthenticationToken){
	// 		userInfo.append(getUserNamePasswordLoginInfo(user));
	// 	} 
	// 	// else if(user instanceof OAuth2AuthenticationToken){
	// 	// 	userInfo.append(getOauth2LoginInfo(user));
	// 	// }
	// 	return userInfo.toString();
	// }

	//pour obtenir userName
	// private StringBuffer getUserNamePasswordLoginInfo(Principal user){
	// 	StringBuffer userNameInfo = new StringBuffer();
	// 	UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)user;
	// 	if(token.isAuthenticated()){
	// 		User u = (User)token.getPrincipal();
	// 		userNameInfo.append("Welcome "+u.getUsername());
	// 	}else{
	// 		userNameInfo.append("NA");
	// 	}
	// 	return userNameInfo;
	// }

	// @GetMapping("/logout")
	// public void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session){
	// 	SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false); 
	// 	session.invalidate();
		
	// }
	// @GetMapping("/test")
	// public String test(){
	// 	try{

	// 		Utilisateur user = utilisateurRepository.findByUserid(2L);

	// 		//test affiche produits dans 1 historique
	// 		List<Historique> historiques= user.getHistoriques();
	// 		Historique i = historiques.get(0);
	// 		List<Produit> p = i.getProduits();

	// 		System.out.println("historique");
	// 		p.forEach(x->System.out.print(x.getNom()+" "));
	// 		System.out.println();

	// 		//test affiche les produits dans panier
	// 		Panier panier = user.getPanier();
	// 		List<Produit> pp = panier.getPrduits();
	// 		pp.forEach(x->System.out.println(x.getNom()));


	// 		return Long.toString(panier.getPanierid());
	// 	}catch(Exception e){
	// 		return "erreur?: "+e.getMessage();
	// 	}
		
	// }
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
	
}
