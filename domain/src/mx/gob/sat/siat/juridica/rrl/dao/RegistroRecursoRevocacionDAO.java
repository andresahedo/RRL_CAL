/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.rrl.dao.domain.model.SolicitudRecursoRevocacion;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author softtek
 */
public interface RegistroRecursoRevocacionDAO extends Serializable {

    /**
     * Metodo para obtener una lista de documentos por tramite
     * 
     * @param idTipoTramite
     * @param opcional
     * @return
     */
    List<DocumentoTramite> buscarDocumentosTramitePorTipoTramite(Integer idTipoTramite, Integer opcional);

    /**
     * Metodo para guardar un documento
     * 
     * @param documentoSol
     */
    void guardaDocumento(DocumentoSolicitud documentoSol);

    /**
     * Metodo para documento de solicitud
     * 
     * @param documento
     */
    void guardaDocumentoSolicitud(Documento documento);

    /**
     * Metodo para guardar un documento de solicitud de requerimiento
     * 
     * @param docSolReq
     */
    void guardaDocumentoSolicitudRequerimiento(DocumentoSolicitudRequerimiento docSolReq);

    /**
     * Metodo para crear una solicitud de revocacion
     */
    SolicitudRecursoRevocacion crearSolicitud(SolicitudRecursoRevocacion solicitud);

    /**
     * Metodo para obtener una lista de unidades administrativas
     * 
     * @return
     */
    List<UnidadAdministrativa> obtenerUnidadesAdministrativas();

    /**
     * Metodo para crear un solicitante
     * 
     * @param solicitante
     */
    void crearSolicitante(Solicitante solicitante);

    /**
     * Metodo para obtener un soliccitante por rfc
     * 
     * @param idSolicitud
     * @return
     */
    Solicitante getSolicituanteByRfc(Long idSolicitud);

    /**
     * Metodo para obtener documentos oficiales
     * 
     * @param numAsunto
     * @return
     */
    List<DocumentoOficial> obtenerDocumentosOficiales(String numAsunto);

    /**
     * Metodo para buscar documentos por tipo de tramite
     * 
     * @param idTramite
     * @param reglaAnexado
     * @return
     */
    List<DocumentoTramite> buscarDocumentosTramitePorTipoTramite(String idTramite, String reglaAnexado);

    /**
     * Metodo para obtener doumentos de una solicitud
     * 
     * @param idSol
     * @return
     */
    List<DocumentoSolicitud> obtenerDocumentoSolicitud(String idSol);
    
    /**
     * Metodo para obtener doumentos de una solicitud
     * 
     * @param idSol
     * @param idTipoDocumento
     * @return DocumentoSolicitud
     */
    DocumentoSolicitud obtenerDocumentoSolicitudTipoDoc(String idSol, Integer idTipoDocumento);
    

    /**
     * Metodo para obtener documentos por ide de solicitud y tipo de
     * documento
     * 
     * @param idSol
     * @param tipoDoc
     * @return
     */
    List<DocumentoSolicitud> obtenerDocumentoSolicitudTipoDocumento(long idSol, int tipoDoc);

    /**
     * Metodo para obtener un documento completo
     * 
     * @param idDoc
     * @return
     */
    Documento obtenerDocumentoCompleto(Long idDoc);

    /**
     * Metodo para obtener una lista por tipo de documento
     * 
     * @return
     */
    List<TipoDocumento> obtenerListaTipoDocumento();

    /**
     * Metodo para buscar una lista de requerimientos por numero de
     * folio
     * 
     * @param numAsunto
     * @return
     */
    List<Requerimiento> buscarRequerimientosPorNumFolio(String numAsunto);

    /**
     * Metodo para buscar un tramite por numero de folio
     * 
     * @param numAsunto
     * @return
     */
    Tramite buscarTramitePorNumFolio(String numAsunto);

    /**
     * Metodo para buscar documentos por tramite por su tipo
     * 
     * @param idTipoTramite
     * @return
     */
    List<DocumentoTramite> buscarDocumentosTramitePorTipoTramite(String idTipoTramite);

    /**
     * Metodo para obtener documentos de solicitud por id de
     * requerimiento
     * 
     * @param idreq
     * @return
     */
    List<DocumentoSolicitud> obtenerDocumentoSolicitudByIdReq(long idreq);

    /**
     * Metodo para obtener documentos oficiales por id
     * 
     * @param idDoc
     * @return
     */
    DocumentoOficial obtenerDocumentosOficialesById(Long idDoc);

    List<DocumentoSolicitud> obtenerDocumentoSolicitudAnexado(String idSol);

    List<DocumentoOficial> obtenerDocumentosAsuntoPromovente(String numAsunto);

    List<DocumentoOficial> obtenerDocumentosOficialesPromovente(String numAsunto);

	List<DocumentoSolicitud> obtenerDocumentoSolicitudEstado(String idSol, String estadoActual);

}
