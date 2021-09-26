package mx.gob.sat.siat.juridica.configuracion.usuarios.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.InfRoleUnidadAdmin;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.InformacionUsuario;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioRolUnidadAdministrativa;
import mx.gob.sat.siat.juridica.configuracion.usuarios.util.exception.TreasPendientesException;

import java.io.Serializable;
import java.util.List;

public interface AdministrarUsuarioUAService extends Serializable {

    List<TipoTramite> buscarTramites();

    List<String> actualizarTramitesAsignadosFuncionario(List<TipoTramite> modalidadesSelect,
            UnidadAdministrativa unidad, InformacionUsuario funcionario) throws TreasPendientesException;

    List<InformacionUsuario> obtenerRolesFuncionario(String idUsuario);

    Role obtenerRolePorIdRol(String idRol);

    List<InfRoleUnidadAdmin> obtenerPermisosPorFuncionario(String rfc, String unidadAdmin, String rol);

    List<UsuarioRolUnidadAdministrativa> obtenerPermisosDeUsuarioPorUnidad(String idUniAdmin, String idUsuario);

}
