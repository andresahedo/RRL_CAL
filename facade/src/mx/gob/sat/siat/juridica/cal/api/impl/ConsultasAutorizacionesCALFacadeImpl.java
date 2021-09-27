/**
 * 
 */
package mx.gob.sat.siat.juridica.cal.api.impl;

import mx.gob.sat.siat.juridica.base.api.impl.ConsultaRecursoRevocacionFacadeImpl;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoAutoridad;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoDTOTransformer;
import mx.gob.sat.siat.juridica.base.service.ConsultaSolicitudServices;
import mx.gob.sat.siat.juridica.base.service.SolicitanteServices;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.base.service.UnidadAdministrativaServices;
import mx.gob.sat.siat.juridica.base.util.constante.EnumeracionConstantes;
import mx.gob.sat.siat.juridica.ca.dao.domain.model.SolicitudConsultaAutorizacion;
import mx.gob.sat.siat.juridica.ca.service.MedioDefensaService;
import mx.gob.sat.siat.juridica.ca.service.PersonaSolicitudServices;
import mx.gob.sat.siat.juridica.ca.util.constants.RegistroSolicitudConstants;
import mx.gob.sat.siat.juridica.cal.api.ConsultasAutorizacionesCALFacade;
import mx.gob.sat.siat.juridica.cal.dto.ManifiestoDTO;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.cal.dto.transformer.ManifiestoDTOTransformer;
import mx.gob.sat.siat.juridica.cal.dto.transformer.SolicitudCALDTOTransformer;
import mx.gob.sat.siat.juridica.cal.service.FraccionDudaService;
import mx.gob.sat.siat.juridica.cal.service.ManifiestoService;
import mx.gob.sat.siat.juridica.cal.service.RegistroSolicitudCALServices;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DatosSolicitudDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.TramiteDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author edgar.mendoza
 * 
 */
