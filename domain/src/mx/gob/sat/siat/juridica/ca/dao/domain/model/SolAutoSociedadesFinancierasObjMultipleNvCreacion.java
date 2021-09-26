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
@DiscriminatorValue(value = DiscriminadorConstants.T2_SOL_AUTO_SOCIEDADES_FINANCIERAS_OBJ_MULTIPLE_NV_CREACION)

public class SolAutoSociedadesFinancierasObjMultipleNvCreacion extends SolicitudConsultaAutorizacion {
    /**
     *
     */
    private static final long serialVersionUID = -9210865907902286061L;

    /**
     * Atributo privado "tipoTramite" tipo TipoTramite
     */
    @Transient
    private TipoTramite tipoTramite = new TipoTramite(Integer.valueOf(DiscriminadorConstants.T2_SOL_AUTO_SOCIEDADES_FINANCIERAS_OBJ_MULTIPLE_NV_CREACION));

    /**
     * Atributo privado "discriminatorValue" tipo String
     */
    @Transient
    private String discriminatorValue = DiscriminadorConstants.T2_SOL_AUTO_SOCIEDADES_FINANCIERAS_OBJ_MULTIPLE_NV_CREACION;

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
