package javamysqlTEST;

import java.sql.*;

public class Main {
	public static void main(String[] args) {
		try {
			// Cargar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecer la conexion con la BD
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/accesodata", "admin", "dam2t");

			// Preparar la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT * FROM departamentos";
			ResultSet resul = sentencia.executeQuery(sql);

			// Recorrer el resultado para visualizar cada fila
			// Se hace un bucle mientras haya registros y se van visualizando
			System.out.println("_______________________");
			System.out.println("|NUMERO DEPARTAMENTO|"+""+"");
			while (resul.next()) {
				System.out.printf("%d, %s, %s %n", resul.getInt(1), resul.getString(2), resul.getString(3));
			}

			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexi�n

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // Fin de main
} // Fin de la clase
