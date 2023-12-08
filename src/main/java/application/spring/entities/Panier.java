package application.spring.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Panier implements Serializable {

    @ElementCollection
    @CollectionTable(name = "panier_produits", joinColumns = @JoinColumn(name = "utilisateur_id"))
    private List<PanierItem> produits = new ArrayList<>();

    public boolean remove(Produit produit) {
        for (PanierItem item : produits) {
            if (item.getProduit().equals(produit)) {
                produits.remove(item);
                return true;
            }
        }
        return false;
    }

    public boolean removeOne(Produit produit) {
        for (PanierItem item : produits) {
            if (item.getProduit().equals(produit)) {
                if (item.getQuantite() <= 1) {
                    produits.remove(item);
                } else {
                    item.setQuantite(item.getQuantite() - 1);
                }
                return true;
            }
        }
        return false;
    }

    public boolean add(PanierItem e) {
        // Trouver l'article dans le panier
        for (PanierItem item : produits) {
            if (item.getProduit().equals(e.getProduit())) {
                // Augmenter la quantité et retourner
                item.setQuantite(item.getQuantite() + e.getQuantite());
                return true;
            }
        }
        // Si le produit n'est pas trouvé, ajoutez un nouvel article
        return produits.add(e);
    }
}