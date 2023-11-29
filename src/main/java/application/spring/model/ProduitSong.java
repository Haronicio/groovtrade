package application.spring.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "produit_songs")
public class ProduitSong implements Serializable
{
    @Id
    @GeneratedValue
    private Long id;

    private String path;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

}

