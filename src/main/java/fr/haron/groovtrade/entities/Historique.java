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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@ToString
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

// L'historique est une entité car il se maintient même sans utilisateur, comparé au Panier
@Table(name = "historique")
// @Embeddable
public class Historique implements Serializable{
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,name = "historique_id")
    private Long historiqueid;
    private boolean archived = false;
    private String etat;//process paid cancel
    private String date;
    private String livraison;
    
    /*
    @ManyToMany(
        fetch = FetchType.LAZY,//à la récupération de la catégorie, les produits ne sont pas récupérés
        cascade = {
            //la cascade s’applique tant en création qu’en modification
            CascadeType.PERSIST,
            CascadeType.MERGE
        }
    )
    @JoinTable(
        name = "historique_produits",
        joinColumns = @JoinColumn(name = "historique_id"),
        inverseJoinColumns = @JoinColumn(name = "produit_id")
    )
        
    private List<Produit> produits = new ArrayList<>();
    */

    @ElementCollection
    private List<PanierItem> panierItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur Utilisateur;

    public void add(PanierItem e)
    {
        this.panierItems.add(e);
    }

    public int getQuantiteTotal() {
        int res = 0;

        for (PanierItem panierItem : panierItems) {
            res += panierItem.getQuantite();
        }
        return res;
    }

    public int getTotal() {
        int res = 0;

        for (PanierItem panierItem : panierItems) {
            res += panierItem.getQuantite() * panierItem.getProduit().getPrix();
        }
        return res;
    }

}