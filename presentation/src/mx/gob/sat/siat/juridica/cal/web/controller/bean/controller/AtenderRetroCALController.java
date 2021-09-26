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
package mx.gob.sat.siat.juridica.cal.web.controller.bean.controller;

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
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoProcesoFirma;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.FirmarTareaBussines;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.BaseCloudController;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.DescargarDocumentoController;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.business.AtenderRetroCALBussines;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AnexarDocumentoBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoOficialDataModel;
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
public class AtenderRetroCALController extends BaseCloudController<DocumentoOficialDTO> {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = 1L;

    /** DTO que representa los datos de una solicitud */
    private SolicitudDTO solicitud;

    /** Archivo para descarga */
    private DefaultStreamedContent archivoDescarga;
    /** Data model de documentos adjuntados */
    private DocumentoOficialDataModel documentoDataModel;
    private RequerimientoDTO requerimientoDTO;
    /** Bussines para adjuntar documentos */
    @ManagedProperty(value = "#{atenderRetroCALBussines}")
    private AtenderRetroCALBussines atenderRetroCALBussines;
    /** Validator para adjuntar documentos */
    @ManagedProperty(value = "#{atenderRequerimientoValidator}")
    private AtenderRequerimientoValidator atenderRequerimientoValidator;
    @ManagedProperty(value = "#{atosBandejaTareaDTO}")
    private DatosBandejaTareaDTO datosBandejaTareaDTO;
    /** Arreglo de documentos seleccionados a eliminar */
    private DocumentoOficialDTO[] registrosEliminar;

    /** Lista de documentos oficiales adjuntados */
    private List<DocumentoOficialDTO> listaDocumentosOficiales = new ArrayList<DocumentoOficialDTO>();

    /** Bussines para adjuntar documentos */
    @ManagedProperty(value = "#{anexarDocumentoBussines}")
    private transient AnexarDocumentoBussines anexarDocumentoBussines;
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
     * Controller para manejar la generacion de documentos en
     * pantalla.
     */
    @ManagedProperty(value = "#{generarDocumentosHelper}")
    private GenerarDocumentosHelper generarDocumentosHelper;

    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;
    
    @ManagedProperty(value = "#{araValidadorHelper}")
    private AraValidadorHelper araValidadorHelper;

    private boolean eliminarVisible;

    private String userID;

    /** DTO que representa lo firma de la solicitud */
    private FirmaDTO firma;

    private String firmaDigital;

    private String nombreDocumento;

    private String cadenaOriginal;

    /**
     * Clave de tipo de documento.
     */
    private String cveTipoDoc;

    /**
     * Propiedad que representa el numero de folio del documento.
     */
    private String numFolio;
    /**
     * Descripcion del tipo de documento
     */
    private String descripcionTipoDoc;

    /**
     * Controller para manejar la operacion de atender requerimiento
     * en pantalla.
     */
    public AtenderRetroCALController() {}

