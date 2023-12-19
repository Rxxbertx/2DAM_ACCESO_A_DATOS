//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.12.14 a las 05:43:00 PM CET 
//


package CONVERSION_XSD_A_CLASES_JAVA;

import java.math.BigDecimal;
import java.math.BigInteger;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para DatosArtic complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DatosArtic"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="denominacion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="stock" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="precio" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatosArtic", propOrder = {
    "codigo",
    "denominacion",
    "stock",
    "precio"
})
public class DatosArtic {

    @XmlElement(required = true)
    protected String codigo;
    @XmlElement(required = true)
    protected String denominacion;
    @XmlElement(required = true)
    protected BigInteger stock;
    @XmlElement(required = true)
    protected BigDecimal precio;

    /**
     * Obtiene el valor de la propiedad codigo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define el valor de la propiedad codigo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

    /**
     * Obtiene el valor de la propiedad denominacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominacion() {
        return denominacion;
    }

    /**
     * Define el valor de la propiedad denominacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominacion(String value) {
        this.denominacion = value;
    }

    /**
     * Obtiene el valor de la propiedad stock.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStock() {
        return stock;
    }

    /**
     * Define el valor de la propiedad stock.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStock(BigInteger value) {
        this.stock = value;
    }

    /**
     * Obtiene el valor de la propiedad precio.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * Define el valor de la propiedad precio.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrecio(BigDecimal value) {
        this.precio = value;
    }

}
