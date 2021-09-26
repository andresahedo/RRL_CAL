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
@Table(name = "RVCA_SOL_FIRMA")
public class FirmaSolicitud extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "firmaSolicitudPK" tipo FirmaSolicitudPK
     */
    @EmbeddedId
    private FirmaSolicitudPK firmaSolicitudPK;

    /**
     * Atributo privado "solicitud" tipo Solicitud
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDSOLICITUD", referencedColumnName = "IDSOLICITUD", insertable = false, updatable = false)
    private Solicitud solicitud;

    /**
     * Atributo privado "firma" tipo Firma
     */
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDFIRMA", referencedColumnName = "IDFIRMA", insertable = false, updatable = false)
    private Firma firma;

    /**
     * 
     * @return firmaSolicitudPK
     */
    public FirmaSolicitudPK getFirmaSolicitudPK() {
        return firmaSolicitudPK;
    }

    /**
     * 
     * @param firmaSolicitudPK
     *            a fijar
     */
    public void setFirmaSolicitudPK(FirmaSolicitudPK firmaSolicitudPK) {
        this.firmaSolicitudPK = firmaSolicitudPK;
    }

    /**
     * 
     * @return solicitud
     */
    public Solicitud getSolicitud() {
        return solicitud;
    }

    /**
     * 
     * @param solicitud
     */
    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
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
