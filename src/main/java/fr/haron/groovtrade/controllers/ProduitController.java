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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fr.haron.groovtrade.dao.ProduitRepository;
import fr.haron.groovtrade.entities.Produit;
import fr.haron.groovtrade.entities.ProduitImg;
import fr.haron.groovtrade.entities.ProduitMeta;
import fr.haron.groovtrade.entities.ProduitType;

@Controller
@RequestMapping("/produits")
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping("/liste")
    public String liste(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        List<Produit> produits = produitRepository.findByDescriptionLike("%" + keyword + "%");
        model.addAttribute("listProduits", produits);
        return "produits";
    }

    @GetMapping("/details/{id}")
    public String detailsProduit(@PathVariable Long id, Model model) {
    Produit produit = produitRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Produit invalide avec l'id:" + id));
    
    model.addAttribute("produit", produit);
    return "detailsProduit";
}

    @GetMapping("/delete")
    public String delete(Long id, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        produitRepository.deleteById(id);
        return "redirect:/produits/liste?keyword=" + keyword;
    }

    @PostMapping("/ajouter")
    public String ajouterProduit(@RequestParam String description,
            @RequestParam double prix,
            @RequestParam ProduitType type,
            @RequestParam List<String> genres,
            @RequestParam String nom,
            @RequestParam String artiste,
            @RequestParam String album,
            @RequestParam int annee,
            @RequestParam("coverImage") MultipartFile coverImage) {
        StringJoiner joiner = new StringJoiner(",");
        for (String genre : genres) {
            joiner.add(genre);
        }

        ProduitMeta meta = new ProduitMeta(nom, artiste, album, annee, joiner.toString());
        Produit nouveauProduit = new Produit(prix, description, type, meta);

        // TODO : Gestion de plusieurs images
        // Logique pour traiter l'image de couverture
        if (!coverImage.isEmpty()) {
            // Définir le chemin où vous voulez stocker les images
            // Exemple: "static/images/"
            String uploadDir = "src/main/resources/static/images";

            // // Obtenir le nom du fichier original
            // String originalFilename = coverImage.getOriginalFilename();

            // // Construire un nouveau nom de fichier pour éviter les conflits
            // String filename = StringUtils.cleanPath(originalFilename);

            // TODO : générer un nom + ajouter vérifications solide
            String filename = album + "." + getExtensionByStringHandling(coverImage.getOriginalFilename()).get();

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
            // Exemple : produit.setCoverImagePath(filename);
        }

        produitRepository.save(nouveauProduit);
        return "redirect:/produits/liste";
    }

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
          .filter(f -> f.contains("."))
          .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
    

}
