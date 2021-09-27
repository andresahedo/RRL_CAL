/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
/**
 * 
 */
package mx.gob.sat.siat.juridica.base.web.controller.bean.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.extensions.component.ajaxerrorhandler.AjaxErrorHandler.PropertyKeys;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.MotivoRechazoDTO;
import mx.gob.sat.siat.juridica.base.dto.ObservacionDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.ConsultasBussines;
import mx.gob.sat.siat.juridica.base.web.util.DateComparator;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.ca.util.constants.RegistroSolicitudConstants;
import mx.gob.sat.siat.juridica.ca.util.validador.TipoRolContribuyenteIDC;
import mx.gob.sat.siat.juridica.oficialia.web.controller.bean.constantes.OficialiaConstantesController;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosRequerimientoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.ConsultasConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;
import mx.gob.sat.siat.juridica.rrl.util.exception.FechaInvalidaException;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AnexarDocumentoBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.BandejaDocumentosLazyList;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.BandejaDocumentosOfiLazyList;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DatosRequerimientoDataModel;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoDataModel;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoOficialDataModel;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.ObservacionDataModel;

/**
 * Bean que implementan la l&oacute;gica de negocio para las consultas
 * 
 * @author Softtek
 * 
 */
@ViewScoped
@ManagedBean(name = "consultasController")
public class ConsultasController extends BaseCloudController<DocumentoOficialDTO> {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Bussiness que implementa las reglas de negocio de consultas.
     */
    @ManagedProperty(value = "#{consultasBussines}")
    private ConsultasBussines consultasBussines;
    /**
     * Bussines que implementa las reglas de negocio para anexar
     * documentos.
     */
    @ManagedProperty(value = "#{anexarDocumentoBussines}")
    private AnexarDocumentoBussines anexarDocumentoBussines;

    @ManagedProperty(value = "#{datosBandejaTareaDTO}")
    private DatosBandejaTareaDTO datosBandejaTareaDTO;

    @ManagedProperty(value = "#{dateComparator}")
    private DateComparator dateComparator;
    /**
     * Lista documentos oficiales
     */
    private List<DocumentoDTO> listaDocumentos = new ArrayList<DocumentoDTO>();
    /** DTO que representa los datos a capturar de una solicitud */
    private DatosSolicitudDTO datosSolicitud = new DatosSolicitudDTO();
    /**
     * Archivo a descargar.
     */
    private DefaultStreamedContent archivoDescarga;
    /**
     * Propiedad que identifica la solicitud.
     */
    private Long idSolicitud;

    /**
     * Propiedad que identifica la solicitud.
     */
    private Long idRequerimiento;

    /**
     * Propiedad que representa el numero de folio.
     */
    private String numFolio;
    /**
     * Propiedad que representa el tipo de documento.
     */
    private String tipoDocumento;

    private Boolean esAbogado;
    /**
     * Propiedad que representa un identificador.
     */
    private String id;
    /**
     * Propiedad que identifica la clave que identifica el tipo de
     * tramite.
     */
    private String idTipoTramite;
    /**
     * Documentos seleccionado.
     */
    private DocumentoDTO documentoSeleccionado;

    /**
     * Atributo privato tramite tipo "TramiteDTO"
     */
    private TramiteDTO tramite;
    /**
     * Lista de tipo de documentos.
     */
    private List<DocumentoDTO> documentosCombo;


    /**
     * Arreglo de registros a ser agregados.
     */
    private DocumentoDTO[] registroAgregar;



    /**
     * Propiedad que represeta un objeto de tipo DocumentoDataModel.
     */
    private DocumentoDataModel documentoDataModel;

    /**
     * Propiedad que represeta un objeto de tipo DocumentoDataModel.
     */
    private DocumentoOficialDataModel documentoOficialDataModel;

    /** Lista de documentos */
    private List<DocumentoOficialDTO> listaDocumentosOficiales = new ArrayList<DocumentoOficialDTO>();

    /** Lista de documentos */
    private List<DocumentoOficialDTO> listaDocumentosOficialesPromovente = new ArrayList<DocumentoOficialDTO>();

    /** Lista de unidades emisoras */
    private List<CatalogoDTO> listaUnidadesEmisoras = new ArrayList<CatalogoDTO>();
    /**
     * Lista de requerimientos.
     */
    private List<DatosRequerimientoDTO> listaRequerimientos = new ArrayList<DatosRequerimientoDTO>();
    /**
     * DTO DatosRequerimentoDTO.
     */
    private DatosRequerimientoDTO datosRequerimientoDTO = new DatosRequerimientoDTO();
    /**
     * Lista documentos de requerimiento.
     */
    private RequerimientoDTO requerimiento;

    private List<DocumentoOficialDTO> listaDocumentosRequerimiento = new ArrayList<DocumentoOficialDTO>();

    /**
     * Data model que manipula los datos del requerimiento.
     */
    private DatosRequerimientoDataModel datosRequerimientoDataModel;

    private DatosRequerimientoDTO selectedReq = new DatosRequerimientoDTO();
    /**
     * Variable utilizada para representar los mensajes.
     */
    private String msg;
    /**
     * Variable para manejar los mensajes de error.
     */
    private String errorMessage;
    /**
     * Variable para establecer el mensaje de la fecha final.
     */
    private String mensageFechaFin;
    /**
     * Variable para establecer el rango de fechas.
     */
    private String mensageFechas;

    /**
     * Propiedad para filas de correo
     */
    private String rowsCorreo;

    /**
     * Propiedad que representa un mensaje.
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    /**
     * Propiedad que representa un mensaje de error.
     */
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    /**
     * Método constructor vacio.
     */

    private boolean banderaRazonSocial;

    private boolean reqVisible;

    // propiedades documento Cumplimentacion

    private String nombreDocumento;

    /**
     * Atributo que activa el dialogo de rechazo de documento tipo
     * boolean
     */
    private boolean activaDialogo;

    /**
     * Lista de motivos de rechazo
     */
    private List<CatalogoDTO> listaMotivoCombo = new ArrayList<CatalogoDTO>();

    /**
     * Motivo de rechazo seleccionado
     */
    private MotivoRechazoDTO motivoSelected = new MotivoRechazoDTO();

    /**
     * Id del morivo de rechazo
     */
    private String idMotivo;

    /** Arreglo de documentos seleccionados a eliminar */
    private DocumentoOficialDTO[] registrosOfEliminar;

    private boolean eliminarVisible;

    private ObservacionDataModel observacionesDataModel;

    private List<ObservacionDTO> listaObservaciones;

