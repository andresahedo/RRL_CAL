package mx.gob.sat.siat.juridica.ca.api;

import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;

public interface DetallePromocionFacade extends BaseFacade {

    TramiteDTO obtenerTramite(String numAasunto);

    void modificarTramite(TramiteDTO tramiteDTO) throws SolicitudNoGuardadaException;

}
