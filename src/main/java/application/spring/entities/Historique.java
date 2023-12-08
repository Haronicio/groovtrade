package application.spring.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@ToString
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "historique")
public class Historique implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "historiqueid")
    private Long historiqueid;
    private boolean archived;
    private String etat;//process paid cancel
    private Date date;
    private String livraison;
    @ElementCollection
    @CollectionTable(name = "panier_produits", joinColumns = @JoinColumn(name = "utilisateur_id"))
    private List<PanierItem> produits = new ArrayList<>();

    public Historique(boolean archived, Date date, String livraison, List<PanierItem> produits) {
        this.archived = archived;
        this.date = date;
        this.produits = produits;
        this.livraison = livraison;
    }


    public void add(PanierItem e)
    {
        this.produits.add(e);
    }

    public int getQuantiteTotal() {
        int res = 0;

        for (PanierItem panierItem : produits) {
            res += panierItem.getQuantite();
        }
        return res;
    }

    public int getTotal() {
        int res = 0;

        for (PanierItem panierItem : produits) {
            res += panierItem.getQuantite() * panierItem.getProduit().getPrix();
        }
        return res;
    }

    public void setEtat(String e)
    {
        this.etat = e;
    }

    public void setArchived(boolean a)
    {
        this.archived = a;
    }
}