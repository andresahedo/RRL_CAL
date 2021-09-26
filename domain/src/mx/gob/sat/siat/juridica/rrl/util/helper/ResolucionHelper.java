/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.util.helper;

import mx.gob.sat.siat.juridica.base.dao.domain.model.ResolucionImpugnada;
import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Component
public class ResolucionHelper extends BaseHelper {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1218822943043637109L;

    /**
     * Metodo para concatenar ids
     * 
     * @param resolucionesImpugnadas
     * @return sbFinal
     */
    public String concatenaIds(List<ResolucionImpugnada> resolucionesImpugnadas) {
        StringBuffer sb = new StringBuffer();
        StringBuffer sbFinal = new StringBuffer();
        if (resolucionesImpugnadas != null && !resolucionesImpugnadas.isEmpty()) {
            sb.append("(");
            for (ResolucionImpugnada resolucionImpugnada : resolucionesImpugnadas) {
                sb.append(resolucionImpugnada.getIdResolucionImpugnada());
                sb.append(", ");
            }
            sbFinal.append(sb.substring(0, sb.length() - 2));
            sbFinal.append(")");
        }

        return sbFinal.toString();
    }
}
