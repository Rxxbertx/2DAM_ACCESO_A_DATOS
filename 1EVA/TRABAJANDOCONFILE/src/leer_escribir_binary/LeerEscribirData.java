package leer_escribir_binary;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LeerEscribirData {

	/*
	 * En el fichero fichdata.dat escriba los nombres ANA, LUIS, ALICIA, PEDRO,
	 * MANUEL, ANDRES, JULIO, ANTONIO, MARIA, JESUS EDADES:
	 * 14,15,13,15,16,12,16,14,13,15
	 */

	public static HashMap<String, Integer> alumnos = new HashMap();

	public static void main(String[] args) {

		File fichero = new File("D:\\FichData.dat");

		alumnos.put("ANA", 14);
		alumnos.put("LUIS", 15);
		alumnos.put("ALICIA", 13);
		alumnos.put("PEDRO", 15);
		alumnos.put("MANUEL", 16);
		alumnos.put("ANDRES", 12);
		alumnos.put("JULIO", 16);
		alumnos.put("ANTONIO", 14);
		alumnos.put("MARIA", 13);
		alumnos.put("JESUS", 15);

		try {
			escribir(fichero);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			leer(fichero);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void escribir(File fic) throws IOException {

		DataOutputStream dos = new DataOutputStream(new FileOutputStream(fic));

		for (Map.Entry<String, Integer> entry : alumnos.entrySet()) {

			dos.writeUTF(entry.getKey());
			dos.writeInt(entry.getValue());
			dos.flush();

		}

		dos.close();

	}

	private static void leer(File fic) throws IOException {

		DataInputStream dos = new DataInputStream(new FileInputStream(fic));

		String nombre = "";
		int edad = -1;

		try {

			while (true) {
				
				nombre = dos.readUTF();
				edad = dos.readInt();
				System.out.println("NOMBRE: " + nombre + " EDAD: " + edad);

			}

		} catch (EOFException e) {
			dos.close();
		}

	}

}
