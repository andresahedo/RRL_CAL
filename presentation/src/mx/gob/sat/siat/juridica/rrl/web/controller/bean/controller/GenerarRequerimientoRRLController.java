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
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRequerimiento;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.AbogadoConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.GeneralConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.RequerimientoConstante;
import mx.gob.sat.siat.juridica.rrl.util.excepcion.ParametrosRequerimientoAutoridadException;
import mx.gob.sat.siat.juridica.rrl.util.excepcion.ParametrosRequerimientoPromoventeException;
import mx.gob.sat.siat.juridica.rrl.util.excepcion.RequerimientoVacioException;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.GenerarRequerimientoRRLBussines;
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

/**
 * Bean que implementan la l&oacute;gica de negocio para la
 * generaci&oacute;n de requerimiento.
 * 
 * @author Softtek
 * 
 */
@ManagedBean(name = "generarRequerimientoRRLController")
@ViewScoped
public class GenerarRequerimientoRRLController extends BaseControllerBean {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = -6209469065778736069L;

    /** DTO que representa los datos del requerimiento */
    private RequerimientoDTO requerimiento;
    /** DTO que representa los datos de los requerimiento a guardar */
    private List<RequerimientoDTO> requerimientos = new ArrayList<RequerimientoDTO>();

    private RequerimientoDataModel requerimientoDataModel;
    /** DTO que representa los datos proporcionados por la bandeja */
    private DatosBandejaTareaDTO datosBandeja;
    /** Lista de los tipos de requerimiento */
    private List<CatalogoDTO> listaTiposRequerimiento = new ArrayList<CatalogoDTO>();
    /**
     * Lista de las autoridades a la que se solicitar&aacute; el
     * requerimiento
     */
    private List<CatalogoDTO> listaAutoridades = new ArrayList<CatalogoDTO>();
    /** Lista de Autorizadores */
    private List<CatalogoDTO> listaAutorizadores = new ArrayList<CatalogoDTO>();
    /** identificador de Requerimiento */
    private int indexRequerimiento;

    private RequerimientoDTO selectedReq = new RequerimientoDTO();

    private boolean eliminarVisible;

    private boolean nuevoDocumento;

    private RequerimientoDTO[] registrosEliminar;
    /**
     * Bean para conectar con el backend, atiende todas las peticiones
     * de la generacion del requerimiento.
     */
    @ManagedProperty("#{generarRequerimientoRRLBussines}")
    private GenerarRequerimientoRRLBussines generarRequerimientoBussines;
    /**
     * Bean para obtener los mensajes
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    /**
     * Bean para obtener los mensajes de error
     */
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    /**
     * Post-Constructor para inicializar la lista de autorizadores,
     * tipos de requerimiento y las autoridades a la que se
     * solicitar&aacute; el requerimiento.
     */
    @PostConstruct
    public void iniciar() {
        setDatosBandeja((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));
        setListaAutorizadores(new ArrayList<CatalogoDTO>());
        setListaTiposRequerimiento(new ArrayList<CatalogoDTO>());
        setListaAutoridades(new ArrayList<CatalogoDTO>());
        requerimientoDataModel = new RequerimientoDataModel(requerimientos);
        setRequerimiento(new RequerimientoDTO());
        setListaTiposRequerimiento(getGenerarRequerimientoBussines().obtenerTiposDeRequerimiento());
        setListaAutoridades(getGenerarRequerimientoBussines().obtenerAutoridadesRRL());
        setListaAutorizadores(getGenerarRequerimientoBussines().obtenerAutorizadores(datosBandeja.getNumeroAsunto()));
        setRequerimiento(getGenerarRequerimientoBussines().prepararRequerimiento(getDatosBandeja().getNumeroAsunto(),
                getUserProfile()));
    }

    public void regresarPagina() {
        nuevoDocumento = false;

    }

