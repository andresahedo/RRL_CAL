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
public enum TipoMensajeBPM {

    /**
     * Estado de un tramite
     */
    INICIAR_TRAMITE("BPM.INI", "Iniciar tarea"), INICIAR_TRAMITE_JSON(
            "BPM.INJ", "Iniciar tarea json"), COMPLETAR_TAREA("BPM.COM",
            "Completar tarea"), COMPLETAR_TAREA_JSON("BPM.CMJ",
            "Completar tarea JSON"), ACTUALIZAR_DATOS("BPM.ACT",
            "Actualizar datos instancia"), REASIGNAR_TAREA("BPM.REA",
            "Reasignar tarea"), RECHAZO_TAREA("EST_TRANS_AL2", "Rechazar tarea"), ADHOC_ACTUALIZAR(
            "BPM.AHA", "Actualizar variable via Adhoc"), EJECUTAR_JS("BPM.EJS",
            "Ejecutar JavaScript"), COMPLETAR_TAREA_AUTOMATICA("BPM.CMA",
                    "Completar tarea NyV");

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
    private TipoMensajeBPM(final String clave, final String descripcion) {
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

}
