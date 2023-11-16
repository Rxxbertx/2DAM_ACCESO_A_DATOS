package RANDOMACCESFILES;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

public class EjercicioEmpleadosEmpresa {

	//un programa qeu reciba un id desde la linea de comandos y borre ese empleado
	//en el id escribo -1 y en el apellido el id que he borrado y dept y salario a 0
	// y otro programa que muestre los registros borrados
	
	static String apellido[] = { "FERNANDEZ", "GIL", "LOPEZ", "RAMOS", "SEVILLA", "CASTILLA", "REY" };
	static int departamento[] = { 10, 20, 10, 10, 30, 30, 20 };
	static double salario[] = { 1000.45, 2400.60, 3000, 1500.56, 2200, 1435.87, 2000 };
	static int id[] = { 1, 2, 3, 4, 5, 6, 7 };

	public static void main(String[] args) {

		// aleatorioemple.dat ingresar empleados, donde los datos son:
		/*
		 * APELLIDO{"FERNANDEZ","GIL","LOPEZ","RAMOS","SEVILLA","CASTILLA","REY",
		 * "ROBERTOO"} DEPARTAMENTO{10,20,10,10,30,30,20}
		 * SALARIO{1000.45,2400.60,3000,1500.56,2200,1435.87,2000,95600}
		 * 
		 * ademas para cada empleado se insertara un id mayor que 0 siendo la longitud
		 * del registro de 36bytes{int se almacena en 4bytes; apellido 20bytes; dep
		 * (int) 4bytes, salario(double)8
		 * 
		 * """"short son 2, long son 8, y byte1, float 4bytes, bit(1bit),
		 * 
		 * 
		 */

		File file = new File("D:\\AleatorioEmple.dat");

		try {
			modificar(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			leer(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void modificar(File file) throws IOException {
		
		//modificar el registro 4 4877  el dept 40
		
		int registro = 4;
		int inicio = (registro-1)*36;
		
		StringBuffer buffer = null;
		RandomAccessFile raf = new RandomAccessFile(file, "rw");

		
		raf.seek(inicio+20);
		raf.writeInt(40);//dept
		raf.writeDouble(4000.87);//salario
		raf.close();
		
		
		
		
	}

	private static void leer(File file) throws IOException {

		RandomAccessFile raf = new RandomAccessFile(file, "r");

		try {

			while (true) {

				StringBuilder sb = new StringBuilder();

				sb.append("Apellido: ");

				for (int i = 0; i < 10; i++) {
					sb.append(raf.readChar());
				}
				String temp = new String(sb).trim();

				sb = new StringBuilder();
				sb.append(temp);
				sb.append(" DEPARTAMENTO: ");
				sb.append(raf.readInt());
				sb.append(" SALARIO: ");
				sb.append(raf.readDouble());
				sb.append(" id: ");
				sb.append(raf.readInt());

				System.out.println(sb);

			}

		} catch (EOFException e) {

		}

	}

	private static void escribir(File file) throws IOException {

		StringBuffer buffer = null;
		RandomAccessFile raf = new RandomAccessFile(file, "rw");

		for (int i = 0; i < apellido.length; i++) {

			buffer = new StringBuffer(apellido[i]);
			buffer.setLength(10);
			raf.writeChars(buffer.toString());
			raf.writeInt(departamento[i]);
			raf.writeDouble(salario[i]);
			raf.writeInt(i + 1);

		}
		raf.close();
	}

	private static void escribirUno(File file) throws IOException {

		StringBuffer buffer = null;
		RandomAccessFile raf = new RandomAccessFile(file, "rw");

		raf.seek(raf.length());
		buffer = new StringBuffer("JOAO");
		buffer.setLength(10);
		raf.writeChars(buffer.toString());
		raf.writeInt(20);
		raf.writeDouble(3500);
		raf.writeInt((int) raf.length() / 36 + 1);

		raf.close();
	}

}
