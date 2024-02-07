import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class SuperPower {
    
    
    @Id
            @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    int id;
    
    String name;
    
    @ManyToMany(mappedBy = "powers")
    java.util.List<SuperHero> superHeros;
    
    
    public SuperPower() {
        // TODO Auto-generated constructor stub
    }
    
    public SuperPower(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
}
