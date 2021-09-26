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
 * Class to implement validations into generar resolucion flow from
 * juridica.
 * 
 * @author Softtek edgar.mendoza
 * @date: 16/05/2014
 */
@FacesValidator("numeroOficioResolucionValidator")
public class NumeroOficioResolucionValidator implements Validator {

    /**
     * M&eacute;todo para validar que el formarto del numero de oficio
     * de resolucion acepte solo letras, numeros y el guion
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)  {

        String numeroOficio = (value == null ? "" : ((String) value).trim());

        boolean requieredOk = true;
        Pattern pat =
                Pattern.compile("^[a-zA-Z0-9\u00C1\u00C9\u00CD\u00D3\u00DA\u00E1\u00E9\u00ED\u00F3\u00FA\u00C4\u00CB\u00CF\u00D6\u00DC\u00E4\u00EB\u00EF\u00F6\u00FC\u00D1\u00F1-]*$");
        Matcher mat = pat.matcher(numeroOficio);
        if (!mat.matches()) {
            requieredOk = false;
        }

        if (!requieredOk) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("El formato de los datos capturados es incorrecto");

            throw new ValidatorException(message);
        }
    }

}
