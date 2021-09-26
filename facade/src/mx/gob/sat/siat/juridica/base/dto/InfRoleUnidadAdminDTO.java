package mx.gob.sat.siat.juridica.base.dto;

public class InfRoleUnidadAdminDTO extends BaseDataTransferObject {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 4045910608617613642L;

    private String nombreUnidad;
    private String numeroEmpleado;
    private String rfcUsuario;
    private String nombreEmpleado;
    private String tipoTramite;
    private String modalidad;
    private String idUsuarioRolUA;
    private Boolean responsable;
    private String responsableMsg;
    private String permiso;
    private Integer blnActivo;
    private Integer idTipoTramite;

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getRfcUsuario() {
        return rfcUsuario;
    }

    public void setRfcUsuario(String rfcUsuario) {
        this.rfcUsuario = rfcUsuario;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getIdUsuarioRolUA() {
        return idUsuarioRolUA;
    }

    public void setIdUsuarioRolUA(String idUsuarioRolUA) {
        this.idUsuarioRolUA = idUsuarioRolUA;
    }

    public Boolean getResponsable() {
        return responsable;
    }

    public void setResponsable(Boolean responsable) {
        this.responsable = responsable;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    public String getResponsableMsg() {
        return responsableMsg;
    }

    public void setResponsableMsg(String responsableMsg) {
        this.responsableMsg = responsableMsg;
    }

    /**
     * @return the blnActivo
     */
    public Integer getBlnActivo() {
        return blnActivo;
    }

    /**
     * @param blnActivo
     *            the blnActivo to set
     */
    public void setBlnActivo(Integer blnActivo) {
        this.blnActivo = blnActivo;
    }

    /**
     * @return the idTipoTramite
     */
    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    /**
     * @param idTipoTramite
     *            the idTipoTramite to set
     */
    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

}
