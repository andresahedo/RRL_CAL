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
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoSolicitud;

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
@DiscriminatorColumn(name = "IDTIPOTRAMITE", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("1")
@Table(name = "RVCT_SOLICITUD")
public abstract class Solicitud extends BaseModel {

    /**
     * Atributo privado "idPersonaSolicitud" tipo Long
     */
    @Transient
    private String discriminatorValue;

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 12345456457565001L;

    /**
     * Atributo privado "idSolicitud" tipo Long
     */
    @Id
    @Column(name = "IDSOLICITUD")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_SOLICITUD")
    @SequenceGenerator(name = "RVCQ_SOLICITUD", sequenceName = "RVCQ_SOLICITUD", allocationSize = 1)
    private Long idSolicitud;

    /**
     * Atributo privado "fechaCreacion" tipo Date
     */
    @Column(name = "FECCREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    /**
     * Atributo privado "fechaInicioTramite" tipo Date
     */
    @Column(name = "FECENVIO")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaInicioTramite;

    /**
     * Atributo privado "fechaActualizacion" tipo Date
     */
    @Column(name = "FECACTUALIZACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaActualizacion;

    /**
     * Atributo privado "claveModalidad" tipo Long
     */
    @Column(name = "IDTIPOTRAMITE", updatable = false, insertable = false)
    private Long claveModalidad;

    /**
     * Atributo privado "fechaEstatus" tipo Date
     */
    @Column(name = "FECESTATUS")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEstatus;

    /**
     * Atributo privado "costo" tipo Double
     */
    @Column(name = "COSTOTOTAL")
    private Double costo;

    /**
     * Atributo privado "estadoSolicitud" tipo String
     */
    @Column(name = "IDEESTSOLICITUD")
    private String estadoSolicitud;

    /**
     * Atributo privado "numeroFolioTramiteOriginal" tipo String
     */
    @Column(name = "IDTRAMITEORIG", nullable = true)
    private String numeroFolioTramiteOriginal;

    /**
     * Atributo privado "unidadAdministrativa" tipo
     * UnidadAdministrativa
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "IDUNIADMIN", nullable = true, referencedColumnName = "IDUNIADMIN")
    private UnidadAdministrativa unidadAdministrativa;

    /**
     * Atributo privado "idPersonaSolicitante" tipo LoLongng
     */
    @Column(name = "IDPERSONASOLICITANTE")
    private Long idPersonaSolicitante;

    /**
     * Atributo privado "cveUsuarioCapturista" tipo String
     */
    @Column(name = "IDUSUARIOCAPTURISTA")
    private String cveUsuarioCapturista;

    /**
     * Atributo privado "cveRolCapturista" tipo String
     */
    @Column(name = "IDROLCAPTURISTA")
    private String cveRolCapturista;

    /**
     * Atributo privado "idPeticionWS" tipo String
     */
    @Column(name = "IDPETICIONWS")
    private String idPeticionWS;

    /**
     * Atributo privado "numeroSerieCertificado" tipo String
     */
    @Column(name = "CERTSERIALNUMBER")
    private String numeroSerieCertificado;

    /**
     * Atributo privado "depuracionDocProcesada" tipo Boolean
     */
    @Column(name = "BLNDEPURACIONDOCPROCESADA")
    private Boolean depuracionDocProcesada = false;

    /**
     * Atributo privado "condicionesUso" tipo Boolean
     */
    @Column(name = "BLNCONDICIONUSO")
    private Boolean condicionesUso;

    /**
     * Atributo privado "NumeroSerieCertificado" tipo String
     */
    // @Column(name = "CERTSERIALNUMBER")
    @Transient
    private String numeroFolio;

    /**
     * Atributo privado "fechaEstatus" tipo Date
     */
    @Column(name = "FECHAAPERTURA")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaApertura;

    /**
     * Atributo privado "tipoTramiteSolicitud" tipo TipoTramite
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "IDTIPOTRAMITE", updatable = false, insertable = false)
    private TipoTramite tipoTramiteSolicitud;
    
    @OneToOne(optional = true)
    @JoinColumn(name = "IDSOLICITUD", referencedColumnName = "IDSOLICITUD", updatable = false, insertable = false)
    private SolicitudDatosGenerales solicitudDatosGenerales;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "solicitud", fetch = FetchType.LAZY)
    private Solicitante solicitante;

    public SolicitudDatosGenerales getSolicitudDatosGenerales() {
        return solicitudDatosGenerales;
    }

    public void setSolicitudDatosGenerales(SolicitudDatosGenerales solicitudDatosGenerales) {
        this.solicitudDatosGenerales = solicitudDatosGenerales;
    }

    /**
     * 
     * @return discriminatorValue
     */
    public String getDiscriminatorValue() {
        return discriminatorValue;
    }

    /**
     * 
     * @param discriminatorValue
     *            a fijar
     */
    public void setDiscriminatorValue(String discriminatorValue) {
        this.discriminatorValue = discriminatorValue;
    }

    /**
     * 
     * @return claveModalidad
     */
    public Long getClaveModalidad() {
        return claveModalidad;
    }

    /**
     * 
     * @param claveModalidad
     *            a fijar
     */
    public void setClaveModalidad(Long claveModalidad) {
        this.claveModalidad = claveModalidad;
    }

    /**
     * Constructor
     */
    public Solicitud() {
        super();
    }

    /**
     * Sobrecarga del constructor
     * 
     * @param idSolicitud
     */
    public Solicitud(long idSolicitud) {
        super();
        this.idSolicitud = idSolicitud;
    }

    /**
     * 
     * @return idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * 
     * @param idSolicitud
     *            a fijar
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
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
     * @return costo
     */
    public Double getCosto() {
        return costo;
    }

    /**
     * 
     * @param costo
     *            a fijar
     */
    public void setCosto(Double costo) {
        this.costo = costo;
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
     * @return EstadoSolicitud
     */
    public EstadoSolicitud getEstadoSolicitud() {
        return EstadoSolicitud.parse(estadoSolicitud);
    }

    /**
     * 
     * @param estadoSolicitud
     *            a fijar
     */
    public void setEstadoSolicitud(EstadoSolicitud estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud.getClave();
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
     * @return cveRolCapturista
     */
    public String getCveRolCapturista() {
        return cveRolCapturista;
    }

    /**
     * 
     * @param cveRolCapturista
     *            a fijar
     */
    public void setCveRolCapturista(String cveRolCapturista) {
        this.cveRolCapturista = cveRolCapturista;
    }

    /**
     * 
     * @return cveUsuarioCapturista
     */
    public String getCveUsuarioCapturista() {
        return cveUsuarioCapturista;
    }

    /**
     * 
     * @param cveUsuarioCapturista
     *            a fijar
     */
    public void setCveUsuarioCapturista(String cveUsuarioCapturista) {
        this.cveUsuarioCapturista = cveUsuarioCapturista;
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaActualizacion() {
        return fechaActualizacion != null ? (Date) fechaActualizacion.clone() : null;
    }

    /**
     * 
     * @param fechaActualizacion
     *            a fijar
     */
    public void setFechaActualizacion(Date fechaActualizacion) {
        if (fechaActualizacion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaActualizacion);
            this.fechaActualizacion = cal.getTime();
        }
        else {
            this.fechaActualizacion = null;
        }
    }

    /**
     * Clase abstracta para obtener el tipo de tramite
     */
    public abstract TipoTramite getTipoTramite();

    /**
     * 
     * @return unidadAdministrativa
     */
    public UnidadAdministrativa getUnidadAdministrativaRepresentacionFederal() {
        return unidadAdministrativa;
    }

    /**
     * 
     * @param unidadAdministrativa
     *            a fijar
     */
    public void setUnidadAdministrativaRepresentacionFederal(UnidadAdministrativa unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    /**
     * @return the condicionesUso
     */
    public Boolean getCondicionesUso() {
        return condicionesUso;
    }

    /**
     * @param condicionesUso
     *            the condicionesUso to set
     */
    public void setCondicionesUso(Boolean condicionesUso) {
        this.condicionesUso = condicionesUso;
    }

    /**
     * 
     * @return idPersonaSolicitante
     */
    public Long getIdPersonaSolicitante() {
        return idPersonaSolicitante;
    }

    /**
     * 
     * @param idPersonaSolicitante
     *            a fijar
     */
    public void setIdPersonaSolicitante(Long idPersonaSolicitante) {
        this.idPersonaSolicitante = idPersonaSolicitante;
    }

    /**
     * 
     * @return numeroFolioTramiteOriginal
     */
    public String getNumeroFolioTramiteOriginal() {
        return numeroFolioTramiteOriginal;
    }

    /**
     * 
     * @param numeroFolioTramiteOriginal
     *            a fijar
     */
    public void setNumeroFolioTramiteOriginal(String numeroFolioTramiteOriginal) {
        this.numeroFolioTramiteOriginal = numeroFolioTramiteOriginal;
    }

    /**
     * 
     * @param idPeticionWS
     *            a fijar
     */
    public void setIdPeticionWS(String idPeticionWS) {
        this.idPeticionWS = idPeticionWS;
    }

    /**
     * 
     * @return idPeticionWS
     */
    public String getIdPeticionWS() {
        return idPeticionWS;
    }

    /**
     * 
     * @param numeroSerieCertificado
     *            a fijar
     */
    public void setNumeroSerieCertificado(String numeroSerieCertificado) {
        this.numeroSerieCertificado = numeroSerieCertificado;
    }

    /**
     * 
     * @return NumeroSerieCertificado
     */
    public String getNumeroSerieCertificado() {
        return numeroSerieCertificado;
    }

    /**
     * 
     * @param depuracionDocProcesada
     *            a fijar
     */
    public void setDepuracionDocProcesada(Boolean depuracionDocProcesada) {
        this.depuracionDocProcesada = depuracionDocProcesada;
    }

    /**
     * 
     * @return depuracionDocProcesada
     */
    public Boolean getDepuracionDocProcesada() {
        return depuracionDocProcesada;
    }

    /**
     * 
     * @return tipoTramiteSolicitud
     */
    public TipoTramite getTipoTramiteSolicitud() {
        return tipoTramiteSolicitud;
    }

    /**
     * 
     * @param tipoTramiteSolicitud
     *            a fijar
     */
    public void setTipoTramiteSolicitud(TipoTramite tipoTramiteSolicitud) {
        this.tipoTramiteSolicitud = tipoTramiteSolicitud;
    }

    /**
     * @return the numeroFolio
     */
    public String getNumeroFolio() {
        return numeroFolio;
    }

    /**
     * @param numeroFolio
     *            the numeroFolio to set
     */
    public void setNumeroFolio(String numeroFolio) {
        this.numeroFolio = numeroFolio;
    }

    /**
     * @return the fechaApertura
     */
    public Date getFechaApertura() {
        return fechaApertura != null ? (Date) fechaApertura.clone() : null;
    }

    /**
     * @param fechaApertura
     *            the fechaApertura to set
     */
    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura != null ? (Date) fechaApertura.clone() : null;
    }

    public Solicitante getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Solicitante solicitante) {
        this.solicitante = solicitante;
    }

}
