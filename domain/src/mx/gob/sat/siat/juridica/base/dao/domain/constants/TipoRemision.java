package mx.gob.sat.siat.juridica.base.dao.domain.constants;

public enum TipoRemision {

    /**
     * Estado de un tipo de requerimiento
     */
    AUTORIDAD_PROPIA("TIPREM.AUIN", "Autoridad propia"),
    AUTORIDAD_INT_EXT("TIPREM.AUEX", "Autoridad interna o externa");

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
    private TipoRemision(final String clave, final String descripcion) {
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
    public static TipoRemision parse(String clave) {
        TipoRemision right = null;
        for (TipoRemision item : TipoRemision.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }
        return right;
    }

}
