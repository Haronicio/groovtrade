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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
// @Table(name = "panier")
@Embeddable
public class Panier implements Serializable {

    @ElementCollection
    @CollectionTable(name = "panier_produits", joinColumns = @JoinColumn(name = "utilisateur_id"))
    private List<PanierItem> produits = new ArrayList<>();

    //Copie superficiel (il ce passent la ref de produit)
    public List<PanierItem> copyPanierItems() {

        List<PanierItem> res = new ArrayList<>();

        for (PanierItem panierItem : produits) { 
            res.add(new PanierItem(panierItem.getProduit(),panierItem.getQuantite(),panierItem.getCommentaire()));
        }
        return res;
    }

    public boolean remove(Produit produit) {
        for (PanierItem item : produits) {
            if (item.getProduit().equals(produit)) {
                produits.remove(item);
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