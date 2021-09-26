package mx.gob.sat.siat.juridica.base.dto;

import java.util.Date;

public class MotivoRechazoDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 8913686720742260887L;

    private String idMotivo;
    private String idTramite;
    private Long idSolDocto;
    private String idTipoRechazo;
    private String cveRechazo;
    private String motivo;
    private String rfc;
    private Long idTarea;
    private FirmaDTO firma;
    private String nombreDocumento;
    private String tipoDocumento;
    private DocumentoDTO doc;
    private String idUsuario;
    private String idEstRechazo;
    private Date fechaRechazo;

    public String getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(String idMotivo) {
        this.idMotivo = idMotivo;
    }

    public String getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(String idTramite) {
        this.idTramite = idTramite;
    }

    public Long getIdSolDocto() {
        return idSolDocto;
    }

    public void setIdSolDocto(Long idSolDocto) {
        this.idSolDocto = idSolDocto;
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

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public FirmaDTO getFirma() {
        return firma;
    }

    public void setFirma(FirmaDTO firma) {
        this.firma = firma;
    }

    public Long getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public DocumentoDTO getDoc() {
        return doc;
    }

    public void setDoc(DocumentoDTO doc) {
        this.doc = doc;
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

    public Date getFechaRechazo() {
        return fechaRechazo != null ? (Date) fechaRechazo.clone() : null;
    }

    public void setFechaRechazo(Date fechaRechazo) {
        this.fechaRechazo = fechaRechazo != null ? (Date) fechaRechazo.clone() : null;
    }

}
