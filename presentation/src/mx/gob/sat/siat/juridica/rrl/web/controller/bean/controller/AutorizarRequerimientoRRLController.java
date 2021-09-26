/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
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
import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRequerimiento;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DatosNyVDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;
import mx.gob.sat.siat.juridica.base.util.constante.EnumeracionConstantes;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.FirmarBusiness;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.BaseCloudController;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.AutorizarConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.GeneralConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import mx.gob.sat.siat.juridica.rrl.util.constante.RequerimientoConstante;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AutorizarRequerimientoRRLBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoOficialDataModel;

/**
 * Bean que implementan la l&oacute;gica de negocio para la
 * autorizaci&oacute;n de requerimiento.
 * 
 * @author Softtek
 * 
 */
@ManagedBean(name = "autorizarRequerimientoRRLController")
@ViewScoped
public class AutorizarRequerimientoRRLController extends BaseCloudController<DocumentoOficialDTO> {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = -6209469065778736069L;

    /** DTO que representa los datos del requerimiento */
    private RequerimientoDTO requerimiento;
    /** DTO que representa los datos proporcionados por la bandeja */
    private DatosBandejaTareaDTO datosBandeja;
    /** Lista de los tipos de requerimiento */
    private List<CatalogoDTO> listaTiposRequerimiento = new ArrayList<CatalogoDTO>();
    /**
     * Lista de las autoridades a la que se solicitar&aacute; el
     * requerimiento
     */
    private List<CatalogoDTO> listaAutoridades = new ArrayList<CatalogoDTO>();
    /** Lista de documentos a adjuntar */
    private List<DocumentoOficialDTO> listaDocumentos = new ArrayList<DocumentoOficialDTO>();
    /** Modelo de datos de documentos oficiales */
    private DocumentoOficialDataModel documentoOficialDataModel;
    /** Archivo para descarga */
    private DefaultStreamedContent archivoDescarga;
    /** Arreglo de documentos seleccionados a eliminar */
    private DocumentoOficialDTO[] registrosEliminar;
    /** Datos de la firma */
    private String firmaDigital;
    /** DTO que representa lo firma de la solicitud */
    private FirmaDTO firma;
    @ManagedProperty(value = "#{firmarBusiness}")
    private FirmarBusiness firmarBusiness;

    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    private String userID;
    
    private String numeroSerie;
    /**
     * Bean para conectar con el backend, atiende todas las peticiones
     * de la autorizaci&oacute;n del requerimiento.
     */
    @ManagedProperty("#{autorizarRequerimientoRRLBussines}")
    private AutorizarRequerimientoRRLBussines autorizarRequerimientoRRLBussines;
    /**
     * Bean para obtener los mensajes
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    
    @ManagedProperty(value = "#{araValidadorHelper}")
    private AraValidadorHelper araValidadorHelper;

    private boolean eliminarVisible;

    private List<DocumentoDTO> docComplementarios;

    private boolean nuevoDocumento;

    /**
     * Atributo Boolean que activa o desactiva btn adjuntar para
     * persona moral
     */
    private boolean bndAnexar;

    /**
     * Atributo DatosNyVDTO
     */
    private DatosNyVDTO datosNyV = new DatosNyVDTO();

    public static final String FACES_REDIRECT = "?faces-redirect=true";

    public boolean isEliminarVisible() {
        return eliminarVisible;
    }

    public void setEliminarVisible(boolean eliminarVisible) {
        this.eliminarVisible = eliminarVisible;
    }

    /**
     * Post-Constructor para inicializar la lista de tipos de
     * requerimiento y y las autoridades a la que se solicitar&aacute;
     * el requerimiento.
     */
    @PostConstruct
    public void iniciar() {
        setListaTiposRequerimiento(new ArrayList<CatalogoDTO>());
        setListaAutoridades(new ArrayList<CatalogoDTO>());
        prepararAutorizarRequerimiento();
        setBndAnexar(true);
    }

    /**
     * M&eacute;todo para preparar la cadena original de la firma de
     * la solicitud
     */
    public void prepararFirmaSolicitud() {
        setFirma(new FirmaDTO(new Date()));
        getFirma().setCadenaOriginal(
                getAutorizarRequerimientoRRLBussines().generaCadenaOriginal(getRequerimiento().getIdRequerimiento(),
                        getFirma().getFechaFirma()));
    }

    public String procesaFirma() {
        getLogger().debug(
                "firma: "
                        + FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                                .get("firmaDigital"));
        setFirmaDigital(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                .get("firmaDigital").toString());
        // Hacer algo con la firma
        getLogger().debug(getFirmaDigital());
        // regresar pagina de firmado correcto
        return "";
    }

