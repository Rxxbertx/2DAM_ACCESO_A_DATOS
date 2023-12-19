package CONVERSION_XSD_A_CLASES_JAVA;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class principal {

	public static void main(String[] args) {

		menu();

	}

	private static void menu() {
		Scanner sc = new Scanner(System.in);

		String opcion;

		do {
			System.out.println("---------MENU---------");
			System.out.println("1) Visualizar XML");
			System.out.println("2) Insertar venta");
			System.out.println("3) Modificar venta");
			System.out.println("4) Eliminar venta");
			System.out.println("5) Salir");
			System.out.println("----------------------");
			opcion = sc.nextLine();

			switch (opcion) {

			case "1":
				visualizarxml();
				break;

			case "2":

				System.err.println("ADVERTENCIA, INSERTE LOS DATOS BIEN");

				System.out.println("Inserta el número de venta: (TIPO DE DATO: NUMERICO)");

				String numeVenta = sc.nextLine();

				System.out.println("Inserta el nombre del cliente (TIPO DE DATO: CARACTER)");

				String nomCli = sc.nextLine();

				System.out.println("Inserta las unidades (TIPO DE DATO: NUMERICO)");

				String uni = sc.nextLine();

				System.out.println("Inserta la fecha (TIPO DE DATO: CARACTER, FORMATO: DD/MM/AA)");

				String fecha = sc.nextLine();

				insertarventa(Integer.valueOf(numeVenta), nomCli, Integer.valueOf(uni), fecha);
				break;

			case "3":

				/*
				 * Un método que reciba un número de venta y la borre, si existe, del documento
				 * XML. El método devolverá true si se ha borrado la venta, y false si no se ha
				 * borrado o ha ocurrido algún error.
				 */
				
				System.err.println("ADVERTENCIA, INSERTE LOS DATOS BIEN");

				System.out.println("Inserta el número de venta: (TIPO DE DATO: NUMERICO)");

				String numVenta = sc.nextLine();

				
				borrarVenta(numVenta);
				
				

				break;
			case "4":

				break;

			}

		} while (!opcion.equals("5"));
	}

	private static void borrarVenta(String numVenta) {
		
		System.out.println("--------------------------------");
		System.out.println("--------- ELIMINAR VENTA ---------");
		System.out.println("--------------------------------");
		
		
		
		
		
		
		
	}

	public static void visualizarxml() {

		System.out.println("--------------------------------");
		System.out.println("-------- VISUALIZAR XML --------");
		System.out.println("--------------------------------");
		try {
			// Para crear el contexto JAXB se pueden usar ambas formas
			// JAXBContext jaxbContext = JAXBContext.newInstance("clasesdatos");
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);

			// Crear un objeto de tipo Unmarshaller para convertir datos XML en un árbol de
			// objetos Java
			Unmarshaller u = jaxbContext.createUnmarshaller();

			// La clase JAXBElement representa a un elemento de un documento XML
			// En este caso a un elemento del documento ventasarticulos.xml
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal(new FileInputStream("./ventasarticulos.xml"));

			// Visualizar el documento
			Marshaller m = jaxbContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(jaxbElement, System.out);

			// Si se quiere operar con el documento hay que obtener los objetos del
			// JAXBElement
			// El método getValue() devuelve el modelo de contenido (ContentModel) y el
			// valor de los atributos del elemento
			VentasType miventa = (VentasType) jaxbElement.getValue();

			// Crear una instancia para obtener todas las ventas
			Ventas vent = miventa.getVentas();

			// Guardar las ventas en la lista
			List listaVentas = new ArrayList();
			listaVentas = vent.getVenta();

			System.out.println("--------------------------------");
			System.out.println("---- VISUALIZAR LOS OBJETOS ----");
			System.out.println("--------------------------------");

			// Cargar los datos del artículo
			DatosArtic miartic = (DatosArtic) miventa.getArticulo();
			System.out.println("Nombre del articulo: " + miartic.getDenominacion());
			System.out.println("Codigo del articulo: " + miartic.getCodigo());
			System.out.println("Stock del articulo : " + miartic.getStock());
			System.out.println("Precio del articulo: " + miartic.getPrecio());
			System.out.println("Ventas del articulo: " + listaVentas.size());

			// Visualizar las ventas del artículo
			for (int i = 0; i < listaVentas.size(); i++) {
				Ventas.Venta ve = (Ventas.Venta) listaVentas.get(i);
				System.out.println("Numero de venta: " + ve.getNumventa() + ". Nombre cliente: " + ve.getNombrecliente()
						+ ". Unidades: " + ve.getUnidades() + ". Fecha: " + ve.getFecha());
			}

		} catch (JAXBException je) {
			System.out.println(je.getCause());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}

	}

	private static void insertarventa(int numeventa, String nomcli, int uni, String fecha) {

		System.out.println("--------------------------------");
		System.out.println("--------- AÑADIR VENTA ---------");
		System.out.println("--------------------------------");
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller u = jaxbContext.createUnmarshaller();
			JAXBElement jaxbElement = (JAXBElement) u
					.unmarshal(new FileInputStream("NUEVODIR/xml/ventasarticulos.xml"));

			VentasType miventa = (VentasType) jaxbElement.getValue();

			// Crear una instancia para obtener todas las ventas
			Ventas vent = miventa.getVentas();

			// Guardar las ventas en la lista
			List listaVentas = new ArrayList();
			listaVentas = vent.getVenta();

			// Comprobar si existe el número de venta recorriendo el ArrayList
			int existe = 0; // Si no existe: 0, si existe: 1
			for (int i = 0; i < listaVentas.size(); i++) {
				Ventas.Venta ve = (Ventas.Venta) listaVentas.get(i);
				if (ve.getNumventa().intValue() == numeventa) {
					existe = 1;
					break;
				}
			}

			// Si el número de venta no existe, se añade la venta
			if (existe == 0) {
				// Crear el objeto Ventas.Venta, y añadirle los datos
				Ventas.Venta ve = new Ventas.Venta();
				ve.setNombrecliente(nomcli);
				ve.setFecha(fecha);
				ve.setUnidades(uni);
				BigInteger nume = BigInteger.valueOf(numeventa);
				ve.setNumventa(nume);

				// Añadir la venta a la lista
				listaVentas.add(ve);

				// Crear el Marshaller, y volcar la lista al fichero XML
				Marshaller m = jaxbContext.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.marshal(jaxbElement, new FileOutputStream("NUEVODIR/xml/ventasarticulos.xml"));
				System.out.println("Venta " + numeventa + " añadida");
			} else
				System.out.println("El número de venta " + numeventa + " ya existe");
		} catch (JAXBException je) {
			System.out.println(je.getCause());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}

	}

}
