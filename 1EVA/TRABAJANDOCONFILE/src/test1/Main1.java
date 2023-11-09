package test1;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class Main1 {

	private static int opcion = 0;
	private static String directorioUsuario = "";
	static File directorio = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//ejer1
		
		/*menu();

		ejecucion();*/

		//ejer2
		
		//ejer2();
		
		//ejer3
		ejer3();
		
		
		// programa java que use listFiles para mostrar la lista de un directorio
		// cualquiera o del actual

		// muestre todos los datos de un fichero; nombre ruta(ambas),es de lectura, es
		// de escritura, tamaño y si es un fichero, si es un directorio y el nombe del
		// directoiro padre

	}

	private static void ejer3() {
		
		
		File dir = new File(".","NUEVODIR");
	
		dir.mkdir();
		
		File Fichero1=new File(dir,"fichero1.txt");
		File Fichero2=new File(dir,"fichero2.txt");
		try {
			Fichero1.createNewFile();
			Fichero2.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Fichero2.exists())Fichero2.renameTo(new File(dir,"FICHERONUEVO.txt"));
		
		for (File fichero:dir.listFiles()){
			System.out.println(fichero);
		}
		
		
		
	}

	private static void ejer2() {
		
		String file = ".classpath";
		
		File fichero = new File(file);
		
		if(fichero.exists()) {
			
			System.out.println("FICHERO: "+fichero.getName());
			System.out.println("ruta relativa: "+fichero.getPath());
			System.out.println("ruta absoluta: "+fichero.getAbsolutePath());
			System.out.println("lectura: "+fichero.canRead());
			System.out.println("escritura: "+fichero.canWrite());
			System.out.println("tamaño(bytes): "+(float)(fichero.getUsableSpace()/1024/1024));
			System.out.println("fichero: "+fichero.isFile());
			System.out.println("directorio: "+fichero.isDirectory());
			System.out.println("nombre padre: "+fichero.getParent());

		}
		
		
		
		
		
	}

	private static void ejecucion() {

		if (directorio != null && directorio.exists()) {

			for (File fileArray : directorio.listFiles()) {

				System.out.println(fileArray + " " + (fileArray.isFile() ? "(FICHERO)" : "(DIRECTORIO)"));

				if (fileArray.list() != null) {

					for (String string : fileArray.list()) {
						System.out.println("\t" + string + " " + (fileArray.isFile() ? "(FICHERO)" : "(DIRECTORIO)"));
					}

				}

			}

		}

	}

	private static void menu() {

		Scanner Sc = new Scanner(System.in);
		System.out.println("1-Directorio Actual\n2-Directorio Especifico");
		try {
			opcion = Sc.nextInt();
		} catch (Exception e) {
		}
		;
		Sc.nextLine();

		switch (opcion) {
		case 1:

			directorio = new File(".");

			break;
		case 2:

			System.out.println("Introduce el directorio puedes hacer tanto rutas absolutas como relativas");

			directorio = new File(Sc.nextLine());

			break;

		default:

			break;
		}
		Sc.close();

	}

}
