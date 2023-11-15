package testEscribir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BfWritter {

	/*
	 * Escribir diez lineas en un fichero fila numero: x-------------10
	 */

	public static void main(String[] args) {

		File fichero = new File("D:\\fichero2.txt");

		BufferedWriter escritura;

		FileWriter escrituraFichero;

		try {

			escrituraFichero = new FileWriter(fichero);
			escritura = new BufferedWriter(escrituraFichero);

			escribir(escritura);
			
			escritura.close();
			escrituraFichero.close();
		
			
		

		} catch (IOException e) {
			System.err.println("ERROR DE ENTRADA SALIDA: ");
			e.printStackTrace();
		}
	}

	private static void escribir(BufferedWriter escritura) throws IOException {

		for (int i = 0; i < 10; i++) {

			escritura.write("FILA NUMERO: " + (i+1)+"\n");
			escritura.flush();

		}
		
		

	}

}
