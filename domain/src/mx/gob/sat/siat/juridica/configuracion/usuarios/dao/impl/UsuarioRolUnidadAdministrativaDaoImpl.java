package mx.gob.sat.siat.juridica.configuracion.usuarios.dao.impl;

import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioRolUnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.impl.BaseJPADao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.UsuarioRolUnidadAdministrativaDao;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository("usuarioRolUnidadAdministrativaDao")
public class UsuarioRolUnidadAdministrativaDaoImpl extends BaseJPADao implements UsuarioRolUnidadAdministrativaDao {

    private static final String ID_USUARIO = "idUsuario";

    /**
     * 
     */
    private static final long serialVersionUID = -5817083133137312139L;

    @SuppressWarnings("unchecked")
    @Override
    public List<UsuarioRolUnidadAdministrativa> obtenerRolesUsuario(String idUsuario) {
        StringBuffer consulta = new StringBuffer();
        consulta.append("FROM UsuarioRolUnidadAdministrativa WHERE idUsuario = :idUsuario AND blnActivo = 1 AND rol.name = :idRol  ");
        Query query = getEntityManager().createQuery(consulta.toString());
        query.setParameter(ID_USUARIO, idUsuario);
        query.setParameter("idRol", "AdministradorUA");
        return query.getResultList();
    }

    @Override
    public void asignarModificarPermiso(UsuarioRolUnidadAdministrativa permisoUsuario) {
        if (permisoUsuario != null) {
            if (permisoUsuario.getIdUsuarioRolUA() != null) {
                getEntityManager().merge(permisoUsuario);
            }
            else {
                getEntityManager().persist(permisoUsuario);
            }
        }
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<UsuarioRolUnidadAdministrativa> consultarPermisosUsuarios(String idUnidadAdmin, String modalidad,
            String numeroEmpleado, String permiso, String rfcEmpleado, String tipoTramite) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT usu FROM UsuarioRolUnidadAdministrativa usu WHERE usu.unidadAdministrativa.clave =:idUnidadAdmin ");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("idUnidadAdmin", idUnidadAdmin);
        return (List<UsuarioRolUnidadAdministrativa>) query.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<UsuarioRolUnidadAdministrativa> obtenerTodosRolesFuncionario(String idUsuario) {
        StringBuffer consulta = new StringBuffer();
        consulta.append("FROM UsuarioRolUnidadAdministrativa WHERE idUsuario = :idUsuario");
        Query query = getEntityManager().createQuery(consulta.toString());
        query.setParameter(ID_USUARIO, idUsuario);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public UsuarioRolUnidadAdministrativa validarExistenciaRol(String idRol, String idUniadmin, String idUsuario) {
        StringBuffer consulta = new StringBuffer();
        consulta.append("FROM UsuarioRolUnidadAdministrativa WHERE idUsuario = :idUsuario ");
        consulta.append("AND rol.name = :idRol AND unidadAdministrativa.clave = :idUniadmin");
        Query query = getEntityManager().createQuery(consulta.toString());
        query.setParameter(ID_USUARIO, idUsuario);
        query.setParameter("idRol", idRol);
        query.setParameter("idUniadmin", idUniadmin);
        List<UsuarioRolUnidadAdministrativa> resultados = query.getResultList();
        if (resultados != null && !resultados.isEmpty()) {
            return resultados.get(0);
        }
        else {
            return null;
        }

    }

    public UsuarioRolUnidadAdministrativa obtenerIdAdministradorUA(UnidadAdministrativa uniAdmin) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT usu FROM UsuarioRolUnidadAdministrativa usu WHERE usu.unidadAdministrativa = :uniAdmin ");
        sql.append("AND usu.rol = :role ");
        sql.append("AND usu.blnActivo = 1 ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("uniAdmin", uniAdmin);
        query.setParameter("role", new Role(RolesConstantes.ROL_INTERNO_ADMINISTRADOR_UNIDAD_ADMINISTRATIVA));
        return (UsuarioRolUnidadAdministrativa) query.getResultList().get(0);
    }

    @SuppressWarnings("unchecked")
    public List<UsuarioRolUnidadAdministrativa> obtenerPermisosDeUsuarioPorUnidad(String idUniAdmin, String idUsuario) {
        StringBuilder sb = new StringBuilder();
        sb.append("FROM UsuarioRolUnidadAdministrativa ");
        sb.append("WHERE unidadAdministrativa.clave = :idUniAdmin ");
        sb.append("AND usuario.userName = :idUsuario AND blnActivo = 1 ");

        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("idUniAdmin", idUniAdmin);
        query.setParameter(ID_USUARIO, idUsuario);
        List<UsuarioRolUnidadAdministrativa> rolesUnidades = query.getResultList();
        if (rolesUnidades != null && !rolesUnidades.isEmpty()) {
            return rolesUnidades;
        }
        else {
            return rolesUnidades;
        }

    }

}
