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
 * 
 */
@Entity
@DiscriminatorValue(value = DiscriminadorConstants.T2_CONSULTA_LEY_ADUANERA)
public class SolicitudConsultaMateriaLeyAduanera extends SolicitudConsultaAutorizacion {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 3799570995423148882L;

    /**
     * 
     */
    @Transient
    private TipoTramite tipoTramite = new TipoTramite(Integer.valueOf(DiscriminadorConstants.T2_CONSULTA_LEY_ADUANERA));
    @Transient
    private String discriminatorValue = DiscriminadorConstants.T2_CONSULTA_LEY_ADUANERA;

    @Override
    public TipoTramite getTipoTramite() {
        return tipoTramite;
    }

    @Override
    public String getDiscriminatorValue() {
        return discriminatorValue;
    }

}
