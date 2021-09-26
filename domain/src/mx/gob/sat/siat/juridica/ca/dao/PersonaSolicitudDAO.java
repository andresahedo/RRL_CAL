/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaSolicitud;

import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface PersonaSolicitudDAO {

    /**
     * Metodo para obtener una lista de personas por solicitud
     * 
     * @param idSolicitud
     * @return
     */
    List<PersonaSolicitud> obtenerPersonasSolicitudByIdSol(Long idSolicitud);

    PersonaSolicitud obtenerPersonaSolicitudByIdSol(Long idSolicitud);

}
