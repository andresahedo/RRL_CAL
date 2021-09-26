package mx.gob.sat.siat.juridica.rrl.api;

import mx.gob.sat.siat.juridica.base.api.AtenderObservacionFacade;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;

import java.util.List;

public interface AtenderObservacionRRLFacade extends AtenderObservacionFacade {

    void modificarSolicitud(DatosSolicitudDTO solicitud) throws SolicitudNoGuardadaException;

    List<CatalogoDTO> obtenerAutoridadesEmisoras();

    DatosSolicitudDTO obtenerSolicitud(Long idSolicitud);

}
