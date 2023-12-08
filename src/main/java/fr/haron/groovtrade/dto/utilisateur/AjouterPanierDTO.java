package fr.haron.groovtrade.dto.utilisateur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import fr.haron.groovtrade.dto.DTOInterface;
import fr.haron.groovtrade.entities.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AjouterPanierDTO implements DTOInterface {
    private Long produitId;
    private int nbProduit;
    private String commentaire;

    // Getters et setters
}

