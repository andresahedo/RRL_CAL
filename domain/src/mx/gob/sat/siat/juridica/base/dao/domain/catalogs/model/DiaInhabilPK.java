/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author softtek
 */
@Embeddable
public class DiaInhabilPK extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "fechaNoLaborable" tipo Date
     */
    @Basic(optional = false)
    @Column(name = "FECNOLABORABLE")
    @Temporal(value = TemporalType.DATE)
    private Date fechaNoLaborable;

    /**
     * Atributo privado "claveCalendario" tipo String
     */
    @Basic(optional = false)
    @Column(name = "IDCALENDARIO")
    private String claveCalendario;

    /**
     * Constructor vacio
     */
    public DiaInhabilPK() {}

    /**
     * Sobrecarga del Constructor
     * 
     * @param claveCalendario
     *            , fechaNoLaborable
     */
    public DiaInhabilPK(String claveCalendario, Date fechaNoLaborable) {
        this.claveCalendario = claveCalendario;
        if (fechaNoLaborable != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaNoLaborable);
            this.fechaNoLaborable = cal.getTime();
        }
        else {
            this.fechaNoLaborable = null;
        }
    }

    /**
     * 
     * @return fechaNoLaborable
     */
    public Date getFechaNoLaborable() {
        return (null == fechaNoLaborable ? null : (Date) fechaNoLaborable.clone());
    }

    /**
     * 
     * @param fechaNoLaborable
     *            a fijar
     */
    public void setFechaNoLaborable(Date fechaNoLaborable) {
        this.fechaNoLaborable = (null == fechaNoLaborable ? null : (Date) fechaNoLaborable.clone());
    }

    /**
     * 
     * @return claveCalendario
     */
    public String getClaveCalendario() {
        return claveCalendario;
    }

    /**
     * 
     * @param claveCalendario
     *            a fijar
     */
    public void setClaveCalendario(String claveCalendario) {
        this.claveCalendario = claveCalendario;
    }

    /**
     * Metodo hashCode
     * 
     * @return hash
     */
    public int hashCode() {
        int hash = 0;
        hash += (claveCalendario != null ? claveCalendario.hashCode() : 0);
        hash += (fechaNoLaborable != null ? fechaNoLaborable.hashCode() : 0);
        return hash;
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     */
    public boolean equals(Object object) {
        if (!(object instanceof DiaInhabilPK)) {
            return false;
        }
        DiaInhabilPK other = (DiaInhabilPK) object;
        if (this.claveCalendario == null || other.claveCalendario == null
                || !this.claveCalendario.equals(other.claveCalendario)) {
            return false;
        }
        if (this.fechaNoLaborable == null || other.fechaNoLaborable == null
                || !this.fechaNoLaborable.equals(other.fechaNoLaborable)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo toString
     * 
     * @return "entity.DiaInhabilPK[claveCalendario=" +
     *         claveCalendario + ", fechaNoLaborable=" +
     *         fechaNoLaborable + "]"
     */
    public String toString() {
        return "entity.DiaInhabilPK[claveCalendario=" + claveCalendario + ", fechaNoLaborable=" + fechaNoLaborable
                + "]";
    }
}
