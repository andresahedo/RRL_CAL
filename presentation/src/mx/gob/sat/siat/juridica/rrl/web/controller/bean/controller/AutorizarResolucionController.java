/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.component.behavior.BehaviorBase;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

import mx.gob.sat.siat.juridica.base.api.GenerarObservacionFacade;
import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dto.AgraviosDTO;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.GenericDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.base.util.constante.CatalogoConstantes;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.ConsultasBussines;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.AdjuntarDocumentoController;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.BaseCloudController;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.FirmarTareaController;
import mx.gob.sat.siat.juridica.base.web.util.ResolucionMasImpotanteHelper;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.ConceptosDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionAbogadoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.AbogadoConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.AutorizarConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.GeneralConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import mx.gob.sat.siat.juridica.rrl.util.helper.DiasHabilesHelper;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AbogadoBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AnexarDocumentoBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AutorizarResolucionBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.CapturaSolicitudBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.emitirresolucion.EmitirResolucionBusiness;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.ConceptoDataModel;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoOficialDataModel;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.ResolucionDataModel;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility.ResolucionesImpHelper;
import mx.gob.sat.siat.juridica.rrl.web.util.validador.AbogadoValidator;
import mx.gob.sat.siat.juridica.rrl.web.util.validador.AnexarDocumentoValidator;
import mx.gob.sat.siat.juridica.rrl.web.util.validador.AutorizarValidator;

/**
 * Bean que implementa la logica de negocio para autorizar una resolucion.
 * 
 * @author softtek
 * 
 */
@ManagedBean(name = "autorizarController")
@ViewScoped
public class AutorizarResolucionController extends BaseCloudController<DocumentoOficialDTO> {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = -3858355910447665369L;

    /**
     * Lista de resoluciones a guardar.
     */
    private List<ResolucionImpugnadaDTO> resolucionesDTOList = new ArrayList<ResolucionImpugnadaDTO>();
    /**
     * Catalogo de autoridad emisora precargado para la forma.
     */
    private List<UnidadAdministrativaDTO> autoridadEmisoras = new ArrayList<UnidadAdministrativaDTO>();
    /**
     * Catalogo de procedimientos precargado para la forma.
     */
    private List<CatalogoDTO> listaProcedimientos = new ArrayList<CatalogoDTO>();
    /**
     * Catalogo de concepto precargado para la forma.
     */
    private List<CatalogoDTO> conceptoCats = new ArrayList<CatalogoDTO>();
    /**
     * Catalogo de recurso precargado para la forma.
     */
    private List<CatalogoDTO> recursoCats = new ArrayList<CatalogoDTO>();
    /**
     * Tabla de conceptos precargada para la forma.
     */
    private List<ConceptosDTO> listConceptosDTOTable = new ArrayList<ConceptosDTO>();

    /**
     * Catalogo precargado de conceptos.
     */
    private List<ConceptosDTO> respaldoConceptosDTOTable = new ArrayList<ConceptosDTO>();
    /**
     * Lista de recurso dual precargado para la forma.
     */
    private List<CatalogoDTO> recursoDualList = new ArrayList<CatalogoDTO>();

    /**
     * Auxiliar para habilitar/Inhabilitar el panel de recurso.
     */
    private boolean visibleRecursoPanel;
    /**
     * Auxiliar para la resolucion de pantalla.
     */
    private int indexResolucion;

    /**
     * Auxiliar para el montoTotalControvertido
     */
    private BigDecimal montoTotalControvertido;
    /**
     * Auxiliar para mensajes.
     */
    private String msg;
    /**
     * Auxiliar para mensajes de error
     */
    private String errorMessage;
    /**
     * Auxiliar para nombre del funcionario
     */
    private String nombreFuncionario;
    /**
     * Auxiliar bandera para guardar
     */
    private boolean banderaGuardar;
    /**
     * Auxiliar para el determinante crediticio
     */
    private boolean determinanteCred;
    /**
     * Auxiliar para visibilidad del panel de concepto
     */
    private boolean visibleConceptoPanel;
    /**
     * Auxilar para resolucion para guardar resolucion
     */
    private boolean resolucionGuardada;
    /**
     * Auxiliar para mensaje de error en concepto
     */
    private String errorConceptoMessage;
    /**
     * Auxiliar para el valor del importe
     */
    private String importeValor;

    private boolean disableElimResol;
    /**
     * Auxiliar para el valor del monto
     */
    private BigDecimal montoValor;
    /**
     * Auxiliar para remover registros
     */
    private boolean banderaRemove;
    /**
     * Auxililar para el monto
     */
    private boolean banderaMonto;
    /**
     * Auxiliar para resolucion impugnada
     */
    private boolean banderaResolucionImpugnada;
    /**
     * Resolucion utilizada en la forma y en pantalla
     */
    private ResolucionImpugnadaDTO resol = new ResolucionImpugnadaDTO();
    /**
     * Resolucion seleccionada utilizada en la forma y en pantalla.
     */
    private ResolucionImpugnadaDTO selectedResol = new ResolucionImpugnadaDTO();
    /**
     * Auxililar para el panel de Agravios
     */
    private boolean visibleAgravios;

    private BigDecimal ultimoValorResolucion;
    private boolean checkMonto;

    @ManagedProperty(value = "#{resolucionesImpHelper}")
    private ResolucionesImpHelper resolucionesImpHelper;

    /**
     * Bean para conectar con el backend, atiende todas las peticiones de la emision
     * de resoluciones.
     */
    @ManagedProperty("#{emitirResolucionBusiness}")
    private EmitirResolucionBusiness emitirResolucionBusiness;
    /**
     * Bean para conectar con el backend, atiende todas las peticiones para
     * autorizar resoluciones.
     */
    @ManagedProperty("#{autorizarResolucionBussines}")
    private AutorizarResolucionBussines autorizarResolucionBusiness;
    /**
     * Bean para conectar con el backend, atiende todas las peticiones para
     * operaciones correspondientes a abogado.
     */
    @ManagedProperty(value = "#{abogadoBussines}")
    private AbogadoBussines abogadoBussines;
    /**
     * Bean que valida las autorizaciones.
     */
    @ManagedProperty(value = "#{autorizarValidator}")
    private AutorizarValidator autorizarValidator;
    /**
     * /** Propiedad para manejar los mensajes en pantalla.
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    /**
     * Propiedad para manejar mensajes de error en pantalla.
     */
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;
    /**
     * Bean para firmar la tarea
     */
    @ManagedProperty(value = "#{firmarTareaController}")
    private FirmarTareaController firmarTareaController;
    /**
     * Bean que controla la logica para adjuntar un documento.
     */
    @ManagedProperty("#{adjuntarDocumentoController}")
    private AdjuntarDocumentoController adjuntarDocumentoController;
    /**
     * Bean que valida el abogado.
     */
    @ManagedProperty(value = "#{abogadoValidator}")
    private AbogadoValidator abogadoValidator;

    @ManagedProperty(value = "#{resolucionMasImpotanteHelper}")
    private ResolucionMasImpotanteHelper resolucionMasImpotanteHelper;

    /** Bean utilizado para obtener informacion del tramite */
    @ManagedProperty("#{generarObservacionFacade}")
    private GenerarObservacionFacade generarObservacionFacade;

    /** Bean utilizado para realizar calculos de fecha */
    @ManagedProperty(value = "#{diasHabilesHelper}")
    private DiasHabilesHelper diasHabilesHelper;

    @ManagedProperty(value = "#{consultasBussines}")
    private ConsultasBussines consultasBussines;

    private Date fechaMinimaExFondo;

    private Date fechaMaximaExcFondo;

    private Date fechaNotifOld;

    private Date fechaAudiOld;

    private boolean inhabilitarGuardar;

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
     *            el firmarTareaController a fijar.
     */
    public void setFirmarTareaController(FirmarTareaController firmarTareaController) {
        this.firmarTareaController = firmarTareaController;
    }

    /**
     * DTO para acceder al catalogo de conceptos.
     */
    private ConceptosDTO conceptosDTO = new ConceptosDTO();
    /**
     * DTO que almacena los conceptos seleccionados.
     */
    private ConceptosDTO selectConceptosDTO = new ConceptosDTO();
    /**
     * Datos en bandeja de la tarea.
     */
    private DatosBandejaTareaDTO datosBandejaTareaDTO;
    /**
     * Data Model para la resolucion.
     */
    private ResolucionDataModel resolDataModel;
    /**
     * Data Model para la resolucion.
     */
    private ConceptoDataModel conceptoDataModel;
    /**
     * Lista para mostrar recursos.
     */
    private DualListModel<CatalogoDTO> recursoDual;
    /**
     * DTO para resolucion de abogado.
     */
    private ResolucionAbogadoDTO abogadoDTO = new ResolucionAbogadoDTO();
    private ConceptosDTO[] selectedConceptos;

    /**
     * Lista de sentidos de resolucion.
     */
    private List<CatalogoDTO> sentidosResolucion;

    /**
     * Lista de sentidos de resolucion perdedores.
     */
    private Map<String, CatalogoDTO> sentidosResolucionPerdedores;

    /**
     * Lista para mostrar agravios.
     */
    private DualListModel<AgraviosDTO> agravios;
    /**
     * Lista de agravios.
     */
    private List<AgraviosDTO> agraviosSource;
    /**
     * Fecha maxima
     */
    private Date fechaMaxima;

    private List<DocumentoDTO> documentosComplementarios;
    /**
     * Carga de catalogos.
     */

    private boolean nuevoDocumento;

    private boolean blnMensajeDefensa;

    private boolean disableEliminar;

    private ResolucionImpugnadaDTO selectedResolTemporal = new ResolucionImpugnadaDTO();

    private BigDecimal montoSelTemporal;

    private Boolean checkResolTemporal;

    private List<DocumentoOficialDTO> listaDocumentos = new ArrayList<DocumentoOficialDTO>();
    private List<GenericDTO> documentosExclusivoFondo;
    @ManagedProperty(value = "#{capturaSolicitudBussines}")
    private CapturaSolicitudBussines capturaSolicitudBussines;
    /** Bussines para adjuntar documentos */
    @ManagedProperty(value = "#{anexarDocumentoBussines}")
    private AnexarDocumentoBussines anexarDocumentoBussines;
    /** Validator para adjuntar documentos */
    @ManagedProperty(value = "#{anexarDocumentoValidator}")
    private AnexarDocumentoValidator anexarDocumentoValidator;

    /** Campos de exclusivo de fondo **/
    private Date fechaNotifExclusivoFondo;
    private Date fechaAudienciaExclusivoFondo;
    private boolean mostrarPanelExclusivoFondo;

    /** Data model de documentos adjuntados */
    private DocumentoOficialDataModel documentoOficialDataModel;
    private boolean eliminarVisible;
    private boolean agregarVisible;

    /** ID del documento seleccionado para el apartado de exclusivo de fondo */
    private String id;
    private DocumentoOficialDTO[] registrosEliminar;

    private boolean tablaExclusivoEditar;

