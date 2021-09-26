/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;

/**
 * 
 * @author softtek
 * 
 */
public class TipoTramiteUnidadAdministrativaDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = -3181412457374422901L;

    private int idTipoTramiteUniAdmin;
    private int idTipoTramite;
    private String idUnidadAdministrativa;
    private String ideTipoTramiteUniAdmin;
    private int blnActivo;
    private String descModalidad;

    /**
     * @return the idTipoTramiteUniAdmin
     */
    public int getIdTipoTramiteUniAdmin() {
        return idTipoTramiteUniAdmin;
    }

    /**
     * @param idTipoTramiteUniAdmin
     *            the idTipoTramiteUniAdmin to set
     */
    public void setIdTipoTramiteUniAdmin(int idTipoTramiteUniAdmin) {
        this.idTipoTramiteUniAdmin = idTipoTramiteUniAdmin;
    }

    /**
     * @return the idTipoTramite
     */
    public int getIdTipoTramite() {
        return idTipoTramite;
    }

    /**
     * @param idTipoTramite
     *            the idTipoTramite to set
     */
    public void setIdTipoTramite(int idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    /**
     * @return the idUnidadAdministrativa
     */
    public String getIdUnidadAdministrativa() {
        return idUnidadAdministrativa;
    }

    /**
     * @param idUnidadAdministrativa
     *            the idUnidadAdministrativa to set
     */
    public void setIdUnidadAdministrativa(String idUnidadAdministrativa) {
        this.idUnidadAdministrativa = idUnidadAdministrativa;
    }

    /**
     * @return the ideTipoTramiteUniAdmin
     */
    public String getIdeTipoTramiteUniAdmin() {
        return ideTipoTramiteUniAdmin;
    }

    /**
     * @param ideTipoTramiteUniAdmin
     *            the ideTipoTramiteUniAdmin to set
     */
    public void setIdeTipoTramiteUniAdmin(String ideTipoTramiteUniAdmin) {
        this.ideTipoTramiteUniAdmin = ideTipoTramiteUniAdmin;
    }

    /**
     * @return the blnActivo
     */
    public int getBlnActivo() {
        return blnActivo;
    }

    /**
     * @param blnActivo
     *            the blnActivo to set
     */
    public void setBlnActivo(int blnActivo) {
        this.blnActivo = blnActivo;
    }

    /**
     * @return the descModalidad
     */
    public String getDescModalidad() {
        return descModalidad;
    }

    /**
     * @param descModalidad
     *            the descModalidad to set
     */
    public void setDescModalidad(String descModalidad) {
        this.descModalidad = descModalidad;
    }

}
