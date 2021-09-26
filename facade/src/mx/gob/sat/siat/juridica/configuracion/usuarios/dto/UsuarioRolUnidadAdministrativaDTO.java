/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;

/**
 * 
 * @author softtek
 * 
 */
public class UsuarioRolUnidadAdministrativaDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = -3870476637652697133L;

    private Long idUsuarioRolUA;
    private String rol;
    private UnidadAdministrativaDTO unidadAdministrativa;
    private String idUsuario;
    private boolean uniadminPrincipal;
    private String numeroEmpleado;
    private String rfcEmpleado;
    private String nombreEmpleado;
    private String tipoTramite;
    private String modalidad;
    private String permiso;
    private String responsable;
    private String descripcionRol;
    private boolean blnActivo;

    /**
     * @return the idUsuarioRolUA
     */
    public Long getIdUsuarioRolUA() {
        return idUsuarioRolUA;
    }

    /**
     * @param idUsuarioRolUA
     *            the idUsuarioRolUA to set
     */
    public void setIdUsuarioRolUA(Long idUsuarioRolUA) {
        this.idUsuarioRolUA = idUsuarioRolUA;
    }

    /**
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * @param rol
     *            the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * @return the unidadAdministrativa
     */
    public UnidadAdministrativaDTO getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * @param unidadAdministrativa
     *            the unidadAdministrativa to set
     */
    public void setUnidadAdministrativa(UnidadAdministrativaDTO unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

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
     * @return the uniadminPrincipal
     */
    public boolean isUniadminPrincipal() {
        return uniadminPrincipal;
    }

    /**
     * @param uniadminPrincipal
     *            the uniadminPrincipal to set
     */
    public void setUniadminPrincipal(boolean uniadminPrincipal) {
        this.uniadminPrincipal = uniadminPrincipal;
    }

    /**
     * @return the numeroEmpleado
     */
    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    /**
     * @param numeroEmpleado
     *            the numeroEmpleado to set
     */
    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    /**
     * @return the rfcEmpleado
     */
    public String getRfcEmpleado() {
        return rfcEmpleado;
    }

    /**
     * @param rfcEmpleado
     *            the rfcEmpleado to set
     */
    public void setRfcEmpleado(String rfcEmpleado) {
        this.rfcEmpleado = rfcEmpleado;
    }

    /**
     * @return the nombreEmpleado
     */
    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    /**
     * @param nombreEmpleado
     *            the nombreEmpleado to set
     */
    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    /**
     * @return the tipoTramite
     */
    public String getTipoTramite() {
        return tipoTramite;
    }

    /**
     * @param tipoTramite
     *            the tipoTramite to set
     */
    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    /**
     * @return the modalidad
     */
    public String getModalidad() {
        return modalidad;
    }

    /**
     * @param modalidad
     *            the modalidad to set
     */
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * @return the permiso
     */
    public String getPermiso() {
        return permiso;
    }

    /**
     * @param permiso
     *            the permiso to set
     */
    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    /**
     * @return the responsable
     */
    public String getResponsable() {
        return responsable;
    }

    /**
     * @param responsable
     *            the responsable to set
     */
    public void setResponsable(String responsable) {
        this.responsable = responsable;
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

}
