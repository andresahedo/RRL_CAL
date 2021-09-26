/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.controller.bean.controller;

import mx.gob.sat.sgi.SgiCripto.SgiARAException;
import mx.gob.sat.siat.juridica.ara.constantes.AraConstantes;
import mx.gob.sat.siat.juridica.ara.util.AraValidadorHelper;
import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EnumeracionBitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoAcuse;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoProcesoFirma;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.MultiFormatReportVU;
import mx.gob.sat.siat.juridica.base.dto.AbogadoDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.MotivoRechazoDTO;
import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.FirmarTareaBussines;
import mx.gob.sat.siat.juridica.bpm.excepcion.EstadoTareaUsuarioNoValidoException;
import mx.gob.sat.siat.juridica.bpm.excepcion.NoExisteTareaUsuarioException;
import mx.gob.sat.siat.juridica.bpm.excepcion.TareaYaAsignadaAlUsuarioException;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.TurnarDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.GeneralConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AnexarDocumentoBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility.GenerarDocumentosHelper;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Bean que implementan la l&oacute;gica de negocio para firmar una
 * tarea y avanzar el BP
 * 
 * @author Softtek
 * 
 */
@SessionScoped
@ManagedBean(name = "firmarTareaController")
public class FirmarTareaController extends BaseControllerBean {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = 1L;

    /** Bussines para firmar tarea */
    @ManagedProperty(value = "#{firmarTareaBussines}")
    private FirmarTareaBussines firmarTareaBussines;
    /**
     * Propiedad que representa una notificacion.
     */
    @ManagedProperty(value = "#{notificacion}")
    private transient MultiFormatReportVU notificacion;
    /**
     * Controller que implementa la logica de negocio de descargar un
     * documento.
     */
    @ManagedProperty(value = "#{descargarDocumentoController}")
    private DescargarDocumentoController descargarDocumentoController;

    /** DTO que representa los datos de un abogado */
    @ManagedProperty(value = "#{abogadoDTO}")
    private AbogadoDTO abogadoDTO;

    /** DTO que representa los datos obtenidos en la bandeja */
    @ManagedProperty(value = "#{datosBandejaTareaDTO}")
    private DatosBandejaTareaDTO datosBandejaTareaDTO;
    /**
     * Controller que implementa la logica de negocio de generar
     * documentos.
     */
    @ManagedProperty(value = "#{generarDocumentosHelper}")
    private GenerarDocumentosHelper generarDocumentosHelper;

    @ManagedProperty(value = "#{anexarDocumentoBussines}")
    private AnexarDocumentoBussines anexarDocumentoBussines;
    
    @ManagedProperty(value = "#{araValidadorHelper}")
    private AraValidadorHelper araValidadorHelper;
    /**
     * Propiedad que representa la firma digital.
     */
    /** DTO que representa lo firma de la solicitud */
    private FirmaDTO firma;

    private String firmaDigital;

    private String userID;
    /**
     * Propiedad que identifica una reasignacion.
     */
    private Boolean reasignar;

    private String cadenaOriginal;

    private MotivoRechazoDTO motivosDto;

    private TurnarDTO datosTurnarDTO = new TurnarDTO();

    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;

    private static final String DATOS_BANDEJA = "datosBandejaDTO";

    private static final String USER_ID = "userID";

    private static final String FIRMA = "firmaDigital";
    
    private static final String OCURRIO_ERROR = "Ocurri\u00F3 un error al firmar.";
    
    private String sNumSerie;
    
    private static final String NUMERO_SERIE="numeroSerie";

    public void definirTurnar() {
        setReasignar(false);
    }

