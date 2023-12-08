package fr.haron.groovtrade.dto.produit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import fr.haron.groovtrade.dto.DTOInterface;
import fr.haron.groovtrade.entities.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AjouterProduitDTO implements DTOInterface{
    private String description;
    private double prix;
    private ProduitType type;
    private List<String> genres;
    private String nom;
    private String artiste;
    private String album;
    private int annee;
    private int nb;
    private List<MultipartFile> coverImages;

    // Getters et setters
}
