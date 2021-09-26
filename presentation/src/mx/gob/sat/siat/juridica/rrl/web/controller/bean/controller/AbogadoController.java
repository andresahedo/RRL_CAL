/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.api.GenerarObservacionFacade;
import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.base.util.constante.CatalogoConstantes;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.ConsultasBussines;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.BaseCloudController;
import mx.gob.sat.siat.juridica.base.web.util.ResolucionMasImpotanteHelper;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.ConceptosDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionAbogadoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.AbogadoConstantes;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import mx.gob.sat.siat.juridica.rrl.util.helper.DiasHabilesHelper;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AbogadoBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AnexarDocumentoBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.CapturaSolicitudBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.emitirresolucion.EmitirResolucionBusiness;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.ConceptoDataModel;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoOficialDataModel;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.ResolucionDataModel;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility.ResolucionesImpHelper;
import mx.gob.sat.siat.juridica.rrl.web.util.validador.AbogadoValidator;
import mx.gob.sat.siat.juridica.rrl.web.util.validador.AnexarDocumentoValidator;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.context.RequestContext;
import org.primefaces.event.*;
import org.primefaces.model.DualListModel;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Clase que controla la pantalla de Abogado agregar resolu&oacute;n.
 * 
 * @author Softtek
 * 
 */
@ManagedBean
@ViewScoped
public class AbogadoController extends BaseCloudController<DocumentoOficialDTO> {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = -3858355910447665369L;

    /**
     * Lista de resoluciones a guardar.
     */
    private List<ResolucionImpugnadaDTO> resolucionesDTOList = new ArrayList<ResolucionImpugnadaDTO>();
    /**
     * Catalogo precargado de autoridades emisoras.
     */
    private List<UnidadAdministrativaDTO> autoridadEmisoras = new ArrayList<UnidadAdministrativaDTO>();
    private List<UnidadAdministrativaDTO> autoridadEmisorasVig = new ArrayList<UnidadAdministrativaDTO>();
    /**
     * Catalogo precardado de lista de procedimientos.
     */
    private List<CatalogoDTO> listaProcedimientos = new ArrayList<CatalogoDTO>();
    /**
     * Catalogo precargado de conceptos.
     */
    private List<CatalogoDTO> conceptoCats = new ArrayList<CatalogoDTO>();
    /**
     * Catalogo precargado de recursos.
     */
    private List<CatalogoDTO> recursoCats = new ArrayList<CatalogoDTO>();
    /**
     * Catalogo precargado de conceptos.
     */
    private List<ConceptosDTO> listConceptosDTOTable = new ArrayList<ConceptosDTO>();

    /**
     * Catalogo precargado de conceptos.
     */
    private List<ConceptosDTO> respaldoConceptosDTOTable = new ArrayList<ConceptosDTO>();
    /**
     * Catalogo precargado de recurso dual.
     */
    private List<CatalogoDTO> recursoDualList = new ArrayList<CatalogoDTO>();

    /**
     * Auxiliar para la resolucion de pantalla
     */
    private int indexResolucion;
    /**
     * Auxiliar para representar los conceptos en pantalla.
     */
    private int indexConcepto;
    /**
     * Variable que representa el monto total controvertido.
     */
    private BigDecimal montoTotalControvertido;
    /**
     * Variable para manejar los mensajes en pantalla.
     */
    private String msg;
    /**
     * Variable para manejar los mensajes de error en pantalla.
     */
    private String errorMessage;
    /**
     * Variable que identifica el abogado.
     */
    private String nombreFuncionario;
    /**
     * Variable que representa un mensaje de error en el concepto.
     */
    private String errorConceptoMessage;
    /**
     * Auxiliar para guardar.
     */
    private boolean banderaGuardar;
    /**
     * Auxiliar para el determinante crediticio
     */
    private boolean determinanteCred;
    /**
     * Auxiliar para verificar se muestre en pantalla el panel de concepto.
     */
    private boolean visibleConceptoPanel;
    /**
     * Importe
     */
    private String importeValor;
    /**
     * Monto
     */
    private BigDecimal montoValor;
    /**
     * Auxiliar para remove.
     */
    private boolean banderaRemove;
    /**
     * Atributo que contiene la observacion de un turnado
     */
    private String observacionTurnado;
    /**
     * Auxiliar para monto
     */
    private boolean banderaMonto;
    /**
     * Auxiliar para resolucion impugnada
     */
    private boolean banderaResolucionImpugnada;

    /**
     * Resolucion utilizada en la forma y en la tabla.
     */
    private ResolucionImpugnadaDTO resol = new ResolucionImpugnadaDTO();
    private ResolucionImpugnadaDTO selectedResol = new ResolucionImpugnadaDTO();
    private ResolucionImpugnadaDTO selectedResolTemporal = new ResolucionImpugnadaDTO();
    private BigDecimal montoSelTemporal;
    private Boolean checkResolTemporal;

    /** Campos de exclusivo de fondo **/
    private Date fechaNotifExclusivoFondo;
    private Date fechaAudienciaExclusivoFondo;
    private boolean mostrarPanelExclusivoFondo;

    /** Data model de documentos adjuntados */
    private DocumentoOficialDataModel documentoOficialDataModel;
    private boolean eliminarVisible;

    private boolean agregarVisible = false;

    /** ID del documento seleccionado para el apartado de exclusivo de fondo */
    private String id;
    private DocumentoOficialDTO[] registrosEliminar;

    private boolean tablaExclusivoEditar = true;

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
     * Bussiness que implementa la logica de negocio del abogado.
     */
    @ManagedProperty(value = "#{abogadoBussines}")
    private transient AbogadoBussines abogadoBussines;
    /**
     * Propiedad que representa la validacion de un abogado.
     */
    @ManagedProperty(value = "#{abogadoValidator}")
    private AbogadoValidator abogadoValidator;