    /**
     * M&eacute;todo para firma de tarea
     * 
     * @throws java.io.IOException
     * @throws NumberFormatException
     * @throws TareaYaAsignadaAlUsuarioException
     * @throws EstadoTareaUsuarioNoValidoException
     * @throws NoExisteTareaUsuarioException
     * @throws mx.gob.sat.sgi.SgiCripto.SgiARAException
     */
    public void firmaTurnar() throws IOException, NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException,
            TareaYaAsignadaAlUsuarioException, SgiARAException {

        getFlash().put(DATOS_BANDEJA, getDatosBandejaTareaDTO());
        setUserID(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(USER_ID)
                .toString());

        if (getUserID().equalsIgnoreCase(getUserProfile().getRfc())) {
            sNumSerie = getAraValidadorHelper().getNumSerie(FacesContext
                    .getCurrentInstance().getExternalContext()
                    .getRequestParameterMap().get(NUMERO_SERIE));

            if (getAraValidadorHelper().validarRfcNumSerie(sNumSerie, getUserProfile().getRfc())
                    && getAraValidadorHelper().getEdoCertificado(sNumSerie)) {
                StringBuffer url = new StringBuffer(LoginConstante.BANDEA_JSF);
                StringBuffer url2 = new StringBuffer(LoginConstante.BANDEJA_RETURNAR_JSF);
                if (!reasignar) {
                    getFirmarTareaBussines().firmarTurnar(getDatosBandejaTareaDTO().getNumeroAsunto(),
                            getDatosBandejaTareaDTO().getIdTareaUsuario().toString(), getAbogadoDTO().getRfc(),
                            getUserProfile().getRfc(), datosTurnarDTO);
                    getFlash().put(
                            GeneralConstantes.MENSAJE_REDIRECT,
                            "El asunto con n\u00famero " + getDatosBandejaTareaDTO().getNumeroAsunto()
                            + " ha sido turnado exitosamente.");

                    ConfigurableNavigationHandler configurableNavigationHandler
                            = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                            .getNavigationHandler();

                    configurableNavigationHandler.performNavigation(url.toString() + "?faces-redirect=true");

                } else {
                    getFirmarTareaBussines().firmarReasignar(getDatosBandejaTareaDTO().getIdTareaUsuario().toString(),
                            getUserProfile().getRfc(), getAbogadoDTO().getRfc(),
                            getDatosBandejaTareaDTO().getNumeroAsunto(), getDatosBandejaTareaDTO().getRfcUsuarioAsignado(), getDatosBandejaTareaDTO().getIdInstancia());
                    getFlash().put(
                            GeneralConstantes.MENSAJE_REDIRECT,
                            "El asunto con n\u00famero " + getDatosBandejaTareaDTO().getNumeroAsunto()
                            + " ha sido turnado exitosamente.");

                    ConfigurableNavigationHandler configurableNavigationHandler
                            = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                            .getNavigationHandler();

                    configurableNavigationHandler.performNavigation(url2.toString() + "?faces-redirect=true");
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null,
                            MessageFormat.format(AraConstantes.MENSAJE_LLAVE_SERIE_INCORRECTO, getUserProfile().getRfc())));

        }
    }

    /**
     * 
     * @return
     */
    public String procesaFirma() {
        getLogger().debug(
                "firma: " + FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(FIRMA));
        setFirmaDigital(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(FIRMA)
                .toString());
        // Hacer algo con la firma
        getLogger().debug(getFirmaDigital());
        // regresar pagina de firmado correcto
        return "";
    }

    public void validaAccesoAbogado() {
        // Validacion de acceso a la funcionalidad
        this.validaAccesoRolEmpleado(RolesConstantes.ROL_EMPLEADO_ABOGADO);
        this.cargarDatosBandeja();
    }

    public void validaAccesoContribuyente() {
        // Validacion de acceso a la funcionalidad
        this.validaAccesoContribuytente();
    }

