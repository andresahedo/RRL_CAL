/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.auditoria.dto.BitacoraDTO;
import mx.gob.sat.siat.juridica.base.api.AnexarDocumentosFacade;
import mx.gob.sat.siat.juridica.base.api.RegistroRecursoRevocacionFacade;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.bpm.excepcion.TareaInicialException;
import mx.gob.sat.siat.juridica.bpm.excepcion.TereaSinUsuarioAsignadoException;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Bussines que implementan la l&oacute;gica de negocio para la
 * captura de la solicitud
 * 
 * @author Softtek
 * 
 */
@ManagedBean(name = "capturaSolicitudBussines")
@NoneScoped
public class CapturaSolicitudBussines extends BaseCloudBusiness {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -5832940846164292532L;

    /** Facade para capturar la solicitud */
    @ManagedProperty("#{registroRecursoRevocacionFacade}")
    private RegistroRecursoRevocacionFacade registroRecursoRevocacionFacade;

    /** Facade para anexar documentos */
    @ManagedProperty("#{anexarDocumentosFacade}")
    private AnexarDocumentosFacade anexarDocumentosFacade;

    /**
     * 
     * @return true
     */
    public Boolean validaFiel() {
        return true;
    }

    /**
     * M&eacute;todo para almacenar los documentos a la solicitud
     */
    public void guardarDocumentosSolicitud(long idSolicitud, List<DocumentoDTO> documentos, String tipoTramite,
            String rfc) throws ArchivoNoGuardadoException {
        getAnexarDocumentosFacade().guardarDocumentosSolicitud(idSolicitud, documentos, tipoTramite, rfc);
    }

    /**
     * M&eacute;todo para descargar un documento
     */
    public InputStream descargarDocumento(DocumentoDTO documento) {
        return getAnexarDocumentosFacade().descargarDocumento(documento);
    }

    /**
     * M&eacute;todo para obtener las autorizades emisoras
     */
    public List<CatalogoDTO> obtenerAutoridadesEmisoras() {

        return getRegistroRecursoRevocacionFacade().obtenerAutoridadesEmisoras();
    }

    /**
     * M&eacute;todo para obtener los datos del solicitante
     */
    public DatosSolicitudDTO obtenerInformacionIDC(String rfc) {
        return getRegistroRecursoRevocacionFacade().obtenerInformacionIDC(rfc);
    }

    public DatosSolicitudDTO obtenerSolicitudPorId(Long idSolicitud) {
        return getRegistroRecursoRevocacionFacade().obtenerSolicitudPorId(idSolicitud);
    }

    /**
     * M&eacute;todo para almacenar la informaci&oacute;n capturada en
     * la solicitud
     */
    public SolicitudDTO guardarSolicitud(DatosSolicitudDTO solicitud) throws SolicitudNoGuardadaException {
        return getRegistroRecursoRevocacionFacade().guardarSolicitud(solicitud);
    }

    /**
     * M&eacute;todo para borrado l&oacute;gico de documentos
     */
    public void eliminaDocumento(Long idDoc) {
        getAnexarDocumentosFacade().eliminaDocumento(idDoc);
    }

    public List<DocumentoDTO> obtenerDocumentosRegistro(Long idSolicitud) {
        return anexarDocumentosFacade.obtenerDocumentosRegistro(idSolicitud);
    }

    /**
     * M&eacute;todo para almacenar la informaci&oacute;n capturada en
     * la solicitud
     */
    public DatosSolicitudDTO guardarSolicitud(DatosSolicitudDTO solicitud, String rfcCapturista)
            throws SolicitudNoGuardadaException {
        return getRegistroRecursoRevocacionFacade().guardarSolicitud(solicitud, rfcCapturista);
    }

    /**
     * M&eacute;todo para obtener los documentos obligatorios
     * asociados a la solicitud
     */
    public List<DocumentoDTO> listaDocObligatorios(Integer idTipoTramite) {
        return getRegistroRecursoRevocacionFacade().getDocumentosObligatorios(idTipoTramite);
    }

    /**
     * M&eacute;todo para obtener los documentos opcionales asociados
     * a la solicitud
     */
    public List<DocumentoDTO> listaDocOpcionales(Integer idTipoTramite) {
        return getRegistroRecursoRevocacionFacade().getDocumentosOpcionales(idTipoTramite);
    }

    public DocumentoDTO[]
            obtenerDocumentosSeleccionados(long idSolicitud, List<DocumentoDTO> listaDocumentosOpcionales) {
        return getRegistroRecursoRevocacionFacade().obtenerDocumentosSeleccionados(idSolicitud,
                listaDocumentosOpcionales);
    }

    /**
     * M&eacute;todo para generar la cadena original de la captura de
     * la solicitud
     */
    public String generaCadenaOriginal(long idSolicitud, Date fechaFirma) {
        return getRegistroRecursoRevocacionFacade().generarCadenaOriginal(idSolicitud, fechaFirma);
    }

    /**
     * M&eacute;todo para firmar la captura de la solicitud
     */
    public String firmarSolicitud(long idSolicitud, FirmaDTO firma, String usuario, String rfcContribuyente,
            Object ceritifcadoUtilizado) throws TareaInicialException, TereaSinUsuarioAsignadoException {
        return getRegistroRecursoRevocacionFacade().firmarSolicitud(idSolicitud, firma, usuario, rfcContribuyente,
                ceritifcadoUtilizado);
    }

    /**
     * 
     * @return registroRecursoRevocacionFacade
     */
    public RegistroRecursoRevocacionFacade getRegistroRecursoRevocacionFacade() {
        return registroRecursoRevocacionFacade;
    }

    /**
     * 
     * @param registroRecursoRevocacionFacade
     *            a fijar
     */
    public void setRegistroRecursoRevocacionFacade(RegistroRecursoRevocacionFacade registroRecursoRevocacionFacade) {
        this.registroRecursoRevocacionFacade = registroRecursoRevocacionFacade;
    }

    /**
     * Metodo para firmar documentos
     * 
     * @param idSolicitud
     */
    public void firmarDocumentos(Long idSolicitud, FirmaDTO firmaDTO) {
        registroRecursoRevocacionFacade.firmarDocumentos(idSolicitud, firmaDTO);

    }

    public void sellaDocumentos(Long idSolicitud) {
        registroRecursoRevocacionFacade.sellaDocumentos(idSolicitud);
    }

    public FirmaDTO obtenSelloPromocionSIAT(String numAsunto, long idSolicitud, Date fechaFirma) {
        return registroRecursoRevocacionFacade.obtenSelloPromocionSIAT(numAsunto, idSolicitud, fechaFirma);
    }

    @Override
    public AnexarDocumentosFacade getCloudFacade() {

        return anexarDocumentosFacade;
    }

    /**
     * 
     * @return anexarDocumentosFacade
     */
    public AnexarDocumentosFacade getAnexarDocumentosFacade() {
        return anexarDocumentosFacade;
    }

    public void setAnexarDocumentosFacade(AnexarDocumentosFacade anexarDocumentosFacade) {
        this.anexarDocumentosFacade = anexarDocumentosFacade;
    }

    public String obtenerModalidadDeTramite(Integer idTipoTramite) {
        return registroRecursoRevocacionFacade.obtenerModalidadDeTramite(idTipoTramite);
    }

    public BitacoraDTO getBitacoraDTO() {
        return new BitacoraDTO();
    }

    public List<DocumentoDTO> obtenerDocumentosPorIdSolicitud(Long idSolicitud) {
        return registroRecursoRevocacionFacade.obtenerDocumentosPorIdSolicitud(idSolicitud);
    }
}
