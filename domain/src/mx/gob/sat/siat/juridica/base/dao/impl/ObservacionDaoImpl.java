/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.ObservacionDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Observacion;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Repository("observacionDao")
public class ObservacionDaoImpl extends BaseJPADao implements ObservacionDao {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -7631295177523389577L;

    /**
     * Metodo para obtener un observacion por id
     */
    @Override
    public Observacion obtenerObservacionPorId(Long idObservacion) {
        Query query =
                getEntityManager().createQuery("select p from Observacion p where p.idObservacion = :idObservacion",
                        Observacion.class);
        query.setParameter("idObservacion", idObservacion);
        return (Observacion) query.getSingleResult();

    }

    /**
     * Metodo para crear una Observacion
     */
    @Override
    public Observacion guardarObservacion(Observacion observacion) {
        if (observacion != null) {
            if (observacion.getIdObservacion() == null) {
                getEntityManager().persist(observacion);
                getEntityManager().flush();
            }
            else {
                getEntityManager().merge(observacion);
                getEntityManager().flush();
            }
        }
        return observacion;
    }

    /**
     * Metodo para obtener la ultima remision por id de tramite
     */
    @Override
    public Observacion obtenerUltimaObservacionPorTramite(String idTramite) {

        Query query =
                getEntityManager().createQuery(
                        "SELECT o FROM Observacion o "
                                + " WHERE o.tramite.numeroAsunto = :idTramite ORDER BY o.idObservacion DESC",
                        Observacion.class);
        query.setParameter("idTramite", idTramite);

        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        List<Observacion> lista = query.getResultList();
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        else {
            return null;
        }
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<Observacion> obtenerObservacionesPorTramite(String numAsunto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT obs FROM Observacion obs ");
        sb.append(" WHERE obs.tramite.numeroAsunto = :numAsunto ");
        Query consulta = getEntityManager().createQuery(sb.toString());
        consulta.setParameter("numAsunto", numAsunto);
        List<Observacion> observaciones = consulta.getResultList();
        if (observaciones != null && !observaciones.isEmpty()) {
            return observaciones;
        }

        else {
            return observaciones;
        }
    }

}
