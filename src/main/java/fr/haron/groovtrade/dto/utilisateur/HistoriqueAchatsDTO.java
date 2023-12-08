package fr.haron.groovtrade.dto.utilisateur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import fr.haron.groovtrade.dto.DTOInterface;
import fr.haron.groovtrade.entities.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class HistoriqueAchatsDTO implements DTOInterface {
    private List<Historique> historiques;


}


