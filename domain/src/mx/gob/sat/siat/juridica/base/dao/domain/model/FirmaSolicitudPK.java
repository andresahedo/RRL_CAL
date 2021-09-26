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
public class FirmaSolicitudPK extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "idSolicitud" tipo Long
     */
    @Basic(optional = false)
    @Column(name = "IDSOLICITUD")
    private Long idSolicitud;

    /**
     * Atributo privado "idFirma" tipo Long
     */
    @Basic(optional = false)
    @Column(name = "IDFIRMA")
    private Long idFirma;

    /**
     * Constructor
     */
    public FirmaSolicitudPK() {
        super();
    }

    /**
     * Sobrecarga del constructor
     * 
     * @param idSolicitud
     * @param idFirma
     */
    public FirmaSolicitudPK(Long idSolicitud, Long idFirma) {
        this.idSolicitud = idSolicitud;
        this.idFirma = idFirma;
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
     * @return idFirma
     */
    public Long getIdFirma() {
        return idFirma;
    }

    /**
     * 
     * @param idFirma
     *            a fijar
     */
    public void setIdFirma(Long idFirma) {
        this.idFirma = idFirma;
    }

}
