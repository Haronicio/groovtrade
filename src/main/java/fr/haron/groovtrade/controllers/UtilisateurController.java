package fr.haron.groovtrade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
// @RequestMapping("/utilisateur/{id}")
@RequestMapping("/utilisateur/0")
public class UtilisateurController {

    // Supposons que vous ayez un service qui gère les données des utilisateurs
    // @Autowired
    // private UtilisateurService utilisateurService;

    // Liste des produits en vente
    @GetMapping("/ventes")
    // public String listeVentes(@PathVariable Long id, Model model) {
    public String listeVentes(Model model) {
        // Fournir le modèle avec la liste des produits en vente
        // model.addAttribute("produits", utilisateurService.getProduitsVente(id));
        return "utilisateur"; // Vue correspondante
    }

    // Panier de l'utilisateur
    @GetMapping("/panier")
    // public String panierUtilisateur(@PathVariable Long id, Model model) {
    public String panierUtilisateur (Model model) {
        // Fournir le modèle avec le panier de l'utilisateur
        // model.addAttribute("panier", utilisateurService.getPanier(id));
        return "utilisateur"; // Vue correspondante
    }

    // Historique des achats
    @GetMapping("/historique")
    // public String historiqueAchats(@PathVariable Long id, Model model) {
    public String historiqueAchats(Model model) {
        // Fournir le modèle avec l'historique des achats
        // model.addAttribute("historique", utilisateurService.getHistoriqueAchats(id));
        return "utilisateur"; // Vue correspondante
    }

}
