package mx.gob.sat.siat.juridica.base.api;

import mx.gob.sat.siat.juridica.base.dto.ObservacionDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;

public interface GenerarObservacionFacade extends BaseFacade {

    ObservacionDTO observar(ObservacionDTO observacionDTO, Long idTarea, String rfcUsuario)
            throws SolicitudNoGuardadaException;

    TramiteDTO obtenerTramite(String numAsunto);

}