    public void siguienteValidador() {
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandeja());
        List<DocumentoDTO> documents =
                getGenerarRequerimientoBussines().obtenerDocumentosComplementarios(getDatosBandeja().getIdSolicitud());
        if (documents == null || documents.isEmpty()) {

            nuevoDocumento = false;

            guardarRequerimiento();

        }
        else {
            nuevoDocumento = true;
        }
    }

    /**
     * M&eacute;todo para guardar los requerimientos.
     */
    public void guardarRequerimiento() {
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandeja());
        try {
            getGenerarRequerimientoBussines().generarRequerimiento(getRequerimiento().getRfcAutorizador(),
                    getRequerimientos(), getDatosBandeja(), getUserProfile().getRfc());
            StringBuffer url = new StringBuffer(RequerimientoConstante.BANDEA_JSF);
            getFlash().put(
                    GeneralConstantes.MENSAJE_REDIRECT,
                    "El requerimiento de informaci\u00f3n para el asunto con el n\u00FAmero "
                            + getDatosBandeja().getNumeroAsunto() + " ha sido guardado.");
            ConfigurableNavigationHandler configurableNavigationHandler =
                    (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                            .getNavigationHandler();

            configurableNavigationHandler.performNavigation(url.toString() + "?faces-redirect=true");
            setRequerimientos(new ArrayList<RequerimientoDTO>());

        }
        catch (ParametrosRequerimientoAutoridadException e) {
            nuevoDocumento = false;
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMsg
                            .getString("vuj.resol.requerimiento.mismaAutMismoMomento"), e.getMessage()));
        }
        catch (RequerimientoVacioException e) {
            nuevoDocumento = false;
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMsg
                            .getString("vuj.resol.requerimiento.agregarAlMenosUnReq"), e.getMessage()));

        }
        catch (ParametrosRequerimientoPromoventeException e) {
            nuevoDocumento = false;
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMsg
                            .getString("vuj.resol.requerimiento.promovente"), e.getMessage()));
        }
        catch (BusinessException e) {
            nuevoDocumento = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrio un error desconocido", e.getMessage()));
        }

    }

    public void agregarRequerimiento(ActionEvent actionEvent) {
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
        setRequerimiento(getGenerarRequerimientoBussines().prepararRequerimiento(getDatosBandeja().getNumeroAsunto(),
                getUserProfile()));
        getRequerimiento().setId((long) indexRequerimiento++);

    }

    /**
     * M&eacute;todo para eliminar requerimientos seleccionados
     */
    public void eliminarDocumentos(ActionEvent event) {
        for (RequerimientoDTO requerimitento : getRegistrosEliminar()) {
            getRequerimientos().remove(requerimitento);
        }
        setEliminarVisible(false);

    }

    /**
     * 
     * @return clave del tipo de requerimiento de autoridad
     */
    public String getClaveRequerimientoAutoridad() {
        return TipoRequerimiento.AUTORIDAD.getClave();
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
     * @return listaAutorizadores
     */
    public List<CatalogoDTO> getListaAutorizadores() {
        return listaAutorizadores;
    }

    /**
     * 
     * @param listaAutorizadores
     *            a fijar
     */
    public void setListaAutorizadores(List<CatalogoDTO> listaAutorizadores) {
        this.listaAutorizadores = listaAutorizadores;
    }

    /**
     * 
     * @return generarRequerimientoBussines
     */
    public GenerarRequerimientoRRLBussines getGenerarRequerimientoBussines() {
        return generarRequerimientoBussines;
    }

    /**
     * 
     * @param generarRequerimientoBussines
     *            a fijar
     */
    public void setGenerarRequerimientoBussines(GenerarRequerimientoRRLBussines generarRequerimientoBussines) {
        this.generarRequerimientoBussines = generarRequerimientoBussines;
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
     * 
     * @return errorMsg
     */
    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    /**
     * 
     * @param errorMsg
     *            a fijar
     */
    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * @return the requerimientos
     */
    public List<RequerimientoDTO> getRequerimientos() {
        return requerimientos;
    }

    /**
     * @return the requerimientoDataModel
     */
    public RequerimientoDataModel getRequerimientoDataModel() {
        return requerimientoDataModel;
    }

    /**
     * @param requerimientoDataModel
     *            the requerimientoDataModel to set
     */
    public void setRequerimientoDataModel(RequerimientoDataModel requerimientoDataModel) {
        this.requerimientoDataModel = requerimientoDataModel;
    }

    /**
     * @return the indexRequerimiento
     */
    public int getIndexRequerimiento() {
        return indexRequerimiento;
    }

    /**
     * @param indexRequerimiento
     *            the indexRequerimiento to set
     */
    public void setIndexRequerimiento(int indexRequerimiento) {
        this.indexRequerimiento = indexRequerimiento;
    }

    /**
     * @param requerimientos
     *            the requerimientos to set
     */
    public void setRequerimientos(List<RequerimientoDTO> requerimientos) {
        this.requerimientos = requerimientos;
    }

    /**
     * @return the registrosEliminar
     */
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

    /**
     * @param registrosEliminar
     *            the registrosEliminar to set
     */
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

    public void rowSelectCheckbox(SelectEvent event) {

        setEliminarVisible(true);

    }

    public void rowUnselectCheckbox(UnselectEvent event) {
        if (getRegistrosEliminar().length < 1) {
            setEliminarVisible(false);
        }
    }

    public void cargaDatos() {
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandeja());
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        configurableNavigationHandler.performNavigation(AbogadoConstantes.ANTERIOR_GENERAR_REQUERIMIENTO_JSF
                + "?faces-redirect=true");

    }

    /**
     * @return the selectedReq
     */
    public RequerimientoDTO getSelectedReq() {
        return selectedReq;
    }

    /**
     * @param selectedReq
     *            the selectedReq to set
     */
    public void setSelectedReq(RequerimientoDTO selectedReq) {
        this.selectedReq = selectedReq;
    }

    /**
     * @return the eliminarVisible
     */
    public boolean isEliminarVisible() {
        return eliminarVisible;
    }

    /**
     * @param eliminarVisible
     *            the eliminarVisible to set
     */
    public void setEliminarVisible(boolean eliminarVisible) {
        this.eliminarVisible = eliminarVisible;
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
        this.nuevoDocumento = nuevoDocumento;
    }

}
