package application.spring.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Embeddable
public class ProduitImg implements Serializable{
    String path;
}
