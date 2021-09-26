/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.controller.bean.controller;

import java.io.IOException;
import java.io.InputStream;
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
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.util.exception.BaseBussinessException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AnexarDocumentoBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.CapturaSolicitudBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoDataModel;

/**
 * Controller que implementa la logica de negocio para anexar
 * documentos de consultas.
 * 
 * @author softtek
 * 
 */
@ManagedBean(name = "consultasAnexarDocumentosController")
@ViewScoped
public class ConsultasAnexarDocumentosController extends BaseCloudController<DocumentoDTO> {
    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 4769072518068579282L;
    
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    /**
     * Propiedad que representa un objeto tipo DocumentoDataModel.
     */
    private DocumentoDataModel documentoDataModel;
    /**
     * Arreglo de registros a ser eliminados.
     */
    private DocumentoDTO[] registrosEliminar;
    /**
     * Bussines que implementa la logica de neocio para anexar
     * documentos.
     */

    private boolean eliminarVisible;

    @ManagedProperty(value = "#{capturaSolicitudBussines}")
    private CapturaSolicitudBussines capturaSolicitudBussines;

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

    /** DTO que representa lo firma de la solicitud */
    private FirmaDTO firma;

    @ManagedProperty(value = "#{anexarDocumentoBussines}")
    private AnexarDocumentoBussines anexarDocumentoBussines;
    /**
     * Propiedad que representa el archivo a descargar.
     */
    private DefaultStreamedContent archivoDescarga;
    /**
     * Lista de documentos
     */
    private List<DocumentoDTO> listaDocumentos = new ArrayList<DocumentoDTO>();
    /**
     * Clave de identificacion del documento.
     */
    private String idTipoDocumento = "";
    /**
     * Propiedad que representa los datos de la solicitud.
     */
    private DatosSolicitudDTO datosSolicitud = new DatosSolicitudDTO();
    /**
     * Clave que identifica la solicitud.
     */
    private Long idSolicitud;
    /**
     * Propiedad que representa el numero de asunto.
     */
    private String numAsunto;

    private String cadenaOriginal;
    
    public static final String TIPO_DOC = "idTipoDocumento";
    public static final String DATOS_SOL = "datosSolicitud";
    public static final String NUM_ASUNTO = "numAsunto";
    public static final String ID_SOL = "idSolicitud";

    /**
     * Metodo para descargar el documento seleccionado.
     * 
     * @param event
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
     * Metodo para eliminar documentos seleccionados de la lista de
     * documentos.
     * 
     * @param event
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
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El (Los) documento(s) ha(n) sido eliminado(s)"));
        }

    }

    /**
     * Metodo que se ejecuta despues de la carga de la JSF de anexado
     * de documentos e inicializa datos de una solicitud.
     */
    @PostConstruct
    public void iniciar() {
        setDocumentoDataModel(new DocumentoDataModel(getListaDocumentos()));

        setIdTipoDocumento((String) getFlash().get(TIPO_DOC));
        setDatosSolicitud((DatosSolicitudDTO) getFlash().get(DATOS_SOL));
        setNumAsunto((String) getFlash().get(NUM_ASUNTO));
        setIdSolicitud((Long) getFlash().get(ID_SOL));
        getFlash().remove("idSolicitud");
    }

    /**
     * Metodo para preparar firma de solicitud.
     */
    public void prepararFirmaSolicitud() {
        setFirma(new FirmaDTO(new Date()));
        setCadenaOriginal(getAnexarDocumentoBussines().generaCadenaOriginal(getDatosSolicitud().getIdSolicitud(),
                getFirma().getFechaFirma()));
    }

    /**
     * Metodo para guardar documentos anexados.
     * 
     * @return
     */
    public void guardarDocumentos() {
        String siguiente = UrlFirma.FIRMA_CONSULTAS.toString();

        try {
            getFlash().put(TIPO_DOC, getIdTipoDocumento());
            getFlash().put(DATOS_SOL, getDatosSolicitud());
            getFlash().put(NUM_ASUNTO, getNumAsunto());
            getFlash().put(ID_SOL, getIdSolicitud());
            DatosBandejaTareaDTO datosBandeja = new DatosBandejaTareaDTO();
            datosBandeja.setIdSolicitud(idSolicitud);
            datosBandeja.setNumeroAsunto(numAsunto);
            getFlash().put("datosBandejaDTO", datosBandeja);

            if (getListaDocumentos() != null && !getListaDocumentos().isEmpty()) {
                getAnexarDocumentoBussines().guardarDocumentosConsulta(getIdSolicitud(), getListaDocumentos(),
                        DiscriminadorConstants.T1_CLASIFICACION_ARANCELARIA, datosSolicitud.getRfcContribuyente());
                prepararFirmaSolicitud();
                getFlash().put("cadenaOriginal", getCadenaOriginal());
            }
            else {
                throw new BaseBussinessException("documentos vacio");
            }
        }
        catch (BaseBussinessException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Debe agregar documentos"));
            return;

        }
        getFlash().put(TIPO_DOC, getIdTipoDocumento());
        getFlash().put(DATOS_SOL, getDatosSolicitud());
        getFlash().put(ID_SOL, getIdSolicitud());
        getFlash().put(NUM_ASUNTO, getNumAsunto());

        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        configurableNavigationHandler.performNavigation(siguiente + "?faces-redirect=true");
    }

