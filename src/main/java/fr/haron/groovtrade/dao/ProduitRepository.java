package fr.haron.groovtrade.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.haron.groovtrade.entities.Produit;


public interface ProduitRepository extends JpaRepository<Produit, Long> {

    List<Produit> findByDescriptionLike(String string);

    //Pas besoin d'implémenter manuellement deleteByID, elle est déjà fournie par JpaRepository
    // Pas besoin d'implémenter manuellement save(), elle est déjà fournie par JpaRepository
}
