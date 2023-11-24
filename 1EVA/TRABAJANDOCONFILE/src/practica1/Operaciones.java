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

		boolean salir = false;
		String dni = null;
		String nombre = null;
		String apellidos = null;
		String ciclo = null;
		int curso = -1;
		String aux = null;

		while (!salir) {

			final long PUNTERO_INCIO_REGISTRO = consultaEspecifica(FICHERO);
			if (PUNTERO_INCIO_REGISTRO == PUNTERO_INVALIDO) {

				System.out.println("(CUALQUIER TECLA)INTENTAR DE NUEVO\n(3)SALIR");
				if (sc.nextLine().equals("3")) {
					salir = true;
				}

			} else {

				if (UtilidadesOperaciones.preguntaModificacion()) {
					
					raf = new RandomAccessFile(FICHERO, "rw");

					while (!salir) {

						switch (UtilidadesOperaciones.opcionesModificar()) {

						case DNI:

							
							raf.seek(PUNTERO_INCIO_REGISTRO);

							dni = UtilidadesOperaciones.leerDNI(raf).trim();
							aux = UtilidadesOperaciones.modificarDNI(dni);

							if (aux.equals(dni)) {

								// nAn

							} else {

								raf.seek(PUNTERO_INCIO_REGISTRO);
								System.out.println(raf.getFilePointer());
								raf.writeChars(aux);
							}

							break;

						case NOMBRE:

							raf.seek(PUNTERO_INCIO_REGISTRO + INICIO_BYTES_NOMBRE);

							nombre = UtilidadesOperaciones.leerNOMBRE(raf).trim();
							aux = UtilidadesOperaciones.modificarNOMBRE(nombre);
							if (aux.equals(nombre)) {

								// nAn

							} else {

								raf.seek(PUNTERO_INCIO_REGISTRO + INICIO_BYTES_NOMBRE);

								raf.writeChars(aux);
							}

							break;
						case APELLIDOS:

							raf.seek(PUNTERO_INCIO_REGISTRO + INICIO_BYTES_APELLIDOS);

							apellidos = UtilidadesOperaciones.leerAPELLIDOS(raf).trim();
							aux = UtilidadesOperaciones.modificarAPELLIDOS(apellidos);
							if (aux.equals(apellidos)) {
								// nAn
							} else {

								raf.seek(PUNTERO_INCIO_REGISTRO + INICIO_BYTES_APELLIDOS);

								raf.writeChars(aux);
							}

							break;
						case CICLO:

							raf.seek(PUNTERO_INCIO_REGISTRO + INICIO_BYTES_CICLO);

							ciclo = UtilidadesOperaciones.leerCICLO(raf).trim();
							aux = UtilidadesOperaciones.modificarCICLO(ciclo);
							if (aux.equals(ciclo)) {
								
								
							} else {

								raf.seek(PUNTERO_INCIO_REGISTRO + INICIO_BYTES_CICLO);

								raf.writeChars(aux);
							}

							break;
						case CURSO:

							raf.seek(PUNTERO_INCIO_REGISTRO + INICIO_BYTES_CURSO);

							curso = UtilidadesOperaciones.leerCURSO(raf);
							int temp = UtilidadesOperaciones.modificarCURSO(curso);

							if (temp == curso) {
								// nAn
							} else {

								raf.seek(PUNTERO_INCIO_REGISTRO + INICIO_BYTES_CURSO);

								raf.writeInt(temp);
							}

							break;
						default:
							salir = true;
							break;

						}

					}

				} else {

					salir = true;

				}
			}
		}
		raf.close();
		System.out.println("<<<MODIFICACION FINALIZADA>>>");

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

					System.out.println("\n" + UtilidadesOperaciones.leerAlumno(raf) + "\n");
					salir = true;
					long temp = raf.getFilePointer() - BYTES_REGISTRO;
					raf.close();
					return temp;

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

				String alumno = UtilidadesOperaciones.leerAlumno(dis);

				if (alumno != null) {
					usuarios.append("\n");
					usuarios.append(alumno);
					usuarios.append("\n");
				} else {
					dis.skipBytes(BYTES_REGISTRO - BYTES_DNI);
				}

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
					sb.setLength(BYTES_APELLIDOS / BYTES_CHAR);
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

								dni = UtilidadesOperaciones.modificarDNI(dni).trim();

								break;
							case NOMBRE:

								nombre = UtilidadesOperaciones.modificarNOMBRE(nombre).trim();

								break;
							case APELLIDOS:

								apellido = UtilidadesOperaciones.modificarAPELLIDOS(apellido).trim();

								break;
							case CICLO:

								ciclo = UtilidadesOperaciones.modificarCICLO(ciclo).trim();

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
