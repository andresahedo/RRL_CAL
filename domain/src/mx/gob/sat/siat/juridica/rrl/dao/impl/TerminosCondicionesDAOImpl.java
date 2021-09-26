package mx.gob.sat.siat.juridica.rrl.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.TerminosCondiciones;
import mx.gob.sat.siat.juridica.base.dao.impl.BaseJPADao;
import mx.gob.sat.siat.juridica.rrl.dao.TerminosCondicionesDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class TerminosCondicionesDAOImpl extends BaseJPADao implements TerminosCondicionesDAO {

    /**
     * 
     */
    private static final long serialVersionUID = 7820963745977850499L;

    @Override
    public TerminosCondiciones obtenerTerminosCondiciones(int numPantalla) {
        TerminosCondiciones terminos = null;
        StringBuffer consulta = new StringBuffer();
        consulta.append(" SELECT termino from TerminosCondiciones termino where termino.idCondicionUso = :clave");

        Query query = getEntityManager().createQuery(consulta.toString());
        query.setParameter("clave", numPantalla);
        terminos = (TerminosCondiciones) query.getResultList().get(0);
        return terminos;
    }

}
