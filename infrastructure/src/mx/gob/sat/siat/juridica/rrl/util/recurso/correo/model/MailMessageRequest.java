/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.util.recurso.correo.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import java.util.Map;
import java.util.Set;

/**
 * Bean con las propiedades de mensajes
 * 
 * @author softtek
 * 
 */
public class MailMessageRequest extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -2285739329143235535L;
    /**
     * Lista de destinos
     */
    private Set<String> destinations;
    /**
     * Cadena con el asunto del mail
     */
    private String subject;
    /**
     * Cadena de plantilla
     */
    private String template;
    /**
     * Mapa de parametros
     */
    private Map<String, String> params;

    /**
     * Mapa de imagenes a incrustar
     */
    private Map<String, String> imagenes;

    /**
     * Constructor de clase
     * 
     * @param destinations
     * @param subject
     * @param template
     * @param params
     */
    public MailMessageRequest(final Set<String> destinations, final String subject, final String template,
            final Map<String, String> params) {
        super();

        this.destinations = destinations;
        this.subject = subject;
        this.template = template;
        this.params = params;
    }

    /**
     * 
     * @return destinations
     */
    public Set<String> getDestinations() {
        return destinations;
    }

    /**
     * 
     * @param destinations
     *            a fijar
     */
    public void setDestinations(final Set<String> destinations) {
        this.destinations = destinations;
    }

    /**
     * 
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 
     * @param subject
     *            a fijar
     */
    public void setSubject(final String subject) {
        this.subject = subject;
    }

    /**
     * 
     * @return template
     */
    public String getTemplate() {
        return template;
    }

    /**
     * 
     * @param template
     *            a fijar
     */
    public void setTemplate(final String template) {
        this.template = template;
    }

    /**
     * 
     * @return params
     */
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * 
     * @param params
     *            a fijar
     */
    public void setParams(final Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(Map<String, String> imagenes) {
        this.imagenes = imagenes;
    }
}
