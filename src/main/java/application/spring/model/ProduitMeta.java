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
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Changer pour Embedded
// @Entity
// @Table(name = "produit_meta")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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


    
}

