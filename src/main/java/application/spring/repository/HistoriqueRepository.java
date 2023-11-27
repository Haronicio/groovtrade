package application.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.spring.model.Historique;

public interface HistoriqueRepository extends JpaRepository<Historique,Long>{
    
}
