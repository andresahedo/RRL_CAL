/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.mensaje;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

/**
 * Clase principal que servira de base para las clases Listener
 * 
 * @author softtek
 * 
 */
public abstract class BaseMessageListener implements MessageListener {

    /**
     * Instancia para el registro de eventos
     */
    private final transient Logger logger = LoggerFactory.getLogger("jms");

    /**
     * Metodo para extraer el contenido del mensaje
     * 
     * @param message
     * @return object
     */
    protected Object extractContentFromMessage(final Message message) {
        Object object = null;

        if (null != message) {
            if (message instanceof BytesMessage) {
                logger.debug("Is BytesMessage");

                final BytesMessage bytesMessage = (BytesMessage) message;

                long length;

                ByteArrayInputStream bais = null;

                ObjectInput objectInput = null;

                try {
                    length = bytesMessage.getBodyLength();

                    final byte[] buffer = new byte[(int) length];

                    bytesMessage.readBytes(buffer);

                    bais = new ByteArrayInputStream(buffer);

                    objectInput = new ObjectInputStream(bais);

                    object = objectInput.readObject();
                }
                catch (JMSException je) {
                    logger.error(je.toString());
                }
                catch (IOException ioe) {
                    logger.error(ioe.toString());
                }
                catch (ClassNotFoundException cnfe) {
                    logger.error(cnfe.toString());
                }
                finally {
                    if (null != objectInput) {
                        try {
                            objectInput.close();
                        }
                        catch (IOException ioe) {
                            logger.error(ioe.toString());
                        }
                    }

                    if (null != bais) {
                        try {
                            bais.close();
                        }
                        catch (IOException ioe) {
                            logger.error(ioe.toString());
                        }
                    }
                }
            }
            else if (message instanceof MapMessage) {
                logger.debug("Is MapMessage");
            }
            else if (message instanceof ObjectMessage) {
                logger.debug("Is ObjectMessage");

                try {
                    object = ((ObjectMessage) message).getObject();
                }
                catch (JMSException je) {
                    logger.error(je.toString());
                }
            }
            else if (message instanceof StreamMessage) {
                logger.debug("Is StreamMessage");
            }
            else if (message instanceof TextMessage) {
                logger.debug("Is TextMessage");

                try {
                    object = ((TextMessage) message).getText();
                }
                catch (JMSException je) {
                    logger.error(je.toString());
                }
            }
        }

        logger.debug("Extracted object: [{}]", object);

        return object;
    }

    public Logger getLogger() {
        return this.logger;
    }

}
