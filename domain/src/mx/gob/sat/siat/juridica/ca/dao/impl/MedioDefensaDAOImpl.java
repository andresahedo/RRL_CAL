package mx.gob.sat.siat.juridica.ca.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.MedioDefensa;
import mx.gob.sat.siat.juridica.base.dao.impl.BaseJPADao;
import mx.gob.sat.siat.juridica.ca.dao.MedioDefensaDAO;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class MedioDefensaDAOImpl extends BaseJPADao implements MedioDefensaDAO {

    /**
     * 
     */
    private static final long serialVersionUID = 8357762638155583339L;

    /**
     * Metodo para obtener un medio de defensa por Id de solicitud
     * 
     * @param idSolicitud
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public MedioDefensa obtenerMedioDefensaById(Long idSolicitud) {
        Query query =
                getEntityManager().createQuery(
                        "SELECT md FROM MedioDefensa md "
                                + " WHERE md.idSolicitud = :pIdSolicitud AND md.blnActivo = 1 ");
        query.setParameter("pIdSolicitud", idSolicitud);
        List<Object> resultado = query.getResultList();
        if (resultado != null && !resultado.isEmpty()) {
            return (MedioDefensa) resultado.get(0);
        }
        else {
            return null;
        }
    }
}