    /**
     * M&eacute;todo para preparar la autorizaci&oacute;n del
     * requerimiento.
     */
    public void prepararAutorizarRequerimiento() {
        setRequerimiento(new RequerimientoDTO());
        setFirma((FirmaDTO) getFlash().get("firma"));
        setDatosBandeja((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));
        String tipoReq;
        if (datosBandeja.getTipoTramite().equals(DiscriminadorConstants.T1_CLASIFICACION_ARANCELARIA)) {
            tipoReq = EnumeracionConstantes.TIPOS_REQUERIMIENTO;
            setListaAutoridades(getAutorizarRequerimientoRRLBussines().obtenerAutoridadesTodas());
        }
        else {
            tipoReq = EnumeracionConstantes.TIPOS_REQUERIMIENTO_T2;
            setListaAutoridades(getAutorizarRequerimientoRRLBussines().obtenerAutoridadesTodas());
        }
        setListaTiposRequerimiento(getAutorizarRequerimientoRRLBussines().obtenerTiposDeRequerimiento(tipoReq));
        if (datosBandeja.getIdRequerimiento() != null) {
            setRequerimiento(getAutorizarRequerimientoRRLBussines().prepararAutorizarRequerimientoByIdRequerimiento(
                    getDatosBandeja().getIdRequerimiento(), getUserProfile()));
        }
        else {
            setRequerimiento(getAutorizarRequerimientoRRLBussines().prepararAutorizarRequerimiento(
                    getDatosBandeja().getNumeroAsunto(), getUserProfile(), getDatosBandeja().getTipoTramite()));
        }
    }

    /**
     * M&eacute;todo para preparar los documentos que se adjuntaran en
     * la autorizaci&oacute;n del requerimiento.
     */
    public void prepararDocumentosRequerimientos() {
        boolean continuar = validaCampoRequerido(requerimiento);
        String documentos = null;
        if (continuar) {
            docComplementarios =
                    autorizarRequerimientoRRLBussines.obtenerDocumentosComplementarios(getDatosBandeja()
                            .getIdSolicitud());

            if (docComplementarios == null || docComplementarios.isEmpty()) {

                documentos = continuarPrepararDocumentosRequerimientos();
                getAutorizarRequerimientoRRLBussines()
                        .eliminaDocumentosRequerimiento(datosBandeja.getIdRequerimiento());
            }
            else {

                nuevoDocumento = true;
                getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandeja());
                documentos = null;
            }

            if (documentos != null) {
                ConfigurableNavigationHandler configurableNavigationHandler =
                        (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                                .getNavigationHandler();

                configurableNavigationHandler.performNavigation(documentos + FACES_REDIRECT);
            }
        }
    }

