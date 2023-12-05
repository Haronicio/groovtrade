package fr.haron.groovtrade.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
// @Entity
@Embeddable
// @Table(name = "panierItems")

//Tandis que le controller manipule les produits directement, l'utilisateur lui manipule des PanierItem qui représente un produit avec une certain quantité
public class PanierItem implements Serializable {

    public PanierItem(Produit produit, int nbProduit) {
        this.produit = produit;
        this.quantite = nbProduit;
    }

     public PanierItem(Produit produit, int nbProduit,String commentaire) {
        this.produit = produit;
        this.quantite = nbProduit;
        this.commentaire = commentaire;
    }

    // @Id
    // @GeneratedValue
    // private Long id;

    @ManyToOne
    private Produit produit;

    private int quantite;
    private String commentaire;
}
