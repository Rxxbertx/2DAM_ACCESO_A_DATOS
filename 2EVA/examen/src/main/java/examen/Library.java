package examen;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private static EntityManagerFactory entityManagerFactory;
    public static String url;
    public static String driver;
    public static final String user = "admin";
    public static final String password = "dam2t";


    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("examen");
        url = entityManagerFactory.getProperties().get("jakarta.persistence.jdbc.url").toString();
        driver = entityManagerFactory.getProperties().get("jakarta.persistence.jdbc.driver").toString();
    }

    /**
     * Añade un autor a la base de datos
     */
    public static void addAuthor(String name, String surname) {


        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Author author = new Author(name, surname);
        entityManager.persist(author);
        entityManager.getTransaction().commit();
        entityManager.close();


    }

    /**
     * Incrementa el número de páginas de un libro especificado por el ISBN
     */
    public static void increaseBookPages(String isbn, int numPages) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Book book = entityManager.find(Book.class, Isbn.Parse(isbn));
        book.setPages(book.getPages() + numPages);
        entityManager.getTransaction().commit();
        entityManager.close();


    }

    /**
     * Obtiene el libro con el ISBN dado
     */
    public static Book getBook(String isbn) {


        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        return entityManager.find(Book.class, Isbn.Parse(isbn));
    }

    /**
     * Añade un autor y sus libros a la base de datos.
     * Los libros son persistidos en casacada el persistir el autor
     */
    public static void addAuthorCollection(String name, String surname, List<Book> bookList) {


        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Author author = new Author(name, surname);
        
        for (Book book : bookList) {
            
            book.setAuthor(author);
            
        }
        
        
        author.setBooks(bookList);
        entityManager.persist(author);
        entityManager.getTransaction().commit();
        entityManager.close();
     

    }

    /**
     * Devuelve la lista de los títulos de los libros de un autor
     * que sean más largos que un determinado número de páginas
     * ordenado por el número de páginas
     */
    public static List<String> getAuthorLongBookTitles(String name, String surname, int numPages) {

        
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List resultList = entityManager.createQuery("select b.title from Book b , Author a where b.author.id = a.id and a.name = :name and a.surname = :surname and b.pages > :pages order by b.pages")
                .setParameter("name", name).setParameter("surname", surname).setParameter("pages", numPages).getResultList();
        return resultList;



    }

    public static void close() {
        entityManagerFactory.close();
    }
}
