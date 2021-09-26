/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.api;

import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;

import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface ConfigurarTramitesUnidadAdministrativaFacade extends BaseFacade {

    List<UnidadAdministrativaDTO> comboUnidades();

    List<TipoTramiteDTO> buscarTramites(String idUniadmin);

    List<TipoTramiteDTO> obtenerServicios();

    void actualizarTramitesUnidad(List<TipoTramiteDTO> modalidades, String idUnidadAdministrativa);
}
