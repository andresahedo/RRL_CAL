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
 *         &lt;element name="ObtieneMediosComunicacionResult" type="{http://tempuri.org/}ArrayOfMedioComunicacion" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "obtieneMediosComunicacionResult" })
@XmlRootElement(name = "ObtieneMediosComunicacionResponse")
public class ObtieneMediosComunicacionResponse {

    @XmlElement(name = "ObtieneMediosComunicacionResult")
    private ArrayOfMedioComunicacion obtieneMediosComunicacionResult;

    /**
     * Obtiene el valor de la propiedad
     * obtieneMediosComunicacionResult.
     * 
     * @return possible object is {@link ArrayOfMedioComunicacion }
     * 
     */
    public ArrayOfMedioComunicacion getObtieneMediosComunicacionResult() {
        return obtieneMediosComunicacionResult;
    }

    /**
     * Define el valor de la propiedad
     * obtieneMediosComunicacionResult.
     * 
     * @param value
     *            allowed object is {@link ArrayOfMedioComunicacion }
     * 
     */
    public void setObtieneMediosComunicacionResult(ArrayOfMedioComunicacion value) {
        this.obtieneMediosComunicacionResult = value;
    }

}