    /**
     * M&eacute;todo para adjuntar un documento
     */
    
    public void anexarDocumentoNube() {
        getFlash().put(TIPO_DOC, getIdTipoDocumento());
        getFlash().put(DATOS_SOL, getDatosSolicitud());
        getFlash().put(NUM_ASUNTO, getNumAsunto());
        getFlash().put(ID_SOL, getIdSolicitud());
        DatosBandejaTareaDTO datosBandeja = new DatosBandejaTareaDTO();
        datosBandeja.setIdSolicitud(idSolicitud);
        datosBandeja.setNumeroAsunto(numAsunto);
        getFlash().put("datosBandejaDTO", datosBandeja);
        String[] ids = getIdTipoDocumento().split(",");
        if (ids != null && ids.length > 0) {
            DocumentoDTO documento = obtenerDocumento(getIdTipoDocumento());
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
    }

    @Override
    public BaseCloudBusiness getBusinessBean() {
        return anexarDocumentoBussines;
    }

    /**
     * 
     * @return documentoDataModel.
     */
    public DocumentoDataModel getDocumentoDataModel() {
        return documentoDataModel;
    }

    /**
     * 
     * @param documentoDataModel
     *            el documentoDataModel a fijar.
     */
    public void setDocumentoDataModel(DocumentoDataModel documentoDataModel) {
        this.documentoDataModel = documentoDataModel;
    }

    /**
     * 
     * @return arreglo de DocumentoDTO a ser eliminados.
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

    /**
     * 
     * @return anexarDocumentosBussines.
     */
    public AnexarDocumentoBussines getAnexarDocumentoBussines() {
        return anexarDocumentoBussines;
    }

    /**
     * 
     * @param anexarDocumentoBussines
     *            a ser fijados.
     */
    public void setAnexarDocumentoBussines(AnexarDocumentoBussines anexarDocumentoBussines) {
        this.anexarDocumentoBussines = anexarDocumentoBussines;
    }

    /**
     * 
     * @return archivoDescarga.
     */
    public DefaultStreamedContent getArchivoDescarga() {
        return archivoDescarga;
    }

    /**
     * 
     * @param archivoDescarga
     *            a ser fijado.
     */
    public void setArchivoDescarga(DefaultStreamedContent archivoDescarga) {
        this.archivoDescarga = archivoDescarga;
    }

    /**
     * 
     * @return listaDocumentos.
     */
    public List<DocumentoDTO> getListaDocumentos() {
        return listaDocumentos;
    }

    /**
     * 
     * @param listaDocumentosOficiales
     *            a ser fijados.
     */
    public void setListaDocumentos(List<DocumentoDTO> listaDocumentosOficiales) {
        this.listaDocumentos = listaDocumentosOficiales;
    }

    /**
     * 
     * @return idTipoDocumento.
     */
    public String getIdTipoDocumento() {
        return idTipoDocumento;
    }

    /**
     * 
     * @param idTipoDocumento
     *            .
     */
    public void setIdTipoDocumento(String idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    /**
     * 
     * @return datosSolicitud.
     */
    public DatosSolicitudDTO getDatosSolicitud() {
        return datosSolicitud;
    }

    /**
     * 
     * @param datosSolicitud
     *            los datosSolicitud a ser fijados.
     */
    public void setDatosSolicitud(DatosSolicitudDTO datosSolicitud) {
        this.datosSolicitud = datosSolicitud;
    }

    /**
     * 
     * @return idSolicitud.
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * 
     * @param idSolicitud
     *            la idSolicitud a ser fijada.
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * 
     * @return numAsunto.
     */
    public String getNumAsunto() {
        return numAsunto;
    }

    /**
     * 
     * @param numAsunto
     *            el numAsunto a ser fijado.
     */
    public void setNumAsunto(String numAsunto) {
        this.numAsunto = numAsunto;
    }

    public void rowSelectCheckbox(SelectEvent event) {
        setEliminarVisible(true);

    }

    public void rowUnselectCheckbox(UnselectEvent event) {
        if (getRegistrosEliminar().length < 1) {
            setEliminarVisible(false);
        }
    }

    public boolean isEliminarVisible() {
        return eliminarVisible;
    }

    public void setEliminarVisible(boolean eliminarVisible) {
        this.eliminarVisible = eliminarVisible;
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

    @Override
    public List<DocumentoDTO> getListaDocumentosAdjuntados() {
        return getListaDocumentos();
    }

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

}
