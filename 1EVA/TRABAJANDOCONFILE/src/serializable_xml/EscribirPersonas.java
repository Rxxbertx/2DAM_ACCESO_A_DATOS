package serializable_xml;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.thoughtworks.xstream.XStream;

import objetos.Persona;

public class EscribirPersonas {
	
	public static void main(String[] args){   	
		File fichero = new File("D:\\FichPersona.dat");
		FileInputStream filein = null;
		ObjectInputStream dataIS = null;
		// Flujo de entrada  
		
		try {
			filein = new FileInputStream(fichero);
			dataIS = new ObjectInputStream(filein);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
  
		System.out.println("Comienza el proceso de creación del fichero XML...");
				
		// Crear un objeto Lista de Personas
		ListaPersonas listaPersonas = new ListaPersonas();	 
     
		
		XStream xstream = new XStream();
		xstream.alias("ListaPersonasMunicipio", ListaPersonas.class);
		xstream.alias("DatosPersona", Persona.class);
		xstream.addImplicitCollection(ListaPersonas.class, "lista");


		while (true) { // Leer el fichero
			// Leer una Persona
			Persona persona;
			try {
				
				persona = (Persona) dataIS.readObject();
				listaPersonas.add(persona); // A�adir persona a la lista  
				
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
					try {
						dataIS.close();
						break;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // Cerrar el Stream de entrada    
			
			}
			
		} 
			  
			
			// Quitar la etiqueta lista (atributo de la clase ListaPersonas)
			xstream.addImplicitCollection(ListaPersonas.class, "lista");
			// Insertar los objetos en el XML
			try {
				xstream.toXML(listaPersonas, new FileOutputStream("Personas.xml"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			System.out.println("Se ha creado el fichero XML...");

    
	} // Fin de main
}
// Fin EscribirPersonas
