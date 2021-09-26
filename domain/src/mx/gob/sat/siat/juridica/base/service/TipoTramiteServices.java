/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface TipoTramiteServices extends Serializable {

    /**
     * Metodo para obtener un tipo de tramite por id
     * 
     * @param idTipoTramite
     * @return
     */
    String obtenerTipoTramite(Integer idTipoTramiteInteger);

    /**
     * M&eacute;todo para obtener el timpo tramite por el id modulo =
     * 1
     * 
     * @return
     */
    List<TipoTramite> obtenerTipoTramiteModulo(String idTipoTramite);

    /**
     * M&eacute;todo para obtener el timpo tramite por el id modulo =
     * 1
     * 
     * @return
     */
    TipoTramite obtenerTipoTramiteId(Integer idTipoTramite);

    /**
     * @return
     */
    List<TipoTramite> obtenerTipoTramiteModulo1();

}
