package mx.gob.sat.siat.juridica.base.api.impl;

import com.softtek.idc.client.util.exception.ContribuyenteInactivoException;
import com.softtek.idc.client.util.exception.ContribuyenteNoEncontradoException;
import com.softtek.idc.client.util.exception.IDCException;
import com.softtek.idc.client.util.exception.RFCNoVigenteException;
import com.softtek.idc.common.exceptions.IDCNoDisponibleException;
import mx.gob.sat.siat.juridica.base.api.BuscarPersonaSolicitanteFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitante;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.PersonaSolicitudDTOTransformer;
import mx.gob.sat.siat.juridica.base.service.BuscarPersonaSolicitanteService;
import mx.gob.sat.siat.juridica.buzon.exception.BuzonNoDisponibleException;
import mx.gob.sat.siat.juridica.buzon.exception.CorreoNoRegistradoException;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("buscarPersonaSolicitanteFacade")
public class BuscarPersonaSolicitanteFacadeImpl implements BuscarPersonaSolicitanteFacade {

    @Autowired
    private transient BuscarPersonaSolicitanteService buscarPersonaService;

    @Autowired
    private transient PersonaSolicitudDTOTransformer personaSolicitudTransformer;

    @Autowired
    private transient CatalogoDTOTransformer catalogoTransformer;

    @Autowired
    private transient PaisDTOTransformer paisTransformer;

    @Autowired
    private transient EntidadFederativaDTOTransformer entidadTransformer;

    @Autowired
    private transient DelegacionMunicipioDTOTransformer delegMunicipioTransformer;

    @Autowired
    private transient LocalidadDTOTransformer localidadTransformer;

    @Autowired
    private transient ColoniaDTOTransformer coloniaTransformer;

    @Override
    public PersonaSolicitudDTO buscarPersonaSolicitante(String rfc) throws IDCNoDisponibleException, IDCException,
            ContribuyenteNoEncontradoException, ContribuyenteInactivoException, RFCNoVigenteException,
            BuzonNoDisponibleException, CorreoNoRegistradoException {
        PersonaSolicitudDTO personaSolicitudDTO = new PersonaSolicitudDTO();
        Solicitante solicitante = buscarPersonaService.buscarPersonaSolicitanteIdc(rfc);
        if (solicitante != null) {
            personaSolicitudDTO = personaSolicitudTransformer.transformarDTO(solicitante);
        }
        return personaSolicitudDTO;
    }

    @Override
    public List<CatalogoDTO> comboTipoPersona() {
        List<CatalogoDTO> comboTiposPersonaDTO = new ArrayList<CatalogoDTO>();
        List<CatalogoD> comboTiposPersona = buscarPersonaService.comboTiposPersona();
        if (comboTiposPersona != null && !comboTiposPersona.isEmpty()) {
            comboTiposPersonaDTO = catalogoTransformer.transformarTiposPersona(comboTiposPersona);
        }
        return comboTiposPersonaDTO;
    }

    @Override
    public PaisDTO obtenerPaises() {
        return paisTransformer.transformarDTO(buscarPersonaService.buscarPaisMexico());
    }

    @Override
    public List<ColoniaDTO> obtenerColoniasPorCp(String cp) {
        List<ColoniaDTO> coloniasDTO = new ArrayList<ColoniaDTO>();
        List<Colonia> colonias = buscarPersonaService.obtenerColoniasPorCp(cp);
        if (colonias != null && !colonias.isEmpty()) {
            for (Colonia col : colonias) {
                DelegacionMunicipioDTO deleg = delegMunicipioTransformer.transformarDTO(col.getDelegacionMunicipio());
                deleg.setEntidadFederativaDTO(entidadTransformer.transformarDTO(col.getDelegacionMunicipio()
                        .getEntidadFederativa()));
                ColoniaDTO coldto = coloniaTransformer.transformarDTO(col);
                coldto.setDelegacionMunicipioDTO(deleg);
                coloniasDTO.add(coldto);
            }
        }
        return coloniasDTO;
    }

    @Override
    public List<ColoniaDTO> obtenerColonias() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DelegacionMunicipioDTO> obtenerDelegacionesMunicipios() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ColoniaDTO> buscarColoniaPorClaveDelegacion(String claveDelegacion) {
        List<ColoniaDTO> coloniasDTO = new ArrayList<ColoniaDTO>();
        for (Colonia col : buscarPersonaService.buscarColoniaPorClaveDelegacion(claveDelegacion)) {
            ColoniaDTO coldto = coloniaTransformer.transformarDTO(col);
            coloniasDTO.add(coldto);

        }
        return coloniasDTO;
    }

