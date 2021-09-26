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
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoNotificacion;
import org.hibernate.annotations.DiscriminatorOptions;

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
@Inheritance
@DiscriminatorColumn(name = "IDETIPONOTIFICACION", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("ABSTRACT")
@DiscriminatorOptions(force = true)
@Table(name = "RVCT_NOTIFICACION")
public abstract class Notificacion extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -4643033993536102934L;

    /**
     * Atributo privado "idNotificacion" tipo Long
     */
    @Id
    @Column(name = "IDNOTIFICACION", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_NOTIFICACION")
    @SequenceGenerator(name = "RVCQ_NOTIFICACION", sequenceName = "RVCQ_NOTIFICACION", allocationSize = 1)
    private Long idNotificacion;

    /**
     * Atributo privado "estadoNotificacion" tipo String
     */
    @Column(name = "IDEESTNOTIFICACION")
    private String estadoNotificacion;

    /**
     * Atributo privado "fechaEnvioNotificacion" tipo Date
     */
    @Column(name = "FECENVIONOTIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEnvioNotificacion;

    /**
     * Atributo privado "fechaAcuseNotificacion" tipo Date
     */
    @Column(name = "FECACUSENOTIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAcuseNotificacion;

    /**
     * Atributo privado "fechaInicioNotificacionEstrados" tipo Date
     */
    @Column(name = "FECININOTIFICACIONESTRADOS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioNotificacionEstrados;

    /**
     * Atributo privado "folioPublicacionEstrados" tipo String
     */
    @Column(name = "FOLIOPUBLICACIONESTRADOS")
    private String folioPublicacionEstrados;

    /**
     * Atributo privado "fechaBaja" tipo Date
     */
    @Column(name = "FECBAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;

    /**
     * 
     * @return idNotificacion
     */
    public Long getIdNotificacion() {
        return idNotificacion;
    }

    /**
     * 
     * @param idNotificacion
     *            a fijar
     */
    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    /**
     * 
     * @return EstadoNotificacion
     */
    public EstadoNotificacion getEstadoNotificacion() {
        return EstadoNotificacion.parse(estadoNotificacion);
    }

    /**
     * 
     * @param estadoNotificacion
     *            a fijar
     */
    public void setEstadoNotificacion(EstadoNotificacion estadoNotificacion) {
        this.estadoNotificacion = estadoNotificacion.getClave();
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaEnvioNotificacion() {
        return fechaEnvioNotificacion != null ? (Date) fechaEnvioNotificacion.clone() : null;
    }

    /**
     * 
     * @param fechaEnvioNotificacion
     *            a fijar
     */
    public void setFechaEnvioNotificacion(Date fechaEnvioNotificacion) {
        if (fechaEnvioNotificacion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaEnvioNotificacion);
            this.fechaEnvioNotificacion = cal.getTime();
        }
        else {
            this.fechaEnvioNotificacion = null;
        }
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaAcuseNotificacion() {
        return fechaAcuseNotificacion != null ? (Date) fechaAcuseNotificacion.clone() : null;
    }

    /**
     * 
     * @param fechaAcuseNotificacion
     *            a fijar
     */
    public void setFechaAcuseNotificacion(Date fechaAcuseNotificacion) {
        if (fechaAcuseNotificacion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaAcuseNotificacion);
            this.fechaAcuseNotificacion = cal.getTime();
        }
        else {
            this.fechaAcuseNotificacion = null;
        }
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaInicioNotificacionEstrados() {
        return fechaInicioNotificacionEstrados != null ? (Date) fechaInicioNotificacionEstrados.clone() : null;
    }

    /**
     * 
     * @param fechaInicioNotificacionEstrados
     *            a fijar
     */
    public void setFechaInicioNotificacionEstrados(Date fechaInicioNotificacionEstrados) {
        if (fechaInicioNotificacionEstrados != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaInicioNotificacionEstrados);
            this.fechaInicioNotificacionEstrados = cal.getTime();
        }
        else {
            this.fechaInicioNotificacionEstrados = null;
        }
    }

    /**
     * 
     * @return folioPublicacionEstrados
     */
    public String getFolioPublicacionEstrados() {
        return folioPublicacionEstrados;
    }

    /**
     * 
     * @param folioPublicacionEstrados
     *            a fijar
     */
    public void setFolioPublicacionEstrados(String folioPublicacionEstrados) {
        this.folioPublicacionEstrados = folioPublicacionEstrados;
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

}
