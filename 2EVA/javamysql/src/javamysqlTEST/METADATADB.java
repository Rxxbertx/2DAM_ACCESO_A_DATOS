package javamysqlTEST;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.DatabaseMetaData;

public class METADATADB {

	public static void main(String[] args) {
		// visualizar la info de todas las columnas de la tabla departamentos.

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/accesodata", "admin", "dam2t");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (connection != null) {

			DatabaseMetaData dmd = null;
			try {
				dmd = (DatabaseMetaData) connection.getMetaData();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (dmd != null) {

				try (ResultSet result = dmd.getColumns(null, null, "DEPARTAMENTOS", null)) {

					int i = 0;

					while (result.next()) {

						if (i == 0) {
							System.out.println(result.getString(3));
							i++;
						}

						System.out
								.println("COLUMNA: " + result.getString(4) + " TIPO DE DATOS: " + result.getString(6));

					}

				} catch (Exception e) {
					// TODO: handle exception
				}

				// visualizar informacion de las claves primarias y ajenas de la tabla
				// departamentos

				try (ResultSet result = dmd.getExportedKeys(null, null, "DEPARTAMENTOS")) {

					while (result.next()) {

						System.out.println("FOREIGN KEY DE " + result.getString(3) + " :::: " + result.getString(8));
						System.out.println("PRIMARY KEY DE " + result.getString(3) + " :::: " + result.getString(4));

					}

				} catch (Exception e) {
					// TODO: handle exception
				}

				try (ResultSet result = dmd.getExportedKeys(null, null, "empleados")) {

					while (result.next()) {

						System.out.println("FOREIGN KEY DE " + result.getString(3) + " :::: " + result.getString(8)
								+ " REFERENCIA A TABLA: " + result.getString(7));
						System.out.println("PRIMARY KEY DE " + result.getString(3) + " :::: " + result.getString(4));

					}

				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
				
				
				
				
				
				
				
				
				try (ResultSet result = dmd.getImportedKeys(null, null, "empleados")) {

					while (result.next()) {

						System.out.println("IMPORTADA FOREIGN KEY DE " + result.getString(3) + " :::: " + result.getString(8)
								+ " REFERENCIA A TABLA: " + result.getString(7));


					}

				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
				
				
				
				
				
				
				
				
				
				

			}

		}

	}

}
