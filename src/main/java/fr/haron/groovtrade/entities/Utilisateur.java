package fr.haron.groovtrade.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "utilisateur")
public class Utilisateur implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,name = "userid")
	private Long userid;
	@Column(unique = true)
	private String username;
	private String password;
	private String role;
	private String email;
	private String imgPath;
	
	@OneToMany(
		cascade = CascadeType.ALL,
		orphanRemoval = true,
		fetch = FetchType.EAGER
	)
	@JsonBackReference // éviter les boucles de dépendances lors de la sérialisation
	@JoinColumn(name = "utilisateur_id")
    private List<Historique> historiques = new ArrayList<>();


	// @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Historique> historiques = new ArrayList<>();

	// @OneToOne(cascade = CascadeType.ALL)
	// @JoinColumn(name = "panierid")
	@Embedded
	private Panier panier;

	public String getUsername()
	{
		return this.username;
	}


	//Pour le controller utilisateur
	public List<PanierItem> getVentes(List<Produit> produits)
	{
		List<PanierItem> res = new ArrayList<>();

		for (Produit e : produits) {
			if (e.getUtilisateurId() == userid) {
				res.add(new PanierItem(e, e.getNbProduit()));
			}
			
		}

		return res;
	}

	
	
}