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
package mx.gob.sat.siat.juridica.ca.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.ca.web.controller.bean.bussiness.BandejaConsultasAutorizacionesBussines;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DatosBandejaConsultasAutorizacionesDataModel;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.*;

/**
 * Bean que implementan la l&oacute;gica de negocio para la consulta
 * de tramites
 * 
 * @author Softtek
 * 
 */
@SessionScoped
@ManagedBean
public class BandejaConsultasAutorizacionesController extends BaseControllerBean {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = 1L;
    /**
     * DTO para represetnar el filtro de la bandeja de una tarea.
     */
    private FiltroBandejaTareaDTO filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();

    public void setMensajeFechaFin(String mensajeFechaFin) {
        this.mensajeFechaFin = mensajeFechaFin;
    }

    public void setMensajeFechas(String mensajeFechas) {
        this.mensajeFechas = mensajeFechas;
    }

    /**
     * Lista de datos de la bandeja.
     */
    private List<DatosBandejaTareaDTO> listDatosBandeja = new ArrayList<DatosBandejaTareaDTO>();
    /**
     * Lista de estados procesales.
     */
    private List<CatalogoDTO> estadosProcesales = new ArrayList<CatalogoDTO>();
    /**
     * Lista del catalogo de tipo de tramite.
     */
    private List<CatalogoDTO> catListTipoTramite = new ArrayList<CatalogoDTO>();
    /**
     * DTO para acceder a datos de la bandeja de una tarea.
     */
    private DatosBandejaTareaDTO bandejaTareaDTO = new DatosBandejaTareaDTO();
    /**
     * DataModel de bandeja de consultas de Autorizacioens.
     */
    private DatosBandejaConsultasAutorizacionesDataModel bandejaDataModel;
    /**
     * Propiedad que representa un mesaje de la fecha final.
     */
    private String mensajeFechaFin;
    /**
     * Propiedad que representa un mensaje para el rango de fechas.
     */
    private String mensajeFechas;
    /**
     * Propiedad que representa la fecha minima.
     */
    private Date minFechaFin;
    /**
     * Bussines que implementa la logica de negocio de la bandeja de
     * consultas de autorizaciones.
     */
    @ManagedProperty(value = "#{bandejaConsultasAutorizacionesBussines}")
    private transient BandejaConsultasAutorizacionesBussines bandejaConsultasAutorizacionesBussines;
    /**
     * Controller que maneja el detalle de promocion.
     */
    @ManagedProperty(value = "#{detallePromocionController}")
    private DetallePromocionController detallePromocionController;

    /**
     * Mensaje de error.
     */
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    /**
     * Controller que maneja las consultas de autorizaciones.
     */
    public BandejaConsultasAutorizacionesController() {
        bandejaDataModel = new DatosBandejaConsultasAutorizacionesDataModel(listDatosBandeja);
    }

    /**
     * Metodo que se ejecuta despues de la creacion del bean.
     */
    @PostConstruct
    public void init() {
        estadosProcesales.addAll(bandejaConsultasAutorizacionesBussines.obtenerCatalogoEstados());
        catListTipoTramite.addAll(bandejaConsultasAutorizacionesBussines.obtenerCatalogoTipoTramites());
    }

