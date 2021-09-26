package mx.gob.sat.siat.juridica.base.dao.domain.constants;

public enum TipoAutoridad {

    AUTORIDAD_HCH("TIAUT.HCH", "Autoridad que conocio el asunto"), AUTORIDAD_SUJETO("TIAUT.SUJ",
            "Autoridad que esta revisando");

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
    private TipoAutoridad(final String clave, final String descripcion) {
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

    public static TipoAutoridad parse(String clave) {
        TipoAutoridad right = null; 
        // Default

        for (TipoAutoridad item : TipoAutoridad.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }

        return right;
    }

}
