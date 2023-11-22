package practica1;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import static practica1.Constantes.*;

public final class Operaciones {

	static RandomAccessFile raf;

	static void consultar(File FICHERO) {
		// TODO Auto-generated method stub

	}

	static void modificar(File FICHERO) {
		// TODO Auto-generated method stub

	}

	static void baja(File FICHERO) {
		// TODO Auto-generated method stub

	}

	static void alta(File FICHERO) throws IOException {

		raf = new RandomAccessFile(FICHERO, "rw");
		int pasos = 1;
		boolean salir = false;
		boolean creacionCorrecta = false;
		String apellido = null;
		String nombre = null;
		String dni = null;
		String ciclo = null;
		int curso = 0;

		while (!salir) {

			switch (pasos) {

			case 1:

				System.out.println("INGRESA EL DNI");
				dni = Constantes.sc.next();
				if (dni.length() > 8 || dni.length() < 8) {
					System.err.println("INGRESA UN DNI CORRECTO");
				} else {
					pasos++;
				}
				break;

			case 2:

				System.out.println("INGRESA EL NOMBRE");
				nombre = Constantes.sc.next();
				if (nombre.length() > 20) {
					System.err.println("INGRESA UN NOMBRE MENOR A 20 CARACTERES");
				} else {
					pasos++;
				}
				break;

			case 3:

				System.out.println("INGRESA EL APELLIDO");
				apellido = Constantes.sc.next();
				if (apellido.length() > 30) {
					System.err.println("INGRESA UN APELLIDO MENOR A 30 CARACTERES");
				} else {
					pasos++;
				}
				break;

			case 4:

				System.out.println("INGRESA EL CICLO");
				ciclo = Constantes.sc.next();
				if (ciclo.length() > 5) {
					System.err.println("INGRESA UN CICLO MENOR A 5 CARACTERES");
				} else {
					pasos++;
				}
				break;
			case 5:

				System.out.println("INGRESA EL CURSO");
				curso = Constantes.sc.nextInt();
				if (curso <= 0 || curso > 10) {
					System.err.println("INGRESA UN CURSO MENOR A 5 CARACTERES");
				} else {
					pasos++;
				}
				break;
			case 6:

				System.out.println("CREACION DE ALUMNO CORRECTA");
				salir = true;
				creacionCorrecta = true;
				break;

			default:

				System.out.println("ERROR DESCONOCIDO");
				salir = true;
				break;

			}

		}

		if (creacionCorrecta) {

			raf.seek(raf.length());
			
			StringBuffer sb = new StringBuffer(dni);
			sb.setLength(BYTES_DNI / BYTES_CHAR);
			raf.writeChars(sb.toString());

			sb = new StringBuffer(nombre);
			sb.setLength(BYTES_NOMBRE / BYTES_CHAR);
			raf.writeChars(sb.toString());

			sb = new StringBuffer(apellido);
			sb.setLength(BYTES_APELLIDO / BYTES_CHAR);
			raf.writeChars(sb.toString());

			sb = new StringBuffer(ciclo);
			sb.setLength(BYTES_CICLO / BYTES_CHAR);
			raf.writeChars(sb.toString());

			raf.writeInt(curso);
			
			System.out.println("ALUMNO AÑADIDO CORRECTAMENTE AL FICHERO");

		}else {
			System.out.println("ALUMNO NO SE HA PODIDO AÑADIR CORRECTAMENTE AL FICHERO");
		}

		raf.close();
	}
}
