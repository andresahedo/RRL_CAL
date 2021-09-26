package mx.gob.sat.siat.juridica.base.dto;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BandejaConsultasDTO extends BaseDataTransferObject {

    private static final long serialVersionUID = 2402627236966258330L;
    private String numAsunto;
    private EstadoTramite estadoProcesal;
    private Date fechaInicio;
    private String url;
    private Long idSolicitud;
    private String tipoTramite;
    private String descripcionTipoTramite;
    private Date fechaRecepcion;
    private String folio;
    private String nombre;
    private String rfc;

    /**
     * Variable para el estado que se le mostrara al promovente
     */
    private String edoToUser;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNumAsunto() {
        return numAsunto;
    }

    public void setNumAsunto(String numAsunto) {
        this.numAsunto = numAsunto;
    }

    public EstadoTramite getEstadoProcesal() {
        return estadoProcesal;
    }

    public void setEstadoProcesal(EstadoTramite estadoProcesal) {
        this.estadoProcesal = estadoProcesal;
    }

    public Date getFechaInicio() {
        if (fechaInicio != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaInicio);
            return cal.getTime();
        }
        else {
            return null;
        }
    }

    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaInicio);
            this.fechaInicio = cal.getTime();
        }
        else {
            this.fechaInicio = null;
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getEdoToUser() {
        return edoToUser;
    }

    public void setEdoToUser(String edoToUser) {
        this.edoToUser = edoToUser;
    }

}
