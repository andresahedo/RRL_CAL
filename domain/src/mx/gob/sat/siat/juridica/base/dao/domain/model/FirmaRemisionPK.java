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
public class FirmaRemisionPK extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "idRemision" tipo Long
     */
    @Basic(optional = false)
    @Column(name = "IDREMISION")
    private Long idRemision;

    /**
     * Atributo privado "idFirma" tipo Long
     */
    @Basic(optional = false)
    @Column(name = "IDFIRMA")
    private Long idFirma;

    /**
     * Constructor
     */
    public FirmaRemisionPK() {
        super();
    }

    /**
     * Sobrecarga de Constructor
     * 
     * @param idRemision
     * @param idFirma
     */
    public FirmaRemisionPK(Long idRemision, Long idFirma) {
        this.idRemision = idRemision;
        this.idFirma = idFirma;
    }

    /**
     * 
     * @return idRemision
     */
    public Long getIdRemision() {
        return idRemision;
    }

    /**
     * 
     * @param idRemision
     *            a fijar
     */
    public void setIdRemision(Long idRemision) {
        this.idRemision = idRemision;
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
