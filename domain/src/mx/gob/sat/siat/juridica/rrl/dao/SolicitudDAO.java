/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.SolicitudDatosGenerales;
import mx.gob.sat.siat.juridica.rrl.dao.domain.model.SolicitudRecursoRevocacion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface SolicitudDAO extends Serializable {

    /**
     * Metodo para obtener una solicitud por id
     * 
     * @param idSolicitud
     * @return
     */
    Solicitud obtenerSolicitudporId(Long idSolicitud);

    /**
     * Metodo para consultar uns solicitud de autorizacion por id
     * 
     * @param idSolicitud
     * @return
     */
    SolicitudDatosGenerales obtenerSolicitudDatosGeneralesPorId(Long idSolicitud);

    /**
     * Metodo para guardar una solicitud
     * 
     * @param solicitud
     * @return
     */
    Solicitud guardarSolicitud(Solicitud solicitud);

    /**
     * Metodo para obtener solicitudes por filtros
     * 
     * @param idSolicitud
     * @param fechaInicio
     * @param fechaFin
     * @param rfc
     * @return
     */
    List<Solicitud> obtenerSolicitudesPorFiltros(Long idSolicitud, Date fechaInicio, Date fechaFin, String rfc);

    SolicitudRecursoRevocacion guardarSolicitudRRL(SolicitudRecursoRevocacion solicitud);

}
