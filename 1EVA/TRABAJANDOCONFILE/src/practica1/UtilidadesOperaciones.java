package practica1;

import java.io.IOException;
import static practica1.Constantes.*;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UtilidadesOperaciones {

	static boolean eliminar(RandomAccessFile raf) {

		StringBuffer sb = new StringBuffer("000000000");
		sb.setLength(9);

		System.out.println("DESEAS BORRAR EL ALUMNO? (1) SI (CUALQUIER TECLA) NO");
		String opc = sc.nextLine();
		if (opc.equals("1")) {

		} else {

			return false;
		}

		try {
			raf.writeChars(sb.toString());
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR EN BORRADO");
		}

		return false;

	}

	static boolean validarDNI(String dni) {
		String patronDNI = "\\d{8}[A-HJ-NP-TV-Z]";
		Pattern pattern = Pattern.compile(patronDNI);
		Matcher matcher = pattern.matcher(dni);

		return matcher.matches();
	}

	static boolean validarNOMBRE(String nombre) {
		return nombre.matches("^[^\\d]{1,20}$");
	}

	static boolean validarAPELLIDOS(String apellidos) {

		return apellidos.matches("^[^\\d]{1,30}$");
	}

	static boolean validarCICLO(String ciclo) {

		return (ciclo.matches("^[\\D]{1,5}$"));

	}

	static boolean validarCURSO(String cursoString) {

		int curso = 0;

		try {
			curso = Integer.parseInt(cursoString);
		} catch (NumberFormatException nfe) {
			return false;
		}

		return !(curso <= 0 || curso > 10);

	}

	static String componerDni(RandomAccessFile raf) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < BYTES_DNI / BYTES_CHAR; i++) {
			try {
				sb.append(raf.readChar());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("ERROR AL BUSCAR EL DNI");
			}
		}
		return new String(sb).trim();
	}

	public static String leerAlumno(RandomAccessFile raf) {

		try {

			StringBuilder temp = new StringBuilder();

			for (int i = 0; i < BYTES_DNI / BYTES_CHAR; i++) {

				temp.append(raf.readChar());

			}

			String dni = new String("DNI: " + temp).trim();
			temp.delete(0, temp.length());

			for (int i = 0; i < BYTES_NOMBRE / BYTES_CHAR; i++) {

				temp.append(raf.readChar());

			}
			temp.append("\nAPELLIDOS: ");
			for (int i = 0; i < BYTES_APELLIDO / BYTES_CHAR; i++) {

				temp.append(raf.readChar());

			}
			temp.append("\nCICLO: ");
			for (int i = 0; i < BYTES_CICLO / BYTES_CHAR; i++) {

				temp.append(raf.readChar());

			}
			temp.append("\nCURSO: ");
			temp.append(raf.readInt());

			temp.append("\n");

			return temp.toString();

		} catch (IOException e) {
			return "";
		}

	}

	public static boolean preguntaModificacion() {

		System.out.println("--MODIFICAR EL ALUMNO?--\n(1) SI\n(CUALQUIER TECLA) NO");
		String opc = sc.nextLine();
		if (opc.equals("1")) {
			return true;
		} else {
			System.out.println("MODIFICACION CANCELADA");
			return false;
		}

	}

	public static boolean confirmacionCreacion() {

		System.out.println("--DESEAS CREAR EL ALUMNO?--\n(1) SI\n(CUALQUIER TECLA) NO");
		String opc = sc.nextLine();
		if (opc.equals("1")) {
			return true;
		} else {

			return false;
		}

	}

	public static int opcionesModificar() {

		boolean salir = false;

		while (!salir) {

			System.out.println(
					"------MODIFICACION------\n(1)DNI\n(2)NOMBRE\n(3)APELLIDOS\n(4)CICLO\n(5)CURSO\n------------------------\n(CUALQUIER TECLA)SALIR");
			String opc = sc.nextLine();

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
		return 0;

	}

	public static String modificarDNI(String dni) {
		System.out.println("INTRODUCE EL DNI NUEVO\nACTUAL: " + dni);
		String temp = sc.nextLine();
		if (UtilidadesOperaciones.validarDNI(temp)) {
			System.out.println(
					"---ESTAS SEGURO DE REEMPLAZAR EL DNI ACTUAL POR EL NUEVO?---\n(1) SI\n(CUALQUIER TECLA) NO\n"
							+ "\nNUEVO: " + temp + " ACTUAL: " + dni);
			if (sc.nextLine().equals("1")) {

				System.out.println("MODIFICACION DNI ACEPTADA");
				return temp;

			} else {
				System.err.println("MODIFICACION DNI CANCELADA");
			}
		} else {
			System.err.println("DNI INCORRECTO, MODIFICACION CANCELADA");
		}
		return dni;

	}

	public static String modificarNOMBRE(String nombre) {
		System.out.println("INTRODUCE EL NOMBRE NUEVO\nACTUAL: " + nombre);
		String temp = sc.nextLine();
		if (UtilidadesOperaciones.validarNOMBRE(temp)) {
			System.out.println(
					"---ESTAS SEGURO DE REEMPLAZAR EL NOMBRE ACTUAL POR EL NUEVO?---\n(1) SI\n(CUALQUIER TECLA) NO\n"
							+ "\nNUEVO: " + temp + " ACTUAL: " + nombre);
			if (sc.nextLine().equals("1")) {

				System.out.println("MODIFICACION NOMBRE ACEPTADA");
				return temp;

			} else {
				System.err.println("MODIFICACION NOMBRE CANCELADA");
			}
		} else {
			System.err.println("NOMBRE INCORRECTO, MODIFICACION CANCELADA");
		}
		return nombre;
	}

	public static String modificarAPELLIDOS(String apellidos) {
		System.out.println("INTRODUCE LOS APELLIDOS NUEVOS\nACTUAL: " + apellidos);
		String temp = sc.nextLine();
		if (UtilidadesOperaciones.validarAPELLIDOS(temp)) {
			System.out.println(
					"---ESTAS SEGURO DE REEMPLAZAR LOS APELLIDOS ACTUALES POR LOS NUEVOS?---\n(1) SI\n(CUALQUIER TECLA) NO\n"
							+ "\nNUEVO: " + temp + " ACTUAL: " + apellidos);
			if (sc.nextLine().equals("1")) {

				System.out.println("MODIFICACION APELLIDOS ACEPTADA");
				return temp;

			} else {
				System.err.println("MODIFICACION APELLIDOS CANCELADA");
			}
		} else {
			System.err.println("APELLIDOS INCORRECTOS, MODIFICACION CANCELADA");
		}
		return apellidos;
	}

	public static String modificarCICLO(String ciclo) {
		
		System.out.println("INTRODUCE EL CICLO NUEVO\nACTUAL: " + ciclo);
		String temp = sc.nextLine();
		if (UtilidadesOperaciones.validarCICLO(temp)) {
			System.out.println(
					"---ESTAS SEGURO DE REEMPLAZAR EL CICLO ACTUAL POR EL NUEVO?---\n(1) SI\n(CUALQUIER TECLA) NO\n"
							+ "\nNUEVO: " + temp + " ACTUAL: " + ciclo);
			if (sc.nextLine().equals("1")) {

				System.out.println("MODIFICACION CICLO ACEPTADA");
				return temp;

			} else {
				System.err.println("MODIFICACION CICLO CANCELADA");
			}
		} else {
			System.err.println("CICLO INCORRECTO, MODIFICACION CANCELADA");
		}
		return ciclo;
		
	}

	public static int modificarCURSO(int curso) {
		System.out.println("INTRODUCE EL CURSO NUEVO\nACTUAL: " + curso);
		String temp = sc.nextLine();
		
		if (UtilidadesOperaciones.validarCURSO(temp)) {
			System.out.println(
					"---ESTAS SEGURO DE REEMPLAZAR EL CICLO ACTUAL POR EL NUEVO?---\n(1) SI\n(CUALQUIER TECLA) NO\n"
							+ "\nNUEVO: " + temp + " ACTUAL: " + curso);
			if (sc.nextLine().equals("1")) {

				System.out.println("MODIFICACION CURSO ACEPTADA");
				return Integer.parseInt(temp);

			} else {
				System.err.println("MODIFICACION CURSO CANCELADA");
			}
		} else {
			System.err.println("CURSO INCORRECTO, MODIFICACION CANCELADA");
		}
		return curso;

	}

}
