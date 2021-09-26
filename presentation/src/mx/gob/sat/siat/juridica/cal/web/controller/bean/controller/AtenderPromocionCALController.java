/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.cal.util.constants.CadenasConstants;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.business.AtencionPromocionCALBussines;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

/**
 * Controller to handle atender promocion flow from juridica.
 * 
 * @author antonio.flores Softtek
 * 
 */
@ViewScoped
@ManagedBean(name = "atenderPromocionController")
public class AtenderPromocionCALController extends BaseControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = 7829486868843606210L;
    /**
     * Bussines.
     */
    @ManagedProperty(value = "#{atencionPromocionBussines}")
    private transient AtencionPromocionCALBussines atencionPromocionBussines;

    /**
     * Object used to get information from bandeja de tarea.
     */
    private FiltroBandejaTareaDTO filtroBandejaTareaDTO = null;

    private DatosBandejaTareaDTO datosBandejaTareaDTO;

    private TramiteDTO tramiteDto;

    /**
     * String to set values from user selection radio button.
     */
    private String optionPromocion = null;

    /**
     * Atributo que contiene la observacion de un turnado
     */
    private String observacionTurnado;

    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    /**
     * Atributo boolean para ocultar/ver el panel de la observacion
     */
    private boolean panelTurnadoVisible = true;

    @PostConstruct
    public void initAtenderPromocion() {
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));

        setTramiteDto(atencionPromocionBussines.obtenerTramite(getDatosBandejaTareaDTO().getNumeroAsunto()));
        setObservacionTurnado(atencionPromocionBussines.obtenerObservacionTurnado(getDatosBandejaTareaDTO()
                .getNumeroAsunto()));
        if (null == observacionTurnado || observacionTurnado.equals("")) {
            panelTurnadoVisible = false;
        }
        optionPromocion = CadenasConstants.GENERAR_RESOLUCION;
    }

    /**
     * Method to redirect to the following step into main flow, it
     * depends in user selection.
     */
    public void atiendePromocion() {
        String navigationFormFollow = null;
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        if (optionPromocion.equalsIgnoreCase(CadenasConstants.REQUERIMIENTO_RETROALIMENTACION)) {
            // va al FA01 21_639_ECU_008GenerarRequerimiento
            navigationFormFollow = CadenasConstants.REQUERIMIENTO_RETROALIMENTACION_ACTION_NAVEGATION;
        }
        else if (optionPromocion.equalsIgnoreCase(CadenasConstants.GENERAR_REMISION)) {
            // va al FA02 21_639_ECU_015RemitirAsunto
            navigationFormFollow = CadenasConstants.GENERAR_REMISION_ACTION_NAVEGATION;
            getFlash().put(VistaConstantes.TAREA_ORIGEN, getAtencionPromocionBussines().getIdeTareaOrigen());
        }
        else if (optionPromocion.equalsIgnoreCase(CadenasConstants.GENERAR_OBSERVACION_OFICIALIA)) {
            navigationFormFollow = CadenasConstants.GENERAR_OBSERVACION_OFICIALIA_ACTION_NAVEGATION;
        }
        else {
            navigationFormFollow = CadenasConstants.GENERAR_RESOLUCION_ACTION_NAVEGATION_CLASIFICACION;
        }
        getDatosBandejaTareaDTO().setEstadoProcesal(EstadoTramite.EN_ESTUDIO.getDescripcion());
        atencionPromocionBussines.actualizaDatosBP(getDatosBandejaTareaDTO());
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        configurableNavigationHandler.performNavigation(navigationFormFollow);
    }

    /**
     * Method to register the data needed to continue the main flow.
     * 
     * @param filtroBandejaTareaDTO
     */
    public void registerActionFollowPromotion(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {

        atencionPromocionBussines.updateAtencion(filtroBandejaTareaDTO, this.getUserProfile());
    }

    /**
     * Method to back from atender promocion to bandeja de entrada.
     */
    public void backBandeja() {
        // TODO definir
        String navigationFormFollow = LoginConstante.BANDEA_JSF;

        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());

        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        configurableNavigationHandler.performNavigation(navigationFormFollow);

    }

    /**
     * @return the messages
     */
    public ResourceBundle getMessages() {
        return messages;
    }

    /**
     * @param messages
     *            the messages to set
     */
    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
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

    /**
     * @return the filtroBandejaTareaDTO
     */
    public FiltroBandejaTareaDTO getFiltroBandejaTareaDTO() {
        return filtroBandejaTareaDTO;
    }

    /**
     * @param filtroBandejaTareaDTO
     *            the filtroBandejaTareaDTO to set
     */
    public void setFiltroBandejaTareaDTO(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {
        this.filtroBandejaTareaDTO = filtroBandejaTareaDTO;
    }

    /**
     * @return the optionPromocion
     */
    public String getOptionPromocion() {
        return optionPromocion;
    }

    /**
     * @param optionPromocion
     *            the optionPromocion to set
     */
    public void setOptionPromocion(String optionPromocion) {
        this.optionPromocion = optionPromocion;
    }

    /**
     * @return the atencionPromocionBussines
     */
    public AtencionPromocionCALBussines getAtencionPromocionBussines() {
        return atencionPromocionBussines;
    }

    /**
     * @param atencionPromocionBussines
     *            the atencionPromocionBussines to set
     */
    public void setAtencionPromocionBussines(AtencionPromocionCALBussines atencionPromocionBussines) {
        this.atencionPromocionBussines = atencionPromocionBussines;
    }

    public DatosBandejaTareaDTO getDatosBandejaTareaDTO() {
        return datosBandejaTareaDTO;
    }

    public void setDatosBandejaTareaDTO(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        this.datosBandejaTareaDTO = datosBandejaTareaDTO;
    }

    public TramiteDTO getTramiteDto() {
        return tramiteDto;
    }

    public void setTramiteDto(TramiteDTO tramiteDto) {
        this.tramiteDto = tramiteDto;
    }

    public String getObservacionTurnado() {
        return observacionTurnado;
    }

    public void setObservacionTurnado(String observacionTurnado) {
        this.observacionTurnado = observacionTurnado;
    }

    public boolean isPanelTurnadoVisible() {
        return panelTurnadoVisible;
    }

    public void setPanelTurnadoVisible(boolean panelTurnadoVisible) {
        this.panelTurnadoVisible = panelTurnadoVisible;
    }

    public void validaAcceso() {
        // Validacion de acceso a la funcionalidad
        this.validaAccesoRolEmpleado(RolesConstantes.ROL_EMPLEADO_ABOGADO);
    }

}
