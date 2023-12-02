package application.spring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produits")
public class Produit implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    private double prix;

    @Enumerated(EnumType.STRING)
    private ProduitType type;

    @Column(length = 3000) // Augmente la limite à 3000 caractères
    private String description;

    private Long utilisateurId;

    @ElementCollection
    @CollectionTable(name = "img_produit", joinColumns = @JoinColumn(name = "produit_id"))
    private List<ProduitImg> imgs = new ArrayList<>();

    public Produit(double prix,  String description,ProduitType type, ProduitMeta meta) {
        this.prix = prix;
        this.type = type;
        this.description = description;
        this.meta = meta;
    }

    @Embedded
    private ProduitMeta meta;

    public void setImgs(List<ProduitImg> imgs) {
        this.imgs = imgs;
    }

    public void addImg(ProduitImg img)
    {
        this.imgs.add(img);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", prix='" + getPrix() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            // ", imgs='" + getImgs() + "'" +
            // ", songs='" + getSongs() + "'" +
            ", meta='" + getMeta() + "'" +
            "}";
    }


}
