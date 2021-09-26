package mx.gob.sat.siat.juridica.base.dao.domain.constants;

public enum TareaOrigen {

    /**
     * Contiene los tipos de tarea que originaron la Remision
     */
    TURNAR_ASUNTO("TAROR.TRN", "Turnar Asunto"), ATENDER_ASUNTO("TAROR.ATR", "Atender Asunto");

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
    private TareaOrigen(final String clave, final String descripcion) {
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
    public static TareaOrigen parse(String clave) {
        TareaOrigen right = null;
        for (TareaOrigen item : TareaOrigen.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }
        return right;
    }

}
