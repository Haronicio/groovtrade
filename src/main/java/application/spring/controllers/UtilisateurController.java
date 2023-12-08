package application.spring.controllers;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import application.spring.entities.Historique;
import application.spring.entities.PanierItem;
import application.spring.entities.Produit;
import application.spring.entities.Utilisateur;
import application.spring.repository.HistoriqueRepository;
import application.spring.repository.ProduitRepository;
import application.spring.repository.UtilisateurRepository;



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
    public String profile(@PathVariable String username, Model model, Authentication authentication) {
        // Utilisateur currenUtilisateur =
        // utilisateurRepository.findByUsername(authentication.getName());
        Utilisateur user = utilisateurRepository.findByUsername(username);

        model.addAttribute("user_email", user.getEmail());
        model.addAttribute("user_name", username);// éviter conflit avec le model global
        model.addAttribute("user_PP", user.getImgPath());// éviter conflit avec le model global
        return "profile";
    }

    @PostMapping("/ajouterPanier")
    public String ajouterPanier(@PathVariable String username, Model model, Authentication authentication,RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            @RequestParam Long produitId,
            @RequestParam int nbProduit,
            @RequestParam(value = "commentaire", required = false, defaultValue = "") String commentaire) {
        // verif referer avec l'exception
        String referer = request.getHeader("Referer");
        Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new IllegalArgumentException("Produit invalide avec l'id:" + produitId));

        int nbInPanier = 0;
        PanierItem article;
        // Vérification dans le controller du bon nombre d'article
        if ((article = currenUtilisateur.getPanier().getItemByProduitId(produitId)) != null) {
            nbInPanier = article.getQuantite();
        }

        // Gestion erreurs
        try {
            if (nbProduit < 1)
                throw new IllegalArgumentException("Il faut au moins un article dans le panier"); // évidemment impossible
            if (nbProduit + nbInPanier > produit.getNbProduit())// acheter un trop grand nombre de produit ou déjà trop
                                                                // dans panier
                throw new IllegalArgumentException("Le nombre d'articles proposés n'est pas suffisant");
            if (currenUtilisateur.getUserid().equals(produit.getUtilisateurId()))//
                throw new IllegalArgumentException("Ce n'est pas votre article !"); // Utilisateur == vendeur
            if (username == "anonymousUser") // Utilisateur non authentifié
                throw new IllegalArgumentException("Vous n'êtes pas connecté");

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            return "redirect:" + referer;
        }

        article = new PanierItem(produit, nbProduit);
        article.setCommentaire(commentaire);
        currenUtilisateur.getPanier().add(article);// add fait déjà la somme des articles déjà présent

        // System.out.println("\n\n"+currenUtilisateur.getPanier()+"\n\n");

        utilisateurRepository.save(currenUtilisateur);

        redirectAttributes.addFlashAttribute("message",article.getProduit().getNom() + " ajouté au panier");

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

        List<Historique> hist = new ArrayList<>();

        for (Historique h : currenUtilisateur.getHistoriques()) {
            if(!h.isArchived())
                hist.add(h);
        }

        model.addAttribute("historiques", hist);
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

        model.addAttribute("listProduits", historique.getProduits());

        return correctUser(authentication.getName(), username); // Vue correspondante
    }

    @PostMapping("/supprimerHistorique")
    public String deleteHistorique(@PathVariable String username, Model model, Authentication authentication,HttpServletRequest request,RedirectAttributes redirectAttributes,
            @RequestParam Long id) {
        //verif referer avec l'exception
        String referer = request.getHeader("Referer");
        Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
        Historique historique = historiqueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Historique invalide avec l'id:" + id));

        historique.setArchived(true);

        // si l'état de l'historique était process, il devient canceled (achat annulé),
        // s'il était paid il reste
        if (historique.getEtat().equals("process")) {
            historique.setEtat("canceled");
        }

        historiqueRepository.save(historique);
        utilisateurRepository.save(currenUtilisateur);

        System.out.println((historique == null));
        System.out.println(historique.getEtat());

         redirectAttributes.addFlashAttribute("message","Historique masqué avec succès");
        return "redirect:" + referer;
    }

    @PostMapping("/supprimerPanier")
    public String deletePanier(@PathVariable String username, Model model, Authentication authentication,HttpServletRequest request,RedirectAttributes redirectAttributes,
            @RequestParam Long produitId) {

        // verif referer avec l'exception
        String referer = request.getHeader("Referer");
        Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
        Produit article = produitRepository.findById(produitId)
                .orElseThrow(() -> new IllegalArgumentException("Produit invalide avec l'id:" + produitId));

        currenUtilisateur.getPanier().remove(article);

        utilisateurRepository.save(currenUtilisateur);

         redirectAttributes.addFlashAttribute("message", "Le produit "+ article.getNom() +" à bien été modifié");

        // return "redirect:/utilisateur/" + currenUtilisateur.getUsername() +
        // "/panier";
        return "redirect:" + referer;
    }

    @PostMapping("/modifierPanier")
    public String modifyPanier(@PathVariable String username, Model model, Authentication authentication,
            HttpServletRequest request,RedirectAttributes redirectAttributes,
            @RequestParam Long produitId,
            @RequestParam int nb,
            @RequestParam(required = false, defaultValue = "") String commentaire) {

        // verif referer avec l'exception
        String referer = request.getHeader("Referer");

        Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
        PanierItem article = currenUtilisateur.getPanier().getItemByProduitId(produitId);

        // verif referer 
        try {
            if (nb < 1)
                throw new IllegalArgumentException("Il faut au moins un article dans le panier"); // évidemment impossible
            if (nb > article.getProduit().getNbProduit())// acheter un trop grand nombre de produit
                throw new IllegalArgumentException("Le nombre d'articles proposés n'est pas suffisant");
            if (currenUtilisateur.getUserid().equals(article.getProduit().getUtilisateurId()))//
                throw new IllegalArgumentException("Utilisateur == vendeur"); // Utilisateur == vendeur
            if (username == "anonymousUser") // Utilisateur non authentifié
                throw new IllegalArgumentException("Utilisateur non authentifié");

        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:" + referer;
        }

        
        article.setCommentaire(commentaire);
        article.setQuantite(nb);
        
        utilisateurRepository.save(currenUtilisateur);

        redirectAttributes.addFlashAttribute("message", "Le produit "+ article.getProduit().getNom() +" à bien été modifié");
        return "redirect:" + referer + "#quantite";

        // return "redirect:/utilisateur/" + currenUtilisateur.getUsername() +
        // "/panier";

        // return correctUser(authentication.getName(), username);
    }

    @PostMapping("/supprimerVente")
    public String deleteVente(@PathVariable String username, Model model, Authentication authentication,RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            @RequestParam Long produitId) {
        // vérif referer dans exception handler 
        String referer = request.getHeader("Referer");
        Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());
        Produit article = produitRepository.findById(produitId)
                .orElseThrow(() -> new IllegalArgumentException("Produit invalide avec l'id:" + produitId));

        article.setArchived(true);
        produitRepository.save(article);

        redirectAttributes.addFlashAttribute("message",article.getNom()+" à été supprimé du catalogue");

        return "redirect:" + referer;

        // return "redirect:/utilisateur/" + currenUtilisateur.getUsername() +
        // "/panier";

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
    public String checkout(@PathVariable String username, Model model, Authentication authentication,RedirectAttributes redirectAttributes,
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
        // Vérifie si le panier n'est pas vide
        if (currentUser.getPanier().getProduits().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Votre panier est vide.");
            return "redirect:/produits/liste";
        }

        // Verifie si les achats sont valide (nombre de produit ok,produit non archivé)
        for (PanierItem pi : currentUser.getPanier().getProduits()) {
            if (pi.getQuantite() > pi.getProduit().getNbProduit()) {
                redirectAttributes.addFlashAttribute("error", "Oops plus de stock pour " + pi.getProduit().getNom());
                return "redirect:/utilisateur/" + currentUser.getUsername() + "/checkout";
            }
            if (pi.getProduit().getArchived() == true) {
                redirectAttributes.addFlashAttribute("error",
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
        historique.setProduits(historyItems);
        historique.setEtat("process");
        historique.setDate(new Date(System.currentTimeMillis()));
        historique.setLivraison(adresse + " " + ville + " " + zip + " " + pays);

        // TODO : Notification de Commande

        // Vider le panier après l'achat
        currentUser.getPanier().clearProduits();

        utilisateurRepository.save(currentUser);
        historiqueRepository.save(historique);

         redirectAttributes.addFlashAttribute("message", "Achat envoyé !");

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
