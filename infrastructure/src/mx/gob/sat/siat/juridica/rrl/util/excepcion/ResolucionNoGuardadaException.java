/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.util.excepcion;

import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;

public class ResolucionNoGuardadaException extends BusinessException {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constante que establece la categoria
     */
    private static final String CATEGORY = "bp";

    /**
     * Constructor de clase solo con el parametro de situacion
     * 
     * @param situation
     */
    public ResolucionNoGuardadaException(String situation) {
        super(CATEGORY, situation);
    }

    /**
     * Constructor de clase con el parametro de situacion y ninguno o
     * varios argumentos
     * 
     * @param situation
     * @param args
     */
    public ResolucionNoGuardadaException(String situation, Object... args) {
        super(CATEGORY, situation, args);
    }

    /**
     * Constructor de clase con los parametros de situacion y causa
     * 
     * @param situation
     * @param cause
     */
    public ResolucionNoGuardadaException(String situation, Throwable cause) {
        super(CATEGORY, situation, cause);
    }

    /**
     * Constructor de clase con los parametros de situacion, causa y
     * ninguno o varios argumentos
     * 
     * @param situation
     * @param cause
     * @param args
     */
    public ResolucionNoGuardadaException(String situation, Throwable cause, Object... args) {
        super(CATEGORY, situation, cause, args);
    }

}
