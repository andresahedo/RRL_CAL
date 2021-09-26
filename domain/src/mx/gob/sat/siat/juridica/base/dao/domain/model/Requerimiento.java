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
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRequerimiento;
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
@DiscriminatorColumn(name = "IDEINFREQUERIMIENTO", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("ABSTRACT")
@DiscriminatorOptions(force = true)
@Table(name = "RVCT_REQUERIMIENTO")
public abstract class Requerimiento extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 12345456457565666L;

    /**
     * Atributo privado "idRequerimiento" tipo Long
     */
    @Id
    @Column(name = "IDREQUERIMIENTO", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_REQUERIMIENTO")
    @SequenceGenerator(name = "RVCQ_REQUERIMIENTO", sequenceName = "RVCQ_REQUERIMIENTO", allocationSize = 1)
    private Long idRequerimiento;

    /**
     * Atributo privado "tramite" tipo Tramite
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTRAMITE", referencedColumnName = "IDTRAMITE")
    private Tramite tramite;

    /**
     * Atributo privado "tipoRequerimiento" tipo String
     */
    @Column(name = "IDETIPOREQUERIMIENTO")
    private String tipoRequerimiento;

    /**
     * Atributo privado "estadoRequerimiento" tipo String
     */
    @Column(name = "IDEESTREQUERIMIENTO")
    private String estadoRequerimiento;

    /**
     * Atributo privado "unidadAdministrativa" tipo
     * UnidadAdministrativa
     */
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDAREA", referencedColumnName = "IDUNIADMIN")
    private UnidadAdministrativa unidadAdministrativa;

    /**
     * Atributo privado "descripcion" tipo String
     */
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;

    /**
     * Atributo privado "descripcionAtencionRequerimiento" tipo String
     */
    @Column(name = "DESCATENCIONREQUERIMIENTO")
    private String descripcionAtencionRequerimiento;

    /**
     * Atributo privado "justificacion" tipo String
     */
    @Lob
    @Column(name = "JUSTIFICACION")
    private String justificacion;

    /**
     * Atributo privado "fechaCreacion" tipo Date
     */
    @Column(name = "FECCREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    /**
     * Atributo privado "fechaGeneracion" tipo Date
     */
    @Column(name = "FECGENERADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaGeneracion;

    /**
     * Atributo privado "fechaVerificacion" tipo Date
     */
    @Column(name = "FECVERIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVerificacion;

    /**
     * Atributo privado "fechaAutorizacion" tipo Date
     */
    @Column(name = "FECAUTORIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAutorizacion;

    /**
     * Atributo privado "fechaAtencion" tipo Date
     */
    @Column(name = "FECATENCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAtencion;

    /**
     * Atributo privado "idMotivoRechazo" tipo Integer
     */
    @Column(name = "IDMOTIVORECHDTTRA")
    private Integer idMotivoRechazo;

    /**
     * Atributo privado "fundamento" tipo String
     */
    @Column(name = "FUNDAMENTO")
    private String fundamento;

    /**
     * Atributo privado "motivo" tipo String
     */
    @Column(name = "MOTIVO")
    private String motivo;

    /**
     * Atributo privado "numeroOficioRequerimiento" tipo String
     */
    @Column(name = "NUMOFICIOREQ")
    private String numeroOficioRequerimiento;

    /**
     * Atributo privado "motivoRequerimiento" tipo String
     */
    @Column(name = "IDEMOTIVOREQUERIMIENTO")
    private String motivoRequerimiento;

    /**
     * Atributo privado "fechaBaja" tipo Date
     */
    @Column(name = "FECBAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;

    @Column(name = "IDUSUARIO")
    private String idUsuario;

    @Transient
    private String tipoRequerimientoBpm;

    /**
     * Constructor vacio
     */
    public Requerimiento() {}

    /**
     * Sobrecarga del constructor
     * 
     * @param idRequerimiento
     */
    public Requerimiento(Long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    /**
     * 
     * @return idRequerimiento
     */
    public Long getIdRequerimiento() {
        return idRequerimiento;
    }

    /**
     * 
     * @param idRequerimiento
     *            a fijar
     */
    public void setIdRequerimiento(Long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    /**
     * 
     * @return tramite
     */
    public Tramite getTramite() {
        return tramite;
    }

    /**
     * 
     * @param tramite
     *            a fijar
     */
    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    /**
     * 
     * @return TipoRequerimiento
     */
    public TipoRequerimiento getTipoRequerimiento() {
        return TipoRequerimiento.parse(tipoRequerimiento);
    }

    /**
     * 
     * @param tipoRequerimiento
     *            a fijar
     */
    public void setTipoRequerimientoBpm(TipoRequerimiento tipoRequerimientoBpm) {
        this.tipoRequerimientoBpm = tipoRequerimientoBpm.getClave();
    }
    
    /**
     * 
     * @return TipoRequerimiento
     */
    public TipoRequerimiento getTipoRequerimientoBpm() {
        return TipoRequerimiento.parse(tipoRequerimientoBpm);
    }

    /**
     * 
     * @param tipoRequerimiento
     *            a fijar
     */
    public void setTipoRequerimiento(TipoRequerimiento tipoRequerimiento) {
        this.tipoRequerimiento = tipoRequerimiento.getClave();
    }

    /**
     * 
     * @return EstadoRequerimiento
     */
    public EstadoRequerimiento getEstadoRequerimiento() {
        return EstadoRequerimiento.parse(estadoRequerimiento);
    }

    /**
     * 
     * @param estadoRequerimiento
     *            a fijar
     */
    public void setEstadoRequerimiento(EstadoRequerimiento estadoRequerimiento) {
        this.estadoRequerimiento = estadoRequerimiento.getClave();
    }

    /**
     * 
     * @return unidadAdministrativa
     */
    public UnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * 
     * @param unidadAdministrativa
     *            a fijar
     */
    public void setUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    /**
     * 
     * @return descripcion
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
     * @return a fijar
     */
    public String getDescripcionAtencionRequerimiento() {
        return descripcionAtencionRequerimiento;
    }

    /**
     * 
     * @param descripcionAtencionRequerimiento
     *            a fijar
     */
    public void setDescripcionAtencionRequerimiento(String descripcionAtencionRequerimiento) {
        this.descripcionAtencionRequerimiento = descripcionAtencionRequerimiento;
    }

    /**
     * 
     * @return justificacion
     */
    public String getJustificacion() {
        return justificacion;
    }

    /**
     * 
     * @param justificacion
     *            a fijar
     */
    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaCreacion() {
        return fechaCreacion != null ? (Date) fechaCreacion.clone() : null;
    }

    /**
     * 
     * @param fechaCreacion
     *            a fijar
     */
    public void setFechaCreacion(Date fechaCreacion) {
        if (fechaCreacion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaCreacion);
            this.fechaCreacion = cal.getTime();
        }
        else {
            this.fechaCreacion = null;
        }
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaGeneracion() {
        return fechaGeneracion != null ? (Date) fechaGeneracion.clone() : null;
    }

    /**
     * 
     * @param fechaGeneracion
     *            a fijar
     */
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

    /**
     * 
     * @return fecha
     */
    public Date getFechaVerificacion() {
        return fechaVerificacion != null ? (Date) fechaVerificacion.clone() : null;
    }

    /**
     * 
     * @param fechaVerificacion
     *            a fijar
     */
    public void setFechaVerificacion(Date fechaVerificacion) {
        if (fechaVerificacion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaVerificacion);
            this.fechaVerificacion = cal.getTime();
        }
        else {
            this.fechaVerificacion = null;
        }
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaAutorizacion() {
        return fechaAutorizacion != null ? (Date) fechaAutorizacion.clone() : null;
    }

    /**
     * 
     * @param fechaAutorizacion
     *            a fijar
     */
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
     * 
     * @return idMotivoRechazo
     */
    public Integer getIdMotivoRechazo() {
        return idMotivoRechazo;
    }

    /**
     * 
     * @param idMotivoRechazo
     *            a fijar
     */
    public void setIdMotivoRechazo(Integer idMotivoRechazo) {
        this.idMotivoRechazo = idMotivoRechazo;
    }

    /**
     * 
     * @return fundamento
     */
    public String getFundamento() {
        return fundamento;
    }

    /**
     * 
     * @param fundamento
     *            a fijar
     */
    public void setFundamento(String fundamento) {
        this.fundamento = fundamento;
    }

    /**
     * 
     * @return motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * 
     * @param motivo
     *            a fijar
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     * 
     * @return numeroOficioRequerimiento
     */
    public String getNumeroOficioRequerimiento() {
        return numeroOficioRequerimiento;
    }

    /**
     * 
     * @param numeroOficioRequerimiento
     *            a fijar
     */
    public void setNumeroOficioRequerimiento(String numeroOficioRequerimiento) {
        this.numeroOficioRequerimiento = numeroOficioRequerimiento;
    }

    /**
     * 
     * @return motivoRequerimiento
     */
    public String getMotivoRequerimiento() {
        return motivoRequerimiento;
    }

    /**
     * 
     * @param motivoRequerimiento
     *            a fijar
     */
    public void setMotivoRequerimiento(String motivoRequerimiento) {
        this.motivoRequerimiento = motivoRequerimiento;
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
        hash += (idRequerimiento != null ? idRequerimiento.hashCode() : 0);
        return hash;
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     */
    public boolean equals(Object object) {
        if (!(object instanceof Requerimiento)) {
            return false;
        }
        Requerimiento other = (Requerimiento) object;
        if (this.idRequerimiento == null || other.idRequerimiento == null
                || !this.idRequerimiento.equals(other.idRequerimiento)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo toString
     * 
     * @return "entity.Requerimiento [idRequerimiento=" +
     *         idRequerimiento + "]";
     */
    public String toString() {
        return "entity.Requerimiento [idRequerimiento=" + idRequerimiento + "]";
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setTipoRequerimiento(String tipoRequerimiento) {
        this.tipoRequerimiento = tipoRequerimiento;
    }

    public void setEstadoRequerimiento(String estadoRequerimiento) {
        this.estadoRequerimiento = estadoRequerimiento;
    }

}
