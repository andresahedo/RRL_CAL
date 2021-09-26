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
@FacesValidator("minutaValidator")
public class MinutaValidator implements Validator {
    /**
     * Constante que representa la expresion regular utilizada como
     * patron para validar el correo electronico.
     */
    private static final String MINUTA_PATTERN = "[A-Za-z0-9\\/]*";
    /**
     * Propiedad que representa el patron a validar.
     */
    private Pattern pattern;
    /**
     * Propiedad utilizada para la comparacion del correo con el
     * patron especificado.
     */
    private Matcher matcher;

    public MinutaValidator() {
        pattern = Pattern.compile(MINUTA_PATTERN);
    }

    /**
     * Metodo implementado para validar el correo electronico manejado
     * a traves de una excepcion.
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)  {
        String minuta = (value == null ? "" : ((String) value).trim());
        if (!minuta.equals("")) {
            matcher = pattern.matcher(value.toString());
            if (!matcher.matches()) {
                FacesMessage message = new FacesMessage();
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                message.setSummary("No se permiten otros caracteres diferentes a n\u00FAmeros, letras y '/'");
                message.setDetail("No se permiten otros caracteres diferentes a n\u00FAmeros, letras y '/'");
                throw new ValidatorException(message);
            }
        }
    }

}
