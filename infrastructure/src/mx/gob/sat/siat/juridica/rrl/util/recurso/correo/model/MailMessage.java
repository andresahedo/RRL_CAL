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

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

/**
 * Bean que se encarga de las propiedades del mail
 * 
 * @author softtek
 * 
 */
public class MailMessage extends BaseModel {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 3488711629049357666L;

    /**
     * Remitente
     */
    private String from;
    /**
     * Remitente personal
     */
    private String personalFrom;
    /**
     * Coleccion de imagenes a adjuntar inline
     */
    private Map<String, String> imagenesInlIne;
    /**
     * Coleccion de destinatarios
     */
    private Collection<String> to = new LinkedList<String>();
    /**
     * Coleccion de destinatarios a quien se les enviara una copia
     */
    private Collection<String> cc;
    /**
     * Coleccion de destinatarios a quien se les enviara una copia
     * oculta
     */
    private Collection<String> bcc;
    /**
     * Asunto
     */
    private String subject;
    /**
     * Cuerpo del mail
     */
    private String body;
    /**
     * Adjuncion de lista de curso
     */
    private final Collection<String> attachmentPaths = new LinkedHashSet<String>();
    /**
     * Adjuncion de curso del mail
     */
    private final Collection<MailMessageAttachment> attachments = new LinkedHashSet<MailMessageAttachment>();

    /**
     * Constructor de clase sin parametros
     */
    public MailMessage() {
        super();
    }

    /**
     * Constructor de la clase con los parametros:
     * 
     * @param from
     * @param to
     * @param subject
     */
    public MailMessage(final String from, final String to, final String subject) {
        this();

        this.from = from;
        this.to.add(to);
        this.subject = subject;
    }

    /**
     * Constructor de la clase con los parametros:
     * 
     * @param from
     * @param to
     * @param subject
     */
    public MailMessage(final String from, final Collection<String> to, final String subject) {
        this();

        this.from = from;
        this.to = to;
        this.subject = subject;
    }

    /**
     * Constructor de la clase con los parametros:
     * 
     * @param from
     * @param personalFrom
     * @param to
     * @param subject
     */
    public MailMessage(final String from, final String personalFrom, final String to, final String subject) {
        this(from, to, subject);

        this.personalFrom = personalFrom;
    }

    /**
     * Constructor de la clase con los parametros:
     * 
     * @param from
     * @param personalFrom
     * @param to
     * @param subject
     */
    public MailMessage(final String from, final String personalFrom, final Collection<String> to, final String subject) {
        this(from, to, subject);

        this.personalFrom = personalFrom;
    }

    /**
     * 
     * @return from
     */
    public String getFrom() {
        return from;
    }

    /**
     * 
     * @param from
     *            a fijar
     */
    public void setFrom(final String from) {
        this.from = from;
    }

    /**
     * personalFrom
     * 
     * @return
     */
    public String getPersonalFrom() {
        return personalFrom;
    }

    /**
     * 
     * @param personalFrom
     *            a fijar
     */
    public void setPersonalFrom(final String personalFrom) {
        this.personalFrom = personalFrom;
    }

    /**
     * 
     * @return to
     */
    public Collection<String> getTo() {
        return to;
    }

    /**
     * 
     * @param to
     *            a fijar
     */
    public void setTo(final Collection<String> to) {
        this.to = to;
    }

    /**
     * 
     * @return cc
     */
    public Collection<String> getCc() {
        return cc;
    }

    /**
     * 
     * @param cc
     *            a fijar
     */
    public void setCc(final Collection<String> cc) {
        this.cc = cc;
    }

    /**
     * 
     * @return bcc
     */
    public Collection<String> getBcc() {
        return bcc;
    }

    /**
     * 
     * @param bcc
     *            a fijar
     */
    public void setBcc(final Collection<String> bcc) {
        this.bcc = bcc;
    }

    /**
     * 
     * @return sibject
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
     * @return body
     */
    public String getBody() {
        return body;
    }

    /**
     * 
     * @param body
     *            a fijar
     */
    public void setBody(final String body) {
        this.body = body;
    }

    /**
     * 
     * @param path
     *            a fijar
     */
    public void addAttachment(final String path) {
        attachmentPaths.add(path);
    }

    /**
     * 
     * @param attachment
     *            a fijar
     */
    public void addAttachment(final MailMessageAttachment attachment) {
        attachments.add(attachment);
    }

    /**
     * 
     * @return attachmentPaths
     */
    public Collection<String> getAttachmentPaths() {
        return attachmentPaths;
    }

    /**
     * 
     * @return attachments
     */
    public Collection<MailMessageAttachment> getAttachments() {
        return attachments;
    }

    public Map<String, String> getImagenesInlIne() {
        return imagenesInlIne;
    }

    public void setImagenesInlIne(Map<String, String> imagenesInlIne) {
        this.imagenesInlIne = imagenesInlIne;
    }
}
