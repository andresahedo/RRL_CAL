/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.Serializable;

/**
 * Clase DAO principal que sirve de base para los DAO JMS
 * 
 * @author softtek
 * 
 */
public abstract class BaseJMSDao implements Serializable {

    private static final long serialVersionUID = 1L;

    protected static final Logger LOG = LoggerFactory.getLogger("jms");

    private JmsTemplate jmsTemplate;

    /**
     * Constructor de la clase
     */
    protected BaseJMSDao() {
        super();
    }

    /**
     * Metodo que establecera el destino
     * 
     * @param destination
     */
    public abstract void setDestination(Destination destination);

    /**
     * Metodo para establecer el valor de la propiedad JmsTemplate
     * 
     * @param jmsTemplate
     */
    public void setJmsTemplate(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * Metodo que envia el mensaje al destino establecido por default
     * 
     * @param message
     */
    protected void sendMessageToDefaultDestination(final BaseModel message) {
        jmsTemplate.convertAndSend(message);
    }

    /**
     * Metodo que envia el texto del mensaje al destino establecido
     * por default
     * 
     * @param message
     */
    protected void sendTextMessageToDefaultDestination(final String message) {
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                return session.createTextMessage(message);
            }

        });
    }

    /**
     * Metodo que envia el mensaje a un destino no preestablecido
     * 
     * @param destination
     * @param message
     */
    protected void sendMessage(final Destination destination, final BaseModel message) {
        jmsTemplate.convertAndSend(destination, message);
    }

    /**
     * Metodo que envia el numero de serie del mensaje a un destino no
     * preestablecido
     * 
     * @param destination
     * @param message
     */
    protected void sendObjectMessage(final Destination destination, final Serializable message) {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                return session.createObjectMessage(message);
            }

        });
    }

    /**
     * Metodo que envia el texto del mensaje a un destino no
     * preestablecido
     * 
     * @param destination
     * @param message
     */
    protected void sendTextMessage(final Destination destination, final String message) {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                return session.createTextMessage(message);
            }

        });
    }
}
