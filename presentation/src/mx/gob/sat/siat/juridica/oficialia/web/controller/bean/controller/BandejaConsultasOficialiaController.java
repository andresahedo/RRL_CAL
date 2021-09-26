package mx.gob.sat.siat.juridica.oficialia.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.auditoria.annotation.Audit;
import mx.gob.sat.siat.juridica.auditoria.controller.BaseAuditoriaControllerBean;
import mx.gob.sat.siat.juridica.base.constantes.MenusConstantes;
import mx.gob.sat.siat.juridica.base.constantes.MovimientosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.SistemasConstantes;
import mx.gob.sat.siat.juridica.base.dto.BandejaConsultaRechazoDTO;
import mx.gob.sat.siat.juridica.base.dto.MotivoRechazoDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.model.DatosBandejaConsultasDataModel;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.oficialia.web.controller.bean.bussiness.BandejaConsultasOPBussines;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;
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
import java.io.IOException;
import java.util.*;

@ViewScoped
@ManagedBean(name = "bandejaConsultasOficialia")
public class BandejaConsultasOficialiaController extends BaseAuditoriaControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = -4825094406772240705L;

    @ManagedProperty("#{bandejaConsultasOPBussines}")
    private BandejaConsultasOPBussines bandejaConsultasOPBussines;

    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;

    /**
     * Propiedad que representa un mensaje de error.
     */
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    private FiltroBandejaTareaDTO filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();

    private List<BandejaConsultaRechazoDTO> listDatosBandeja = new ArrayList<BandejaConsultaRechazoDTO>();

    private MotivoRechazoDTO motivoRechazo;

    private BandejaConsultaRechazoDTO bandejaTareaDTO = new BandejaConsultaRechazoDTO();

    private String mensageFechaFin;

    private String mensageFechas;

    private String mensajeBandejaVacia;

    private Date maxFechaFin;

    private Date minFechaFin;

    private DatosBandejaConsultasDataModel bandejaDataModel;

    @PostConstruct
    public void initFiltro() {
        getListDatosBandeja().clear();
        filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
        listDatosBandeja = new ArrayList<BandejaConsultaRechazoDTO>();
        motivoRechazo = new MotivoRechazoDTO();
        getFiltroBandejaTareaDTO().setUsuario(getUserProfile().getRfc());
        setMensajeBandejaVacia("");
    }

    public void buscarTareaBandejaRechazo() {
        if ((filtroBandejaTareaDTO.getFechaFin() != null && filtroBandejaTareaDTO.getFechaInicio() == null)
                || (filtroBandejaTareaDTO.getFechaInicio() != null && filtroBandejaTareaDTO.getFechaFin() == null)) {
            RequestContext.getCurrentInstance().reset("formBandeja");

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
        listDatosBandeja.clear();
        listDatosBandeja.addAll(getBandejaConsultasOPBussines().obtenerTareasRechazo(getFiltroBandejaTareaDTO()));
        if (listDatosBandeja == null || listDatosBandeja.isEmpty()) {
            setMensajeBandejaVacia(messages.getString("vuj.oficialia.grid.vacio"));
        }
    }

    public void buscarDoctosBandejaRechazo() {

        if ((filtroBandejaTareaDTO.getFechaFin() != null && filtroBandejaTareaDTO.getFechaInicio() == null)
                || (filtroBandejaTareaDTO.getFechaInicio() != null && filtroBandejaTareaDTO.getFechaFin() == null)) {
            RequestContext.getCurrentInstance().reset("formBandeja");
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
        listDatosBandeja.clear();
        listDatosBandeja.addAll(getBandejaConsultasOPBussines().obtenerTareasDoctoRechazo(filtroBandejaTareaDTO));
        if (listDatosBandeja == null || listDatosBandeja.isEmpty()) {
            setMensajeBandejaVacia(messages.getString("vuj.oficialia.grid.vacio"));
        }
    }

    public void onRowDblClckSelect(final SelectEvent event) throws IOException {
        DatosBandejaTareaDTO datoSelected = ((DatosBandejaTareaDTO) event.getObject());
        DatosBandejaTareaDTO datosSel = new DatosBandejaTareaDTO();
        datosSel.setIdSolicitud(datoSelected.getIdSolicitud());
        datosSel.setTipoTramite(datoSelected.getTipoTramite());
        datosSel.setNumeroAsunto(datoSelected.getNumeroAsunto());
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, datosSel);
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();
        configurableNavigationHandler.performNavigation(datoSelected.getUrl() + "?faces-redirect=true");
    }

    public void limpiarBandeja() {
        filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
        listDatosBandeja.clear();
        setMensajeBandejaVacia("");
        RequestContext.getCurrentInstance().reset("formBandeja");
    }

    public void rangoMaxFechas() {
        Date minFechFin = new Date();
        minFechFin.setTime(filtroBandejaTareaDTO.getFechaInicio().getTime());
        setMinFechaFin(minFechFin);
    }

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

    public Date getFechaInicio() {
        if (filtroBandejaTareaDTO != null) {
            return filtroBandejaTareaDTO.getFechaInicio();
        }
        return null;

    }

    public BandejaConsultasOPBussines getBandejaConsultasOPBussines() {
        return bandejaConsultasOPBussines;
    }

    public void setBandejaConsultasOPBussines(BandejaConsultasOPBussines bandejaConsultasOPBussines) {
        this.bandejaConsultasOPBussines = bandejaConsultasOPBussines;
    }

    public FiltroBandejaTareaDTO getFiltroBandejaTareaDTO() {
        return filtroBandejaTareaDTO;
    }

    public void setFiltroBandejaTareaDTO(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {
        this.filtroBandejaTareaDTO = filtroBandejaTareaDTO;
    }

    public ResourceBundle getMessages() {
        return messages;
    }

    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    public String getMensajeBandejaVacia() {
        return mensajeBandejaVacia;
    }

    public void setMensajeBandejaVacia(String mensajeBandejaVacia) {
        this.mensajeBandejaVacia = mensajeBandejaVacia;
    }

    public Date getMinFechaFin() {
        return minFechaFin != null ? (Date) minFechaFin.clone() : null;
    }

    public String getMensageFechaFin() {
        mensageFechaFin = errorMsg.getString("validation.fechaInicialMayor");
        return mensageFechaFin;
    }

    public void setMensageFechaFin(String mensageFechaFin) {
        this.mensageFechaFin = mensageFechaFin;
    }

    public String getMensageFechas() {
        mensageFechas = errorMsg.getString("validation.fechas");
        return mensageFechas;
    }

    public void setMensageFechas(String mensageFechas) {
        this.mensageFechas = mensageFechas;
    }

    public Date getMaxFechaInicio() {
        return new Date();
    }

    public List<BandejaConsultaRechazoDTO> getListDatosBandeja() {
        return listDatosBandeja;
    }

    public void setListDatosBandeja(List<BandejaConsultaRechazoDTO> listDatosBandeja) {
        this.listDatosBandeja = listDatosBandeja;
    }

    public DatosBandejaConsultasDataModel getBandejaDataModel() {
        return bandejaDataModel;
    }

    public void setBandejaDataModel(DatosBandejaConsultasDataModel bandejaDataModel) {
        this.bandejaDataModel = bandejaDataModel;
    }

    public BandejaConsultaRechazoDTO getBandejaTareaDTO() {
        return bandejaTareaDTO;
    }

    public void setBandejaTareaDTO(BandejaConsultaRechazoDTO bandejaTareaDTO) {
        this.bandejaTareaDTO = bandejaTareaDTO;
    }

    public MotivoRechazoDTO getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(MotivoRechazoDTO motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Audit(identificadorProceso = ProcesosConstantes.PROCESO_EMPLEADO_RECHAZOS_OFICIALIA,
            movimiento = MovimientosConstantes.MOVIMIENTO_OFICIAL_INGRESO_RECHAZOS,
            claveSistema = SistemasConstantes.SISTEMA_RRL, observaciones = "Consulta de rechazos Oficial de Partes")
    public void validaAccesoOficialia() {
        // Validacion de acceso a la funcionalidad para el rol de
        // oficialia
        this.validaAccesoProcesoMenu(ProcesosConstantes.PROCESO_EMPLEADO_REGISTRO_OFICIALIA,
                MenusConstantes.MENU_EMPLEADOS);
    }

}
