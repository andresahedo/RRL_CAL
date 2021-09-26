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
public class FirmaResolucionPK extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "idResolucion" tipo Long
     */
    @Basic(optional = false)
    @Column(name = "IDRESOLUCION")
    private Long idResolucion;

    /**
     * Atributo privado "idFirma" tipo Long
     */
    @Basic(optional = false)
    @Column(name = "IDFIRMA")
    private Long idFirma;

    /**
     * Constructor
     */
    public FirmaResolucionPK() {
        super();
    }

    /**
     * Sobrecarga de Constructor
     * 
     * @param idResolucion
     * @param idFirma
     */
    public FirmaResolucionPK(Long idResolucion, Long idFirma) {
        this.idResolucion = idResolucion;
        this.idFirma = idFirma;
    }

    /**
     * 
     * @return idResolucion
     */
    public Long getIdResolucion() {
        return idResolucion;
    }

    /**
     * 
     * @param idResolucion
     *            a fijar
     */
    public void setIdResolucion(Long idResolucion) {
        this.idResolucion = idResolucion;
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
