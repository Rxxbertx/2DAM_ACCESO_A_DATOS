package javamysqlTEST;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


//realizar un procedimiento en mysql qeu reciba un numero de dept y devuleva el numero de empleados y el salario medio dd dicho dept, realizar un programa java para usar dicho procedimiento

//crear una plantilla para obetner por cada dept ademas de sus datos el numero de empleados la media del sueldo y la suma de sus salarios

public class Ejercicio2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		 * utilizar la interfaz prepared statement para visualiar el apellido el salario
		 * y el oficio de lo smepleados de un único departamento visulaizar también el
		 * nombre del depratmenteo el salario medio de ese departamento y el numero de
		 * empleados de ese departamento. Si el dept no existe mostrar un mensaje
		 * indicándolo .
		 */

		// Colores ANSI para texto en la consola
		String ANSI_RESET = "\u001B[0m";
		String ANSI_RED = "\u001B[31m";
		String ANSI_GREEN = "\u001B[32m";
		String ANSI_YELLOW = "\u001B[33m";
		String ANSI_BLUE = "\u001B[34m";
		String ANSI_PURPLE = "\u001B[35m";
		
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {

			System.out.println("ERROR INCREIBLE");
			return;
		}

		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/accesodata", "admin", "dam2t");
		} catch (SQLException e) {

			e.printStackTrace();
			return;
		}

		Scanner sc = new Scanner(System.in);

		int departamento;

		while (true) {
			System.out.println("Inserta el numero del departamento");

			try {
				departamento = Integer.parseInt(sc.nextLine());

			} catch (Exception e) {

				System.out.println("INTRODUCE UN NUMERO");
				continue;
			}

			if (comprobarDepartamento(connection, departamento))
				break;
		}

		try {
			PreparedStatement statement = connection.prepareStatement(

					"SELECT \r\n" + "    e.apellido, \r\n" + "    e.salario, \r\n" + "    e.oficio, \r\n"
							+ "    d1.dnombre as nombre_departamento_1, \r\n"
							+ "    AVG(e2.salario) as salario_medio_departamento_1, \r\n"
							+ "    COUNT(*) as num_empleados_departamento_1\r\n" + "FROM \r\n" + "    empleados e\r\n"
							+ "LEFT JOIN \r\n" + "    departamentos d1 ON e.dept_no = d1.dept_no\r\n" + "LEFT JOIN\r\n"
							+ "    empleados e2 ON e.dept_no = e2.dept_no\r\n" + "LEFT JOIN\r\n"
							+ "    departamentos d2 ON e2.dept_no = d2.dept_no\r\n" + "WHERE \r\n"
							+ "    e.dept_no = ?\r\n" + "GROUP BY \r\n"
							+ "    e.apellido, e.salario, e.oficio, d1.dnombre;\r\n" + ""

			);

			statement.setInt(1, departamento);

			ResultSet rs = statement.executeQuery();
			int i = 0;

			while (rs.next()) {
				i++;
				StringBuilder sb = new StringBuilder();

				

				// Construir la cadena con colores diferentes

				sb.append(ANSI_RED + rs.getString(1) + " " + ANSI_RESET);
				sb.append(ANSI_GREEN + rs.getString(2) + " " + ANSI_RESET);
				sb.append(ANSI_YELLOW + rs.getString(3) + " " + ANSI_RESET);
				sb.append(ANSI_BLUE + rs.getString(4) + " " + ANSI_RESET);
				sb.append(ANSI_PURPLE + rs.getString(5) + " " + ANSI_RESET);
				sb.append(rs.getString(6)); // El último campo sin color

				System.out.println(sb.toString());

			}
			if (i == 0)
				System.out.println("NO HAY INFORMACION SOBRE ESE NUMERO DE DEPT");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			sc.close();
		}

	}

	private static boolean comprobarDepartamento(Connection connection, int departamento) {

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT dept_no FROM departamentos WHERE dept_no = ?");

			statement.setInt(1, departamento);

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
