package mx.gob.sat.siat.juridica.base.dto;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TramiteDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = -7729278116856025374L;
    private String numeroAsunto;
    private Long idSolicitud;
    private Date fechaVencimiento;
    private Date fechaNotificacion;
    private Date fechaRecepcion;
    private String fechaRecepcionStr;
    private String tipoAsunto;
    private String estadoProcesal;
    private CatalogoDTO catEstadoProcesal;
    private String tipoTramite;

    public TramiteDTO() {

    }

    public String getNumeroAsunto() {
        return numeroAsunto;
    }

    public void setNumeroAsunto(String numeroAsunto) {
        this.numeroAsunto = numeroAsunto;
    }

    public Date getFechaVencimiento() {
        if (fechaVencimiento != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaVencimiento);
            return cal.getTime();
        }
        else {
            return null;
        }
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        if (fechaVencimiento != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaVencimiento);
            this.fechaVencimiento = cal.getTime();
        }
        else {
            this.fechaVencimiento = null;
        }
    }

    public Date getFechaNotificacion() {
        if (fechaNotificacion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaNotificacion);
            return cal.getTime();
        }
        else {
            return null;
        }
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        if (fechaNotificacion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaNotificacion);
            this.fechaNotificacion = cal.getTime();
        }
        else {
            this.fechaNotificacion = null;
        }
    }

    public Date getFechaRecepcion() {
        if (fechaRecepcion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaRecepcion);
            return cal.getTime();
        }
        else {
            return null;
        }
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        if (fechaRecepcion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaRecepcion);
            this.fechaRecepcion = cal.getTime();
        }
        else {
            this.fechaRecepcion = null;
        }
    }

    public String getTipoAsunto() {
        return tipoAsunto;
    }

    public void setTipoAsunto(String tipoAsunto) {
        this.tipoAsunto = tipoAsunto;
    }

    public String getEstadoProcesal() {
        return estadoProcesal;
    }

    public void setEstadoProcesal(String estadoProcesal) {
        this.estadoProcesal = estadoProcesal;
    }

    public CatalogoDTO getCatEstadoProcesal() {
        return catEstadoProcesal;
    }

    public void setCatEstadoProcesal(CatalogoDTO catEstadoProcesal) {
        this.catEstadoProcesal = catEstadoProcesal;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getFechaRecepcionStr() {
        return fechaRecepcionStr;
    }

    public void setFechaRecepcionStr(String fechaRecepcionStr) {
        this.fechaRecepcionStr = fechaRecepcionStr;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

}
