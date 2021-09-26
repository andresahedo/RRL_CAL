/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;

/**
 * FiltroUsuariosDTO
 * 
 * @author softtek - EQG 08/10/2014
 */
public class FiltroUsuariosDTO extends BaseDataTransferObject {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1590077635035189563L;

    private String idUnidadAdmin;
    private String permiso;
    private String numeroEmpleado;
    private String rfcEmpleado;
    private String tipoTramite;
    private String nombreEmpleado;
    private String modalidad;
    private boolean responsable;

    public String getIdUnidadAdmin() {
        return idUnidadAdmin;
    }

    public void setIdUnidadAdmin(String idUnidadAdmin) {
        this.idUnidadAdmin = idUnidadAdmin;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getRfcEmpleado() {
        return rfcEmpleado;
    }

    public void setRfcEmpleado(String rfcEmpleado) {
        this.rfcEmpleado = rfcEmpleado;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public boolean isResponsable() {
        return responsable;
    }

    public void setResponsable(boolean responsable) {
        this.responsable = responsable;
    }

}
