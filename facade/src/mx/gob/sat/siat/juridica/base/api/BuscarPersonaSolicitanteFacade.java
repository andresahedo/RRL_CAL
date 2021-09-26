package mx.gob.sat.siat.juridica.base.api;

import com.softtek.idc.client.util.exception.ContribuyenteInactivoException;
import com.softtek.idc.client.util.exception.ContribuyenteNoEncontradoException;
import com.softtek.idc.client.util.exception.IDCException;
import com.softtek.idc.client.util.exception.RFCNoVigenteException;
import com.softtek.idc.common.exceptions.IDCNoDisponibleException;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.buzon.exception.BuzonNoDisponibleException;
import mx.gob.sat.siat.juridica.buzon.exception.CorreoNoRegistradoException;

import java.util.List;

public interface BuscarPersonaSolicitanteFacade {

    PersonaSolicitudDTO buscarPersonaSolicitante(String rfc) throws IDCNoDisponibleException, IDCException,
            ContribuyenteNoEncontradoException, ContribuyenteInactivoException, RFCNoVigenteException,
            BuzonNoDisponibleException, CorreoNoRegistradoException;

    List<CatalogoDTO> comboTipoPersona();

    List<ColoniaDTO> obtenerColoniasPorCp(String cp);

    List<ColoniaDTO> obtenerColonias();

    List<DelegacionMunicipioDTO> obtenerDelegacionesMunicipios();

    List<ColoniaDTO> buscarColoniaPorClaveDelegacion(String claveDelegacion);

    List<LocalidadDTO> buscarLocalidadPorClaveMunicipio(String claveMunicipio);

    PaisDTO obtenerPaises();

    List<LocalidadDTO> obtenerLocalidadPorClaveDelegacion(String claveDelegacion);

    List<EntidadFederativaDTO> obtenerEstados();

    List<DelegacionMunicipioDTO> obtenerDelegacionMunicipioPorCveEstado(String claveEstado);

    List<ColoniaDTO> buscarColoniaPorCveLocalidad(String claveLocalidad);

    List<LocalidadDTO> buscarLocalidadPorCP(String cp);

}
