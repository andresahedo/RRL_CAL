/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.bpm.excepcion;

import mx.gob.sat.siat.juridica.base.excepcion.InfrastructureException;

/**
 * Clase que maneja la excepcion de XML
 * 
 * @author softtek
 * 
 */
public class XMLException extends InfrastructureException {
    /**
     * Numero de version
     */
    private static final long serialVersionUID = 456989927099318525L;

    /**
     * Constructor de clase sin parametros
     */
    public XMLException() {
        super();
    }

    /**
     * Constructor de clase con solo el parametro de mensaje
     * 
     * @param message
     */
    public XMLException(final String message) {
        super(message);
    }

    /**
     * Constructor de clase con solo el parametro de causa
     * 
     * @param cause
     */
    public XMLException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructor de clase con solo el parametro de mensaje y de
     * causa
     * 
     * @param message
     * @param cause
     */
    public XMLException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
