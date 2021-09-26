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
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author softtek
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Tramite.findAll", query = "select t from Tramite t"),
        @NamedQuery(name = "Tramite.findById", query = "select t from Tramite t where t.numeroAsunto = :numeroAsunto"),
        @NamedQuery(name = "Tramite.findBySolicitud",
                query = "select t from Tramite t where t.solicitud.idSolicitud = :idSolicitud") })
@Table(name = "RVCT_TRAMITE")
public class Tramite extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 6478698709756334L;

    /**
     * Atributo privado "numeroAsunto" tipo String
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDTRAMITE")
    private String numeroAsunto;

    /**
     * Atributo privado "solicitud" tipo Solicitud
     */
    @OneToOne(optional = false)
    @JoinColumn(name = "IDSOLICITUD", referencedColumnName = "IDSOLICITUD")
    private Solicitud solicitud;

    /**
     * Atributo privado "fechaInicioTramite" tipo Date
     */
    @Column(name = "FECINITRAMITE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioTramite;

    /**
     * Atributo privado "fechaFinTramite" tipo Date
     */
    @Column(name = "FECFINTRAMITE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinTramite;

    /**
     * Atributo privado "fechaEstatus" tipo Date
     */
    @Column(name = "FECESTATUS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstatus;

    /**
     * Atributo privado "estadoTramite" tipo String
     */
    @Column(name = "IDEESTTRAMITE")
    private String estadoTramite;

    /**
     * Atributo privado "fechaRecepcion" tipo Date
     */
    @Column(name = "FECRECEPCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecepcion;

    /**
     * Atributo privado "fechaCalculoTranscurridos" tipo Date
     */
    @Column(name = "FECCALTRANSCURRIDOS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCalculoTranscurridos;

    /**
     * Atributo privado "fechaBaja" tipo Date
     */
    @Column(name = "FECBAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;

    /**
     * Constructor vacio
     */
    public Tramite() {}

    /**
     * Sobrecarga de Constructor
     * 
     * @param numeroAsunto
     */
    public Tramite(String numeroAsunto) {
        this.numeroAsunto = numeroAsunto;
    }

    /**
     * 
     * @return numeroAsunto
     */
    public String getNumeroAsunto() {
        return numeroAsunto;
    }

    /**
     * 
     * @param numeroAsunto
     *            a fijar
     */
    public void setNumeroAsunto(String numeroAsunto) {
        this.numeroAsunto = numeroAsunto;
    }

    /**
     * 
     * @return solicitud
     */
    public Solicitud getSolicitud() {
        return solicitud;
    }

    /**
     * 
     * @param solicitud
     *            a fijar
     */
    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaInicioTramite() {
        return fechaInicioTramite != null ? (Date) fechaInicioTramite.clone() : null;
    }

    /**
     * 
     * @param fechaInicioTramite
     *            a fijar
     */
    public void setFechaInicioTramite(Date fechaInicioTramite) {
        if (fechaInicioTramite != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaInicioTramite);
            this.fechaInicioTramite = cal.getTime();
        }
        else {
            this.fechaInicioTramite = null;
        }
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaFinTramite() {
        return fechaFinTramite != null ? (Date) fechaFinTramite.clone() : null;
    }

    /**
     * 
     * @param fechaFinTramite
     *            a fijar
     */
    public void setFechaFinTramite(Date fechaFinTramite) {
        if (fechaFinTramite != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaFinTramite);
            this.fechaFinTramite = cal.getTime();
        }
        else {
            this.fechaFinTramite = null;
        }
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaEstatus() {
        return fechaEstatus != null ? (Date) fechaEstatus.clone() : null;
    }

    /**
     * 
     * @param fechaEstatus
     *            a fijar
     */
    public void setFechaEstatus(Date fechaEstatus) {
        if (fechaEstatus != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaEstatus);
            this.fechaEstatus = cal.getTime();
        }
        else {
            this.fechaEstatus = null;
        }
    }

    /**
     * 
     * @return EstadoTramite
     */
    public EstadoTramite getEstadoTramite() {
        return EstadoTramite.parse(estadoTramite);
    }

    /**
     * 
     * @param estadoTramite
     *            a fijar
     */
    public void setEstadoTramite(EstadoTramite estadoTramite) {
        this.estadoTramite = estadoTramite.getClave();
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaRecepcion() {
        return fechaRecepcion != null ? (Date) fechaRecepcion.clone() : null;
    }

    /**
     * 
     * @param fechaRecepcion
     *            a fijar
     */
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

    /**
     * 
     * @return fecha
     */
    public Date getFechaCalculoTranscurridos() {
        return fechaCalculoTranscurridos != null ? (Date) fechaCalculoTranscurridos.clone() : null;
    }

    /**
     * 
     * @param fechaCalculoTranscurridos
     *            a fijar
     */
    public void setFechaCalculoTranscurridos(Date fechaCalculoTranscurridos) {
        if (fechaCalculoTranscurridos != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaCalculoTranscurridos);
            this.fechaCalculoTranscurridos = cal.getTime();
        }
        else {
            this.fechaCalculoTranscurridos = null;
        }
    }

    public Date getFechaBaja() {
        return fechaBaja != null ? (Date) fechaBaja.clone() : null;
    }

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

    @Override
    public String toString() {
        return "Tramite [numeroAsunto=" + numeroAsunto + ", solicitud=" + solicitud + ", fechaInicioTramite="
                + fechaInicioTramite + ", fechaFinTramite=" + fechaFinTramite + ", fechaEstatus=" + fechaEstatus
                + ", estadoTramite=" + estadoTramite + ", fechaRecepcion=" + fechaRecepcion
                + ", fechaCalculoTranscurridos=" + fechaCalculoTranscurridos + ", fechaBaja=" + fechaBaja + "]";
    }
    
    
}
