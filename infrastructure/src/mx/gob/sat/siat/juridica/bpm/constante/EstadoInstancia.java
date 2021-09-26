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
 * Enumerador que representa los estados de las instancias
 * 
 * @author softtek
 * 
 */
public enum EstadoInstancia {

    ACTIVA("EST.INS.ACT", "Activa"), TERMINADA("EST.INS.TM", "Terminada");

    /**
     * Clave del estado
     */
    private String clave;
    /**
     * Descripcion del estado
     */
    private String descripcion;

    /**
     * Constructor del enumerador
     * 
     * @param clave
     * @param descripcion
     */
    private EstadoInstancia(final String clave, final String descripcion) {
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
     * Metodo que busca y devuelve el tipo de estado de la instancia
     * 
     * @param clave
     * @return
     */
 // Default
    public static EstadoInstancia parse(String clave) {
        EstadoInstancia right = null; 
        for (EstadoInstancia item : EstadoInstancia.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }
        return right;
    }

}
