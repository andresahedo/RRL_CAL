/**
 * 
 */
package mx.gob.sat.siat.juridica.rrl.util.excepcion;

import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;

/**
 * @author softtek
 * 
 */
public class ParametrosRequerimientoAutoridadException extends BusinessException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constante que establece la categoria
     */

    private static final String CATEGORY = "bp";

    public ParametrosRequerimientoAutoridadException() {
        super();

    }

    public ParametrosRequerimientoAutoridadException(String situation, Object... args) {
        super(CATEGORY, situation, args);

    }

    public ParametrosRequerimientoAutoridadException(String situation, Throwable cause, Object... args) {
        super(CATEGORY, situation, cause, args);

    }

    public ParametrosRequerimientoAutoridadException(String situation, Throwable cause) {
        super(CATEGORY, situation, cause);

    }

    public ParametrosRequerimientoAutoridadException(String situation) {
        super(CATEGORY, situation);

    }

}
