package mx.gob.sat.siat.juridica.configuracion.usuarios.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;

public class AdminUsuariosDTO extends BaseDataTransferObject {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 5425348962739624909L;

    private String idUnidadAmin;
    private String rfcUsuario;
    private String permiso;
    private String unidadAdministrativa;
    private String nombreEmpleado;
    private String idPermiso;
    private boolean blnIsAdminGlobal;
    private Long numeroEmpleado;
    private boolean blnGlobalUnidadAdmin;

    public String getIdUnidadAmin() {
        return idUnidadAmin;
    }

    public void setIdUnidadAmin(String idUnidadAmin) {
        this.idUnidadAmin = idUnidadAmin;
    }

    public String getRfcUsuario() {
        return rfcUsuario;
    }

    public void setRfcUsuario(String rfcUsuario) {
        this.rfcUsuario = rfcUsuario;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    public boolean isBlnIsAdminGlobal() {
        return blnIsAdminGlobal;
    }

    public void setBlnIsAdminGlobal(boolean blnIsAdminGlobal) {
        this.blnIsAdminGlobal = blnIsAdminGlobal;
    }

    public Long getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(Long numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(String unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    public String getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(String idPermiso) {
        this.idPermiso = idPermiso;
    }

    /**
     * @return the blnGlobalUnidadAdmin
     */
    public boolean isBlnGlobalUnidadAdmin() {
        return blnGlobalUnidadAdmin;
    }

    /**
     * @param blnGlobalUnidadAdmin
     *            the blnGlobalUnidadAdmin to set
     */
    public void setBlnGlobalUnidadAdmin(boolean blnGlobalUnidadAdmin) {
        this.blnGlobalUnidadAdmin = blnGlobalUnidadAdmin;
    }

}
