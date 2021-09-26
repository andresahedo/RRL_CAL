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
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoDocumento;

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
@Table(name = "RVCA_SOL_DOCUMENTO")
@NamedQueries({ @NamedQuery(
        name = "DocumentoSolicitud.totalDocumentosAsociados",
        query = "SELECT nvl(count(idDocumentoSolicitud),0) FROM DocumentoSolicitud e WHERE e.solicitud.idSolicitud = :idSolicitud") })
public class DocumentoSolicitud extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "idDocumentoSolicitud" tipo Long
     */
    @Id
    @Column(name = "IDSOLDOCUMENTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_DOCUMENTO")
    @SequenceGenerator(name = "RVCQ_DOCUMENTO", sequenceName = "RVCQ_DOCUMENTO", allocationSize = 1)
    private Long idDocumentoSolicitud;

    /**
     * Atributo privado "solicitud" tipo Solicitud
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDSOLICITUD", referencedColumnName = "IDSOLICITUD", insertable = false, updatable = false)
    private Solicitud solicitud;

    /**
     * Atributo privado "documento" tipo Documento
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDDOCUMENTO", referencedColumnName = "IDDOCUMENTO", insertable = false, updatable = false)
    private Documento documento;

    /**
     * Atributo privado "estadoDocumentoSolicitud" tipo String
     */
    @Column(name = "IDEESTDOCUMENTOSOL")
    private String estadoDocumentoSolicitud;

    /**
     * Atributo privado "idSolicitud" tipo Long
     */
    @Column(name = "IDSOLICITUD")
    private Long idSolicitud;

    /**
     * Atributo privado "idDocumento" tipo Long
     */
    @Column(name = "IDDOCUMENTO")
    private Long idDocumento;

    /**
     * Atributo privado "descripcion" tipo String
     */
    @Column(name = "DESCRIPCION")
    private String descripcion;

    /**
     * Atributo privado "fechaAsociacion" tipo Date
     */
    @Column(name = "FECASOCIACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaAsociacion;

    /**
     * Atributo privado "idTipoDocumento" tipo Integer
     */
    @Column(name = "IDTIPODOCUMENTO")
    private Integer idTipoDocumento;

    /**
     * Atributo privado "cvePersona" tipo Long
     */
    @Column(name = "IDPERSONA")
    private Long cvePersona;

    /**
     * Atributo privado "complementario" tipo Boolean
     */
    @Column(name = "BLNCOMPLEMENTARIO")
    private Boolean complementario;

    /**
     * Atributo privado "blnActivo" tipo Boolean
     */
    @Column(name = "BLNACTIVO")
    private Integer blnActivo;

    @Column(name = "FOLIO")
    private Long folio;

    @Column(name = "FECHAAPERTURA")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaApertura;

    /**
     * Atributo privado "requerido" tipo Boolean
     */
    @Transient
    private Boolean requerido = false;

    /**
     * Atributo privado "tipoDocumento" tipo TipoDocumento
     */
    @Transient
    private TipoDocumento tipoDocumento;

    /**
     * Constructor vacio
     */
    public DocumentoSolicitud() {

    }

    /**
     * 
     * @param doctoSol
     *            a fijar
     */
    public DocumentoSolicitud(DocumentoSolicitud doctoSol) {
        this.idSolicitud = doctoSol.getIdSolicitud();
        this.descripcion = doctoSol.getDescripcion();
        this.idTipoDocumento = doctoSol.getTipoDocumento().getIdTipoDocumento();
    }

    /**
     * 
     * @param idDocumentoSolicitud
     *            a fijar
     */
    public DocumentoSolicitud(long idDocumentoSolicitud) {
        this.idDocumentoSolicitud = idDocumentoSolicitud;
    }

    /**
     * 
     * @return idDocumentoSolicitud
     */
    public Long getIdDocumentoSolicitud() {
        return idDocumentoSolicitud;
    }

    /**
     * 
     * @param idDocumentoSolicitud
     *            a fijar
     */
    public void setIdDocumentoSolicitud(Long idDocumentoSolicitud) {
        this.idDocumentoSolicitud = idDocumentoSolicitud;
    }

    /**
     * @return the solicitud
     */
    public Solicitud getSolicitud() {
        return solicitud;
    }

    /**
     * @param solicitud
     *            the solicitud to set
     */
    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
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
     * @return idDocumento
     */
    public Long getIdDocumento() {
        return idDocumento;
    }

    /**
     * 
     * @param idDocumento
     *            a fijar
     */
    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
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
     * @return documento
     */
    public Documento getDocumento() {
        return documento;
    }

    /**
     * 
     * @param documento
     *            a fijar
     * 
     */
    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    /**
     * 
     * @return requerido
     */
    public Boolean getRequerido() {
        return requerido == null ? false : true;
    }

    /**
     * 
     * @param requerido
     *            a fijar
     */
    public void setRequerido(Boolean requerido) {
        this.requerido = requerido;
    }

    /**
     * 
     * @return complementario
     */
    public Boolean getComplementario() {
        return complementario;
    }

    /**
     * 
     * @param complementario
     *            a fijar
     */
    public void setComplementario(Boolean complementario) {
        this.complementario = complementario;
    }

    /**
     * 
     * @return idTipoDocumento
     */
    public Integer getIdTipoDocumento() {
        return idTipoDocumento;
    }

    /**
     * 
     * @param idTipoDocumento
     *            a fijar
     */
    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    /**
     * 
     * @param estadoDocumentoSolicitud
     *            a fijar
     */
    public void setEstadoDocumentoSolicitud(String estadoDocumentoSolicitud) {
        this.estadoDocumentoSolicitud = estadoDocumentoSolicitud;
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
     * @return fecha
     */
    public Date getFechaAsociacion() {
        return fechaAsociacion != null ? (Date) fechaAsociacion.clone() : null;
    }

    /**
     * 
     * @param fechaAsociacion
     *            a fijar
     */
    public void setFechaAsociacion(Date fechaAsociacion) {
        if (fechaAsociacion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaAsociacion);
            this.fechaAsociacion = cal.getTime();
        }
        else {
            this.fechaAsociacion = null;
        }
    }

    /**
     * 
     * @return tipoDocumento
     */
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * 
     * @param tipoDocumento
     *            a fijar
     */
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * 
     * @param cvePersona
     *            a fijar
     */
    public void setCvePersona(Long cvePersona) {
        this.cvePersona = cvePersona;
    }

    /**
     * 
     * @return cvePersona
     */
    public Long getCvePersona() {
        return cvePersona;
    }

    /**
     * 
     * @return estadoDocumentoSolicitud
     */
    public String getEstadoDocumentoSolicitud() {
        return estadoDocumentoSolicitud;
    }

    /**
     * 
     * @return blnActivo
     */
    public Integer getBlnActivo() {
        return blnActivo;
    }

    /**
     * 
     * @param blnActivo
     *            a fijar
     */
    public void setBlnActivo(Integer blnActivo) {
        this.blnActivo = blnActivo;
    }

    public Long getFolio() {
        return folio;
    }

    public void setFolio(Long folio) {
        this.folio = folio;
    }

    public Date getFechaApertura() {
        return fechaApertura != null ? (Date) fechaApertura.clone() : null;
    }

    public void setFechaApertura(Date fechaApertura) {
        if (fechaApertura != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaApertura);
            this.fechaApertura = cal.getTime();
        }
        else {
            this.fechaApertura = null;
        }
    }

}
