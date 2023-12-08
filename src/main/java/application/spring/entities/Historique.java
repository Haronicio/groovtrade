package application.spring.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "historique")
// @Embeddable
public class Historique implements Serializable{
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,name = "historiqueid")
    private Long historiqueid;
    private boolean archived;

    private Date date;
    private String livraison;
    // @ManyToMany(
    //     fetch = FetchType.LAZY,//à la récupération de la catégorie, les produits ne sont pas récupérés
    //     cascade = {
    //         //la cascade s’applique tant en création qu’en modification
    //         CascadeType.PERSIST,
    //         CascadeType.MERGE
    //     }
    // )
    // @JoinTable(
    //     name = "historique_produits",
    //     joinColumns = @JoinColumn(name = "historiqueid"),
    //     inverseJoinColumns = @JoinColumn(name = "produitid")
    // )
    @ElementCollection
    @CollectionTable(name = "panier_produits", joinColumns = @JoinColumn(name = "utilisateur_id"))
    private List<PanierItem> produits = new ArrayList<>();



    public Historique(boolean archived, Date date, String livraison, List<PanierItem> produits){
        this.archived = archived;
        this.date = date;
        this.produits = produits;
        this.livraison = livraison;
    }
}