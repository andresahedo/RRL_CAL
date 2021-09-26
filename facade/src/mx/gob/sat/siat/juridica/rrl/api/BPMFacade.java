package mx.gob.sat.siat.juridica.rrl.api;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;

import java.util.List;

public interface BPMFacade extends BaseFacade {

    List<CatalogoDTO> obtenerCatalogoEstados();

    SolicitudDTO obtenerSolicitud(Long idSolicitud);

}