    /**
     * Metodo que realiza una busqueda de tramites en la bandeja.
     */
    public void buscarTramitesBandeja() {
        if (validaFiltro()) {
            filtroBandejaTareaDTO.setUsuario(getUserProfile().getRfc());
            listDatosBandeja.clear();
            listDatosBandeja.addAll(bandejaConsultasAutorizacionesBussines.obtenerPromocion(filtroBandejaTareaDTO));
        }
        else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                            .getString("vuj.bandeja.consultas.criterioBusquedaValido")));
        }
    }

    public boolean validaFiltro() {
        boolean bAsunto = false;
        boolean bEstado = false;
        boolean bFecha = false;
        boolean bTipoTramite = false;
        boolean c1 = false;
        boolean c2 = false;
        boolean c3 = false;
        boolean valido = false;

        if (filtroBandejaTareaDTO.getNumeroAsunto() != null && !filtroBandejaTareaDTO.getNumeroAsunto().isEmpty()) {
            bAsunto = true;
        }
        if (filtroBandejaTareaDTO.getEstadoProcesal() != null
                && !filtroBandejaTareaDTO.getEstadoProcesal().getClave().isEmpty()) {
            bEstado = true;
        }
        if (filtroBandejaTareaDTO.getFechaInicio() != null && filtroBandejaTareaDTO.getFechaFin() != null) {
            bFecha = true;
        }
        if (bAsunto && bEstado) {
            c1 = true;
        }
        if (bAsunto && bFecha) {
            c2 = true;
        }
        if (bEstado && bFecha) {
            c3 = true;
        }
        if (filtroBandejaTareaDTO.getTipoTramite() != null
                && !filtroBandejaTareaDTO.getTipoTramite().getClave().isEmpty()) {
            bTipoTramite = true;
        }

        if (!getUserProfile().getRoles().contains("Abogado")) {
            if (c1 || c2 || c3) {
                valido = true;
            }
        }
        else if (bAsunto || bEstado || bFecha || bTipoTramite) {
            valido = true;
        }
        return valido;
    }

    /**
     * Metodo que limpia los filtros de busqueda.
     */
    public void limpiaFiltros() {
        setMinFechaFin(null);
        filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
        listDatosBandeja.clear();
    }

    /**
     * Metodo que permite seleccionar una solicitud con sus campos y
     * los carga en la bandeja.
     * 
     * @param event
     * @throws IOException
     */
    public void onRowDblClckSelect(final SelectEvent event) throws IOException {
        DatosBandejaTareaDTO datoSelected = ((DatosBandejaTareaDTO) event.getObject());
        getFlash().put("idSolicitud", datoSelected.getIdSolicitud());
        getFlash().put("tipoTramite", datoSelected.getTipoTramite());
        getFlash().put("numAsunto", datoSelected.getNumeroAsunto());
        detallePromocionController.setDatosBandejaTareaDTO(datoSelected);
        inicializarBandeja();
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        configurableNavigationHandler.performNavigation(datoSelected.getUrl() + "?faces-redirect=true");

    }

    /**
     * Metodo que inicializa la bandeja.
     * 
     * @return
     */
    public String inicializarBandeja() {
        listDatosBandeja.clear();
        filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
        filtroBandejaTareaDTO.setUsuario(getUserProfile().getRfc());
        estadosProcesales.addAll(bandejaConsultasAutorizacionesBussines.obtenerCatalogoEstados());
        catListTipoTramite.addAll(bandejaConsultasAutorizacionesBussines.obtenerCatalogoTipoTramites());
        getLogger().debug("obterner estados {}", estadosProcesales);
        return LoginConstante.BANDEJA_CONSULTAS_AUTORIZACIONES_JSF;
    }

    /**
     * Metdodo que establece el rango maximo de fechas.
     */
    public void rangoMaxFechas() {
        Date minFechFin = new Date();
        minFechFin.setTime(filtroBandejaTareaDTO.getFechaInicio().getTime());
        setMinFechaFin(minFechFin);
        filtroBandejaTareaDTO.setFechaFin(null);
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
     *            el filtroBandejaTareaDTO
     */
    public void setFiltroBandejaTareaDTO(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {
        this.filtroBandejaTareaDTO = filtroBandejaTareaDTO;
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
     * 
     * @return bandejaDataModel
     */
    public DatosBandejaConsultasAutorizacionesDataModel getBandejaDataModel() {
        return bandejaDataModel;
    }

    /**
     * 
     * @param bandejaDataModel
     *            la bandejaDatamodel a fijar.
     */
    public void setBandejaDataModel(DatosBandejaConsultasAutorizacionesDataModel bandejaDataModel) {
        this.bandejaDataModel = bandejaDataModel;
    }

    /**
     * 
     * @return listaDatosBandeja
     */
    public List<DatosBandejaTareaDTO> getListDatosBandeja() {
        return listDatosBandeja;
    }

    /**
     * 
     * @param listDatosBandeja
     *            el listDatosBandeja a fijar.
     */
    public void setListDatosBandeja(List<DatosBandejaTareaDTO> listDatosBandeja) {
        this.listDatosBandeja = listDatosBandeja;
    }

    /**
     * 
     * @return bandejaTareaDTO
     */
    public DatosBandejaTareaDTO getBandejaTareaDTO() {
        return bandejaTareaDTO;
    }

    /**
     * 
     * @param bandejaTareaDTO
     *            la bandejaTareaDTO a fijar.
     */
    public void setBandejaTareaDTO(DatosBandejaTareaDTO bandejaTareaDTO) {
        this.bandejaTareaDTO = bandejaTareaDTO;
    }

    /**
     * 
     * @return bandejaConsultarAutorizacionesBussines
     */
    public BandejaConsultasAutorizacionesBussines getBandejaConsultasAutorizacionesBussines() {
        return bandejaConsultasAutorizacionesBussines;
    }

    /**
     * 
     * @param bandejaConsultasAutorizacionesBussines
     *            el bandejaConsultasAutorizacionesBussines a fijar.
     */
    public void setBandejaConsultasAutorizacionesBussines(
            BandejaConsultasAutorizacionesBussines bandejaConsultasAutorizacionesBussines) {
        this.bandejaConsultasAutorizacionesBussines = bandejaConsultasAutorizacionesBussines;
    }

    /**
     * 
     * @return detallePromocionController
     */
    public DetallePromocionController getDetallePromocionController() {
        return detallePromocionController;
    }

    /**
     * 
     * @param detallePromocionController
     *            el detallePromocionController a fijar.
     */
    public void setDetallePromocionController(DetallePromocionController detallePromocionController) {
        this.detallePromocionController = detallePromocionController;
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
     *            los estadosProcesales a fijar.
     */
    public void setEstadosProcesales(List<CatalogoDTO> estadosProcesales) {
        this.estadosProcesales = estadosProcesales;
    }

    /**
     * 
     * @return catListTipoTramite
     */
    public List<CatalogoDTO> getCatListTipoTramite() {
        return catListTipoTramite;
    }

    /**
     * 
     * @param catListTipoTramite
     *            el catListTipoTramite
     */
    public void setCatListTipoTramite(List<CatalogoDTO> catListTipoTramite) {
        this.catListTipoTramite = catListTipoTramite;
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
     * @return boolean
     */
    public boolean getHidden() {
        if (getUserProfile().getRoles().contains("Abogado")) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @return Fecha final minima.
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
     *            la minFechaFin a fijar.
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

    public Date getMaxFechaInicio() {

        return new Date();
    }

}
