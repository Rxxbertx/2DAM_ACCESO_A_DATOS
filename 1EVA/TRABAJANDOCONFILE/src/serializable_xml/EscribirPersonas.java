package serializable_xml;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.thoughtworks.xstream.XStream;

import objetos.Persona;

public class EscribirPersonas {
	public static void main(String[] args) throws IOException, ClassNotFoundException {   	
		File fichero = new File("D:\\FichPersona.dat");
		FileInputStream filein = new FileInputStream(fichero); // Flujo de entrada   
		// Conectar el flujo de bytes al flujo de datos
		ObjectInputStream dataIS = new ObjectInputStream(filein);      
		System.out.println("Comienza el proceso de creación del fichero XML...");
				
		// Crear un objeto Lista de Personas
		ListaPersonas listaPersonas = new ListaPersonas();	 
     
		try {
			while (true) { // Leer el fichero
				// Leer una Persona
				Persona persona= (Persona) dataIS.readObject();    
				listaPersonas.add(persona); // A�adir persona a la lista  
			}	
		}
		catch (EOFException eo) {
		}
		dataIS.close(); // Cerrar el Stream de entrada     
    
		try {
			XStream xstream = new XStream();   
			// Cambiar de nombre a las etiquetas XML
			xstream.alias("ListaPersonasMunicipio", ListaPersonas.class);	
			xstream.alias("DatosPersona", Persona.class);
		
			//xstream.aliasField("Nombre alumno", Persona.class, "nombre");
			//xstream.aliasField("Edad alumno", Persona.class, "edad");
		
			// Quitar la etiqueta lista (atributo de la clase ListaPersonas)
			xstream.addImplicitCollection(ListaPersonas.class, "lista");
			// Insertar los objetos en el XML
			xstream.toXML(listaPersonas, new FileOutputStream("Personas.xml"));	
			System.out.println("Se ha creado el fichero XML...");
		}
		catch (Exception e) {
			e.printStackTrace();
		}	    
	} // Fin de main
} // Fin EscribirPersonas
