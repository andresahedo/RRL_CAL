package mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.math.BigDecimal;

@FacesConverter("bigDecimalConverter")
public class BigDecimalConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        try {
            double d = Double.parseDouble(arg2);
            return new BigDecimal(d).doubleValue();
        }
        catch (NumberFormatException nfe) {
            return arg2;
        }

    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        if (!arg2.equals("")) {
            return ((BigDecimal) arg2).toString();
        }
        else {
            return "";
        }
    }
}
