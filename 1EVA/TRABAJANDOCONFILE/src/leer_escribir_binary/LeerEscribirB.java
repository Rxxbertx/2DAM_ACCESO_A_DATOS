package leer_escribir_binary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class LeerEscribirB {

	
	public static void main(String[] args) {
		
		FileInputStream lectura = null;
		FileOutputStream escritura = null;
		File fichero = new File("D:\\FichBytes.dat");
		
		
		try {
			escribir(escritura,fichero);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR EN ESCRITURA");
		}
		try {
			leer(lectura,fichero);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR EN LECTURA");
		}

		
	}

	private static void leer(FileInputStream lectura, File fichero) throws IOException {
		lectura = new FileInputStream(fichero);
		
		int i;
		
		while ((i=lectura.read())!=-1) {
		
			System.err.println("NUMERO LEIDO: "+i);
		}
		
		lectura.close();
		
	}

	private static void escribir(FileOutputStream escritura, File fichero) throws IOException {
		
		escritura = new FileOutputStream(fichero);
		
		
		
		
		for (int i = 0; i <= 400; i++) {
			escritura.write((byte)i);
		}
		escritura.close();

	}
	
}
