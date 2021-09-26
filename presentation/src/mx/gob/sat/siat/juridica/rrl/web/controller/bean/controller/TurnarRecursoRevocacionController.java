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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dto.AbogadoDTO;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.MotivoRechazoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.FirmarTareaBussines;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.FirmarTareaController;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.TurnarDTO;
import mx.gob.sat.siat.juridica.oficialia.web.controller.bean.constantes.OficialiaConstantesController;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.AbogadoConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.GeneralConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.TurnarRecursoRevocacionBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.AbogadoDataModel;

/**
 * Bean que implementan la l&oacute;gica de negocio para turnar la
 * solicitud
 * 
 * @author Softtek
 * 
 */

@ViewScoped
@ManagedBean(name = "turnarRecursoRevocacionController")
public class TurnarRecursoRevocacionController extends BaseControllerBean {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = 1L;
    /** DTO que representa los datos obtenidos en la bandeja */
    private DatosBandejaTareaDTO datosBandejaTareaDTO;
    /** Data model de abogados */
    private AbogadoDataModel abogadoDataModel;
    /** DTO que representa los datos del abogado */
    private AbogadoDTO seleccionAbogadoDTO;
    /** DTO que representa los datos del turnado **/
    private TurnarDTO turnaDTO = new TurnarDTO();
    /** Lista de abogados */
    private List<AbogadoDTO> listaAbogados = new ArrayList<AbogadoDTO>();
    /** Bussines para Turnar Recurso Revocacion */
    @ManagedProperty(value = "#{turnarRecursoRevocacionBussines}")
    private TurnarRecursoRevocacionBussines turnarRecursoRevocacionBussines;
    /** Bean para firmar la tarea */
    @ManagedProperty(value = "#{firmarTareaController}")
    private FirmarTareaController firmarTareaController;
    /** Bussines para firmar tarea */
    @ManagedProperty(value = "#{firmarTareaBussines}")
    private FirmarTareaBussines firmarTareaBussines;

    /**
     * Propiedad para manejar los mensajes en pantalla.
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    /**
     * Propiedad para manejar los mensajes de error en pantalla.
     */
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    private TramiteDTO tramite;

    private Boolean reasignar = false;

    private MotivoRechazoDTO motivo = new MotivoRechazoDTO();

    private static final String REASIGNAR = "reasignar";

    /**
     * Atributo que activa el dialogo de rechazo de documento tipo
     * boolean
     */
    private boolean activaDialogo;

