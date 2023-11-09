package testEscribir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;

public class EscribirFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		 * Crear un fichero de texto con contenido de nombre "fichero1.txt", meter
		 * contenido y crear un programa java para mostrar el contenido por consola.
		 * 
		 * escribir desde 0, UNA LINEA POR CADA NOMBRE DE LAS PROVINCIAS DE CYL
		 * 
		 */
		
		
		String[] cyl = {"VALLADOLID","BURGOS","SALAMANCA","SEGOVIA","ZAMORA","PALENCIA","LEON","SORIA","AVILA"};

		Arrays.sort(cyl);
		
		
		
		FileReader lectura = null;
		File file = new File("D:\\fichero1.txt.txt");
		
		FileWriter wrt = null;
		try {
			
			wrt = new FileWriter(file);
			for (int i = 0; i < cyl.length; i++) {
				wrt.write(cyl[i]+"\n");
				
			
				
			}
			
		
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if (wrt!=null) {
				try {
					wrt.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		try {
			lectura = new FileReader(file);
			
			String rd="";
			try {
				
				int temp=0;
				
				while(temp!=-1) {
					
					temp=lectura.read();
					rd+=(char)temp;
					
			
				};
				
				System.out.println(rd);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			if (lectura!=null) {
				try {
					lectura.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
					
		
		
		

	}

}
