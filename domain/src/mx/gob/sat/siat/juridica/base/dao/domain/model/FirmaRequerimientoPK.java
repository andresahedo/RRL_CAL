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
public class FirmaRequerimientoPK extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -3035541772010992059L;

    /**
     * Atributo privado "idRequerimiento" tipo Long
     */
    @Basic(optional = false)
    @Column(name = "IDREQUERIMIENTO")
    private Long idRequerimiento;

    /**
     * Atributo privado "idFirma" tipo Long
     */
    @Basic(optional = false)
    @Column(name = "IDFIRMA")
    private Long idFirma;

    /**
     * Constructor
     */
    public FirmaRequerimientoPK() {
        super();
    }

    /**
     * Sobrecarga del constructor
     * 
     * @param idRequerimiento
     * @param idFirma
     */
    public FirmaRequerimientoPK(Long idRequerimiento, Long idFirma) {
        this.idRequerimiento = idRequerimiento;
        this.idFirma = idFirma;
    }

    /**
     * 
     * @return idRequerimiento
     */
    public Long getIdRequerimiento() {
        return idRequerimiento;
    }

    /**
     * 
     * @param idRequerimiento
     *            a fijar
     */
    public void setIdRequerimiento(Long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
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
