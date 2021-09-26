package mx.gob.sat.siat.juridica.base.dto;

public class InformacionUsuarioDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 4214544792372857747L;

    private String idUsuario;
    private String idRol;
    private boolean blnActivo;
    private boolean rolReplica;
    private String descripcionRol;
    private boolean asignado;

    /**
     * @return the idUsuario
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario
     *            the idUsuario to set
     */
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the idRol
     */
    public String getIdRol() {
        return idRol;
    }

    /**
     * @param idRol
     *            the idRol to set
     */
    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    /**
     * @return the blnActivo
     */
    public boolean isBlnActivo() {
        return blnActivo;
    }

    /**
     * @param blnActivo
     *            the blnActivo to set
     */
    public void setBlnActivo(boolean blnActivo) {
        this.blnActivo = blnActivo;
    }

    /**
     * @return the rolReplica
     */
    public boolean isRolReplica() {
        return rolReplica;
    }

    /**
     * @param rolReplica
     *            the rolReplica to set
     */
    public void setRolReplica(boolean rolReplica) {
        this.rolReplica = rolReplica;
    }

    /**
     * @return the descripcionRol
     */
    public String getDescripcionRol() {
        return descripcionRol;
    }

    /**
     * @param descripcionRol
     *            the descripcionRol to set
     */
    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }

    /**
     * @return the asignado
     */
    public boolean isAsignado() {
        return asignado;
    }

    /**
     * @param asignado
     *            the asignado to set
     */
    public void setAsignado(boolean asignado) {
        this.asignado = asignado;
    }

}
