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

import javax.persistence.*;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@Table(name = "RVCA_REMISION_FIRMA")
public class FirmaRemision extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "firmaRemisionPK" tipo FirmaRemisionPK
     */
    @EmbeddedId
    private FirmaRemisionPK firmaRemisionPK;

    /**
     * Atributo privado "remision" tipo Remision
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDREMISION", referencedColumnName = "IDREMISION", insertable = false, updatable = false)
    private Remision remision;

    /**
     * Atributo privado "firma" tipo Firma
     */
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDFIRMA", referencedColumnName = "IDFIRMA", insertable = false, updatable = false)
    private Firma firma;

    /**
     * 
     * @return firmaRemisionPK
     */
    public FirmaRemisionPK getFirmaRemisionPK() {
        return firmaRemisionPK;
    }

    /**
     * 
     * @param firmaRemisionPK
     *            a fijar
     */
    public void setFirmaRemisionPK(FirmaRemisionPK firmaRemisionPK) {
        this.firmaRemisionPK = firmaRemisionPK;
    }

    /**
     * 
     * @return remision
     */
    public Remision getRemision() {
        return remision;
    }

    /**
     * 
     * @param remision
     *            a fijar
     */
    public void setRemision(Remision remision) {
        this.remision = remision;
    }

    /**
     * 
     * @return firma
     */
    public Firma getFirma() {
        return firma;
    }

    /**
     * 
     * @param firma
     *            a fijar
     */
    public void setFirma(Firma firma) {
        this.firma = firma;
    }

}
