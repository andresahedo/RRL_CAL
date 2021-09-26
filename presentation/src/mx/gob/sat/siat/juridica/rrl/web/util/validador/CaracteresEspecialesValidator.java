package mx.gob.sat.siat.juridica.rrl.web.util.validador;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator("caracteresEspecialesValidator")
public class CaracteresEspecialesValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)  {

        String msgObligatorio = null;

        boolean isFormatOk = true;
        Object msgObligatorioValue = component.getAttributes().get("msgValidacion");
        if (msgObligatorioValue != null) {
            msgObligatorio = (String) msgObligatorioValue;
        }

        if (value != null && !value.equals("")) {
            String cadena = (String) value;
            Pattern charNoValidos = Pattern.compile("[\\u005E\\u0088\u0060\\|\u00AC\\u0022\\{\\}\\~\\'\\\\]");
            Matcher matchAct = charNoValidos.matcher(cadena);
            if (matchAct.find()) {
                isFormatOk = false;
            }
            if (!isFormatOk) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", msgObligatorio));
            }
        }
    }
}
