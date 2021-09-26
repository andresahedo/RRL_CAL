/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.web.controller.bean.business;

import com.softtek.idc.client.util.exception.ContribuyenteInactivoException;
import com.softtek.idc.client.util.exception.ContribuyenteNoEncontradoException;
import com.softtek.idc.client.util.exception.IDCException;
import com.softtek.idc.client.util.exception.RFCNoVigenteException;
import com.softtek.idc.common.exceptions.IDCNoDisponibleException;
import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.buzon.exception.BuzonNoDisponibleException;
import mx.gob.sat.siat.juridica.buzon.exception.CorreoNoRegistradoException;
import mx.gob.sat.siat.juridica.cal.api.RegistroSolicitudCALCommonFacade;
import mx.gob.sat.siat.juridica.cal.dto.ManifiestoDTO;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Bean para conectar con el backend, atiende todas las peticiones del
 * registro de la solicitud.
 * 
 * @author Softtek
 * 
 */
public abstract class RegistroSolicitudCALCommonBussines extends BaseCloudBusiness {

    /**
     * 
     */
    private static final long serialVersionUID = -1231013432247486261L;

    /**
     * M&eacute;todo para obtener las autorizades emisoras.
     */
    public List<CatalogoDTO> obtenerAutoridadesEmisoras() {
        return getRegistroSolicitudCALCommonFacade().obtenerAutoridadesEmisoras();
    }

    /**
     * M&eacute;todo para obtener lista comun de desici&oacute;n SI o
     * NO.
     */
    public List<CatalogoDTO> obtenerListaSiNo() {
        return getRegistroSolicitudCALCommonFacade().obtenerListaSiNo();
    }

    public List<CatalogoDTO> obtenerListaTipoPersona() {
        return getRegistroSolicitudCALCommonFacade().obtenerListaTipoPersona();
    }

    public List<CatalogoDTO> obtenerListaTipoClasificacion() {
        return getRegistroSolicitudCALCommonFacade().obtenerListaTipoClasificacion();
    }

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     */
    public List<CatalogoDTO> obtenerMediosDeDefensa() {
        return getRegistroSolicitudCALCommonFacade().obtenerMediosDeDefensa();
    }

    /**
     * M&eacute;todo para obtener lista de sentidos de
     * resolcui&oacute;n.
     */
    public List<CatalogoDTO> obtenerSentidosResolucion(String codGen1) {
        return getRegistroSolicitudCALCommonFacade().obtenerCatalogoDPorCodGen1(codGen1);
    }

    /**
     * M&eacute;todo para obtener modalidades de consulta.
     * 
     * @return Lista ModalidadesDTO
     */
    public List<ModalidadesDTO> obtenerModalidadesConsulta(String tipoPersona) {
        return getRegistroSolicitudCALCommonFacade().obtenerModalidadesConsulta(tipoPersona);
    }

    public TipoTramiteDTO obtenerTipoTramitePorId(String idTIpoTramite) {
        return getRegistroSolicitudCALCommonFacade().obtenerTipoTramitePorId(idTIpoTramite);
    }

    /**
     * M&eacute;todo para obtener modalidades de autorizaci&oacute;n.
     * 
     * @return Lista ModalidadesDTO
     */
    public List<ModalidadesDTO> obtenerModalidadesAutorizacion(String tipoPersona) {
        return getRegistroSolicitudCALCommonFacade().obtenerModalidadesAutorizacion(tipoPersona);
    }

    /**
     * M&eacute;todo para obtener documentos obligatorios por tipo
     * tramite.
     * 
     * @param idTipoTramite
     * @return Lista DocumentoDTO
     */
    public List<DocumentoDTO> obtenerDocumentosObligatorios(Integer idTipoTramite, String tipoClasificacion) {
        return getRegistroSolicitudCALCommonFacade().obtenerDocumentosObligatorios(idTipoTramite, tipoClasificacion);
    }

    /**
     * M&eacute;todo para obtener documentos opcionales por tipo
     * tramite.
     * 
     * @param idTipoTramite
     * @return Lista DocumentoDTO
     */
    public List<DocumentoDTO> obtenerDocumentosOpcionales(Integer idTipoTramite, String tipoClasificacion) {
        return getRegistroSolicitudCALCommonFacade().obtenerDocumentosOpcionales(idTipoTramite, tipoClasificacion);
    }

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
    public List<DocumentoDTO> obtenerDocumentosAnexados(long idSolicitud,
            List<DocumentoDTO> listaDocumentosSeleccionadosAnexar) {
        return getRegistroSolicitudCALCommonFacade().obtenerDocumentosAnexados(idSolicitud,
                listaDocumentosSeleccionadosAnexar);
    }

    public DocumentoDTO[]
            obtenerDocumentosSeleccionados(long idSolicitud, List<DocumentoDTO> listaDocumentosOpcionales) {
        return getRegistroSolicitudCALCommonFacade().obtenerDocumentosSeleccionados(idSolicitud,
                listaDocumentosOpcionales);
    }

    /**
     * M&eacute;todo para preparar la solicitud que se desea capturar.
     * 
     * @param rfc
     * @param modalidad
     * @return SolicitudCALDTO
     * @throws SolicitudNoGuardadaException
     */
    public SolicitudCALDTO iniciarRegistroSolicitud(String rfc, String modalidad) throws SolicitudNoGuardadaException {
        return getRegistroSolicitudCALCommonFacade().iniciarRegistroSolicitud(rfc, modalidad);
    }

