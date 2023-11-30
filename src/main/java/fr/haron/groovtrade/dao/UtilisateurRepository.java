package fr.haron.groovtrade.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.haron.groovtrade.entities.*;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
    public Utilisateur findByUsername(String username);
    //public List<Utilisateur> findByUserid(Long userid);
    public Utilisateur findByUserid(Long userid);
}
