/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.api;

import mx.gob.sat.siat.juridica.base.dto.InformacionUsuarioDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.UsuarioRolUnidadAdministrativaDTO;

import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface AdministrarUsuariosGlobalFacade extends BaseFacade {

    List<UsuarioRolUnidadAdministrativaDTO> buscarRolesUsuario(String idUsuario);

    PersonaInternaDTO buscarEmpleado(Long numEmpleado);

    List<UnidadAdministrativaDTO> obtenerCatalogoUnidades();

    boolean verificarUsuarioActivo(String idUsuario);

    void asignarPermiso(UsuarioRolUnidadAdministrativaDTO permisoUsuario);

    void revocarPermiso(List<UsuarioRolUnidadAdministrativaDTO> permisos, String rfc);

    List<InformacionUsuarioDTO> buscarPermisosUsuario(String idUsuario);

    void activarAdministradorGlobal(String idUsuario, boolean checkGlobal);

    boolean verificarAdministradorGlobalActivo(String idUsuario);
}
