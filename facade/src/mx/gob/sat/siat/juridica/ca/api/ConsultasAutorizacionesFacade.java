package mx.gob.sat.siat.juridica.ca.api;

import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.dto.SolicitudConsultaAutorizacionDTO;

public interface ConsultasAutorizacionesFacade extends BaseFacade {

    SolicitudConsultaAutorizacionDTO obtenerDatosSolicitud(Long idSolicitud);

    DatosSolicitudDTO obtenerDatosSolicitante(Long idSolicitud);

}
