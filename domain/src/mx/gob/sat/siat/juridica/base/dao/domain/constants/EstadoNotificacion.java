/*
 *  Todos los Derechos Reservados © 2013 SAT
 *  Servicio de Administracion Tributaria (SAT).
 *  
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.constants;

/**
 * 
 * @author softtek
 * 
 */
public enum EstadoNotificacion {

    /**
     * Estados de una notificacion
     */
    EMITIDA("ESTNOT.EM", "Emitida"), NOTIFICADO_SISTEMA("ESTNOT.NS", "Notificado por Sistema"),
    NOTIFICADO_CONTRIBUYENTE("ESTNOT.NC", "Notificado por Contribuyente");

    /**
     * Atributo privado "clave" tipo String
     */
    private String clave;

    /**
     * Atributo privado "descripcion" tipo String
     */
    private String descripcion;

    /**
     * Constructor
     * 
     * @param clave
     * @param descripcion
     */
    private EstadoNotificacion(final String clave, final String descripcion) {
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
     * @param clave
     *            a fijar
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * 
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * 
     * @param descripcion
     *            a fijar
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Metodo que parsea el estado de una notificacion
     * 
     * @param clave
     * @return right
     */
    public static EstadoNotificacion parse(String clave) {
        EstadoNotificacion right = null;
        for (EstadoNotificacion item : EstadoNotificacion.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }
        return right;
    }

}