    @PostConstruct
    public void cargarDatosIniciales() {

        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));
        resolDataModel = new ResolucionDataModel(resolucionesDTOList);
        conceptoDataModel = new ConceptoDataModel(listConceptosDTOTable);
        banderaGuardar = false;

        autoridadEmisoras.addAll(abogadoBussines.obtenerCatalogoAutoridad());
        listaProcedimientos.addAll(abogadoBussines.obtenerCatalogos("procedimiento"));

        determinanteCred = false;
        agravios = new DualListModel<AgraviosDTO>(emitirResolucionBusiness.obtenerAgravios(),
                new ArrayList<AgraviosDTO>());
        agraviosSource = new ArrayList<AgraviosDTO>();
        agraviosSource.addAll(agravios.getSource());
        sentidosResolucion = emitirResolucionBusiness.obtenerSentidosResolucion();
        sentidosResolucionPerdedores = emitirResolucionBusiness.obtenerSentidosResolucionPerdedores();
        montoValor = BigDecimal.ZERO;
        banderaMonto = true;
        fechaMaxima = new Date();
        mostrarPanelExclusivoFondo = false;
        setDatosIniciales();
        nombreFuncionario = (getUserProfile().getNombreCompleto() != null ? getUserProfile().getNombreCompleto() + " "
                : "") + (getUserProfile().getPrimerApellido() != null ? getUserProfile().getPrimerApellido() + " " : "")
                + (getUserProfile().getSegundoApellido() != null ? getUserProfile().getSegundoApellido() : "");
        TramiteDTO tramiteDto = generarObservacionFacade.obtenerTramite(getDatosBandejaTareaDTO().getNumeroAsunto());
        fechaMinimaExFondo = diasHabilesHelper.obtenerFechaDiasHabiles(tramiteDto.getFechaRecepcion(),
                NumerosConstantes.CERO);
        fechaMaximaExcFondo = diasHabilesHelper.sumarDiasInhabiles(VistaConstantes.anio,
                tramiteDto.getFechaRecepcion());
        fechaMaximaExcFondo = diasHabilesHelper.obtenerFechaDiasHabiles(fechaMaximaExcFondo, NumerosConstantes.CERO);
        setDisableEliminar(true);
        setDisableElimResol(true);
        /** carga de documentos de exclusivo de fondo */
        documentosExclusivoFondo = obtenerDocumentosExclusivoFondo();
        setTablaExclusivoEditar(!abogadoDTO.getTramiteDTO().getEstadoProcesal().equals("Remitido"));
    }

    /**
     * Metodo para verificar el momento en el que se cambia el valor de el checkbox
     * de si es determinante de credito o no la resolucion
     */

    public void determinanteChangeEvent(AjaxBehaviorEvent changeEvent) {
        SelectBooleanCheckbox checkbox = (SelectBooleanCheckbox) changeEvent.getSource();
        if ((Boolean) checkbox.getValue()) {
            this.banderaMonto = false;
        } else {
            this.banderaMonto = true;
        }
    }

    /**
     * Metodo para evaluar si existen resoluciones impugandas duplicadas y enviar el
     * mensaje de medio de defensa.
     * 
     */

    public void validadListaResolucionesImpugnadas() {
        setBlnMensajeDefensa(false);
    }

    /**
     * Inicializacion de listas previamente guardadas de la resolucion.
     */
    public void setDatosIniciales() {
        resolucionesDTOList = new ArrayList<ResolucionImpugnadaDTO>();
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));
        SolicitudDTO solicitud = new SolicitudDTO();
        List<CatalogoDTO> catalogoDTOSource = new ArrayList<CatalogoDTO>();
        conceptoCats = new ArrayList<CatalogoDTO>();
        recursoCats = new ArrayList<CatalogoDTO>();
        recursoDualList = new ArrayList<CatalogoDTO>();
        conceptoCats.addAll((List<CatalogoDTO>) abogadoBussines.obtenerCatalogos("concepto"));
        recursoCats.addAll((List<CatalogoDTO>) abogadoBussines.obtenerCatalogos("recurso"));

        solicitud.setIdSolicitud(datosBandejaTareaDTO.getIdSolicitud());
        solicitud.setTipoTramite(datosBandejaTareaDTO.getTipoTramite());
        catalogoDTOSource.addAll(recursoCats);

        TramiteDTO dto = new TramiteDTO();
        dto.setNumeroAsunto(datosBandejaTareaDTO.getNumeroAsunto());
        abogadoDTO = autorizarResolucionBusiness.mostrarResoluciones(solicitud, dto);
        if (abogadoDTO.getResolucionesDTO() != null && !abogadoDTO.getResolucionesDTO().isEmpty()) {
            resolucionesDTOList.addAll(abogadoDTO.getResolucionesDTO());
        }
        resolDataModel = new ResolucionDataModel(resolucionesDTOList);
        updateMontoTotal();
        visibleRecursoPanel = true;
        if (abogadoDTO.getRecursos() != null) {
            recursoDualList.addAll(abogadoDTO.getRecursos());
            /*** Exclusivo Fondo */
            for (int j = 0; j < abogadoDTO.getRecursos().size(); j++) {
                if (abogadoDTO.getRecursos().get(j).getClave().equals(CatalogoConstantes.CAT_EXCLUSIVO_FONDO)) {
                    mostrarPanelExclusivoFondo = true;
                    getListaDocumentos().clear();
                    setListaDocumentos(getAnexarDocumentoBussines()
                            .obtenerDocumentosOficialesAnexadosExclusivoFondo(datosBandejaTareaDTO.getNumeroAsunto()));
                    setDocumentoOficialDataModel(new DocumentoOficialDataModel(getListaDocumentos()));
                    break;
                }
            }
        }
        for (CatalogoDTO catalogoDTOSelected : recursoDualList) {
            for (CatalogoDTO catalogoDTO : catalogoDTOSource) {
                if (catalogoDTOSelected.getClave().equals(catalogoDTO.getClave())) {
                    catalogoDTOSource.remove(catalogoDTO);
                    break;
                }
            }
        }
        resaltarResolucionMasImportante();
        recursoDual = new DualListModel<CatalogoDTO>(catalogoDTOSource, recursoDualList);
        banderaMonto = true;
        listConceptosDTOTable = null;
        selectedResol = null;
        visibleConceptoPanel = false;
        nuevoDocumento = false;
        validadListaResolucionesImpugnadas();

    }

    /**
     * Actualiza los recursos asignados a la resolucion
     */
    @SuppressWarnings("unchecked")
    public void actualizarRecursos(TransferEvent event) {
        List<CatalogoDTO> lista = (List<CatalogoDTO>) event.getItems();
        for (CatalogoDTO elemento : lista) {
            String clave = elemento.getClave();
            if (clave.equals(CatalogoConstantes.CAT_EXCLUSIVO_FONDO)) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("inicializarCombo()");
                setTablaExclusivoEditar(!abogadoDTO.getTramiteDTO().getEstadoProcesal()
                        .equals(EstadoTramite.REMITIDO.getDescripcion()));
                mostrarPanelExclusivoFondo = event.isAdd() || !getListaDocumentos().isEmpty();
                if (event.isRemove() && !getListaDocumentos().isEmpty()) {
                    this.agregarCaracteristicaEx();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            errorMsg.getString("exclusivo.documentos.existentes")));
                }
                break;
            }
        }
        abogadoDTO.setRecursos(recursoDual.getTarget());
    }

    private void agregarCaracteristicaEx() {
        List<CatalogoDTO> catalogoDTOSource = recursoDual.getSource();
        List<CatalogoDTO> catalogoDTOTarget = recursoDual.getTarget();
        int indice = NumerosConstantes.CERO;
        for (CatalogoDTO source : catalogoDTOSource) {
            if (source.getClave().equals(CatalogoConstantes.CAT_EXCLUSIVO_FONDO)) {
                catalogoDTOSource.remove(indice);
                catalogoDTOTarget.add(source);
                break;
            }
            indice++;
        }
        recursoDual.setSource(catalogoDTOSource);
        recursoDual.setTarget(catalogoDTOTarget);

    }

    /**
     * Actualiza los recursos asignados a la resolucion
     */
    public void rechazarResolucion(ActionEvent event) {
        autorizarResolucionBusiness.rechazarResolucion(getDatosBandejaTareaDTO().getNumeroAsunto(),
                getDatosBandejaTareaDTO().getIdTareaUsuario(), getUserProfile().getUsuario(),
                getDatosBandejaTareaDTO().getIdSolicitud());

        getFlash().put(GeneralConstantes.MENSAJE_REDIRECT, "La resoluci\u00F3n para el asunto "
                + getDatosBandejaTareaDTO().getNumeroAsunto() + " ha sido rechazada.");
        ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext
                .getCurrentInstance().getApplication().getNavigationHandler();

        configurableNavigationHandler.performNavigation(LoginConstante.BANDEA_JSF + "?faces-redirect=true");
    }

    /**
     * Actualiza los agravios asignados a la resolucion.
     */
    @SuppressWarnings("unchecked")
    public void actualizarResolucionImpugnada(TransferEvent event) {
        List<AgraviosDTO> itemsTransferred = (List<AgraviosDTO>) event.getItems();
        if (event.isAdd()) {
            List<AgraviosDTO> agrvs = selectedResol.getAgravios() != null ? selectedResol.getAgravios()
                    : new ArrayList<AgraviosDTO>();
            agrvs.addAll(itemsTransferred);
            getSelectedResol().setAgravios(agrvs);
        } else {
            List<AgraviosDTO> agrvs = selectedResol.getAgravios();
            agrvs.removeAll(itemsTransferred);
            getSelectedResol().setAgravios(agrvs);
        }
    }

    /**
     * Actualiza los agravios asignados a la resolucion.
     */
    public void siguienteAdjuntar() {
        if (validarExcluivoFondo()) {
            if (isMostrarPanelExclusivoFondo()) {
                if (getAnexarDocumentoValidator().validarDocumentosOficiales(getListaDocumentos())) {
                    getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
                    documentosComplementarios = autorizarResolucionBusiness
                            .obtenerDocumentosComplementariosAutorizacion(getDatosBandejaTareaDTO().getIdSolicitud());

                    if (documentosComplementarios == null || documentosComplementarios.isEmpty()) {
                        nuevoDocumento = false;
                        continuarSiguienteAdjuntar();
                    } else {
                        nuevoDocumento = true;
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            errorMsg.getString("vuj.resol.documentos.adjuntarAlMenosUnDocExFon.resolucion")));
                }
            } else {
                getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
                documentosComplementarios = autorizarResolucionBusiness
                        .obtenerDocumentosComplementariosAutorizacion(getDatosBandejaTareaDTO().getIdSolicitud());

                if (documentosComplementarios == null || documentosComplementarios.isEmpty()) {
                    nuevoDocumento = false;
                    continuarSiguienteAdjuntar();
                } else {
                    nuevoDocumento = true;
                }
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    errorMsg.getString("vuj.resol.documentos.adjuntarAlMenosUnDocExFon")));
        }
    }

    public void continuarSiguienteAdjuntar() {

        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());

        boolean validacion = guardarResolucionesImpugnadas(null);

        if (validacion) {
            getAdjuntarDocumentoController().inicializarAdjuntarDocumentos(datosBandejaTareaDTO,
                    TipoDocumentoOficial.OFICIO_RESOLUCION.getClave());
        } else {
            return;
        }

        ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext
                .getCurrentInstance().getApplication().getNavigationHandler();

        configurableNavigationHandler
                .performNavigation(AutorizarConstantes.ADJUNTAR_DOCUMENTO + "?faces-redirect=true");

    }

    public void regresarPaginaAutorizacion() {
        nuevoDocumento = false;
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());

    }

    /**
     * Metodo para restear resolucion
     */
    public void resetResol() {
        selectedResolTemporal.setMonto(montoSelTemporal);
        selectedResolTemporal.setDeterminanteCred(checkResolTemporal);
        selectedResol = selectedResolTemporal;

        RequestContext.getCurrentInstance().reset("resolucionForm:resolucionTable");
        selectedResol.setEditarResolSelected(false);
        if (!selectedResol.getDeterminanteCred()) {
            updateMontoResolucion();
        }
        resaltarResolucionMasImportante();
        setDisableElimResol(true);

    }

    private boolean validarExcluivoFondo() {
        boolean lbExclusivoFondo = true;
        if (isMostrarPanelExclusivoFondo()) {
            if (getListaDocumentos() != null && !getListaDocumentos().isEmpty()) {
                for (DocumentoOficialDTO objDocOfi : getListaDocumentos()) {
                    if (objDocOfi.getCveTipoDocumento()
                            .equals(TipoDocumentoOficial.OFICIO_ADMISION_SIN_AUDIENCIA.getClave())) {
                        if (objDocOfi.getFechaNotificacion() == null) {
                            lbExclusivoFondo = false;
                        }
                    } else if (objDocOfi.getCveTipoDocumento()
                            .equals(TipoDocumentoOficial.OFICIO_ADMISION_CON_AUDIENCIA.getClave())
                            || objDocOfi.getCveTipoDocumento()
                                    .equals(TipoDocumentoOficial.OFICIO_AUDIENCIA_PERITO.getClave())) {
                        if (objDocOfi.getFechaNotificacion() != null) {
                            if (!objDocOfi.getFechaNotificacion().before(objDocOfi.getFechaAudiencia())) {
                                lbExclusivoFondo = false;
                            }
                        } else {
                            lbExclusivoFondo = false;
                        }
                    } else if (objDocOfi.getCveTipoDocumento()
                            .equals(TipoDocumentoOficial.OFICIO_CAMBIO_FECHA.getClave())) {
                        if (getListaDocumentos() != null && !getListaDocumentos().isEmpty()) {
                            List<DocumentoOficialDTO> objLstDocOF = new ArrayList<DocumentoOficialDTO>(
                                    getListaDocumentos());
                            for (DocumentoOficialDTO objDocOF : objLstDocOF) {
                                if (objDocOF.getCveTipoDocumento()
                                        .equals(TipoDocumentoOficial.OFICIO_ADMISION_CON_AUDIENCIA.getClave())) {
                                    if (objDocOF.getFechaNotificacion() != null) {
                                        if (!objDocOfi.getFechaAudiencia().after(objDocOF.getFechaAudiencia())) {
                                            lbExclusivoFondo = false;
                                            break;
                                        }
                                    } else {
                                        lbExclusivoFondo = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return lbExclusivoFondo;
    }

    /**
     * Metodo para el guardado final de la resolucion.
     */
    public boolean guardarResolucionesImpugnadas(ActionEvent actionEvent) {
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        if (resolucionesDTOList.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg.getString("vuj.resolucion.vacio")));
            return false;
        } else {
            for (ResolucionImpugnadaDTO resolucionImpugnadaDTO : resolucionesDTOList) {
                if (!(resolucionImpugnadaDTO.getDeterminanteCred()) && resolucionImpugnadaDTO.getConceptos() != null
                        && resolucionImpugnadaDTO.getConceptos().isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            errorMsg.getString("vuj.resolucion.resolCred")));
                    return false;

                }
                if (resolucionImpugnadaDTO.getIdeSentidoResolucion() == null
                        || resolucionImpugnadaDTO.getIdeSentidoResolucion().toString().equals("")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "El Sentido es requerido", "Seleccione un sentido de resoluci\u00F3n"));
                    return false;
                } else {
                    if (sentidosResolucionPerdedores
                            .containsKey(resolucionImpugnadaDTO.getIdeSentidoResolucion().getClave())) {
                        if (resolucionImpugnadaDTO.getAgravios() == null
                                || resolucionImpugnadaDTO.getAgravios().isEmpty()) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR, "El Agravio es requerido",
                                    errorMsg.getString("vuj.resol.impugnada.desfavorable.tenerAlMenosUnAgravio")));
                            return false;
                        }
                    }
                }

            }
            boolean lbDoc = true;
            if (isMostrarPanelExclusivoFondo()) {
                if (isTablaExclusivoEditar() && validaCaracteristicaExcl()) {
                    lbDoc = guardarDocumentos();
                    getListaDocumentos().clear();
                    setListaDocumentos(getAnexarDocumentoBussines()
                            .obtenerDocumentosOficialesAnexadosExclusivoFondo(datosBandejaTareaDTO.getNumeroAsunto()));
                    setDocumentoOficialDataModel(new DocumentoOficialDataModel(getListaDocumentos()));
                    setBanderaGuardar(getListaDocumentos().isEmpty());
                    RequestContext.getCurrentInstance().update("exFondoForm:tablaDocumentos");
                }
            }
            if (lbDoc) {
                abogadoDTO.setResolucionesDTO(resolucionesDTOList);
                abogadoDTO.setMontoTotalControvertido(montoTotalControvertido);
                autorizarResolucionBusiness.guardarResolucion(abogadoDTO);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "",
                        errorMsg.getString("vuj.resolucion.guardada")));
                for (ResolucionImpugnadaDTO resolucion : resolucionesDTOList) {
                    resolucion.setEditarResolSelected(false);
                }
            }
        }
        RequestContext.getCurrentInstance().reset("resolucionForm");
        setVisibleConceptoPanel(false);
        setDisableElimResol(true);
        return true;
    }

    private boolean validaCaracteristicaExcl() {
        boolean banCaracteristicaExl = false;
        List<CatalogoDTO> catalogoDTOTarget = recursoDual.getTarget();
        for (CatalogoDTO targetDto : catalogoDTOTarget) {
            if (targetDto.getClave().equals(CatalogoConstantes.CAT_EXCLUSIVO_FONDO)) {
                banCaracteristicaExl = true;
                break;
            }
        }
        if (!banCaracteristicaExl && !getListaDocumentos().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    errorMsg.getString("exclusivo.documentos.existentes")));
        }
        return banCaracteristicaExl;
    }

    /**
     * M&eacute;todo para firmar al momento de autorizar solicitud.
     */
    public void firmar() {

    }

    /**
     * M&eacute;todo para agregar una resolucion nueva.
     */
    public void agregarResolucion(ActionEvent actionEvent) {
        visibleConceptoPanel = false;
        resol.setIdTramite(getDatosBandejaTareaDTO().getNumeroAsunto());
        ResolucionImpugnadaDTO resolucion = abogadoBussines.validarResolucionExistente(resol);
        Map<Integer, String> mnsg = abogadoValidator.resolucionNueva(resolucion, resol.getResImpugnada(),
                this.resolucionesDTOList);
        if (mnsg.containsKey(AbogadoConstantes.RESOLUCION_REPETIDA)) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", mnsg.get(AbogadoConstantes.RESOLUCION_REPETIDA)));
            resol = new ResolucionImpugnadaDTO();
        } else {
            if (mnsg.containsKey(AbogadoConstantes.RESOLUCION_DUPLICADA)) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        mnsg.get(AbogadoConstantes.RESOLUCION_DUPLICADA), ""));
            }
            if (!determinanteCred) {
                montoValor = BigDecimal.ZERO;
            }
            ResolucionImpugnadaDTO resolucionesDTO = new ResolucionImpugnadaDTO();
            resolucionesDTO.setId(Long.valueOf(indexResolucion++));
            resolucionesDTO.setAutoridadEmisora(resol.getAutoridadEmisora());
            resolucionesDTO.setDeterminanteCred(determinanteCred);
            resolucionesDTO.setFechaEmision(resol.getFechaEmision());
            resolucionesDTO.setMonto(this.montoValor == null ? BigDecimal.ZERO : this.montoValor);
            resolucionesDTO.setProcedimiento(resol.getProcedimiento());
            resolucionesDTO.setResImpugnada(resol.getResImpugnada());
            resolucionesDTO.setIdeSentidoResolucion(resol.getIdeSentidoResolucion());

            resolucionesDTOList.add(resolucionesDTO);

            resol = new ResolucionImpugnadaDTO();
            montoValor = BigDecimal.ZERO;

            updateMontoTotal();
            resaltarResolucionMasImportante();
            for (ResolucionImpugnadaDTO resolImp : resolucionesDTOList) {
                resolImp.setEditarResolSelected(false);

            }
        }
        if (mnsg.containsKey(AbogadoConstantes.RESOLUCION_DUPLICADA)
                || mnsg.containsKey(AbogadoConstantes.RESOLUCION_REPETIDA)) {
            montoValor = BigDecimal.ZERO;
            banderaMonto = true;
        }
        determinanteCred = false;
        banderaMonto = true;
        this.selectedResol = null;
        setDisableElimResol(true);
    }

    public void resaltarResolucionMasImportante(RowEditEvent event) {
        resaltarResolucionMasImportante();
    }

    /**
     * M&eacute;todo para identificar la resolucion mas importante.
     */
    private void resaltarResolucionMasImportante() {
        List<ResolucionImpugnadaDTO> resolucionesImpugnadaDTOs = new ArrayList<ResolucionImpugnadaDTO>();
        resolucionesImpugnadaDTOs.addAll(resolucionesDTOList);
        ResolucionImpugnadaDTO impugnadaImportanteDTO = resolucionMasImpotanteHelper
                .obtenerResolucionImportante(resolucionesDTOList);

        for (ResolucionImpugnadaDTO resolucionImpugnadaDTO : resolucionesDTOList) {
            if (resolucionImpugnadaDTO.getResImpugnada().equals(impugnadaImportanteDTO.getResImpugnada())) {
                resolucionImpugnadaDTO.setImportante(true);

            } else {
                resolucionImpugnadaDTO.setImportante(false);
            }
        }
        visibleConceptoPanel = false;

        validadListaResolucionesImpugnadas();
    }

    /**
     * M&eacute;todo para guardar los conceptos a la resolucion seleccionada.
     */
    public void guardarConcepto(ActionEvent actionEvent) {
        conceptosDTO.setImporte(new BigDecimal(getImporteValor()));
        Map<Integer, String> mnsg = abogadoValidator.conceptoNuevo(conceptosDTO, listConceptosDTOTable);
        if (mnsg != null && !mnsg.isEmpty() && mnsg.containsKey(AbogadoConstantes.CONCEPTO_DUPLICADO)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, mnsg.get(AbogadoConstantes.CONCEPTO_DUPLICADO), ""));
            conceptosDTO = new ConceptosDTO();
            setImporteValor(null);
        } else {
            conceptosDTO.setId((new Date()).getTime());
            conceptosDTO.setImporte(new BigDecimal(importeValor));
            importeValor = "";
            listConceptosDTOTable.add(conceptosDTO);
            conceptoDataModel = new ConceptoDataModel(listConceptosDTOTable);
            selectedResol.setConceptos(listConceptosDTOTable);
            updateMontoResolucion();
            updateMontoTotal();
            resaltarResolucionMasImportante();
            conceptosDTO = new ConceptosDTO();
            banderaRemove = false;
            deleteMaxResolValue();
            resetConcepto();
        }
        setDisableEliminar(true);
    }

    public void respaldoConcepto(RowEditEvent event) {
        respaldoConceptosDTOTable = new ArrayList<ConceptosDTO>();
        for (ConceptosDTO con : listConceptosDTOTable) {
            ConceptosDTO con2 = new ConceptosDTO();
            con2.setConceptoValor(con.getConceptoValor());
            con2.setEditarConcepto(con.isEditarConcepto());
            con2.setId(con.getId());
            con2.setImporte(con.getImporte());

            respaldoConceptosDTOTable.add(con2);
        }
        disableEliminar = false;
    }

    /**
     * Metodo para cancelar
     */
    public void cancelConcepto() {

        setConceptoDataModel(new ConceptoDataModel(respaldoConceptosDTOTable));
        resetConcepto();
        importeValor = "";
        disableEliminar = true;
    }

    /**
     * M&eacute;todo para guardar en el concepto el valor modificado.
     */
    public void editarConcepto(RowEditEvent event) {

        ConceptosDTO concepto = ((ConceptosDTO) event.getObject());
        for (ConceptosDTO resp : respaldoConceptosDTOTable) {
            if (concepto.getId().intValue() == resp.getId().intValue()) {
                concepto.setImporte(resp.getImporte());
            }
        }

        ConceptosDTO conceptoVal = new ConceptosDTO();
        conceptoVal.setImporte(new BigDecimal(importeValor));
        conceptoVal.setConceptoValor(concepto.getConceptoValor());
        conceptoVal.setEditarConcepto(concepto.isEditarConcepto());
        conceptoVal.setId(concepto.getId());

        Map<Integer, String> mnsg = abogadoValidator.conceptoNuevo(conceptoVal, listConceptosDTOTable);
        if (mnsg != null && !mnsg.isEmpty() && mnsg.containsKey(AbogadoConstantes.CONCEPTO_DUPLICADO)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, mnsg.get(AbogadoConstantes.CONCEPTO_DUPLICADO), ""));
            FacesContext.getCurrentInstance().validationFailed();
        } else {
            concepto.setImporte(new BigDecimal(importeValor));
            updateMontoResolucion();
            updateMontoTotal();
            resaltarResolucionMasImportante();
            importeValor = "";
            SelectEvent sel = new SelectEvent(event.getComponent(), event.getBehavior(), getSelectedResol());
            onRowSelect(sel);
            resetConcepto();
            disableEliminar = true;
        }
        disableEliminar = true;
        RequestContext.getCurrentInstance().reset("resolucionForm");
    }

    /**
     * Metodo para actualizar el monto total.
     */
    public void resetConcepto() {
        for (ConceptosDTO concepto : listConceptosDTOTable) {
            concepto.setEditarConcepto(false);
        }
        setSelectedConceptos(null);
        disableEliminar = true;
        RequestContext.getCurrentInstance().reset("infoAdicionalForm");

    }

    /**
     * Metodo para actualizar el monto total.
     */
    public void resetCamposConcepto() {
        setImporteValor("");
        conceptosDTO = new ConceptosDTO();
        RequestContext.getCurrentInstance().reset("infoAdicionalForm");

    }

    /**
     * 
     * @param editEvent
     */
    public void editarResolucion(RowEditEvent editEvent) {

        this.selectedResol = (ResolucionImpugnadaDTO) editEvent.getObject();
        this.selectedResol.setIdTramite(getDatosBandejaTareaDTO().getNumeroAsunto());
        ResolucionImpugnadaDTO resolucion = abogadoBussines.validarResolucionExistente(selectedResol);
        this.ultimoValorResolucion = this.selectedResol.getMonto();
        getLogger().debug("Editing row ultimo resol monto {} ", this.ultimoValorResolucion);

        Map<Integer, String> mnsg = abogadoValidator.resolucionNueva(resolucion, selectedResol.getResImpugnada(),
                this.resolucionesDTOList);

        if (mnsg.containsKey(AbogadoConstantes.RESOLUCION_DUPLICADA)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, mnsg.get(AbogadoConstantes.RESOLUCION_DUPLICADA), ""));
        }

        if (mnsg.containsKey(AbogadoConstantes.RESOLUCION_REPETIDA)) {
            banderaResolucionImpugnada = false;
        }

        if (selectedResol.getDeterminanteCred()) {
            this.selectedResol.setConceptos(null);

            selectedResol = null;
            conceptoDataModel = new ConceptoDataModel(new ArrayList<ConceptosDTO>());
        } else {
            BigDecimal monto = BigDecimal.ZERO;
            if (this.selectedResol.getConceptos() != null) {
                for (ConceptosDTO concepto : this.selectedResol.getConceptos()) {
                    monto = monto.add(concepto.getImporte());
                }
            }
            this.selectedResol.setMonto(monto);
            selectedResol = null;
        }

        visibleConceptoPanel = false;
        resaltarResolucionMasImportante();
        updateMontoTotal();
        actualizaEdicionResolucion();
        banderaResolucionImpugnada = true;
        selectedResol = null;
        setVisibleAgravios(false);
        setDisableElimResol(true);
        RequestContext.getCurrentInstance().reset("resolucionForm");

    }

    /**
     * Metodo que actualiza la opcion de edicion de un concepto seleccionado
     */
    public void actualizaEdicionResolucion() {
        for (ResolucionImpugnadaDTO resolucion : resolucionesDTOList) {
            if (selectedResol != null && resolucion.getId().longValue() == selectedResol.getId().longValue()) {
                resolucion.setEditarResolSelected(true);
            } else {
                resolucion.setEditarResolSelected(false);
            }
        }
    }

    /**
     * M&eacute;todo para actualizar el monto total.
     */
    private void updateMontoTotal() {
        setMontoTotalControvertido(BigDecimal.ZERO);
        for (ResolucionImpugnadaDTO resolucion : this.resolucionesDTOList) {
            if (resolucion.getMonto() != null) {
                BigDecimal bg = resolucion.getMonto();
                setMontoTotalControvertido(getMontoTotalControvertido().add(bg));
            }
        }
    }

    /**
     * @return the montoTotalControvertido
     */
    public BigDecimal getMontoTotalControvertido() {
        return montoTotalControvertido;
    }

    /**
     * Metodo para validar el remover un concepto.
     * 
     * @return
     */
    public boolean validateRemoveConcepto() {
        if (selectedConceptos == null || (selectedConceptos.length == 0)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    errorMsg.getString("vuj.resolucion.conceptoObligatorio")));
            return false;
        } else {
            return true;

        }
    }

    /**
     * Metodo para validar al remover un concepto.
     */
    public void validRemoveConcepto() {
        if (selectedConceptos == null || (selectedConceptos.length == 0)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    errorMsg.getString("vuj.resolucion.conceptoObligatorio")));
            banderaRemove = false;
        } else {
            errorConceptoMessage = "";
            banderaRemove = true;
            setImporteValor(null);

        }

    }

    public void onRowEditInit() {
        inhabilitarGuardar = true;
    }
    
    public void onRowSelect(SelectEvent event) {
        inhabilitarGuardar = true;
        RequestContext.getCurrentInstance().reset("resolucionForm:panelForm");
        setImporteValor("");
        this.selectedResol = (ResolucionImpugnadaDTO) event.getObject();
        this.selectedResolTemporal = (ResolucionImpugnadaDTO) event.getObject();
        this.montoSelTemporal = selectedResolTemporal.getMonto();
        this.checkResolTemporal = selectedResolTemporal.getDeterminanteCred();
        if (!selectedResol.getDeterminanteCred()) {
            visibleConceptoPanel = true;
        } else {
            visibleConceptoPanel = false;
        }
        if (selectedResol.getConceptos() != null) {
            listConceptosDTOTable = selectedResol.getConceptos();
            for (Integer i = 0; i < listConceptosDTOTable.size(); i++) {
                if (listConceptosDTOTable.get(i).getId() == null) {
                    listConceptosDTOTable.get(i).setId(i.longValue());
                }
            }
            conceptoDataModel = new ConceptoDataModel(listConceptosDTOTable);
        } else {
            listConceptosDTOTable = new ArrayList<ConceptosDTO>();
            conceptoDataModel = new ConceptoDataModel(listConceptosDTOTable);
        }
        if (abogadoDTO.getRecursos() != null) {
            for (int i = 0; i < recursoDual.getSource().size(); i++) {
                for (int j = 0; j < abogadoDTO.getRecursos().size(); j++) {
                    if (recursoDual.getSource().get(i).equals(abogadoDTO.getRecursos().get(j))) {
                        recursoDual.getSource().remove(i);
                        break;
                    }
                }
            }
            recursoDual.setTarget(abogadoDTO.getRecursos());
        }

        actualizaEdicionResolucion();
        actualizarAgravios();
        selectedConceptos = null;
        RequestContext.getCurrentInstance().reset("infoAdicionalForm:conceptoPanel");
        setConceptosDTO(new ConceptosDTO());
        setDisableElimResol(false);

    }

    /**
     * M&eacute;todo para actualizar agravios dependiendo de la resolucion
     * seleccionada.
     */
    public void actualizarAgravios() {
        List<AgraviosDTO> sourceList = new ArrayList<AgraviosDTO>();
        List<AgraviosDTO> targetList = new ArrayList<AgraviosDTO>();
        boolean flag = false;
        if (selectedResol.getAgravios().isEmpty()) {
            sourceList.addAll(agraviosSource);
        } else {
            for (AgraviosDTO catalogo : agraviosSource) {
                flag = false;
                for (AgraviosDTO catalogoSource : selectedResol.getAgravios()) {
                    if (catalogo.getClave().equals(catalogoSource.getClave())) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    targetList.add(catalogo);
                } else {
                    sourceList.add(catalogo);
                }
            }
        }

        agravios.setSource(sourceList);
        agravios.setTarget(targetList);
        setVisibleAgravios(true);
    }

    /**
     * M&eacute;todo acutalizar la vista cuando se deselecciona una resolucion.
     */
    public void onRowUnselect(UnselectEvent event) {
        inhabilitarGuardar = true;
        this.selectedResol = new ResolucionImpugnadaDTO();
        visibleConceptoPanel = false;
        setVisibleAgravios(false);
        setDisableElimResol(true);
    }

    /**
     * M&eacute;todo para actualizar el concepto seleccionado.
     */
    public void conceptoRowSelect(SelectEvent event) {
        this.selectConceptosDTO = (ConceptosDTO) event.getObject();
    }

    /**
     * M&eacute;todo para eliminar una resolucion impungada.
     */
    public void removeRow(ActionEvent actionEvent) {
        visibleConceptoPanel = false;
        montoValor = BigDecimal.ZERO;
        if (resolucionesDTOList.isEmpty()) {
            return;
        }
        resolucionesDTOList.remove(this.selectedResol);
        listConceptosDTOTable.clear();
        if (resolucionesDTOList.isEmpty()) {
            visibleRecursoPanel = false;
            mostrarPanelExclusivoFondo = false;
            visibleConceptoPanel = false;
        }
        resaltarResolucionMasImportante();
        for (ResolucionImpugnadaDTO resolImp : resolucionesDTOList) {
            resolImp.setEditarResolSelected(false);

        }
        updateMontoTotal();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "",
                messages.getString("vuj.resol.resImpugnada.eliminada") + " " + datosBandejaTareaDTO.getNumeroAsunto()));
        setDisableElimResol(true);
        inhabilitarGuardar = true;
    }

    /**
     * M&eacute;todo para eliminar un concepto de la resolucion seleccionada.
     */
    public void removeConcepto() {
        banderaRemove = false;
        if (validateRemoveConcepto()) {
            List<ConceptosDTO> conceptosDTOs = new ArrayList<ConceptosDTO>();
            if (selectedConceptos != null) {
                for (ConceptosDTO conceptDTO : selectedConceptos) {
                    conceptosDTOs.add(conceptDTO);
                }
            }
            listConceptosDTOTable.removeAll(conceptosDTOs);
            updateMontoResolucion();
            updateMontoTotal();
            resaltarResolucionMasImportante();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "",
                    messages.getString("vuj.resolucion.eliminarConceptos")));
            banderaRemove = false;
            resetConcepto();
        }
        Behavior v = new BehaviorBase();
        UIComponent myComponent = FacesContext.getCurrentInstance().getViewRoot()
                .findComponent("infoAdicionalForm:resConcepto");
        SelectEvent sel = new SelectEvent(myComponent, v, getSelectedResol());
        onRowSelect(sel);
    }

    /**
     * Metodo que valida la eliminacion de un concepto.
     * 
     * @param actionEvent
     */
    public void validateRemoveConcepto(ActionEvent actionEvent) {
        if (selectedConceptos == null || (selectedConceptos.length == 0)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    errorMsg.getString("vuj.resolucion.conceptoObligatorio")));
        }
    }

    /**
     * M&eacute;todo para actualizar el monto de la resolucion seleccionada.
     */
    private void updateMontoResolucion() {
        BigDecimal montoT = BigDecimal.ZERO;
        for (ConceptosDTO concepto : listConceptosDTOTable) {
            montoT = montoT.add(concepto.getImporte());
        }

        selectedResol.setMonto(montoT);
    }

    /**
     * Metodo que actualiza la bandeja de resolucion impugnada
     * 
     * @param actionEvent
     */
    public void actualizarBanderaResolucionImpugnada(ActionEvent actionEvent) {
        setBanderaResolucionImpugnada(false);
    }

    /**
     * Metodo para cargar datosBandeja
     */
    public void cargaDatos() {
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        // Validar que sea cal o rrl
        ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext
                .getCurrentInstance().getApplication().getNavigationHandler();
        configurableNavigationHandler.performNavigation("/resources/pages/rrl/bandeja/bandeja.jsf?faces-redirect=true");
    }

    /**
     * Metodo que actualiza la opcion de edicion de una resolucion seleccionada
     * 
     * @param conceptoDM2
     */
    public void actualizaEdicionConceptos(ConceptosDTO concepto, boolean activo) {

        for (ConceptosDTO conceptoDM : listConceptosDTOTable) {

            if (conceptoDM.getId().longValue() == concepto.getId().longValue()) {
                conceptoDM.setEditarConcepto(activo);
            }

        }

    }

    public void rowSelectCheckboxSimple(SelectEvent event) {
        ConceptosDTO concepto = (ConceptosDTO) event.getObject();

        for (ConceptosDTO conceptoDM : listConceptosDTOTable) {

            if (conceptoDM.getId().longValue() == concepto.getId().longValue()) {
                conceptoDM.setEditarConcepto(true);
            } else {
                conceptoDM.setEditarConcepto(false);
            }

        }

        setDisableEliminar(false);
    }

    public void rowSelectCheckbox(SelectEvent event) {
        ConceptosDTO concepto = (ConceptosDTO) event.getObject();

        actualizaEdicionConceptos(concepto, true);

        setDisableEliminar(false);
    }

    public void rowUnselectCheckbox(UnselectEvent event) {
        ConceptosDTO concepto = (ConceptosDTO) event.getObject();
        actualizaEdicionConceptos(concepto, false);
        if (null != getSelectedConceptos() && getSelectedConceptos().length > 0) {
            return;
        } else {
            setDisableEliminar(true);
        }
    }

    @Override
    public BaseCloudBusiness getBusinessBean() {
        return capturaSolicitudBussines;
    }

    @Override
    public List<DocumentoOficialDTO> getListaDocumentosAdjuntados() {
        return getListaDocumentos();
    }

    /**
     * 
     * @return resol
     */
    public ResolucionImpugnadaDTO getResol() {
        return resol;
    }

    /**
     * 
     * @param resol
     *            la resol a fijar.
     */
    public void setResol(ResolucionImpugnadaDTO resol) {
        this.resol = resol;
    }

    /**
     * 
     * @return resolDataModel
     */
    public ResolucionDataModel getResolDataModel() {
        return resolDataModel;
    }

    /**
     * 
     * @param resolDataModel
     *            la resolDataModel a fijar.
     */
    public void setResolDataModel(ResolucionDataModel resolDataModel) {
        this.resolDataModel = resolDataModel;
    }

    /**
     * 
     * @return conceptosDTO
     */
    public ConceptosDTO getConceptosDTO() {
        return conceptosDTO;
    }

    /**
     * 
     * @param conceptosDTO
     *            los conceptosDTO a fijar.
     */
    public void setConceptosDTO(ConceptosDTO conceptosDTO) {
        this.conceptosDTO = conceptosDTO;
    }

    /**
     * 
     * @return selectedConceptosDTO
     */
    public ConceptosDTO getSelectConceptosDTO() {
        return selectConceptosDTO;
    }

    /**
     * 
     * @param selectConceptosDTO
     *            el selectedConceptoDTO a fijar.
     */
    public void setSelectConceptosDTO(ConceptosDTO selectConceptosDTO) {
        this.selectConceptosDTO = selectConceptosDTO;
    }

    /**
     * 
     * @return selectedResol
     */
    public ResolucionImpugnadaDTO getSelectedResol() {
        return selectedResol;
    }

    /**
     * 
     * @param selectedResol
     *            la selectedResol a fijar.
     */
    public void setSelectedResol(ResolucionImpugnadaDTO selectedResol) {
        this.selectedResol = selectedResol;
    }

    /**
     * 
     * @return resolucionesDTOList
     */
    public List<ResolucionImpugnadaDTO> getResolucionesDTOList() {
        return resolucionesDTOList;
    }

    /**
     * 
     * @param resolucionesDTOList
     *            las resolucionesList a fijar.
     */
    public void setResolucionesDTOList(List<ResolucionImpugnadaDTO> resolucionesDTOList) {
        this.resolucionesDTOList = resolucionesDTOList;
    }

    /**
     * 
     * @return autoridadEmisoras
     */
    public List<UnidadAdministrativaDTO> getAutoridadEmisoras() {
        return autoridadEmisoras;
    }

    /**
     * 
     * @param autoridadEmisoras
     *            las autoridadEmisoras a fijar.
     */
    public void setAutoridadEmisoras(List<UnidadAdministrativaDTO> autoridadEmisoras) {
        this.autoridadEmisoras = autoridadEmisoras;
    }

    /**
     * 
     * @return conceptoCats
     */
    public List<CatalogoDTO> getConceptoCats() {
        return conceptoCats;
    }

    /**
     * 
     * @param conceptoCats
     *            el conceptoCats a fijar.
     */
    public void setConceptoCats(List<CatalogoDTO> conceptoCats) {
        this.conceptoCats = conceptoCats;
    }

    /**
     * 
     * @return recursoCats
     */
    public List<CatalogoDTO> getRecursoCats() {
        return recursoCats;
    }

    /**
     * 
     * @param recursoCats
     *            el recursoCats a fijar.
     */
    public void setRecursoCats(List<CatalogoDTO> recursoCats) {
        this.recursoCats = recursoCats;
    }

    /**
     * 
     * @return listConceptosDTOTable
     */
    public List<ConceptosDTO> getListConceptosDTOTable() {
        return listConceptosDTOTable;
    }

    /**
     * 
     * @param listConceptosDTOTable
     */
    public void setListConceptosDTOTable(List<ConceptosDTO> listConceptosDTOTable) {
        this.listConceptosDTOTable = listConceptosDTOTable;
    }

    /**
     * 
     * @return recursoDual
     */
    public DualListModel<CatalogoDTO> getRecursoDual() {
        return recursoDual;
    }

    /**
     * 
     * @param recursoDual
     *            el recursoDual a fijar.
     */
    public void setRecursoDual(DualListModel<CatalogoDTO> recursoDual) {
        this.recursoDual = recursoDual;
    }

    /**
     * 
     * @return conceptoDataModel
     */
    public ConceptoDataModel getConceptoDataModel() {
        return conceptoDataModel;
    }

    /**
     * 
     * @param conceptoDataModel
     *            el conceptoDataModel
     */
    public void setConceptoDataModel(ConceptoDataModel conceptoDataModel) {
        this.conceptoDataModel = conceptoDataModel;
    }

    /**
     * 
     * @return recursoDualList
     */
    public List<CatalogoDTO> getRecursoDualList() {
        return recursoDualList;
    }

    /**
     * 
     * @param recursoDualList
     *            el recursoDualList a fijar.
     */
    public void setRecursoDualList(List<CatalogoDTO> recursoDualList) {
        this.recursoDualList = recursoDualList;
    }

    /**
     * 
     * @return abogadoBussines
     */
    public AbogadoBussines getAbogadoBussines() {
        return abogadoBussines;
    }

    /**
     * 
     * @param abogadoBussines
     *            el abogadoBussiness a fijar
     */
    public void setAbogadoBussines(AbogadoBussines abogadoBussines) {
        this.abogadoBussines = abogadoBussines;
    }

    /**
     * 
     * @return msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 
     * @param msg
     *            el msg a fijar.
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 
     * @return errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 
     * @param errorMessage
     *            el errorMessage a fijar.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 
     * @return banderaGuardar
     */
    public boolean isBanderaGuardar() {
        return banderaGuardar;
    }

    /**
     * 
     * @param banderaGuardar
     *            la banderaGuardar a fijar.
     */
    public void setBanderaGuardar(boolean banderaGuardar) {
        this.banderaGuardar = banderaGuardar;
    }

    /**
     * 
     * @return listaProcedimientos
     */
    public List<CatalogoDTO> getListaProcedimientos() {
        return listaProcedimientos;
    }

    /**
     * 
     * @param listaProcedimientos
     *            la listaProcedimientos a fijar.
     */
    public void setListaProcedimientos(List<CatalogoDTO> listaProcedimientos) {
        this.listaProcedimientos = listaProcedimientos;
    }

    /**
     * 
     * @return abogadoDTO
     */
    public ResolucionAbogadoDTO getAbogadoDTO() {
        return abogadoDTO;
    }

    /**
     * 
     * @param abogadoDTO
     *            el abogadoDTO a fijar.
     */
    public void setAbogadoDTO(ResolucionAbogadoDTO abogadoDTO) {
        this.abogadoDTO = abogadoDTO;
    }

    /**
     * 
     * @return determinanteCred
     */
    public boolean isDeterminanteCred() {
        return determinanteCred;
    }

    /**
     * 
     * @param determinanteCred
     *            el determinanteCred a fijar.
     */
    public void setDeterminanteCred(boolean determinanteCred) {
        this.determinanteCred = determinanteCred;
    }

    /**
     * 
     * @return nombreFuncionario
     */
    public String getNombreFuncionario() {
        return nombreFuncionario;
    }

    /**
     * 
     * @param nombreFuncionario
     *            el nombreFuncionario a fijar.
     */
    public void setNombreFuncionario(String nombreFuncionario) {
        this.nombreFuncionario = nombreFuncionario;
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
     *            el/los datosBandejaDTO a fijar.
     */
    public void setDatosBandejaTareaDTO(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        this.datosBandejaTareaDTO = datosBandejaTareaDTO;
    }

    /**
     * 
     * @return visibleRecursoPanel
     */
    public boolean isVisibleRecursoPanel() {
        return visibleRecursoPanel;
    }

    /**
     * 
     * @param visibleRecursoPanel
     *            el visibleRecursoPanel a fijar.
     */
    public void setVisibleRecursoPanel(boolean visibleRecursoPanel) {
        this.visibleRecursoPanel = visibleRecursoPanel;
    }

    /**
     * 
     * @return visibleConceptoPanel
     */
    public boolean isVisibleConceptoPanel() {
        return visibleConceptoPanel;
    }

    /**
     * 
     * @param visibleConceptoPanel
     *            el visibleConceptoPanel a fijar.
     */
    public void setVisibleConceptoPanel(boolean visibleConceptoPanel) {
        this.visibleConceptoPanel = visibleConceptoPanel;
    }

    /**
     * 
     * @return resolucionGuardada
     */
    public boolean isResolucionGuardada() {
        return resolucionGuardada;
    }

    /**
     * 
     * @param resolucionGuardada
     *            la resolucionGuardada a fijar.
     */
    public void setResolucionGuardada(boolean resolucionGuardada) {
        this.resolucionGuardada = resolucionGuardada;
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
     *            los messages a fijar.
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
     *            el errorMsg a fijar.
     */
    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 
     * @return sentidosResolucion
     */
    public List<CatalogoDTO> getSentidosResolucion() {
        return sentidosResolucion;
    }

    /**
     * 
     * @param sentidosResolucion
     *            los sentidosResolucion a fijar.
     */
    public void setSentidosResolucion(List<CatalogoDTO> sentidosResolucion) {
        this.sentidosResolucion = sentidosResolucion;
    }

    /**
     * @return the sentidosResolucionPerdedores
     */
    public Map<String, CatalogoDTO> getSentidosResolucionPerdedores() {
        return sentidosResolucionPerdedores;
    }

    /**
     * @param sentidosResolucionPerdedores
     *            the sentidosResolucionPerdedores to set
     */
    public void setSentidosResolucionPerdedores(Map<String, CatalogoDTO> sentidosResolucionPerdedores) {
        this.sentidosResolucionPerdedores = sentidosResolucionPerdedores;
    }

    /**
     * 
     * @return agravios
     */
    public DualListModel<AgraviosDTO> getAgravios() {
        return agravios;
    }

    /**
     * 
     * @param agravios
     */
    public void setAgravios(DualListModel<AgraviosDTO> agravios) {
        this.agravios = agravios;
    }

    /**
     * 
     * @return agraviosSource
     */
    public List<AgraviosDTO> getAgraviosSource() {
        return agraviosSource;
    }

    /**
     * 
     * @param agraviosSource
     *            los agraviosSource a fijar
     */
    public void setAgraviosSource(List<AgraviosDTO> agraviosSource) {
        this.agraviosSource = agraviosSource;
    }

    /**
     * 
     * @return emitirResolucionesBusiness
     */
    public EmitirResolucionBusiness getEmitirResolucionBusiness() {
        return emitirResolucionBusiness;
    }

    /**
     * 
     * @param emitirResolucionBusiness
     */
    public void setEmitirResolucionBusiness(EmitirResolucionBusiness emitirResolucionBusiness) {
        this.emitirResolucionBusiness = emitirResolucionBusiness;
    }

    /**
     * 
     * @return autorizarBussiness
     */
    public AutorizarResolucionBussines getAutorizarResolucionBusiness() {
        return autorizarResolucionBusiness;
    }

    /**
     * 
     * @param autorizarBusiness
     *            el autorizarBussines a fijar.
     */
    public void setAutorizarResolucionBusiness(AutorizarResolucionBussines autorizarResolucionBusiness) {
        this.autorizarResolucionBusiness = autorizarResolucionBusiness;
    }

    /**
     * 
     * @return arreglo
     */
    public ConceptosDTO[] getSelectedConceptos() {
        if (selectedConceptos != null) {
            ConceptosDTO[] arreglo = new ConceptosDTO[selectedConceptos.length];
            for (int i = 0; i < selectedConceptos.length; i++) {
                ConceptosDTO c = selectedConceptos[i];
                arreglo[i] = c;
            }
            return arreglo;
        } else {
            return null;
        }
    }

    /**
     * 
     * @param selectedConceptos
     *            los selectedConceptos a fijar.
     */
    public void setSelectedConceptos(ConceptosDTO[] selectedConceptosArray) {
        if (selectedConceptosArray != null) {
            ConceptosDTO[] selectedConceptosLocal = selectedConceptosArray.clone();
            ConceptosDTO[] arreglo = new ConceptosDTO[selectedConceptosLocal.length];
            for (int i = 0; i < selectedConceptosLocal.length; i++) {
                ConceptosDTO c = selectedConceptosLocal[i];
                arreglo[i] = c;
            }
            this.selectedConceptos = arreglo;
        } else {
            this.selectedConceptos = null;
        }
    }

    /**
     * 
     * @return
     */
    public String getErrorConceptoMessage() {
        return errorConceptoMessage;
    }

    /**
     * 
     * @param errorConceptoMessage
     *            el errorConceptoMessage a fijar
     */
    public void setErrorConceptoMessage(String errorConceptoMessage) {
        this.errorConceptoMessage = errorConceptoMessage;
    }

    /**
     * 
     * @return autorizarValidator
     */
    public AutorizarValidator getAutorizarValidator() {
        return autorizarValidator;
    }

    /**
     * 
     * @param autorizarValidator
     *            el autorizarValidator a fijar.
     */
    public void setAutorizarValidator(AutorizarValidator autorizarValidator) {
        this.autorizarValidator = autorizarValidator;
    }

    /**
     * 
     * @return adjuntarDocumentoController
     */
    public AdjuntarDocumentoController getAdjuntarDocumentoController() {
        return adjuntarDocumentoController;
    }

    /**
     * 
     * @param adjuntarDocumentoController
     *            el adjuntarDocumentosController
     */
    public void setAdjuntarDocumentoController(AdjuntarDocumentoController adjuntarDocumentoController) {
        this.adjuntarDocumentoController = adjuntarDocumentoController;
    }

    /**
     * 
     * @return importeValor
     */
    public String getImporteValor() {
        return importeValor;
    }

    /**
     * 
     * @param importeValor
     *            el importeValor a fijar.
     */
    public void setImporteValor(String importeValor) {
        this.importeValor = importeValor;
    }

    /**
     * 
     * @return banderaRemove
     */
    public boolean isBanderaRemove() {
        return banderaRemove;
    }

    /**
     * 
     * @param banderaRemove
     *            la banderaRemove a fijar.
     */
    public void setBanderaRemove(boolean banderaRemove) {
        this.banderaRemove = banderaRemove;
    }

    /**
     * 
     * @return banderaMonto
     */
    public boolean isBanderaMonto() {
        return banderaMonto;
    }

    /**
     * 
     * @param banderaMonto
     *            la banderaMonto a fijar.
     */
    public void setBanderaMonto(boolean banderaMonto) {
        this.banderaMonto = banderaMonto;
    }

    /**
     * 
     * @return montoValor
     */
    public BigDecimal getMontoValor() {
        return montoValor;
    }

    /**
     * 
     * @param montoValor
     *            el montoValor a fijar.
     */
    public void setMontoValor(BigDecimal montoValor) {
        this.montoValor = montoValor;
    }

    /**
     * 
     * @return abogadoValidator
     */
    public AbogadoValidator getAbogadoValidator() {
        return abogadoValidator;
    }

    /**
     * 
     * @param abogadoValidator
     *            el abogadoValidator a fijar
     */
    public void setAbogadoValidator(AbogadoValidator abogadoValidator) {
        this.abogadoValidator = abogadoValidator;
    }

    public boolean isBanderaResolucionImpugnada() {
        return banderaResolucionImpugnada;
    }

    public void setBanderaResolucionImpugnada(boolean banderaResolucionImpugnada) {
        this.banderaResolucionImpugnada = banderaResolucionImpugnada;
    }

    /**
     * @return fechaMaxima
     */
    public Date getFechaMaxima() {
        return fechaMaxima != null ? (Date) fechaMaxima.clone() : null;
    }

    /**
     * @param fechaMaxima
     *            fechaMaxima a fijar
     */
    public void setFechaMaxima(Date fechaMaxima) {
        if (fechaMaxima != null) {
            this.fechaMaxima = (Date) fechaMaxima.clone();
        } else {
            this.fechaMaxima = null;
        }
    }

    public void banderaFalse() {
        banderaRemove = false;
    }

    /**
     * @return visibleAgravios
     */
    public boolean isVisibleAgravios() {
        return visibleAgravios;
    }

    /**
     * @param visibleAgravios
     *            visibleAgravios a fijar
     */
    public void setVisibleAgravios(boolean visibleAgravios) {
        this.visibleAgravios = visibleAgravios;
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
     * @return the documentosComplementarios
     */
    public List<DocumentoDTO> getDocumentosComplementarios() {
        return documentosComplementarios;
    }

    /**
     * @param documentosComplementarios
     *            the documentosComplementarios to set
     */
    public void setDocumentosComplementarios(List<DocumentoDTO> documentosComplementarios) {
        this.documentosComplementarios = documentosComplementarios;
    }

    /**
     * @param montoTotalControvertido
     *            the montoTotalControvertido to set
     */
    public void setMontoTotalControvertido(BigDecimal montoTotalControvertido) {
        this.montoTotalControvertido = montoTotalControvertido;
    }

    public void validaAcceso() {
        // Validacion de acceso a la funcionalidad para el rol de
        // oficialia
        this.validaAccesoRolEmpleado(RolesConstantes.ROL_EMPLEADO_ADMINISTRADOR);

    }

    public boolean isBlnMensajeDefensa() {
        return blnMensajeDefensa;
    }

    public void setBlnMensajeDefensa(boolean blnMensajeDefensa) {
        this.blnMensajeDefensa = blnMensajeDefensa;
    }

    public boolean isDisableEliminar() {
        return disableEliminar;
    }

    public void setDisableEliminar(boolean disableEliminar) {
        this.disableEliminar = disableEliminar;
    }

    public boolean isDisableElimResol() {
        return disableElimResol;
    }

    public void setDisableElimResol(boolean disableElimResol) {
        this.disableElimResol = disableElimResol;
    }

    public ResolucionMasImpotanteHelper getResolucionMasImpotanteHelper() {
        return resolucionMasImpotanteHelper;
    }

    public void setResolucionMasImpotanteHelper(ResolucionMasImpotanteHelper resolucionMasImpotanteHelper) {
        this.resolucionMasImpotanteHelper = resolucionMasImpotanteHelper;
    }

    // P
    public void restoreMaxResolValue() {

        getLogger().error("restored max resolucion monto set to 99999999999999.99");
    }

    // P
    public void deleteMaxResolValue() {

        getLogger().info("deleted max resolucion monto set to 100000000000000000");
    }

    // P
    public void montoResolChangeEvent(AjaxBehaviorEvent changeEvent) {

        getLogger().debug("montoResolChangeEvent check value {}, monto {}", this.checkMonto,
                this.selectedResol.getMonto());

    }

    public void actualizarSentidosResolucion(ValueChangeEvent changeEvent) {

        String numResol = (String) changeEvent.getComponent().getAttributes().get("numResol");
        CatalogoDTO sentido1 = (CatalogoDTO) changeEvent.getNewValue();
        for (ResolucionImpugnadaDTO rsl : resolucionesDTOList) {
            if (rsl.getResImpugnada().equals(numResol)) {
                rsl.setIdeSentidoResolucion(sentido1);
            }
        }
    }

    // P
    public void montoResolCheckChangeEvent(AjaxBehaviorEvent changeEvent) {

        SelectBooleanCheckbox checkbox = (SelectBooleanCheckbox) changeEvent.getSource();
        getLogger().debug("check value {}", checkbox.getValue());
        this.checkMonto = (Boolean) checkbox.getValue();
        if (this.checkMonto) {
            this.selectedResol.setMonto(new BigDecimal("0.00"));
            restoreMaxResolValue();
        } else {
            this.selectedResol.setMonto(this.ultimoValorResolucion);
            deleteMaxResolValue();
        }

    }

    public void setUltimoValorResolucion(BigDecimal ultimoValorResolucion) {
        this.ultimoValorResolucion = ultimoValorResolucion;
    }

    public BigDecimal getUltimoValorResolucion() {
        return ultimoValorResolucion;
    }

    /**
     * @return the indexResolucion
     */
    public int getIndexResolucion() {
        return indexResolucion;
    }

    /**
     * @param indexResolucion
     *            the indexResolucion to set
     */
    public void setIndexResolucion(int indexResolucion) {
        this.indexResolucion = indexResolucion;
    }

    /**
     * @return the checkMonto
     */
    public boolean isCheckMonto() {
        return checkMonto;
    }

    /**
     * @param checkMonto
     *            the checkMonto to set
     */
    public void setCheckMonto(boolean checkMonto) {
        this.checkMonto = checkMonto;
    }

    /**
     * @return the resolucionesImpHelper
     */
    public ResolucionesImpHelper getResolucionesImpHelper() {
        return resolucionesImpHelper;
    }

    /**
     * @param resolucionesImpHelper
     *            the resolucionesImpHelper to set
     */
    public void setResolucionesImpHelper(ResolucionesImpHelper resolucionesImpHelper) {
        this.resolucionesImpHelper = resolucionesImpHelper;
    }

    /**
     * @return the selectedResolTemporal
     */
    public ResolucionImpugnadaDTO getSelectedResolTemporal() {
        return selectedResolTemporal;
    }

    /**
     * @param selectedResolTemporal
     *            the selectedResolTemporal to set
     */
    public void setSelectedResolTemporal(ResolucionImpugnadaDTO selectedResolTemporal) {
        this.selectedResolTemporal = selectedResolTemporal;
    }

    /**
     * @return the montoSelTemporal
     */
    public BigDecimal getMontoSelTemporal() {
        return montoSelTemporal;
    }

    /**
     * @param montoSelTemporal
     *            the montoSelTemporal to set
     */
    public void setMontoSelTemporal(BigDecimal montoSelTemporal) {
        this.montoSelTemporal = montoSelTemporal;
    }

    /**
     * @return the checkResolTemporal
     */
    public Boolean getCheckResolTemporal() {
        return checkResolTemporal;
    }

    /**
     * @param checkResolTemporal
     *            the checkResolTemporal to set
     */
    public void setCheckResolTemporal(Boolean checkResolTemporal) {
        this.checkResolTemporal = checkResolTemporal;
    }

    public List<DocumentoOficialDTO> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<DocumentoOficialDTO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public List<GenericDTO> getDocumentosExclusivoFondo() {
        return documentosExclusivoFondo;
    }

    public void setDocumentosExclusivoFondo(List<GenericDTO> documentosExclusivoFondo) {
        this.documentosExclusivoFondo = documentosExclusivoFondo;
    }

    public CapturaSolicitudBussines getCapturaSolicitudBussines() {
        return capturaSolicitudBussines;
    }

    public void setCapturaSolicitudBussines(CapturaSolicitudBussines capturaSolicitudBussines) {
        this.capturaSolicitudBussines = capturaSolicitudBussines;
    }

    public AnexarDocumentoBussines getAnexarDocumentoBussines() {
        return anexarDocumentoBussines;
    }

    public void setAnexarDocumentoBussines(AnexarDocumentoBussines anexarDocumentoBussines) {
        this.anexarDocumentoBussines = anexarDocumentoBussines;
    }

    public AnexarDocumentoValidator getAnexarDocumentoValidator() {
        return anexarDocumentoValidator;
    }

    public void setAnexarDocumentoValidator(AnexarDocumentoValidator anexarDocumentoValidator) {
        this.anexarDocumentoValidator = anexarDocumentoValidator;
    }

    public Date getFechaNotifExclusivoFondo() {
        return fechaNotifExclusivoFondo != null ? (Date) fechaNotifExclusivoFondo.clone() : null;
    }

    public void setFechaNotifExclusivoFondo(Date fechaNotifExclusivoFondo) {
        if (fechaNotifExclusivoFondo != null) {
            this.fechaNotifExclusivoFondo = (Date) fechaNotifExclusivoFondo.clone();
        } else {
            this.fechaNotifExclusivoFondo = null;
        }
    }

    public Date getFechaAudienciaExclusivoFondo() {
        return fechaAudienciaExclusivoFondo != null ? (Date) fechaAudienciaExclusivoFondo.clone() : null;
    }

    public void setFechaAudienciaExclusivoFondo(Date fechaAudienciaExclusivoFondo) {
        if (fechaAudienciaExclusivoFondo != null) {
            this.fechaAudienciaExclusivoFondo = (Date) fechaAudienciaExclusivoFondo.clone();
        } else {
            this.fechaAudienciaExclusivoFondo = null;
        }
    }

    public void comboExclusivoChanged() {
        setFechaNotifExclusivoFondo(null);
        setFechaAudienciaExclusivoFondo(null);
    }

    public void fechaNotifExclusivoFondoChanged(SelectEvent event) {
        this.setFechaNotifExclusivoFondo((Date) event.getObject());
        agregarVisible = false;
        if (this.id.equals(TipoDocumentoOficial.OFICIO_ADMISION_SIN_AUDIENCIA.getClave())) {
            agregarVisible = true;
        }
        if (this.fechaAudienciaExclusivoFondo != null) {
            if (this.id.equals(TipoDocumentoOficial.OFICIO_ADMISION_CON_AUDIENCIA.getClave())
                    || this.id.equals(TipoDocumentoOficial.OFICIO_AUDIENCIA_PERITO.getClave())) {
                agregarVisible = validaOficioAudiencia(this.fechaNotifExclusivoFondo,
                        this.fechaAudienciaExclusivoFondo);
            } else if (this.id.equals(TipoDocumentoOficial.OFICIO_CAMBIO_FECHA.getClave())) {
                agregarVisible = validaOficioCambioFecha(this.fechaNotifExclusivoFondo,
                        this.fechaAudienciaExclusivoFondo);
            }
        }
    }

    public void fechaAudienciaExclusivoFondoChanged(SelectEvent event) {
        this.setFechaAudienciaExclusivoFondo((Date) event.getObject());
        agregarVisible = false;
        if (this.id.equals(TipoDocumentoOficial.OFICIO_ADMISION_CON_AUDIENCIA.getClave())
                || this.id.equals(TipoDocumentoOficial.OFICIO_AUDIENCIA_PERITO.getClave())) {
            agregarVisible = validaOficioAudiencia(this.fechaNotifExclusivoFondo, this.fechaAudienciaExclusivoFondo);
        }
        if (this.id.equals(TipoDocumentoOficial.OFICIO_CAMBIO_FECHA.getClave())) {
            agregarVisible = validaOficioCambioFecha(this.fechaNotifExclusivoFondo, this.fechaAudienciaExclusivoFondo);
        }
    }

    public void onRowEditListaDocumentosExFondo(RowEditEvent editEvent) {
        Date fechaNotifCorregida = null;
        Date fechaAudCorregida = null;
        DocumentoOficialDTO documentoSelec = (DocumentoOficialDTO) editEvent.getObject();
        if (documentoSelec.getCveTipoDocumento().equals(TipoDocumentoOficial.OFICIO_ADMISION_CON_AUDIENCIA.getClave())
                || documentoSelec.getCveTipoDocumento()
                        .equals(TipoDocumentoOficial.OFICIO_AUDIENCIA_PERITO.getClave())) {
            if (validaOficioAudiencia(documentoSelec.getFechaNotificacion(), documentoSelec.getFechaAudiencia())) {
                fechaNotifCorregida = documentoSelec.getFechaNotificacion();
                fechaAudCorregida = documentoSelec.getFechaAudiencia();
            } else {
                fechaNotifCorregida = fechaNotifOld != null ? fechaNotifOld : documentoSelec.getFechaNotificacion();
                fechaAudCorregida = fechaAudiOld != null ? fechaAudiOld : documentoSelec.getFechaAudiencia();
            }
        } else if (documentoSelec.getCveTipoDocumento()
                .equals(TipoDocumentoOficial.ACTA_AUDIENCIA_CON_CONTRIBUYENTE.getClave())
                || documentoSelec.getCveTipoDocumento()
                        .equals(TipoDocumentoOficial.ACTA_AUDIENCIA_SIN_CONTRIBUYENTE.getClave())
                || documentoSelec.getCveTipoDocumento().equals(TipoDocumentoOficial.ACTA_AUDIENCIA_PERITO.getClave())
                || documentoSelec.getCveTipoDocumento().equals(TipoDocumentoOficial.DICTAMEN_INICIAL_RRL.getClave())
                || documentoSelec.getCveTipoDocumento().equals(TipoDocumentoOficial.DICTAMEN_FINAL_RRL.getClave())
                || documentoSelec.getCveTipoDocumento().equals(TipoDocumentoOficial.INSTRUCTIVO_RRL.getClave())
                || documentoSelec.getCveTipoDocumento()
                        .equals(TipoDocumentoOficial.PROTOCOLO_AUDIENCIA_CONRIBUYENTE.getClave())
                || documentoSelec.getCveTipoDocumento()
                        .equals(TipoDocumentoOficial.PROTOCOLO_AUDIENCIA_PERITO.getClave())
                || documentoSelec.getCveTipoDocumento().equals(TipoDocumentoOficial.OTROS.getClave())
                || documentoSelec.getCveTipoDocumento()
                        .equals(TipoDocumentoOficial.ACTA_NOTIFICACION_CONTRIBUYENTE.getClave())
                || documentoSelec.getCveTipoDocumento()
                        .equals(TipoDocumentoOficial.ACTA_NOTIFICACION_PERITO.getClave())) {
            boolean enviarMensajeError = false;
            if (documentoSelec.getFechaAudiencia() != null) {
                fechaAudCorregida = null;
                enviarMensajeError = true;
            }
            if (documentoSelec.getFechaNotificacion() != null) {
                enviarMensajeError = true;
                fechaNotifCorregida = null;
            }
            if (enviarMensajeError) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                        errorMsg.getString("exclusivo.fecha.AudienciaNotif.documento")));
            }
        } else if (documentoSelec.getCveTipoDocumento()
                .equals(TipoDocumentoOficial.OFICIO_ADMISION_SIN_AUDIENCIA.getClave())) {
            if (documentoSelec.getFechaAudiencia() != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                        errorMsg.getString("exclusivo.fecha.Audiencia.documento")));
                fechaNotifCorregida = documentoSelec.getFechaNotificacion();
                fechaAudCorregida = null;
            }
        } else if (documentoSelec.getCveTipoDocumento().equals(TipoDocumentoOficial.OFICIO_CAMBIO_FECHA.getClave())) {
            if (validaOficioCambioFecha(documentoSelec.getFechaNotificacion(), documentoSelec.getFechaAudiencia())) {
                fechaNotifCorregida = documentoSelec.getFechaNotificacion();
                fechaAudCorregida = documentoSelec.getFechaAudiencia();
            } else {
                fechaNotifCorregida = fechaNotifOld != null ? fechaNotifOld : documentoSelec.getFechaNotificacion();
                fechaAudCorregida = fechaAudiOld != null ? fechaAudiOld : documentoSelec.getFechaAudiencia();
            }
        }
        for (DocumentoOficialDTO doc : getListaDocumentos()) {
            if (doc.getRutaAzure().equals(documentoSelec.getRutaAzure())) {
                doc.setFechaAudiencia(fechaAudCorregida);
                doc.setFechaNotificacion(fechaNotifCorregida);
            }
            doc.setEditarDocumSelect(false);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("docExFondoTableVar.unselectAllRows()");
        setEliminarVisible(false);
        this.fechaNotifOld = null;
        this.fechaAudiOld = null;
    }

    private boolean validaOficioAudiencia(Date fechaNotOCA, Date fechaAudiOCA) {
        boolean validaOCA = false;
        if (fechaNotOCA != null && fechaNotOCA.before(fechaAudiOCA)) {
            validaOCA = true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    errorMsg.getString("exclusivo.fecha.NotifError")));
        }
        return validaOCA;
    }

    private boolean validaOficioCambioFecha(Date fechaNotOCF, Date fechaAudiOCF) {
        List<DocumentoOficialDTO> listaDo = new ArrayList<DocumentoOficialDTO>(getListaDocumentos());
        boolean validaOCF = false;
        if (fechaNotOCF != null && fechaNotOCF.before(fechaAudiOCF)) {
            int contadorOACA = NumerosConstantes.CERO;
            for (DocumentoOficialDTO doc : listaDo) {
                if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.OFICIO_ADMISION_CON_AUDIENCIA.getClave())) {
                    contadorOACA++;
                    if (fechaAudiOCF.after(doc.getFechaAudiencia())) {
                        validaOCF = true;
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "", errorMsg.getString("exclusivo.fecha.CambioFechaAudiencia.error")));
                    }
                    break;
                }
            }
            if (contadorOACA == NumerosConstantes.CERO) {
                GenericDTO ofi = new GenericDTO(TipoDocumentoOficial.OFICIO_ADMISION_CON_AUDIENCIA.getClave(),
                        TipoDocumentoOficial.OFICIO_ADMISION_CON_AUDIENCIA.getDescripcion());
                boolean existeOACA = true;
                for (GenericDTO docuExFondo : this.documentosExclusivoFondo) {
                    if (docuExFondo.getClave().equals(ofi.getClave())) {
                        existeOACA = false;
                        break;
                    }
                }
                if (existeOACA) {
                    this.documentosExclusivoFondo.add(ofi);
                    setDocumentosExclusivoFondo(this.documentosExclusivoFondo);
                }

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                        errorMsg.getString("exclusivo.fecha.CambioFechaAudiencia.error")));
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    errorMsg.getString("exclusivo.fecha.NotifError")));
        }
        return validaOCF;
    }

    public void fechaNotifChanged(ValueChangeEvent e) {
        this.fechaNotifOld = (Date) e.getOldValue();
    }

    public void fechaAudiChanged(ValueChangeEvent e) {
        this.fechaAudiOld = (Date) e.getOldValue();
    }

    public boolean isMostrarPanelExclusivoFondo() {
        return mostrarPanelExclusivoFondo;
    }

    public void setMostrarPanelExclusivoFondo(boolean mostrarPanelExclusivoFondo) {
        this.mostrarPanelExclusivoFondo = mostrarPanelExclusivoFondo;
    }

    public DocumentoOficialDataModel getDocumentoOficialDataModel() {
        return documentoOficialDataModel;
    }

    public void setDocumentoOficialDataModel(DocumentoOficialDataModel documentoOficialDataModel) {
        this.documentoOficialDataModel = documentoOficialDataModel;
    }

    public void rowSelectCheckboxTablaDocumentos(SelectEvent event) {
        if (isTablaExclusivoEditar()) {
            setEliminarVisible(true);
        }
        DocumentoOficialDTO documentoOficialDTOSel = (DocumentoOficialDTO) event.getObject();
        for (DocumentoOficialDTO doc : getListaDocumentos()) {
            if (doc.getRutaAzure().equals(documentoOficialDTOSel.getRutaAzure())) {
                doc.setEditarDocumSelect(true);
            }
        }
        RequestContext.getCurrentInstance().update("exFondoForm:scrollExclusivoFondo");
    }

    public void rowSelectAllCheckboxTablaDocumentos(ToggleSelectEvent event) {
        boolean lbVisible = false;
        if (event.isSelected()) {
            if (getListaDocumentos() != null && !getListaDocumentos().isEmpty()) {
                lbVisible = true;
            }
        }
        if (isTablaExclusivoEditar()) {
            setEliminarVisible(lbVisible);
        } else {
            setEliminarVisible(false);
        }
        for (DocumentoOficialDTO doc : getListaDocumentos()) {
            doc.setEditarDocumSelect(false);
        }
    }

    public boolean isEliminarVisible() {
        return eliminarVisible;
    }

    public void setEliminarVisible(boolean eliminarVisible) {
        this.eliminarVisible = eliminarVisible;
    }

    public boolean isAgregarVisible() {
        return agregarVisible;
    }

    public void setAgregarVisible(boolean agregarVisible) {
        this.agregarVisible = agregarVisible;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DocumentoOficialDTO[] getRegistrosEliminar() {
        if (registrosEliminar != null) {
            DocumentoOficialDTO[] arreglo = new DocumentoOficialDTO[registrosEliminar.length];
            for (int i = 0; i < registrosEliminar.length; i++) {
                DocumentoOficialDTO doc = registrosEliminar[i];
                arreglo[i] = doc;
            }
            return arreglo;
        } else {
            return null;
        }
    }

    public void setRegistrosEliminar(DocumentoOficialDTO[] registrosEliminarArray) {
        if (registrosEliminarArray != null) {
            DocumentoOficialDTO[] registrosEliminarLocal = registrosEliminarArray.clone();
            DocumentoOficialDTO[] arreglo = new DocumentoOficialDTO[registrosEliminarLocal.length];
            for (int i = 0; i < registrosEliminarLocal.length; i++) {
                DocumentoOficialDTO doc = registrosEliminarLocal[i];
                arreglo[i] = doc;
            }
            this.registrosEliminar = arreglo.clone();
        } else {
            this.registrosEliminar = null;
        }
    }

    public void rowUnselectCheckboxTablaDocumentos(UnselectEvent event) {
        DocumentoOficialDTO documentoOficialDTOSel = (DocumentoOficialDTO) event.getObject();
        for (DocumentoOficialDTO doc : getListaDocumentos()) {
            if (doc.getRutaAzure().equals(documentoOficialDTOSel.getRutaAzure())) {
                doc.setEditarDocumSelect(false);
            }
        }
        if (getRegistrosEliminar().length < 1) {
            setEliminarVisible(false);
        }
    }

    public void eliminarDocumentos(ActionEvent event) {
        if (getRegistrosEliminar() != null && getRegistrosEliminar().length > 0) {
            for (DocumentoOficialDTO documento : getRegistrosEliminar()) {
                getListaDocumentos().remove(documento);
                if (documento.getIdDocumentoOficial() != 0) {
                    getAnexarDocumentoBussines().eliminaDocumentoOficial(documento.getIdDocumentoOficial());
                }
            }
            setEliminarVisible(false);
            setRegistrosEliminar(new DocumentoOficialDTO[registrosEliminar.length]);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "",
                    messages.getString("documentos.exclusivoFondo.eliminar")));
        }
        List<CatalogoDTO> listCaracteristicas = recursoDual.getSource();
        if (listCaracteristicas != null && listCaracteristicas.size() > 0) {
            for (CatalogoDTO cat : listCaracteristicas) {
                if (cat.getClave().equals(CatalogoConstantes.CAT_EXCLUSIVO_FONDO)) {
                    mostrarPanelExclusivoFondo = !getListaDocumentos().isEmpty();
                    break;
                }
            }
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("anexarBtnW.disable();");
    }

    /**
     * M&eacute;todo para adjuntar un documento
     */
    
    public void anexarDocumentoNube() {
        if (getId() != null && !getId().equals("")) {
            DocumentoOficialDTO documento = obtenerDocumentoOficialDTO(TipoDocumentoOficial.parse(getId()));
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
                RequestContext.getCurrentInstance().update("exFondoForm:scrollExclusivoFondo");
            }
        }
    }
    
    private boolean validaDocumentoAzure(DocumentoOficialDTO documento, List<DocumentoOficialDTO> list) {
        boolean ban = true;
        if(documento.getRutaAzure() != null  && !documento.getRutaAzure().equals("ERROR") ) {
            getLogger().debug("Ruta Azure: "+ documento.getRutaAzure());
            for(DocumentoOficialDTO doc :list) {
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
    
    private void verificaDocumentoAzure(DocumentoOficialDTO documento) {
        FacesMessage msg = new FacesMessage("", "Documento adjuntado correctamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        documento.setFechaAudiencia(getFechaAudienciaExclusivoFondo());
        documento.setFechaNotificacion(getFechaNotifExclusivoFondo());
        getListaDocumentos().add(documento);
        setDocumentoOficialDataModel(new DocumentoOficialDataModel(getListaDocumentos()));
        setId("");
        setFechaAudienciaExclusivoFondo(null);
        setFechaNotifExclusivoFondo(null);
        RequestContext.getCurrentInstance().update("exFondoForm:scrollExclusivoFondo");
    }

    private List<GenericDTO> obtenerDocumentosExclusivoFondo() {
        List<GenericDTO> docEx = new ArrayList<GenericDTO>();
        DocumentoDTO objDocDTO = getConsultasBussines().obtenerDocumentoSolicitudTipoDoc(
                datosBandejaTareaDTO.getIdSolicitud().toString(), CatalogoConstantes.ID_TIPO_DOC_SOL_CAM_FEC_AUD);
        boolean agregarDoc = false;
        if (objDocDTO != null) {
            agregarDoc = true;
        } else {
            docEx.add(new GenericDTO(TipoDocumentoOficial.OFICIO_ADMISION_CON_AUDIENCIA.getClave(),
                    TipoDocumentoOficial.OFICIO_ADMISION_CON_AUDIENCIA.getDescripcion()));
        }
        docEx.add(new GenericDTO(TipoDocumentoOficial.OFICIO_ADMISION_SIN_AUDIENCIA.getClave(),
                TipoDocumentoOficial.OFICIO_ADMISION_SIN_AUDIENCIA.getDescripcion()));
        docEx.add(new GenericDTO(TipoDocumentoOficial.ACTA_AUDIENCIA_CON_CONTRIBUYENTE.getClave(),
                TipoDocumentoOficial.ACTA_AUDIENCIA_CON_CONTRIBUYENTE.getDescripcion()));
        docEx.add(new GenericDTO(TipoDocumentoOficial.ACTA_AUDIENCIA_SIN_CONTRIBUYENTE.getClave(),
                TipoDocumentoOficial.ACTA_AUDIENCIA_SIN_CONTRIBUYENTE.getDescripcion()));
        if (agregarDoc) {
            docEx.add(new GenericDTO(TipoDocumentoOficial.OFICIO_CAMBIO_FECHA.getClave(),
                    TipoDocumentoOficial.OFICIO_CAMBIO_FECHA.getDescripcion()));
        }
        docEx.add(new GenericDTO(TipoDocumentoOficial.OFICIO_AUDIENCIA_PERITO.getClave(),
                TipoDocumentoOficial.OFICIO_AUDIENCIA_PERITO.getDescripcion()));
        docEx.add(new GenericDTO(TipoDocumentoOficial.ACTA_AUDIENCIA_PERITO.getClave(),
                TipoDocumentoOficial.ACTA_AUDIENCIA_PERITO.getDescripcion()));
        docEx.add(new GenericDTO(TipoDocumentoOficial.DICTAMEN_INICIAL_RRL.getClave(),
                TipoDocumentoOficial.DICTAMEN_INICIAL_RRL.getDescripcion()));
        docEx.add(new GenericDTO(TipoDocumentoOficial.DICTAMEN_FINAL_RRL.getClave(),
                TipoDocumentoOficial.DICTAMEN_FINAL_RRL.getDescripcion()));
        docEx.add(new GenericDTO(TipoDocumentoOficial.INSTRUCTIVO_RRL.getClave(),
                TipoDocumentoOficial.INSTRUCTIVO_RRL.getDescripcion()));
        docEx.add(new GenericDTO(TipoDocumentoOficial.PROTOCOLO_AUDIENCIA_CONRIBUYENTE.getClave(),
                TipoDocumentoOficial.PROTOCOLO_AUDIENCIA_CONRIBUYENTE.getDescripcion()));
        docEx.add(new GenericDTO(TipoDocumentoOficial.PROTOCOLO_AUDIENCIA_PERITO.getClave(),
                TipoDocumentoOficial.PROTOCOLO_AUDIENCIA_PERITO.getDescripcion()));
        docEx.add(new GenericDTO(TipoDocumentoOficial.OTROS.getClave(), TipoDocumentoOficial.OTROS.getDescripcion()));
        docEx.add(new GenericDTO(TipoDocumentoOficial.ACTA_NOTIFICACION_CONTRIBUYENTE.getClave(),
                TipoDocumentoOficial.ACTA_NOTIFICACION_CONTRIBUYENTE.getDescripcion()));
        docEx.add(new GenericDTO(TipoDocumentoOficial.ACTA_NOTIFICACION_PERITO.getClave(),
                TipoDocumentoOficial.ACTA_NOTIFICACION_PERITO.getDescripcion()));
        return docEx;
    }

    /**
     * M&eacute;todo para almacenar los documentos a la solicitud
     */
    private boolean guardarDocumentos() {
        boolean lbOk = true;
        if (getAnexarDocumentoValidator().validarDocumentosOficiales(getListaDocumentos())) {
            try {
                getAnexarDocumentoBussines().guardarDocumentosOficialesExlusivoFondo(
                        getDatosBandejaTareaDTO().getNumeroAsunto(), getListaDocumentos());
                lbOk = true;
            } catch (ArchivoNoGuardadoException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                        errorMsg.getString("vuj.resol.documentos.adjuntarAlMenosUnDocExFon.errorGuardar")));
                lbOk = false;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    errorMsg.getString("vuj.resol.documentos.adjuntarAlMenosUnDocExFon")));
            lbOk = false;
        }
        return lbOk;
    }

    public void setGenerarObservacionFacade(GenerarObservacionFacade generarObservacionFacade) {
        this.generarObservacionFacade = generarObservacionFacade;
    }

    public void setDiasHabilesHelper(DiasHabilesHelper diasHabilesHelper) {
        this.diasHabilesHelper = diasHabilesHelper;
    }

    public Date getFechaMinimaExFondo() {
        return fechaMinimaExFondo != null ? (Date) fechaMinimaExFondo.clone() : null;
    }

    public void setFechaMinimaExFondo(Date fechaMinimaExFondo) {
        if (fechaMinimaExFondo != null) {
            this.fechaMinimaExFondo = (Date) fechaMinimaExFondo.clone();
        } else {
            this.fechaMinimaExFondo = null;
        }
    }

    public Date getFechaMaximaExcFondo() {
        return fechaMaximaExcFondo != null ? (Date) fechaMaximaExcFondo.clone() : null;
    }

    public void setFechaMaximaExcFondo(Date fechaMaximaExcFondo) {
        if (fechaMaximaExcFondo != null) {
            this.fechaMaximaExcFondo = (Date) fechaMaximaExcFondo.clone();
        } else {
            this.fechaMaximaExcFondo = null;
        }
    }

    public ConsultasBussines getConsultasBussines() {
        return consultasBussines;
    }

    public void setConsultasBussines(ConsultasBussines consultasBussines) {
        this.consultasBussines = consultasBussines;
    }

    public boolean isInhabilitarGuardar() {
        return inhabilitarGuardar;
    }

    public void setInhabilitarGuardar(boolean inhabilitarGuardar) {
        this.inhabilitarGuardar = inhabilitarGuardar;
    }

    public boolean isTablaExclusivoEditar() {
        return tablaExclusivoEditar;
    }

    public void setTablaExclusivoEditar(boolean tablaExclusivoEditar) {
        this.tablaExclusivoEditar = tablaExclusivoEditar;
    }

}
