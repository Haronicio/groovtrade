package application.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.spring.entities.Historique;


public interface HistoriqueRepository extends JpaRepository<Historique, Long> {

}
