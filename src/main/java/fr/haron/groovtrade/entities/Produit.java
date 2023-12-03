package fr.haron.groovtrade.entities;

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

@Entity
@Table(name = "produits")
public class Produit implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    private int nbProduit;

   

    private double prix;

    @Enumerated(EnumType.STRING)
    private ProduitType type;

    @Column(length = 3000) // Augmente la limite à 3000 caractères
    private String description;

    private Long utilisateurId;

    //par défaut le produit est visible
    private boolean archived = false;

    @ElementCollection
    @CollectionTable(name = "img_produit", joinColumns = @JoinColumn(name = "produit_id"))
    private List<ProduitImg> imgs = new ArrayList<>();

    @Embedded
    private ProduitMeta meta;


    public Produit(double prix,  String description,ProduitType type, ProduitMeta meta) {
        this.prix = prix;
        this.type = type;
        this.description = description;
        this.meta = meta;
    }

    public Produit() {
    }
    
     public boolean isArchived() {
        return this.archived;
    }

    public boolean getArchived() {
        return this.archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Long getUtilisateurId() {
        return this.utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
    
     public int getNbProduit() {
        return this.nbProduit;
    }

    public void setNbProduit(int nbProduit) {
        this.nbProduit = nbProduit;
    }

    public String getNom() {
        return this.meta.getNom();
    }
    public void setNom(String s) {
        this.meta.setNom(s);
    }

    public String getArtiste() {
         return this.meta.getArtiste();
    }

        public void setArtiste(String s) {
        this.meta.setArtiste(s);
    }

    public String getAlbum() {
         return this.meta.getAlbum();
    }

         public void setAlbum(String s) {
        this.meta.setAlbum(s);
    }

    public int getAnnee() {
        return this.meta.getAnnee();
    }

         public void setAnnee(int i) {
        this.meta.setAnnee(i);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrix() {
        return this.prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public ProduitType getType() {
        return this.type;
    }

    public void setType(ProduitType type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProduitImg> getImgs() {
        return this.imgs;
    }

    public void setImgs(List<ProduitImg> imgs) {
        this.imgs = imgs;
    }

    public void addImg(ProduitImg img)
    {
        this.imgs.add(img);
    }

    // public List<ProduitSong> getSongs() {
    //     return this.songs;
    // }

    // public void setSongs(List<ProduitSong> songs) {
    //     this.songs = songs;
    // }

    public ProduitMeta getMeta() {
        return this.meta;
    }

    public void setMeta(ProduitMeta meta) {
        this.meta = meta;
    }
    public void setGenres(String string) {
            this.meta.setGenres(string);
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
