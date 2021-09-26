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
@DiscriminatorValue(value = DiscriminadorConstants.T2_SOL_DEJAR_SIN_EFECTOS_AUTOR_OPERAR_ORGANO_CERTIFICADOR)

public class SolDejarSinEfectosAutorOperarOrganoCertificador extends SolicitudConsultaAutorizacion  {
    /**
     *
     */
    private static final long serialVersionUID = -4028442902638514953L;

    /**
     * Atributo privado "tipoTramite" tipo TipoTramite
     */
    @Transient
    private TipoTramite tipoTramite = new TipoTramite(Integer.valueOf(DiscriminadorConstants.T2_SOL_DEJAR_SIN_EFECTOS_AUTOR_OPERAR_ORGANO_CERTIFICADOR));

    /**
     * Atributo privado "discriminatorValue" tipo String
     */
    @Transient
    private String discriminatorValue = DiscriminadorConstants.T2_SOL_DEJAR_SIN_EFECTOS_AUTOR_OPERAR_ORGANO_CERTIFICADOR;

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
