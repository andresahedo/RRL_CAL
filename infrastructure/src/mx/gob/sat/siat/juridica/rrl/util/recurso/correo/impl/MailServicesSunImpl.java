/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.util.recurso.correo.impl;

import mx.gob.sat.siat.juridica.base.service.impl.BaseSupportServices;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.MailServices;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.exception.MailException;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.model.MailMessage;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.model.MailMessageAttachment;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

@Service("mailServices")
public class MailServicesSunImpl extends BaseSupportServices implements MailServices, ApplicationContextAware {

    /**
     * 
     */

    @Autowired
    private transient JavaMailSenderImpl sender;
    private static final long serialVersionUID = 5848841835487882428L;
    private static final String MAIL_FROM = "mail.from";

    /**
     * Variable de contexto
     */
    private transient ApplicationContext context;

    protected MailServicesSunImpl() {
        super();
    }

    @Override
    public void sendHTMLMessage(final MailMessage mailMessage) throws MailException {

        if (null != mailMessage) {
            MimeMessage message = sender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                String[] tos = new String[mailMessage.getTo().size()];

                helper.setTo(mailMessage.getTo().toArray(tos));
                helper.setText(mailMessage.getBody(), true);

                if (null == mailMessage.getPersonalFrom()) {
                    helper.setFrom(sender.getSession().getProperty(MAIL_FROM));
                }
                else {
                    helper.setFrom(sender.getSession().getProperty(MAIL_FROM), mailMessage.getPersonalFrom());
                }

                if (null != mailMessage.getCc() && !mailMessage.getCc().isEmpty()) {
                    String[] ccs = new String[mailMessage.getCc().size()];

                    helper.setCc(mailMessage.getCc().toArray(ccs));
                }

                if (null != mailMessage.getBcc() && !mailMessage.getBcc().isEmpty()) {
                    String[] bccs = new String[mailMessage.getCc().size()];

                    helper.setBcc(mailMessage.getBcc().toArray(bccs));
                }

                helper.setSubject(mailMessage.getSubject());
                if (null != mailMessage.getImagenesInlIne()) {
                    for (String key : mailMessage.getImagenesInlIne().keySet()) {
                        Object object = context.getBean("realPathUtility");
                        FileSystemResource res =
                                new FileSystemResource(object.toString() + mailMessage.getImagenesInlIne().get(key));
                        helper.addInline(key, res);
                    }
                }
                getLogger().debug("Cuerpo del correo enviado: ");
                getLogger().debug(mailMessage.getBody());
                sender.send(message);

            }
            catch (MessagingException e) {
                getLogger().error("", e);
                throw new MailException(e.getMessage(), e);
            }
            catch (UnsupportedEncodingException e) {
                getLogger().error("", e);
                throw new MailException(e.getMessage(), e);
            }

        }
    }

    @Override
    public void sendTextMessage(final MailMessage mailMessage) throws MailException {
        if (null != mailMessage) {
            try {
                Multipart multipart = new MimeMultipart();

                final MimeBodyPart messageBodyPart = new MimeBodyPart();

                messageBodyPart.setContent(mailMessage.getBody(), "text/plain");

                multipart.addBodyPart(messageBodyPart);

                multipart = prepareAttachments(multipart, mailMessage);

                final Message message = prepareMessage(mailMessage);

                message.setContent(multipart);

                Transport.send(message);
            }
            catch (MessagingException me) {
                throw new MailException(me.getMessage(), me);
            }
            catch (IOException ioe) {
                throw new MailException(ioe.getMessage(), ioe);
            }
        }
    }

    private MimeMessage prepareMessage(final MailMessage mailMessage) throws MailException {
        getLogger().debug("mail message: [{}]", mailMessage);

        final MimeMessage message = sender.createMimeMessage();

        try {
            if (null == mailMessage.getPersonalFrom()) {
                message.setFrom(buildInternetAddress(sender.getSession().getProperty(MAIL_FROM)));
            }
            else {
                message.setFrom(buildInternetAddress(sender.getSession().getProperty(MAIL_FROM),
                        mailMessage.getPersonalFrom()));
            }

            message.setRecipients(Message.RecipientType.TO, buildAddresses(mailMessage.getTo()));

            if (null != mailMessage.getCc() && !mailMessage.getCc().isEmpty()) {
                message.setRecipients(Message.RecipientType.CC, buildAddresses(mailMessage.getCc()));
            }

            if (null != mailMessage.getBcc() && !mailMessage.getBcc().isEmpty()) {
                message.setRecipients(Message.RecipientType.BCC, buildAddresses(mailMessage.getBcc()));
            }

            message.setSubject(mailMessage.getSubject(), "iso-8859-1");
        }
        catch (AddressException ae) {
            throw new MailException("Invalid e-mail address.", ae);
        }
        catch (MessagingException me) {
            throw new MailException(me);
        }

        return message;
    }

    private InternetAddress[] buildAddresses(final Collection<String> addresses) throws MailException {
        getLogger().debug("addresses: [{}]", addresses);

        if (null != addresses && !addresses.isEmpty()) {
            final Collection<InternetAddress> mailAddresses = new ArrayList<InternetAddress>();
            InternetAddress mailAddress = null;

            for (final String address : addresses) {
                getLogger().debug("address: [{}]", address);

                if (null != address && !address.trim().isEmpty()) {
                    mailAddress = buildInternetAddress(address);

                    mailAddresses.add(mailAddress);
                }
            }

            getLogger().debug("mail addresses: [{}]", mailAddresses);

            return mailAddresses.toArray(new InternetAddress[0]);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    private InternetAddress buildInternetAddress(final String eMailAddress) throws MailException {
        InternetAddress internetAddress = null;

        if (null != eMailAddress && !eMailAddress.trim().isEmpty()
                && EmailValidator.getInstance().isValid(eMailAddress)) {
            try {
                internetAddress = new InternetAddress(eMailAddress);
            }
            catch (AddressException ae) {
                throw new MailException(eMailAddress, ae);
            }
        }
        else {
            throw new MailException(eMailAddress);
        }

        return internetAddress;
    }

    private InternetAddress buildInternetAddress(final String eMailAddress, final String personalAddress)
            throws MailException {
        InternetAddress internetAddress = buildInternetAddress(eMailAddress);

        try {
            internetAddress.setPersonal(personalAddress);
        }
        catch (UnsupportedEncodingException uee) {
            throw new MailException(uee.getMessage(), uee);
        }

        return internetAddress;
    }

    private Multipart prepareAttachments(final Multipart multipart, final MailMessage mailMessage) throws IOException,
            MessagingException {
        MimeBodyPart messageBodyPart = null;

        Collection<String> attachmentPaths = mailMessage.getAttachmentPaths();

        getLogger().debug("attachment paths: [{}]", attachmentPaths);
        getLogger().debug("attachment paths - size: [{}]", attachmentPaths.size());

        if (!attachmentPaths.isEmpty()) {
            for (String path : attachmentPaths) {
                messageBodyPart = new MimeBodyPart();

                messageBodyPart.attachFile(path);

                multipart.addBodyPart(messageBodyPart);
            }
        }

        Collection<MailMessageAttachment> attachments = mailMessage.getAttachments();

        getLogger().debug("attachments: [{}]", attachments);

        if (attachments != null && !attachments.isEmpty()) {
            getLogger().debug("attachments - size: [{}]", attachments.size());

            DataSource dataSource;

            for (MailMessageAttachment attachment : attachments) {
                getLogger().debug("attachment: [{}]", attachment);

                dataSource = new ByteArrayDataSource(attachment.getContent(), attachment.getContentType());

                messageBodyPart = new MimeBodyPart();

                messageBodyPart.setDataHandler(new DataHandler(dataSource));
                messageBodyPart.setFileName(attachment.getName());

                multipart.addBodyPart(messageBodyPart);
            }
        }

        return multipart;
    }

    /**
     * Metodo para la aplicacion del contexto
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)  {
        context = applicationContext;
    }
}
