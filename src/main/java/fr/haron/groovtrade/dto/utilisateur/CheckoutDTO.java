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

public class CheckoutDTO  implements DTOInterface {
    String nom;
    String adresse;
    String ville;
    String pays;
    String zip;
    String nomCarte;
    String numero_carte;
    String cvv_carte;
    String annee_mois_carte;

    // Getters et setters
}
