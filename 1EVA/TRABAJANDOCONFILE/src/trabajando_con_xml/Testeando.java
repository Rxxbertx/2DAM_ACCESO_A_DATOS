package trabajando_con_xml;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Testeando {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		DOMImplementation implementation = db.getDOMImplementation();
		
		Document d = db.parse("D:\\Empleados.xml");
		Element raiz = d.getDocumentElement();
		
		NodeList noditos = raiz.getElementsByTagName("empleado");
		
		for (int i = 0; i < noditos.getLength(); i++) {
			
			Node nodo = noditos.item(i);
			
			Element id = (Element) nodo;
			Element apellido = (Element) nodo;
			
			System.out.println("ID: "+id.getElementsByTagName("id").item(0).getTextContent());
			System.out.println("APELLIDOS: "+id.getElementsByTagName("apellidos").item(0).getTextContent());
			
		}
		
	}

}