@Component("consultasAutorizacionesCALFacade")
public class ConsultasAutorizacionesCALFacadeImpl extends ConsultaRecursoRevocacionFacadeImpl
        implements ConsultasAutorizacionesCALFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -7145088847701561182L;

    @Autowired
    private transient SolicitudService solicitudService;

    @Autowired
    private transient ConsultaSolicitudServices consultaSolicitudServices;

    @Autowired
    private transient MedioDefensaService medioDefensaService;

    @Autowired
    private transient PersonaSolicitudServices personaSolicitudServices;

    @Autowired
    private transient SolicitanteServices solicitanteServices;

    @Autowired
    private transient RegistroSolicitudCALServices registroSolicitudCALServices;

    @Autowired
    private transient TramiteServices tramiteServices;

    @Autowired
    private transient FraccionDudaService fraccionDudaService;

    @Autowired
    private transient UnidadAdministrativaServices unidadAdministrativaServices;

    @Autowired
    private transient ManifiestoService manifiestoService;

    @Autowired
    private transient SolicitudCALDTOTransformer solicitudCALDTOTransformer;

    @Autowired
    private transient DatosSolicitudDTOTransformer datosSolicitudDTOTransformer;

    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;

    @Autowired
    private transient TramiteDTOTransformer tramiteDTOTransformer;

    @Autowired
    private transient DocumentoDTOTransformer documentoDTOTransformer;

    @Autowired
    private transient ManifiestoDTOTransformer manifiestoDTOTransformer;

    public SolicitudCALDTO obtenerDatosSolicitud(Long idSolicitud) {
        SolicitudCALDTO solicitudCALDTO = new SolicitudCALDTO();
        Solicitud solicitud = consultaSolicitudServices.obtenerSolicitudporId(idSolicitud);
        if (solicitud instanceof SolicitudConsultaAutorizacion) {
            MedioDefensa medioDefensa = medioDefensaService.obtenerMedioDefensaById(idSolicitud);
            // Sentidos de resolucion para seccion medios de defensa

            Autoridad autoridadDeHechos = registroSolicitudCALServices.obtenerAutoridad(idSolicitud,
                    TipoAutoridad.AUTORIDAD_HCH);
            Autoridad autoridadSujetoEjercicio = registroSolicitudCALServices.obtenerAutoridad(idSolicitud,
                    TipoAutoridad.AUTORIDAD_SUJETO);
            List<PersonaSolicitud> listaPersonaSolicitud = personaSolicitudServices
                    .obtenerPersonasSolicitudByIdSol(idSolicitud);
            List<FraccionDuda> fraccionesDuda = fraccionDudaService.obtenerFraccionesDudaBySolicitudId(idSolicitud);

            List<EstadoManifiesto> manifiestos = ((SolicitudConsultaAutorizacion) solicitud).getEstadosManifiesto();
            List<ManifiestoDTO> manifiestosDTO = new ArrayList<ManifiestoDTO>(manifiestos.size());

            for (EstadoManifiesto estadoManifiesto : manifiestos) {
                if (estadoManifiesto.getManifiesto() == null) {
                    estadoManifiesto.setManifiesto(manifiestoService
                            .obtenerManifiestoPorId(estadoManifiesto.getEstadoManifiestoPK().getIdManifiesto()));
                }
                ManifiestoDTO manifiestoDTO = manifiestoDTOTransformer.transformarDTO(estadoManifiesto.getManifiesto());
                manifiestoDTO.setAceptado(estadoManifiesto.getAceptado());
                manifiestosDTO.add(manifiestoDTO);
            }

            solicitudCALDTO = solicitudCALDTOTransformer.transformaSolicitud((SolicitudConsultaAutorizacion) solicitud,
                    medioDefensa, listaPersonaSolicitud, fraccionesDuda, autoridadDeHechos, autoridadSujetoEjercicio);

            solicitudCALDTO.setManifiestos(manifiestosDTO);
        }

        return solicitudCALDTO;
    }

    public DatosSolicitudDTO obtenerDatosSolicitante(Long idSolicitud) {
        Solicitante solicitante = solicitanteServices.obtenerSolicitanteByIdSol(idSolicitud);

        return datosSolicitudDTOTransformer.transformarSolicitanteDTO(solicitante);
    }

    public TramiteDTO obtenerDatosTramite(Long idSolicitud) {
        Tramite tramite = tramiteServices.buscarTramite(null, idSolicitud);
        TramiteDTO tramiteDTO = null;
        if (tramite != null) {
            tramiteDTO = tramiteDTOTransformer.transformarDTO(tramite);
        }
        return tramiteDTO;
    }

    /**
     * M&eacute;todo para obtener lista comun de desici&oacute;n SI o NO.
     */
    public List<CatalogoDTO> obtenerListaSiNo() {
        List<CatalogoDTO> listaCatalogo = catalogoDTOTransformer
                .transformarCatalogo(registroSolicitudCALServices.obtenerListaSiNo());
        return listaCatalogo;
    }

    /**
     * M&eacute;todo para obtener las autorizades emisoras.
     */
    public List<CatalogoDTO> obtenerAutoridadesEmisoras() {
        List<CatalogoDTO> listaCatalogo = catalogoDTOTransformer
                .transformarDTO(registroSolicitudCALServices.obtenerAutoridadesEmisoras());
        return listaCatalogo;
    }

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     */
    public List<CatalogoDTO> obtenerMediosDeDefensa() {
        List<EnumeracionTr> listaEnumeraciones = registroSolicitudCALServices
                .obtenerEnumeracionPorId(EnumeracionConstantes.TIPOS_MEDIOS_DEFENSA);
        return catalogoDTOTransformer.transformarEnumeracionTrDTO(listaEnumeraciones);
    }

    /**
     * M&eacute;todo para obtener lista de sentidos de resolcui&oacute;n.
     */
    public List<CatalogoDTO> obtenerSentidosResolucion() {
        List<EnumeracionTr> listaEnumeraciones = registroSolicitudCALServices
                .obtenerEnumeracionPorId(EnumeracionConstantes.SENTIDOS_RESOLCUION);
        return catalogoDTOTransformer.transformarEnumeracionTrDTO(listaEnumeraciones);
    }

    /**
     * M&eacute;todo para obtener lista de tipos de clasificaci&oacute;n arancelaria
     */
    public List<CatalogoDTO> obtenerListaTipoClasificacion() {
        List<CatalogoDTO> listaCatalogo = catalogoDTOTransformer
                .transformarEnumeracionTrDTO(registroSolicitudCALServices.obtenerTipoClasificacion());
        return listaCatalogo;
    }

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     */
    public List<CatalogoDTO> obtenerCatalogoDPorCodGen1(String codGen1) {
        List<CatalogoD> listaEnumeraciones = registroSolicitudCALServices.obtenerCatalogoDCodGen1(codGen1);
        return catalogoDTOTransformer.transformaCatalogoDDTO(listaEnumeraciones);
    }

    public String obtenerDescripcionModalidad(String idTipoTramite) {
        String descModalidad = null;
        if (idTipoTramite != null) {
            try {
                List<TipoTramite> tiposTramite = tramiteServices
                        .obtenerTiposDeTramitesPorFiltros(Integer.valueOf(idTipoTramite), null);
                if (tiposTramite != null && !tiposTramite.isEmpty()) {
                    descModalidad = tiposTramite.get(0).getDescripcionModalidad();
                }
            } catch (NumberFormatException nfe) {
                getLogger().error("", nfe);
            }
        }

        return descModalidad;
    }

    public String obtenerNombreAdministracion(Long idSolicitud) {
        String nombreAdministracion = null;
        Solicitud solicitud = consultaSolicitudServices.obtenerSolicitudporId(idSolicitud);
        if (solicitud instanceof SolicitudConsultaAutorizacion) {
            UnidadAdministrativa ua = unidadAdministrativaServices
                    .obtenerUnidadPorId(((SolicitudConsultaAutorizacion) solicitud).getUnidadAdminBalanceo());
            nombreAdministracion = ua != null ? ua.getNombre() : "";
        }

        return nombreAdministracion;
    }

    public DataPage obtenerDocumentosRegistroObligatoriosLazy(String idSol) {
        return construyeListaDocumentosRegistroLazy(idSol, RegistroSolicitudConstants.DOCUMENTOS_OBLIGATORIOS);
    }

    public DataPage obtenerDocumentosRegistroOpcionalesLazy(String idSol) {
        return construyeListaDocumentosRegistroLazy(idSol, RegistroSolicitudConstants.DOCUMENTOS_OPCIONALES);
    }

    /**
     * Metodo que obtiene los documentos obligatorios correspondiente al idSol y los
     * devuelve en una lista DocumentoDTO.
     * 
     * @param idSol
     * @return
     */
    public List<DocumentoDTO> obtenerDocumentosRegistroObligatorios(String idSol) {
        return construyeListaDocumentosRegistro(idSol, RegistroSolicitudConstants.DOCUMENTOS_OBLIGATORIOS);
    }

    /**
     * Metodo que obtiene los documentos opcionales correspondiente al idSol y los
     * devuelve en una lista DocumentoDTO.
     * 
     * @param idSol
     * @return
     */
    public List<DocumentoDTO> obtenerDocumentosRegistroOpcionales(String idSol) {
        return construyeListaDocumentosRegistro(idSol, RegistroSolicitudConstants.DOCUMENTOS_OPCIONALES);
    }

    /**
     * Metodo que obtiene los documentos opcionales u obligatorios correspondiente
     * al idSol y los devuelve en una lista DocumentoDTO.
     * 
     * @param idSol
     * @param opcional Indica si busca los documentos de registro opcionales u
     *                 obligatorios
     * @return
     */
    private List<DocumentoDTO> construyeListaDocumentosRegistro(String idSol, Integer opcional) {
        List<DocumentoDTO> listaDocDTO = new ArrayList<DocumentoDTO>();
        List<DocumentoDTO> listaDocumentosRegistro = super.obtenerDocumentosRegistro(idSol);
        Solicitud sol = solicitudService.obtenerSolicitudporId(Long.parseLong(idSol));
        Integer tipoTramite = 0;
        if (sol != null) {
            tipoTramite = sol.getTipoTramiteSolicitud().getIdTipoTramite();
        }
        List<DocumentoDTO> listaDocumentosObligatorios = obtenerdocumentosTramite(tipoTramite, opcional);

        for (DocumentoDTO documento : listaDocumentosRegistro) {
            for (DocumentoDTO documentoObligatorio : listaDocumentosObligatorios) {
                if (documento.getIdTipoDocumento().equals(documentoObligatorio.getIdTipoDocumento())) {
                    listaDocDTO.add(documento);
                }
            }
        }

        return listaDocDTO;
    }

    private List<DocumentoDTO> obtenerdocumentosTramite(Integer idTipoTramite, Integer opcional) {
        List<DocumentoDTO> listaDocumentos = new ArrayList<DocumentoDTO>();
        List<DocumentoTramite> listaDocumentoTramite = registroSolicitudCALServices
                .obtenerTiposDocumentosPorTramite(idTipoTramite, opcional);
        for (DocumentoTramite documentoTramite : listaDocumentoTramite) {
            DocumentoDTO documento = documentoDTOTransformer.transformarDTO(documentoTramite);
            listaDocumentos.add(documento);
        }
        return listaDocumentos;
    }

    @Override
    public List<ManifiestoDTO> obtenerManifiestosPorModalidad(Integer modalidad) {
        List<ManifiestoDTO> manifiestosDTO = new ArrayList<ManifiestoDTO>();
        List<Manifiesto> manifiestos = manifiestoService.obtenerManifiestosPorIdModalidad(modalidad);

        for (Manifiesto manifiesto : manifiestos) {
            manifiestosDTO.add(manifiestoDTOTransformer.transformarDTO(manifiesto));
        }

        return manifiestosDTO;
    }

    private DataPage construyeListaDocumentosRegistroLazy(String idSol, Integer opcional) {
        DataPage dataPage = new DataPage();
        List<DocumentoDTO> listaDocDTO = new ArrayList<DocumentoDTO>();
        List<DocumentoDTO> listaDocumentosRegistro = super.obtenerDocumentosRegistro(idSol);
        Solicitud sol = solicitudService.obtenerSolicitudporId(Long.parseLong(idSol));
        Integer tipoTramite = 0;
        if (sol != null) {
            tipoTramite = sol.getTipoTramiteSolicitud().getIdTipoTramite();
        }
        List<DocumentoDTO> listaDocumentosObligatorios = obtenerdocumentosTramite(tipoTramite, opcional);

        for (DocumentoDTO documento : listaDocumentosRegistro) {
            for (DocumentoDTO documentoObligatorio : listaDocumentosObligatorios) {
                if (documento.getIdTipoDocumento().equals(documentoObligatorio.getIdTipoDocumento())) {
                    listaDocDTO.add(documento);
                }
            }
        }
        dataPage.setData(listaDocDTO);
        return dataPage;
    }

	@Override
	public SolicitudCALDTO obtenerSolicitudPorId(Long idSolicitud) {
		SolicitudCALDTO solicitudCALDTO = new SolicitudCALDTO();
        Solicitud solicitud = consultaSolicitudServices.obtenerSolicitudporId(idSolicitud);
        if(solicitud != null) {
        	solicitudCALDTO.setIdSolicitud(solicitud.getIdSolicitud());
        	solicitudCALDTO.setTipoTramite(solicitud.getTipoTramite().getModalidad());
        }
		return solicitudCALDTO;
	}

}
