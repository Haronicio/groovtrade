package application.spring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Historique implements Serializable{
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,name = "hitoriqueid")
    private Long hitoriqueid;
    private boolean archived;
    @OneToMany
	@JoinColumn(name = "hitoriqueid")
    private List<Produit> produits = new ArrayList<>();
}
