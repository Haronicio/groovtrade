package fr.haron.groovtrade.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.haron.groovtrade.entities.Historique;

public interface HistoriqueRepository extends JpaRepository<Historique, Long> {

}
