package mx.gob.sat.siat.juridica.cal.web.controller.bean.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import mx.gob.sat.siat.juridica.base.constantes.MenusConstantes;
import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dto.ModalidadesDTO;
import mx.gob.sat.siat.juridica.base.dto.PersonaSolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.ca.util.constants.RegistroSolicitudConstants;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.business.RegistroSolicitudCALBussines;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.constantes.AdministracionConstantes;

@ManagedBean
@ViewScoped
public class SeleccionaModalidadController extends BaseControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{registroSolicitudCALBussines}")
    private transient RegistroSolicitudCALBussines registroSolicitudCALBussines;

    /** Lista de modalidades de consulta */
    private List<ModalidadesDTO> listaModalidadesConsulta = new ArrayList<ModalidadesDTO>();
    /** Lista de modalidades de autorizaci&oacute;n */

    private List<ModalidadesDTO> listaModalidadesAutorizacion = new ArrayList<ModalidadesDTO>();
    
    private List<ModalidadesDTO> listaModalidadesAviso = new ArrayList<ModalidadesDTO>();

    private List<ModalidadesDTO> listaModalidadesConsultaFiltrada = new ArrayList<ModalidadesDTO>();
    private List<ModalidadesDTO> listaModalidadesAutorizacionFiltrada = new ArrayList<ModalidadesDTO>();
    private List<ModalidadesDTO> listaModalidadesAvisoFiltrada = new ArrayList<ModalidadesDTO>();

    /**
     * Lista de modalidades que contiene consulta y autorizaci&oacute;n
     */
    private List<ModalidadesDTO> listaModalidadesGeneral = new ArrayList<ModalidadesDTO>();

    private String optionModalidad;
    private String modalidadSeleccionada;
    private String tooltipModalidadSeleccionada;
    private Boolean modalidadRequerida;
    private Boolean deshabilitarBoton;

    private String tipoPersona;

    private String modalidad;
    private String unidadAdmin;
    private String idModulo;
    private String asignacion;
    private SolicitudDTO solicitudDTO;

    private PersonaSolicitudDTO personaSolicitudDTO;

    @PostConstruct
    public void iniciar() {
        setSolicitudDTO((SolicitudDTO) getFlash().get("datosSolicitud"));
        setPersonaSolicitudDTO((PersonaSolicitudDTO) getFlash().get("solicitante"));
        String rfcSolicitante;
        if (getPersonaSolicitudDTO() != null) {
            if (getPersonaSolicitudDTO().getRazonSocial() != null
                    && !getPersonaSolicitudDTO().getRazonSocial().equals("")) {
                tipoPersona = AdministracionConstantes.TIPO_PERSONA_MORAL;

            }
            else {
                tipoPersona = AdministracionConstantes.TIPO_PERSONA_FISICA;
            }
        }
        else {

            rfcSolicitante = getUserProfile().getRfc();

            if (rfcSolicitante.length() != NumerosConstantes.DOCE) {
                tipoPersona = AdministracionConstantes.TIPO_PERSONA_FISICA;
            }
            else {
                tipoPersona = AdministracionConstantes.TIPO_PERSONA_MORAL;
            }

        }
        setListaModalidadesConsulta(getRegistroSolicitudCALBussines().obtenerModalidadesConsulta(tipoPersona));
        setListaModalidadesAutorizacion(getRegistroSolicitudCALBussines().obtenerModalidadesAutorizacion(tipoPersona));
        setListaModalidadesAviso(getRegistroSolicitudCALBussines().obtenerModalidadesAviso(tipoPersona));
        setOptionModalidad("4");
        escogerTipoModalidad(null);
        setDeshabilitarBoton(true);
        setModalidad((String) getFlash().get("modalidad"));
        setUnidadAdmin((String) getFlash().get("unidadAdmin"));
        setIdModulo((String) getFlash().get("idModulo"));
        setAsignacion((String) getFlash().get("asignacion"));

    }

    public void prepararRegistroSolicitud(ActionEvent event) {

        String tipoRegistro = (String) event.getComponent().getAttributes().get("tipoRegistro");
        getFlash().put("modalidad", getModalidad());
        getFlash().put("unidadAdmin", getUnidadAdmin());
        getFlash().put("idModulo", getIdModulo());
        getFlash().put("asignacion", getAsignacion());
        getFlash().put("datosSolicitud", getSolicitudDTO());
        getFlash().put("solicitante", getPersonaSolicitudDTO());
        if (tipoRegistro != null && tipoRegistro.equals("oficialia")) {
            redireccionarPagina("/resources/pages/cal/op/registroSolicitud/buscarPersonaOficialiaCA.xhtml");
        }
        else {
            redireccionarPagina("/resources/pages/cal/registroSolicitud/captura_solicitud_ca.xhtml");
        }
    }

    public void escogerTipoModalidad(AjaxBehaviorEvent event) {
        getListaModalidadesGeneral().clear();
        setTooltipModalidadSeleccionada("");
        setModalidadSeleccionada("");
        setDeshabilitarBoton(true);
        if (getOptionModalidad().equals("1")) {
            getListaModalidadesGeneral().addAll(getListaModalidadesAutorizacion());
        } else if (getOptionModalidad().equals("2")) {
            getListaModalidadesGeneral().addAll(getListaModalidadesConsulta());
        } else if (getOptionModalidad().equals("3")) {
            getListaModalidadesGeneral().addAll(getListaModalidadesAviso());
        } else {
            getListaModalidadesGeneral().addAll(getListaModalidadesAutorizacion());
            getListaModalidadesGeneral().addAll(getListaModalidadesConsulta());
            getListaModalidadesGeneral().addAll(getListaModalidadesAviso());
        }
    }

    public void muestraTooltip(AjaxBehaviorEvent event) {
        setTooltipModalidadSeleccionada("");
        setModalidadRequerida(false);
        for (ModalidadesDTO mod : getListaModalidadesGeneral()) {
            if (mod.getId().equals(getModalidadSeleccionada())) {
                setTooltipModalidadSeleccionada(mod.getTooltip());
            }
        }
    }

    public void deshabilitaBoton(ValueChangeEvent event) {
        if (null == event.getNewValue() || event.getNewValue().equals("")) {
            setDeshabilitarBoton(true);
        }
        else {
            setDeshabilitarBoton(false);
        }
    }

    public void accionSiguiente(ActionEvent event) {
        ModalidadesDTO modalidadDTO = new ModalidadesDTO();
        String tipoRegistro = (String) event.getComponent().getAttributes().get("tipoRegistro");
        for (ModalidadesDTO modTemp : getListaModalidadesGeneral()) {
            if (modTemp.getId().equals(modalidadSeleccionada)) {
                modalidadDTO = modTemp;
            }
        }
        getFlash().put("modalidad", modalidadDTO.getId());
        getFlash().put("unidadAdmin", modalidadDTO.getUnidadAdminResponsable());
        getFlash().put("idModulo", modalidadDTO.getIdModulo());
        getFlash().put("asignacion", modalidadDTO.getAsignacion());
        getFlash().put("datosSolicitud", getSolicitudDTO());
        getFlash().put("solicitante", getPersonaSolicitudDTO());
        if (tipoRegistro != null && tipoRegistro.equals("oficialia")) {
            redireccionarPagina("/resources/pages/cal/op/registroSolicitud/captura_solicitud_ca.xhtml");
        }
        else {
            redireccionarPagina("/resources/pages/cal/registroSolicitud/captura_solicitud_ca.xhtml");
        }
    }

    private void redireccionarPagina(String url) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().setResponseCharacterEncoding("UTF-8");
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) facesContext.getApplication().getNavigationHandler();
        configurableNavigationHandler.performNavigation(url + RegistroSolicitudConstants.FACES_REDIRECT);
    }

    /**
     * @return the registroSolicitudCALBussines
     */
    public RegistroSolicitudCALBussines getRegistroSolicitudCALBussines() {
        return registroSolicitudCALBussines;
    }

    /**
     * @param registroSolicitudCALBussines
     *            the registroSolicitudCALBussines to set
     */
    public void setRegistroSolicitudCALBussines(RegistroSolicitudCALBussines registroSolicitudCALBussines) {
        this.registroSolicitudCALBussines = registroSolicitudCALBussines;
    }

    /**
     * @return the listaModalidadesConsulta
     */
    public List<ModalidadesDTO> getListaModalidadesConsulta() {
        return listaModalidadesConsulta;
    }

    /**
     * @param listaModalidadesConsulta
     *            the listaModalidadesConsulta to set
     */
    public void setListaModalidadesConsulta(List<ModalidadesDTO> listaModalidadesConsulta) {
        this.listaModalidadesConsulta = listaModalidadesConsulta;
    }

    /**
     * @return the listaModalidadesAutorizacion
     */
    public List<ModalidadesDTO> getListaModalidadesAutorizacion() {
        return listaModalidadesAutorizacion;
    }

    /**
     * @param listaModalidadesAutorizacion
     *            the listaModalidadesAutorizacion to set
     */
    public void setListaModalidadesAutorizacion(List<ModalidadesDTO> listaModalidadesAutorizacion) {
        this.listaModalidadesAutorizacion = listaModalidadesAutorizacion;
    }

    public List<ModalidadesDTO> getListaModalidadesGeneral() {
        return listaModalidadesGeneral;
    }

    public void setListaModalidadesGeneral(List<ModalidadesDTO> listaModalidadesGeneral) {
        this.listaModalidadesGeneral = listaModalidadesGeneral;
    }

    public String getOptionModalidad() {
        return optionModalidad;
    }

    public void setOptionModalidad(String optionModalidad) {
        this.optionModalidad = optionModalidad;
    }

    public SeleccionaModalidadController() {}

    public String getModalidadSeleccionada() {
        return modalidadSeleccionada;
    }

    public void setModalidadSeleccionada(String modalidadSeleccionada) {
        this.modalidadSeleccionada = modalidadSeleccionada;
    }

    public String getTooltipModalidadSeleccionada() {
        return tooltipModalidadSeleccionada;
    }

    public void setTooltipModalidadSeleccionada(String tooltipModalidadSeleccionada) {
        this.tooltipModalidadSeleccionada = tooltipModalidadSeleccionada;
    }

    public Boolean getModalidadRequerida() {
        return modalidadRequerida;
    }

    public void setModalidadRequerida(Boolean modalidadRequerida) {
        this.modalidadRequerida = modalidadRequerida;
    }

    public void validaAccesoProcesoMenu() {
        // Validacion de acceso a la funcionalidad
        this.validaAccesoContribuytente();
    }

    public void validaAccesoOficialia() {
        // Validacion de acceso a la funcionalidad para el rol de
        // oficialia
        this.validaAccesoProcesoMenu(ProcesosConstantes.PROCESO_EMPLEADO_REGISTRO_OFICIALIA,
                MenusConstantes.MENU_EMPLEADOS);

    }

    public Boolean getDeshabilitarBoton() {
        return deshabilitarBoton;
    }

    public void setDeshabilitarBoton(Boolean deshabilitarBoton) {
        this.deshabilitarBoton = deshabilitarBoton;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    /**
     * @return the modalidad
     */
    public String getModalidad() {
        return modalidad;
    }

    /**
     * @param modalidad
     *            the modalidad to set
     */
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * @return the unidadAdmin
     */
    public String getUnidadAdmin() {
        return unidadAdmin;
    }

    /**
     * @param unidadAdmin
     *            the unidadAdmin to set
     */
    public void setUnidadAdmin(String unidadAdmin) {
        this.unidadAdmin = unidadAdmin;
    }

    /**
     * @return the idModulo
     */
    public String getIdModulo() {
        return idModulo;
    }

    /**
     * @param idModulo
     *            the idModulo to set
     */
    public void setIdModulo(String idModulo) {
        this.idModulo = idModulo;
    }

    /**
     * @return the asignacion
     */
    public String getAsignacion() {
        return asignacion;
    }

    /**
     * @param asignacion
     *            the asignacion to set
     */
    public void setAsignacion(String asignacion) {
        this.asignacion = asignacion;
    }

    /**
     * @return the solicitudDTO
     */
    public SolicitudDTO getSolicitudDTO() {
        return solicitudDTO;
    }

    /**
     * @param solicitudDTO
     *            the solicitudDTO to set
     */
    public void setSolicitudDTO(SolicitudDTO solicitudDTO) {
        this.solicitudDTO = solicitudDTO;
    }

    /**
     * @return the personaSolicitudDTO
     */
    public PersonaSolicitudDTO getPersonaSolicitudDTO() {
        return personaSolicitudDTO;
    }

    /**
     * @param personaSolicitudDTO
     *            the personaSolicitudDTO to set
     */
    public void setPersonaSolicitudDTO(PersonaSolicitudDTO personaSolicitudDTO) {
        this.personaSolicitudDTO = personaSolicitudDTO;
    }
    
    /**
     * @return the listaModalidadesAviso
     */
    public List<ModalidadesDTO> getListaModalidadesAviso() {
        return listaModalidadesAviso;
    }

    /**
     * @param listaModalidadesAviso
     *            the listaModalidadesAviso to set
     */
    public void setListaModalidadesAviso(List<ModalidadesDTO> listaModalidadesAviso) {
        this.listaModalidadesAviso = listaModalidadesAviso;
    }

    /**
     * @return the listaModalidadesConsultaFiltrada
     */
    public List<ModalidadesDTO> getListaModalidadesConsultaFiltrada() {
        return listaModalidadesConsultaFiltrada;
    }

    /**
     * @param listaModalidadesConsultaFiltrada
     *            the listaModalidadesConsultaFiltrada to set
     */
    public void setListaModalidadesConsultaFiltrada(List<ModalidadesDTO> listaModalidadesConsultaFiltrada) {
        this.listaModalidadesConsultaFiltrada = listaModalidadesConsultaFiltrada;
    }

    public List<ModalidadesDTO> getListaModalidadesAutorizacionFiltrada() {
        return listaModalidadesAutorizacionFiltrada;
    }

    public void setListaModalidadesAutorizacionFiltrada(List<ModalidadesDTO> listaModalidadesAutorizacionFiltrada) {
        this.listaModalidadesAutorizacionFiltrada = listaModalidadesAutorizacionFiltrada;
    }

    public List<ModalidadesDTO> getListaModalidadesAvisoFiltrada() {
        return listaModalidadesAvisoFiltrada;
    }

    public void setListaModalidadesAvisoFiltrada(List<ModalidadesDTO> listaModalidadesAvisoFiltrada) {
        this.listaModalidadesAvisoFiltrada = listaModalidadesAvisoFiltrada;
    }
}
