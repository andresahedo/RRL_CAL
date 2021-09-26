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
@DiscriminatorValue(value = DiscriminadorConstants.T2_SOL_AUT_NO_APL_DIS_TI_VI_CA_I_LEY_ISR_ING_PAS_GEN_ENT_FIG)

public class SolAutNoAplDisTiVICaILeyISRIngPasGenEntFig extends SolicitudConsultaAutorizacion {
    /**
     *
     */
    private static final long serialVersionUID = -7872389888299188821L;

    /**
     * Atributo privado "tipoTramite" tipo TipoTramite
     */
    @Transient
    private TipoTramite tipoTramite = new TipoTramite(Integer.valueOf(DiscriminadorConstants.T2_SOL_AUT_NO_APL_DIS_TI_VI_CA_I_LEY_ISR_ING_PAS_GEN_ENT_FIG));

    /**
     * Atributo privado "discriminatorValue" tipo String
     */
    @Transient
    private String discriminatorValue = DiscriminadorConstants.T2_SOL_AUT_NO_APL_DIS_TI_VI_CA_I_LEY_ISR_ING_PAS_GEN_ENT_FIG;

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
