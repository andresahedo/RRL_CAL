/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.util.validador;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRequerimiento;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author Softtek
 * 
 */
@FacesValidator("generarRequerimientoValidator")
public class GenerarRequerimientoValidator implements Validator {

    /**
     * M&eacute;todo para validar que sean requeridas las autoridades
     * s&oacute;lo si el Tipo de Requerimiento es A Autoridad
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)  {

        String autoridad = (value == null ? "" : ((String) value).trim());
        UIInput tipoRequerimiento = (UIInput) component.getAttributes().get("tipoRequerimientoSelect");
        String claveTipoRequerimiento =
                tipoRequerimiento.getLocalValue() == null ? "" : tipoRequerimiento.getLocalValue().toString();

        if (claveTipoRequerimiento.equals(TipoRequerimiento.AUTORIDAD.getClave()) && autoridad.equals("")) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("El campo Autoridad a la que se solicita es requerido");
            message.setDetail("El campo Autoridad a la que se solicita es requerido");
            throw new ValidatorException(message);
        }
    }

}
