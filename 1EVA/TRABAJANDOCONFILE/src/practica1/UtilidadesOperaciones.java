package practica1;

import java.io.IOException;
import static practica1.Constantes.*;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class UtilidadesOperaciones {
	
	

	static boolean eliminar(RandomAccessFile raf)  {
		
		 StringBuffer sb = new StringBuffer("000000000");
		 sb.setLength(9);
		 
		 System.out.println("DESEAS BORRAR EL ALUMNO? (1) SI (CUALQUIER TECLA) NO");
		 String opc = sc.nextLine();
		 if (opc.equals("1")) {
			
		}else {
			
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
	
	 static String componerDni(RandomAccessFile raf) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < BYTES_DNI/BYTES_CHAR; i++) {
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

		temp.append("DNI: ");
		for (int i = 0; i < BYTES_DNI / BYTES_CHAR; i++) {

			temp.append(raf.readChar());

		}

		temp.append("\nNOMBRE: ");
		for (int i = 0; i < BYTES_NOMBRE / BYTES_CHAR; i++) {

			temp.append(raf.readChar());

		}
		temp.append("\nAPELLIDO: ");
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
		
		}catch(IOException e) {
			return "";
		}
		
	}

	public static boolean preguntaModificacion() {
		
		System.out.println("MODIFICAR EL ALUMNO? (1) SI (CUALQUIER TECLA) NO");
		 String opc = sc.nextLine();
		 if (opc.equals("1")) {
			return true;
		}else {
			
			return false;
		}
		
	}

	public static boolean confirmacionCreacion() {
		
		System.out.println("DESEAS CREAR EL ALUMNO? (1) SI (CUALQUIER TECLA) NO");
		 String opc = sc.nextLine();
		 if (opc.equals("1")) {
			return true;
		}else {
			
			return false;
		}
		
	}

	public static int opcionesModificar() {
		
		boolean salir=false;
		
		while (!salir) {
			
			System.out.println("------MODIFICACION------\n(1)DNI\n(2)NOMBRE\n(3)APELLIDOS\n(4)CICLO\n(5)CURSO\n------------------------\n(CUALQUIER TECLA)SALIR");
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
			case "6":
				salir=true;
				break;
			default:
				System.err.println("OPCION INCORRECTA");
				break;
			}
			 
			
		}
		return 0;
		
		
	}
	
	

}
