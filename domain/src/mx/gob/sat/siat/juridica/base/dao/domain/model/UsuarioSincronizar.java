package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Table(name = "RVCT_SINCUSUARIO")
public class UsuarioSincronizar extends BaseModel {

    /**
     *
     */
    private static final long serialVersionUID = -5993006615908576748L;

    @Id
    @Column(name = "RFCCORTO")
    private String rfcCorto;

    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "ACTIVO")
    private Integer activo;

    public String getRfcCorto() {
        return rfcCorto;
    }

    public void setRfcCorto(String rfcCorto) {
        this.rfcCorto = rfcCorto;
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

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

}
