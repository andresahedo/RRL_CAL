/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.DiaInhabilDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DiaInhabil;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Repository
public class DiaInhabilDAOImpl extends BaseJPADao implements DiaInhabilDAO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Metodo para obtener los dias inhabiles
     * 
     * @return diasInhabiles
     */
    public List<DiaInhabil> obtenerDiasInhabiles() {
        StringBuilder sb = new StringBuilder();
        sb.append("select c from DiaInhabil c where c.calendario.clave='02' and c.vigencia.blnActivo= 1");
        Query query = getEntityManager().createQuery(sb.toString());
        return query.getResultList();
    }

    @Override
    public int numeroDiasFeriadosEnCalendario(Date fechaInicio, Date fechaFin) {
        int maxResults;
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from DiaInhabil c");
        sb.append(" where c.diaInhabilPK.fechaNoLaborable between :fechaInicio and :fechaFin and c.calendario.clave = '02'");

        Query createQuery = getEntityManager().createQuery(sb.toString());

        createQuery = createQuery.setParameter("fechaInicio", fechaInicio);
        createQuery = createQuery.setParameter("fechaFin", fechaFin);

        maxResults = Integer.parseInt(createQuery.getSingleResult().toString());

        return maxResults;
    }
}
