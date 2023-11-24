package practica1;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import static practica1.Constantes.*;

public final class Operaciones {

	static RandomAccessFile raf;

	static void modificar(File FICHERO) throws IOException {

		raf = new RandomAccessFile(FICHERO, "rw");
		boolean salir = false;
		String dni = null;

		while (!salir) {

			final long PUNTERO_INCIO_REGISTRO = consultaEspecifica(FICHERO);
			if (PUNTERO_INCIO_REGISTRO == PUNTERO_INVALIDO) {

				System.out.println("(CUALQUIER TECLA)INTENTAR DE NUEVO\n(3)SALIR");
				if (sc.nextLine().equals("3")) {
					salir = true;
				}

			} else {

				if (UtilidadesOperaciones.preguntaModificacion()) {

					// campo a solicitar, en base a que quiere modificar

					while (!salir) {

						switch (UtilidadesOperaciones.opcionesModificar()) {

						case DNI:

							raf.seek(PUNTERO_INCIO_REGISTRO);
							raf.writeChars(dni);

							break;

						case NOMBRE:

							raf.seek(PUNTERO_INCIO_REGISTRO + INICIO_BYTES_NOMBRE);
							raf.writeChars(dni);

							break;
						case APELLIDOS:

							raf.seek(PUNTERO_INCIO_REGISTRO + INICIO_BYTES_APELLIDOS);
							raf.writeChars(dni);

							break;
						case CICLO:

							raf.seek(PUNTERO_INCIO_REGISTRO + INICIO_BYTES_CICLO);
							raf.writeChars(dni);

							break;
						case CURSO:

							raf.seek(PUNTERO_INCIO_REGISTRO + INICIO_BYTES_CURSO);
							raf.writeChars(dni);

							break;
						default:
							salir = true;
							break;

						}

					}

					System.out.println("<<<MODIFICACION FINALIZADA>>>");

				} else {

					System.out.println("MODIFICACION CANCELADA");
					salir = true;

				}
			}
		}

	}

