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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;

import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.util.exception.BaseBussinessException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.BaseCloudController;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AnexarDocumentoBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoDataModel;
import mx.gob.sat.siat.juridica.rrl.web.util.validador.AnexarDocumentoValidator;

/**
 * Bean que implementan la l&oacute;gica de negocio para adjuntar,
 * descargar y guardar documentos de la solicitud
 * 
 * @author Softtek
 * 
 */
@ManagedBean(name = "anexarDocumentoController")
@ViewScoped
public class AnexarDocumentoController extends BaseCloudController<DocumentoDTO> {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = 1L;
    /** DTO que representa los datos de una solicitud */
    private SolicitudDTO solicitud;
    /** Id documento adjuntar */
    private String id;
    /** Documento seleccionado */
    private DocumentoDTO documentoSeleccionado;
    /** Lista de documentos adjuntados */
    private List<DocumentoDTO> listaDocumentos = new ArrayList<DocumentoDTO>();
    /** Lista de tipos de documentos seleccionados para ser adjuntados */
    private List<DocumentoDTO> documentosCombo;
    /** Data model de documentos adjuntados */
    private DocumentoDataModel documentoDataModel;
    /** Archivo para descarga */
    private transient DefaultStreamedContent archivoDescarga;
    /** Bussines para adjuntar documentos */
    @ManagedProperty(value = "#{anexarDocumentoBussines}")
    private AnexarDocumentoBussines anexarDocumentoBussines;

    /** Validator para adjuntar documentos */
    @ManagedProperty(value = "#{anexarDocumentoValidator}")
    private AnexarDocumentoValidator anexarDocumentoValidator;
    
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;
    /**
     * Arreglo de registros a eliminar.
     */
    private DocumentoDTO[] registrosEliminar;

    private List<DocumentoDTO> documents;

    private boolean eliminarVisible;
    private boolean agregarVisible;

    public boolean isEliminarVisible() {
        return eliminarVisible;
    }

    public void setEliminarVisible(boolean eliminarVisible) {
        this.eliminarVisible = eliminarVisible;
    }

    /**
     * Constructor para referenciar los documentos a adjuntar al data
     * model
     */
    public AnexarDocumentoController() {

    }

    /**
     * Metodo que se ejecuta despues de la creacion del bean.
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @PostConstruct
    public void init() {
        documents = new ArrayList<DocumentoDTO>();
        setDocumentosCombo((List<DocumentoDTO>) getFlash().get(VistaConstantes.DOCUMENTOS_COMBO));
        setSolicitud((SolicitudDTO) getFlash().get(VistaConstantes.SOLICITUD));
        if (getSolicitud() != null) {
            getListaDocumentos().addAll(
                    getAnexarDocumentoBussines().obtenerDocumentosRegistro(getSolicitud().getIdSolicitud()));
        }
        List<DocumentoDTO> documentos = (List<DocumentoDTO>) getFlash().get("documentoDataModel");
        if (documentos != null && !documentos.isEmpty()) {
            getListaDocumentos().addAll(documentos);
        }
        setDocumentoDataModel(new DocumentoDataModel(getListaDocumentos()));
    }

    /**
     * M&eacute;todo para adjuntar un documento
     */

