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
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.base.web.util.validador.GenerarResolucionValidator;
import mx.gob.sat.siat.juridica.cal.dto.BienResarcimientoDTO;
import mx.gob.sat.siat.juridica.cal.dto.ResolucionDatosGeneradosDTO;
import mx.gob.sat.siat.juridica.cal.util.constants.CadenasConstants;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.business.GeneracionResolucionCALBussines;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.model.BienResarcimientoDataModel;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.GeneralConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.RequerimientoConstante;
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
@ManagedBean(name = "generarResolucionController")
public class GenerarResolucionClasificacionCALController extends BaseControllerBean {

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
     * tipo de modalidad para los paneles dinamicos
     */
    private Integer modalidadType;
    /**
     * lista de resoluci&oacute;n para el combo asociado
     */
    private List<CatalogoDTO> listaResolucion = new ArrayList<CatalogoDTO>();
    /**
     * Lista de usuarios para el combo asociado
     */
    private List<CatalogoDTO> listaUsuarios = new ArrayList<CatalogoDTO>();
    /**
     * Variable para el panel de clasificac&iacute;n
     */
    private Date fechaSesion;

    private Date fechaResolucion;
    /**
     * Variable para el panel de clasificac&iacute;n
     */
    private String fraccion;
    /**
     * Variable para el panel de clasificac&iacute;n
     */
    private String minuta;
    /**
     * Variable de selecci&oacute;n del combo resoluci&oacute;n
     */
    private String resolucionCbo;
    /**
     * Variable del combo de la persona que firma.
     */
    private String nombreFirmaCbo;
    /**
     * dto de resoluciones
     */
    private CatalogoDTO resolucion;
    /**
     * dto de nombre de quien firma
     */
    private CatalogoDTO nombreFirma;
    /**
     * variable de la fracci&oacute;n arancelaria
     */
    private String fraccionArancel;
    /**
     * lista con los medios de defensa
     */
    private List<CatalogoDTO> mediosDefensa = new ArrayList<CatalogoDTO>();
    /**
     * lista con los medios de transporte
     */
    private List<CatalogoDTO> mediosTransporte = new ArrayList<CatalogoDTO>();
    /**
     * Lista con las autoridades para el embargo
     */
    private List<UnidadAdministrativaDTO> autoridadesEmbargo = new ArrayList<UnidadAdministrativaDTO>();
    /**
     * BienResarcimientoDataModel para la tabla
     */
    private BienResarcimientoDataModel bienResarcimientoDataModel;
    /**
     * Fecha maxima
     */
    private Date fechaMaxima;
    /**
     * variable administraci&oacute;n local.
     */
    private String adminLocal;
    /**
     * variable con la representaci&oacute;n legal info
     */
    private String repLegalInfo;
    /**
     * variable buzon
     */
    private String buzon;
    /**
     * variable municipio
     */
    private String municipio;
    /**
     * variable estado
     */
    private String estado;
    /**
     * variable c&oacute;digo postal
     */
    private String cp;
    /**
     * variable tel&eacute;fono
     */
    private String telefono;
    /**
     * variable calle
     */
    private String calle;
    /**
     * variable n&uacute;mero exterior
     */
    private String numExt;
    /**
     * variable n&uacute;mero Interior
     */
    private String numInt;
    /**
     * variable colonia
     */
    private String colonia;
    /**
     * varianle apellido paterno
     */
    private String apellidoPat;
    /**
     * variable apellido materno
     */
    private String apellidoMat;
    /**
     * variable nombre
     */
    private String nombre;
    /**
     * variable tipo de panel para la selecci&oacute;n
     */
    private Integer panelType;
    /**
     * variable n&uacute;mero asunto resarcimiento, se selecciona
     */
    private String numeroAsuntoResarcimiento;
    /**
     * variable monto de sentencia
     */
    private Long montoSentencia;
    /**
     * variable medio de defensa
     */
    private String medioDefensa;
    /**
     * variable autoridad que embarga
     */
    private String autoridadEmbarga;
    /**
     * variable cumplimiento sentencia
     */
    private String cumplimientoSentencia;
    /**
     * variable monto total Ordenado
     */
    private Long montoTotalOrdenado;
    /**
     * variable fecha de embargo
     */
    private Date fechaEmbargo;
    /**
     * variable medio de trasporte
     */
    private String medioTransporte;

