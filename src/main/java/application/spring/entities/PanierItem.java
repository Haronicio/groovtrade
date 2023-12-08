package application.spring.entities;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
// @Entity
@Embeddable
// @Table(name = "panierItems")

// Tandis que le controller manipule les produits directement, l'utilisateur lui
// manipule des PanierItem qui représente un produit avec une certain quantité
public class PanierItem implements Serializable {

    public PanierItem(Produit produit, int nbProduit) {
        this.produit = produit;
        this.quantite = nbProduit;
    }

    @ManyToOne
    private Produit produit;
    private String commentaire;
    private int quantite;
}
