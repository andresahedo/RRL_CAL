/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.util.recurso.correo.impl;

import mx.gob.sat.siat.juridica.base.dao.impl.BaseJMSDao;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.MailRequestSender;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.model.MailMessageRequest;

import javax.jms.Destination;

/**
 * Implementaci&oacute;n con JMS para hacer el envio de correo de
 * manera asincrona, env&iacute;a la petici&oacute;n a una cola JMS
 * alojada en el Application server, para su posterios procesamiento.
 * 
 * @author Softtek
 * 
 */
public class MailRequestSenderJMSImpl extends BaseJMSDao implements MailRequestSender {

    private static final long serialVersionUID = 135633461802383852L;

    /**
     * Cola destino para el envio de peticiones de correo.
     */
    private transient Destination destination;

    @Override
    public void sendMailRequest(MailMessageRequest mailMessage) {
        super.sendObjectMessage(destination, mailMessage);
        LOG.debug("El mensaje [{}] fue enviada a la cola: [{}]", mailMessage, destination);
    }

    @Override
    public void setDestination(Destination destination) {
        this.destination = destination;
    }

}
