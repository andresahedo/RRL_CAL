/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.util.exception;

import mx.gob.sat.siat.juridica.base.util.exception.BaseBussinessException;

/**
 * 
 * @author softtek
 * 
 */
public class ArchivoNoGuardadoException extends BaseBussinessException {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     * 
     * @param message
     */
    public ArchivoNoGuardadoException(String message) {
        super(message);
    }

}
