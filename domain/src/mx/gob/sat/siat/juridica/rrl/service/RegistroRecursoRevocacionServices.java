/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.rrl.dao.domain.model.SolicitudRecursoRevocacion;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 */
public interface RegistroRecursoRevocacionServices extends Serializable {

    /**
     * Metodo para obtener datos de idc por rfc
     * 
     * @param rfc
     * @return Solicitante
     */
    Solicitante obtenerDatosIdc(String rfc);

    /**
     * Metodo para guardar una solicitud de recurso de revocacion
     * 
     * @param solicitud
     * @param solicitante
     * @return Solicitante
     */
    SolicitudRecursoRevocacion guardarSolicitud(SolicitudRecursoRevocacion solicitud, Solicitante solicitante)
            throws SolicitudNoGuardadaException;

    /**
     * Metodo para obtener un catalogo de unidades administrativas
     * 
     * @return Lista
     */
    List<UnidadAdministrativa> obtenerCatalogo();

    /**
     * Metodo para obtener una lista de documentos por tipo de tramite
     * 
     * @param idTipoTramite
     * @param opcional
     * @return Lista
     */
    List<DocumentoTramite> obtenerDocumentosPorTipoTramite(Integer idTipoTramite, Integer opcional);

    /**
     * Metodo para descargar un documento
     * 
     * @param ruta
     */
    InputStream descargarDocumento(String ruta);

    /**
     * Metodo para guardar documentos de solicitud
     * 
     * @param DocumentoSol
     */
    void guardarDocumentosSolicitud(DocumentoSolicitud documentoSol);

    /**
     * Metodo para generar fecha
     */
    Date generarFecha();

    /**
     * Metodo para obtener una persona por RFC
     * 
     * @param rfc
     * @return Persona
     */
    Persona obtenerPersonaPorRFC(String rfc);

    /**
     * Metodo para guardar un documento de requerimiento
     * 
     * @param documento
     * @param documentoSol
     * @param docsReq
     */
    void guardaDocumentoRequerimiento(List<Documento> documento, List<DocumentoSolicitud> documentoSol,
            List<DocumentoSolicitudRequerimiento> docsReq);

    /**
     * Metodo para guardar un documento
     * 
     * @param documento
     * @param documentoSol
     */
    void guardaDocumento(List<Documento> documento, List<DocumentoSolicitud> documentoSol);

    /**
     * Metodo para validar un documento
     * 
     * @param tipoTramite
     * @param documentos
     * @throws ArchivoNoGuardadoException
     */
    void validarDocumentos(String tipoTramite, List<Documento> documentos) throws ArchivoNoGuardadoException;

    /**
     * Metodo para obtener una lista de documentos oficiales por
     * numero de asunto
     * 
     * @param numAsunto
     * @return Lista
     */
    List<DocumentoOficial> obtenerDocumentosOficiales(String numAsunto);

    /**
     * Metodo para obtener una lista de documentos oficiales por ids
     * de documento
     * 
     * @param idsDoc
     * @return Lista
     */
    List<DocumentoOficial> obtenerDocumentosOficialesById(List<Long> idsDoc);

    /**
     * Metodo para borrado logico de un documento
     * 
     * @param idDoc
     */
    void eliminaDocumento(long idDoc);

    void eliminaDocumentoOficial(long idDoc);

    SolicitudRecursoRevocacion guardarSolicitud(SolicitudRecursoRevocacion solicitud, Solicitante solicitante,
            String rfcCapturista) throws SolicitudNoGuardadaException;

    /**
     * Metodo para obtener la lista de documentos generados por el
     * sistema en la consulta de promociones del promovente.
     */
    List<DocumentoOficial> obtenerDocOficialesGenerados(String numAsunto);

}
