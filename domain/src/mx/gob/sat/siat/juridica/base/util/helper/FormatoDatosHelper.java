/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.util.helper;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author softtek
 * 
 */
@Component
public class FormatoDatosHelper extends BaseHelper {

    /**
     * 
     */
    private static final long serialVersionUID = -2022537602700831321L;

    private Long longitudFraccion = Long.valueOf(NumerosConstantes.OCHO);
    private Pattern pat = Pattern.compile("[0-9]+");

    /**
     * Genera una cadena que representa una fraccion arancelaria con
     * el formato de mascara NNNN.NN.NN
     * 
     * @param fraccion
     *            Cadena que representa el dato numerico de una
     *            fraccion arancelaria
     * @return Devuelve una cadena con formato NNNN.NN.NN a partir de
     *         la fraccion de entrada. Si la cadena de entrada no es
     *         de longitud longitudFraccion y/o no se conforma
     *         s&oacute;lo por digitos, se devuelve la misma cadena de
     *         entrada
     */
    public String creaFraccionArancelariaConFormato(String fraccion) {
        if (fraccion == null) {
            return fraccion;
        }
        StringBuilder strb = new StringBuilder(fraccion);
        if (fraccion.length() == longitudFraccion && esCadenaNumerica(fraccion)) {
            strb.insert(NumerosConstantes.CUATRO, '.'); 
            // NNNN.NNNN
            strb.insert(NumerosConstantes.SIETE, '.'); 
            // NNNN.NN.NN
        }

        return strb.toString();
    }

    /**
     * enera una cadena que representa una fraccion arancelaria con el
     * formato de mascara NNNN.NN.NN
     * 
     * @param fraccion
     *            Numero que representa el dato numerico de una
     *            fraccion arancelaria
     * @return Devuelve una cadena con formato NNNN.NN.NN a partir del
     *         n&uacute;mero de entrada. Si el n&uacute;mero de
     *         entrada no reprsenta un valor de una fraccion, se
     *         devuelve fraccionl.toString()
     */
    public String creaFraccionArancelariaConFormato(Integer fraccion) {
        if (fraccion == null) {
            return null;
        }
        return creaFraccionArancelariaConFormato(fraccion.toString());
    }

    public boolean esCadenaNumerica(String str) {
        boolean esNumerica = false;
        if (str != null && !str.isEmpty()) {
            Matcher matcher = pat.matcher(str);
            esNumerica = matcher.matches();
        }
        return esNumerica;
    }
}
