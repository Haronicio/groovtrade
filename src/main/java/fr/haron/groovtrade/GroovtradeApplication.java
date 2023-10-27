package fr.haron.groovtrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import fr.haron.groovtrade.dao.ProduitRepository;
import fr.haron.groovtrade.entities.Produit;
import fr.haron.groovtrade.entities.ProduitImg;
import fr.haron.groovtrade.entities.ProduitMeta;
import fr.haron.groovtrade.entities.ProduitSong;
import fr.haron.groovtrade.entities.ProduitType;


@SpringBootApplication
public class GroovtradeApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(GroovtradeApplication.class, args);
	}

}
