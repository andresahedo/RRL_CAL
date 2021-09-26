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
 * Clase que maneja la excepcion de una tarea sin usuario asignado
 * 
 * @author softtek
 * 
 */
public class TereaSinUsuarioAsignadoException extends BPMException {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 4982869063681569421L;

    /**
     * Constructor de clase con solo el parametro de situacion
     * 
     * @param situation
     */
    public TereaSinUsuarioAsignadoException(String situation) {
        super(situation);
    }

    /**
     * Constructor de clase con el parametro de situacion y ninguno o
     * varios argumentos
     * 
     * @param situation
     * @param args
     */
    public TereaSinUsuarioAsignadoException(String situation, Object... args) {
        super(situation, args);
    }

}
