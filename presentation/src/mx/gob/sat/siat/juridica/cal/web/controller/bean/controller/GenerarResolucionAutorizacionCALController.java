/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.AdjuntarDocumentoController;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.FirmarTareaController;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.base.web.util.validador.GenerarResolucionValidator;
import mx.gob.sat.siat.juridica.cal.dto.BienResarcimientoDTO;
import mx.gob.sat.siat.juridica.cal.dto.ResolucionDatosGeneradosDTO;
import mx.gob.sat.siat.juridica.cal.util.constants.CadenasConstants;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.business.GeneracionResolucionCALBussines;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.model.BienResarcimientoDataModel;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.AutorizarConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.GeneralConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller Class to handle atender promocion flow from juridica.
 * 
 * @author antonio.flores Softtek
 * 
 */
@ViewScoped
@ManagedBean(name = "generarResolucionAutorizacionController")
public class GenerarResolucionAutorizacionCALController extends BaseControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = 7829486868843606210L;
    /**
     * Bussines.
     */
    @ManagedProperty(value = "#{generacionResolucionBussines}")
    private transient GeneracionResolucionCALBussines generacionResolucionBussines;

    /**
     * Validator
     */
    @ManagedProperty(value = "#{generarResolucionValidator}")
    private GenerarResolucionValidator generarResolucionValidator;

    /**
     * Bean que controla la logica para adjuntar un documento.
     */
    @ManagedProperty("#{adjuntarDocumentoController}")
    private AdjuntarDocumentoController adjuntarDocumentoController;

    /** Bean para firmar la tarea */
    @ManagedProperty(value = "#{firmarTareaController}")
    private FirmarTareaController firmarTareaController;

    /**
     * 
     */
    private Integer modalidadType;
    /**
     * 
     */
    private List<CatalogoDTO> listaResolucion = new ArrayList<CatalogoDTO>();
    /**
     * 
     */
    private List<CatalogoDTO> listaUsuarios = new ArrayList<CatalogoDTO>();
    /**
     * Variable para el panel de clasificac&iacute;n
     */
    private Date fechaSesion;
    /**
     * Variable para el panel de clasificac&iacute;n
     */
    private String fraccion;
    /**
     * Variable para el panel de clasificac&iacute;n
     */
    private String minuta;
    /**
     * 
     */
    private String resolucionCbo;
    /**
     * 
     */
    private String nombreFirmaCbo;
    /**
     * 
     */
    private CatalogoDTO resolucion;
    /**
     * 
     */
    private CatalogoDTO nombreFirma;
    /**
     * 
     */
    private String fraccionArancel;
    /**
     * 
     */
    private List<CatalogoDTO> mediosDefensa = new ArrayList<CatalogoDTO>();
    /**
     * 
     */
    private List<CatalogoDTO> mediosTransporte = new ArrayList<CatalogoDTO>();
    /**
     * 
     */
    private List<UnidadAdministrativaDTO> autoridadesEmbargo = new ArrayList<UnidadAdministrativaDTO>();
    /**
     * 
     */
    private BienResarcimientoDataModel bienResarcimientoDataModel;
    /**
     * Fecha maxima
     */
    private Date fechaMaxima;
    /**
     * 
     */
    private String adminLocal;
    /**
     * 
     */
    private String repLegalInfo;
    /**
     * 
     */
    private String buzon;
    /**
     * 
     */
    private String municipio;
    /**
     * 
     */
    private String estado;
    /**
     * 
     */
    private String cp;
    /**
     * 
     */
    private String telefono;
    /**
     * 
     */
    private String calle;
    /**
     * 
     */
    private String numExt;
    /**
     * 
     */
    private String numInt;
    /**
     * 
     */
    private String colonia;
    /**
     * 
     */
    private String apellidoPat;
    /**
     * 
     */
    private String apellidoMat;
    /**
     * 
     */
    private String nombre;
    /**
     * 
     */
    private Integer panelType;
    /**
     * 
     */
    private String numeroAsuntoResarcimiento;
    /**
     * 
     */
    private Long montoSentencia;
    /**
     * 
     */
    private String medioDefensa;
    /**
     * 
     */
    private String autoridadEmbarga;
    /**
     * 
     */
    private String cumplimientoSentencia;
    /**
     * 
     */
    private Long montoTotalOrdenado;
    /**
     * 
     */
    private Date fechaEmbargo;
    /**
     * 
     */
    private String medioTransporte;

    /**
     * 
     */
    private Long valorBien;
    /**
     * 
     */
    private String descripcionBien;
    /**
     * 
     */
    private String clave;
    /**
     * 
     */
    private String firmante;
    /**
     * 
     */
    private Long montoCalculado;
    /**
     * Lista de resoluciones a guardar.
     */
    private List<BienResarcimientoDTO> bienResarcimientoDTOList = new ArrayList<BienResarcimientoDTO>();
    /**
     * Lista de resoluciones a eliminar.
     */
    private List<BienResarcimientoDTO> bienResarcimientoDTODeleteList = new ArrayList<BienResarcimientoDTO>();
    /**
     * 
     */
    private BienResarcimientoDTO[] selectedBienResarcimiento;
    /**
     * 
     */
    private List<String> mensajesValidaciones;
    /**
     * 
     */
    private DatosBandejaTareaDTO datosBandejaTareaDTO;
    /**
     * 
     */
    private TramiteDTO tramiteDTO;
    /**
     * 
     */
    private String rol;
    /**
     * 
     */
    private ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO;
    /**
     * Variable para llenar un tipo de combo
     */
    private String tipoTramiteDescripcion;
    /**
     * Para la selecci&oacute;n del check en tabla
     */
    private boolean eliminarVisible = false;
    /**
     * Para habilitar el bot&oacute;n
     */
    private boolean agregarVisible;
    /**
     * Numero de oficio
     */
    private String numeroOficio;
    /**
     * fecha de resolucion
     */
    private Date fechaResolucion;
    /**
     * 
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    /**
     * 
     */
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    private String msgValidaEliminar = "";

    private Date fechaMinima;

    /**
     * 
     */
    @PostConstruct
    public void initAtenderPromocion() {
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));
        setPanelType(obtenerTipo());
        fechaMaxima = new Date();
        bienResarcimientoDTOList = new ArrayList<BienResarcimientoDTO>();
        bienResarcimientoDTODeleteList = new ArrayList<BienResarcimientoDTO>();
        listaUsuarios.addAll(getListaAutorizadores(getDatosBandejaTareaDTO().getNumeroAsunto()));
        listaResolucion.addAll(getListaResolucion(getTipoTramiteDescripcion()));
        obtenerDatosAprovacion(getDatosBandejaTareaDTO().getNumeroAsunto());
        if (getPanelType().toString().equalsIgnoreCase(CadenasConstants.PANEL_TIPO_RESARCIMIENTO)) {
            mediosDefensa.addAll(getListMediosDefensa());
            mediosTransporte.addAll(getMediosDeTransporte());
            autoridadesEmbargo.addAll(getAutoridades());
            bienResarcimientoDTOList.addAll(getBienesResarcimiento());
            bienResarcimientoDataModel = new BienResarcimientoDataModel(bienResarcimientoDTOList);
            if (bienResarcimientoDTOList.size() == NumerosConstantes.CINCO) {
                setAgregarVisible(true);
            }
            else {
                setAgregarVisible(false);
            }
            llenaDatosResarcimiento();
        }
        else if (getPanelType().toString().equalsIgnoreCase(CadenasConstants.PANEL_TIPO_CLASIFICACION)) {
            llenaDatosClasificacion();
        }
        else {
            colocaDatosGenerales();
        }
        fechaMinima = getTramiteDTO().getFechaRecepcion();
    }

    /**
     * Para llenar los datos de la clasificaci&oacute;n
     */
    private void llenaDatosClasificacion() {
        if (getResolucionDatosGeneradosDTO() != null) {
            colocaDatosGenerales();
            this.setFraccionArancel(getResolucionDatosGeneradosDTO().getFraccionArancelaria());
            this.setFraccion(getResolucionDatosGeneradosDTO().isConfirmaFraccion() ? "1" : "0");
            this.setMinuta(getResolucionDatosGeneradosDTO().getNumeroMinuta() == null ? "": getResolucionDatosGeneradosDTO().getNumeroMinuta().trim());
            this.setFechaSesion(getResolucionDatosGeneradosDTO().getFechaSesion());
        }
    }

    /**
     * 
     */
    private void colocaDatosGenerales() {

        String resolucionDesSelected = getResolucionDatosGeneradosDTO().getResolucionSelected();
        for (CatalogoDTO resoluciones : listaResolucion) {
            if (resoluciones.getDescripcion().equalsIgnoreCase(resolucionDesSelected)) {
                this.setResolucionCbo(resoluciones.getClave());
            }
        }
        this.setNumeroOficio(getResolucionDatosGeneradosDTO().getNumeroOficio());
        this.setFechaResolucion(getResolucionDatosGeneradosDTO().getFechaResolucion());

    }

    /**
     * 
     */
    private void llenaDatosResarcimiento() {
        if (getResolucionDatosGeneradosDTO() != null) {
            colocaDatosGenerales();
            String cumplimentacion = "0";
            if (getResolucionDatosGeneradosDTO().isCumplimentacion()) {
                cumplimentacion = "1";
            }
            this.setFechaEmbargo(getResolucionDatosGeneradosDTO().getFechaEmbargo());
            this.setMontoTotalOrdenado(getResolucionDatosGeneradosDTO().getMontoTotalOrdenado() == null ? null 
                    : getResolucionDatosGeneradosDTO().getMontoTotalOrdenado().longValue());
            this.setCumplimientoSentencia(cumplimentacion);
            this.setMontoSentencia(getResolucionDatosGeneradosDTO().getMontoSentencia() == null ? null
                    : getResolucionDatosGeneradosDTO().getMontoSentencia().longValue());
            this.setAutoridadEmbarga(getResolucionDatosGeneradosDTO().getUnidadAdministrativa());
            this.setMedioDefensa(getResolucionDatosGeneradosDTO().getTipoMedioPago());
            this.setNumeroAsuntoResarcimiento(getResolucionDatosGeneradosDTO().getNumeroAsunto());
            this.setNumeroOficio(getResolucionDatosGeneradosDTO().getNumeroOficio());
            this.setFechaResolucion(getResolucionDatosGeneradosDTO().getFechaResolucion());
            updateMonto();
        }
    }

    /**
     * LLenado del combo datos de aprovaci&oacute;n.
     * 
     * @param numeroAsunto
     */
    private void obtenerDatosAprovacion(String numeroAsunto) {
        setResolucionDatosGeneradosDTO(getGeneracionResolucionBussines().getDatosResolucion(numeroAsunto));
    }

    /**
     * M&eacute;todo para obtener el tipo de panel que se va a
     * trabajar, tambi&eacute;n obtiene el tipo de resoluci&oacute;n
     * para llenar el combo.
     * 
     * @return
     */
    private Integer obtenerTipo() {
        Integer tipo = Integer.valueOf(0);
        setTramiteDTO(generacionResolucionBussines.obtenerTramite(getDatosBandejaTareaDTO().getNumeroAsunto()));
        List<TipoTramiteDTO> tiposTramite =
                generacionResolucionBussines.getTipoTramiteModulo(getTramiteDTO().getTipoTramite());
        for (TipoTramiteDTO tipoTramiteDTO : tiposTramite) {
            if (tipoTramiteDTO.getClaveModulo() == null) {
                tipo = 0;
            }
            else if (tipoTramiteDTO.getClaveModulo().toString().trim()
                    .equalsIgnoreCase(CadenasConstants.PANEL_TIPO_CLASIFICACION)) {
                tipo = 1;
            }
            else if (tipoTramiteDTO.getClaveModulo().toString().trim()
                    .equalsIgnoreCase(CadenasConstants.PANEL_TIPO_RESARCIMIENTO)) {
                tipo = 2;
            }
            if (tipoTramiteDTO.getDescripcionSubservicio().equalsIgnoreCase(
                    CadenasConstants.RESOLUCION_TYPE_CONSULTA_DESCRIPCION)) {
                setTipoTramiteDescripcion(CadenasConstants.RESOLUCION_TYPE_CONSULTA);
            }
            else {
                setTipoTramiteDescripcion(CadenasConstants.RESOLUCION_TYPE_AUTORIZACION);
            }
        }
        return tipo;
    }

    /**
     * M&eacute;todo para obtener las autoridades del cbo.
     * 
     * @return
     */
    private List<UnidadAdministrativaDTO> getAutoridades() {
        return generacionResolucionBussines.obtenerAutoridades();
    }

    /**
     * M&eacute;todo para llenar los bienes resarcimiento del cbo.
     * 
     * @return
     */
    private List<BienResarcimientoDTO> getBienesResarcimiento() {

        return getGeneracionResolucionBussines().obtenerBienes(getResolucionDatosGeneradosDTO());
    }

    /**
     * M&eacute;todo para obtener los medios de defensa del cbo.
     * 
     * @return
     */
    private List<CatalogoDTO> getListMediosDefensa() {
        return generacionResolucionBussines.obtenerMediosDeDefensa();
    }

    /**
     * M&eacute;todo para llenar los medios de transporte del cbo.
     * 
     * @return
     */
    private List<CatalogoDTO> getMediosDeTransporte() {
        return generacionResolucionBussines.obtenerMediosDeTransporte();
    }

    /**
     * M&eacute;todo para a�adir un bien de resarcimeinto
     * 
     * @param actionEvent
     */
    public void addResarcimientoBien(ActionEvent actionEvent) {

        boolean requierdDataOk = true;
        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(
                getDescripcionBien() == null ? "" : getDescripcionBien())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, errorMsg
                            .getString("cal.resolucion.required.resarcimiento.descripcionBien")));
            requierdDataOk = false;
        }
        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(
                getValorBien() == null ? "" : getValorBien().toString())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, errorMsg
                            .getString("cal.resolucion.required.resarcimiento.valorBien")));
            requierdDataOk = false;
        }

        if (requierdDataOk) {
            BienResarcimientoDTO bienResarcimientoDTO = new BienResarcimientoDTO();
            bienResarcimientoDTO.setIdTableBien(new Date().getTime());
            bienResarcimientoDTO.setDescripcionBien(getDescripcionBien());
            bienResarcimientoDTO.setValor(getValorBien());
            bienResarcimientoDTO.setMedioTransporte(getMedioTransporteDescripcion());
            bienResarcimientoDTOList.add(bienResarcimientoDTO);
            limpiaCampos();
            updateMonto();
            habilitarBotonAgregar();
            RequestContext.getCurrentInstance().reset("formResolucion:formResarcimientoResolucion");
        }
    }

    /**
     * M&eacute;todo para habilitar o inhabilitar el bot&oacute;n de
     * agregar del grid.
     */
    private void habilitarBotonAgregar() {
        setAgregarVisible(bienResarcimientoDTOList.size() == NumerosConstantes.CINCO);
    }

    /**
     * M&eacute;todo para eliminar de la tabla de bienes.
     * 
     * @param actionEvent
     */
    public void eliminarAction(ActionEvent actionEvent) {
        if (getSelectedBienResarcimiento() != null) {
            List<BienResarcimientoDTO> datosOriginales = new ArrayList<BienResarcimientoDTO>();
            datosOriginales.addAll(getBienResarcimientoDTOList());
            for (int i = 0; i < getSelectedBienResarcimiento().length; i++) {
                BienResarcimientoDTO dto = getSelectedBienResarcimiento()[i];
                for (BienResarcimientoDTO bien : datosOriginales) {
                    if (bien.getIdTableBien().equals(dto.getIdTableBien())) {
                        getBienResarcimientoDTOList().remove(bien);
                        bienResarcimientoDTODeleteList.add(bien);
                    }
                }

            }
        }
        updateMonto();
        habilitarBotonAgregar();
        setEliminarVisible(false);
    }

    public void validaEliminar() {
        if (getSelectedBienResarcimiento().length == 1) {
            StringBuffer str = new StringBuffer(messages.getString("cal.resarcimiento.confirmacion.1bien"));
            BienResarcimientoDTO dto = getSelectedBienResarcimiento()[0];
            str.append(dto.getDescripcionBien());
            str.append("?");
            setMsgValidaEliminar(str.toString());
        }
        else {
            StringBuffer str = new StringBuffer(messages.getString("cal.resarcimiento.confirmacion.nbienes"));
            for (int i = 0; i < getSelectedBienResarcimiento().length; i++) {
                BienResarcimientoDTO dto = getSelectedBienResarcimiento()[i];
                str.append(dto.getDescripcionBien());
                if ((i + 1) != getSelectedBienResarcimiento().length) {
                    str.append(", ");
                }
            }
            str.append("?");
            setMsgValidaEliminar(str.toString());
        }
    }

    /**
     * M&eacute;todo para limpiar los campos al agregar un bien.
     */
    private void limpiaCampos() {
        setDescripcionBien("");
        setValorBien(null);
        setMedioTransporte("");

    }

    /**
     * M&eacute;todo para calcular el monto total de los bienes que se
     * van agregando
     */
    private void updateMonto() {
        this.montoCalculado = 0l;
        for (BienResarcimientoDTO data : bienResarcimientoDTOList) {
            if (data.getValor() != null) {
                this.montoCalculado += data.getValor();
            }
        }
    }

    /**
     * M&eacute;todo para ejecutar la acci&oacute;n de siguiente,
     * primero valida los datos necesarios seg&uacute;n el tipo de
     * panel y luego env&iacute;a a adjuntar documento. Si falta
     * alg&uacute;n dato requerido el flujo se detiene.
     * 
     * @return String Action to perform.
     */
    public void siguienteAction() {
        // actualiza datos segun el tipo.
        if (guardarDatosResoluciones()) {
            // manda el flujo a adjuntar (generico se encarga de todo
            // lo demas)
            getAdjuntarDocumentoController().inicializarAdjuntarDocumentos(datosBandejaTareaDTO,
                    TipoDocumentoOficial.OFICIO_RESOLUCION.getClave());

            ConfigurableNavigationHandler configurableNavigationHandler =
                    (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                            .getNavigationHandler();

            // se oculta direccionamiento de NYV
            configurableNavigationHandler.performNavigation(AutorizarConstantes.ADJUNTAR_DOCUMENTO
                    + "?faces-redirect=true");

        }
    }

    /**
     * Obtener la descripci&oacute;n del medio de transporte a partir
     * de su clave
     * 
     * @return descripcio&acute;n del medio de transporte.
     */
    private String getMedioTransporteDescripcion() {
        String descripcion = "";
        if (getMedioTransporte() != null) {
            for (CatalogoDTO medios : getMediosTransporte()) {
                if (medios.getClave().equalsIgnoreCase(getMedioTransporte())) {
                    descripcion = medios.getDescripcion();
                }
            }
        }
        return descripcion;
    }

    /**
     * M&eacute;todo para guardar las resoluciones, datos generados
     * para la resoluci&oacute;n y bienes de resoluci&oacute;n
     * seg&uacute;n el tipo.
     * 
     * @return true si se debe continuar el flojo si no false.
     */
    public boolean guardarDatosResoluciones() {
        boolean continueFlow = true;
        // Se extraen los datos de los paneles y los datos generado
        ResolucionDatosGeneradosDTO dto = getDataDTO();
        // Se validan los datos generales de la pantalla
        continueFlow = validaDatosGenerales(dto);
        dto.setFechaResolucion(getFechaResolucion());
        dto.setNumeroOficio(getNumeroOficio());
        if (getPanelType().toString().equalsIgnoreCase(CadenasConstants.PANEL_TIPO_CLASIFICACION)) {
            if (validaDatosClasificacion(dto) && continueFlow) {
                // actualiza la resolucion y los datos generales de la
                // misma
                getGeneracionResolucionBussines().actualizarResolucion(dto, getResolucionDatosGeneradosDTO());
            }
            else {
                muestraMensajes();
                continueFlow = false;
            }
        }
        else if (getPanelType().toString().equalsIgnoreCase(CadenasConstants.PANEL_TIPO_RESARCIMIENTO)) {
            if (validaDatosBienes(dto) && continueFlow) {
                // actualiza la resolucion, los datos generales y los
                // bienes (agregado y eliminados).
                getGeneracionResolucionBussines().actualizarResolucion(dto, resolucionDatosGeneradosDTO);
            }
            else {
                muestraMensajes();
                continueFlow = false;
            }
        }
        else if (continueFlow) {
            // actualiza una resolucion generica y actualiza los datos
            // generados
            getGeneracionResolucionBussines().actualizarResolucion(dto, getResolucionDatosGeneradosDTO());
        }
        return continueFlow;
    }

    /**
     * Actualiza los recursos asignados a la resolucion
     */
    public void rechazarResolucion(ActionEvent event) {
        generacionResolucionBussines.rechazarResolucion(getDatosBandejaTareaDTO().getNumeroAsunto(),
                getDatosBandejaTareaDTO().getIdTareaUsuario(), getUserProfile().getUsuario(), getDatosBandejaTareaDTO()
                        .getIdSolicitud());

        getFlash().put(
                GeneralConstantes.MENSAJE_REDIRECT,
                "La resoluci\u00F3n para el asunto " + getDatosBandejaTareaDTO().getNumeroAsunto()
                        + " ha sido rechazada.");
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        configurableNavigationHandler.performNavigation(LoginConstante.BANDEA_JSF + "?faces-redirect=true");
    }

    /**
     * 
     */
    private void muestraMensajes() {
        for (String mensaje : getMensajesValidaciones()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, mensaje));
        }
    }

    /**
     * 
     * @return
     */
    private boolean validaDatosBienes(ResolucionDatosGeneradosDTO dto) {
        boolean continueFlow = true;
        mensajesValidaciones = new ArrayList<String>();
        if (getBienResarcimientoDTOList().isEmpty()) {
            mensajesValidaciones.add(errorMsg.getString("cal.resolucion.required.resarcimiento.bienesNecesarios"));
            continueFlow = false;
        }
        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(
                dto.getFechaEmbargo() == null ? null : dto.getFechaEmbargo().toString().trim())) {
            mensajesValidaciones.add(errorMsg.getString("cal.resolucion.required.fechaEmbargo"));
            continueFlow = false;
        }
        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(
                dto.getAutoridadEmbargo() == null ? null : dto.getAutoridadEmbargo().toString().trim())) {
            mensajesValidaciones.add(errorMsg.getString("cal.resolucion.required.resarcimiento.autoridadEmbarga"));
            continueFlow = false;
        }
        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(
                dto.getMontoTotalOrdenado() == null ? null : dto.getMontoTotalOrdenado().toString().trim())) {
            mensajesValidaciones.add(errorMsg.getString("cal.resolucion.required.resarcimiento.montoOrdenadoT"));
            continueFlow = false;
        }
        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(
                dto.getMedioDefensa() == null ? null : dto.getMedioDefensa().toString().trim())) {
            mensajesValidaciones.add(errorMsg.getString("cal.resolucion.required.resarcimiento.medioDefensaRes"));
            continueFlow = false;
        }
        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(
                dto.getNumeroAsunto() == null ? null : dto.getNumeroAsunto().toString().trim())) {
            mensajesValidaciones.add(errorMsg
                    .getString("cal.resolucion.required.resarcimiento.numeroAsuntoResarcimiento"));
            continueFlow = false;
        }
        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(getNumeroAsuntoResarcimiento())) {
            mensajesValidaciones.add(errorMsg
                    .getString("cal.resolucion.required.resarcimiento.numeroAsuntoResarcimiento"));
            continueFlow = false;
        }
        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(
                getCumplimientoSentencia() == null ? null : getCumplimientoSentencia().toString().trim())) {
            mensajesValidaciones.add(errorMsg.getString("cal.resolucion.required.resarcimiento.cumplimientoSentencia"));
            continueFlow = false;
        }

        return continueFlow;
    }

    /**
     * 
     * @return
     */
    private boolean validaDatosClasificacion(ResolucionDatosGeneradosDTO dto) {
        boolean continueFlow = true;
        mensajesValidaciones = new ArrayList<String>();
        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(dto.getFraccionArancelaria())) {
            mensajesValidaciones.add(errorMsg.getString("cal.resolucion.required.fraccionArancelaria"));
            continueFlow = false;
        }
        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(dto.getNumeroMinuta())) {
            mensajesValidaciones.add(errorMsg.getString("cal.resolucion.required.numeroMinuta"));
            continueFlow = false;
        }
        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(dto.getFechaSesion())) {
            mensajesValidaciones.add(errorMsg.getString("cal.resolucion.required.fechaSesion"));
            continueFlow = false;
        }
        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(getFraccion())) {
            mensajesValidaciones.add(errorMsg.getString("cal.resolucion.required.confirmaFraccion"));
            continueFlow = false;
        }
        return continueFlow;
    }

    /**
     * 
     * @return
     */
    private boolean validaDatosGenerales(ResolucionDatosGeneradosDTO dto) {
        boolean continueFlow = true;

        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(dto.getResolucionSelected())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, errorMsg
                            .getString("cal.resolucion.required.resolucion")));
            continueFlow = false;
        }
        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(getNumeroOficio())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, errorMsg
                            .getString("cal.resolucion.required.numeroOficio")));
            continueFlow = false;
        }
        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(getFechaResolucion())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, errorMsg
                            .getString("cal.resolucion.required.fechaResolucion")));
            continueFlow = false;
        }
        return continueFlow;
    }

    /**
     * 
     */
    public void anteriorAction() {
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        configurableNavigationHandler.performNavigation(LoginConstante.BANDEA_JSF);

    }

    /**
     * Method to build ResolucionDTO
     * 
     * @return ResolucionDTO
     */
    private ResolucionDatosGeneradosDTO getDataDTO() {
        ResolucionDatosGeneradosDTO resolucionCALDTO = new ResolucionDatosGeneradosDTO();
        resolucionCALDTO.setResolucionSelected(getResolucionCbo() == null ? "" : getResolucionCboDescripcion());
        resolucionCALDTO.setIdEsentreSolucion(getResolucionCbo() == null ? "" : getResolucionCbo());
        if (getPanelType().intValue() == 1) {
            resolucionCALDTO.setNumeroOficio(getNumeroOficio());
            resolucionCALDTO.setFechaResolucion(getFechaResolucion());
            resolucionCALDTO.setFraccionArancelaria(getFraccionArancel());
            resolucionCALDTO.setNumeroMinuta(getMinuta());
            resolucionCALDTO.setFechaSesion(getFechaSesion() == null ? null : getFechaSesion());
            resolucionCALDTO.setNumeroAsunto(getDatosBandejaTareaDTO().getNumeroAsunto());
            if (getFraccion() != null) {
                resolucionCALDTO.setConfirmaFraccion("1".equals(getFraccion()));
            }
        }
        else if (getPanelType().intValue() == 2) {
            // PAra Resarcimiento
            resolucionCALDTO.setFechaEmbargo(getFechaEmbargo());
            resolucionCALDTO.setAutoridadEmbargo(getAutoridadEmbarga());
            resolucionCALDTO.setMontoTotal(getMontoCalculado() == null ? null : new BigDecimal(getMontoCalculado()));
            resolucionCALDTO.setMontoTotalOrdenado(getMontoTotalOrdenado() == null ? null : new BigDecimal(
                    getMontoTotalOrdenado()));
            resolucionCALDTO.setMedioDefensa(getMedioDefensa());
            resolucionCALDTO.setNumeroAsunto(getDatosBandejaTareaDTO().getNumeroAsunto());
            resolucionCALDTO.setCumplimentacion((getCumplimientoSentencia() != null && getCumplimientoSentencia()
                    .equals("1")));
            resolucionCALDTO
                    .setMontoSentencia(getMontoSentencia() == null ? null : new BigDecimal(getMontoSentencia()));
            resolucionCALDTO.setBienesResolucion(bienResarcimientoDTOList);
            resolucionCALDTO.setBienesResolucionDelete(bienResarcimientoDTODeleteList);
            resolucionCALDTO.setNumeroOficio(getNumeroOficio());
            resolucionCALDTO.setNumeroAsuntoResarcimiento(getNumeroAsuntoResarcimiento());
            resolucionCALDTO.setFechaResolucion(getFechaResolucion());
        }

        // necesario para inserciones y actualizaciones
        resolucionCALDTO.setTramiteDTO(getTramiteDTO());

        return resolucionCALDTO;
    }

    /**
     * 
     * @return
     */
    private String getResolucionCboDescripcion() {
        String descripcion = "";
        String clv = getResolucionCbo();
        for (CatalogoDTO data : getListaResolucion()) {
            if (data.getClave().equalsIgnoreCase(clv)) {
                descripcion = data.getDescripcion();
            }
        }
        return descripcion;
    }

    /**
     * 
     * @param event
     */
    public void rowSelectCheckbox(SelectEvent event) {

        setEliminarVisible(getSelectedBienResarcimiento().length > 0);

    }

    /**
     * 
     * @param event
     */
    public void rowUnselectCheckbox(UnselectEvent event) {
        setEliminarVisible(getSelectedBienResarcimiento().length > 0);
    }

    /**
     * Method to get all available Autorizadores from data base. needs
     * the numero de asunto to perform the search.
     * 
     * @param string
     *            numeroAsunto
     * @return
     */
    private List<CatalogoDTO> getListaAutorizadores(String numeroAsunto) {
        return generacionResolucionBussines.getAutorizadores(numeroAsunto);
    }

    /**
     * Metodo para obtener la lista que llena el combo de resultados
     * filtra por tipo de consulta.
     * 
     * @param string
     * @return
     */
    private List<CatalogoDTO> getListaResolucion(String type) {
        return generacionResolucionBussines.obtenerResolucionCatalog(type);
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
     * @return the modalidadType
     */
    public Integer getModalidadType() {
        return modalidadType;
    }

    /**
     * @param modalidadType
     *            the modalidadType to set
     */
    public void setModalidadType(Integer modalidadType) {
        this.modalidadType = modalidadType;
    }

    /**
     * @return the listaResolucion
     */
    public List<CatalogoDTO> getListaResolucion() {
        return listaResolucion;
    }

    /**
     * @param listaResolucion
     *            the listaResolucion to set
     */
    public void setListaResolucion(List<CatalogoDTO> listaResolucion) {
        this.listaResolucion = listaResolucion;
    }

    /**
     * @return the listaUsuarios
     */
    public List<CatalogoDTO> getListaUsuarios() {
        return listaUsuarios;
    }

    /**
     * @param listaUsuarios
     *            the listaUsuarios to set
     */
    public void setListaUsuarios(List<CatalogoDTO> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    /**
     * @return the generacionResolucionBussines
     */
    public GeneracionResolucionCALBussines getGeneracionResolucionBussines() {
        return generacionResolucionBussines;
    }

    /**
     * @param generacionResolucionBussines
     *            the generacionResolucionBussines to set
     */
    public void setGeneracionResolucionBussines(GeneracionResolucionCALBussines generacionResolucionBussines) {
        this.generacionResolucionBussines = generacionResolucionBussines;
    }

    /**
     * @return the fechaSesion
     */
    public Date getFechaSesion() {
        return fechaSesion != null ? (Date) fechaSesion.clone() : null;
    }

    /**
     * @param fechaSesion
     *            the fechaSesion to set
     */
    public void setFechaSesion(Date fechaSesion) {
        if (fechaSesion != null) {
            this.fechaSesion = (Date) fechaSesion.clone();
        }
        else {
            this.fechaSesion = null;
        }
    }

    /**
     * @return the fraccion
     */
    public String getFraccion() {
        return fraccion;
    }

    /**
     * @param fraccion
     *            the fraccion to set
     */
    public void setFraccion(String fraccion) {
        this.fraccion = fraccion;
    }

    /**
     * @return the minuta
     */
    public String getMinuta() {
        return minuta;
    }

    /**
     * @param minuta
     *            the minuta to set
     */
    public void setMinuta(String minuta) {
        this.minuta = minuta;
    }

    /**
     * @return the resolucion
     */
    public CatalogoDTO getResolucion() {
        return resolucion;
    }

    /**
     * @param resolucion
     *            the resolucion to set
     */
    public void setResolucion(CatalogoDTO resolucion) {
        this.resolucion = resolucion;
    }

    /**
     * @return the nombreFirma
     */
    public CatalogoDTO getNombreFirma() {
        return nombreFirma;
    }

    /**
     * @param nombreFirma
     *            the nombreFirma to set
     */
    public void setNombreFirma(CatalogoDTO nombreFirma) {
        this.nombreFirma = nombreFirma;
    }

    /**
     * @return the fraccionArancel
     */
    public String getFraccionArancel() {
        return fraccionArancel;
    }

    /**
     * @param fraccionArancel
     *            the fraccionArancel to set
     */
    public void setFraccionArancel(String fraccionArancel) {
        this.fraccionArancel = fraccionArancel;
    }

    /**
     * @return the generarResolucionValidator
     */
    public GenerarResolucionValidator getGenerarResolucionValidator() {
        return generarResolucionValidator;
    }

    /**
     * @param generarResolucionValidator
     *            the generarResolucionValidator to set
     */
    public void setGenerarResolucionValidator(GenerarResolucionValidator generarResolucionValidator) {
        this.generarResolucionValidator = generarResolucionValidator;
    }

    /**
     * @return the resolucionCbo
     */
    public String getResolucionCbo() {
        return resolucionCbo;
    }

    /**
     * @param resolucionCbo
     *            the resolucionCbo to set
     */
    public void setResolucionCbo(String resolucionCbo) {
        this.resolucionCbo = resolucionCbo;
    }

    /**
     * @return the nombreFirmaCbo
     */
    public String getNombreFirmaCbo() {
        return nombreFirmaCbo;
    }

    /**
     * @param nombreFirmaCbo
     *            the nombreFirmaCbo to set
     */
    public void setNombreFirmaCbo(String nombreFirmaCbo) {
        this.nombreFirmaCbo = nombreFirmaCbo;
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

    /**
     * @return the adminLocal
     */
    public String getAdminLocal() {
        return adminLocal;
    }

    /**
     * @param adminLocal
     *            the adminLocal to set
     */
    public void setAdminLocal(String adminLocal) {
        this.adminLocal = adminLocal;
    }

    /**
     * @return the repLegalInfo
     */
    public String getRepLegalInfo() {
        return repLegalInfo;
    }

    /**
     * @param repLegalInfo
     *            the repLegalInfo to set
     */
    public void setRepLegalInfo(String repLegalInfo) {
        this.repLegalInfo = repLegalInfo;
    }

    /**
     * @return the buzon
     */
    public String getBuzon() {
        return buzon;
    }

    /**
     * @param buzon
     *            the buzon to set
     */
    public void setBuzon(String buzon) {
        this.buzon = buzon;
    }

    /**
     * @return the municipio
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio
     *            the municipio to set
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado
     *            the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the cp
     */
    public String getCp() {
        return cp;
    }

    /**
     * @param cp
     *            the cp to set
     */
    public void setCp(String cp) {
        this.cp = cp;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono
     *            the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the calle
     */
    public String getCalle() {
        return calle;
    }

    /**
     * @param calle
     *            the calle to set
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * @return the numExt
     */
    public String getNumExt() {
        return numExt;
    }

    /**
     * @param numExt
     *            the numExt to set
     */
    public void setNumExt(String numExt) {
        this.numExt = numExt;
    }

    /**
     * @return the numInt
     */
    public String getNumInt() {
        return numInt;
    }

    /**
     * @param numInt
     *            the numInt to set
     */
    public void setNumInt(String numInt) {
        this.numInt = numInt;
    }

    /**
     * @return the colonia
     */
    public String getColonia() {
        return colonia;
    }

    /**
     * @param colonia
     *            the colonia to set
     */
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    /**
     * @return the apellidoPat
     */
    public String getApellidoPat() {
        return apellidoPat;
    }

    /**
     * @param apellidoPat
     *            the apellidoPat to set
     */
    public void setApellidoPat(String apellidoPat) {
        this.apellidoPat = apellidoPat;
    }

    /**
     * @return the apellidoMat
     */
    public String getApellidoMat() {
        return apellidoMat;
    }

    /**
     * @param apellidoMat
     *            the apellidoMat to set
     */
    public void setApellidoMat(String apellidoMat) {
        this.apellidoMat = apellidoMat;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the numeroAsuntoResarcimiento
     */
    public String getNumeroAsuntoResarcimiento() {
        return numeroAsuntoResarcimiento;
    }

    /**
     * @param numeroAsuntoResarcimiento
     *            the numeroAsuntoResarcimiento to set
     */
    public void setNumeroAsuntoResarcimiento(String numeroAsuntoResarcimiento) {
        this.numeroAsuntoResarcimiento = numeroAsuntoResarcimiento;
    }

    /**
     * @return the montoSentencia
     */
    public Long getMontoSentencia() {
        return montoSentencia;
    }

    /**
     * @param montoSentencia
     *            the montoSentencia to set
     */
    public void setMontoSentencia(Long montoSentencia) {
        this.montoSentencia = montoSentencia;
    }

    /**
     * @return the medioDefensa
     */
    public String getMedioDefensa() {
        return medioDefensa;
    }

    /**
     * @param medioDefensa
     *            the medioDefensa to set
     */
    public void setMedioDefensa(String medioDefensa) {
        this.medioDefensa = medioDefensa;
    }

    /**
     * @return the autoridadEmbarga
     */
    public String getAutoridadEmbarga() {
        return autoridadEmbarga;
    }

    /**
     * @param autoridadEmbarga
     *            the autoridadEmbarga to set
     */
    public void setAutoridadEmbarga(String autoridadEmbarga) {
        this.autoridadEmbarga = autoridadEmbarga;
    }

    /**
     * @return the cumplimientoSentencia
     */
    public String getCumplimientoSentencia() {
        return cumplimientoSentencia;
    }

    /**
     * @param cumplimientoSentencia
     *            the cumplimientoSentencia to set
     */
    public void setCumplimientoSentencia(String cumplimientoSentencia) {
        this.cumplimientoSentencia = cumplimientoSentencia;
    }

    /**
     * @return the montoTotalOrdenado
     */
    public Long getMontoTotalOrdenado() {
        return montoTotalOrdenado;
    }

    /**
     * @param montoTotalOrdenado
     *            the montoTotalOrdenado to set
     */
    public void setMontoTotalOrdenado(Long montoTotalOrdenado) {
        this.montoTotalOrdenado = montoTotalOrdenado;
    }

    /**
     * @return the fechaEmbargo
     */
    public Date getFechaEmbargo() {
        return fechaEmbargo != null ? (Date) fechaEmbargo.clone() : null;
    }

    /**
     * @param fechaEmbargo
     *            the fechaEmbargo to set
     */
    public void setFechaEmbargo(Date fechaEmbargo) {
        if (fechaEmbargo != null) {
            this.fechaEmbargo = (Date) fechaEmbargo.clone();
        }
        else {
            this.fechaEmbargo = null;
        }
    }

    /**
     * @return the medioTransporte
     */
    public String getMedioTransporte() {
        return medioTransporte;
    }

    /**
     * @param medioTransporte
     *            the medioTransporte to set
     */
    public void setMedioTransporte(String medioTransporte) {
        this.medioTransporte = medioTransporte;
    }

    /**
     * @return the valorBien
     */
    public Long getValorBien() {
        return valorBien;
    }

    /**
     * @param valorBien
     *            the valorBien to set
     */
    public void setValorBien(Long valorBien) {
        this.valorBien = valorBien;
    }

    /**
     * @return the descripcionBien
     */
    public String getDescripcionBien() {
        return descripcionBien;
    }

    /**
     * @param descripcionBien
     *            the descripcionBien to set
     */
    public void setDescripcionBien(String descripcionBien) {
        this.descripcionBien = descripcionBien;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave
     *            the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the firmante
     */
    public String getFirmante() {
        return firmante;
    }

    /**
     * @param firmante
     *            the firmante to set
     */
    public void setFirmante(String firmante) {
        this.firmante = firmante;
    }

    /**
     * @return the montoCalculado
     */
    public Long getMontoCalculado() {
        return montoCalculado;
    }

    /**
     * @param montoCalculado
     *            the montoCalculado to set
     */
    public void setMontoCalculado(Long montoCalculado) {
        this.montoCalculado = montoCalculado;
    }

    /**
     * @return the mediosDefensa
     */
    public List<CatalogoDTO> getMediosDefensa() {
        return mediosDefensa;
    }

    /**
     * @param mediosDefensa
     *            the mediosDefensa to set
     */
    public void setMediosDefensa(List<CatalogoDTO> mediosDefensa) {
        this.mediosDefensa = mediosDefensa;
    }

    /**
     * @return the mediosTransporte
     */
    public List<CatalogoDTO> getMediosTransporte() {
        return mediosTransporte;
    }

    /**
     * @param mediosTransporte
     *            the mediosTransporte to set
     */
    public void setMediosTransporte(List<CatalogoDTO> mediosTransporte) {
        this.mediosTransporte = mediosTransporte;
    }

    /**
     * @return the bienResarcimientoDataModel
     */
    public BienResarcimientoDataModel getBienResarcimientoDataModel() {
        return bienResarcimientoDataModel;
    }

    /**
     * @param bienResarcimientoDataModel
     *            the bienResarcimientoDataModel to set
     */
    public void setBienResarcimientoDataModel(BienResarcimientoDataModel bienResarcimientoDataModel) {
        this.bienResarcimientoDataModel = bienResarcimientoDataModel;
    }

    /**
     * @return the bienResarcimientoDTOList
     */
    public List<BienResarcimientoDTO> getBienResarcimientoDTOList() {
        return bienResarcimientoDTOList;
    }

    /**
     * @param bienResarcimientoDTOList
     *            the bienResarcimientoDTOList to set
     */
    public void setBienResarcimientoDTOList(List<BienResarcimientoDTO> bienResarcimientoDTOList) {
        this.bienResarcimientoDTOList = bienResarcimientoDTOList;
    }

    /**
     * @return the autoridadesEmbargo
     */
    public List<UnidadAdministrativaDTO> getAutoridadesEmbargo() {
        return autoridadesEmbargo;
    }

    /**
     * @param autoridadesEmbargo
     *            the autoridadesEmbargo to set
     */
    public void setAutoridadesEmbargo(List<UnidadAdministrativaDTO> autoridadesEmbargo) {
        this.autoridadesEmbargo = autoridadesEmbargo;
    }

    /**
     * @return the selectedBienResarcimiento
     */
    public BienResarcimientoDTO[] getSelectedBienResarcimiento() {
        if (selectedBienResarcimiento != null) {
            BienResarcimientoDTO[] arreglo = new BienResarcimientoDTO[selectedBienResarcimiento.length];
            for (int i = 0; i < selectedBienResarcimiento.length; i++) {
                BienResarcimientoDTO o = selectedBienResarcimiento[i];
                arreglo[i] = o;
            }
            return arreglo;
        }
        else {
            return null;
        }
    }

    /**
     * @param selectedBienResarcimiento
     *            the selectedBienResarcimiento to set
     */
    public void setSelectedBienResarcimiento(BienResarcimientoDTO[] selectedBienResarcimientoArray) {
        if (selectedBienResarcimientoArray != null) {
            BienResarcimientoDTO[] selectedBienResarcimientoLocal = selectedBienResarcimientoArray.clone();
            BienResarcimientoDTO[] arreglo = new BienResarcimientoDTO[selectedBienResarcimientoLocal.length];
            for (int i = 0; i < selectedBienResarcimientoLocal.length; i++) {
                BienResarcimientoDTO o = selectedBienResarcimientoLocal[i];
                arreglo[i] = o;
            }
            this.selectedBienResarcimiento = arreglo;
        }
        else {
            this.selectedBienResarcimiento = null;
        }
    }

    /**
     * @return the mensajesValidaciones
     */
    public List<String> getMensajesValidaciones() {
        return mensajesValidaciones;
    }

    /**
     * @param mensajesValidaciones
     *            the mensajesValidaciones to set
     */
    public void setMensajesValidaciones(List<String> mensajesValidaciones) {
        this.mensajesValidaciones = mensajesValidaciones;
    }

    /**
     * @return the datosBandejaTareaDTO
     */
    public DatosBandejaTareaDTO getDatosBandejaTareaDTO() {
        return datosBandejaTareaDTO;
    }

    /**
     * @param datosBandejaTareaDTO
     *            the datosBandejaTareaDTO to set
     */
    public void setDatosBandejaTareaDTO(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        this.datosBandejaTareaDTO = datosBandejaTareaDTO;
    }

    /**
     * @return the panelType
     */
    public Integer getPanelType() {
        return panelType;
    }

    /**
     * @param panelType
     *            the panelType to set
     */
    public void setPanelType(Integer panelType) {
        this.panelType = panelType;
    }

    /**
     * @return the tramiteDTO
     */
    public TramiteDTO getTramiteDTO() {
        return tramiteDTO;
    }

    /**
     * @param tramiteDTO
     *            the tramiteDTO to set
     */
    public void setTramiteDTO(TramiteDTO tramiteDTO) {
        this.tramiteDTO = tramiteDTO;
    }

    /**
     * @param rol
     *            the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * @return the adjuntarDocumentoController
     */
    public AdjuntarDocumentoController getAdjuntarDocumentoController() {
        return adjuntarDocumentoController;
    }

    /**
     * @param adjuntarDocumentoController
     *            the adjuntarDocumentoController to set
     */
    public void setAdjuntarDocumentoController(AdjuntarDocumentoController adjuntarDocumentoController) {
        this.adjuntarDocumentoController = adjuntarDocumentoController;
    }

    /**
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * @return the firmarTareaController
     */
    public FirmarTareaController getFirmarTareaController() {
        return firmarTareaController;
    }

    /**
     * @param firmarTareaController
     *            the firmarTareaController to set
     */
    public void setFirmarTareaController(FirmarTareaController firmarTareaController) {
        this.firmarTareaController = firmarTareaController;
    }

    /**
     * @return the resolucionDatosGeneradosDTO
     */
    public ResolucionDatosGeneradosDTO getResolucionDatosGeneradosDTO() {
        return resolucionDatosGeneradosDTO;
    }

    /**
     * @param resolucionDatosGeneradosDTO
     *            the resolucionDatosGeneradosDTO to set
     */
    public void setResolucionDatosGeneradosDTO(ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO) {
        this.resolucionDatosGeneradosDTO = resolucionDatosGeneradosDTO;
    }

    /**
     * @return the tipoTramiteDescripcion
     */
    public String getTipoTramiteDescripcion() {
        return tipoTramiteDescripcion;
    }

    /**
     * @param tipoTramiteDescripcion
     *            the tipoTramiteDescripcion to set
     */
    public void setTipoTramiteDescripcion(String tipoTramiteDescripcion) {
        this.tipoTramiteDescripcion = tipoTramiteDescripcion;
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
     * @return the agregarVisible
     */
    public boolean isAgregarVisible() {
        return agregarVisible;
    }

    /**
     * @param agregarVisible
     *            the agregarVisible to set
     */
    public void setAgregarVisible(boolean agregarVisible) {
        this.agregarVisible = agregarVisible;
    }

    /**
     * @return the bienResarcimientoDTODeleteList
     */
    public List<BienResarcimientoDTO> getBienResarcimientoDTODeleteList() {
        return bienResarcimientoDTODeleteList;
    }

    /**
     * @param bienResarcimientoDTODeleteList
     *            the bienResarcimientoDTODeleteList to set
     */
    public void setBienResarcimientoDTODeleteList(List<BienResarcimientoDTO> bienResarcimientoDTODeleteList) {
        this.bienResarcimientoDTODeleteList = bienResarcimientoDTODeleteList;
    }

    public String getNumeroOficio() {
        return numeroOficio;
    }

    public void setNumeroOficio(String numeroOficio) {
        this.numeroOficio = numeroOficio;
    }

    public Date getFechaResolucion() {
        return fechaResolucion != null ? (Date) fechaResolucion.clone() : null;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        if (fechaResolucion != null) {
            this.fechaResolucion = (Date) fechaResolucion.clone();
        }
        else {
            this.fechaResolucion = null;
        }
    }

    public String getMsgValidaEliminar() {
        return msgValidaEliminar;
    }

    public void setMsgValidaEliminar(String msgValidaEliminar) {
        this.msgValidaEliminar = msgValidaEliminar;
    }

    public Date getFechaMinima() {
        return fechaMinima != null ? (Date) fechaMinima.clone() : null;
    }

    public void setFechaMinima(Date fechaMinima) {
        this.fechaMinima = fechaMinima != null ? (Date) fechaMinima.clone() : null;
    }

}
