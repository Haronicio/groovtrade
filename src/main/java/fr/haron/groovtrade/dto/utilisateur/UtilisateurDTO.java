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

public class UtilisateurDTO implements DTOInterface {
    private List<PanierItem> listProduits;
    private String closeAction;

    // Getters et setters
}