    /**
     * Metodo para preparar y atender el requerimiento.
     * 
     * @param datosBandeja
     */
    @PostConstruct
    public void prepararAtenderRequerimiento() {
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));
        setRequerimientoDTO(new RequerimientoDTO());
        setRequerimientoDTO(getAtenderRetroCALBussines().preparaAtenderRequerimiento(
                getDatosBandejaTareaDTO().getNumeroAsunto(), getDatosBandejaTareaDTO().getTipoTramite()));
        setListaDocumentosOficiales(new ArrayList<DocumentoOficialDTO>());
        setDocumentoDataModel(new DocumentoOficialDataModel(getListaDocumentosOficiales()));
        if (getFlash().get(VistaConstantes.FIRMA) == null) {
            getAnexarDocumentoBussines().eliminarDocumentosOficialesAnexadosPorTipo(
                    getDatosBandejaTareaDTO().getNumeroAsunto(), TipoDocumentoOficial.OFICIO_ATENCION_RETRO.getClave());
        }
        setCadenaOriginal((String) getFlash().get("cadenaOriginal"));
        setFirma((FirmaDTO) getFlash().get("firma"));
    }

    /**
     * M&eacute;todo para adjuntar un documento
     */
    public void anexarDocumentoNube() {
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        DocumentoOficialDTO documento = obtenerDocumentoOficialDTO(TipoDocumentoOficial.OFICIO_ATENCION_RETRO);
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
        FacesMessage msg = new FacesMessage("", "Documento adjuntado correctamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        getListaDocumentosOficiales().add(documento);

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
        setDescripcionTipoDoc("requerimientoAutoridadTemporal");
    }

    /**
     * Metodo que reinicia el data model de documento.
     */
    public void reiniciarDataModel() {

        setDocumentoDataModel(null);
    }

    /**
     * M&eacute;todo para eliminar documentos seleccionados.
     */
    public void eliminarDocumentos(ActionEvent event) {
        for (DocumentoOficialDTO documento : getRegistrosEliminar()) {
            getListaDocumentosOficiales().remove(documento);
            if (documento.getIdDocumentoOficial() != 0) {
                getAtenderRetroCALBussines().eliminaDocumento(documento.getIdDocumentoOficial());
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

        InputStream input = getAnexarDocumentoBussines().descargarDocumento(documento);
        HttpServletResponse response =
                (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

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
        if (getListaDocumentosOficiales() != null && getListaDocumentosOficiales().size() > 0) {
            getAtenderRetroCALBussines().guardarDocumentosAutorizarRequerimiento(getListaDocumentosOficiales(),
                    getDatosBandejaTareaDTO().getNumeroAsunto(), getDatosBandejaTareaDTO().getIdRequerimiento());

            prepararFirmaSolicitud();
            getFlash().put("datosBandejaDTO", getDatosBandejaTareaDTO());
            getFlash().put("cadenaOriginal", getCadenaOriginal());
            getFlash().put("firma", getFirma());

            ConfigurableNavigationHandler configurableNavigationHandler =
                    (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                            .getNavigationHandler();
            getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
            getFlash().put(VistaConstantes.FIRMA, "true");
            configurableNavigationHandler.performNavigation(UrlFirma.PAGINA_FIRMA_ATENDER_REQUERIMIENTO_AUTORIDAD
                    + "?faces-redirect=true");
        }
        else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                            .getString("vuj.requerimiento.adjuntarDoc.reqInformacion")));
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
        String urlforward = null;
        setFirma(new FirmaDTO(new Date()));
        setUserID(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userID")
                .toString());

        try {
            if (getUserID().equalsIgnoreCase(getUserProfile().getRfc())) {
                sNumSerie = getAraValidadorHelper().getNumSerie(FacesContext
                        .getCurrentInstance().getExternalContext()
                        .getRequestParameterMap().get("numeroSerie"));

                if (getAraValidadorHelper().validarRfcNumSerie(sNumSerie, getUserProfile().getRfc()) 
                        && getAraValidadorHelper().getEdoCertificado(sNumSerie)) {
                    List<Long> idsDoc = null;
                    setFirmaDigital(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                            .get("firmaDigital").toString());

                    firma.setSello(getFirmaDigital());
                    firma.setRfcUsuario(getUserProfile().getRfc());
                    firma.setCveRol(getUserProfile().getRol());
                    firma.setCadenaOriginal(getCadenaOriginal());
                    firma.setCveProceso(TipoProcesoFirma.AT_REQ_AUT.getClave());
                    firma.setCertificado(sNumSerie);

                    try {
                        getAtenderRetroCALBussines().sellarDocumentosAtencion(getDatosBandejaTareaDTO().getNumeroAsunto());
                        FirmaDTO firmaSelladora
                                = getAtenderRetroCALBussines().obtenSelloAtenderRetroSIAT(
                                        getDatosBandejaTareaDTO().getNumeroAsunto(), firma.getFechaFirma(),
                                        this.getUserProfile().getRfc());

                        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
                        idsDoc
                                = getGenerarDocumentosHelper().generarDocumentosRequerimientoAutoridad(getDatosBandejaTareaDTO(),
                                        TipoAcuse.ATREQ.getClave(), firma.getCadenaOriginal(), firma.getSello(),
                                        firmaSelladora.getCadenaOriginal(), firmaSelladora.getSello());
                        getLogger().debug(getDatosBandejaTareaDTO().toString());
                        getAtenderRetroCALBussines().firmaAtender(getDatosBandejaTareaDTO(),
                                getDatosBandejaTareaDTO().getIdRequerimiento(), this.getUserProfile().getRfc(), firma,
                                getDatosBandejaTareaDTO().getNumeroAsunto());
                    } catch (BusinessException e) {
                        getLogger().error("", e);
                    }
                    descargarDocumentoController.getDatosBandejaTareaDTO().setNumeroAsunto(
                            getDatosBandejaTareaDTO().getNumeroAsunto());
                    descargarDocumentoController.obtenerDocumentosByIdDoc(idsDoc);

                    FacesMessage msg
                            = new FacesMessage("", "El requerimiento de informaci\u00f3n para el asunto con el n\u00famero "
                                    + getDatosBandejaTareaDTO().getNumeroAsunto() + " ha sido atendido");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    urlforward = LoginConstante.DESCARGA_DOCUMENTO;
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", 
                                MessageFormat.format(AraConstantes.MENSAJE_LLAVE_SERIE_INCORRECTO, getUserProfile().getRfc())));
            }
        } catch (SgiARAException ex) {
            getLogger().error("Ocurrio un error al firmar AtenderRetroCALController.firmar {0}", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } catch (Exception ex) {
            getLogger().error("Ocurrio un error al firmar AtenderRetroCALController.firmar {0}", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurri\u00F3 un error al firmar.", ""));
        }

        return urlforward;
    }

    /**
     * M&eacute;todo para preparar la cadena original de la firma de
     * la atenci&oacute;n de la retroalimentaci&oacute;n
     */

    public void prepararFirmaSolicitud() {
        setFirma(new FirmaDTO(new Date()));
        setCadenaOriginal(getAtenderRetroCALBussines()
                .generaCadenaOriginal(getDatosBandejaTareaDTO().getNumeroAsunto(), getFirma().getFechaFirma(),
                        this.getUserProfile().getRfc()));
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
     * @param solicitud
     *            a fijar
     */
    public void setSolicitud(SolicitudDTO solicitud) {
        this.solicitud = solicitud;
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
     *            a fijar
     */
    public void setArchivoDescarga(DefaultStreamedContent archivoDescarga) {
        this.archivoDescarga = archivoDescarga;
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
     * @param documentoDataModel
     *            el documentoDataModel a fijar.
     */
    public void setDocumentoDataModel(DocumentoOficialDataModel documentoDataModel) {
        this.documentoDataModel = documentoDataModel;
    }

    /**
     * 
     * @return AtenderRetroCALBussines
     */
    public AtenderRetroCALBussines getAtenderRetroCALBussines() {
        return atenderRetroCALBussines;
    }

    /**
     * 
     * @param atenderRequerimientoBussines
     *            el atenderRequerimientoBussiness a fijar
     */
    public void setAtenderRetroCALBussines(AtenderRetroCALBussines atenderRetroCALBussines) {
        this.atenderRetroCALBussines = atenderRetroCALBussines;
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
     * @param atenderRequerimientoValidator
     *            el atenderRequerimientoValidator a fijar.
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
     * @param datosBandejaTareaDTO
     *            los datosBandejaTareaDTO a fijar.
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
     * @param requerimientoDTO
     *            el requerimientoDTO a fijar.
     */
    public void setRequerimientoDTO(RequerimientoDTO requerimientoDTO) {
        this.requerimientoDTO = requerimientoDTO;
    }

    /**
     * 
     * @return registrosEliminar
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
     *            a fijar
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

    /**
     * @return the firmarTareaBussines
     */
    public FirmarTareaBussines getFirmarTareaBussines() {
        return firmarTareaBussines;
    }

    /**
     * @param firmarTareaBussines
     *            the firmarTareaBussines to set
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
     * @param descargarDocumentoController
     *            the descargarDocumentoController to set
     */
    public void setDescargarDocumentoController(DescargarDocumentoController descargarDocumentoController) {
        this.descargarDocumentoController = descargarDocumentoController;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID
     *            the userID to set
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
     * @param anexarDocumentoBussines
     *            the anexarDocumentoBussines to set
     */
    public void setAnexarDocumentoBussines(AnexarDocumentoBussines anexarDocumentoBussines) {
        this.anexarDocumentoBussines = anexarDocumentoBussines;
    }

    /**
     * @return the generarDocumentosHelper
     */
    public GenerarDocumentosHelper getGenerarDocumentosHelper() {
        return generarDocumentosHelper;
    }

    /**
     * @param generarDocumentosHelper
     *            the generarDocumentosHelper to set
     */
    public void setGenerarDocumentosHelper(GenerarDocumentosHelper generarDocumentosHelper) {
        this.generarDocumentosHelper = generarDocumentosHelper;
    }

    /**
     * @return the cveTipoDoc
     */
    public String getCveTipoDoc() {
        return cveTipoDoc;
    }

    /**
     * @param cveTipoDoc
     *            the cveTipoDoc to set
     */
    public void setCveTipoDoc(String cveTipoDoc) {
        this.cveTipoDoc = cveTipoDoc;
    }

    public boolean isEliminarVisible() {
        return eliminarVisible;
    }

    public void setEliminarVisible(boolean eliminarVisible) {
        this.eliminarVisible = eliminarVisible;
    }

    /**
     * @return the numFolio
     */
    public String getNumFolio() {
        return numFolio;
    }

    /**
     * @param numFolio
     *            the numFolio to set
     */
    public void setNumFolio(String numFolio) {
        this.numFolio = numFolio;
    }

    /**
     * @return the descripcionTipoDoc
     */
    public String getDescripcionTipoDoc() {
        return descripcionTipoDoc;
    }

    /**
     * @param descripcionTipoDoc
     *            the descripcionTipoDoc to set
     */
    public void setDescripcionTipoDoc(String descripcionTipoDoc) {
        this.descripcionTipoDoc = descripcionTipoDoc;
    }

    /**
     * @return the listaDocumentosOficiales
     */
    public List<DocumentoOficialDTO> getListaDocumentosOficiales() {
        return listaDocumentosOficiales;
    }

    /**
     * @param listaDocumentosOficiales
     *            the listaDocumentosOficiales to set
     */
    public void setListaDocumentosOficiales(List<DocumentoOficialDTO> listaDocumentosOficiales) {
        this.listaDocumentosOficiales = listaDocumentosOficiales;
    }

    /**
     * @return the nombreDocumento
     */
    public String getNombreDocumento() {
        return nombreDocumento;
    }

    /**
     * @param nombreDocumento
     *            the nombreDocumento to set
     */
    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    /**
     * @return the cadenaOriginal
     */
    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    /**
     * @param cadenaOriginal
     *            the cadenaOriginal to set
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
     * @param firmaDigital
     *            the firmaDigital to set
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
     * @param firma
     *            the firma to set
     */
    public void setFirma(FirmaDTO firma) {
        this.firma = firma;
    }

    @Override
    public BaseCloudBusiness getBusinessBean() {
        return atenderRetroCALBussines;
    }

    @Override
    public List<DocumentoOficialDTO> getListaDocumentosAdjuntados() {
        return getListaDocumentosOficiales();
    }

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
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

}
