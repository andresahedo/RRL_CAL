package mx.gob.sat.siat.juridica.rrl.web.util.validador;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator("rfcValidatorOficialia")
public class RFCValidatorOficialia implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        String rfc = null;
        Object msgObligatorioValue = component.getAttributes().get("msgRFC");
        String msgObligatorio = null;
        if (msgObligatorioValue != null) {
            msgObligatorio = (String) msgObligatorioValue;
        }
        if (value != null && !value.equals("")) {
            rfc = (String) value;
            Pattern pat =
                    Pattern.compile("[A-Za-z0-9\u00D1\u00F1&�\\_\\+\\.\\,\\-]{4}[A-Za-z0-9\u00D1\u00F1&�\\_\\+\\.\\,\\-]{0,10}");
            Matcher mat = pat.matcher(rfc);
            if (!mat.matches()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", msgObligatorio != null
                        ? msgObligatorio : "Existen datos incorrectos que no cumplen con el formato esperado."));
            }
        }
    }
}
