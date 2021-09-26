/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.NotificacionDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Notificacion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.NotificacionRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.NotificacionResolucion;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Repository("notificacionDao")
public class NotificacionDaoImpl extends BaseJPADao implements NotificacionDao {

    private static final long serialVersionUID = 6220366991918850205L;

    /**
     * Metodo para guardar una notificacion
     */
    public void guardarNotificacion(Notificacion notificacion) {
        if (notificacion != null) {
            if (notificacion.getIdNotificacion() == null) {
                getEntityManager().persist(notificacion);
                getEntityManager().flush();
            }
            else {
                getEntityManager().merge(notificacion);
                getEntityManager().flush();
            }
        }
    }

    @Override
    public Notificacion obtenerNotificacionById(Long idNotificacion) {
        return (Notificacion) getEntityManager().find(Notificacion.class, idNotificacion);
    }

    @Override
    public NotificacionRequerimiento obtenerNotificacionByIdRequerimiento(Long idRequerimiento) {
        Query query =
                getEntityManager().createQuery(
                        "from NotificacionRequerimiento nr where nr.requerimiento.idRequerimiento = :idRequerimiento");
        query.setParameter("idRequerimiento", idRequerimiento);
        List<NotificacionRequerimiento> notificaciones = query.getResultList();
        return notificaciones.size() == 0 ? null : notificaciones.get(0);
    }

    @Override
    public NotificacionResolucion obtenerNotificacionByIdResolucion(Long idResolucion) {
        Query query =
                getEntityManager().createQuery(
                        "from NotificacionResolucion nr where nr.resolucion.idResolucion = :idResolucion");
        query.setParameter("idResolucion", idResolucion);
        List<NotificacionResolucion> notificaciones = query.getResultList();
        return notificaciones.size() == 0 ? null : notificaciones.get(0);
    }

}
