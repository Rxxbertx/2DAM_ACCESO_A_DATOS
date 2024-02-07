import jakarta.persistence.*;

@Entity
public class Person {


    String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToOne(mappedBy = "personId")
    SuperHero superHero;

	

    public Person() {
        // TODO Auto-generated constructor stub
    }


    public Person(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public SuperHero getSuperHero() {
        return superHero;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


}
