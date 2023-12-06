package fr.haron.groovtrade.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import fr.haron.groovtrade.dao.ProduitRepository;
import fr.haron.groovtrade.dao.UtilisateurRepository;
import fr.haron.groovtrade.entities.Produit;
import fr.haron.groovtrade.entities.ProduitImg;
import fr.haron.groovtrade.entities.ProduitMeta;
import fr.haron.groovtrade.entities.ProduitType;
import fr.haron.groovtrade.entities.Utilisateur;

@Controller
@RequestMapping("/produits")
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/liste")
    public String liste(Model model,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "artiste", required = false) String artiste,
            @RequestParam(name = "nom", required = false) String nom,
            @RequestParam(name = "album", required = false) String album,
            @RequestParam(name = "genres", required = false) String genres,
            @RequestParam(name = "annee_inf", required = false, defaultValue = "0") Integer annee_inf,
            @RequestParam(name = "annee_sup", required = false, defaultValue = "2100") Integer annee_sup) {

        List<Produit> produits;

        boolean isAdvancedSearch = (artiste != null && !artiste.isEmpty()) ||
                (nom != null && !nom.isEmpty()) ||
                (album != null && !album.isEmpty()) ||
                (genres != null && !genres.isEmpty()) ||
                annee_inf != null ||
                annee_sup != null;

        if (isAdvancedSearch) {
            // Utiliser la recherche par critères
            produits = produitRepository.findByCombinedCriteria(
                    "%" + keyword + "%",
                    "%" + (artiste != null ? artiste : "") + "%",
                    "%" + (nom != null ? nom : "") + "%",
                    "%" + (album != null ? album : "") + "%",
                    "%" + (genres != null ? genres : "") + "%",
                    annee_inf,
                    annee_sup);
        } else {
            // Utiliser une recherche globale
            produits = produitRepository.findGlobal("%" + keyword + "%");
        }

        model.addAttribute("listProduits", produits);
        return "produits";
    }

    @GetMapping("/details/{id}")
    public String detailsProduit(@PathVariable Long id, Model model, Authentication authentication) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit invalide avec l'id:" + id));

        model.addAttribute("produit", produit);

        Utilisateur vendorUser = utilisateurRepository.findByUserid(produit.getUtilisateurId());
        model.addAttribute("vendeurUsername", vendorUser.getUsername());

        // Permet de dire à la vue c'est l'objet peut être acheté (plus simple)
        // TODO : peut être introduire ce mécanisme sur d'autre mapping pour les vues
        boolean buyable;

        if (authentication != null) // Si le client n'est pas connecté il sera amené a ce connecter
        {
            buyable = !(vendorUser.getUsername().equals(authentication.getName()));
        } else
            buyable = true;

        model.addAttribute("buyable", buyable);

        return "detailsProduit";
    }

    @GetMapping("/delete")
    public String delete(Long id, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        produitRepository.deleteById(id);
        return "redirect:/produits/liste?keyword=" + keyword;
    }

    @GetMapping("/ajouterProduit")
    public String ajouterProduitForm() {
        return "ajouterProduit";
    }

    @PostMapping("/ajouter")
    public String ajouterProduit(
            @RequestParam String description,
            @RequestParam double prix,
            @RequestParam ProduitType type,
            @RequestParam List<String> genres,
            @RequestParam String nom,
            @RequestParam String artiste,
            @RequestParam String album,
            @RequestParam int annee,
            @RequestParam int nb,
            @RequestParam("coverImages") List<MultipartFile> coverImages,
            Authentication authentication) {

        StringJoiner joiner = new StringJoiner(",");
        for (String genre : genres) {
            joiner.add(genre);
        }

        ProduitMeta meta = new ProduitMeta(nom, artiste, album, annee, joiner.toString());
        Produit nouveauProduit = new Produit(prix, description, type, meta);

        nouveauProduit.setNbProduit(nb);

        if (authentication == null) {
            return "redirect:/login/";
        }
        Long utilisateurId = utilisateurRepository.findByUsername(authentication.getName()).getUserid();
        nouveauProduit.setUtilisateurId(utilisateurId);

        // Logique pour traiter l'image de couverture
        int i = 0;
        for (MultipartFile coverImage : coverImages) {
            if (!coverImage.isEmpty()) {
                // Définir le chemin où vous voulez stocker les images
                // Exemple: "static/images/"
                String uploadDir = "src/main/resources/static/images";

                // // Obtenir le nom du fichier original
                // String originalFilename = coverImage.getOriginalFilename();

                // // Construire un nouveau nom de fichier pour éviter les conflits
                // String filename = StringUtils.cleanPath(originalFilename);

                // TODO :ajouter vérifications solide
                String filename = utilisateurId.toString() + i + truncate(getFileNameWithoutExtension(coverImage.getOriginalFilename()),10) + "."
                        + getExtensionByStringHandling(coverImage.getOriginalFilename()).get();

                // Sauvegarder le fichier sur le disque dur
                try {
                    Path uploadPath = Paths.get(uploadDir);

                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    try (InputStream inputStream = coverImage.getInputStream()) {
                        Path filePath = uploadPath.resolve(filename);
                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // TODO : Gérer l'exception ici
                }

                // TODO : Ajouter le chemin de l'image à l'entité Produit
                nouveauProduit.addImg(new ProduitImg(filename));
                // Exemple : produit.setCoverImagePath(filename)
                i++;
            }
        }

        produitRepository.save(nouveauProduit);
        // return "redirect:/produits/liste";
        return "redirect:/utilisateur/" + authentication.getName() + "/ventes";
    }

    @GetMapping("/modifierProduit/{id}")
    public String modifierProduitForm(@PathVariable Long id, Model model) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit invalide avec l'id:" + id));

        model.addAttribute("produit", produit);
        return "modifierProduit";
    }

    @PostMapping("/modifier")
    public String ajouterProduit(Authentication authentication, Model model,
            @RequestParam Long id,
            @RequestParam String description,
            @RequestParam double prix,
            @RequestParam ProduitType type,
            @RequestParam List<String> genres,
            @RequestParam("nom") String nom,
            @RequestParam String artiste,
            @RequestParam String album,
            @RequestParam int annee,
            @RequestParam int nb,
            @RequestParam("coverImages") List<MultipartFile> coverImages) {

        StringJoiner joiner = new StringJoiner(",");
        for (String genre : genres) {
            joiner.add(genre);
        }

        // Produit produit = (Produit)model.getAttribute("produit");
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit invalide avec l'id:" + id));

        Utilisateur currenUtilisateur = utilisateurRepository.findByUsername(authentication.getName());

        // TODO : sécurité pour modification de produit
        if (produit.getUtilisateurId() != currenUtilisateur.getUserid())
            return "redirect:/utilisateur/" + authentication.getName() + "/ventes";

        produit.setDescription(description);
        produit.setPrix(prix);
        produit.setType(type);
        produit.setGenres(joiner.toString());
        produit.setNom(nom);
        produit.setArtiste(artiste);
        // String pastAlbum = produit.getAlbum();
        produit.setAlbum(album);
        produit.setAnnee(annee);
        produit.setNbProduit(nb);

        int i = 0;
        for (MultipartFile coverImage : coverImages) {
            // TODO : Gestion de plusieurs images
            if (!coverImage.isEmpty()) {
                String uploadDir = "src/main/resources/static/images";

                // // Obtenir le nom du fichier original
                // String originalFilename = coverImage.getOriginalFilename();

                // // Construire un nouveau nom de fichier pour éviter les conflits
                // String filename = StringUtils.cleanPath(originalFilename);

                // TODO : ajouter vérifications solide
                String filename = currenUtilisateur.getUserid().toString() + i + truncate(getFileNameWithoutExtension(coverImage.getOriginalFilename()),10) + "."
                        + getExtensionByStringHandling(coverImage.getOriginalFilename()).get();

                // Sauvegarder le fichier sur le disque dur
                try {
                    Path uploadPath = Paths.get(uploadDir);

                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    try (InputStream inputStream = coverImage.getInputStream()) {
                        Path filePath = uploadPath.resolve(filename);
                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // TODO : Gérer l'exception ici
                }

                // TODO : Ajouter le chemin de l'image à l'entité Produit
                // Exemple : produit.setCoverImagePath(filename);
                produit.addImg(new ProduitImg(filename));
                i++;
            }
        }

        produitRepository.save(produit);

        return "redirect:/utilisateur/" + authentication.getName() + "/ventes";
    }

    public static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
    public static String getFileNameWithoutExtension(String fileName) {
        if (fileName == null) {
            return null;
        }

        int indexOfLastDot = fileName.lastIndexOf('.');

        if (indexOfLastDot > 0) {
            return fileName.substring(0, indexOfLastDot);
        }

        return fileName;
    }

    /**
     * Tronque une chaîne de caractères jusqu'à un maximum de caractères.
     *
     * @param input La chaîne de caractères à tronquer.
     * @param maxLength La longueur maximale de la chaîne tronquée.
     * @return La chaîne tronquée.
     */
    public static String truncate(String input, int maxLength) {
        if (input == null) {
            return null;
        }
        if (input.length() <= maxLength) {
            return input;
        }
        return input.substring(0, maxLength);
    }

}
