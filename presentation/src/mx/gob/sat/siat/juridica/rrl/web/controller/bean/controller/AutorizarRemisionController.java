/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.FirmarBusiness;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.AdjuntarDocumentoController;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.RemisionDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.GeneralConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import mx.gob.sat.siat.juridica.rrl.util.exception.RemitirAsuntoException;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AutorizarRemisionBussiness;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Bean que implementa la logica de negocio para autorizar remision.
 * 
 * @author softtek
 * 
 */
@SessionScoped
@ManagedBean(name = "autorizarRemisionController")
public class AutorizarRemisionController extends BaseControllerBean {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Lista de unidades emisoras
     */
    private List<CatalogoDTO> listaUnidadesEmisoras = new ArrayList<CatalogoDTO>();
    /**
     * DTO de los datos de remision.
     */
    private RemisionDTO datosRemision = new RemisionDTO();
    /**
     * DTO de los datos de la bandeja.
     */
    private DatosBandejaTareaDTO datosBandejaTareaDTO;
    /**
     * Mensaje de error
     */
    private String errorMessage;
    /**
     * Mensaje
     */
    private String msg;
    /**
     * DTO de tramite
     */
    private TramiteDTO tramite;
    /**
     * Unidad emisora.
     */
    private String unidad;

    private boolean nuevoDocumento = false;
    /**
     * Mensajes
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    /**
     * Mensajes de error
     */
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;
    /**
     * Bussines que implementa la logica de negocio para autorizar
     * remision.
     */
    @ManagedProperty(value = "#{autorizarRemisionBussiness}")
    private AutorizarRemisionBussiness autorizarRemisionBussiness;
    /**
     * Clase que controla la funcion de adjuntar un documento en
     * pantalla.
     */
    @ManagedProperty(value = "#{adjuntarDocumentoController}")
    private AdjuntarDocumentoController adjuntarDocumentoController;

    /** Datos de la firma */
    private String firmaDigital;
    /** DTO que representa lo firma de la solicitud */
    private FirmaDTO firma;
    /**
     * Bean que implemanta la logica de negocio para firmar.
     */
    @ManagedProperty(value = "#{firmarBusiness}")
    private FirmarBusiness firmarBusiness;

    /**
     * Constructor vacio.
     */
    public AutorizarRemisionController() {

    }

    /**
     * Metodo para preparar datos de bandeja de tarea.
     * 
     * @param datosBandejaTareaDTO
     */
    public void prepararAutorizarRemision(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        this.datosBandejaTareaDTO = datosBandejaTareaDTO;
        setTramite(getAutorizarRemisionBussiness().obtenerTramite(getDatosBandejaTareaDTO().getNumeroAsunto()));
        setListaUnidadesEmisoras(getAutorizarRemisionBussiness().obtenerUnidadesEmisoras());
        setUnidad(getAutorizarRemisionBussiness().obtenerUnidad(getDatosBandejaTareaDTO().getNumeroAsunto()));
        getFlash().put("numAsunto", getDatosBandejaTareaDTO().getNumeroAsunto());
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
    }

    /**
     * Metodo para preparar firma de solicitud.
     */
    public void prepararFirmaSolicitud() {
        setFirma(new FirmaDTO(new Date()));
        getFirma().setCadenaOriginal(
                getAutorizarRemisionBussiness().generaCadenaOriginal(getDatosRemision().getNumAsunto(),
                        getFirma().getFechaFirma()));
    }

