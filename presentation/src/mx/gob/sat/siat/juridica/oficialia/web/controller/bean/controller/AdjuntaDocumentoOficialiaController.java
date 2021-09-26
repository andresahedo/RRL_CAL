package mx.gob.sat.siat.juridica.oficialia.web.controller.bean.controller;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitud;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.BaseCloudController;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.oficialia.web.controller.bean.bussiness.AdjuntaDocumentoOficialiaBusiness;
import mx.gob.sat.siat.juridica.oficialia.web.controller.bean.constantes.OficialiaConstantesController;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoDataModel;

@ViewScoped
@ManagedBean(name = "adjuntaDocumentoOficialia")
public class AdjuntaDocumentoOficialiaController extends BaseCloudController<DocumentoDTO> {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{adjuntaDocumentoOficialiaBussines}")
    private AdjuntaDocumentoOficialiaBusiness adjuntaDocumentoOficialiaBussines;

    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    private String documentoSelected;

    private List<DocumentoDTO> listaDocumentos;

    private Solicitud solicitud;

    private List<DocumentoDTO> documentosCombo = new ArrayList<DocumentoDTO>();

    private DefaultStreamedContent archivoDescarga;

    private List<DocumentoDTO> listaGeneralDocumentos;

    private DocumentoDTO[] registrosEliminar;

    private String id = "";

    private Long folio;

    private boolean eliminarVisible;

    private boolean agregarVisible;

    private DatosBandejaTareaDTO dbdto;

    private DocumentoDTO documentoDto = new DocumentoDTO();

    private DocumentoDataModel documentoAdjuntarDataModel;

    @PostConstruct
    public void iniciar() {
        dbdto = (DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO);
        documentoDto = (DocumentoDTO) getFlash().get("documentoDTO");
        setSolicitud(getAdjuntaDocumentoOficialiaBussines().obtenerDatos(dbdto.getIdSolicitud()));
        listaDocumentos = new ArrayList<DocumentoDTO>();
        listaGeneralDocumentos = new ArrayList<DocumentoDTO>();
    }

    @Override
    public BaseCloudBusiness getBusinessBean() {
        return adjuntaDocumentoOficialiaBussines;
    }

    public void anexarDocumentoNube() {
        String[] ids = getId().split(",");
        if (ids != null && ids.length > 0) {
            getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, dbdto);
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
        setAgregarVisible(false);
        setId("");
    }

