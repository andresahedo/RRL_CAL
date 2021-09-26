package mx.gob.sat.siat.juridica.base.dao.domain.constants;

public enum TipoClasificacionArancelaria {

    CLASIFICACION_CLAS("CLA.CLAS",
            "Cuando considere que la mercanc\u00EDa puede clasificarse en dos o m\u00E1s fracciones arancelarias"),
    CLASIFICACION_CON("CLA.CON", "Cuando desee conocer la clasificaci\u00F3n arancelaria");

    /**
     * Atributo privado "clave" tipo String
     */
    private String clave;

    /**
     * Atributo privado "clave" tipo String
     */
    private String descripcion;

    /**
     * Constructor
     * 
     * @param clave
     *            a fijar
     * @param descripcion
     *            a fijar
     */
    private TipoClasificacionArancelaria(final String clave, final String descripcion) {
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

    public static TipoClasificacionArancelaria parse(String clave) {
        TipoClasificacionArancelaria right = null; 
        // Default

        for (TipoClasificacionArancelaria item : TipoClasificacionArancelaria.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }

        return right;
    }

}
