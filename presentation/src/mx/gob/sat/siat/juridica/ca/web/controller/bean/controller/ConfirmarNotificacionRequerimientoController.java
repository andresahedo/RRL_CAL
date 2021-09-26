package mx.gob.sat.siat.juridica.ca.web.controller.bean.controller;

import mx.gob.sat.sgi.SgiCripto.SgiARAException;
import mx.gob.sat.siat.juridica.ara.constantes.AraConstantes;
import mx.gob.sat.siat.juridica.ara.util.AraValidadorHelper;
import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dao.domain.model.MultiFormatReportVU;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.FirmarBusiness;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.DescargarDocumentoController;
import mx.gob.sat.siat.juridica.ca.web.controller.bean.bussiness.ConfirmarNotificacionRequerimientoBusiness;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility.GenerarDocumentosHelper;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "confirmarNotificacionRequerimientoController")
@ViewScoped
public class ConfirmarNotificacionRequerimientoController extends BaseControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = 5973435127594285395L;

    /** DTO que representa lo firma de la solicitud */
    private FirmaDTO firma;

    private String cadenaOriginal;
    private String firmaDigital;
    private String userID;

    @ManagedProperty(value = "#{generarDocumentosHelper}")
    private GenerarDocumentosHelper generarDocumentosHelper;

    /** DTO que representa los datos obtenidos en la bandeja */
    @ManagedProperty(value = "#{datosBandejaTareaDTO}")
    private DatosBandejaTareaDTO datosBandejaTareaDTO;

    // TODO arreglar, no deberia tener dos bussines
    @ManagedProperty(value = "#{firmarBusiness}")
    private FirmarBusiness firmarBusiness;

    /**
     * Propiedad que representa una notificacion.
     */
    @ManagedProperty(value = "#{notificacion}")
    private transient MultiFormatReportVU notificacion;

    /** DTO que representa los datos de una solicitud */
    private SolicitudDTO solicitud;

    @ManagedProperty("#{confirmarNotificacionRequerimientoBusiness}")
    private ConfirmarNotificacionRequerimientoBusiness confirmarNotificacionRequerimientoBusiness;

    @ManagedProperty(value = "#{descargarDocumentoController}")
    private DescargarDocumentoController descargarDocumentoController;
    
    @ManagedProperty(value = "#{araValidadorHelper}")
    private AraValidadorHelper araValidadorHelper;

    /**
     * Metodo que carga los datos a la bandeja.
     */
    @PostConstruct
    public void cargarDatosBandeja() {
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get("datosBandejaDTO"));
        SolicitudDTO solDTO = new SolicitudDTO();
        solDTO.setIdSolicitud(getDatosBandejaTareaDTO().getIdSolicitud());
        setSolicitud(solDTO);
        setFirma(new FirmaDTO(new Date()));
    }

    /**
     * M&eacute;todo para firmar la solicitud
     */
    public String firmar() {
        List<Long> idsDoc = new ArrayList<Long>();
        String sNumSerie;
        String urlforward = null;
        // Valida que usuario que firma es el usuario que esta logeado

        setUserID(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userID")
                .toString());

        try {
            if (getUserID().equalsIgnoreCase(getUserProfile().getRfc())) {
                sNumSerie = getAraValidadorHelper().getNumSerie(FacesContext
                        .getCurrentInstance().getExternalContext()
                        .getRequestParameterMap().get("numeroSerie"));

                if (getAraValidadorHelper().validarRfcNumSerie(sNumSerie, getUserProfile().getRfc()) 
                        && getAraValidadorHelper().getEdoCertificado(sNumSerie)) {
                    setFirmaDigital(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                            .get("firmaDigital").toString());

                    firma.setFechaFirma(new Date());
                    firma.setSello(getFirmaDigital());
                    firma.setRfcUsuario(getUserProfile().getRfc());
                    firma.setCveRol(getUserProfile().getRol());
                    firma.setCertificado(sNumSerie);

                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy 'a las' HH:mm:ss 'hrs'");
                    Calendar cal = Calendar.getInstance();

                    getDescargarDocumentoController().inicializar(datosBandejaTareaDTO);
                    // Firma la notificacion del requerimiento y genera la
                    // NotificacionRequerimiento
                    getConfirmarNotificacionRequerimientoBusiness().firmarConfirmarNotificacion(
                            getDatosBandejaTareaDTO().getNumeroAsunto(), getDatosBandejaTareaDTO().getIdTareaUsuario(),
                            getDatosBandejaTareaDTO().getIdRequerimiento(), getUserProfile().getRfc());
                    // Hasta este punto se genera la cadena, ya que al firmar
                    // la notificacion se genera el registro asociado en
                    // "Notificacion" con los datos
                    // requeridos para la cadena
                    firma.setCadenaOriginal(getConfirmarNotificacionRequerimientoBusiness()
                            .generaCadenaOriginalNotificacionRequerimiento(getSolicitud().getIdSolicitud(),
                                    getDatosBandejaTareaDTO().getIdRequerimiento(), getFirma().getFechaFirma(),
                                    getUserProfile().getRfc()));
                    getConfirmarNotificacionRequerimientoBusiness().guardarFirmaConfirmarNotificacion(firma,
                            getDatosBandejaTareaDTO().getIdRequerimiento());

                    FacesMessage msg
                            = new FacesMessage("", "El asunto con n\u00FAmero " + getDatosBandejaTareaDTO().getNumeroAsunto()
                                    + " ha sido notificado exitosamente con fecha " + dateFormat.format(cal.getTime()));
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                    getDescargarDocumentoController().getDatosBandejaTareaDTO().setNumeroAsunto(
                            getDatosBandejaTareaDTO().getNumeroAsunto());
                    getDescargarDocumentoController().obtenerDocumentosByIdDoc(idsDoc);

                    urlforward = LoginConstante.BANDEA_JSF;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, null, 
                                MessageFormat.format(AraConstantes.MENSAJE_LLAVE_SERIE_INCORRECTO, getUserProfile().getRfc())));
            }
        } catch (SgiARAException ex) {
            getLogger().error("Ocurrio un error al firmar ConfirmarNotificacionRequerimientoController.firmar {0}", ex);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } catch (Exception ex) {
            getLogger().error("Ocurrio un error al firmar ConfirmarNotificacionRequerimientoController.firmar {0}", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurri\u00F3 un error al firmar.", ""));
        }
        return urlforward;
    }

    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }

    public FirmaDTO getFirma() {
        return firma;
    }

    public void setFirma(FirmaDTO firma) {
        this.firma = firma;
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getFirmaDigital() {
        return firmaDigital;
    }

    public FirmarBusiness getFirmarBusiness() {
        return firmarBusiness;
    }

    public void setFirmarBusiness(FirmarBusiness firmarBusiness) {
        this.firmarBusiness = firmarBusiness;
    }

    public SolicitudDTO getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudDTO solicitud) {
        this.solicitud = solicitud;
    }

    public DatosBandejaTareaDTO getDatosBandejaTareaDTO() {
        return datosBandejaTareaDTO;
    }

    public void setDatosBandejaTareaDTO(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        this.datosBandejaTareaDTO = datosBandejaTareaDTO;
    }

    public ConfirmarNotificacionRequerimientoBusiness getConfirmarNotificacionRequerimientoBusiness() {
        return confirmarNotificacionRequerimientoBusiness;
    }

    public void setConfirmarNotificacionRequerimientoBusiness(
            ConfirmarNotificacionRequerimientoBusiness confirmarNotificacionRequerimientoBusiness) {
        this.confirmarNotificacionRequerimientoBusiness = confirmarNotificacionRequerimientoBusiness;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public DescargarDocumentoController getDescargarDocumentoController() {
        return descargarDocumentoController;
    }

    public void setDescargarDocumentoController(DescargarDocumentoController descargarDocumentoController) {
        this.descargarDocumentoController = descargarDocumentoController;
    }

    /**
     * @return the araValidadorHelper
     */
    public AraValidadorHelper getAraValidadorHelper() {
        return araValidadorHelper;
    }

    /**
     * @param araValidadorHelper the araValidadorHelper to set
     */
    public void setAraValidadorHelper(AraValidadorHelper araValidadorHelper) {
        this.araValidadorHelper = araValidadorHelper;
    }

    /**
     * @return the notificacion
     */
    public MultiFormatReportVU getNotificacion() {
        return notificacion;
    }

    /**
     * @param notificacion
     *            the notificacion to set
     */
    public void setNotificacion(MultiFormatReportVU notificacion) {
        this.notificacion = notificacion;
    }

    /**
     * @return the generarDocumentosHelper
     */
    public GenerarDocumentosHelper getGenerarDocumentosHelper() {
        return generarDocumentosHelper;
    }

    /**
     * @param generarDocumentosHelper
     *            the generarDocumentosHelper to set
     */
    public void setGenerarDocumentosHelper(GenerarDocumentosHelper generarDocumentosHelper) {
        this.generarDocumentosHelper = generarDocumentosHelper;
    }

    public void validaAcceso() {
        // Validacion de acceso a la funcionalidad
        this.validaAccesoRolEmpleado(RolesConstantes.ROL_EMPLEADO_ADMINISTRADOR);
    }
}
