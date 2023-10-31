package devrep.projet_p1.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "utilisateur")
public class Utilisateur implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long utilisateurId;
    @Column(unique = true)
    private String nom;
    private String e_mail;
    private String mdp;
    @OneToMany(targetEntity=Historique.class, mappedBy = "utilisateur",cascade = CascadeType.ALL)
    private List<Historique> historique = new ArrayList<>();
    public Utilisateur(String nom,String mail,String mdp){
        this.nom = nom;
        this.e_mail = mail;
        this.mdp = mdp;
    }
    public void ajouteHistorique(Historique historique){
        this.historique.add(historique);
    }
}