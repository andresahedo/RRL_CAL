/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.bpm.excepcion;

/**
 * Clase para el manejor de la no existencia de transicion para la
 * tarea del usuario
 * 
 * @author softtek
 * 
 */
public class NoExisteTransicionParaTareaUsuarioException extends BPMException {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 4982869063681569421L;

    /**
     * Constructor de clase con solo el parametro de situacion
     * 
     * @param situation
     */
    public NoExisteTransicionParaTareaUsuarioException(String situation) {
        super(situation);
    }

    /**
     * Constructor de clase con el parametro de situacion y ninguno o
     * varios argumentos
     * 
     * @param situation
     * @param args
     */
    public NoExisteTransicionParaTareaUsuarioException(String situation, Object... args) {
        super(situation, args);
    }

}
