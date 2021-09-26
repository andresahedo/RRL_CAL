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
 * @author softtek
 */
@Entity
@Table(name = "RVCA_REQ_SOLDOC")
public class DocumentoSolicitudRequerimiento extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "idDocumentoRequerimientoSolicitud" tipo Long
     */
    @Id
    @Column(name = "IDREQSOLDOC")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_REQ_SOLDOC")
    @SequenceGenerator(name = "RVCQ_REQ_SOLDOC", sequenceName = "RVCQ_REQ_SOLDOC", allocationSize = 1)
    private Long idDocumentoRequerimientoSolicitud;

    /**
     * Atributo privado "documentoSolicitud" tipo DocumentoSolicitud
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDSOLDOCUMENTO", referencedColumnName = "IDSOLDOCUMENTO")
    private DocumentoSolicitud documentoSolicitud;

    /**
     * Atributo privado "TipoDocumento" tipo TipoDocumento
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDTIPODOCUMENTO", referencedColumnName = "IDTIPODOCUMENTO")
    private TipoDocumento tipoDocumento;

    /**
     * Atributo privado "requerimiento" tipo Requerimiento
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDREQUERIMIENTO", referencedColumnName = "IDREQUERIMIENTO")
    private Requerimiento requerimiento;

    /**
     * Atributo privado "fechaBaja" tipo Date
     */
    @Column(name = "FECBAJA")
    private Date fechaBaja;

    /**
     * Atributo privado "blnActivo" tipo Boolean
     */
    @Column(name = "BLNACTIVO")
    private Integer blnActivo;

    /**
     * Constructor vacio
     */
    public DocumentoSolicitudRequerimiento() {

    }

    /**
     * Sobrecarga de constructor
     * 
     * @param idDocumentoRequerimientoSolicitud
     */
    public DocumentoSolicitudRequerimiento(long idDocumentoRequerimientoSolicitud) {
        this.idDocumentoRequerimientoSolicitud = idDocumentoRequerimientoSolicitud;
    }

    /**
     * 
     * @return idDocumentoRequerimientoSolicitud
     */
    public Long getIdDocumentoSolicitud() {
        return idDocumentoRequerimientoSolicitud;
    }

    /**
     * 
     * @param idDocumentoRequerimientoSolicitud
     *            a fijar
     */
    public void setIdDocumentoSolicitud(Long idDocumentoRequerimientoSolicitud) {
        this.idDocumentoRequerimientoSolicitud = idDocumentoRequerimientoSolicitud;
    }

    /**
     * @return the idDocumentoRequerimientoSolicitud
     */
    public Long getIdDocumentoRequerimientoSolicitud() {
        return idDocumentoRequerimientoSolicitud;
    }

    /**
     * @param idDocumentoRequerimientoSolicitud
     *            the idDocumentoRequerimientoSolicitud to set
     */
    public void setIdDocumentoRequerimientoSolicitud(Long idDocumentoRequerimientoSolicitud) {
        this.idDocumentoRequerimientoSolicitud = idDocumentoRequerimientoSolicitud;
    }

    /**
     * @return the documentoSolicitud
     */
    public DocumentoSolicitud getDocumentoSolicitud() {
        return documentoSolicitud;
    }

    /**
     * @param documentoSolicitud
     *            the documentoSolicitud to set
     */
    public void setDocumentoSolicitud(DocumentoSolicitud documentoSolicitud) {
        this.documentoSolicitud = documentoSolicitud;
    }

    /**
     * @return the tipoDocumento
     */
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento
     *            the tipoDocumento to set
     */
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * @return the requerimiento
     */
    public Requerimiento getRequerimiento() {
        return requerimiento;
    }

    /**
     * @param requerimiento
     *            the requerimiento to set
     */
    public void setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
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

}
