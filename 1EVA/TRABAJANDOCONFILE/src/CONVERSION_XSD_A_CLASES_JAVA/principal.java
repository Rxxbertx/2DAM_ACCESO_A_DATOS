package CONVERSION_XSD_A_CLASES_JAVA;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
			System.out.println("4) Aumentar Stock");
			System.out.println("5) Eliminar venta");
			System.out.println("6) Salir");
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

				if (modificarVenta(sc))
					System.out.println("VENTA MODIFICADO EXITOSAMENTE");

				
				
				break;

			case "4":

				/*
				 * Un método para modificar el stock del artículo. El método recibe una cantidad
				 * numérica que se debe sumar al stock del artículo. Devolverá true si la
				 * operación se realiza correctamente, y false si ocurre algún error.
				 */

				if (modificarStock(sc))
					System.out.println("STOCK MODIFICADO EXITOSAMENTE");

				break;
			case "5":

				/*
				 * Un método que reciba un número de venta y la borre, si existe, del documento
				 * XML. El método devolverá true si se ha borrado la venta, y false si no se ha
				 * borrado o ha ocurrido algún error.
				 */

				if (borrarVenta(sc))
					System.out.println("VENTA BORRADA EXITOSAMENTE");

				break;

			}

		} while (!opcion.equals("6"));
	}

	private static boolean modificarVenta(Scanner sc) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean modificarStock(Scanner sc) {

		System.out.println("--------------------------------");
		System.out.println("--------- MODIFICAR STOCK ---------");
		System.out.println("--------------------------------");

		try {

			JAXBContext contexto = obtenerContexto();

			Unmarshaller u = crearUnmarshaller(contexto);

			JAXBElement jaxbElement = obtenerElementoJAXB(u);

			VentasType vt = obtenerModeloVentas(jaxbElement);

			mostrarArticulo(vt, vt.getVentas().getVenta());

			System.err.println("ADVERTENCIA, INSERTE LOS DATOS BIEN");

			System.out.println("Inserta la cantidad para agregar al stock: (TIPO DE DATO: NUMERICO)");

			int i = -123456789;

			try {
				i = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.err.println("ERROR INGRESA UN NUMERO CORRECTO");
				return false;
			}

			if (i != -123456789 && i > 0) {

				vt.getArticulo().setStock(BigInteger.valueOf(vt.getArticulo().getStock().longValue() + i));
				actualizarXml(contexto, jaxbElement);
				return true;

			} else {
				System.err.println("INTRODUCE UN NUMERO CORRECTO POR FAVOR");
				return false;
			}

		} catch (JAXBException e) {

			System.err.println("ERROR AL AÑADIR STOCK AL ARTICULO :JAXB");
			e.printStackTrace();
		} catch (FileNotFoundException e) {

			System.err.println("ERROR FICHERO NO ACCESIBLE");
			e.printStackTrace();
		}

		return false;

	}

	private static boolean borrarVenta(Scanner sc) {

		System.out.println("--------------------------------");
		System.out.println("--------- ELIMINAR VENTA ---------");
		System.out.println("--------------------------------");

		try {

			JAXBContext context = obtenerContexto();
			Unmarshaller u = crearUnmarshaller(context);
			JAXBElement jaxbElement = obtenerElementoJAXB(u);

			// contiene objeto ventas
			VentasType modeloVentas = obtenerModeloVentas(jaxbElement);

			// contiene las ventas generales
			Ventas ventas = modeloVentas.getVentas();

			// contiene las ventas de un articulo
			List<Ventas.Venta> ventasDeVenta = ventas.getVenta();

			mostrarArticulo(modeloVentas, ventasDeVenta);
			visualizarVentasArticulo(ventasDeVenta);

			System.err.println("ADVERTENCIA, INSERTE LOS DATOS BIEN");

			System.out.println("Inserta el número de venta: (TIPO DE DATO: NUMERICO)");

			String numVenta = sc.nextLine();

			int i = verificarVenta(Integer.valueOf(numVenta), ventasDeVenta);

			if (i != -1) {
				ventasDeVenta.remove(i);
			} else {
				System.err.println("VENTA INEXISTENTE :(, PRUEBA CON OTRO NUMERO DE VENTA");
				return false;
			}

			try {

				actualizarXml(context, jaxbElement);
				return true;

			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (JAXBException e) {

			System.err.println("ERROR AL ELIMINAR EL ARTICULO :JAXB");
			e.printStackTrace();
		} catch (FileNotFoundException e) {

			System.err.println("ERROR FICHERO NO ACCESIBLE");
			e.printStackTrace();
		} catch (NumberFormatException e) {

			System.err.println("ERROR INTRODUZCA UN NUMERO CORRECTO");

		}

		return false;

	}

	private static int verificarVenta(int numeventa, List<Ventas.Venta> listaVentas) {
		for (Ventas.Venta venta : listaVentas) {
			if (numeventa == venta.getNumventa().intValue()) {
				return listaVentas.indexOf(venta);
			}
		}
		return -1;
	}

	private static VentasType obtenerModeloVentas(JAXBElement jaxbElement) {
		return (VentasType) jaxbElement.getValue();
	}

	private static JAXBElement obtenerElementoJAXB(Unmarshaller u) throws JAXBException, FileNotFoundException {
		return (JAXBElement) u.unmarshal(new FileInputStream("ventasarticulos.xml"));
	}

	private static Unmarshaller crearUnmarshaller(JAXBContext context) throws JAXBException {
		return context.createUnmarshaller();
	}

	private static JAXBContext obtenerContexto() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
		return context;
	}

	public static void visualizarxml() {

		System.out.println("--------------------------------");
		System.out.println("-------- VISUALIZAR XML --------");
		System.out.println("--------------------------------");
		try {
			JAXBContext jaxbContext = obtenerContexto();

			// Crear un objeto de tipo Unmarshaller para convertir datos XML en un árbol de
			// objetos Java
			Unmarshaller u = jaxbContext.createUnmarshaller();

			// La clase JAXBElement representa a un elemento de un documento XML
			// En este caso a un elemento del documento ventasarticulos.xml
			JAXBElement jaxbElement = obtenerElementoJAXB(u);

			// Visualizar el documento
			Marshaller m = jaxbContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(jaxbElement, System.out);

			// Si se quiere operar con el documento hay que obtener los objetos del
			// JAXBElement
			// El método getValue() devuelve el modelo de contenido (ContentModel) y el
			// valor de los atributos del elemento
			VentasType miventa = obtenerModeloVentas(jaxbElement);

			// Crear una instancia para obtener todas las ventas
			Ventas vent = miventa.getVentas();

			// Guardar las ventas en la lista
			List listaVentas = new ArrayList();
			listaVentas = vent.getVenta();

			System.out.println("--------------------------------");
			System.out.println("---- VISUALIZAR LOS OBJETOS ----");
			System.out.println("--------------------------------");

			mostrarArticulo(miventa, listaVentas);

			visualizarVentasArticulo(listaVentas);

		} catch (JAXBException je) {
			System.out.println(je.getCause());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}

	}

	private static void mostrarArticulo(VentasType miventa, List listaVentas) {
		// Cargar los datos del artículo
		DatosArtic miartic = (DatosArtic) miventa.getArticulo();
		System.out.println("Nombre del articulo: " + miartic.getDenominacion());
		System.out.println("Codigo del articulo: " + miartic.getCodigo());
		System.out.println("Stock del articulo : " + miartic.getStock());
		System.out.println("Precio del articulo: " + miartic.getPrecio());
		System.out.println("Ventas del articulo: " + listaVentas.size());
	}

	private static void visualizarVentasArticulo(List listaVentas) {
		// Visualizar las ventas del artículo
		for (int i = 0; i < listaVentas.size(); i++) {
			Ventas.Venta ve = (Ventas.Venta) listaVentas.get(i);
			System.out.println("Numero de venta: " + ve.getNumventa() + ". Nombre cliente: " + ve.getNombrecliente()
					+ ". Unidades: " + ve.getUnidades() + ". Fecha: " + ve.getFecha());
		}
	}

	public static void actualizarXml(JAXBContext jaxbContext, Object jaxbElement)
			throws FileNotFoundException, JAXBException {

		// Crear el Marshaller, y volcar la lista al fichero XML
		Marshaller m = jaxbContext.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(jaxbElement, new FileOutputStream("ventasarticulos.xml"));

	};

	private static void insertarventa(int numeventa, String nomcli, int uni, String fecha) {

		System.out.println("--------------------------------");
		System.out.println("--------- AÑADIR VENTA ---------");
		System.out.println("--------------------------------");
		try {

			JAXBContext jaxbContext = obtenerContexto();
			Unmarshaller u = jaxbContext.createUnmarshaller();
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal(new FileInputStream("ventasarticulos.xml"));

			VentasType miventa = obtenerModeloVentas(jaxbElement);

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
				m.marshal(jaxbElement, new FileOutputStream("ventasarticulos.xml"));
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
