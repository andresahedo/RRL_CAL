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
public enum TipoRequerimiento {

    /**
     * Estado de un tipo de requerimiento
     */
    AUTORIDAD("TIREQ.RIA", "Autoridad"), CONTRIBUYENTE("TIREQ.RIP", "Promovente"), AUTORIDAD_INTERNA("TIREQ.RAI",
            "Autoridad Interna"), RETROALIMENTACION_CAL("REQCAL.RTR", "Retroalimentaci\u00F3n"),
    PROMOVENTE_CAL("REQCAL.PR", "Promovente");

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
    private TipoRequerimiento(final String clave, final String descripcion) {
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
     * Metodo que parsea un Tipo de Requerimiento
     * 
     * @param clave
     * @return right
     */
    public static TipoRequerimiento parse(String clave) {
        TipoRequerimiento right = null;
        for (TipoRequerimiento item : TipoRequerimiento.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }
        return right;
    }

}
