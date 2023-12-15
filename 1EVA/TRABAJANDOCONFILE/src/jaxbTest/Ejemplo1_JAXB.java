package jaxbTest;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
 
public class Ejemplo1_JAXB {
 
    private static final String MIARCHIVO_XML = "D:\\librerias.xml";
 
    //realizar cambios que agrupe varias librerias y con varios libro cada una de ellas debera generar un documento xml d enombre LIBRERIAS LA RAIZ QUE SEA <MISLIBRERIAS>
    
    public static void main(String[] args) throws JAXBException, IOException {
        // Crear la lista de libros
        ArrayList<Libro> libroLista = new ArrayList<Libro>();
        ArrayList<Libro> libroLista2 = new ArrayList<Libro>();
 
        // Crear dos libros y añadirlos
        Libro libro1 = new Libro("Los pilares de la Tierra", "Ken Follett", "Plaza y Janés", "978-84-0149-958-6");
        libroLista.add(libro1);
        Libro libro2 = new Libro("Yo, robot", "Isaac Asimov", "Planeta DeAgostini", "978-84-6742-694-6");
        libroLista.add(libro2);
        
        Libro libro3 = new Libro("tierra", "Ken Follett", "Plaza y Janés", "978-84-0249-958-6");
        libroLista2.add(libro3);
        Libro libro4 = new Libro("robot", "Isaac Asimov", "Planeta DeAgostini", "973-84-6742-694-6");
        libroLista2.add(libro4);
        
 
        // Crear La librería y asignarle la lista de libros
        Libreria milibreria = new Libreria();
        milibreria.setNombre("Prueba de libreria JAXB");
        milibreria.setLugar("Burgos, como no");
        milibreria.setListaLibro(libroLista);
        
        Libreria milibreria2 = new Libreria();
        milibreria2.setNombre("Prueba de libreria JAXB2");
        milibreria2.setLugar("Burgos, como no2");
        milibreria2.setListaLibro(libroLista2);
        
        
        
        MisLibrerias misLibrerias = new MisLibrerias();
        misLibrerias.añadirLibreria(milibreria);
        misLibrerias.añadirLibreria(milibreria2);
        
        
 
        // Crear el contexto indicando la clase raíz
        JAXBContext context = JAXBContext.newInstance(MisLibrerias.class);
      
        // Crear el Marshaller (convierte el JavaBean en una cadena XML)
        Marshaller m = context.createMarshaller();
        
        // Formatear el XML para tener un aspecto amigable
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
        // Visualizarlo con System.out
        m.marshal(misLibrerias, System.out);
 
        // Escribirlo en el archivo
        m.marshal(misLibrerias, new File(MIARCHIVO_XML));
 
		 /*********************************************************************************/
		 
        // Visualizar los datos del documento XML creado
        System.out.println("------------ Leer el XML ------------");
        
        // Crear el Unmarshaller en el cotexto de la clase Libreria
        Unmarshaller unmars = context.createUnmarshaller();
        
        // Utilizar el método unmarshal para obtener datos de un Reader
        Libreria libreria2 = (Libreria) unmars.unmarshal(new FileReader(MIARCHIVO_XML));
        
        // Recuperar el ArrayList y visualizarlo
        System.out.println("Nombre de librería: " + libreria2.getNombre());
        System.out.println("Lugar de la librería: " + libreria2.getLugar());
        System.out.println("Libros de la librería: ");
        ArrayList<Libro> lista = libreria2.getListaLibro();
        for (Libro libro : lista) {
            System.out.println("\tTítulo del libro: " + libro.getNombre() + ", autor: " + libro.getAutor());
        }
    }
}
