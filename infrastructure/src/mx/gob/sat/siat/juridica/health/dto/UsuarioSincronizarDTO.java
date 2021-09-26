package mx.gob.sat.siat.juridica.health.dto;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UsuarioSincronizarDTO extends BaseModel {

    /**
     *
     */
    private static final long serialVersionUID = 7298767882040152512L;

    private String rfcCorto;

    private Integer activo;

    private Date fecha;

    public String getRfcCorto() {
        return rfcCorto;
    }

    public void setRfcCorto(String rfcCorto) {
        this.rfcCorto = rfcCorto;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Date getFecha() {
        return fecha != null ? (Date) fecha.clone() : null;
    }

    public void setFecha(Date fecha) {
        if (fecha != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fecha);
            this.fecha = cal.getTime();
        }
        else {
            this.fecha = null;
        }
    }



}
