package application.spring.entities;

import java.io.Serializable;

import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PanierItem implements Serializable{
    // @Id
    // @GeneratedValue
    // private Long id;

    @ManyToOne
    private Produit produit;

    private int quantite;
}
