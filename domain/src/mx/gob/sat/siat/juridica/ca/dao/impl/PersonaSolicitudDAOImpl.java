/**
 * 
 */
package mx.gob.sat.siat.juridica.ca.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaSolicitud;
import mx.gob.sat.siat.juridica.base.dao.impl.BaseJPADao;
import mx.gob.sat.siat.juridica.ca.dao.PersonaSolicitudDAO;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author ivan.guzman
 * 
 */
@Repository
public class PersonaSolicitudDAOImpl extends BaseJPADao implements PersonaSolicitudDAO {

    /**
     * 
     */
    private static final long serialVersionUID = 8318973802972278622L;

    /**
     * Metodo para obtener una lista de personas por solicitud
     * 
     * @param idSolicitud
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<PersonaSolicitud> obtenerPersonasSolicitudByIdSol(Long idSolicitud) {
        Query query =
                getEntityManager().createQuery(
                        "SELECT ps FROM PersonaSolicitud ps "
                                + " WHERE ps.idSolicitud = :pIdSolicitud  and ps.blnActivo = 1");
        query.setParameter("pIdSolicitud", idSolicitud);
        return query.getResultList();
    }

    public PersonaSolicitud obtenerPersonaSolicitudByIdSol(Long idSolicitud) {
        return (PersonaSolicitud) getEntityManager().find(PersonaSolicitud.class, idSolicitud);
    }

}
