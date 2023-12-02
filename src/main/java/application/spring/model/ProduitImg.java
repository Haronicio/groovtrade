package application.spring.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Entity
// @Table(name = "produit_imgs")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProduitImg implements Serializable
{
    // @Id
    // @GeneratedValue
    // private Long id;

    private String path;

    // @ManyToOne
    // private Produit produit;

    @Override
    public String toString() {
        return "{" +
            // " id='" + getId() + "'" +
            ", path='" + getPath() + "'" +
            "}";
    }


}

