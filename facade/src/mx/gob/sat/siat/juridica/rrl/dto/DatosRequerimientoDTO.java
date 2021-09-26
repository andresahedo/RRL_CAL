package mx.gob.sat.siat.juridica.rrl.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;

import java.util.*;

public class DatosRequerimientoDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = -3865357464046315921L;

    private Long idRequerimiento;
    private String tipoRequerimiento;
    private String autoridad;
    private Date fechaGeneracion;
    private Date fechaAtencion;
    private String estadoRequerimiento;
    private String generadoPor;
    private String autorizadoPor;
    private Date fechaAutorizacion;
    private Date fechaNotificacion;
    private boolean deshabilitarFechas;
    private String idUsuario;
    private String nombreAbogado;
    private List<DocumentoDTO> documentosConsultaReq = new ArrayList<DocumentoDTO>();

    public DatosRequerimientoDTO() {

    }

    public Long getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public String getTipoRequerimiento() {
        return tipoRequerimiento;
    }

    public void setTipoRequerimiento(String tipoRequerimiento) {
        this.tipoRequerimiento = tipoRequerimiento;
    }

    public String getAutoridad() {
        return autoridad;
    }

    public void setAutoridad(String autoridad) {
        this.autoridad = autoridad;
    }

    public Date getFechaGeneracion() {
        if (fechaGeneracion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaGeneracion);
            return cal.getTime();
        }
        else {
            return null;
        }
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        if (fechaGeneracion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaGeneracion);
            this.fechaGeneracion = cal.getTime();
        }
        else {
            this.fechaGeneracion = null;
        }
    }

    public Date getFechaAtencion() {
        return fechaAtencion != null ? (Date) fechaAtencion.clone() : null;
    }

    public void setFechaAtencion(Date fechaAtencion) {
        if (fechaAtencion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaAtencion);
            this.fechaAtencion = cal.getTime();
        }
        else {
            this.fechaAtencion = null;
        }
    }

    public String getEstadoRequerimiento() {
        return estadoRequerimiento;
    }

    public void setEstadoRequerimiento(String estadoRequerimiento) {
        this.estadoRequerimiento = estadoRequerimiento;
    }

    public String getGeneradoPor() {
        return generadoPor;
    }

    public void setGeneradoPor(String generadoPor) {
        this.generadoPor = generadoPor;
    }

    public String getAutorizadoPor() {
        return autorizadoPor;
    }

    public void setAutorizadoPor(String autorizadoPor) {
        this.autorizadoPor = autorizadoPor;
    }

    public Date getFechaAutorizacion() {
        if (fechaAutorizacion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaAutorizacion);
            return cal.getTime();
        }
        else {
            return null;
        }
    }

    public void setFechaAutorizacion(Date fechaAutorizacion) {
        if (fechaAutorizacion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaAutorizacion);
            this.fechaAutorizacion = cal.getTime();
        }
        else {
            this.fechaAutorizacion = null;
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

    public List<DocumentoDTO> getDocumentosConsultaReq() {
        return documentosConsultaReq;
    }

    public void setDocumentosConsultaReq(List<DocumentoDTO> documentosConsultaReq) {
        this.documentosConsultaReq = documentosConsultaReq;
    }

    public boolean isDeshabilitarFechas() {
        return deshabilitarFechas;
    }

    public void setDeshabilitarFechas(boolean deshabilitarFechas) {
        this.deshabilitarFechas = deshabilitarFechas;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the nombreAbogado
     */
    public String getNombreAbogado() {
        return nombreAbogado;
    }

    /**
     * @param nombreAbogado
     *            the nombreAbogado to set
     */
    public void setNombreAbogado(String nombreAbogado) {
        this.nombreAbogado = nombreAbogado;
    }

}
