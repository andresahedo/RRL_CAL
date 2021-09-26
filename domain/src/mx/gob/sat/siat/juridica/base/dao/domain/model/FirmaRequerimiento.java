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
@Table(name = "RVCA_REQUERIMIENTO_FIRMA")
public class FirmaRequerimiento extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 4772966849796805593L;

    /**
     * Atributo privado "firmaRequerimientoPK" tipo
     * FirmaRequerimientoPK
     */
    @EmbeddedId
    private FirmaRequerimientoPK firmaRequerimientoPK;

    /**
     * Atributo privado "requerimiento" tipo Requerimiento
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDREQUERIMIENTO", referencedColumnName = "IDREQUERIMIENTO", insertable = false,
            updatable = false)
    private Requerimiento requerimiento;

    /**
     * Atributo privado "firma" tipo Firma
     */
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDFIRMA", referencedColumnName = "IDFIRMA", insertable = false, updatable = false)
    private Firma firma;

    /**
     * 
     * @return firmaRequerimientoPK
     */
    public FirmaRequerimientoPK getFirmaRequerimientoPK() {
        return firmaRequerimientoPK;
    }

    /**
     * 
     * @param firmaRequerimientoPK
     *            a fijar
     */
    public void setFirmaRequerimientoPK(FirmaRequerimientoPK firmaRequerimientoPK) {
        this.firmaRequerimientoPK = firmaRequerimientoPK;
    }

    /**
     * 
     * @return requerimiento
     */
    public Requerimiento getRequerimiento() {
        return requerimiento;
    }

    /**
     * 
     * @param requerimiento
     *            a fijar
     */
    public void setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
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
