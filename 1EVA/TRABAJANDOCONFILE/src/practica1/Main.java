package practica1;

import static practica1.Constantes.*;
import static practica1.Operaciones.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	
	static final File FICHERO = new File("./src/practica1/Alumnos.dat");


	public static void main(String[] args) {

		boolean activo = true;

		while (activo) {

			menu();
			int opcion =sc.nextInt();
			sc.nextLine();//parar eliminar el enter

			switch (opcion) {
			case ALTA:

				try {
					alta(FICHERO);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.err.println("error en dar de alta");
				}
				

				break;
			case BAJA:

				try {
					baja(FICHERO);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case MODIFICAR:

				modificar(FICHERO);

				break;
			case CONSULTA:

				try {
					consultar(FICHERO);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			default:

				activo=false;
				System.err.println("SALIDA DEL SISTEMA");
				System.exit(0);

				break;
			}

		}

	}



	private static void menu() {

		String menu = "---------MENU-------\n-1.ALTA ALUMNO\n-2.BAJA ALUMNO\n-3.MODIFICAR ALUMNO\n-4.CONSULTAR ALUMNOS\n--------------------";

		System.out.println(menu);

	}

}
