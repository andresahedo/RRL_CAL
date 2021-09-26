/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.service.impl;

import mx.gob.sat.siat.juridica.base.dao.InformacionUsuarioDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.InformacionUsuario;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UserCredentials;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UserState;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioRolUnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.UsuarioRolUnidadAdministrativaDao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.AdministrarEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */

@Service
public class AdministrarEmpleadoServiceImpl extends BaseSerializableBusinessServices implements
        AdministrarEmpleadoService {

    /**
     * 
     */
    private static final long serialVersionUID = -5522212438977826098L;
    @Autowired
    private UsuarioRolUnidadAdministrativaDao usuarioRolUniAdminDao;
    @Autowired
    private InformacionUsuarioDao informacionUsuarioDao;

    @Override
    public void actualizarPermisos(List<InformacionUsuario> permisos, String idUnidad, String idUsuario) {
        List<UsuarioRolUnidadAdministrativa> permisosGuardar = new ArrayList<UsuarioRolUnidadAdministrativa>();
        for (InformacionUsuario permiso : permisos) {
            UsuarioRolUnidadAdministrativa rolUnidad =
                    usuarioRolUniAdminDao.validarExistenciaRol(permiso.getInformacionUsuarioPK().getIdRol(), idUnidad,
                            idUsuario);
            if (rolUnidad != null) {
                rolUnidad.setBlnActivo(permiso.getBlnActivo());
            }
            else {
                rolUnidad = new UsuarioRolUnidadAdministrativa();
                rolUnidad.setBlnActivo(permiso.getBlnActivo());
                Role rol = new Role();
                rol.setName(permiso.getInformacionUsuarioPK().getIdRol());
                rolUnidad.setRol(rol);
                rolUnidad.setUnidadAdministrativa(new UnidadAdministrativa(idUnidad));
                UserCredentials usuario = new UserCredentials();
                usuario.setUserName(idUsuario);
                rolUnidad.setUsuario(new UserState(usuario));
            }
            permisosGuardar.add(rolUnidad);
        }
        for (UsuarioRolUnidadAdministrativa rolUnidad : permisosGuardar) {
            InformacionUsuario inf = this.buscarRolReplica(permisos);
            if (inf != null) {
                if (inf.getInformacionUsuarioPK().getIdUsuario().equals(rolUnidad.getUsuario().getUserName())
                        && inf.getInformacionUsuarioPK().getIdRol().equals(rolUnidad.getRol().getName())) {
                    if (rolUnidad.getUniAdminPrincipal() == null || !rolUnidad.getUniAdminPrincipal()) {
                        usuarioRolUniAdminDao.asignarModificarPermiso(rolUnidad);
                    }
                }
                else {

                    usuarioRolUniAdminDao.asignarModificarPermiso(rolUnidad);

                }
            }
        }
        actualizarRolReplica(permisos);
        /**
         * Se actualiza la unidad administrativa base.
         * 
         */

        List<UsuarioRolUnidadAdministrativa> roles =
                usuarioRolUniAdminDao.obtenerPermisosDeUsuarioPorUnidad(idUnidad, idUsuario);
        String idUnidadBase = this.validarUnidadBase(idUsuario);
        if (roles.size() > 1) {
            for (UsuarioRolUnidadAdministrativa rolUnidad : roles) {
                if (rolUnidad.getUniAdminPrincipal() != null && rolUnidad.getUniAdminPrincipal()) {
                    if (rolUnidad.getUnidadAdministrativa().getClave().equals(idUnidadBase)) {
                        usuarioRolUniAdminDao.asignarModificarPermiso(rolUnidad);
                    }
                }
                else {
                    rolUnidad.setUniAdminPrincipal(true);
                    usuarioRolUniAdminDao.asignarModificarPermiso(rolUnidad);
                }
            }
        }
        else {
            for (UsuarioRolUnidadAdministrativa rolUnidad : roles) {
                rolUnidad.setUniAdminPrincipal(false);
                usuarioRolUniAdminDao.asignarModificarPermiso(rolUnidad);
            }

        }

    }

    @Override
    public List<UsuarioRolUnidadAdministrativa> validarUnidadBase(String idUniAdmin, String idUsuario) {
        return usuarioRolUniAdminDao.obtenerPermisosDeUsuarioPorUnidad(idUniAdmin, idUsuario);
    }

    @Override
    public void asignarUnidadBase() {

    }

    @Override
    public boolean buscarTareasPendientes() {

        return false;
    }

    @Override
    public void actualizarRolReplica(List<InformacionUsuario> permisos) {
        for (InformacionUsuario permiso : permisos) {
            InformacionUsuario info =
                    informacionUsuarioDao.buscarUsuarioPorId(permiso.getInformacionUsuarioPK().getIdUsuario(), permiso
                            .getInformacionUsuarioPK().getIdRol());
            if (info != null) {
                info.setRolReplica(permiso.getRolReplica());
                informacionUsuarioDao.modificarRolUsuario(info);
            }
        }

    }

    @Override
    public InformacionUsuario buscarRolReplica(List<InformacionUsuario> permisos) {
        InformacionUsuario usuarioRolReplica = null;
        for (InformacionUsuario permiso : permisos) {
            usuarioRolReplica =
                    informacionUsuarioDao.buscarUsuarioPorId(permiso.getInformacionUsuarioPK().getIdUsuario(), permiso
                            .getInformacionUsuarioPK().getIdRol());
            if (usuarioRolReplica != null) {
                if (usuarioRolReplica.getRolReplica() != null) {
                    if (usuarioRolReplica.getRolReplica()) {
                        return usuarioRolReplica;
                    }
                }
            }
        }
        return usuarioRolReplica;
    }

    @Override
    public String validarUnidadBase(String idUsuario) {
        String idUnidadBase = "";
        List<UsuarioRolUnidadAdministrativa> listaPermisosTotales =
                usuarioRolUniAdminDao.obtenerTodosRolesFuncionario(idUsuario);

        if (listaPermisosTotales != null && !listaPermisosTotales.isEmpty()) {

            for (UsuarioRolUnidadAdministrativa permiso : listaPermisosTotales) {
                if (permiso.getUniAdminPrincipal() != null && permiso.getUniAdminPrincipal()) {
                    idUnidadBase = permiso.getUnidadAdministrativa().getClave();
                }
            }
        }
        return idUnidadBase;
    }
}
