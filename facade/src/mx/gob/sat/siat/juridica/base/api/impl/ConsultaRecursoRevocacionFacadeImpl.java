package mx.gob.sat.siat.juridica.base.api.impl;

import mx.gob.sat.siat.juridica.base.api.ConsultaRecursoRevocacionFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoOficialDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.ObservacionDTOTransformer;
import mx.gob.sat.siat.juridica.base.service.*;
import mx.gob.sat.siat.juridica.base.util.constante.CatalogoConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.DatosRequerimientoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DatosRequerimientoDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DatosSolicitudDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.TramiteDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.service.RegistroRecursoRevocacionServices;
import mx.gob.sat.siat.juridica.rrl.util.constante.AnexarDocumentosConstantes;
import mx.gob.sat.siat.juridica.rrl.util.exception.FechaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("consultaRecursoRevocacionFacade")
public class ConsultaRecursoRevocacionFacadeImpl extends BaseCloudFacadeImpl
        implements ConsultaRecursoRevocacionFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -3278931251622648910L;

    @Autowired
    private transient ConsultaSolicitudServices consultaSolicitudServices;

    @Autowired
    private transient RequerimientoServices requerimientoServices;

    @Autowired
    private transient DatosSolicitudDTOTransformer solicitudDTOTransformer;

    @Autowired
    private transient DocumentoDTOTransformer documentoDTOTransformer;

    @Autowired
    private transient DatosRequerimientoDTOTransformer datosRequerimientoDTOTransformer;

    @Autowired
    private transient DocumentoOficialDTOTransformer documentoOficialDTOTransformer;

    @Autowired
    private transient TramiteServices tramiteServices;

    @Autowired
    private transient PersonaServices personaServices;

    @Autowired
    private transient RegistroRecursoRevocacionServices registroRecursoRevocacionServices;

    @Autowired
    private transient DocumentosServices documentosServices;

    @Autowired
    private transient CatalogoDServices catalogoService;

    @Autowired
    private transient TramiteDTOTransformer tramiteDTOTransformer;

    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;

    @Autowired
    private transient ObservacionServices observacionServices;

    @Autowired
    private transient ObservacionDTOTransformer observacionDTOTransformer;

    @Override
    public DatosSolicitudDTO buscarSolicitud(Long idSolicitud) {
        SolicitudDatosGenerales solicitud = consultaSolicitudServices.buscar(idSolicitud);
        DatosSolicitudDTO datosSolicitudDTO = solicitudDTOTransformer.transformarDTO(solicitud);
        Tramite tramite = tramiteServices.obtenerTramitePorIdSolicitud(idSolicitud);
        datosSolicitudDTO.setFechaFin(tramite != null ? tramite.getFechaCalculoTranscurridos() : new Date());
        return datosSolicitudDTO;
    }

    @Override
    public List<DocumentoOficialDTO> obtenerDocumentosOficiales(String numFolio) {
        List<DocumentoOficialDTO> listaDocumentoDTO = new ArrayList<DocumentoOficialDTO>();
        List<DocumentoOficial> listaDocumentoOficial = consultaSolicitudServices
                .obtenerDocumentosOficialesEmpleado(numFolio);
        for (DocumentoOficial documentoOficial : listaDocumentoOficial) {
            DocumentoOficialDTO doc = documentoDTOTransformer.transformarDocumentoOficial(documentoOficial);

            doc.setDescripcionTipoDocumento(
                    consultaSolicitudServices.obtenerDescripcionTipoDoc(doc.getCveTipoDocumento()));
            listaDocumentoDTO.add(doc);
        }
        return listaDocumentoDTO;
    }
    
    

    @Override
    public List<DocumentoOficialDTO> obtenerDocumentosOficialesPromovente(String numFolio, Long idRequerimiento) {
        List<DocumentoOficialDTO> listaDocumentoDTO = new ArrayList<DocumentoOficialDTO>();
        List<DocumentoOficial> listaDocumentoOficial = consultaSolicitudServices
                .obtenerDocumentosOficialesPromovente(numFolio, idRequerimiento);
        for (DocumentoOficial documentoOficial : listaDocumentoOficial) {
            DocumentoOficialDTO doc = documentoDTOTransformer.transformarDocumentoOficial(documentoOficial);

            doc.setDescripcionTipoDocumento(
                    consultaSolicitudServices.obtenerDescripcionTipoDoc(doc.getCveTipoDocumento()));
            listaDocumentoDTO.add(doc);
        }
        return listaDocumentoDTO;
    }

    @Override
    public DataPage obtenerDocumentosOficialesLazy(String numFoli) {
        DataPage dataPage = new DataPage();

        List<DocumentoOficialDTO> listaDocumentoDTO = new ArrayList<DocumentoOficialDTO>();
        List<DocumentoOficial> listaDocumentoOficial = consultaSolicitudServices
                .obtenerDocumentosOficialesEmpleado(numFoli);
        for (DocumentoOficial documentoOficial : listaDocumentoOficial) {
            DocumentoOficialDTO doc = documentoDTOTransformer.transformarDocumentoOficial(documentoOficial);

            doc.setDescripcionTipoDocumento(
                    consultaSolicitudServices.obtenerDescripcionTipoDoc(doc.getCveTipoDocumento()));
            listaDocumentoDTO.add(doc);
        }
        dataPage.setData(listaDocumentoDTO);
        return dataPage;
    }

    @Override
    public DataPage obtenerDocumentosRegistroLazy(String idsolicitud) {
        DataPage dataPage = new DataPage();
        ArrayList<DocumentoDTO> listaDocDTO = new ArrayList<DocumentoDTO>();

        List<Documento> listaDocumento = consultaSolicitudServices.obtenerDocumentoSolicitud(idsolicitud);

        List<TipoDocumento> listaTipoDocumento = consultaSolicitudServices.tiposDocumentos();

        for (Documento documento : listaDocumento) {
            if (documento != null) {
                DocumentoDTO documentoDTO = documentoDTOTransformer.transformarDocumentoDTO(documento);

                for (TipoDocumento tipoDocumento : listaTipoDocumento) {
                    if (tipoDocumento.getIdTipoDocumento().equals(documento.getIdTipoDocumento())) {
                        documentoDTO.setTipoDocumento(tipoDocumento.getNombre());
                    }
                }
                listaDocDTO.add(documentoDTO);
            }
        }

        dataPage.setData(listaDocDTO);
        return dataPage;
    }

    @Override
    public List<DocumentoDTO> obtenerTiposDocumentos(String idTipoTramite, String numAsunto) {
        List<DocumentoDTO> listaDTO = new ArrayList<DocumentoDTO>();

        List<DocumentoTramite> listaDocumentos = consultaSolicitudServices.obtenerDocumentosTramite(idTipoTramite,
                numAsunto);
        if (listaDocumentos != null) {
            for (DocumentoTramite doc : listaDocumentos) {
                DocumentoDTO nuevoDocumento = documentoDTOTransformer.transformarDTO(doc);
                listaDTO.add(nuevoDocumento);
            }
            return listaDTO;
        } else {
            return null;
        }
    }

    @Override
    public List<DocumentoDTO> obtenerDocumentosRegistro(String idSol) {
        List<DocumentoDTO> listaDocDTO = new ArrayList<DocumentoDTO>();
        List<Documento> listaDocumento = consultaSolicitudServices.obtenerDocumentoSolicitud(idSol);
        List<TipoDocumento> listaTipoDocumento = consultaSolicitudServices.tiposDocumentos();
        for (Documento documento : listaDocumento) {
            if (documento != null) {
                DocumentoDTO documentoDTO = documentoDTOTransformer.transformarDocumentoDTO(documento);

                for (TipoDocumento tipoDocumento : listaTipoDocumento) {
                    if (tipoDocumento.getIdTipoDocumento().equals(documento.getIdTipoDocumento())) {
                        documentoDTO.setTipoDocumento(tipoDocumento.getNombre());
                    }
                }
                listaDocDTO.add(documentoDTO);
            }
        }
        return listaDocDTO;
    }

    @Override
    public DocumentoDTO obtenerDocumentoSolicitudTipoDoc(String idSol, Integer idTipoDocumento) {
        DocumentoDTO objDocDTO = null;
        Documento objDocumento = consultaSolicitudServices.obtenerDocumentoSolicitudTipoDoc(idSol, idTipoDocumento);
        if (objDocumento != null) {
            objDocDTO = documentoDTOTransformer.transformarDocumentoDTO(objDocumento);
        }
        return objDocDTO;
    }

    @Override
    public InputStream descargarDocumento(DocumentoDTO documento) {

        return consultaSolicitudServices.descargarDocumento(documento.getRuta());
    }

    @Override
    public List<DatosRequerimientoDTO> obtenerRequerimientos(String numFolio) {
        List<DatosRequerimientoDTO> listaRequerimeintosDTO = new ArrayList<DatosRequerimientoDTO>();
        List<Requerimiento> listaRequerimientos = consultaSolicitudServices.obteneRequerimientosPorAsunto(numFolio);
        for (Requerimiento requerimiento : listaRequerimientos) {
            DatosRequerimientoDTO datosRequerimientoDTO = datosRequerimientoDTOTransformer
                    .transformarDTO(requerimiento);
            Persona abogadoReq = personaServices.obtenerPersonaPorRFC(datosRequerimientoDTO.getIdUsuario());
            StringBuilder nomAbogado = new StringBuilder();
            nomAbogado.append(abogadoReq.getNombre());
            nomAbogado.append(" ");
            nomAbogado.append(abogadoReq.getApellidoPaterno());
            nomAbogado.append(" ");
            nomAbogado.append(abogadoReq.getApellidoMaterno());
            datosRequerimientoDTO.setNombreAbogado(nomAbogado.toString());
            if (requerimiento.getTipoRequerimiento().getClave().equals(TipoRequerimiento.CONTRIBUYENTE.getClave())
                    || requerimiento.getTipoRequerimiento().getClave()
                            .equals(TipoRequerimiento.PROMOVENTE_CAL.getClave())) {
                NotificacionRequerimiento notificacion = consultaSolicitudServices
                        .obtenerNotificacionPorIdRequerimiento(requerimiento.getIdRequerimiento());
                if (notificacion != null) {
                    datosRequerimientoDTO.setFechaNotificacion(notificacion.getFechaEnvioNotificacion());
                }
                List<DocumentoDTO> listaDocsDTO = new ArrayList<DocumentoDTO>();
                List<Documento> listaDocs = consultaSolicitudServices
                        .obtenerDocumentoAnexadosRequerimiento(requerimiento.getIdRequerimiento());
                for (Documento documento : listaDocs) {
                    DocumentoDTO doc = documentoDTOTransformer.transformarDocumentoDTO(documento);

                    doc.setTipoDocumento(AnexarDocumentosConstantes.DESC_TIPO_DOCUMENTO_REQUERIMIENTO);
                    listaDocsDTO.add(doc);
                }
                datosRequerimientoDTO.setDocumentosConsultaReq(listaDocsDTO);

            } else {
                List<DocumentoDTO> listaDocsDTO = new ArrayList<DocumentoDTO>();
                List<DocumentoOficial> listaDocs = requerimientoServices
                        .buscarTiposDocumentosOfiAtencionRetroPorIdTramiteIdReq(numFolio,
                                datosRequerimientoDTO.getIdRequerimiento());
                for (DocumentoOficial documento : listaDocs) {
                    DocumentoDTO doc = documentoDTOTransformer.transformarDocOfiADocDTO(documento);

                    doc.setTipoDocumento(AnexarDocumentosConstantes.DESC_TIPO_DOCUMENTO_REQUERIMIENTO);
                    listaDocsDTO.add(doc);
                    datosRequerimientoDTO.setDocumentosConsultaReq(listaDocsDTO);
                }

            }
            Persona persona = consultaSolicitudServices
                    .obtenerAutorizadorPorIdRequerimiento(requerimiento.getIdRequerimiento());

            if (persona != null) {
                StringBuffer autorizadoPor = new StringBuffer(persona.getNombre() != null ? persona.getNombre() : "");
                autorizadoPor.append(" ");
                autorizadoPor.append(persona.getApellidoPaterno() != null ? persona.getApellidoPaterno() : "");
                autorizadoPor.append(" ");
                autorizadoPor.append(persona.getApellidoMaterno() != null ? persona.getApellidoMaterno() : "");

                datosRequerimientoDTO.setAutorizadoPor(autorizadoPor.toString());
            }

            Persona abogado = consultaSolicitudServices.obtenerAbogadoPorIdTramite(numFolio);
            if (abogado != null) {
                StringBuffer generadoPor = new StringBuffer(abogado.getNombre() != null ? abogado.getNombre() : "");
                generadoPor.append(" ");
                generadoPor.append(abogado.getApellidoPaterno() != null ? abogado.getApellidoPaterno() : "");
                generadoPor.append(" ");
                generadoPor.append(abogado.getApellidoMaterno() != null ? abogado.getApellidoMaterno() : "");

                datosRequerimientoDTO.setGeneradoPor(generadoPor.toString());
            }
            listaRequerimeintosDTO.add(datosRequerimientoDTO);
        }
        return listaRequerimeintosDTO;
    }

    public void actualizarRequerimiento(DatosRequerimientoDTO datosRequerimientoDTO) throws FechaInvalidaException {
        Requerimiento requerimiento = datosRequerimientoDTOTransformer.transformarModel(datosRequerimientoDTO);
        requerimientoServices.actualizarRequerimiento(requerimiento);
    }

    @Override
    public void eliminaDocumento(long idDocumento) {
        registroRecursoRevocacionServices.eliminaDocumentoOficial(idDocumento);
    }

    /**
     * M&eacute;todo para guardar los documentos asociadas al requerimiento
     * (autorizaci&oacute;n).
     */
    @Override
    public void guardarDocumentos(List<DocumentoOficialDTO> documentos, String numAsunto, Long idRequerimiento) {
        List<DocumentoOficial> listaDocumentoOficial = documentoOficialDTOTransformer
                .transdormarModelDocumento(documentos, numAsunto);
        requerimientoServices.guardaDocumentosOficiales(listaDocumentoOficial, idRequerimiento, numAsunto);
    }

    @Override
    public void eliminaDocumentosNoFirmados(long idSolicitud) {
        documentosServices.eliminaDocumentoNoFirmadosSol(idSolicitud);
    }

    @Override
    public TramiteDTO obtenerTramitePorId(String numFolio) {
        Tramite tramite = tramiteServices.buscarTramite(numFolio, null);
        return tramiteDTOTransformer.transformarDTO(tramite);
    }

    @Override
    public List<CatalogoDTO> obtenerMotivosRechazo() {
        return catalogoDTOTransformer
                .transformaCatalogoDDTO(catalogoService.obtenerCatalogoPorIdH(CatalogoConstantes.CATALOGO_RECHAZO));
    }

    @Override
    public List<ObservacionDTO> obtenerObservacionesPorTramite(String numAsunto) {
        List<Observacion> obs = observacionServices.obtenerObservacionesPorTramite(numAsunto);
        List<ObservacionDTO> observacionesDTO = new ArrayList<ObservacionDTO>();
        for (Observacion ob : obs) {
            ObservacionDTO observacionDTO = observacionDTOTransformer.transformarDTO(ob);
            observacionesDTO.add(observacionDTO);
        }

        return observacionesDTO;
    }

    @Override
    public List<DocumentoOficialDTO> obtenerDocOficialesGenerados(String numAsunto) {
        List<DocumentoOficialDTO> docsOfDto = new ArrayList<DocumentoOficialDTO>();
        List<DocumentoOficial> docsOf = registroRecursoRevocacionServices.obtenerDocOficialesGenerados(numAsunto);
        if (docsOf != null && !docsOf.isEmpty()) {
            for (DocumentoOficial dc : docsOf) {
                DocumentoOficialDTO docDto = documentoOficialDTOTransformer.transformarDTO(dc);
                docsOfDto.add(docDto);
            }
        }
        return docsOfDto;
    }

	@Override
	public List<DocumentoOficialDTO> obtenerDocumentoOficialesTipo(String idTipoTramite,
			String idTipoDocumentoOficial) {
		List<DocumentoOficialDTO> docsOfDto = new ArrayList<DocumentoOficialDTO>();
		 List<DocumentoOficial> documentoOficials = documentosServices.obtenerDocumentosOficialesTipo(idTipoTramite, idTipoDocumentoOficial);
		for(DocumentoOficial documentoOficial: documentoOficials) {
			DocumentoOficialDTO documentoOficialDTO = documentoOficialDTOTransformer.transformarDTO(documentoOficial);
			docsOfDto.add(documentoOficialDTO);
		}
		return docsOfDto;
	}

}
