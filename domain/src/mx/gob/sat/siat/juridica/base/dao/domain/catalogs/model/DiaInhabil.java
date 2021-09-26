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
import java.util.Date;

/**
 * 
 * @author softtek
 */
@Entity
@NamedQueries({ @NamedQuery(name = "DiaInhabil.findAll", query = "select Dias from DiaInhabil Dias") })
@Table(name = "RVCC_DIAINHABIL")
public class DiaInhabil extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "diaInhabilPK" tipo DiaInhabilPK
     */
    @EmbeddedId
    private DiaInhabilPK diaInhabilPK;

    /**
     * Atributo privado "descripcion" tipo String
     */
    @Column(name = "DESCRIPCION")
    private String descripcion;

    /**
     * Atributo privado "calendario" tipo Calendario
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDCALENDARIO", referencedColumnName = "IDCALENDARIO", insertable = false, updatable = false)
    private Calendario calendario;

    /**
     * Atributo privado "fechaNoLaborable" tipo Date
     */
    @Column(name = "FECNOLABORABLE", insertable = false, updatable = false)
    @Temporal(value = TemporalType.DATE)
    private Date fechaNoLaborable;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * Constructor
     */
    public DiaInhabil() {
        diaInhabilPK = new DiaInhabilPK();
    }

    /**
     * Sobrecarga del constructor
     * 
     * @param claveCalendario
     *            , fechaNoLaborable
     */
    public DiaInhabil(String claveCalendario, Date fechaNoLaborable) {
        diaInhabilPK = new DiaInhabilPK(claveCalendario, fechaNoLaborable);
    }

    /**
     * 
     * @return diaInhabilPK
     */
    public DiaInhabilPK getDiaInhabilPK() {
        if (this.diaInhabilPK == null) {
            this.diaInhabilPK = new DiaInhabilPK();
        }
        return this.diaInhabilPK;
    }

    /**
     * 
     * @param diaInhabilPK
     *            a fijar
     */
    public void setDiaInhabilPK(DiaInhabilPK diaInhabilPK) {
        if (diaInhabilPK != null) {
            this.diaInhabilPK = diaInhabilPK;
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
        diaInhabilPK.setFechaNoLaborable(getFechaNoLaborable());
    }

    /**
     * 
     * @return calendario
     */
    public Calendario getCalendario() {
        return calendario;
    }

    /**
     * 
     * @param calendario
     *            a fijar
     */
    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
        if (calendario != null) {
            diaInhabilPK.setClaveCalendario(calendario.getClave());
        }
    }

    /**
     * 
     * @return descrpcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * 
     * @param descripcion
     *            a fijar
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * 
     * @return vigencia
     */
    public Vigencia getVigencia() {
        return vigencia;
    }

    /**
     * 
     * @param vigencia
     *            a fijar
     */
    public void setVigencia(Vigencia vigencia) {
        this.vigencia = vigencia;
    }

    /**
     * Metodo hashCode
     * 
     * @return hash
     */
    public int hashCode() {
        int hash = 0;
        hash += (diaInhabilPK != null ? diaInhabilPK.hashCode() : 0);
        return hash;
    }

    /***
     * Metodo equals
     * 
     * @return boolean
     */
    public boolean equals(Object object) {
        if (!(object instanceof DiaInhabil)) {
            return false;
        }
        DiaInhabil other = (DiaInhabil) object;
        if (this.diaInhabilPK == null || other.diaInhabilPK == null) {
            return false;
        }
        else if (!this.diaInhabilPK.equals(other.diaInhabilPK)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo toString
     * 
     * @return "entity.DiaInhabil[diaInhabilPK=" + diaInhabilPK + "]"
     */
    public String toString() {
        return "entity.DiaInhabil[diaInhabilPK=" + diaInhabilPK + "]";
    }

}
