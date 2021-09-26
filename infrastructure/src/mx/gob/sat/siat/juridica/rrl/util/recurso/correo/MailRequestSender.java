/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.util.recurso.correo;

import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.model.MailMessageRequest;

import java.io.Serializable;

/**
 * Clase para enviar una petici&oacute;n de envio de correo.
 * 
 * @author Softtek
 * 
 */
public interface MailRequestSender extends Serializable {

    /**
     * M&eacute;todo para enviar una petici&oacute;n de envio de
     * correo.
     * 
     * @param message
     *            Mensaje a enviar.
     */
    void sendMailRequest(MailMessageRequest message);

}
