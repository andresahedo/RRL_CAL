/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;

import mx.gob.sat.sgi.SgiCripto.SgiARAException;
import mx.gob.sat.siat.juridica.ara.constantes.AraConstantes;
import mx.gob.sat.siat.juridica.ara.util.AraValidadorHelper;
import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoAcuse;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoProcesoFirma;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRol;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.BaseCloudController;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.DescargarDocumentoController;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.ca.util.constants.RegistroSolicitudConstants;
import mx.gob.sat.siat.juridica.ca.util.validador.TipoRolContribuyenteIDC;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import mx.gob.sat.siat.juridica.rrl.util.constante.Runtime;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import mx.gob.sat.siat.juridica.rrl.util.helper.DiasHabilesHelper;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.CapturaSolicitudBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoDataModel;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility.GenerarDocumentosHelper;
import mx.gob.sat.siat.juridica.rrl.web.util.validador.AnexarDocumentoValidator;


/**
 * Bean que implementan la l&oacute;gica de negocio para la captura de la
 * solicitud
 * 
 * @author Softtek
 * 
 */
@ManagedBean(name = "solicitudController")
@ViewScoped
public class CapturaSolicitudController extends
        BaseCloudController<DocumentoDTO> {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = 1L;
    
    private static final String FORMATO_FECHA = "dd/MM/yyyy HH:mm:ss:SS";
    
    
    public String tipoRolContribuyenteText;
    
    /**
     * @return the solicitudDTO
     */
    public String getTipoRolContribuyenteText() {
        return tipoRolContribuyenteText;
    }

    /**
     * @param solicitudDTO
     *            the solicitudDTO to set
     */
    public void setTipoRolContribuyenteText(String tipoRolContribuyenteText) {
        this.tipoRolContribuyenteText = tipoRolContribuyenteText;
    }


    /** DTO que representa los datos de una solicitud */
    private DatosSolicitudDTO solicitud = new DatosSolicitudDTO();
    /** DTO que representa los datos a capturar de una solicitud */
    private DatosSolicitudDTO datosSolicitud = new DatosSolicitudDTO();
    /** Lista de unidades emisoras */
    private List<CatalogoDTO> listaUnidadesEmisoras = new ArrayList<CatalogoDTO>();

    /** Data model de documentos opcionales */
    private DocumentoDataModel documentoSeleccionDataModel;

    /** Data model de documentos adjuntados */
    private DocumentoDataModel documentoAdjuntarDataModel;

    /** Validator para adjuntar documentos */
    @ManagedProperty(value = "#{anexarDocumentoValidator}")
    private AnexarDocumentoValidator anexarDocumentoValidator;

    @ManagedProperty(value = "#{descargarDocumentoController}")
    private DescargarDocumentoController descargarDocumentoController;

    @ManagedProperty(value = "#{capturaSolicitudBussines}")
    private CapturaSolicitudBussines capturaSolicitudBussines;

    @ManagedProperty(value = "#{generarDocumentosHelper}")
    private GenerarDocumentosHelper generarDocumentosHelper;

    @ManagedProperty(value = "#{diasHabilesHelper}")
    private DiasHabilesHelper diasHabilesHelper;
    
    @ManagedProperty(value = "#{araValidadorHelper}")
    private AraValidadorHelper araValidadorHelper;

    /** Bean para obtener los mensajes */
    /** Mensaje entre pantallas */
    private String messagesRedirect;

    private String rowsCorreo;

    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;

    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    /** Lista de documentos opcionales */
    private List<DocumentoDTO> listaDocumentosOpcionales = new ArrayList<DocumentoDTO>();
    /** Lista de documentos requeridos */
    private List<DocumentoDTO> listaDocumentosRequeridos = new ArrayList<DocumentoDTO>();
    /** Lista de documentos requeridos */
    private List<DocumentoDTO> listaDocumentos = new ArrayList<DocumentoDTO>();

    /** Documentos opcionales seleccionados */
    private DocumentoDTO[] documentosSeleccionados;

    private List<DocumentoDTO> documentosCombo;

    /** Archivo para descarga */
    private transient DefaultStreamedContent archivoDescarga;

    private boolean banderaRazonSocial;

    private DocumentoDTO[] registrosEliminar;

    private List<DocumentoDTO> documents;

    /** Documento seleccionado */
    private DocumentoDTO documentoSeleccionado;

    /** Id documento adjuntar */
    private String id;

    /** DTO que representa lo firma de la solicitud */
    private FirmaDTO firma;

    private String cadenaOriginal;
    private String firmaDigital;

    private String userID;

    private String idDinamicoOpcionales;

    private boolean eliminarVisible;
    private boolean agregarVisible;

    private StringBuffer modalidadTramite;

    private List<String> documentosPorAnexar;
    @ManagedProperty(value = "#{tipoRolContribuyenteIDC}")
    private TipoRolContribuyenteIDC tipoRolContribuyente;
    
    private String numeroAsuntoFaltantes;
    private boolean acusesFaltantes=Boolean.FALSE;

	private Long idSolicitudFaltantes;
    
    public boolean isEliminarVisible() {
        return eliminarVisible;
    }

    public void setEliminarVisible(boolean eliminarVisible) {
        this.eliminarVisible = eliminarVisible;
    }

    /**
     * Constructor
     */
    public CapturaSolicitudController() {
        super();
    }
   

    /**
     * Postc-Constructor para cargar las unidades emisoras e informacion del
     * solicitante
     */
    @PostConstruct
    public void iniciar() {
    	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	numeroAsuntoFaltantes = (String) session.getAttribute("numeroAsuntoFaltantes");
    	idSolicitudFaltantes = (Long) session.getAttribute("idSolicitudFaltantes");
    	getLogger().debug("numeroAsuntoFaltantes:"+numeroAsuntoFaltantes);
    	session.removeAttribute("numeroAsuntoFaltantes");
    	session.removeAttribute("idSolicitudFaltantes");
    	if (numeroAsuntoFaltantes != null && idSolicitudFaltantes != null) {
    		this.iniciarConFaltantes();
    	}else {
    		this.iniciarSinFaltantes();
    	}
    	
    }
    
    private void iniciarConFaltantes() {
    	getLogger().debug("ingresando a iniciar acuses faltantes");
    	llenaModalidaTramite();
    	acusesFaltantes=Boolean.TRUE;
    	setSolicitud(capturaSolicitudBussines.obtenerSolicitudPorId(idSolicitudFaltantes));
		prepararFirmaSolicitud();
    }
    
    private void iniciarSinFaltantes() {
    	
        getLogger().debug("ingresando a iniciar");
        setListaUnidadesEmisoras(getCapturaSolicitudBussines()
                .obtenerAutoridadesEmisoras());
     solicitud.setTipoRolContribuyente("Hidrocarburos");
        datosSolicitud.setTipoRolContribuyente("Hidrocarburos1");
      

        getLogger().debug("va a obtener informacion de idc");
        getLogger().debug("getUserProfile().getRfc() {}",getUserProfile().getRfc());
        setDatosSolicitud(getCapturaSolicitudBussines().obtenerInformacionIDC(getUserProfile().getRfc()));
        getDatosSolicitud().setRolCapturista(TipoRol.SOLICITANTE.getClave());
        
        if (getDatosSolicitud().getCorreoElectronico() != null) {
            String[] correos = getDatosSolicitud().getCorreoElectronico()
                    .split("\n");
            setRowsCorreo(String.valueOf(correos.length));
            if (getRowsCorreo().equals("1") || getRowsCorreo().equals("2")) {
                setRowsCorreo("3");
            }
        }
        if (getDatosSolicitud().getRazonSocial() != null) {
            if (getDatosSolicitud().getRazonSocial().equalsIgnoreCase("")) {
                setBanderaRazonSocial(false);
            } else {
                setBanderaRazonSocial(true);
            }

        } else {
            setBanderaRazonSocial(false);
        }
        llenaModalidaTramite();
        getLogger().debug("terminando  iniciar");
        meterId();
        
        /**
         *  LLAMA AL SERVICIO DE HIDROCARBUROS
         * 
         */
        try {
            tipoRolContribuyenteText = tipoRolContribuyente.consultaHidrocarburos(getUserProfile().getRfc());
        } catch (IOException e) {
            getLogger().error("Error al consumir el servicio para Hidrocarburos");
        }
    }

   private void llenaModalidaTramite() {
	   setModalidadTramite(new StringBuffer());
       String modalidad = getCapturaSolicitudBussines()
               .obtenerModalidadDeTramite(
                       Integer.parseInt(DiscriminadorConstants.T1_CLASIFICACION_ARANCELARIA));
       getModalidadTramite().append("Recursos Administrativos-");
       getModalidadTramite().append(modalidad);
	   
   }

	protected void meterId() {
        Date hora = new Date();
        Long identificados = hora.getTime();
        StringBuffer idCompleto = new StringBuffer();
        idCompleto.append("idTree");
        idCompleto.append(identificados);

        setIdDinamicoOpcionales(idCompleto.toString());
    }

    /**
     * M&eacute;todo para almacenar la informaci&oacute;n capturada en la
     * solicitud
     */
    public void guardarSolicitud() throws SolicitudNoGuardadaException {
        if (getDatosSolicitud().getRfcContribuyente() == null
                || getDatosSolicitud().getRfcContribuyente().equals("")) {
            throw new SolicitudNoGuardadaException(
                    "Parece haber un error con el servicio, intente mas tarde.");

        }
        setSolicitud(getCapturaSolicitudBussines().guardarSolicitud(
                getDatosSolicitud(), getUserProfile().getRfc()));
        setListaDocumentosRequeridos(getCapturaSolicitudBussines()
                .listaDocObligatorios(
                        Integer.valueOf(getSolicitud().getTipoTramite())));
        setListaDocumentosOpcionales(getCapturaSolicitudBussines()
                .listaDocOpcionales(
                        Integer.valueOf(getSolicitud().getTipoTramite())));
        setDocumentoSeleccionDataModel(new DocumentoDataModel(
                getListaDocumentosOpcionales()));
        setDocumentoAdjuntarDataModel(new DocumentoDataModel(
                getListaDocumentos()));

        // Preselecciona los documentos opcionales que ya se hayan
        // anexado previamente a la solicitud (guardado parcial)
        setDocumentosSeleccionados(getCapturaSolicitudBussines()
                .obtenerDocumentosSeleccionados(
                        getSolicitud().getIdSolicitud(),
                        getListaDocumentosOpcionales()));

    }

    /**
     * M&eacute;todo para cambiar autoridades
     */
    public void cambiarAutoridadEmisora(ValueChangeEvent event) {
        getDatosSolicitud().setIdAutoridadEmisora(
                event.getNewValue().toString());

    }

    /**
     * M&eacute;todo para continuar con anexar documentos
     */
    public void anexarDocumentos() {
        List<DocumentoDTO> listaDoctos = new ArrayList<DocumentoDTO>();
        listaDoctos.addAll(getListaDocumentos());
        for (DocumentoDTO d : getDocumentosSeleccionados()) {
            listaDoctos.add(d);
        }

    }

    /**
     * 
     * @return listaDocumentosOpcionales
     */
    public List<DocumentoDTO> getListaDocumentosOpcionales() {
        return listaDocumentosOpcionales;
    }

    /**
     * 
     * @param listaDocumentosOpcionales
     *            a fijar
     */
    public void setListaDocumentosOpcionales(
            List<DocumentoDTO> listaDocumentosOpcionales) {
        this.listaDocumentosOpcionales = listaDocumentosOpcionales;
    }

    /**
     * 
     * @return documentosSeleccionados
     */
    public DocumentoDTO[] getDocumentosSeleccionados() {
        if (documentosSeleccionados != null) {
            DocumentoDTO[] arreglo = new DocumentoDTO[documentosSeleccionados.length];
            for (int i = 0; i < documentosSeleccionados.length; i++) {
                DocumentoDTO doc = documentosSeleccionados[i];
                arreglo[i] = doc;
            }
            return arreglo;
        } else {
            return null;
        }
    }

    /**
     * 
     * @param documentosSeleccionados
     *            a fijar
     */
    public void setDocumentosSeleccionados(
            DocumentoDTO[] documentosSeleccionadosArray) {
        if (documentosSeleccionadosArray != null) {
            DocumentoDTO[] documentosSeleccionadosLocal = documentosSeleccionadosArray
                    .clone();
            DocumentoDTO[] arreglo = new DocumentoDTO[documentosSeleccionadosLocal.length];
            for (int i = 0; i < documentosSeleccionadosLocal.length; i++) {
                DocumentoDTO doc = documentosSeleccionadosLocal[i];
                arreglo[i] = doc;
            }
            this.documentosSeleccionados = arreglo.clone();
        } else {
            this.documentosSeleccionados = null;
        }
    }

    public String onFlowProcess(FlowEvent event) {

        if (event.getNewStep().equals(
                RegistroSolicitudConstants.SELECCION_DOCUMENTOS)) {
            try {
                meterId();
                guardarSolicitud();
                return event.getNewStep();
            } catch (SolicitudNoGuardadaException e) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e
                                .getMessage()));
                return RegistroSolicitudConstants.DATOS_SOLICITUD;

            }
        } else if (event.getNewStep().equals(
                RegistroSolicitudConstants.ANEXAR_DOCUMENTOS_SOLICITUD)) {
            prepararDocumentosAnexar();
            return event.getNewStep();
        } else if (event.getNewStep().equals(RegistroSolicitudConstants.FIRMAR)) {
            try {
                List<DocumentoDTO> listaDocs = getCapturaSolicitudBussines()
                        .obtenerDocumentosPorIdSolicitud(
                                getSolicitud().getIdSolicitud());
                if (listaDocs != null && !listaDocs.isEmpty()) {
                    prepararFirmaSolicitud();
                    return event.getNewStep();

                } else {
                    String mensaje = guardarDocumentos();
                    if (mensaje == null) {
                        prepararFirmaSolicitud();
                        return event.getNewStep();
                    } else {
                        throw new ArchivoNoGuardadoException(mensaje);
                    }
                }
            } catch (ArchivoNoGuardadoException e) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, e
                                .getMessage(), ""));
                for (String documento : getDocumentosPorAnexar()) {
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    documento, ""));
                }
                return RegistroSolicitudConstants.ANEXAR_DOCUMENTOS_SOLICITUD;
            }
        }
        return RegistroSolicitudConstants.DATOS_SOLICITUD;

    }

    /**
     * Ejecuta el guardado parcial de documentos y muestra mensaje de
     * confirmacion
     * 
     * @param actionEvent
     */
    public void guardarDocumentosParcial() {

        setDocumentoAdjuntarDataModel(new DocumentoDataModel(
                getListaDocumentos()));

        String guardadoParcial = messages
                .getString("cal.captura.solicitud.promocion.guardadoParcial");
        String[] param = new String[2];
        param[0] = getSolicitud().getIdSolicitud().toString();
        param[1] = diasHabilesHelper.calcularDiasFaltantesSolParcial(
                getSolicitud().getFechaCreacion()).toString();

        getFlash().put(VistaConstantes.SOLICITUD, getSolicitud());

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "", MessageFormat
                        .format(guardadoParcial, (Object[]) param)));
    }

    /**
     * M&eacute;todo para prsentar mensajes de otra pagina.
     */
    public void mensajesRedirect() {
        if (getMessagesRedirect() != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(getMessagesRedirect(), ""));
            setMessagesRedirect(null);
        }
    }

    // anexar Documentos

    public boolean prepararDocumentosAnexar() {
        documents = new ArrayList<DocumentoDTO>();
        setDocumentosCombo(new ArrayList<DocumentoDTO>());
        getDocumentosCombo().addAll(getListaDocumentosRequeridos());

        for (DocumentoDTO doc : getDocumentosSeleccionados()) {
            getDocumentosCombo().add(doc);
        }

        for (DocumentoDTO doc : getDocumentosCombo()) {
            if (!Runtime.getInstance().isFielRequired()) {
                if (listaDocumentos != null && listaDocumentos.isEmpty()) {
                    doc = documentosEstres(doc);
                    if (getListaDocumentos().size() != getDocumentosCombo()
                            .size()) {
                        getListaDocumentos().add(doc);
                    }
                }
            }
        }

        getListaDocumentos().addAll(
                getCapturaSolicitudBussines().obtenerDocumentosRegistro(
                        getSolicitud().getIdSolicitud()));
        List<DocumentoDTO> documentos = documents;
        if (documentos != null && !documentos.isEmpty()) {
            getListaDocumentos().addAll(documentos);
        }
        setDocumentoAdjuntarDataModel(new DocumentoDataModel(
                getListaDocumentos()));
        return !getDocumentosCombo().isEmpty();
    }

    /**
     * Metodo que llena los documentos cuando se inicia en modo estres
     * 
     * @param documentosCombo
     * @return listaDocumentos
     */
    public DocumentoDTO documentosEstres(DocumentoDTO documento) {
        documento.setIdTipoDocumento(documento.getIdTipoDocumento());
        documento.setNombre("estres");
        documento.setRutaAzure("AERF770708T57.1426036676396");
        documento.setHashDocumento("bdd74a9c7a29820a4001e885bc151190");
        documento.setTamanioArchivo(new Long(
                NumerosConstantes.NOVENTA_Y_SEIS_MIL_CINCUENTA_Y_UNO));
        documento.setCadenaTamanioArchivo("1");
        return documento;
    }
    
    /**
     * M&eacute;todo para adjuntar un documento
     */
    public void anexarDocumentoNube() {
        String[] ids = getId().split(",");
        getLogger().debug("ids al guardar documento subido a la nube:{}", getId());
        if (ids != null && ids.length > 0) {
            DocumentoDTO documento = obtenerDocumento(getId());
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
        setDocumentoAdjuntarDataModel(new DocumentoDataModel(getListaDocumentos()));
        setId("");
    }

    /**
     * Metodo que carga la lista de documentos.
     */
    public void iniciarDocumentos() {
        listaDocumentos = new ArrayList<DocumentoDTO>();
    }

    /**
     * M&eacute;todo para eliminar documentos seleccionados
     */
    public void eliminarDocumentos(ActionEvent event) {
        if (getRegistrosEliminar() != null && getRegistrosEliminar().length > 0) {
            for (DocumentoDTO documento : getRegistrosEliminar()) {
                getListaDocumentos().remove(documento);
                if (documento.getIdDocumentoSolicitud() != 0) {
                    getCapturaSolicitudBussines().eliminaDocumento(
                            documento.getIdDocumentoSolicitud());
                }
            }
            setEliminarVisible(false);
            setRegistrosEliminar(new DocumentoDTO[registrosEliminar.length]);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "",
                            "El (Los) documento(s) ha(n) sido eliminado(s)"));
        }

    }

    /**
     * M&eacute;todo para eliminar documentos seleccionados
     */
    public void cambioVisible() {
        if (getId() != null) {
            setAgregarVisible(true);
        } else {
            setAgregarVisible(false);
        }
    }

    /**
     * M&eacute;todo para descargar un documento
     * 
     * @throws IOException
     */
    public void descargarDocumento(ActionEvent event) throws IOException {
        DocumentoDTO documento = (DocumentoDTO) event.getComponent()
                .getAttributes().get("documentoParametro");
        InputStream input = getCapturaSolicitudBussines().descargarDocumento(
                documento);

        HttpServletResponse response = (HttpServletResponse) FacesContext
                .getCurrentInstance().getExternalContext().getResponse();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename="
                + documento.getNombre());
        IOUtils.copy(input, response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * M&eacute;todo para almacenar los documentos a la solicitud
     * 
     * @throws ArchivoNoGuardadoException
     */
    public String guardarDocumentos() throws ArchivoNoGuardadoException {
        String documentosFaltantes = "";

        if (getAnexarDocumentoValidator().validarDocumentosAnexados(
                getListaDocumentos(), getDocumentosCombo())) {

            getCapturaSolicitudBussines().guardarDocumentosSolicitud(
                    getSolicitud().getIdSolicitud(), getListaDocumentos(),
                    getSolicitud().getTipoTramite(), getUserProfile().getRfc());
            return null;

        } else {

            if (getAnexarDocumentoValidator()
                    .validarDocumentosAnexadosFaltantes(getListaDocumentos(),
                            getDocumentosCombo()) != null) {

                setDocumentosPorAnexar(getAnexarDocumentoValidator()
                        .validarDocumentosAnexadosFaltantes(listaDocumentos,
                                documentosCombo));
            }
            String docsFaltantes = "Adjuntar todos los documentos seleccionados "
                    + documentosFaltantes;

            for (DocumentoDTO doc : listaDocumentos) {
                documents.add(doc);
            }
            setAgregarVisible(true);
            return docsFaltantes;
        }

    }

    /**
     * Metodo para redireccionar a firma.
     * 
     * 
     */

    public String getDireccionFirma() {

        return UrlFirma.PAGINA_FIRMA_SOLICITUD.toString();
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
     *            a fijar
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
     *            a fijar
     */
    public void setDocumentoSeleccionado(DocumentoDTO documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    /**
     * 
     * @return listaDocumentos
     */
    public List<DocumentoDTO> getListaDocumentos() {
        return listaDocumentos;
    }

    /**
     * 
     * @param listaDocumentos
     *            a fijar
     */
    public void setListaDocumentos(List<DocumentoDTO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
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
     *            a fijar
     */
    public void setDocumentosCombo(List<DocumentoDTO> documentosCombo) {
        this.documentosCombo = documentosCombo;
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
     *            a fijar
     */
    public void setArchivoDescarga(DefaultStreamedContent archivoDescarga) {
        this.archivoDescarga = archivoDescarga;
    }

    /**
     * 
     * @return anexarDocumentoValidator
     */
    public AnexarDocumentoValidator getAnexarDocumentoValidator() {
        return anexarDocumentoValidator;
    }

    /**
     * 
     * @param anexarDocumentoValidator
     *            a fijar
     */
    public void setAnexarDocumentoValidator(
            AnexarDocumentoValidator anexarDocumentoValidator) {
        this.anexarDocumentoValidator = anexarDocumentoValidator;
    }

    /**
     * 
     * @return arreglo
     */
    public DocumentoDTO[] getRegistrosEliminar() {
        if (registrosEliminar != null) {
            DocumentoDTO[] arreglo = new DocumentoDTO[registrosEliminar.length];
            for (int i = 0; i < registrosEliminar.length; i++) {
                DocumentoDTO doc = registrosEliminar[i];
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
     *            los registrosEliminar a fijar.
     */
    public void setRegistrosEliminar(DocumentoDTO[] registrosEliminarArray) {
        if (registrosEliminarArray != null) {
            DocumentoDTO[] registrosEliminarLocal = registrosEliminarArray
                    .clone();
            DocumentoDTO[] arreglo = new DocumentoDTO[registrosEliminarLocal.length];
            for (int i = 0; i < registrosEliminarLocal.length; i++) {
                DocumentoDTO doc = registrosEliminarLocal[i];
                arreglo[i] = doc;
            }
            this.registrosEliminar = arreglo.clone();
        } else {
            this.registrosEliminar = null;
        }
    }

    public void rowSelectCheckbox(SelectEvent event) {

        setEliminarVisible(true);

    }

    public void rowUnselectCheckbox(UnselectEvent event) {
        if (getRegistrosEliminar().length < 1) {
            setEliminarVisible(false);
        }
    }

    public List<DocumentoDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentoDTO> documents) {
        this.documents = documents;
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

    @Override
    public CapturaSolicitudBussines getBusinessBean() {
        return capturaSolicitudBussines;
    }

    /**
     * 
     * @return solicitud
     */
    public DatosSolicitudDTO getSolicitud() {
        return solicitud;
    }

    /**
     * 
     * @param solicitud
     *            a fijar
     */
    public void setSolicitud(DatosSolicitudDTO solicitud) {
        this.solicitud = solicitud;
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
     *            a fijar
     */
    public void setDatosSolicitud(DatosSolicitudDTO datosSolicitud) {
        this.datosSolicitud = datosSolicitud;
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
     *            a fijar
     */
    public void setListaUnidadesEmisoras(List<CatalogoDTO> listaUnidadesEmisoras) {
        this.listaUnidadesEmisoras = listaUnidadesEmisoras;
    }

    public boolean isBanderaRazonSocial() {
        return banderaRazonSocial;
    }

    public void setBanderaRazonSocial(boolean banderaRazonSocial) {
        this.banderaRazonSocial = banderaRazonSocial;
    }

    // firma

    /**
     * M&eacute;todo para preparar la cadena original de la firma de la
     * solicitud
     */

    public void prepararFirmaSolicitud() {

        setFirma(new FirmaDTO(new Date()));
        if (getSolicitud() != null) {
            getFirma().setCadenaOriginal(
                    getCapturaSolicitudBussines().generaCadenaOriginal(
                            getSolicitud().getIdSolicitud(),
                            getFirma().getFechaFirma()));
        }
    }

    public String procesaFirma() {
        setFirmaDigital(FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("firmaDigital").toString());
        return "";
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
    	Date fechaFirma = new Date();
		firma.setFechaFirma(fechaFirma);
		firma.setSello(getFirmaDigital());
		firma.setCertificado(sNumSerie);
		firma.setRfcUsuario(getUserProfile().getRfc());
		firma.setCveRol(getUserProfile().getRol());
		firma.setCveProceso(TipoProcesoFirma.REG_PROM.getClave());
    }
    
	public void firmar() {
		try {
			if (validarAra()) {
				generaFechaFirma();
				if (acusesFaltantes) {
					firmarFaltantes();
				} else {
					firmarSinFaltantes();
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error:",
						MessageFormat.format(AraConstantes.MENSAJE_LLAVE_SERIE_INCORRECTO, getUserProfile().getRfc())));
			}
		} catch (SgiARAException ex) {
			getLogger().error("Error al firmar promocion ", ex);
			getFlash().put(VistaConstantes.SOLICITUD, getSolicitud());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
		} catch (BusinessException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", e.getMessage()));
			getFlash().put(VistaConstantes.SOLICITUD, getSolicitud());
		} catch (Exception e) {
			getLogger().error("Error al firmar promocion ", e);
			getFlash().put(VistaConstantes.SOLICITUD, getSolicitud());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ocurri\u00F3 un error al firmar la promoci\u00F3n", ""));
		}
	}
    
	/* Firmar Acuses Faltantes */
	private void firmarFaltantes() throws SgiARAException, BusinessException {
		List<Long> idsDoc = new ArrayList<Long>();
		/* generar Asunto */
		String numAsunto = numeroAsuntoFaltantes;
		// clh profiling firma
		FirmaDTO firmaSelladora = getCapturaSolicitudBussines().obtenSelloPromocionSIAT(numAsunto, getSolicitud().getIdSolicitud(), firma.getFechaFirma());
		DatosBandejaTareaDTO datosBandejaTareaDTO = new DatosBandejaTareaDTO();
		datosBandejaTareaDTO.setNumeroAsunto(numAsunto);
		datosBandejaTareaDTO.setIdSolicitud(getSolicitud().getIdSolicitud());
		datosBandejaTareaDTO.setRfcSolicitante(getUserProfile().getRfc());
		idsDoc = getGenerarDocumentosHelper().generarDocumentosPromocion(datosBandejaTareaDTO,
				TipoAcuse.RECPROM.getClave(), firma.getCadenaOriginal(), firma.getSello(),
				firmaSelladora.getCadenaOriginal(), firmaSelladora.getSello());
		getCapturaSolicitudBussines().firmarDocumentos(getSolicitud().getIdSolicitud(), firma);
		new SimpleDateFormat(FORMATO_FECHA).format(new Date());
		resultadoAcuses(numAsunto,idsDoc);
    }
    
	private void resultadoAcuses(String numAsunto, List<Long> idsDoc) {
		descargarDocumentoController.getDatosBandejaTareaDTO().setNumeroAsunto(numAsunto);
		descargarDocumentoController.obtenerDocumentosByIdDoc(idsDoc);
		String aviso = "Tu Promoci\u00F3n ha sido registrada con el siguiente  n\u00FAmero de Asunto " + numAsunto;
		descargarDocumentoController.setMessagesRedirect(aviso);
		ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext
				.getCurrentInstance().getApplication().getNavigationHandler();
		configurableNavigationHandler
				.performNavigation(LoginConstante.DESCARGA_DOCUMENTO + "?faces-redirect=true");
	}
	
    /**
     * M&eacute;todo para firmar la solicitud
     * @throws BusinessException 
     */
	private void firmarSinFaltantes() throws BusinessException {
		getLogger().debug("Profiling inicio firmar {}", new SimpleDateFormat(FORMATO_FECHA).format(new Date()));

		List<Long> idsDoc = new ArrayList<Long>();
		String numAsunto = getCapturaSolicitudBussines().firmarSolicitud(getSolicitud().getIdSolicitud(), getFirma(),
				getUserProfile().getRfc(), getSolicitud().getRfcContribuyente(), new Object());
		getLogger().debug("Profiling fin bussines firmar {}", new SimpleDateFormat(FORMATO_FECHA).format(new Date()));
		if (numAsunto != null) {
			getLogger().debug("Profiling inicio bussines obtenSelloPromocionSIAT {}",
					new SimpleDateFormat(FORMATO_FECHA).format(new Date()));
			FirmaDTO firmaSelladora = getCapturaSolicitudBussines().obtenSelloPromocionSIAT(numAsunto,
					getSolicitud().getIdSolicitud(), firma.getFechaFirma());
			getLogger().debug("Profiling fin bussines obtenSelloPromocionSIAT {}",
					new SimpleDateFormat(FORMATO_FECHA).format(new Date()));

			DatosBandejaTareaDTO datosBandejaTareaDTO = new DatosBandejaTareaDTO();
			datosBandejaTareaDTO.setNumeroAsunto(numAsunto);
			datosBandejaTareaDTO.setIdSolicitud(getSolicitud().getIdSolicitud());
			datosBandejaTareaDTO.setRfcSolicitante(getUserProfile().getRfc());
			// clh profiling firma
			getLogger().debug("Profiling inicio bussines generarDocumentosPromocion {}",
					new SimpleDateFormat(FORMATO_FECHA).format(new Date()));
			idsDoc = getGenerarDocumentosHelper().generarDocumentosPromocion(datosBandejaTareaDTO,
					TipoAcuse.RECPROM.getClave(), firma.getCadenaOriginal(), firma.getSello(),
					firmaSelladora.getCadenaOriginal(), firmaSelladora.getSello());
			getLogger().debug("Profiling fin bussines generarDocumentosPromocion {}",
					new SimpleDateFormat(FORMATO_FECHA).format(new Date()));

			// clh profiling firma
			getLogger().debug("Profiling inicio bussines firmarDocumentos {}",
					new SimpleDateFormat(FORMATO_FECHA).format(new Date()));
			getCapturaSolicitudBussines().firmarDocumentos(getSolicitud().getIdSolicitud(), firma);
			getLogger().debug("Profiling fin bussines firmarDocumentos {}",
					new SimpleDateFormat(FORMATO_FECHA).format(new Date()));
		}

		descargarDocumentoController.getDatosBandejaTareaDTO().setNumeroAsunto(numAsunto);
		descargarDocumentoController.obtenerDocumentosByIdDoc(idsDoc);
		String aviso = "Tu Promoci\u00F3n ha sido registrada con el siguiente  n\u00FAmero de Asunto " + numAsunto;
		descargarDocumentoController.setMessagesRedirect(aviso);
		ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext
				.getCurrentInstance().getApplication().getNavigationHandler();

		configurableNavigationHandler.performNavigation(LoginConstante.DESCARGA_DOCUMENTO + "?faces-redirect=true");
	}

    /**
     * 
     * @return firma
     */
    public FirmaDTO getFirma() {
        return firma;
    }

    /**
     * 
     * @param firma
     *            a fijar
     */
    public void setFirma(FirmaDTO firma) {
        this.firma = firma;
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
    public void setDescargarDocumentoController(
            DescargarDocumentoController descargarDocumentoController) {
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
    public void setGenerarDocumentosHelper(
            GenerarDocumentosHelper generarDocumentosHelper) {
        this.generarDocumentosHelper = generarDocumentosHelper;
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

    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
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
     * @return the documentoSeleccionDataModel
     */
    public DocumentoDataModel getDocumentoSeleccionDataModel() {
        return documentoSeleccionDataModel;
    }

    /**
     * @param documentoSeleccionDataModel
     *            the documentoSeleccionDataModel to set
     */
    public void setDocumentoSeleccionDataModel(
            DocumentoDataModel documentoSeleccionDataModel) {
        this.documentoSeleccionDataModel = documentoSeleccionDataModel;
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
    public void setDocumentoAdjuntarDataModel(
            DocumentoDataModel documentoAdjuntarDataModel) {
        this.documentoAdjuntarDataModel = documentoAdjuntarDataModel;
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
    public void setListaDocumentosRequeridos(
            List<DocumentoDTO> listaDocumentosRequeridos) {
        this.listaDocumentosRequeridos = listaDocumentosRequeridos;
    }

    @Override
    public List<DocumentoDTO> getListaDocumentosAdjuntados() {
        return getListaDocumentos();
    }

    public CapturaSolicitudBussines getCapturaSolicitudBussines() {
        return capturaSolicitudBussines;
    }

    public void setCapturaSolicitudBussines(
            CapturaSolicitudBussines capturaSolicitudBussines) {
        this.capturaSolicitudBussines = capturaSolicitudBussines;
    }

    public void validaAccesoProcesoMenu() {
        // Validacion de acceso a la funcionalidad
        this.validaAccesoContribuytente();
    }

    public String getRowsCorreo() {
        return rowsCorreo;
    }

    public void setRowsCorreo(String rowsCorreo) {
        this.rowsCorreo = rowsCorreo;
    }

    public StringBuffer getModalidadTramite() {
        return modalidadTramite;
    }

    public void setModalidadTramite(StringBuffer modalidadTramite) {
        this.modalidadTramite = modalidadTramite;
    }

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<String> getDocumentosPorAnexar() {
        return documentosPorAnexar;
    }

    public void setDocumentosPorAnexar(List<String> documentosPorAnexar) {
        this.documentosPorAnexar = documentosPorAnexar;
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

    public void setTipoRolContribuyente(TipoRolContribuyenteIDC tipoRolContribuyente) {
        this.tipoRolContribuyente = tipoRolContribuyente;
    }

}
