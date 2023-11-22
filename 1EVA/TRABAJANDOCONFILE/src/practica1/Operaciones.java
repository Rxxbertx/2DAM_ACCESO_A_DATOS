package practica1;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import static practica1.Constantes.*;

public final class Operaciones {

	static RandomAccessFile raf;

	static void modificar(File FICHERO) {
		// TODO Auto-generated method stub

	}

	static void baja(File FICHERO) throws IOException {

		raf = new RandomAccessFile(FICHERO, "rw");
		boolean salir = false;
		boolean encontrado = false;
		String dni = null;

		while (!salir) {

			System.out.println("INTRODUCE EL DNI DEL ALUMNO QUE DESEAS ELIMINAR");
			dni = sc.next();

			if (UtilidadesOperaciones.validarDNI(dni)) {

				System.out.println("BUSCANDO ALUMNO POR DNI.....");

				while (!encontrado) {

					if (raf.getFilePointer() >= raf.length()) {
						System.err.println("NO SE HA ENCONTRADO ESE DNI EN EL SISTEMA");
						break;
					}

					String temp = UtilidadesOperaciones.componerDni(raf);

					if (temp.equals(dni)) {
						System.out.println("ALUMNO ENCONTRADO.. BORRANDO");

						raf.seek(raf.getFilePointer() - BYTES_DNI / BYTES_CHAR);

						if (UtilidadesOperaciones.eliminar(raf))
							encontrado = true;
						else
							break;

					} else {
						raf.skipBytes(BYTES_REGISTRO - BYTES_DNI);
					}

				}

				if (encontrado) {
					System.out.println("BORRADO CORRECTAMENTE");
					salir = true;
				} else {
					System.out.println("NO SE HA BORRADO");

				}

			}else {
				System.err.println("INGRESA UN DNI CORRECTO");
			}

		}

	}

	static void consultar(File FICHERO) throws FileNotFoundException {

		boolean salir = false;
		boolean encontrado = false;
		String dni = null;

		while (!salir) {

			System.out.println("Que deseas hacer? (1)Consultar Todo (2)Consultar Alumno Especifico (3) Salir");

			switch (sc.nextInt()) {
			case 1:

				consultaGeneral(FICHERO);

				break;
			case 2:

				consultaEspecifica(FICHERO);

				break;
			case 3:
				salir = true;
				break;

			default:
				System.out.println("ERROR OPCION DESCONOCIDA SI DESEA SALIR INTRODUCE (3)");
				break;
			}

		}

	}

	private static void consultaEspecifica(File FICHERO) throws FileNotFoundException {

		raf = new RandomAccessFile(new File(""), "r");

	}

	private static void consultaGeneral(File FICHERO) throws FileNotFoundException {

		DataInputStream dis = new DataInputStream(new FileInputStream(FICHERO));
		StringBuffer usuarios = new StringBuffer();

		while (true) {

			StringBuffer temp = new StringBuffer();

		}

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
				dni = Constantes.sc.nextLine();
				if (!UtilidadesOperaciones.validarDNI(dni)) {
					System.err.println("INGRESA UN DNI CORRECTO");
				} else {
					pasos++;
				}
				break;

			case 2:

				System.out.println("INGRESA EL NOMBRE");
				nombre = Constantes.sc.nextLine();
				if (!nombre.matches("^[^\\d]{1,20}$")) {
					System.err.println("INGRESA UN NOMBRE MENOR A 20 CARACTERES, MAYOR A 5 CARACTERES Y SIN NINGUN NUMERO");
				} else {
					pasos++;
				}
				break;

			case 3:

				System.out.println("INGRESA EL APELLIDO");
				apellido = Constantes.sc.nextLine();
				if (!apellido.matches("^[^\\d]{1,30}$")) {
					System.err.println("INGRESA UN APELLIDO MENOR A 30 CARACTERES, MAYOR A 0 CARACTERES Y SIN NINGUN NUMERO");
				} else {
					pasos++;
				}
				break;

			case 4:

				System.out.println("INGRESA EL CICLO");
				ciclo = Constantes.sc.nextLine();
				if (ciclo.length() > 5 || ciclo.isBlank()) {
					System.err.println("INGRESA UN CICLO MENOR A 5 CARACTERES, MINIMO UN CARACTER");
				} else {
					pasos++;
				}
				break;
			case 5:

				System.out.println("INGRESA EL CURSO");
				curso = Constantes.sc.nextInt();
				sc.nextLine();
				if (curso <= 0 || curso > 10) {
					System.err.println("INGRESA UN CURSO MENOR A 10 y MAYOR A 0");
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

				System.err.println("ERROR DESCONOCIDO");
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

		} else {
			System.err.println("ALUMNO NO SE HA PODIDO AÑADIR CORRECTAMENTE AL FICHERO");
		}

		raf.close();
	}
}
