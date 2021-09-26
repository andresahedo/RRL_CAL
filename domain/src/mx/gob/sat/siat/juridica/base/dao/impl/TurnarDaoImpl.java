/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.TurnarDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Turnar;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Clase Dao para la Reasignacion y turnado de asuntos
 * 
 * @author Softtek - EQG
 * @since 14/11/2014
 */
@Repository
public class TurnarDaoImpl extends BaseJPADao implements TurnarDao {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 4688295676917595150L;

    /**
     * Metodo para guardar las observaciones del turnado
     * 
     * @param datosTurnar
     */
    @Override
    public void guardaDatosTurnar(Turnar datosTurnar) {
        try {
            if (datosTurnar != null) {
                if (datosTurnar.getIdTurnar() != null) {
                    getEntityManager().merge(datosTurnar);
                }
                else {
                    getEntityManager().persist(datosTurnar);
                }
            }
            getEntityManager().flush();
        }
        catch (Exception e) {
            getLog().error("", e);
        }
    }

    /**
     * Metodo para obtener la descripcion de la observacion del
     * turnado
     * 
     * @param numeroAsunto
     * @return String descObservacion
     */
    @SuppressWarnings("unchecked")
    @Override
    public String obtenerObservacionTurnado(String numeroAsunto) {
        Query query =
                getEntityManager()
                        .createQuery(
                                "SELECT tr.descObservacion FROM Turnar tr WHERE tr.tramite.numeroAsunto =:numeroAsunto Order by tr.idTurnar DESC");
        query.setParameter("numeroAsunto", numeroAsunto);
        List<String> descObservacion = query.getResultList();
        if (descObservacion != null && !descObservacion.isEmpty()) {
            return descObservacion.get(0);
        }
        else {
            return "";
        }
    }

}
