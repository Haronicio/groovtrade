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
    //TODO : sujet au bug anonymousUser
    @ModelAttribute
    public void globalAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();   
        model.addAttribute("username", username);                                                   
        if (authentication != null && authentication.isAuthenticated() && username != "anonymousUser") {
            // System.out.println("user " + username);
            //Pose problème si "anonymousUser"
            Long userid = utilisateurRepository.findByUsername(username).getUserid();
            model.addAttribute("userid", userid);
        }//si anonymous user (buuug)

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle400(HttpMessageNotReadableException e) {
        System.err.println("Returning HTTP 400 Bad Request " + e);
        throw e;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handle500(HttpMessageNotReadableException e) {
        System.err.println("Returning HTTP 500 Bad Request " + e);
        throw e;
    }
}
