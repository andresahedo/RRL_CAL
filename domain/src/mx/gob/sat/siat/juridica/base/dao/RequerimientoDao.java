/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.NotificacionRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Requerimiento;

import java.util.Date;
import java.util.List;


/**
 * 
 * @author Softtek
 * 
 */
public interface RequerimientoDao {

    /**
     * M&eacute;todo para guardar requerimiento.
     * 
     * @param requerimiento
     * @throws BusinessException 
     */
    void crearRequerimiento(Requerimiento requerimiento);

    /**
     * M&eacute;todo para guardar documento oficial.
     * 
     * @param documentoOficial
     */
    void guardaDocumentoOficial(DocumentoOficial documentoOficial);

    /**
     * M&eacute;todo para guardar la asociaci&oacute;n del los
     * documentos oficiales con el requerimiento.
     * 
     * @param documentoRequerimiento
     */
    void guardaDocumentoRequerimiento(DocumentoRequerimiento documentoRequerimiento);

    /**
     * M&eacute;todo para obtener requerimiento por ID.
     * 
     * @param idRequerimiento
     * @return
     */
    Requerimiento obtenerRequerimientoPorId(Long idRequerimiento);

    /**
     * Metodo para obtener una lista de requerimientos por id del
     * tramite
     * 
     * @param idTramite
     * @return Lista de Requerimiento
     */
    List<Requerimiento> obtenerListaRequerimientosPorIdTramite(String idTramite);

    /**
     * Metodo para obtener una lista de documentos oficiales por id
     * del requerimiento
     * 
     * @param idRequerimiento
     * @return Lista de Documentos oficiales
     */
    List<DocumentoOficial> obtenerDocumentosOficialesPorIdRequerimiento(Long idRequerimiento);

    /**
     * Metodo para obtener una notificacion por id de requerimiento
     * 
     * @param idRequerimiento
     * @return notificacion de requerimiento
     */
    NotificacionRequerimiento obtenerNotificacionPorIdRequerimiento(Long idRequerimiento);

    /**
     * Metodo para obtener un requerimiento autorizado por id
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
     * Metodo para actualizar un requerimiento
     * 
     * @param requerimiento
     */
    void actualizarRequerimiento(Requerimiento requerimiento);

    /**
     * M&eacute;todo para obtener el &uacute;ltimo requerimiento del
     * tramite solicitado.
     * 
     * @param idTramite
     * @return Requerimiento
     */

    Requerimiento obtenerUltimoRequerimientoPorTramiteCAL(String idTramite);

    Requerimiento obtenerRequerimientoContributente(String idTramite);

    /**
     * M&eacute;todo para obtener el &uacute;ltimo requerimiento del
     * tramite solicitado.
     * 
     * @param idTramite
     * @return Requerimiento
     */

    Requerimiento obtenerUltimoRequerimientoPorTramiteRRL(String idTramite);

    Requerimiento obtenerUltimoRequerimientoPorTramiteRRLTarea(String idTramite);

    Requerimiento obtenerRequerimientoAutoridadInterna(String idTramite);

    Requerimiento obtenerUltimoRequerimientoPorTramite(String numeroAsunto);

    Date obtenerFechaRequerimiento(String numeroAsunto);

    Requerimiento obtenerUltimoRequerimientoPorTramiteActivoCAL(String idTramite);

    List<Requerimiento> obtenerRequermientosAutoridad(String idTramite);

    Requerimiento obtenerUltimoRequerimientoPorTramiteRRLCAL(String idTramite);

    boolean obtenerTramitesActivos(String idTramite);

    List<Requerimiento> obtenerRequerimientosActivos(String idTramite);

}
