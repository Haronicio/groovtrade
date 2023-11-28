package application.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.spring.model.Utilisateur;
import java.util.List;


public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
    public Utilisateur findByUsername(String username);
    //public List<Utilisateur> findByUserid(Long userid);
    public Utilisateur findByUserid(Long userid);
}
