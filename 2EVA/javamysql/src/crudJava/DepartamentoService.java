package crudJava;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class DepartamentoService {

	public static boolean Create(Departamento obj) {

		Conexion con = Conexion.ObtenerConexion();
		Connection conx = Conexion.OpenConnection(con);

		if (!Find(obj))
			return false;

		// Prepara la sentencia SQL
		String sql = "INSERT INTO departamentos VALUES (?, ?, ?)";
		try {

			// Prepara la sentencia SQL

			try (PreparedStatement preparedStatement = conx.prepareStatement(sql)) {
				// Establece los valores de los parámetros

				preparedStatement.setInt(1, obj.dept_no);
				preparedStatement.setString(2, obj.dnombre);
				preparedStatement.setString(3, obj.loc);

				// Ejecuta la sentencia
				if (preparedStatement.executeUpdate() == 1) {
					System.out.println("Departamento insertado correctamente.");

					return true;

				} else {
					return false;
				}

			}
		} catch (SQLException e) {
			System.out.println("Error al insertar el Departamento: " + e.getMessage());
		} finally {
			// Cierra la conexión

			Conexion.CloseConnection(con);

		}

		return true;

	};

	public static Departamento Read(Departamento obj) {

		Conexion con = Conexion.ObtenerConexion();
		Connection conx = Conexion.OpenConnection(con);

		// Prepara la sentencia SQL
		String sql = "SELECT * departamentos where dept_no = ?";
		try {

			// Prepara la sentencia SQL

			try (PreparedStatement preparedStatement = conx.prepareStatement(sql)) {
				// Establece los valores de los parámetros

				preparedStatement.setInt(1, obj.dept_no);

				// Ejecuta la sentencia
				ResultSet rs = preparedStatement.executeQuery();

				Departamento dept = new Departamento();

				while (rs.next()) {

					dept.dept_no = rs.getInt(1);
					dept.dnombre = rs.getString(2);
					dept.loc = rs.getString(3);

				}

				return dept;

			}
		} catch (SQLException e) {
			System.out.println("Error al buscar el Departamento: " + e.getMessage());
		} finally {
			// Cierra la conexión

			Conexion.CloseConnection(con);

		}

		return null;

	};

	public static boolean Delete(Departamento obj) {

		Conexion con = Conexion.ObtenerConexion();
		Connection conx = Conexion.OpenConnection(con);

		if (!Find(obj)) {
			return false;
		}

		// Prepara la sentencia SQL
		String sql = "DELETE FROM departamentos where dept_no = ?";
		try {

			// Prepara la sentencia SQL

			try (PreparedStatement preparedStatement = conx.prepareStatement(sql)) {
				// Establece los valores de los parámetros

				preparedStatement.setInt(1, obj.dept_no);

				// Ejecuta la sentencia
				int rs = preparedStatement.executeUpdate();

				if (rs == 1) {

					return true;

				}

				return false;

			}
		} catch (SQLException e) {
			System.out.println("Error al buscar el Departamento: " + e.getMessage());
		} finally {
			// Cierra la conexión

			Conexion.CloseConnection(con);

		}

		return false;

	};

	public static boolean Update(Departamento obj) {

		Conexion con = Conexion.ObtenerConexion();
		Connection conx = Conexion.OpenConnection(con);

		if (!Find(obj)) {
			return false;
		}

		// Prepara la sentencia SQL
		String sql = "UPDATE departamentos SET dept_no = ?, dnombre = ?,  loc = ? where dept_no = ?";
		try {

			// Prepara la sentencia SQL

			try (PreparedStatement preparedStatement = conx.prepareStatement(sql)) {
				// Establece los valores de los parámetros

				preparedStatement.setInt(1, obj.dept_no);
				preparedStatement.setString(2, obj.dnombre);
				preparedStatement.setString(3, obj.loc);
				preparedStatement.setInt(4, obj.dept_no);


				// Ejecuta la sentencia
				int rs = preparedStatement.executeUpdate();

				if (rs == 1) {

					return true;

				}

				return false;

			}
		} catch (SQLException e) {
			System.out.println("Error al buscar el Departamento: " + e.getMessage());
		} finally {
			// Cierra la conexión

			Conexion.CloseConnection(con);

		}

		return false;

	};

	public static boolean Find(Departamento obj) {

		Conexion con = Conexion.ObtenerConexion();
		Connection conx = Conexion.OpenConnection(con);

		// Prepara la sentencia SQL
		String sql = "SELECT COUNT(*) departamentos where dept_no = ?";
		try {

			// Prepara la sentencia SQL

			try (PreparedStatement preparedStatement = conx.prepareStatement(sql)) {
				// Establece los valores de los parámetros

				preparedStatement.setInt(1, obj.dept_no);

				// Ejecuta la sentencia
				if (preparedStatement.executeQuery().last()) {

					return true;

				} else {
					return false;
				}

			}
		} catch (SQLException e) {
			System.out.println("Error al buscar el Departamento: " + e.getMessage());
		} finally {
			// Cierra la conexión

			Conexion.CloseConnection(con);

		}

		return false;

	};

}
