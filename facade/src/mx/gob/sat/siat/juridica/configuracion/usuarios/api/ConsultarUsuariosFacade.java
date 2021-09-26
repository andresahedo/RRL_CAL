/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.api;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.InfRoleUnidadAdminDTO;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.FiltroUsuariosDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PermisosAUDTO;

import java.util.List;

/**
 * Clase DTO para el armado de los bienes resarcimeinto
 * 
 * @author Softtek - EQG
 * @since 08/10/2014
 */
public interface ConsultarUsuariosFacade extends BaseFacade {

    /**
     * Metodo para obtener la Lista de Unidades Administrativas
     * 
     * @return List<CatalogoDTO>
     */
    List<CatalogoDTO> obtenerUnidadesAdministrativas();

    List<TipoTramiteDTO> obtenerTiposDeTramites();

    List<PermisosAUDTO> obtenerPermisos();

    List<InfRoleUnidadAdminDTO> consultarPermisosUsuarios(FiltroUsuariosDTO filtroUsuarios);

    List<CatalogoDTO> obtenerUnidadAdministrativasUAByRfc(String rfc);

}
