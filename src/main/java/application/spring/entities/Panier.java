package application.spring.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

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
public class Panier implements Serializable{
    
    // @ManyToMany(
    //     fetch = FetchType.LAZY,//à la récupération de la catégorie, les produits ne sont pas récupérés
    //     cascade = {
    //         //la cascade s’applique tant en création qu’en modification
    //         CascadeType.PERSIST,
    //         CascadeType.MERGE
    //     }
    // )
    // @JoinTable(
    //     name = "panier_produits",
    //     joinColumns = @JoinColumn(name = "panierid"),
    //     inverseJoinColumns = @JoinColumn(name = "produitid")
    // )
    @ElementCollection
    @CollectionTable(name = "panier_produits", joinColumns = @JoinColumn(name = "utilisateur_id"))
    private List<PanierItem> produits = new ArrayList<>();

    // @OneToOne(cascade = {
    //     CascadeType.MERGE,
    //     CascadeType.PERSIST
    // })
    // @JoinColumn(name = "userid")
    // private Utilisateur utilisateur;



    public boolean remove(Produit produit)
    {
        for (PanierItem item : produits) {
            if (item.getProduit().equals(produit)) {
                produits.remove(item);
                return true;
            }
        }
        return false;
    }
    public boolean removeOne(Produit produit)
    {
        for (PanierItem item : produits) {
            if (item.getProduit().equals(produit)) {
                if(item.getQuantite()<=1){
                    produits.remove(item);
                }else{
                    item.setQuantite(item.getQuantite()-1);
                }
                return true;
            }
        }
        return false;
    }
    public boolean add(PanierItem e)
    {
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