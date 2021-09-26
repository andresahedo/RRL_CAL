package mx.gob.sat.siat.juridica.rrl.web.util.validador;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator para el Numero de Empleado
 * 
 * @author softtek - EQG
 * @since 06/12/2014
 */
@FacesValidator("numeroEmpleadoValidator")
public class NumeroEmpleadoValidator implements Validator {

    /**
     * Metodo para validar el Numero de Empleado
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)  {
        String numeroEmpleado = null;
        Object msgObligatorioValue = component.getAttributes().get("msgNumeroEmpleado");
        String msgObligatorio = null;
        if (msgObligatorioValue != null) {
            msgObligatorio = (String) msgObligatorioValue;
        }
        if (value != null && !value.equals("")) {
            numeroEmpleado = (String) value;
            Pattern pat = Pattern.compile("[0-9]");
            Matcher mat = pat.matcher(numeroEmpleado);
            if (!mat.matches()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", msgObligatorio != null
                        ? msgObligatorio : "Este campo solo acepta n\u00f9meros del 0 al 9."));
            }
        }

    }
}