    public void descargarDocumento(ActionEvent event) {
        DocumentoDTO documento = (DocumentoDTO) event.getComponent().getAttributes().get("documentoParametro");
        File file = new File(documento.getRuta());
        InputStream input = getAdjuntaDocumentoOficialiaBussines().descargarArchivo(documento);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        setArchivoDescarga((new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()),
                documento.getNombre())));
    }

    public void guardarDocumentosSolicitud() throws ArchivoNoGuardadoException {
        if (getListaDocumentos() != null && !getListaDocumentos().isEmpty()) {
            getAdjuntaDocumentoOficialiaBussines().guardarDocumentosSolicitud(getSolicitud(), getListaDocumentos(),
                    getListaGeneralDocumentos(), getUserProfile().getRfc(), documentoDto);
            getFlash().put(VistaConstantes.SOLICITUD, getSolicitud());
            getFlash().put("documentosCombo", getDocumentosCombo());
            getFlash().put("documentoDataModel", listaDocumentos);
            getFlash().put("datosBandejaDTO", getDbdto());

            ConfigurableNavigationHandler configurableNavigationHandler =
                    (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                            .getNavigationHandler();
            configurableNavigationHandler.performNavigation(UrlFirma.PAGINA_FIRMA_DOCUMENTO_OFICIALIA.toString());
        }
        else {
            getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, dbdto);
            getFlash().put("documentoDTO", documentoDto);
            ConfigurableNavigationHandler configurableNavigationHandler =
                    (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                            .getNavigationHandler();
            configurableNavigationHandler
                    .performNavigation(OficialiaConstantesController.ADJUNTAR_DOCTO_CUMPLIMENTACION_OFICIALIA
                            .toString());

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe ingresar al menos un documento"));
        }

    }

    public void eliminarDocumentos(ActionEvent event) {
        if (getRegistrosEliminar() != null && getRegistrosEliminar().length > 0) {
            for (DocumentoDTO documento : getRegistrosEliminar()) {
                getListaDocumentos().remove(documento);
                if (documento.getIdDocumentoSolicitud() != 0) {
                    getAdjuntaDocumentoOficialiaBussines().eliminaDocumento(documento.getIdDocumentoSolicitud());
                }
            }
            setEliminarVisible(false);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "", messages.getString("vuj.documento.eliminar")));
        }
    }

    public void prepararDocumentosAnexar() {
        listaGeneralDocumentos = new ArrayList<DocumentoDTO>();
        setDocumentosCombo(listaGeneralDocumentos);
        setDocumentosCombo(getAdjuntaDocumentoOficialiaBussines().obtenerDocumentosTramite(dbdto.getTipoTramite(),
                dbdto.getNumeroAsunto()));
        setDocumentoAdjuntarDataModel(new DocumentoDataModel(getListaDocumentos()));
    }

    public void cambioVisible() {
        if ((getId() != null)) {
            setAgregarVisible(true);
        }
        else {
            setAgregarVisible(false);
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

    public AdjuntaDocumentoOficialiaBusiness getAdjuntaDocumentoOficialiaBussines() {
        return adjuntaDocumentoOficialiaBussines;
    }

    public void
            setAdjuntaDocumentoOficialiaBussines(AdjuntaDocumentoOficialiaBusiness adjuntaDocumentoOficialiaBussines) {
        this.adjuntaDocumentoOficialiaBussines = adjuntaDocumentoOficialiaBussines;
    }

    public String getDocumentoSelected() {
        return documentoSelected;
    }

    public void setDocumentoSelected(String documentoSelected) {
        this.documentoSelected = documentoSelected;
    }

    public List<DocumentoDTO> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<DocumentoDTO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public List<DocumentoDTO> getDocumentosCombo() {
        return documentosCombo;
    }

    public void setDocumentosCombo(List<DocumentoDTO> documentosCombo) {
        this.documentosCombo = documentosCombo;
    }

    public DefaultStreamedContent getArchivoDescarga() {
        return archivoDescarga;
    }

    public void setArchivoDescarga(DefaultStreamedContent archivoDescarga) {
        this.archivoDescarga = archivoDescarga;
    }

    public List<DocumentoDTO> getListaGeneralDocumentos() {
        return listaGeneralDocumentos;
    }

    public void setListaGeneralDocumentos(List<DocumentoDTO> listaGeneralDocumentos) {
        this.listaGeneralDocumentos = listaGeneralDocumentos;
    }

    public DocumentoDTO[] getRegistrosEliminar() {
        return registrosEliminar != null ? (DocumentoDTO[]) registrosEliminar.clone() : null;
    }

    public void setRegistrosEliminar(DocumentoDTO[] regsEliminar) {
        if (regsEliminar != null) {
            this.registrosEliminar = regsEliminar.clone();
        }
        else {
            this.registrosEliminar = null;
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

    public DocumentoDataModel getDocumentoAdjuntarDataModel() {
        return documentoAdjuntarDataModel;
    }

    public void setDocumentoAdjuntarDataModel(DocumentoDataModel documentoAdjuntarDataModel) {
        this.documentoAdjuntarDataModel = documentoAdjuntarDataModel;
    }

    public DatosBandejaTareaDTO getDbdto() {
        return dbdto;
    }

    public void setDbdto(DatosBandejaTareaDTO dbdto) {
        this.dbdto = dbdto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public List getListaDocumentosAdjuntados() {
        return getListaDocumentos();
    }

    public ResourceBundle getMessages() {
        return messages;
    }

    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    public Long getFolio() {
        return folio;
    }

    public void setFolio(Long folio) {
        this.folio = folio;
    }

    public DocumentoDTO getDocumentoDto() {
        return documentoDto;
    }

    public void setDocumentoDto(DocumentoDTO documentoDto) {
        this.documentoDto = documentoDto;
    }

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

}
