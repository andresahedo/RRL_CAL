/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dto;

/**
 * DTO que representa los datos de una solicitud.
 * 
 * @author Softtek
 * 
 */
public class SolicitudDTO extends BaseDataTransferObject {

    private static final long serialVersionUID = -9080983534763646760L;
    /** Identificador de la solicitud. */
    private Long idSolicitud;
    /** RFC del solicitante que genera la solicitud. */
    private String rfc;
    /** Tipo de Tramite que genera la solicitud. */
    private String tipoTramite;

    private String cveRolCapturista;

    /**
     * DTO que representa los datos de la persona que genera la
     * solicitud.
     */
    private PersonaSolicitudDTO solicitante;

    /**
     * @return el(la) idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud
     *            el(la) idSolicitud a fijar
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * @return el(la) rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * @param rfc
     *            el(la) rfc a fijar
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * @return el(la) tipoTramite
     */
    public String getTipoTramite() {
        return tipoTramite;
    }

    /**
     * @param tipoTramite
     *            el(la) tipoTramite a fijar
     */
    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    /**
     * 
     * @return el(la) solicitante
     */
    public PersonaSolicitudDTO getSolicitante() {
        return solicitante;
    }

    /**
     * 
     * @param solicitante
     *            el(la) solicitante a fijar
     */
    public void setSolicitante(PersonaSolicitudDTO solicitante) {
        this.solicitante = solicitante;
    }

    public String getCveRolCapturista() {
        return cveRolCapturista;
    }

    public void setCveRolCapturista(String cveRolCapturista) {
        this.cveRolCapturista = cveRolCapturista;
    }

}
