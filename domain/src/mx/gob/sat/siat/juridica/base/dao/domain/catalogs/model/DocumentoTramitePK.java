/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author softtek
 */
@Embeddable
public class DocumentoTramitePK extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "idTipoDoc" tipo Integer
     */
    @Basic(optional = false)
    @Column(name = "IDTIPODOCUMENTO")
    private Integer idTipoDoc;

    /**
     * Atributo privado "idTipoTramite" tipo Integer
     */
    @Basic(optional = false)
    @Column(name = "IDTIPOTRAMITE")
    private Integer idTipoTramite;

    /**
     * Constructor vacio
     */
    public DocumentoTramitePK() {}

    /**
     * Sobrecarga de Constructor
     * 
     * @param idTipoDoc
     *            , idTipoTramite
     */
    public DocumentoTramitePK(Integer idTipoDoc, Integer idTipoTramite) {
        this.idTipoDoc = idTipoDoc;
        this.idTipoTramite = idTipoTramite;
    }

    /**
     * 
     * @return idTipoDoc
     */
    public Integer getIdTipoDoc() {
        return idTipoDoc;
    }

    /**
     * 
     * @param idTipoDoc
     *            a fijar
     */
    public void setIdTipoDoc(Integer idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
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
     * Metodo hashCode
     * 
     * @return hash
     */
    public int hashCode() {
        int hash = 0;
        hash += (idTipoDoc != null ? idTipoDoc.hashCode() : 0);
        hash += (idTipoTramite != null ? idTipoTramite.hashCode() : 0);
        return hash;
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     */
    public boolean equals(Object object) {
        if (!(object instanceof DocumentoTramitePK)) {
            return false;
        }
        DocumentoTramitePK other = (DocumentoTramitePK) object;
        if (this.idTipoDoc == null || other.idTipoDoc == null || !this.idTipoDoc.equals(other.idTipoDoc)) {
            return false;
        }
        if (this.idTipoTramite == null || other.idTipoTramite == null
                || !this.idTipoTramite.equals(other.idTipoTramite)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo toString
     * 
     * @return "entity.DocumentoTramitePK[idTipoDoc=" + idTipoDoc +
     *         ", idTipoTramite=" + idTipoTramite + "]";
     */
    public String toString() {
        return "entity.DocumentoTramitePK[idTipoDoc=" + idTipoDoc + ", idTipoTramite=" + idTipoTramite + "]";
    }
}
