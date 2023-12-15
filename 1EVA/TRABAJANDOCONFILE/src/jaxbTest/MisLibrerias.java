package jaxbTest;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MISLIBRERIAS")

public class MisLibrerias {

	ArrayList<Libreria> librerias;
	
	
	
	public MisLibrerias() {
		
		librerias = new ArrayList<Libreria>();
		
		
	}
	
	public void añadirLibreria(Libreria libreria) {
		
		librerias.add(libreria);
		
		
	};

	@XmlElement(name="Libreria")
	public ArrayList<Libreria> getLibrerias() {
		
		
		return librerias;
		
	};
	
}
