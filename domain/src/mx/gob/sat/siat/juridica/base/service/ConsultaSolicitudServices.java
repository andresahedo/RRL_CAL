/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface ConsultaSolicitudServices extends Serializable {

    /**
     * Metodo para obtener datos de un solicitante por rfc
     * 
     * @param rfc
     * @return Solicitante
     */
    Solicitante obtenerDatosIdc(String rfc);

    /**
     * Metodo para obtener los datos de un un funcionario por rfc
     * 
     * @param rfc
     * @return PersonaInterna
     */
    PersonaInterna obtenerDatosFuncionarioIdc(String rfc);

    /**
     * Metodo para buscar una solicitud
     * 
     * @param idSolicitud
     * @return SolicitudRecursoRevocacion
     */
    SolicitudDatosGenerales buscar(long idSolicitud);

    /**
     * Metodo para buscar usuarios
     * 
     * @return Lista de personas
     */
    List<Persona> obtenerUsuarios();

    /**
     * Metodo para obtener Documentos oficiales
     * 
     * @param numFolio
     * @return lista de documentos oficiales
     */
    List<DocumentoOficial> obtenerDocumentosOficiales(String numFolio);

    /**
     * Metodo para obtener la descripcion de un tipo de documento por
     * id
     * 
     * @param idTipoDoc
     * @return String
     */
    String obtenerDescripcionTipoDoc(String idTipoDoc);

    /**
     * Metodo para obtener los documentos de un tramite
     * 
     * @param idTipoTramite
     * @param numAsunto
     * @return Lista de documentos del tramite
     */
    List<DocumentoTramite> obtenerDocumentosTramite(String idTipoTramite, String numAsunto);

    /**
     * Metodo para obtener los documentos de una solicitud
     * 
     * @param idSol
     * @return Lista de documentos
     */
    List<Documento> obtenerDocumentoSolicitud(String idSol);
    
    /**
     * Metodo para obtener el documento
     * @param idSol
     * @param idTipoDocumento
     * @return documento
     */
    Documento obtenerDocumentoSolicitudTipoDoc(String idSol, Integer idTipoDocumento);

    /**
     * Metodo para obtener un documento completo
     * 
     * @param idDoc
     * @return Documento
     */
    Documento obtenerDocumentoCompleto(Long idDoc);

    /**
     * Metodo para realizar la descarga de un documento
     * 
     * @param ruta
     * @return InputStream
     */
    InputStream descargarDocumento(String ruta);

    /**
     * Metodo para obtener los tipos de documentos
     * 
     * @return Lista de los tipos de documento
     */
    List<TipoDocumento> tiposDocumentos();

    /**
     * Metodo para obtener requerimientos por asunto
     * 
     * @param asunto
     * @return Lista de requerimiento
     */
    List<Requerimiento> obteneRequerimientosPorAsunto(String asunto);

    /**
     * Metodo para obtener documentos oficiales por id de
     * requerimientos
     * 
     * @param idRequerimiento
     * @return Lista de documentos oficiales
     */
    List<DocumentoOficial> obteneDocumentosOficialesPorIdRequerimiento(Long idRequerimiento);

    /**
     * Metodo para obtener documentos por tramite
     * 
     * @param idTipoTramite
     * @return Lista de Documento de tramite
     */
    List<DocumentoTramite> obtenerDocumentosTramite(String idTipoTramite);

    /**
     * Metodo para obtener una notificacion por id de requerimiento
     * 
     * @param idRequerimiento
     * @return NotificacionRequerimiento
     */
    NotificacionRequerimiento obtenerNotificacionPorIdRequerimiento(Long idRequerimiento);

    /**
     * Metodo para obtener un autorizador por id de requerimiento
     * 
     * @param idRequerimiento
     * @return Persona
     */
    Persona obtenerAutorizadorPorIdRequerimiento(Long idRequerimiento);

    /**
     * Metodo para obtener un abogado por id de tramite
     * 
     * @param folioTramite
     * @return Persona
     */
    Persona obtenerAbogadoPorIdTramite(final String folioTramite);

    /**
     * Metodo para obtener un documento anexado por id de
     * requerimiento
     * 
     * @param idReq
     * @return Lista de Documento
     */
    List<Documento> obtenerDocumentoAnexadosRequerimiento(long idReq);

    /**
     * Metodo para obtener un documento de requerimento por id de
     * solicitud
     * 
     * @param idSol
     * @return
     */
    List<Documento> obtenerDocumentoRequerimientoSolicitud(String idSol);

    /**
     * Metodo para obtener una solicitud por id
     * 
     * @param idSolicitud
     * @return Solicitud
     */
    Solicitud obtenerSolicitudporId(Long idSolicitud);

    List<DocumentoOficial> obtenerDocumentosOficialesPromovente(String numFolio, Long idRequerimiento);

    String generaCadenaOriginalDocumentosAdicionales(Long idSolicitud, Date fechaFirma);

    void eliminaDocumentosSolicitudRequerimiento(Long idRequerimiento);

    List<DocumentoOficial> obtenerDocumentosOficialesEmpleado(String numFolio);
    
    List<Requerimiento> obtenerRequermientosAutoridad(String numFolio);
    
}
