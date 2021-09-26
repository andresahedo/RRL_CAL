/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.api;

import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.UsuarioRolUnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.util.exception.TreasPendientesException;

import java.util.List;

public interface AdministrarUsuarioUAFacade extends BaseFacade {

    List<InformacionUsuarioDTO> obtenerTodosRolesFuncionario(String idUsuario);

    List<TipoTramiteDTO> obtenerModalidadesAsignar();

    List<String> guardarModalidadesAsignadas(List<TipoTramiteDTO> listaSelected, UnidadAdministrativaDTO unidad,
            InformacionUsuarioDTO idUsuario) throws TreasPendientesException;

    List<InfRoleUnidadAdminDTO> obtenerPermisosPorFuncionario(String rfc, String unidadAdmin, String rol);

    List<ServicioDTO> obtenerServiciosAsignar(String idUnidad);

    List<UsuarioRolUnidadAdministrativaDTO> obtenerPermisosDeUsuarioPorUnidad(String idUniAdmin, String idUsuario);

   
}
