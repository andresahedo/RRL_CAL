package mx.gob.sat.siat.juridica.rrl.dto;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import java.util.Date;

public class TareaUsuarioDTO extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -2773947242987554855L;

    private Long idTareaUsuario;
    private Long idInstancia;
    private String tipoTramite;
    private String descripcionTipoTramite;
    private String nombreTarea;
    private String numeroAsunto;
    private String rfcSolicitante;
    private String rfcUsuarioAsignado;
    private String abogado;
    private String administrador;
    private String oficial;
    private String nombrePersonaAsignada;
    private Date fechaAsignacion;
    private String fechaAsignacionStr;
    private String estadoProcesal;
    private String estadoRequerimiento;
    private String tipoRequerimiento;
    private String cveEstadoProcesal;

    public Long getIdTareaUsuario() {
        return idTareaUsuario;
    }

    public void setIdTareaUsuario(Long idTareaUsuario) {
        this.idTareaUsuario = idTareaUsuario;
    }

    public Long getIdInstancia() {
        return idInstancia;
    }

    public void setIdInstancia(Long idInstancia) {
        this.idInstancia = idInstancia;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getDescripcionTipoTramite() {
        return descripcionTipoTramite;
    }

    public void setDescripcionTipoTramite(String descripcionTipoTramite) {
        this.descripcionTipoTramite = descripcionTipoTramite;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public String getNumeroAsunto() {
        return numeroAsunto;
    }

    public void setNumeroAsunto(String numeroAsunto) {
        this.numeroAsunto = numeroAsunto;
    }

    public String getRfcSolicitante() {
        return rfcSolicitante;
    }

    public void setRfcSolicitante(String rfcSolicitante) {
        this.rfcSolicitante = rfcSolicitante;
    }

    public String getRfcUsuarioAsignado() {
        return rfcUsuarioAsignado;
    }

    public void setRfcUsuarioAsignado(String rfcUsuarioAsignado) {
        this.rfcUsuarioAsignado = rfcUsuarioAsignado;
    }

    public String getAbogado() {
        return abogado;
    }

    public void setAbogado(String abogado) {
        this.abogado = abogado;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public String getOficial() {
        return oficial;
    }

    public void setOficial(String oficial) {
        this.oficial = oficial;
    }

    public String getNombrePersonaAsignada() {
        return nombrePersonaAsignada;
    }

    public void setNombrePersonaAsignada(String nombrePersonaAsignada) {
        this.nombrePersonaAsignada = nombrePersonaAsignada;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion != null ? (Date) fechaAsignacion.clone() : null;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = new Date(fechaAsignacion.getTime());
    }

    public String getFechaAsignacionStr() {
        return fechaAsignacionStr;
    }

    public void setFechaAsignacionStr(String fechaAsignacionStr) {
        this.fechaAsignacionStr = fechaAsignacionStr;
    }

    public String getEstadoProcesal() {
        return estadoProcesal;
    }

    public void setEstadoProcesal(String estadoProcesal) {
        this.estadoProcesal = estadoProcesal;
    }

    public String getEstadoRequerimiento() {
        return estadoRequerimiento;
    }

    public void setEstadoRequerimiento(String estadoRequerimiento) {
        this.estadoRequerimiento = estadoRequerimiento;
    }

    public String getTipoRequerimiento() {
        return tipoRequerimiento;
    }

    public void setTipoRequerimiento(String tipoRequerimiento) {
        this.tipoRequerimiento = tipoRequerimiento;
    }

    public String getCveEstadoProcesal() {
        return cveEstadoProcesal;
    }

    public void setCveEstadoProcesal(String cveEstadoProcesal) {
        this.cveEstadoProcesal = cveEstadoProcesal;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
