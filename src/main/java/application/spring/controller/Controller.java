package application.spring.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import application.spring.model.Produit;
import application.spring.model.Utilisateur;
import application.spring.repository.UtilisateurRepository;

@RestController
public class Controller {
	@Autowired
	private UtilisateurRepository utilisateurRepository;

	//private OAuth2AuthorizedClientService authorizedClientService;
	// public Controller(OAuth2AuthorizedClientService authorizedClientService) {
	// 	this.authorizedClientService = authorizedClientService;
	//  }

	@GetMapping("/add")
	public void add(){
		Long id = (long) 0;
		String username = "admin";
		String password = "admin";
		String role = "ADMIN";
		String email = "c@gmail.com";
		List<Produit> produits = new ArrayList<>();
		Utilisateur u = new Utilisateur(id, username, password, role, email, null);
		utilisateurRepository.save(u);
	}

	@GetMapping("/user")
	public String getUser() {
		return "Welcome, User";
	}
	
	@GetMapping("/admin")
	public String getAdmin() {
		return "Welcome, Admin";
	}

	@GetMapping("/*")
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
