package devrep.projet_p1.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Historique implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historiqueId;
    @ManyToOne
    @JoinColumn(name="utilisateurId", nullable=false)
    private Utilisateur utilisateur;
    private String produits;
    public Historique(Utilisateur utilisateur,String produits){
        this.utilisateur = utilisateur;
        this.produits = produits;
    }
}
