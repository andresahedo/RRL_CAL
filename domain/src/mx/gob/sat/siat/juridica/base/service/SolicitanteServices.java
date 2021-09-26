/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitante;

import java.io.Serializable;

/***
 * 
 * @author softtek
 * 
 */
public interface SolicitanteServices extends Serializable {

    /**
     * Metodo para obtener un dolicitante por id de solicitud
     * 
     * @param idSolicitud
     * @return Solicitante
     */
    Solicitante obtenerSolicitanteByIdSol(Long idSolicitud);

}
