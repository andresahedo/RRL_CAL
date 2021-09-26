package mx.gob.sat.siat.juridica.base.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Descripcion;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.service.CatalogosServices;
import mx.gob.sat.siat.juridica.base.service.DocumentosServices;
import mx.gob.sat.siat.juridica.base.util.constante.CatalogoConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentoDTOTransformer extends DTOTransformer<DocumentoTramite, DocumentoDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = -3907469803886253314L;

    @Autowired
    private DocumentosServices documentosServices;

    @Autowired
    private CatalogosServices catalogosServices;

    @Override
    public DocumentoDTO transformarDTO(DocumentoTramite documento) {
        DocumentoDTO docDTO = new DocumentoDTO();
        docDTO.setTipoDocumento(documento.getTipoDoc().getNombre());
        docDTO.setIdTipoDocumento(documento.getTipoDoc().getIdTipoDocumento());
        docDTO.setDescripcion(documento.getTipoDoc().getDescripcion());
        Descripcion descripcionTooltip =
                catalogosServices.buscarDescripcionPorTipoIdentificador(
                        CatalogoConstantes.ENU_CATALOGO_DESCRIPCION_DOCUMENTO, documento.getTipoDoc()
                                .getIdTipoDocumento().toString());
        docDTO.setTooltipDescripcion(descripcionTooltip != null ? descripcionTooltip.getDescripcion() : null);
        return docDTO;
    }

    public DocumentoDTO transformarDocumentoDTO(Documento documento) {
        DocumentoDTO documentoDTO = new DocumentoDTO();
        if (documento != null) {
            documentoDTO.setFechaRecepcion(documento.getFechaCreacion());
            documentoDTO.setNombre(documento.getNombre());
            documentoDTO.setIdTipoDocumento(documento.getIdTipoDocumento());
            documentoDTO.setRutaAzure(documento.getUrlDocumento());

            documentoDTO.setHashDocumento(documento.getHash());
            documentoDTO.setTamanioArchivo(documento.getLongitud());
            documentoDTO
                    .setCadenaTamanioArchivo(documentosServices.obtenerCadenaTamanioArchivo(documento.getLongitud() != null
                            ? documento.getLongitud() : 0L));
            if (documento.getIdDocumento() != null) {
                documentoDTO.setIdDocumentoSolicitud(documento.getIdDocumento());
            }
        }
        return documentoDTO;
    }

    public DocumentoDTO transformarDocumentoDTO(Documento documento, Long idDocumentoSolicitud) {
        DocumentoDTO documentoDTO = new DocumentoDTO();
        if (documento != null) {
            documentoDTO = transformarDocumentoDTO(documento);
            documentoDTO.setIdDocumentoSolicitud(idDocumentoSolicitud);
        }
        return documentoDTO;
    }

    public List<Documento> transdormarModelDocumento(List<DocumentoDTO> listaDocumentoDTO, Persona persona) {
        List<Documento> listaDocumento = new ArrayList<Documento>();
        for (DocumentoDTO documentoDTO : listaDocumentoDTO) {
            Documento documento = new Documento();
            documento.setIdTipoDocumento(documentoDTO.getIdTipoDocumento());
            documento.setNombre(documentoDTO.getNombre());
            documento.setFechaCreacion(documentoDTO.getFechaRecepcion());
            documento.setUrlDocumento(documentoDTO.getRutaAzure());
            documento.setPersona(persona);
            documento.setComplementario(documentoDTO.isComplementario());
            documento.setHash(documentoDTO.getHashDocumento());
            documento.setLongitud(documentoDTO.getTamanioArchivo());
            if (documentoDTO.getIdDocumentoSolicitud() != 0) {
                documento.setIdDocumento(documentoDTO.getIdDocumentoSolicitud());
            }
            listaDocumento.add(documento);

        }
        return listaDocumento;
    }

    public List<DocumentoSolicitud> transformarModelDocumentoSolicitud(Long idsol, Persona persona,
            List<Documento> listaDocumento) {
        List<DocumentoSolicitud> listaDocumentoSol = new ArrayList<DocumentoSolicitud>();
        for (Documento documento : listaDocumento) {
            DocumentoSolicitud documentoSol = new DocumentoSolicitud();
            documentoSol.setIdTipoDocumento(documento.getIdTipoDocumento());
            documentoSol.setFechaAsociacion(documento.getFechaCreacion());
            documentoSol.setIdSolicitud(idsol);
            documentoSol.setCvePersona(persona.getIdPersona());
            documentoSol.setComplementario(documento.getComplementario());
            listaDocumentoSol.add(documentoSol);
        }
        return listaDocumentoSol;
    }

    public List<DocumentoSolicitud> transformarDocumentoSolicitud(Long idsol, Persona persona,
            List<Documento> listaDocumento, DocumentoDTO documentoDto) {
        List<DocumentoSolicitud> listaDocumentoSol = new ArrayList<DocumentoSolicitud>();
        for (Documento documento : listaDocumento) {
            DocumentoSolicitud documentoSol = new DocumentoSolicitud();
            documentoSol.setIdTipoDocumento(documento.getIdTipoDocumento());
            documentoSol.setFechaAsociacion(documento.getFechaCreacion());
            documentoSol.setIdSolicitud(idsol);
            documentoSol.setCvePersona(persona.getIdPersona());
            documentoSol.setComplementario(documento.getComplementario());
            documentoSol.setFolio(documentoDto.getFolio());
            documentoSol.setFechaApertura(documentoDto.getFechaApertura());
            listaDocumentoSol.add(documentoSol);
        }
        return listaDocumentoSol;
    }

    public DocumentoOficialDTO transformarDocumentoOficial(DocumentoOficial doc) {
        DocumentoOficialDTO documentoDTO = new DocumentoOficialDTO();
        documentoDTO.setNombreArchivo(doc.getDescripcionDocumento());
        documentoDTO.setEstadoDocumentoOficial(doc.getEstadoDocumento());
        documentoDTO.setFechaCreacion(doc.getFechaCreacion());
        documentoDTO.setCveTipoDocumento(doc.getIdeTipoDocOficial());
        documentoDTO.setRutaAzure(doc.getUrlDocumento());
        documentoDTO.setHashDocumento(doc.getHash());
        documentoDTO.setTamanioArchivo(doc.getLongitud());
        documentoDTO.setCadenaTamanioArchivo(documentosServices.obtenerCadenaTamanioArchivo(doc.getLongitud() != null
                ? doc.getLongitud() : 0L));
        documentoDTO.setFechaAudiencia(doc.getFechaAudiencia());
        documentoDTO.setFechaNotificacion(doc.getFechaNotificacion());
        return documentoDTO;
    }

    public Documento transdormarModelDocumentoDTO(DocumentoDTO documentoDTO) {
        Documento documento = new Documento();
        documento.setIdTipoDocumento(documentoDTO.getIdTipoDocumento());
        documento.setNombre(documentoDTO.getNombre());
        documento.setFechaCreacion(documentoDTO.getFechaRecepcion());
        documento.setUrlDocumento(documentoDTO.getRutaAzure());
        documento.setHash(documentoDTO.getHashDocumento());
        documento.setLongitud(documentoDTO.getTamanioArchivo());
        return documento;
    }

    public List<DocumentoSolicitudRequerimiento> transformarDocumentoSolicitudRequerimiento(
            List<DocumentoSolicitud> docs, Long idRequerimiento) {
        Requerimiento requerimiento = new RequerimientoDocumentos();
        requerimiento.setIdRequerimiento(idRequerimiento);
        List<DocumentoSolicitudRequerimiento> documentos = new ArrayList<DocumentoSolicitudRequerimiento>();
        for (DocumentoSolicitud doc : docs) {
            DocumentoSolicitudRequerimiento docSolReq = new DocumentoSolicitudRequerimiento();
            docSolReq.setDocumentoSolicitud(doc);
            docSolReq.setTipoDocumento(doc.getTipoDocumento());
            docSolReq.setRequerimiento(requerimiento);
            documentos.add(docSolReq);
        }
        return documentos;
    }

    public List<DocumentoDTO> tranformarDocSolicitud(List<DocumentoSolicitud> documentoSolicitud) {
        List<DocumentoDTO> docsDTO = new ArrayList<DocumentoDTO>();
        for (DocumentoSolicitud ds : documentoSolicitud) {
            DocumentoDTO docu = new DocumentoDTO();
            docu.setComplementario(ds.getComplementario());
            docu.setDescripcion(ds.getDescripcion());
            docu.setFechaRecepcion(ds.getFechaAsociacion());
            docu.setIdDocumentoSolicitud(ds.getIdDocumentoSolicitud());
            docu.setIdTipoDocumento(ds.getIdTipoDocumento());
            docsDTO.add(docu);

        }
        return docsDTO;

    }

    public DocumentoDTO transformarDocOfiADocDTO(DocumentoOficial doc) {
        DocumentoDTO documentoDTO = new DocumentoDTO();
        documentoDTO.setNombre(doc.getDescripcionDocumento());
        documentoDTO.setFechaRecepcion(doc.getFechaCreacion());
        documentoDTO.setRutaAzure(doc.getUrlDocumento());
        documentoDTO.setHashDocumento(doc.getHash());

        return documentoDTO;
    }

}
