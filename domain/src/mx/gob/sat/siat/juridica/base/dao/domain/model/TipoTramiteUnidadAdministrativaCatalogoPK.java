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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author softtek
 * 
 */
@Embeddable
public class TipoTramiteUnidadAdministrativaCatalogoPK extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "idTipoTramite" tipo Integer
     */
    @Basic(optional = false)
    @Column(name = "IDTIPOTRAMITE")
    private Integer idTipoTramite;

    /**
     * Atributo privado "clave" tipo String
     */
    @Basic(optional = false)
    @Column(name = "IDUNIADMIN")
    private String clave;

    /**
     * Atributo privado "ideTipoTramiteUniAdmin" tipo String
     */
    @Basic(optional = false)
    @Column(name = "IDETIPOTRAMITEUNIADMIN")
    private String ideTipoTramiteUniAdmin;

    /**
     * Constructor vacio
     */
    public TipoTramiteUnidadAdministrativaCatalogoPK() {}

    /**
     * Sobrecarga del constructor
     * 
     * @param idTipoTramite
     * @param clave
     * @param ideTipoTramiteUniAdmin
     */
    public TipoTramiteUnidadAdministrativaCatalogoPK(Integer idTipoTramite, String clave, String ideTipoTramiteUniAdmin) {
        this.idTipoTramite = idTipoTramite;
        this.clave = clave;
        this.ideTipoTramiteUniAdmin = ideTipoTramiteUniAdmin;
    }

    /**
     * 
     * @return idTipoTramite
     */
    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    /**
     * 
     * @param idTipoTramite
     *            a fijar
     */
    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    /**
     * 
     * @return clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * 
     * @param clave
     *            a fijar
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * 
     * @return ideTipoTramiteUniAdmin
     */
    public String getIdeTipoTramiteUniAdmin() {
        return ideTipoTramiteUniAdmin;
    }

    /**
     * 
     * @param ideTipoTramiteUniAdmin
     *            a fijar
     */
    public void setIdeTipoTramiteUniAdmin(String ideTipoTramiteUniAdmin) {
        this.ideTipoTramiteUniAdmin = ideTipoTramiteUniAdmin;
    }

    /**
     * Metodo hashCode
     * 
     * @return hash
     */
    public int hashCode() {
        int hash = 0;
        hash += (idTipoTramite != null ? idTipoTramite.hashCode() : 0);
        hash += (clave != null ? clave.hashCode() : 0);
        hash += (ideTipoTramiteUniAdmin != null ? ideTipoTramiteUniAdmin.hashCode() : 0);
        return hash;
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     */
    public boolean equals(Object object) {
        if (!(object instanceof TipoTramiteUnidadAdministrativaCatalogoPK)) {
            return false;
        }
        TipoTramiteUnidadAdministrativaCatalogoPK other = (TipoTramiteUnidadAdministrativaCatalogoPK) object;
        if (this.idTipoTramite == null || other.idTipoTramite == null
                || !this.idTipoTramite.equals(other.idTipoTramite)) {
            return false;
        }
        if (this.clave == null || other.clave == null || !this.clave.equals(other.clave)) {
            return false;
        }
        if (this.ideTipoTramiteUniAdmin == null || other.ideTipoTramiteUniAdmin == null
                || !this.ideTipoTramiteUniAdmin.equals(other.ideTipoTramiteUniAdmin)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo toString
     * 
     * @return 
     *         "entity.TipoTramiteUnidadAdministrativaCatalogoPK[idTipoTramite="
     *         + idTipoTramite + ", clave=" + clave +
     *         ", ideTipoTramiteUniAdmin=" + ideTipoTramiteUniAdmin +
     *         " ]";
     */
    public String toString() {
        return "entity.TipoTramiteUnidadAdministrativaCatalogoPK[idTipoTramite=" + idTipoTramite + ", clave=" + clave
                + ", ideTipoTramiteUniAdmin=" + ideTipoTramiteUniAdmin + " ]";
    }

}
