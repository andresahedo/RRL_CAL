/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.util.recurso.correo;

import mx.gob.sat.siat.juridica.base.mensaje.BaseMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;

/**
 * Metodo para solicitar el receptor del mail
 * 
 * @author softtek
 * 
 */
public class MailRequestReceiver extends BaseMessageListener {

    /**
     * Instancia para el registro de eventos
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Inyeccion del MailHelper
     */
    @Autowired
    private MailHelper mailHelper;

    /**
     * Metodo para extraer el contenido del mensaje
     */
    @Override
    public void onMessage(Message message) {
        logger.debug("Message received: [{}]", message);
        final Object content = extractContentFromMessage(message);
        if (null != content) {
            try {
                mailHelper.sendMessage(content);
            }
            catch (Exception e) {
                logger.error(
                        "A request to send an e-mail failed due exception: {}.\nRequest was sent to 'Error' queue.\n{}",
                        e, content);

            }
        }
    }

}
