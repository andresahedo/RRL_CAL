/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.TipoTramiteUnidadAdministrativaCatalogo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface ConfigurarTramitesUnidadAdministrativaService extends Serializable {

    List<TipoTramiteUnidadAdministrativaCatalogo> buscarTramitesUnidad(String claveUnidad);

    Integer validarServicio(String idServicio, String idUnidadAdministrativa);

    List<TipoTramite> obtenerServicios();

    void actualizarTramitesUnidad(List<TipoTramite> modalidades, String idUnidadAdministrativa);

}