    @ManagedProperty(value = "#{resolucionMasImpotanteHelper}")
    private ResolucionMasImpotanteHelper resolucionMasImpotanteHelper;

    @ManagedProperty(value = "#{resolucionesImpHelper}")
    private ResolucionesImpHelper resolucionesImpHelper;

    /**
     * Bean para conectar con el backend, atiende todas las peticiones de la emision
     * de resoluciones.
     */
    @ManagedProperty("#{emitirResolucionBusiness}")
    private transient EmitirResolucionBusiness emitirResolucionBusiness;
    /**
     * Controller para de remitir recurso revocacion.
     */

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
    /**
     * Catalogo de conceptos
     */
    private ConceptosDTO conceptosDTO = new ConceptosDTO();
    private DatosBandejaTareaDTO datosBandejaTareaDTO;
    /**
     * DataModel de resolucion.
     */
    private ResolucionDataModel resolDataModel;
    /**
     * DataModel de conceptos
     */
    private ConceptoDataModel conceptoDataModel;
    /**
     * Catalogo de recurso dual.
     */
    private DualListModel<CatalogoDTO> recursoDual;
    /**
     * Catalogo de abogados.
     */
    private ResolucionAbogadoDTO abogadoDTO = new ResolucionAbogadoDTO();
    /**
     * Arreglo de conceptos seleccionados.
     */
    private ConceptosDTO[] selectedConceptos;
    /**
     * Fecha maxima
     */
    private Date fechaMaxima;

    /**
     * Mensaje utilizado cuando una resolucion impugnada se aplica en otro asunto y
     * aun no termina el flujo de alguno.
     */
    private String msgMedioDefensa;

    private boolean blnMensajeDefensa;

    private boolean disableEliminar;

    private boolean disableElimResol;

    private boolean checkMonto;

