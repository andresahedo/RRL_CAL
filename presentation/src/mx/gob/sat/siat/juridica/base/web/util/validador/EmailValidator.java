/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.util.validador;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase de utileria para validar el formato del correo electronico.
 * 
 * @author Softtek
 */
@FacesValidator("eMailValidator")
public class EmailValidator implements Validator {
    /**
     * Constante que representa la expresion regular utilizada como
     * patron para validar el correo electronico.
     */
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
            + "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" + "(\\.[A-Za-z]{2,})$";
    /**
     * Propiedad que representa el patron a validar.
     */
    private Pattern pattern;
    /**
     * Propiedad utilizada para la comparacion del correo con el
     * patron especificado.
     */
    private Matcher matcher;

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
     * Metodo implementado para validar el correo electronico manejado
     * a traves de una excepcion.
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)  {
        String eMail = (value == null ? "" : ((String) value).trim());
        if (!eMail.equals("")) {
            matcher = pattern.matcher(value.toString());
            if (!matcher.matches()) {
                FacesMessage message = new FacesMessage();
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                message.setSummary("El correo electr\u00F3nico no cumplen con el formato esperado.");
                message.setDetail("El correo electr\u00F3nico no cumplen con el formato esperado.");
                throw new ValidatorException(message);
            }
        }
    }

}
