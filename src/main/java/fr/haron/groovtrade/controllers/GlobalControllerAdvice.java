package fr.haron.groovtrade.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


//Controller Globale qui utilise un modèle disponible dans toutes les vues, ainsi l'username peut être utilisé sur toutes les vues
@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void globalAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("username", username);
        }
    }
}
