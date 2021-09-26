package mx.gob.sat.siat.juridica.rrl.dto;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import java.util.Date;

public class DatosBandejaTareaDTO extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 6936636060500363860L;
    private Long idTareaUsuario;
    private Long idTarea;
    private Long idSolicitud;
    private Long idInstancia;
    private String tipoTramite;
    private String descripcionTipoTramite;
    private String nombreTarea;
    private String numeroAsunto;
    private String rfcSolicitante;
    private String rfcUsuarioAsignado;
    private Date fechaAsignacion;
    private Date fechaAsignacionRequerimiento;
    private String fechaAsignacionStr;
    private String estadoProcesal;
    private String estadoRequerimiento;
    private String tipoRequerimiento;
    private String cveEstadoProcesal;
    private String estadoContribuyente;
    private Long idRequerimiento;
    private String url;
    private String nombrePersonaAsignada;
    private Long idNotificacion;
    private boolean esAbogado;
    private boolean contribuyenteMoral;
    private String folio;
    private boolean oficialia;

    /**
     * Variable para el estado que se le mostrara al promovente
     */
    private String edoToUser;

    public boolean isOficialia() {
        return oficialia;
    }

    public void setOficialia(boolean oficialia) {
        this.oficialia = oficialia;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Long getIdTareaUsuario() {
        return idTareaUsuario;
    }

    public void setIdTareaUsuario(Long idTareaUsuario) {
        this.idTareaUsuario = idTareaUsuario;
    }

    public Long getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
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

    public Date getFechaAsignacion() {
        return fechaAsignacion != null ? (Date) fechaAsignacion.clone() : null;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion != null ? (Date) fechaAsignacion.clone() : null;
    }

    public String getNumeroAsunto() {
        return numeroAsunto;
    }

    public void setNumeroAsunto(String numeroAsunto) {
        this.numeroAsunto = numeroAsunto;
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

    public String getEstadoProcesal() {
        return estadoProcesal;
    }

    public void setEstadoProcesal(String estadoProcesal) {
        this.estadoProcesal = estadoProcesal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getCveEstadoProcesal() {
        return cveEstadoProcesal;
    }

    public void setCveEstadoProcesal(String cveEstadoProcesal) {
        this.cveEstadoProcesal = cveEstadoProcesal;
    }

    /**
     * @return the idRequerimiento
     */
    public Long getIdRequerimiento() {
        return idRequerimiento;
    }

    /**
     * @param idRequerimiento the idRequerimiento to set
     */
    public void setIdRequerimiento(Long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public String getNombrePersonaAsignada() {
        return nombrePersonaAsignada;
    }

    public void setNombrePersonaAsignada(String nombrePersonaAsignada) {
        this.nombrePersonaAsignada = nombrePersonaAsignada;
    }

    public String getFechaAsignacionStr() {
        return fechaAsignacionStr;
    }

    public void setFechaAsignacionStr(String fechaAsignacionStr) {
        this.fechaAsignacionStr = fechaAsignacionStr;
    }

    /**
     * @return the idNotificacion
     */
    public Long getIdNotificacion() {
        return idNotificacion;
    }

    /**
     * @param idNotificacion the idNotificacion to set
     */
    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    /**
     * @return the idInstancia
     */
    public Long getIdInstancia() {
        return idInstancia;
    }

    /**
     * @param idInstancia the idInstancia to set
     */
    public void setIdInstancia(Long idInstancia) {
        this.idInstancia = idInstancia;
    }

    /**
     * @return the esAbogado
     */
    public boolean isEsAbogado() {
        return esAbogado;
    }

    /**
     * @param esAbogado the esAbogado to set
     */
    public void setEsAbogado(boolean esAbogado) {
        this.esAbogado = esAbogado;
    }

    public boolean isContribuyenteMoral() {
        return contribuyenteMoral;
    }

    public void setContribuyenteMoral(boolean contribuyenteMoral) {
        this.contribuyenteMoral = contribuyenteMoral;
    }

    public String getEdoToUser() {
        return edoToUser;
    }

    public void setEdoToUser(String edoToUser) {
        this.edoToUser = edoToUser;
    }

    /**
     * @return the fechaAsignacionRequerimiento
     */
    public Date getFechaAsignacionRequerimiento() {
        return (null == fechaAsignacionRequerimiento ? null : (Date) fechaAsignacionRequerimiento.clone());
    }

    /**
     * @param fechaAsignacionRequerimiento the fechaAsignacionRequerimiento to set
     */
    public void setFechaAsignacionRequerimiento(Date fechaAsignacionRequerimiento) {
        this.fechaAsignacionRequerimiento = (null == fechaAsignacionRequerimiento ? null
                : (Date) fechaAsignacionRequerimiento.clone());
    }

    /**
     * @return the estadoContribuyente
     */
    public String getEstadoContribuyente() {
        return estadoContribuyente;
    }

    /**
     * @param estadoContribuyente the estadoContribuyente to set
     */
    public void setEstadoContribuyente(String estadoContribuyente) {
        this.estadoContribuyente = estadoContribuyente;
    }

    @Override
    public String toString() {
        return "DatosBandejaTareaDTO [idTareaUsuario=" + idTareaUsuario + ", idTarea=" + idTarea + ", idSolicitud="
                + idSolicitud + ", idInstancia=" + idInstancia + ", tipoTramite=" + tipoTramite
                + ", descripcionTipoTramite=" + descripcionTipoTramite + ", nombreTarea=" + nombreTarea
                + ", numeroAsunto=" + numeroAsunto + ", rfcSolicitante=" + rfcSolicitante + ", rfcUsuarioAsignado="
                + rfcUsuarioAsignado + ", fechaAsignacion=" + fechaAsignacion + ", fechaAsignacionRequerimiento="
                + fechaAsignacionRequerimiento + ", fechaAsignacionStr=" + fechaAsignacionStr + ", estadoProcesal="
                + estadoProcesal + ", estadoRequerimiento=" + estadoRequerimiento + ", tipoRequerimiento="
                + tipoRequerimiento + ", cveEstadoProcesal=" + cveEstadoProcesal + ", estadoContribuyente="
                + estadoContribuyente + ", idRequerimiento=" + idRequerimiento + ", url=" + url
                + ", nombrePersonaAsignada=" + nombrePersonaAsignada + ", idNotificacion=" + idNotificacion
                + ", esAbogado=" + esAbogado + ", contribuyenteMoral=" + contribuyenteMoral + ", folio=" + folio
                + ", oficialia=" + oficialia + ", edoToUser=" + edoToUser + "]";
    }
    
    

}
