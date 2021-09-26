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

/**
 * Validator que implementa la logica de negocio para validar los
 * sentidos de resolucion.
 * 
 * @author Softtek
 * 
 */
@FacesValidator("sentidosResolucionCAValidator")
public class SentidosResolucionValidator implements Validator {
    /**
     * Metodo para validar los sentidos de resolucion.
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {

        String sentidoResolucionSelect = (value == null ? "" : ((String) value).trim());
        UIInput blnManifiesto = (UIInput) component.getAttributes().get("manifiestoUno");
        String blnManifiesto1 = blnManifiesto.getLocalValue() == null ? "" : blnManifiesto.getLocalValue().toString();

        if (blnManifiesto1.equals("true") && sentidoResolucionSelect.equals("")) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("El sentido de la resoluci\u00F3n es requerido");
            throw new ValidatorException(message);
        }

    }

}
