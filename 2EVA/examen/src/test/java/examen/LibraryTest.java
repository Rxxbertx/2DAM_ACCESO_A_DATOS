package examen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class LibraryTest {
	
	 private static HikariDataSource cp;
	    
	    @BeforeAll
	    static void setup() {
	        var config = new HikariConfig();
			config.setJdbcUrl(Library.url);
			config.setUsername(Library.user);
			config.setPassword(Library.password);
			config.setDriverClassName(Library.driver);
			cp = new HikariDataSource(config);	
	    }

	    @AfterAll
	    static void teardown() {
	        Library.close();
	        cp.close();
	    }

	    @Test
	    void testAddAuthor() {
	        // Añade un nuevo autor
	        Library.addAuthor("Patrick", "Rothfuss");

	        // Comprueba si el autor ha sido correctamente añadido
	        try (Connection conn = cp.getConnection()) {
	            String sql = "SELECT * FROM autores WHERE nombre = ? AND apellidos = ?";
	            var preparedStatement = conn.prepareStatement(sql);
	            preparedStatement.setString(1, "Patrick");
	            preparedStatement.setString(2, "Rothfuss");

	            String deleteSql = "DELETE FROM autores WHERE id = ?";
	            var deletePreparedStatement = conn.prepareStatement(deleteSql);
	            try (var resultSet = preparedStatement.executeQuery()) {
	                assertTrue(resultSet.next(), "No existen un autor con el nombre y apellido dados");
	                assertEquals("Patrick", resultSet.getString("nombre"), "El nombre del autor no es el esperado");
	                assertEquals("Rothfuss", resultSet.getString("apellidos"), "El apellido del autor no es el esperado");

	                // borra el autor
	                deletePreparedStatement.setInt(1, resultSet.getInt("id"));
	                assertFalse(resultSet.next(), "Existe más de un autor con el mismo nombre y apellidos");
	            }
	            deletePreparedStatement.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }	   

	    @Test
	    void testIncreaseBookPages() {
	        // Aumenta en 100 el número de páginas a un libro dado por el ISBN
	        Library.increaseBookPages("0-7653-1178", 100);

	        // Comprueba que el numero de paginas se ha incrementado en 100
	        try (Connection conn = cp.getConnection()) {
	            String sql = "SELECT * FROM libros WHERE registration_group = ? AND registrant = ? AND publication=?";
	            var preparedStatement = conn.prepareStatement(sql);
	            preparedStatement.setInt(1, 0);
				preparedStatement.setInt(2, 7653);
				preparedStatement.setInt(3, 1178);

	            try (var resultSet = preparedStatement.executeQuery()) {
	                assertTrue(resultSet.next());
	                assertEquals(637, resultSet.getInt("paginas"));
	                assertFalse(resultSet.next());
	            }

	            // Reestablece el número de páginas a 537
	            String updateSql = "UPDATE libros SET paginas = 537 WHERE registration_group = ? AND registrant = ? AND publication=?";
	            var updatePreparedStatement = conn.prepareStatement(updateSql);
	            updatePreparedStatement.setInt(1, 0);
	            updatePreparedStatement.setInt(2, 7653);
	            updatePreparedStatement.setInt(3, 1178);
	            updatePreparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }	    

	    @Test
	    void testBookAuthor() {
	        // Get a book and check if the author is correct
	        var theFinalEmpire = Library.getBook("0-7653-1178");
	        assertNotNull(theFinalEmpire, "El libro no se ha encontrado");
			var brandonSanderson = theFinalEmpire.getAuthor();
			assertNotNull(brandonSanderson, "El libro no tiene un autor asociado");
			assertEquals("Brandon", brandonSanderson.getName());
			assertEquals("Sanderson", brandonSanderson.getSurname());
	    }

	    @Test
	    void testAuthorCollection() {
		// Añade un autor y sus libros
	        Library.addAuthorCollection("Patrick", "Rothfuss",
	                Arrays.asList(
	                        new Book("0-7654-0407", "The Name of the Wind", 662),
	                        new Book("0-7654-1180", "The Fear of the Wise", 1000)));

	        // Comprueba si se ha creado correctamente y su coleccion de libros
	        try (Connection conn = cp.getConnection()) {
	            String authorSql = "SELECT * FROM autores WHERE nombre = ? AND apellidos = ?";
	            var authorPreparedStatement = conn.prepareStatement(authorSql);
	            authorPreparedStatement.setString(1, "Patrick");
	            authorPreparedStatement.setString(2, "Rothfuss");

	            try (var resultSet = authorPreparedStatement.executeQuery()) {
	                assertTrue(resultSet.next(), "No se encontro el autor buscado");
	                int authorId = resultSet.getInt("id");

	                String bookSql = "SELECT * FROM libros WHERE autor = ?";
	                var booksPreparedStatement = conn.prepareStatement(bookSql);
	                booksPreparedStatement.setInt(1, authorId);

	                try (var booksResultSet = booksPreparedStatement.executeQuery()) {
	                    assertTrue(booksResultSet.next(), "No se han enconctrado libros del autor seleccionado");
	                    assertEquals("The Name of the Wind", booksResultSet.getString("titulo"));
	                    assertTrue(booksResultSet.next());
	                    assertEquals("The Fear of the Wise", booksResultSet.getString("titulo"));
	                    assertFalse(booksResultSet.next());
	                }

	                // Borrar los libros
	                String deleteBooksSql = "DELETE FROM libros WHERE autor = ?";
	                var deleteBooksPreparedStatement = conn.prepareStatement(deleteBooksSql);
	                deleteBooksPreparedStatement.setInt(1, authorId);
	                deleteBooksPreparedStatement.executeUpdate();

				// Eliminar el autor
				String deleteSql = "DELETE FROM autores WHERE id = ?";
				var deletePreparedStatement = conn.prepareStatement(deleteSql);
				deletePreparedStatement.setInt(1, authorId);
				deletePreparedStatement.executeUpdate();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    @Test
	    void testQueryAuthorLongBooks() {
	        // Obtiene los libros que tengan más de 1000 páginas
	        var longBooks = Library.getAuthorLongBookTitles("George", "Martin", 1000);

	        // Comprueba que los libros encontrados son los esperados
			assertNotNull(longBooks, "no se devolvio ningún resultado");
	        assertEquals(3, longBooks.size());
	        assertEquals("A Feast for Crows", longBooks.get(0));
	        assertEquals("A Dance with Dragons", longBooks.get(1));		
			assertEquals("A Storm of Swords", longBooks.get(2));
	    }
}
