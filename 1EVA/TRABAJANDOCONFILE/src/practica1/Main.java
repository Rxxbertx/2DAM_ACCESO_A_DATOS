package practica1;

import static practica1.Constantes.*;
import static practica1.Operaciones.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	static final File FICHERO = new File("./src/practica1/Alumnos.dat");
	static final int ALTA = 1;
	static final int BAJA = 2;
	static final int MODIFICAR = 3;
	static final int CONSULTA = 4;
	static final int SALIDA=5;

	public static void main(String[] args) {

		boolean activo = true;
		int opcion=-1;

		while (activo) {

			menu(); // Muestra el menú

			// Lee la opción del usuario
			try {
			 opcion = sc.nextInt();
			}catch(Exception e) {
				 
			 }
			sc.nextLine(); // Para eliminar el salto de línea después de la entrada

			switch (opcion) {
			case ALTA:
				try {
					alta(FICHERO); // Llama al método para dar de alta
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("Error al dar de alta");
				}
				break;
			case BAJA:
				try {
					baja(FICHERO); // Llama al método para dar de baja
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("Error al dar de baja");
				}
				break;
			case MODIFICAR:
				try {
					modificar(FICHERO);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("FALLO");
				} // Llama al método para modificar
				break;
			case CONSULTA:
				try {
					consultar(FICHERO); // Llama al método para consultar
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("Error al consultar");
				}
				break;
			case SALIDA:
				System.exit(0); // Sale del programa
				activo = false;
				break;
			default:
				System.err.println("opcion desconocida");
				break;
			}
		}
	}

	// Método para mostrar el menú
	private static void menu() {
		String menu = "---------MENU--------\n(1)ALTA ALUMNO\n(2)BAJA ALUMNO\n(3)MODIFICAR ALUMNO\n(4)CONSULTAR ALUMNOS\n(5)SALIR DEL SISTEMA\n---------------------";
		System.out.println(menu);
	}

}
