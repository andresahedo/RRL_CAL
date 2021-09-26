/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.web.util.validador;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator que implementa la logica de negocio para validar el
 * numero de asunto.
 * 
 * @author Softtek
 * 
 */
@FacesValidator("numeroAsuntoCAValidator")
public class NumeroAsuntoValidator implements Validator {

    /**
     * Propiedad constante que representa la expresion regular
     * utilizada como patron para validar el numero de asunto.
     */
    private static final String NUMERO_ASUNTO_PATTERN = "[A-Za-z0-9]{0,30}";
    /**
     * Propiedad utilizada para representar el patron definido para
     * validar.
     */
    private Pattern pattern;
    /**
     * Propiedad utilizada para comparar el patron con el numero de
     * asunto ingresado.
     */
    private Matcher matcher;

    /**
     * Constructor
     */
    public NumeroAsuntoValidator() {
        pattern = Pattern.compile(NUMERO_ASUNTO_PATTERN);
    }

    /**
     * Metodo que valida que un numero de asunto concuerde con el
     * patro definido.
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)  {

        String numeroAsunto = (value == null ? "" : ((String) value).trim());
        UIInput blnManifiesto = (UIInput) component.getAttributes().get("manifiestoUno");
        String blnManifiesto1 = blnManifiesto.getLocalValue() == null ? "" : blnManifiesto.getLocalValue().toString();

        // TODO Auto-generated method stub
        if (blnManifiesto1.equals("true") && numeroAsunto.equals("")) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("El n\u00FAmero de asunto es requerido");
            throw new ValidatorException(message);
        }
        else {
            matcher = pattern.matcher(value.toString());
            if (!matcher.matches()) {
                FacesMessage message = new FacesMessage();
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                message.setSummary("El n\u00famero de asunto no cumple con el formato esperado.");
                message.setDetail("El n\u00famero de asunto no cumple con el formato esperado.");
                throw new ValidatorException(message);
            }
        }
    }

}
