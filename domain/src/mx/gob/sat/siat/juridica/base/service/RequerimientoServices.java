/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.EnumeracionTr;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoUnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;
import mx.gob.sat.siat.juridica.rrl.util.exception.FechaInvalidaException;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface RequerimientoServices extends Serializable {

    /**
     * Metodo para recarcular la fecha de vencimiento al atender el
     * requerimiento
     * 
     */


    /**
     * M&eacute;todo para obtener los tipos de requerimiento.
     * 
     * @param ideEnumeracionH
     * @return Lista de EnumeracionTr
     */
    List<EnumeracionTr> obtenerTiposDeRequerimiento(String ideEnumeracionH);

    /**
     * M&eacute;todo para obtener unidades administrativas por tipo.
     * 
     * @param tipoUnidad
     * @return Lista de UnidadAdministrativa
     */
    List<UnidadAdministrativa> obtenerUnidadesPorTipo(TipoUnidadAdministrativa tipoUnidad);

    /**
     * M&eacute;todo para las personas con rol de autorizador por
     * n&uacute;mero de asunto.
     * 
     * @param numeroAsunto
     * @return Lista de Persona
     */
    List<Persona> obtenerAutorizadores(String numeroAsunto);

    /**
     * M&eacute;todo para obtener tramite por n&uacute;mero de asunto.
     * 
     * @param numeroAsunto
     * @return Tramite
     */
    Tramite buscarTramitePorAsunto(String numeroAsunto);

    /**
     * M&eacute;todo para guardar requerimiento.
     * 
     * @param requerimiento
     * @return 
     * @throws BusinessException 
     */
    List<RequerimientoTarea> guardarRequerimientos(List<Requerimiento> requerimientos)
            throws BusinessException;

    /**
     * M&eacute;todo para recuperar el archivo del documento oficial.
     * 
     * @param ruta
     * @return InputStream
     */
    InputStream descargarDocumento(String ruta);

    /**
     * M&eacute;todo para guardar los documentos oficiales asociadas
     * al requerimiento (autorizaci&oacute;n).
     * 
     * @param listaDocumentosOficiales
     * @param idRequerimiento
     * @param numeroAsunto
     */
    void guardaDocumentosOficiales(List<DocumentoOficial> listaDocumentosOficiales, Long idRequerimiento,
            String numeroAsunto);

    /**
     * M&eacute;todo para actualizar requerimiento.
     * 
     * @param idRequerimiento
     * @param estadoRequerimiento
     * @param esAutorizacion
     */
    void actualizaRequerimiento(Requerimiento requerimiento, boolean esAutorizacion)
            throws FechaInvalidaException;

    /**
     * M&eacute;todo para actualizar tramite.
     * 
     * @param numeroAsunto
     * @param estadoTramite
     * @param claveTarea
     */
    void actualizaTramitePorTarea(String numeroAsunto, EstadoTramite estadoTramite, String claveTarea);

    /**
     * Metodo para obtener un requerimiento por id
     * 
     * @param idRequerimiento
     * @return Requerimiento
     */
    Requerimiento obtenerRequerimientoById(Long idRequerimiento);

    /**
     * Metodo para actualizar un requerimiento
     * 
     * @param requerimiento
     */
    void actualizarRequerimiento(Requerimiento requerimiento) throws FechaInvalidaException;

    void eliminaDocumentoOficial(long idDocumento);

    /**
     * M&eacute;todo para obtener el &uacute;ltimo requerimiento del
     * tramite solicitado.
     * 
     * @param idTramite
     * @return Requerimiento
     */
    Requerimiento obtenerUltimoRequerimientoPorTramite(String idTramite, String modalidad);

    Requerimiento obtenerUltimoRequerimientoPorTramiteTarea(String idTramite, String modalidad);

    Requerimiento obtenerRequerimientoAutoridadInterna(String idTramite);

    Requerimiento obtenerRequerimientoContributente(String idTramite, String modalidad);

    List<UnidadAdministrativa> obtenerUnidadesTodas();

    String obtenerAbogadoRequerimiento(Long idRequerimiento);

    void rechazarRequerimiento(String numAsunto, String idTarea, String rfc, Long idRequerimiento);

    String generaCadenaOriginalAutorizarRequerimiento(Long idRequerimiento, Date fechaFirma, String rfcFuncionario);

    String generaCadenaOriginalNotificacionRequerimiento(Long idSolicitud, Long idRequerimiento, Date fechaFirma,
            String rfcUsuario);

    String generaCadenaOriginalRetroalimentacion(String numeroAsunto, Date fechaFirma, String rfcFuncionario);

    Firma generaFirmaRetroalimentacionSIAT(String numeroAsunto, Date fechaFirma, String rfcFuncionario);

    List<DocumentoOficial> buscarTiposDocumentosOfiAtencionRetroPorIdTramite(String numAsunto);

    List<DocumentoOficial>
            buscarTiposDocumentosOfiAtencionRetroPorIdTramiteIdReq(String numAsunto, Long idRequerimiento);

    List<DocumentoSolicitud> obtenerDocumentosComplementarios(Long idSolicitud);

    /**
     * Obtiene la notificaci&oacute;n del requerimiento especificado
     * 
     * @param idRequerimiento
     * @return
     */
    NotificacionRequerimiento obtenerNotificacionRequerimiento(Long idRequerimiento);

    void actualizaRetroalimentacion(Requerimiento requerimiento, boolean esAutorizacion)
            throws FechaInvalidaException;

    void enviarCorreoRechazoAsuntoRequerimiento(String rfcPara, String numAsunto, Long idSolicitud, String elemento,
            Date fechaRecepcion, String nombreRechazo);

    void guardaDocumentosOficialesAtencion(
            List<DocumentoOficial> listaDocumentosOficiales,
            Long idRequerimiento, String numeroAsunto);

    void actualizarFecha(String numeroAsunto, String claveTarea,
            Requerimiento requerimiento);



}
