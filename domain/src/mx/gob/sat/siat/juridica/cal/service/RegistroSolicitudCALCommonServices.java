/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoAutoridad;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.ca.dao.domain.model.SolicitudConsultaAutorizacion;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Softtek
 */
public interface RegistroSolicitudCALCommonServices extends Serializable {

    /**
     * Metodo par aobtener una lista de unidades administrativas
     * emisoras
     * 
     * @return List<UnidadAdministrativa>
     */
    List<UnidadAdministrativa> obtenerAutoridadesEmisoras();

    /**
     * Metodo para obtener una lista de catalogos
     * 
     * @return List<Catalogo>
     */
    List<Catalogo> obtenerListaSiNo();

    /**
     * Metodo para obtener una enumeracion por id
     * 
     * @param ideEnumeracionH
     * @return List<EnumeracionTr>
     */
    List<EnumeracionTr> obtenerEnumeracionPorId(String ideEnumeracionH);

    /**
     * Metodo para obtener una lista de modalidades de tramite por
     * subservicio
     * 
     * @param idSubservicio
     * @return List<TipoTramite>
     */
    List<TipoTramite> obtenerModalidadesPorSubservicio(String idSubservicio, String tipoPersona);

    /**
     * Metodo para obtener una lista de tipos de documento por tramite
     * 
     * @param idTipoTramite
     * @param opcional
     * @return List<DocumentoTramite>
     */
    List<DocumentoTramite> obtenerTiposDocumentosPorTramite(Integer idTipoTramite, Integer opcional);

    /**
     * Metodo para obtener una persona por RFC
     * 
     * @param rfc
     * @return Persona
     */
    Persona obtenerPersonaPorRFC(String rfc);

    /**
     * Metodo para validar la existencia de una persona
     * 
     * @param persona
     * @throws SolicitudNoGuardadaException
     */
    void validarPersonaExistente(Persona persona) throws SolicitudNoGuardadaException;

    /**
     * Metodo para guardar una solicitud
     * 
     * @param solicitud
     * @return SolicitudConsultaAutorizacion
     * @throws SolicitudNoGuardadaException
     */
    SolicitudConsultaAutorizacion guardarSolicitud(SolicitudConsultaAutorizacion solicitud, String rfcSolicitante)
            throws SolicitudNoGuardadaException;

    /**
     * Metodo para descargar un archivo
     * 
     * @param ruta
     * @return InputStream
     */
    InputStream descargarArchivo(String ruta);

    /**
     * Metodo para validar documentos
     * 
     * @param tipoTramite
     * @param documentos
     * @throws ArchivoNoGuardadoException
     */
    void validarDocumentos(String tipoTramite, List<Documento> documentos) throws ArchivoNoGuardadoException;

    /**
     * M&eacute;todo para validar documentos pasando los documentos
     * seleccionados como requeridos y los que va anexando el usuario
     * en pantalla.
     * 
     * @param tipoTramite
     * @param documentos
     * @throws ArchivoNoGuardadoException
     */
    void validarDocumentosRequeridos(List<Documento> documentos, List<Documento> documentosR)
            throws ArchivoNoGuardadoException;

    /**
     * Metodo para guardar documentos
     * 
     * @param listaDocumentos
     * @param listaDocumentosSolicitud
     */
    void guardarDocumentos(List<Documento> listaDocumentos, List<DocumentoSolicitud> listaDocumentosSolicitud);

    /**
     * Metodo para guardado de documentos devolviendo la lista de
     * DocumentoSolicitud persistida
     * 
     * @param listaDocumentos
     * @param listaDocumentosSolicitud
     */
    List<DocumentoSolicitud> guardarParcialDocumentos(long idSolicitud, List<Documento> listaDocumentos,
            List<DocumentoSolicitud> listaDocumentosSolicitud);

    /**
     * Metodo para generar una cadena
     * 
     * @param idSolicitud
     * @param fechaFirma
     * @return
     */
    String generaCadenaOriginal(long idSolicitud, Date fechaFirma);

    /**
     * Metodo para validar un documento anexado
     * 
     * @param documento
     * @param listaDocumentos
     * @param tamanioArchivo
     * @throws ArchivoNoGuardadoException
     */
    void validarDocumentosAnexar(Documento documento, List<Documento> listaDocumentos, long tamanioArchivo)
            throws ArchivoNoGuardadoException;

    List<Catalogo> obtenerTipoPersona();

    void guardaAutoridad(Autoridad autoridad);

    Autoridad obtenerAutoridad(Long idSolicitud, TipoAutoridad tipoAutoridad);

    PersonaOirRecibirNotificaciones
            guardaPersonaOirNot(PersonaOirRecibirNotificaciones personaOirRecibirNotificaciones);

    PersonaResidenteExtranjero guardaResidenteExtranjero(PersonaResidenteExtranjero personaResidenteExtranjero);

    FraccionDuda guardarFraccionDuda(FraccionDuda fraccionDuda);

    List<CatalogoD> obtenerCatalogoDCodGen1(String codGen1);

    List<EnumeracionTr> obtenerTipoClasificacion();

    void eliminaPersona(Long idPersona);

    void eliminaFraccion(Long idFraccion);

    TipoTramite obtenerTipoTramitePorId(String idTIpoTramite);

    Firma generaFirmaPromocionSIAT(String numAsunto, long idSolicitud, Date fechaFirma);

    List<String> obtenerUnidadesAdministrativas(String rfc, String cveRol, String tipoTramite);

    DocumentoTramite obtenerDocumentoRazonesLogicoJuridicas();

    boolean tieneDocumentosAnexados(String idSolicitud);

    void eliminarFraccionesDuda(Long idSolicitud);

}
