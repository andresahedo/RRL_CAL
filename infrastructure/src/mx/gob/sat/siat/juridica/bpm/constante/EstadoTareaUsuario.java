/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.bpm.constante;

/**
 * 
 * @author softtek
 * 
 */
public enum EstadoTareaUsuario {

    DISPONIBLE("EST.TAU.DISP", "Disponible"), ACTIVA("EST.TAU.ACT", "Activa"), COMPLETADA("EST.TAU.COM", "Completada"),
    REASIGNADA("EST.TAU.REA", "Reasignada"), ABORTADA("EST.TAU.ABO", "Abortada");

    /**
     * Clave de la tarea
     */
    private String clave;
    /**
     * Descripcion de la tarea
     */
    private String descripcion;

    /**
     * Constructor del enumerador
     * 
     * @param clave
     * @param descripcion
     */
    private EstadoTareaUsuario(final String clave, final String descripcion) {
        this.clave = clave;
        this.descripcion = descripcion;
    }

    /**
     * 
     * @return clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * 
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Metodo que busca y devuelve el tipo de estado de la tarea del
     * usuario
     * 
     * @param clave
     * @return right
     */
 // Default    
    public static EstadoTareaUsuario parse(String clave) {
        EstadoTareaUsuario right = null; 
        for (EstadoTareaUsuario item : EstadoTareaUsuario.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }
        return right;
    }

}
