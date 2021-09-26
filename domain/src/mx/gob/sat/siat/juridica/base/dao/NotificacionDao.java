/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Notificacion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.NotificacionRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.NotificacionResolucion;

/**
 * 
 * @author Softtek
 * 
 */
public interface NotificacionDao {
    /**
     * Metodo para guardar una notificacion
     * 
     * @param ideEnumeracionH
     */
    void guardarNotificacion(Notificacion notificacion);

    Notificacion obtenerNotificacionById(Long idNotificacion);

    NotificacionRequerimiento obtenerNotificacionByIdRequerimiento(Long idRequerimiento);

    NotificacionResolucion obtenerNotificacionByIdResolucion(Long idResolucion);

}
