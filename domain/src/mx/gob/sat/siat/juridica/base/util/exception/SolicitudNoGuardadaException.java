/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.util.exception;

/**
 * 
 * @author softtek
 * 
 */
public class SolicitudNoGuardadaException extends BaseBussinessException {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1585419326318370025L;

    /**
     * Constructor
     * 
     * @param message
     */
    public SolicitudNoGuardadaException(String message) {
        super(message);
    }

    public SolicitudNoGuardadaException(String message, Throwable cause) {
        super(message, cause);
    }

}
