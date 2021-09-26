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
@Table(name = "RVCA_OBSERVACION_FIRMA")
public class FirmaObservacion extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 4772966849796805593L;

    /**
     * Atributo privado "firmaObservacionPK" tipo FirmaObservacionPK
     */
    @EmbeddedId
    private FirmaObservacionPK firmaObservacionPK;

    /**
     * Atributo privado "observacion" tipo Observacion
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDOBSERVACION", referencedColumnName = "IDOBSERVACION", insertable = false, updatable = false)
    private Observacion observacion;

    /**
     * Atributo privado "firma" tipo Firma
     */
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDFIRMA", referencedColumnName = "IDFIRMA", insertable = false, updatable = false)
    private Firma firma;

    /**
     * 
     * @return firmaObservacionPK
     */
    public FirmaObservacionPK getFirmaObservacionPK() {
        return firmaObservacionPK;
    }

    /**
     * 
     * @param firmaObservacionPK
     *            a fijar
     */
    public void setFirmaObservacionPK(FirmaObservacionPK firmaObservacionPK) {
        this.firmaObservacionPK = firmaObservacionPK;
    }

    /**
     * 
     * @return observacion
     */
    public Observacion getObservacion() {
        return observacion;
    }

    /**
     * 
     * @param observacion
     *            a fijar
     */
    public void setObservacion(Observacion observacion) {
        this.observacion = observacion;
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
