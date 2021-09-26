package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.ManifiestoDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Manifiesto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ManifiestoDAOImpl extends BaseJPADao implements ManifiestoDAO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public List<Manifiesto> obtenerManifiestos() {
        String q = "FROM Manifiesto AS m";
        return getEntityManager()
                .createQuery(q, Manifiesto.class)
                .getResultList();
    }

    @Override
    public List<Manifiesto> obtenerManifiestosPorIdModalidad(Integer idModalidad) {
        String q = "FROM Manifiesto AS m WHERE m.tipoTramite.id =:idModalidad ORDER BY m.secuencia ASC";
        return getEntityManager()
                .createQuery(q, Manifiesto.class)
                .setParameter("idModalidad", idModalidad)
                .getResultList();
    }

    @Override
    public Manifiesto obtenerManifiestoPorId(Integer idManifiesto) {
        String q = "FROM Manifiesto AS m WHERE m.idManifiesto =:idManifiesto";
        return getEntityManager()
                .createQuery(q, Manifiesto.class)
                .setParameter("idManifiesto", idManifiesto)
                .getSingleResult();
    }

}
