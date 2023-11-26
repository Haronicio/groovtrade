package application.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@ToString
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DBUser implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
	private Integer userid;
	private String username;
	private String password;
	private String role;


}