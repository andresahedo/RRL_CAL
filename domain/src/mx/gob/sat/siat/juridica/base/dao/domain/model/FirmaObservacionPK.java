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
public class FirmaObservacionPK extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -3035541772010992059L;

    /**
     * Atributo privado "idObservacion" tipo Long
     */
    @Basic(optional = false)
    @Column(name = "IDOBSERVACION")
    private Long idObservacion;

    /**
     * Atributo privado "idFirma" tipo Long
     */
    @Basic(optional = false)
    @Column(name = "IDFIRMA")
    private Long idFirma;

    /**
     * Constructor
     */
    public FirmaObservacionPK() {
        super();
    }

    /**
     * Sobrecarga del constructor
     * 
     * @param idObservacion
     * @param idFirma
     */
    public FirmaObservacionPK(Long idObservacion, Long idFirma) {
        this.idObservacion = idObservacion;
        this.idFirma = idFirma;
    }

    /**
     * 
     * @return idObservacion
     */
    public Long getIdObservacion() {
        return idObservacion;
    }

    /**
     * 
     * @param idObservacion
     *            a fijar
     */
    public void setIdObservacion(Long idObservacion) {
        this.idObservacion = idObservacion;
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
