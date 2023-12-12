package trabajando_con_xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class SAX_empleado_xml {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, FileNotFoundException, IOException {
		
	
		
		File fichero = new File("D:\\Empleados.xml");
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();
		
		xr.setContentHandler(new HandlerSAX());
	
		xr.parse(new InputSource(new FileInputStream(fichero)));
		
		
		
		
		//CREAR UNA LISTA DE PERSOANA Y VOLCARLO EN XML, PARA ELLO NECESTIAMOS LA CLASE PERSONA
		//UNA NUEVA CLASE LISTA PERSONAS
		
		

	}

}
