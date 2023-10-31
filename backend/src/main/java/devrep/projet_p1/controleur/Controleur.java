package devrep.projet_p1.controleur;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.status.Status;
import devrep.projet_p1.dao.HistoriqueRepository;
import devrep.projet_p1.dao.ProduitRepository;
import devrep.projet_p1.dao.UtilisateurRepository;
import devrep.projet_p1.entities.Historique;
import devrep.projet_p1.entities.Produit;
import devrep.projet_p1.entities.Utilisateur;

@RestController
@RequestMapping("api/commander")
public class Controleur {
    @Autowired
    ProduitRepository produitRepository;
    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    HistoriqueRepository historiqueRepository;

    @GetMapping("/produits")
    public ResponseEntity<List<Produit>> getProduit(){
        List<Produit> res = produitRepository.findAll( );
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
