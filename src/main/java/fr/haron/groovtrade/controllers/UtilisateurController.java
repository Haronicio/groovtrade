package fr.haron.groovtrade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
// @RequestMapping("/utilisateur/{id}")
@RequestMapping("/utilisateur/{username}")
public class UtilisateurController {


    @GetMapping
    public String utilisateur(@PathVariable String username, Model model, Authentication authentication) {
        // Récupérer et ajouter les informations de l'utilisateur au modèle
        return "utilisateur";
    }

    // @Autowired
    // private UtilisateurService utilisateurService;

    // Liste des produits en vente
    @GetMapping("/ventes")
    // public String listeVentes(@PathVariable Long id, Model model) {
    public String listeVentes(@PathVariable String username, Model model, Authentication authentication) {
        // Fournir le modèle avec la liste des produits en vente
        // model.addAttribute("produits", utilisateurService.getProduitsVente(id));
        return "utilisateur"; // Vue correspondante
    }

    // Panier de l'utilisateur
    @GetMapping("/panier")
    // public String panierUtilisateur(@PathVariable Long id, Model model) {
    public String panierUtilisateur (@PathVariable String username, Model model, Authentication authentication) {
        // Fournir le modèle avec le panier de l'utilisateur
        // model.addAttribute("panier", utilisateurService.getPanier(id));
        return "utilisateur"; // Vue correspondante
    }

    // Historique des achats
    @GetMapping("/historique")
    // public String historiqueAchats(@PathVariable Long id, Model model) {
    public String historiqueAchats(@PathVariable String username, Model model, Authentication authentication) {
        // Fournir le modèle avec l'historique des achats
        // model.addAttribute("historique", utilisateurService.getHistoriqueAchats(id));
        return "utilisateur"; // Vue correspondante
    }

    public String correctUser(String connectedUser,String userPath)
    {
        if(connectedUser.equals(userPath))
            return "utilisateur";
        return "utilisateur/"+connectedUser;

    }
}
