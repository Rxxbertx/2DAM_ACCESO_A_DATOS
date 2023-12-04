package trabajando_con_xml;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import objetos.Persona;

public class XmlConObjetos {

	public static void main(String[] args) {

		File fichero = new File("D:\\FichPersona.dat");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DOMImplementation DOMI = db.getDOMImplementation();
		Document doc = DOMI.createDocument(null, "Personas", null);
		doc.setXmlVersion("1.0");

		try {
			leer(fichero, doc);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		crearXML(doc);
		leerXML(db);
		
		

	}

	private static void leerXML(DocumentBuilder db) {
		
		// Cargar el documento con el método parse()
					Document document = null;
					try {
						document = db.parse(new File("D:\\Personas.xml"));
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// Se normaliza la estructura de árbol
					document.getDocumentElement().normalize();
					
					System.out.printf("Elemento raiz: %s %n", document.getDocumentElement().getNodeName());
					// Crear una lista con todos los nodos "empleado"  
					NodeList empleados = document.getElementsByTagName("persona");      
					System.out.printf("Nodos empleado a recorrer: %d %n", empleados.getLength());
					
					// Recorrer la lista  
					for (int i = 0; i < empleados.getLength(); i ++) {
						Node emple = empleados.item(i); // Obtener un nodo empleado
						if (emple.getNodeType() == Node.ELEMENT_NODE) { // Tipo de nodo "elemento"
							// Obtener los elementos del nodo           
							Element elemento = (Element) emple;	
							System.out.printf("* NOMBRE = %s %n", elemento.getElementsByTagName("nombre").item(0).getTextContent());
							System.out.printf(" * EDAD = %s %n", elemento.getElementsByTagName("edad"). item(0).getTextContent());
}
					}
		
		
		
	}

	private static void crearXML( Document doc) {
		
		Source s = new DOMSource(doc);
		Result r = new StreamResult(new File("D:\\Personas.xml"));
		Transformer transformer;
		try {

			transformer =  TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(s, r);

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private static void leer(File fichero, Document doc)
			throws FileNotFoundException, IOException, ClassNotFoundException {

		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero));

		Object obj;

		try {

			while (true) {

				if ((obj = ois.readObject()) instanceof Persona) {
System.out.println("1");
					escribirAXML((Persona) obj, doc);
				}

			}

		} catch (EOFException e) {

		}
		ois.close();

	}

	private static void escribirAXML(Persona obj, Document doc) {

		Element persona = doc.createElement("persona");
		Element nombre = doc.createElement("nombre");
		Element edad = doc.createElement("edad");

		nombre.appendChild(doc.createTextNode(obj.getNombre()));
		
		edad.appendChild(doc.createTextNode(String.valueOf(obj.getEdad())));
		
		persona.appendChild(edad);
		persona.appendChild(nombre);

		doc.getDocumentElement().appendChild(persona);
		
		
	}

}
