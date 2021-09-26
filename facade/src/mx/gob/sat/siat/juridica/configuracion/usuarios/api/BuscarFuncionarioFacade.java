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
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.UsuarioRolUnidadAdministrativaDTO;

import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface BuscarFuncionarioFacade extends BaseFacade {

    PersonaInternaDTO buscarEmpleado(Long numEmpleado);

    boolean verificarUsuarioActivo(String idUsuario);

    List<UsuarioRolUnidadAdministrativaDTO> obtenerTodosRolesUsuario(String idUsuario);

    List<InformacionUsuarioDTO> consultarRolesNovell(String idUsuario);
}
