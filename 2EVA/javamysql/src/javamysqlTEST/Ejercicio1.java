package javamysqlTEST;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio1 {

	public static void main(String[] args) {

		// con esta bbdd 1- obtener apellidos oficio salario de todos los empleados del
		// departamento 10

		// 2- apellido salario nombre del dept del empleado que mas cobre de toda la
		// empresa

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {

			System.exit(1);
			e.printStackTrace();
		}

		// connect
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/accesodata", "admin", "dam2t");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// consultar empleados

		if (connection != null) {

			try (Statement sentence = connection.createStatement();) {

				System.out.println("EJERCICIO 1");

				try (ResultSet result = sentence
						.executeQuery("SELECT apellido, oficio, salario FROM empleados WHERE dept_no = 1");) {

					while (result.next()) {

						System.out.printf("%s  %s  %s %n", result.getString(1), result.getString(2), result.getFloat(3));

					}
				}

				System.out.println("EJERCICIO 2");

				try (ResultSet result = sentence.executeQuery("SELECT e.apellido, e.salario, d.dnombre\r\n"
						+ "FROM empleados e\r\n" + "JOIN departamentos d ON e.dept_no = d.dept_no\r\n"
						+ "WHERE e.salario = (SELECT MAX(salario) FROM empleados)\r\n"
						+ "GROUP BY e.apellido, e.salario, d.dnombre;");) {

					while (result.next()) {

						System.out.printf("%s  %s  %s %n", result.getString(1), result.getString(2),
								result.getString(3));

					}
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
