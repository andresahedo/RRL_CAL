package mx.gob.sat.siat.juridica.cal.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRequerimiento;
import mx.gob.sat.siat.juridica.base.dto.AbogadoDTO;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.business.GenerarRequerimientoCALBussines;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.GeneralConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.RequerimientoConstante;
import mx.gob.sat.siat.juridica.rrl.util.excepcion.ParametrosRequerimientoAutoridadException;
import mx.gob.sat.siat.juridica.rrl.util.excepcion.ParametrosRequerimientoPromoventeException;
import mx.gob.sat.siat.juridica.rrl.util.excepcion.RequerimientoVacioException;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.RequerimientoDataModel;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "generarRequerimientoCALController")
@ViewScoped
public class GenerarRequerimientoCALController extends BaseControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = -5049273534212285523L;

    private RequerimientoDTO requerimiento;

    private List<RequerimientoDTO> requerimientos = new ArrayList<RequerimientoDTO>();

    private RequerimientoDataModel requerimientoDataModel;

    private DatosBandejaTareaDTO datosBandejaTareaDTO;

    private List<CatalogoDTO> listaTiposRequerimiento = new ArrayList<CatalogoDTO>();

    private List<CatalogoDTO> listaAutoridades = new ArrayList<CatalogoDTO>();

    private List<CatalogoDTO> listaAutorizadores = new ArrayList<CatalogoDTO>();

    private RequerimientoDTO selectedReq = new RequerimientoDTO();

    private RequerimientoDTO[] registrosEliminar;

    private CatalogoDTO selectedAutorizador;

    private AbogadoDTO abogadoDTO;

    private boolean eliminarVisible;

    private int indexRequerimiento;

    @ManagedProperty("#{generarRequerimientoCALBussines}")
    private transient GenerarRequerimientoCALBussines generarRequerimientoCALBussines;

    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;

    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    @PostConstruct
    public void iniciarDatos() {
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));
        setListaAutorizadores(new ArrayList<CatalogoDTO>());
        setListaTiposRequerimiento(new ArrayList<CatalogoDTO>());
        setListaAutoridades(new ArrayList<CatalogoDTO>());
        requerimientoDataModel = new RequerimientoDataModel(requerimientos);
        setRequerimiento(new RequerimientoDTO());
        setListaTiposRequerimiento(getGenerarRequerimientoCALBussines().obtenerTiposDeRequerimiento());
        setListaAutoridades(getGenerarRequerimientoCALBussines().obtenerAutoridadesCAL());
        setListaAutorizadores(getGenerarRequerimientoCALBussines().obtenerAutorizadores(
                getDatosBandejaTareaDTO().getNumeroAsunto()));
        setRequerimiento(getGenerarRequerimientoCALBussines().prepararRequerimiento(
                getDatosBandejaTareaDTO().getNumeroAsunto(), getUserProfile()));
    }

    public void guardarRequerimiento(ActionEvent actionEvent) {
        try {
            getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
            getGenerarRequerimientoCALBussines().generarRequerimiento(getRequerimiento().getRfcAutorizador(),
                    getRequerimientos(), getDatosBandejaTareaDTO(), getUserProfile().getRfc());
            StringBuffer url = new StringBuffer(RequerimientoConstante.BANDEA_JSF);
            getFlash().put(
                    GeneralConstantes.MENSAJE_REDIRECT,
                    "El requerimiento de informaci\u00f3n para el asunto con el n\u00FAmero "
                            + getDatosBandejaTareaDTO().getNumeroAsunto() + " ha sido guardado.");

            ConfigurableNavigationHandler configurableNavigationHandler =
                    (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                            .getNavigationHandler();

            configurableNavigationHandler.performNavigation(url.toString() + "?faces-redirect=true");
            setRequerimientos(new ArrayList<RequerimientoDTO>());

        }
        catch (RequerimientoVacioException e) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMsg
                            .getString("vuj.resol.requerimiento.agregarAlMenosUnReq"), e.getMessage()));

        }
        catch (ParametrosRequerimientoPromoventeException e) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "S\u00f3lo puede generar un requerimiento de tipo Promovente.", e.getMessage()));
        }

        catch (ParametrosRequerimientoAutoridadException e) {
            FacesContext
                    .getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "S\u00f3lo puede generar un requerimiento de tipo Retroalimentaci\u00f3n a la misma Autoridad.",
                                    e.getMessage()));
        }
        catch (BusinessException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrio un error desconocido", e.getMessage()));
        }

    }

    public void agregarRequerimiento(ActionEvent actionEvent) {
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        if (getRequerimientos() == null) {
            setRequerimientos(new ArrayList<RequerimientoDTO>());
        }
        for (CatalogoDTO tiposReq : getListaTiposRequerimiento()) {
            if (getRequerimiento().getClaveTipoRequerimiento().equals(tiposReq.getClave())) {
                getRequerimiento().setTipoRequerimiento(tiposReq.getDescripcion());
                Date id = new Date();
                getRequerimiento().setId(id.getTime());
            }

        }
        if (getRequerimiento().getClaveUnidadAdministrativa() != null) {
            for (CatalogoDTO autoridades : getListaAutoridades()) {
                if (getRequerimiento().getClaveUnidadAdministrativa().equals(autoridades.getClave())) {
                    getRequerimiento().setUnidadAdministrativa(autoridades.getDescripcion());
                }
            }
        }
        getRequerimientos().add(getRequerimiento());

        setRequerimientoDataModel(new RequerimientoDataModel(getRequerimientos()));
        setRequerimiento(new RequerimientoDTO());
        setRequerimiento(getGenerarRequerimientoCALBussines().prepararRequerimiento(
                getDatosBandejaTareaDTO().getNumeroAsunto(), getUserProfile()));
        getRequerimiento().setId((long) indexRequerimiento++);

    }

    public RequerimientoDTO[] getRegistrosEliminar() {
        if (registrosEliminar != null) {
            RequerimientoDTO[] arreglo = new RequerimientoDTO[registrosEliminar.length];
            for (int i = 0; i < registrosEliminar.length; i++) {
                RequerimientoDTO doc = registrosEliminar[i];
                arreglo[i] = doc;
            }
            return arreglo;
        }
        else {
            return null;
        }
    }

    public void setRegistrosEliminar(RequerimientoDTO[] registrosEliminarArray) {
        if (registrosEliminarArray != null) {
            RequerimientoDTO[] registrosEliminarLocal = registrosEliminarArray.clone();
            RequerimientoDTO[] arreglo = new RequerimientoDTO[registrosEliminarLocal.length];
            for (int i = 0; i < registrosEliminarLocal.length; i++) {
                RequerimientoDTO doc = registrosEliminarLocal[i];
                arreglo[i] = doc;
            }
            this.registrosEliminar = arreglo;
        }
        else {
            this.registrosEliminar = null;
        }
    }

    public void eliminarDocumentos(ActionEvent event) {
        for (RequerimientoDTO requerimitento : getRegistrosEliminar()) {
            getRequerimientos().remove(requerimitento);
        }
        setEliminarVisible(false);
    }

    public void cargaDatos() {
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
    }

    public RequerimientoDTO getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(RequerimientoDTO requerimiento) {
        this.requerimiento = requerimiento;
    }

    public List<RequerimientoDTO> getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(List<RequerimientoDTO> requerimientos) {
        this.requerimientos = requerimientos;
    }

    public RequerimientoDataModel getRequerimientoDataModel() {
        return requerimientoDataModel;
    }

    public void setRequerimientoDataModel(RequerimientoDataModel requerimientoDataModel) {
        this.requerimientoDataModel = requerimientoDataModel;
    }

    public List<CatalogoDTO> getListaTiposRequerimiento() {
        return listaTiposRequerimiento;
    }

    public void setListaTiposRequerimiento(List<CatalogoDTO> listaTiposRequerimiento) {
        this.listaTiposRequerimiento = listaTiposRequerimiento;
    }

    public List<CatalogoDTO> getListaAutoridades() {
        return listaAutoridades;
    }

    public void setListaAutoridades(List<CatalogoDTO> listaAutoridades) {
        this.listaAutoridades = listaAutoridades;
    }

    public List<CatalogoDTO> getListaAutorizadores() {
        return listaAutorizadores;
    }

    public void setListaAutorizadores(List<CatalogoDTO> listaAutorizadores) {
        this.listaAutorizadores = listaAutorizadores;
    }

    public RequerimientoDTO getSelectedReq() {
        return selectedReq;
    }

    public void setSelectedReq(RequerimientoDTO selectedReq) {
        this.selectedReq = selectedReq;
    }

    public GenerarRequerimientoCALBussines getGenerarRequerimientoCALBussines() {
        return generarRequerimientoCALBussines;
    }

    public void setGenerarRequerimientoBussines(GenerarRequerimientoCALBussines generarRequerimientoCALBussines) {
        this.generarRequerimientoCALBussines = generarRequerimientoCALBussines;
    }

    public ResourceBundle getMessages() {
        return messages;
    }

    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    public CatalogoDTO getSelectedAutorizador() {
        return selectedAutorizador;
    }

    public void setSelectedAutorizador(CatalogoDTO selectedAutorizador) {
        this.selectedAutorizador = selectedAutorizador;
    }

    public int getIndexRequerimiento() {
        return indexRequerimiento;
    }

    public void setIndexRequerimiento(int indexRequerimiento) {
        this.indexRequerimiento = indexRequerimiento;
    }

    public AbogadoDTO getAbogadoDTO() {
        return abogadoDTO;
    }

    public void setAbogadoDTO(AbogadoDTO abogadoDTO) {
        this.abogadoDTO = abogadoDTO;
    }

    public void setGenerarRequerimientoCALBussines(GenerarRequerimientoCALBussines generarRequerimientoCALBussines) {
        this.generarRequerimientoCALBussines = generarRequerimientoCALBussines;
    }

    public void rowSelectCheckbox(SelectEvent event) {
        setEliminarVisible(true);
    }

    public void rowUnselectCheckbox(UnselectEvent event) {
        if (getRegistrosEliminar().length < 1) {
            setEliminarVisible(false);
        }
    }

    public boolean isEliminarVisible() {
        return eliminarVisible;
    }

    public void setEliminarVisible(boolean eliminarVisible) {
        this.eliminarVisible = eliminarVisible;
    }

    public String getClaveRequerimientoAutoridad() {
        return TipoRequerimiento.RETROALIMENTACION_CAL.getClave();
    }

    public DatosBandejaTareaDTO getDatosBandejaTareaDTO() {
        return datosBandejaTareaDTO;
    }

    public void setDatosBandejaTareaDTO(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        this.datosBandejaTareaDTO = datosBandejaTareaDTO;
    }

}
