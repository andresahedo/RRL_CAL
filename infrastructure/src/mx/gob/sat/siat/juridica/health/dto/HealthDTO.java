package mx.gob.sat.siat.juridica.health.dto;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class HealthDTO extends BaseModel {

    private static final long serialVersionUID = 144733271104570302L;

    private Boolean servicioOk;
    private String respuesta;    
    
    private Date fechaUltimaSincronizacion;
    private boolean permitirEjecucionSincronizacion;
    private List<UsuarioSincronizarDTO> usuariosSincronizacion;

    public HealthDTO() {
        super();
    }

    public HealthDTO(boolean servicioOk, String respuesta) {
        this.servicioOk = servicioOk;
        this.respuesta = respuesta;
    }    
    public Boolean getServicioOk() {
        return servicioOk;
    }

    public void setServicioOk(Boolean servicioOk) {
        this.servicioOk = servicioOk;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Date getFechaUltimaSincronizacion() {
        return fechaUltimaSincronizacion != null ? (Date) fechaUltimaSincronizacion.clone() : null;
    }

    public void setFechaUltimaSincronizacion(Date fechaUltimaSincronizacion) {
        if (fechaUltimaSincronizacion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaUltimaSincronizacion);
            this.fechaUltimaSincronizacion = cal.getTime();
        }
        else {
            this.fechaUltimaSincronizacion = null;
        }
    }

    public boolean isPermitirEjecucionSincronizacion() {
        return permitirEjecucionSincronizacion;
    }

    public void setPermitirEjecucionSincronizacion(
            boolean permitirEjecucionSincronizacion) {
        this.permitirEjecucionSincronizacion = permitirEjecucionSincronizacion;
    }

    public List<UsuarioSincronizarDTO> getUsuariosSincronizacion() {
        return usuariosSincronizacion;
    }

    public void setUsuariosSincronizacion(
            List<UsuarioSincronizarDTO> usuariosSincronizacion) {
        this.usuariosSincronizacion = usuariosSincronizacion;
    }

}