    @Override
    public List<EntidadFederativaDTO> obtenerEstados() {

        List<EntidadFederativaDTO> estadosDTO = new ArrayList<EntidadFederativaDTO>();
        for (EntidadFederativa entidad : buscarPersonaService.obtenerEstados()) {
            EntidadFederativaDTO entidadDTO = new EntidadFederativaDTO();
            entidadDTO.setClave(entidad.getClave());
            entidadDTO.setClaveEnIDC(entidad.getClaveEnIDC());
            entidadDTO.setNombre(entidad.getNombre());
            PaisDTO paisDTO = paisTransformer.transformarDTO(entidad.getPais());
            entidadDTO.setPaisDTO(paisDTO);
            estadosDTO.add(entidadDTO);
        }
        return estadosDTO;
    }

    @Override
    public List<LocalidadDTO> buscarLocalidadPorClaveMunicipio(String claveMunicipio) {
        List<LocalidadDTO> localidadesDTO = new ArrayList<LocalidadDTO>();
        List<Localidad> localidades = buscarPersonaService.obtenerLocalidadPorClaveDelegacion(claveMunicipio);
        if (localidades != null && !localidades.isEmpty()) {
            for (Localidad loc : localidades) {
                LocalidadDTO locdto = localidadTransformer.transformarDTO(loc);
                DelegacionMunicipioDTO deleg = delegMunicipioTransformer.transformarDTO(loc.getDelegacionMunicipio());
                deleg.setEntidadFederativaDTO(entidadTransformer.transformarDTO(loc.getDelegacionMunicipio()
                        .getEntidadFederativa()));
                locdto.setDelegacionMunicipioDTO(deleg);
                localidadesDTO.add(locdto);
            }
        }
        return localidadesDTO;
    }

    @Override
    public List<LocalidadDTO> obtenerLocalidadPorClaveDelegacion(String claveDelegacion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DelegacionMunicipioDTO> obtenerDelegacionMunicipioPorCveEstado(String claveEstado) {
        List<DelegacionMunicipioDTO> listaDelegMunicipiosDTO = new ArrayList<DelegacionMunicipioDTO>();
        List<DelegacionMunicipio> listaDelegaciones =
                buscarPersonaService.obtenerDelegacionMunicipioPorCveEstado(claveEstado);
        List<ColoniaDTO> coloniasDTO = new ArrayList<ColoniaDTO>();
        if (listaDelegaciones != null && !listaDelegaciones.isEmpty()) {
            for (DelegacionMunicipio del : listaDelegaciones) {
                DelegacionMunicipioDTO delegacionDTO = delegMunicipioTransformer.transformarDTO(del);
                EntidadFederativaDTO estadoDTO = entidadTransformer.transformarDTO(del.getEntidadFederativa());
                delegacionDTO.setEntidadFederativaDTO(estadoDTO);
                for (Colonia col : del.getColonias()) {
                    ColoniaDTO coldto = coloniaTransformer.transformarDTO(col);
                    coldto.setDelegacionMunicipioDTO(delegacionDTO);
                    coldto.setCp(col.getCp());
                    coldto.setClave(col.getClave());
                    coldto.setNombre(col.getNombre());
                    coldto.setIdColoniaIDC(col.getIdColoniaIDC());
                    coloniasDTO.add(coldto);
                }
                delegacionDTO.setColoniasDTO(coloniasDTO);
                listaDelegMunicipiosDTO.add(delegacionDTO);
            }
        }

        return listaDelegMunicipiosDTO;
    }

    @Override
    public List<ColoniaDTO> buscarColoniaPorCveLocalidad(String claveLocalidad) {
        List<ColoniaDTO> coloniasDTO = new ArrayList<ColoniaDTO>();
        List<Colonia> cols = buscarPersonaService.buscarColoniaPorCveLocalidad(claveLocalidad);
        if (cols != null && !cols.isEmpty()) {
            for (Colonia col : cols) {
                ColoniaDTO coldto = coloniaTransformer.transformarDTO(col);
                LocalidadDTO locdto = localidadTransformer.transformarDTO(col.getLocalidad());
                coldto.setLocalidad(locdto);
                coloniasDTO.add(coldto);
            }
        }
        return coloniasDTO;

    }

    @Override
    public List<LocalidadDTO> buscarLocalidadPorCP(String cp) {

        List<LocalidadDTO> listaLocalidadesDTO = new ArrayList<LocalidadDTO>();
        List<Localidad> locs = buscarPersonaService.buscarLocalidadPorCP(cp);
        if (locs != null && !locs.isEmpty()) {
            for (Localidad lc : locs) {
                LocalidadDTO locDTO = localidadTransformer.transformarDTO(lc);
                listaLocalidadesDTO.add(locDTO);

            }
        }

        return listaLocalidadesDTO;
    }

}
