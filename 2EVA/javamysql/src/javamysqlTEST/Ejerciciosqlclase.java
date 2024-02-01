package javamysqlTEST;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class Ejerciciosqlclase {

	public static void main(String[] args) {

		/*
		 * Crear un programa java que inserte un empleado en la tabla empleados, para
		 * ello recibirá desde la línea de argumentos los valores a insertar son :
		 * emp_no, apellido, oficio, director, salario, comision, dept_no Antes de
		 * insertar comprobar que el emp_no no exista, que el dept_no exista, que el
		 * salario sea mayor que 0, que el director exista y no sea null, apellido y
		 * oficio no pueden ser nulos. Visualizar un mensaje si se ha insertado
		 * correctamente o no y con su condicion.
		 */

		if (args.length != 7) {
			System.out.println("Error: Debes proporcionar 7 argumentos.");
			return;
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {

			System.out.println("ERROR INCREIBLE");
			return;

		}

		int emp_no = Integer.parseInt(args[0]);
		String apellido = args[1];
		String oficio = args[2];
		int director = Integer.parseInt(args[3]);
		double salario = Double.parseDouble(args[4]);
		double comision = Double.parseDouble(args[5]);
		int dept_no = Integer.parseInt(args[6]);

		if (emp_Existente(emp_no)) {
			System.out.println("Error: El empleado con emp_no " + emp_no + " ya existe.");
			return;
		}

		if (!deptExistente(dept_no)) {
			System.out.println("Error: El departamento con dept_no " + dept_no + " no existe.");
			return;
		}

		if (salario <= 0) {
			System.out.println("Error: El salario debe ser mayor que 0.");
			return;
		}

		if (!directorExistente(director)) {
			System.out.println("Error: El director con emp_no " + director + " no existe.");
			return;
		}

		if (apellido == null || oficio == null) {
			System.out.println("Error: El apellido y el oficio no pueden ser nulos.");
			return;
		}

		Connection connection = null;
		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/accesodata", "admin", "dam2t");

			// Prepara la sentencia SQL
			String sql = "INSERT INTO empleados VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				// Establece los valores de los parámetros

				Calendar calendar = Calendar.getInstance();
				calendar.getTime();

				preparedStatement.setInt(1, emp_no);
				preparedStatement.setString(2, apellido);
				preparedStatement.setInt(2, new Integer(null));
				preparedStatement.setString(3, oficio);
				preparedStatement.setInt(4, director);
				preparedStatement.setDate(5, (Date) calendar.getTime());
				preparedStatement.setDouble(6, salario);
				preparedStatement.setDouble(7, comision);
				preparedStatement.setInt(8, dept_no);

				// Ejecuta la sentencia
				preparedStatement.executeUpdate();

				System.out.println("Empleado insertado correctamente.");
			}
		} catch (SQLException e) {
			System.out.println("Error al insertar el empleado: " + e.getMessage());
		} finally {
			// Cierra la conexión
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean emp_Existente(int emp_no) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/accesodata", "admin", "dam2t");

			String sql = "SELECT emp_no FROM empleados WHERE emp_no = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setInt(1, emp_no);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					return resultSet.next(); // Devuelve true si existe, false si no existe
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			cerrarConexion(connection);
		}
	}

	private static boolean deptExistente(int dept_no) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/accesodata", "admin", "dam2t");

			String sql = "SELECT dept_no FROM departamentos WHERE dept_no = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setInt(1, dept_no);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					return resultSet.next(); // Devuelve true si existe, false si no existe
				}
			
			
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // Manejo básico de errores, ajusta según tus necesidades
		} finally {
			cerrarConexion(connection);
		}
	}

	private static boolean directorExistente(int director) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/accesodata", "admin", "dam2t");

			String sql = "SELECT emp_no FROM empleados WHERE emp_no = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setInt(1, director);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					return resultSet.next(); // Devuelve true si existe, false si no existe
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // Manejo básico de errores, ajusta según tus necesidades
		} finally {
			cerrarConexion(connection);
		}
	}

	private static void cerrarConexion(Connection connection) {

	}
}
