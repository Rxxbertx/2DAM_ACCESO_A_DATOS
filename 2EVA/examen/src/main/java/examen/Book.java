package examen;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "libros")
public class Book {

    @EmbeddedId
    private Isbn isbn;

    @Column(name = "titulo")
    private String title;

    @Column(name = "paginas")
    private int pages;


    @ManyToOne()
    @JoinColumn(name = "autor")
    private Author author;

    public Book(String isbn, String title, int pages) {
        this.isbn = Isbn.Parse(isbn);
        this.title = title;
        this.pages = pages;
    }

    public Book() {

    }

    public Isbn getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {

        this.pages = pages;

    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}