    public void anexarDocumentoNube() {
        String[] ids = getId().split(",");
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
                    getAnexarDocumentoBussines().eliminaDocumento(documento.getIdDocumentoSolicitud());
                }
            }
            setEliminarVisible(false);
            setRegistrosEliminar(new DocumentoDTO[registrosEliminar.length]);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El (Los) documento(s) ha(n) sido eliminado(s)"));
        }

    }

    /**
     * M&eacute;todo para eliminar documentos seleccionados
     */
    public void cambioVisible() {
        if (getId() != null) {
            setAgregarVisible(true);
        }
        else {
            setAgregarVisible(false);
        }
    }

    /**
     * M&eacute;todo para descargar un documento
     */
    public void descargarDocumento(ActionEvent event) throws IOException {
        DocumentoDTO documento = (DocumentoDTO) event.getComponent().getAttributes().get("documentoParametro");
        InputStream input = getAnexarDocumentoBussines().descargarDocumento(documento);

        HttpServletResponse response =
                (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=" + documento.getNombre());
        IOUtils.copy(input, response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * M&eacute;todo para almacenar los documentos a la solicitud
     */
    public String guardarDocumentos() {
        String siguiente = UrlFirma.PAGINA_FIRMA_SOLICITUD.toString();
        String error = "anexarDocumentos.jsf";
        if (getAnexarDocumentoValidator().validarDocumentosAnexados(getListaDocumentos(), getDocumentosCombo())) {

            getFlash().put(VistaConstantes.SOLICITUD, getSolicitud());
            for (DocumentoDTO doc : getListaDocumentos()) {
                doc.setComplementario(false);
            }
            try {
                getAnexarDocumentoBussines().guardarDocumentosSolicitud(getSolicitud().getIdSolicitud(),
                        getListaDocumentos(), solicitud.getTipoTramite(), getSolicitud().getRfc());
            }
            catch (BaseBussinessException e) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                "Debe proporcionar todos los requisitos obligatorios del asunto"));
                getFlash().put(VistaConstantes.SOLICITUD, getSolicitud());
                getFlash().put(VistaConstantes.DOCUMENTOS_COMBO, getDocumentosCombo());
                return error;
            }
            setListaDocumentos(new ArrayList<DocumentoDTO>());
            setDocumentoDataModel(new DocumentoDataModel(getListaDocumentos()));
            return siguiente;
        }
        else {

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Debe adjuntar todos los documentos que seleccion\u00F3 del asunto los cuales son:"));
            for (DocumentoDTO doc : listaDocumentos) {
                documents.add(doc);
            }
            getFlash().put("documentosCombo", getDocumentosCombo());
            getFlash().put("documentoDataModel", documents);
            getFlash().put(VistaConstantes.SOLICITUD, getSolicitud());
            return error;

        }
    }

    /**
     * 
     * @return solicitud
     */
    public SolicitudDTO getSolicitud() {
        return solicitud;
    }

    /**
     * 
     * @param solicitud
     *            a fijar
     */
    public void setSolicitud(SolicitudDTO solicitud) {
        this.solicitud = solicitud;
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
     * @return documentoDataModel
     */
    public DocumentoDataModel getDocumentoDataModel() {
        return documentoDataModel;
    }

    /**
     * 
     * @param documentoDataModel
     *            a fijar
     */
    public void setDocumentoDataModel(DocumentoDataModel documentoDataModel) {
        this.documentoDataModel = documentoDataModel;
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
     * @return anexarDocumentoBussines
     */
    public AnexarDocumentoBussines getAnexarDocumentoBussines() {
        return anexarDocumentoBussines;
    }

    /**
     * 
     * @param anexarDocumentoBussines
     *            a fijar
     */
    public void setAnexarDocumentoBussines(AnexarDocumentoBussines anexarDocumentoBussines) {
        this.anexarDocumentoBussines = anexarDocumentoBussines;
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
    public void setAnexarDocumentoValidator(AnexarDocumentoValidator anexarDocumentoValidator) {
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
        }
        else {
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
            DocumentoDTO[] registrosEliminarLocal = registrosEliminarArray.clone();
            DocumentoDTO[] arreglo = new DocumentoDTO[registrosEliminarLocal.length];
            for (int i = 0; i < registrosEliminarLocal.length; i++) {
                DocumentoDTO doc = registrosEliminarLocal[i];
                arreglo[i] = doc;
            }
            this.registrosEliminar = arreglo;
        }
        else {
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
    public BaseCloudBusiness getBusinessBean() {
        return anexarDocumentoBussines;
    }

    @Override
    public List<DocumentoDTO> getListaDocumentosAdjuntados() {
        return getListaDocumentos();
    }

    public void validaAccesoProcesoMenu() {
        // Validacion de acceso a la funcionalidad
        this.validaAccesoContribuytente();
    }

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

}
