package mx.gob.sat.siat.juridica.base.api;

import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaPendientesDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaPendientesDTO;

import java.io.Serializable;
import java.util.List;

public interface BandejaPendientesFacade extends Serializable {

    List<DatosBandejaPendientesDTO> obtenerSolicitudes(FiltroBandejaPendientesDTO filtroBandejaPendientesDTO);

}
