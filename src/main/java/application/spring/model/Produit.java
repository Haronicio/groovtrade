package application.spring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produits")
public class Produit implements Serializable{
    @Id
    @GeneratedValue
    private Long produitid;

    private double prix;

    @Enumerated(EnumType.STRING)
    private ProduitType type;

    @Column(length = 3000) // Augmente la limite à 3000 caractères
    private String description;

    // @OneToMany(cascade = CascadeType.ALL)
    // @JoinColumn(name = "img_id")

    @ElementCollection
    @CollectionTable(name = "img_produit", joinColumns = @JoinColumn(name = "produit_id"))
    private List<ProduitImg> imgs = new ArrayList<>();

    // @OneToMany(cascade = CascadeType.ALL)
    // private List<ProduitSong> songs = new ArrayList<>();

    //TODO : pourquoi pas embended ? Changer pour emmbeded

    @Embedded
    private ProduitMeta meta;

    public Produit(double prix,  String description,ProduitType type, ProduitMeta meta) {
        this.prix = prix;
        this.type = type;
        this.description = description;
        this.meta = meta;
    }
    public void addImg(ProduitImg img)
    {
        this.imgs.add(img);
    }
    @Override
    public String toString() {
        return "{" +
            " id='" + getProduitid() + "'" +
            ", prix='" + getPrix() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            // ", imgs='" + getImgs() + "'" +
            // ", songs='" + getSongs() + "'" +
            ", meta='" + getMeta() + "'" +
            "}";
    }


}
