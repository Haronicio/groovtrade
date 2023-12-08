package application.spring.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
public class Utilisateur implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, name = "userid")
	private Long userid;

	@Column(unique = true, nullable = false)
	private String username;

	private String password;
	private String role;
	private String email;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")
	private List<Historique> historiques = new ArrayList<>();

	@Embedded
	private Panier panier;

	public Utilisateur(String username, String password, String role, String email,
			List<Historique> historiques, Panier panier) {
		this.username = username;
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		this.password = b.encode(password);
		this.role = role;
		this.email = email;
		this.historiques = historiques;
		this.panier = panier;
	}

	// Pour le controller utilisateur
	public List<PanierItem> getVentes(List<Produit> produits) {
		List<PanierItem> res = new ArrayList<>();

		for (Produit e : produits) {
			if (e.getUtilisateurId() == userid) {
				res.add(new PanierItem(e, e.getNbProduit()));
			}

		}

		return res;
	}
}