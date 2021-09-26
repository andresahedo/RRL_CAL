//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2014.06.16 a las 05:19:54 PM CDT 
//

package mx.gob.sat.siat.juridica.buzon.wsdl;

import javax.xml.bind.annotation.*;

/**
 * <p>
 * Clase Java para anonymous complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se
 * espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NotificacionesBuzonResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "notificacionesBuzonResult" })
@XmlRootElement(name = "NotificacionesBuzonResponse")
public class NotificacionesBuzonResponse {

    @XmlElement(name = "NotificacionesBuzonResult")
    private String notificacionesBuzonResult;

    /**
     * Obtiene el valor de la propiedad notificacionesBuzonResult.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNotificacionesBuzonResult() {
        return notificacionesBuzonResult;
    }

    /**
     * Define el valor de la propiedad notificacionesBuzonResult.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setNotificacionesBuzonResult(String value) {
        this.notificacionesBuzonResult = value;
    }

}
