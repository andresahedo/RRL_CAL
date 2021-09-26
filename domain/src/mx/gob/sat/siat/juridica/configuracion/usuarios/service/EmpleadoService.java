/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */

package mx.gob.sat.siat.juridica.configuracion.usuarios.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.InformacionUsuario;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UserState;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioRolUnidadAdministrativa;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */

public interface EmpleadoService extends Serializable {

    PersonaInterna buscarEmpleado(Long numEmpleado);

    List<UsuarioRolUnidadAdministrativa> buscarRolesUsuario(String idUsuario);

    UserState verificarUsuarioActivo(String idUsuario);

    void asignarPermiso(UsuarioRolUnidadAdministrativa permisoUsuario);

    void revocarPermiso(List<UsuarioRolUnidadAdministrativa> permisoRevocado);

    List<UsuarioRolUnidadAdministrativa> obtenerTodosRolesFuncionario(String idUsuario);

    List<InformacionUsuario> consultarRolesNovell(String idUsuario);

    void activarAdministradorGlobal(String idUsuario, boolean checkGlobal);
}
