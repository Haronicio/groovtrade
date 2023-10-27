package fr.haron.groovtrade.controllers;

import java.util.List;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.haron.groovtrade.dao.ProduitRepository;
import fr.haron.groovtrade.entities.Produit;
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
            @RequestParam int annee) {
        StringJoiner joiner = new StringJoiner(",");
        for (String genre : genres) {
            joiner.add(genre);
        }

        ProduitMeta meta = new ProduitMeta(nom, artiste, album, annee, joiner.toString());
        Produit nouveauProduit = new Produit(prix, description, type, meta);

        produitRepository.save(nouveauProduit);
        return "redirect:/produits/liste";
    }
}
