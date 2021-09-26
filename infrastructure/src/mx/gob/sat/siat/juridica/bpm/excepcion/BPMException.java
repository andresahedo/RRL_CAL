/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.bpm.excepcion;

import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;

/**
 * Clase para manejo de excepciones de BP
 * 
 * @author softtek
 * 
 */
public class BPMException extends BusinessException {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constante de categoria
     */
    private static final String CATEGORY = "bp";

    /**
     * Constructor de clase con solo el parametro de situacion
     * 
     * @param situation
     */
    public BPMException(String situation) {
        super(CATEGORY, situation);
    }

    /**
     * Constructor de clase con el parametro de situacion y ninguno o
     * varios argumentos
     * 
     * @param situation
     * @param args
     */
    public BPMException(String situation, Object... args) {
        super(CATEGORY, situation, args);
    }

    /**
     * Constructor de clase con parametros de situacion y de causa
     * 
     * @param situation
     * @param cause
     */
    public BPMException(String situation, Throwable cause) {
        super(CATEGORY, situation, cause);
    }

    /**
     * Constructor de clase con parametros de situacion, causa y
     * ninguno o varios argumentos
     * 
     * @param situation
     * @param cause
     * @param args
     */
    public BPMException(String situation, Throwable cause, Object... args) {
        super(CATEGORY, situation, cause, args);
    }

}
