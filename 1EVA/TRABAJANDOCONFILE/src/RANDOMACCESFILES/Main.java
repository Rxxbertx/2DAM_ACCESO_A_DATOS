package RANDOMACCESFILES;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	private static final int BYTES_DNI = 18;
	private static final int BYTES_CHAR = 2;
	private static final int BYTES_REGISTRO = 0;
	static RandomAccessFile raf;
	static Scanner sc = new Scanner(System.in);
	static File FICHERO = new File("FICHERO");

	
	
	static void consulta() throws FileNotFoundException {
		
		
		
		boolean salir = false;
		boolean encontrado = false;
		String dni = null;
		
		
		
		while (!salir) {
			
			System.out.println("Que deseas hacer? (1)Consultar Todo (2)Consultar Alumno Especifico");
			
			switch (sc.nextInt()) {
			case 1:
				
				consultaGeneral();
				
				break;
			case 2:
				
				consultaEspecifica();
				
				break;

			default:
				break;
			}
			
			
		}
		
		
		
	}
	

	
	private static void consultaEspecifica() throws FileNotFoundException {
		
		raf = new RandomAccessFile(new File(""), "r");
		
	}

	private static void consultaGeneral() throws FileNotFoundException {
		
		DataInputStream dis = new DataInputStream(new FileInputStream(FICHERO));
		StringBuffer usuarios = new StringBuffer();
		
		while (true) {
			
			StringBuffer temp = new StringBuffer();
			
	
		}
			
	}


	static void baja() throws IOException {

		raf = new RandomAccessFile(new File(""), "rw");
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
				}else {
					System.out.println("NO SE HA BORRADO");
					
				}

			}

		}

	}

}
