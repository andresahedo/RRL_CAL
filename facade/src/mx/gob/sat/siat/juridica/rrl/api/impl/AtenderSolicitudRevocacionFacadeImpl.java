package mx.gob.sat.siat.juridica.rrl.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.CatalogoD;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.SentidosResolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Resolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.ResolucionDatosGenerados;
import mx.gob.sat.siat.juridica.base.dao.domain.model.ResolucionImpugnada;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.base.dto.transformer.AgraviosDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.UnidadAdministrativaDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.CatalogoDServices;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.base.service.TurnarService;
import mx.gob.sat.siat.juridica.base.service.UnidadAdministrativaServices;
import mx.gob.sat.siat.juridica.base.util.constante.CatalogoConstantes;
import mx.gob.sat.siat.juridica.cal.dto.transformer.ResolucionDatosGeneradosDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.api.AtenderSolicitudRevocacionFacade;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionAbogadoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.ResolucionDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.TramiteDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.service.AtenderSolicitudRevocacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("atenderSolicitudRevocacionFacade")
public class AtenderSolicitudRevocacionFacadeImpl extends BaseFacadeImpl implements AtenderSolicitudRevocacionFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 1393262081230655841L;

    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;

    @Autowired
    private transient ResolucionDTOTransformer resolucionDTOTransformer;

    @Autowired
    private transient TramiteDTOTransformer tramiteDTOTransformer;

    @Autowired
    private transient UnidadAdministrativaDTOTransformer unidadAdministrativaDTOTransformer;

    @Autowired
    private transient AtenderSolicitudRevocacionService atenderSolicitudRevocacionService;

    @Autowired
    private transient CatalogoDServices catalogoDServices;

    @Autowired
    private transient UnidadAdministrativaServices unidadAdministrativaServices;

    @Autowired
    private transient DocumentoDTOTransformer documentoDTOTransformer;

    @Autowired
    private transient ResolucionDatosGeneradosDTOTransformer resolucionDatosGeneralesDTOTransformer;

    @Autowired
    private transient AgraviosDTOTransformer agraviosDTOTransformer;

    @Autowired
    private TurnarService turnarService;

    @Autowired
    private transient TramiteServices tramiteServices;

    /*
     * Obtiene informacion de la primera seccion
     */
    @Override
    public ResolucionAbogadoDTO obtenerInformacionFuncionario(SolicitudDTO solicitudDTO, TramiteDTO tramiteDTO) {

        Tramite tramite =
                atenderSolicitudRevocacionService.buscarTramite(tramiteDTO.getNumeroAsunto(),
                        solicitudDTO.getIdSolicitud());
        TramiteDTO tramiteDTOCompleto = tramiteDTOTransformer.transformarDTO(tramite);
        Resolucion resolucionG =
                atenderSolicitudRevocacionService.obtenerResolucionIdTramite(tramiteDTO.getNumeroAsunto());
        ResolucionAbogadoDTO resolucionAbogadoDTO = new ResolucionAbogadoDTO();
        if (resolucionG != null && resolucionG.getIdResolucion() != null) {
            resolucionAbogadoDTO = resolucionDTOTransformer.transformarDTO(resolucionG);
        }

        resolucionAbogadoDTO.setSolicitudDTO(solicitudDTO);
        resolucionAbogadoDTO.setTramiteDTO(tramiteDTOCompleto);
        return resolucionAbogadoDTO;
    }

    /*
     * Obtiene informacion de la ultima seccion Tabs Promovente,
     * Promocion y Documentos Ajuntos
     */
    @Override
    public ResolucionAbogadoDTO consultarSolicitud(SolicitudDTO solicitud) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * Obtiene la resolucion cuando viene del boton atras o de la
     * bandeja, Promocion y Documentos Ajuntos
     */
    @Override
    public ResolucionAbogadoDTO mostrarResolucion(ResolucionAbogadoDTO resolucion) {
        // TODO Auto-generated method stub

        Resolucion resolucionG = atenderSolicitudRevocacionService.obtenerResolucion(resolucion.getIdResolucion());

        ResolucionAbogadoDTO resolucionAbogadoDTO = resolucionDTOTransformer.transformarDTO(resolucionG);

        return resolucionAbogadoDTO;
    }

    /*
     * Llama a service para Guardar resoluciones
     */
    @Override
    public void guardar(ResolucionAbogadoDTO resolucionAbogadoDTO) {
        Resolucion resolucion = resolucionDTOTransformer.transformarModelCopia(resolucionAbogadoDTO, true);
        atenderSolicitudRevocacionService.guardar(resolucion);

    }

    @Override
    public void autorizarSolicitud(ResolucionAbogadoDTO resolucionAbogadoDTO) {
        Resolucion resolucion = resolucionDTOTransformer.transformarModel(resolucionAbogadoDTO, false);
        ResolucionDatosGenerados resolucionDatosGenerados =
                resolucionDatosGeneralesDTOTransformer.transformarResolDatosGeneradosDTO(resolucion);
        atenderSolicitudRevocacionService.autorizar(resolucion, resolucionAbogadoDTO.getTramiteDTO().getNumeroAsunto());
        atenderSolicitudRevocacionService.guardarResolucionDatosGenerados(resolucionDatosGenerados);

    }

    /*
     * Llama a service para firmar atender resolucion
     */
    @Override
    public void firmar(ResolucionAbogadoDTO resolucionAbogadoDTO) {
        // Falta Transformer para convertir resolucionAbogadoDTO a
        // resolucion
        Resolucion resolucion = new Resolucion();
        // Falta Helper para pasar las resoluciones impugandas
        resolucion.setResolucionesImpugnadas(new ArrayList<ResolucionImpugnada>());
        atenderSolicitudRevocacionService.firmar(resolucion);
    }

    @Override
    public List<CatalogoDTO> obtenerCatalogoConcepto() {
        List<CatalogoD> listaCatalogo = catalogoDServices.obtenerCatalogoPorIdH(CatalogoConstantes.CATALOGO_CONCEPTO);

        return catalogoDTOTransformer.transformarCatalogoDDTO(listaCatalogo);
    }

    @Override
    public List<CatalogoDTO> obtenerCatalogoRecurso() {
        List<CatalogoD> listaCatalogo = catalogoDServices.obtenerCatalogoPorIdH(CatalogoConstantes.CATALOGO_PROCESO);

        return catalogoDTOTransformer.transformarCatalogoDDTO(listaCatalogo);
    }

    @Override
    public List<CatalogoDTO> obtenerCatalogoCaracteristicas() {
        List<CatalogoD> listaCatalogo =
                catalogoDServices.obtenerCatalogoPorIdH(CatalogoConstantes.CATALOGO_CARACTERISTICAS);

        return catalogoDTOTransformer.transformarCatalogoDDTO(listaCatalogo);
    }

    @Override
    public List<UnidadAdministrativaDTO> obtenerCatalogoUnidadAdministrativa() {
        List<UnidadAdministrativa> listaCatalogo = unidadAdministrativaServices.obtenerCatalogo();
        List<UnidadAdministrativaDTO> listaCatalogoDTO =
                unidadAdministrativaDTOTransformer.transformarDTO(listaCatalogo);
        return listaCatalogoDTO;
    }
    
    @Override
    public List<UnidadAdministrativaDTO> obtenerCatalogoUnidadAdministrativaVig() {
        List<UnidadAdministrativa> listaCatalogo = unidadAdministrativaServices.obtenerCatalogoVig();
        List<UnidadAdministrativaDTO> listaCatalogoDTO =
                unidadAdministrativaDTOTransformer.transformarDTO(listaCatalogo);
        return listaCatalogoDTO;
    }

    @Override
    public List<AgraviosDTO> obtenerCatalogoAgravio() {

        List<CatalogoD> listaCatalogo = catalogoDServices.obtenerCatalogoPorIdH(CatalogoConstantes.CATALOGO_AGRAVIO);
        List<AgraviosDTO> listaCatalogoDTO = new ArrayList<AgraviosDTO>();

        for (CatalogoD catalogoD : listaCatalogo) {
            listaCatalogoDTO.add(agraviosDTOTransformer.transformarDTO(catalogoD));
        }
        getLogger().debug("Lista agravios {}", listaCatalogoDTO);

        return listaCatalogoDTO;
    }

    @Override
    public List<CatalogoDTO> obtenerCatalogoSentido() {
        List<CatalogoD> listaCatalogo = catalogoDServices.obtenerCatalogoPorIdH(CatalogoConstantes.CATALOGO_SENTIDO);

        return catalogoDTOTransformer.transformarCatalogoDDTO(listaCatalogo);
    }
    
    /**
     * Obtiene el catalogo de sentidos perdedores
     * @return 
     */
    @Override
    public Map<String,CatalogoDTO> obtenerSentidosResolucionPerdedores(){
        Map<String, CatalogoDTO> sentidosPerdidos = new HashMap<String, CatalogoDTO>();
        List<CatalogoD> listaCatalogo = catalogoDServices.obtenerSentidosResolucionPerdedores(CatalogoConstantes.CATALOGO_SENTIDO, SentidosResolucion.PERDIDO.getClave());
        
        for(CatalogoD catalogoD : listaCatalogo){
            sentidosPerdidos.put(catalogoD.getClave(), new CatalogoDTO(catalogoD.getClave(), catalogoD.getDescripcionGenerica1()));
        }
        
        return sentidosPerdidos;
    }

    @Override
    public ResolucionImpugnadaDTO verificarExistencia(ResolucionImpugnadaDTO abogadoDTO) {
        ResolucionImpugnadaDTO dto = new ResolucionImpugnadaDTO();
        Tramite tramite =
                atenderSolicitudRevocacionService.obtenerTramite(abogadoDTO.getResImpugnada(),
                        abogadoDTO.getIdTramite());
        if (tramite != null && tramite.getNumeroAsunto() != null && !tramite.getNumeroAsunto().equals("")) {
            dto.setIdTramite(tramite.getNumeroAsunto());
            UnidadAdministrativa administrativa =
                    atenderSolicitudRevocacionService.obtenerUnidadAdministrativaBalanceo(tramite.getNumeroAsunto());
            if (administrativa != null && administrativa.getNombre() != null && !administrativa.getNombre().equals("")) {
                dto.setAutoridadEmisora(new UnidadAdministrativaDTO());
                dto.getAutoridadEmisora().setNombre(administrativa.getNombre());
            }
        }
        return dto;
    }

    public List<CatalogoDTO> obtenerAutorizadores(String numeroAsunto) {
        List<Persona> listaAutorizadores = atenderSolicitudRevocacionService.obtenerAutorizadores(numeroAsunto);
        return catalogoDTOTransformer.transformarPersonas(listaAutorizadores);
    }

    public boolean validaBanderaGuardar(String numFolioResolucion) {
        Resolucion resolucion = atenderSolicitudRevocacionService.obtenerResolucionIdTramite(numFolioResolucion);
        if (resolucion != null) {
            return resolucion.getIdResolucion() != null;
        }
        return false;
    }

    @Override
    public void actualizaDatosBP(int idInstancia, String numAsunto, String rfcUsuario) {
        tramiteServices.actualizaDatosBP(idInstancia, numAsunto, rfcUsuario);
    }

    @Override
    public List<DocumentoDTO> obtenerDocumentosComplementarios(Long idSolicitud) {

        return documentoDTOTransformer.tranformarDocSolicitud(atenderSolicitudRevocacionService
                .obtenerDocumentosComplementarios(idSolicitud));
    }

    @Override
    public String getIdeTareaOrigen() {
        return atenderSolicitudRevocacionService.getIdeTareaOrigen();
    }

    @Override
    public TramiteDTO validarMedioDefensaResolucion(ResolucionImpugnadaDTO resolucionDTO) {
        Tramite tramite =
                atenderSolicitudRevocacionService.obtenerTramiteParaMensajeAviso(resolucionDTO.getResImpugnada(),
                        resolucionDTO.getIdTramite());
        if (tramite != null) {
            return tramiteDTOTransformer.transformarDTO(tramite);
        }
        else {
            return null;
        }

    }

    /**
     * Metodo para obtener la descripcion de la observacion del
     * turnado por numero de asunto
     * 
     * @param numeroAsunto
     * @return String descObservacion
     */
    @Override
    public String obtenerObservacionTurnado(String numeroAsunto) {
        return turnarService.obtenerObservacionTurnado(numeroAsunto);
    }

}
