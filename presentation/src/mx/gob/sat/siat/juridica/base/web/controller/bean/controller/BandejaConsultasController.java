/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dto.BandejaConsultasDTO;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BandejaConsultasBussines;
import mx.gob.sat.siat.juridica.base.web.controller.bean.model.DatosBandejaConsultasDataModel;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.oficialia.web.controller.bean.constantes.OficialiaConstantesController;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.util.*;

/**
 * Controller para la bandeja de consultas.
 * 
 * @author softtek
 * 
 */
@ManagedBean
@ViewScoped
public class BandejaConsultasController extends BaseControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final String FORMA_BANDEJA = "formBandeja";

    /**
     * Bussines que implementa las reglas de negocio de la bandeja de
     * consultas.
     */
    @ManagedProperty("#{bandejaConsultasBussines}")
    private BandejaConsultasBussines bandejaConsultasBussines;
    /**
     * Propiedad que representa un mensaje.
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    /**
     * Propiedad que representa un mensaje de error.
     */
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;
    /**
     * Propiedad que representa un objeto de tipo
     * DatosBandejaConsultasDataModel
     */
    private DatosBandejaConsultasDataModel bandejaDataModel;
    /**
     * Propiedad que representa un objeto tipo filtroBandejaTareaDTO
     */
    private FiltroBandejaTareaDTO filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
    /**
     * Propiedad que representa un objeto DTO de tipo
     * BandejaConsultarDTO
     */
    private BandejaConsultasDTO bandejaTareaDTO = new BandejaConsultasDTO();
    /**
     * Lista para almacenar los datos de la bandeja.
     */
    private List<BandejaConsultasDTO> listDatosBandeja = new ArrayList<BandejaConsultasDTO>();
    /**
     * Lista para almacenar los estados procesales
     */
    private List<CatalogoDTO> estadosProcesales = new ArrayList<CatalogoDTO>();
    /**
     * Lista par almacenar los tipos de tramites.
     */
    private List<CatalogoDTO> tiposTramites = new ArrayList<CatalogoDTO>();
    /**
     * Propiedad para representar un mensaje de fecha final.
     */
    private String mensajeFechaFin;
    /**
     * Propiedad que representa un mensaje con las fechas
     */
    private String mensajeFechas;
    /**
     * Propiedad que representa la fecha final maxima.
     */
    private Date maxFechaFin;
    /**
     * Propiedad que representa la fecha final minima.
     */
    private Date minFechaFin;

    private String mensageFechaFin;
    private String mensageFechas;
    private Boolean showMessages;
    private String mensajeBandejaVacia;
    private String mensajeBandejaVaciaOficialia;

    /**
     * Metodo que se ejecuta despues de la creacion del bean.
     */
    @PostConstruct
    public void initFiltro() {
        getListDatosBandeja().clear();
        filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
        getEstadosProcesales().addAll(getBandejaConsultasBussines().obtenerCatalogoEstados());
        getTiposTramites().addAll(getBandejaConsultasBussines().obtenerTiposTramites());
        bandejaDataModel = new DatosBandejaConsultasDataModel(getListDatosBandeja());
        setMensajeBandejaVacia("");
        setMensajeBandejaVaciaOficialia("");
        String show =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("showMessages");
        setShowMessages(show != null);
    }

    /**
     * Metodo que busca una tarea mediante un filtro de busqueda.
     * 
     * @param actionEvent
     */
    public void buscarTareaBandeja(ActionEvent actionEvent) {

        if ((filtroBandejaTareaDTO.getFechaFin() != null && filtroBandejaTareaDTO.getFechaInicio() == null)
                || (filtroBandejaTareaDTO.getFechaInicio() != null && filtroBandejaTareaDTO.getFechaFin() == null)) {
            RequestContext.getCurrentInstance().reset(FORMA_BANDEJA);
            setShowMessages(true);
            filtroBandejaTareaDTO.setFechaFin(null);
            filtroBandejaTareaDTO.setFechaInicio(null);
            FacesContext context = FacesContext.getCurrentInstance();
            UIInput fechaFinInput = (UIInput) context.getViewRoot().findComponent(":formBandeja:fechaFin");
            fechaFinInput.setValid(false);
            UIInput fechaInicioInput = (UIInput) context.getViewRoot().findComponent(":formBandeja:fechaInicio");
            fechaInicioInput.setValid(false);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", getMensageFechas()));
            return;

        }

        if ((filtroBandejaTareaDTO.getFechaFin() != null && filtroBandejaTareaDTO.getFechaInicio() != null)
                && filtroBandejaTareaDTO.getFechaFin().before(filtroBandejaTareaDTO.getFechaInicio())) {
            RequestContext.getCurrentInstance().reset(FORMA_BANDEJA);
            setShowMessages(true);
            filtroBandejaTareaDTO.setFechaFin(null);
            filtroBandejaTareaDTO.setFechaInicio(null);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", getMensageFechaFin()));
            return;
        }
        if (filtrosValidos()) {

            getFiltroBandejaTareaDTO().setUsuario(getUserProfile().getRfc());
            getListDatosBandeja().clear();
            getListDatosBandeja().addAll(
                    getBandejaConsultasBussines().obtenerAsuntosConsulta(getFiltroBandejaTareaDTO()));
            if (getListDatosBandeja() == null || getListDatosBandeja().isEmpty()) {
                setMensajeBandejaVacia(messages.getString("vuj.grid.vacio"));
                setMensajeBandejaVaciaOficialia(messages.getString("vuj.grid.vacio"));
            }
        }

        else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:",
                            " Favor de seleccionar al menos un criterio de b\u00FAsqueda."));
        }
    }

    /**
     * Metodo que busca una tarea mediante filtros de oficialia.
     * 
     * @param actionEvent
     */
    public void buscarTareaBandejaOficialia(ActionEvent actionEvent) {

        if ((filtroBandejaTareaDTO.getFechaFin() != null && filtroBandejaTareaDTO.getFechaInicio() == null)
                || (filtroBandejaTareaDTO.getFechaInicio() != null && filtroBandejaTareaDTO.getFechaFin() == null)) {
            RequestContext.getCurrentInstance().reset(FORMA_BANDEJA);
            setShowMessages(true);
            filtroBandejaTareaDTO.setFechaFin(null);
            filtroBandejaTareaDTO.setFechaInicio(null);
            FacesContext context = FacesContext.getCurrentInstance();
            UIInput fechaFinInput = (UIInput) context.getViewRoot().findComponent(":formBandeja:fechaFin");
            fechaFinInput.setValid(false);
            UIInput fechaInicioInput = (UIInput) context.getViewRoot().findComponent(":formBandeja:fechaInicio");
            fechaInicioInput.setValid(false);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", getMensageFechas()));
            return;

        }
        getListDatosBandeja().clear();
        if (!getFiltroBandejaTareaDTO().getNumeroAsunto().equals("")
                || (getFiltroBandejaTareaDTO().getFolio().equals(""))) {
            getFiltroBandejaTareaDTO().setBanderaFolio(false);
        }
        else if ((getFiltroBandejaTareaDTO().getFolio() != null)
                && (getFiltroBandejaTareaDTO().getNumeroAsunto().equals(""))) {
            getFiltroBandejaTareaDTO().setBanderaFolio(true);
        }
        getListDatosBandeja().addAll(
                getBandejaConsultasBussines().obtenerAsuntosOficialiaConsulta(getFiltroBandejaTareaDTO()));
        if (getListDatosBandeja() == null || getListDatosBandeja().isEmpty()) {
            setMensajeBandejaVacia(messages.getString("oficialia.bandeja.consulta.asuntos.bandejaVacia"));
            setMensajeBandejaVaciaOficialia(messages.getString("oficialia.bandeja.consulta.asuntos.bandejaVaciaOP"));
        }
    }

    /**
     * Metodo que maneja el evento de seleccionar un row.
     * 
     * @param event
     * @throws IOException
     */
    public void onRowDblClckSelect(final SelectEvent event) throws IOException {
        BandejaConsultasDTO datoSelected = ((BandejaConsultasDTO) event.getObject());
        DatosBandejaTareaDTO datosSel = new DatosBandejaTareaDTO();
        datosSel.setIdSolicitud(datoSelected.getIdSolicitud());
        datosSel.setTipoTramite(datoSelected.getTipoTramite());
        datosSel.setNumeroAsunto(datoSelected.getNumAsunto());
        datosSel.setEstadoProcesal(datoSelected.getEstadoProcesal().getDescripcion());
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, datosSel);
        inicializarBandeja();
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();
        configurableNavigationHandler.performNavigation(datoSelected.getUrl() + "?faces-redirect=true");
    }

    /**
     * Metodo que maneja el evento de seleccionar un row de oficialia.
     * 
     * @param event
     * @throws IOException
     */
    public void onRowDblClckSelectOf(final SelectEvent event) throws IOException {
        BandejaConsultasDTO datoSelected = ((BandejaConsultasDTO) event.getObject());
        DatosBandejaTareaDTO datosSel = new DatosBandejaTareaDTO();
        datosSel.setIdSolicitud(datoSelected.getIdSolicitud());
        datosSel.setTipoTramite(datoSelected.getTipoTramite());
        datosSel.setNumeroAsunto(datoSelected.getNumAsunto());
        datosSel.setFolio(datoSelected.getFolio());
        datosSel.setEstadoProcesal(datoSelected.getEstadoProcesal().getDescripcion());
        datosSel.setCveEstadoProcesal(datoSelected.getEstadoProcesal().getClave());
        datosSel.setRfcSolicitante(datoSelected.getRfc());
        datosSel.setNombrePersonaAsignada(datoSelected.getNombre());
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, datosSel);
        inicializarBandeja();
        redireccionarPagina(OficialiaConstantesController.REGISTRO_FOLIO_OFICIALIA);
    }

    /**
     * Metodo que redirecciona al registro de folio para actualizacion
     * de asunto de oficialia
     * 
     * @param url
     */
    private void redireccionarPagina(String url) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().setResponseCharacterEncoding("UTF-8");
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) facesContext.getApplication().getNavigationHandler();
        configurableNavigationHandler.performNavigation(url + OficialiaConstantesController.FACES_REDIRECT);
    }

    /**
     * Metodo para limpiar el filtro de busqueda.
     * 
     * @param actionEvent
     */
    public void limpiarBandeja(ActionEvent actionEvent) {
        filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
        listDatosBandeja = new ArrayList<BandejaConsultasDTO>();
        bandejaDataModel = new DatosBandejaConsultasDataModel(listDatosBandeja);
        setMensajeBandejaVacia("");
        setMensajeBandejaVaciaOficialia("");
        RequestContext.getCurrentInstance().reset(FORMA_BANDEJA);
    }

    /**
     * Metodo que inicializa bandeja.
     * 
     * @return
     */
    public String inicializarBandeja() {
        listDatosBandeja.clear();
        filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
        filtroBandejaTareaDTO.setUsuario(getUserProfile().getRfc());
        getEstadosProcesales().addAll(getBandejaConsultasBussines().obtenerCatalogoEstados());
        getTiposTramites().addAll(getBandejaConsultasBussines().obtenerTiposTramites());
        return LoginConstante.BANDEJA_BUSCAR_PROMOCION;
    }

    /**
     * Metodo para validar campos de filtro de busqueda no esten
     * vacios.
     * 
     * @return
     */
    public boolean filtrosValidos() {
        boolean banderaAsunto = false;
        boolean banderaEstado = false;
        boolean banderaTramite = false;
        boolean banderaFecha = false;
        boolean banderaFiltros = false;
        if (getFiltroBandejaTareaDTO().getNumeroAsunto() != null
                && !getFiltroBandejaTareaDTO().getNumeroAsunto().isEmpty()) {
            banderaAsunto = true;
        }
        if (getFiltroBandejaTareaDTO().getEstadoProcesal() != null
                && !getFiltroBandejaTareaDTO().getEstadoProcesal().getClave().isEmpty()) {
            banderaEstado = true;
        }
        if (getFiltroBandejaTareaDTO().getTipoTramite() != null
                && !getFiltroBandejaTareaDTO().getTipoTramite().getClave().isEmpty()) {
            banderaTramite = true;
        }
        if (getFiltroBandejaTareaDTO().getFechaInicio() != null && getFiltroBandejaTareaDTO().getFechaFin() != null) {
            banderaFecha = true;
        }
        if (banderaAsunto || banderaEstado || banderaTramite || banderaFecha) {
            banderaFiltros = true;
        }
        return banderaFiltros;
    }

    /**
     * 
     * @return bandejaDataModel
     */
    public DatosBandejaConsultasDataModel getBandejaDataModel() {
        return bandejaDataModel;
    }

    /**
     * 
     * @param bandejaDataModel
     */
    public void setBandejaDataModel(DatosBandejaConsultasDataModel bandejaDataModel) {
        this.bandejaDataModel = bandejaDataModel;
    }

    /**
     * 
     * @return filtroBandejaTareaDTO
     */
    public FiltroBandejaTareaDTO getFiltroBandejaTareaDTO() {
        return filtroBandejaTareaDTO;
    }

    /**
     * 
     * @param filtroBandejaTareaDTO
     */
    public void setFiltroBandejaTareaDTO(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {
        this.filtroBandejaTareaDTO = filtroBandejaTareaDTO;
    }

    /**
     * 
     * @return bandejaTareaDTO
     */
    public BandejaConsultasDTO getBandejaTareaDTO() {
        return bandejaTareaDTO;
    }

    /**
     * 
     * @param bandejaTareaDTO
     */
    public void setBandejaTareaDTO(BandejaConsultasDTO bandejaTareaDTO) {
        this.bandejaTareaDTO = bandejaTareaDTO;
    }

    /**
     * 
     * @return bandejaConsultasBussines
     */
    public BandejaConsultasBussines getBandejaConsultasBussines() {
        return bandejaConsultasBussines;
    }

    /**
     * 
     * @param bandejaConsultasBussines
     */
    public void setBandejaConsultasBussines(BandejaConsultasBussines bandejaConsultasBussines) {
        this.bandejaConsultasBussines = bandejaConsultasBussines;
    }

    /**
     * 
     * @return ResourceBundle
     */
    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    /**
     * 
     * @param errorMsg
     */
    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 
     * @return
     */
    public ResourceBundle getMessages() {
        return messages;
    }

    /**
     * 
     * @param messages
     */
    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    /**
     * 
     * @return listaDatosBandeja
     */
    public List<BandejaConsultasDTO> getListDatosBandeja() {
        return listDatosBandeja;
    }

    /**
     * 
     * @param listDatosBandeja
     */
    public void setListDatosBandeja(List<BandejaConsultasDTO> listDatosBandeja) {
        this.listDatosBandeja = listDatosBandeja;
    }

    /**
     * 
     * @return estadosProcesales
     */
    public List<CatalogoDTO> getEstadosProcesales() {
        return estadosProcesales;
    }

    /**
     * 
     * @param estadosProcesales
     */
    public void setEstadosProcesales(List<CatalogoDTO> estadosProcesales) {
        this.estadosProcesales = estadosProcesales;
    }

    /**
     * 
     * @return tiposTramites
     */
    public List<CatalogoDTO> getTiposTramites() {
        return tiposTramites;
    }

    /**
     * 
     * @param tiposTramites
     */
    public void setTiposTramites(List<CatalogoDTO> tiposTramites) {
        this.tiposTramites = tiposTramites;
    }

    /**
     * 
     * @return mensajeFechaFin
     */
    public String getMensajeFechaFin() {
        mensajeFechaFin = errorMsg.getString("validation.fechaFin");
        return mensajeFechaFin;
    }

    /**
     * 
     * @return mensajeFechas
     */
    public String getMensajeFechas() {
        mensajeFechas = errorMsg.getString("validation.fechas");
        return mensajeFechas;
    }

    /**
     * @param mensageFechaFin
     *            the mensageFechaFin to set
     */
    public void setMensageFechaFin(String mensageFechaFin) {
        this.mensageFechaFin = mensageFechaFin;
    }

    /**
     * 
     * @return maxFechaFin
     */
    public Date getMaxFechaFin() {

        if (maxFechaFin != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(maxFechaFin);
            return cal.getTime();
        }
        else {
            return getMaxFechaInicio();
        }

    }

    /**
     * 
     * @param maxFechaFin
     */
    public void setMaxFechaFin(Date maxFechaFin) {
        if (maxFechaFin != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(maxFechaFin);
            this.maxFechaFin = cal.getTime();
        }
        else {
            this.maxFechaFin = null;
        }

    }

    /**
     * 
     * @return minFechaFin
     */
    public Date getMinFechaFin() {
        if (minFechaFin != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(minFechaFin);
            return cal.getTime();
        }
        else {
            return null;
        }
    }

    /**
     * 
     * @param minFechaFin
     */
    public void setMinFechaFin(Date minFechaFin) {
        if (minFechaFin != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(minFechaFin);
            this.minFechaFin = cal.getTime();
        }
        else {
            this.minFechaFin = null;
        }
    }

    /**
     * Metodo que genera rango maximo de fechas.
     */
    public void rangoMaxFechas() {
        Date minFechFin = new Date();

        minFechFin.setTime(filtroBandejaTareaDTO.getFechaInicio().getTime());
        Calendar calTramite = Calendar.getInstance();
        calTramite.setTime(filtroBandejaTareaDTO.getFechaInicio());
        calTramite.add(Calendar.YEAR, (1));
        Date maxFechFin = calTramite.getTime();
        if (maxFechFin.after(new Date())) {
            maxFechFin = new Date();
        }
        setMinFechaFin(minFechFin);
        setMaxFechaFin(maxFechFin);
        filtroBandejaTareaDTO.setFechaFin(null);

    }

    public Date getFechaInicio() {
        if (filtroBandejaTareaDTO != null) {
            return filtroBandejaTareaDTO.getFechaInicio();
        }
        return null;

    }

    public Date getMaxFechaInicio() {

        return new Date();
    }

    /**
     * @return the mensajeBandejaVacia
     */
    public String getMensajeBandejaVacia() {
        return mensajeBandejaVacia;
    }

    /**
     * @param mensajeBandejaVacia
     *            the mensajeBandejaVacia to set
     */
    public void setMensajeBandejaVacia(String mensajeBandejaVacia) {
        this.mensajeBandejaVacia = mensajeBandejaVacia;
    }

    public String getMensajeBandejaVaciaOficialia() {
        return mensajeBandejaVaciaOficialia;
    }

    public void setMensajeBandejaVaciaOficialia(String mensajeBandejaVaciaOficialia) {
        this.mensajeBandejaVaciaOficialia = mensajeBandejaVaciaOficialia;
    }

    public String getMensageFechas() {
        mensageFechas = errorMsg.getString("validation.fechas");
        return mensageFechas;
    }

    public void validaAccesoProcesoMenu() {
        // Validacion de acceso a la funcionalidad
        this.validaAccesoContribuytente();
    }

    public Boolean getShowMessages() {
        return showMessages;
    }

    public void setShowMessages(Boolean showMessages) {
        this.showMessages = showMessages;
    }

    public String getMensageFechaFin() {
        return mensageFechaFin;
    }

    public void setMensajeFechaFin(String mensajeFechaFin) {
        this.mensajeFechaFin = mensajeFechaFin;
    }

    public void setMensajeFechas(String mensajeFechas) {
        this.mensajeFechas = mensajeFechas;
    }

    public void setMensageFechas(String mensageFechas) {
        this.mensageFechas = mensageFechas;
    }
}
