package mx.gob.sat.siat.juridica.base.dto;

public class ModalidadesDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = -3013802578072290810L;

    private String id;
    private String descripcion;
    private String asignacion;
    private String unidadAdminResponsable;
    private String idModulo;
    private String tooltip;

    public ModalidadesDTO() {}

    public ModalidadesDTO(String id, String descripcion, String asignacion) {
        this.id = id;
        this.descripcion = descripcion;
        this.asignacion = asignacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(String asignacion) {
        this.asignacion = asignacion;
    }

    /**
     * @return the unidadAdminResponsable
     */
    public String getUnidadAdminResponsable() {
        return unidadAdminResponsable;
    }

    /**
     * @param unidadAdminResponsable
     *            the unidadAdminResponsable to set
     */
    public void setUnidadAdminResponsable(String unidadAdminResponsable) {
        this.unidadAdminResponsable = unidadAdminResponsable;
    }

    /**
     * @return the idModulo
     */
    public String getIdModulo() {
        return idModulo;
    }

    /**
     * @param idModulo
     *            the idModulo to set
     */
    public void setIdModulo(String idModulo) {
        this.idModulo = idModulo;
    }

    /**
     * @return the tooltip
     */
    public String getTooltip() {
        return tooltip;
    }

    /**
     * @param tooltip
     *            the tooltip to set
     */
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

}