    public MotivoRechazoDTO getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoRechazoDTO motivo) {
        this.motivo = motivo;
    }

    public List<CatalogoDTO> getMotivoCombo() {
        return motivoCombo;
    }

    public void setMotivoCombo(List<CatalogoDTO> motivoCombo) {
        this.motivoCombo = motivoCombo;
    }

    public String getCveMotivo() {
        return cveMotivo;
    }

    public void setCveMotivo(String cveMotivo) {
        this.cveMotivo = cveMotivo;
    }

    public void setTramite(TramiteDTO tramite) {
        this.tramite = tramite;
    }

    private List<CatalogoDTO> motivoCombo;

    private String cveMotivo;

    /**
     * Constructor
     */
    public TurnarRecursoRevocacionController() {

    }

    /**
     * 1. Cargar datos bandeja 2. Identifica que no existen Abogados
     * que cumplan con los requisitos para que se les pueda asignar la
     * tarea. 3. Informa al Administrador que no existen abogados que
     * cumplan los criterios para que se les asigne la tarea 4. Fin
     * del caso de uso (regresa a la bandeja de or&iacute;gen)
     */
    @PostConstruct
    public void cargarDatosBandeja() {
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));
        setReasignar((Boolean) getFlash().get(REASIGNAR));
        setMotivoCombo(getTurnarRecursoRevocacionBussines().obtenerMotivosRechazo());
    }

    /**
     * M&eacute;todo para cambiar a la pantalla de firmar tarea
     */
    public String firmar() {
        if (getSeleccionAbogadoDTO() == null) {
            getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
            getFlash().put(REASIGNAR, getReasignar());
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                            .getString("vuj.turnar.seleccionar.abogadoParaTurnar")));
            return "";
        } else {
            try {
                Integer.parseInt(getSeleccionAbogadoDTO().getNumPendientes());
            } catch (NumberFormatException nfe) {
                getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
                getFlash().put(REASIGNAR, getReasignar());
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                .getString("vuj.turnar.noExistenAbogadoEnBP")));
                return "";
            }
        }
        firmarTareaController.setAbogadoDTO(getSeleccionAbogadoDTO());
        firmarTareaController.setDatosBandejaTareaDTO(getDatosBandejaTareaDTO());
        firmarTareaController.setReasignar(getReasignar());
        return UrlFirma.PAGINA_FIRMA_TURNAR.toString();
    }

    public void siguienteSinFirmar() {
        if (getSeleccionAbogadoDTO() == null) {
            getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
            getFlash().put(REASIGNAR, getReasignar());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    errorMsg.getString("vuj.turnar.seleccionar.abogadoParaTurnar")));
        } else {
            
            try {
                Integer.parseInt(getSeleccionAbogadoDTO().getNumPendientes());
            } catch (NumberFormatException nfe) {
                getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
                getFlash().put(REASIGNAR, getReasignar());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                        errorMsg.getString("vuj.turnar.noExistenAbogadoEnBP")));
            }

            getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());

            StringBuffer url = new StringBuffer(LoginConstante.BANDEA_JSF);
            StringBuffer url2 = new StringBuffer(LoginConstante.BANDEJA_RETURNAR_JSF);
            
            if (!reasignar) {
                turnaDTO.setIdTramite(datosBandejaTareaDTO.getNumeroAsunto());
                turnaDTO.setIdUsuarioAnterior(null);
                turnaDTO.setIdUsuarioAsigna(getUserProfile().getRfc());
                turnaDTO.setIdUsuarioNuevo(getSeleccionAbogadoDTO().getRfc());
                turnaDTO.setFechaAsignacion(new Date());
                turnaDTO.setNombreTarea("Turnar asunto");
                getFlash().put(VistaConstantes.DATOS_TURNADO_DTO, getTurnaDTO());
                getFirmarTareaBussines().firmarTurnar(getDatosBandejaTareaDTO().getNumeroAsunto(),
                        getDatosBandejaTareaDTO().getIdTareaUsuario().toString(), getSeleccionAbogadoDTO().getRfc(),
                        getUserProfile().getRfc(), getTurnaDTO());
                getFlash().put(GeneralConstantes.MENSAJE_REDIRECT, "El asunto con n\u00famero "
                        + getDatosBandejaTareaDTO().getNumeroAsunto() + " ha sido turnado exitosamente.");
                ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext
                        .getCurrentInstance().getApplication().getNavigationHandler();
                configurableNavigationHandler.performNavigation(url.toString() + "?faces-redirect=true");

            } else {
                getFirmarTareaBussines().firmarReasignar(getDatosBandejaTareaDTO().getIdTareaUsuario().toString(),
                        getUserProfile().getRfc(), getSeleccionAbogadoDTO().getRfc(),
                        getDatosBandejaTareaDTO().getNumeroAsunto(), getDatosBandejaTareaDTO().getRfcUsuarioAsignado(),
                        getDatosBandejaTareaDTO().getIdInstancia());
                getFlash().put(GeneralConstantes.MENSAJE_REDIRECT, "El asunto con n\u00famero "
                        + getDatosBandejaTareaDTO().getNumeroAsunto() + " ha sido reasignado exitosamente.");
                ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext
                        .getCurrentInstance().getApplication().getNavigationHandler();
                configurableNavigationHandler.performNavigation(url2.toString() + "?faces-redirect=true");
            }
        }
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
     * @param datosBandejaTareaDTO
     *            a fijar
     */
    public void setDatosBandejaTareaDTO(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        this.datosBandejaTareaDTO = datosBandejaTareaDTO;
        this.seleccionAbogadoDTO = null;
        setListaAbogados(getTurnarRecursoRevocacionBussines().listaAbogado(getDatosBandejaTareaDTO().getNumeroAsunto()));
        setAbogadoDataModel(new AbogadoDataModel(getListaAbogados()));
    }

    /**
     * Metodo para rechazar asunto de oficialia
     * 
     * @return
     * @throws IOException
     */
    public String rechazarAsunto() throws IOException {
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        return OficialiaConstantesController.RECHAZAR_ASUNTO_OFICIALIA;
    }

    /**
     * Metodo para firmar elrechazo de asunto de oficialia
     */
    public void firmaRechazo() {
        if (cveMotivo != null) {
            getMotivo().setCveRechazo(cveMotivo);
            getMotivo().setIdTramite(getDatosBandejaTareaDTO().getNumeroAsunto());
            getMotivo().setRfc(getUserProfile().getRfc());
            getMotivo().setIdTarea(getDatosBandejaTareaDTO().getIdTareaUsuario());

            getFlash().put(VistaConstantes.MOTIVO_RECHAZO, getMotivo());
            getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());

            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().setResponseCharacterEncoding("UTF-8");
            ConfigurableNavigationHandler configurableNavigationHandler =
                    (ConfigurableNavigationHandler) facesContext.getApplication().getNavigationHandler();
            configurableNavigationHandler.performNavigation(UrlFirma.PAGINA_FIRMA_RECHAZO_ASUNTO_OFICIALIA.toString());
        }
        else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, errorMsg
                            .getString("op.validaciones.motivoRechazo")));
        }
    }

    /**
     * Metodo para remitir los recursos de revocacion. Funcionalidad
     * compartida por T1 y T2
     * 
     * @return
     * @throws IOException
     */
    public String remitir() throws IOException {
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        getFlash().put(VistaConstantes.TAREA_ORIGEN, getTurnarRecursoRevocacionBussines().getIdeTareaOrigen());
        return AbogadoConstantes.REMITIR_JSF;
    }

    /**
     * Metodo para activar cuadro de dialogo para el rechazo de
     * documento
     */
    public void activaDialogo() {
        activaDialogo = true;
    }

    /**
     * Metodo que se ejecuta al cancelar el rechazo de documento
     */
    public void noConfirma() {
        activaDialogo = false;
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());

    }

    /**
     * 
     * @return seleccionAbogadoDTO
     */
    public AbogadoDTO getSeleccionAbogadoDTO() {
        return seleccionAbogadoDTO;
    }

    /**
     * 
     * @param seleccionAbogadoDTO
     *            a fijar
     */
    public void setSeleccionAbogadoDTO(AbogadoDTO seleccionAbogadoDTO) {
        this.seleccionAbogadoDTO = seleccionAbogadoDTO;
    }

    /**
     * 
     * @return listaAbogados
     */
    public List<AbogadoDTO> getListaAbogados() {
        return listaAbogados;
    }

    /**
     * 
     * @param listaAbogados
     *            a fijar
     */
    public void setListaAbogados(List<AbogadoDTO> listaAbogados) {
        this.listaAbogados = listaAbogados;
    }

    /**
     * 
     * @return abogadoDataModel
     */
    public AbogadoDataModel getAbogadoDataModel() {
        return abogadoDataModel;
    }

    /**
     * 
     * @param abogadoDataModel
     *            a fijar
     */
    public void setAbogadoDataModel(AbogadoDataModel abogadoDataModel) {
        this.abogadoDataModel = abogadoDataModel;
    }

    /**
     * 
     * @return turnarRecursoRevocacionBussines
     */
    public TurnarRecursoRevocacionBussines getTurnarRecursoRevocacionBussines() {
        return turnarRecursoRevocacionBussines;
    }

    /**
     * 
     * @param turnarRecursoRevocacionBussines
     *            a fijar
     */
    public void setTurnarRecursoRevocacionBussines(TurnarRecursoRevocacionBussines turnarRecursoRevocacionBussines) {
        this.turnarRecursoRevocacionBussines = turnarRecursoRevocacionBussines;
    }

    /**
     * 
     * @return firmarTareaController
     */
    public FirmarTareaController getFirmarTareaController() {
        return firmarTareaController;
    }

    /**
     * 
     * @param firmarTareaController
     *            a fijar
     */
    public void setFirmarTareaController(FirmarTareaController firmarTareaController) {
        this.firmarTareaController = firmarTareaController;
    }

    public Boolean getReasignar() {
        return reasignar;
    }

    public void setReasignar(Boolean reasignar) {
        this.reasignar = reasignar;
    }

    public TramiteDTO getTramite() {
        tramite = turnarRecursoRevocacionBussines.obtenerEstadoProcesal(datosBandejaTareaDTO.getNumeroAsunto());
        return tramite;
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

    public boolean isHayAbogadosTurnar() {
        return (getListaAbogados() != null && getListaAbogados().size() > 0);
    }

    public boolean isActivaDialogo() {
        return activaDialogo;
    }

    public void setActivaDialogo(boolean activaDialogo) {
        this.activaDialogo = activaDialogo;
    }

    /**
     * 
     * @return firmarTareaBussines
     */
    public FirmarTareaBussines getFirmarTareaBussines() {
        return firmarTareaBussines;
    }

    /**
     * 
     * @param firmarTareaBussines
     *            a fijar
     */
    public void setFirmarTareaBussines(FirmarTareaBussines firmarTareaBussines) {
        this.firmarTareaBussines = firmarTareaBussines;
    }

    public TurnarDTO getTurnaDTO() {
        return turnaDTO;
    }

    public void setTurnaDTO(TurnarDTO turnaDTO) {
        this.turnaDTO = turnaDTO;
    }

    public void validaAccesoAdministrador() {
        // Validacion de acceso a la funcionalidad
        this.validaAccesoRolEmpleado(RolesConstantes.ROL_EMPLEADO_ADMINISTRADOR);
    }

}
