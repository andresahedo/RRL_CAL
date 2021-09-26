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
@DiscriminatorValue(value = DiscriminadorConstants.T2_AUTORIZACION_APLICAR_REGIMEN_OPCIONAL_GRUPOS_SOCIEDADES)

public class AutorizacionAplicarRegimenOpcionalGruposSociedades extends SolicitudConsultaAutorizacion {
    /**
     *
     */
    private static final long serialVersionUID = -3376581975385862675L;

    /**
     * Atributo privado "tipoTramite" tipo TipoTramite
     */
    @Transient
    private TipoTramite tipoTramite = new TipoTramite(Integer.valueOf(DiscriminadorConstants.T2_AUTORIZACION_APLICAR_REGIMEN_OPCIONAL_GRUPOS_SOCIEDADES));

    /**
     * Atributo privado "discriminatorValue" tipo String
     */
    @Transient
    private String discriminatorValue = DiscriminadorConstants.T2_AUTORIZACION_APLICAR_REGIMEN_OPCIONAL_GRUPOS_SOCIEDADES;

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