	static void baja(File FICHERO) throws IOException {

		raf = new RandomAccessFile(FICHERO, "rw");
		boolean salir = false;
		boolean encontrado = false;
		String dni = null;

		while (!salir) {

			System.out.println("--INTRODUCE EL DNI DEL ALUMNO QUE DESEAS ELIMINAR--\n(3)PARA SALIR");
			dni = sc.nextLine();

			if (dni.equals("3"))
				break;

			if (UtilidadesOperaciones.validarDNI(dni)) {

				System.out.println(">>>\nBUSCANDO ALUMNO POR DNI.....");

				while (!encontrado) {

					if (raf.getFilePointer() >= raf.length()) {
						System.err.println("NO SE HA ENCONTRADO ESE DNI EN EL SISTEMA");
						System.out.println(">>>");
						break;
					}

					String temp = UtilidadesOperaciones.componerDni(raf);

					if (temp.equals(dni)) {
						System.out.println("ALUMNO ENCONTRADO.. BORRANDO\n>>>");

						raf.seek(raf.getFilePointer() - BYTES_DNI);

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
				}

			} else {
				System.err.println("!!!INGRESA UN DNI CORRECTO!!!");
			}

		}
		raf.close();

	}

	static void consultar(File FICHERO) throws IOException {

		boolean salir = false;
		boolean encontrado = false;
		String dni = null;

		while (!salir) {

			System.out.println("---Que deseas hacer?---\n(1)Consultar Todo\n(2)Consultar Alumno Especifico\n(3)Salir\n"
					+ "-----------------------");

			switch (sc.nextLine()) {
			case "1":

				consultaGeneral(FICHERO);

				break;
			case "2":

				consultaEspecifica(FICHERO);

				break;
			case "3":
				salir = true;
				break;

			default:
				System.err.println("<<<ERROR OPCION DESCONOCIDA>>>");
				break;
			}

		}

	}

	private static long consultaEspecifica(File FICHERO) throws IOException {

		raf = new RandomAccessFile(FICHERO, "r");

		boolean salir = false;
		boolean encontrado = false;
		String dni = null;

		while (!salir) {

			System.out.println("INTRODUCE EL DNI DEL ALUMNO QUE DESEAS BUSCAR");
			dni = sc.nextLine();

			if (UtilidadesOperaciones.validarDNI(dni)) {

				System.out.println(">>>\nBUSCANDO ALUMNO POR DNI.....");

				while (!encontrado) {

					if (raf.getFilePointer() >= raf.length()) {
						System.err.println("NO SE HA ENCONTRADO ESE DNI EN EL SISTEMA");
						System.out.println(">>>");
						break;
					}

					String temp = UtilidadesOperaciones.componerDni(raf);

					if (temp.equals(dni)) {
						System.out.println("ALUMNO ENCONTRADO.. leyendo\n>>>");
						encontrado = true;
					} else {
						raf.skipBytes(BYTES_REGISTRO - BYTES_DNI);
					}
				}
				if (encontrado) {

					raf.seek(raf.getFilePointer() - BYTES_DNI);

					System.out.println(UtilidadesOperaciones.leerAlumno(raf));
					salir = true;
					return raf.getFilePointer() - BYTES_REGISTRO;

				} else {
					salir = true;
				}
			} else {
				System.err.println("!!!INGRESA UN DNI CORRECTO!!!");
			}
		}

		raf.close();
		return PUNTERO_INVALIDO;

	}

	private static void consultaGeneral(File FICHERO) throws FileNotFoundException {

		DataInputStream dis = new DataInputStream(new FileInputStream(FICHERO));
		StringBuilder usuarios = new StringBuilder();

		try {

			while (true) {

				StringBuilder temp = new StringBuilder();

				for (int i = 0; i < BYTES_DNI / BYTES_CHAR; i++) {

					temp.append(dis.readChar());

				}

				if (temp.toString().equals("000000000")) {

					dis.readNBytes(BYTES_REGISTRO - BYTES_DNI);
					continue;

				}

				String dni = new String("DNI: " + temp).trim();

				temp.delete(0, temp.length());

				for (int i = 0; i < BYTES_NOMBRE / BYTES_CHAR; i++) {

					temp.append(dis.readChar());

				}

				String nombre = new String("Nombre: " + temp).trim();
				temp.delete(0, temp.length());

				for (int i = 0; i < BYTES_APELLIDO / BYTES_CHAR; i++) {

					temp.append(dis.readChar());

				}

				String apellidos = new String("Apellidos: " + temp).trim();
				temp.delete(0, temp.length());

				for (int i = 0; i < BYTES_CICLO / BYTES_CHAR; i++) {

					temp.append(dis.readChar());

				}

				String ciclo = new String("Ciclo: " + temp).trim();
				temp.delete(0, temp.length());

				String curso = new String("Curso: " + dis.readInt()).trim();

				temp.append(dni);
				temp.append("\n");
				temp.append(nombre);
				temp.append("\n");
				temp.append(apellidos);
				temp.append("\n");
				temp.append(ciclo);
				temp.append("\n");
				temp.append(curso);

				usuarios.append(temp);
				usuarios.append("\n");

			}
		} catch (IOException e) {

			System.out.println(usuarios);
			try {
				dis.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

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

				System.out.println("---INGRESA EL DNI---\n(3)PARA SALIR");
				dni = Constantes.sc.nextLine();

				if (dni.equals("3")) {
					salir = true;
					break;
				}

				if (!UtilidadesOperaciones.validarDNI(dni)) {
					System.err.println("!!!INGRESA UN DNI CORRECTO!!!");
				} else {
					pasos++;
				}
				break;

			case 2:

				System.out.println("---INGRESA EL NOMBRE---\n(3)PARA SALIR");
				nombre = Constantes.sc.nextLine();

				if (nombre.equals("3")) {
					salir = true;
					break;
				}

				if (!UtilidadesOperaciones.validarNOMBRE(nombre)) {
					System.err.println(
							"!!!INGRESA UN NOMBRE MENOR A 20 CARACTERES, MAYOR A 5 CARACTERES Y SIN NINGUN NUMERO!!!");
				} else {
					pasos++;
				}
				break;

			case 3:

				System.out.println("---INGRESA EL APELLIDO---\n(3)PARA SALIR");
				apellido = Constantes.sc.nextLine();

				if (apellido.equals("3")) {
					salir = true;
					break;
				}

				if (!UtilidadesOperaciones.validarAPELLIDOS(apellido)) {
					System.err.println(
							"!!!INGRESA UN APELLIDO MENOR A 30 CARACTERES, MAYOR A 0 CARACTERES Y SIN NINGUN NUMERO!!!");
				} else {
					pasos++;
				}
				break;

			case 4:

				System.out.println("---INGRESA EL CICLO---\n(3)PARA SALIR");
				ciclo = Constantes.sc.nextLine();

				if (ciclo.equals("3")) {
					salir = true;
					break;
				}

				if (!UtilidadesOperaciones.validarCICLO(ciclo)) {
					System.err.println("!!!INGRESA UN CICLO MENOR A 5 CARACTERES, MINIMO UN CARACTER!!!");
				} else {
					pasos++;
				}
				break;
			case 5:

				System.out.println("---INGRESA EL CURSO---\n(X)PARA SALIR");
				String temp = Constantes.sc.nextLine();

				if (temp.equals("X") || temp.equals("x")) {
					salir = true;
					break;
				}

				if (!UtilidadesOperaciones.validarCURSO(temp)) {
					System.err.println("!!!INGRESA UN CURSO MENOR A 10 y MAYOR A 0!!!");
				} else {

					curso = Integer.parseInt(temp);
					pasos++;
				}
				break;
			case 6:

				salir = true;
				creacionCorrecta = true;
				break;

			default:

				System.err.println("!!!ERROR DESCONOCIDO!!!");
				salir = true;
				break;

			}

		}

		if (creacionCorrecta) {

			salir = false;

			while (!salir) {

				if (UtilidadesOperaciones.confirmacionCreacion()) {

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
					salir = true;

				} else {

					if (UtilidadesOperaciones.preguntaModificacion()) {

						while (!salir) {

							switch (UtilidadesOperaciones.opcionesModificar()) {

							case DNI:

								dni = UtilidadesOperaciones.modificarDNI(dni);

								break;
							case NOMBRE:

								nombre = UtilidadesOperaciones.modificarNOMBRE(nombre);

								break;
							case APELLIDOS:

								apellido = UtilidadesOperaciones.modificarAPELLIDOS(apellido);

								break;
							case CICLO:

								ciclo = UtilidadesOperaciones.modificarCICLO(ciclo);

								break;
							case CURSO:

								curso = UtilidadesOperaciones.modificarCURSO(curso);

								break;
							default:
								System.out.println("MODIFICACION FINALIZADA");
								salir = true;
								break;
							}

						}
						salir = false;

					} else {
						System.err.println("<<<SE HA CANCELADO LA CREACION DEL ALUMNO>>>");
						salir = true;
					}

				}

			}

		} else {
			System.err.println("<<<SE HA CANCELADO LA CREACION DEL ALUMNO>>>");
		}

		raf.close();
	}

}
