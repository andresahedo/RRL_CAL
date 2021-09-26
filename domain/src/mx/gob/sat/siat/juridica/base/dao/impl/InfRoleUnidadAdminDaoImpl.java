package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.InfRoleUnidadAdminDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.InfRoleUnidadAdmin;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.InfRoleUnidadAdminPK;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class InfRoleUnidadAdminDaoImpl extends BaseJPADao implements InfRoleUnidadAdminDao {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -2476438848650267998L;

    private static final String UNCHECKED = "unchecked";
    private static final String ROL = "rol";

    @SuppressWarnings(UNCHECKED)
    @Override
    public List<InfRoleUnidadAdmin> obtenerPermisosPorUnidadAdministratuva(String idUnidadAdmin, String modalidad,
            String numeroEmpleado, String permiso, String rfcEmpleado, String idServicio) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT permiso FROM InfRoleUnidadAdmin permiso, Persona per, TipoTramite tp WHERE permiso.unidadAdminPK.claveUnidadAdministrativa =:idUnidadAdmin ");
        sb.append("AND permiso.unidadAdminPK.claveUsuario = per.rfc  ");
        sb.append("AND tp.idTipoTramite = permiso.unidadAdminPK.ideTipoTramite ");
        if (numeroEmpleado != null) {
            sb.append("AND per.numeroEmpleado =:numeroEmpleado ");
        }
        if (permiso != null) {
            sb.append("AND permiso.unidadAdminPK.claveRol =:permiso ");
        }
        if (rfcEmpleado != null) {
            sb.append("AND permiso.unidadAdminPK.claveUsuario =:rfcEmpleado ");
        }
        if (idServicio != null) {
            sb.append("AND tp.servicio =:idServicio ");
        }
        sb.append("AND (permiso.vigencia.blnActivo = 1 OR permiso.vigencia.blnActivo IS NULL) ");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("idUnidadAdmin", idUnidadAdmin);
        if (numeroEmpleado != null) {
            query.setParameter("numeroEmpleado", Long.valueOf(numeroEmpleado));
        }
        if (permiso != null) {
            query.setParameter("permiso", permiso);
        }
        if (rfcEmpleado != null) {
            query.setParameter("rfcEmpleado", rfcEmpleado);
        }
        if (idServicio != null) {
            query.setParameter("idServicio", idServicio);
        }
        return query.getResultList();
        // revisar si no hace falta filtrarlo por booleano activo

    }

    @SuppressWarnings(UNCHECKED)
    @Override
    public List<UnidadAdministrativa> obtenerUnidadesAUA(String rfc) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT distinct unidad.unidadAdministrativa FROM UsuarioRolUnidadAdministrativa unidad ");
        sb.append("WHERE unidad.usuario.userName = :rfc and unidad.rol.name = :rol AND unidad.blnActivo = 1 ");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("rfc", rfc);
        query.setParameter(ROL, "AdministradorUA");

        return query.getResultList();
    }

    @Override
    public void guardarModalidadesAsignadosUsuarios(List<InfRoleUnidadAdmin> modalidades) {
        for (InfRoleUnidadAdmin tramiteAsignado : modalidades) {

            InfRoleUnidadAdmin aux = this.consultarExistente(tramiteAsignado.getUnidadAdminPK());
            if (aux != null && aux.getUnidadAdminPK() != null) {
                aux.getVigencia().setBlnActivo(tramiteAsignado.getVigencia().getBlnActivo());
                aux.setResponsable(tramiteAsignado.isResponsable());

                getEntityManager().merge(aux);

            }
            else {
                getEntityManager().persist(tramiteAsignado);
            }
        }

    }

    @SuppressWarnings(UNCHECKED)
    @Override
    public List<InfRoleUnidadAdmin> obtenerPermisosPorFuncionario(String rfc) {
        StringBuffer consulta = new StringBuffer();
        consulta.append("FROM InfRoleUnidadAdmin WHERE unidadAdminPK.claveUsuario = :rfc");
        Query query = getEntityManager().createQuery(consulta.toString());
        query.setParameter("rfc", rfc);
        return query.getResultList();
    }

    @SuppressWarnings(UNCHECKED)
    @Override
    public List<InfRoleUnidadAdmin> obtenerPermisosPorFuncionario(String rfc, String unidadAdmin, String rol) {
        StringBuffer consulta = new StringBuffer();
        consulta.append("FROM InfRoleUnidadAdmin WHERE unidadAdminPK.claveUsuario = :rfc and unidadAdminPK.claveRol = :rol and unidadAdminPK.claveUnidadAdministrativa = :unidadAdmin ");
        Query query = getEntityManager().createQuery(consulta.toString());
        query.setParameter("rfc", rfc);
        query.setParameter("unidadAdmin", unidadAdmin);
        query.setParameter(ROL, rol);
        return query.getResultList();
    }

    @SuppressWarnings(UNCHECKED)
    @Override
    public InfRoleUnidadAdmin consultarExistente(InfRoleUnidadAdminPK idInfRole) {
        StringBuffer consulta = new StringBuffer();
        consulta.append("SELECT permiso FROM InfRoleUnidadAdmin permiso WHERE permiso.unidadAdminPK = :idInfRole");
        Query query = getEntityManager().createQuery(consulta.toString());
        query.setParameter("idInfRole", idInfRole);
        List<InfRoleUnidadAdmin> inf = query.getResultList();
        if (inf != null && !inf.isEmpty()) {
            return (InfRoleUnidadAdmin) query.getResultList().get(0);
        }
        else {
            return null;
        }
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public Boolean validarResponsable(InfRoleUnidadAdmin aux) {
        StringBuffer consulta = new StringBuffer();
        consulta.append(" SELECT usuadmintt.IDROL FROM RVCA_ROL_USU_UA_TT usuadmintt "
                + " WHERE usuadmintt.IDROL        = :rol AND usuadmintt.BLNRESPONSABLE = 1 "
                + " AND usuadmintt.BLNACTIVO      = 1 AND usuadmintt.IDUNIADMIN     = :uniAdmin "
                + " AND usuadmintt.IDTIPOTRAMITE  = :modalidad ");
        Query query = getEntityManager().createNativeQuery(consulta.toString());
        query.setParameter("uniAdmin", aux.getUnidadAdminPK().getClaveUnidadAdministrativa());
        query.setParameter("modalidad", aux.getUnidadAdminPK().getIdeTipoTramite());
        query.setParameter(ROL, aux.getUnidadAdminPK().getClaveRol());
        List<String> listaIds = query.getResultList();
        return (listaIds != null && !listaIds.isEmpty());
    }

    public Boolean validarResponsableDuplicado(InfRoleUnidadAdminPK infRoleUnidadAdminPK) {
        StringBuffer consulta = new StringBuffer();
        consulta.append(" SELECT usuadmintt.IDROL FROM RVCA_ROL_USU_UA_TT usuadmintt "
                + " WHERE usuadmintt.IDROL        = :rol AND usuadmintt.BLNRESPONSABLE = 1 "
                + " AND usuadmintt.BLNACTIVO      = 1 AND usuadmintt.IDUNIADMIN     = :uniAdmin "
                + " AND usuadmintt.IDTIPOTRAMITE  = :modalidad and usuadmintt.IDUSUARIO <> :persona ");
        Query query = getEntityManager().createNativeQuery(consulta.toString());
        query.setParameter("uniAdmin", infRoleUnidadAdminPK.getClaveUnidadAdministrativa());
        query.setParameter("modalidad", infRoleUnidadAdminPK.getIdeTipoTramite());
        query.setParameter(ROL, infRoleUnidadAdminPK.getClaveRol());
        query.setParameter("persona", infRoleUnidadAdminPK.getClaveUsuario());
        List<String> listaIds = query.getResultList();

        return !listaIds.isEmpty();
    }
}
