package fr.haron.groovtrade.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

// Changer pour Embedded
// @Entity
// @Table(name = "produit_meta")
@Embeddable
public class ProduitMeta implements Serializable
{
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;

    private String nom;
    private String artiste;
    private String album;
    private int annee;
    private String genres;

    // Changer pour Embedded
    // @OneToOne(mappedBy = "meta")
    // private Produit produit;


    public ProduitMeta(String nom, String artiste, String album, int annee, String genres) {
        this.nom = nom; //= titre ou nom du produit
        this.artiste = artiste;
        this.album = album;
        this.annee = annee;
        this.genres = genres;
    }


    public ProduitMeta() {
    }


    // public Long getId() {
    //     return this.id;
    // }

    // public void setId(Long id) {
    //     this.id = id;
    // }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getArtiste() {
        return this.artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public String getAlbum() {
        return this.album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getAnnee() {
        return this.annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    // public String getGenres() {
    //     return this.genres;
    // }
    public String getGenres() {
        return this.genres;
    }

    // public void setGenres(List<MetaGenre> genres) {
    //     this.genres = genres;
    // }

    public void setGenres(String genres) {
        this.genres = genres;
    }


    @Override
    public String toString() {
        return "{" +
            // " id='" + getId() + "'" +
            ", nom='" + getNom() + "'" +
            ", artiste='" + getArtiste() + "'" +
            ", album='" + getAlbum() + "'" +
            ", annee='" + getAnnee() + "'" +
            ", genres='" + getGenres() + "'" +
            "}";
    }


    // public Produit getProduit() {
    //     return produit;
    // }


    // public void setProduit(Produit produit) {
    //     this.produit = produit;
    // }

    
}

