package mx.gob.sat.siat.juridica.oficialia.api;

import mx.gob.sat.siat.juridica.base.dto.BandejaConsultaRechazoDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;

import java.util.List;

/**
 * @author Softtek
 *
 */
public interface BandejaConsultasOficialiaFacade extends BaseFacade {

    List<BandejaConsultaRechazoDTO> obtenerTareasRechazo(FiltroBandejaTareaDTO filtroBandejaTareaDTO);

    List<BandejaConsultaRechazoDTO> obtenerTareasDoctoRechazo(FiltroBandejaTareaDTO filtroBandejaTareaDTO);

}
