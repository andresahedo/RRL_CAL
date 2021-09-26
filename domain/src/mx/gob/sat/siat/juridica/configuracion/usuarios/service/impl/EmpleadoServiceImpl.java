package mx.gob.sat.siat.juridica.configuracion.usuarios.service.impl;

import mx.gob.sat.siat.juridica.base.dao.InformacionUsuarioDao;
import mx.gob.sat.siat.juridica.base.dao.PersonasInternasDAO;
import mx.gob.sat.siat.juridica.base.dao.UserStateDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.InformacionUsuario;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UserState;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioRolUnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.UsuarioRolUnidadAdministrativaDao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoServiceImpl extends BaseSerializableBusinessServices implements EmpleadoService {

    /**
     * 
     */
    private static final long serialVersionUID = -2500705920821351502L;

    @Autowired
    private PersonasInternasDAO personasInternasDAO;

    @Autowired
    private UsuarioRolUnidadAdministrativaDao usuarioRol;

    @Autowired
    private UserStateDao usuarioDAO;

    @Autowired
    private InformacionUsuarioDao infoUsuarioDao;

    @Override
    public PersonaInterna buscarEmpleado(Long numEmpleado) {

        return personasInternasDAO.buscarEmpleado(numEmpleado);
    }

    @Override
    public List<UsuarioRolUnidadAdministrativa> buscarRolesUsuario(String idUsuario) {
        List<UsuarioRolUnidadAdministrativa> lista = usuarioRol.obtenerRolesUsuario(idUsuario);
        if (lista != null && !lista.isEmpty()) {
            for (UsuarioRolUnidadAdministrativa rol : lista) {
                rol.getUnidadAdministrativa().getNombre();
                rol.getUnidadAdministrativa().getDescripcion();
                rol.getUsuario().getUserName();
                rol.getRol().getName();

            }
            return lista;
        }
        else {
            return null;
        }
    }

    @Override
    public UserState verificarUsuarioActivo(String idUsuario) {

        return usuarioDAO.obtenerUsuario(idUsuario);
    }

    @Override
    public void asignarPermiso(UsuarioRolUnidadAdministrativa permisoUsuario) {
        UsuarioRolUnidadAdministrativa rolActualizar =
                usuarioRol.validarExistenciaRol(permisoUsuario.getRol().getName(), permisoUsuario
                        .getUnidadAdministrativa().getClave(), permisoUsuario.getUsuario().getUserName());
        if (rolActualizar != null && rolActualizar.getIdUsuarioRolUA() != null) {
            rolActualizar.setBlnActivo(true);
            usuarioRol.asignarModificarPermiso(rolActualizar);
        }
        else {
            usuarioRol.asignarModificarPermiso(permisoUsuario);
        }
        List<UsuarioRolUnidadAdministrativa> roles =
                usuarioRol.obtenerPermisosDeUsuarioPorUnidad(permisoUsuario.getUnidadAdministrativa().getClave(),
                        permisoUsuario.getUsuario().getUserName());
        if (roles.size() > 1) {
            for (UsuarioRolUnidadAdministrativa rolUnidad : roles) {
                if (!rolUnidad.getUniAdminPrincipal()) {
                    rolUnidad.setUniAdminPrincipal(true);
                    usuarioRol.asignarModificarPermiso(rolUnidad);
                }
            }
        }
    }

    @Override
    public void revocarPermiso(List<UsuarioRolUnidadAdministrativa> permisosRevocado) {
        for (UsuarioRolUnidadAdministrativa per : permisosRevocado) {
            UsuarioRolUnidadAdministrativa permisoRev =
                    usuarioRol.validarExistenciaRol(per.getRol().getName(), per.getUnidadAdministrativa().getClave(),
                            per.getUsuario().getUserName());
            if (permisoRev != null) {
                permisoRev.setBlnActivo(false);
            }
            usuarioRol.asignarModificarPermiso(permisoRev);
        }
        for (UsuarioRolUnidadAdministrativa permiso : permisosRevocado) {
            List<UsuarioRolUnidadAdministrativa> roles =
                    usuarioRol.obtenerPermisosDeUsuarioPorUnidad(permiso.getUnidadAdministrativa().getClave(), permiso
                            .getUsuario().getUserName());

            if (roles.size() > 1) {
                for (UsuarioRolUnidadAdministrativa rolUnidad : roles) {
                    if (!rolUnidad.getUniAdminPrincipal()) {
                        rolUnidad.setUniAdminPrincipal(true);
                    }
                }
            }
            else {
                for (UsuarioRolUnidadAdministrativa rolUnidad : roles) {
                    rolUnidad.setUniAdminPrincipal(false);
                }

            }
            usuarioRol.asignarModificarPermiso(permiso);
        }
    }

    @Override
    public List<UsuarioRolUnidadAdministrativa> obtenerTodosRolesFuncionario(String idUsuario) {
        List<UsuarioRolUnidadAdministrativa> lista = usuarioRol.obtenerTodosRolesFuncionario(idUsuario);
        if (lista != null && !lista.isEmpty()) {
            for (UsuarioRolUnidadAdministrativa rol : lista) {
                rol.getUnidadAdministrativa().getNombre();
                rol.getUnidadAdministrativa().getDescripcion();
                rol.getUsuario().getUserName();
                rol.getRol().getName();

            }
            return lista;
        }
        else {
            return null;
        }
    }

    @Override
    public List<InformacionUsuario> consultarRolesNovell(String idUsuario) {
        return infoUsuarioDao.consultarRolesFuncionario(idUsuario);
    }

    @Override
    public void activarAdministradorGlobal(String idUsuario, boolean checkGlobal) {
        UserState usuario = usuarioDAO.obtenerUsuario(idUsuario);
        usuario.setBlnActivoGlobal(checkGlobal);
        usuarioDAO.modificarUsuario(usuario);

    }

}
