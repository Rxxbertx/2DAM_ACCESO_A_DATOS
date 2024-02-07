import jakarta.persistence.*;

@Entity
public class SuperHero {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	String name;

	@JoinColumn(name = "person")
	@OneToOne(cascade = jakarta.persistence.CascadeType.ALL)
	Person personId;

	@JoinColumn(name="team")
	@ManyToOne(cascade = jakarta.persistence.CascadeType.ALL)
	Team team;
	
	
	
	@JoinTable(name = "superhero_superpower", joinColumns = @JoinColumn(name = "superhero_id"), inverseJoinColumns = @JoinColumn(name = "superpower_id"))
	@ManyToMany(cascade = jakarta.persistence.CascadeType.ALL)
	java.util.List<SuperPower> powers;
	
	
	
	
	public SuperHero() {
		// TODO Auto-generated constructor stub
	}

	public SuperHero(String name, Person persona, Team team) {

		this.personId = persona;
		this.team = team;
		this.name = name;
	}
	
	public void addPower(SuperPower power) {
		this.powers.add(power);
	}

}
