/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BandejaPendientesBusiness;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaPendientesDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaPendientesDTO;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DatosBandejaPendientesDataModel;
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

@ViewScoped
@ManagedBean
public class BandejaPendientesController extends BaseControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{bandejaPendientesBusiness}")
    private BandejaPendientesBusiness bandejaPendientesBusiness;

    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;

    /**
     * Propiedad que representa un mensaje de error.
     */
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    private DatosBandejaPendientesDTO datosBandejaPendientesDTO = new DatosBandejaPendientesDTO();

    private DatosBandejaPendientesDataModel datosBandejaPendientesDataModel;

    private FiltroBandejaPendientesDTO filtroBandejaPendientesDTO = new FiltroBandejaPendientesDTO();

    private List<DatosBandejaPendientesDTO> listaDatosBandejaPendientes = new ArrayList<DatosBandejaPendientesDTO>();

    private Date minFechaFin;

    private String mensajeBandejaVacia = "";

    private Date maxFechaFin;

    private Date minFechaFinFin;

    public String getMensageFechaFin() {
        return errorMsg.getString("validation.fechaFin");
    }

    public String getMensageFechas() {
        return errorMsg.getString("validation.fechas");
    }

    public BandejaPendientesController() {
        datosBandejaPendientesDataModel = new DatosBandejaPendientesDataModel(listaDatosBandejaPendientes);
    }

    public void onRowDblClckSelect(final SelectEvent event) throws IOException {
        DatosBandejaPendientesDTO datoSelected = ((DatosBandejaPendientesDTO) event.getObject());
        getFlash().put("idSolicitud", datoSelected.getIdSolicitud());
        inicializaBandeja();
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();
        configurableNavigationHandler.performNavigation(datoSelected.getUrl() + "?faces-redirect=true");
    }

    @PostConstruct
    public void inicializaBandeja() {
        listaDatosBandejaPendientes.clear();
        filtroBandejaPendientesDTO = new FiltroBandejaPendientesDTO();
        setMensajeBandejaVacia("");
        rangoMaxFechas();

    }

    public void rangoMaxFechas() {
        Date minFechFin = new Date();

        Calendar fechaMin = Calendar.getInstance();
        fechaMin.setTime(minFechFin);
        fechaMin.add(Calendar.DAY_OF_YEAR, -NumerosConstantes.TRES);
        fechaMin.set(Calendar.HOUR_OF_DAY, 0);
        fechaMin.clear(Calendar.MINUTE);
        fechaMin.clear(Calendar.SECOND);
        fechaMin.clear(Calendar.MILLISECOND);
        setMinFechaFin(fechaMin.getTime());
        if (filtroBandejaPendientesDTO.getFechaInicio() != null) {
            setMinFechaFinFin(filtroBandejaPendientesDTO.getFechaInicio());
        }
        else {

            setMinFechaFinFin(fechaMin.getTime());
        }

        setMaxFechaFin(new Date());
        filtroBandejaPendientesDTO.setFechaFin(null);
    }

    public void buscarPendientes() {

        if ((filtroBandejaPendientesDTO.getFechaFin() != null && filtroBandejaPendientesDTO.getFechaInicio() == null)
                || (filtroBandejaPendientesDTO.getFechaInicio() != null && filtroBandejaPendientesDTO.getFechaFin() == null)) {
            RequestContext.getCurrentInstance().reset("formBandejaPendientes");

            filtroBandejaPendientesDTO.setFechaFin(null);
            filtroBandejaPendientesDTO.setFechaInicio(null);

            FacesContext context = FacesContext.getCurrentInstance();
            UIInput fechaFinInput = (UIInput) context.getViewRoot().findComponent(":formBandejaPendientes:fechaFin");
            fechaFinInput.setValid(false);
            UIInput fechaInicioInput =
                    (UIInput) context.getViewRoot().findComponent(":formBandejaPendientes:fechaInicio");
            fechaInicioInput.setValid(false);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", getMensageFechas()));
            return;

        }

        if ((filtroBandejaPendientesDTO.getFechaFin() != null && filtroBandejaPendientesDTO.getFechaInicio() != null)
                && filtroBandejaPendientesDTO.getFechaFin().before(filtroBandejaPendientesDTO.getFechaInicio())) {
            RequestContext.getCurrentInstance().reset("formBandejaPendientes");
            filtroBandejaPendientesDTO.setFechaFin(null);
            filtroBandejaPendientesDTO.setFechaInicio(null);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", getMensageFechaFin()));
            return;
        }

        filtroBandejaPendientesDTO.setRfcPromovente(getUserProfile().getRfc());
        FiltroBandejaPendientesDTO filtro = new FiltroBandejaPendientesDTO();
        filtro.setFechaFin(getFiltroBandejaPendientesDTO().getFechaFin());
        filtro.setFechaInicio(getFiltroBandejaPendientesDTO().getFechaInicio());
        filtro.setIdSolicitud(getFiltroBandejaPendientesDTO().getIdSolicitud());
        filtro.setRfcPromovente(getFiltroBandejaPendientesDTO().getRfcPromovente());
        listaDatosBandejaPendientes.clear();
        if (filtroBandejaPendientesDTO.getFechaInicio() == null && filtroBandejaPendientesDTO.getFechaFin() == null) {
            filtro.setFechaInicio(getMinFechaFin());
            filtro.setFechaFin(getMaxFechaFin());
        }
        listaDatosBandejaPendientes.addAll(bandejaPendientesBusiness.obtenerPendientes(filtro));
        if (listaDatosBandejaPendientes.isEmpty()) {
            setMensajeBandejaVacia(messages.getString("vuj.bandejaPendientes.bandejaVacia"));

        }

    }

    public void limpiarBandejaPendientes(ActionEvent actionEvent) {
        filtroBandejaPendientesDTO = new FiltroBandejaPendientesDTO();
        listaDatosBandejaPendientes.clear();
        setMensajeBandejaVacia("");
        RequestContext.getCurrentInstance().reset("formBandejaPendientes");
    }

    public boolean filtroValido() {

        if ((getFiltroBandejaPendientesDTO().getIdSolicitud() != null && getFiltroBandejaPendientesDTO()
                .getIdSolicitud().toString().equals(""))
                || (getFiltroBandejaPendientesDTO().getFechaInicio() != null && getFiltroBandejaPendientesDTO()
                        .getFechaFin() != null)) {

            return true;
        }
        return false;
    }

    public BandejaPendientesBusiness getBandejaPendientesBusiness() {
        return bandejaPendientesBusiness;
    }

    public void setBandejaPendientesBusiness(BandejaPendientesBusiness bandejaPendientesBusiness) {
        this.bandejaPendientesBusiness = bandejaPendientesBusiness;
    }

    public FiltroBandejaPendientesDTO getFiltroBandejaPendientesDTO() {
        return filtroBandejaPendientesDTO;
    }

    public void setFiltroBandejaPendientesDTO(FiltroBandejaPendientesDTO filtroBandejaPendientesDTO) {
        this.filtroBandejaPendientesDTO = filtroBandejaPendientesDTO;
    }

    public ResourceBundle getMessages() {
        return messages;
    }

    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    public DatosBandejaPendientesDataModel getDatosBandejaPendientesDataModel() {
        return datosBandejaPendientesDataModel;
    }

    public void setDatosBandejaPendientesDataModel(DatosBandejaPendientesDataModel datosBandejaPendientesDataModel) {
        this.datosBandejaPendientesDataModel = datosBandejaPendientesDataModel;
    }

    public DatosBandejaPendientesDTO getDatosBandejaPendientesDTO() {
        return datosBandejaPendientesDTO;
    }

    public void setDatosBandejaPendientesDTO(DatosBandejaPendientesDTO datosBandejaPendientesDTO) {
        this.datosBandejaPendientesDTO = datosBandejaPendientesDTO;
    }

    public List<DatosBandejaPendientesDTO> getListaDatosBandejaPendientes() {
        return listaDatosBandejaPendientes;
    }

    public void setListaDatosBandejaPendientes(List<DatosBandejaPendientesDTO> listaDatosBandejaPendientes) {
        this.listaDatosBandejaPendientes = listaDatosBandejaPendientes;
    }

    public Date getMinFechaFin() {
        return minFechaFin != null ? (Date) minFechaFin.clone() : null;
    }

    public void setMinFechaFin(Date minFechaFin) {
        if (minFechaFin != null) {
            this.minFechaFin = (Date) minFechaFin.clone();
        }
        else {
            this.minFechaFin = null;
        }
    }

    public String getMensajeBandejaVacia() {
        return mensajeBandejaVacia;
    }

    public void setMensajeBandejaVacia(String mensajeBandejaVacia) {
        this.mensajeBandejaVacia = mensajeBandejaVacia;
    }

    public Date getMaxFechaFin() {
        if (maxFechaFin != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(maxFechaFin);
            return cal.getTime();
        }
        else {
            return null;
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

    public Date getMaxFechaInicio() {

        return new Date();
    }

    /**
     * @return the errorMsg
     */
    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg
     *            the errorMsg to set
     */
    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void validaAccesoProcesoMenu() {
        // Validacion de acceso a la funcionalidad
        this.validaAccesoContribuytente();
    }

    public Date getMinFechaFinFin() {
        if (minFechaFinFin != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(minFechaFinFin);
            return cal.getTime();
        }
        else {
            return null;
        }
    }

    public void setMinFechaFinFin(Date minFechaFinFin) {
        if (minFechaFinFin != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(minFechaFinFin);
            this.minFechaFinFin = cal.getTime();
        }
        else {
            this.minFechaFinFin = null;
        }
    }

}
