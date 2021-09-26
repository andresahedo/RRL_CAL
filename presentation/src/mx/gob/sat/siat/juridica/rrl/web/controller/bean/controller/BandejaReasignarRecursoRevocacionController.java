/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRol;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.GeneralConstantes;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.BandejaReasignarRecursoRevocacionBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.BandejaReasignacionLazyList;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DatosBandejaDataModel;

@ManagedBean
@ViewScoped
public class BandejaReasignarRecursoRevocacionController extends BaseControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @ManagedProperty("#{bandejaReasignarRecursoRevocacionBussines}")
    private BandejaReasignarRecursoRevocacionBussines bandejaReasignarRecursoRevocacionBussines;

    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    private DatosBandejaDataModel bandejaDataModel;
    private FiltroBandejaTareaDTO filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
    private DatosBandejaTareaDTO bandejaTareaDTO = new DatosBandejaTareaDTO();
    private List<DatosBandejaTareaDTO> listDatosBandeja = new ArrayList<DatosBandejaTareaDTO>();
    private String mensajeBandeja = "";
    private Boolean showMessages;

    private BandejaReasignacionLazyList bandejaReasignacionLazyList;
    private transient DataTable dataTableFil = new DataTable();
    private Map<String, String> data = new LinkedHashMap<String, String>();

    @PostConstruct
    public void initFiltro() {
        bandejaReasignacionLazyList =
                new BandejaReasignacionLazyList(listDatosBandeja, bandejaReasignarRecursoRevocacionBussines);
        String show = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(GeneralConstantes.MENSAJE_REDIRECT);
        setShowMessages( show == null);
    }

    public void buscarTareaBandeja(ActionEvent actionEvent) {
        if (validaFiltro()) {
            if (filtroBandejaTareaDTO.getRfcFuncionario() != null
                    && !filtroBandejaTareaDTO.getRfcFuncionario().isEmpty()) {
                if (filtroBandejaTareaDTO.getRfcFuncionario().length() < NumerosConstantes.CUATRO) {
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                    .getString("vuj.bandeja.reasignar.recurso.rfc")));
                    return;
                }
            }
            filtroBandejaTareaDTO.setUsuario(getUserProfile().getRfc());
            listDatosBandeja.clear();
            data.put("numeroAsunto", filtroBandejaTareaDTO.getNumeroAsunto());
            data.put("rfcFuncionario", filtroBandejaTareaDTO.getRfcFuncionario());
            data.put("rol", TipoRol.ADMINISTRADOR.getClave());
            data.put("usuario", filtroBandejaTareaDTO.getUsuario());
            getDataTableFil().setFilters(data);
            setMensajeBandeja(messages.getString("vuj.grid.busquedaVacia"));
        }
        else {
            setShowMessages(true);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                            .getString("vuj.bandeja.reasignar.seleccionar.alMenosUnCriterioBusqueda")));
        }
    }

    public boolean validaFiltro() {
        if (filtroBandejaTareaDTO.getNumeroAsunto() != null && !filtroBandejaTareaDTO.getNumeroAsunto().isEmpty()
                || filtroBandejaTareaDTO.getRfcFuncionario() != null
                && !filtroBandejaTareaDTO.getRfcFuncionario().isEmpty()) {
            return true;
        }
        return false;
    }

    public void onRowDblClckSelect(final SelectEvent event) throws IOException {
        DatosBandejaTareaDTO datoSelected = ((DatosBandejaTareaDTO) event.getObject());
        getFlash().put("idSolicitud", datoSelected.getIdSolicitud());
        getFlash().put("tipoTramite", datoSelected.getTipoTramite());
        getFlash().put("numAsunto", datoSelected.getNumeroAsunto());

        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, datoSelected);

        getFlash().put("datosBandejaDTO", datoSelected);
        getFlash().put("reasignar", true);

        listDatosBandeja.clear();
        filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
        setMensajeBandeja("");
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        configurableNavigationHandler.performNavigation(datoSelected.getUrl() + "?faces-redirect=true");

    }

    public List<DatosBandejaTareaDTO> getListDatosBandeja() {
        return listDatosBandeja;
    }

    public void setListDatosBandeja(List<DatosBandejaTareaDTO> listDatosBandeja) {
        this.listDatosBandeja = listDatosBandeja;
    }

    public void limpiarBandeja(ActionEvent actionEvent) {
        filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
        setMensajeBandeja("");
        data = new LinkedHashMap<String, String>();
        getDataTableFil().setFilters(data);
    }

    public BandejaReasignarRecursoRevocacionController() {
        bandejaDataModel = new DatosBandejaDataModel(listDatosBandeja);
    }

    public DatosBandejaDataModel getBandejaDataModel() {
        return bandejaDataModel;
    }

    public void setBandejaDataModel(DatosBandejaDataModel bandejaDataModel) {
        this.bandejaDataModel = bandejaDataModel;
    }

    public FiltroBandejaTareaDTO getFiltroBandejaTareaDTO() {
        return filtroBandejaTareaDTO;
    }

    public void setFiltroBandejaTareaDTO(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {
        this.filtroBandejaTareaDTO = filtroBandejaTareaDTO;
    }

    public DatosBandejaTareaDTO getBandejaTareaDTO() {
        return bandejaTareaDTO;
    }

    public void setBandejaTareaDTO(DatosBandejaTareaDTO bandejaTareaDTO) {
        this.bandejaTareaDTO = bandejaTareaDTO;
    }

    public BandejaReasignarRecursoRevocacionBussines getBandejaReasignarRecursoRevocacionBussines() {
        return bandejaReasignarRecursoRevocacionBussines;
    }

    public void setBandejaReasignarRecursoRevocacionBussines(
            BandejaReasignarRecursoRevocacionBussines bandejaReasignarRecursoRevocacionBussines) {
        this.bandejaReasignarRecursoRevocacionBussines = bandejaReasignarRecursoRevocacionBussines;
    }

    public ResourceBundle getMessages() {
        return messages;
    }

    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    public String getMensajeBandeja() {
        return mensajeBandeja;
    }

    public void setMensajeBandeja(String mensajeBandeja) {
        this.mensajeBandeja = mensajeBandeja;
    }

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    public BandejaReasignacionLazyList getBandejaReasignacionLazyList() {
        return bandejaReasignacionLazyList;
    }

    public void setBandejaReasignacionLazyList(BandejaReasignacionLazyList bandejaReasignacionLazyList) {
        this.bandejaReasignacionLazyList = bandejaReasignacionLazyList;
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

    public Boolean getShowMessages() {
        return showMessages;
    }

    public void setShowMessages(Boolean showMessages) {
        this.showMessages = showMessages;
    }

}
