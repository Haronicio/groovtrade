package application.spring.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Changer pour Embedded
// @Entity
// @Table(name = "produit_meta")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProduitMeta implements Serializable
{
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;

    @Column(length = 500)
    private String nom;
    @Column(length = 500)
    private String artiste;
    @Column(length = 500)
    private String album;
    @Column(length = 500)
    private int annee;
    @Column(length = 500)
    private String genres;

    // Changer pour Embedded
    // @OneToOne(mappedBy = "meta")
    // private Produit produit;

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

