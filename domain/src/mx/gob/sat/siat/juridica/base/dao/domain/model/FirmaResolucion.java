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
@Table(name = "RVCA_RESOLUCION_FIRMA")
public class FirmaResolucion extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "firmaResolucionPK" tipo FirmaResolucionPK
     */
    @EmbeddedId
    private FirmaResolucionPK firmaResolucionPK;

    /**
     * Atributo privado "resolucion" tipo Resolucion
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDRESOLUCION", referencedColumnName = "IDRESOLUCION", insertable = false, updatable = false)
    private Resolucion resolucion;

    /**
     * Atributo privado "firma" tipo Firma
     */
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDFIRMA", referencedColumnName = "IDFIRMA", insertable = false, updatable = false)
    private Firma firma;

    /**
     * 
     * @return firmaResolucionPK
     */
    public FirmaResolucionPK getFirmaResolucionPK() {
        return firmaResolucionPK;
    }

    /**
     * 
     * @param firmaResolucionPK
     *            a fijar
     */
    public void setFirmaResolucionPK(FirmaResolucionPK firmaResolucionPK) {
        this.firmaResolucionPK = firmaResolucionPK;
    }

    /**
     * 
     * @return resolucion
     */
    public Resolucion getResolucion() {
        return resolucion;
    }

    /**
     * 
     * @param resolucion
     *            a fijar
     */
    public void setResolucion(Resolucion resolucion) {
        this.resolucion = resolucion;
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
