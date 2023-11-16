package objetos;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LeerEscribirObjetos {

	public static void main(String[] args) {

		/*
		 * ingresar 10 nombres y edad, FichPersona.dat y lo leea y lo muestre por
		 * pantalla
		 */

		File fichero = new File("D:\\FichPersona.dat");

		try {
			escribir(fichero);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			leer(fichero);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void leer(File fichero) throws FileNotFoundException, IOException, ClassNotFoundException {

		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero));

		Object obj;

		try {

			while (true) {

				if ((obj = ois.readObject()) instanceof Persona) {

					System.out.println(
							"NOMBRE: " + ((Persona) (obj)).getNombre() + " EDAD: " + ((Persona) (obj)).getEdad());
				}

			}

		} catch (IOException e) {
			
		}
		ois.close();

	}

	private static void escribir(File fichero) throws FileNotFoundException, IOException {

		ObjectOutputStream oos = new MyObjectOutputStream(new FileOutputStream(fichero,true));

		
		
		for (int i = 1; i <= 10; i++) {

			oos.writeObject(new Persona("NOMBRE" + i, (int) (i * (Math.random() * 10)+2)));
			
			oos.flush();

		}
		oos.close();

	}

}