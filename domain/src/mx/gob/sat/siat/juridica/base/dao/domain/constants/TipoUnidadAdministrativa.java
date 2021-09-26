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
public enum TipoUnidadAdministrativa {

    /**
     * Estados del tipo de unidad administrativa
     */
    ADMINISTRACION_CENTRAL("TIUAD.ADC", "Administraci\u00F3n Central"), ADMINISTRACION_EXTERNA("TIUAD.ADEX",
            "Administraci\u00F3n Externa"), ADMINISTRACION_GENERAL("TIUAD.ADG", "Administraci\u00F3n General"),
    ADMINISTRACION_LOCAL("TIUAD.ADL", "Administraci\u00F3n Local"), ADMINISTRACION_INTERNA("TIUAD.INT",
            "Administraci\u00F3n Interna");

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
    private TipoUnidadAdministrativa(final String clave, final String descripcion) {
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
     * Metod que parsea el Tipo de Unidad Administrativa
     * 
     * @param clave
     * @return right
     */
    public static TipoUnidadAdministrativa parse(String clave) {
        TipoUnidadAdministrativa right = null;
        for (TipoUnidadAdministrativa item : TipoUnidadAdministrativa.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }
        return right;
    }

}
