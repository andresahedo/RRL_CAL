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
@Table(name = "RVCA_NOTIFICACION_FIRMA")
public class FirmaNotificacion extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "firmaNotificacionPK" tipo FirmaNotificacionPK
     */
    @EmbeddedId
    private FirmaNotificacionPK firmaNotificacionPK;

    /**
     * Atributo privado "notificacion" tipo Notificacion
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDNOTIFICACION", referencedColumnName = "IDNOTIFICACION", insertable = false, updatable = false)
    private Notificacion notificacion;

    /**
     * Atributo privado "firma" tipo Firma
     */
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDFIRMA", referencedColumnName = "IDFIRMA", insertable = false, updatable = false)
    private Firma firma;

    /**
     * 
     * @return firmaNotificacionPK
     */
    public FirmaNotificacionPK getFirmaNotificacionPK() {
        return firmaNotificacionPK;
    }

    /**
     * 
     * @param firmaNotificacionPK
     *            a fijar
     */
    public void setFirmaNotificacionPK(FirmaNotificacionPK firmaNotificacionPK) {
        this.firmaNotificacionPK = firmaNotificacionPK;
    }

    /**
     * 
     * @return notificacion
     */
    public Notificacion getNotificacion() {
        return notificacion;
    }

    /**
     * 
     * @param notificacion
     *            a fijar
     */
    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
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
