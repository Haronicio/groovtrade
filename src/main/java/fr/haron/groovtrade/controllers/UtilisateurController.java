package fr.haron.groovtrade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import fr.haron.groovtrade.dao.ProduitRepository;
import fr.haron.groovtrade.dao.UtilisateurRepository;
import fr.haron.groovtrade.entities.Produit;
import fr.haron.groovtrade.entities.Utilisateur;

@Controller
@RequestMapping("/utilisateur/{username}")
public class UtilisateurController {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping
    public String utilisateur(@PathVariable String username, Model model, Authentication authentication) {
        System.out.println(username+","+authentication.getName());
        // Récupérer et ajouter les informations de l'utilisateur au modèle
        return correctUser(authentication.getName(),username);
    }

    @PostMapping("/ajouterPanier")
    public String ajouterPanier(@PathVariable String username, Model model, Authentication authentication,
                                @RequestParam Long produitId )
    {
        Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
       Produit produit = produitRepository.findById(produitId)
        .orElseThrow(() -> new IllegalArgumentException("Produit invalide avec l'id:" + produitId));

        currenUtilisateur.getPanier().add(produit);

        // System.out.println("\n\n"+currenUtilisateur.getPanier()+"\n\n");

        utilisateurRepository.save(currenUtilisateur);

        return "redirect:/produits/details/"+produitId;
    }

    // @Autowired
    // private UtilisateurService utilisateurService;

    // Liste des produits en vente
    @GetMapping("/ventes")
    // public String listeVentes(@PathVariable Long id, Model model) {
    public String listeVentes(@PathVariable String username, Model model, Authentication authentication) {
        // Fournir le modèle avec la liste des produits en vente
         Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
          model.addAttribute("listProduits",produitRepository.findByUtilisateurId(currenUtilisateur.getUserid()));


        return correctUser(authentication.getName(),username); // Vue correspondante
    }

    // Panier de l'utilisateur
    @GetMapping("/panier")
    public String panierUtilisateur (@PathVariable String username, Model model, Authentication authentication) {
        // Fournir le modèle avec le panier de l'utilisateur
        Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
        
        // System.out.println("\n\npanierrr"+currenUtilisateur.getPanier()+"\n\n");

        model.addAttribute("listProduits", currenUtilisateur.getPanier().getProduits());
        return correctUser(authentication.getName(),username); // Vue correspondante
    }

    // Historique des achats
    @GetMapping("/historique")
    // public String historiqueAchats(@PathVariable Long id, Model model) {
    public String historiqueAchats(@PathVariable String username, Model model, Authentication authentication) {
        // Fournir le modèle avec l'historique des achats
        // model.addAttribute("historique", utilisateurService.getHistoriqueAchats(id));
        return correctUser(authentication.getName(),username); // Vue correspondante
    }

    //Renvoi la chaine correspondant à la vue de l'utilisateur connecté, une sécurité pour ne pas avoir accès à un autre compte que le sien
    public String correctUser(String connectedUser,String userPath)
    {
        if(connectedUser.equals(userPath))
            return "utilisateur";
        return "redirect:/utilisateur/"+connectedUser;

    }
}
