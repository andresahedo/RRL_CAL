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
 * DTO que representa los datos de la Observacion.
 * 
 * @author Softtek
 * 
 */
public class ObservacionDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 4160370117467492326L;

    private Long idObservacion;

    /**
     * Contiene la descripci&oacute;n del estado de la observacion.
     */
    private String estadoObservacion;

    /**
     * Contiene la descripci&oacute;n de la observacion.
     */
    private String observacionDesc;

    /**
     * Contiene la fecha de la observacion
     */
    private Date fechaObservacion;

    /**
     * Contiene la fecha de atencion de la observacion
     */
    private Date fechaAtencion;

    /**
     * Contiene el rfc del funcionario.
     */
    private String rfcAbogado;

    /**
     * DTO que representa los datos del tramite.
     */
    private String numeroAsunto;

    private String descEstado;

    /**
     * /** Constructor
     */
    public ObservacionDTO() {}

    /**
     * @return the idObservacion
     */
    public Long getIdObservacion() {
        return idObservacion;
    }

    /**
     * @param idObservacion
     *            the idObservacion to set
     */
    public void setIdObservacion(Long idObservacion) {
        this.idObservacion = idObservacion;
    }

    /**
     * @return the estadoObservacion
     */
    public String getEstadoObservacion() {
        return estadoObservacion;
    }

    /**
     * @param estadoObservacion
     *            the estadoObservacion to set
     */
    public void setEstadoObservacion(String estadoObservacion) {
        this.estadoObservacion = estadoObservacion;
    }

    /**
     * @return the fechaObservacion
     */
    public Date getFechaObservacion() {
        return fechaObservacion != null ? (Date) fechaObservacion.clone() : null;
    }

    /**
     * @param fechaObservacion
     *            the fechaObservacion to set
     */
    public void setFechaObservacion(Date fechaObservacion) {
        this.fechaObservacion = fechaObservacion != null ? (Date) fechaObservacion.clone() : null;
    }

    /**
     * @return the fechaAtencion
     */
    public Date getFechaAtencion() {
        return fechaAtencion != null ? (Date) fechaAtencion.clone() : null;
    }

    /**
     * @param fechaAtencion
     *            the fechaAtencion to set
     */
    public void setFechaAtencion(Date fechaAtencion) {
        this.fechaAtencion = fechaAtencion != null ? (Date) fechaAtencion.clone() : null;
    }

    /**
     * @return the rfcAbogado
     */
    public String getRfcAbogado() {
        return rfcAbogado;
    }

    /**
     * @param rfcAbogado
     *            the rfcAbogado to set
     */
    public void setRfcAbogado(String rfcAbogado) {
        this.rfcAbogado = rfcAbogado;
    }

    /**
     * @return the observacionDesc
     */
    public String getObservacionDesc() {
        return observacionDesc;
    }

    /**
     * @param observacionDesc
     *            the observacionDesc to set
     */
    public void setObservacionDesc(String observacionDesc) {
        this.observacionDesc = observacionDesc;
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
     * @return the descEstado
     */
    public String getDescEstado() {
        return descEstado;
    }

    /**
     * @param descEstado
     *            the descEstado to set
     */
    public void setDescEstado(String descEstado) {
        this.descEstado = descEstado;
    }

}
