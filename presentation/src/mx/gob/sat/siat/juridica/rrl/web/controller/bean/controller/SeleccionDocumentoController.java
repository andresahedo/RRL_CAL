/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.SeleccionDocumentosBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoDataModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean que implementan la l&oacute;gica de negocio para la
 * obtenci&oacute;n de los documentos de la solicitud
 * 
 * @author Softtek
 * 
 */
@ManagedBean(name = "seleccionDocumentoController")
@ViewScoped
public class SeleccionDocumentoController extends BaseControllerBean {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = 1L;
    /** DTO que representa los datos de una solicitud */
    private SolicitudDTO solicitud;
    /** Lista de documentos opcionales */
    private List<DocumentoDTO> listaDocumentosOpcionales = new ArrayList<DocumentoDTO>();
    /** Lista de documentos requeridos */
    private List<DocumentoDTO> listaDocumentos = new ArrayList<DocumentoDTO>();
    /** Data model de documentos opcionales */
    private DocumentoDataModel documentoDataModel;
    /** Documentos opcionales seleccionados */
    private DocumentoDTO[] documentosSeleccionados;
    /**
     * Bussines para la obtenci&oacute;n de los documentos de la
     * solicitud
     */
    @ManagedProperty(value = "#{seleccionDocumentosBussines}")
    private SeleccionDocumentosBussines seleccionDocumentosBussines;

    /**
     * Constructor
     */
    public SeleccionDocumentoController() {
        super();
    }

    /**
     * M&eacute;todo para preparar los documentos asociados a la
     * solicitud (opcionales y requeridos)
     */
    public void prepararDocumentosSolicitud() {
        setListaDocumentos(seleccionDocumentosBussines.listaDocObligatorios(Integer.valueOf(getSolicitud()
                .getTipoTramite())));
        setListaDocumentosOpcionales(seleccionDocumentosBussines.listaDocOpcionales(Integer.valueOf(getSolicitud()
                .getTipoTramite())));
        setDocumentoDataModel(new DocumentoDataModel(getListaDocumentosOpcionales()));
    }

    /**
     * M&eacute;todo para continuar con anexar documentos
     */
    public String anexarDocumentos() {
        List<DocumentoDTO> listaDoctos = new ArrayList<DocumentoDTO>();
        listaDoctos.addAll(getListaDocumentos());
        for (DocumentoDTO d : getDocumentosSeleccionados()) {
            listaDoctos.add(d);
        }

        getFlash().put("solicitud", getSolicitud());
        getFlash().put("documentosCombo", listaDoctos);
        return "anexarDocumentos.jsf";
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
    public void setListaDocumentosOpcionales(List<DocumentoDTO> listaDocumentosOpcionales) {
        this.listaDocumentosOpcionales = listaDocumentosOpcionales;
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
        }
        else {
            return null;
        }
    }

    /**
     * 
     * @param documentosSeleccionados
     *            a fijar
     */
    public void setDocumentosSeleccionados(DocumentoDTO[] documentosSeleccionadosArray) {
        if (documentosSeleccionadosArray != null) {
            DocumentoDTO[] documentosSeleccionadosLocal = documentosSeleccionadosArray.clone();
            DocumentoDTO[] arreglo = new DocumentoDTO[documentosSeleccionadosLocal.length];
            for (int i = 0; i < documentosSeleccionadosLocal.length; i++) {
                DocumentoDTO doc = documentosSeleccionadosLocal[i];
                arreglo[i] = doc;
            }
            this.documentosSeleccionados = arreglo;
        }
        else {
            this.documentosSeleccionados = null;
        }
    }

    /**
     * 
     * @return seleccionDocumentosBussines
     */
    public SeleccionDocumentosBussines getSeleccionDocumentosBussines() {
        return seleccionDocumentosBussines;
    }

    /**
     * 
     * @param seleccionDocumentosBussines
     *            a fijar
     */
    public void setSeleccionDocumentosBussines(SeleccionDocumentosBussines seleccionDocumentosBussines) {
        this.seleccionDocumentosBussines = seleccionDocumentosBussines;
    }

    /**
     * 
     * @return solicitud
     */
    public SolicitudDTO getSolicitud() {
        if (solicitud == null) {
            solicitud = (SolicitudDTO) getFlash().get("solicitud");
        }
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

}
