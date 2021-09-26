package mx.gob.sat.siat.juridica.base.api;

import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public interface AnexarDocumentosFacade extends BaseCloudFacade {

    DocumentoDTO adjuntarDocumento(DocumentoDTO documento) throws ArchivoNoGuardadoException;

    InputStream descargarDocumento(DocumentoDTO documento);

    void guardarDocumentosSolicitud(long idSolicitud, List<DocumentoDTO> documentos, String tipoDocumento,
            String rfc) throws ArchivoNoGuardadoException;

    List<DocumentoDTO> getDocumentosObligatorios(Integer idTipoTramite);

    List<DocumentoDTO> getDocumentosOpcionales(Integer idTipoTramite);

    InputStream descargarDocumentoOficial(DocumentoOficialDTO documento);

    List<DocumentoOficialDTO> obtenerDocumentos(String numAsunto);

    void guardarDocumentosConsulta(long idSolicitud, List<DocumentoDTO> documentos, String tipoTramite,
            String rfc) throws ArchivoNoGuardadoException;

    List<DocumentoOficialDTO> obtenerDocumentosByIdDoc(List<Long> idsDoc);

    List<DocumentoDTO> obtenerDocumentosRegistro(Long idSol);

    void guardarDocumentosOficiales(String numFolio, List<DocumentoOficialDTO> documentos)
            throws ArchivoNoGuardadoException;
    void guardarDocumentosOficialesExlusivoFondo(String numFolio, List<DocumentoOficialDTO> documentos)
            throws ArchivoNoGuardadoException;

    List<DocumentoOficialDTO> obtenerDocumentosPorTipo(String numAsunto, String tipoDocumento);
    
    List<DocumentoOficialDTO> obtenerDocumentosOficialesAnexadosExclusivoFondo(String numAsunto);

    void eliminaDocumento(long idDoc);

    void eliminaDocumentoOficial(long idDoc);

    DocumentoOficialDTO adjuntarDocumentoOficial(DocumentoOficialDTO documento,
            List<DocumentoOficialDTO> listaDocumentos, String numAsunto) throws ArchivoNoGuardadoException;

    String generaCadenaOriginalDocumentosAdicionales(Long idSolicitud, Date fechaFirma);

    String generarCadenaOriginal(long idSolicitud, Date fechaFirma);

    /**
     * Borrado logico de documentos oficiales ANEXADOS del tipo
     * indicado
     * 
     * @param numFolio
     * @param cveDocumentos
     */
    void eliminarDocumentosOficialesAnexadosPorTipo(String numFolio, String cveDocumentos);

}
