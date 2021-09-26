/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.controller.bean.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;

import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dto.DatosNyVDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.util.exception.BaseBussinessException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AnexarDocumentoBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AutorizarResolucionBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoOficialDataModel;
import mx.gob.sat.siat.juridica.rrl.web.util.validador.AnexarDocumentoValidator;

/**
 * Clase que controla en pantalla el adjuntar documentos.
 * 
 * @author softtek
 * 
 */
@ManagedBean(name = "adjuntarDocumentoController")
@SessionScoped
public class AdjuntarDocumentoController extends BaseCloudController<DocumentoOficialDTO> {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = 1L;
    /** Lista de documentos oficiales adjuntados */
    private List<DocumentoOficialDTO> listaDocumentosOficiales = new ArrayList<DocumentoOficialDTO>();
    /** Data model de documentos adjuntados */
    private DocumentoOficialDataModel documentoDataModel;
    /** Archivo para descarga */
    private transient DefaultStreamedContent archivoDescarga;
    /** Bussines para adjuntar documentos */
    @ManagedProperty(value = "#{anexarDocumentoBussines}")
    // TODO usar Business propio
    private AnexarDocumentoBussines anexarDocumentoBussines;
    /** Validator para adjuntar documentos */
    @ManagedProperty(value = "#{anexarDocumentoValidator}")
    private AnexarDocumentoValidator anexarDocumentoValidator;
    @ManagedProperty(value = "#{autorizarResolucionBussines}")
    private AutorizarResolucionBussines autorizarResolucionBussines;

    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    /** DTO que representa lo firma de una autorizacion */
    private FirmaDTO firma;

    /**
     * Arreglo de registros a eliminar.
     */
    private DocumentoOficialDTO[] registrosEliminar;
    /**
     * Propiedad que representa el numero de folio del documento.
     */
    private String numFolio;
    /**
     * DTO de bandeja de tarea.
     */
    private DatosBandejaTareaDTO datosBandejaTareaDTO = new DatosBandejaTareaDTO();
    /**
     * Clave de tipo de documento.
     */
    private String cveTipoDoc;
    /**
     * Descripcion del tipo de documento
     */
    private String descripcionTipoDoc;
    /**
     * Mensaje redireccionar
     */
    private String messagesRedirect;

    private boolean eliminarVisible;

    /**
     * Atributo Boolean que activa o desactiva btn adjuntar para
     * persona moral
     */
    private boolean bndAdjuntar;

    /**
     * Atributo DatosNyVDTO
     */
    private DatosNyVDTO datosNyV = new DatosNyVDTO();

    public boolean isEliminarVisible() {
        return eliminarVisible;
    }

    public void setEliminarVisible(boolean eliminarVisible) {
        this.eliminarVisible = eliminarVisible;
    }

    /**
     * Metodo que carga los documentos.
     */
    public void cargaDocumentos() {
        String id = (String) getFlash().get("documentoSeleccionado");
        getLogger().debug("mi id: {}", id);
    }

    /**
     * Metodo que anexa un documento oficial.
     * 
     * @param event
     * @throws ArchivoNoGuardadoException
     */
    public void anexarDocumentoOficial(FileUploadEvent event) throws ArchivoNoGuardadoException, IOException {
        try {
            File file = new File(event.getFile().getFileName());
            String nombreArchivo = file.getName();
            DocumentoOficialDTO documento = new DocumentoOficialDTO();
            documento.setCveTipoDocumento(cveTipoDoc);
            documento.setNombreArchivo(nombreArchivo);
            documento.setFechaCreacion(new Date());
            documento.setDescripcionTipoDocumento(descripcionTipoDoc);
            documento.setFile(event.getFile().getInputstream());
            documento.setTamanioArchivo(getTamanioDocumento());
            documento.setCadenaTamanioArchivo(getBusinessBean().getCadenaTamanioArchivo(getTamanioDocumento()));
            documento =
                    getAnexarDocumentoBussines().adjuntarDocumentoOficial(documento, getListaDocumentosOficiales(),
                            getDatosBandejaTareaDTO().getNumeroAsunto());
            FacesMessage msg = new FacesMessage("", "Documento adjuntado correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            getListaDocumentosOficiales().add(documento);
            setDocumentoDataModel(new DocumentoOficialDataModel(getListaDocumentosOficiales()));
        }
        catch (ArchivoNoGuardadoException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", e.getMessage()));
        }
        catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", e.getMessage()));
        }
    }
    
