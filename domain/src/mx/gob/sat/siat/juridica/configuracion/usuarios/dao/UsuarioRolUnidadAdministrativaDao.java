package mx.gob.sat.siat.juridica.configuracion.usuarios.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioRolUnidadAdministrativa;

import java.util.List;

public interface UsuarioRolUnidadAdministrativaDao {

    List<UsuarioRolUnidadAdministrativa> obtenerRolesUsuario(String idUsuario);

    void asignarModificarPermiso(UsuarioRolUnidadAdministrativa permisoUsuario);

    List<UsuarioRolUnidadAdministrativa> consultarPermisosUsuarios(String idUnidadAdmin, String modalidad,
            String numeroEmpleado, String permiso, String tipoTramite, String tipoTramite2);

    List<UsuarioRolUnidadAdministrativa> obtenerTodosRolesFuncionario(String idUsuario);

    UsuarioRolUnidadAdministrativa validarExistenciaRol(String idRol, String idUniadmin, String idUsuario);

    UsuarioRolUnidadAdministrativa obtenerIdAdministradorUA(UnidadAdministrativa uniAdmin);

    List<UsuarioRolUnidadAdministrativa> obtenerPermisosDeUsuarioPorUnidad(String idUniAdmin, String idUsuario);

}
