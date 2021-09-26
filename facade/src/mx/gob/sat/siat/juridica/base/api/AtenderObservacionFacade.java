/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.api;

import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.ObservacionDTO;
import mx.gob.sat.siat.juridica.base.dto.PersonaSolicitudDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;

/**
 * 
 * @author Softtek
 * 
 */
public interface AtenderObservacionFacade extends BaseFacade {

    ObservacionDTO obternerObservacion(String numeroAsunto);

    PersonaSolicitudDTO obtenerSolicitante(Long idSolicitud);

    void firmaAtender(String numeroAsunto, Long idObservacion, String rfc, FirmaDTO firma, String idTarea)
            throws SolicitudNoGuardadaException;

}
