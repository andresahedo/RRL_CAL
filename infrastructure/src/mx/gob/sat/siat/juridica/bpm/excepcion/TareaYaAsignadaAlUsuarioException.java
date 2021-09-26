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
 * Clase para el manejo de excepcion de una tarea ya asignada a un
 * usuario
 * 
 * @author softtek
 * 
 */
public class TareaYaAsignadaAlUsuarioException extends BPMException {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 4982869063681569421L;

    /**
     * Constructor de clase con solo el parametro de situacion
     * 
     * @param situation
     */
    public TareaYaAsignadaAlUsuarioException(String situation) {
        super(situation);
    }

    /**
     * Constructor de clase con el parametro de situacion y ninguno o
     * varios argumentos
     * 
     * @param situation
     * @param args
     */
    public TareaYaAsignadaAlUsuarioException(String situation, Object... args) {
        super(situation, args);
    }

}
