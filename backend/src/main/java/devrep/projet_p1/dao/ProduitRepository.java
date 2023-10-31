package devrep.projet_p1.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import devrep.projet_p1.entities.Produit;

public interface ProduitRepository extends JpaRepository<Produit,Long>{
    
}
