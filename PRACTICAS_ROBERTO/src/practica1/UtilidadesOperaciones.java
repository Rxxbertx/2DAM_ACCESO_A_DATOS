package practica1;

import java.io.DataInputStream;
import java.io.IOException;
import static practica1.Constantes.*;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UtilidadesOperaciones {

	static boolean eliminar(RandomAccessFile raf) {

		// Crear un StringBuffer con 9 caracteres "0" para marcar el alumno como borrado
		StringBuffer sb = new StringBuffer("000000000");
		sb.setLength(9);

		// Solicitar confirmación para borrar el alumno
		System.out.println("DESEAS BORRAR EL ALUMNO? (1) SI (CUALQUIER TECLA) NO");
		String opc = sc.nextLine();

		// Verificar la opción del usuario
		if (opc.equals("1")) {
			// El usuario confirmó borrar el alumno
		} else {
			// El usuario eligió no borrar el alumno
			return false;
		}

		try {
			// Escribir en el archivo marcando el alumno como borrado
			raf.writeChars(sb.toString());
			return true; // Indicar que el borrado fue exitoso
		} catch (IOException e) {
			// Manejar error en caso de que ocurra una excepción de E/S
			System.out.println("ERROR EN BORRADO");
		}

		return false; // Indicar que el borrado no fue exitoso
	}

	// Función para validar el formato de un DNI
	static boolean validarDNI(String dni) {
		String patronDNI = "\\d{8}[A-HJ-NP-TV-Z]";
		Pattern pattern = Pattern.compile(patronDNI);
		Matcher matcher = pattern.matcher(dni);

		return matcher.matches();
	}

// Función para validar el formato del nombre
	static boolean validarNOMBRE(String nombre) {
		// Verificar que el nombre no contenga números y tenga entre 1 y 20 caracteres
		return nombre.matches("^[^\\d]{1,20}$");
	}

// Función para validar el formato de los apellidos
	static boolean validarAPELLIDOS(String apellidos) {
		// Verificar que los apellidos no contengan números y tengan entre 1 y 30
		// caracteres
		return apellidos.matches("^[^\\d]{1,30}$");
	}

// Función para validar el formato del ciclo
	static boolean validarCICLO(String ciclo) {
		// Verificar que el ciclo no contenga números y tenga entre 1 y 5 caracteres
		return ciclo.matches("^[\\D]{1,5}$");
	}

// Función para validar el formato del curso
	static boolean validarCURSO(String cursoString) {
		int curso = 0;

		try {
			// Intentar convertir la cadena a un entero
			curso = Integer.parseInt(cursoString);
		} catch (NumberFormatException nfe) {
			// Manejar la excepción si la cadena no es un número válido
			return false;
		}

		// Verificar que el curso sea mayor a 0 y menor o igual a 10
		return !(curso <= 0 || curso > 10);
	}

	// Función para componer el DNI a partir de un archivo de acceso aleatorio
	static String componerDni(RandomAccessFile raf) {
		// StringBuffer para almacenar los caracteres del DNI
		StringBuffer sb = new StringBuffer();

		// Iterar sobre cada carácter del DNI en el archivo
		for (int i = 0; i < BYTES_DNI / BYTES_CHAR; i++) {
			try {
				// Leer un carácter del archivo y agregarlo al StringBuffer
				sb.append(raf.readChar());
			} catch (IOException e) {
				// Manejar error en caso de que ocurra una excepción de E/S
				System.out.println("ERROR AL BUSCAR EL DNI");
			}
		}

		// Convertir el StringBuffer a una cadena y eliminar espacios en blanco al final
		return new String(sb).trim();
	}

	// Función para leer y formatear la información de un alumno desde un archivo de
	// acceso aleatorio
	public static String leerAlumno(RandomAccessFile raf) {
		try {
			// StringBuilder para construir la información del alumno
			StringBuilder temp = new StringBuilder();

			// Leer y formatear el DNI
			String dni = new String("DNI: " + leerDNI(raf)).trim();

			// Verificar si el DNI es el marcador de eliminación
			if (dni.equals("DNI: 000000000")) {
				return null; // Devolver null si el alumno fue marcado como eliminado
			}

			// Leer y formatear el nombre, apellidos, ciclo y curso
			String nombre = new String("Nombre: " + leerNOMBRE(raf)).trim();
			String apellidos = new String("Apellidos: " + leerAPELLIDOS(raf)).trim();
			String ciclo = new String("Ciclo: " + leerCICLO(raf)).trim();
			String curso = new String("Curso: " + leerCURSO(raf)).trim();

			// Agregar la información al StringBuilder
			temp.append(dni);
			temp.append("\n");
			temp.append(nombre);
			temp.append("\n");
			temp.append(apellidos);
			temp.append("\n");
			temp.append(ciclo);
			temp.append("\n");
			temp.append(curso);

			// Devolver la información del alumno como una cadena
			return temp.toString();

		} catch (IOException e) {
			return ""; // Manejar cualquier error de E/S devolviendo una cadena vacía
		}
	}

// Función para leer y formatear la información de un alumno desde un flujo de entrada de datos
	public static String leerAlumno(DataInputStream dis) throws IOException {
		// StringBuilder para construir la información del alumno
		StringBuilder temp = new StringBuilder();

		// Leer y formatear el DNI
		String dni = new String("DNI: " + leerDNI(dis)).trim();

		// Verificar si el DNI es el marcador de eliminación
		if (dni.equals("DNI: 000000000")) {
			return null; // Devolver null si el alumno fue marcado como eliminado
		}

		// Leer y formatear el nombre, apellidos, ciclo y curso
		String nombre = new String("Nombre: " + leerNOMBRE(dis)).trim();
		String apellidos = new String("Apellidos: " + leerAPELLIDOS(dis)).trim();
		String ciclo = new String("Ciclo: " + leerCICLO(dis)).trim();
		String curso = new String("Curso: " + leerCURSO(dis)).trim();

		// Agregar la información al StringBuilder
		temp.append(dni);
		temp.append("\n");
		temp.append(nombre);
		temp.append("\n");
		temp.append(apellidos);
		temp.append("\n");
		temp.append(ciclo);
		temp.append("\n");
		temp.append(curso);

		// Devolver la información del alumno como una cadena
		return temp.toString();
	}

	// Función para preguntar al usuario si desea modificar un alumno
	public static boolean preguntaModificacion() {
		// Mostrar mensaje de confirmación
		System.out.println("--MODIFICAR EL ALUMNO?--\n(1) SI\n(CUALQUIER TECLA) NO");

		// Leer la opción del usuario
		String opc = sc.nextLine();

		// Verificar la opción del usuario
		if (opc.equals("1")) {
			return true; // Devolver true si el usuario elige modificar
		} else {
			System.out.println("MODIFICACION CANCELADA");
			return false; // Devolver false si el usuario elige no modificar
		}
	}

// Función para preguntar al usuario si desea confirmar la creación de un alumno
	public static boolean confirmacionCreacion() {
		// Mostrar mensaje de confirmación
		System.out.println("--DESEAS CREAR EL ALUMNO?--\n(1) SI\n(CUALQUIER TECLA) NO");

		// Leer la opción del usuario
		String opc = sc.nextLine();

		// Verificar la opción del usuario
		if (opc.equals("1")) {
			return true; // Devolver true si el usuario elige confirmar la creación
		} else {
			return false; // Devolver false si el usuario elige cancelar la creación
		}
	}

	// Función para mostrar las opciones de modificación y obtener la elección del
	// usuario
	public static int opcionesModificar() {
		boolean salir = false;

		while (!salir) {
			// Mostrar las opciones de modificación al usuario
			System.out.println(
					"------MODIFICACION------\n(1)DNI\n(2)NOMBRE\n(3)APELLIDOS\n(4)CICLO\n(5)CURSO\n------------------------\n(CUALQUIER TECLA)SALIR");

			// Leer la opción del usuario
			String opc = sc.nextLine();

			// Procesar la opción del usuario y devolver el código correspondiente
			switch (opc) {
			case "1":
				return DNI;
			case "2":
				return NOMBRE;
			case "3":
				return APELLIDOS;
			case "4":
				return CICLO;
			case "5":
				return CURSO;
			default:
				salir = true;
				break;
			}
		}
		return 0; // Devolver 0 si el usuario elige salir
	}

// Función para modificar el DNI del alumno
	public static String modificarDNI(String dni) {
		// Mostrar mensaje para ingresar el nuevo DNI
		System.out.println("INTRODUCE EL DNI NUEVO\nACTUAL: " + dni);

		// Leer el nuevo DNI del usuario
		String temp = sc.nextLine();

		// Validar el nuevo DNI ingresado por el usuario
		if (UtilidadesOperaciones.validarDNI(temp)) {
			// Verificar si el nuevo DNI es igual al actual
			if (temp.equals(dni)) {
				System.err.println("<<<MODIFICACION NO REALIZADA, MISMO DNI>>>");
			} else {
				// Confirmar la modificación con el usuario
				System.out.println(
						"---ESTAS SEGURO DE REEMPLAZAR EL DNI ACTUAL POR EL NUEVO?---\n(1) SI\n(CUALQUIER TECLA) NO\n"
								+ "\nNUEVO: " + temp + " ACTUAL: " + dni);
				if (sc.nextLine().equals("1")) {
					System.out.println("MODIFICACION DNI ACEPTADA");

					// Formatear el nuevo DNI y devolverlo
					StringBuffer sb = new StringBuffer(temp);
					sb.setLength(BYTES_DNI / BYTES_CHAR);
					return sb.toString();
				} else {
					System.err.println("MODIFICACION DNI CANCELADA");
				}
			}
		} else {
			System.err.println("DNI INCORRECTO, MODIFICACION CANCELADA");
		}
		return dni; // Devolver el DNI actual si la modificación es cancelada
	}

// Función para modificar el nombre del alumno
	public static String modificarNOMBRE(String nombre) {
		// Mostrar mensaje para ingresar el nuevo nombre
		System.out.println("INTRODUCE EL NOMBRE NUEVO\nACTUAL: " + nombre);

		// Leer el nuevo nombre del usuario
		String temp = sc.nextLine();

		// Validar el nuevo nombre ingresado por el usuario
		if (UtilidadesOperaciones.validarNOMBRE(temp)) {
			// Verificar si el nuevo nombre es igual al actual
			if (temp.equals(nombre)) {
				System.err.println("<<<MODIFICACION NO REALIZADA, MISMO NOMBRE>>>");
			} else {
				// Confirmar la modificación con el usuario
				System.out.println(
						"---ESTAS SEGURO DE REEMPLAZAR EL NOMBRE ACTUAL POR EL NUEVO?---\n(1) SI\n(CUALQUIER TECLA) NO\n"
								+ "\nNUEVO: " + temp + " ACTUAL: " + nombre);
				if (sc.nextLine().equals("1")) {
					System.out.println("MODIFICACION NOMBRE ACEPTADA");

					// Formatear el nuevo nombre y devolverlo
					StringBuffer sb = new StringBuffer(temp);
					sb.setLength(BYTES_NOMBRE / BYTES_CHAR);
					return sb.toString();
				} else {
					System.err.println("MODIFICACION NOMBRE CANCELADA");
				}
			}
		} else {
			System.err.println("NOMBRE INCORRECTO, MODIFICACION CANCELADA");
		}
		return nombre; // Devolver el nombre actual si la modificación es cancelada
	}

// Función para modificar los apellidos del alumno
	public static String modificarAPELLIDOS(String apellidos) {
		// Mostrar mensaje para ingresar los nuevos apellidos
		System.out.println("INTRODUCE LOS APELLIDOS NUEVOS\nACTUAL: " + apellidos);

		// Leer los nuevos apellidos del usuario
		String temp = sc.nextLine();

		// Validar los nuevos apellidos ingresados por el usuario
		if (UtilidadesOperaciones.validarAPELLIDOS(temp)) {
			// Verificar si los nuevos apellidos son iguales a los actuales
			if (temp.equals(apellidos)) {
				System.err.println("<<<MODIFICACION NO REALIZADA, MISMOS APELLIDOS>>>");
			} else {
				// Confirmar la modificación con el usuario
				System.out.println(
						"---ESTAS SEGURO DE REEMPLAZAR LOS APELLIDOS ACTUALES POR LOS NUEVOS?---\n(1) SI\n(CUALQUIER TECLA) NO\n"
								+ "\nNUEVO: " + temp + " ACTUAL: " + apellidos);
				if (sc.nextLine().equals("1")) {
					System.out.println("MODIFICACION APELLIDOS ACEPTADA");

					// Formatear los nuevos apellidos y devolverlos
					StringBuffer sb = new StringBuffer(temp);
					sb.setLength(BYTES_APELLIDOS / BYTES_CHAR);
					return sb.toString();
				} else {
					System.err.println("MODIFICACION APELLIDOS CANCELADA");
				}
			}
		} else {
			System.err.println("APELLIDOS INCORRECTOS, MODIFICACION CANCELADA");
		}
		return apellidos; // Devolver los apellidos actuales si la modificación es cancelada
	}

// Función para modificar el ciclo del alumno
	public static String modificarCICLO(String ciclo) {
		// Mostrar mensaje para ingresar el nuevo ciclo
		System.out.println("INTRODUCE EL CICLO NUEVO\nACTUAL: " + ciclo);

		// Leer el nuevo ciclo del usuario
		String temp = sc.nextLine();

		// Validar el nuevo ciclo ingresado por el usuario
		if (UtilidadesOperaciones.validarCICLO(temp)) {
			// Verificar si el nuevo ciclo es igual al actual
			if (temp.equals(ciclo)) {
				System.err.println("<<<MODIFICACION NO REALIZADA, MISMO CICLO>>>");
			} else {
				// Confirmar la modificación con el usuario
				System.out.println(
						"---ESTAS SEGURO DE REEMPLAZAR EL CICLO ACTUAL POR EL NUEVO?---\n(1) SI\n(CUALQUIER TECLA) NO\n"
								+ "\nNUEVO: " + temp + " ACTUAL: " + ciclo);
				if (sc.nextLine().equals("1")) {
					System.out.println("MODIFICACION CICLO ACEPTADA");

					// Formatear el nuevo ciclo y devolverlo
					StringBuffer sb = new StringBuffer(temp);
					sb.setLength(BYTES_CICLO / BYTES_CHAR);
					return sb.toString();
				} else {
					System.err.println("MODIFICACION CICLO CANCELADA");
				}
			}
		} else {
			System.err.println("CICLO INCORRECTO, MODIFICACION CANCELADA");
		}
		return ciclo; // Devolver el ciclo actual si la modificación es cancelada
	}

// Función para modificar el curso del alumno
	public static int modificarCURSO(int curso) {
		// Mostrar mensaje para ingresar el nuevo curso
		System.out.println("INTRODUCE EL CURSO NUEVO\nACTUAL: " + curso);

		// Leer el nuevo curso del usuario
		String temp = sc.nextLine();

		// Validar el nuevo curso ingresado por el usuario
		if (UtilidadesOperaciones.validarCURSO(temp)) {
			// Verificar si el nuevo curso es igual al actual
			if (Integer.parseInt(temp) == curso) {
				System.err.println("<<<MODIFICACION NO REALIZADA, MISMO CURSO>>>");
			} else {
				// Confirmar la modificación con el usuario
				System.out.println(
						"---ESTAS SEGURO DE REEMPLAZAR EL CURSO ACTUAL POR EL NUEVO?---\n(1) SI\n(CUALQUIER TECLA) NO\n"
								+ "\nNUEVO: " + temp + " ACTUAL: " + curso);
				if (sc.nextLine().equals("1")) {
					System.out.println("MODIFICACION CURSO ACEPTADA");

					// Devolver el nuevo curso convertido a entero
					return Integer.parseInt(temp);
				} else {
					System.err.println("MODIFICACION CURSO CANCELADA");
				}
			}
		} else {
			System.err.println("CURSO INCORRECTO, MODIFICACION CANCELADA");
		}
		return curso; // Devolver el curso actual si la modificación es cancelada
	}

	public static String leerDNI(RandomAccessFile raf) throws IOException {

		StringBuilder temp = new StringBuilder();

		for (int i = 0; i < BYTES_DNI / BYTES_CHAR; i++) {

			temp.append(raf.readChar());

		}
		return temp.toString();

	}

	public static String leerNOMBRE(RandomAccessFile raf) throws IOException {
		StringBuilder temp = new StringBuilder();

		for (int i = 0; i < BYTES_NOMBRE / BYTES_CHAR; i++) {

			temp.append(raf.readChar());

		}
		return temp.toString();
	}

	public static String leerAPELLIDOS(RandomAccessFile raf) throws IOException {
		StringBuilder temp = new StringBuilder();

		for (int i = 0; i < BYTES_APELLIDOS / BYTES_CHAR; i++) {

			temp.append(raf.readChar());

		}
		return temp.toString();
	}

	public static String leerCICLO(RandomAccessFile raf) throws IOException {
		StringBuilder temp = new StringBuilder();

		for (int i = 0; i < BYTES_CICLO / BYTES_CHAR; i++) {

			temp.append(raf.readChar());

		}
		return temp.toString();
	}

	public static int leerCURSO(RandomAccessFile raf) throws IOException {
		// TODO Auto-generated method stub
		return raf.readInt();
	}

	public static String leerDNI(DataInputStream das) throws IOException {

		StringBuilder temp = new StringBuilder();

		for (int i = 0; i < BYTES_DNI / BYTES_CHAR; i++) {

			temp.append(das.readChar());

		}
		return temp.toString();

	}

	public static String leerNOMBRE(DataInputStream das) throws IOException {
		StringBuilder temp = new StringBuilder();

		for (int i = 0; i < BYTES_NOMBRE / BYTES_CHAR; i++) {

			temp.append(das.readChar());

		}
		return temp.toString();
	}

	public static String leerAPELLIDOS(DataInputStream das) throws IOException {
		StringBuilder temp = new StringBuilder();

		for (int i = 0; i < BYTES_APELLIDOS / BYTES_CHAR; i++) {

			temp.append(das.readChar());

		}
		return temp.toString();
	}

	public static String leerCICLO(DataInputStream das) throws IOException {
		StringBuilder temp = new StringBuilder();

		for (int i = 0; i < BYTES_CICLO / BYTES_CHAR; i++) {

			temp.append(das.readChar());

		}
		return temp.toString();
	}

	public static int leerCURSO(DataInputStream das) throws IOException {
		// TODO Auto-generated method stub
		return das.readInt();
	}

}
