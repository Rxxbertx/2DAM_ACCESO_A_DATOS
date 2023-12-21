package CONVERSION_XSD_A_CLASES_JAVA;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class principal {

	/**
	 * Un programa que permita realizar las siguientes operaciones:
	 * 
	 * Visualizar el documento XML.
	 * 
	 * Insertar una venta.
	 * 
	 * Modificar los datos de una venta.
	 * 
	 * Modificar el stock del artículo.
	 * 
	 * Eliminar una venta.
	 * 
	 * Salir del programa.
	 * 
	 * 
	 * @param args Argumentos de la linea de comandos
	 */
	public static void main(String[] args) {

		menu();

	}

	/**
	 * Método que contiene el menu principal del programa
	 */
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

				System.out.println("Inserta la fecha (TIPO DE DATO: CARACTER, FORMATO: DD-MM-AAAA)");

				String fecha = sc.nextLine();

				insertarventa(Integer.valueOf(numeVenta), nomCli, Integer.valueOf(uni), fecha);
				break;

			case "3":

				/*
				 * Un método para cambiar los datos de una venta. Este método recibirá un número
				 * de venta a modificar, las unidades y la fecha, de tal manera que esos serán
				 * los datos a modificar del número de venta. Devolverá true si la operación se
				 * realiza correctamente, y false si ocurre algún error.
				 */

				if (modificarVenta(sc))
					System.out.println("VENTA MODIFICADA EXITOSAMENTE");

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

		sc.close();
		// se muestra la salida del sistema con el formato ISO_DATE_TIME
		System.err.println("SALIDA DEL SISTEMA: " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
	}

	/**
	 * Método que contiene la lógica para modificar una venta
	 * 
	 * 
	 * @param sc Scanner para leer datos desde teclado
	 * @return true si la operación se realiza correctamente, y false si ocurre
	 *         algún error.
	 */
	private static boolean modificarVenta(Scanner sc) {

		System.out.println("--------------------------------");
		System.out.println("--------- MODIFICAR VENTA ---------");
		System.out.println("--------------------------------");

		try {

			JAXBContext contexto = obtenerContexto();

			Unmarshaller u = crearUnmarshaller(contexto);

			JAXBElement jaxbElement = obtenerElementoJAXB(u);

			VentasType vt = obtenerModeloVentas(jaxbElement);

			mostrarArticulo(vt, vt.getVentas().getVenta());
			visualizarVentasArticulo(vt.getVentas().getVenta());

			// contiene las ventas generales
			Ventas venta = vt.getVentas();
			// contiene las ventas de un articulo
			List<Ventas.Venta> ventasDeVenta = venta.getVenta();
			// contiene el articulo
			DatosArtic articulo = vt.getArticulo();

			System.err.println("ADVERTENCIA, INSERTE LOS DATOS BIEN");

			System.out.println("Inserta el número de venta: (TIPO DE DATO: NUMERICO)");

			String numVenta = sc.nextLine();// numero de venta a modificar

			// contiene el indice de la venta a modificar
			int i = verificarVenta(Integer.valueOf(numVenta), ventasDeVenta);

			// si la venta existe
			if (i != -1) {

				// contiene la venta a modificar
				Ventas.Venta ventaArt = ventasDeVenta.get(i);

				//mientras el usuario quiera seguir modificando
				while (true) {

					//opciones para modificar
					switch (opcionesModificar(sc)) {
					
					//modificar unidades
					case "1":

						System.out.println("INTRODUCE EL NUEVO VALOR DE UNIDADES VENDIDAS");
						System.out.println("ACTUAL: " + ventaArt.getUnidades());
						System.out.println("NUEVO: ");
						String val = sc.nextLine();
						int uni = -1; // valor por defecto

						// si el valor ingresado es correcto
						if (!val.isBlank()) {

							try {

								uni = Integer.parseInt(val);

							} catch (NumberFormatException e) {
								System.out.println("ERROR, NUMERO ERRONEO");
								break;
							}

							if (uni <= 0) {
								System.out.println("ERROR, NUMERO DEBE SER MAYOR A 0");
								break;
							} else {

								// se actualiza el stock del articulo
								int temp = articulo.getStock().intValue() + (ventaArt.getUnidades() - uni);

								// si el stock es menor a 0
								if (temp < 0) {
									System.out.println("NO PUEDES ESTABLECER ESA CANTIDAD, STOCK INSUFICIENTE");

									break;

								} else {

									// se actualiza el stock del articulo
									articulo.setStock(BigInteger.valueOf(temp));

									ventaArt.setUnidades(uni);

									System.out.println("UNIDADES MODIFICADAS CORRECTAMENTE");

								}

							}

						} else {

							System.out.println("ERROR, NUMERO ERRONEO");
							break;

						}

						break;

						//modificar fecha
					case "2":

						System.out.println("INTRODUCE LA NUEVA FECHA QUE DESEES");
						System.out.println("ACTUAL: " + ventaArt.getFecha());
						System.out.println("NUEVO: ");

						// contiene la nueva fecha
						String fecha = sc.nextLine();

						// DD-MM-AAAA
						// si la fecha es correcta
						if (fecha.matches("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$")) {

							// se actualiza la fecha
							ventaArt.setFecha(fecha);
							System.out.println("FECHA MODIFICADA CORRECTAMENTE");

						} else {
							System.out.println("ERROR EN FECHA, FORMATO DD-MM-AAAA");
						}

						break;

					default:
						System.err.println("OPCION DESCONOCIDA");
						break;

					}

					//se pregunta si se quiere seguir modificando
					System.out.println("¿QUIERES SEGUIR MODIFICANDO? (1) SI (CUALQUIER TECLA) NO");
					if (!sc.nextLine().equals("1")) {
						break;//si no se quiere seguir modificando se sale del bucle
					}

				}

				// se actualiza el xml
				actualizarXml(contexto, jaxbElement);
				return true;

			} else {

				System.err.println("VENTA INEXISTENTE :(, PRUEBA CON OTRO NUMERO DE VENTA");
				return false;

			}

		} catch (JAXBException e) {

			System.err.println("ERROR AL MODIFICAR VENTA :JAXB");
			e.printStackTrace();
		} catch (FileNotFoundException e) {

			System.err.println("ERROR FICHERO NO ACCESIBLE");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("ERROR, NUMERO DE VENTA ERRONEO");

		}

		return false;
	}

	/**
	 * Método que contiene las opciones para modificar una venta
	 * 
	 * @param sc Scanner para leer datos desde teclado
	 * @return String con la opción elegida
	 */
	private static String opcionesModificar(Scanner sc) {

		System.out.println("MODIFICAR");
		System.out.println(">> UNIDADES VENDIDAS << (1)");
		System.out.println(">> FECHA << (2)");

		return sc.nextLine();

	}

	/**
	 * Método que contiene la lógica para modificar el stock de un articulo
	 * 
	 * @param sc Scanner para leer datos desde teclado
	 * @return true si la operación se realiza correctamente, y false si ocurre
	 *         algún error.
	 */
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

			int i = -123456789;// valor por defecto

			try {
				i = Integer.parseInt(sc.nextLine());// intentar parsear a int el valor ingresado
			} catch (NumberFormatException e) {
				System.err.println("ERROR INGRESA UN NUMERO CORRECTO");
				return false;
			}

			// si el valor ingresado es correcto
			if (i != -123456789 && i > 0) {

				// se actualiza el stock del articulo y el xml
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

	/**
	 * Método que contiene la lógica para borrar una venta
	 * 
	 * @param sc Scanner para leer datos desde teclado
	 * @return true si la operación se realiza correctamente, y false si ocurre
	 *         algún error.
	 */
	private static boolean borrarVenta(Scanner sc) {

		System.out.println("--------------------------------");
		System.out.println("--------- ELIMINAR VENTA ---------");
		System.out.println("--------------------------------");

		try {

			// contiene el contexto
			JAXBContext context = obtenerContexto();
			// contiene el unmarshaller
			Unmarshaller u = crearUnmarshaller(context);
			// contiene el elemento JAXB
			JAXBElement jaxbElement = obtenerElementoJAXB(u);

			// contiene objeto ventas
			VentasType modeloVentas = obtenerModeloVentas(jaxbElement);

			// contiene las ventas generales
			Ventas ventas = modeloVentas.getVentas();

			// contiene las ventas de un articulo
			List<Ventas.Venta> ventasDeVenta = ventas.getVenta();

			// obtiene el articulo
			DatosArtic articulo = modeloVentas.getArticulo();

			// mostrar articulo
			mostrarArticulo(modeloVentas, ventasDeVenta);

			// mostrar ventas de articulo
			visualizarVentasArticulo(ventasDeVenta);

			System.err.println("ADVERTENCIA, INSERTE LOS DATOS BIEN");

			System.out.println("Inserta el número de venta: (TIPO DE DATO: NUMERICO)");

			// contiene el numero de venta a eliminar
			String numVenta = sc.nextLine();

			// contiene el indice de la venta a eliminar
			int i = verificarVenta(Integer.valueOf(numVenta), ventasDeVenta);

			if (i != -1) {

				// contiene las unidades de la venta a eliminar
				int temp = ventasDeVenta.get(i).getUnidades();

				// se actualiza el stock del articulo
				articulo.setStock(BigInteger.valueOf(articulo.getStock().intValue() + temp));

				// se elimina la venta
				ventasDeVenta.remove(i);

			} else {
				System.err.println("VENTA INEXISTENTE :(, PRUEBA CON OTRO NUMERO DE VENTA");
				return false;
			}

			// se actualiza el xml

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

	/**
	 * Método que verifica si existe una venta
	 * 
	 * @param numeventa   número de venta a verificar
	 * @param listaVentas lista de ventas
	 * @return -1 si no existe, indice de la venta en el array si existe
	 */
	private static int verificarVenta(int numeventa, List<Ventas.Venta> listaVentas) {
		for (Ventas.Venta venta : listaVentas) {
			if (numeventa == venta.getNumventa().intValue()) {
				return listaVentas.indexOf(venta);
			}
		}
		return -1;
	}

	/**
	 * Método que obtiene el modelo de ventas
	 * 
	 * @param jaxbElement elemento JAXB
	 * @return VentasType con el modelo de ventas
	 */

	private static VentasType obtenerModeloVentas(JAXBElement jaxbElement) {
		return (VentasType) jaxbElement.getValue();
	}

	/**
	 * Método que obtiene el elemento JAXB
	 * 
	 * @param u Unmarshaller
	 * @return JAXBElement con el elemento JAXB
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	private static JAXBElement obtenerElementoJAXB(Unmarshaller u) throws JAXBException, FileNotFoundException {
		return (JAXBElement) u.unmarshal(new FileInputStream("ventasarticulos.xml"));
	}

	/**
	 * Método que crea un Unmarshaller
	 * 
	 * @param context JAXBContext
	 * @return Unmarshaller
	 * @throws JAXBException
	 */
	private static Unmarshaller crearUnmarshaller(JAXBContext context) throws JAXBException {
		return context.createUnmarshaller();
	}

	/**
	 * Método que obtiene el contexto
	 * 
	 * @return JAXBContext
	 * @throws JAXBException
	 */
	private static JAXBContext obtenerContexto() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
		return context;
	}

	/**
	 * Método que visualiza el xml
	 * 
	 */
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

	/**
	 * Método que muestra los datos de un artículo
	 * 
	 * @param miventa     modelo de ventas
	 * @param listaVentas lista de ventas
	 */
	private static void mostrarArticulo(VentasType miventa, List listaVentas) {
		// Cargar los datos del artículo
		DatosArtic miartic = (DatosArtic) miventa.getArticulo();
		System.out.println("Nombre del articulo: " + miartic.getDenominacion());
		System.out.println("Codigo del articulo: " + miartic.getCodigo());
		System.out.println("Stock del articulo : " + miartic.getStock());
		System.out.println("Precio del articulo: " + miartic.getPrecio());
		System.out.println("Ventas del articulo: " + listaVentas.size());
	}

	/**
	 * Método que visualiza las ventas de un artículo
	 * 
	 * @param listaVentas lista de ventas
	 */
	private static void visualizarVentasArticulo(List listaVentas) {
		// Visualizar las ventas del artículo
		for (int i = 0; i < listaVentas.size(); i++) {
			Ventas.Venta ve = (Ventas.Venta) listaVentas.get(i);
			System.out.println("Numero de venta: " + ve.getNumventa() + ". Nombre cliente: " + ve.getNombrecliente()
					+ ". Unidades: " + ve.getUnidades() + ". Fecha: " + ve.getFecha());
		}
	}

	/**
	 * Método que actualiza el xml
	 * 
	 * @param jaxbContext JAXBContext
	 * @param jaxbElement elemento JAXB
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	public static void actualizarXml(JAXBContext jaxbContext, Object jaxbElement)
			throws FileNotFoundException, JAXBException {

		// Crear el Marshaller, y volcar la lista al fichero XML
		Marshaller m = jaxbContext.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(jaxbElement, new FileOutputStream("ventasarticulos.xml"));

	};

	/**
	 * Método que inserta una venta
	 * 
	 * @param numeventa número de venta
	 * @param nomcli    nombre del cliente
	 * @param uni       unidades
	 * @param fecha     fecha
	 */
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
