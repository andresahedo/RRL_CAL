/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.controller.emitirresolucion;

import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionAbogadoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.AbogadoConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.GeneralConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.emitirresolucion.EmitirResolucionBusiness;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.ResolucionDataModel;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility.CatalogoAutorizadorConverter;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility.ResolucionesComparator;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.*;

/**
 * Clase que controla la pantalla de Abogado emiir resolu&oacute;n.
 * 
 * @author Softtek
 * 
 */
@ManagedBean(name = "emitirResolucionController")
@ViewScoped
public class EmitirResolucionController extends BaseControllerBean {

    private static final long serialVersionUID = -404228437115513886L;

    /**
     * Bean para conectar con el backend, atiende todas las peticiones
     * de la emision de resoluciones.
     */
    @ManagedProperty("#{emitirResolucionBusiness}")
    private EmitirResolucionBusiness emitirResolucionBusiness;

    @ManagedProperty("#{autorizadorConverter}")
    private transient CatalogoAutorizadorConverter catalogoAutorizadorConverter;

    /**
     * Bean para obtener los mensajes
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;

    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    /**
     * Estructura para mantener la infromacion de la tabla de
     * resoluciones.
     */
    private ResolucionDataModel resolucionesDataModel;

    /**
     * Objeto que contiene la lista de resolucionesImpuganadas
     */
    private ResolucionAbogadoDTO resolucionAbogado;

    /**
     * Objeto que corresponde con la resolucion a la que se agregan
     * agravios
     */
    private ResolucionImpugnadaDTO selectedResolucionImpugnada;

    /**
     * 
     */
    private List<CatalogoDTO> sentidosResolucion;
    /**
     * Mapa que contiene la resoluciones perdedoras
     */
    private Map<String,CatalogoDTO> sentidosResolucionPerdedores;

    /**
     * Lista para mostrar agravios.
     */
    private DualListModel<AgraviosDTO> agravios;

    /**
     * Lista donde se guardan las lista de agravios del catalogo
     */
    private List<AgraviosDTO> agraviosSource;

    /**
     * Contiene el nombre completo del funcionario
     */
    private String nombreFuncionario;

    /**
     * Determina si el panel de agravios se muestra o no
     */
    private boolean mostrarPanelAgravios;

    /**
     * DTO para poder avanzar tarea
     */
    private DatosBandejaTareaDTO datosBandejaTarea;

    /**
     * Lista de autorizadores a quienes se puede asignar la tarea
     */
    private List<CatalogoDTO> autorizadores;

    /**
     * Autorizador al que se le asigna la sig. tarea
     */
    private CatalogoDTO selectedAutorizador;

    private String messagesRedirect;

    private boolean nuevoDocumento = false;

    private Date fechaMaxima;

    /**
     * Constructor
     */
    public EmitirResolucionController() {

    }

    /**
     * M&ecaute;todo para inicializar los elementos de la pantalla.
     */

    public void init() {
        fechaMaxima = new Date();
        sentidosResolucion = emitirResolucionBusiness.obtenerSentidosResolucion();
        sentidosResolucionPerdedores = emitirResolucionBusiness.obtenerSentidosResolucionPerdedores();

        agravios =
                new DualListModel<AgraviosDTO>(emitirResolucionBusiness.obtenerAgravios(), new ArrayList<AgraviosDTO>());
        agraviosSource = agravios.getSource();

        nombreFuncionario =
                getUserProfile().getNombreCompleto() + " " + getUserProfile().getPrimerApellido() + " "
                        + getUserProfile().getSegundoApellido();

        SolicitudDTO solicitud = new SolicitudDTO();
        solicitud.setIdSolicitud(resolucionAbogado.getSolicitudDTO().getIdSolicitud());
        solicitud.setTipoTramite(resolucionAbogado.getSolicitudDTO().getTipoTramite());
        TramiteDTO dto = new TramiteDTO();
        dto.setNumeroAsunto(resolucionAbogado.getTramiteDTO().getNumeroAsunto());

        resolucionAbogado = emitirResolucionBusiness.mostrarResoluciones(solicitud, dto);

        resolucionesDataModel = new ResolucionDataModel(resolucionAbogado.getResolucionesDTO());

        mostrarPanelAgravios = false;

        autorizadores = emitirResolucionBusiness.obtenerAutorizadores(datosBandejaTarea.getNumeroAsunto());
        this.catalogoAutorizadorConverter.setNumAsunto(datosBandejaTarea.getNumeroAsunto());
        this.catalogoAutorizadorConverter.setAutorizadores(emitirResolucionBusiness
                .obtenerAutorizadores(datosBandejaTarea.getNumeroAsunto()));

    }