    private boolean blnPanelMotivo;

    private ObservacionDTO observacionSelected;

    private boolean esDesestimiento;

    private boolean manifiesto;

    private boolean manifiestoRequerido;

    private boolean disabledBoton;

    private Date fechaMaxima;
    
    private String tipoContribuyente;
    
    @ManagedProperty(value = "#{tipoRolContribuyenteIDC}")
    private transient TipoRolContribuyenteIDC tipoRolContribuyente;
    private StreamedContent archivoPdf;
    
    private StreamedContent archivoZip;
    private StreamedContent archivoOficialZip;
    
    private StreamedContent archivoOficialPdf;
    
    private BandejaDocumentosLazyList lazyModel ;
    private Map<String, String> dataDto = new LinkedHashMap<String, String>();  
    private transient DataTable dataTableFil = new DataTable();
    
    private BandejaDocumentosOfiLazyList lazyModelOficial;
    private Map<String, String> dataOficial = new LinkedHashMap<String, String>();  
    private transient DataTable dataTableFilOficial = new DataTable();
    
    private static final String PDF=".pdf";
    
    public void rowSelectCheckbox(SelectEvent event) {

        setEliminarVisible(true);

    }

    public void rowUnselectCheckbox(UnselectEvent event) {
        if (getRegistrosOfEliminar().length < 1) {
            setEliminarVisible(false);
        }
    }

    /**
     * Metodo que selecciona una observacion
     * 
     * @param event
     */
    public void onRowSelectObservacion(SelectEvent event) {
        observacionSelected = (ObservacionDTO) event.getObject();
        blnPanelMotivo = true;
    }

    /**
     * M&eacute;todo para eliminar documentos seleccionados.
     */
    public void eliminarDocumentos(ActionEvent event) {
        for (DocumentoOficialDTO documento : getRegistrosOfEliminar()) {
            getListaDocumentosOficiales().remove(documento);
            if (documento.getIdDocumentoOficial() != 0) {
                getConsultasBussines().eliminaDocumento(documento.getIdDocumentoOficial());
            }
        }
        setEliminarVisible(false);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El (Los) documento(s) ha(n) sido eliminado(s)"));
    }

    /**
     * M&eacute;todo para adjuntar un documento
     */

    
    public void anexarDocumentoNube() {
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        DocumentoOficialDTO documento = super.obtenerDocumentoOficialDTO(TipoDocumentoOficial.OFICIO_ATENCION_RETRO);
        if(validaDocumentoAzure(documento,getListaDocumentosOficiales())) {
            verificaDocumentoAzure(documento);
        }else {
            getLogger().error("Archivo invalido");
            getLogger().error("Error: "+ documento.getNombreDocumento());
            String error =documento.getNombreDocumento();
            if(error.substring(error.length() -4, error.length() ) .equalsIgnoreCase(PDF)) {
                error = "S\u00F3lo es posible adjuntar un archivo a la vez";
            }
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:",error);
            FacesContext.getCurrentInstance().addMessage(null, msg);
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
        documento.setIdRequerimiento(getDatosBandejaTareaDTO().getIdRequerimiento());
        getListaDocumentosOficiales().add(documento);
        setDocumentoOficialDataModel(new DocumentoOficialDataModel(getListaDocumentosOficiales()));
        FacesMessage mnsg = new FacesMessage("", "Documento adjuntado correctamente.");
        FacesContext.getCurrentInstance().addMessage(null, mnsg);
    }

    /**
     * M&eacute;todo para almacenar los documentos a la solicitud
     */
    public void guardarDocumentos() {
        if (getListaDocumentosOficiales() != null && getListaDocumentosOficiales().size() > 0) {
            getConsultasBussines().guardarDocumentos(getListaDocumentosOficiales(),
                    getDatosBandejaTareaDTO().getNumeroAsunto(), getDatosBandejaTareaDTO().getIdRequerimiento());
            ConfigurableNavigationHandler configurableNavigationHandler =
                    (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                    .getNavigationHandler();
            getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
            configurableNavigationHandler.performNavigation(LoginConstante.BANDEA_JSF + "?faces-redirect=true");
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe ingresar los documentos requeridos"));
        }
    }

    @PostConstruct
    public void getIniciar() {
        setFechaMaxima(new Date());
        setEsDesestimiento(false);
        setManifiestoRequerido(false);
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));
        setIdSolicitud(getDatosBandejaTareaDTO().getIdSolicitud());
        setIdTipoTramite(getDatosBandejaTareaDTO().getTipoTramite());
        setNumFolio(getDatosBandejaTareaDTO().getNumeroAsunto());
        setEsAbogado(getDatosBandejaTareaDTO().isEsAbogado());
        getSelectedReq().setDeshabilitarFechas(false);
        
        dataDto.put("IdSolicitud",getDatosBandejaTareaDTO().getIdSolicitud().toString());
        dataOficial.put("numFolio", getDatosBandejaTareaDTO().getNumeroAsunto());
        getDataTableFil().setFilters(dataDto);
        getDataTableFilOficial().setFilters(dataOficial);
        
        setLazyModel(getDocumentoConsultaLazy()); 
        setLazyModelOficial(getDocumentoConsultaLazyOficial());
        
        if ((DocumentoDTO) getFlash().get(VistaConstantes.DOCUMENTO_RECHAZO) != null) {
            getMotivoSelected().setDoc((DocumentoDTO) getFlash().get(VistaConstantes.DOCUMENTO_RECHAZO));
        }
        setTramite(getConsultasBussines().obtenerTramitePorId(getNumFolio()));
        setListaMotivoCombo(getConsultasBussines().obtenerMotivosRechazo());

        if (getIdSolicitud() != null) {
            setDatosSolicitud(getConsultasBussines().buscar(getIdSolicitud()));
        }
        if (this.getNumFolio() != null && !this.getNumFolio().equals("")) {
            setDocumentoDataModel(getDocumentosConsulta());
            obtenerRequerimientos();
        }
        if (getDatosSolicitud().getCorreoElectronico() != null) {
            String[] correos = getDatosSolicitud().getCorreoElectronico().split("\n");
            setRowsCorreo(String.valueOf(correos.length));
            if (getRowsCorreo().equals("1") || getRowsCorreo().equals("2")) {
                setRowsCorreo("3");
            }
        }
        if (getDatosSolicitud().getRazonSocial() != null) {
            if (getDatosSolicitud().getRazonSocial().equalsIgnoreCase("")) {
                setBanderaRazonSocial(false);
            }
            else {
                setBanderaRazonSocial(true);
            }

        }
        else {
            setBanderaRazonSocial(false);
        }

        if (getDatosBandejaTareaDTO().getNumeroAsunto() != null
                && !getDatosBandejaTareaDTO().getNumeroAsunto().isEmpty()) {
            obtenerObservaciones();
        }
        //obtener el tipo de contribuyente
        try {
            this.setTipoContribuyente(tipoRolContribuyente.consultaHidrocarburos(getDatosSolicitud().getRfcContribuyente()));

        } catch (IOException e) {
            getLogger().error("Error al consultar el tipo de contribuyente en IDC"+e.getMessage());
        }
    }

