package mx.gob.sat.siat.juridica.rrl.web.util.validador;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRequerimiento;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("generarRequerimientoCalValidator")
public class GenerarRequerimientoCalValidator implements Validator {

    @Override
    public void validate(FacesContext arg0, UIComponent component, Object value)  {

        String autoridad = (value == null ? "" : ((String) value).trim());
        UIInput tipoRequerimiento = (UIInput) component.getAttributes().get("tipoRequerimientoSelect");
        String claveTipoRequerimiento =
                tipoRequerimiento.getLocalValue() == null ? "" : tipoRequerimiento.getLocalValue().toString();

        if (claveTipoRequerimiento.equals(TipoRequerimiento.RETROALIMENTACION_CAL.getClave()) && autoridad.equals("")) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail("Este dato es requerido");
            throw new ValidatorException(message);
        }
    }

}