    public void anexarDocumentoNube() {
        DocumentoOficialDTO documento = obtenerDocumentoOficialDTO(cveTipoDoc, descripcionTipoDoc);
        if (validaDocumentoAzure(documento, getListaDocumentosOficiales())) {
            verificaDocumentoAzure(documento);
        } else {
            getLogger().error("Archivo invalido");
            getLogger().error("Error: "+ documento.getNombreDocumento());
            String error =documento.getNombreDocumento();
            if(error.substring(error.length() -4, error.length() ) .equalsIgnoreCase(".pdf")) {
                error = "S\u00F3lo es posible adjuntar un archivo a la vez";
            }
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:",error);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    private boolean validaDocumentoAzure(DocumentoOficialDTO documento, List<DocumentoOficialDTO> list) {
        boolean ban = true;
        if(documento.getRutaAzure() != null&& !documento.getRutaAzure().equals("ERROR") ) {
            getLogger().debug("Ruta Azure: "+ documento.getRutaAzure());
            for(DocumentoOficialDTO doc :list) {
                if(doc.getRutaAzure().equals(documento.getRutaAzure())) {
                    ban = false;
                    break;
                }
            } 
        }else {
            ban = false;
        }
        return ban;
    }
    
    private void verificaDocumentoAzure(DocumentoOficialDTO documento) {
        try {
            documento =
                    getAnexarDocumentoBussines().adjuntarDocumentoOficial(documento, getListaDocumentosOficiales(),
                            getDatosBandejaTareaDTO().getNumeroAsunto());
        } catch (ArchivoNoGuardadoException e) {
            getLogger().error(e.getMessage());
        }
        FacesMessage msg = new FacesMessage("", "Documento adjuntado correctamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        getListaDocumentosOficiales().add(documento);
        setDocumentoDataModel(new DocumentoOficialDataModel(getListaDocumentosOficiales()));
        setBndAdjuntar(true);
    }

    /**
     * Metodo que inicializa el proceso para adjuntar documentos.
     * 
     * @param datosBandeja
     * @param cveDocumentos
     */
    public void inicializarAdjuntarDocumentos(DatosBandejaTareaDTO datosBandeja, String cveDocumentos) {
        reiniciarDataModel();
        setDatosNyV(new DatosNyVDTO());
        setDatosBandejaTareaDTO(datosBandeja);
        setCveTipoDoc(cveDocumentos);

        setNumFolio(datosBandeja.getNumeroAsunto());
        setDescripcionTipoDoc(obtenerDescripcionTipoDoc(cveDocumentos));
        getAnexarDocumentoBussines().eliminarDocumentosOficialesAnexadosPorTipo(getNumFolio(), cveDocumentos);

        setListaDocumentosOficiales(new ArrayList<DocumentoOficialDTO>());
        setDocumentoDataModel(new DocumentoOficialDataModel(getListaDocumentosOficiales()));
        setBndAdjuntar(false);
    }

    /**
     * Metodo para obtener la descripcion del tipo de documento.
     * 
     * @param cveTipoDoc
     * @return
     */
    public String obtenerDescripcionTipoDoc(String cveTipoDoc) {
        String descripcion = "";
        if (TipoDocumentoOficial.ACUSE_RECEPCION_TRAMITE.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.ACUSE_RECEPCION_TRAMITE.getDescripcion();
        }
        else if (TipoDocumentoOficial.ACUSE_RECEPCION_PROMOCION.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.ACUSE_RECEPCION_PROMOCION.getDescripcion();
        }
        else if (TipoDocumentoOficial.ACUSE_NOTIFICACION.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.ACUSE_NOTIFICACION.getDescripcion();
        }
        else if (TipoDocumentoOficial.AVISE_NOTIFICACION_REQUERIMIENTO.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.AVISE_NOTIFICACION_REQUERIMIENTO.getDescripcion();
        }
        else if (TipoDocumentoOficial.OFICIO_RESOLUCION.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.OFICIO_RESOLUCION.getDescripcion();
        }
        else if (TipoDocumentoOficial.OFICIO_REQUERIMIENTO.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.OFICIO_REQUERIMIENTO.getDescripcion();
        }
        else if (TipoDocumentoOficial.CONSTANCIA_RECEPCION_DOCUMENTO.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.CONSTANCIA_RECEPCION_DOCUMENTO.getDescripcion();
        }
        else if (TipoDocumentoOficial.OFICIO_REMISION.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.OFICIO_REMISION.getDescripcion();
        }
        return descripcion;
    }

    /**
     * Metodo que reinicia el data model de documento.
     */
    public void reiniciarDataModel() {
        setDocumentoDataModel(null);
    }

    /**
     * Metodo que obtiene documentos.
     */
    public void obtenerDocumentos() {
        setListaDocumentosOficiales(getAnexarDocumentoBussines().obtenerDocumentos(
                getDatosBandejaTareaDTO().getNumeroAsunto()));
        setDocumentoDataModel(new DocumentoOficialDataModel(getListaDocumentosOficiales()));
    }

    /**
     * M&eacute;todo para eliminar documentos seleccionados
     */
    public void eliminarDocumentos(ActionEvent event) {
        if (getRegistrosEliminar() != null && getRegistrosEliminar().length > 0) {
            for (DocumentoOficialDTO documento : getRegistrosEliminar()) {
                getListaDocumentosOficiales().remove(documento);
                if (documento.getIdDocumentoOficial() != 0) {
                    getAnexarDocumentoBussines().eliminaDocumentoOficial(documento.getIdDocumentoOficial());
                }
            }
            setEliminarVisible(false);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El (Los) documento(s) ha(n) sido eliminado(s)"));
        }
        setBndAdjuntar(false);
    }

    /**
     * M&eacute;todo para descargar un documento
     */
    public void descargarDocumento(ActionEvent event) throws IOException {
        DocumentoOficialDTO documento =
                (DocumentoOficialDTO) event.getComponent().getAttributes().get("documentoParametro");

        InputStream input = getAnexarDocumentoBussines().descargarDocumentoOficial(documento);
        HttpServletResponse response =
                (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=" + documento.getNombreArchivo());
        IOUtils.copy(input, response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * Metodo que guarda los documentos oficiales.
     * 
     * @return
     */
    public String guardarDocumentosOficiales() {
        String siguiente = "";
        getFlash().put("datosBandejaDTO", getDatosBandejaTareaDTO());
        getLogger().debug("getDatosBandejaTareaDTO().getIdSolicitud() {}", getDatosBandejaTareaDTO().getIdSolicitud());
        getLogger().debug("getDatosBandejaTareaDTO().getRfcSolicitante() {}",
                getDatosBandejaTareaDTO().getRfcSolicitante());
        SolicitudDTO solicitud = new SolicitudDTO();
        solicitud.setIdSolicitud(getDatosBandejaTareaDTO().getIdSolicitud());
        solicitud.setRfc(getDatosBandejaTareaDTO().getRfcSolicitante());
        getFlash().put(VistaConstantes.SOLICITUD, solicitud);

        String error = "anexarDocumentos.jsf";
        if (getAnexarDocumentoValidator().validarDocumentosOficiales(getListaDocumentosOficiales())) {
            try {
                    for(DocumentoOficialDTO documentoOficial: listaDocumentosOficiales){
                        if(documentoOficial.getCveTipoDocumento() == null){
                            documentoOficial.setCveTipoDocumento(cveTipoDoc);
                        }
                    }
                getAnexarDocumentoBussines().guardarDocumentosOficiales(numFolio, getListaDocumentosOficiales());
                if (cveTipoDoc.equals(TipoDocumentoOficial.OFICIO_RESOLUCION.getClave())) {
                    prepararFirmaAutorizarResolucion();
                    getFlash().put("firma", getFirma());
                    getFlash().put("cadenaOriginal", getFirma().getCadenaOriginal());
                    getFlash().put("datos_nyv", getDatosNyV());
                    siguiente = UrlFirma.PAGINA_FIRMA_RESOLUCION.toString();
                } else if (cveTipoDoc.equals(TipoDocumentoOficial.OFICIO_REMISION.getClave())) { 
                    siguiente = UrlFirma.PAGINA_FIRMA_REMITIR.toString();
                }
            } catch (BaseBussinessException e) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", errorMsg
                                .getString("vuj.resol.documentos.adjuntarAlMenosUnDoc")));
                return error;
            }
            setListaDocumentosOficiales(new ArrayList<DocumentoOficialDTO>());
            return siguiente;
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", errorMsg
                            .getString("vuj.resol.documentos.adjuntarAlMenosUnDoc")));
            return error;
        }
    }

