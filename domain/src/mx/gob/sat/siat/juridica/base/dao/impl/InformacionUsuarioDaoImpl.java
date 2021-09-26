/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.InformacionUsuarioDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.InformacionUsuario;
import mx.gob.sat.siat.juridica.base.util.constante.LoggerConstants;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Repository
public class InformacionUsuarioDaoImpl extends BaseJPADao implements InformacionUsuarioDao {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -7480008152608280013L;

    /**
     * Atributo protegido "LOGGER" tipo estatico final Logger
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(LoggerConstants.DOMAIN_REPOSITORY_LOGGER);

    private static final String ID_USUARIO = "idUsuario";

    /**
     * Metodo para insertar un usuario
     */

    public void insertarRolUsuario(InformacionUsuario informacionUsuario) {
        if (informacionUsuario != null) {
            getEntityManager().persist(informacionUsuario);
        }
    }

    @Override
    public InformacionUsuario verificarUsuarioActivo(String idUsuario) {
        StringBuffer consulta = new StringBuffer();
        consulta.append("FROM InformacionUsuario WHERE informacionUsuarioPK.idUsuario = :idUsuario");
        Query query = getEntityManager().createQuery(consulta.toString());
        query.setParameter(ID_USUARIO, idUsuario);

        return (InformacionUsuario) query.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<InformacionUsuario> consultarRolesFuncionario(String idUsuario) {
        StringBuffer consulta = new StringBuffer();
        consulta.append("FROM InformacionUsuario info WHERE info.informacionUsuarioPK.idUsuario = :idUsuario AND (info.blnActivo=1 OR info.blnActivo IS NULL)");
        Query query = getEntityManager().createQuery(consulta.toString());
        query.setParameter(ID_USUARIO, idUsuario);
        return query.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<InformacionUsuario> buscarUsuarioPorRol(String idRol) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT i FROM InformacionUsuario i ");
        sb.append("WHERE i.informacionUsuarioPK.idRol = :idRol");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("idRol", idRol);
        return (List<InformacionUsuario>) query.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<InformacionUsuario> buscarRolesActivosPorId(String idUsuario) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT i FROM InformacionUsuario i ");
        sb.append("WHERE i.informacionUsuarioPK.idUsuario = :idUsuario ");
        sb.append("AND (i.blnActivo = 1 or i.blnActivo is null)");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter(ID_USUARIO, idUsuario);
        return (List<InformacionUsuario>) query.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<InformacionUsuario> buscarRolesPorId(String idUsuario) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT i FROM InformacionUsuario i ");
        sb.append("WHERE i.informacionUsuarioPK.idUsuario = :idUsuario ");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter(ID_USUARIO, idUsuario);
        return (List<InformacionUsuario>) query.getResultList();
    }

    public void modificarRolUsuario(InformacionUsuario infoUsu) {
        getEntityManager().merge(infoUsu);
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public InformacionUsuario buscarUsuarioPorId(String idUsuario, String idRol) {
        StringBuffer consulta = new StringBuffer();
        consulta.append("SELECT info FROM InformacionUsuario info ");
        consulta.append("WHERE info.informacionUsuarioPK.idUsuario = :idUsuario ");
        consulta.append("AND info.informacionUsuarioPK.idRol = :idRol ");
        Query query = getEntityManager().createQuery(consulta.toString());
        query.setParameter(ID_USUARIO, idUsuario);
        query.setParameter("idRol", idRol);
        List<InformacionUsuario> listaInfUsuario = query.getResultList();
        if (listaInfUsuario != null && !listaInfUsuario.isEmpty()) {
            return listaInfUsuario.get(0);
        }
        else {
            return null;
        }
    }

}
