package fr.haron.groovtrade.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

// @Entity
// @Table(name = "produit_imgs")
@Embeddable
public class ProduitImg implements Serializable
{
    // @Id
    // @GeneratedValue
    // private Long id;

    private String path;

    // @ManyToOne
    // private Produit produit;


    public ProduitImg(String path) {
        this.path = path;
    }


    public ProduitImg() {
    }


    // public Long getId() {
    //     return this.id;
    // }

    // public void setId(Long id) {
    //     this.id = id;
    // }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    @Override
    public String toString() {
        return "{" +
            // " id='" + getId() + "'" +
            ", path='" + getPath() + "'" +
            "}";
    }


}

