package application.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.spring.model.Produit;

public interface ProduitRepository extends JpaRepository<Produit,Long>{
    
}
