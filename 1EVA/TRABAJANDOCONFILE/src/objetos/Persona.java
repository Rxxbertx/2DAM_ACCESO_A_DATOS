package objetos;

import java.io.Serializable;

public class Persona  implements Serializable{

	/**
	 * 
	 */

	String nombre;
	int edad;
	
	public Persona(String nombre, int edad) {
		
		this.edad=edad;
		this.nombre=nombre;
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	

}
