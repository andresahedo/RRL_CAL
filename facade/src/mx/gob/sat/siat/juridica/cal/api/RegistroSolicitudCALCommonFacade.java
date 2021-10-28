/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.api;

import com.softtek.idc.client.util.exception.ContribuyenteInactivoException;
import com.softtek.idc.client.util.exception.ContribuyenteNoEncontradoException;
import com.softtek.idc.client.util.exception.IDCException;
import com.softtek.idc.client.util.exception.RFCNoVigenteException;
import com.softtek.idc.common.exceptions.IDCNoDisponibleException;
import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tarea;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.buzon.exception.BuzonNoDisponibleException;
import mx.gob.sat.siat.juridica.buzon.exception.CorreoNoRegistradoException;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface RegistroSolicitudCALCommonFacade extends BaseCloudFacade {

    /**
     * M&eacute;todo para preparar la solicitud que se desea capturar.
     * 
     * @param rfc
     * @param modalidad
     * @return SolicitudCALDTO
     * @throws SolicitudNoGuardadaException
     */
    SolicitudCALDTO iniciarRegistroSolicitud(String rfc, String modalidad) throws SolicitudNoGuardadaException;

    /**
     * M&eacute;todo para obtener las autorizades emisoras.
     * 
     * @return Lista CatalogoDTO
     */
    List<CatalogoDTO> obtenerAutoridadesEmisoras();

    /**
     * M&eacute;todo para obtener lista comun de desici&oacute;n SI o
     * NO.
     * 
     * @return Lista CatalogoDTO
     */
    List<CatalogoDTO> obtenerListaSiNo();

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     * 
     * @return Lista CatalogoDTO
     */
    List<CatalogoDTO> obtenerMediosDeDefensa();

    /**
     * M&eacute;todo para obtener lista de sentidos de
     * resolcui&oacute;n.
     * 
     * @return Lista CatalogoDTO
     */
    List<CatalogoDTO> obtenerSentidosResolucion();

    /**
     * M&eacute;todo para obtener modalidades de consulta.
     * 
     * @return Lista ModalidadesDTO
     */
    List<ModalidadesDTO> obtenerModalidadesConsulta(String tipoPersona);

    /**
     * M&eacute;todo para obtener modalidades de autorizaci&oacute;n.
     * 
     * @return Lista ModalidadesDTO
     */
    List<ModalidadesDTO> obtenerModalidadesAutorizacion(String tipoPersona);

    /**
     * M&eacute;todo para obtener documentos obligatorios por tipo
     * tramite.
     * 
     * @param idTipoTramite
     * @return Lista DocumentoDTO
     */
    List<DocumentoDTO> obtenerDocumentosObligatorios(Integer idTipoTramite, String tipoClasificacion);

    /**
     * M&eacute;todo para obtener documentos opcionales por tipo
     * tramite.
     * 
     * @param idTipoTramite
     * @return Lista DocumentoDTO
     */
    List<DocumentoDTO> obtenerDocumentosOpcionales(Integer idTipoTramite, String tipoClasificacion);

    /**
     * M&eacute;todo para guardar la firma.
     * 
     */

    void guardarFirma(FirmaDTO firmaDTO, Long idSolicitud);

    /**
     * M&eacute;todo para guardar la solicitud.
     * 
     * @param solicitud
     * @return SolicitudCALDTO
     */
    SolicitudCALDTO guardarSolicitud(SolicitudCALDTO solicitud, String rfcSolicitante)
            throws SolicitudNoGuardadaException;

    InputStream descargarArchivo(String ruta);

    /**
     * M&eacute;todo para guardar los documentos que se adjuntan a una
     * solicitud
     * 
     * @param solicitud
     * @param listaDocumentos
     * @param listaDocumentosR
     * @throws ArchivoNoGuardadoException
     *             **********************************************
     *             Modificaci&oacute;n para pasar los documentos
     *             seleccionados por el usuario mas los configurados
     *             por BD.
     * @since 04/04/2014
     * @author antonio.flores
     */
    void guardarDocumentosSolicitud(SolicitudCALDTO solicitud, List<DocumentoDTO> listaDocumentos,
            List<DocumentoDTO> listaDocumentosR, String rfcCapturista) throws ArchivoNoGuardadoException;

    /**
     * M&eacute;todo para guardar parcialmente los documentos que se
     * adjuntan a una solicitud
     * 
     * @param solicitud
     * @param listaDocumentos
     * @return Lista de documentos guardados
     */
    List<DocumentoDTO> guardarDocumentosParcialSolicitud(SolicitudCALDTO solicitud, List<DocumentoDTO> listaDocumentos,
            String rfcCapturista);

    String generaCadenaOriginal(Long idSolicitud, Date fechaFirma);

    List<CatalogoDTO> obtenerListaTipoPersona();

    List<CatalogoDTO> obtenerCatalogoDPorCodGen1(String codGen1);

    List<CatalogoDTO> obtenerListaTipoClasificacion();

    void eliminaPersona(Long idPersona);

    void eliminaFaccion(Long idFraccion);

    /**
     * 
     * @param idSolicitud
     *            Identificador de la solicitud
     * @param listaDocumentosSeleccionadosAnexar
     *            Lista de documentos que seleccion&oacute; el
     *            promovente para adjuntar (requeridos + opcionales)
     * @return Lista de documentos previamente anexados a la solicitud
     *         (por guardado parcial de documentos), o una lista
     *         vac&iacute;a en caso de no existir documentos anexados
     *         a la solicitud.
     */
    List<DocumentoDTO> obtenerDocumentosAnexados(long idSolicitud,
            List<DocumentoDTO> listaDocumentosSeleccionadosAnexar);

    /**
     * Elimina el documento especificado por idDocumentoSol
     * 
     * @param idDocumentoSol
     */
    void eliminaDocumento(long idDocumentoSol);

    /**
     * Devuelve la lista de documentos que est�n en
     * listaDocumentosOpcionales y que ya hayan sido anexados a la
     * solicitud
     * 
     * @param listaDocumentosOpcionales
     * @param idSolicitud
     *            Id de la solicitud
     * @return Lista de documentos que est�n en
     *         listaDocumentosOpcionales y que ya hayan sido anexados
     *         a la solicitud
     */
    DocumentoDTO[] obtenerDocumentosSeleccionados(long idSolicitud, List<DocumentoDTO> listaDocumentosOpcionales);

    TipoTramiteDTO obtenerTipoTramitePorId(String idTIpoTramite);

    /**
     * Obtiene una Firma con la cadena original, y la firma (sello)
     * generada por la SIAT JURIDICA
     * 
     * @param numAsunto
     *            Numero de asunto
     * @param idSolicitud
     *            Id de la solicitud
     * @param fechaFirma
     *            Fecha de firma
     * @return FirmaDTO con la cadena y sello de la selladora (SIAT
     *         Juridica)
     */
    FirmaDTO obtenSelloPromocionSIAT(String numAsunto, long idSolicitud, Date fechaFirma);

    Tarea firmaSolicitud(SolicitudCALDTO solicitud, String usuario, FirmaDTO firma);

    /**
     * Metodo para buscar persona en Idc
     * 
     * @param rfc
     * @return Persona
     * @throws IDCNoDisponibleException
     * @throws IDCException
     * @throws ContribuyenteNoEncontradoException
     * @throws ContribuyenteInactivoException
     * @throws RFCNoVigenteException
     * @throws BuzonNoDisponibleException
     * @throws CorreoNoRegistradoException
     */
    PersonaSolicitudDTO buscarPersonaSolicitante(String rfc) throws IDCNoDisponibleException, IDCException,
            ContribuyenteNoEncontradoException, ContribuyenteInactivoException, RFCNoVigenteException,
            BuzonNoDisponibleException, CorreoNoRegistradoException;

    boolean tieneDocumentosAnexados(String idSolicitud);

    void eliminarFraccionesDuda(Long idSolicitud);

    List<ModalidadesDTO> obtenerModalidadesAviso(String tipoPersona);
}
