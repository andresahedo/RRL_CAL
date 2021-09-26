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
@Table(name = "RVCC_DEPENDENCIA")
public class Dependencia extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "idDependencia" tipo Integer
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDDEPENDENCIA")
    private Integer idDependencia;

    /**
     * Atributo privado "nombre" tipo String
     */
    @Column(name = "NOMBRE")
    private String nombre;

    /**
     * Atributo privado "acronimo" tipo String
     */
    @Column(name = "ACRONIMO")
    private String acronimo;

    /**
     * Atributo privado "calendario" tipo Calendario
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "IDCALENDARIO", referencedColumnName = "IDCALENDARIO")
    private Calendario calendario;

    /**
     * Atributo privado "fechaCaptura" tipo Date
     */
    @Column(name = "FECCAPTURA")
    @Temporal(TemporalType.DATE)
    private Date fechaCaptura;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * Constructor vacio
     */
    public Dependencia() {}

    /**
     * 
     * Sobrecarga del constructor
     */
    public Dependencia(Integer idDependencia) {
        this.idDependencia = idDependencia;
    }

    /**
     * 
     * @return idDependencia
     */
    public Integer getIdDependencia() {
        return idDependencia;
    }

    /**
     * 
     * @param idDependencia
     *            a fijar
     */
    public void setIdDependencia(Integer idDependencia) {
        this.idDependencia = idDependencia;
    }

    /**
     * 
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @param nombre
     *            a fijar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * 
     * @return acronimo
     */
    public String getAcronimo() {
        return acronimo;
    }

    /**
     * 
     * @param acronimo
     *            a fijar
     */
    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    /**
     * 
     * @return fechaCaptura
     */
    public Date getFechaCaptura() {
        return (null == fechaCaptura ? null : (Date) fechaCaptura.clone());
    }

    /**
     * 
     * @param fechaCaptura
     *            a fijar
     */
    public void setFechaCaptura(Date fechaCaptura) {
        this.fechaCaptura = (null == fechaCaptura ? null : (Date) fechaCaptura.clone());
    }

    /**
     * Metodo hashCode
     * 
     * @return hash
     */
    public int hashCode() {
        int hash = 0;
        hash += (idDependencia != null ? idDependencia.hashCode() : 0);
        return hash;
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     */
    public boolean equals(Object object) {
        if (!(object instanceof Dependencia)) {
            return false;
        }
        Dependencia other = (Dependencia) object;
        if (this.idDependencia == null || other.idDependencia == null
                || !this.idDependencia.equals(other.idDependencia)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo toString
     * 
     * @return "entity.Dependencia[idDependencia=" + idDependencia +
     *         "]";
     */
    public String toString() {
        return "entity.Dependencia[idDependencia=" + idDependencia + "]";
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

}
