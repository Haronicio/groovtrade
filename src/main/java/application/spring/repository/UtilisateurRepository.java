package application.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.spring.entities.Utilisateur;


public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
    public Utilisateur findByUsername(String username);
    public Utilisateur findByUserid(Long userid);
}
