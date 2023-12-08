package fr.haron.groovtrade.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


    @GetMapping("/login")//ne semble pas fonctionner peut être a cause de 
    public String showLoginPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Vérifie si l'utilisateur est déjà authentifié
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/login/done"; // Redirige vers la page d'accueil
        }

        return "login";
    }

     @GetMapping("/login/done")
    public String doneLogin() {

        return "redirect:/welcome"; // Redirige vers la page d'accueil

    }

    @GetMapping("/welcome")
    public String welcome() {
        return "guide";}
}