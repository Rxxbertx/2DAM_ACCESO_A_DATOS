package RANDOMACCESFILES;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class UtilidadesOperaciones {
	
	
	private static final int BYTES_DNI = 0;
	private static final int BYTES_CHAR = 0;

	static boolean eliminar(RandomAccessFile raf)  {
		
		 StringBuffer sb = new StringBuffer("000000000");
		 sb.setLength(9);
		 
		 System.out.println("DESEAS BORRAR EL ALUMNO? (1) SI (CUALQUIER TECLA) NO");
		 String opc = sc.next();
		 if (opc=="1") {
			
		}else {
			
			System.out.println("SE HA CANCELADO EL BORRADO");
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
	
	

}