    public ConsultasController() {}

    /**
     * Metodo que devuelve los datos del solicitante.
     * 
     * @return consultaDatosSolicitante
     */
    public String getTabSolicitante() {

        return ConsultasConstantes.CONSULTA_DATOS_SOLICITANTE;
    }

    /**
     * Metodo que obtiene los requerimientos de una solicitud.
     */
    public void obtenerRequerimientos() {
        setListaRequerimientos(getConsultasBussines().obtenerRequerimientos(numFolio));
        datosRequerimientoDataModel = new DatosRequerimientoDataModel(listaRequerimientos);
    }

    /**
     * Metodo que obtiene los metodos del requerimiento.
     */
    public void obtenerDocumentosRequerimiento() {
        setListaDocumentosRequerimiento(getConsultasBussines().obtenerDocumentos(numFolio));
    }

    /**
     * Metodo que actualiza la informacion del requerimiento.
     */
    public void actualizarRequerimiento() {
        Date fechaNotificacion = getSelectedReq().getFechaNotificacion();
        Date fechaAtencion = getSelectedReq().getFechaAtencion();

        try {
            if(fechaAtencion!=null){
                if (dateComparator.compare(fechaAtencion, fechaNotificacion) < 1) {
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, null, errorMsg
                                    .getString("validation.fechas.requerimiento")));
                }
                else {
                    getConsultasBussines().actualizarRequerimiento(getSelectedReq());
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "", messages
                                    .getString("rrl.requerimiento.consulta.guadar")));
                }
            }else{
                getConsultasBussines().actualizarRequerimiento(getSelectedReq());
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "", messages
                                .getString("rrl.requerimiento.consulta.guadar")));
            }
        }
        catch (FechaInvalidaException e) {
            FacesContext
            .getCurrentInstance()
            .addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "La fecha de notificaci\u00F3n es menor a la fecha de autorizaci\u00F3n del requerimiento o la resoluci\u00F3n."));

        }
    }

    /**
     * Metodo para limpiar el Dialog.
     */
    public void limpiarDialog() {

        this.msg = "";
        this.selectedReq = new DatosRequerimientoDTO();
    }

    /**
     * Metodo para obtener el requerimiento seleccionado.
     * 
     * @param event
     */
    public void onRowSelect(SelectEvent event) {
        setReqVisible(true);
        this.selectedReq = (DatosRequerimientoDTO) event.getObject();
    }

    /**
     * 
     * @return consltaDatosTramite
     */
    public String getTabTramite() {
        return ConsultasConstantes.CONSULTA_DATOS_TRAMITE;
    }

    /**
     * 
     * @return consultaDatosDocumentos
     */
    public String getTabDocumentos() {
        setDocumentoOficialDataModel(obtenerDocumentosOficiales());
        return ConsultasConstantes.CONSULTA_DATOS_DOCUMENTOS;
    }

    /**
     * 
     * @return consultaDatosDocumentos
     */
    public String getTabDocumentosConcluidos() {
        setDocumentoOficialDataModel(obtenerDocumentosOficiales());
        return ConsultasConstantes.CONSULTA_DATOS_DOCUMENTOS_CONCLUIDOS;
    }

    /**
     * 
     * @return consultaDescargaDocumentosPromovente
     */
    public String getTabConsultarDocumentosPromovente() {
        setIdRequerimiento((Long) getFlash().get("idRequerimiento"));
        setDocumentoOficialDataModel(getDocumentosOficialesConsulta());
        return ConsultasConstantes.CONSULTA_DOCUMENTOS;
    }

    /**
     * 
     */
    public String getTabRequerimientos() {
        return ConsultasConstantes.CONSULTA_DATOS_REQUERIMIENTOS;
    }
    
    public String getTabRequerimientosConcluidos() {
        return ConsultasConstantes.CONSULTA_DATOS_REQUERIMIENTOS_CONCLUIDOS;
    }

    
    /**
     * 
     * @return consultaDescargaDocumentos
     */
    public String getTabAnexarDocumentos() {
        setDocumentoDataModel(getDocumentosConsulta());
        setDocumentoOficialDataModel(getDocumentosOficialesConsulta());
        prepararDocumentosCombo();
        if (getDocumentosCombo() != null) {
            if (getDocumentosCombo().isEmpty()) {
                String[] param = new String[1];
                param[0] = getDatosBandejaTareaDTO().getEstadoProcesal();

                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", MessageFormat.format(
                                messages.getString("bandeja.consulta.mensaje.adjuntaDoctos"), (Object[]) param)));
            }
        }

        return ConsultasConstantes.CONSULTA_DESCARGA_DOCUMENTOS;
    }

    /**
     * @return consultaDatosObservaciones
     */
    public String getTabObservaciones() {
        return ConsultasConstantes.CONSULTA_DATOS_OBSERVACIONES;
    }

    /**
     * Metodo que obtiene las observaciones del asunto
     */
    public void obtenerObservaciones() {
        setListaObservaciones(getConsultasBussines().obtenerObservacionesPorTramite(
                getDatosBandejaTareaDTO().getNumeroAsunto()));
        observacionesDataModel = new ObservacionDataModel(listaObservaciones);
    }

    /**
     * 
     * @return adjuntarCumplimentacion
     */
    public String adjuntarCumplimentacion() {
        getDatosBandejaTareaDTO().setIdRequerimiento(getSelectedReq().getIdRequerimiento());
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        return ConsultasConstantes.ADJUNTAR_DOC_CUMPLIMENTACION;

    }
    
    /*
     * Consulta via Lazy
     */
    
    public BandejaDocumentosLazyList getDocumentoConsultaLazy() {
        return new BandejaDocumentosLazyList(consultasBussines);        
    }
    
    public BandejaDocumentosOfiLazyList getDocumentoConsultaLazyOficial() {
        return new BandejaDocumentosOfiLazyList(consultasBussines);
    }
    
    
    /**
     * Metodo para activar cuadro de dialogo para el rechazo de asunto
     */
    public void activaDialogoAsunto() {
        activaDialogo = true;
        String[] param = new String[2];
        param[0] = motivoSelected.getDoc().getNombre();
        param[1] = motivoSelected.getDoc().getTipoDocumento();
        msg =
                MessageFormat.format(messages.getString("oficialia.rechazo.asunto.confirmacionrechazar"),
                        (Object[]) param);
    }

    /**
     * Metodo para activar cuadro de dialogo para el rechazo de
     * documento
     */
    public void activaDialogo() {

        if (idMotivo == null) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, errorMsg
                            .getString("op.validaciones.motivoRechazoDocto")));
        }
        else {
            activaDialogo = true;
            String[] param = new String[2];
            param[0] = motivoSelected.getDoc().getNombre();
            param[1] = motivoSelected.getDoc().getTipoDocumento();

            msg =
                    MessageFormat.format(messages.getString("oficialia.rechazo.asunto.confirmacionrechazar"),
                            (Object[]) param);
        }
    }

    /**
     * Metodo que se ejecuta al cancelar el rechazo de documento
     */
    public void noConfirma() {
        getMotivoSelected().setIdTramite(getDatosBandejaTareaDTO().getNumeroAsunto());
        getMotivoSelected().setRfc(getUserProfile().getRfc());
        getMotivoSelected().setIdSolDocto(motivoSelected.getDoc().getIdDocumentoSolicitud());
        getMotivoSelected().setNombreDocumento(motivoSelected.getDoc().getNombre());
        getMotivoSelected().setTipoDocumento(motivoSelected.getDoc().getTipoDocumento());

        getFlash().put(VistaConstantes.MOTIVO_RECHAZO, getMotivoSelected());
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        activaDialogo = false;

    }

    /**
     * Metodo para la firma del rechazo del documento
     */
    public void firmaRechazo() {
        getMotivoSelected().setIdTramite(getDatosBandejaTareaDTO().getNumeroAsunto());
        getMotivoSelected().setRfc(getUserProfile().getRfc());
        getMotivoSelected().setIdSolDocto(motivoSelected.getDoc().getIdDocumentoSolicitud());
        getMotivoSelected().setNombreDocumento(motivoSelected.getDoc().getNombre());
        getMotivoSelected().setTipoDocumento(motivoSelected.getDoc().getTipoDocumento());
        getMotivoSelected().setCveRechazo(idMotivo);

        getFlash().put(VistaConstantes.MOTIVO_RECHAZO, getMotivoSelected());
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());

        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().setResponseCharacterEncoding("UTF-8");
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) facesContext.getApplication().getNavigationHandler();
        configurableNavigationHandler.performNavigation(UrlFirma.PAGINA_FIRMA_RECHAZO_DOCUMENTO_OFICIALIA.toString());
    }

    /**
     * Boton de rechazo de documento
     * 
     * @param event
     */
    public void rechazarButton(ActionEvent event) {
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        getFlash().put(VistaConstantes.DOCUMENTO_RECHAZO, (DocumentoDTO) event.getComponent().getAttributes().get("doc"));

        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().setResponseCharacterEncoding("UTF-8");
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) facesContext.getApplication().getNavigationHandler();
        configurableNavigationHandler.performNavigation(OficialiaConstantesController.RECHAZAR_DOCUMENTO_OFICIALIA);
    }

    public void validarDesistimiento() {
        if (id == null || id.equals("")) {
            disabledBoton = true;
            esDesestimiento = false;
        }
        else {
            disabledBoton = false;
            String desisitimiento = "";
            if (id.length() > NumerosConstantes.DOCE) {
                desisitimiento = id.substring((id.length() - NumerosConstantes.TRECE), id.length());
            }
            if (getIdTipoTramite().equals("10101")) {
                if (desisitimiento.equals("Desistimiento")) {
                    esDesestimiento = true;
                    manifiestoRequerido = true;
                }
                else {
                    esDesestimiento = false;
                    manifiestoRequerido = false;
                }
            }
        }
    }

    /**
     * 
     * @return consultasBussines
     */
    public ConsultasBussines getConsultasBussines() {
        return consultasBussines;
    }

    /**
     * 
     * @param consultasBussines
     *            el consultarBussines a fijar.
     */
    public void setConsultasBussines(ConsultasBussines consultasBussines) {
        this.consultasBussines = consultasBussines;
    }

    /**
     * 
     * @return datosSolicitud
     */
    public DatosSolicitudDTO getDatosSolicitud() {
        return datosSolicitud;
    }

    /**
     * 
     * @param datosSolicitud
     *            el datosSolicitud a ser fijado.
     */
    public void setDatosSolicitud(DatosSolicitudDTO datosSolicitud) {
        this.datosSolicitud = datosSolicitud;
    }

    /**
     * 
     * @return listDocumentos
     */
    public List<DocumentoDTO> getListaDocumentos() {
        return listaDocumentos;
    }

    /**
     * 
     * @param listaDocumentos
     *            la listaDocumentos a fijar
     */
    public void setListaDocumentos(List<DocumentoDTO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    /**
     * 
     * @return listaUnidadesEmisoras
     */
    public List<CatalogoDTO> getListaUnidadesEmisoras() {
        return listaUnidadesEmisoras;
    }

    /**
     * 
     * @param listaUnidadesEmisoras
     *            la listaUnidadesEmisoras a fijar
     */
    public void setListaUnidadesEmisoras(List<CatalogoDTO> listaUnidadesEmisoras) {
        this.listaUnidadesEmisoras = listaUnidadesEmisoras;
    }

    /**
     * 
     * @return idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * 
     * @param idSolicitud
     *            la idSolicitud a fijar.
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * 
     * @return numFolio
     */
    public String getNumFolio() {
        return numFolio;
    }

    /**
     * 
     * @param numFolio
     *            el numFolio a fijar.
     */
    public void setNumFolio(String numFolio) {
        this.numFolio = numFolio;
    }

    /**
     * 
     * @return tipoDocumento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * 
     * @param tipoDocumento
     *            el tipoDocumento a fijar.
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * 
     * @return getListaDocumentosOficiales()
     */
    public DocumentoDataModel getDocumentosConsulta() {
        setListaDocumentos(getConsultasBussines().obtenerDocumentosRegistro(idSolicitud.toString()));
        return new DocumentoDataModel(getListaDocumentos());
    }

    /**
     * 
     * @return getListaDocumentosOficiales()
     */
    public DocumentoOficialDataModel getDocumentosOficialesConsulta() {
        setListaDocumentosOficiales(getConsultasBussines().obtenerDocumentosOficialesPromovente(numFolio,
                idRequerimiento));
        List<DocumentoOficialDTO> lstDocOficial=getAnexarDocumentoBussines().obtenerDocumentosOficialesAnexadosExclusivoFondo(numFolio);
        if(lstDocOficial!=null && !lstDocOficial.isEmpty()){
            List<DocumentoOficialDTO> lstDocOficialTemp=new ArrayList<DocumentoOficialDTO>(lstDocOficial);
            for(DocumentoOficialDTO objDocOf:lstDocOficial){
                if(objDocOf.getCveTipoDocumento().equals(TipoDocumentoOficial.DICTAMEN_INICIAL_RRL.getClave())){
                    lstDocOficialTemp.remove(objDocOf);
                }else if(objDocOf.getCveTipoDocumento().equals(TipoDocumentoOficial.DICTAMEN_FINAL_RRL.getClave())){
                    lstDocOficialTemp.remove(objDocOf);
                }else if(objDocOf.getCveTipoDocumento().equals(TipoDocumentoOficial.INSTRUCTIVO_RRL.getClave())){
                    lstDocOficialTemp.remove(objDocOf);
                }else if(objDocOf.getCveTipoDocumento().equals(TipoDocumentoOficial.PROTOCOLO_AUDIENCIA_CONRIBUYENTE.getClave())){
                    lstDocOficialTemp.remove(objDocOf);
                }else if(objDocOf.getCveTipoDocumento().equals(TipoDocumentoOficial.PROTOCOLO_AUDIENCIA_PERITO.getClave())){
                    lstDocOficialTemp.remove(objDocOf);
                }else if(objDocOf.getCveTipoDocumento().equals(TipoDocumentoOficial.OTROS.getClave())){
                    lstDocOficialTemp.remove(objDocOf);
                }
            }
            getListaDocumentosOficiales().addAll(lstDocOficialTemp);
        }
        return new DocumentoOficialDataModel(getListaDocumentosOficiales());
    }

    /**
     * Metodo que obtiene los documentos de una solicitud mediante un
     * numero de folio.
     */
    public DocumentoOficialDataModel obtenerDocumentosOficiales() {
        setListaDocumentosOficiales(getConsultasBussines().obtenerDocumentos(numFolio));
        List<DocumentoOficialDTO> lstDocOficial=getAnexarDocumentoBussines().obtenerDocumentosOficialesAnexadosExclusivoFondo(numFolio);
        if(lstDocOficial!=null && !lstDocOficial.isEmpty()){
            getListaDocumentosOficiales().addAll(lstDocOficial);
        }
        return new DocumentoOficialDataModel(getListaDocumentosOficiales());
    }

    public BandejaDocumentosOfiLazyList getDocumentoConsultaLazyOficialCal() {
        return new BandejaDocumentosOfiLazyList(consultasBussines);
    }

    /**
     * Metodo que descarga el documento seleccionado.
     * 
     * @param event
     */
    public void descargarDocumento(ActionEvent event) throws IOException {
        DocumentoDTO documento = (DocumentoDTO) event.getComponent().getAttributes().get("documentoParametro");

        InputStream input = getConsultasBussines().descargarDocumento(documento);

        HttpServletResponse response =
                (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        response.setContentType(ConsultasConstantes.APPLICATION_PDF);
        response.setHeader("Content-Disposition", "attachment;filename=" + documento.getNombre());
        IOUtils.copy(input, response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * 
     * @return archivoDescarga
     */
    public DefaultStreamedContent getArchivoDescarga() {
        return archivoDescarga;
    }

    /**
     * 
     * @param archivoDescarga
     *            el archivoDescarga a fijar.
     */
    public void setArchivoDescarga(DefaultStreamedContent archivoDescarga) {
        this.archivoDescarga = archivoDescarga;
    }

    /**
     * 
     * @return anexarDocumentoBussines
     */
    public AnexarDocumentoBussines getAnexarDocumentoBussines() {
        return anexarDocumentoBussines;
    }

    /**
     * 
     * @param anexarDocumentoBussines
     *            el / la anexarDocumentoBussines a fijar.
     */
    public void setAnexarDocumentoBussines(AnexarDocumentoBussines anexarDocumentoBussines) {
        this.anexarDocumentoBussines = anexarDocumentoBussines;
    }

    /**
     * 
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *            el/la id a fijar.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return documentoSeleccionado
     */
    public DocumentoDTO getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    /**
     * 
     * @param documentoSeleccionado
     *            el documentoSeleccionado a fijar.
     */
    public void setDocumentoSeleccionado(DocumentoDTO documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    /**
     * 
     * @return documentosCombo
     */
    public List<DocumentoDTO> getDocumentosCombo() {
        return documentosCombo;
    }

    /**
     * 
     * @param documentosCombo
     *            el documentosCombo a fijar.
     */
    public void setDocumentosCombo(List<DocumentoDTO> documentosCombo) {
        this.documentosCombo = documentosCombo;
    }

    /**
     * 
     * @return documentoDataModel
     */
    public DocumentoDataModel getDocumentoDataModel() {
        return documentoDataModel;
    }

    /**
     * 
     * @param documentoDataModel
     */
    public void setDocumentoDataModel(DocumentoDataModel documentoDataModel) {
        this.documentoDataModel = documentoDataModel;
    }

    /**
     * Metodo que carga datos al combo de documentos.
     */
    public void prepararDocumentosCombo() {
        setDocumentosCombo(consultasBussines.obtenerDocumentosTramite(idTipoTramite, numFolio));
    }

    /**
     * 
     * @return idTipoTramite
     */
    public String getIdTipoTramite() {
        return idTipoTramite;
    }

    /**
     * 
     * @param idTipoTramite
     *            el idTipoTramite a fijar.
     */
    public void setIdTipoTramite(String idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    /**
     * 
     * @return ConsultasConstantes.consultaAnexarDocumentos
     */
    public String documentosAnexar() {
        if (isEsDesestimiento()) {
            if (manifiesto) {
                getFlash().put("idTipoDocumento", getId());
                getFlash().put("datosSolicitud", getDatosSolicitud());
                getFlash().put("idSolicitud", getIdSolicitud());
                getFlash().put("numAsunto", numFolio);
                getConsultasBussines().eliminaDocumentosNoFirmados(getIdSolicitud());
                return ConsultasConstantes.CONSULTA_ANEXAR_DOCUMENTOS;
            }
            else {
                FacesContext
                .getCurrentInstance()
                .addMessage(
                        null,
                        new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                "",
                                "Para adjuntar el documento de Desistimiento es necesario que marque la opci\u00F3n de Manifestaci\u00F3n bajo protesta de decir verdad."));
                disabledBoton = false;
                return null;
            }
        }
        else {
            getFlash().put("idTipoDocumento", getId());
            getFlash().put("datosSolicitud", getDatosSolicitud());
            getFlash().put("idSolicitud", getIdSolicitud());
            getFlash().put("numAsunto", numFolio);
            getConsultasBussines().eliminaDocumentosNoFirmados(getIdSolicitud());
            return ConsultasConstantes.CONSULTA_ANEXAR_DOCUMENTOS;

        }

    }

    public void activarAdjuntar(AjaxBehaviorEvent e) {
        disabledBoton = false;
    }

    /**
     * 
     * @return listaRequerimientos
     */
    public List<DatosRequerimientoDTO> getListaRequerimientos() {
        return listaRequerimientos;
    }

    /**
     * 
     * @param listaRequerimientos
     *            la listRequerimientos a fijar.
     */
    public void setListaRequerimientos(List<DatosRequerimientoDTO> listaRequerimientos) {
        this.listaRequerimientos = listaRequerimientos;
    }

    /**
     * 
     * @return datosRequerimientoDTO
     */
    public DatosRequerimientoDTO getDatosRequerimientoDTO() {
        return datosRequerimientoDTO;
    }

    /**
     * 
     * @param datosRequerimientoDTO
     *            los datosRequerimientoDTO a fijar.
     */
    public void setDatosRequerimientoDTO(DatosRequerimientoDTO datosRequerimientoDTO) {
        this.datosRequerimientoDTO = datosRequerimientoDTO;
    }

    /**
     * 
     * @return datosRequerimientoDataModel
     */
    public DatosRequerimientoDataModel getDatosRequerimientoDataModel() {
        return datosRequerimientoDataModel;
    }

    /**
     * 
     * @param datosRequerimientoDataModel
     *            los datosRequerimientoDataModel a fijar.
     */
    public void setDatosRequerimientoDataModel(DatosRequerimientoDataModel datosRequerimientoDataModel) {
        this.datosRequerimientoDataModel = datosRequerimientoDataModel;
    }

    /**
     * 
     * @return selectedReq
     */
    public DatosRequerimientoDTO getSelectedReq() {
        return selectedReq;
    }

    /**
     * 
     * @param selectedReq
     */
    public void setSelectedReq(DatosRequerimientoDTO selectedReq) {
        // no quitar esta validacion ya que el front manda a setear el
        // requerimiento nulo varias veces.
        if (selectedReq != null) {
            this.selectedReq = selectedReq;
        }
    }

    /**
     * 
     * @return listaDocumentosRequerimiento
     */
    public List<DocumentoOficialDTO> getListaDocumentosRequerimiento() {
        return listaDocumentosRequerimiento;
    }

    /**
     * 
     * @param listaDocumentosRequerimiento
     *            la listaDocumentos a fijar.
     */
    public void setListaDocumentosRequerimiento(List<DocumentoOficialDTO> listaDocumentosRequerimiento) {
        this.listaDocumentosRequerimiento = listaDocumentosRequerimiento;
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
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 
     * @return mensageFechaFin
     */
    public String getMensageFechaFin() {
        mensageFechaFin = errorMsg.getString("validation.fechaFin");
        return mensageFechaFin;
    }

    /**
     * 
     * @param mensageFechaFin
     *            el mensajeFechaFin a fijar.
     */
    public void setMensageFechaFin(String mensageFechaFin) {
        this.mensageFechaFin = mensageFechaFin;
    }

    /**
     * 
     * @return mensageFechas
     */
    public String getMensageFechas() {
        mensageFechas = errorMsg.getString("validation.fechas.notifiReq");
        return mensageFechas;
    }

    /**
     * 
     * @param mensageFechas
     *            el mensageFechas a fijar.
     */
    public void setMensageFechas(String mensageFechas) {
        this.mensageFechas = mensageFechas;
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
     *            el/los messages a fijar.
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
     */
    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isBanderaRazonSocial() {
        return banderaRazonSocial;
    }

    public void setBanderaRazonSocial(boolean banderaRazonSocial) {
        this.banderaRazonSocial = banderaRazonSocial;
    }

    /**
     * @return the listaDocumentosOficiales
     */
    public List<DocumentoOficialDTO> getListaDocumentosOficiales() {
        return listaDocumentosOficiales;
    }

    /**
     * @param listaDocumentosOficiales
     *            the listaDocumentosOficiales to set
     */
    public void setListaDocumentosOficiales(List<DocumentoOficialDTO> listaDocumentosOficiales) {
        this.listaDocumentosOficiales = listaDocumentosOficiales;
    }

    /**
     * @return the listaDocumentosOficialesPromovente
     */
    public List<DocumentoOficialDTO> getListaDocumentosOficialesPromovente() {
        return listaDocumentosOficialesPromovente;
    }

    /**
     * @param listaDocumentosOficialesPromovente
     *            the listaDocumentosOficialesPromovente to set
     */
    public void setListaDocumentosOficialesPromovente(List<DocumentoOficialDTO> listaDocumentosOficialesPromovente) {
        this.listaDocumentosOficialesPromovente = listaDocumentosOficialesPromovente;
    }

    /**
     * @return the documentoOficialDataModel
     */
    public DocumentoOficialDataModel getDocumentoOficialDataModel() {
        return documentoOficialDataModel;
    }

    
  
    /**
     * @param documentoOficialDataModel
     *            the documentoOficialDataModel to set
     */
    public void setDocumentoOficialDataModel(DocumentoOficialDataModel documentoOficialDataModel) {
        this.documentoOficialDataModel = documentoOficialDataModel;
    }

    /**
     * @return the idRequerimiento
     */
    public Long getIdRequerimiento() {
        return idRequerimiento;
    }

    /**
     * @param idRequerimiento
     *            the idRequerimiento to set
     */
    public void setIdRequerimiento(Long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public RequerimientoDTO getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(RequerimientoDTO requerimiento) {
        this.requerimiento = requerimiento;
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
     * @return the nombreDocumento
     */
    public String getNombreDocumento() {
        return nombreDocumento;
    }

    /**
     * @param nombreDocumento
     *            the nombreDocumento to set
     */
    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    /**
     * /**
     * 
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
     * @return the esAbogado
     */
    public boolean isEsAbogado() {
        return esAbogado;
    }

    /**
     * @param esAbogado
     *            the esAbogado to set
     */
    public void setEsAbogado(boolean esAbogado) {
        this.esAbogado = esAbogado;
    }

    /**
     * @return the reqVisible
     */
    public boolean isReqVisible() {
        return reqVisible;
    }

    /**
     * @param reqVisible
     *            the reqVisible to set
     */
    public void setReqVisible(boolean reqVisible) {
        this.reqVisible = reqVisible;
    }

    @Override
    public BaseCloudBusiness getBusinessBean() {
        return consultasBussines;
    }

    /**
     * @return the registrosOfEliminar
     */
    public DocumentoOficialDTO[] getRegistrosOfEliminar() {
        return registrosOfEliminar != null ? (DocumentoOficialDTO[]) registrosOfEliminar.clone() : null;
    }

    /**
     * @param registrosOfEliminar
     *            the registrosOfEliminar to set
     */
    public void setRegistrosOfEliminar(DocumentoOficialDTO[] registrosOfEliminarArray) {
        if (registrosOfEliminarArray != null) {
            this.registrosOfEliminar = registrosOfEliminarArray.clone();
        }
        else {
            this.registrosOfEliminar = null;
        }
    }

    public DateComparator getDateComparator() {
        return dateComparator;
    }

    public void setDateComparator(DateComparator dateComparator) {
        this.dateComparator = dateComparator;
    }

    @Override
    public List<DocumentoOficialDTO> getListaDocumentosAdjuntados() {
        return getListaDocumentosOficiales();
    }

    public boolean isActivaDialogo() {
        return activaDialogo;
    }

    public void setActivaDialogo(boolean activaDialogo) {
        this.activaDialogo = activaDialogo;
    }

    public List<CatalogoDTO> getListaMotivoCombo() {
        return listaMotivoCombo;
    }

    public void setListaMotivoCombo(List<CatalogoDTO> listaMotivoCombo) {
        this.listaMotivoCombo = listaMotivoCombo;
    }

    public MotivoRechazoDTO getMotivoSelected() {
        return motivoSelected;
    }

    public void setMotivoSelected(MotivoRechazoDTO motivoSelected) {
        this.motivoSelected = motivoSelected;
    }

    public String getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(String idMotivo) {
        this.idMotivo = idMotivo;
    }

    public TramiteDTO getTramite() {
        return tramite;
    }

    public void setTramite(TramiteDTO tramite) {
        this.tramite = tramite;
    }

    public ObservacionDataModel getObservacionesDataModel() {
        return observacionesDataModel;
    }

    public void setObservacionesDataModel(ObservacionDataModel observacionesDataModel) {
        this.observacionesDataModel = observacionesDataModel;
    }

    public List<ObservacionDTO> getListaObservaciones() {
        return listaObservaciones;
    }

    public void setListaObservaciones(List<ObservacionDTO> listaObservaciones) {
        this.listaObservaciones = listaObservaciones;
    }

    public boolean isBlnPanelMotivo() {
        return blnPanelMotivo;
    }

    public void setBlnPanelMotivo(boolean blnPanelMotivo) {
        this.blnPanelMotivo = blnPanelMotivo;
    }

    public ObservacionDTO getObservacionSelected() {
        return observacionSelected;
    }

    public void setObservacionSelected(ObservacionDTO observacionSelected) {
        this.observacionSelected = observacionSelected;
    }

    public String getRowsCorreo() {
        return rowsCorreo;
    }

    public void setRowsCorreo(String rowsCorreo) {
        this.rowsCorreo = rowsCorreo;
    }

    public boolean isEsDesestimiento() {
        return esDesestimiento;
    }

    public void setEsDesestimiento(boolean esDesestimiento) {
        this.esDesestimiento = esDesestimiento;
    }

    public boolean isManifiesto() {
        return manifiesto;
    }

    public void setManifiesto(boolean manifiesto) {
        this.manifiesto = manifiesto;
    }

    public boolean isManifiestoRequerido() {
        return manifiestoRequerido;
    }

    public void setManifiestoRequerido(boolean manifiestoRequerido) {
        this.manifiestoRequerido = manifiestoRequerido;
    }

    public boolean isDisabledBoton() {
        return disabledBoton;
    }

    public void setDisabledBoton(boolean disabledBoton) {
        this.disabledBoton = disabledBoton;
    }

    public Boolean getEsAbogado() {
        return esAbogado;
    }

    public void setEsAbogado(Boolean esAbogado) {
        this.esAbogado = esAbogado;
    }

    public Date getFechaMaxima() {
        return fechaMaxima != null ? (Date) fechaMaxima.clone() : null;
    }

    public void setFechaMaxima(Date fechaMaxima) {
        this.fechaMaxima = (null == fechaMaxima ? null : (Date) fechaMaxima.clone());
    }

    public String getTipoContribuyente() {
        return tipoContribuyente;
    }

    public void setTipoContribuyente(String tipoContribuyente) {
        this.tipoContribuyente = tipoContribuyente;
    }

    public void setTipoRolContribuyente(TipoRolContribuyenteIDC tipoRolContribuyente) {
        this.tipoRolContribuyente = tipoRolContribuyente;
    }


    /**
     * 
     * @return arreglo de DocumentoDTO a ser eliminados.
     */
    public DocumentoDTO[] getRegistroAgregar() {
        if (registroAgregar != null) {
            DocumentoDTO[] arreglo = new DocumentoDTO[registroAgregar.length];
            for (int i = 0; i < registroAgregar.length; i++) {
                DocumentoDTO doc = registroAgregar[i];
                arreglo[i] = doc;
            }
            return arreglo;
        } else {
            return null;
        }

    }

    /**
     * 
     * @param registrosEliminar
     *            los registrosAgregarLocal a fijar.
     */
    public void setRegistroAgregar(DocumentoDTO[] registroAgregarArray) {
        if (registroAgregarArray != null) {
            DocumentoDTO[] registrosAgregarLocal = registroAgregarArray.clone();
            DocumentoDTO[] arreglo = new DocumentoDTO[registrosAgregarLocal.length];
            for (int i = 0; i < registrosAgregarLocal.length; i++) {
                DocumentoDTO doc = registrosAgregarLocal[i];
                arreglo[i] = doc;
            }
            this.registroAgregar = arreglo;
        }
        else {
            this.registroAgregar = null;
        }
    }
    
    public void descargarDocumentoOptVer(ActionEvent event){
        DocumentoDTO documento = (DocumentoDTO) event.getComponent().getAttributes().get("verDocumentoOpt");
        setArchivoPdf(new DefaultStreamedContent(descargaDocumentoDto(documento),ConsultasConstantes.APPLICATION_PDF,documento.getNombreDocumento()));
    }
    
    public void descargarDocumentosOpcionalesSeleccionados() {
        Map<Integer,String> mapaRutas = new HashMap<Integer, String>();
        List<DocumentoDTO> lista = getListaDocumentosOpcionalesDTO();
        archivoZip = null;
        for(int i=0; i<lista.size(); i++ ) {
            DocumentoDTO documento = lista.get(i);
            String nombre = documento.getNombreDocumento().substring(NumerosConstantes.CERO,documento.getNombreDocumento().length() -NumerosConstantes.CUATRO) + "-" +  new Date().getTime() +i+ PDF;
            mapaRutas.put(i,nombre);
        }
        archivoZip = new DefaultStreamedContent(new ByteArrayInputStream(generarZipOpcionales(mapaRutas)), "application/zip", "documentos.zip");
    }
    
    public void paginaDescargarResueltosDTO(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("listaDocumentos.clearSelection()");
        setBolDocDto(getListaDocumentosDTO().size() == 0);
    }
    
    public void descargarDocumentoVer(ActionEvent event){
        DocumentoDTO documento = (DocumentoDTO) event.getComponent().getAttributes().get("verDocumento");
        setArchivoPdf(new DefaultStreamedContent(descargaDocumentoDto(documento),ConsultasConstantes.APPLICATION_PDF,documento.getNombreDocumento()));
    }

    public void descargarDocumentosSeleccionados() {
        Map<Integer,String> mapaRutas = new HashMap<Integer, String>();
        List<DocumentoDTO> lista = getListaDocumentosDTO();
        archivoZip = null;
        for(int i=0; i<lista.size(); i++ ) {
            DocumentoDTO documento = lista.get(i);
            String nombre = documento.getNombreDocumento().substring(NumerosConstantes.CERO,documento.getNombreDocumento().length() -NumerosConstantes.CUATRO) + "-" +  new Date().getTime() +i+ PDF;
            mapaRutas.put(i,nombre);
        }
        archivoZip = new DefaultStreamedContent(new ByteArrayInputStream(generarZipResueltos(mapaRutas)), "application/zip", "documentos.zip");
    }
    
    public void paginaDescargarResueltosOficiales(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("listaDocumentosOficiales.clearSelection()");
        setBolDocDtoOficial(true);
    }
    
    public boolean getValidarAcusesFaltantes() {
    	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	session.removeAttribute("numeroAsuntoFaltantes");
    	session.removeAttribute("idSolicitudFaltantes");
    	return consultasBussines.obtenerDocumentosOficialesTipo(tramite.getNumeroAsunto(), TipoDocumentoOficial.OFICIO_TERMINOS_CONDICIONES.getClave()).isEmpty();
    }
    
    public String getAcusesFaltantes() {
    	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("numeroAsuntoFaltantes", tramite.getNumeroAsunto());
        session.setAttribute("idSolicitudFaltantes", tramite.getIdSolicitud());
        String urlFirma;
        if(tramite.getNumeroAsunto().substring(0,3).equals("RRL") ) {
        	urlFirma = UrlFirma.PAGINA_FIRMA_FALTANTES.toString();
        }else {
        	urlFirma = UrlFirma.PAGINA_FIRMA_SOLICITUD_CAL.toString();
        }
        
    	return urlFirma;
    }
    
    
    public void descargarDocumentoOficialeVer(ActionEvent event){
        DocumentoOficialDTO documento = (DocumentoOficialDTO) event.getComponent().getAttributes().get("verDocumentoOficial");
        String nombre = documento.getNombreArchivo();
        if(!nombre.toLowerCase().endsWith(PDF)) {
            nombre = documento.getRutaAzure();
        }
        setArchivoOficialPdf(new DefaultStreamedContent(descargaDocumentoOficial(documento),ConsultasConstantes.APPLICATION_PDF,nombre));
    }
    
    public void descargarDocumentosOficialesSeleccionados() {
        Map<Integer,String> mapaRutas = new HashMap<Integer, String>();
        List<DocumentoOficialDTO> lista = getListaDocumentosOficialDTO();
        archivoOficialZip = null;
        for(int i=0; i<lista.size(); i++ ) {
            DocumentoOficialDTO documento = lista.get(i);
            String nombre = documento.getNombreArchivo();
            if(!nombre.toLowerCase().endsWith(PDF)) {
                nombre = documento.getRutaAzure();
            }
            nombre = nombre.substring(NumerosConstantes.CERO,nombre.length() -NumerosConstantes.CUATRO) + "-" +  new Date().getTime() +i+ PDF;
            mapaRutas.put(i,nombre);
        }
        archivoOficialZip = new DefaultStreamedContent(new ByteArrayInputStream(generarZipOficial(mapaRutas)), "application/zip", "documentosOficiales.zip");
    }

    public StreamedContent getArchivoZip() {
        return archivoZip;
    }

    public void setArchivoZip(StreamedContent archivoZip) {
        this.archivoZip = archivoZip;
    }

    public StreamedContent getArchivoOficialZip() {
        return archivoOficialZip;
    }

    public void setArchivoOficialZip(StreamedContent archivoOficialZip) {
        this.archivoOficialZip = archivoOficialZip;
    }

    public StreamedContent getArchivoPdf() {
        return archivoPdf;
    }

    public void setArchivoPdf(StreamedContent archivoPdf) {
        this.archivoPdf = archivoPdf;
    }

    public StreamedContent getArchivoOficialPdf() {
        return archivoOficialPdf;
    }

    public void setArchivoOficialPdf(StreamedContent archivoOficialPdf) {
        this.archivoOficialPdf = archivoOficialPdf;
    }

    public BandejaDocumentosLazyList getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(BandejaDocumentosLazyList lazyModel) {
        this.lazyModel = lazyModel;
    }

    public DataTable getDataTableFil() {
        return dataTableFil;
    }

    public void setDataTableFil(DataTable dataTableFil) {
        this.dataTableFil = dataTableFil;
    }

    public BandejaDocumentosOfiLazyList getLazyModelOficial() {
        return lazyModelOficial;
    }

    public void setLazyModelOficial(BandejaDocumentosOfiLazyList lazyModelOficial) {
        this.lazyModelOficial = lazyModelOficial;
    }

    public DataTable getDataTableFilOficial() {
        return dataTableFilOficial;
    }

    public void setDataTableFilOficial(DataTable dataTableFilOficial) {
        this.dataTableFilOficial = dataTableFilOficial;
    }
}
