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
    
    @ManyToMany(
        fetch = FetchType.LAZY,//à la récupération de la catégorie, les produits ne sont pas récupérés
        cascade = {
            //la cascade s’applique tant en création qu’en modification
            CascadeType.PERSIST,
            CascadeType.MERGE
        }
    )
    @JoinTable(
        name = "panier_produits",
        joinColumns = @JoinColumn(name = "panierid"),
        inverseJoinColumns = @JoinColumn(name = "produitid")
    )
    private List<Produit> produits = new ArrayList<>();

    // @OneToOne(cascade = {
    //     CascadeType.MERGE,
    //     CascadeType.PERSIST
    // })
    // @JoinColumn(name = "userid")
    // private Utilisateur utilisateur;


    public boolean add(Produit e)
    {
        return produits.add(e);
    }
}