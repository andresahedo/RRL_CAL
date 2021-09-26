/*
 *  Todos los Derechos Reservados © 2013 SAT
 *  Servicio de Administracion Tributaria (SAT).
 *  
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Documento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoResolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoSolicitud;

import java.util.List;

/**
 * Interface para guardar documentos oficiales
 * 
 * @author Softtek
 */
public interface DocumentoDao {

    void guardarDocumentoOficial(DocumentoOficial doc);

    List<DocumentoOficial> obtenerDocumentosPorTipo(String numAsunto, String tipoDocumento, Long idRequerimieto);
    
    List<DocumentoOficial> obtenerDocumentoOficialTipo(String idtramite, String idTipoDocume0toOficial);

    List<DocumentoSolicitud> obtenerDocumentosComplementarios(Long idSolicitud);
    
    

    List<DocumentoSolicitud> obtenerDocumentosAnexados(Long idSolicitud);

    DocumentoSolicitud obtenerDocumentoSolPorId(long idDocumentoSol);

    void guardaDocumentoSolicitud(Documento documento);

    void guardaDocumento(DocumentoSolicitud documentoSol);

    Long obtenIdDocumentoPorIdDocSol(long idDocumentoSol);

    TipoDocumento obtenerTipoDocumentoPorId(int idTipoDocumento);

    List<DocumentoSolicitud> obtenerDocumentosFirmados(Long idSolicitud);

    List<DocumentoOficial> obtenerDocumentosOficialesAnexadosPorTipo(String numAsunto, String tipoDocumento);
   
    List<DocumentoOficial> obtenerDocumentosOficialesAnexadosExclusivoFondo(String numAsunto);
   
    DocumentoOficial obtenerUltimoDocumentoOficialesAnexadosPorTipo(String numAsunto, String tipoDocumento);

    DocumentoSolicitud obtenerDocumentoSolPorIdDocumento(long idDocumentoSol);

    Documento obtenerDocumentoPorId(Long idSolDocumento);

    List<DocumentoOficial> obtenerDocumentosPorTipo(String numAsunto, String tipoDocumento);

    List<Long> obtenerDocumentosFirmadosOfialia(Long idSolicitud, Long folio);

    List<Long> obtenerIdSolicitudPorFolio(Long folio);

    void guardarDocumentResolucion(DocumentoResolucion docRes);

    List<DocumentoResolucion> obtenerDocumentoResolucionPorIdRes(Long idResolucion);

    List<DocumentoSolicitud> obtenerDocumentosPorIdSolicitud(Long idSolicitud);

}
