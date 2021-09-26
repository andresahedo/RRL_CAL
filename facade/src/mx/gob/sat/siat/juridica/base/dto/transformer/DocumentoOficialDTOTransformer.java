/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.service.DocumentosServices;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Transformer de documento oficial.
 * 
 * @author Softtek
 * 
 */
@Component
public class DocumentoOficialDTOTransformer extends DTOTransformer<DocumentoOficial, DocumentoOficialDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = -4526296053188116653L;

    @Autowired
    private DocumentosServices documentosServices;

    /**
     * M&eacute;todo para transformar el objeto DocumentoOficial a
     * DocumentoOficialDTO.
     */
    @Override
    public DocumentoOficialDTO transformarDTO(DocumentoOficial documentoOficial) {
        DocumentoOficialDTO docOfDTO = new DocumentoOficialDTO();
        docOfDTO.setNombreArchivo(documentoOficial.getDescripcionDocumento());
        docOfDTO.setFechaCreacion(documentoOficial.getFechaCreacion());
        docOfDTO.setIdDocumentoOficial(documentoOficial.getIdDocumentoOficial());
        docOfDTO.setRutaAzure(documentoOficial.getUrlDocumento());
        docOfDTO.setEstadoDocumentoOficial(documentoOficial.getEstadoDocumento());
        docOfDTO.setHashDocumento(documentoOficial.getHash());
        docOfDTO.setCveTipoDocumento(documentoOficial.getIdeTipoDocOficial());
        docOfDTO.setTamanioArchivo(documentoOficial.getLongitud());
        docOfDTO.setCadenaTamanioArchivo(documentosServices.obtenerCadenaTamanioArchivo(documentoOficial.getLongitud() != null
                ? documentoOficial.getLongitud() : 0L));
        docOfDTO.setFechaAudiencia(documentoOficial.getFechaAudiencia());
        docOfDTO.setFechaNotificacion(documentoOficial.getFechaNotificacion());
        return docOfDTO;
    }

    /**
     * M&eacute;todo para transformar el objeto DocumentoOficialDTO a
     * DocumentoOficial.
     */
    public DocumentoOficial transformarModel(DocumentoOficialDTO doc) {
        DocumentoOficial documentoOficial = new DocumentoOficial();
        documentoOficial.setFechaCreacion(doc.getFechaCreacion());
        documentoOficial.setIdDocumentoOficial(doc.getIdDocumentoOficial());
        documentoOficial.setUrlDocumento(doc.getRutaAzure());
        documentoOficial.setIdeTipoDocOficial(doc.getCveTipoDocumento());
        documentoOficial.setHash(doc.getHashDocumento());
        documentoOficial.setDescripcionDocumento(doc.getNombreArchivo());
        documentoOficial.setEstadoDocumento(doc.getEstadoDocumentoOficial());
        documentoOficial.setLongitud(doc.getTamanioArchivo());
        if (doc.getIdDocumentoOficial() != 0) {
            documentoOficial.setIdDocumentoOficial(doc.getIdDocumentoOficial());
        }
        documentoOficial.setFechaAudiencia(doc.getFechaAudiencia());
        documentoOficial.setFechaNotificacion(doc.getFechaNotificacion());
        return documentoOficial;
    }

    /**
     * M&eacute;todo para transformar lista de DocumentoOficialDTO a
     * DocumentoOficial.
     */
    public List<DocumentoOficial> transdormarModelDocumento(List<DocumentoOficialDTO> listaDocumentoOficialDTO,
            String numeroAsunto) {
        List<DocumentoOficial> listaDocumentoOficial = new ArrayList<DocumentoOficial>();
        for (DocumentoOficialDTO documentoOficialDTO : listaDocumentoOficialDTO) {
            DocumentoOficial documentoOficial = new DocumentoOficial();
            documentoOficial.setIdeTipoDocOficial(documentoOficialDTO.getCveTipoDocumento());
            documentoOficial.setDescripcionDocumento(documentoOficialDTO.getNombreArchivo());
            documentoOficial.setFechaCreacion(documentoOficialDTO.getFechaCreacion());
            documentoOficial.setUrlDocumento(documentoOficialDTO.getRutaAzure());
            documentoOficial.setTramite(new Tramite(numeroAsunto));
            documentoOficial.setHash(documentoOficialDTO.getHashDocumento());
            documentoOficial.setLongitud(documentoOficialDTO.getTamanioArchivo());
            documentoOficial.setEstadoDocumento(documentoOficialDTO.getEstadoDocumentoOficial());
            documentoOficial.setBlnActivo(1);
            if (documentoOficialDTO.getIdDocumentoOficial() != 0) {
                documentoOficial.setIdDocumentoOficial(documentoOficialDTO.getIdDocumentoOficial());
            }
            documentoOficial.setFechaAudiencia(documentoOficialDTO.getFechaAudiencia());
            documentoOficial.setFechaNotificacion(documentoOficialDTO.getFechaNotificacion());
            listaDocumentoOficial.add(documentoOficial);
        }
        return listaDocumentoOficial;
    }

}
