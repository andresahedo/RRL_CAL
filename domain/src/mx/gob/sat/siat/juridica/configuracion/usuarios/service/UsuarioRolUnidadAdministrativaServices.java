package mx.gob.sat.siat.juridica.configuracion.usuarios.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioRolUnidadAdministrativa;

import java.io.Serializable;
import java.util.List;

public interface UsuarioRolUnidadAdministrativaServices extends Serializable {

    List<UsuarioRolUnidadAdministrativa> consultarPermisosUsuarios(String idUnidadAdmin, String modalidad,
            String numeroEmpleado, String permiso, String rfcEmpleado, String tipoTramite);

    List<UsuarioRolUnidadAdministrativa> obtenerTodosRolesFuncionario(String idUsuario);

    List<TipoTramite> obtenerModalidadesAsignar();

    List<TipoTramite> obtenerTiposDeModalidadByTipoTramite(String idServicio);

    List<TipoTramite> buscarTramitesAsignadosUnidad(String unidadAdmin);

}
