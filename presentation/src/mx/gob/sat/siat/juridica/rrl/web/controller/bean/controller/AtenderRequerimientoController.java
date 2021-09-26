/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
/**
 * 
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;

import mx.gob.sat.sgi.SgiCripto.SgiARAException;
import mx.gob.sat.siat.juridica.ara.constantes.AraConstantes;
import mx.gob.sat.siat.juridica.ara.util.AraValidadorHelper;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoAcuse;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRequerimiento;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;
import mx.gob.sat.siat.juridica.base.util.exception.BaseBussinessException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.FirmarTareaBussines;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.BaseCloudController;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.DescargarDocumentoController;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.AnexarDocumentosConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AnexarDocumentoBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AtenderRequerimientoBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.CapturaSolicitudBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoDataModel;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility.GenerarDocumentosHelper;
import mx.gob.sat.siat.juridica.rrl.web.util.validador.AtenderRequerimientoValidator;

/**
 * Bean que implementan la l&oacute;gica de negocio para atender un
 * requerimiento
 * 
 * @author Softtek
 * 
 */
@ManagedBean
@ViewScoped
public class AtenderRequerimientoController extends BaseCloudController<DocumentoDTO> {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = 1L;

    /** DTO que representa los datos de una solicitud */
    private SolicitudDTO solicitud;
    /** Lista de documentos adjuntados */
    private List<DocumentoDTO> listaDocumentos = new ArrayList<DocumentoDTO>();
    /** Archivo para descarga */
    private DefaultStreamedContent archivoDescarga;
    /** Data model de documentos adjuntados */
    private DocumentoDataModel documentoDataModel;
    private RequerimientoDTO requerimientoDTO;
    /** Bussines para adjuntar documentos */
    @ManagedProperty(value = "#{atenderRequerimientoBussines}")
    private AtenderRequerimientoBussines atenderRequerimientoBussines;
    /** Validator para adjuntar documentos */
    @ManagedProperty(value = "#{atenderRequerimientoValidator}")
    private AtenderRequerimientoValidator atenderRequerimientoValidator;
    @ManagedProperty(value = "#{atosBandejaTareaDTO}")
    private DatosBandejaTareaDTO datosBandejaTareaDTO;
    /** Arreglo de documentos seleccionados a eliminar */
    private DocumentoDTO[] registrosEliminar;

    /** Bussines para adjuntar documentos */
    @ManagedProperty(value = "#{anexarDocumentoBussines}")
    private transient AnexarDocumentoBussines anexarDocumentoBussines;

    @ManagedProperty(value = "#{capturaSolicitudBussines}")
    private CapturaSolicitudBussines capturaSolicitudBussines;

    @ManagedProperty(value = "#{araValidadorHelper}")
    private AraValidadorHelper araValidadorHelper;

    /**
     * @return the capturaSolicitudBussines
     */
    public CapturaSolicitudBussines getCapturaSolicitudBussines() {
        return capturaSolicitudBussines;
    }

    /**
     * @param capturaSolicitudBussines the capturaSolicitudBussines to set
     */
    public void setCapturaSolicitudBussines(CapturaSolicitudBussines capturaSolicitudBussines) {
        this.capturaSolicitudBussines = capturaSolicitudBussines;
    }

    /**
     * @return the araValidadorHelper
     */
    public AraValidadorHelper getAraValidadorHelper() {
        return araValidadorHelper;
    }

    /**
     * @param araValidadorHelper the araValidadorHelper to set
     */
    public void setAraValidadorHelper(AraValidadorHelper araValidadorHelper) {
        this.araValidadorHelper = araValidadorHelper;
    }

    /**
     * Propiedad que representa el bussines para firmar tarea.
     */
    @ManagedProperty(value = "#{firmarTareaBussines}")
    private FirmarTareaBussines firmarTareaBussines;
    /**
     * Controller para manejar la descarga de documentos en pantalla.
     */
    @ManagedProperty(value = "#{descargarDocumentoController}")
    private DescargarDocumentoController descargarDocumentoController;
    /**
     * Controller para manejar la generacion de documentos en pantalla.
     */
    @ManagedProperty(value = "#{generarDocumentosHelper}")
    private GenerarDocumentosHelper generarDocumentosHelper;

    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    private boolean eliminarVisible;

    /** DTO que representa lo firma de la solicitud */
    private FirmaDTO firma;

