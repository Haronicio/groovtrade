package fr.haron.groovtrade.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import fr.haron.groovtrade.dao.UtilisateurRepository;
import fr.haron.groovtrade.entities.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GlobalDTO {

    private DTOInterface dto;

    private String username;
    private String userPP;
    private Long userid;

    //gérer dans le controller de l'api rest


    // public GlobalDTO(Authentication authentication,DTOInterface dto)
    // {
    //     this.dto = dto;
    //     String username;

    //     if (authentication == null) {
    //         username = "anonymousUser";
    //     }
    //     else
    //         username = authentication.getName();   
    //     this.username = username;
        
    //     String userPP = null;
    //     Long userid = null;
    //     if (authentication != null && authentication.isAuthenticated() && username != "anonymousUser") {
    //         // System.out.println("user " + username);
    //         //Pose problème si "anonymousUser"
    //         Utilisateur utilisateurCourrant = utilisateurRepository.findByUsername(username);
    //          userid = utilisateurCourrant.getUserid();
    //          userPP = utilisateurCourrant.getImgPath();
    //         }//si anonymous user (buuug)
    //         this.userPP = userPP;
    //         this.userid = userid;
    // }
}
