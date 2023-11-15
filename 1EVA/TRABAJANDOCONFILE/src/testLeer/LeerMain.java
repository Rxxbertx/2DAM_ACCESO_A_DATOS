package testLeer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LeerMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File fichero = new File("D:\\fichero1.txt.txt");

		try {
			BufferedReader reader = new BufferedReader(new FileReader(fichero));

			String temp;

			try {
				while ((temp = reader.readLine()) != null) {

					System.out.println(temp);

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
