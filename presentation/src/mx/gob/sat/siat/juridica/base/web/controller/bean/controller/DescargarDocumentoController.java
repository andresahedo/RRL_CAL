package mx.gob.sat.siat.juridica.base.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AnexarDocumentoBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoOficialDataModel;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("rawtypes")
@ManagedBean(name = "descargarDocumentoController")
@SessionScoped
public class DescargarDocumentoController extends BaseCloudController {

    private static final long serialVersionUID = 1L;
    private List<DocumentoOficialDTO> listaDocumentosOficiales = new ArrayList<DocumentoOficialDTO>();
    private DocumentoOficialDataModel documentoDataModel;
    private transient DefaultStreamedContent archivoDescarga;
    private DatosBandejaTareaDTO datosBandejaTareaDTO = new DatosBandejaTareaDTO();
    private String cveTipoDoc;
    private String descripcionTipoDoc;
    private String messagesRedirect;
    @ManagedProperty(value = "#{anexarDocumentoBussines}")
    private AnexarDocumentoBussines anexarDocumentoBussines; 

    public List<DocumentoOficialDTO> getListaDocumentosOficiales() {
        return listaDocumentosOficiales;
    }

    public void setListaDocumentosOficiales(List<DocumentoOficialDTO> listaDocumentosOficiales) {
        this.listaDocumentosOficiales = listaDocumentosOficiales;
    }

    public DocumentoOficialDataModel getDocumentoDataModel() {
        return documentoDataModel;
    }

    public void setDocumentoDataModel(DocumentoOficialDataModel documentoDataModel) {
        this.documentoDataModel = documentoDataModel;
    }

    public DefaultStreamedContent getArchivoDescarga() {
        return archivoDescarga;
    }

    public void setArchivoDescarga(DefaultStreamedContent archivoDescarga) {
        this.archivoDescarga = archivoDescarga;
    }

    public DatosBandejaTareaDTO getDatosBandejaTareaDTO() {
        return datosBandejaTareaDTO;
    }

    public void setDatosBandejaTareaDTO(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        this.datosBandejaTareaDTO = datosBandejaTareaDTO;
    }

    public String getCveTipoDoc() {
        return cveTipoDoc;
    }

    public void setCveTipoDoc(String cveTipoDoc) {
        this.cveTipoDoc = cveTipoDoc;
    }

    public AnexarDocumentoBussines getAnexarDocumentoBussines() {
        return anexarDocumentoBussines;
    }

    public void setAnexarDocumentoBussines(AnexarDocumentoBussines anexarDocumentoBussines) {
        this.anexarDocumentoBussines = anexarDocumentoBussines;
    }

    public String getDescripcionTipoDoc() {
        return descripcionTipoDoc;
    }

    public void setDescripcionTipoDoc(String descripcionTipoDoc) {
        this.descripcionTipoDoc = descripcionTipoDoc;
    }

    public String getMessagesRedirect() {
        return messagesRedirect;
    }

    public void setMessagesRedirect(String messagesRedirect) {
        this.messagesRedirect = messagesRedirect;
    }

    public void obtenerDocumentos() {
        setListaDocumentosOficiales(getAnexarDocumentoBussines().obtenerDocumentos(
                getDatosBandejaTareaDTO().getNumeroAsunto()));
        setDocumentoDataModel(new DocumentoOficialDataModel(getListaDocumentosOficiales()));
    }

    public void obtenerDocumentosByIdDoc(List<Long> idsDoc) {
        setListaDocumentosOficiales(getAnexarDocumentoBussines().obtenerDocumentosByIdDoc(idsDoc));
        setDocumentoDataModel(new DocumentoOficialDataModel(getListaDocumentosOficiales()));
    }

    public DocumentoOficialDataModel getDocumentosConsulta() {
        if (getListaDocumentosOficiales() == null || getListaDocumentosOficiales().isEmpty()) {
            setListaDocumentosOficiales(getAnexarDocumentoBussines().obtenerDocumentos(
                    getDatosBandejaTareaDTO().getNumeroAsunto()));
        }
        return new DocumentoOficialDataModel(getListaDocumentosOficiales());
    }

    public void descargarDocumento(ActionEvent event) throws IOException {
        DocumentoOficialDTO documento =
                (DocumentoOficialDTO) event.getComponent().getAttributes().get("documentoParametro");

        InputStream input = getAnexarDocumentoBussines().descargarDocumentoOficial(documento);

        HttpServletResponse response =
                (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=" + documento.getNombreArchivo());
        IOUtils.copy(input, response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
        FacesContext.getCurrentInstance().responseComplete();

    }

    public void descargarDocumentoReq(ActionEvent event) throws IOException {
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

    public void anexarDocumentoOficial(FileUploadEvent event) throws ArchivoNoGuardadoException {
        try {
            File file = new File(event.getFile().getFileName());
            String nombreArchivo = file.getName();
            DocumentoOficialDTO documento = new DocumentoOficialDTO();
            documento.setCveTipoDocumento(cveTipoDoc);
            documento.setNombreArchivo(nombreArchivo);
            documento.setFechaCreacion(new Date());
            documento.setDescripcionTipoDocumento(descripcionTipoDoc);
            documento.setFile(event.getFile().getInputstream());
            documento =
                    getAnexarDocumentoBussines().adjuntarDocumentoOficial(documento, getListaDocumentosOficiales(),
                            getDatosBandejaTareaDTO().getNumeroAsunto());
            // guardar archivo en filesystem
            FacesMessage msg = new FacesMessage("Anexar", "Documento adjuntado correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            getListaDocumentosOficiales().add(documento);
            setDocumentoDataModel(new DocumentoOficialDataModel(getListaDocumentosOficiales()));
        }
        catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Error:", e.getMessage()));
        }
    }

    public void mensajesRedirect() {
        if (getMessagesRedirect() != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "", getMessagesRedirect()));
            setMessagesRedirect(null);
        }
    }

    public void inicializar(DatosBandejaTareaDTO datosBandeja) {
        setDatosBandejaTareaDTO(datosBandeja);
        obtenerDocumentos();
    }

    @Override
    public BaseCloudBusiness getBusinessBean() {
        return anexarDocumentoBussines;
    }

    @Override
    public List getListaDocumentosAdjuntados() {
        return null;
    }

}
