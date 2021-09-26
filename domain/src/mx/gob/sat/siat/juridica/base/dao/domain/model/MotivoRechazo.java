package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RVCT_MOTIVORECHAZO")
public class MotivoRechazo extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 5897424143140500957L;

    /**
     * Atributo privado "idDocumentoSolicitud" tipo Long
     */
    @Id
    @Column(name = "IDMOTIVORECHAZO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_MOTIVORECHAZO")
    @SequenceGenerator(name = "RVCQ_MOTIVORECHAZO", sequenceName = "RVCQ_MOTIVORECHAZO", allocationSize = 1)
    private Long idMotivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTRAMITE", referencedColumnName = "IDTRAMITE", insertable = false, updatable = false)
    private Tramite tramite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDSOLDOCUMENTO", referencedColumnName = "IDSOLDOCUMENTO", insertable = false, updatable = false)
    private DocumentoSolicitud documentoSolicitud;

    @Column(name = "IDTRAMITE")
    private String idTramite;

    @Column(name = "IDSOLDOCUMENTO")
    private Long idSolDocumento;

    @Column(name = "IDETIPORECHAZO")
    private String idTipoRechazo;

    @Column(name = "CVERECHAZO")
    private String cveRechazo;

    @Column(name = "FECCREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRechazo;

    @Column(name = "IDUSUARIO")
    private String idUsuario;

    @Column(name = "IDEESTRECHAZO")
    private String idEstRechazo;

    public Long getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(Long idMotivo) {
        this.idMotivo = idMotivo;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    public DocumentoSolicitud getDocumentoSolicitud() {
        return documentoSolicitud;
    }

    public void setDocumentoSolicitud(DocumentoSolicitud documentoSolicitud) {
        this.documentoSolicitud = documentoSolicitud;
    }

    public String getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(String idTramite) {
        this.idTramite = idTramite;
    }

    public Long getIdSolDocumento() {
        return idSolDocumento;
    }

    public void setIdSolDocumento(Long idSolDocumento) {
        this.idSolDocumento = idSolDocumento;
    }

    public String getIdTipoRechazo() {
        return idTipoRechazo;
    }

    public void setIdTipoRechazo(String idTipoRechazo) {
        this.idTipoRechazo = idTipoRechazo;
    }

    public String getCveRechazo() {
        return cveRechazo;
    }

    public void setCveRechazo(String cveRechazo) {
        this.cveRechazo = cveRechazo;
    }

    public Date getFechaRechazo() {
        return fechaRechazo != null ? (Date) fechaRechazo.clone() : null;
    }

    public void setFechaRechazo(Date fechaRechazo) {
        this.fechaRechazo = fechaRechazo != null ? (Date) fechaRechazo.clone() : null;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdEstRechazo() {
        return idEstRechazo;
    }

    public void setIdEstRechazo(String idEstRechazo) {
        this.idEstRechazo = idEstRechazo;
    }

}