    private String cadenaOriginal;
    private String cadenaOriginalAcusePromocion;
    private String firmaDigital;

    private String userID;

    public boolean isEliminarVisible() {
        return eliminarVisible;
    }

    public void setEliminarVisible(boolean eliminarVisible) {
        this.eliminarVisible = eliminarVisible;
    }

    /**
     * Controller para manejar la operacion de atender requerimiento en pantalla.
     */
    public AtenderRequerimientoController() {
    }

    /**
     * M&eacute;todo para preparar la cadena original de la firma de la solicitud
     */

    public void prepararFirmaSolicitud() {
        setFirma(new FirmaDTO(new Date()));
        setCadenaOriginal(getAtenderRequerimientoBussines().generaCadenaOriginal(
                getDatosBandejaTareaDTO().getIdSolicitud(), getFirma().getFechaFirma()));
        setCadenaOriginalAcusePromocion(getAtenderRequerimientoBussines().generaCadenaOriginalPromocion(
                getDatosBandejaTareaDTO().getIdSolicitud(), getFirma().getFechaFirma()));
    }

    /**
     * Metodo para preparar y atender el requerimiento.
     * 
     * @param datosBandeja
     */
    @PostConstruct
    public void prepararAtenderRequerimiento() {
        setRequerimientoDTO(new RequerimientoDTO());
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get("datosBandejaDTO"));
        if (getDatosBandejaTareaDTO().getIdRequerimiento() != null) {
            setRequerimientoDTO(getAtenderRequerimientoBussines().obtenerRequerimientoporId(getDatosBandejaTareaDTO().getIdRequerimiento()));
        } else {
            setRequerimientoDTO(getAtenderRequerimientoBussines().preparaAtenderRequerimiento(
                    getDatosBandejaTareaDTO().getNumeroAsunto(), getDatosBandejaTareaDTO().getTipoTramite())
            );
        }
        if (getFlash().get(VistaConstantes.FIRMA) == null) {
            atenderRequerimientoBussines
                    .eliminaDocumentosSolicitudRequerimiento(getRequerimientoDTO().getIdRequerimiento());
        }

