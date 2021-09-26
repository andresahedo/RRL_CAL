package mx.gob.sat.siat.juridica.rrl.web.util.validador;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Date;

@FacesValidator("calendarioFechasVaciasValidator")
public class CalendarioFechasVaciasValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {

        Date startDate = null;
        Date endDate = null;
        Object startDateValue = component.getAttributes().get("startDate");
        Object msgObligatorioValue = component.getAttributes().get("msgObligatorio");
        Object msgFechaFinValue = component.getAttributes().get("msgFechaFin");

        if (startDateValue != null) {
            startDate = (Date) startDateValue;
        }
        if (value != null) {
            endDate = (Date) value;
        }
        String msgObligatorio = (String) msgObligatorioValue;
        String msgFechaFin = (String) msgFechaFinValue;

        if (startDate == null || endDate == null || startDateValue == null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", msgObligatorio));
        }
        else if (endDate.before(startDate) || endDate.equals(startDate)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", msgFechaFin));
        }
    }
}
