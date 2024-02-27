package examen;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "apellidos")
    private String surname;


    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST)
    private List<Book> books;

    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Author() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBooks(List<Book> bookList) {

        this.books = bookList;

    }
    
    public List<Book> getBooks(){
        return books;
    }
}
