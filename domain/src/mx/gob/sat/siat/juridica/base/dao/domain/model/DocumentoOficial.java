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
import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author Softtek
 * 
 */
@Entity
@Table(name = "RVCT_DOCOFICIAL")
public class DocumentoOficial extends BaseModel implements Serializable {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "idDocumentoOficial" tipo Long
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDDOCOFICIAL")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_DOCOFICIAL")
    @SequenceGenerator(name = "RVCQ_DOCOFICIAL", sequenceName = "RVCQ_DOCOFICIAL", allocationSize = 1)
    private Long idDocumentoOficial;

    /**
     * Atributo privado "ideTipoDocOficial" tipo String
     */
    @Column(name = "IDETIPODOCOFICIAL")
    private String ideTipoDocOficial;

    /**
     * Atributo privado "tramite" tipo Tramite
     */
    @JoinColumn(name = "IDTRAMITE", referencedColumnName = "IDTRAMITE")
    @ManyToOne
    private Tramite tramite;

    /**
     * Atributo privado "documentoElectronico" tipo byte
     */
    @Lob
    @Column(name = "DOCELECTRONICO")
    private byte[] documentoElectronico;

    /**
     * Atributo privado "descripcionDocumento" tipo String
     */
    @Column(name = "DESCDOCUMENTO")
    private String descripcionDocumento;

    /**
     * Atributo privado "fechaCreacion" tipo Date
     */
    @Column(name = "FECCREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    /**
     * Atributo privado "numFolioOficio" tipo String
     */
    @Column(name = "NUMFOLIOOFICIO")
    private String numFolioOficio;

    /**
     * Atributo privado "fechaBaja" tipo Date
     */
    @Column(name = "FECBAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;

    /**
     * Atributo privado "idDictamen" tipo Long
     */
    @Column(name = "URLDOCUMENTO")
    private String urlDocumento;

    /**
     * Atributo privado "idDictamen" tipo Long
     */
    @Column(name = "IDEESTDOCUMENTOOFI")
    private String estadoDocumento;

    /**
     * Atributo privado "blnActivo" tipo Boolean
     */
    @Column(name = "BLNACTIVO")
    private Integer blnActivo;

    @Column(name = "SELLO")
    private String sello;

    /**
     * Atributo privado "firma" tipo Firma
     */
    @Transient
    private Firma firma;

    /**
     * Atributo privado "documentoElectronico64" tipo byte
     */
    @Transient
    private byte[] documentoElectronico64;

    /**
     * Atributo privado "cadenaDocumentoElectronico" tipo String
     */
    @Transient
    private String cadenaDocumentoElectronico;

    @Column(name = "HASHDOC")
    private String hash;

    @Column(name = "LONGITUD")
    private Long longitud;
    
    @Column(name = "FECNOTIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNotificacion;
    @Column(name = "FECAUDIENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAudiencia;

    /**
     * 
     * @return firma
     */
    public Firma getFirma() {
        return firma;
    }

    /**
     * 
     * @param firma
     *            a fijar
     */
    public void setFirma(Firma firma) {
        this.firma = firma;
    }

    /**
     * Constructor vacio
     */
    public DocumentoOficial() {

    }

    /**
     * Sobrecarga del constructor
     * 
     * @param documentoElectronico
     */
    public DocumentoOficial(byte[] documentoElectronicoNuevo) {
        super();
        if (documentoElectronicoNuevo != null) {
            this.documentoElectronico = Arrays.copyOf(documentoElectronicoNuevo, documentoElectronicoNuevo.length - 1);
        }
        else {
            this.documentoElectronico = null;
        }
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
     * @return descripcionDocumento
     */
    public String getDescripcionDocumento() {
        return descripcionDocumento;
    }

    /**
     * 
     * @param descripcionDocumento
     *            a fijar
     */
    public void setDescripcionDocumento(String descripcionDocumento) {
        this.descripcionDocumento = descripcionDocumento;
    }

    /**
     * 
     * @return documentoElectronico
     */
    public byte[] getDocumentoElectronico() {
        if (documentoElectronico != null) {
            return Arrays.copyOf(documentoElectronico, documentoElectronico.length);
        }
        else {
            return null;
        }
    }

    /**
     * 
     * @param documentoElectronico
     *            a fijar
     */
    public void setDocumentoElectronico(byte[] documentoElectronicoNuevo) {
        if (documentoElectronicoNuevo != null) {

            this.documentoElectronico = Arrays.copyOf(documentoElectronicoNuevo, documentoElectronicoNuevo.length);
        }
        else {
            this.documentoElectronico = null;
        }
    }

    /**
     * 
     * @return idDocumentoOficial
     */
    public Long getIdDocumentoOficial() {
        return idDocumentoOficial;
    }

    /**
     * 
     * @param idDocumentoOficial
     *            a fijar
     */
    public void setIdDocumentoOficial(Long idDocumentoOficial) {
        this.idDocumentoOficial = idDocumentoOficial;
    }

    /**
     * 
     * @return ideTipoDocOficial
     */
    public String getIdeTipoDocOficial() {
        return ideTipoDocOficial;
    }

    /**
     * 
     * @param ideTipoDocOficial
     *            a fijar
     */
    public void setIdeTipoDocOficial(String ideTipoDocOficial) {
        this.ideTipoDocOficial = ideTipoDocOficial;
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
    @Override
    public int hashCode() {
        int hashCode = 0;
        hashCode += (idDocumentoOficial != null ? idDocumentoOficial.hashCode() : 0);
        return hashCode;
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id
        // fields are
        // not set
        if (!(object instanceof DocumentoOficial)) {
            return false;
        }
        DocumentoOficial other = (DocumentoOficial) object;
        if ((this.idDocumentoOficial == null && other.idDocumentoOficial != null)
                || (this.idDocumentoOficial != null && !this.idDocumentoOficial.equals(other.idDocumentoOficial))) {
            return false;
        }
        return true;
    }

    /**
     * Metodo toString
     * 
     * @return "entitys.JurDocumentoOficial[idDocumentoOficial=" +
     *         idDocumentoOficial + "]";
     */
    @Override
    public String toString() {
        return "entitys.JurDocumentoOficial[idDocumentoOficial=" + idDocumentoOficial + "]";
    }

    /**
     * 
     * @return numFolioOficio
     */
    public String getNumFolioOficio() {
        return numFolioOficio;
    }

    /**
     * 
     * @param numFolioOficio
     *            a fijar
     */
    public void setNumFolioOficio(String numFolioOficio) {
        this.numFolioOficio = numFolioOficio;
    }

    /**
     * 
     * @return documentoElectronico64
     */
    public byte[] getDocumentoElectronico64() {
        if (documentoElectronico64 != null) {
            return Arrays.copyOf(documentoElectronico64, documentoElectronico64.length);
        }
        else {
            return null;
        }
    }

    /**
     * 
     * @param documentoElectronico64
     *            a fijar
     */
    public void setDocumentoElectronico64(byte[] documentoElectronico64Nuevo) {
        if (documentoElectronico64Nuevo != null) {
            this.documentoElectronico64 =
                    Arrays.copyOf(documentoElectronico64Nuevo, documentoElectronico64Nuevo.length);
        }
        else {
            this.documentoElectronico64 = null;
        }
    }

    /**
     * 
     * @return cadenaDocumentoElectronico
     */
    public String getCadenaDocumentoElectronico() {
        return cadenaDocumentoElectronico;
    }

    /***
     * 
     * @param cadenaDocumentoElectronico
     *            a fijar
     */
    public void setCadenaDocumentoElectronico(String cadenaDocumentoElectronico) {
        this.cadenaDocumentoElectronico = cadenaDocumentoElectronico;
    }

    /**
     * 
     * @return urlDocumento
     */
    public String getUrlDocumento() {
        return urlDocumento;
    }

    /**
     * 
     * @param urlDocumento
     *            a fijar
     */
    public void setUrlDocumento(String urlDocumento) {
        this.urlDocumento = urlDocumento;
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getLongitud() {
        return longitud;
    }

    public void setLongitud(Long longitud) {
        this.longitud = longitud;
    }

    public String getSello() {
        return sello;
    }

    public void setSello(String sello) {
        this.sello = sello;
    }

    /**
     * @return the estadoDocumento
     */
    public String getEstadoDocumento() {
        return estadoDocumento;
    }

    /**
     * @param estadoDocumento
     *            the estadoDocumento to set
     */
    public void setEstadoDocumento(String estadoDocumento) {
        this.estadoDocumento = estadoDocumento;
    }

    public Date getFechaNotificacion() {
        return fechaNotificacion != null ? (Date) fechaNotificacion.clone() : null;
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion != null ? (Date) fechaNotificacion.clone() : null;
    }

    public Date getFechaAudiencia() {
        return fechaAudiencia != null ? (Date) fechaAudiencia.clone() : null;
    }

    public void setFechaAudiencia(Date fechaAudiencia) {
        this.fechaAudiencia = fechaAudiencia != null ? (Date) fechaAudiencia.clone() : null;
    }
}
