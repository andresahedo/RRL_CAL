package mx.gob.sat.siat.juridica.base.service;

import com.softtek.idc.client.util.exception.ContribuyenteInactivoException;
import com.softtek.idc.client.util.exception.ContribuyenteNoEncontradoException;
import com.softtek.idc.client.util.exception.IDCException;
import com.softtek.idc.client.util.exception.RFCNoVigenteException;
import com.softtek.idc.common.exceptions.IDCNoDisponibleException;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitante;
import mx.gob.sat.siat.juridica.buzon.exception.BuzonNoDisponibleException;
import mx.gob.sat.siat.juridica.buzon.exception.CorreoNoRegistradoException;

import java.io.Serializable;
import java.util.List;

public interface BuscarPersonaSolicitanteService extends Serializable {

    Solicitante buscarPersonaSolicitanteIdc(String rfc) throws IDCNoDisponibleException, IDCException,
            ContribuyenteNoEncontradoException, ContribuyenteInactivoException, RFCNoVigenteException,
            BuzonNoDisponibleException, CorreoNoRegistradoException;

    List<CatalogoD> comboTiposPersona();

    List<Colonia> obtenerColoniasPorCp(String cp);

    List<Colonia> obtenerColonias();

    List<DelegacionMunicipio> obtenerDelegacionesMunicipios();

    List<Colonia> buscarColoniaPorClaveDelegacion(String claveDelegacion);

    Pais buscarPaisMexico();

    List<Localidad> obtenerLocalidadPorClaveDelegacion(String claveDelegacion);

    List<EntidadFederativa> obtenerEstados();

    List<DelegacionMunicipio> obtenerDelegacionMunicipioPorCveEstado(String claveEstado);

    List<Colonia> buscarColoniaPorCveLocalidad(String claveLocalidad);

    List<Localidad> buscarLocalidadPorCP(String cp);
}