    /**
     * variable valor del bien
     */
    private Long valorBien;
    /**
     * variable descripci&oacute;n del bien
     */
    private String descripcionBien;
    /**
     * variable clave
     */
    private String clave;
    /**
     * variable firmante
     */
    private String firmante;
    /**
     * variable monto calculado
     */
    private Long montoCalculado;
    /**
     * Lista de resoluciones a guardar.
     */
    private List<BienResarcimientoDTO> bienResarcimientoDTOList = new ArrayList<BienResarcimientoDTO>();
    /**
     * DTO BienResarcimientoDTO seleccionado
     */
    private BienResarcimientoDTO[] selectedBienResarcimiento;
    /**
     * Lista de mensajes para mostrar las validaciones
     */
    private List<String> mensajesValidaciones;
    /**
     * DTO con los datos de la bandeja
     */
    private DatosBandejaTareaDTO datosBandejaTareaDTO;
    /**
     * Tramite DTO con los datos completos
     */
    private TramiteDTO tramiteDTO;
    /**
     * Rol
     */
    private String rol;
    /**
     * Para la selecci&oacute;n del check en tabla
     */
    private boolean eliminarVisible = true;
    /**
     * Para habilitar el bot&oacute;n
     */
    private boolean agregarVisible;
    /**
     * Variable para llenar un tipo de combo
     */
    private String tipoTramiteDescripcion;

    private String numeroOficio;
    /**
     * mensajer&iacute;a
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    /**
     * mensajes de error
     */
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    private String msgValidaEliminar = "";

    private Date fechaMinima;

