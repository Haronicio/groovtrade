package application.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.spring.model.Panier;

public interface PanierRepository extends JpaRepository<Panier,Long>{
    
}
