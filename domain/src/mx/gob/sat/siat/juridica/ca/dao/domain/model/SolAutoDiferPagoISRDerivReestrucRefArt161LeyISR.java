package mx.gob.sat.siat.juridica.ca.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * 
 * @author HPE
 * 
 */
@Entity
@DiscriminatorValue(value = DiscriminadorConstants.T2_SOL_AUTO_DIFER_PAGO_ISR_DERIV_REESTRUC_REF_ART161_LEY_ISR)
public class SolAutoDiferPagoISRDerivReestrucRefArt161LeyISR extends SolicitudConsultaAutorizacion{
    /**
     *
     */
    private static final long serialVersionUID = 4021128397649542188L;

    /**
     * Atributo privado "tipoTramite" tipo TipoTramite
     */
    @Transient
    private TipoTramite tipoTramite = new TipoTramite(Integer.valueOf(DiscriminadorConstants.T2_SOL_AUTO_DIFER_PAGO_ISR_DERIV_REESTRUC_REF_ART161_LEY_ISR));

    /**
     * Atributo privado "discriminatorValue" tipo String
     */
    @Transient
    private String discriminatorValue = DiscriminadorConstants.T2_SOL_AUTO_DIFER_PAGO_ISR_DERIV_REESTRUC_REF_ART161_LEY_ISR;

    /**
     * @return tipoTramite
     */
    @Override
    public TipoTramite getTipoTramite() {
        return tipoTramite;
    }

    /**
     * @return discriminatorValue
     */
    @Override
    public String getDiscriminatorValue() {
        return discriminatorValue;
    }

}
