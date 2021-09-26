package mx.gob.sat.siat.juridica.rrl.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;

import java.util.Date;

public class DatosBandejaPendientesDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long idSolicitud;

    private String tipoTramite;

    private Date fechaCreacion;

    private String fechaCreacionStr;

    private String diasHabilesTranscurridos;

    private String url;

    private Integer idTipoTramite;

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public Date getFechaCreacion() {
        return fechaCreacion != null ? (Date) fechaCreacion.clone() : null;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion != null ? (Date) fechaCreacion.clone() : null;
    }

    public String getFechaCreacionStr() {
        return fechaCreacionStr;
    }

    public void setFechaCreacionStr(String fechaCreacionStr) {
        this.fechaCreacionStr = fechaCreacionStr;
    }

    public String getDiasHabilesTranscurridos() {
        return diasHabilesTranscurridos;
    }

    public void setDiasHabilesTranscurridos(String diasHabilesTranscurridos) {
        this.diasHabilesTranscurridos = diasHabilesTranscurridos;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the idTipoTramite
     */
    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    /**
     * @param idTipoTramite
     *            the idTipoTramite to set
     */
    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

}
