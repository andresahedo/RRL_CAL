/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.UserStateDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UserState;
import mx.gob.sat.siat.juridica.base.util.constante.LoggerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Repository
public class UserStateDaoImpl extends BaseJPADao implements UserStateDao {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -7480008152608280013L;

    /**
     * Atributo protegido "LOGGER" tipo estatico final Logger
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(LoggerConstants.DOMAIN_REPOSITORY_LOGGER);

    /**
     * Metodo para buscar un usuario por rfc
     */
    public UserState obtenerUsuario(String rfc) {
        Query query = getEntityManager().createQuery("select u from UserState u where u.userName = :rfc");
        query.setParameter("rfc", rfc);
        List<Object> resultado = query.getResultList();
        if (resultado != null && !resultado.isEmpty()) {
            return (UserState) resultado.get(0);
        }
        else {
            return null;
        }
    }

    /**
     * Metodo para insertar un usuario
     */

    public UserState insertarUsuario(UserState usuario) {
        if (usuario != null) {
            getEntityManager().persist(usuario);
        }
        return usuario;
    }

    @Override
    public void modificarUsuario(UserState usuario) {
        getEntityManager().merge(usuario);
    }

    @Override
    public List<UserState> obtenerUsuariosActivos() {
        String q = "select u from UserState u where u.active = :active";
        TypedQuery<UserState> query = getEntityManager().createQuery(q, UserState.class);
        query.setParameter("active", true);

        return query.getResultList();
    }

}
