package mx.gob.sat.siat.juridica.rrl.web.controller.bean.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import mx.gob.sat.siat.juridica.auditoria.annotation.Audit;
import mx.gob.sat.siat.juridica.auditoria.controller.BaseAuditoriaControllerBean;
import mx.gob.sat.siat.juridica.base.constantes.MenusConstantes;
import mx.gob.sat.siat.juridica.base.constantes.MovimientosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.SistemasConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EnumeracionBitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoAcuse;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRol;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.FirmarTareaController;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.ca.util.validador.TipoRolContribuyenteIDC;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.cal.util.constants.CadenasConstants;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.controller.AutorizarRemisionCALController;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.GeneralConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.BandejaBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.BandejaLazyList;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DatosBandejaDataModel;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility.GenerarDocumentosHelper;

@ViewScoped
@ManagedBean(name = "bandejaController")
public class BandejaController extends BaseAuditoriaControllerBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6589382091780946280L;

	private DatosBandejaDataModel bandejaDataModel;

	@ManagedProperty(value = "#{bandejaBussiness}")
	private BandejaBussines bandejaBussines;

	@ManagedProperty("#{autorizarRemisionController}")
	private AutorizarRemisionController autorizarRemisionController;
	@ManagedProperty("#{autorizarRemisionCALController}")
	private AutorizarRemisionCALController autorizarRemisionCALController;
	@ManagedProperty("#{firmarTareaController}")
	private FirmarTareaController firmarTareaController;
	
	@ManagedProperty(value = "#{generarDocumentosHelper}")
    private GenerarDocumentosHelper generarDocumentosHelper;
	
	@ManagedProperty(value = "#{tipoRolContribuyenteIDC}")
    private transient TipoRolContribuyenteIDC tipoRolContribuyenteIDC;
	
	private String messagesRedirect;
	private String mensajeBandejaVacia;

	@ManagedProperty("#{msg}")
	private transient ResourceBundle messages;
	@ManagedProperty("#{errmsg}")
	private transient ResourceBundle errorMsg;

	private List<DatosBandejaTareaDTO> listDatosBandeja = new ArrayList<DatosBandejaTareaDTO>();
	private List<CatalogoDTO> estadosProcesales = new ArrayList<CatalogoDTO>();
	private DatosBandejaTareaDTO bandejaTareaDTO = new DatosBandejaTareaDTO();
	private FiltroBandejaTareaDTO filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
	private String mensageFechaFin;
	private String mensageFechas;
	private Date minFechaFin;
	private Date maxFechaFin;
	private Boolean showMessages;

	private BandejaLazyList bandejaLazyList;
	private transient DataTable dataTableFil = new DataTable();
	private Map<String, String> data = new LinkedHashMap<String, String>();

	public BandejaController() {
	}

	@PostConstruct
	public void initFiltro() {
		getLogger().debug("Ingresando al postconstruct de BandejaController");
		getLogger().debug("  filtroBandejaTareaDTO {}", filtroBandejaTareaDTO);
		getLogger().debug("  getUserProfile {}", getUserProfile());

		listDatosBandeja = new ArrayList<DatosBandejaTareaDTO>();
		bandejaLazyList = new BandejaLazyList(listDatosBandeja, bandejaBussines);
		listDatosBandeja.clear();
		filtroBandejaTareaDTO.setUsuario(getUserProfile().getRfc());
		estadosProcesales.addAll(bandejaBussines.obtenerCatalogoEstados());
		Date maxFechFin = new Date();
		setMaxFechaFin(maxFechFin);
		getLogger().debug("  obterner estados {}", estadosProcesales);
		getLogger().debug("termina el postconstruct de BandejaController");
		String show = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("showMessages");
		setShowMessages(show == null);

	}

	public void mensajesRedirect() {
		getLogger().debug("ingresando a mensajesRedirect");
		if (getMessagesRedirect() != null) {
			listDatosBandeja.clear();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", getMessagesRedirect()));
			setMessagesRedirect(null);
		}
	}

	public void ordenar() {

		buscarTareaBandeja(null);

	}

	public Date getMaxFechaInicio() {
		return new Date();
	}

	@Audit(identificadorProceso = ProcesosConstantes.PROCESO_EMPLEADO_BANDEJA_TAREAS, movimiento = MovimientosConstantes.MOVIMIENTO_ABOGADO_BUSCA_PENDIENTES, claveSistema = SistemasConstantes.SISTEMA_RRL)
	public void buscarTareaBandeja(ActionEvent actionEvent) {
		getLogger().debug("getting bandeja data...");
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String fecini = "";
		String fecfin = "";

		if (filtroBandejaTareaDTO.getFechaInicio() != null && filtroBandejaTareaDTO.getFechaFin() != null) {

			fecini = df.format(filtroBandejaTareaDTO.getFechaInicio());
			fecfin = df.format(filtroBandejaTareaDTO.getFechaFin());
		} else {

			if ((filtroBandejaTareaDTO.getFechaFin() != null && filtroBandejaTareaDTO.getFechaInicio() == null)
					|| (filtroBandejaTareaDTO.getFechaInicio() != null
							&& filtroBandejaTareaDTO.getFechaFin() == null)) {
				RequestContext.getCurrentInstance().reset("formBandeja");
				setShowMessages(true);
				filtroBandejaTareaDTO.setFechaFin(null);
				filtroBandejaTareaDTO.setFechaInicio(null);
				FacesContext context = FacesContext.getCurrentInstance();
				UIInput fechaFinInput = (UIInput) context.getViewRoot().findComponent(":formBandeja:fechaFin");
				fechaFinInput.setValid(false);
				UIInput fechaInicioInput = (UIInput) context.getViewRoot().findComponent(":formBandeja:fechaInicio");
				fechaInicioInput.setValid(false);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "", getMensageFechas()));
				return;

			}

			if ((filtroBandejaTareaDTO.getFechaFin() != null && filtroBandejaTareaDTO.getFechaInicio() != null)
					&& filtroBandejaTareaDTO.getFechaFin().before(filtroBandejaTareaDTO.getFechaInicio())) {
				RequestContext.getCurrentInstance().reset("formBandeja");
				setShowMessages(true);
				filtroBandejaTareaDTO.setFechaFin(null);
				filtroBandejaTareaDTO.setFechaInicio(null);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "", getMensageFechaFin()));
				return;
			}

		}
		data.put("numeroAsunto", filtroBandejaTareaDTO.getNumeroAsunto());
		data.put("rfc", getUserProfile().getRfc());
		data.put("fechIni", fecini);
		data.put("fechFin", fecfin);
		data.put("rfcPromovente",
				filtroBandejaTareaDTO.getRfcPromovente() != null ? filtroBandejaTareaDTO.getRfcPromovente() : "");
		data.put("estadoProcesal",
				filtroBandejaTareaDTO.getEstadoProcesal() != null ? filtroBandejaTareaDTO.getEstadoProcesal().getClave()
						: "");
		data.put("busqueda", "busqueda");
		data.put("esContribuyente", String.valueOf(getUserProfile().getEsContribuyente()));
		getDataTableFil().setFilters(data);

		if (listDatosBandeja == null || listDatosBandeja.isEmpty()) {
			setMensajeBandejaVacia(messages.getString("vuj.grid.vacio"));
		}
		getFlash().remove(GeneralConstantes.MENSAJE_REDIRECT);

		// Escribe los datos de la busqueda para la bitacora
		guardarObservacionesBitacora(filtroBandejaTareaDTO.getCadenaBitacora());

	}

	public void limpiarBandeja(ActionEvent actionEvent) {

		getFlash().remove(GeneralConstantes.MENSAJE_REDIRECT);
		filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
		data = new LinkedHashMap<String, String>();
		getDataTableFil().setFilters(data);
		setMensajeBandejaVacia("");
		RequestContext.getCurrentInstance().reset("formBandeja");
	}

	public void onRowDblClckSelect(final SelectEvent event) throws IOException {
		DatosBandejaTareaDTO datoSelected = ((DatosBandejaTareaDTO) event.getObject());
		verificaAcuses(datoSelected);
		if (bandejaTareaDTO.getNombreTarea().equals(EnumeracionBitacora.TURNAR_ASUNTO.getDescripcion())) {
			getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, datoSelected);
			getFlash().put("reasignar", false);
		} else if ((bandejaTareaDTO.getNombreTarea().equals("Registrar fecha notificaci\u00F3n"))
				|| bandejaTareaDTO.getNombreTarea().equals(EnumeracionBitacora.ATENDER_ASUNTO.getDescripcion())
				|| (bandejaTareaDTO.getNombreTarea()
						.equals(EnumeracionBitacora.NOTIFICACION_REQUERIMIENTO.getDescripcion())
						|| (bandejaTareaDTO.getNombreTarea()
								.equals(EnumeracionBitacora.NOTIFICACION_RESOLUCION.getDescripcion())))) {
			datoSelected.setEsAbogado(true);
		} else if (bandejaTareaDTO.getNombreTarea().equals(EnumeracionBitacora.FIRMAR_REMISION.getDescripcion())) {
			if (datoSelected.getTipoTramite().startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
				autorizarRemisionCALController.prepararAutorizarRemision(datoSelected);
			} else if (datoSelected.getTipoTramite().startsWith(DiscriminadorConstants.T1_TIPO_TRAMITE_PREFIJO)) {
				autorizarRemisionController.prepararAutorizarRemision(datoSelected);
			}
		} else if (bandejaTareaDTO.getNombreTarea().equals(EnumeracionBitacora.FIRMAR_RESOLUCION.getDescripcion())
				&& datoSelected.getTipoTramite().startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
			bandejaTareaDTO.setUrl(CadenasConstants.GENERAR_RESOLUCION_ACTION_NAVEGATION_AUTORIZACION);
		}
		SolicitudDTO sol = getBandejaBussines().obtenerSolicitud(datoSelected.getIdSolicitud());
		if (sol != null) {
			datoSelected.setOficialia((TipoRol.OFICIAL_PARTES.getClave().equals(sol.getCveRolCapturista())));
			if (sol.getSolicitante() != null && sol.getSolicitante().getRazonSocial() != null) {
				datoSelected.setContribuyenteMoral(true);
			}
		}
		getFlash().put("idSolicitud", datoSelected.getIdSolicitud());
		getFlash().put("tipoTramite", datoSelected.getTipoTramite());
		getFlash().put("numAsunto", datoSelected.getNumeroAsunto());
		getFlash().put("idRequerimiento", datoSelected.getIdRequerimiento());

		getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, datoSelected);

		inicializarBandeja();
		ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext
				.getCurrentInstance().getApplication().getNavigationHandler();

		configurableNavigationHandler.performNavigation(datoSelected.getUrl() + "?faces-redirect=true");

	}

	private void verificaAcuses(DatosBandejaTareaDTO datoSelected) {
		getLogger().debug("Se verificando los acuses del asunto: {}",datoSelected.getNumeroAsunto());
		if(getBandejaBussines().obtenerDocumentosOficialesTipo(datoSelected.getNumeroAsunto(), TipoDocumentoOficial.OFICIO_TERMINOS_CONDICIONES.getClave()).isEmpty()) {
			getLogger().debug("El asunto {} no tiene acuses",datoSelected.getNumeroAsunto());
			regenerarAcuses(datoSelected);
		}
		
	}
	
	private void regenerarAcuses(DatosBandejaTareaDTO datoSelected) {
		getLogger().info("Se re-generando los acuses del asunto: {}",datoSelected.getNumeroAsunto());
		getLogger().info("datoSelected: {}", datoSelected.toString());
		List<Long> idsDoc = null;
		if(getBandejaBussines().tieneDocumentosAnexados(datoSelected.getIdSolicitud().toString())) {
			FirmaDTO firma = getBandejaBussines().obtieneFirma(datoSelected);
			FirmaDTO firmaSelladora = getBandejaBussines().obtenSelloPromocionSIAT(datoSelected.getNumeroAsunto(),
					datoSelected.getIdSolicitud(), firma.getFechaFirma());
			try {
				if (datoSelected.getTipoTramite().startsWith(DiscriminadorConstants.T1_TIPO_TRAMITE_PREFIJO)) {
					idsDoc = acusesRrl(datoSelected, firma, firmaSelladora);
				} else {
					idsDoc = acusesCal(datoSelected, firma, firmaSelladora);
				}
			} catch (BusinessException e) {
				getLogger().error(e.getMessage());
			} catch (IOException e) {
				getLogger().error(e.getMessage());
			}

		}
		
		if(idsDoc == null) {
			getLogger().error("Ocurrio un error al re-generar los acuses del asunto: {}", datoSelected.getNumeroAsunto());
		}else {
			getLogger().info("Se re-generaron correctamente los acuses del asunto: {}",datoSelected.getNumeroAsunto());
			getBandejaBussines().cambiarEstadofirmarDocumentos(datoSelected.getIdSolicitud(),EstadoDocumento.ANEXADO.getClave(), EstadoDocumento.FIRMADO.getClave());
		}
	}
	
	private List<Long> acusesRrl(DatosBandejaTareaDTO datoSelected, FirmaDTO firma, FirmaDTO firmaSelladora) throws BusinessException {
		return getGenerarDocumentosHelper().generarDocumentosPromocion(datoSelected,
				TipoAcuse.RECPROM.getClave(), firma.getCadenaOriginal(), firma.getSello(),
				firmaSelladora.getCadenaOriginal(), firmaSelladora.getSello());
	}

	private List<Long> acusesCal(DatosBandejaTareaDTO datoSelected, FirmaDTO firma, FirmaDTO firmaSelladora) throws BusinessException, IOException {
		return getGenerarDocumentosHelper().generarDocumentosPromocionCAL(datoSelected, TipoAcuse.RECPROM.getClave(), firma,
				firmaSelladora.getCadenaOriginal(), firmaSelladora.getSello(),
				getBandejaBussines().tieneDocumentosAnexados(datoSelected.getIdSolicitud().toString()),
 				tipoRolContribuyenteIDC.consultaHidrocarburos(datoSelected.getRfcSolicitante()), tipoRolContribuyenteIDC.getEstadoContribuyente());
	}

	public String inicializarBandeja() {
		listDatosBandeja.clear();
		estadosProcesales.clear();
		filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
		filtroBandejaTareaDTO.setUsuario(getUserProfile().getRfc());
		estadosProcesales.addAll(bandejaBussines.obtenerCatalogoEstados());
		getLogger().debug("obterner estados {}", estadosProcesales);
		setMensajeBandejaVacia("");
		return LoginConstante.BANDEA_JSF;
	}

	public void iniciarBandejaPromovente() {
		// Validacion de acceso a la funcionalidad
		this.validaAccesoContribuytente();
		this.cargaMensajes();
	}

	@Audit(identificadorProceso = ProcesosConstantes.PROCESO_EMPLEADO_BANDEJA_TAREAS, movimiento = MovimientosConstantes.MOVIMIENTO_ABOGADO_INGRESO_PENDIENTES, claveSistema = SistemasConstantes.SISTEMA_RRL, observaciones = "Ingreso a Consulta de asuntos pendientes")
	public void iniciarBandejaFuncionario() {
		// Validacion de acceso a la funcionalidad
		this.validaAccesoProcesoMenu(ProcesosConstantes.PROCESO_EMPLEADO_BANDEJA_TAREAS,
				MenusConstantes.MENU_EMPLEADOS);
		this.cargaMensajes();
	}

	public void iniciarBandejaReasignacion() {
		this.validaAccesoProcesoMenu(ProcesosConstantes.PROCESO_EMPLEADO_REASIGNAR_RRL, MenusConstantes.MENU_EMPLEADOS);
		this.cargaMensajes();
	}

	public void cargaMensajes() {
		getLogger().debug("ingresando a cargaMensajes");
		String mensaje = (String) getFlash().get(GeneralConstantes.MENSAJE_REDIRECT);
		if (mensaje != null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensaje));

		}
		getLogger().debug("finalizando a cargaMensajes");
	}

	public void rangoMaxFechas() {
		Date minFechFin = new Date();

		minFechFin.setTime(filtroBandejaTareaDTO.getFechaInicio().getTime());
		Calendar calTramite = Calendar.getInstance();
		calTramite.setTime(filtroBandejaTareaDTO.getFechaInicio());
		calTramite.add(Calendar.YEAR, (1));
		Date maxFechFin = calTramite.getTime();
		if (maxFechFin.after(new Date())) {
			maxFechFin = new Date();
		}
		setMinFechaFin(minFechFin);
		setMaxFechaFin(maxFechFin);
		filtroBandejaTareaDTO.setFechaFin(null);

	}

	public DatosBandejaTareaDTO getBandejaTareaDTO() {
		return bandejaTareaDTO;
	}

	public void setBandejaTareaDTO(DatosBandejaTareaDTO bandejaTareaDTO) {
		this.bandejaTareaDTO = bandejaTareaDTO;
	}

	public FiltroBandejaTareaDTO getFiltroBandejaTareaDTO() {
		return filtroBandejaTareaDTO;
	}

	public void setFiltroBandejaTareaDTO(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {
		this.filtroBandejaTareaDTO = filtroBandejaTareaDTO;
	}

	public DatosBandejaDataModel getBandejaDataModel() {
		return bandejaDataModel;
	}

	public void setBandejaDataModel(DatosBandejaDataModel bandejaDataModel) {
		this.bandejaDataModel = bandejaDataModel;
	}

	public List<DatosBandejaTareaDTO> getListDatosBandeja() {
		return listDatosBandeja;
	}

	public void setListDatosBandeja(List<DatosBandejaTareaDTO> listDatosBandeja) {
		this.listDatosBandeja = listDatosBandeja;
	}

	public BandejaBussines getBandejaBussines() {
		return bandejaBussines;
	}

	public void setBandejaBussines(BandejaBussines bandejaBussines) {
		this.bandejaBussines = bandejaBussines;
	}

	public AutorizarRemisionController getAutorizarRemisionController() {
		return autorizarRemisionController;
	}

	public void setAutorizarRemisionController(AutorizarRemisionController autorizarRemisionController) {
		this.autorizarRemisionController = autorizarRemisionController;
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

	public String getMensageFechaFin() {
		mensageFechaFin = errorMsg.getString("validation.fechaFin");
		return mensageFechaFin;
	}

	public String getMensageFechas() {
		mensageFechas = errorMsg.getString("validation.fechas");
		return mensageFechas;
	}

	public FirmarTareaController getFirmarTareaController() {
		return firmarTareaController;
	}

	public void setFirmarTareaController(FirmarTareaController firmarTareaController) {
		this.firmarTareaController = firmarTareaController;
	}

	public List<CatalogoDTO> getEstadosProcesales() {
		return estadosProcesales;
	}

	public void setEstadosProcesales(List<CatalogoDTO> estadosProcesales) {
		this.estadosProcesales = estadosProcesales;
	}

	public String getMessagesRedirect() {
		return messagesRedirect;
	}

	public void setMessagesRedirect(String messagesRedirect) {
		this.messagesRedirect = messagesRedirect;
	}

	public Date getMinFechaFin() {
		if (minFechaFin != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(minFechaFin);
			return cal.getTime();
		} else {
			return null;
		}
	}

	public void setMinFechaFin(Date minFechaFin) {
		if (minFechaFin != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(minFechaFin);
			this.minFechaFin = cal.getTime();
		} else {
			this.minFechaFin = null;
		}
	}

	public Date getMaxFechaFin() {
		if (maxFechaFin != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(maxFechaFin);
			return cal.getTime();
		} else {
			return null;
		}
	}

	public void setMaxFechaFin(Date maxFechaFin) {
		if (maxFechaFin != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(maxFechaFin);
			this.maxFechaFin = cal.getTime();
		} else {
			this.maxFechaFin = null;
		}
	}

	/**
	 * @param mensageFechaFin the mensageFechaFin to set
	 */
	public void setMensageFechaFin(String mensageFechaFin) {
		this.mensageFechaFin = mensageFechaFin;
	}

	/**
	 * @param mensageFechas the mensageFechas to set
	 */
	public void setMensageFechas(String mensageFechas) {
		this.mensageFechas = mensageFechas;
	}

	/**
	 * @return the mensajeBandejaVacia
	 */
	public String getMensajeBandejaVacia() {
		return mensajeBandejaVacia;
	}

	/**
	 * @param mensajeBandejaVacia the mensajeBandejaVacia to set
	 */
	public void setMensajeBandejaVacia(String mensajeBandejaVacia) {
		this.mensajeBandejaVacia = mensajeBandejaVacia;
	}

	public AutorizarRemisionCALController getAutorizarRemisionCALController() {
		return autorizarRemisionCALController;
	}

	public void setAutorizarRemisionCALController(AutorizarRemisionCALController autorizarRemisionCALController) {
		this.autorizarRemisionCALController = autorizarRemisionCALController;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public DataTable getDataTableFil() {
		return dataTableFil;
	}

	public void setDataTableFil(DataTable dataTableFil) {
		this.dataTableFil = dataTableFil;
	}

	public BandejaLazyList getBandejaLazyList() {
		return bandejaLazyList;
	}

	public void setBandejaLazyList(BandejaLazyList bandejaLazyList) {
		this.bandejaLazyList = bandejaLazyList;
	}

	public Boolean getShowMessages() {
		return showMessages;
	}

	public void setShowMessages(Boolean showMessages) {
		this.showMessages = showMessages;
	}
	
	public GenerarDocumentosHelper getGenerarDocumentosHelper() {
		return generarDocumentosHelper;
	}

	public void setGenerarDocumentosHelper(GenerarDocumentosHelper generarDocumentosHelper) {
		this.generarDocumentosHelper = generarDocumentosHelper;
	}

	public TipoRolContribuyenteIDC getTipoRolContribuyenteIDC() {
		return tipoRolContribuyenteIDC;
	}

	public void setTipoRolContribuyenteIDC(TipoRolContribuyenteIDC tipoRolContribuyenteIDC) {
		this.tipoRolContribuyenteIDC = tipoRolContribuyenteIDC;
	}


}
