package application.spring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

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
public class Utilisateur implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,name = "userid")
	private Long userid;
	private String username;
	private String password;
	private String role;
	private String email;
	@OneToMany(
		cascade = CascadeType.ALL,
		orphanRemoval = true,
		fetch = FetchType.EAGER
	)
	@JoinColumn(name = "userid")
	private List<Historique> historiques;
	
}