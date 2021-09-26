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
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoDictamen;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.SentidoDictamen;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author Softtek
 * 
 */
@Entity
@Inheritance
@DiscriminatorColumn(name = "IDETIPODICTAMEN", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorOptions(force = true)
@Table(name = "RVCT_DICTAMEN")
public abstract class Dictamen extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1467878797883454651L;

    /**
     * Atributo privado "idDictamen" tipo Long
     */
    @Id
    @Column(name = "IDDICTAMEN", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DICTAMEN")
    @SequenceGenerator(name = "SEQ_DICTAMEN", sequenceName = "SEQ_DICTAMEN", allocationSize = 1)
    private Long idDictamen;

    /**
     * Atributo privado "tramite" tipo Tramite
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTRAMITE", referencedColumnName = "IDTRAMITE", insertable = true, updatable = false)
    private Tramite tramite;

    /**
     * Atributo privado "estadoDictamen" tipo String
     */
    @Column(name = "IDEESTDICTAMEN")
    private String estadoDictamen;

    /**
     * Atributo privado "fechaCreacion" tipo Date
     */
    @Column(name = "FECCREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    /**
     * Atributo privado "fechaEmision" tipo Date
     */
    @Column(name = "FECEMISION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;

    /**
     * Atributo privado "fechaAutorizacion" tipo Date
     */
    @Column(name = "FECAUTORIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAutorizacion;

    /**
     * Atributo privado "fechaVerificacion" tipo Date
     */
    @Column(name = "FECVERIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVerificacion;

    /**
     * Atributo privado "textoDictamen" tipo String
     */
    @Lob
    @Column(name = "TEXTODICTAMEN", nullable = true)
    private String textoDictamen;

    /**
     * Atributo privado "opinion" tipo String
     */
    @Lob
    @Column(name = "OBSERVACION", nullable = true)
    private String opinion;

    /**
     * Atributo privado "fechaObservacion" tipo Date
     */
    @Column(name = "FECOBSERVACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaObservacion;

    /**
     * Atributo privado "fechaCita" tipo Date
     */
    @Column(name = "FECCITA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCita;

    /**
     * Atributo privado "justificacion" tipo String
     */
    @Column(name = "JUSTIFICACION")
    private String justificacion;

    /**
     * Atributo privado "sentidoDictamen" tipo String
     */
    @Column(name = "IDESENTDICTAMEN")
    private String sentidoDictamen;

    /**
     * Atributo privado "plazoAnios" tipo Integer
     */
    @Column(name = "PLAZOANIOS")
    private Integer plazoAnios;

    /**
     * Atributo privado "plazoMeses" tipo String
     */
    @Column(name = "IDEPLAZOMESES")
    private String plazoMeses;

    /**
     * Atributo privado "fechaInicioVigencia" tipo Date
     */
    @Column(name = "FECINIVIGENCIA")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;

    /**
     * Atributo privado "fechaFinVigencia" tipo Date
     */
    @Column(name = "FECFINVIGENCIA")
    @Temporal(TemporalType.DATE)
    private Date fechaFinVigencia;

    /**
     * Atributo privado "idTipoDictamen" tipo Long
     */
    @Column(name = "IDTIPODICTAMEN")
    private Long idTipoDictamen;

    /**
     * Atributo privado "plazo" tipo String
     */
    @Column(name = "PLAZO")
    private String plazo;

    /**
     * Atributo privado "numeroFolioExterno" tipo String
     */
    @Column(name = "NUM_FOLIO_EXTERNO")
    private String numeroFolioExterno;

    /**
     * Constructor vacio
     */
    public Dictamen() {}

    /**
     * Sobrecarga del constructor
     * 
     * @return idDictamen
     */
    public Long getIdDictamen() {
        return idDictamen;
    }

    /**
     * 
     * @param idDictamen
     *            a fijar
     */
    public void setIdDictamen(Long idDictamen) {
        this.idDictamen = idDictamen;
    }

    /**
     * 
     * @return EstadoDictamen
     */
    public EstadoDictamen getEstadoDictamen() {
        return EstadoDictamen.parse(estadoDictamen);
    }

    /**
     * 
     * @param estadoDictamen
     *            a fijar
     */
    public void setEstadoDictamen(EstadoDictamen estadoDictamen) {
        this.estadoDictamen = estadoDictamen.getClave();
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
    public Date getFechaEmision() {
        return fechaEmision != null ? (Date) fechaEmision.clone() : null;
    }

    /**
     * 
     * @param fechaEmision
     *            a fijar
     */
    public void setFechaEmision(Date fechaEmision) {
        if (fechaEmision != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaEmision);
            this.fechaEmision = cal.getTime();
        }
        else {
            this.fechaEmision = null;
        }
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaObservacion() {
        return fechaObservacion != null ? (Date) fechaObservacion.clone() : null;
    }

    /**
     * 
     * @param fechaObservacion
     *            a fijar
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
    public Date getFechaCita() {
        return fechaCita != null ? (Date) fechaCita.clone() : null;
    }

    /**
     * 
     * @param fechaCita
     *            a fijar
     */
    public void setFechaCita(Date fechaCita) {
        if (fechaCita != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaCita);
            this.fechaCita = cal.getTime();
        }
        else {
            this.fechaCita = null;
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
     * @return SentidoDictamen
     */
    public SentidoDictamen getSentidoDictamen() {
        return SentidoDictamen.parse(sentidoDictamen);
    }

    /**
     * 
     * @param sentidoDictamen
     *            a fijar
     */
    public void setSentidoDictamen(SentidoDictamen sentidoDictamen) {
        if (sentidoDictamen != null) {
            this.sentidoDictamen = sentidoDictamen.getClave();
        }
        else {
            this.sentidoDictamen = null;
        }
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
     * @return textoDictamen
     */
    public String getTextoDictamen() {
        return textoDictamen;
    }

    /**
     * 
     * @param textoDictamen
     *            a fijar
     */
    public void setTextoDictamen(String textoDictamen) {
        this.textoDictamen = textoDictamen;
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
     * @return opinion
     */
    public String getOpinion() {
        return opinion;
    }

    /**
     * 
     * @param opinion
     *            a fijar
     */
    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaInicioVigencia() {
        return fechaInicioVigencia != null ? (Date) fechaInicioVigencia.clone() : null;
    }

    /**
     * 
     * @param fechaInicioVigencia
     *            a fijar
     */
    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        if (fechaInicioVigencia != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaInicioVigencia);
            this.fechaInicioVigencia = cal.getTime();
        }
        else {
            this.fechaInicioVigencia = null;
        }
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaFinVigencia() {
        return fechaFinVigencia != null ? (Date) fechaFinVigencia.clone() : null;
    }

    /**
     * 
     * @param fechaFinVigencia
     *            a fijar
     */
    public void setFechaFinVigencia(Date fechaFinVigencia) {
        if (fechaFinVigencia != null) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(fechaFinVigencia);
            this.fechaFinVigencia = calendar.getTime();
        }
        else {
            this.fechaFinVigencia = null;
        }
    }

    /**
     * Metodo hashCode
     * 
     * @return hash
     */
    public int hashCode() {
        int hash = 0;
        hash += (idDictamen != null ? idDictamen.hashCode() : 0);
        return hash;
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     */
    public boolean equals(Object object) {
        if (!(object instanceof Dictamen)) {
            return false;
        }
        Dictamen other = (Dictamen) object;
        if (this.idDictamen == null || other.idDictamen == null || !this.idDictamen.equals(other.idDictamen)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo toString
     * 
     * @return "entity.Dictamen [idDictamen=" + idDictamen + "]";
     */
    public String toString() {
        return "entity.Dictamen [idDictamen=" + idDictamen + "]";
    }

    /**
     * 
     * @param plazoAnios
     *            a fijar
     */
    public void setPlazoAnios(Integer plazoAnios) {
        this.plazoAnios = plazoAnios;
    }

    /**
     * 
     * @return plazoAnios
     */
    public Integer getPlazoAnios() {
        return plazoAnios;
    }

    /**
     * 
     * @param plazoMeses
     *            a fijar
     */
    public void setPlazoMesesS(String plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public void setPlazoMeses(String plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public String getPlazoMeses() {
        return this.plazoMeses;
    }

    /**
     * 
     * @return numeroFolioExterno
     */
    public String getNumeroFolioExterno() {
        return numeroFolioExterno;
    }

    /**
     * 
     * @param numeroFolioExterno
     *            a fijar
     */
    public void setNumeroFolioExterno(String numeroFolioExterno) {
        this.numeroFolioExterno = numeroFolioExterno;
    }

    /**
     * 
     * @return plazo
     */
    public String getPlazo() {
        return plazo;
    }

    /**
     * 
     * @param plazo
     *            a fijar
     */
    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public Long getIdTipoDictamen() {
        return this.idTipoDictamen;
    }

    public void setIdTipoDictamen(Long idTipoDictamen) {
        this.idTipoDictamen = idTipoDictamen;
    }

}