    /**
     * Metodo que procesa la firma.
     * 
     * @return ""
     */
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
     * Metodo para anexar documentos.
     */
    public void anexarDocumentos() {

        List<DocumentoDTO> documentosComplementarios =
                getAutorizarRemisionBussiness().obtenerDocumentosComplementariosAutorizacion(
                        getDatosBandejaTareaDTO().getIdSolicitud());

        if (!(documentosComplementarios == null || documentosComplementarios.isEmpty())) {
            nuevoDocumento = true;
            return;

        }
        nuevoDocumento = false;

        try {
            actualizarRemision();
        }
        catch (RemitirAsuntoException e) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", errorMsg
                            .getString("vuj.remision.mismaUnidad")));
        }

    }

    public void regresarPaginaAutorizacion() {
        nuevoDocumento = false;
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());

    }

    /**
     * Metodo que actualiza la remision.
     */
    public void actualizarRemision() throws RemitirAsuntoException {
        getAdjuntarDocumentoController().inicializarAdjuntarDocumentos(datosBandejaTareaDTO,
                TipoDocumentoOficial.OFICIO_REMISION.getClave());
        getAutorizarRemisionBussiness().actualizarRemision(getDatosBandejaTareaDTO().getNumeroAsunto(), getUnidad());
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        // se oculta funcionalidad de NyV
        configurableNavigationHandler.performNavigation("adjuntarDocumentoRemitir.jsf" + "?faces-redirect=true");

    }

    /**
     * Metodo que actualiza la remision.
     */
    public void rechazarRemision() {
        getAutorizarRemisionBussiness().rechazarRemision(getDatosBandejaTareaDTO().getNumeroAsunto(),
                getDatosBandejaTareaDTO().getIdTareaUsuario(), getUserProfile().getUsuario(),
                getDatosBandejaTareaDTO().getIdSolicitud());

        String[] param = new String[1];
        param[0] = getDatosBandejaTareaDTO().getNumeroAsunto();

        getFlash().put(GeneralConstantes.MENSAJE_REDIRECT,
                MessageFormat.format(messages.getString("vuj.autorizar.remision.rechazar"), (Object[]) param));
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        configurableNavigationHandler.performNavigation(LoginConstante.BANDEA_JSF + "?faces-redirect=true");
    }

    /**
     * Metodo que se ejecuta despues de la creacion del bean.
     */
    @PostConstruct
    public void iniciar() {
        setTramite(new TramiteDTO());
    }

    /**
     * Metodo para guardar.
     * 
     * @return
     */
    public String guardar() {
        return null;
    }

    /**
     * 
     * @return
     */
    public String autorizarRemitir() {
        return null;
    }

    /**
     * 
     * @return listUnidadesEmisoras
     */
    public List<CatalogoDTO> getListaUnidadesEmisoras() {
        return listaUnidadesEmisoras;
    }

    /**
     * 
     * @param listaUnidadesEmisoras
     *            la listaUnidadesEmisoras a fijar.
     */
    public void setListaUnidadesEmisoras(List<CatalogoDTO> listaUnidadesEmisoras) {
        this.listaUnidadesEmisoras = listaUnidadesEmisoras;
    }

    /**
     * 
     * @return datosRemision
     */
    public RemisionDTO getDatosRemision() {
        return datosRemision;
    }

    /**
     * 
     * @param datosRemision
     *            los datosRemision a fijar.
     */
    public void setDatosRemision(RemisionDTO datosRemision) {
        this.datosRemision = datosRemision;
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
     * @return errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 
     * @param errorMessage
     *            el errorMessage a fijar.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 
     * @return msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 
     * @param msg
     *            el msg a fijar.
     */
    public void setMsg(String msg) {
        this.msg = msg;
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
     *            los messages a fijar.
     */
    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    /**
     * 
     * @return errorMsg
     */
    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    /**
     * 
     * @param errorMsg
     *            el errorMsg a fijar.
     */
    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 
     * @return tramite
     */
    public TramiteDTO getTramite() {
        return tramite;
    }

    /**
     * 
     * @param tramite
     *            el tramite a fijar.
     */
    public void setTramite(TramiteDTO tramite) {
        this.tramite = tramite;
    }

    /**
     * 
     * @return autorizarRemisionBussiness
     */
    public AutorizarRemisionBussiness getAutorizarRemisionBussiness() {
        return autorizarRemisionBussiness;
    }

    /**
     * 
     * @param autorizarRemisionBussiness
     *            el autorizarRemisionBussiness a fijar.
     */
    public void setAutorizarRemisionBussiness(AutorizarRemisionBussiness autorizarRemisionBussiness) {
        this.autorizarRemisionBussiness = autorizarRemisionBussiness;
    }

    /**
     * 
     * @return unidad
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * 
     * @param unidad
     *            la unidad a fijar.
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    /**
     * 
     * @return adjuntarDocumentoController
     */
    public AdjuntarDocumentoController getAdjuntarDocumentoController() {
        return adjuntarDocumentoController;
    }

    /**
     * 
     * @param adjuntarDocumentoController
     *            el adjuntarDocumentoController a fijar.
     */
    public void setAdjuntarDocumentoController(AdjuntarDocumentoController adjuntarDocumentoController) {
        this.adjuntarDocumentoController = adjuntarDocumentoController;
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

    /**
     * @param datosBandejaTareaDTO
     *            datosBandejaTareaDTO a fijar
     */
    public void setDatosBandejaTareaDTO(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        this.datosBandejaTareaDTO = datosBandejaTareaDTO;
    }

    public boolean isNuevoDocumento() {
        return nuevoDocumento;
    }

    public void setNuevoDocumento(boolean nuevoDocumento) {
        this.nuevoDocumento = nuevoDocumento;
    }

}
