package mx.gob.sat.siat.juridica.base.api.impl;

import mx.gob.sat.siat.juridica.base.api.AnexarDocumentosFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Documento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoSolicitud;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoOficialDTOTransformer;
import mx.gob.sat.siat.juridica.base.service.ConsultaSolicitudServices;
import mx.gob.sat.siat.juridica.base.service.DocumentosServices;
import mx.gob.sat.siat.juridica.ca.util.constants.RegistroSolicitudConstants;
import mx.gob.sat.siat.juridica.rrl.service.AutorizarRemitirService;
import mx.gob.sat.siat.juridica.rrl.service.RegistroRecursoRevocacionServices;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("anexarDocumentosFacade")
public class AnexarDocumentosFacadeImpl extends BaseCloudFacadeImpl implements AnexarDocumentosFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 4150431598995038402L;

    @Autowired
    private transient RegistroRecursoRevocacionServices registroRecursoRevocacionServices;

    @Autowired
    private transient DocumentoDTOTransformer documentoDTOTransformer;

    @Autowired
    private transient DocumentoOficialDTOTransformer documentoOficialDTOTransformer;

    @Autowired
    private transient AutorizarRemitirService autorizarRemitirService;

    @Autowired
    private transient SolicitudService solicitudService;

    @Autowired
    private transient ConsultaSolicitudServices consultaSolicitudServices;

    @Autowired
    private transient DocumentosServices documentosServices;

    @Override
    public DocumentoDTO adjuntarDocumento(DocumentoDTO documento) throws ArchivoNoGuardadoException {

        documento.setFechaRecepcion(registroRecursoRevocacionServices.generarFecha());
        return documento;
    }

    @Override
    public List<DocumentoOficialDTO> obtenerDocumentos(String numAsunto) {
        List<DocumentoOficialDTO> listaDocsDTO = new ArrayList<DocumentoOficialDTO>();
        List<DocumentoOficial> listaDocs = registroRecursoRevocacionServices.obtenerDocumentosOficiales(numAsunto);
        for (DocumentoOficial documentoOficial : listaDocs) {
            DocumentoOficialDTO doc = documentoOficialDTOTransformer.transformarDTO(documentoOficial);
            if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.ACUSE_NOTIFICACION.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.ACUSE_NOTIFICACION.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.ACUSE_RECEPCION_TRAMITE.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.ACUSE_RECEPCION_TRAMITE.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.ACUSE_RECEPCION_PROMOCION.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.ACUSE_RECEPCION_PROMOCION.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.AVISE_NOTIFICACION_REQUERIMIENTO.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.AVISE_NOTIFICACION_REQUERIMIENTO.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.OFICIO_RESOLUCION.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.OFICIO_RESOLUCION.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.OFICIO_REQUERIMIENTO.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.OFICIO_REQUERIMIENTO.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.CONSTANCIA_RECEPCION_DOCUMENTO.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.CONSTANCIA_RECEPCION_DOCUMENTO.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.OFICIO_REMISION.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.OFICIO_REMISION.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.FORMATO_SOLICITUD_PROMOCION.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.FORMATO_SOLICITUD_PROMOCION.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.OFICIO_TERMINOS_CONDICIONES.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.OFICIO_TERMINOS_CONDICIONES.getDescripcion());
            }

            listaDocsDTO.add(doc);
        }
        return listaDocsDTO;
    }

    @Override
    public List<DocumentoOficialDTO> obtenerDocumentosByIdDoc(List<Long> idsDoc) {
        List<DocumentoOficialDTO> listaDocsDTO = new ArrayList<DocumentoOficialDTO>();
        List<DocumentoOficial> listaDocs = registroRecursoRevocacionServices.obtenerDocumentosOficialesById(idsDoc);
        for (DocumentoOficial documentoOficial : listaDocs) {
            DocumentoOficialDTO doc = documentoOficialDTOTransformer.transformarDTO(documentoOficial);
            if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.ACUSE_NOTIFICACION.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.ACUSE_NOTIFICACION.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.ACUSE_RECEPCION_TRAMITE.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.ACUSE_RECEPCION_TRAMITE.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.ACUSE_RECEPCION_PROMOCION.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.ACUSE_RECEPCION_PROMOCION.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.AVISE_NOTIFICACION_REQUERIMIENTO.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.AVISE_NOTIFICACION_REQUERIMIENTO.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.OFICIO_RESOLUCION.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.OFICIO_RESOLUCION.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.OFICIO_REQUERIMIENTO.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.OFICIO_REQUERIMIENTO.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.CONSTANCIA_RECEPCION_DOCUMENTO.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.CONSTANCIA_RECEPCION_DOCUMENTO.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.OFICIO_REMISION.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.OFICIO_REMISION.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.FORMATO_SOLICITUD_PROMOCION.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.FORMATO_SOLICITUD_PROMOCION.getDescripcion());
            }
            else if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.OFICIO_TERMINOS_CONDICIONES.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.OFICIO_TERMINOS_CONDICIONES.getDescripcion());
            }

            listaDocsDTO.add(doc);
        }
        return listaDocsDTO;
    }

    @Override
    public DocumentoOficialDTO adjuntarDocumentoOficial(DocumentoOficialDTO documento,
            List<DocumentoOficialDTO> listaDocumentos, String numAsunto) throws ArchivoNoGuardadoException {
        List<DocumentoOficial> listDocumentos =
                documentoOficialDTOTransformer.transdormarModelDocumento(listaDocumentos, numAsunto);
        if (listDocumentos != null) {
            for (DocumentoOficial doc : listDocumentos) {
                if (doc.getDescripcionDocumento().equals(documento.getNombreArchivo())) {
                    throw new ArchivoNoGuardadoException(RegistroSolicitudConstants.DOCUMENTO_DUPLICADO);
                }
            }
        }

        documento.setFechaCreacion(registroRecursoRevocacionServices.generarFecha());
        return documento;
    }

    @Override
    public String generarCadenaOriginal(long idSolicitud, Date fechaFirma) {
        return solicitudService.generarCadenaOriginalDocumentosAdicionales(idSolicitud, fechaFirma);
    }

    @Override
    public InputStream descargarDocumento(DocumentoDTO documento) {

        return registroRecursoRevocacionServices.descargarDocumento(documento.getRuta());
    }

    @Override
    public InputStream descargarDocumentoOficial(DocumentoOficialDTO documento) {

        return registroRecursoRevocacionServices.descargarDocumento(documento.getRuta());
    }

    @Override
    public void guardarDocumentosSolicitud(long idSolicitud, List<DocumentoDTO> documentos, String tipoTramite,
            String rfc) throws ArchivoNoGuardadoException {
        List<Documento> listaDocumento =
                documentoDTOTransformer.transdormarModelDocumento(documentos,
                        registroRecursoRevocacionServices.obtenerPersonaPorRFC(rfc));
        registroRecursoRevocacionServices.validarDocumentos(tipoTramite, listaDocumento);
        List<DocumentoSolicitud> listaDocumentoSol =
                documentoDTOTransformer.transformarModelDocumentoSolicitud(idSolicitud,
                        registroRecursoRevocacionServices.obtenerPersonaPorRFC(rfc), listaDocumento);
        registroRecursoRevocacionServices.guardaDocumento(listaDocumento, listaDocumentoSol);
    }

    @Override
    public void guardarDocumentosConsulta(long idSolicitud, List<DocumentoDTO> documentos, String tipoTramite,
            String rfc) throws ArchivoNoGuardadoException {
        List<Documento> listaDocumento =
                documentoDTOTransformer.transdormarModelDocumento(documentos,
                        registroRecursoRevocacionServices.obtenerPersonaPorRFC(rfc));
        List<DocumentoSolicitud> listaDocumentoSol =
                documentoDTOTransformer.transformarModelDocumentoSolicitud(idSolicitud,
                        registroRecursoRevocacionServices.obtenerPersonaPorRFC(rfc), listaDocumento);
        registroRecursoRevocacionServices.guardaDocumento(listaDocumento, listaDocumentoSol);
    }

    @Override
    public void guardarDocumentosOficiales(String numFolio, List<DocumentoOficialDTO> documentos)
            throws ArchivoNoGuardadoException {
        List<DocumentoOficial> documentosOficiales = new ArrayList<DocumentoOficial>();
        for (DocumentoOficialDTO documentoOficialDTO : documentos) {
            DocumentoOficial doc = documentoOficialDTOTransformer.transformarModel(documentoOficialDTO);
            documentosOficiales.add(doc);
        }
        autorizarRemitirService.guardar(numFolio, documentosOficiales);
    }
    
    @Override
    public void guardarDocumentosOficialesExlusivoFondo(String numFolio, List<DocumentoOficialDTO> documentos)
            throws ArchivoNoGuardadoException {
        List<DocumentoOficial> documentosOficiales = new ArrayList<DocumentoOficial>();
        for (DocumentoOficialDTO documentoOficialDTO : documentos) {
            DocumentoOficial doc = documentoOficialDTOTransformer.transformarModel(documentoOficialDTO);
            documentosOficiales.add(doc);
        }
        autorizarRemitirService.guardarExclusivoFondo(numFolio, documentosOficiales);
    }
    
    
    @Override
    public List<DocumentoOficialDTO> obtenerDocumentosOficialesAnexadosExclusivoFondo(String numAsunto) {
        List<DocumentoOficialDTO> listaDocsDTO = new ArrayList<DocumentoOficialDTO>();
        List<DocumentoOficial> listaDocs =autorizarRemitirService.obtenerDocumentosOficialesAnexadosExclusivoFondo(numAsunto);
        for (DocumentoOficial documentoOficial : listaDocs) {
            DocumentoOficialDTO doc = documentoOficialDTOTransformer.transformarDTO(documentoOficial);
            doc.setEditarDocumSelect(false);
            if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.OFICIO_ADMISION_CON_AUDIENCIA.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.OFICIO_ADMISION_CON_AUDIENCIA.getDescripcion());
            } else if(doc.getCveTipoDocumento().equals(TipoDocumentoOficial.OFICIO_ADMISION_SIN_AUDIENCIA.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.OFICIO_ADMISION_SIN_AUDIENCIA.getDescripcion());
            } else if(doc.getCveTipoDocumento().equals(TipoDocumentoOficial.ACTA_AUDIENCIA_CON_CONTRIBUYENTE.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.ACTA_AUDIENCIA_CON_CONTRIBUYENTE.getDescripcion());
            } else if(doc.getCveTipoDocumento().equals(TipoDocumentoOficial.ACTA_AUDIENCIA_SIN_CONTRIBUYENTE.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.ACTA_AUDIENCIA_SIN_CONTRIBUYENTE.getDescripcion());
            } else if(doc.getCveTipoDocumento().equals(TipoDocumentoOficial.OFICIO_CAMBIO_FECHA.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.OFICIO_CAMBIO_FECHA.getDescripcion());
            }else if(doc.getCveTipoDocumento().equals(TipoDocumentoOficial.OFICIO_AUDIENCIA_PERITO.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.OFICIO_AUDIENCIA_PERITO.getDescripcion());
            }else if(doc.getCveTipoDocumento().equals(TipoDocumentoOficial.ACTA_AUDIENCIA_PERITO.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.ACTA_AUDIENCIA_PERITO.getDescripcion());
            }else if(doc.getCveTipoDocumento().equals(TipoDocumentoOficial.DICTAMEN_INICIAL_RRL.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.DICTAMEN_INICIAL_RRL.getDescripcion());
            }else if(doc.getCveTipoDocumento().equals(TipoDocumentoOficial.DICTAMEN_FINAL_RRL.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.DICTAMEN_FINAL_RRL.getDescripcion());
            }else if(doc.getCveTipoDocumento().equals(TipoDocumentoOficial.INSTRUCTIVO_RRL.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.INSTRUCTIVO_RRL.getDescripcion());
            }else if(doc.getCveTipoDocumento().equals(TipoDocumentoOficial.PROTOCOLO_AUDIENCIA_CONRIBUYENTE.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.PROTOCOLO_AUDIENCIA_CONRIBUYENTE.getDescripcion());
            }else if(doc.getCveTipoDocumento().equals(TipoDocumentoOficial.PROTOCOLO_AUDIENCIA_PERITO.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.PROTOCOLO_AUDIENCIA_PERITO.getDescripcion());
            }else if(doc.getCveTipoDocumento().equals(TipoDocumentoOficial.OTROS.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.OTROS.getDescripcion());
            }else if(doc.getCveTipoDocumento().equals(TipoDocumentoOficial.ACTA_NOTIFICACION_CONTRIBUYENTE.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.ACTA_NOTIFICACION_CONTRIBUYENTE.getDescripcion());
            }else if(doc.getCveTipoDocumento().equals(TipoDocumentoOficial.ACTA_NOTIFICACION_PERITO.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.ACTA_NOTIFICACION_PERITO.getDescripcion());
            }
            listaDocsDTO.add(doc);
        }
        return listaDocsDTO;
    }

    

    @Override
    public List<DocumentoDTO> getDocumentosObligatorios(Integer idTipoTramite) {
        List<DocumentoDTO> docsDTO = new ArrayList<DocumentoDTO>();
        List<DocumentoTramite> docsTramite =
                registroRecursoRevocacionServices.obtenerDocumentosPorTipoTramite(idTipoTramite, 1);
        for (DocumentoTramite docTramite : docsTramite) {
            DocumentoDTO docDTO = documentoDTOTransformer.transformarDTO(docTramite);
            docsDTO.add(docDTO);
        }
        return docsDTO;

    }

    @Override
    public List<DocumentoDTO> getDocumentosOpcionales(Integer idTipoTramite) {
        List<DocumentoDTO> docsDTO = new ArrayList<DocumentoDTO>();
        List<DocumentoTramite> docsTramite =
                registroRecursoRevocacionServices.obtenerDocumentosPorTipoTramite(idTipoTramite, 0);
        for (DocumentoTramite docTramite : docsTramite) {
            DocumentoDTO docDTO = documentoDTOTransformer.transformarDTO(docTramite);
            docsDTO.add(docDTO);
        }
        return docsDTO;
    }

    @Override
    public List<DocumentoDTO> obtenerDocumentosRegistro(Long idSol) {
        List<DocumentoDTO> listaDocDTO = new ArrayList<DocumentoDTO>();
        List<Documento> listaDocumento = consultaSolicitudServices.obtenerDocumentoSolicitud(idSol.toString());
        List<TipoDocumento> listaTipoDocumento = consultaSolicitudServices.tiposDocumentos();
        for (Documento documento : listaDocumento) {
            DocumentoDTO documentoDTO = documentoDTOTransformer.transformarDocumentoDTO(documento);
            for (TipoDocumento tipoDocumento : listaTipoDocumento) {
                if (tipoDocumento.getIdTipoDocumento().equals(documento.getIdTipoDocumento())) {
                    documentoDTO.setTipoDocumento(tipoDocumento.getNombre());
                }
            }
            listaDocDTO.add(documentoDTO);
        }
        return listaDocDTO;
    }

    @Override
    public List<DocumentoOficialDTO> obtenerDocumentosPorTipo(String numAsunto, String tipoDocumento) {
        List<DocumentoOficialDTO> listaDocsDTO = new ArrayList<DocumentoOficialDTO>();
        List<DocumentoOficial> listaDocs = autorizarRemitirService.obtenerDocumentosPorTipo(numAsunto, tipoDocumento);
        for (DocumentoOficial documentoOficial : listaDocs) {
            DocumentoOficialDTO doc = documentoOficialDTOTransformer.transformarDTO(documentoOficial);
            if (tipoDocumento.equals(TipoDocumentoOficial.OFICIO_REMISION.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.OFICIO_REMISION.getDescripcion());
            }
            else if (tipoDocumento.equals(TipoDocumentoOficial.OFICIO_RESOLUCION.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.OFICIO_RESOLUCION.getDescripcion());
            }
            listaDocsDTO.add(doc);
        }
        return listaDocsDTO;
    }

    @Override
    public void eliminaDocumento(long idDoc) {
        registroRecursoRevocacionServices.eliminaDocumento(idDoc);
    }

    @Override
    public void eliminaDocumentoOficial(long idDoc) {
        registroRecursoRevocacionServices.eliminaDocumentoOficial(idDoc);
    }

    @Override
    public String generaCadenaOriginalDocumentosAdicionales(Long idSolicitud, Date fechaFirma) {
        return consultaSolicitudServices.generaCadenaOriginalDocumentosAdicionales(idSolicitud, fechaFirma);
    }

    @Override
    /**
     * Borrado logico de documentos oficiales ANEXADOS del tipo indicado
     * @param numFolio
     * @param cveDocumentos
     */
    public void eliminarDocumentosOficialesAnexadosPorTipo(String numFolio, String cveDocumentos) {
        documentosServices.eliminarDocumentosOficialesAnexadosPorTipo(numFolio, cveDocumentos);
    }

}
