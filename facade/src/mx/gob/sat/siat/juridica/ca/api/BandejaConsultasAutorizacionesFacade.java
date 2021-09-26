package mx.gob.sat.siat.juridica.ca.api;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;

import java.util.List;

public interface BandejaConsultasAutorizacionesFacade extends BaseFacade {

    List<DatosBandejaTareaDTO> obtenerTramites(FiltroBandejaTareaDTO filtroBandeja);

    List<CatalogoDTO> obtenerCatalogoTipoTramites(String servicio);

}
