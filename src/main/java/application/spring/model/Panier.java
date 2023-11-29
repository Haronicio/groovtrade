package application.spring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
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
@Table(name = "panier")
public class Panier implements Serializable{
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,name = "panierid")
    private Long panierid;

    
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
    private List<Produit> prduits = new ArrayList<>();

    @OneToOne(cascade = {
        CascadeType.MERGE,
        CascadeType.PERSIST
    })
    @JoinColumn(name = "userid")
    private Utilisateur utilisateur;
}
