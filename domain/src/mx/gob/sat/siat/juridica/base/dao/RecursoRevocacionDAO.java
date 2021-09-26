/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.SolicitudDatosGenerales;

/**
 * 
 * @author Softtek
 * 
 */
public interface RecursoRevocacionDAO {
    /**
     * Metodo para obtener una solucitud de recurso de revocacion por
     * idSolicitud
     * 
     * @param idSolicitud
     * @return solucitud de recurso de revocacion
     */
    SolicitudDatosGenerales obtenerSolicitudRRPorId(Long idSolicitud);

}
