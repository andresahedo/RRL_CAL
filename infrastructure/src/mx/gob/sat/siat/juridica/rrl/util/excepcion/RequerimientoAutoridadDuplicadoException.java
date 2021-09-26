package mx.gob.sat.siat.juridica.rrl.util.excepcion;

import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;

public class RequerimientoAutoridadDuplicadoException extends BusinessException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constante que establece la categoria.
     */
    private static final String CATEGORY = "bp";

    public RequerimientoAutoridadDuplicadoException() {
        super();

    }

    public RequerimientoAutoridadDuplicadoException(String situation, Object... args) {
        super(CATEGORY, situation, args);

    }

    public RequerimientoAutoridadDuplicadoException(String situation, Throwable cause, Object... args) {
        super(CATEGORY, situation, cause, args);

    }

    public RequerimientoAutoridadDuplicadoException(String situation, Throwable cause) {
        super(CATEGORY, situation, cause);

    }

    public RequerimientoAutoridadDuplicadoException(String situation) {
        super(CATEGORY, situation);

    }

}
