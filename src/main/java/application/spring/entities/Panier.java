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

    //Copie superficiel (il ce passent la ref de produit)
    public List<PanierItem> copyPanierItems() {

        List<PanierItem> res = new ArrayList<>();

        for (PanierItem panierItem : produits) { 
            res.add(new PanierItem(panierItem.getProduit(),panierItem.getQuantite(),panierItem.getCommentaire()));
        }
        return res;
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

    public PanierItem getItemByProduitId(Long id) {
        // Trouver l'article dans le panier
        for (PanierItem item : produits) {
            if (item.getProduit().getId().equals(id)) {
                return item;
            }
        }
        // Si le produit n'est pas trouvé, ajoutez un nouvel article
        return null;
    }

    public int getQuantiteTotal() {
        int res = 0;

        for (PanierItem panierItem : produits) {
            res += panierItem.getQuantite();
        }
        return res;
    }

    public int getTotal() {
        int res = 0;

        for (PanierItem panierItem : produits) {
            res += panierItem.getQuantite() * panierItem.getProduit().getPrix();
        }
        return res;
    }

    public void clearProduits() {
        produits.clear();
    }
}