    /**
     * 
     * @return documentoDataModel
     */
    public DocumentoOficialDataModel getDocumentoDataModel() {
        return documentoDataModel;
    }

    /**
     * 
     * @return DocumentoOficialDataModel
     */
    public DocumentoOficialDataModel getDocumentosConsulta() {
        setListaDocumentosOficiales(getAnexarDocumentoBussines().obtenerDocumentos(
                getDatosBandejaTareaDTO().getNumeroAsunto()));
        return new DocumentoOficialDataModel(getListaDocumentosOficiales());
    }

    /**
     * 
     * @param documentoDataModel
     */
    public void setDocumentoDataModel(DocumentoOficialDataModel documentoDataModel) {
        this.documentoDataModel = documentoDataModel;
    }

    /**
     * 
     * @return archivoDescarga
     */
    public DefaultStreamedContent getArchivoDescarga() {
        return archivoDescarga;
    }

    /**
     * 
     * @param archivoDescarga
     *            el archivoDescarga a fijar.
     */
    public void setArchivoDescarga(DefaultStreamedContent archivoDescarga) {
        this.archivoDescarga = archivoDescarga;
    }

    /**
     * 
     * @return anexarDocumentosBussines
     */
    public AnexarDocumentoBussines getAnexarDocumentoBussines() {
        return anexarDocumentoBussines;
    }

