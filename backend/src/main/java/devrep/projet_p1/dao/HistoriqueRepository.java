package devrep.projet_p1.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import devrep.projet_p1.entities.Historique;

public interface HistoriqueRepository extends JpaRepository<Historique,Long>{
    
}