    /**
     * Atributo boolean para ocultar/ver el panel de la observacion
     */
    private boolean panelTurnadoVisible = true;

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
        } else {
            this.fechaMaxima = null;
        }
    }

    /**
     * Constructor vacio.
     */
    public AbogadoController() {
        super();
    }

    /**
     * Metodo que es ejecutado despues de que se crea el controller.
     */
    @PostConstruct
    public void cargarDatosIniciales() {
        // modificar con los datos que traigan de la bandeja
        inhabilitarGuardar = false;
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));
        setObservacionTurnado(abogadoBussines.obtenerObservacionTurnado(getDatosBandejaTareaDTO().getNumeroAsunto()));
        if (null == observacionTurnado || observacionTurnado.equals("")) {
            panelTurnadoVisible = false;
        }
        resolDataModel = new ResolucionDataModel(resolucionesDTOList);
        conceptoDataModel = new ConceptoDataModel(listConceptosDTOTable);
        conceptoCats.addAll((List<CatalogoDTO>) abogadoBussines.obtenerCatalogos("concepto"));
        recursoCats.addAll((List<CatalogoDTO>) abogadoBussines.obtenerCatalogos("recurso"));
        autoridadEmisoras.addAll(abogadoBussines.obtenerCatalogoAutoridad());
        autoridadEmisorasVig.addAll(abogadoBussines.obtenerCatalogoAutoridadVig());
        listaProcedimientos.addAll(abogadoBussines.obtenerCatalogos("procedimiento"));
        List<CatalogoDTO> catalogoDTOSource = new ArrayList<CatalogoDTO>();
        List<CatalogoDTO> catalogoDTOTarget = new ArrayList<CatalogoDTO>();
        catalogoDTOSource.addAll(recursoCats);
        recursoDual = new DualListModel<CatalogoDTO>(catalogoDTOSource, catalogoDTOTarget);
        determinanteCred = false;
        banderaMonto = true;
        setMontoValor(BigDecimal.ZERO);
        fechaMaxima = new Date();
        TramiteDTO tramiteDto = generarObservacionFacade.obtenerTramite(getDatosBandejaTareaDTO().getNumeroAsunto());
        fechaMinimaExFondo = diasHabilesHelper.obtenerFechaDiasHabiles(tramiteDto.getFechaRecepcion(),
                NumerosConstantes.CERO);
        fechaMaximaExcFondo = diasHabilesHelper.sumarDiasInhabiles(VistaConstantes.anio,
                tramiteDto.getFechaRecepcion());
        fechaMaximaExcFondo = diasHabilesHelper.obtenerFechaDiasHabiles(fechaMaximaExcFondo, NumerosConstantes.CERO);
        setDisableEliminar(true);
        setDisableElimResol(true);
        mostrarPanelExclusivoFondo = false;
        /** carga de documentos de exclusivo de fondo */
        documentosExclusivoFondo = obtenerDocumentosExclusivoFondo();
    }

    public void validadListaResolucionesImpugnadas() {
        setBlnMensajeDefensa(false);
    }

    public void cargarDatosBandeja() {
        if (getDatosBandejaTareaDTO() == null) {
            updateMontoTotal();
        }
    }

    /**
     * Metodo para cargar los datos iniciales en la bandeja.
     * 
     * @param bandejaTareaDTO
     */
    public void setDatosIniciales() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            DatosBandejaTareaDTO bandejaTareaDTO = (DatosBandejaTareaDTO) getFlash()
                    .get(VistaConstantes.DATOS_BANDEJA_DTO);
            resolucionesDTOList = new ArrayList<ResolucionImpugnadaDTO>();
            resolDataModel = new ResolucionDataModel(resolucionesDTOList);
            setDatosBandejaTareaDTO(bandejaTareaDTO);
            SolicitudDTO solicitud = new SolicitudDTO();
            solicitud.setIdSolicitud(datosBandejaTareaDTO.getIdSolicitud());
            solicitud.setTipoTramite(datosBandejaTareaDTO.getTipoTramite());
            TramiteDTO dto = new TramiteDTO();
            dto.setNumeroAsunto(datosBandejaTareaDTO.getNumeroAsunto());
            abogadoDTO = getAbogadoBussines().mostrarResoluciones(solicitud, dto);
            if (abogadoDTO.getRecursos() != null) {
                this.cargaRecursos();
            }
            setTablaExclusivoEditar(!abogadoDTO.getTramiteDTO().getEstadoProcesal().equals(EstadoTramite.REMITIDO.getDescripcion()));
            banderaGuardar = abogadoBussines.validaBanderaGuardar(bandejaTareaDTO.getNumeroAsunto());
            getFlash().put("numAsunto", getDatosBandejaTareaDTO().getNumeroAsunto());
            getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());

            nombreFuncionario = (getUserProfile().getNombreCompleto() != null ? getUserProfile().getNombreCompleto() + " ": "")
                    + (getUserProfile().getPrimerApellido() != null ? getUserProfile().getPrimerApellido() + " " : "")
                    + (getUserProfile().getSegundoApellido() != null ? getUserProfile().getSegundoApellido() : "");
            if (abogadoDTO.getResolucionesDTO() != null && !abogadoDTO.getResolucionesDTO().isEmpty()) {
                resolucionesDTOList.addAll(abogadoDTO.getResolucionesDTO());
            }
            resaltarResolucionMasImportante();
            listConceptosDTOTable = null;
            visibleConceptoPanel = false;
            updateMontoTotal();
        }
        validadListaResolucionesImpugnadas();
    }

    private void cargaRecursos() {
        if (abogadoDTO.getRecursos().size() == recursoDual.getSource().size()) {
            recursoDual.setSource(new ArrayList<CatalogoDTO>());
        } else {
            for (int i = 0; i < recursoDual.getSource().size(); i++) {
                CatalogoDTO source = recursoDual.getSource().get(i);
                for (int j = 0; j < abogadoDTO.getRecursos().size(); j++) {
                    CatalogoDTO actual = abogadoDTO.getRecursos().get(j);
                    if (source.getClave().equals(actual.getClave())) {
                        recursoDual.getSource().remove(i);
                        break;
                    }
                }
            }
        }
        recursoDual.setTarget(abogadoDTO.getRecursos());
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

    /**
     * Metodo para actualizar los recursos.
     * 
     * @param event
     */
    @SuppressWarnings("unchecked")
    public void actualizarRecursos(TransferEvent event) {
        List<CatalogoDTO> lista = (List<CatalogoDTO>) event.getItems();
        for (CatalogoDTO elemento : lista) {
            String clave = elemento.getClave();
            if (clave.equals(CatalogoConstantes.CAT_EXCLUSIVO_FONDO)) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("inicializarCombo()");
                setTablaExclusivoEditar(!abogadoDTO.getTramiteDTO().getEstadoProcesal().equals(EstadoTramite.REMITIDO.getDescripcion()));
                mostrarPanelExclusivoFondo = event.isAdd() || !getListaDocumentos().isEmpty(); 
                if (event.isRemove() && !getListaDocumentos().isEmpty()) {
                    this.agregarCaracteristicaEx();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg.getString("exclusivo.documentos.existentes")));
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
        for(CatalogoDTO source : catalogoDTOSource) {
            if(source.getClave().equals(CatalogoConstantes.CAT_EXCLUSIVO_FONDO)) {
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
     * Metodo que responde al evento para guardar las resoluciones impugnadas y
     * ejectuta el metodo para guardar.
     * 
     * @param actionEvent
     */
    public void guardarResolucionesImpugnadas(ActionEvent actionEvent) {
        guardarResolucionesImpugnadas();
        for (ResolucionImpugnadaDTO resolucion : resolucionesDTOList) {
            resolucion.setEditarResolSelected(false);
        }
        setVisibleConceptoPanel(false);
        setDisableElimResol(true);
        RequestContext.getCurrentInstance().reset("resolucionForm:resolucionTable");
    }

    /**
     * Metodo para guardar las resoluciones impugnadas.
     */
    private Boolean guardarResolucionesImpugnadas() {
        if (resolucionesDTOList.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg.getString("vuj.resolucion.vacio")));
        } else if (!resolucionesDTOList.isEmpty()) {
            for (ResolucionImpugnadaDTO resolucionImpugnadaDTO : resolucionesDTOList) {
                if (!(resolucionImpugnadaDTO.getDeterminanteCred())
                        && resolucionImpugnadaDTO.getConceptos().isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            errorMsg.getString("vuj.resolucion.resolCred")));
                    return false;
                }
            }
            boolean lbDoc = true;
            if (isMostrarPanelExclusivoFondo()) {
                if (isTablaExclusivoEditar() && validaCaracteristicaExcl()) {
                    lbDoc = guardarDocumentos(false);
                    getListaDocumentos().clear();
                    setListaDocumentos(getAnexarDocumentoBussines()
                            .obtenerDocumentosOficialesAnexadosExclusivoFondo(datosBandejaTareaDTO.getNumeroAsunto()));
                    setDocumentoOficialDataModel(new DocumentoOficialDataModel(getListaDocumentos()));
                    RequestContext.getCurrentInstance().update("exFondoForm:tablaDocumentos");
                }
            }
            if (lbDoc) {
                abogadoDTO.setResolucionesDTO(resolucionesDTOList);
                abogadoDTO.setRecursos(getRecursoDual().getTarget());
                abogadoBussines.guardarResolucion(abogadoDTO);
                abogadoDTO.setMontoTotalControvertido(montoTotalControvertido);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", errorMsg.getString("vuj.resolucion.guardada")));
                if ((isMostrarPanelExclusivoFondo() && validarExcluivoFondo()) || !isMostrarPanelExclusivoFondo()) {
                    abogadoBussines.actualizarAsunto(getDatosBandejaTareaDTO().getNumeroAsunto(),
                            getDatosBandejaTareaDTO().getIdInstancia().intValue(), getUserProfile().getRfc());
                    if (abogadoDTO.getTramiteDTO().getEstadoProcesal().equals(EstadoTramite.PROMOVIDO.getDescripcion())
                            || abogadoDTO.getTramiteDTO().getEstadoProcesal()
                                    .equals(EstadoTramite.REMITIDO.getDescripcion())) {
                        abogadoDTO.getTramiteDTO().setEstadoProcesal(EstadoTramite.EN_ESTUDIO.getDescripcion());
                    }
                    banderaGuardar = true;
                }
            }
        }
        return true;
    }

    private boolean validaCaracteristicaExcl() {
        boolean banCaracteristicaExl = false;
        List<CatalogoDTO> catalogoDTOTarget = recursoDual.getTarget();
        for(CatalogoDTO targetDto : catalogoDTOTarget){
            if(targetDto.getClave().equals(CatalogoConstantes.CAT_EXCLUSIVO_FONDO)) {
                banCaracteristicaExl = true;
                break;
            }
        }
        if(!banCaracteristicaExl && !getListaDocumentos().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg.getString("exclusivo.documentos.existentes")));
        }
        return banCaracteristicaExl;
    }

    /**
     * Metodo para remitir los recursos de revocacion.
     * 
     * @return
     * @throws IOException
     */
    public String remitir() throws IOException {
        String lsSiguiente = null;
        if (isMostrarPanelExclusivoFondo()) {
            if (validarExcluivoFondo()) {
                if (guardarDocumentos(true)) {
                    getLogger().debug("getDatosBandejaTareaDTO {}", getDatosBandejaTareaDTO());
                    getDatosBandejaTareaDTO().setEstadoProcesal(EstadoTramite.EN_ESTUDIO.getDescripcion());
                    getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
                    getFlash().put(VistaConstantes.TAREA_ORIGEN, getAbogadoBussines().getIdeTareaOrigen());
                    abogadoBussines.actualizarAsunto(getDatosBandejaTareaDTO().getNumeroAsunto(),
                            getDatosBandejaTareaDTO().getIdInstancia().intValue(), getUserProfile().getRfc());
                    lsSiguiente = AbogadoConstantes.REMITIR_JSF;
                } else {
                    lsSiguiente = null;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                        errorMsg.getString("vuj.resol.documentos.adjuntarAlMenosUnDocExFon.remitir")));
                lsSiguiente = null;
            }
        } else {
            getLogger().debug("getDatosBandejaTareaDTO {}", getDatosBandejaTareaDTO());
            getDatosBandejaTareaDTO().setEstadoProcesal(EstadoTramite.EN_ESTUDIO.getDescripcion());
            getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
            getFlash().put(VistaConstantes.TAREA_ORIGEN, getAbogadoBussines().getIdeTareaOrigen());
            abogadoBussines.actualizarAsunto(getDatosBandejaTareaDTO().getNumeroAsunto(),
                    getDatosBandejaTareaDTO().getIdInstancia().intValue(), getUserProfile().getRfc());
            lsSiguiente = AbogadoConstantes.REMITIR_JSF;
        }
        return lsSiguiente;
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
     * Metodo para observar los recursos de revocacion.
     * 
     * @return
     * @throws IOException
     */
    public void observar() throws IOException {
        getLogger().debug("getDatosBandejaTareaDTO {}", getDatosBandejaTareaDTO());
        getDatosBandejaTareaDTO().setEstadoProcesal(EstadoTramite.EN_ESTUDIO.getDescripcion());
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        getFlash().put(VistaConstantes.TAREA_ORIGEN, getAbogadoBussines().getIdeTareaOrigen());
        abogadoBussines.actualizarAsunto(getDatosBandejaTareaDTO().getNumeroAsunto(),
                getDatosBandejaTareaDTO().getIdInstancia().intValue(), getUserProfile().getRfc());

        ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext
                .getCurrentInstance().getApplication().getNavigationHandler();

        configurableNavigationHandler.performNavigation(AbogadoConstantes.OBSERVAR_JSF + "?faces-redirect=true ");

    }

    /**
     * Metodo para generar un requerimiento.
     * 
     * @param actionEvent
     */
    public void generarRequerimiento(ActionEvent actionEvent) {
        getLogger().debug("Generar Requerimiento - idTramite {}", getDatosBandejaTareaDTO().getNumeroAsunto());
        getLogger().debug("Generar Requerimiento - idTareaUsuario {}",
                getDatosBandejaTareaDTO().getIdTareaUsuario().toString());

        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        getFlash().put("esAbogado", true);

        ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext
                .getCurrentInstance().getApplication().getNavigationHandler();

        configurableNavigationHandler
                .performNavigation(AbogadoConstantes.GENERAR_REQUERIMIENTO + "?faces-redirect=true");

    }

    /**
     * Metodo par emitir siguiente resolucion.
     * 
     * @param actionEvent
     */
    public void siguienteEmitir(ActionEvent actionEvent) {
        boolean validacion = guardarResolucionesImpugnadas();
        if (validacion) {
            if (isMostrarPanelExclusivoFondo()) {
                if (getAnexarDocumentoValidator().validarDocumentosOficiales(getListaDocumentos())) {
                    if (validarExcluivoFondo()) {
                        getFlash().put("abogado", abogadoDTO);
                        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
                        ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext
                                .getCurrentInstance().getApplication().getNavigationHandler();

                        configurableNavigationHandler
                                .performNavigation(AbogadoConstantes.EMITIR_JSF + "?faces-redirect=true");
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "", errorMsg.getString("vuj.resol.documentos.adjuntarAlMenosUnDocExFon.resolucion")));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            errorMsg.getString("vuj.resol.documentos.adjuntarAlMenosUnDocExFon.resolucion")));
                }
            } else {
                getFlash().put("abogado", abogadoDTO);
                getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
                ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext
                        .getCurrentInstance().getApplication().getNavigationHandler();

                configurableNavigationHandler.performNavigation(AbogadoConstantes.EMITIR_JSF + "?faces-redirect=true");
            }
        }
    }

    /**
     * Metodo para agregar una resolucion.
     * 
     * @param actionEvent
     */
    public void agregarResolucion(ActionEvent actionEvent) {
        visibleConceptoPanel = false;
        resol.setIdTramite(getDatosBandejaTareaDTO().getNumeroAsunto());
        ResolucionImpugnadaDTO resolucion = abogadoBussines.validarResolucionExistente(resol);
        Map<Integer, String> mnsg = abogadoValidator.resolucionNueva(resolucion, resol.getResImpugnada(),
                this.resolucionesDTOList);
        if (mnsg.containsKey(AbogadoConstantes.RESOLUCION_REPETIDA)) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, mnsg.get(AbogadoConstantes.RESOLUCION_REPETIDA), ""));
            resol = new ResolucionImpugnadaDTO();
        } else {
            if (mnsg.containsKey(AbogadoConstantes.RESOLUCION_DUPLICADA)) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        mnsg.get(AbogadoConstantes.RESOLUCION_DUPLICADA), ""));
            }
            if (!determinanteCred) {
                montoValor = BigDecimal.ZERO;
            }
            ResolucionImpugnadaDTO resolDTO = new ResolucionImpugnadaDTO();
            resolDTO.setId(Long.valueOf(indexResolucion++));
            resolDTO.setAutoridadEmisora(resol.getAutoridadEmisora());
            resolDTO.setDeterminanteCred(determinanteCred);
            resolDTO.setFechaEmision(resol.getFechaEmision());
            resolDTO.setMonto(this.montoValor == null ? BigDecimal.ZERO : this.montoValor);
            resolDTO.setProcedimiento(resol.getProcedimiento());
            resolDTO.setResImpugnada(resol.getResImpugnada());
            resolDTO.setIdeSentidoResolucion(resol.getIdeSentidoResolucion());

            resolucionesDTOList.add(resolDTO);

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
        validadListaResolucionesImpugnadas();
    }

    /**
     * Metodod que busca la resolucion mas importante para resaltarse.
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

        if (null == this.selectedResol) {
            for (ResolucionImpugnadaDTO resolucion : resolucionesDTOList) {
                resolucion.setEditarResolSelected(false);
            }
        }
        validadListaResolucionesImpugnadas();

    }

    /**
     * Metodo que resalta una resolucion.
     * 
     * @param editEvent
     */
    public void resaltarResolucionMasImportante(RowEditEvent editEvent) {
        selectedResol = null;
        resaltarResolucionMasImportante();
    }

    /**
     * Metodo para guardar un concepto seleccionado.
     * 
     * @param actionEvent
     */
    public void guardarConcepto(ActionEvent actionEvent) {
        inhabilitarGuardar = false;
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
            selectedResol.setConceptos(listConceptosDTOTable);
            setConceptoDataModel(new ConceptoDataModel(listConceptosDTOTable));
            updateMontoResolucion();
            updateMontoTotal();
            resaltarResolucionMasImportante();
            conceptosDTO = new ConceptosDTO();
            visibleConceptoPanel = true;
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
        inhabilitarGuardar = false;
        setConceptoDataModel(new ConceptoDataModel(respaldoConceptosDTOTable));
        resetConcepto();
        importeValor = "";
        disableEliminar = true;
    }

    /**
     * Metodo para editar un concepto.
     * 
     * @param event
     */
    public void editarConcepto(RowEditEvent event) {
        inhabilitarGuardar = false;
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
            SelectEvent sel = new SelectEvent(event.getComponent(), event.getBehavior(), getSelectedResol());
            onRowSelect(sel);
            resetConcepto();
            disableEliminar = true;
        }
        disableEliminar = true;
        RequestContext.getCurrentInstance().reset("resolucionForm");
    }

    /**
     * Metodo para editar el valor de una resolucion.
     * 
     * @param editEvent
     */
    public void editarResolucion(RowEditEvent editEvent) {
        inhabilitarGuardar = false;
        this.selectedResol = (ResolucionImpugnadaDTO) editEvent.getObject();
        this.selectedResol.setIdTramite(datosBandejaTareaDTO.getNumeroAsunto());
        ResolucionImpugnadaDTO resolucion = abogadoBussines.validarResolucionExistente(selectedResol);

        Map<Integer, String> mnsg = abogadoValidator.resolucionNueva(resolucion, selectedResol.getResImpugnada(),
                this.resolucionesDTOList);

        if (mnsg.containsKey(AbogadoConstantes.RESOLUCION_DUPLICADA)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, mnsg.get(AbogadoConstantes.RESOLUCION_DUPLICADA), ""));
        }

        if (selectedResol.getDeterminanteCred()) {
            selectedResol.setConceptos(null);

            selectedResol = null;
            conceptoDataModel = new ConceptoDataModel(new ArrayList<ConceptosDTO>());

        } else {
            BigDecimal monto = BigDecimal.ZERO;
            if (selectedResol.getConceptos() != null) {
                for (ConceptosDTO concepto : selectedResol.getConceptos()) {
                    monto = monto.add(concepto.getImporte());
                }
            }
            BigDecimal monto2 = monto.setScale(2);
            selectedResol.setMonto(monto2);
            selectedResol = null;
        }

        visibleConceptoPanel = false;
        updateMontoTotal();
        actualizaEdicionResolucion();
        resaltarResolucionMasImportante();
        selectedResol = null;
        banderaResolucionImpugnada = true;
        validadListaResolucionesImpugnadas();
        setDisableElimResol(true);

    }

    /**
     * Metodo para actualizar el monto de una resolucion.
     */
    private void updateMontoResolucion() {
        BigDecimal montoT = BigDecimal.ZERO;
        for (ConceptosDTO concepto : listConceptosDTOTable) {
            montoT = montoT.add(concepto.getImporte());
        }

        selectedResol.setMonto(montoT);
    }

    /**
     * Metodo para actualizar el monto total.
     */
    public void resetResol() {
        inhabilitarGuardar = false;
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
     * Metodo para actualizar el monto total.
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
     * Metodo para verificar el momento en el que se cambia el valor de el checkbox
     * de si es determinante de credito o no la resolucion
     */

    public void determinanteChangeEvent(AjaxBehaviorEvent changeEvent) {
        SelectBooleanCheckbox checkbox = (SelectBooleanCheckbox) changeEvent.getSource();
        if ((Boolean) checkbox.getValue()) {
            this.banderaMonto = false;
            this.montoValor = BigDecimal.ZERO;
        } else {
            this.banderaMonto = true;
            this.montoValor = BigDecimal.ZERO;
        }

    }
    public void onRowEditInit() {
        inhabilitarGuardar = true;
    }
    /**
     * Metodo para identificar una fila que fue elegido en la tabla.
     * 
     * @param event
     */
    public void onRowSelect(SelectEvent event) {
        inhabilitarGuardar = false;
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
        selectedConceptos = null;
        RequestContext.getCurrentInstance().reset("infoAdicionalForm:conceptoPanel");
        setConceptosDTO(new ConceptosDTO());
        setDisableElimResol(false);
    }

    /**
     * Metodo que actualiza la opcion de edicion de un concepto seleccionado
     */
    public void actualizaEdicionResolucion() {
        RequestContext.getCurrentInstance().reset("resolucionForm:panelForm");
        for (ResolucionImpugnadaDTO resolucion : resolucionesDTOList) {
            if (selectedResol != null && resolucion.getId().longValue() == selectedResol.getId().longValue()) {
                resolucion.setEditarResolSelected(true);
            } else {
                resolucion.setEditarResolSelected(false);
            }
        }
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

    /**
     * /** Metodo para deseleccionar una fila en la tabla.
     * 
     * @param event
     */
    public void onRowUnselect(UnselectEvent event) {
        inhabilitarGuardar = false;
        this.selectedResol = new ResolucionImpugnadaDTO();
        visibleConceptoPanel = false;
        setDisableElimResol(true);
    }

    /**
     * Metodo para eliminar una fila.
     * 
     * @param actionEvent
     */
    public void removeRow(ActionEvent actionEvent) {
        inhabilitarGuardar = false;
        visibleConceptoPanel = false;
        montoValor = BigDecimal.ZERO;
        if (resolucionesDTOList.isEmpty()) {
            return;
        }
        resolucionesDTOList.remove(this.selectedResol);
        listConceptosDTOTable.clear();
        if (resolucionesDTOList.isEmpty()) {
            visibleConceptoPanel = false;
        }
        updateMontoTotal();
        resaltarResolucionMasImportante();
        for (ResolucionImpugnadaDTO resolucion : resolucionesDTOList) {
            resolucion.setEditarResolSelected(false);

        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "",
                messages.getString("vuj.resol.resImpugnada.eliminada") + " " + datosBandejaTareaDTO.getNumeroAsunto()));
        setDisableElimResol(true);

    }

    /**
     * Metodo para eliminar concepto.
     */
    public void removeConcepto() {
        inhabilitarGuardar = false;
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
    }

    /**
     * Metodo que valida la eliminacion del concepto.
     * 
     * @return
     * @throws ValidatorException
     */
    public boolean validateRemoveConcepto() {
        boolean valido = false;
        if (selectedConceptos == null || (selectedConceptos.length == 0)) {
            valido = false;
        } else {
            valido = true;
        }
        return valido;
    }

    /**
     * Metodo que valida la eliminacion del concepto.
     */
    public void validRemoveConcepto() {
        if (selectedConceptos == null || (selectedConceptos.length == 0)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    errorMsg.getString("vuj.resolucion.conceptoObligatorio")));
            banderaRemove = false;
        } else {
            banderaRemove = true;

        }
    }

    public void rowSelectCheckbox(SelectEvent event) {
        ConceptosDTO concepto = (ConceptosDTO) event.getObject();

        actualizaEdicionConceptos(concepto, true);

        setDisableEliminar(false);
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

    public void rowUnselectCheckbox(UnselectEvent event) {
        ConceptosDTO concepto = (ConceptosDTO) event.getObject();
        actualizaEdicionConceptos(concepto, false);
        if (null != getSelectedConceptos() && getSelectedConceptos().length > 0) {
            return;
        } else {
            setDisableEliminar(true);
        }
    }

    public void onRowEditInit(RowEditEvent event) {
    }

    public String getMsgMedioDefensa() {
        return msgMedioDefensa;
    }

    public void setMsgMedioDefensa(String msgMedioDefensa) {
        this.msgMedioDefensa = msgMedioDefensa;
    }

    /**
     * Metodo que activa campo monto.
     */
    public void onActivaMonto() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String onDeterminanteCredStr = (String) map.get("onDeterminanteCredParam");
        boolean activaMonto = Boolean.valueOf(onDeterminanteCredStr.toLowerCase());
        this.selectedResol.setDeterminanteCred(activaMonto);

    }

    /**
     * Metodo para cambiar el valor de BanderaResolucionImpugnada
     * 
     * @param actionEvent
     */
    public void actualizarBanderaResolucionImpugnada(ActionEvent actionEvent) {
        banderaResolucionImpugnada = false;

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
     *            el resolDataModel a fijar.
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
     *            el conceptosDTO a fijar.
     */
    public void setConceptosDTO(ConceptosDTO conceptosDTO) {
        this.conceptosDTO = conceptosDTO;
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
     *            el selectedResol a fijar.
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
     *            la resolucionesDTOList
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
     * @return autoridadEmisoras
     */
    public List<UnidadAdministrativaDTO> getAutoridadEmisorasVig() {
        return autoridadEmisorasVig;
    }

    /**
     * 
     * @param autoridadEmisoras
     *            las autoridadEmisoras a fijar.
     */
    public void setAutoridadEmisorasVig(List<UnidadAdministrativaDTO> autoridadEmisorasVig) {
        this.autoridadEmisorasVig = autoridadEmisorasVig;
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
     *            la listConceptosDTOTable a fijar.
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
     *            el conceptoDataModel a fijar.
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
     *            el abogadoBussines a fijar.
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
     * @return errorMessage a fijar.
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
     * @param montoTotalControvertido
     */
    public void setMontoTotalControvertido(BigDecimal montoTotalControvertido) {
        this.montoTotalControvertido = montoTotalControvertido;
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
     * @return abogadoValidator
     */
    public AbogadoValidator getAbogadoValidator() {
        return abogadoValidator;
    }

    /**
     * 
     * @param abogadoValidator
     *            el abogadoValidator a fijar.
     */
    public void setAbogadoValidator(AbogadoValidator abogadoValidator) {
        this.abogadoValidator = abogadoValidator;
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
     *            los datosBandejaDTO a fijar.
     */
    public void setDatosBandejaTareaDTO(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        this.datosBandejaTareaDTO = datosBandejaTareaDTO;
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
     * @return messages
     */
    public ResourceBundle getMessages() {
        return messages;
    }

    /**
     * 
     * @param messages
     *            the messages to set
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
     * @return arreglo
     */
    public ConceptosDTO[] getSelectedConceptos() {
        if (selectedConceptos != null) {
            ConceptosDTO[] arreglo = new ConceptosDTO[selectedConceptos.length];
            for (int i = 0; i < selectedConceptos.length; i++) {
                ConceptosDTO conc = selectedConceptos[i];
                arreglo[i] = conc;
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
                ConceptosDTO conc = selectedConceptosLocal[i];
                conc.setEditarConcepto(true);
                arreglo[i] = conc;
            }

            this.selectedConceptos = arreglo;
        } else {
            this.selectedConceptos = null;
        }
    }

    /**
     * 
     * @return errorConceptorMessage
     */
    public String getErrorConceptoMessage() {
        return errorConceptoMessage;
    }

    /**
     * 
     * @param errorConceptoMessage
     *            el errorConceptoMessage a fijar.
     */
    public void setErrorConceptoMessage(String errorConceptoMessage) {
        this.errorConceptoMessage = errorConceptoMessage;
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
     * @return banderaResolucionImpugnada
     */
    public boolean isBanderaResolucionImpugnada() {
        return banderaResolucionImpugnada;
    }

    /**
     * 
     * @param banderaResolucionImpugnada
     *            la banderaResolucionImpuganada a fijar.
     */
    public void setBanderaResolucionImpugnada(boolean banderaResolucionImpugnada) {
        this.banderaResolucionImpugnada = banderaResolucionImpugnada;
    }

    /**
     * Metodo para inhabilitar checkbox determinante de credito y mensaje de
     * confirmacion para eliminar concepto
     */
    public void banderaFalse() {
        banderaRemove = false;
        determinanteCred = false;
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
     * @return the indexConcepto
     */
    public int getIndexConcepto() {
        return indexConcepto;
    }

    /**
     * @param indexConcepto
     *            the indexConcepto to set
     */
    public void setIndexConcepto(int indexConcepto) {
        this.indexConcepto = indexConcepto;
    }

    /**
     * @return the emitirResolucionBusiness
     */
    public EmitirResolucionBusiness getEmitirResolucionBusiness() {
        return emitirResolucionBusiness;
    }

    /**
     * @param emitirResolucionBusiness
     *            the emitirResolucionBusiness to set
     */
    public void setEmitirResolucionBusiness(EmitirResolucionBusiness emitirResolucionBusiness) {
        this.emitirResolucionBusiness = emitirResolucionBusiness;
    }

    public boolean isBlnMensajeDefensa() {
        return blnMensajeDefensa;
    }

    public void setBlnMensajeDefensa(boolean blnMensajeDefensa) {
        this.blnMensajeDefensa = blnMensajeDefensa;
    }

    /**
     * @return the montoTotalControvertido
     */
    public BigDecimal getMontoTotalControvertido() {
        return montoTotalControvertido;
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

    /**
     * @return the resolucionMasImpotanteHelper
     */
    public ResolucionMasImpotanteHelper getResolucionMasImpotanteHelper() {
        return resolucionMasImpotanteHelper;
    }

    /**
     * @param resolucionMasImpotanteHelper
     *            the resolucionMasImpotanteHelper to set
     */
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

        getLogger().debug("montoResolChangeEvent check value {}, monto {}");
    }

    // P
    public void montoResolCheckChangeEvent(AjaxBehaviorEvent changeEvent) {

        SelectBooleanCheckbox checkbox = (SelectBooleanCheckbox) changeEvent.getSource();
        getLogger().debug("check value {}", checkbox.getValue());
        Boolean check = (Boolean) checkbox.getValue();
        if (check) {
            this.selectedResol.setMonto(new BigDecimal("0.00"));

        }

    }

    /**
     * @return the observacionTurnado
     */
    public String getObservacionTurnado() {
        return observacionTurnado;
    }

    /**
     * @param observacionTurnado
     *            the observacionTurnado to set
     */
    public void setObservacionTurnado(String observacionTurnado) {
        this.observacionTurnado = observacionTurnado;
    }

    /**
     * @return the panelTurnadoVisible
     */
    public boolean isPanelTurnadoVisible() {
        return panelTurnadoVisible;
    }

    /**
     * @param panelTurnadoVisible
     *            the panelTurnadoVisible to set
     */
    public void setPanelTurnadoVisible(boolean panelTurnadoVisible) {
        this.panelTurnadoVisible = panelTurnadoVisible;
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

    public void validaAcceso() {
        // Validacion de acceso a la funcionalidad
        this.validaAccesoRolEmpleado(RolesConstantes.ROL_EMPLEADO_ABOGADO);
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

    public boolean isMostrarPanelExclusivoFondo() {
        return mostrarPanelExclusivoFondo;
    }

    @Override
    public BaseCloudBusiness getBusinessBean() {
        return capturaSolicitudBussines;
    }

    @Override
    public List<DocumentoOficialDTO> getListaDocumentosAdjuntados() {
        return getListaDocumentos();
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
        List<CatalogoDTO> listCaracteristicas = recursoDual.getSource() != null ? recursoDual.getSource() : new ArrayList<CatalogoDTO>();
        if (!listCaracteristicas.isEmpty()) {
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

    public List<DocumentoOficialDTO> getListaDocumentos() {
        return listaDocumentos;
    }

    /**
     * 
     * @param listaDocumentos
     *            a fijar
     */
    public void setListaDocumentos(List<DocumentoOficialDTO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public void setCapturaSolicitudBussines(CapturaSolicitudBussines capturaSolicitudBussines) {
        this.capturaSolicitudBussines = capturaSolicitudBussines;
    }

    public boolean isAgregarVisible() {
        return agregarVisible && id != null;
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
                FacesContext.getCurrentInstance().addMessage("exFondoForm:msgsExFondo", msg);
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
    
    public List<GenericDTO> getDocumentosExclusivoFondo() {
        return documentosExclusivoFondo;
    }

    public void setDocumentosExclusivoFondo(List<GenericDTO> documentosExclusivoFondo) {
        this.documentosExclusivoFondo = documentosExclusivoFondo;
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
    private boolean guardarDocumentos(boolean lbRemision) {
        boolean lbOk = true;
        if (getAnexarDocumentoValidator().validarDocumentosOficiales(getListaDocumentos())) {
            try {
                getAnexarDocumentoBussines().guardarDocumentosOficialesExlusivoFondo(
                        getDatosBandejaTareaDTO().getNumeroAsunto(), getListaDocumentos());
                lbOk = true;
            } catch (ArchivoNoGuardadoException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                        errorMsg.getString("vuj.resol.documentos.adjuntarAlMenosUnDocExFon.errorGuardar")));
                getLogger().error("Error al guardarDocumentos", e);
                lbOk = false;
            }
        } else {
            if (lbRemision) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                        errorMsg.getString("vuj.resol.documentos.adjuntarAlMenosUnDocExFon.remitir")));
                lbOk = false;
            }
        }
        return lbOk;
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
                enviarMensajeError = true;
            }
            if (documentoSelec.getFechaNotificacion() != null) {
                enviarMensajeError = true;
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

    public DocumentoOficialDataModel getDocumentoOficialDataModel() {
        return documentoOficialDataModel;
    }

    public void fechaNotifChanged(ValueChangeEvent e) {
        this.fechaNotifOld = (Date) e.getOldValue();
    }

    public void fechaAudiChanged(ValueChangeEvent e) {
        this.fechaAudiOld = (Date) e.getOldValue();
    }

    public void setDocumentoOficialDataModel(DocumentoOficialDataModel documentoOficialDataModel) {
        this.documentoOficialDataModel = documentoOficialDataModel;
    }

    public AnexarDocumentoValidator getAnexarDocumentoValidator() {
        return anexarDocumentoValidator;
    }

    public void setAnexarDocumentoValidator(AnexarDocumentoValidator anexarDocumentoValidator) {
        this.anexarDocumentoValidator = anexarDocumentoValidator;
    }

    public AnexarDocumentoBussines getAnexarDocumentoBussines() {
        return anexarDocumentoBussines;
    }

    public void setAnexarDocumentoBussines(AnexarDocumentoBussines anexarDocumentoBussines) {
        this.anexarDocumentoBussines = anexarDocumentoBussines;
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

    public void setMostrarPanelExclusivoFondo(boolean mostrarPanelExclusivoFondo) {
        this.mostrarPanelExclusivoFondo = mostrarPanelExclusivoFondo;
    }

}
