package test1;

import java.io.File;
import java.util.Iterator;
import java.util.Scanner;

public class Main1 {

	private static int opcion = 0;
	private static String directorioUsuario = "";
	static File directorio = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		menu();

		ejecucion();

		// programa java que use listFiles para mostrar la lista de un directorio
		// cualquiera o del actual

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
		try{opcion = Sc.nextInt();}catch(Exception e){};
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
