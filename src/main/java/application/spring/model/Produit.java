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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "produit")
public class Produit implements Serializable{
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,name = "produitid")
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
}
