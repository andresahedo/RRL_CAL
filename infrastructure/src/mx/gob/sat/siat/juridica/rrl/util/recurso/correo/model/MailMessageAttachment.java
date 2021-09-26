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

import java.util.Arrays;

/**
 * Clase que determina el trayecto del mail
 * 
 * @author softtek
 * 
 */
public class MailMessageAttachment extends BaseModel {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = -8543171944668865186L;

    /**
     * Nombre
     */
    private String name;
    /**
     * Tipo de contenido
     */
    private String contentType;
    /**
     * Contenido
     */
    private byte[] content;

    /**
     * Constructor de clase
     * 
     * @param name
     * @param contentType
     * @param content
     */
    public MailMessageAttachment(final String name, final String contentType, final byte[] content) {
        super();

        this.name = name;
        String s = contentType;
        this.contentType = s;
        this.content = (content != null ? Arrays.copyOf(content, content.length) : null);
    }

    /**
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *            a fijar
     */
    protected void setName(final String name) {
        this.name = name;
    }

    /**
     * 
     * @return cotentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * 
     * @param contentType
     *            a fijar
     */
    protected void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    /**
     * 
     * @return content
     */
    public byte[] getContent() {
        return null == content ? null : content.clone();
    }

    /**
     * 
     * @param content
     *            a fijar
     */
    protected void setContent(final byte[] content) {
        this.content = (content != null ? Arrays.copyOf(content, content.length) : null);
    }
}
