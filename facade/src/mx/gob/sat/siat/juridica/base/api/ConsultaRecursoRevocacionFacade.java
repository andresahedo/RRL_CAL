package mx.gob.sat.siat.juridica.base.api;

import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.rrl.dto.DatosRequerimientoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.util.exception.FechaInvalidaException;

import java.io.InputStream;
import java.util.List;

public interface ConsultaRecursoRevocacionFacade extends BaseCloudFacade {

    /*
     * Lazy para DocumentoDTO
     */
    DataPage obtenerDocumentosRegistroLazy(String idsolicitud);
    
    /*
     * Lazy para DocumentosOficiales
     */
    DataPage obtenerDocumentosOficialesLazy(String numFolio);
    
    DatosSolicitudDTO buscarSolicitud(Long idSolicitud);

    List<DatosRequerimientoDTO> obtenerRequerimientos(String numFolio);

    void actualizarRequerimiento(DatosRequerimientoDTO datosRequerimientoDTO) throws FechaInvalidaException;

    List<DocumentoDTO> obtenerTiposDocumentos(String idSolicitud, String numAsunto);

    List<DocumentoDTO> obtenerDocumentosRegistro(String idSol);
    
    DocumentoDTO obtenerDocumentoSolicitudTipoDoc(String idSol, Integer idTipoDocumento);

    InputStream descargarDocumento(DocumentoDTO documento);

    List<DocumentoOficialDTO> obtenerDocumentosOficialesPromovente(String numFolio, Long idRequerimiento);

    List<DocumentoOficialDTO> obtenerDocumentosOficiales(String numFolio);

    void eliminaDocumento(long idDocumento);

    void guardarDocumentos(List<DocumentoOficialDTO> documentos, String numAsunto, Long idRequerimiento);

    void eliminaDocumentosNoFirmados(long idSolicitud);

    TramiteDTO obtenerTramitePorId(String numFolio);

    List<CatalogoDTO> obtenerMotivosRechazo();

    List<ObservacionDTO> obtenerObservacionesPorTramite(String numAsunto);

    List<DocumentoOficialDTO> obtenerDocOficialesGenerados(String numAsunto);
    
    List<DocumentoOficialDTO> obtenerDocumentoOficialesTipo(String idTipoTramite, String idTipoDocumentoOficial);

}
