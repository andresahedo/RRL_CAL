package mx.gob.sat.siat.juridica.configuracion.usuarios.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.base.dao.impl.BaseJPADao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.RolesDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RolesDaoImpl extends BaseJPADao implements RolesDao {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -4077752977746865118L;

    @SuppressWarnings("unchecked")
    @Override
    public List<Role> obtenerRoles() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT roles FROM Role roles where roles.vigencia.blnActivo = 1 ");
        Query query = getEntityManager().createQuery(sb.toString());
        return query.getResultList();
    }

    @Override
    public Role obtenerRolePorIdRol(String claveRol) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT roles FROM Role roles WHERE roles.name =:claveRol and roles.vigencia.blnActivo = 1 ");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("claveRol", claveRol);
        return (Role) query.getSingleResult();
    }

}
