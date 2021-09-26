package mx.gob.sat.siat.juridica.rrl.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;

import java.util.Date;

public class FiltroBandejaPendientesDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long idSolicitud;

    private Date fechaInicio;

    private Date fechaFin;

    private String rfcPromovente;

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Date getFechaInicio() {
        return fechaInicio != null ? (Date) fechaInicio.clone() : null;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio != null ? (Date) fechaInicio.clone() : null;
    }

    public Date getFechaFin() {
        return fechaFin != null ? (Date) fechaFin.clone() : null;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin != null ? (Date) fechaFin.clone() : null;
    }

    public String getRfcPromovente() {
        return rfcPromovente;
    }

    public void setRfcPromovente(String rfcPromovente) {
        this.rfcPromovente = rfcPromovente;
    }

}
