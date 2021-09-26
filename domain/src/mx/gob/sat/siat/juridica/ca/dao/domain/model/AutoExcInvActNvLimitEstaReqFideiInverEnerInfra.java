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
@DiscriminatorValue(value = DiscriminadorConstants.T2_AUTO_EXC_INV_ACT_NV_LIMIT_ESTA_REQ_FIDEI_INVER_ENER_INFRA)

public class AutoExcInvActNvLimitEstaReqFideiInverEnerInfra extends SolicitudConsultaAutorizacion  {
    /**
     *
     */
    private static final long serialVersionUID = 2606788723522961809L;

    /**
     * Atributo privado "tipoTramite" tipo TipoTramite
     */
    @Transient
    private TipoTramite tipoTramite = new TipoTramite(Integer.valueOf(DiscriminadorConstants.T2_AUTO_EXC_INV_ACT_NV_LIMIT_ESTA_REQ_FIDEI_INVER_ENER_INFRA));

    /**
     * Atributo privado "discriminatorValue" tipo String
     */
    @Transient
    private String discriminatorValue = DiscriminadorConstants.T2_AUTO_EXC_INV_ACT_NV_LIMIT_ESTA_REQ_FIDEI_INVER_ENER_INFRA;

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
