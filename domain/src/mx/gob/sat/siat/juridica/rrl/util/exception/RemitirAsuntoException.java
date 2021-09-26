/**
 * 
 */
package mx.gob.sat.siat.juridica.rrl.util.exception;

import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;

/**
 * @author softtek
 * 
 */
public class RemitirAsuntoException extends BusinessException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constante que establece la categoria
     */

    private static final String CATEGORY = "remitir";

    public RemitirAsuntoException() {
        super();

    }

    public RemitirAsuntoException(String situation, Object... args) {
        super(CATEGORY, situation, args);

    }

    public RemitirAsuntoException(String situation, Throwable cause, Object... args) {
        super(CATEGORY, situation, cause, args);

    }

    public RemitirAsuntoException(String situation, Throwable cause) {
        super(CATEGORY, situation, cause);

    }

    public RemitirAsuntoException(String situation) {
        super(CATEGORY, situation);

    }

}
