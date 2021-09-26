package mx.gob.sat.siat.juridica.ca.web.util.validador;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validator que implementa la logica de negocio para validar que se
 * ingrese un monto de tipo entero.
 * 
 * @author softtek
 * 
 */
@FacesValidator("montoEnteroValidator")
public class MontoEnteroValidator implements Validator {

    /**
     * Metodo que valida que se ingrese un monto de tipo entero.
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)  {

        String monto = (value == null ? "" : ((String) value).trim());
        if (!esEntero(monto)) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("El formato del monto de la operaci\u00F3n u operaciones objeto de la promoci\u00F3n es incorrecto.");
            throw new ValidatorException(message);
        }

    }

    /**
     * Metodo para verificar que una variable es de tipo entero.
     * 
     * @param cad
     * @return
     */
    public boolean esEntero(String cad) {
        for (int i = 0; i < cad.length(); i++) {
            if (!Character.isDigit(cad.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
