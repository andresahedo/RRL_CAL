package mx.gob.sat.siat.juridica.base.web.util.validador;

import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.ResolucionDataModel;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator("validatorAbogado")
public class EditarResolValidator implements Validator {

    private static final String RESOLUTION_PATTERN = "^[A-Z0-9\u00D1\\.\\(\\)\\-\\(\\s*)/]{1,35}$";
    private Pattern pattern;
    private Matcher matcher;

    public EditarResolValidator() {
        pattern = Pattern.compile(RESOLUTION_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)  {

        String resolucionImp = (String) value;
        Object msgFormato = component.getAttributes().get("msgFormato");

        if (!resolucionImp.equals("")) {
            matcher = pattern.matcher(resolucionImp);
            if (!matcher.matches()) {
                String msjFormato = (String) msgFormato;
                FacesMessage message = new FacesMessage();
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                message.setDetail(msjFormato);
                message.setSummary(msjFormato);
                throw new ValidatorException(message);
            }

            UIInput resolImpugnada = (UIInput) component.findComponent("tableResolucionInput");
            if (resolImpugnada != null) {
                String valorAnteriorResol = (String) resolImpugnada.getValue();
                String valorEdicionResol = (String) resolImpugnada.getSubmittedValue();
                String resIm = (String) value;
                UIData tabla = (UIData) component.getParent().findComponent("resolucionTable");
                ResolucionDataModel listaResoluciones = (ResolucionDataModel) tabla.getValue();

                for (ResolucionImpugnadaDTO resolucion : listaResoluciones) {

                    if (resIm.equals(resolucion.getResImpugnada()) && !valorAnteriorResol.equals(valorEdicionResol)) {
                        FacesMessage message = new FacesMessage();
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        message.setSummary("La resoluci\u00F3n " + value
                                + "  ya se encuentra registrada para el asunto que se est\u00E1 atendiendo.");
                        message.setDetail("La resoluci\u00F3n " + value
                                + "  ya se encuentra registrada para el asunto que se est\u00E1 atendiendo.");
                        throw new ValidatorException(message);

                    }

                }
            }

        }

    }

    public static String getResolutionPattern() {
        return RESOLUTION_PATTERN;
    }
}
