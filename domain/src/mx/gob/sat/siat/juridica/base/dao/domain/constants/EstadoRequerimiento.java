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
public enum EstadoRequerimiento {

    /**
     * Estados de un requerimiento
     */
    GENERADO("ESTREQ.GN", "Generado"), REGISTRADO("ESTREQ.RG", "Requerido Notificado"), AUTORIZADO("ESTREQ.AU",
            "Requerido"), ELIMINADO("ESTREQ.EL", "Eliminado"), ATENDIDO("ESTREQ.AT", "Atendido"),
    NO_ATENDIDO("ESTREQ.NA", "No Atendido"), CONCLUIDO("ESTREQ.CN", "Concluido"), RECHAZADO("ESTREQ.RZ", "Rechazado");

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
    private EstadoRequerimiento(final String clave, final String descripcion) {
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
     * Metodo para parsear el Estado de un Requerimiento
     * 
     * @param clave
     * @return right
     */
    public static EstadoRequerimiento parse(String clave) {
        EstadoRequerimiento right = null;
        for (EstadoRequerimiento item : EstadoRequerimiento.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }
        return right;
    }
}
