package practica1;

import java.util.Scanner;

public final class Constantes {

	static Scanner sc = new Scanner(System.in);
	static final int ALTA = 1;
	static final int BAJA = 2;
	static final int MODIFICAR = 3;
	static final int CONSULTA = 4;
	
	static final int DNI=1;
	static final int NOMBRE=2;
	static final int APELLIDOS=3;
	static final int CICLO=4;
	static final int CURSO=5;
	
	static final int BYTES_CHAR = 2;
	static final int BYTES_INT = 4;
	
	
	static final int BYTES_NOMBRE = 40;
	static final int BYTES_APELLIDO = 60;
	static final int BYTES_DNI = 18;
	static final int BYTES_CICLO = 10;
	static final int BYTES_CURSO = 4;
	static final int BYTES_REGISTRO = BYTES_APELLIDO + BYTES_CICLO + BYTES_CURSO + BYTES_NOMBRE + BYTES_DNI;
}