    /**
     * M&eacute;todo para guardar la solicitud.
     * 
     * @param solicitud
     * @return SolicitudCALDTO
     */
    public SolicitudCALDTO guardarSolicitud(SolicitudCALDTO solicitud, String rfcSolicitante)
            throws SolicitudNoGuardadaException {
        getLogger().debug("antes de guardar solicitud en la fachada");
        return getRegistroSolicitudCALCommonFacade().guardarSolicitud(solicitud, rfcSolicitante);
    }
    
    public ManifiestoDTO validaManifiestos(SolicitudCALDTO solicitud) {
        for (ManifiestoDTO manifiestoDTO : solicitud.getManifiestos()) {
            if (manifiestoDTO.isObligatorio() && !manifiestoDTO.isAceptado()) {
                return manifiestoDTO;
            }
        }
        return null;
    }
    
    public void guardarManifiestosSolicitud(SolicitudCALDTO solicitud) {

    }

    /**
     * M&eacute;todo para descargar archivos
     * 
     * @param documento
     * @return
     */
    public InputStream descargarArchivo(DocumentoDTO documento) {
        return getRegistroSolicitudCALCommonFacade().descargarArchivo(documento.getRuta());
    }

    /**
     * M&eacute;todo para guardar los documentos de una solicitud
     * 
     * @param solicitud
     * @param listaDocumentos
     * @param list
     * @throws ArchivoNoGuardadoException
     *             ***************************************************
     *             ****** Modificaci&oacute;n para validar los
     *             documentos requeridos (por BD y seleccionados)
     * @since 04/04/2014
     * @author antonio.flores
     */
    public void guardarDocumentosSolicitud(SolicitudCALDTO solicitud, List<DocumentoDTO> listaDocumentos,
            List<DocumentoDTO> listDocumentosRequeridos, String rfcCapturista) throws ArchivoNoGuardadoException {
        getRegistroSolicitudCALCommonFacade().guardarDocumentosSolicitud(solicitud, listaDocumentos,
                listDocumentosRequeridos, rfcCapturista);
    }

    /**
     * M&eacute;todo para guardar de manera parcial los documentos de
     * una solicitud
     * 
     * @param solicitud
     * @param listaDocumentos
     * @param list
     *            ****************************************************
     *            *****
     */
    public List<DocumentoDTO> guardarDocumentosParcialSolicitud(SolicitudCALDTO solicitud,
            List<DocumentoDTO> listaDocumentos, String rfcCapturista) {
        return getRegistroSolicitudCALCommonFacade().guardarDocumentosParcialSolicitud(solicitud, listaDocumentos,
                rfcCapturista);
    }

    /**
     * Elimina el documento especificado por idDocumentoSol
     * 
     * @param idDocumentoSol
     */
    public void eliminaDocumento(long idDocumentoSol) {
        getRegistroSolicitudCALCommonFacade().eliminaDocumento(idDocumentoSol);
    }

    /**
     * Metodo que genera cadena original.
     * 
     * @param idSolicitud
     * @param fechaFirma
     * @return
     */
    public String generaCadenaOriginal(long idSolicitud, Date fechaFirma) {
        return getRegistroSolicitudCALCommonFacade().generaCadenaOriginal(idSolicitud, fechaFirma);
    }

    /**
     * Metodo para firmar la solicitud.
     * 
     * @param solicitud
     * @param firma
     * @return
     */
    public String firmaSolicitud(SolicitudCALDTO solicitud, String usuario, FirmaDTO firma) {
        return getRegistroSolicitudCALCommonFacade().firmaSolicitud(solicitud, usuario, firma);
    }

    /**
     * Metodo que guarda la firma.
     * 
     * @param firmaDTO
     */
    public void guardarFirma(FirmaDTO firmaDTO, Long idSolicitud) {
        getRegistroSolicitudCALCommonFacade().guardarFirma(firmaDTO, idSolicitud);
    }

    public void eliminaPersona(Long idPersona) {
        getRegistroSolicitudCALCommonFacade().eliminaPersona(idPersona);
    }

    public void eliminaFraccion(Long idFraccion) {
        getRegistroSolicitudCALCommonFacade().eliminaFaccion(idFraccion);
    }

    /**
     * @return the registroSolicitudCALFacade
     */
    public abstract RegistroSolicitudCALCommonFacade getRegistroSolicitudCALCommonFacade();

    @Override
    public BaseCloudFacade getCloudFacade() {
        return getRegistroSolicitudCALCommonFacade();
    }

    public FirmaDTO obtenSelloPromocionSIAT(String numAsunto, long idSolicitud, Date fechaFirma) {
        return getRegistroSolicitudCALCommonFacade().obtenSelloPromocionSIAT(numAsunto, idSolicitud, fechaFirma);
    }

    public PersonaSolicitudDTO buscarPersonaSolicitante(String rfc) throws IDCNoDisponibleException, IDCException,
            ContribuyenteNoEncontradoException, ContribuyenteInactivoException, RFCNoVigenteException,
            BuzonNoDisponibleException, CorreoNoRegistradoException {
        return getRegistroSolicitudCALCommonFacade().buscarPersonaSolicitante(rfc);
    }

    public boolean tieneDocumentosAnexados(String idSolicitud) {
        return getRegistroSolicitudCALCommonFacade().tieneDocumentosAnexados(idSolicitud);
    }
    public List<ModalidadesDTO> obtenerModalidadesAviso(String tipoPersona) {
        return getRegistroSolicitudCALCommonFacade().obtenerModalidadesAviso(tipoPersona);
    }

}
