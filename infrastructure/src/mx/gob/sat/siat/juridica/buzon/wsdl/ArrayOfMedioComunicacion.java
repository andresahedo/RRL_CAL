//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2014.06.16 a las 05:19:54 PM CDT 
//

package mx.gob.sat.siat.juridica.buzon.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Clase Java para ArrayOfMedioComunicacion complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se
 * espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfMedioComunicacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MedioComunicacion" type="{http://www.sat.gob.mx/}MedioComunicacion" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfMedioComunicacion", propOrder = { "medioComunicacion" })
public class ArrayOfMedioComunicacion {

    @XmlElement(name = "MedioComunicacion", nillable = true)
    private List<MedioComunicacion> medioComunicacion;

    /**
     * Gets the value of the medioComunicacion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not
     * a snapshot. Therefore any modification you make to the returned
     * list will be present inside the JAXB object. This is why there
     * is not a <CODE>set</CODE> method for the medioComunicacion
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getMedioComunicacion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MedioComunicacion }
     * 
     * 
     */
    public List<MedioComunicacion> getMedioComunicacion() {
        if (medioComunicacion == null) {
            medioComunicacion = new ArrayList<MedioComunicacion>();
        }
        return this.medioComunicacion;
    }

}
