package mx.gob.sat.siat.juridica.configuracion.usuarios.service.impl;

import mx.gob.sat.siat.juridica.base.dao.TipoTramiteDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioRolUnidadAdministrativa;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.UsuarioRolUnidadAdministrativaDao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.UsuarioRolUnidadAdministrativaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioRolUnidadAdministrativaServicesImpl implements UsuarioRolUnidadAdministrativaServices {

    /**
     * 
     */
    private static final long serialVersionUID = -7450673648819776823L;

    @Autowired
    private UsuarioRolUnidadAdministrativaDao usuarioRolUADao;

    @Autowired
    private TipoTramiteDAO tipoTramiteDao;

    @Override
    public List<UsuarioRolUnidadAdministrativa> consultarPermisosUsuarios(String idUnidadAdmin, String modalidad,
            String numeroEmpleado, String permiso, String rfcEmpleado, String tipoTramite) {
        List<UsuarioRolUnidadAdministrativa> listaUsuarios =
                usuarioRolUADao.consultarPermisosUsuarios(idUnidadAdmin, modalidad, numeroEmpleado, permiso,
                        rfcEmpleado, tipoTramite);
        for (UsuarioRolUnidadAdministrativa lista : listaUsuarios) {
            lista.getUniAdminPrincipal();
            lista.getUnidadAdministrativa();
            lista.getUsuario();
            lista.getRol();
        }
        return listaUsuarios;
    }

    @Override
    public List<UsuarioRolUnidadAdministrativa> obtenerTodosRolesFuncionario(String idUsuario) {
        return usuarioRolUADao.obtenerTodosRolesFuncionario(idUsuario);
    }

    @Override
    public List<TipoTramite> obtenerModalidadesAsignar() {

        return tipoTramiteDao.todasModalidades();
    }

    @Override
    public List<TipoTramite> buscarTramitesAsignadosUnidad(String unidadAdmin) {
        return tipoTramiteDao.buscarTramitesAsignadosUnidad(unidadAdmin);
    }

    @Override
    public List<TipoTramite> obtenerTiposDeModalidadByTipoTramite(String idServicio) {
        return tipoTramiteDao.obtenerTodosTramites(idServicio);
    }

}
