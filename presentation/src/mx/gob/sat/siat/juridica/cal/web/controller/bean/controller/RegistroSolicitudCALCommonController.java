package mx.gob.sat.siat.juridica.cal.web.controller.bean.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;

import com.softtek.idc.client.util.exception.ContribuyenteInactivoException;
import com.softtek.idc.client.util.exception.ContribuyenteNoEncontradoException;
import com.softtek.idc.client.util.exception.IDCException;
import com.softtek.idc.client.util.exception.RFCNoVigenteException;
import com.softtek.idc.common.exceptions.IDCNoDisponibleException;

import mx.gob.sat.sgi.SgiCripto.SgiARAException;
import mx.gob.sat.siat.juridica.ara.constantes.AraConstantes;
import mx.gob.sat.siat.juridica.ara.util.AraValidadorHelper;
import mx.gob.sat.siat.juridica.base.constantes.MenusConstantes;
import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.AccionesBitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.AccionesBitacoraConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoAcuse;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoClasificacionArancelaria;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoProcesoFirma;
import mx.gob.sat.siat.juridica.base.dao.domain.model.BitacoraAU;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tarea;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.DomicilioSolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.ModalidadesDTO;
import mx.gob.sat.siat.juridica.base.dto.PersonaSolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.base.util.helper.FormatoDatosHelper;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.BaseCloudController;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.DescargarDocumentoController;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.buzon.exception.BuzonNoDisponibleException;
import mx.gob.sat.siat.juridica.buzon.exception.CorreoNoRegistradoException;
import mx.gob.sat.siat.juridica.ca.util.constants.RegistroSolicitudConstants;
import mx.gob.sat.siat.juridica.ca.util.validador.TipoRolContribuyenteIDC;
import mx.gob.sat.siat.juridica.cal.dto.FraccionArancelariaDudaDTO;
import mx.gob.sat.siat.juridica.cal.dto.ManifiestoDTO;
import mx.gob.sat.siat.juridica.cal.dto.PersonaInvolucradaDTO;
import mx.gob.sat.siat.juridica.cal.dto.PersonaOirNotificacionesDTO;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.business.ConsultasAutorizacionesCALBusiness;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.business.RegistroSolicitudCALCommonBussines;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.model.FraccionArancelariaDudaDataModel;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.model.PersonaInvolucradaDataModel;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.model.PersonaOirNotificacionesDataModel;
import mx.gob.sat.siat.juridica.cal.web.util.RegistroSolicitudCALCommonValidator;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import mx.gob.sat.siat.juridica.rrl.util.constante.Runtime;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import mx.gob.sat.siat.juridica.rrl.util.helper.DiasHabilesHelper;
import mx.gob.sat.siat.juridica.rrl.util.helper.ValidarFechaHelper;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.CapturaSolicitudBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoDataModel;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility.GenerarDocumentosHelper;