    /**
     * 
     * @param anexarDocumentoBussines
     *            el anexarDocumentoBussines a fijar.
     */
    public void setAnexarDocumentoBussines(AnexarDocumentoBussines anexarDocumentoBussines) {
        this.anexarDocumentoBussines = anexarDocumentoBussines;
    }

    /**
     * 
     * @return anexarDocumentoValidator
     */
    public AnexarDocumentoValidator getAnexarDocumentoValidator() {
        return anexarDocumentoValidator;
    }

    /**
     * 
     * @param anexarDocumentoValidator
     *            el anexarDocumentoValidator a fijar.
     */
    public void setAnexarDocumentoValidator(AnexarDocumentoValidator anexarDocumentoValidator) {
        this.anexarDocumentoValidator = anexarDocumentoValidator;
    }

    /**
     * 
     * @return serialVersionUID
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * 
     * @return numFolio
     */
    public String getNumFolio() {
        return numFolio;
    }

    /**
     * 
     * @param numFolio
     *            el numFolio a fijar
     */
    public void setNumFolio(String numFolio) {
        this.numFolio = numFolio;
    }

    /**
     * 
     * @return datosBandejaTareaDTO
     */
    public DatosBandejaTareaDTO getDatosBandejaTareaDTO() {
        return datosBandejaTareaDTO;
    }

    /**
     * 
     * @param datosBandejaTareaDTO
     */
    public void setDatosBandejaTareaDTO(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        this.datosBandejaTareaDTO = datosBandejaTareaDTO;
    }

    /**
     * 
     * @return listaDocumentosOficiales
     */
    public List<DocumentoOficialDTO> getListaDocumentosOficiales() {
        return listaDocumentosOficiales;
    }

    /**
     * 
     * @param listaDocumentosOficiales
     */
    public void setListaDocumentosOficiales(List<DocumentoOficialDTO> listaDocumentosOficiales) {
        this.listaDocumentosOficiales = listaDocumentosOficiales;
    }

    /**
     * 
     * @return cveTipoDoc
     */
    public String getCveTipoDoc() {
        return cveTipoDoc;
    }

    /**
     * 
     * @param cveTipoDoc
     *            la cveTipoDoc a fijar
     */
    public void setCveTipoDoc(String cveTipoDoc) {
        this.cveTipoDoc = cveTipoDoc;
    }

    /**
     * 
     * @return descripcionTipoDoc
     */
    public String getDescripcionTipoDoc() {
        return descripcionTipoDoc;
    }

