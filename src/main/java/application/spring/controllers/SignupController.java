package application.spring.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import application.spring.entities.Panier;
import application.spring.entities.Utilisateur;
import application.spring.repository.UtilisateurRepository;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/signup")
public class SignupController {

     @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping
    public String registerForm()
    {
        return "signup";
    }
    

    @PostMapping
    public String registerUser(@RequestParam("username") String username,
                                     @RequestParam("email") String email,
                                     @RequestParam("password") String password,
                                     @RequestParam("image") MultipartFile imageFile) {
        // Logique pour enregistrer l'utilisateur
        String imgPath = saveImage(imageFile,username);
        
        Utilisateur newUser = new Utilisateur();
        newUser.setUsername(username);
        newUser.setEmail(email);
        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        newUser.setPassword(b.encode(password));
        newUser.setImgPath(imgPath);
        newUser.setHistoriques(new ArrayList<>());
		newUser.setPanier(new Panier());

        newUser.setRole("USER");

        utilisateurRepository.save(newUser);

        return "redirect:/login";
    }

    private String saveImage(MultipartFile imageFile, String username) {
        if (!imageFile.isEmpty()) {
                // Définir le chemin où vous voulez stocker les images
                // Exemple: "static/images/"
                String uploadDir = "src/main/resources/static/images";

                // TODO :ajouter vérifications solide
                String filename = username.toString() + ".profile." + truncate(getFileNameWithoutExtension(imageFile.getOriginalFilename()),10) + "."
                        + getExtensionByStringHandling(imageFile.getOriginalFilename()).get();

                // Sauvegarder le fichier sur le disque dur
                try {
                    Path uploadPath = Paths.get(uploadDir);

                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    try (InputStream inputStream = imageFile.getInputStream()) {
                        Path filePath = uploadPath.resolve(filename);
                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

                        return filename;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // TODO : Gérer l'exception ici
                }
            }
        return "";
    }
    public static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
    public static String getFileNameWithoutExtension(String fileName) {
        if (fileName == null) {
            return null;
        }

        int indexOfLastDot = fileName.lastIndexOf('.');

        if (indexOfLastDot > 0) {
            return fileName.substring(0, indexOfLastDot);
        }

        return fileName;
    }

    /**
     * Tronque une chaîne de caractères jusqu'à un maximum de caractères.
     *
     * @param input La chaîne de caractères à tronquer.
     * @param maxLength La longueur maximale de la chaîne tronquée.
     * @return La chaîne tronquée.
     */
    public static String truncate(String input, int maxLength) {
        if (input == null) {
            return null;
        }
        if (input.length() <= maxLength) {
            return input;
        }
        return input.substring(0, maxLength);
    }

}