        setListaDocumentos(new ArrayList<DocumentoDTO>());
        setDocumentoDataModel(new DocumentoDataModel(getListaDocumentos()));
        setCadenaOriginal((String) getFlash().get("cadenaOriginal"));
        setCadenaOriginalAcusePromocion((String) getFlash().get("cadenaOriginalAcusePromocion"));
        setFirma((FirmaDTO) getFlash().get("firma"));
    }

    /**
     * M&eacute;todo para adjuntar un documento
     */

    public void anexarDocumentoNube() {
        DocumentoDTO documento = obtenerDocumentoDTO(AnexarDocumentosConstantes.TIPO_DOCUMENTO_REQUERIMIENTO,
                AnexarDocumentosConstantes.DESC_TIPO_DOCUMENTO_REQUERIMIENTO);
        if (validaDocumentoAzure(documento, getListaDocumentos())) {
            verificaDocumentoAzure(documento);
        } else {
            getLogger().error("Archivo invalido");
            getLogger().error("Error: " + documento.getNombreDocumento());
            String error = documento.getNombreDocumento();
            if (error.substring(error.length() - 4, error.length()).equalsIgnoreCase(".pdf")) {
                error = "S\u00F3lo es posible adjuntar un archivo a la vez";
            }
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", error);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    private boolean validaDocumentoAzure(DocumentoDTO documento, List<DocumentoDTO> list) {
        boolean ban = true;
        if (documento.getRutaAzure() != null && !documento.getRutaAzure().equals("ERROR")) {
            getLogger().debug("Ruta Azure: " + documento.getRutaAzure());
            for (DocumentoDTO doc : list) {
                if (doc.getRutaAzure().equals(documento.getRutaAzure())) {
                    ban = false;
                    break;
                }
            }
        } else {
            ban = false;
        }
        return ban;
    }

    private void verificaDocumentoAzure(DocumentoDTO documento) {
        FacesMessage msg = new FacesMessage("", "Documento adjuntado correctamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        getListaDocumentos().add(documento);
        setDocumentoDataModel(new DocumentoDataModel(getListaDocumentos()));
    }

    /**
     * M&eacute;todo para eliminar documentos seleccionados.
     */
    public void eliminarDocumentos(ActionEvent event) {
        for (DocumentoDTO documento : getRegistrosEliminar()) {
            getListaDocumentos().remove(documento);
            if (documento.getIdDocumentoSolicitud() != 0) {
                getAtenderRequerimientoBussines().eliminaDocumento(documento.getIdDocumentoSolicitud());
            }
        }
        setEliminarVisible(false);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El (Los) documento(s) ha(n) sido eliminado(s)"));
    }

    /**
     * M&eacute;todo para descargar un documento
     */
    public void descargarDocumento(ActionEvent event) throws IOException {
        DocumentoDTO documento = (DocumentoDTO) event.getComponent().getAttributes().get("documentoParametro");

        InputStream input = getAtenderRequerimientoBussines().descargarDocumento(documento);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
                .getResponse();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=" + documento.getNombre());
        IOUtils.copy(input, response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * M&eacute;todo para almacenar los documentos a la solicitud
     */
    public void guardarDocumentos() {
        String mensaje = "";
        if (getAtenderRequerimientoValidator().validarDocumentosAnexados(getListaDocumentos())) {
            try {
                getAtenderRequerimientoBussines().guardarDocumentosSolicitud(getDatosBandejaTareaDTO().getIdSolicitud(),
                        getRequerimientoDTO().getIdRequerimiento(), getListaDocumentos(),
                        getDatosBandejaTareaDTO().getTipoTramite(), getUserProfile().getRfc());
            } catch (BaseBussinessException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                        "Debe proporcionar todos los requisitos obligatorios del asunto"));
            }
            prepararFirmaSolicitud();
            getFlash().put("datosBandejaDTO", getDatosBandejaTareaDTO());
            getFlash().put("cadenaOriginal", getCadenaOriginal());
            getFlash().put("cadenaOriginalAcusePromocion", getCadenaOriginalAcusePromocion());
            getFlash().put("firma", getFirma());
            getFlash().put(VistaConstantes.FIRMA, Boolean.TRUE);
            ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext
                    .getCurrentInstance().getApplication().getNavigationHandler();

            configurableNavigationHandler
                    .performNavigation(UrlFirma.PAGINA_FIRMA_ATENDER_REQUERIMIENTO + "?faces-redirect=true");
        } else {

            if (getRequerimientoDTO().getClaveTipoRequerimiento().equals(TipoRequerimiento.PROMOVENTE_CAL.getClave())
                    || getRequerimientoDTO().getClaveTipoRequerimiento()
                            .equals(TipoRequerimiento.RETROALIMENTACION_CAL.getClave())) {

                mensaje = "Debe proporcionar un documento de cumplimentaci\u00f3n";

            } else {

                mensaje = errorMsg.getString("vuj.resol.adjuntar.documentos");
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", mensaje));
        }
    }

    /**
     * Metodo para la firma.
     * 
     * @return
     * @throws Exception
     */
    public String firmar() {
        String sNumSerie;
        String urlForward = null;
        List<Long> idsDoc = new ArrayList<Long>();
        RequerimientoDTO req = getRequerimientoDTO();
        req.setTramite(getRequerimientoDTO().getTramite());

        setUserID(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userID")
                .toString());
        try {
            if (getUserID().equalsIgnoreCase(getUserProfile().getRfc())) {
                sNumSerie = getAraValidadorHelper().getNumSerie(FacesContext.getCurrentInstance().getExternalContext()
                        .getRequestParameterMap().get("numeroSerie"));

                if (getAraValidadorHelper().validarRfcNumSerie(sNumSerie, getUserProfile().getRfc())
                        && getAraValidadorHelper().getEdoCertificado(sNumSerie)) {
                    setFirmaDigital(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                            .get("firmaDigital").toString());

                    firma.setSello(getFirmaDigital());
                    firma.setRfcUsuario(getUserProfile().getRfc());
                    firma.setCveRol(getUserProfile().getRol());
                    firma.setCadenaOriginal(getCadenaOriginal());
                    firma.setCertificado(sNumSerie);

                    try {
                        FirmaDTO firmaSelladora = getAtenderRequerimientoBussines().obtenSelloAtenderRequerimientoSIAT(
                                getDatosBandejaTareaDTO().getNumeroAsunto(), getDatosBandejaTareaDTO().getIdSolicitud(),
                                firma.getFechaFirma());
                        idsDoc = getGenerarDocumentosHelper().generarDocumentosPromocion(getDatosBandejaTareaDTO(),
                                TipoAcuse.ATREQ.getClave(), getCadenaOriginalAcusePromocion(), firma.getSello(),
                                firmaSelladora.getCadenaOriginal(), firmaSelladora.getSello());
                        getAtenderRequerimientoBussines().firmaAtender(getDatosBandejaTareaDTO(), req,
                                this.getUserProfile().getRfc(), firma, getDatosBandejaTareaDTO().getIdSolicitud());
                    } catch (BusinessException e) {
                        getLogger().error("Ocurrio un error AtenderRequerimientoController.firmar {0}", e);
                    }
                    descargarDocumentoController.getDatosBandejaTareaDTO()
                            .setNumeroAsunto(getDatosBandejaTareaDTO().getNumeroAsunto());
                    descargarDocumentoController.obtenerDocumentosByIdDoc(idsDoc);

                    FacesMessage msg = new FacesMessage("",
                            "El requerimiento de informaci\u00f3n para el asunto con n\u00famero "
                                    + getDatosBandejaTareaDTO().getNumeroAsunto() + " ha sido atendido");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                    urlForward = LoginConstante.DESCARGA_DOCUMENTO;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null,
                        MessageFormat.format(AraConstantes.MENSAJE_LLAVE_SERIE_INCORRECTO, getUserProfile().getRfc())));
            }
        } catch (SgiARAException ex) {
            getLogger().error("Ocurrio un error al firmar AtenderRequerimientoController.firmar {0}", ex);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        }

        return urlForward;
    }

    public void rowSelectCheckbox(SelectEvent event) {

        setEliminarVisible(true);

    }

    public void rowUnselectCheckbox(UnselectEvent event) {
        if (getRegistrosEliminar().length < 1) {
            setEliminarVisible(false);
        }
    }

    /**
     * 
     * @return solicitud
     */
    public SolicitudDTO getSolicitud() {
        return solicitud;
    }

    /**
     * 
     * @param solicitud a fijar
     */
    public void setSolicitud(SolicitudDTO solicitud) {
        this.solicitud = solicitud;
    }

    /**
     * 
     * @return listaDocumentos
     */
    public List<DocumentoDTO> getListaDocumentos() {
        return listaDocumentos;
    }

    /**
     * 
     * @param listaDocumentos a fijar
     */
    public void setListaDocumentos(List<DocumentoDTO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
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
     * @param archivoDescarga a fijar
     */
    public void setArchivoDescarga(DefaultStreamedContent archivoDescarga) {
        this.archivoDescarga = archivoDescarga;
    }

    /**
     * 
     * @return documentoDataModel
     */
    public DocumentoDataModel getDocumentoDataModel() {
        return documentoDataModel;
    }

    /**
     * 
     * @param documentoDataModel el documentoDataModel a fijar.
     */
    public void setDocumentoDataModel(DocumentoDataModel documentoDataModel) {
        this.documentoDataModel = documentoDataModel;
    }

    /**
     * 
     * @return atenderRequerimientoBussiness
     */
    public AtenderRequerimientoBussines getAtenderRequerimientoBussines() {
        return atenderRequerimientoBussines;
    }

    /**
     * 
     * @param atenderRequerimientoBussines el atenderRequerimientoBussiness a fijar
     */
    public void setAtenderRequerimientoBussines(AtenderRequerimientoBussines atenderRequerimientoBussines) {
        this.atenderRequerimientoBussines = atenderRequerimientoBussines;
    }

    /**
     * 
     * @return atenderRequerimientoValidator
     */
    public AtenderRequerimientoValidator getAtenderRequerimientoValidator() {
        return atenderRequerimientoValidator;
    }

    /**
     * 
     * @param atenderRequerimientoValidator el atenderRequerimientoValidator a
     *                                      fijar.
     */
    public void setAtenderRequerimientoValidator(AtenderRequerimientoValidator atenderRequerimientoValidator) {
        this.atenderRequerimientoValidator = atenderRequerimientoValidator;
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
     * @param datosBandejaTareaDTO los datosBandejaTareaDTO a fijar.
     */
    public void setDatosBandejaTareaDTO(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        this.datosBandejaTareaDTO = datosBandejaTareaDTO;
    }

    /**
     * 
     * @return requerimientoDTO
     */
    public RequerimientoDTO getRequerimientoDTO() {
        return requerimientoDTO;
    }

    /**
     * 
     * @param requerimientoDTO el requerimientoDTO a fijar.
     */
    public void setRequerimientoDTO(RequerimientoDTO requerimientoDTO) {
        this.requerimientoDTO = requerimientoDTO;
    }

    /**
     * 
     * @return registrosEliminar
     */
    public DocumentoDTO[] getRegistrosEliminar() {
        if (registrosEliminar != null) {
            DocumentoDTO[] arreglo = new DocumentoDTO[registrosEliminar.length];
            for (int i = 0; i < registrosEliminar.length; i++) {
                DocumentoDTO o = registrosEliminar[i];
                arreglo[i] = o;
            }
            return arreglo;
        } else {
            return null;
        }
    }

    /**
     * 
     * @param registrosEliminar a fijar
     */
    public void setRegistrosEliminar(DocumentoDTO[] registrosEliminarArray) {
        if (registrosEliminarArray != null) {
            DocumentoDTO[] registrosEliminarLocal = registrosEliminarArray.clone();
            DocumentoDTO[] arreglo = new DocumentoDTO[registrosEliminarLocal.length];
            for (int i = 0; i < registrosEliminarLocal.length; i++) {
                DocumentoDTO o = registrosEliminarLocal[i];
                arreglo[i] = o;
            }
            this.registrosEliminar = arreglo;
        } else {
            this.registrosEliminar = null;
        }
    }

    /**
     * @return the firmarTareaBussines
     */
    public FirmarTareaBussines getFirmarTareaBussines() {
        return firmarTareaBussines;
    }

    /**
     * @param firmarTareaBussines the firmarTareaBussines to set
     */
    public void setFirmarTareaBussines(FirmarTareaBussines firmarTareaBussines) {
        this.firmarTareaBussines = firmarTareaBussines;
    }

    /**
     * @return the descargarDocumentoController
     */
    public DescargarDocumentoController getDescargarDocumentoController() {
        return descargarDocumentoController;
    }

    /**
     * @param descargarDocumentoController the descargarDocumentoController to set
     */
    public void setDescargarDocumentoController(DescargarDocumentoController descargarDocumentoController) {
        this.descargarDocumentoController = descargarDocumentoController;
    }

    /**
     * @return the generarDocumentosHelper
     */
    public GenerarDocumentosHelper getGenerarDocumentosHelper() {
        return generarDocumentosHelper;
    }

    /**
     * @param generarDocumentosHelper the generarDocumentosHelper to set
     */
    public void setGenerarDocumentosHelper(GenerarDocumentosHelper generarDocumentosHelper) {
        this.generarDocumentosHelper = generarDocumentosHelper;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the anexarDocumentoBussines
     */
    public AnexarDocumentoBussines getAnexarDocumentoBussines() {
        return anexarDocumentoBussines;
    }

    /**
     * @param anexarDocumentoBussines the anexarDocumentoBussines to set
     */
    public void setAnexarDocumentoBussines(AnexarDocumentoBussines anexarDocumentoBussines) {
        this.anexarDocumentoBussines = anexarDocumentoBussines;
    }

    /**
     * @return the cadenaOriginal
     */
    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    /**
     * @param cadenaOriginal the cadenaOriginal to set
     */
    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    /**
     * @return the firmaDigital
     */
    public String getFirmaDigital() {
        return firmaDigital;
    }

    /**
     * @param firmaDigital the firmaDigital to set
     */
    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }

    /**
     * @return the firma
     */
    public FirmaDTO getFirma() {
        return firma;
    }

    /**
     * @param firma the firma to set
     */
    public void setFirma(FirmaDTO firma) {
        this.firma = firma;
    }

    @Override
    public BaseCloudBusiness getBusinessBean() {
        // TODO Auto-generated method stub
        return atenderRequerimientoBussines;
    }

    public String getCadenaOriginalAcusePromocion() {
        return cadenaOriginalAcusePromocion;
    }

    public void setCadenaOriginalAcusePromocion(String cadenaOriginalAcusePromocion) {
        this.cadenaOriginalAcusePromocion = cadenaOriginalAcusePromocion;
    }

    @Override
    public List<DocumentoDTO> getListaDocumentosAdjuntados() {
        return getListaDocumentos();
    }

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

}