    /**
     * 
     * @param descripcionTipoDoc
     *            la descripcionTipoDoc a fijar.
     */
    public void setDescripcionTipoDoc(String descripcionTipoDoc) {
        this.descripcionTipoDoc = descripcionTipoDoc;
    }

    /**
     * 
     * @return messagesRedirect
     */
    public String getMessagesRedirect() {
        return messagesRedirect;
    }

    /**
     * 
     * @param messagesRedirect
     *            los messagesRedirect a fijar.
     */
    public void setMessagesRedirect(String messagesRedirect) {
        this.messagesRedirect = messagesRedirect;
    }

    /**
     * Metodo que envia los mensajes.
     */
    public void mensajesRedirect() {
        if (getMessagesRedirect() != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "", getMessagesRedirect()));
            setMessagesRedirect(null);
        }
    }

    /**
     * Metodo que obtiene los registros a ser eliminados.
     * 
     * @return arreglo
     */
    public DocumentoOficialDTO[] getRegistrosEliminar() {
        if (registrosEliminar != null) {
            DocumentoOficialDTO[] arreglo = new DocumentoOficialDTO[registrosEliminar.length];
            for (int i = 0; i < registrosEliminar.length; i++) {
                DocumentoOficialDTO o = registrosEliminar[i];
                arreglo[i] = o;
            }
            return arreglo;
        }
        else {
            return null;
        }
    }

    /**
     * 
     * @param registrosEliminar
     *            los registrosEliminar a fijar
     */
    public void setRegistrosEliminar(DocumentoOficialDTO[] registrosEliminarArray) {
        if (registrosEliminarArray != null) {
            DocumentoOficialDTO[] registrosEliminarLocal = registrosEliminarArray.clone();
            DocumentoOficialDTO[] arreglo = new DocumentoOficialDTO[registrosEliminarLocal.length];
            for (int i = 0; i < registrosEliminarLocal.length; i++) {
                DocumentoOficialDTO o = registrosEliminarLocal[i];
                arreglo[i] = o;
            }
            this.registrosEliminar = arreglo;
        }
        else {
            this.registrosEliminar = null;
        }
    }

    public void rowSelectCheckbox(SelectEvent event) {

        setEliminarVisible(true);

    }

    public void rowUnselectCheckbox(UnselectEvent event) {
        if (getRegistrosEliminar().length < 1) {
            setEliminarVisible(false);
        }
    }

    @Override
    public BaseCloudBusiness getBusinessBean() {
        // TODO Auto-generated method stub
        return anexarDocumentoBussines;
    }

    public FirmaDTO getFirma() {
        return firma;
    }

    public void setFirma(FirmaDTO firma) {
        this.firma = firma;
    }

    /**
     * M&eacute;todo para preparar la cadena original de la firma de
     * la autorizaci&oacute;n de la resolucion
     */
    public void prepararFirmaAutorizarResolucion() {
        setFirma(new FirmaDTO(new Date()));
        getFirma().setCadenaOriginal(
                getAutorizarResolucionBussines().generaCadenaOriginalAutorizarResolucion(
                        getDatosBandejaTareaDTO().getIdSolicitud(), getFirma().getFechaFirma(),
                        getUserProfile().getRfc()));
    }

    public AutorizarResolucionBussines getAutorizarResolucionBussines() {
        return autorizarResolucionBussines;
    }

    public void setAutorizarResolucionBussines(AutorizarResolucionBussines autorizarResolucionBussines) {
        this.autorizarResolucionBussines = autorizarResolucionBussines;
    }

    @Override
    public List<DocumentoOficialDTO> getListaDocumentosAdjuntados() {
        return getListaDocumentosOficiales();
    }

    public void validaAccesoAdministrador() {
        // Validacion de acceso a la funcionalidad
        this.validaAccesoRolEmpleado(RolesConstantes.ROL_EMPLEADO_ADMINISTRADOR);
    }

    public boolean isBndAdjuntar() {
        return bndAdjuntar;
    }

    public void setBndAdjuntar(boolean bndAdjuntar) {
        this.bndAdjuntar = bndAdjuntar;
    }

    public DatosNyVDTO getDatosNyV() {
        return datosNyV;
    }

    public void setDatosNyV(DatosNyVDTO datosNyV) {
        this.datosNyV = datosNyV;
    }

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

}
