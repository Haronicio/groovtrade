package application.spring.model;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProduitMeta {
    private String nom;
    private String artiste;
    private String album;
    private int annee;
    private String genres;
}