    public String continuarPrepararDocumentosRequerimientos() {

        requerimiento.setRfcAutorizador(datosBandeja.getRfcSolicitante());
        getAutorizarRequerimientoRRLBussines().actualizaRequerimiento(getRequerimiento());
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandeja());
        getFlash().put("datos_nyv", getDatosNyV());
        String direccion = null;
        if (getRequerimiento().getClaveTipoRequerimiento().equals(TipoRequerimiento.CONTRIBUYENTE.getClave())
                || getRequerimiento().getClaveTipoRequerimiento().equals(TipoRequerimiento.PROMOVENTE_CAL.getClave())) {
            if (getDatosBandeja().getRfcSolicitante().length() == NumerosConstantes.DOCE) {
                direccion = RequerimientoConstante.ANEXAR_DOCS_AUTORIZAR_REQUERIMIENTO_RRL;
            }
            else {
                direccion = RequerimientoConstante.ANEXAR_DOCS_AUTORIZAR_REQUERIMIENTO_RRL;
            }
        }
        else {
            direccion = RequerimientoConstante.ANEXAR_DOCS_AUTORIZAR_REQUERIMIENTO_RRL;
        }
        return direccion;
    }

    public String regresarPaginaAutorizarRequerimiento() {
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandeja());
        getFlash().put("datos_nyv", getDatosNyV());
        return AutorizarConstantes.AUTORIZAR_REQUERIMIENTO;
    }

    /**
     * M&eacute;todo para preparar los documentos que se adjuntaran en
     * la autorizaci&oacute;n del requerimiento para tramite 2.
     */
    public void prepararDocumentosRequerimientosCal(ActionEvent actionEvent) {
        boolean continuar = validaCampoRequerido(requerimiento);
        if (continuar) {
            requerimiento.setRfcAutorizador(datosBandeja.getRfcSolicitante());
            getAutorizarRequerimientoRRLBussines().actualizaRequerimiento(getRequerimiento());
            getAutorizarRequerimientoRRLBussines().eliminaDocumentosRequerimiento(datosBandeja.getIdRequerimiento());
            setListaDocumentos(new ArrayList<DocumentoOficialDTO>());
            setDocumentoOficialDataModel(new DocumentoOficialDataModel(getListaDocumentos()));
            getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandeja());
            ConfigurableNavigationHandler configurableNavigationHandler =
                    (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                            .getNavigationHandler();

            configurableNavigationHandler
                    .performNavigation(RequerimientoConstante.ANEXAR_DOCS_AUTORIZAR_REQUERIMIENTO_RRL
                            + FACES_REDIRECT);

        }
    }

    /**
     * M&eacute;todo para adjuntar archivo.
     */
    public void rechazarRequerimiento() {
        getAutorizarRequerimientoRRLBussines().rechazarRequerimiento(getDatosBandeja(), getUserProfile().getRfc(),
                getRequerimiento().getIdRequerimiento());

        String[] param = new String[1];
        param[0] = getDatosBandeja().getNumeroAsunto();
        if (getRequerimiento().getClaveUnidadAdministrativa() != null
                && !getDatosBandeja().getNumeroAsunto().startsWith("RRL")) {
            getFlash().put(
                    GeneralConstantes.MENSAJE_REDIRECT,
                    MessageFormat.format(messages.getString("vuj.autorizar.rechazarRequerimientoAutoridad"),
                            (Object[]) param));
        }
        else {
            getFlash().put(GeneralConstantes.MENSAJE_REDIRECT,
                    MessageFormat.format(messages.getString("vuj.autorizar.rechazarRequerimiento"), (Object[]) param));
        }

        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        configurableNavigationHandler.performNavigation(LoginConstante.BANDEA_JSF + FACES_REDIRECT);
    }

    /**
     * M&eacute;todo para adjuntar un documento
     */

    public void anexarDocumentoNube() {
        DocumentoOficialDTO documento = obtenerDocumentoOficialDTO(TipoDocumentoOficial.OFICIO_REQUERIMIENTO);
        if(validaDocumentoAzure(documento,getListaDocumentos())) {
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
        FacesMessage msg = new FacesMessage("", "Documento adjuntado correctamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        getListaDocumentos().add(documento);
        setDocumentoOficialDataModel(new DocumentoOficialDataModel(getListaDocumentos()));
        if (getRequerimiento().getClaveTipoRequerimiento().equals(TipoRequerimiento.CONTRIBUYENTE.getClave())
                || getRequerimiento().getClaveTipoRequerimiento().equals(TipoRequerimiento.PROMOVENTE_CAL.getClave())) {
            setBndAnexar(false);
        }
    }
    
    /**
     * M&eacute;todo para eliminar documentos seleccionados.
     */
    public void eliminarDocumentos(ActionEvent event) {
        if (getRegistrosEliminar() != null && getRegistrosEliminar().length > 0) {
            for (DocumentoOficialDTO documento : getRegistrosEliminar()) {
                getListaDocumentos().remove(documento);
                if (documento.getIdDocumentoOficial() != 0) {
                    getAutorizarRequerimientoRRLBussines().eliminaDocumentoOficial(documento.getIdDocumentoOficial());
                }
            }
            setEliminarVisible(false);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "", messages.getString("vuj.documento.eliminar")));
        }
        setBndAnexar(true);
    }

    /**
     * M&eacute;todo para descargar documento.
     */
    public void descargarDocumento(ActionEvent event) throws IOException {
        DocumentoOficialDTO documento =
                (DocumentoOficialDTO) event.getComponent().getAttributes().get("documentoParametro");
        InputStream input = getAutorizarRequerimientoRRLBussines().descargarArchivoOficial(documento);
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
     * M&eacute;todo para guardar los documentos en la
     * autorizaci&oacute;n del requerimiento.
     */
    public void anexarDocumentosRequerimientos(ActionEvent actionEvent) {
        if (getListaDocumentos() != null && getListaDocumentos().size() > 0) {
                    for(DocumentoOficialDTO documento : listaDocumentos){
                if(documento.getCveTipoDocumento() == null){
                    documento.setCveTipoDocumento(TipoDocumentoOficial.OFICIO_REQUERIMIENTO.getClave());
                }
            }
            getAutorizarRequerimientoRRLBussines().guardarDocumentosAutorizarRequerimiento(getListaDocumentos(),
                    getRequerimiento());
            prepararFirmaSolicitud();
            getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandeja());
            getFlash().put("firma", getFirma());

            getFlash().put("datos_nyv", getDatosNyV());

            ConfigurableNavigationHandler configurableNavigationHandler =
                    (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                            .getNavigationHandler();

            configurableNavigationHandler.performNavigation(UrlFirma.PAGINA_FIRMA_REQUERIMIENTO + FACES_REDIRECT);
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se ha adjuntado el oficio de requerimiento"));
        }
    }

    /**
     * M&eacute;todo para firmar/avanzar la autorizaci&oacute;n del
     * requerimiento.
     */
    public String firmarAutorizarRequerimiento() throws BusinessException {
        String sNumSerie;
        String urlforward = null;
        setUserID(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userID")
                .toString());
        try {
            if (getUserID().equalsIgnoreCase(getUserProfile().getRfc())) {
                sNumSerie = getAraValidadorHelper().getNumSerie(FacesContext
                        .getCurrentInstance().getExternalContext()
                        .getRequestParameterMap().get("numeroSerie"));

                if (getAraValidadorHelper().validarRfcNumSerie(sNumSerie, getUserProfile().getRfc()) 
                        && getAraValidadorHelper().getEdoCertificado(sNumSerie)) {
                    setFirmaDigital(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                            .get("firmaDigital").toString());

                    firma.setSello(getFirmaDigital());
                    firma.setRfcUsuario(getUserProfile().getRfc());
                    firma.setCveRol(getUserProfile().getRol());
                    firma.setCadenaOriginal(autorizarRequerimientoRRLBussines.generaCadenaOriginalAutorizarRequerimiento(
                            getRequerimiento().getIdRequerimiento(), new Date(), getUserProfile().getRfc()));
                    firma.setCertificado(sNumSerie);

                    getAutorizarRequerimientoRRLBussines().firmarAutorizarRequerimiento(firma, getRequerimiento(),
                            getDatosBandeja(), getUserProfile().getRfc());

                    StringBuffer msgRequerimeinto
                            = new StringBuffer()
                            .append(" El requerimiento de informaci\u00F3n para el asunto con n\u00FAmero ")
                            .append(getDatosBandeja().getNumeroAsunto()).append(" ha sido autorizado exitosamente");
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", msgRequerimeinto.toString());
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    urlforward = LoginConstante.BANDEA_JSF;
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, null, 
                                MessageFormat.format(AraConstantes.MENSAJE_LLAVE_SERIE_INCORRECTO, getUserProfile().getRfc())));
            }
        }catch (SgiARAException ex){
                getLogger().error("Ocurrio un error al firmar AutorizarRequerimientoRRLController.firmarAutorizarRequerimiento {0}", ex);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, null
                                        ,ex.getMessage()));
        }catch (Exception e) {
                getLogger().error("Ocurrio un error AutorizarRequerimientoRRLController.firmarAutorizarRequerimiento {0}", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
        }

        return urlforward;
    }

    public boolean validaCampoRequerido(RequerimientoDTO requerimiento) {
        boolean continuar = true;
        if (isRequerimientoAutoridad() && requerimiento.getClaveUnidadAdministrativa() == null) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, errorMsg
                            .getString("vuj.requerimiento.campoRequerido.unidadAdmin")));
            continuar = false;
        }
        return continuar;
    }

    /**
     * 
     * @return requerimiento
     */
    public RequerimientoDTO getRequerimiento() {
        return requerimiento;
    }

    /**
     * 
     * @param requerimiento
     *            a fijar
     */
    public void setRequerimiento(RequerimientoDTO requerimiento) {
        this.requerimiento = requerimiento;
    }

    /**
     * 
     * @return datosBandeja
     */
    public DatosBandejaTareaDTO getDatosBandeja() {
        return datosBandeja;
    }

    /**
     * 
     * @param datosBandeja
     *            a fijar
     */
    public void setDatosBandeja(DatosBandejaTareaDTO datosBandeja) {
        this.datosBandeja = datosBandeja;
    }

    /**
     * 
     * @return listaTiposRequerimiento
     */
    public List<CatalogoDTO> getListaTiposRequerimiento() {
        return listaTiposRequerimiento;
    }

    /**
     * 
     * @param listaTiposRequerimiento
     *            a fijar
     */
    public void setListaTiposRequerimiento(List<CatalogoDTO> listaTiposRequerimiento) {
        this.listaTiposRequerimiento = listaTiposRequerimiento;
    }

    /**
     * 
     * @return listaAutoridades
     */
    public List<CatalogoDTO> getListaAutoridades() {
        return listaAutoridades;
    }

    /**
     * 
     * @param listaAutoridades
     *            a fijar
     */
    public void setListaAutoridades(List<CatalogoDTO> listaAutoridades) {
        this.listaAutoridades = listaAutoridades;
    }

    /**
     * 
     * @return autorizarRequerimientoRRLBussines
     */
    public AutorizarRequerimientoRRLBussines getAutorizarRequerimientoRRLBussines() {
        return autorizarRequerimientoRRLBussines;
    }

    /**
     * 
     * @param autorizarRequerimientoRRLBussines
     *            a fijar
     */
    public void
            setAutorizarRequerimientoRRLBussines(AutorizarRequerimientoRRLBussines autorizarRequerimientoRRLBussines) {
        this.autorizarRequerimientoRRLBussines = autorizarRequerimientoRRLBussines;
    }

    /**
     * 
     * @return messages
     */
    public ResourceBundle getMessages() {
        return messages;
    }

    /**
     * 
     * @param messages
     *            a fijar
     */
    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
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
     * 
     * @return listaDocumentos
     */
    public List<DocumentoOficialDTO> getListaDocumentos() {
        return listaDocumentos;
    }

    /**
     * 
     * @param listaDocumentos
     *            a fijar
     */
    public void setListaDocumentos(List<DocumentoOficialDTO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    /**
     * 
     * @return documentoOficialDataModel
     */
    public DocumentoOficialDataModel getDocumentoOficialDataModel() {
        return documentoOficialDataModel;
    }

    /**
     * 
     * @param documentoOficialDataModel
     *            a fijar
     */
    public void setDocumentoOficialDataModel(DocumentoOficialDataModel documentoOficialDataModel) {
        this.documentoOficialDataModel = documentoOficialDataModel;
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
     * Determina si se trata de un requerimiento de Autoridad.
     * Considera las enumeraciones aplicables para T1 y T2: TIREQ.RIA
     * y REQCAL.RTR, respectivamente
     * 
     * @return boolean indicando si es requerimiento de autoridad
     */
    public boolean isRequerimientoAutoridad() {
        return getRequerimiento() != null
                && (TipoRequerimiento.AUTORIDAD.getClave().equals(getRequerimiento().getClaveTipoRequerimiento()) || TipoRequerimiento.RETROALIMENTACION_CAL
                        .getClave().equals(getRequerimiento().getClaveTipoRequerimiento()));
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
     * @return firmaDigital
     */
    public String getFirmaDigital() {
        return firmaDigital;
    }

    /**
     * @param firmaDigital
     *            firmaDigital a fijar
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
     * @param firma
     *            firma a fijar
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
     * @param firmarBusiness
     *            firmarBusiness a fijar
     */
    public void setFirmarBusiness(FirmarBusiness firmarBusiness) {
        this.firmarBusiness = firmarBusiness;
    }

    @Override
    public BaseCloudBusiness getBusinessBean() {
        // TODO Auto-generated method stub
        return autorizarRequerimientoRRLBussines;
    }

    /**
     * @return the docComplementarios
     */
    public List<DocumentoDTO> getDocComplementarios() {
        return docComplementarios;
    }

    /**
     * @param docComplementarios
     *            the docComplementarios to set
     */
    public void setDocComplementarios(List<DocumentoDTO> docComplementarios) {
        this.docComplementarios = docComplementarios;
    }

    /**
     * @return the nuevoDocumento
     */
    public boolean isNuevoDocumento() {
        return nuevoDocumento;
    }

    /**
     * @param nuevoDocumento
     *            the nuevoDocumento to set
     */
    public void setNuevoDocumento(boolean nuevoDocumento) {

    }

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
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

    @Override
    public List<DocumentoOficialDTO> getListaDocumentosAdjuntados() {
        return getListaDocumentos();
    }

    public void validaAcceso() {
        // Validacion de acceso a la funcionalidad para el rol de
        // oficialia
        this.validaAccesoRolEmpleado(RolesConstantes.ROL_EMPLEADO_ADMINISTRADOR);

    }

    public boolean isBndAnexar() {
        return bndAnexar;
    }

    public void setBndAnexar(boolean bndAnexar) {
        this.bndAnexar = bndAnexar;
    }

    public DatosNyVDTO getDatosNyV() {
        return datosNyV;
    }

    public void setDatosNyV(DatosNyVDTO datosNyV) {
        this.datosNyV = datosNyV;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

}
