package mx.gob.sat.siat.juridica.rrl.web.controller.bean.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.EnumeracionBitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Notificacion;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.FirmarBusiness;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.BaseCloudController;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import mx.gob.sat.siat.juridica.rrl.util.exception.FechaInvalidaException;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AnexarDocumentoBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.RegistrarFechaNotificacionBusiness;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoOficialDataModel;
import mx.gob.sat.siat.juridica.rrl.web.util.validador.AnexarDocumentoValidator;

@ViewScoped
@ManagedBean(name = "registrarFechaNotificacionController")
public class RegistrarFechaNotificacionController extends BaseCloudController<DocumentoOficialDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<CatalogoDTO> listaUnidadesEmisoras = new ArrayList<CatalogoDTO>();

    private DatosBandejaTareaDTO datosBandejaTareaDTO;

    private String errorMessage;
    private String msg;
    private TramiteDTO tramite;
    private String unidad;

    private RequerimientoDTO requerimiento;

    /** Lista de documentos oficiales adjuntados */
    private List<DocumentoOficialDTO> listaDocumentosOficiales = new ArrayList<DocumentoOficialDTO>();
    /** Data model de documentos adjuntados */
    private DocumentoOficialDataModel documentoDataModel;
    /** Archivo para descarga */
    private DefaultStreamedContent archivoDescarga;
    /** Bussines para adjuntar documentos */
    @ManagedProperty(value = "#{anexarDocumentoBussines}")
    private AnexarDocumentoBussines anexarDocumentoBussines;
    /** Validator para adjuntar documentos */
    @ManagedProperty(value = "#{anexarDocumentoValidator}")
    private AnexarDocumentoValidator anexarDocumentoValidator;
    /** Bussines para registrar registrar fecha de noticifacion */

    /**
     * Arreglo de registros a eliminar.
     */
    private DocumentoOficialDTO[] registrosEliminar;
    /**
     * Propiedad que representa el numero de folio del documento.
     */
    private String numFolio;

    /**
     * Clave de tipo de documento.
     */
    private String cveTipoDoc;

    /**
     * Clave de tipo de documento.
     */
    private Long idNotificacion;
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
     * Auxiliar para guardar.
     */
    private boolean banderaGuardar;

    /** Lista de documentos adjuntados */
    private List<DocumentoOficialDTO> listaDocumentos = new ArrayList<DocumentoOficialDTO>();

    private Date fechaNotificacion;
    private Boolean blnAutorizacion = false;
    private Boolean blnFechaNotifDesactivo = false;

    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    @ManagedProperty(value = "#{registrarFechaNotificacionBusiness}")
    private RegistrarFechaNotificacionBusiness registrarFechaNotificacionBusiness;

    /** Datos de la firma */
    private String firmaDigital;
    /** DTO que representa lo firma de la solicitud */
    private FirmaDTO firma;
    @ManagedProperty(value = "#{firmarBusiness}")
    private FirmarBusiness firmarBusiness;

    private Date maxDate;
    private Date minDate;

    public RegistrarFechaNotificacionController() {

    }

    @PostConstruct
    public void iniciar() {
        setTramite(new TramiteDTO());
        cargarDatosBandeja();
        setBlnFechaNotifDesactivo(false);

        validaNotifParcial();
    }

    /**
     * Metodo que carga los datos a la bandeja.
     */
    public void cargarDatosBandeja() {
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get("datosBandejaDTO"));
        if (getDatosBandejaTareaDTO().getNombreTarea()
                .equals(EnumeracionBitacora.NOTIFICACION_RESOLUCION.getDescripcion())) {
            setBlnAutorizacion(true);
        }
        getRegistrarFechaNotificacionBusiness()
                .eliminaDocumentosNotificacionAnexados(getDatosBandejaTareaDTO().getNumeroAsunto());
        setDocumentoDataModel(new DocumentoOficialDataModel(getListaDocumentosOficiales()));
        minDate = getDatosBandejaTareaDTO().getFechaAsignacion() != null
                ? getDatosBandejaTareaDTO().getFechaAsignacion()
                : getDatosBandejaTareaDTO().getFechaAsignacionRequerimiento();
        maxDate = new Date();
    }

    public void validaNotifParcial() {
        Notificacion notificacion = registrarFechaNotificacionBusiness
                .buscarNotifParcial(getDatosBandejaTareaDTO().getNumeroAsunto(), getBlnAutorizacion());
        if (null != notificacion) {
            setFechaNotificacion(notificacion.getFechaEnvioNotificacion());
        }
    }

    /**
     * Metodo que carga los documentos.
     */
    public void cargaDocumentos() {
        String id = (String) getFlash().get("documentoSeleccionado");
        getLogger().debug("mi id: {}", id);
    }

    /**
     * Metodo que reinicia el data model de documento.
     */
    public void reiniciarDataModel() {

        setDocumentoDataModel(null);
    }

    public void prepararFirmaSolicitud() {
        setFirma(new FirmaDTO(new Date()));
        getFirma().setCadenaOriginal(getRegistrarFechaNotificacionBusiness()
                .generaCadenaOriginal(getDatosBandejaTareaDTO().getNumeroAsunto(), getFirma().getFechaFirma()));
    }

    public String procesaFirma() {
        getLogger().debug("firma: "
                + FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("firmaDigital"));
        setFirmaDigital(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                .get("firmaDigital").toString());
        getLogger().debug(getFirmaDigital());
        return "";
    }

    /**
     * M&eacute;todo para eliminar documentos seleccionados
     */
    public void eliminarDocumentos(ActionEvent event) {
        if (getRegistrosEliminar() != null && getRegistrosEliminar().length > 0) {
            for (DocumentoOficialDTO documento : getRegistrosEliminar()) {
                getListaDocumentosOficiales().remove(documento);
            }
            setEliminarVisible(false);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El (Los) documento(s) ha(n) sido eliminado(s)"));
        }

    }

    /**
     * Metodo que inicializa el proceso para adjuntar documentos.
     * 
     * @param datosBandeja
     * @param cveDocumentos
     */
    public void inicializarAdjuntarDocumentos(DatosBandejaTareaDTO datosBandeja, String cveDocumentos) {
        reiniciarDataModel();
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get("datosBandejaDTO"));
        setCveTipoDoc(cveDocumentos);
        setNumFolio(datosBandeja.getNumeroAsunto());
        setDescripcionTipoDoc(TipoDocumentoOficial.ACTA_NOTIFICACION.getDescripcion());
    }

    /**
     * Metodo que anexa un documento oficial.
     * 
     * @param event
     * @throws ArchivoNoGuardadoException
     */
    public void anexarDocumentoOficial(FileUploadEvent event) throws ArchivoNoGuardadoException {
        try {
            inicializarAdjuntarDocumentos(datosBandejaTareaDTO, TipoDocumentoOficial.ACTA_NOTIFICACION.getClave());
            File file = new File(event.getFile().getFileName());
            String nombreArchivo = file.getName();
            DocumentoOficialDTO documento = new DocumentoOficialDTO();
            documento.setCveTipoDocumento(cveTipoDoc);
            documento.setNombreArchivo(nombreArchivo);
            documento.setFechaCreacion(new Date());
            documento.setDescripcionTipoDocumento(descripcionTipoDoc);
            documento.setFile(event.getFile().getInputstream());
            documento = getAnexarDocumentoBussines().adjuntarDocumentoOficial(documento, getListaDocumentosOficiales(),
                    getDatosBandejaTareaDTO().getNumeroAsunto());
            FacesMessage mnsg = new FacesMessage("", "Documento adjuntado correctamente");
            FacesContext.getCurrentInstance().addMessage(null, mnsg);
            getListaDocumentosOficiales().add(documento);
            setDocumentoDataModel(new DocumentoOficialDataModel(getListaDocumentosOficiales()));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", e.getMessage()));
        }
    }

    /**
     * M&eacute;todo para adjuntar un documento
     */

    public void anexarDocumentoNube() {
        getLogger().debug("anexarDocumentoNube");
        setCveTipoDoc(TipoDocumentoOficial.ACTA_NOTIFICACION.getClave());
        setDescripcionTipoDoc(TipoDocumentoOficial.ACTA_NOTIFICACION.getDescripcion());
        DocumentoOficialDTO documento = obtenerDocumentoOficialDTO(cveTipoDoc, descripcionTipoDoc);
        if(validaDocumentoAzure(documento,getListaDocumentosOficiales())) {
            verificaDocumentoAzure(documento);
        }else {
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
        if(documento.getRutaAzure() != null  && !documento.getRutaAzure().equals("ERROR") ) {
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
    	FacesMessage mnsg = new FacesMessage("", "Documento adjuntado correctamente");
        FacesContext.getCurrentInstance().addMessage(null, mnsg);
        getListaDocumentosOficiales().add(documento);
        setDocumentoDataModel(new DocumentoOficialDataModel(getListaDocumentosOficiales()));
    }
    
    public void guardarFechaDeNotificacion(ActionEvent actionEvent) {
        guardarDocumentosOficiales();
    }

    /**
     * Metodo que guarda los documentos oficiales.
     * 
     * @return
     */
    public Long guardarDocumentosOficiales() {
        Long idNotif;

        if (getAnexarDocumentoValidator().validarDocumentosOficiales(getListaDocumentosOficiales())) {

            try {
                idNotif = getRegistrarFechaNotificacionBusiness().guardarFechaNotificacion(
                        getDatosBandejaTareaDTO().getNumeroAsunto(), getBlnAutorizacion(), getFechaNotificacion(),
                        getIdNotificacion());
                getAnexarDocumentoBussines().guardarDocumentosOficiales(getDatosBandejaTareaDTO().getNumeroAsunto(),
                        getListaDocumentosOficiales());
                setIdNotificacion(idNotif);
            } catch (ArchivoNoGuardadoException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                        errorMsg.getString("vuj.resol.documentos.adjuntarAlMenosUnDoc")));
                return null;
            } catch (FechaInvalidaException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                        "La fecha de notificaci\u00F3n es menor a la fecha de autorizaci\u00F3n del requerimiento o la resoluci\u00F3n."));
                return null;
            }

            return idNotif;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    errorMsg.getString("vuj.resol.documentos.adjuntarAlMenosUnDoc")));
            return null;
        }
    }

    /**
     * Metodo par emitir siguiente resolucion.
     * 
     * @param actionEvent
     */
    public void siguienteFirmar(ActionEvent actionEvent) {
        Long idNotif = guardarDocumentosOficiales();

        if (idNotif != null) {
            getDatosBandejaTareaDTO().setIdNotificacion(idNotif);
            getFlash().put("datosBandejaDTO", getDatosBandejaTareaDTO());

            ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext
                    .getCurrentInstance().getApplication().getNavigationHandler();

            configurableNavigationHandler
                    .performNavigation(UrlFirma.PAGINA_FIRMA_CAPTURA_FECHA + "?faces-redirect=true");
        }
    }

    /**
     * M&eacute;todo para descargar un documento
     */
    public void descargarDocumento(ActionEvent event) throws IOException {
        DocumentoDTO documento = (DocumentoDTO) event.getComponent().getAttributes().get("documentoParametro");

        InputStream input = getAnexarDocumentoBussines().descargarDocumento(documento);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
                .getResponse();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=" + documento.getNombre());
        IOUtils.copy(input, response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void rowSelectCheckbox(SelectEvent event) {
        setEliminarVisible(true);
    }

    public void rowUnselectCheckbox(UnselectEvent event) {
        if (getRegistrosEliminar().length < 1) {
            setEliminarVisible(false);
        }
    }

    public Date fechaRecep() {
        return tramite.getFechaRecepcion();
    }

    public Date fechaVenc() {
        return tramite.getFechaVencimiento();
    }

    public List<CatalogoDTO> getListaUnidadesEmisoras() {
        return listaUnidadesEmisoras;
    }

    public void setListaUnidadesEmisoras(List<CatalogoDTO> listaUnidadesEmisoras) {
        this.listaUnidadesEmisoras = listaUnidadesEmisoras;
    }

    public DatosBandejaTareaDTO getDatosBandejaTareaDTO() {
        return datosBandejaTareaDTO;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResourceBundle getMessages() {
        return messages;
    }

    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    public TramiteDTO getTramite() {
        return getRegistrarFechaNotificacionBusiness().obtenerTramite(getDatosBandejaTareaDTO().getNumeroAsunto());
    }

    public void setTramite(TramiteDTO tramite) {
        this.tramite = tramite;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    /**
     * @return firmaDigital
     */
    public String getFirmaDigital() {
        return firmaDigital;
    }

    /**
     * @param firmaDigital firmaDigital a fijar
     */
    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }

    /**
     * @return firma
     */
    public FirmaDTO getFirma() {
        return firma;
    }

    /**
     * @param firma firma a fijar
     */
    public void setFirma(FirmaDTO firma) {
        this.firma = firma;
    }

    /**
     * @return firmarBusiness
     */
    public FirmarBusiness getFirmarBusiness() {
        return firmarBusiness;
    }

    /**
     * @param firmarBusiness firmarBusiness a fijar
     */
    public void setFirmarBusiness(FirmarBusiness firmarBusiness) {
        this.firmarBusiness = firmarBusiness;
    }

    /**
     * @param datosBandejaTareaDTO datosBandejaTareaDTO a fijar
     */
    public void setDatosBandejaTareaDTO(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        if (this.datosBandejaTareaDTO == null) {
            this.datosBandejaTareaDTO = datosBandejaTareaDTO;
        }
    }

    /**
     * @return banderaGuardar
     */
    public boolean isBanderaGuardar() {
        return banderaGuardar;
    }

    /**
     * @param banderaGuardar banderaGuardar a fijar
     */
    public void setBanderaGuardar(boolean banderaGuardar) {
        this.banderaGuardar = banderaGuardar;
    }

    /**
     * @return fechaNotificacion
     */
    public Date getFechaNotificacion() {
        return fechaNotificacion != null ? (Date) fechaNotificacion.clone() : null;
    }

    /**
     * @param fechaNotificacion fechaNotificacion a fijar
     */
    public void setFechaNotificacion(Date fechaNotificacion) {
        if (fechaNotificacion != null) {
            this.fechaNotificacion = (Date) fechaNotificacion.clone();
        } else {
            this.fechaNotificacion = null;
        }
    }

    /**
     * @return documentoDataModel
     */
    public DocumentoOficialDataModel getDocumentoDataModel() {
        return documentoDataModel;
    }

    /**
     * @param documentoDataModel documentoDataModel a fijar
     */
    public void setDocumentoDataModel(DocumentoOficialDataModel documentoDataModel) {
        this.documentoDataModel = documentoDataModel;
    }

    /**
     * @return archivoDescarga
     */
    public DefaultStreamedContent getArchivoDescarga() {
        return archivoDescarga;
    }

    /**
     * @param archivoDescarga archivoDescarga a fijar
     */
    public void setArchivoDescarga(DefaultStreamedContent archivoDescarga) {
        this.archivoDescarga = archivoDescarga;
    }

    /**
     * @return anexarDocumentoBussines
     */
    public AnexarDocumentoBussines getAnexarDocumentoBussines() {
        return anexarDocumentoBussines;
    }

    /**
     * @param anexarDocumentoBussines anexarDocumentoBussines a fijar
     */
    public void setAnexarDocumentoBussines(AnexarDocumentoBussines anexarDocumentoBussines) {
        this.anexarDocumentoBussines = anexarDocumentoBussines;
    }

    /**
     * @return eliminarVisible
     */
    public boolean isEliminarVisible() {
        return eliminarVisible;
    }

    /**
     * @param eliminarVisible eliminarVisible a fijar
     */
    public void setEliminarVisible(boolean eliminarVisible) {
        this.eliminarVisible = eliminarVisible;
    }

    /**
     * @return listaDocumentos
     */
    public List<DocumentoOficialDTO> getListaDocumentos() {
        return listaDocumentos;
    }

    /**
     * @param listaDocumentos listaDocumentos a fijar
     */
    public void setListaDocumentos(List<DocumentoOficialDTO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    /**
     * @return numFolio
     */
    public String getNumFolio() {
        return numFolio;
    }

    /**
     * @param numFolio numFolio a fijar
     */
    public void setNumFolio(String numFolio) {
        this.numFolio = numFolio;
    }

    /**
     * @return descripcionTipoDoc
     */
    public String getDescripcionTipoDoc() {
        return descripcionTipoDoc;
    }

    /**
     * @param descripcionTipoDoc descripcionTipoDoc a fijar
     */
    public void setDescripcionTipoDoc(String descripcionTipoDoc) {
        this.descripcionTipoDoc = descripcionTipoDoc;
    }

    /**
     * @return listaDocumentosOficiales
     */
    public List<DocumentoOficialDTO> getListaDocumentosOficiales() {
        return listaDocumentosOficiales;
    }

    /**
     * @param listaDocumentosOficiales listaDocumentosOficiales a fijar
     */
    public void setListaDocumentosOficiales(List<DocumentoOficialDTO> listaDocumentosOficiales) {
        this.listaDocumentosOficiales = listaDocumentosOficiales;
    }

    /**
     * @return cveTipoDoc
     */
    public String getCveTipoDoc() {
        return cveTipoDoc;
    }

    /**
     * @param cveTipoDoc cveTipoDoc a fijar
     */
    public void setCveTipoDoc(String cveTipoDoc) {
        this.cveTipoDoc = cveTipoDoc;
    }

    /**
     * @return messagesRedirect
     */
    public String getMessagesRedirect() {
        return messagesRedirect;
    }

    /**
     * @param messagesRedirect messagesRedirect a fijar
     */
    public void setMessagesRedirect(String messagesRedirect) {
        this.messagesRedirect = messagesRedirect;
    }

    /**
     * @return registrosEliminar
     */
    public DocumentoOficialDTO[] getRegistrosEliminar() {
        return registrosEliminar != null ? (DocumentoOficialDTO[]) registrosEliminar.clone() : null;
    }

    /**
     * @param registrosEliminar registrosEliminar a fijar
     */
    public void setRegistrosEliminar(DocumentoOficialDTO[] registrosEliminarArray) {
        if (registrosEliminarArray != null) {
            this.registrosEliminar = registrosEliminarArray.clone();
        } else {
            this.registrosEliminar = null;
        }
    }

    /**
     * @return anexarDocumentoValidator
     */
    public AnexarDocumentoValidator getAnexarDocumentoValidator() {
        return anexarDocumentoValidator;
    }

    /**
     * @param anexarDocumentoValidator anexarDocumentoValidator a fijar
     */
    public void setAnexarDocumentoValidator(AnexarDocumentoValidator anexarDocumentoValidator) {
        this.anexarDocumentoValidator = anexarDocumentoValidator;
    }

    /**
     * @return idNotificacion
     */
    public Long getIdNotificacion() {
        return idNotificacion;
    }

    /**
     * @param idNotificacion idNotificacion a fijar
     */
    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    /**
     * @return blnAutorizacion
     */
    public Boolean getBlnAutorizacion() {
        return blnAutorizacion;
    }

    /**
     * @param blnAutorizacion blnAutorizacion a fijar
     */
    public void setBlnAutorizacion(Boolean blnAutorizacion) {
        this.blnAutorizacion = blnAutorizacion;
    }

    public RequerimientoDTO getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(RequerimientoDTO requerimiento) {
        this.requerimiento = requerimiento;
    }

    public RegistrarFechaNotificacionBusiness getRegistrarFechaNotificacionBusiness() {
        return registrarFechaNotificacionBusiness;
    }

    public void setRegistrarFechaNotificacionBusiness(
            RegistrarFechaNotificacionBusiness registrarFechaNotificacionBusiness) {
        this.registrarFechaNotificacionBusiness = registrarFechaNotificacionBusiness;
    }

    @Override
    public BaseCloudBusiness getBusinessBean() {
        // TODO Auto-generated method stub
        return anexarDocumentoBussines;
    }

    @Override
    public List<DocumentoOficialDTO> getListaDocumentosAdjuntados() {
        return getListaDocumentosOficiales();
    }

    public Boolean getBlnFechaNotifDesactivo() {
        return blnFechaNotifDesactivo;
    }

    public void setBlnFechaNotifDesactivo(Boolean blnFechaNotifDesactivo) {
        this.blnFechaNotifDesactivo = blnFechaNotifDesactivo;
    }

    /**
     * @return the maxDate
     */
    public Date getMaxDate() {
        return maxDate != null ? (Date) maxDate.clone() : null;
    }

    /**
     * @param maxDate the maxDate to set
     */
    public void setMaxDate(Date maxDate) {
        if (maxDate != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(maxDate);
            this.maxDate = cal.getTime();
        } else {
            this.maxDate = null;
        }
    }

    /**
     * @return the minDate
     */
    public Date getMinDate() {
        return minDate != null ? (Date) minDate.clone() : null;
    }

    /**
     * @param minDate the minDate to set
     */
    public void setMinDate(Date minDate) {
        if (minDate != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(minDate);
            this.minDate = cal.getTime();
        } else {
            this.minDate = null;
        }
    }

}
