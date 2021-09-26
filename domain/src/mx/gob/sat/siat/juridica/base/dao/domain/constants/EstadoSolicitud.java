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
public enum EstadoSolicitud {

    /**
     * Estados de una solicitud
     */
    FAVORABLE("ESTSOL.FV", "Favorable"), RECIBIDA("ESTSOL.RC", "Recibida"), REGISTRADA("ESTSOL.RG", "Registrada"),
    DESFAVORABLE("ESTSOL.DF", "Desfavorable"), ELIMINADA("ESTSOL.EL", "Eliminada");

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
    private EstadoSolicitud(final String clave, final String descripcion) {
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
     * Metodo que parsea el estado de una solicitud
     * 
     * @param clave
     * @return right
     */
    public static EstadoSolicitud parse(String clave) {
        EstadoSolicitud right = null; 
        // Default

        for (EstadoSolicitud item : EstadoSolicitud.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }

        return right;
    }
}
