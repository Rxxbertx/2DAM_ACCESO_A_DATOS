package trabajando_con_xml;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
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
import org.w3c.dom.Element;

public class CrearEmpleadoXml {

	static Document document;

	public static void main(String[] args) throws FileNotFoundException, ParserConfigurationException {

		File fichero = new File("D:\\AleatorioEmple.dat");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder = factory.newDocumentBuilder();

		DOMImplementation implementation = builder.getDOMImplementation();
		document = implementation.createDocument(null, "Empleados", null);

		document.setXmlVersion("1.0");

		try {
			leer(fichero);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CrearXML();

	}

	private static void CrearXML() {

		Source source = new DOMSource(document);
		Result result = new StreamResult(new File("D:\\Empleados.xml"));
		Transformer transformer;
		try {

			transformer =  TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);

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

	private static void leer(File file) throws IOException {

		RandomAccessFile raf = new RandomAccessFile(file, "r");

		try {

			while (true) {

				int id;
				double salario;
				int dept;
				String apellido;

				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < 10; i++) {
					sb.append(raf.readChar());
				}
				apellido = new String(sb).trim();

				dept = raf.readInt();

				salario = raf.readDouble();

				id = raf.readInt();

				añadirXML(id, salario, dept, apellido);

			}

		} catch (EOFException e) {

		}

	}

	private static void añadirXML(int id, double salario, int dept, String apellido) {

		Element raiz = document.createElement("empleado");

		document.getDocumentElement().appendChild(raiz);

		crearElemento("id", String.valueOf(id), raiz, document);
		crearElemento("apellidos", apellido, raiz, document);
		crearElemento("departamento", String.valueOf(dept), raiz, document);
		crearElemento("salario", String.valueOf(salario), raiz, document);

	}

	private static void crearElemento(String etiqueta, String valor, Element raiz, Document document) {

		Element aux = document.createElement(etiqueta);
		aux.appendChild(document.createTextNode(valor));
		raiz.appendChild(aux);

	}

}
