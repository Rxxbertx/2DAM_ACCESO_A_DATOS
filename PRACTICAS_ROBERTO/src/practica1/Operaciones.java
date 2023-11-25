package practica1;

import static practica1.Constantes.APELLIDOS;
import static practica1.Constantes.BYTES_APELLIDOS;
import static practica1.Constantes.BYTES_CHAR;
import static practica1.Constantes.BYTES_CICLO;
import static practica1.Constantes.BYTES_DNI;
import static practica1.Constantes.BYTES_NOMBRE;
import static practica1.Constantes.BYTES_REGISTRO;
import static practica1.Constantes.CICLO;
import static practica1.Constantes.CURSO;
import static practica1.Constantes.DNI;
import static practica1.Constantes.INICIO_BYTES_APELLIDOS;
import static practica1.Constantes.INICIO_BYTES_CICLO;
import static practica1.Constantes.INICIO_BYTES_CURSO;
import static practica1.Constantes.INICIO_BYTES_NOMBRE;
import static practica1.Constantes.NOMBRE;
import static practica1.Constantes.PUNTERO_INVALIDO;
import static practica1.Constantes.sc;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public final class Operaciones {

	static RandomAccessFile raf;

	static void modificar(File FICHERO) throws IOException {

		// Variables para almacenar información a modificar
		boolean salir = false;
		String dni = null;
		String nombre = null;
		String apellidos = null;
		String ciclo = null;
		int curso = -1;
		String aux = null;

		// Bucle principal para la modificación de registros
		while (!salir) {

			// Consultar la posición del registro a modificar
			final long PUNTERO_INCIO_REGISTRO = consultaEspecifica(FICHERO);

			// Verificar si se encontró el registro
			if (PUNTERO_INCIO_REGISTRO == PUNTERO_INVALIDO) {

				// Mostrar opciones al usuario
				System.out.println("(CUALQUIER TECLA)INTENTAR DE NUEVO\n(3)SALIR");

				// Verificar si el usuario desea salir
				if (sc.nextLine().equals("3")) {
					salir = true;
				}

			} else {

				// Verificar si el usuario desea realizar modificaciones
				if (UtilidadesOperaciones.preguntaModificacion()) {

					// Crear un nuevo RandomAccessFile para acceder al fichero de datos
					raf = new RandomAccessFile(FICHERO, "rw");

					// Bucle para manejar las diferentes opciones de modificación
					while (!salir) {

						// Seleccionar la opción de modificación
						switch (UtilidadesOperaciones.opcionesModificar()) {

						case DNI:

							// Posicionarse en el inicio del registro y leer el DNI
							raf.seek(PUNTERO_INCIO_REGISTRO);
							dni = UtilidadesOperaciones.leerDNI(raf).trim();

							// Solicitar la modificación del DNI
							aux = UtilidadesOperaciones.modificarDNI(dni);

							// Verificar si el DNI ha sido modificado
							if (aux.equals(dni)) {
								// nAn (ninguna acción necesaria)
							} else {
								// Posicionarse en el inicio del registro y escribir el nuevo DNI
								raf.seek(PUNTERO_INCIO_REGISTRO);
								System.out.println(raf.getFilePointer());
								raf.writeChars(aux);
							}

							break;

						case NOMBRE:

							// Posicionarse en la posición del nombre en el registro
							raf.seek(PUNTERO_INCIO_REGISTRO + INICIO_BYTES_NOMBRE);

							// Leer el nombre actual
							nombre = UtilidadesOperaciones.leerNOMBRE(raf).trim();

							// Solicitar la modificación del nombre
							aux = UtilidadesOperaciones.modificarNOMBRE(nombre);

							// Verificar si el nombre ha sido modificado
							if (aux.equals(nombre)) {
								// nAn
							} else {
								// Posicionarse en la posición del nombre en el registro y escribir el nuevo
								// nombre
								raf.seek(PUNTERO_INCIO_REGISTRO + INICIO_BYTES_NOMBRE);
								raf.writeChars(aux);
							}

							break;

						// ... (seguir con casos similares para APELLIDOS, CICLO, CURSO)

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
		// Cerrar el RandomAccessFile al finalizar la modificación
		raf.close();
		System.out.println("<<<MODIFICACION FINALIZADA>>>");

	}

	static void baja(File FICHERO) throws IOException {

		// Abrir el archivo de acceso aleatorio en modo de lectura y escritura
		raf = new RandomAccessFile(FICHERO, "rw");

		// Variables para el control del bucle y la búsqueda
		boolean salir = false;
		boolean encontrado = false;
		String dni = null;

		// Bucle principal para la eliminación de registros
		while (!salir) {

			// Solicitar al usuario el DNI del alumno a eliminar
			System.out.println("--INTRODUCE EL DNI DEL ALUMNO QUE DESEAS ELIMINAR--\n(3)PARA SALIR");
			dni = sc.nextLine();

			// Verificar si el usuario desea salir
			if (dni.equals("3"))
				break;

			// Validar el formato del DNI ingresado
			if (UtilidadesOperaciones.validarDNI(dni)) {

				System.out.println(">>>\nBUSCANDO ALUMNO POR DNI.....");

				// Bucle para buscar el alumno por DNI
				while (!encontrado) {

					// Verificar si se ha llegado al final del archivo
					if (raf.getFilePointer() >= raf.length()) {
						System.err.println("NO SE HA ENCONTRADO ESE DNI EN EL SISTEMA");
						System.out.println(">>>");
						break;
					}

					// Leer el DNI actual del registro
					String temp = UtilidadesOperaciones.componerDni(raf);

					// Verificar si el DNI coincide con el buscado
					if (temp.equals(dni)) {
						System.out.println("ALUMNO ENCONTRADO.. BORRANDO\n>>>");

						// Retroceder al inicio del DNI y realizar la eliminación del registro
						raf.seek(raf.getFilePointer() - BYTES_DNI);

						if (UtilidadesOperaciones.eliminar(raf))
							encontrado = true;
						else
							break;
					} else {
						// Saltar al siguiente registro
						raf.skipBytes(BYTES_REGISTRO - BYTES_DNI);
					}
				}

				// Verificar si se encontró y eliminó el alumno
				if (encontrado) {
					System.out.println("BORRADO CORRECTAMENTE");
					salir = true;
					
				}

			} else {
				System.err.println("!!!INGRESA UN DNI CORRECTO!!!");
			}
		}

		// Cerrar el archivo de acceso aleatorio al finalizar la eliminación
		raf.close();
	}

	static void consultar(File FICHERO) throws IOException {

		// Variable para controlar el bucle
		boolean salir = false;

		// Bucle principal para la consulta de registros
		while (!salir) {

			// Mostrar opciones al usuario
			System.out.println("---Que deseas hacer?---\n(1)Consultar Todo\n(2)Consultar Alumno Especifico\n(3)Salir\n"
					+ "-----------------------");

			// Leer la opción del usuario
			switch (sc.nextLine()) {
			case "1":

				// Llamar a la función de consulta general
				consultaGeneral(FICHERO);

				break;
			case "2":

				// Llamar a la función de consulta específica
				consultaEspecifica(FICHERO);

				break;
			case "3":
				salir = true; // Salir del bucle si el usuario elige salir
				break;

			default:
				System.err.println("<<<ERROR OPCION DESCONOCIDA>>>"); // Manejar opción no válida
				break;
			}
		}
	}

	private static long consultaEspecifica(File FICHERO) throws IOException {

		// Abrir el archivo de acceso aleatorio en modo de solo lectura
		raf = new RandomAccessFile(FICHERO, "r");

		// Variables para controlar el bucle y la búsqueda
		boolean salir = false;
		boolean encontrado = false;
		String dni = null;

		// Bucle para solicitar el DNI y realizar la búsqueda específica
		while (!salir) {

			// Solicitar al usuario el DNI del alumno a buscar
			System.out.println("INTRODUCE EL DNI DEL ALUMNO QUE DESEAS BUSCAR");
			dni = sc.nextLine();

			// Validar el formato del DNI ingresado
			if (UtilidadesOperaciones.validarDNI(dni)) {

				System.out.println(">>>\nBUSCANDO ALUMNO POR DNI.....");

				// Bucle para buscar el alumno por DNI
				while (!encontrado) {

					// Verificar si se ha llegado al final del archivo
					if (raf.getFilePointer() >= raf.length()) {
						System.err.println("NO SE HA ENCONTRADO ESE DNI EN EL SISTEMA");
						System.out.println(">>>");
						break;
					}

					// Leer el DNI actual del registro
					String temp = UtilidadesOperaciones.componerDni(raf);

					// Verificar si el DNI coincide con el buscado
					if (temp.equals(dni)) {
						System.out.println("ALUMNO ENCONTRADO.. leyendo\n>>>");
						encontrado = true;
					} else {
						// Saltar al siguiente registro
						raf.skipBytes(BYTES_REGISTRO - BYTES_DNI);
					}
				}

				// Verificar si se encontró y leyó el alumno
				if (encontrado) {

					// Retroceder al inicio del DNI para leer todo el registro
					raf.seek(raf.getFilePointer() - BYTES_DNI);

					// Imprimir la información del alumno
					System.out.println("\n" + UtilidadesOperaciones.leerAlumno(raf) + "\n");

					// Finalizar el bucle y devolver la posición del registro encontrado
					salir = true;
					long temp = raf.getFilePointer() - BYTES_REGISTRO;
					raf.close();
					return temp;

				} else {
					// Finalizar el bucle si no se encontró el alumno
					salir = true;
				}
			} else {
				System.err.println("!!!INGRESA UN DNI CORRECTO!!!");
			}
		}

		// Cerrar el archivo de acceso aleatorio al finalizar la búsqueda sin éxito
		raf.close();
		return PUNTERO_INVALIDO; // Indicar que no se encontró el alumno
	}

	private static void consultaGeneral(File FICHERO) throws FileNotFoundException {

		// Crear un flujo de entrada de datos para leer el archivo binario
		DataInputStream dis = new DataInputStream(new FileInputStream(FICHERO));

		// StringBuilder para almacenar la información de los usuarios
		StringBuilder usuarios = new StringBuilder();

		try {

			// Bucle para leer y procesar registros hasta que se lanza una excepción de
			// IOException
			while (true) {

				// Leer la información de un alumno desde el flujo de entrada de datos
				String alumno = UtilidadesOperaciones.leerAlumno(dis);

				// Verificar si se ha leído correctamente la información
				if (alumno != null) {
					// Agregar la información del alumno al StringBuilder
					usuarios.append("\n");
					usuarios.append(alumno);
					usuarios.append("\n");
				} else {
					// Saltar al siguiente registro si no se puede leer el alumno
					dis.skipBytes(BYTES_REGISTRO - BYTES_DNI);
				}
			}

		} catch (IOException e) {

			// Imprimir la información de los usuarios
			System.out.println(usuarios);

			try {
				// Cerrar el flujo de entrada de datos
				dis.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	static void alta(File FICHERO) throws IOException {

		// Abrir el archivo de acceso aleatorio en modo de lectura y escritura
		raf = new RandomAccessFile(FICHERO, "rw");

		// Inicializar variables y banderas
		int pasos = 1;
		boolean salir = false;
		boolean creacionCorrecta = false;
		String apellido = null;
		String nombre = null;
		String dni = null;
		String ciclo = null;
		int curso = 0;

		// Bucle principal para la entrada de datos del alumno
		while (!salir) {

			// Procesar cada paso según el estado actual
			switch (pasos) {

			case 1:
				// Ingresar el DNI
				System.out.println("---INGRESA EL DNI---\n(3)PARA SALIR");
				dni = Constantes.sc.nextLine();

				if (dni.equals("3")) {
					salir = true;
					break;
				}

				// Validar el formato del DNI
				if (!UtilidadesOperaciones.validarDNI(dni)) {
					System.err.println("!!!INGRESA UN DNI CORRECTO!!!");
				} else {
					pasos++;
				}
				break;

			case 2:
				// Ingresar el nombre
				System.out.println("---INGRESA EL NOMBRE---\n(3)PARA SALIR");
				nombre = Constantes.sc.nextLine();

				if (nombre.equals("3")) {
					salir = true;
					break;
				}

				// Validar el formato del nombre
				if (!UtilidadesOperaciones.validarNOMBRE(nombre)) {
					System.err.println("!!!INGRESA UN NOMBRE VÁLIDO!!!");
				} else {
					pasos++;
				}
				break;

			// ... (repetir casos similares para APELLIDO, CICLO, CURSO)

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
				// Finalizar el proceso de entrada de datos
				salir = true;
				creacionCorrecta = true;
				break;

			default:
				// Manejar caso desconocido
				System.err.println("!!!ERROR DESCONOCIDO!!!");
				salir = true;
				break;

			}

		}

		// Verificar si la entrada de datos fue exitosa
		if (creacionCorrecta) {

			salir = false;

			// Bucle para confirmar la creación del alumno
			while (!salir) {

				// Verificar si el usuario confirma la creación
				if (UtilidadesOperaciones.confirmacionCreacion()) {

					// Posicionarse al final del archivo y escribir los datos del nuevo alumno
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

					// Verificar si el usuario desea realizar modificaciones en la entrada
					if (UtilidadesOperaciones.preguntaModificacion()) {

						// Bucle para modificar la entrada de datos
						while (!salir) {

							// Procesar las opciones de modificación
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
						// Mostrar mensaje si se cancela la creación del alumno
						System.err.println("<<<SE HA CANCELADO LA CREACION DEL ALUMNO>>>");
						salir = true;
					}
				}
			}

		} else {
			// Mostrar mensaje si se cancela la creación del alumno
			System.err.println("<<<SE HA CANCELADO LA CREACION DEL ALUMNO>>>");
		}

		// Cerrar el archivo de acceso aleatorio
		raf.close();
	}

}
