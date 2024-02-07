import java.util.List;

import jakarta.persistence.*;

@Entity
public class Team {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	
	@OneToMany(mappedBy = "team")
	List<SuperHero> heros;
	
	String name;
	
	
	public Team() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Team(String name) {
		
		this.name = name;
		
	}


	public List<SuperHero> getHeros() {
		return heros;
	}


	public void setHeros(List<SuperHero> heros) {
		this.heros = heros;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