    /**
     * Metodo que carga los datos a la bandeja.
     */
    public void cargarDatosBandeja() {
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get(DATOS_BANDEJA));
        if (getDatosBandejaTareaDTO() == null) {
            setDatosBandejaTareaDTO(new DatosBandejaTareaDTO());
            getDatosBandejaTareaDTO().setNumeroAsunto((String) getFlash().get("numAsunto"));
            getDatosBandejaTareaDTO().setIdSolicitud((Long) getFlash().get("idSolicitud"));
            getFlash().remove("idSolicitud");
        }
        setCadenaOriginal((String) getFlash().get("cadenaOriginal"));
    }

    /**
     * Metodo que firma una tarea en especifico una consulta.
     * 
     * @return
     */
    public String firmaConsulta() {
        String urlforward = UrlFirma.FIRMA_CONSULTAS.toString();
        getFlash().put(DATOS_BANDEJA, getDatosBandejaTareaDTO());
        setFirma(new FirmaDTO(new Date()));
        getDatosBandejaTareaDTO().setRfcSolicitante(getUserProfile().getRfc());
        setUserID(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(USER_ID)
                .toString());

        try {
            if (getUserID().equalsIgnoreCase(getUserProfile().getRfc())) {
                List<Long> idsDoc = null;

                sNumSerie = getAraValidadorHelper().getNumSerie(FacesContext
                        .getCurrentInstance().getExternalContext()
                        .getRequestParameterMap().get(NUMERO_SERIE));

                if (getAraValidadorHelper().validarRfcNumSerie(sNumSerie, getUserProfile().getRfc()) 
                        && getAraValidadorHelper().getEdoCertificado(sNumSerie)) {
                    setFirmaDigital(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                            .get(FIRMA).toString());

                    firma.setSello(getFirmaDigital());
                    firma.setRfcUsuario(getUserProfile().getRfc());
                    firma.setCveRol(getUserProfile().getRol());
                    firma.setCadenaOriginal(getCadenaOriginal());
                    firma.setCveProceso(TipoProcesoFirma.ADJ_DOC_ADI.getClave());
                    firma.setCertificado(sNumSerie);
                    FirmaDTO firmaSelladora = getFirmarTareaBussines().obtenSelloSIAT(getCadenaOriginal());

                    idsDoc
                            = getGenerarDocumentosHelper().generarDocumentosPromocion(getDatosBandejaTareaDTO(),
                                    TipoAcuse.RDOCAD.getClave(), firma.getCadenaOriginal(), firma.getSello(),
                                    firmaSelladora.getCadenaOriginal(), firmaSelladora.getSello());
                    getFirmarTareaBussines().firmarDocumentos(getDatosBandejaTareaDTO().getIdSolicitud(), firma);
                }

                descargarDocumentoController.getDatosBandejaTareaDTO().setNumeroAsunto(
                        getDatosBandejaTareaDTO().getNumeroAsunto());
                descargarDocumentoController.obtenerDocumentosByIdDoc(idsDoc);

                FacesMessage msg = new FacesMessage("", "Los documentos fueron guardados.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                urlforward = LoginConstante.DESCARGA_DOCUMENTO;

            } else {

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, null,
                                MessageFormat.format(AraConstantes.MENSAJE_LLAVE_SERIE_INCORRECTO, getUserProfile().getRfc())));
            }
        } catch (BusinessException e) {
            getLogger().error("Ocurrio un error FirmarTareaController.firmaConsulta {0}", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
        } catch (SgiARAException ex) {
            getLogger().error("Ocurrio un error al firmar FirmarTareaController.firmaConsulta {0}", ex);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } catch (Exception ex) {
            getLogger().error("Ocurrio un error al firmar FirmarTareaController.firmaConsulta {0}", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, OCURRIO_ERROR, ""));
        }

        return urlforward;
    }

    /**
     * Metodo que carga los datos del motivo derechazo
     */
    public void cargarDatosRechazo() {
        setMotivosDto((MotivoRechazoDTO) getFlash().get("motivoRechazo"));
        cargarDatosBandeja();
    }

    /**
     * Metodo de firmar de rechazo de asunto para oficialia
     * 
     * @return
     */
    public String firmaRechazoOficialia() {
        String urlforward = UrlFirma.PAGINA_FIRMA_RECHAZO_ASUNTO_OFICIALIA.toString();
        setUserID(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(USER_ID)
                .toString());

        try {
            if (getUserID().equalsIgnoreCase(getUserProfile().getRfc())) {
                sNumSerie = getAraValidadorHelper().getNumSerie(FacesContext
                        .getCurrentInstance().getExternalContext()
                        .getRequestParameterMap().get(NUMERO_SERIE));

                if (getAraValidadorHelper().validarRfcNumSerie(sNumSerie, getUserProfile().getRfc()) 
                        && getAraValidadorHelper().getEdoCertificado(sNumSerie)) {
                    setFirma(new FirmaDTO(new Date()));
                    firma.setSello(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(FIRMA)
                            .toString());
                    firma.setRfcUsuario(getUserProfile().getRfc());
                    firma.setCveRol(getUserProfile().getRol());
                    firma.setCadenaOriginal(getCadenaOriginal());
                    firma.setCertificado(sNumSerie);

                    getMotivosDto().setFirma(firma);
                    getFirmarTareaBussines().rechazarAsunto(getMotivosDto());

                    String[] param = new String[1];
                    param[0] = getMotivosDto().getIdTramite();

                    getFlash()
                            .put(GeneralConstantes.MENSAJE_REDIRECT,
                                    MessageFormat.format(messages.getString("oficialia.rechazo.asunto.confirmacion"),
                                            (Object[]) param));

                    urlforward = LoginConstante.BANDEA_JSF;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", 
                                MessageFormat.format(AraConstantes.MENSAJE_LLAVE_SERIE_INCORRECTO, getUserProfile().getRfc())));
            }
        } catch (SgiARAException ex) {
            getLogger().error("Ocurrio un error al firmar FirmarTareaController.firmaRechazoOficialia {0}", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } catch (Exception ex) {
            getLogger().error("Ocurrio un error al firmar FirmarTareaController.firmaRechazoOficialia {0}", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurri\u00F3 un error al firmar la promoci\u00F3n", ""));
        }

        return urlforward;
    }

    /**
     * Metodo de firmar de rechazo de documento para oficialia
     * 
     * @return
     */
    public String firmaRechazoDocumentoOficialia() {
        String urlforward = UrlFirma.PAGINA_FIRMA_RECHAZO_DOCUMENTO_OFICIALIA.toString();
        setUserID(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(USER_ID)
                .toString());
        try {
            if (getUserID().equalsIgnoreCase(getUserProfile().getRfc())) {
                sNumSerie = getAraValidadorHelper().getNumSerie(FacesContext
                        .getCurrentInstance().getExternalContext()
                        .getRequestParameterMap().get(NUMERO_SERIE));

                if (getAraValidadorHelper().validarRfcNumSerie(sNumSerie, getUserProfile().getRfc()) 
                        && getAraValidadorHelper().getEdoCertificado(sNumSerie)) {
                    setFirma(new FirmaDTO(new Date()));
                    firma.setSello(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(FIRMA)
                            .toString());
                    firma.setRfcUsuario(getUserProfile().getRfc());
                    firma.setCveRol(getUserProfile().getRol());
                    firma.setCadenaOriginal(getCadenaOriginal());
                    firma.setCertificado(sNumSerie);
                    if (getMotivosDto() != null) {
                        getMotivosDto().setFirma(firma);
                    } else {
                        setMotivosDto(new MotivoRechazoDTO());
                        getMotivosDto().setFirma(firma);
                    }
                    getFirmarTareaBussines().rechazarDocumento(getMotivosDto());

                    String[] param = new String[2];
                    param[0] = getMotivosDto().getNombreDocumento();
                    param[1] = getMotivosDto().getTipoDocumento();

                    getFlash().put(
                            GeneralConstantes.MENSAJE_REDIRECT,
                            MessageFormat.format(
                                    messages.getString("oficialia.rechazo.asunto.confirmacionDocumento.rechazado"),
                                    (Object[]) param));

                    urlforward = LoginConstante.BANDEA_JSF;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", 
                                MessageFormat.format(AraConstantes.MENSAJE_LLAVE_SERIE_INCORRECTO, getUserProfile().getRfc())));
            }
        } catch (SgiARAException ex) {
            getLogger().error("Ocurrio un error al firmar FirmarTareaController.firmaRechazoDocumentoOficialia {0}", ex);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } catch (Exception ex) {
            getLogger().error("Ocurrio un error al firmar FirmarTareaController.firmaRechazoDocumentoOficialia {0}", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, OCURRIO_ERROR, ""));
        }

        return urlforward;
    }

    /**
     * 
     * @return
     */
    public String firmaDocumentoOficialia() {
        String urlforward = LoginConstante.FIRMA_DOCUMENTO_OFICIALIA;
        getFlash().put(DATOS_BANDEJA, getDatosBandejaTareaDTO());
        setFirma(new FirmaDTO(new Date()));
        List<Long> idsDoc;
        setUserID(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(USER_ID)
                .toString());
        try {
            if (getUserID().equalsIgnoreCase(getUserProfile().getRfc())) {
                sNumSerie = getAraValidadorHelper().getNumSerie(FacesContext
                        .getCurrentInstance().getExternalContext()
                        .getRequestParameterMap().get(NUMERO_SERIE));

                if (getAraValidadorHelper().validarRfcNumSerie(sNumSerie, getUserProfile().getRfc()) 
                        && getAraValidadorHelper().getEdoCertificado(sNumSerie)) {
                    setFirmaDigital(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(FIRMA)
                            .toString());
                    firma.setSello(getFirmaDigital());
                    firma.setRfcUsuario(getUserProfile().getRfc());
                    firma.setCveRol(getUserProfile().getRol());
                    // TODO cadena original
                    firma.setCadenaOriginal(getCadenaOriginal());
                    firma.setCveProceso(TipoProcesoFirma.ADJ_DOC_ADI.getClave());
                    firma.setCertificado(sNumSerie);

                    FirmaDTO firmaSelladora = getFirmarTareaBussines().obtenSelloSIAT(cadenaOriginal);

                    idsDoc
                            = getGenerarDocumentosHelper().generarDocumentosPromocion(getDatosBandejaTareaDTO(),
                                    TipoAcuse.RDOCAD.getClave(), firma.getCadenaOriginal(), firma.getSello(),
                                    firmaSelladora.getCadenaOriginal(), firmaSelladora.getSello());

                    // TODO: INTEGRAR LA SELLADORA
                    getFirmarTareaBussines().firmarDocumentos(getDatosBandejaTareaDTO().getIdSolicitud(), firma);

                    descargarDocumentoController.getDatosBandejaTareaDTO().setNumeroAsunto(
                            getDatosBandejaTareaDTO().getNumeroAsunto());
                    descargarDocumentoController.obtenerDocumentosByIdDoc(idsDoc);

                    FacesMessage msg = new FacesMessage("", messages.getString("oficialia.mensaje.documento"));
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                    urlforward = LoginConstante.DESCARGA_DOCUMENTO;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", 
                                MessageFormat.format(AraConstantes.MENSAJE_LLAVE_SERIE_INCORRECTO, getUserProfile().getRfc())));
            }
        } catch (BusinessException e) {
            getLogger().error("Ocurrio un error FirmarTareaController.firmaDocumentoOficialia {0}", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
        } catch (SgiARAException ex) {
            getLogger().error("Ocurrio un error al firmar FirmarTareaController.firmaDocumentoOficialia {0}", ex);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } catch (Exception ex) {
            getLogger().error("Ocurrio un error al firmar FirmarTareaController.firmaDocumentoOficialia {0}", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, OCURRIO_ERROR, ""));
        }

        return urlforward;
    }

    /**
     * Metodo que implementa la logica de negocio para remitir una
     * firma.
     * 
     * @return
     */
    public String firmarRemitir() {
        String urlforward = UrlFirma.PAGINA_FIRMA_REMITIR.toString();
        getFlash().put(DATOS_BANDEJA, getDatosBandejaTareaDTO());
        setUserID(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(USER_ID)
                .toString());

        try {
            if (getUserID().equalsIgnoreCase(getUserProfile().getRfc())) {
                Date fechaFirma = new Date();
                sNumSerie = getAraValidadorHelper().getNumSerie(FacesContext
                        .getCurrentInstance().getExternalContext()
                        .getRequestParameterMap().get(NUMERO_SERIE));

                if (getAraValidadorHelper().validarRfcNumSerie(sNumSerie, getUserProfile().getRfc()) 
                        && getAraValidadorHelper().getEdoCertificado(sNumSerie)) {
                    setFirma(new FirmaDTO(fechaFirma));
                    firma.setSello(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(FIRMA)
                            .toString());
                    firma.setRfcUsuario(getUserProfile().getRfc());
                    firma.setCveRol(getUserProfile().getRol());

                    firma.setCadenaOriginal(firmarTareaBussines.generaCadenaOriginalRemitir(getDatosBandejaTareaDTO()
                            .getIdSolicitud(), getUserProfile().getRfc()));
                    firma.setCertificado(sNumSerie);

                    getFirmarTareaBussines().firmarRemitir(firma, this.getDatosBandejaTareaDTO().getNumeroAsunto(),
                            getDatosBandejaTareaDTO().getIdTareaUsuario(), getUserProfile().getRfc());
                    StringBuffer msgRemitir
                            = new StringBuffer().append("El asunto ").append(getDatosBandejaTareaDTO().getNumeroAsunto())
                            .append(" ha sido remitido");
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", msgRemitir.toString());
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                    urlforward = LoginConstante.BANDEA_JSF;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, null, 
                                MessageFormat.format(AraConstantes.MENSAJE_LLAVE_SERIE_INCORRECTO, getUserProfile().getRfc())));
                urlforward = UrlFirma.PAGINA_FIRMA_REMITIR.toString();
            }
        } catch (SgiARAException ex) {
            getLogger().error("Ocurrio un error al firmar FirmarTareaController.firmarRemitir {0}", ex);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } catch (Exception ex) {
            getLogger().error("Ocurrio un error al firmar FirmarTareaController.firmarRemitir {0}", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
            urlforward = "mostrarFirma.jsf";
        }

        return urlforward;
    }

    /**
     * Metodo que implementa la logica de negocio para confirmar una
     * notificacion.
     * 
     * @return
     * @throws IOException
     */
    public String firmarCapturaFechaNotificacion() throws IOException {
        String urlforward = UrlFirma.PAGINA_FIRMA_CAPTURA_FECHA.toString();
        getFlash().put(DATOS_BANDEJA, getDatosBandejaTareaDTO());

        try {
            setUserID(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(USER_ID)
                    .toString());
            if (getUserID().equalsIgnoreCase(getUserProfile().getRfc())) {
                sNumSerie = getAraValidadorHelper().getNumSerie(FacesContext
                        .getCurrentInstance().getExternalContext()
                        .getRequestParameterMap().get(NUMERO_SERIE));

                if (getAraValidadorHelper().validarRfcNumSerie(sNumSerie, getUserProfile().getRfc()) 
                        && getAraValidadorHelper().getEdoCertificado(sNumSerie)) {
                    boolean blnResolucion = this.getDatosBandejaTareaDTO().getNombreTarea()
                            .equals(EnumeracionBitacora.NOTIFICACION_RESOLUCION.getDescripcion());
                    
                    setFirma(new FirmaDTO(new Date()));
                    firma.setSello(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(FIRMA)
                            .toString());
                    firma.setRfcUsuario(getUserProfile().getRfc());
                    firma.setCveRol(getUserProfile().getRol());
                    firma.setCadenaOriginal(getCadenaOriginal());
                    firma.setCertificado(sNumSerie);
                    getDescargarDocumentoController().inicializar(datosBandejaTareaDTO);
                    getFirmarTareaBussines().firmarCapturaFechaNotificacion(firma, this.getDatosBandejaTareaDTO(),
                            getUserProfile().getRfc(), blnResolucion);
                    FacesMessage msg = new FacesMessage("", "La informaci\u00F3n se ha guardado correctamente");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                    urlforward = LoginConstante.BANDEA_JSF;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, null, 
                                MessageFormat.format(AraConstantes.MENSAJE_LLAVE_SERIE_INCORRECTO, getUserProfile().getRfc())));
            }
        } catch (SgiARAException ex) {
            getLogger().error("Ocurrio un error al firmar FirmarTareaController.firmarCapturaFechaNotificacion {0}", ex);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } catch (Exception ex) {
            getLogger().error("Ocurrio un error al firmar FirmarTareaController.firmarCapturaFechaNotificacion {0}", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, OCURRIO_ERROR, ""));
        }

        return urlforward;
    }

    /**
     * Metodo que implementa la logica de negocio para autorizar un
     * asunto.
     * 
     * @return
     */
    public String firmarAutorizarResolucion() {
        String urlforward = UrlFirma.PAGINA_FIRMA_RESOLUCION.toString();
        getFlash().put(DATOS_BANDEJA, getDatosBandejaTareaDTO());
        setUserID(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(USER_ID)
                .toString());
        try {
            if (getUserID().equalsIgnoreCase(getUserProfile().getRfc())) {
                sNumSerie = getAraValidadorHelper().getNumSerie(FacesContext
                        .getCurrentInstance().getExternalContext()
                        .getRequestParameterMap().get(NUMERO_SERIE));

                if (getAraValidadorHelper().validarRfcNumSerie(sNumSerie, getUserProfile().getRfc()) 
                        && getAraValidadorHelper().getEdoCertificado(sNumSerie)) {
                    setFirma(new FirmaDTO(new Date()));
                    firma.setSello(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(FIRMA)
                            .toString());
                    firma.setRfcUsuario(getUserProfile().getRfc());
                    firma.setCveRol(getUserProfile().getRol());
                    firma.setCadenaOriginal(getCadenaOriginal());
                    firma.setCertificado(sNumSerie);
                    getFirmarTareaBussines().firmaAutorizar(firma, this.getDatosBandejaTareaDTO().getNumeroAsunto(),
                            this.getDatosBandejaTareaDTO().getIdTareaUsuario().toString(), firma.getRfcUsuario());
                    StringBuffer msgAutorizar
                            = new StringBuffer().append("El asunto ").append(getDatosBandejaTareaDTO().getNumeroAsunto())
                            .append(" ha sido autorizado");
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", msgAutorizar.toString());
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return LoginConstante.BANDEA_JSF;
                }
            } else {

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, null, 
                                MessageFormat.format(AraConstantes.MENSAJE_LLAVE_SERIE_INCORRECTO, getUserProfile().getRfc())));
            }
        } catch (SgiARAException ex) {
            getLogger().error("Ocurrio un error al firmar la autorizacion de la resolucion FirmarTareaController.firmarAutorizarResolucion {0}", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } catch (Exception ex) {
            getLogger().error("Ocurrio un error al firmar la autorizacion de la resolucion FirmarTareaController.firmarAutorizarResolucion {0}", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, OCURRIO_ERROR, ""));
        }

        return urlforward;
    }

    /**
     * 
     * @return abogadoDTO
     */
    public AbogadoDTO getAbogadoDTO() {
        return abogadoDTO;
    }

    /**
     * 
     * @param abogadoDTO
     *            a fijar
     */
    public void setAbogadoDTO(AbogadoDTO abogadoDTO) {
        this.abogadoDTO = abogadoDTO;
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
    }

    /**
     * Metodo que guarda un documento oficial.
     * 
     * @param numeroAsunto
     * @param documentoOficial
     */
    public void guardarDocOficial(String numeroAsunto, DocumentoOficial documentoOficial) {
        firmarTareaBussines.guardarDocOficial(numeroAsunto, documentoOficial);

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
     * 
     * @return reasignar
     */
    public Boolean getReasignar() {
        return reasignar;
    }

    /**
     * 
     * @param reasignar
     *            el/la reasignar a fijar.
     */
    public void setReasignar(Boolean reasignar) {
        this.reasignar = reasignar;
    }

    /**
     * 
     * @return descargarDocumentoController
     */
    public DescargarDocumentoController getDescargarDocumentoController() {
        return descargarDocumentoController;
    }

    /**
     * 
     * @param descargarDocumentoController
     *            el descargarDocumentoController a fijar.
     */
    public void setDescargarDocumentoController(DescargarDocumentoController descargarDocumentoController) {
        this.descargarDocumentoController = descargarDocumentoController;
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

    /**
     * 
     * @return firmaDigital
     */
    public String getFirmaDigital() {
        return firmaDigital;
    }

    /**
     * 
     * @param firmaDigital
     *            la firmaDigital a fijar.
     */
    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }

    /**
     * @return the sNumSerie
     */
    public String getsNumSerie() {
        return sNumSerie;
    }

    /**
     * @param sNumSerie the sNumSerie to set
     */
    public void setsNumSerie(String sNumSerie) {
        this.sNumSerie = sNumSerie;
    }

    public FirmaDTO getFirma() {
        return firma;
    }

    public void setFirma(FirmaDTO firma) {
        this.firma = firma;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID
     *            the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the cadenaOriginal
     */
    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    /**
     * @param cadenaOriginal
     *            the cadenaOriginal to set
     */
    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public AnexarDocumentoBussines getAnexarDocumentoBussines() {
        return anexarDocumentoBussines;
    }

    public void setAnexarDocumentoBussines(AnexarDocumentoBussines anexarDocumentoBussines) {
        this.anexarDocumentoBussines = anexarDocumentoBussines;
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

    public MotivoRechazoDTO getMotivosDto() {
        return motivosDto;
    }

    public void setMotivosDto(MotivoRechazoDTO motivosDto) {
        this.motivosDto = motivosDto;
    }

    public ResourceBundle getMessages() {
        return messages;
    }

    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    public TurnarDTO getDatosTurnarDTO() {
        return datosTurnarDTO;
    }

    public void setDatosTurnarDTO(TurnarDTO datosTurnarDTO) {
        this.datosTurnarDTO = datosTurnarDTO;
    }

}
