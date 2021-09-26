package mx.gob.sat.siat.juridica.rrl.dto;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatosPersonaTareaDTO extends BaseModel implements Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 6936636060500363860L;
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
    private List<TareaUsuarioDTO> tareas = new ArrayList<TareaUsuarioDTO>();

    public void add(DatosPersonaTareaDTO datosPersona) {
        TareaUsuarioDTO tarea = new TareaUsuarioDTO();
        tarea.setAbogado(datosPersona.getAbogado());
        tarea.setAdministrador(datosPersona.getAdministrador());
        tarea.setDescripcionTipoTramite(datosPersona.getDescripcionTipoTramite());
        tarea.setIdInstancia(datosPersona.getIdInstancia());
        tarea.setIdTareaUsuario(datosPersona.getIdTareaUsuario());
        tarea.setNombreTarea(datosPersona.getNombreTarea());
        tarea.setNumeroAsunto(datosPersona.getNumeroAsunto());
        tarea.setOficial(datosPersona.getOficial());
        tarea.setRfcSolicitante(datosPersona.getRfcSolicitante());
        tarea.setRfcUsuarioAsignado(datosPersona.getRfcUsuarioAsignado());
        tarea.setTipoTramite(datosPersona.getTipoTramite());
        tarea.setCveEstadoProcesal(datosPersona.getCveEstadoProcesal());
        tarea.setEstadoProcesal(datosPersona.getEstadoProcesal());
        tarea.setEstadoRequerimiento(datosPersona.getEstadoRequerimiento());
        tarea.setFechaAsignacion(datosPersona.getFechaAsignacion());
        tarea.setFechaAsignacionStr(datosPersona.getFechaAsignacionStr());
        tarea.setNombrePersonaAsignada(datosPersona.getNombrePersonaAsignada());
        tarea.setTipoRequerimiento(datosPersona.getTipoRequerimiento());
        tareas.add(tarea);
    }

    public void add(DatosPersonaTareaDTO datosPersona, String rol) {
        TareaUsuarioDTO tarea = new TareaUsuarioDTO();
        tarea.setAbogado(datosPersona.getAbogado());
        tarea.setAdministrador(datosPersona.getAdministrador());
        tarea.setDescripcionTipoTramite(datosPersona.getDescripcionTipoTramite());
        tarea.setIdInstancia(datosPersona.getIdInstancia());
        tarea.setIdTareaUsuario(datosPersona.getIdTareaUsuario());
        // este getRfcUsuarioAsignado debe cambiarse por el rol que se
        // agregara la siguiente semana
        if (rol != null && rol.equals(tarea.getRfcUsuarioAsignado())) {
            this.setNombreTarea(datosPersona.getNombreTarea());
        }
        tarea.setNombreTarea(datosPersona.getNombreTarea());
        tarea.setNumeroAsunto(datosPersona.getNumeroAsunto());
        tarea.setOficial(datosPersona.getOficial());
        tarea.setRfcSolicitante(datosPersona.getRfcSolicitante());
        tarea.setRfcUsuarioAsignado(datosPersona.getRfcUsuarioAsignado());
        tarea.setTipoTramite(datosPersona.getTipoTramite());
        tarea.setCveEstadoProcesal(datosPersona.getCveEstadoProcesal());
        tarea.setEstadoProcesal(datosPersona.getEstadoProcesal());
        tarea.setEstadoRequerimiento(datosPersona.getEstadoRequerimiento());
        tarea.setFechaAsignacion(datosPersona.getFechaAsignacion());
        tarea.setFechaAsignacionStr(datosPersona.getFechaAsignacionStr());
        tarea.setNombrePersonaAsignada(datosPersona.getNombrePersonaAsignada());
        tarea.setTipoRequerimiento(datosPersona.getTipoRequerimiento());
        tareas.add(tarea);
    }

    @Override
    public DatosPersonaTareaDTO clone() throws CloneNotSupportedException {

        DatosPersonaTareaDTO tarea = (DatosPersonaTareaDTO) super.clone();
        tarea = new DatosPersonaTareaDTO();
        tarea.setAbogado(this.getAbogado());
        tarea.setAdministrador(this.getAdministrador());
        tarea.setDescripcionTipoTramite(this.getDescripcionTipoTramite());
        tarea.setIdInstancia(this.getIdInstancia());
        tarea.setIdTareaUsuario(this.getIdTareaUsuario());
        tarea.setNombreTarea(this.getNombreTarea());
        tarea.setNumeroAsunto(this.getNumeroAsunto());
        tarea.setOficial(this.getOficial());
        tarea.setRfcSolicitante(this.getRfcSolicitante());
        tarea.setRfcUsuarioAsignado(this.getRfcUsuarioAsignado());
        tarea.setTipoTramite(this.getTipoTramite());
        tarea.setCveEstadoProcesal(this.getCveEstadoProcesal());
        tarea.setEstadoProcesal(this.getEstadoProcesal());
        tarea.setEstadoRequerimiento(this.getEstadoRequerimiento());
        tarea.setFechaAsignacion(this.getFechaAsignacion());
        tarea.setFechaAsignacionStr(this.getFechaAsignacionStr());
        tarea.setNombrePersonaAsignada(this.getNombrePersonaAsignada());
        tarea.setTipoRequerimiento(this.getTipoRequerimiento());

        return tarea;
    }

    /**
     * @return the idTareaUsuario
     */
    public Long getIdTareaUsuario() {
        return idTareaUsuario;
    }

    /**
     * @param idTareaUsuario
     *            the idTareaUsuario to set
     */
    public void setIdTareaUsuario(Long idTareaUsuario) {
        this.idTareaUsuario = idTareaUsuario;
    }

    /**
     * @return the idInstancia
     */
    public Long getIdInstancia() {
        return idInstancia;
    }

    /**
     * @param idInstancia
     *            the idInstancia to set
     */
    public void setIdInstancia(Long idInstancia) {
        this.idInstancia = idInstancia;
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
     * @return the descripcionTipoTramite
     */
    public String getDescripcionTipoTramite() {
        return descripcionTipoTramite;
    }

    /**
     * @param descripcionTipoTramite
     *            the descripcionTipoTramite to set
     */
    public void setDescripcionTipoTramite(String descripcionTipoTramite) {
        this.descripcionTipoTramite = descripcionTipoTramite;
    }

    /**
     * @return the nombreTarea
     */
    public String getNombreTarea() {
        return nombreTarea;
    }

    /**
     * @param nombreTarea
     *            the nombreTarea to set
     */
    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    /**
     * @return the numeroAsunto
     */
    public String getNumeroAsunto() {
        return numeroAsunto;
    }

    /**
     * @param numeroAsunto
     *            the numeroAsunto to set
     */
    public void setNumeroAsunto(String numeroAsunto) {
        this.numeroAsunto = numeroAsunto;
    }

    /**
     * @return the rfcSolicitante
     */
    public String getRfcSolicitante() {
        return rfcSolicitante;
    }

    /**
     * @param rfcSolicitante
     *            the rfcSolicitante to set
     */
    public void setRfcSolicitante(String rfcSolicitante) {
        this.rfcSolicitante = rfcSolicitante;
    }

    /**
     * @return the rfcUsuarioAsignado
     */
    public String getRfcUsuarioAsignado() {
        return rfcUsuarioAsignado;
    }

    /**
     * @param rfcUsuarioAsignado
     *            the rfcUsuarioAsignado to set
     */
    public void setRfcUsuarioAsignado(String rfcUsuarioAsignado) {
        this.rfcUsuarioAsignado = rfcUsuarioAsignado;
    }

    /**
     * @return the nombrePersonaAsignada
     */
    public String getNombrePersonaAsignada() {
        return nombrePersonaAsignada;
    }

    /**
     * @param nombrePersonaAsignada
     *            the nombrePersonaAsignada to set
     */
    public void setNombrePersonaAsignada(String nombrePersonaAsignada) {
        this.nombrePersonaAsignada = nombrePersonaAsignada;
    }

    /**
     * @return the fechaAsignacion
     */
    public Date getFechaAsignacion() {
        return (null == fechaAsignacion ? null : (Date) fechaAsignacion.clone());
    }

    /**
     * @param fechaAsignacion
     *            the fechaAsignacion to set
     */
    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = (null == fechaAsignacion ? null : (Date) fechaAsignacion.clone());
    }

    /**
     * @return the fechaAsignacionStr
     */
    public String getFechaAsignacionStr() {
        return fechaAsignacionStr;
    }

    /**
     * @param fechaAsignacionStr
     *            the fechaAsignacionStr to set
     */
    public void setFechaAsignacionStr(String fechaAsignacionStr) {
        this.fechaAsignacionStr = fechaAsignacionStr;
    }

    /**
     * @return the estadoProcesal
     */
    public String getEstadoProcesal() {
        return estadoProcesal;
    }

    /**
     * @param estadoProcesal
     *            the estadoProcesal to set
     */
    public void setEstadoProcesal(String estadoProcesal) {
        this.estadoProcesal = estadoProcesal;
    }

    /**
     * @return the estadoRequerimiento
     */
    public String getEstadoRequerimiento() {
        return estadoRequerimiento;
    }

    /**
     * @param estadoRequerimiento
     *            the estadoRequerimiento to set
     */
    public void setEstadoRequerimiento(String estadoRequerimiento) {
        this.estadoRequerimiento = estadoRequerimiento;
    }

    /**
     * @return the tipoRequerimiento
     */
    public String getTipoRequerimiento() {
        return tipoRequerimiento;
    }

    /**
     * @param tipoRequerimiento
     *            the tipoRequerimiento to set
     */
    public void setTipoRequerimiento(String tipoRequerimiento) {
        this.tipoRequerimiento = tipoRequerimiento;
    }

    /**
     * @return the cveEstadoProcesal
     */
    public String getCveEstadoProcesal() {
        return cveEstadoProcesal;
    }

    /**
     * @param cveEstadoProcesal
     *            the cveEstadoProcesal to set
     */
    public void setCveEstadoProcesal(String cveEstadoProcesal) {
        this.cveEstadoProcesal = cveEstadoProcesal;
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

    public List<TareaUsuarioDTO> getTareas() {
        return tareas;
    }

    public void setTareas(List<TareaUsuarioDTO> tareas) {
        this.tareas = tareas;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DatosPersonaTareaDTO)) {
            return false;
        }

        return ((DatosPersonaTareaDTO) obj).numeroAsunto.equals(this.getNumeroAsunto());

    }

    /**
     * Sobreescritura del metodo hashCode
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idTareaUsuario == null) ? 0 : idTareaUsuario.hashCode());
        return result;
    }
}
