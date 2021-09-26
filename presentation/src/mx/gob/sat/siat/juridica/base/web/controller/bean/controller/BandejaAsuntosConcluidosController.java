package mx.gob.sat.siat.juridica.base.web.controller.bean.controller;
/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */

import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dto.BandejaConsultasDescarga;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.service.PersonaServices;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BandejaConsultasBussines;
import mx.gob.sat.siat.juridica.base.web.controller.bean.model.DatosBandejaConsultasDataModelDesc;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.BandejaLazyAsuntosConcluidos;
import org.primefaces.component.datatable.DataTable;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller para la bandeja de consultas de asuntos concluidos.
 * 
 * @author consorcio
 * 
 */
@ManagedBean
@ViewScoped
public class BandejaAsuntosConcluidosController extends BaseControllerBean {

    private static final long serialVersionUID = 1L;
    private static final String FORMA_BANDEJA = "formBandeja";
    private static final String NUMERO_ASUNTO_PATTERN = "[A-Za-z0-9]{0,30}";

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
     * DatosBandejaConsultasDataModelDesc
     */
    private DatosBandejaConsultasDataModelDesc bandejaDataModelDesc;
    /**
     * Propiedad que representa un objeto tipo filtroBandejaTareaDTO
     */
    private FiltroBandejaTareaDTO filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
    /**
     * Propiedad que representa un objeto DTO de tipo
     * BandejaConsultarDTO
     */
    private BandejaConsultasDescarga bandejaTareaDTO = new BandejaConsultasDescarga();
    /**
     * Lista para almacenar los datos de la bandeja.
     */
    private List<BandejaConsultasDescarga> listDatosBandeja = new ArrayList<BandejaConsultasDescarga>();
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


    /**
     * Atributo "personaDao" tipo PersonaDao
     */
    @ManagedProperty("#{personaServices}")
    private PersonaServices personaServices;

    private DatosBandejaTareaDTO  datosSel  = new DatosBandejaTareaDTO();

    private String mensageFechaFin;
    private String mensageFechas;
    private Boolean showMessages;
    private String mensajeBandejaVacia;
    private String mensajeBandejaVaciaOficialia;

    private BandejaLazyAsuntosConcluidos bandejaLazyAsuntos;
    private transient DataTable dataTableFil = new DataTable();
    private Map<String, String> data = new LinkedHashMap<String, String>();
    /**
     * Metodo que se ejecuta despues de la creacion del bean.
     */
    @PostConstruct
    public void initFiltro() {
        getListDatosBandeja().clear();
        bandejaLazyAsuntos = new BandejaLazyAsuntosConcluidos(getListDatosBandeja(), bandejaConsultasBussines);
        filtroBandejaTareaDTO = new FiltroBandejaTareaDTO(); 
        estadosProcesales.add(new CatalogoDTO("ESTTR.RS","Resuelto"));
        estadosProcesales.add(new CatalogoDTO("ESTTR.RN", "Resuelto Notificado"));
        estadosProcesales.add(new CatalogoDTO("ESTTR.RM", "Remitido"));
        getTiposTramites().addAll(getBandejaConsultasBussines().obtenerTiposTramites());
        setMensajeBandejaVacia("");
        setMensajeBandejaVaciaOficialia("");
        String show =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("showMessages");
        setShowMessages(show != null);
    }


    /*
     * Nuevo controlador
     * Para la busqueda de los asuntos concluidos
     */

