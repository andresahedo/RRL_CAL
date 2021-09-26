/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.RemisionDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Remision;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Repository("remisionDao")
public class RemisionDaoImpl extends BaseJPADao implements RemisionDao {

    private static final long serialVersionUID = -4148156714248346410L;

    /**
     * Metodo para guardar una remision
     */
    @Override
    public void guardarRemision(Remision remision) {
        if (null != remision) {
            if (null != remision.getIdRemision()) {
                getEntityManager().persist(remision);
            }
            getEntityManager().merge(remision);
        }
    }

    /**
     * Metodo para obtener la ultima remision por id de tramite
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public Remision obtenerUltimaRemisionPorIdTramite(String numAsunto) {
        Query query =
                getEntityManager()
                        .createQuery(
                                "SELECT t FROM Remision t "
                                        + " WHERE t.tramite.numeroAsunto = :nAsunto and t.estadoRemision <> 'ESTREM.RZ' ORDER BY t.idRemision DESC",
                                Remision.class);
        query.setParameter("nAsunto", numAsunto);
        List<Remision> lista = query.getResultList();
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }
}
