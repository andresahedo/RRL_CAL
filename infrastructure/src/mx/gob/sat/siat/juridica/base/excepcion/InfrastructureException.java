/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.excepcion;

/**
 * Clase que define las excepciones de infraestructura
 * 
 * @author softtek
 * 
 */
public class InfrastructureException extends RuntimeException {
    /**
     * Numero de version
     */
    private static final long serialVersionUID = 5354510507216821858L;

    /**
     * constructor de la clase
     */
    public InfrastructureException() {
        super();
    }

    /**
     * Metodo pasa a la clase padre el mensaje de excepcion
     * 
     * @param message
     */
    public InfrastructureException(final String message) {
        super(message);
    }

    /**
     * Metodo pasa a la clase padre la causa de la excepcion
     * 
     * @param cause
     */
    public InfrastructureException(final Throwable cause) {
        super(cause);
    }

    /**
     * Metodo para enviar el mensaje de excepcion y la causa de la
     * excepcion
     * 
     * @param message
     * @param cause
     */
    public InfrastructureException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
