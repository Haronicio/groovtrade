package fr.haron.groovtrade.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import fr.haron.groovtrade.dao.HistoriqueRepository;
import fr.haron.groovtrade.dao.ProduitRepository;
import fr.haron.groovtrade.dao.UtilisateurRepository;
import fr.haron.groovtrade.entities.Historique;
import fr.haron.groovtrade.entities.PanierItem;
import fr.haron.groovtrade.entities.Produit;
import fr.haron.groovtrade.entities.Utilisateur;

@Controller
@RequestMapping("/utilisateur/{username}")
public class UtilisateurController {

    /**
     * Utilisateur juste sous chaine de charactère
     */

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private HistoriqueRepository historiqueRepository;

    @GetMapping
    public String utilisateur(@PathVariable String username, Model model, Authentication authentication) {
        System.out.println(username + "," + authentication.getName());
        // Récupérer et ajouter les informations de l'utilisateur au modèle
        return correctUser(authentication.getName(), username);
    }

    // Les infos du profiles de l'utilisateur
    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
        model.addAttribute("useremail", currenUtilisateur.getEmail());
        return "profile";
    }

    @PostMapping("/ajouterPanier")
    public String ajouterPanier(@PathVariable String username, Model model, Authentication authentication,
            @RequestParam Long produitId,
            @RequestParam int nbProduit,
            @RequestParam(value = "commentaire", required = false, defaultValue = "") String commentaire) {
        Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new IllegalArgumentException("Produit invalide avec l'id:" + produitId));

        int nbInPanier = 0;
        PanierItem article;
        // Vérification dans le controller du bon nombre d'article
        if ((article = currenUtilisateur.getPanier().getItemByProduitId(produitId)) != null) {
            nbInPanier = article.getQuantite();
        }

        // TODO : Gestion erreurs
        try {
            if (nbProduit + nbInPanier > produit.getNbProduit())// acheter un trop grand nombre de produit ou déjà trop
                                                                // dans panier
                throw new IllegalArgumentException();
            if (currenUtilisateur.getUserid().equals(produit.getUtilisateurId()))
                throw new IllegalArgumentException();
            if (username == "anonymousUser")
                throw new IllegalArgumentException();

        } catch (IllegalArgumentException e) {
            return "redirect:/produits/details/" + produitId;
        }

        article = new PanierItem(produit, nbProduit);
        article.setCommentaire(commentaire);
        currenUtilisateur.getPanier().add(article);// add fait déjà la somme des articles déjà présent

        // System.out.println("\n\n"+currenUtilisateur.getPanier()+"\n\n");

        utilisateurRepository.save(currenUtilisateur);

        return "redirect:/produits/details/" + produitId;
    }

    // @Autowired
    // private UtilisateurService utilisateurService;

    // Liste des produits en vente
    @GetMapping("/ventes")
    // public String listeVentes(@PathVariable Long id, Model model) {
    public String listeVentes(@PathVariable String username, Model model, Authentication authentication) {
        // Fournir le modèle avec la liste des produits en vente
        Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
        // avec manipulation des produits directement
        // model.addAttribute("listProduits",produitRepository.findByUtilisateurId(currenUtilisateur.getUserid()));
        // avec manipulation de ProduitItem
        model.addAttribute("listProduits", currenUtilisateur.getVentes(produitRepository.findAll()));
        model.addAttribute("closeAction", "ventes");
        return correctUser(authentication.getName(), username); // Vue correspondante
    }

    // Panier de l'utilisateur
    @GetMapping("/panier")
    public String panierUtilisateur(@PathVariable String username, Model model, Authentication authentication) {
        // Fournir le modèle avec le panier de l'utilisateur
        Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());

        // System.out.println("\n\npanierrr"+currenUtilisateur.getPanier()+"\n\n");

        model.addAttribute("listProduits", currenUtilisateur.getPanier().getProduits());
        model.addAttribute("closeAction", "panier");

        return correctUser(authentication.getName(), username); // Vue correspondante
    }

    // Historique des achats
    @GetMapping("/historique")
    // public String historiqueAchats(@PathVariable Long id, Model model) {
    public String historiqueAchats(@PathVariable String username, Model model, Authentication authentication) {
        // Fournir le modèle avec l'historique des achats
        // model.addAttribute("closeAction", "historique");
        Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
        model.addAttribute("historiques", currenUtilisateur.getHistoriques());
        return "historique";

    }

    // Détails historique
    @GetMapping("/historique/{id}/details")
    // public String historiqueAchats(@PathVariable Long id, Model model) {
    public String historiqueDetails(@PathVariable String username, @PathVariable Long id, Model model,
            Authentication authentication) {
        // Fournir le modèle avec l'historique des achats
        Historique historique = historiqueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Historique invalide avec l'id:" + id));

        model.addAttribute("listProduits", historique.getPanierItems());

        return correctUser(authentication.getName(), username); // Vue correspondante
    }

    @PostMapping("/supprimerHistorique")
    public String deleteHistorique(@PathVariable String username, Model model, Authentication authentication,
            @RequestParam Long id) {
        Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
        Historique historique = historiqueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Historique invalide avec l'id:" + id));

        historique.setArchived(true);

        // si l'état de l'historique était process, il devient canceled (achat annulé),
        // s'il était paid il reste
        if (historique.getEtat() == "process") {
            historique.setEtat("canceled");
        }

        historiqueRepository.save(historique);
        return "redirect:/utilisateur/" + currenUtilisateur.getUsername();
    }

    @PostMapping("/supprimerPanier")
    public String deletePanier(@PathVariable String username, Model model, Authentication authentication,
            @RequestParam Long produitId) {
        Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
        Produit article = produitRepository.findById(produitId)
                .orElseThrow(() -> new IllegalArgumentException("Produit invalide avec l'id:" + produitId));

        currenUtilisateur.getPanier().remove(article);

        utilisateurRepository.save(currenUtilisateur);

        return "redirect:/utilisateur/" + currenUtilisateur.getUsername() + "/panier";

        // return correctUser(authentication.getName(), username);
    }

    @PostMapping("/supprimerVente")
    public String deleteVente(@PathVariable String username, Model model, Authentication authentication,
            @RequestParam Long produitId) {
        Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
        Produit article = produitRepository.findById(produitId)
                .orElseThrow(() -> new IllegalArgumentException("Produit invalide avec l'id:" + produitId));

        article.setArchived(true);
        produitRepository.save(article);

        return "redirect:/utilisateur/" + currenUtilisateur.getUsername() + "/panier";

        // return correctUser(authentication.getName(), username);
    }

    @GetMapping("/checkout")
    public String checkoutForm(@PathVariable String username, Model model, Authentication authentication) {
        Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
        model.addAttribute("listProduits", currenUtilisateur.getPanier().getProduits());
        model.addAttribute("panier", currenUtilisateur.getPanier());
        return "checkout";
    }

    @PostMapping("/validate")
    public String checkout(@PathVariable String username, Model model, Authentication authentication,
            @RequestParam("nom") String nom,
            @RequestParam("adresse") String adresse,
            @RequestParam("ville") String ville,
            @RequestParam("pays") String pays,
            @RequestParam("zip") String zip,
            @RequestParam("nom_carte") String nomCarte,
            @RequestParam("numero_carte") String numeroCarte,
            @RequestParam("cvv_carte") String cvvCarte,
            @RequestParam("annee_mois_carte") String anneMoisCarte)

    /* @RequestParam("annee_carte") String anneeCarte */
    {
        Utilisateur currentUser = utilisateurRepository.findByUsername(authentication.getName());
        // TODO : Vérifier si le panier n'est pas vide
        if (currentUser.getPanier().getProduits().isEmpty()) {
            model.addAttribute("error", "Votre panier est vide.");
            return "redirect:/produit/liste";
        }

        // Verifie si les achats sont valide (nombre de produit ok,produit non archivé)
        for (PanierItem pi : currentUser.getPanier().getProduits()) {
            if (pi.getQuantite() > pi.getProduit().getNbProduit()) {
                model.addAttribute("error", "Oops plus de stock pour " + pi.getProduit().getNom());
                return "redirect:/utilisateur/" + currentUser.getUsername() + "/checkout";
            }
            if (pi.getProduit().getArchived() == true) {
                model.addAttribute("error",
                        "Oops il semblerait que " + pi.getProduit().getNom() + "ne soit pas disponible");
                return "redirect:/utilisateur/" + currentUser.getUsername() + "/checkout";
            }
        }

        // ICI le panier est validé

        // décrémente le nombre de produit et les sauvegarde
        for (PanierItem pi : currentUser.getPanier().getProduits()) {
            int quantite = pi.getQuantite();
            pi.getProduit().decreaseNb(quantite);
            produitRepository.save(pi.getProduit());
        }

        // Historique copie le panier de l'utilisateur

        List<PanierItem> historyItems = currentUser.getPanier().copyPanierItems();
        Historique historique = new Historique();
        historique.setPanierItems(historyItems);
        historique.setEtat("process");
        historique.setUtilisateur(currentUser);
        historique.setDate(LocalDateTime.now().toString());
        historique.setLivraison(adresse + " " + ville + " " + zip + " " + pays);

        // TODO : Notification de Commande

        // Vider le panier après l'achat
        currentUser.getPanier().clearProduits();

        utilisateurRepository.save(currentUser);
        historiqueRepository.save(historique);

        // TODO : message de confirmation

        return "redirect:/produits/liste";
    }

    // Renvoi la chaine correspondant à la vue de l'utilisateur connecté, une
    // sécurité pour ne pas avoir accès à un autre compte que le sien
    public String correctUser(String connectedUser, String userPath) {
        if (connectedUser.equals(userPath))
            return "utilisateur";
        return "redirect:/utilisateur/" + connectedUser;

    }

}
