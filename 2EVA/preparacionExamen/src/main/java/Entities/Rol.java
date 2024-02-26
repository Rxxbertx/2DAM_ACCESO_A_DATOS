package Entities;

import jakarta.persistence.*;

import javax.naming.Name;
import java.util.List;

@Entity
@Table(name = "Rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "name")
    private String nombre;
    
    @OneToMany(mappedBy = "rol")
    private List<User> user;

    public Rol() {
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Rol(String nombre) {
        this.nombre = nombre;
    }
}
