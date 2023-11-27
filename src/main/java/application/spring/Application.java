package application.spring;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import application.spring.model.Historique;
import application.spring.model.Utilisateur;
import application.spring.repository.HistoriqueRepository;
import application.spring.repository.ProduitRepository;
import application.spring.repository.UtilisateurRepository;


@SpringBootApplication
public class Application {

	

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
