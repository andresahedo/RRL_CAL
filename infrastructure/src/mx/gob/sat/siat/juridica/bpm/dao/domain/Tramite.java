/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.bpm.dao.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

/**
 * Bean para el inicio del tramite
 * 
 * @author softtek
 * 
 */
@XStreamAlias(value = "tramite")
public class Tramite extends BaseModel {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 5518054673085918824L;

    private ParticipantesBPM participantes;

    private ParametroTramiteBPM parametroTramite;

    /**
     * Estado procesal
     */
    private String estadoProcesal;

    /**
     * Folio del tramite
     */
    private String folioTramite;

    /**
     * Unidad de administracion
     */
    private String unidadAdmin;
    /**
     * Identificacion de la solicitud
     */
    private Long idSolicitud;
    /**
     * Tipo de tramite
     */
    private Long tipoTramite;
    /**
     * Tipo de tramite
     */
    private String descripcionTipoTramite;
    /**
     * Tipo persona
     */
    private String tipoPersona;
    /**
     * Es amaparada
     */
    private Boolean amparada;

    /**
     * 
     * @return folioTramite
     */
    public String getFolioTramite() {
        return folioTramite;
    }

    /**
     * 
     * @param folioTramite
     *            a fijar
     */
    public void setFolioTramite(String folioTramite) {
        this.folioTramite = folioTramite;
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
     * @return tipoTramite
     */
    public Long getTipoTramite() {
        return tipoTramite;
    }

    /**
     * 
     * @param tipoTramite
     *            a fijar
     */
    public void setTipoTramite(Long tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    /**
     * 
     * @return descripcionTipoTramite
     */
    public String getDescripcionTipoTramite() {
        return descripcionTipoTramite;
    }

    /**
     * 
     * @param descripcionTipoTramite
     *            a fijar
     */
    public void setDescripcionTipoTramite(String descripcionTipoTramite) {
        this.descripcionTipoTramite = descripcionTipoTramite;
    }

    /**
     * /**
     * 
     * @return unidadAdmin
     */
    public String getUnidadAdmin() {
        return unidadAdmin;
    }

    /**
     * 
     * @param unidadAdmin
     *            a fijar
     */
    public void setUnidadAdmin(String unidadAdmin) {
        this.unidadAdmin = unidadAdmin;
    }

    public ParticipantesBPM getParticipantes() {
        return participantes;
    }

    public void setParticipantes(ParticipantesBPM participantes) {
        this.participantes = participantes;
    }

    /**
     * 
     * @return estadoProcesal
     */
    public String getEstadoProcesal() {
        return estadoProcesal;
    }

    /**
     * 
     * @param estadoProcesal
     *            a fijar
     */
    public void setEstadoProcesal(String estadoProcesal) {
        this.estadoProcesal = estadoProcesal;
    }

    public ParametroTramiteBPM getParametroTramite() {
        return parametroTramite;
    }

    public void setParametroTramite(ParametroTramiteBPM parametroTramite) {
        this.parametroTramite = parametroTramite;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public Boolean getAmparada() {
        return amparada;
    }

    public void setAmparada(Boolean amparada) {
        this.amparada = amparada;
    }    

}