    public void buscarAsuntosConcluidos(ActionEvent actionEvent) {
        if(validaPeriodo() && validaNumAsunto()) {  
            if (filtrosValidos() && validarAdminResponsable()) {
                getListDatosBandeja().clear();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                data.put("numeroAsunto", getFiltroBandejaTareaDTO().getNumeroAsunto());
                data.put("rfc", getUserProfile().getRfc());
                data.put("fechIni", getFiltroBandejaTareaDTO().getFechaInicio() != null ? df.format(getFiltroBandejaTareaDTO().getFechaInicio()) : "");
                data.put("fechFin", getFiltroBandejaTareaDTO().getFechaFin() != null ? df.format(getFiltroBandejaTareaDTO().getFechaFin()) : "");
                data.put("rfcPromovente",
                        getFiltroBandejaTareaDTO().getRfcPromovente() != null ? getFiltroBandejaTareaDTO().getRfcPromovente() : "");
                data.put("estadoProcesal", getFiltroBandejaTareaDTO().getEstadoProcesal() != null ? getFiltroBandejaTareaDTO()
                        .getEstadoProcesal().getClave() : "");
                getDataTableFil().setFilters(data); 
                if (getListDatosBandeja() == null || getListDatosBandeja().isEmpty()) {
                    setMensajeBandejaVacia(messages.getString("vuj.grid.vacio"));
                    setMensajeBandejaVaciaOficialia(messages.getString("vuj.grid.vacio"));
                }
            }else{
                String mensaje = !filtrosValidos() ?  "Se requiere por lo menos ingresar un par\u00E1metro de consulta." : "Dado que no tiene asignado el rol de Administrador Responsable, no podr\u00e1 consultar asuntos para descargar su documentaci\u00f3n.";
                setShowMessages(true);
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ",
                                mensaje));
            }
        }
    }

    private boolean validarAdminResponsable() {
        return getBandejaConsultasBussines().verificarAdminResponsable(getUserProfile().getRfc()) || this.validaRolEmpleado(RolesConstantes.ROL_ADMINISTRADOR_GLOBAL);
    }


    private boolean validaNumAsunto() {
        boolean bandera = true;
        if(filtroBandejaTareaDTO.getNumeroAsunto() != null && !filtroBandejaTareaDTO.getNumeroAsunto().matches(NUMERO_ASUNTO_PATTERN)) {
            setShowMessages(true);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ",
                            "Dato incorrecto."));
            bandera = false;
        }
        return bandera;
    }


    private boolean validaPeriodo(){
        boolean bandera = true;
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
            bandera = false;
        }

        if ((filtroBandejaTareaDTO.getFechaFin() != null && filtroBandejaTareaDTO.getFechaInicio() != null)
                && filtroBandejaTareaDTO.getFechaFin().before(filtroBandejaTareaDTO.getFechaInicio())) {
            RequestContext.getCurrentInstance().reset(FORMA_BANDEJA);
            setShowMessages(true);
            filtroBandejaTareaDTO.setFechaFin(null);
            filtroBandejaTareaDTO.setFechaInicio(null);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", getMensajeFechaFin()));
            bandera = false;
        }
        
        if ((filtroBandejaTareaDTO.getFechaFin() != null && filtroBandejaTareaDTO.getFechaInicio() != null)
                && filtroBandejaTareaDTO.getFechaFin().equals(filtroBandejaTareaDTO.getFechaInicio())) {
            RequestContext.getCurrentInstance().reset(FORMA_BANDEJA);
            setShowMessages(true);
            filtroBandejaTareaDTO.setFechaFin(null);
            filtroBandejaTareaDTO.setFechaInicio(null);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", getMensajeFechaFin()));
            bandera = false;
        }
        return bandera;
    }

    /**
     * Metodo para validar campos de filtro de busqueda no esten
     * vacios.
     */
    public boolean filtrosValidos() {
        boolean banderaAsunto = false;
        if (getFiltroBandejaTareaDTO().getNumeroAsunto() != null
                && !getFiltroBandejaTareaDTO().getNumeroAsunto().equals("")) {
            banderaAsunto = true;
        }
        if (getFiltroBandejaTareaDTO().getEstadoProcesal() != null
                && !getFiltroBandejaTareaDTO().getEstadoProcesal().getClave().isEmpty()) {
            banderaAsunto = true;
        }
        if(getFiltroBandejaTareaDTO().getRfcPromovente() !=null && !getFiltroBandejaTareaDTO().getRfcPromovente().equals("")){
            banderaAsunto = true;
        }
        if(getFiltroBandejaTareaDTO().getFechaInicio() != null && getFiltroBandejaTareaDTO().getFechaFin() != null){
            banderaAsunto = true;
        }
        return banderaAsunto;
    }


    /**
     * Metodo que maneja el evento de seleccionar un row.
     * 
     * @param event
     * @throws IOException
     */
    public void onRowDblClckSelect(final SelectEvent event) throws IOException {
        BandejaConsultasDescarga datoSelected = ((BandejaConsultasDescarga) event.getObject());
        DatosBandejaTareaDTO datosSele = new DatosBandejaTareaDTO();
        datosSele.setIdSolicitud(datoSelected.getIdSolicitud());
        datosSele.setTipoTramite(datoSelected.getTipoTramite());
        datosSele.setNumeroAsunto(datoSelected.getNumAsunto());
        datosSele.setEstadoProcesal(datoSelected.getEstadoProcesal().getDescripcion());
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, datosSele);
        inicializarBandeja();
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                .getNavigationHandler();
        configurableNavigationHandler.performNavigation(datoSelected.getUrl() + "?faces-redirect=true");
    }

    /**
     * Metodo para limpiar el filtro de busqueda.
     * 
     * @param actionEvent
     */
    public void limpiarBandeja(ActionEvent actionEvent) {
        filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
        data = new LinkedHashMap<String, String>();
        getDataTableFil().setFilters(data);
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
        return LoginConstante.BANDEJA_ASUNTOS_CONCLUIDOS;
    }
    /**
     * 
     * @return DatosBandejaConsultasDataModelDesc
     */
    public DatosBandejaConsultasDataModelDesc getbandejaDataModelDesc() {
        return bandejaDataModelDesc;
    }

    /**
     * 
     * @param bandejaDataModelDesc
     */
    public void setDatosBandejaConsultasDataModelDesc(DatosBandejaConsultasDataModelDesc bandejaDataModelDesc) {
        this.bandejaDataModelDesc = bandejaDataModelDesc;
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
    public BandejaConsultasDescarga getBandejaTareaDTO() {
        return bandejaTareaDTO;
    }

    /**
     * 
     * @param bandejaTareaDTO
     */
    public void setBandejaTareaDTO(BandejaConsultasDescarga bandejaTareaDTO) {
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
    public List<BandejaConsultasDescarga> getListDatosBandeja() {
        return listDatosBandeja;
    }

    /**
     * 
     * @param listDatosBandeja
     */
    public void setListDatosBandeja(List<BandejaConsultasDescarga> listDatosBandeja) {
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

    public void iniciarBandejaAsuntosConcluidos() {
        this.validaAcceso(RolesConstantes.ROL_EMPLEADO_ADMINISTRADOR,RolesConstantes.ROL_ADMINISTRADOR_GLOBAL);
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

    /**
     * 
     * @return datosSolicitud
     */
    public DatosBandejaTareaDTO getDatosBandejaTareaDTO() {
        return datosSel;
    }

    /**
     * 
     * @param datosSolicitud
     *            el datosSolicitud a ser fijado.
     */
    public void setDatosBandejaTareaDTO(DatosBandejaTareaDTO datosSel) {
        this.datosSel = datosSel;
    }

    public PersonaServices getPersonaServices() {
        return personaServices;
    }


    public void setPersonaServices(PersonaServices personaServices) {
        this.personaServices = personaServices;
    }


    public DataTable getDataTableFil() {
        return dataTableFil;
    }


    public void setDataTableFil(DataTable dataTableFil) {
        this.dataTableFil = dataTableFil;
    }


    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public BandejaLazyAsuntosConcluidos getBandejaLazyAsuntos() {
        return bandejaLazyAsuntos;
    }

    public void setBandejaLazyAsuntos(BandejaLazyAsuntosConcluidos bandejaLazyAsuntos) {
        this.bandejaLazyAsuntos = bandejaLazyAsuntos;
    }

}


