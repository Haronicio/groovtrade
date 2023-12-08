package application.spring.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class RefererValidator {


    private static final List<String> trustedOrigins = Arrays.asList(
        //  ici vont les hôtes autorisé
    );

    public static boolean isRefererTrusted(HttpServletRequest request) {
        String refererHeader = request.getHeader("Referer");

        return true;

        // TODO : désactiver temporairement tant qu'il n'y as pas besoin
        
        /*
        if (refererHeader != null && !refererHeader.isEmpty()) {
            try {
                // Création d'un objet URL pour analyser le referer
                java.net.URL url = new java.net.URL(refererHeader);

                // Vérification si l'hôte du referer est dans la liste blanche
                return trustedOrigins.contains(url.getProtocol() + "://" + url.getHost());
            } catch (Exception e) {
                // Gérer l'exception si l'URL n'est pas valide
                return false;
            }
        }

        return false;
        */
    }
}
