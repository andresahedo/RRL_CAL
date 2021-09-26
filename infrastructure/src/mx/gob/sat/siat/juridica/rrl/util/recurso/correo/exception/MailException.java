/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.util.recurso.correo.exception;

/**
 * Clase que maneja las excepciones de correo
 * 
 * @author softtek
 * 
 */
public class MailException extends Exception {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 3649157174485951161L;

    /**
     * Constructor de clase con el parametro de mensaje y causa de la
     * excepcion
     * 
     * @param message
     * @param cause
     */
    public MailException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor de clase con el parametro de mensaje de la
     * excepcion
     * 
     * @param message
     */
    public MailException(final String message) {
        super(message);
    }

    /**
     * Constructor de clase con el parametro de causa de la excepcion
     */
    public MailException(final Throwable cause) {
        super(cause);
    }

}
