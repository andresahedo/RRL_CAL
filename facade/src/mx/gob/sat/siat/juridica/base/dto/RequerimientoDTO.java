/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dto;

import java.util.Date;

/**
 * DTO que representa los datos del requerimiento.
 * 
 * @author Softtek
 * 
 */
public class RequerimientoDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 4160370117467492326L;

    private Long id;

    /**
     * Contiene el nombre completo del funcionario.
     */
    private String nombrePersona;
    /**
     * Contiene el rfc del funcionario.
     */
    private String rfc;

    /**
     * Contiene el ID del requerimiento.
     */
    private Long idRequerimiento;
    /**
     * DTO que representa los datos del tramite.
     */
    private TramiteDTO tramite;
    /**
     * Contiene la clave del tipo de requerimiento.
     */
    private String claveTipoRequerimiento;
    /**
     * Contiene la clave la unidad administrativa del requerimiento.
     */
    private String claveUnidadAdministrativa;
    /**
     * Contiene la el tipo de requerimiento.
     */
    private String tipoRequerimiento;
    /**
     * Contiene la unidad administrativa del requerimiento.
     */
    private String unidadAdministrativa;
    /**
     * Contiene la descripci&oacute;n del estado del requerimiento.
     */
    private String estadoRequerimiento;
    /**
     * Contiene el rfc del autorizador al que se le asignara el
     * requerimiento.
     */
    private String rfcAutorizador;

    private String idUsuario;

    private Date fechaAutorizacion;

    /**
     * Constructor
     */
    public RequerimientoDTO() {}

    /**
     * 
     * @return identificador
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return nombrePersona
     */
    public String getNombrePersona() {
        return nombrePersona;
    }

    /**
     * 
     * @param nombrePersona
     *            a fijar
     */
    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    /**
     * 
     * @return rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * 
     * @param rfc
     *            a fijar
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * 
     * @return idRequerimiento
     */
    public Long getIdRequerimiento() {
        return idRequerimiento;
    }

    /**
     * 
     * @param idRequerimiento
     *            a fijar
     */
    public void setIdRequerimiento(Long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    /**
     * 
     * @return tramite
     */
    public TramiteDTO getTramite() {
        return tramite;
    }

    /**
     * 
     * @param tramite
     *            a fijar
     */
    public void setTramite(TramiteDTO tramite) {
        this.tramite = tramite;
    }

    /**
     * 
     * @return claveTipoRequerimiento
     */
    public String getClaveTipoRequerimiento() {
        return claveTipoRequerimiento;
    }

    /**
     * 
     * @param claveTipoRequerimiento
     *            a fijar
     */
    public void setClaveTipoRequerimiento(String claveTipoRequerimiento) {
        this.claveTipoRequerimiento = claveTipoRequerimiento;
    }

    /**
     * 
     * @return claveUnidadAdministrativa
     */
    public String getClaveUnidadAdministrativa() {
        return claveUnidadAdministrativa;
    }

    /**
     * 
     * @param claveUnidadAdministrativa
     *            a fijar
     */
    public void setClaveUnidadAdministrativa(String claveUnidadAdministrativa) {
        this.claveUnidadAdministrativa = claveUnidadAdministrativa;
    }

    /**
     * 
     * @return estadoRequerimiento
     */
    public String getEstadoRequerimiento() {
        return estadoRequerimiento;
    }

    /**
     * 
     * @param estadoRequerimiento
     *            a fijar
     */
    public void setEstadoRequerimiento(String estadoRequerimiento) {
        this.estadoRequerimiento = estadoRequerimiento;
    }

    /**
     * 
     * @return rfcAutorizador
     */
    public String getRfcAutorizador() {
        return rfcAutorizador;
    }

    /**
     * 
     * @param rfcAutorizador
     *            a fijar
     */
    public void setRfcAutorizador(String rfcAutorizador) {
        this.rfcAutorizador = rfcAutorizador;
    }

    /**
     * @return the tipoRequerimiento
     */
    public String getTipoRequerimiento() {
        return tipoRequerimiento;
    }

    /**
     * @return the unidadAdministrativa
     */
    public String getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * @param unidadAdministrativa
     *            the unidadAdministrativa to set
     */
    public void setUnidadAdministrativa(String unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    /**
     * @param tipoRequerimiento
     *            the tipoRequerimiento to set
     */
    public void setTipoRequerimiento(String tipoRequerimiento) {
        this.tipoRequerimiento = tipoRequerimiento;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaAutorizacion() {
        return fechaAutorizacion != null ? (Date) fechaAutorizacion.clone() : null;
    }

    public void setFechaAutorizacion(Date fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion != null ? (Date) fechaAutorizacion.clone() : null;
    }

}
