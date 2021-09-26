/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * 
 * @author softtek
 */
@Entity
@DiscriminatorValue(value = DiscriminadorConstants.T2_AUTORIZACION_NO_DEDUCCION_COSTO_ADQUISICION)
public class SolicitudAutorizacionNoDisminucionCostoAdquisicion extends SolicitudConsultaAutorizacion {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 5052309525979730604L;

    /**
     * Atributo privado "tipoTramite" tipo TipoTramite
     */
    @Transient
    private TipoTramite tipoTramite = new TipoTramite(
            Integer.valueOf(DiscriminadorConstants.T2_AUTORIZACION_NO_DEDUCCION_COSTO_ADQUISICION));

    /**
     * Atributo privado "discriminatorValue" tipo String
     */
    @Transient
    private String discriminatorValue = DiscriminadorConstants.T2_AUTORIZACION_NO_DEDUCCION_COSTO_ADQUISICION;

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
