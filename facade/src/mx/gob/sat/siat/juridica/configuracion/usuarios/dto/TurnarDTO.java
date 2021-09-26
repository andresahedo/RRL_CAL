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

import java.util.Date;

/**
 * Clase Model para la Reasignacion y turnado de asuntos
 * 
 * @author Softtek - EQG
 * @since 14/11/2014
 */
public class TurnarDTO extends BaseDataTransferObject {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -5667575486932342433L;

    /**
     * Atibutos para el DTO del Model Reasignacion
     */
    private Long idReasignacion;
    private String idTramite;
    private String idUsuarioAsigna;
    private String idUsuarioAnterior;
    private String idUsuarioNuevo;
    private String nombreTarea;
    private Date fechaAsignacion;
    private Date fechaBaja;
    private String observacion;

    public Long getIdReasignacion() {
        return idReasignacion;
    }

    public void setIdReasignacion(Long idReasignacion) {
        this.idReasignacion = idReasignacion;
    }

    public String getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(String idTramite) {
        this.idTramite = idTramite;
    }

    public String getIdUsuarioAsigna() {
        return idUsuarioAsigna;
    }

    public void setIdUsuarioAsigna(String idUsuarioAsigna) {
        this.idUsuarioAsigna = idUsuarioAsigna;
    }

    public String getIdUsuarioAnterior() {
        return idUsuarioAnterior;
    }

    public void setIdUsuarioAnterior(String idUsuarioAnterior) {
        this.idUsuarioAnterior = idUsuarioAnterior;
    }

    public String getIdUsuarioNuevo() {
        return idUsuarioNuevo;
    }

    public void setIdUsuarioNuevo(String idUsuarioNuevo) {
        this.idUsuarioNuevo = idUsuarioNuevo;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public Date getFechaAsignacion() {
        return (null == fechaAsignacion ? null : (Date) fechaAsignacion.clone());
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = (null == fechaAsignacion ? null : (Date) fechaAsignacion.clone());
    }

    public Date getFechaBaja() {
        return (null == fechaBaja ? null : (Date) fechaBaja.clone());
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = (null == fechaBaja ? null : (Date) fechaBaja.clone());
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

}
