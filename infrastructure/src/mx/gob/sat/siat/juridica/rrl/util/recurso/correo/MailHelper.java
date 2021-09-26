/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.util.recurso.correo;

import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.exception.MailException;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.model.MailMessage;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.model.MailMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Clase Helper de correo
 * 
 * @author softtek
 * 
 */
@Component
public class MailHelper extends BaseHelper {

    /**
     * Instancia para el registro de eventos
     */
    private final Logger logger = LoggerFactory.getLogger(MailHelper.class);
    /**
     * Numero de version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Inyeccion del MailServices
     */
    @Autowired
    private MailServices mailServices;

    /**
     * Metodo para el envio del mensaje
     * 
     * @param messageRequest
     * @throws Exception
     */
    public void sendMessage(final Object messageRequest) throws MailException {
        mailServices.sendHTMLMessage(buildMailMessage((MailMessageRequest) messageRequest));
    }

    /**
     * Metodo para la construccion del mensaje
     * 
     * @param mailMessageRequest
     * @return
     */
    private MailMessage buildMailMessage(final MailMessageRequest mailMessageRequest) {
        final MailMessage mailMessage = new MailMessage();
        mailMessage.setTo(mailMessageRequest.getDestinations());
        mailMessage.setSubject(mailMessageRequest.getSubject());
        mailMessage.setBody(buildBody(mailMessageRequest));
        mailMessage.setImagenesInlIne(mailMessageRequest.getImagenes());

        logger.debug("Mail message: [{}]", mailMessage);

        return mailMessage;
    }

    /**
     * Metodo que crea el cuerpo del mensaje
     * 
     * @param mailMessageRequest
     * @return
     */
    private String buildBody(final MailMessageRequest mailMessageRequest) {
        return replaceTokens(mailMessageRequest.getTemplate(), mailMessageRequest.getParams());
    }

    /**
     * Metodo para reemplazar los Tokens
     * 
     * @param eMailTemplate
     * @param tokens
     * @return
     */
    private String replaceTokens(final String eMailTemplate, final Map<String, String> tokens) {
        String result = "";

        if (null != eMailTemplate && !eMailTemplate.trim().isEmpty() && null != tokens && !tokens.isEmpty()) {
            result = eMailTemplate;

            final Set<Entry<String, String>> entries = tokens.entrySet();

            for (final Entry<String, String> entry : entries) {
                result = result.replace('$' + entry.getKey(), entry.getValue());
            }
        }

        return result;
    }

}