    @PostConstruct
    public void iniciarEmision() {
        setResolucionAbogado((ResolucionAbogadoDTO) getFlash().get("abogado"));
        setDatosBandejaTarea((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));
        init();
    }

    public void anterior() {

        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTarea());
        configurableNavigationHandler.performNavigation(AbogadoConstantes.ATENDER_ASUNTO + "?faces-redirect=true");
    }

    /**
     * M&eacute;todo para guardar las resoluciones y agravios.
     */
    public void guardarResolucionesImpugnadas() {
        getFlash().put("abogado", getResolucionAbogado());
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, datosBandejaTarea);
        List<ResolucionImpugnadaDTO> resolImpugnadas = resolucionAbogado.getResolucionesDTO();
        List<DocumentoDTO> documents
                = emitirResolucionBusiness.obtenerDocumentosComplementarios(getDatosBandejaTarea().getIdSolicitud());
        if (resolImpugnadas != null && !resolImpugnadas.isEmpty()) {
            for (ResolucionImpugnadaDTO resolucion : resolImpugnadas) {
                if (resolucion.getIdeSentidoResolucion() == null
                        || resolucion.getIdeSentidoResolucion().toString().equals("")) {
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Sentido es requerido",
                                    "Seleccione un sentido de resoluci\u00F3n"));
                    return;
                } else {
                    if(sentidosResolucionPerdedores.containsKey(resolucion.getIdeSentidoResolucion().getClave())){
                        if (resolucion.getAgravios() == null || resolucion.getAgravios().isEmpty()) {
                            FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Agravio es requerido", errorMsg
                                            .getString("vuj.resol.impugnada.desfavorable.tenerAlMenosUnAgravio")));
                            return;
                        }
                    }
                }

            }
        }

        ResolucionesComparator resolucionesComparator = new ResolucionesComparator();
        Collections.sort(resolucionAbogado.getResolucionesDTO(), resolucionesComparator);
        int count = 0;
        for (ResolucionImpugnadaDTO resol : resolucionAbogado.getResolucionesDTO()) {
            count++;
            if (count == resolucionAbogado.getResolucionesDTO().size()) {
                resol.setImportante(true);

            }
            else {
                resol.setImportante(false);
            }
        }

        if (documents == null || documents.isEmpty()) {

            nuevoDocumento = false;

            confirmarGuardado();

        }
        else {
            nuevoDocumento = true;

        }
        emitirResolucionBusiness.actualizaDatosBP(Integer.valueOf(getDatosBandejaTarea().getIdInstancia().toString()),
                getDatosBandejaTarea().getNumeroAsunto(), getUserProfile().getRfc());
    }

    public void confirmarGuardado() {
        getFlash().put("abogado", getResolucionAbogado());
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, datosBandejaTarea);
        emitirResolucionBusiness.guardarResolucionesImpugnadas(this.resolucionAbogado);

        emitirResolucionBusiness.terminarAtenderSolicitud(this.datosBandejaTarea, selectedAutorizador.getClave(),
                getUserProfile().getRfc(), resolucionAbogado.getTramiteDTO().getNumeroAsunto());
        banderaFalse();

        StringBuffer url = new StringBuffer(LoginConstante.BANDEA_JSF);
        getFlash().put(
                GeneralConstantes.MENSAJE_REDIRECT,
                "La resoluci\u00F3n del asunto " + resolucionAbogado.getTramiteDTO().getNumeroAsunto()
                        + " ha sido enviado para ser autorizada.");
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        configurableNavigationHandler.performNavigation(url.toString() + "?faces-redirect=true");

    }

    public void regresarPaginaEmitir() {
        nuevoDocumento = false;

    }

    public void banderaFalse() {
        nuevoDocumento = false;
    }

    public void onRowSelect(SelectEvent event) {
        selectedResolucionImpugnada = (ResolucionImpugnadaDTO) event.getObject();
        mostrarPanelAgravios = true;
        if (!selectedResolucionImpugnada.getAgravios().isEmpty()) {
            agravios.setTarget(selectedResolucionImpugnada.getAgravios());
            agravios.setSource(generarListaAgraviosSource(selectedResolucionImpugnada.getAgravios()));
        }
        else {
            agravios.setTarget(new ArrayList<AgraviosDTO>());
            agravios.setSource(agraviosSource);
        }
    }

    @SuppressWarnings("unchecked")
    public void actualizarResolucionImpugnada(TransferEvent event) {
        if (event.isAdd()) {
            selectedResolucionImpugnada.getAgravios().addAll((List<AgraviosDTO>) event.getItems());
        }
        else {
            selectedResolucionImpugnada.getAgravios().removeAll((List<AgraviosDTO>) event.getItems());
        }

    }

    private List<AgraviosDTO> generarListaAgraviosSource(List<AgraviosDTO> agraviosAgregados) {
        List<AgraviosDTO> agraviosSourceAuxiliar = copiarCatalogoAgravios();
        for (AgraviosDTO agravioA : agraviosAgregados) {
            for (int i = 0; i < agraviosSource.size(); i++) {
                if (agraviosSource.get(i).getClave().equals(agravioA.getClave())) {
                    agraviosSourceAuxiliar.remove(agravioA);
                }
            }
        }
        return agraviosSourceAuxiliar;
    }

    private List<AgraviosDTO> copiarCatalogoAgravios() {
        List<AgraviosDTO> agraviosAuxiliar = new ArrayList<AgraviosDTO>();
        agraviosAuxiliar.addAll(agraviosSource);
        return agraviosAuxiliar;
    }

    public void mensajesRedirect() {
        if (getMessagesRedirect() != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "", getMessagesRedirect()));
            setMessagesRedirect(null);
        }
    }

    /**
     * @return el(la) emitirResolucionBusiness
     */
    public EmitirResolucionBusiness getEmitirResolucionBusiness() {
        return emitirResolucionBusiness;
    }

    /**
     * @param emitirResolucionBusiness
     *            el(la) emitirResolucionBusiness a fijar
     */
    public void setEmitirResolucionBusiness(EmitirResolucionBusiness emitirResolucionBusiness) {
        this.emitirResolucionBusiness = emitirResolucionBusiness;
    }

    public CatalogoAutorizadorConverter getCatalogoAutorizadorConverter() {
        return catalogoAutorizadorConverter;
    }

    /**
     * @param emitirResolucionBusiness
     *            el(la) emitirResolucionBusiness a fijar
     */
    public void setCatalogoAutorizadorConverter(CatalogoAutorizadorConverter catalogoAutorizadorConverter) {
        this.catalogoAutorizadorConverter = catalogoAutorizadorConverter;
    }

    /**
     * @return el(la) resolucionesDataModel
     */
    public ResolucionDataModel getResolucionesDataModel() {
        return resolucionesDataModel;
    }

    /**
     * @param resolucionesDataModel
     *            el(la) resolucionesDataModel a fijar
     */
    public void setResolucionesDataModel(ResolucionDataModel resolucionesDataModel) {
        this.resolucionesDataModel = resolucionesDataModel;
    }

    /**
     * @return el(la) resolucionAbogado
     */
    public ResolucionAbogadoDTO getResolucionAbogado() {
        return resolucionAbogado;
    }

    /**
     * @param resolucionAbogado
     *            el(la) resolucionAbogado a fijar
     */
    public void setResolucionAbogado(ResolucionAbogadoDTO resolucionAbogado) {
        this.resolucionAbogado = resolucionAbogado;
    }

    /**
     * @return el(la) sentidosResolucion
     */
    public List<CatalogoDTO> getSentidosResolucion() {
        return sentidosResolucion;
    }

    /**
     * @param sentidosResolucion
     *            el(la) sentidosResolucion a fijar
     */
    public void setSentidosResolucion(List<CatalogoDTO> sentidosResolucion) {
        this.sentidosResolucion = sentidosResolucion;
    }

    /**
     * @return the sentidosResolucionPerdedores
     */
    public Map<String,CatalogoDTO> getSentidosResolucionPerdedores() {
        return sentidosResolucionPerdedores;
    }

    /**
     * @param sentidosResolucionPerdedores the sentidosResolucionPerdedores to set
     */
    public void setSentidosResolucionPerdedores(Map<String,CatalogoDTO> sentidosResolucionPerdedores) {
        this.sentidosResolucionPerdedores = sentidosResolucionPerdedores;
    }

    /**
     * @return el(la) agravios
     */
    public DualListModel<AgraviosDTO> getAgravios() {
        return agravios;
    }

    /**
     * @param agravios
     *            el(la) agravios a fijar
     */
    public void setAgravios(DualListModel<AgraviosDTO> agravios) {
        this.agravios = agravios;
    }

    /**
     * @return el(la) messages
     */
    public ResourceBundle getMessages() {
        return messages;
    }

    /**
     * @param messages
     *            el(la) messages a fijar
     */
    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    public ResolucionImpugnadaDTO getSelectedResolucionImpugnada() {
        return selectedResolucionImpugnada;
    }

    public void setSelectedResolucionImpugnada(ResolucionImpugnadaDTO selectedResolucionImpugnada) {
        this.selectedResolucionImpugnada = selectedResolucionImpugnada;
    }

    public List<AgraviosDTO> getAgraviosSource() {
        return agraviosSource;
    }

    public void setAgraviosSource(List<AgraviosDTO> agraviosSource) {
        this.agraviosSource = agraviosSource;
    }

    public String getNombreFuncionario() {
        return nombreFuncionario;
    }

    public void setNombreFuncionario(String nombreFuncionario) {
        this.nombreFuncionario = nombreFuncionario;
    }

    public boolean isMostrarPanelAgravios() {
        return mostrarPanelAgravios;
    }

    public void setMostrarPanelAgravios(boolean mostrarPanelAgravios) {
        this.mostrarPanelAgravios = mostrarPanelAgravios;
    }

    public DatosBandejaTareaDTO getDatosBandejaTarea() {
        return datosBandejaTarea;
    }

    public void setDatosBandejaTarea(DatosBandejaTareaDTO datosBandejaTarea) {
        this.datosBandejaTarea = datosBandejaTarea;
    }

    public List<CatalogoDTO> getAutorizadores() {
        return autorizadores;
    }

    public void setAutorizadores(List<CatalogoDTO> autorizadores) {
        this.autorizadores = autorizadores;
    }

    public CatalogoDTO getSelectedAutorizador() {
        return selectedAutorizador;
    }

    public void setSelectedAutorizador(CatalogoDTO selectedAutorizador) {
        this.selectedAutorizador = selectedAutorizador;
    }

    public String getMessagesRedirect() {
        return messagesRedirect;
    }

    public void setMessagesRedirect(String messagesRedirect) {
        this.messagesRedirect = messagesRedirect;
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

    /**
     * @return the fechaMaxima
     */
    public Date getFechaMaxima() {
        return fechaMaxima != null ? (Date) fechaMaxima.clone() : null;
    }

    /**
     * @param fechaMaxima
     *            the fechaMaxima to set
     */
    public void setFechaMaxima(Date fechaMaxima) {
        if (fechaMaxima != null) {
            this.fechaMaxima = (Date) fechaMaxima.clone();
        }
        else {
            this.fechaMaxima = null;
        }
    }

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

}
