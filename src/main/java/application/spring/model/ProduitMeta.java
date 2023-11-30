package application.spring.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// Changer pour Embedded
// @Entity
// @Table(name = "produit_meta")
@Getter
@Setter
@AllArgsConstructor
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
}