public abstract class RegistroSolicitudCALCommonController extends BaseCloudController<DocumentoDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{consultasAutorizacionesCALBusiness}")
    private ConsultasAutorizacionesCALBusiness consultasAutorizacionesCALBusiness;
    /**
     * Controller para manejar la generacion de documentos en
     * pantalla.
     */
    @ManagedProperty(value = "#{generarDocumentosHelper}")
    private GenerarDocumentosHelper generarDocumentosHelper;

    @ManagedProperty(value = "#{validarFechaHelper}")
    private ValidarFechaHelper validarFechaHelper;

    @ManagedProperty(value = "#{capturaSolicitudBussines}")
    private CapturaSolicitudBussines capturaSolicitudBussines;
    @ManagedProperty(value = "#{formatoDatosHelper}")
    private FormatoDatosHelper formatoDatosHelper;
    @ManagedProperty(value = "#{descargarDocumentoController}")
    private DescargarDocumentoController descargarDocumentoController;
    @ManagedProperty(value = "#{diasHabilesHelper}")
    private DiasHabilesHelper diasHabilesHelper;
    @ManagedProperty(value = "#{registroSolicitudCALCommonValidator}")
    private RegistroSolicitudCALCommonValidator registroSolicitudCALCommonValidator;
    
    @ManagedProperty(value = "#{araValidadorHelper}")
    private AraValidadorHelper araValidadorHelper;

    private SolicitudCALDTO solicitud;

    private PersonaOirNotificacionesDTO personaOirNotificacionesDTO = new PersonaOirNotificacionesDTO();

    private PersonaOirNotificacionesDTO personaOirNotificacionesDTOSelect = new PersonaOirNotificacionesDTO();

    private List<PersonaOirNotificacionesDTO> listaPersonasOirNot;

    private PersonaOirNotificacionesDataModel personaOirNotificacionesDataModel;

    private PersonaInvolucradaDTO personaInvolucradaDTO = new PersonaInvolucradaDTO();

    private FraccionArancelariaDudaDTO fraccionArancelariaDudaDTO = new FraccionArancelariaDudaDTO();

    private List<FraccionArancelariaDudaDTO> listaFraccionAraDuda;

    private FraccionArancelariaDudaDataModel fraccionArancelariaDudaDataModel;

    private List<PersonaInvolucradaDTO> listaPersonaInvolucrada;

    private PersonaInvolucradaDataModel personaInvolucradaDataModel;
    /** Lista de modalidades de consulta */
    private List<ModalidadesDTO> listaModalidadesConsulta = new ArrayList<ModalidadesDTO>();
    /** Lista de modalidades de autorizaci&oacute;n */
    private List<ModalidadesDTO> listaModalidadesAutorizacion = new ArrayList<ModalidadesDTO>();
    
    /** Listas de Manifiestos*/
    private List<ManifiestoDTO> listaManifiestos = new ArrayList<ManifiestoDTO>();
    
    /** Lista de que representa los valores de gran continuyente */
    private List<CatalogoDTO> listaGranContribuyente = new ArrayList<CatalogoDTO>();
    /** Lista de autoridad */
    private List<CatalogoDTO> listaAutoridad = new ArrayList<CatalogoDTO>();
    /** Lista de medios de defensa */
    private List<CatalogoDTO> listaMediosDefensa = new ArrayList<CatalogoDTO>();
    /** Lista de sentido de resoluci&oacute;n */
    private List<CatalogoDTO> listaSentidosResolucion = new ArrayList<CatalogoDTO>();

    private List<CatalogoDTO> listaTipoPersona = new ArrayList<CatalogoDTO>();
    /**
     * Lista que representa si se encuentra dentro del plazo del
     * art&iacute;culo 50
     */
    private List<CatalogoDTO> listaPlazo = new ArrayList<CatalogoDTO>();
    /**
     * Lista con los tipos de clasificacion arancelaria
     */
    private List<CatalogoDTO> listaTipoClasificacion = new ArrayList<CatalogoDTO>();
    /** Representa la modalidad a procesar */
    private String modalidad;
    /** Bean para obtener los mensajes */
    /** Mensaje entre pantallas */
    private String messagesRedirect;
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    /** Bean para obtener los mensajes de error */
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;
    /** Lista de documentos opcionales */
    private List<DocumentoDTO> listaDocumentosOpcionales = new ArrayList<DocumentoDTO>();
    /** Lista de documentos requeridos */
    private List<DocumentoDTO> listaDocumentosRequeridos = new ArrayList<DocumentoDTO>();
    /** Data model de documentos opcionales */
    private DocumentoDataModel documentoSeleccionDataModel;
    /** Documentos opcionales seleccionados */
    private DocumentoDTO[] documentosSeleccionados;
    /** Lista de documentos adjuntados */
    private List<DocumentoDTO> listaDocumentos;
    /** Lista de tipos de documentos seleccionados para ser adjuntados */
    private List<DocumentoDTO> documentosCombo = new ArrayList<DocumentoDTO>();;
    /** Data model de documentos adjuntados */
    private DocumentoDataModel documentoAdjuntarDataModel;
    /** Id documento adjuntar */
    private String documentoSelected;
    /** Archivo para descarga */
    private DefaultStreamedContent archivoDescarga;
    /** Arreglo de documentos a eliminar */
    private DocumentoDTO[] registrosEliminar;

    private PersonaOirNotificacionesDTO[] personasONEliminar;

    private PersonaInvolucradaDTO[] personasInvolucradasEliminar;

    private FraccionArancelariaDudaDTO[] fraccionesDudaEliminar;

    private Date maxDate;
    /** DTO que representa lo firma de la solicitud */
    private FirmaDTO firma;

    private String cadenaOriginal;

    private String firmaDigital;

    private boolean tipoPer;

    private boolean tipoPerMor;

    private boolean tipoClasificacionArancelariaCombo;

    private boolean hechosCirc;

    private boolean mediosDefensa;

    private boolean sujetoEjercicio;

    private boolean clasificacionArancelaria;
    
    private boolean manifiesto = false;

    private boolean banderaRazonSocial;

    private boolean requerido = true;

    private boolean requeridoPrincipal = true;

    private boolean eliminarVisible;

    private boolean eliminarVisiblePerOir;

    private boolean eliminarVisiblePerInv;

    private boolean activarCampos;
    private boolean activarCamposPerInv;

    private boolean activarPerOirNotNom;
    private boolean activarPerOirNotAPat;
    private boolean activarPerOirNotAMat;
    private boolean activarPerOirNotDir;
    private boolean activarPerOirNotTel;
    private boolean disableBuscar = true;

    private String userID;

    private boolean eliminarVisibleFraccDuda;
    private String mensajeAviso;

    private boolean agregarVisible;

    private String rowsCorreo;

    private StringBuffer modalidadTramite;

    private String idDinamicoOpcionales;

    private String idDinamicoFracc;

    private boolean tramiteOficialia = false;
    /**
     * Arreglo con los documentos requeridos (por carga y por
     * selecci&oacute;n de usuario)
     */
    private List<DocumentoDTO> listaGeneralDocumentos;

    private List<String> docsFaltantesPorAnexar;
    
    private String numeroAsuntoFaltantes;
    private boolean acusesFaltantes=Boolean.FALSE;
    private Date fechaRecepcionFaltantes;
    private Long idSolicitudFaltantes;

    public RegistroSolicitudCALCommonController() {}

    public static final String CARACTER_NOMBRE = "cal.solicitud.promocion.caracter.invalido.nombre";
    public static final String CARACTER_A_PATERNO = "cal.solicitud.promocion.caracter.invalido.aPaterno";
    public static final String CARACTER_A_MATERNO = "cal.solicitud.promocion.caracter.invalido.aMaterno";
    
    private String tipoRolContribuyenteText;
    @ManagedProperty(value = "#{tipoRolContribuyenteIDC}")
    private transient TipoRolContribuyenteIDC tipoRolContribuyenteIDC;
    
    private int reintentos = NumerosConstantes.CERO;
    
    /**
     * Postc-Constructor para cargar las unidades emisoras e informacion del
     * solicitante
     */
    @PostConstruct
    public void iniciar() {
    	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	numeroAsuntoFaltantes = (String) session.getAttribute("numeroAsuntoFaltantes");
    	idSolicitudFaltantes = (Long) session.getAttribute("idSolicitudFaltantes");
    	fechaRecepcionFaltantes = (Date) session.getAttribute("fechaRecepcionFaltantes");
    	reintentos = NumerosConstantes.CERO;
    	getLogger().debug("numeroAsuntoFaltantes:"+numeroAsuntoFaltantes);
    	session.removeAttribute("numeroAsuntoFaltantes");
    	session.removeAttribute("idSolicitudFaltantes");
    	session.removeAttribute("fechaRecepcionFaltantes");
    	if (numeroAsuntoFaltantes != null && idSolicitudFaltantes != null) {
    		this.iniciarConFaltantes();
    	}else {
    		this.iniciarSinFaltantes();
    	}
    	
    }
    
    private void iniciarConFaltantes() {
    	getLogger().debug("ingresando a iniciar acuses faltantes");
    	acusesFaltantes=Boolean.TRUE;
    	setSolicitud(getConsultasAutorizacionesCALBusiness().obtenerSolicitudPorId(idSolicitudFaltantes));
    	llenaModalidaTramite();
		prepararFirmaSolicitud();
    }
    
    
    
    private void llenaModalidaTramite() {
    	modalidadTramite = new StringBuffer();
        String mod = getConsultasAutorizacionesCALBusiness().obtenerDescripcionModalidad(getModalidad());
        getModalidadTramite().append("Consultas y Autorizaciones-");
        if (mod != null && !mod.equals("")) {
            getModalidadTramite().append(mod);
        }
        else {
            getModalidadTramite().append(
                    getConsultasAutorizacionesCALBusiness()
                            .obtenerDescripcionModalidad(getSolicitud().getTipoTramite()));
        }
	}

	private void iniciarSinFaltantes() {
        cargarCombosCaptura();
        maxDate = new Date();
        listaPersonasOirNot = new ArrayList<PersonaOirNotificacionesDTO>();
        listaPersonaInvolucrada = new ArrayList<PersonaInvolucradaDTO>();
        listaFraccionAraDuda = new ArrayList<FraccionArancelariaDudaDTO>();
        personaOirNotificacionesDataModel = new PersonaOirNotificacionesDataModel(listaPersonasOirNot);
        personaInvolucradaDataModel = new PersonaInvolucradaDataModel(listaPersonaInvolucrada);
        fraccionArancelariaDudaDataModel = new FraccionArancelariaDudaDataModel(listaFraccionAraDuda);
        inicializaTipoPersonaInvolucrada();
        setDisableBuscar(false);
        setActivarCampos(false);
        setActivarCamposPerInv(false);
        setTipoPer(true);

        Long idSolicitud = (Long) getFlash().get("idSolicitud");

        if (idSolicitud != null) {
            setSolicitud(getConsultasAutorizacionesCALBusiness().obtenerDatos(idSolicitud));
            if (solicitud.getSolicitante() != null && solicitud.getSolicitante().getRazonSocial() != null) {
                setBanderaRazonSocial(true);
            }
            else {
                setBanderaRazonSocial(false);
            }
            listaPersonasOirNot.addAll(getSolicitud().getListaPersonasNotificaciones());
            listaPersonaInvolucrada.addAll(getSolicitud().getListaPersonasInvolucradas());
            if (getSolicitud().getListaFraccionDuda() != null) {
                listaFraccionAraDuda.addAll(getSolicitud().getListaFraccionDuda());
            }
            getSolicitud().setIdSolicitud(idSolicitud);
            validaHechosCircParcial();
            validaMediosDefensaParcial();
            validaSujetoEjercicioParcial();
            validaClasificacionArancelaria();
            TipoTramiteDTO tipoTramite =
                    getRegistroSolicitudCALCommonBussines().obtenerTipoTramitePorId(getSolicitud().getTipoTramite());
            if (tipoTramite.getAsignado()) {
                setMensajeAviso(messages.getString("cal.mensaje.aviso"));
            }
            if (tipoTramite.getClaveModulo() != null && tipoTramite.getClaveModulo() == 1) {
                clasificacionArancelaria = true;
            }
            setListaSentidosResolucion(getConsultasAutorizacionesCALBusiness().obtenerSentidosResolucion(
                    getSolicitud().getClaveMedioDefensa()));
            
        }
        else {
            setModalidad((String) getFlash().get("modalidad"));
            String unidadAdmin = (String) getFlash().get("unidadAdmin");
            String idModulo = (String) getFlash().get("idModulo");
            TipoTramiteDTO tipoTramite =
                    getRegistroSolicitudCALCommonBussines().obtenerTipoTramitePorId(getModalidad());
            if (tipoTramite.getAsignado()) {
                setMensajeAviso(messages.getString("cal.mensaje.aviso"));
            }
            if (idModulo != null && idModulo.equals("1")) {
                clasificacionArancelaria = true;
            }
            try {
                setSolicitud(getRegistroSolicitudCALCommonBussines().iniciarRegistroSolicitud(
                        getUserProfile().getRfc(), getModalidad()));
                if (solicitud.getSolicitante() != null && solicitud.getSolicitante().getRazonSocial() != null) {
                    setBanderaRazonSocial(true);
                }
                else {
                    setBanderaRazonSocial(false);
                }
                getSolicitud().setClaveUnidadEmisora(unidadAdmin);
                getSolicitud().setMontoOperacion(BigDecimal.ZERO);
                getSolicitud().setHechosCircunstanciasPrevPlanteadas("0");
                getSolicitud().setHechosCircunstanciasMatMedios("0");
                getSolicitud().setContribuyenteSujetoEjercicio("0");
                getSolicitud().setCveRolCapturista(tipoCapturista());
                
            }
            catch (SolicitudNoGuardadaException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, e.getMessage(), ""));
            }
        }
        if (getSolicitud().getSolicitante() != null) {
            if (getSolicitud().getSolicitante().getDomicilio() != null) {
                if (getSolicitud().getSolicitante().getDomicilio().getCorreoElectronico() != null) {
                    String[] correos =
                            getSolicitud().getSolicitante().getDomicilio().getCorreoElectronico().split("\n");
                    setRowsCorreo(String.valueOf(correos.length));
                    if (getRowsCorreo().equals("1") || getRowsCorreo().equals("2")) {
                        setRowsCorreo("3");
                    }
                }
            }
        }
        llenaModalidaTramite();
        getFlash().remove("idSolicitud");
        meterId();
        meterIdFr();
        
        try {
            this.setTipoRolContribuyenteText(tipoRolContribuyenteIDC.consultaHidrocarburos(getUserProfile().getRfc()));
            this.solicitud.setTipoRolContribuyente(this.getTipoRolContribuyenteText());
            this.solicitud.setEstadoContribuyente(tipoRolContribuyenteIDC.getEstadoContribuyente());
                if(this.getTipoRolContribuyenteText().equals(ProcesosConstantes.GRAN_CONTRIBUYENTE)){
                    this.solicitud.setGranContribuyete("1");
                }else{
                    this.solicitud.setGranContribuyete("0");
                }
        } catch (IOException e) {
            getLogger().error("Error al consultar el tipo de contribuyente en IDC"+e.getMessage());
        }

    }

    protected abstract String tipoCapturista();

    public void selectMontoOperacion(ValueChangeEvent event) {
        getSolicitud().setMontoOperacion((BigDecimal) event.getNewValue());
    }

    protected void meterId() {
        Date hora = new Date();
        Long id = hora.getTime();
        StringBuffer idCompleto = new StringBuffer();
        idCompleto.append("idTree");
        idCompleto.append(id);

        setIdDinamicoOpcionales(idCompleto.toString());
    }

    protected void meterIdFr() {
        Date hora = new Date();
        Long id = hora.getTime();
        StringBuffer idCompleto = new StringBuffer();
        idCompleto.append("idFrac");
        idCompleto.append(id);
        setIdDinamicoFracc(idCompleto.toString());
    }

    public String onFlowProcess(FlowEvent event) throws ArchivoNoGuardadoException {
        getLogger().debug("Entrando al controller de registroSolicitudCAL onFlowProcess");
        if (event.getOldStep().equals(RegistroSolicitudConstants.ANEXAR_DOCUMENTOS_SOLICITUD)) {
            guardarDocumentosParcialNext();
        }
        if (event.getNewStep().equals(RegistroSolicitudConstants.DATOS_SOLICITANTE)) {
            return event.getNewStep();
        }
        else if (event.getNewStep().equals(RegistroSolicitudConstants.PERSONAS_RELACIONADAS)) {
            return event.getNewStep();
        }
        else if (event.getNewStep().equals(RegistroSolicitudConstants.DATOS_PROMOCION)) {
            try {
                RequestContext.getCurrentInstance().reset("FIELForm:panelPromocion5");
                if (!isTramiteOficialia()) {
                    getLogger().debug("intenta guardar la solicitud");
                    guardarSolicitud();
                    getLogger().debug("solicitud guardada");
                }
                return event.getNewStep();
            }
            catch (SolicitudNoGuardadaException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
                getLogger().error("Regresa pagina de captura por excepcion", e);
                return RegistroSolicitudConstants.DATOS_SOLICITUD;

            }
        }
        else if (event.getNewStep().equals(RegistroSolicitudConstants.DATOS_ADICIONALES)) {
            try {
                RequestContext.getCurrentInstance().reset("FIELForm:panelPromocion6");
                RequestContext.getCurrentInstance().reset("FIELForm:panelPromocion7");
                RequestContext.getCurrentInstance().reset("FIELForm:panelPromocion8");
                if (!isTramiteOficialia()) {
                    meterIdFr();
                    getLogger().debug("intenta guardar la solicitud");
                    guardarSolicitud();
                    getLogger().debug("solicitud guardada");
                }
                cargarManifiestos();
                return event.getNewStep();
            }
            catch (SolicitudNoGuardadaException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
                getLogger().error("Regresa pagina de captura por excepcion", e);
                return RegistroSolicitudConstants.DATOS_PROMOCION;

            }
        }
        else if (event.getNewStep().equals(RegistroSolicitudConstants.SELECCION_DOCUMENTOS)) {
            try {
                ManifiestoDTO manifiestoDTO = getRegistroSolicitudCALCommonBussines().validaManifiestos(getSolicitud());
                if (manifiestoDTO != null){
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "", 
                                    messages.getString("cal.manifiesto.error.mensaje")));
                    return RegistroSolicitudConstants.DATOS_ADICIONALES;
                }
                getLogger().debug("intenta guardar la solicitud");
                meterId();
                guardarSolicitud();
                getLogger().debug("solicitud guardada");
                return event.getNewStep();
            }
            catch (SolicitudNoGuardadaException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
                getLogger().error("Regresa pagina de captura por excepcion", e);
                return RegistroSolicitudConstants.DATOS_ADICIONALES;

            }
        }
        else if (event.getNewStep().equals(RegistroSolicitudConstants.ANEXAR_DOCUMENTOS_SOLICITUD)) {
            if (prepararDocumentosAnexar()) {
                return event.getNewStep();
            }
            else {
                if (RegistroSolicitudConstants.FIRMAR.equals(event.getOldStep())) {
                    meterId();
                    return RegistroSolicitudConstants.SELECCION_DOCUMENTOS;
                }
                else {
                    prepararFirmaSolicitud();
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "", messages
                                    .getString("documentos.solicitud.aviso")));
                    return RegistroSolicitudConstants.FIRMAR;
                }
            }
        }
        else if (event.getNewStep().equals(RegistroSolicitudConstants.FIRMAR)) {
            try {
                guardarDocumentosSolicitud();
                prepararFirmaSolicitud();
                return event.getNewStep();
            }
            catch (ArchivoNoGuardadoException e) {
                // Regresa a la pagina de adjuntar documentos con los
                // mismos pasos que cuando se llega a ella a traves
                // del wizard
                setDocsFaltantesPorAnexar(getRegistroSolicitudCALCommonValidator().validarDocumentosAnexadosFaltantes(
                        getListaDocumentos(), getListaGeneralDocumentos()));
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
                for (String nombre : getDocsFaltantesPorAnexar()) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, nombre, ""));
                }
                setAgregarVisible(true);

                return RegistroSolicitudConstants.ANEXAR_DOCUMENTOS_SOLICITUD;
            }
        }
        getLogger().debug("me regresa a datos solicitud");
        return RegistroSolicitudConstants.DATOS_SOLICITUD;

    }
    
    public void cargarManifiestos() {
        // Se llenan los Manifiestos
        try {
            if (getSolicitud().getManifiestos() == null || getSolicitud().getManifiestos().size() == 0) {
                setListaManifiestos(getConsultasAutorizacionesCALBusiness()
                        .obtenerManifiestosPorModalidad(Integer.parseInt(getSolicitud().getTipoTramite())));

                getSolicitud().setManifiestos(getListaManifiestos());
            } else {
                setListaManifiestos(getSolicitud().getManifiestos());
            }

            if (getListaManifiestos().size() > 0) {
                setManifiesto(true);
            }

        } catch (Exception ex) {
            setListaManifiestos(new ArrayList<ManifiestoDTO>());
            setManifiesto(false);
        }
    }

    public void cambiaRequerido() {
        requerido = true;
    }

    public void cambiaNoReq() {
        requerido = false;
    }

    public void cambiaNoReqPrincipal() {
        requeridoPrincipal = false;
    }

    public void cambiaRequeridoPrincipal() {
        requeridoPrincipal = true;
    }

    /**
     * 
     * M&eacute;todo para preparar la solicitud que se desea capturar.
     */
    public void prepararRegistroSolicitud(ActionEvent event) {

    }

    public void validaPersona() {
        tipoPerMor = false;
        tipoPer = false;
        String tipo = getPersonaInvolucradaDTO().getTipoPersona();
        if (getPersonaInvolucradaDTO().getTipoPersona() != null) {
            if (getPersonaInvolucradaDTO().getTipoPersona().equals("1")) {
                        // Persona
                tipoPerMor = true;
                tipoPer = false;
            }
            else { 
                        // Persona fisica
                tipoPerMor = false;
                tipoPer = true;
            }
            personaInvolucradaDTO = new PersonaInvolucradaDTO();
            personaInvolucradaDTO.setTipoPersona(tipo);
        }
        // Se manda a llamar este metodo para que se actualize el
        // panel de los datos
        // de la persona al cambiar el tipo
        extranjeroSelect(null);
    }

    public void validaTipoClasificacion() {
        if (getSolicitud().getTipoClasificacion().equals(TipoClasificacionArancelaria.CLASIFICACION_CLAS.getClave())) {
            tipoClasificacionArancelariaCombo = true;
        }
        else {
            tipoClasificacionArancelariaCombo = false;
            // Limpia el grid de fracciones en las que existe duda
            listaFraccionAraDuda = new ArrayList<FraccionArancelariaDudaDTO>();
            fraccionArancelariaDudaDataModel = new FraccionArancelariaDudaDataModel(listaFraccionAraDuda);
        }
    }

    public void validaHechosCirc() {
        if (getSolicitud().getHechosCircunstanciasPrevPlanteadas().equals("1")) {
            hechosCirc = true;
        }
        else {
            hechosCirc = false;
        }
    }

    public void validaMediosDefensa() {
        if (getSolicitud().getHechosCircunstanciasMatMedios().equals("1")) {
            mediosDefensa = true;
        }
        else {
            mediosDefensa = false;
        }
    }

    public void validaSujetoEjercicio() {
        if (getSolicitud().getContribuyenteSujetoEjercicio().equals("1")) {
            sujetoEjercicio = true;
        }
        else {
            sujetoEjercicio = false;
        }
    }

    private void validaClasificacionArancelaria() {
        setClasificacionArancelaria(false);
        setTipoClasificacionArancelariaCombo(false);
        if (getSolicitud().getTipoClasificacion() != null) {
            setClasificacionArancelaria(true);
            if (getSolicitud().getTipoClasificacion()
                    .equals(TipoClasificacionArancelaria.CLASIFICACION_CLAS.getClave())) {
                setTipoClasificacionArancelariaCombo(true);
            }
        }
    }

    private void validaHechosCircParcial() {
        hechosCirc = false;
        if (getSolicitud().getHechosCircunstanciasPrevPlanteadas() != null
                && getSolicitud().getHechosCircunstanciasPrevPlanteadas().equals("1")) {
            hechosCirc = true;
        }
    }

    private void validaMediosDefensaParcial() {
        mediosDefensa = false;
        if (getSolicitud().getHechosCircunstanciasMatMedios() != null
                && getSolicitud().getHechosCircunstanciasMatMedios().equals("1")) {
            mediosDefensa = true;
        }
    }

    private void validaSujetoEjercicioParcial() {
        setSujetoEjercicio(false);
        if (getSolicitud().getContribuyenteSujetoEjercicio() != null
                && getSolicitud().getContribuyenteSujetoEjercicio().equals("1")) {
            setSujetoEjercicio(true);
        }

    }

    public void agregarPersonaOirNotificacion(ActionEvent event) {
        if (listaPersonasOirNot.size() < NumerosConstantes.CINCO) {
            if (validaPersonaOirNotificacion()) {
                StringBuffer strErrMsg = new StringBuffer("");
                if (!validaNombrePersona(getPersonaOirNotificacionesDTO().getNombre())) {
                    strErrMsg.append(errorMsg.getString(CARACTER_NOMBRE));
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg.getString(CARACTER_NOMBRE)));
                }
                if (!validaNombrePersona(getPersonaOirNotificacionesDTO().getPaterno())) {
                    strErrMsg.append(errorMsg.getString(CARACTER_A_PATERNO));
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg.getString(CARACTER_A_PATERNO)));
                }
                if (!validaNombrePersona(getPersonaOirNotificacionesDTO().getMaterno())) {
                    strErrMsg.append(errorMsg.getString(CARACTER_A_MATERNO));
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg.getString(CARACTER_A_MATERNO)));
                }
                if (strErrMsg.length()==0) {
                    if (validaRFC()) {
                        Date fechaActual = new Date();
                        PersonaOirNotificacionesDTO personaOirNotifDTO = new PersonaOirNotificacionesDTO();
                        personaOirNotifDTO.setIdPersona(fechaActual.getTime());
                        personaOirNotifDTO.setEstadoContribuyente(getPersonaOirNotificacionesDTO().getEstadoContribuyente());
                        personaOirNotifDTO.setNombre(getPersonaOirNotificacionesDTO().getNombre());
                        personaOirNotifDTO.setPaterno(getPersonaOirNotificacionesDTO().getPaterno());
                        personaOirNotifDTO.setMaterno(getPersonaOirNotificacionesDTO().getMaterno());
                        personaOirNotifDTO.setRfc(getPersonaOirNotificacionesDTO().getRfc().toUpperCase());
                        personaOirNotifDTO.setTelefono(getPersonaOirNotificacionesDTO().getTelefono());
                        personaOirNotifDTO.setDireccion(getPersonaOirNotificacionesDTO().getDireccion());
                        personaOirNotifDTO.setNuevo(true);
                        listaPersonasOirNot.add(personaOirNotifDTO);
                        setActivarPerOirNotNom(false);
                        setActivarPerOirNotAPat(false);
                        setActivarPerOirNotAMat(false);
                        setActivarPerOirNotDir(false);
                        setActivarPerOirNotTel(false);
                        setPersonaOirNotificacionesDTO(new PersonaOirNotificacionesDTO());
                    }
                    else {
                        FacesContext.getCurrentInstance().addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                        .getString("cal.solicitud.promocion.RFCFormato")));
                    }
                }
            }
            else {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                .getString("ca.solicitud.promocion.datosPersonaIncompletos")));
            }
        }
        else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                            .getString("ca.solicitud.promocion.maxPersonasOirNot")));
        }
    }

    /**
     * M&eacute;todo para validar el formato del RFC al agregar una
     * persona involucrada, la validacion solo es a 13 posiciones
     * 
     * @return boolean true si el formato es correcto, false si no
     *         cumple con el formato
     * @author antonio.flores Softtek
     * @since 24/04/2014
     */
    private boolean validaRFC() {
        boolean isFormatOk = false;
        Pattern pat13 = Pattern.compile("^[A-Za-z\u00D1\u00F1&�\\_\\+\\.\\,\\-]{4}[0-9]{6}[A-Za-z0-9]{3}$");
        Pattern pat10 = Pattern.compile("^[A-Za-z\u00D1\u00F1&�\\_\\+\\.\\,\\-]{4}[0-9]{6}$");
        Matcher mat13 = pat13.matcher(getPersonaOirNotificacionesDTO().getRfc());
        Matcher mat10 = pat10.matcher(getPersonaOirNotificacionesDTO().getRfc());
        if (mat13.matches() || mat10.matches()) {
            if (validaFechaRFC(getPersonaOirNotificacionesDTO().getRfc())) {
                isFormatOk = true;
                setActivarCampos(true);
            }
        }
        else {
            setActivarCampos(false);
        }
        return isFormatOk;
    }

    /**
     * Metodo que evalua si el formato del mes y dia para el RFC son
     * correctos
     * 
     * @param rfc
     * @return
     */
    private boolean validaFechaRFC(String rfc) {
        int dia = 0;
        int mes = 0;
        int anio = 0;
        // Se evalua el RFC por tipo de persona, dependiendo del tipo
        // de la persona se
        // obtiene un subString de las posiciones para obtener anio,
        // mes y dia
        if (rfc.length() == NumerosConstantes.DIEZ || rfc.length() == NumerosConstantes.TRECE) {
            dia = Integer.parseInt(rfc.substring(NumerosConstantes.OCHO, NumerosConstantes.DIEZ));
            mes = Integer.parseInt(rfc.substring(NumerosConstantes.SEIS, NumerosConstantes.OCHO));
            anio = Integer.parseInt(rfc.substring(NumerosConstantes.CUATRO, NumerosConstantes.SEIS));
        }
        else {
            dia = Integer.parseInt(rfc.substring(NumerosConstantes.SIETE, NumerosConstantes.NUEVE));
            mes = Integer.parseInt(rfc.substring(NumerosConstantes.CINCO, NumerosConstantes.SIETE));
            anio = Integer.parseInt(rfc.substring(NumerosConstantes.TRES, NumerosConstantes.CINCO));
        }
        return validarFechaHelper.validaFechaHelper(dia, mes, anio);
    }

    public void buscarPersonaOirNotificaciones() throws IDCException, ContribuyenteInactivoException,
            RFCNoVigenteException, BuzonNoDisponibleException, CorreoNoRegistradoException {
        String rfcBuscar = personaOirNotificacionesDTO.getRfc();
        String rfcMayusculas = rfcBuscar.toUpperCase();
        setPersonaOirNotificacionesDTO(new PersonaOirNotificacionesDTO());
        getPersonaOirNotificacionesDTO().setRfc(rfcMayusculas);
        if (validaRFC()) {
            try {
                PersonaSolicitudDTO persona = getRegistroSolicitudCALCommonBussines().buscarPersonaSolicitante(getPersonaOirNotificacionesDTO().getRfc());
                if (null != persona) {
                    getPersonaOirNotificacionesDTO().setNombre(persona.getNombre());
                    getPersonaOirNotificacionesDTO().setPaterno(persona.getApellidoPaterno());
                    getPersonaOirNotificacionesDTO().setMaterno(persona.getApellidoMaterno());
                    getPersonaOirNotificacionesDTO().setDireccion(persona.getDomicilio().getCalle() +" "+ persona.getDomicilio().getNombrePais());
                    validaActivarCamposPerOirNot(persona);
                }
            }
            catch (IDCNoDisponibleException e) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", messages
                                .getString("oficialia.busqueda.idcnodisponible")));
            }
            catch (ContribuyenteNoEncontradoException e) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                .getString("cal.validacion.rfcNoEncontrado")));
                setActivarCampos(true);
                setActivarPerOirNotNom(true);
                setActivarPerOirNotAPat(true);
                setActivarPerOirNotAMat(true);
                setActivarPerOirNotDir(true);
                setActivarPerOirNotTel(true);
            }
        }
        else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                            .getString("cal.solicitud.promocion.RFCFormato")));
        }
    }

    public void validaActivarCamposPerOirNot(PersonaSolicitudDTO persona) {
        setActivarPerOirNotNom(false);
        setActivarPerOirNotAPat(false);
        setActivarPerOirNotAMat(false);
        setActivarPerOirNotDir(false);
        setActivarPerOirNotTel(false);

        if (null == persona.getNombre()) {
            setActivarPerOirNotNom(true);
        }
        if (null == persona.getApellidoPaterno()) {
            setActivarPerOirNotAPat(true);
        }
        if (null == persona.getApellidoMaterno()) {
            setActivarPerOirNotAMat(true);
        }
        if (null == persona.getDomicilio()) {
            setActivarPerOirNotDir(true);
        }
        if (null == persona.getDomicilio().getTelefono()) {
            setActivarPerOirNotTel(true);
        }
    }

    public boolean compararApellidoOirNotificaciones(String apePat, String apeMat) {
        return (null == apePat || null == apeMat);
    }

    public boolean compararDomicilioOirNotificaciones(DomicilioSolicitudDTO domicilio, String telefono) {
        return (null == domicilio || null == telefono);
    }

    public boolean validaPersonaOirNotificacion() {
        boolean banderaNombre = false;
        boolean banderaPaterno = false;
        boolean banderaMaterno = false;
        boolean banderaRfc = false;
        boolean banderaTelefono = false;
        boolean banderaNom = false;
        boolean banderaDireccion = false;
        boolean persona;

        if (getPersonaOirNotificacionesDTO().getNombre() != null
                && !getPersonaOirNotificacionesDTO().getNombre().isEmpty()) {
            banderaNombre = true;
        }
        if (getPersonaOirNotificacionesDTO().getPaterno() != null
                && !getPersonaOirNotificacionesDTO().getPaterno().isEmpty()) {
            banderaPaterno = true;
        }
        if (getPersonaOirNotificacionesDTO().getMaterno() != null
                && !getPersonaOirNotificacionesDTO().getMaterno().isEmpty()) {
            banderaMaterno = true;
        }
        if (getPersonaOirNotificacionesDTO().getRfc() != null && !getPersonaOirNotificacionesDTO().getRfc().isEmpty()) {
            banderaRfc = true;
        }
        if (getPersonaOirNotificacionesDTO().getTelefono() != null
                && !getPersonaOirNotificacionesDTO().getTelefono().isEmpty()) {
            banderaTelefono = true;
        }
        if (banderaNombre && banderaMaterno && banderaPaterno) {
            banderaNom = true;
        }
        if (getPersonaOirNotificacionesDTO().getDireccion() != null
                && !getPersonaOirNotificacionesDTO().getDireccion().isEmpty()) {
            banderaDireccion = true;
        }
        if (banderaNom && banderaRfc && banderaTelefono && banderaDireccion) {
            persona = true;
        }
        else {
            persona = false;
            setActivarCampos(true);
        }
        return persona;
    }

    public void eliminarPersonaOirNotificacion(ActionEvent event) {
        for (PersonaOirNotificacionesDTO personaON : getPersonasONEliminar()) {
            getListaPersonasOirNot().remove(personaON);
            if (!personaON.isNuevo()) {
                getRegistroSolicitudCALCommonBussines().eliminaPersona(personaON.getIdPersona());
            }
        }
        setEliminarVisiblePerOir(false);
    }

    public void buscarPersonaInvolucrada() throws IDCException, ContribuyenteInactivoException, RFCNoVigenteException,
            BuzonNoDisponibleException, CorreoNoRegistradoException {
        String rfcBuscar = personaInvolucradaDTO.getRfc();
        String rfcMayusculas = rfcBuscar.toUpperCase();
        String tipoPerInvBuscar = personaInvolucradaDTO.getTipoPersona();
        setPersonaInvolucradaDTO(new PersonaInvolucradaDTO());
        getPersonaInvolucradaDTO().setRfc(rfcMayusculas);
        getPersonaInvolucradaDTO().setTipoPersona(tipoPerInvBuscar);
        if (validaRFCPersonaInv()) {
            try {
                PersonaSolicitudDTO persona =
                        getRegistroSolicitudCALCommonBussines().buscarPersonaSolicitante(
                                getPersonaInvolucradaDTO().getRfc());
                if (null != persona) {
                    getPersonaInvolucradaDTO().setNombre(persona.getNombre());
                    getPersonaInvolucradaDTO().setPaterno(persona.getApellidoPaterno());
                    getPersonaInvolucradaDTO().setMaterno(persona.getApellidoMaterno());
                    getPersonaInvolucradaDTO().setExtranjero(persona.getBlnExtranjero());
                    getPersonaInvolucradaDTO().setDireccion(
                            persona.getDomicilio().getCalle() +" "+ persona.getDomicilio().getNombrePais());
                    getPersonaInvolucradaDTO().setRazonSocial(persona.getRazonSocial());
                    setActivarCamposPerInv(false);
                }
            }
            catch (IDCNoDisponibleException e) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", messages
                                .getString("oficialia.busqueda.idcnodisponible")));
            }
            catch (ContribuyenteNoEncontradoException e) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                .getString("cal.validacion.rfcNoEncontrado")));
                setActivarCamposPerInv(true);
            }
        }
        else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                            .getString("ca.solicitud.promocion.RFCFormato")));
        }

    }

    /**
     * Metodo para validar el RFC de una persona involucrada
     * 
     * @return
     */
    private boolean validaRFCPersonaInv() {
        boolean isFormatOk = false;
        if (!getPersonaInvolucradaDTO().isExtranjero()) {
            if (tipoPer) {
                Pattern pat13 = Pattern.compile("^[A-Za-z\u00D1\u00F1&�\\_\\+\\.\\,\\-]{4}[0-9]{6}[A-Za-z0-9]{3}$");
                Pattern pat10 = Pattern.compile("^[A-Za-z\u00D1\u00F1&�\\_\\+\\.\\,\\-]{4}[0-9]{6}$");
                Matcher mat13 = pat13.matcher(getPersonaInvolucradaDTO().getRfc());
                Matcher mat10 = pat10.matcher(getPersonaInvolucradaDTO().getRfc());
                if (mat13.matches() || mat10.matches()) {
                    setActivarCamposPerInv((validaFechaRFC(getPersonaInvolucradaDTO().getRfc())));
                    isFormatOk = (validaFechaRFC(getPersonaInvolucradaDTO().getRfc()));
                }
            }
            else {
                Pattern pat12 = Pattern.compile("^[A-Za-z\u00D1\u00F1&�\\_\\+\\.\\,\\-]{3}[0-9]{6}[A-Za-z0-9]{3}$");
                Pattern pat9 = Pattern.compile("^[A-Za-z\u00D1\u00F1&�\\_\\+\\.\\,\\-]{3}[0-9]{6}$");
                Matcher mat12 = pat12.matcher(getPersonaInvolucradaDTO().getRfc());
                Matcher mat9 = pat9.matcher(getPersonaInvolucradaDTO().getRfc());
                if (mat12.matches() || mat9.matches()) {
                    setActivarCamposPerInv((validaFechaRFC(getPersonaInvolucradaDTO().getRfc())));
                    isFormatOk = (validaFechaRFC(getPersonaInvolucradaDTO().getRfc()));
                }
            }
        }
        else {
            isFormatOk = true;
        }
        return isFormatOk;
    }

    /**
     * Metodo que valida si una persona es Residente en el extranjero
     * Si la persona es residente en el extranjero, se habilitan los
     * campos para que sean capturables, de lo contrario, los campos
     * permanecen deshabilitados
     * 
     * @param changeEvent
     */
    public void extranjeroSelect(AjaxBehaviorEvent changeEvent) {
        String tipo = personaInvolucradaDTO.getTipoPersona();
        boolean extranjero = personaInvolucradaDTO.isExtranjero();
        if (getPersonaInvolucradaDTO().isExtranjero()) {
            setActivarCamposPerInv(true);
            setDisableBuscar(true);
        }
        else {
            setActivarCamposPerInv(false);
            setDisableBuscar(false);
        }
        personaInvolucradaDTO = new PersonaInvolucradaDTO();
        personaInvolucradaDTO.setTipoPersona(tipo);
        personaInvolucradaDTO.setExtranjero(extranjero);
    }

    public void agregarPersonaInvolucrada(ActionEvent event) {

        if (listaPersonaInvolucrada.size() < NumerosConstantes.CINCO) {
            if (validaPersonaInvolucrada()) {
                if (getPersonaInvolucradaDTO().getTipoPersona() == null
                        || getPersonaInvolucradaDTO().getTipoPersona().isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMsg
                                    .getString("ca.solicitud.promocion.required.tipoPersona"), ""));
                }
                else {
                    StringBuffer strErrMsg = new StringBuffer("");
                    if (!tipoPerMor) {
                        if (!validaNombrePersona(getPersonaInvolucradaDTO().getNombre())) {
                            strErrMsg.append(errorMsg.getString(CARACTER_NOMBRE));
                            FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                            .getString(CARACTER_NOMBRE)));
                        }
                        if (!validaNombrePersona(getPersonaInvolucradaDTO().getPaterno())) {
                            strErrMsg.append(errorMsg.getString(CARACTER_A_PATERNO));
                            FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                            .getString(CARACTER_A_PATERNO)));
                        }
                        if (!validaNombrePersona(getPersonaInvolucradaDTO().getMaterno())) {
                            strErrMsg.append(errorMsg.getString(CARACTER_A_MATERNO));
                            FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                            .getString(CARACTER_A_MATERNO)));
                        }
                    }
                    if (strErrMsg.length() == 0) {
                        if (validaRFCPersonaInv()) {
                            PersonaInvolucradaDTO persInvolucradaDTO = new PersonaInvolucradaDTO();
                            persInvolucradaDTO.setIdPersona(new Date().getTime());
                            persInvolucradaDTO.setNombre(getPersonaInvolucradaDTO().getNombre());
                            persInvolucradaDTO.setPaterno(getPersonaInvolucradaDTO().getPaterno());
                            persInvolucradaDTO.setMaterno(getPersonaInvolucradaDTO().getMaterno());
                            persInvolucradaDTO.setRfc(getPersonaInvolucradaDTO().getRfc().toUpperCase());
                            persInvolucradaDTO.setRazonSocial(getPersonaInvolucradaDTO().getRazonSocial());
                            persInvolucradaDTO.setDireccion(getPersonaInvolucradaDTO().getDireccion());
                            persInvolucradaDTO.setTipoPersona(getPersonaInvolucradaDTO().getTipoPersona());
                            persInvolucradaDTO.setExtranjero(getPersonaInvolucradaDTO().isExtranjero());
                            persInvolucradaDTO.setNuevo(true);
                            listaPersonaInvolucrada.add(persInvolucradaDTO);
                            setActivarCamposPerInv(false);
                            PersonaInvolucradaDTO nuevaPersona = new PersonaInvolucradaDTO();
                            setPersonaInvolucradaDTO(nuevaPersona);
                            inicializaTipoPersonaInvolucrada();
                            setDisableBuscar(false);
                        }
                        else {
                            FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                            .getString("ca.solicitud.promocion.RFCFormato")));
                        }

                    }
                }
            }
            else {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                .getString("ca.solicitud.promocion.datosPersonaIncompletos")));
            }
        }
        else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                            .getString("ca.solicitud.promocion.maxPersonasInv")));
        }
    }

    private boolean validaNombrePersona(String dato) {
        boolean isFormatOk = false;
        Pattern pat =
                Pattern.compile("[A-Za-z\\s\u00E1\u00E9\u00ED\u00F3\u00FA\u00C1\u00C9\u00CD\u00D3\u00DA\u00DC\u00FC\u00D1\u00F1]{1,200}$");
        Matcher mat = pat.matcher(dato);
        if (mat.matches()) {
            isFormatOk = true;
        }
        return isFormatOk;
    }

    public boolean validaPersonaInvolucrada() {
        boolean banderaNombre = false;
        boolean banderaPaterno = false;
        boolean banderaRfc = false;
        boolean banderaDireccion = false;
        boolean banderaRazonSoc = false;
        boolean persona = false;

        if (getPersonaInvolucradaDTO().getRfc() != null && !getPersonaInvolucradaDTO().getRfc().isEmpty()) {
            banderaRfc = true;
        }
        if (getPersonaInvolucradaDTO().getDireccion() != null && !getPersonaInvolucradaDTO().getDireccion().isEmpty()) {
            banderaDireccion = true;
        }
        if (getPersonaInvolucradaDTO().getNombre() != null && !getPersonaInvolucradaDTO().getNombre().isEmpty()) {
            banderaNombre = true;
        }
        if (getPersonaInvolucradaDTO().getPaterno() != null && !getPersonaInvolucradaDTO().getPaterno().isEmpty()) {
            banderaPaterno = true;
        }
        if (getPersonaInvolucradaDTO().getRazonSocial() != null
                && !getPersonaInvolucradaDTO().getRazonSocial().isEmpty()) {
            banderaRazonSoc = true;
        }
        if (banderaRfc && banderaDireccion) {
            if (getPersonaInvolucradaDTO().getTipoPersona().equals("0") && banderaNombre && banderaPaterno) {
                persona = true;
            }
            else if (banderaRazonSoc) {
                persona = true;
            }
        }
        return persona;
    }

    public void eliminarPersonaInvolucrada(ActionEvent event) {
        for (PersonaInvolucradaDTO personaInv : getPersonasInvolucradasEliminar()) {
            getListaPersonaInvolucrada().remove(personaInv);
            if (!personaInv.isNuevo()) {
                getRegistroSolicitudCALCommonBussines().eliminaPersona(personaInv.getIdPersona());
            }
        }
        setEliminarVisiblePerInv(false);
    }

    public void agregarFraccionDuda(ActionEvent event) {
        if (getFraccionArancelariaDudaDTO().getFraccionArancelaria() != null
                && !getFraccionArancelariaDudaDTO().getFraccionArancelaria().equals("")) {
            if (getListaFraccionAraDuda() != null && getListaFraccionAraDuda().size() < NumerosConstantes.TRES) {
                FraccionArancelariaDudaDTO fraccionDuda = new FraccionArancelariaDudaDTO();
                fraccionDuda.setIdFraccionArancelaria(new Date().getTime()); 

                fraccionDuda.setFraccionArancelaria(getFraccionArancelariaDudaDTO().getFraccionArancelaria());
                fraccionDuda.setNuevo(true);
                listaFraccionAraDuda.add(fraccionDuda);
            }
            else {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                .getString("ca.solicitud.promocion.maxFraccionesDuda")));
            }
            setFraccionArancelariaDudaDTO(new FraccionArancelariaDudaDTO());
        }
        else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                            .getString("ca.solicitud.promocion.required.fraccionDuda")));
        }
    }

    public void eliminarFraccionDuda(ActionEvent event) {
        for (FraccionArancelariaDudaDTO fraccionDuda : getFraccionesDudaEliminar()) {
            getListaFraccionAraDuda().remove(fraccionDuda);
            if (!fraccionDuda.isNuevo()) {
                getRegistroSolicitudCALCommonBussines().eliminaFraccion(fraccionDuda.getIdFraccionArancelaria());
            }
        }
        setEliminarVisibleFraccDuda(false);
    }

    /**
     * 
     * M&eacute;todo para guardar la solicitud.
     * 
     * @throws SolicitudNoGuardadaException
     */
    public void guardarSolicitud() throws SolicitudNoGuardadaException {
        if (getSolicitud().getSolicitante().getRfc() == null || getSolicitud().getSolicitante().getRfc().equals("")) {
            throw new SolicitudNoGuardadaException("Parece haber un error con el servicio, intente mas tarde.");

        }
        getLogger().debug("Entrando a guardar solicitud");
        getSolicitud().setListaPersonasInvolucradas(listaPersonaInvolucrada);
        getSolicitud().setListaPersonasNotificaciones(listaPersonasOirNot);
        getSolicitud().setListaFraccionDuda(listaFraccionAraDuda);
        getLogger().debug("antes de llamada al business");
        setSolicitud(getRegistroSolicitudCALCommonBussines().guardarSolicitud(getSolicitud(), getUserProfile().getRfc()));
        getLogger().debug("antes de preparar documentos");
        prepararDocumentosSolicitud();
        getLogger().debug("despues de preparar documentos");
        String guardado = messages.getString("cal.captura.solicitud.promocion.guardadoSolicitud");
        
        if (manifiesto) {
            getRegistroSolicitudCALCommonBussines().guardarManifiestosSolicitud(getSolicitud());
        }
        

        String[] param = new String[1];
        param[0] = getSolicitud().getIdSolicitud().toString();

        getLogger().debug("termina guardar solicitud");
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "", MessageFormat.format(guardado, (Object[]) param)));
    }

    /**
     * 
     * M&eacute;todo para guardar la solicitud.
     */
    public void guardarSolicitudParcial(ActionEvent actionEvent) {
        try {
            String guardadoParcial = null;

            getSolicitud().setListaPersonasInvolucradas(listaPersonaInvolucrada);
            getSolicitud().setListaPersonasNotificaciones(listaPersonasOirNot);
            getSolicitud().setListaFraccionDuda(listaFraccionAraDuda);

            if (getSolicitud().getIdSolicitud() == null) {
                getLogger().debug("guardadoParcial IdSolicitud= NULL: " + getSolicitud().getIdSolicitud());
                guardadoParcial = messages.getString("cal.captura.solicitud.promocion.primerGuardadoParcial");
            }
            
            if (getSolicitud().getManifiestos() != null && getSolicitud().getManifiestos().size() > 0) {
                ManifiestoDTO manifiestoDTO = getRegistroSolicitudCALCommonBussines().validaManifiestos(getSolicitud());
                if (manifiestoDTO != null){
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "",
                                    messages.getString("cal.manifiesto.error.mensaje")));
                    return;
                }
            }

            setSolicitud(getRegistroSolicitudCALCommonBussines().guardarSolicitud(getSolicitud(),getUserProfile().getRfc()));
            listaPersonaInvolucrada = getSolicitud().getListaPersonasInvolucradas();
            listaPersonasOirNot = getSolicitud().getListaPersonasNotificaciones();

            String[] param = new String[2];
            param[0] = getSolicitud().getIdSolicitud().toString();

            if (guardadoParcial == null) {
                guardadoParcial = messages.getString("cal.captura.solicitud.promocion.guardadoParcial");
                param[1] =
                        diasHabilesHelper.calcularDiasFaltantesSolParcial(getSolicitud().getFechaCreacion()).toString();
            }

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "", MessageFormat.format(guardadoParcial,
                            (Object[]) param)));

        }
        catch (SolicitudNoGuardadaException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
        }
    }

    /**
     * Metodo que prepara los documentos de una solicitud.
     */
    private void prepararDocumentosSolicitud() {
        getLogger().debug("Entrando a prepararDocumentosSolicitud");
        setListaDocumentos(new ArrayList<DocumentoDTO>());
        setListaDocumentosRequeridos(getRegistroSolicitudCALCommonBussines().obtenerDocumentosObligatorios(
                Integer.valueOf(getSolicitud().getTipoTramite()), getSolicitud().getTipoClasificacion()));
        setListaDocumentosOpcionales(getRegistroSolicitudCALCommonBussines().obtenerDocumentosOpcionales(
                Integer.valueOf(getSolicitud().getTipoTramite()), getSolicitud().getTipoClasificacion()));
        setDocumentoSeleccionDataModel(new DocumentoDataModel(getListaDocumentosOpcionales()));
        setDocumentoAdjuntarDataModel(new DocumentoDataModel(getListaDocumentos()));

        // Preselecciona los documentos opcionales que ya se hayan
        // anexado previamente a la solicitud (guardado parcial)
        setDocumentosSeleccionados(getRegistroSolicitudCALCommonBussines().obtenerDocumentosSeleccionados(
                getSolicitud().getIdSolicitud(), getListaDocumentosOpcionales()));
        getLogger().debug("termina prepararDocumentosSolicitud");
    }

    public void prepararFirmaSolicitud() {
        Date fechaFirma = new Date();
        setFirma(new FirmaDTO(fechaFirma));
        setCadenaOriginal(getRegistroSolicitudCALCommonBussines().generaCadenaOriginal(getSolicitud().getIdSolicitud(),
                fechaFirma));
    }

    /**
     * M&eacute;todo para prsentar mensajes de otra pagina.
     */
    public void mensajesRedirect() {
        if (getMessagesRedirect() != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(getMessagesRedirect(), ""));
            setMessagesRedirect(null);
        }
    }

    /**
     * Metodo que carga valores en los combos.
     */
    private void cargarCombosCaptura() {
        setListaGranContribuyente(getRegistroSolicitudCALCommonBussines().obtenerListaSiNo());
        setListaMediosDefensa(getRegistroSolicitudCALCommonBussines().obtenerMediosDeDefensa());
        setListaAutoridad(getRegistroSolicitudCALCommonBussines().obtenerAutoridadesEmisoras());
        setListaPlazo(getRegistroSolicitudCALCommonBussines().obtenerListaSiNo());
        setListaTipoPersona(getRegistroSolicitudCALCommonBussines().obtenerListaTipoPersona());
        setListaTipoClasificacion(getRegistroSolicitudCALCommonBussines().obtenerListaTipoClasificacion());
    }

    public void cargaSentidosResol() {
        setListaSentidosResolucion(getRegistroSolicitudCALCommonBussines().obtenerSentidosResolucion(
                getSolicitud().getClaveMedioDefensa()));
    }

    /**
     * M&eacutetodo para agregar los documentos necesarios a la
     * solicitud, se aniaden al combo y tambi&eacuten para su
     * valiaci&oacuten. seleccionados y requeridos.
     * *********************************************************
     * Modificaci&oacute;n para enviar los documentos opcionales que
     * se agregan a la solicitud, se necesita validarlos
     * desp&uacute;es.
     * 
     * @since 04/04/2012
     * @author antonio.flores
     */
    public boolean prepararDocumentosAnexar() {
        listaGeneralDocumentos = new ArrayList<DocumentoDTO>();
        listaGeneralDocumentos.addAll(getListaDocumentosRequeridos());
        for (DocumentoDTO d : getDocumentosSeleccionados()) {
            listaGeneralDocumentos.add(d);
        }
        setDocumentosCombo(listaGeneralDocumentos);

        setListaDocumentos(getRegistroSolicitudCALCommonBussines().obtenerDocumentosAnexados(
                getSolicitud().getIdSolicitud(), listaGeneralDocumentos));
        
        for (DocumentoDTO doc : getDocumentosCombo()) {
            if(!Runtime.getInstance().isFielRequired()){
                doc= documentosEstres(doc);
                if(getListaDocumentos().size()!=getDocumentosCombo().size()){
                    getListaDocumentos().add(doc);
                }
            }
        }
        
        setDocumentoAdjuntarDataModel(new DocumentoDataModel(getListaDocumentos()));
        return !getListaGeneralDocumentos().isEmpty();
    }
    
    /**
     * Metodo que llena los documentos cuando se inicia en modo estres
     * @param documentosCombo
     * @return listaDocumentos
     */
    public DocumentoDTO documentosEstres(DocumentoDTO documento){
        documento.setIdTipoDocumento(documento.getIdTipoDocumento());
        documento.setNombre("estres");
        documento.setRutaAzure("AERF770708T57.1426036676396");
        documento.setHashDocumento("bdd74a9c7a29820a4001e885bc151190");
        documento.setTamanioArchivo(new Long(NumerosConstantes.NOVENTA_Y_SEIS_MIL_CINCUENTA_Y_UNO));
        documento.setCadenaTamanioArchivo("1");
        documento.setDescripcion(documento.getDescripcion());
        return documento;
    }

    /**
     * M&eacute;todo para eliminar documentos seleccionados
     */
    public void eliminarDocumentos(ActionEvent event) {
        if (getRegistrosEliminar() != null && getRegistrosEliminar().length > 0) {
            for (DocumentoDTO documento : getRegistrosEliminar()) {
                getListaDocumentos().remove(documento);
                if (documento.getIdDocumentoSolicitud() != 0) {
                    getRegistroSolicitudCALCommonBussines().eliminaDocumento(documento.getIdDocumentoSolicitud());
                }
            }
            setEliminarVisible(false);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El (Los) documento(s) ha(n) sido eliminado(s)"));
            // No muestra mensaje de confirmacion, el ECU
            // "21_639_ECU_003AnexarDocumentoSol" no lo especifica
        }
    }

    /**
     * M&eacute;todo para descargar un documento
     */
    public void descargarDocumento(ActionEvent event) {
        DocumentoDTO documento = (DocumentoDTO) event.getComponent().getAttributes().get("documentoParametro");
        File file = new File(documento.getRuta());
        InputStream input = getRegistroSolicitudCALCommonBussines().descargarArchivo(documento);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        setArchivoDescarga((new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()),
                documento.getNombre())));
    }

    /**
     * M&eacute;todo para adjuntar un documento
     */
    public void anexarDocumentoNube() {
        String[] ids = getDocumentoSelected().split("\\|");
        if (ids != null && ids.length > 0) {
            DocumentoDTO documento = obtenerDocumento(getDocumentoSelected());
            if(validaDocumentoAzure(documento,getListaDocumentos())) {
                verificaDocumentoAzure(documento);
            }else {
                getLogger().error("Archivo invalido");
                getLogger().error("Error: "+ documento.getNombreDocumento());
                String error =documento.getNombreDocumento();
                if(error.substring(error.length() -4, error.length() ) .equalsIgnoreCase(".pdf")) {
                    error = "S\u00F3lo es posible adjuntar un archivo a la vez";
                }
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:",error);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                getFlash().put(VistaConstantes.SOLICITUD, getSolicitud());
                getFlash().put(VistaConstantes.DOCUMENTOS_COMBO, getDocumentosCombo());
            }
        }
    }
    
    private boolean validaDocumentoAzure(DocumentoDTO documento, List<DocumentoDTO> list) {
        boolean ban = true;
        if(documento.getRutaAzure() != null  && !documento.getRutaAzure().equals("ERROR") ) {
            getLogger().debug("Ruta Azure: "+ documento.getRutaAzure());
            for(DocumentoDTO doc :list) {
                if(doc.getRutaAzure().equals(documento.getRutaAzure())) {
                    ban = false;
                    break;
                }
            } 
        }else {
            ban = false;
        }
        return ban;
    }
    
    private void verificaDocumentoAzure(DocumentoDTO documento) {
        FacesMessage msg = new FacesMessage("", "Documento adjuntado correctamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        getListaDocumentos().add(documento);
        setDocumentoSelected("");
    }
    

    @Override
    public DocumentoDTO obtenerDocumento(String id) {
        DocumentoDTO documento = null;
        String[] ids = id.split("\\|");
        if (ids != null && ids.length > 0) {
            documento = obtenerDocumentoDTO(Integer.valueOf(ids[0]), ids[1]);
        }
        return documento;

    }

    /**
     * M&eacute;todo para preparar los documentos que se desea anexar
     * a la solicitud.
     * 
     * @param listaGeneralDocumentosRequeridos
     *            ****************************************************
     *            * Modificaci&oacute;n para validar los documentos
     *            requeridos (seleccionados) se para como
     *            par&aacute;metro.
     * @since 04/04/2014
     * @author antonio.flores
     */
    public void guardarDocumentosSolicitud() throws ArchivoNoGuardadoException {
        getFlash().put(VistaConstantes.SOLICITUD, getSolicitud());
        getRegistroSolicitudCALCommonBussines().guardarDocumentosSolicitud(getSolicitud(), getListaDocumentos(),
                getListaGeneralDocumentos(), getUserProfile().getRfc());
    }

    /**
     * Ejecuta el guardado parcial de documentos y muestra mensaje de
     * confirmacion
     * 
     * @param actionEvent
     */
    public void guardarDocumentosParcial() {

        setListaDocumentos(getRegistroSolicitudCALCommonBussines().guardarDocumentosParcialSolicitud(getSolicitud(),
                getListaDocumentos(), getUserProfile().getRfc()));
        setDocumentoAdjuntarDataModel(new DocumentoDataModel(getListaDocumentos()));

        String guardadoParcial = messages.getString("cal.captura.solicitud.promocion.guardadoParcial");
        String[] param = new String[2];
        param[0] = getSolicitud().getIdSolicitud().toString();
        param[1] = diasHabilesHelper.calcularDiasFaltantesSolParcial(getSolicitud().getFechaCreacion()).toString();

        getFlash().put(VistaConstantes.SOLICITUD, getSolicitud());

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "", MessageFormat
                        .format(guardadoParcial, (Object[]) param)));
    }

    public void guardarDocumentosParcialNext() {

        setListaDocumentos(getRegistroSolicitudCALCommonBussines().guardarDocumentosParcialSolicitud(getSolicitud(),
                getListaDocumentos(), getUserProfile().getRfc()));
        setDocumentoAdjuntarDataModel(new DocumentoDataModel(getListaDocumentos()));

        String[] param = new String[2];
        param[0] = getSolicitud().getIdSolicitud().toString();
        param[1] = diasHabilesHelper.calcularDiasFaltantesSolParcial(getSolicitud().getFechaCreacion()).toString();

        getFlash().put(VistaConstantes.SOLICITUD, getSolicitud());

    }
    
    String sNumSerie;
    private boolean validarAra() throws SgiARAException {
    	setUserID(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userID")
				.toString());
    	sNumSerie = getAraValidadorHelper().getNumSerie(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("numeroSerie"));

    	return getAraValidadorHelper().validarRfcNumSerie(sNumSerie, getUserProfile().getRfc())
				&& getAraValidadorHelper().getEdoCertificado(sNumSerie);
    }
    
    private void generaFechaFirma() {
        setFirmaDigital(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                .get("firmaDigital").toString());
        Date fechaFirma = new Date();
        getFirma().setFechaFirma(acusesFaltantes ? fechaRecepcionFaltantes : fechaFirma);
        getFirma().setSello(getFirmaDigital());
        getFirma().setRfcUsuario(getUserProfile().getRfc());
        getFirma().setCveRol(getUserProfile().getRol());
        getFirma().setCadenaOriginal(getCadenaOriginal());
        getFirma().setCveProceso(TipoProcesoFirma.REG_PROM.getClave());
        getFirma().setCertificado(sNumSerie);
    }
    
	public String firmar() {
		String[] param = new String[NumerosConstantes.CINCO];
		try {
			if (validarAra()) {
				generaFechaFirma();
				if (acusesFaltantes) {
					return firmarFaltantes();
				} else {
					return firmarSinFaltantes();
				}
			} else {
				param[0] = getUserProfile().getRfc();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "",
						MessageFormat.format(AraConstantes.MENSAJE_LLAVE_SERIE_INCORRECTO, getUserProfile().getRfc())));
				return RegistroSolicitudConstants.FIRMAR;
			}
		} catch (SgiARAException ex) {
			 getLogger().error("Ocurrio un error al firmar RegistroSolicitudCALCommonController.firmar {0}", ex);
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
		} catch (BusinessException e) {
			getLogger().error("Ocurrio un error RegistroSolicitudCALCommonController.firmar {0}", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
            getFlash().put(VistaConstantes.SOLICITUD, getSolicitud());
		} catch (Exception e) {
			getLogger().error("Ocurrio un error RegistroSolicitudCALCommonController.firmar {0}", e);
            getFlash().put(VistaConstantes.SOLICITUD, getSolicitud());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurri\u00F3 un error al firmar la promoci\u00F3n", ""));
		}
		
		return RegistroSolicitudConstants.FIRMAR;

	}
    
	/* Firmar Acuses Faltantes */
	private String firmarFaltantes() throws SgiARAException, BusinessException {
		/* generar Asunto */
		String numAsunto = numeroAsuntoFaltantes;
		FirmaDTO firmaSelladora = getRegistroSolicitudCALCommonBussines().obtenSelloPromocionSIAT(numAsunto,
				getSolicitud().getIdSolicitud(), getFirma().getFechaFirma());
		DatosBandejaTareaDTO datosBandejaTareaDTO = new DatosBandejaTareaDTO();
		datosBandejaTareaDTO.setNumeroAsunto(numAsunto);
		datosBandejaTareaDTO.setIdSolicitud(getSolicitud().getIdSolicitud());
		datosBandejaTareaDTO.setRfcSolicitante(getUserProfile().getRfc());
		datosBandejaTareaDTO.setFechaAsignacion(getFirma().getFechaFirma());
		List<Long> idsDoc = getGenerarDocumentosHelper().generarDocumentosPromocionCAL(datosBandejaTareaDTO,
				TipoAcuse.RECPROM.getClave(), getFirma(), firmaSelladora.getCadenaOriginal(), firmaSelladora.getSello(),
				getRegistroSolicitudCALCommonBussines()
						.tieneDocumentosAnexados(getSolicitud().getIdSolicitud().toString()),
				solicitud.getTipoRolContribuyente(), solicitud.getEstadoContribuyente());
		getCapturaSolicitudBussines().firmarDocumentos(getSolicitud().getIdSolicitud(), getFirma());
		String aviso = "Se muestran los acuses correctamente.";
		resultadoAcuses(numAsunto,idsDoc,aviso);
		return LoginConstante.DESCARGA_DOCUMENTO;
    }
    
	private void resultadoAcuses(String numAsunto, List<Long> idsDoc,String aviso) {
		descargarDocumentoController.getDatosBandejaTareaDTO().setNumeroAsunto(numAsunto);
        descargarDocumentoController.obtenerDocumentosByIdDoc(idsDoc);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", aviso));
	}
	
    /**
     * M&eacute;todo para firmar la solicitud
     * @throws BusinessException 
     */
	
	private String firmarSinFaltantes() throws BusinessException, Exception {
		Tarea tarea = getRegistroSolicitudCALCommonBussines().firmaSolicitud(getSolicitud(),
				getUserProfile().getRfc(), getFirma());
		String numAsunto = tarea.getNumeroAsunto();
		
		if (numAsunto == null) {
			throw new Exception("Ocurrio un error al generar el asunto");
		}
		FirmaDTO firmaSelladora = getRegistroSolicitudCALCommonBussines().obtenSelloPromocionSIAT(numAsunto,
				getSolicitud().getIdSolicitud(), getFirma().getFechaFirma());
		DatosBandejaTareaDTO datosBandejaTareaDTO = new DatosBandejaTareaDTO();
		datosBandejaTareaDTO.setNumeroAsunto(numAsunto);
		datosBandejaTareaDTO.setIdSolicitud(getSolicitud().getIdSolicitud());
		datosBandejaTareaDTO.setRfcSolicitante(getUserProfile().getRfc());
		List<Long> idsDoc = getGenerarDocumentosHelper().generarDocumentosPromocionCAL(datosBandejaTareaDTO,
				TipoAcuse.RECPROM.getClave(), getFirma(), firmaSelladora.getCadenaOriginal(),
				firmaSelladora.getSello(),
				getRegistroSolicitudCALCommonBussines()
						.tieneDocumentosAnexados(getSolicitud().getIdSolicitud().toString()),
				solicitud.getTipoRolContribuyente(), solicitud.getEstadoContribuyente());
		getCapturaSolicitudBussines().firmarDocumentos(getSolicitud().getIdSolicitud(), getFirma());
		reintentaGeneracionAcuses(idsDoc, datosBandejaTareaDTO,tarea, firmaSelladora);
		
		return LoginConstante.DESCARGA_DOCUMENTO;
	}
	
	private void reintentaGeneracionAcuses(List<Long> idsDoc,DatosBandejaTareaDTO datosBandejaTareaDTO,Tarea tarea, FirmaDTO firmaSelladora) throws BusinessException {
		//Validar documentos
		Boolean banDocumento = Boolean.TRUE;
		Boolean banTarea = Boolean.TRUE;
		BitacoraAU bitacora = new BitacoraAU();
		bitacora.setFechaAccion(new Date());
		bitacora.setIdRealizadoPor(tarea.getNumeroAsunto()!=null ? tarea.getNumeroAsunto() : "Sin Asunto");
		bitacora.setIdAplicadoA(AccionesBitacoraConstants.ERROR_REGISTRO_CAL);
		getLogger().debug("Se validan los documento");
		if(idsDoc.isEmpty()) {
			//Bitacora no se genraron los acuses
			getLogger().error("No se generaron acuses para el asunto:{}",tarea.getNumeroAsunto());
			banDocumento = Boolean.FALSE;
			bitacora.setIdAccion(AccionesBitacora.ERROR_ACUSES.getClave());
			bitacora.setDescripcion("Ocurrio un error en la generación del acuse y se realiza el reintento:"+reintentos);
			getCapturaSolicitudBussines().guardarBitacora(bitacora);
			getCapturaSolicitudBussines().cambiarEstadofirmarDocumentos(getSolicitud().getIdSolicitud(),EstadoDocumento.FIRMADO.getClave(), EstadoDocumento.ANEXADO.getClave());
			idsDoc = getGenerarDocumentosHelper().generarDocumentosPromocionCAL(datosBandejaTareaDTO,
					TipoAcuse.RECPROM.getClave(), getFirma(), firmaSelladora.getCadenaOriginal(),
					firmaSelladora.getSello(),
					getRegistroSolicitudCALCommonBussines()
							.tieneDocumentosAnexados(getSolicitud().getIdSolicitud().toString()),
					solicitud.getTipoRolContribuyente(), solicitud.getEstadoContribuyente());
			if(!idsDoc.isEmpty()) {
				getCapturaSolicitudBussines().cambiarEstadofirmarDocumentos(getSolicitud().getIdSolicitud(),EstadoDocumento.ANEXADO.getClave(), EstadoDocumento.FIRMADO.getClave());
				banDocumento = Boolean.TRUE;
				getLogger().info("Se generaron acuses para el asunto:{}",tarea.getNumeroAsunto());
			}
		}
				
		
		//validar tareas
		List<Tarea> tareas = getCapturaSolicitudBussines().obtenerTareasPorNumAsunto(tarea.getNumeroAsunto());
		if(tareas.isEmpty()) {
			banTarea = Boolean.FALSE;
			getLogger().error("No se guardo la tarea:{}",tarea.toString());
			bitacora.setIdAccion(AccionesBitacora.ERROR_TAREA.getClave());
			bitacora.setDescripcion("Ocurrio un error en la generación de la tarea y se realiza el reintento:"+reintentos);
			getCapturaSolicitudBussines().guardarBitacora(bitacora);
			tarea.setIdTarea(null);
			getCapturaSolicitudBussines().regenerarTarea(tarea);
			tareas = getCapturaSolicitudBussines().obtenerTareasPorNumAsunto(tarea.getNumeroAsunto());
			if(!tareas.isEmpty()) {
				banTarea = Boolean.TRUE;
				getLogger().error("Se guardo la tarea:{}",tarea.toString());
			}
			
		}
		
		if(reintentos==NumerosConstantes.TRES) {
			getLogger().error("Reintentos:{} para el asunto:{} se genearon acuses:{} se creo tarea:{}",reintentos,tarea.getNumeroAsunto(),banDocumento, banTarea);
			banDocumento = Boolean.TRUE;
			banTarea = Boolean.TRUE;
		}
		
		if(banDocumento && banTarea) {
			getLogger().info("Reintentos:{} para el asunto:{}",reintentos,tarea.getNumeroAsunto());
			getLogger().info("Acuses generados:{} para el asunto:{}",idsDoc.size(),tarea.getNumeroAsunto());
			String aviso = "Tu Promoci\u00F3n ha sido registrada con el siguiente  n\u00FAmero de Asunto " + tarea.getNumeroAsunto();
			resultadoAcuses(tarea.getNumeroAsunto(),idsDoc,aviso);	
		}else {
			getLogger().error("Reintentos:{} para el asunto:{} se genearon acuses:{} se creo tarea:{}",reintentos,tarea.getNumeroAsunto(),banDocumento, banTarea);
			reintentos=reintentos+NumerosConstantes.UNO;
			reintentaGeneracionAcuses(idsDoc, datosBandejaTareaDTO,tarea, firmaSelladora);
		}	
	}
	
	
    public void rowSelectCheckbox(SelectEvent event) {
        setEliminarVisiblePerOir(true);
    }

    public void rowUnselectCheckbox(UnselectEvent event) {
        if (getPersonasONEliminar().length < 1) {
            setEliminarVisiblePerOir(false);
        }
    }

    public void rowSelectCheckboxDocumentos(SelectEvent event) {
        setEliminarVisible(true);
    }

    public void rowUnselectCheckboxDocumentos(UnselectEvent event) {
        if (getRegistrosEliminar().length < 1) {
            setEliminarVisible(false);
        }
    }

    public void rowSelectCheckboxPerInv(SelectEvent event) {
        setEliminarVisiblePerInv(true);
    }

    public void rowUnselectCheckboxPerInv(UnselectEvent event) {
        if (getPersonasInvolucradasEliminar().length < 1) {
            setEliminarVisiblePerInv(false);
        }
    }

    public void rowSelectCheckboxFraccDuda(SelectEvent event) {
        if (getFraccionesDudaEliminar() != null && getFraccionesDudaEliminar().length > 0) {
            setEliminarVisibleFraccDuda(true);
        }
    }

    public void rowUnselectCheckboxFraccDuda(UnselectEvent event) {
        if (getFraccionesDudaEliminar().length < 1) {
            setEliminarVisibleFraccDuda(false);
        }
    }

    private void inicializaTipoPersonaInvolucrada() {
        tipoPer = true;
        // Persona fisica, por default
        tipoPerMor = false;
        getPersonaInvolucradaDTO().setTipoPersona("0"); 
                                                        // Persona
                                                        // fisica
    }

    public abstract RegistroSolicitudCALCommonBussines getRegistroSolicitudCALCommonBussines();

    /**
     * @return the solicitud
     */
    public SolicitudCALDTO getSolicitud() {
        return solicitud;
    }

    /**
     * @param solicitud
     *            the solicitud to set
     */
    public void setSolicitud(SolicitudCALDTO solicitud) {
        this.solicitud = solicitud;
    }

    /**
     * @return the personaOirNotificacionesDTO
     */
    public PersonaOirNotificacionesDTO getPersonaOirNotificacionesDTO() {
        return personaOirNotificacionesDTO;
    }

    /**
     * @param personaOirNotificacionesDTO
     *            the personaOirNotificacionesDTO to set
     */
    public void setPersonaOirNotificacionesDTO(PersonaOirNotificacionesDTO personaOirNotificacionesDTO) {
        this.personaOirNotificacionesDTO = personaOirNotificacionesDTO;
    }

    /**
     * @return the listaPersonasOirNot
     */
    public List<PersonaOirNotificacionesDTO> getListaPersonasOirNot() {
        return listaPersonasOirNot;
    }

    /**
     * @param listaPersonasOirNot
     *            the listaPersonasOirNot to set
     */
    public void setListaPersonasOirNot(List<PersonaOirNotificacionesDTO> listaPersonasOirNot) {
        this.listaPersonasOirNot = listaPersonasOirNot;
    }

    /**
     * @return the personaOirNotificacionesDataModel
     */
    public PersonaOirNotificacionesDataModel getPersonaOirNotificacionesDataModel() {
        return personaOirNotificacionesDataModel;
    }

    /**
     * @param personaOirNotificacionesDataModel
     *            the personaOirNotificacionesDataModel to set
     */
    public void
            setPersonaOirNotificacionesDataModel(PersonaOirNotificacionesDataModel personaOirNotificacionesDataModel) {
        this.personaOirNotificacionesDataModel = personaOirNotificacionesDataModel;
    }

    /**
     * @return the personaInvolucradaDTO
     */
    public PersonaInvolucradaDTO getPersonaInvolucradaDTO() {
        return personaInvolucradaDTO;
    }

    /**
     * @param personaInvolucradaDTO
     *            the personaInvolucradaDTO to set
     */
    public void setPersonaInvolucradaDTO(PersonaInvolucradaDTO personaInvolucradaDTO) {
        this.personaInvolucradaDTO = personaInvolucradaDTO;
    }

    /**
     * @return the listaPersonaInvolucrada
     */
    public List<PersonaInvolucradaDTO> getListaPersonaInvolucrada() {
        return listaPersonaInvolucrada;
    }

    /**
     * @param listaPersonaInvolucrada
     *            the listaPersonaInvolucrada to set
     */
    public void setListaPersonaInvolucrada(List<PersonaInvolucradaDTO> listaPersonaInvolucrada) {
        this.listaPersonaInvolucrada = listaPersonaInvolucrada;
    }

    /**
     * @return the personaInvolucradaDataModel
     */
    public PersonaInvolucradaDataModel getPersonaInvolucradaDataModel() {
        return personaInvolucradaDataModel;
    }

    /**
     * @param personaInvolucradaDataModel
     *            the personaInvolucradaDataModel to set
     */
    public void setPersonaInvolucradaDataModel(PersonaInvolucradaDataModel personaInvolucradaDataModel) {
        this.personaInvolucradaDataModel = personaInvolucradaDataModel;
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
     * @return the listaDocumentosOpcionales
     */
    public List<DocumentoDTO> getListaDocumentosOpcionales() {
        return listaDocumentosOpcionales;
    }

    /**
     * @param listaDocumentosOpcionales
     *            the listaDocumentosOpcionales to set
     */
    public void setListaDocumentosOpcionales(List<DocumentoDTO> listaDocumentosOpcionales) {
        this.listaDocumentosOpcionales = listaDocumentosOpcionales;
    }

    /**
     * @return the listaDocumentosRequeridos
     */
    public List<DocumentoDTO> getListaDocumentosRequeridos() {
        return listaDocumentosRequeridos;
    }

    /**
     * @param listaDocumentosRequeridos
     *            the listaDocumentosRequeridos to set
     */
    public void setListaDocumentosRequeridos(List<DocumentoDTO> listaDocumentosRequeridos) {
        this.listaDocumentosRequeridos = listaDocumentosRequeridos;
    }

    /**
     * @return the documentoSeleccionDataModel
     */
    public DocumentoDataModel getDocumentoSeleccionDataModel() {
        return documentoSeleccionDataModel;
    }

    /**
     * @param documentoSeleccionDataModel
     *            the documentoSeleccionDataModel to set
     */
    public void setDocumentoSeleccionDataModel(DocumentoDataModel documentoSeleccionDataModel) {
        this.documentoSeleccionDataModel = documentoSeleccionDataModel;
    }

    /**
     * @return the documentosSeleccionados
     */
    public DocumentoDTO[] getDocumentosSeleccionados() {
        if (documentosSeleccionados != null) {
            DocumentoDTO[] arreglo = new DocumentoDTO[documentosSeleccionados.length];
            for (int i = 0; i < documentosSeleccionados.length; i++) {
                DocumentoDTO doc = documentosSeleccionados[i];
                arreglo[i] = doc;
            }
            return arreglo;
        }
        else {
            return null;
        }
    }

    /**
     * @param documentosSeleccionados
     *            the documentosSeleccionados to set
     */
    public void setDocumentosSeleccionados(DocumentoDTO[] documentosSeleccionadosArray) {
        if (documentosSeleccionadosArray != null) {
            DocumentoDTO[] documentosSeleccionadosLocal = documentosSeleccionadosArray.clone();
            DocumentoDTO[] arreglo = new DocumentoDTO[documentosSeleccionadosLocal.length];
            for (int i = 0; i < documentosSeleccionadosLocal.length; i++) {
                DocumentoDTO doc = documentosSeleccionadosLocal[i];
                arreglo[i] = doc;
            }
            this.documentosSeleccionados = arreglo;
        }
        else {
            this.documentosSeleccionados = null;
        }
    }

    /**
     * @return the listaDocumentos
     */
    public List<DocumentoDTO> getListaDocumentos() {
        return listaDocumentos;
    }

    /**
     * @param listaDocumentos
     *            the listaDocumentos to set
     */
    public void setListaDocumentos(List<DocumentoDTO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    /**
     * @return the documentosCombo
     */
    public List<DocumentoDTO> getDocumentosCombo() {
        return documentosCombo;
    }

    /**
     * @param documentosCombo
     *            the documentosCombo to set
     */
    public void setDocumentosCombo(List<DocumentoDTO> documentosCombo) {
        this.documentosCombo = documentosCombo;
    }

    /**
     * @return the documentoAdjuntarDataModel
     */
    public DocumentoDataModel getDocumentoAdjuntarDataModel() {
        return documentoAdjuntarDataModel;
    }

    /**
     * @param documentoAdjuntarDataModel
     *            the documentoAdjuntarDataModel to set
     */
    public void setDocumentoAdjuntarDataModel(DocumentoDataModel documentoAdjuntarDataModel) {
        this.documentoAdjuntarDataModel = documentoAdjuntarDataModel;
    }

    /**
     * @return the documentoSelected
     */
    public String getDocumentoSelected() {
        return documentoSelected;
    }

    /**
     * @param documentoSelected
     *            the documentoSelected to set
     */
    public void setDocumentoSelected(String documentoSelected) {
        this.documentoSelected = documentoSelected;
    }

    /**
     * @return the archivoDescarga
     */
    public DefaultStreamedContent getArchivoDescarga() {
        return archivoDescarga;
    }

    /**
     * @param archivoDescarga
     *            the archivoDescarga to set
     */
    public void setArchivoDescarga(DefaultStreamedContent archivoDescarga) {
        this.archivoDescarga = archivoDescarga;
    }

    /**
     * @return the registrosEliminar
     */
    public DocumentoDTO[] getRegistrosEliminar() {
        return registrosEliminar != null ? (DocumentoDTO[]) registrosEliminar.clone() : null;
    }

    /**
     * @param registrosEliminar
     *            the registrosEliminar to set
     */
    public void setRegistrosEliminar(DocumentoDTO[] registrosEliminarArray) {
        if (registrosEliminarArray != null) {
            this.registrosEliminar = registrosEliminarArray.clone();
        }
        else {
            this.registrosEliminar = null;
        }
    }

    /**
     * @return the messagesRedirect
     */
    public String getMessagesRedirect() {
        return messagesRedirect;
    }

    /**
     * @param messagesRedirect
     *            the messagesRedirect to set
     */
    public void setMessagesRedirect(String messagesRedirect) {
        this.messagesRedirect = messagesRedirect;
    }

    /**
     * @return the listaGranContribuyente
     */
    public List<CatalogoDTO> getListaGranContribuyente() {
        return listaGranContribuyente;
    }

    /**
     * @param listaGranContribuyente
     *            the listaGranContribuyente to set
     */
    public void setListaGranContribuyente(List<CatalogoDTO> listaGranContribuyente) {
        this.listaGranContribuyente = listaGranContribuyente;
    }

    /**
     * @return the personaOirNotificacionesDTOSelect
     */
    public PersonaOirNotificacionesDTO getPersonaOirNotificacionesDTOSelect() {
        return personaOirNotificacionesDTOSelect;
    }

    /**
     * @param personaOirNotificacionesDTOSelect
     *            the personaOirNotificacionesDTOSelect to set
     */
    public void setPersonaOirNotificacionesDTOSelect(PersonaOirNotificacionesDTO personaOirNotificacionesDTOSelect) {
        this.personaOirNotificacionesDTOSelect = personaOirNotificacionesDTOSelect;
    }

    /**
     * @return the firma
     */
    public FirmaDTO getFirma() {
        return firma;
    }

    /**
     * @param firma
     *            the firma to set
     */
    public void setFirma(FirmaDTO firma) {
        this.firma = firma;
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

    /**
     * @return the firmaDigital
     */
    public String getFirmaDigital() {
        return firmaDigital;
    }

    /**
     * @param firmaDigital
     *            the firmaDigital to set
     */
    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }

    /**
     * @return the fraccionArancelariaDudaDTO
     */
    public FraccionArancelariaDudaDTO getFraccionArancelariaDudaDTO() {
        return fraccionArancelariaDudaDTO;
    }

    /**
     * @param fraccionArancelariaDudaDTO
     *            the fraccionArancelariaDudaDTO to set
     */
    public void setFraccionArancelariaDudaDTO(FraccionArancelariaDudaDTO fraccionArancelariaDudaDTO) {
        this.fraccionArancelariaDudaDTO = fraccionArancelariaDudaDTO;
    }

    /**
     * @return the listaFraccionAraDuda
     */
    public List<FraccionArancelariaDudaDTO> getListaFraccionAraDuda() {
        return listaFraccionAraDuda;
    }

    /**
     * @param listaFraccionAraDuda
     *            the listaFraccionAraDuda to set
     */
    public void setListaFraccionAraDuda(List<FraccionArancelariaDudaDTO> listaFraccionAraDuda) {
        this.listaFraccionAraDuda = listaFraccionAraDuda;
    }

    /**
     * @return the fraccionArancelariaDudaDataModel
     */
    public FraccionArancelariaDudaDataModel getFraccionArancelariaDudaDataModel() {
        return fraccionArancelariaDudaDataModel;
    }

    /**
     * @param fraccionArancelariaDudaDataModel
     *            the fraccionArancelariaDudaDataModel to set
     */
    public void setFraccionArancelariaDudaDataModel(FraccionArancelariaDudaDataModel fraccionArancelariaDudaDataModel) {
        this.fraccionArancelariaDudaDataModel = fraccionArancelariaDudaDataModel;
    }

    /**
     * @return the listaAutoridad
     */
    public List<CatalogoDTO> getListaAutoridad() {
        return listaAutoridad;
    }

    /**
     * @param listaAutoridad
     *            the listaAutoridad to set
     */
    public void setListaAutoridad(List<CatalogoDTO> listaAutoridad) {
        this.listaAutoridad = listaAutoridad;
    }

    /**
     * @return the listaMediosDefensa
     */
    public List<CatalogoDTO> getListaMediosDefensa() {
        return listaMediosDefensa;
    }

    /**
     * @param listaMediosDefensa
     *            the listaMediosDefensa to set
     */
    public void setListaMediosDefensa(List<CatalogoDTO> listaMediosDefensa) {
        this.listaMediosDefensa = listaMediosDefensa;
    }

    /**
     * @return the listaSentidosResolucion
     */
    public List<CatalogoDTO> getListaSentidosResolucion() {
        return listaSentidosResolucion;
    }

    /**
     * @param listaSentidosResolucion
     *            the listaSentidosResolucion to set
     */
    public void setListaSentidosResolucion(List<CatalogoDTO> listaSentidosResolucion) {
        this.listaSentidosResolucion = listaSentidosResolucion;
    }

    /**
     * @return the listaPlazo
     */
    public List<CatalogoDTO> getListaPlazo() {
        return listaPlazo;
    }

    /**
     * @param listaPlazo
     *            the listaPlazo to set
     */
    public void setListaPlazo(List<CatalogoDTO> listaPlazo) {
        this.listaPlazo = listaPlazo;
    }

    public List<CatalogoDTO> getListaTipoClasificacion() {
        return listaTipoClasificacion;
    }

    public void setListaTipoClasificacion(List<CatalogoDTO> listaTipoClasificacion) {
        this.listaTipoClasificacion = listaTipoClasificacion;
    }

    /**
     * @return the listaTipoPersona
     */
    public List<CatalogoDTO> getListaTipoPersona() {
        return listaTipoPersona;
    }

    /**
     * @param listaTipoPersona
     *            the listaTipoPersona to set
     */
    public void setListaTipoPersona(List<CatalogoDTO> listaTipoPersona) {
        this.listaTipoPersona = listaTipoPersona;
    }

    /**
     * @return the personasONEliminar
     */
    public PersonaOirNotificacionesDTO[] getPersonasONEliminar() {
        return personasONEliminar != null ? (PersonaOirNotificacionesDTO[]) personasONEliminar.clone() : null;
    }

    /**
     * @param personasONEliminar
     *            the personasONEliminar to set
     */
    public void setPersonasONEliminar(PersonaOirNotificacionesDTO[] personasONEliminarArray) {
        if (personasONEliminarArray != null) {
            this.personasONEliminar = personasONEliminarArray.clone();
        }
        else {
            this.personasONEliminar = null;
        }
    }

    /**
     * @return the personasInvolucradasEliminar
     */
    public PersonaInvolucradaDTO[] getPersonasInvolucradasEliminar() {
        return personasInvolucradasEliminar != null ? (PersonaInvolucradaDTO[]) personasInvolucradasEliminar.clone()
                : null;
    }

    /**
     * @param personasInvolucradasEliminar
     *            the personasInvolucradasEliminar to set
     */
    public void setPersonasInvolucradasEliminar(PersonaInvolucradaDTO[] personasInvolucradasEliminarArray) {
        if (personasInvolucradasEliminarArray != null) {
            this.personasInvolucradasEliminar = personasInvolucradasEliminarArray.clone();
        }
        else {
            this.personasInvolucradasEliminar = null;
        }
    }

    /**
     * @return the fraccionesDudaEliminar
     */
    public FraccionArancelariaDudaDTO[] getFraccionesDudaEliminar() {
        return fraccionesDudaEliminar != null ? (FraccionArancelariaDudaDTO[]) fraccionesDudaEliminar.clone() : null;
    }

    /**
     * @param fraccionesDudaEliminar
     *            the fraccionesDudaEliminar to set
     */
    public void setFraccionesDudaEliminar(FraccionArancelariaDudaDTO[] fraccionesDudaEliminarArray) {
        if (fraccionesDudaEliminarArray != null) {
            this.fraccionesDudaEliminar = fraccionesDudaEliminarArray.clone();
        }
        else {
            this.fraccionesDudaEliminar = null;
        }
    }

    /**
     * @return the tipoPer
     */
    public boolean isTipoPer() {
        return tipoPer;
    }

    /**
     * @param tipoPer
     *            the tipoPer to set
     */
    public void setTipoPer(boolean tipoPer) {
        this.tipoPer = tipoPer;
    }

    /**
     * @return the tipoPerMor
     */
    public boolean isTipoPerMor() {
        return tipoPerMor;
    }

    /**
     * @param tipoPerMor
     *            the tipoPerMor to set
     */
    public void setTipoPerMor(boolean tipoPerMor) {
        this.tipoPerMor = tipoPerMor;
    }

    /**
     * @return the hechosCirc
     */
    public boolean isHechosCirc() {
        return hechosCirc;
    }

    /**
     * @param hechosCirc
     *            the hechosCirc to set
     */
    public void setHechosCirc(boolean hechosCirc) {
        this.hechosCirc = hechosCirc;
    }

    /**
     * @return the mediosDefensa
     */
    public boolean isMediosDefensa() {
        return mediosDefensa;
    }

    /**
     * @param mediosDefensa
     *            the mediosDefensa to set
     */
    public void setMediosDefensa(boolean mediosDefensa) {
        this.mediosDefensa = mediosDefensa;
    }

    /**
     * @return the sujetoEjercicio
     */
    public boolean isSujetoEjercicio() {
        return sujetoEjercicio;
    }

    /**
     * @param sujetoEjercicio
     *            the sujetoEjercicio to set
     */
    public void setSujetoEjercicio(boolean sujetoEjercicio) {
        this.sujetoEjercicio = sujetoEjercicio;
    }

    /**
     * @return the clasificacionArancelaria
     */
    public boolean isClasificacionArancelaria() {
        return clasificacionArancelaria;
    }

    /**
     * @param clasificacionArancelaria
     *            the clasificacionArancelaria to set
     */
    public void setClasificacionArancelaria(boolean clasificacionArancelaria) {
        this.clasificacionArancelaria = clasificacionArancelaria;
    }

    public boolean isTipoClasificacionArancelariaCombo() {
        return tipoClasificacionArancelariaCombo;
    }

    public void setTipoClasificacionArancelariaCombo(boolean tipoClasificacionArancelariaCombo) {
        this.tipoClasificacionArancelariaCombo = tipoClasificacionArancelariaCombo;
    }

    /**
     * @return the maxDate
     */
    public Date getMaxDate() {
        return maxDate != null ? (Date) maxDate.clone() : null;
    }

    /**
     * @param maxDate
     *            the maxDate to set
     */
    public void setMaxDate(Date maxDate) {
        if (maxDate != null) {
            this.maxDate = (Date) maxDate.clone();
        }
        else {
            this.maxDate = null;
        }
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
     * M&eacute;todo habilitar boton de agregar
     */
    public void cambioVisible() {
        if (getDocumentoSelected() != null) {
            setAgregarVisible(true);
        }
        else {
            setAgregarVisible(false);
        }
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
     * @return the eliminarVisiblePerInv
     */
    public boolean isEliminarVisiblePerInv() {
        return eliminarVisiblePerInv;
    }

    /**
     * @param eliminarVisiblePerInv
     *            the eliminarVisiblePerInv to set
     */
    public void setEliminarVisiblePerInv(boolean eliminarVisiblePerInv) {
        this.eliminarVisiblePerInv = eliminarVisiblePerInv;
    }

    public boolean isEliminarVisibleFraccDuda() {
        return eliminarVisibleFraccDuda;
    }

    public void setEliminarVisibleFraccDuda(boolean eliminarVisibleFraccDuda) {
        this.eliminarVisibleFraccDuda = eliminarVisibleFraccDuda;
    }

    public boolean isEliminarVisiblePerOir() {
        return eliminarVisiblePerOir;
    }

    public void setEliminarVisiblePerOir(boolean eliminarVisiblePerOir) {
        this.eliminarVisiblePerOir = eliminarVisiblePerOir;
    }

    public FormatoDatosHelper getFormatoDatosHelper() {
        return formatoDatosHelper;
    }

    public void setFormatoDatosHelper(FormatoDatosHelper formatoDatosHelper) {
        this.formatoDatosHelper = formatoDatosHelper;
    }

    /**
     * @return the requeridoPrincipal
     */
    public boolean isRequeridoPrincipal() {
        return requeridoPrincipal;
    }

    /**
     * @param requeridoPrincipal
     *            the requeridoPrincipal to set
     */
    public void setRequeridoPrincipal(boolean requeridoPrincipal) {
        this.requeridoPrincipal = requeridoPrincipal;
    }

    /**
     * @return the requerido
     */
    public boolean isRequerido() {
        return requerido;
    }

    /**
     * @param requerido
     *            the requerido to set
     */
    public void setRequerido(boolean requerido) {
        this.requerido = requerido;
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
     * @return the capturaSolicitudBussines
     */
    public CapturaSolicitudBussines getCapturaSolicitudBussines() {
        return capturaSolicitudBussines;
    }

    /**
     * @param capturaSolicitudBussines
     *            the capturaSolicitudBussines to set
     */
    public void setCapturaSolicitudBussines(CapturaSolicitudBussines capturaSolicitudBussines) {
        this.capturaSolicitudBussines = capturaSolicitudBussines;
    }

    /**
     * @return the descargarDocumentoController
     */
    public DescargarDocumentoController getDescargarDocumentoController() {
        return descargarDocumentoController;
    }

    /**
     * @param descargarDocumentoController
     *            the descargarDocumentoController to set
     */
    public void setDescargarDocumentoController(DescargarDocumentoController descargarDocumentoController) {
        this.descargarDocumentoController = descargarDocumentoController;
    }

    /**
     * @return the consultasAutorizacionesCALBusiness
     */
    public ConsultasAutorizacionesCALBusiness getConsultasAutorizacionesCALBusiness() {
        return consultasAutorizacionesCALBusiness;
    }

    /**
     * @param consultasAutorizacionesCALBusiness
     *            the consultasAutorizacionesCALBusiness to set
     */
    public void setConsultasAutorizacionesCALBusiness(
            ConsultasAutorizacionesCALBusiness consultasAutorizacionesCALBusiness) {
        this.consultasAutorizacionesCALBusiness = consultasAutorizacionesCALBusiness;
    }

    /**
     * @return the mensajeAviso
     */
    public String getMensajeAviso() {
        return mensajeAviso;
    }

    /**
     * @param mensajeAviso
     *            the mensajeAviso to set
     */
    public void setMensajeAviso(String mensajeAviso) {
        this.mensajeAviso = mensajeAviso;
    }

    public boolean isAgregarVisible() {
        return agregarVisible;
    }

    public void setAgregarVisible(boolean agregarVisible) {
        this.agregarVisible = agregarVisible;
    }

    public boolean isBanderaRazonSocial() {
        return banderaRazonSocial;
    }

    public void setBanderaRazonSocial(boolean banderaRazonSocial) {
        this.banderaRazonSocial = banderaRazonSocial;
    }

    /**
     * @return the diasHabilesHelper
     */
    public DiasHabilesHelper getDiasHabilesHelper() {
        return diasHabilesHelper;
    }

    /**
     * @param diasHabilesHelper
     *            the diasHabilesHelper to set
     */
    public void setDiasHabilesHelper(DiasHabilesHelper diasHabilesHelper) {
        this.diasHabilesHelper = diasHabilesHelper;
    }

    /**
     * @return the listaGeneralDocumentos
     */
    public List<DocumentoDTO> getListaGeneralDocumentos() {
        return listaGeneralDocumentos;
    }

    /**
     * @param listaGeneralDocumentos
     *            the listaGeneralDocumentos to set
     */
    public void setListaGeneralDocumentos(List<DocumentoDTO> listaGeneralDocumentos) {
        this.listaGeneralDocumentos = listaGeneralDocumentos;
    }

    @Override
    public BaseCloudBusiness getBusinessBean() {
        return getRegistroSolicitudCALCommonBussines();
    }

    @Override
    public List<DocumentoDTO> getListaDocumentosAdjuntados() {
        return getListaDocumentos();
    }

    public String getFirmarSolicitudCAL() {
        return UrlFirma.PAGINA_FIRMA_SOLICITUD_CAL.toString();
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

    public String getRowsCorreo() {
        return rowsCorreo;
    }

    public void setRowsCorreo(String rowsCorreo) {
        this.rowsCorreo = rowsCorreo;
    }

    /**
     * @return the modalidadTramite
     */
    public StringBuffer getModalidadTramite() {
        return modalidadTramite;
    }

    /**
     * @param modalidadTramite
     *            the modalidadTramite to set
     */
    public void setModalidadTramite(StringBuffer modalidadTramite) {
        this.modalidadTramite = modalidadTramite;
    }

    /**
     * @return the tramiteOficialia
     */
    public boolean isTramiteOficialia() {
        return tramiteOficialia;
    }

    /**
     * @param tramiteOficialia
     *            the tramiteOficialia to set
     */
    public void setTramiteOficialia(boolean tramiteOficialia) {
        this.tramiteOficialia = tramiteOficialia;
    }

    public boolean isActivarCampos() {
        return activarCampos;
    }

    public void setActivarCampos(boolean activarCampos) {
        this.activarCampos = activarCampos;
    }

    public boolean isActivarCamposPerInv() {
        return activarCamposPerInv;
    }

    public void setActivarCamposPerInv(boolean activarCamposPerInv) {
        this.activarCamposPerInv = activarCamposPerInv;
    }

    public List<String> getDocsFaltantesPorAnexar() {
        return docsFaltantesPorAnexar;
    }

    public void setDocsFaltantesPorAnexar(List<String> docsFaltantesPorAnexar) {
        this.docsFaltantesPorAnexar = docsFaltantesPorAnexar;
    }

    public RegistroSolicitudCALCommonValidator getRegistroSolicitudCALCommonValidator() {
        return registroSolicitudCALCommonValidator;
    }

    public void setRegistroSolicitudCALCommonValidator(
            RegistroSolicitudCALCommonValidator registroSolicitudCALCommonValidator) {
        this.registroSolicitudCALCommonValidator = registroSolicitudCALCommonValidator;
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

    public boolean isActivarPerOirNotNom() {
        return activarPerOirNotNom;
    }

    public void setActivarPerOirNotNom(boolean activarPerOirNotNom) {
        this.activarPerOirNotNom = activarPerOirNotNom;
    }

    public boolean isActivarPerOirNotAPat() {
        return activarPerOirNotAPat;
    }

    public void setActivarPerOirNotAPat(boolean activarPerOirNotAPat) {
        this.activarPerOirNotAPat = activarPerOirNotAPat;
    }

    public boolean isActivarPerOirNotAMat() {
        return activarPerOirNotAMat;
    }

    public void setActivarPerOirNotAMat(boolean activarPerOirNotAMat) {
        this.activarPerOirNotAMat = activarPerOirNotAMat;
    }

    public boolean isActivarPerOirNotDir() {
        return activarPerOirNotDir;
    }

    public void setActivarPerOirNotDir(boolean activarPerOirNotDir) {
        this.activarPerOirNotDir = activarPerOirNotDir;
    }

    public boolean isActivarPerOirNotTel() {
        return activarPerOirNotTel;
    }

    public void setActivarPerOirNotTel(boolean activarPerOirNotTel) {
        this.activarPerOirNotTel = activarPerOirNotTel;
    }

    public ValidarFechaHelper getValidarFechaHelper() {
        return validarFechaHelper;
    }

    public void setValidarFechaHelper(ValidarFechaHelper validarFechaHelper) {
        this.validarFechaHelper = validarFechaHelper;
    }

    /**
     * @return the idDinamicoOpcionales
     */
    public String getIdDinamicoOpcionales() {
        return idDinamicoOpcionales;
    }

    /**
     * @param idDinamicoOpcionales
     *            the idDinamicoOpcionales to set
     */
    public void setIdDinamicoOpcionales(String idDinamicoOpcionales) {
        this.idDinamicoOpcionales = idDinamicoOpcionales;
    }

    public boolean isDisableBuscar() {
        return disableBuscar;
    }

    public void setDisableBuscar(boolean disableBuscar) {
        this.disableBuscar = disableBuscar;
    }

    public String getIdDinamicoFracc() {
        return idDinamicoFracc;
    }

    public void setIdDinamicoFracc(String idDinamicoFracc) {
        this.idDinamicoFracc = idDinamicoFracc;
    }

    public List<ManifiestoDTO> getListaManifiestos() {
        return listaManifiestos;
    }

    public void setListaManifiestos(List<ManifiestoDTO> listaManifiestos) {
        this.listaManifiestos = listaManifiestos;
    }

    public boolean isManifiesto() {
        return manifiesto;
    }

    public void setManifiesto(boolean manifiesto) {
        this.manifiesto = manifiesto;
    }

    public String getTipoRolContribuyenteText() {
        return tipoRolContribuyenteText;
    }

    public void setTipoRolContribuyenteText(String tipoRolContribuyenteText) {
        this.tipoRolContribuyenteText = tipoRolContribuyenteText;
    }

    public TipoRolContribuyenteIDC getTipoRolContribuyenteIDC() {
        return tipoRolContribuyenteIDC;
    }

    public void setTipoRolContribuyenteIDC(TipoRolContribuyenteIDC tipoRolContribuyenteIDC) {
        this.tipoRolContribuyenteIDC = tipoRolContribuyenteIDC;
    }
}
