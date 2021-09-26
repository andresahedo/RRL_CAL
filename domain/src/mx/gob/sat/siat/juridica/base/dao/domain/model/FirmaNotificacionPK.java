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

/**
 * 
 * @author softtek
 * 
 */
public class FirmaNotificacionPK extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "idNotificacion" tipo Long
     */
    @Basic(optional = false)
    @Column(name = "IDNOTIFICACION")
    private Long idNotificacion;

    /**
     * Atributo privado "idFirma" tipo Long
     */
    @Basic(optional = false)
    @Column(name = "IDFIRMA")
    private Long idFirma;

    /**
     * Constructor
     */
    public FirmaNotificacionPK() {
        super();
    }

    /**
     * Sobrecarga de Constructor
     * 
     * @param idNotificacion
     * @param idFirma
     */
    public FirmaNotificacionPK(Long idNotificacion, Long idFirma) {
        this.idNotificacion = idNotificacion;
        this.idFirma = idFirma;
    }

    /**
     * 
     * @return idNotificacion
     */
    public Long getIdNotificacion() {
        return idNotificacion;
    }

    /**
     * 
     * @param idNotificacion
     *            a fijar
     */
    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    /**
     * 
     * @return firma
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