    private ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO;
    
    
    /**
     * Constructor inicial de la clase carga los datos, inicializa la
     * pantalla.
     */
    @PostConstruct
    public void initAtenderPromocion() {
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));
        obtenerDatosAprovacion(getDatosBandejaTareaDTO().getNumeroAsunto());
        setPanelType(obtenerTipo());
        fechaMaxima = new Date();
        bienResarcimientoDTOList = new ArrayList<BienResarcimientoDTO>();
        listaUsuarios.addAll(getListaAutorizadores(getDatosBandejaTareaDTO().getNumeroAsunto()));
        listaResolucion.addAll(getListaResolucion(getTipoTramiteDescripcion()));
        if (getPanelType().toString().equalsIgnoreCase(CadenasConstants.PANEL_TIPO_RESARCIMIENTO)) {
            mediosDefensa.addAll(getListMediosDefensa());
            mediosTransporte.addAll(getMediosDeTransporte());
            autoridadesEmbargo.addAll(getAutoridades());
            if (null != getResolucionDatosGeneradosDTO()) {
                bienResarcimientoDTOList = getBienesResarcimiento();
            }
            bienResarcimientoDataModel = new BienResarcimientoDataModel(bienResarcimientoDTOList);

            /**
             * 
             */
            if (bienResarcimientoDTOList.size() == NumerosConstantes.CINCO) {
                setAgregarVisible(true);
            }
            else {
                setAgregarVisible(false);
            }
            llenaDatosResarcimiento();
        }
        else if (getPanelType().toString().equalsIgnoreCase(CadenasConstants.PANEL_TIPO_CLASIFICACION)) {
            if (null != getResolucionDatosGeneradosDTO()) {
                llenaDatosClasificacion();
            }

        }
        else {
            if (null != getResolucionDatosGeneradosDTO()) {
                colocaDatosGenerales();
            }
        }
        fechaMinima = tramiteDTO.getFechaRecepcion();
    }

    private void obtenerDatosAprovacion(String numeroAsunto) {
        ResolucionDatosGeneradosDTO resol = getGeneracionResolucionBussines().getDatosResolucion(numeroAsunto);
        if (null != resol) {
            setResolucionDatosGeneradosDTO(resol);
        }
    }

    private void colocaDatosGenerales() {

        String resolucionDesSelected = getResolucionDatosGeneradosDTO().getResolucionSelected();
        for (CatalogoDTO resoluciones : listaResolucion) {
            if (resoluciones.getDescripcion().equalsIgnoreCase(resolucionDesSelected)) {
                this.setResolucionCbo(resoluciones.getClave());
            }
        }

    }

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

            updateMonto();
        }
    }

    private void llenaDatosClasificacion() {
        if (getResolucionDatosGeneradosDTO() != null) {
            colocaDatosGenerales();
            this.setFraccionArancel(getResolucionDatosGeneradosDTO().getFraccionArancelaria());
            this.setFraccion(getResolucionDatosGeneradosDTO().isConfirmaFraccion() ? "1" : "0");
            this.setMinuta(null == getResolucionDatosGeneradosDTO().getNumeroMinuta() ? null
                    : getResolucionDatosGeneradosDTO().getNumeroMinuta().trim());
            this.setFechaSesion(getResolucionDatosGeneradosDTO().getFechaSesion());
        }
    }

    /**
     * M&eacute;todo para obtener el tipo de tramite a tratar en la
     * resoluci&oacute;n, 1 para clasificaci&oacute;n, 2 para
     * resarcimiento.
     * 
     * @return
     */
    private Integer obtenerTipo() {
        Integer tipo = Integer.valueOf(0);
        setTramiteDTO(generacionResolucionBussines.obtenerTramite(getDatosBandejaTareaDTO().getNumeroAsunto()));
        List<TipoTramiteDTO> tiposTramite =
                generacionResolucionBussines.getTipoTramiteModulo(getTramiteDTO().getTipoTramite());
        for (TipoTramiteDTO tipoTramiteDTO : tiposTramite) {
            Integer idTipoTramite = tipoTramiteDTO.getIdTipoTramite();
            if (idTipoTramite.equals(Integer.valueOf(DiscriminadorConstants.T2_CLASIFICACION_ARANCELARIA))) {
                tipo = 1;
            }
            else if (idTipoTramite.equals(Integer.valueOf(DiscriminadorConstants.T2_RESARCIMIENTO)) 
                    || idTipoTramite.equals(Integer.valueOf(DiscriminadorConstants.T2_RESARCIMIENTO_ECONOMICO_DESCOMPOCISION_ANIMALES))
                    || idTipoTramite.equals(Integer.valueOf(DiscriminadorConstants.T2_RESARCIMIENTO_ECONOMICO_DESTRUCCION_MERCANCIAS))
                    || idTipoTramite.equals(Integer.valueOf(DiscriminadorConstants.T2_RESARCIMIENTO_ECONOMICO_ESPECIE))) {
                tipo = 2;
            }
            else {
                tipo = NumerosConstantes.TRES;
            }

            if (tipoTramiteDTO.getDescripcionSubservicio().equalsIgnoreCase(
                    CadenasConstants.RESOLUCION_TYPE_CONSULTA_DESCRIPCION)) {
                setTipoTramiteDescripcion(CadenasConstants.RESOLUCION_TYPE_CONSULTA);
            } else if (tipoTramiteDTO.getDescripcionSubservicio().equalsIgnoreCase(
                    CadenasConstants.RESOLUCION_TYPE_AVISO_DESCRIPCION)) {
                setTipoTramiteDescripcion(CadenasConstants.RESOLUCION_TYPE_AVISO);
            } else {
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
     * M&eacute;todo para agregar un bien en la tabla de
     * resarcimiento.
     * 
     * @param actionEvent
     */
    public void addResarcimientoBien(ActionEvent actionEvent) {
        boolean requierdDataOk = true;
        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(getDescripcionBien())) {
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
                                                                      // id
                                                                      // unico
                                                                      // de
                                                                      // la
                                                                      // tabla
            bienResarcimientoDTO.setDescripcionBien(getDescripcionBien());
            bienResarcimientoDTO.setValor(getValorBien());
            bienResarcimientoDTO.setMedioTransporte(getMedioTransporteDescripcion());
            bienResarcimientoDTOList.add(bienResarcimientoDTO);
            limpiaCampos();
            updateMonto();
            habilitarBotonAgregar();
        }
    }

    /**
     * Obtener la descripci&oacute;n del medio de transporte a partir
     * de su clave
     * 
     * @return
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
                    }
                }

            }
        }
        updateMonto();
        habilitarBotonAgregar();
        setEliminarVisible(true);
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
     * M&eacute;todo para habilitar o inhabilitar el bot&oacute;n de
     * agregar del grid.
     */
    private void habilitarBotonAgregar() {
        if (bienResarcimientoDTOList.size() == NumerosConstantes.CINCO) {
            setAgregarVisible(true);
        }
        else {
            setAgregarVisible(false);
        }

    }

    /**
     * M&eacute;tdodo para limpiar los campos de pantalla al agregar
     * un bien resarcimiento.
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
     * Method to execute the following action into juridica resolucion
     * flow, first validate required data. if required field are
     * missing shows error otherwise sends the next step into flow.
     * 
     * @return String Action to perform.
     */
    public void siguienteAction() {
              /**
         * bln avanzarResolucion
         * Este bln solo es activo cuando: 
         * 1) No se tienen requerimientos asociados al tramite
         * 2) Se tienen requerimientos a Autoridad Externa y 
         *                     dicho requerimiento tiene capturada la fecha de verificacion (notificacion)
         */
              if(getGeneracionResolucionBussines().obtenerBlnAvanzarResolucion(tramiteDTO.getNumeroAsunto())){
                  // Estos datos son requeridos por ambos paneles
                  String mensaje =
                          "La Resoluci\u00F3n del asunto " + getDatosBandejaTareaDTO().getNumeroAsunto()
                                  + " ha sido enviada para ser autorizada.";
                  ResolucionDatosGeneradosDTO dto = getDataDTO();
                  boolean basicoOk = validaDatosGenerales(dto);
                  if (getPanelType().toString().equalsIgnoreCase(CadenasConstants.PANEL_TIPO_CLASIFICACION)) {
                      if (validaDatosClasificacion(dto) && basicoOk) {
                          getGeneracionResolucionBussines().guardaClasificacion(dto, getUserProfile().getRfc());
                          getGeneracionResolucionBussines().actualizaDatosBP(getDatosBandejaTareaDTO(), dto);
          
                          StringBuffer url = new StringBuffer(RequerimientoConstante.BANDEA_JSF);
                          getFlash().put(GeneralConstantes.MENSAJE_REDIRECT, mensaje);
                          ConfigurableNavigationHandler configurableNavigationHandler =
                                  (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                                          .getNavigationHandler();
          
                          configurableNavigationHandler.performNavigation(url.toString() + "?faces-redirect=true");
          
                      }
                      else {
                          muestraMensajes();
                      }
                  }
                  else if (getPanelType().toString().equalsIgnoreCase(CadenasConstants.PANEL_TIPO_RESARCIMIENTO)) {
                      if (validaDatosBienes(dto) && basicoOk) {
                          getGeneracionResolucionBussines().guardaResarcimiento(dto, getUserProfile().getRfc());
                          getGeneracionResolucionBussines().actualizaDatosBP(getDatosBandejaTareaDTO(), dto);
                          FacesContext.getCurrentInstance().addMessage(
                                  null,
                                  new FacesMessage(FacesMessage.SEVERITY_INFO, null, messages
                                          .getString("cal.resolucion.resarcimiento.mensaje")));
                          StringBuffer url = new StringBuffer(RequerimientoConstante.BANDEA_JSF);
                          getFlash().put(GeneralConstantes.MENSAJE_REDIRECT, mensaje);
                          ConfigurableNavigationHandler configurableNavigationHandler =
                                  (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                                          .getNavigationHandler();
          
                          configurableNavigationHandler.performNavigation(url.toString() + "?faces-redirect=true");
          
                      }
                      else {
                          muestraMensajes();
                      }
                  }
                  else {
                      if (basicoOk) {
                          getGeneracionResolucionBussines().guardaClasificacion(dto, getUserProfile().getRfc());
                          getGeneracionResolucionBussines().actualizaDatosBP(getDatosBandejaTareaDTO(), dto);
                          StringBuffer url = new StringBuffer(RequerimientoConstante.BANDEA_JSF);
                          getFlash().put(GeneralConstantes.MENSAJE_REDIRECT, mensaje);
                          ConfigurableNavigationHandler configurableNavigationHandler =
                                  (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                                          .getNavigationHandler();
          
                          configurableNavigationHandler.performNavigation(url.toString() + "?faces-redirect=true");
                      }
                  }
              } else {
                        FacesContext.getCurrentInstance().addMessage(
                                            null, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", messages
                                                                .getString("cal.resolucion.requerimiento.fechaVerificacion")));
              }

    }

    /**
     * M&eacute;todo para mostrar los mensajes de error
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
            mensajesValidaciones.add(errorMsg.getString("cal.resolucion.required.resarcimiento.fechaEmbargo"));
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
        if (!getGenerarResolucionValidator().validaCamposRequeridosAgregar(getNombreFirmaCbo())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, errorMsg
                            .getString("cal.resolucion.required.nombrefirma")));
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

        configurableNavigationHandler
                .performNavigation("/resources/pages/cal/atencion/atenderPromocion.jsf?faces-redirect=true");

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
        resolucionCALDTO.setRfcFirmante(getNombreFirmaCbo());
        resolucionCALDTO.setFechaResolucion(getFechaResolucion());
        resolucionCALDTO.setNumeroOficio(getNumeroOficio());
        if (getPanelType().intValue() == 1) {
            resolucionCALDTO.setFraccionArancelaria(getFraccionArancel());
            resolucionCALDTO.setNumeroMinuta(getMinuta());
            resolucionCALDTO.setFechaSesion(getFechaSesion() == null ? null : getFechaSesion());
            resolucionCALDTO.setNumeroAsunto(getDatosBandejaTareaDTO().getNumeroAsunto());
            if (getFraccion() != null) {
                resolucionCALDTO.setConfirmaFraccion(getFraccion().equalsIgnoreCase("1") ? true : false);
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
            resolucionCALDTO.setNumeroAsunto(getNumeroAsuntoResarcimiento());
            resolucionCALDTO.setCumplimentacion("1".equals(getCumplimientoSentencia()) ? true : false);
            resolucionCALDTO
                    .setMontoSentencia(getMontoSentencia() == null ? null : new BigDecimal(getMontoSentencia()));
            resolucionCALDTO.setBienesResolucion(bienResarcimientoDTOList);
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
        setEliminarVisible(false);
    }

    /**
     * 
     * @param event
     */
    public void rowUnselectCheckbox(UnselectEvent event) {
        if (getSelectedBienResarcimiento().length < 1) {
            setEliminarVisible(true);
        }
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
     * @return the rol
     */
    public String getRol() {
        return rol;
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

    public ResolucionDatosGeneradosDTO getResolucionDatosGeneradosDTO() {
        return resolucionDatosGeneradosDTO;
    }

    public void setResolucionDatosGeneradosDTO(ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO) {
        this.resolucionDatosGeneradosDTO = resolucionDatosGeneradosDTO;
    }

    private List<BienResarcimientoDTO> getBienesResarcimiento() {

        return getGeneracionResolucionBussines().obtenerBienes(getResolucionDatosGeneradosDTO());
    }

}
