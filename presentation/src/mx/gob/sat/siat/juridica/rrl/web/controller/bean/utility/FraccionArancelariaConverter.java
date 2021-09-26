package mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility;

import mx.gob.sat.siat.juridica.base.util.helper.FormatoDatosHelper;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("fraccionArancelariaConverter")
public class FraccionArancelariaConverter implements Converter {

    private FormatoDatosHelper formatoDatosHelper = new FormatoDatosHelper();

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        String nuevoValor = null;
        Integer num = null;
        if (arg2 == null) {
            return arg2;
        }
        nuevoValor = arg2.trim();
        if (nuevoValor.contains(".")) {
            try {
                num = Integer.parseInt(getNumbersFromString(nuevoValor));
            }
            catch (NumberFormatException nfe) {
                FacesMessage errMsg = new FacesMessage("El dato contiene un formato incorrecto");
                FacesContext.getCurrentInstance().addMessage(null, errMsg);
                throw new ConverterException(errMsg.getSummary(), nfe);
            }
        }
        return num;

    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        String fraccion = "";
        if (arg2 instanceof String && !arg2.equals("")) {

            synchronized (formatoDatosHelper) {
                fraccion = formatoDatosHelper.creaFraccionArancelariaConFormato((String) arg2);
            }
        }

        return fraccion;
    }

    private String getNumbersFromString(String strNum) {
        StringBuilder strb = new StringBuilder();
        if (strNum == null) {
            return strNum;
        }
        for (int i = 0; i < strNum.length(); i++) {
            if (strNum.charAt(i) != '.') {
                strb.append(strNum.charAt(i));
            }
        }

        return strb.toString();
    }

    public FormatoDatosHelper getFormatoDatosHelper() {
        return formatoDatosHelper;
    }

    public void setFormatoDatosHelper(FormatoDatosHelper formatoDatosHelper) {
        this.formatoDatosHelper = formatoDatosHelper;
    }
}
