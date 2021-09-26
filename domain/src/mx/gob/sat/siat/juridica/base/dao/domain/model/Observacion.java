/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "RVCT_OBSERVACION")
public class Observacion extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 3303261047216040172L;

    /**
     * Atributo privado "idObservacion" tipo Long
     */
    @Id
    @Column(name = "IDOBSERVACION", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_OBSERVACION")
    @SequenceGenerator(name = "RVCQ_OBSERVACION", sequenceName = "RVCQ_OBSERVACION", allocationSize = 1)
    private Long idObservacion;

    /**
     * Atributo privado "tramite" tipo Tramite
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTRAMITE", referencedColumnName = "IDTRAMITE")
    private Tramite tramite;

    /**
     * Atributo privado "tipoObservacion" tipo String
     */
    @Column(name = "IDEESTOBSERVACION")
    private String estadoObservacion;

    /**
     * Atributo privado "descripcion" tipo String
     */
    @Column(name = "OBSERVACION")
    private String observacionDesc;

    /**
     * Atributo privado "fechaCreacion" tipo Date
     */
    @Column(name = "FECOBSERVACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaObservacion;

    /**
     * Atributo privado "fechaAtencion" tipo Date
     */
    @Column(name = "FECATENCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAtencion;

    /**
     * Atributo privado "fechaBaja" tipo Date
     */
    @Column(name = "FECBAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;

    /**
     * Atributo privado "IDUSUARIO" tipo String
     */
    @Column(name = "IDUSUARIO")
    private String rfcAbogado;

    /**
     * Constructor
     */
    public Observacion() {
        super();
    }

    /**
     * Sobrecarga del constructor
     * 
     * @param idObservacion
     */
    public Observacion(Long idObservacion) {
        super();
        this.idObservacion = idObservacion;
    }

    /**
     * 
     * @return idObservacion
     */
    public Long getIdObservacion() {
        return idObservacion;
    }

    /**
     * 
     * @param idObservacion
     *            a fijar
     */
    public void setIdObservacion(Long idObservacion) {
        this.idObservacion = idObservacion;
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaAtencion() {
        return fechaAtencion != null ? (Date) fechaAtencion.clone() : null;
    }

    /**
     * 
     * @param fechaAtencion
     *            a fijar
     */
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

    /**
     * @return the fechaObservacion
     */
    public Date getFechaObservacion() {
        return fechaObservacion != null ? (Date) fechaObservacion.clone() : null;
    }

    /**
     * @param fechaObservacion
     *            the fechaObservacion to set
     */
    public void setFechaObservacion(Date fechaObservacion) {
        if (fechaObservacion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaObservacion);
            this.fechaObservacion = cal.getTime();
        }
        else {
            this.fechaObservacion = null;
        }
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaBaja() {
        return fechaBaja != null ? (Date) fechaBaja.clone() : null;
    }

    /**
     * 
     * @param fechaBaja
     *            a fijar
     */
    public void setFechaBaja(Date fechaBaja) {
        if (fechaBaja != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaBaja);
            this.fechaBaja = cal.getTime();
        }
        else {
            this.fechaBaja = null;
        }
    }

    /**
     * Metodo hashCode
     * 
     * @return hash
     */
    public int hashCode() {
        int hash = 0;
        hash += (idObservacion != null ? idObservacion.hashCode() : 0);
        return hash;
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     */
    public boolean equals(Object object) {
        if (!(object instanceof Observacion)) {
            return false;
        }
        Observacion other = (Observacion) object;
        if (this.idObservacion == null || other.idObservacion == null
                || !this.idObservacion.equals(other.idObservacion)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo toString
     * 
     * @return "entity.Observacion [idObservacion=" + idObservacion +
     *         "]";
     */
    public String toString() {
        return "entity.Observacion [idObservacion=" + idObservacion + "]";
    }

    /**
     * @return the estadoObservacion
     */
    public String getEstadoObservacion() {
        return estadoObservacion;
    }

    /**
     * @param estadoObservacion
     *            the estadoObservacion to set
     */
    public void setEstadoObservacion(String estadoObservacion) {
        this.estadoObservacion = estadoObservacion;
    }

    /**
     * @return the rfcAbogado
     */
    public String getRfcAbogado() {
        return rfcAbogado;
    }

    /**
     * @param rfcAbogado
     *            the rfcAbogado to set
     */
    public void setRfcAbogado(String rfcAbogado) {
        this.rfcAbogado = rfcAbogado;
    }

    /**
     * @return the observacionDesc
     */
    public String getObservacionDesc() {
        return observacionDesc;
    }

    /**
     * @param observacionDesc
     *            the observacionDesc to set
     */
    public void setObservacionDesc(String observacionDesc) {
        this.observacionDesc = observacionDesc;
    }

    /**
     * @return the tramite
     */
    public Tramite getTramite() {
        return tramite;
    }

    /**
     * @param tramite
     *            the tramite to set
     */
    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

}
