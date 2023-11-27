package application.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.spring.model.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
    public Utilisateur findByUsername(String username);
}
