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
 * Validator para implementar la logica de negocio para validar que no
 * este vacio el campo de plazo.
 * 
 * @author Softtek
 * 
 */
@FacesValidator("plazoCAValidator")
public class PlazoCAValidator implements Validator {
    /**
     * Metodo para validar que el campo de plazo no este vacio y envie
     * un mensaje al usuario que es un campo requerido.
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)  {

        String plazo = (value == null ? "" : ((String) value).trim());
        UIInput blnManifiesto = (UIInput) component.getAttributes().get("manifiestoDos");
        String blnManifiesto1 = blnManifiesto.getLocalValue() == null ? "" : blnManifiesto.getLocalValue().toString();

        if (blnManifiesto1.equals("true") && plazo.equals("")) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("El plazo para que la autoridad emita la resoluci\u00F3n es requerido");
            throw new ValidatorException(message);
        }
    }

}
