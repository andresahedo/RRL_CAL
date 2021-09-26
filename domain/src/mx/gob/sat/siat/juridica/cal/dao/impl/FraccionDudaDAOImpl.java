/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.FraccionDuda;
import mx.gob.sat.siat.juridica.base.dao.impl.BaseJPADao;
import mx.gob.sat.siat.juridica.cal.dao.FraccionDudaDAO;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Repository
public class FraccionDudaDAOImpl extends BaseJPADao implements FraccionDudaDAO {

    /**
     * 
     */
    private static final long serialVersionUID = 2015658165545055861L;

    /**
     * Metodo para obtener fracciones arancelarias en las que existe
     * duda por Id de solicitud
     * 
     * @param idSolicitud
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<FraccionDuda> obtenerFraccionesDudaBySolicitudId(Long idSolicitud) {
        Query query =
                getEntityManager().createQuery(
                        "SELECT fd FROM FraccionDuda fd "
                                + " WHERE fd.idSolicitud = :pIdSolicitud and fd.blnActivo = 1 ");
        query.setParameter("pIdSolicitud", idSolicitud);
        return query.getResultList();
    }

    @Override
    public FraccionDuda obtenerFraccionById(Long idFraccion) {
        // TODO Auto-generated method stub
        return (FraccionDuda) getEntityManager().find(FraccionDuda.class, idFraccion);
    }

}
