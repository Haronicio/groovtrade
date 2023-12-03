package fr.haron.groovtrade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.haron.groovtrade.dao.UtilisateurRepository;


//Controller Globale qui utilise un modèle disponible dans toutes les vues, ainsi l'username peut être utilisé sur toutes les vues
@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @ModelAttribute
    public void globalAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Long userid = utilisateurRepository.findByUsername(username).getUserid();
            model.addAttribute("username", username);
            model.addAttribute("userid", userid);
        }
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        System.err.println("Returning HTTP 400 Bad Request " + e);
        throw e;
    }
}
