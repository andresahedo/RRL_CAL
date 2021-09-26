package mx.gob.sat.siat.juridica.cal.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.FirmarBusiness;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.business.AutorizarRequerimientoCALBussines;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionAbogadoDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import mx.gob.sat.siat.juridica.rrl.util.constante.RequerimientoConstante;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoOficialDataModel;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.RequerimientoDataModel;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "autorizarRequerimientoCALController")
@SessionScoped
public class AutorizarRequerimientoCALController extends BaseControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = 119140489821428951L;

    private RequerimientoDTO requerimiento;

    private DatosBandejaTareaDTO datosBandeja;

    private List<CatalogoDTO> listaTiposRequerimiento = new ArrayList<CatalogoDTO>();

    private List<CatalogoDTO> listaAutoridades = new ArrayList<CatalogoDTO>();

    private List<RequerimientoDTO> requerimientos = new ArrayList<RequerimientoDTO>();

    private List<DocumentoOficialDTO> listaDocumentos = new ArrayList<DocumentoOficialDTO>();

    private List<CatalogoDTO> listaAutorizadores = new ArrayList<CatalogoDTO>();

    private DocumentoOficialDataModel documentoOficialDataModel;

    private RequerimientoDataModel requerimientoDataModel;

    private DefaultStreamedContent archivoDescarga;

    private DocumentoOficialDTO[] registrosEliminar;

    private String firmaDigital;

    private FirmaDTO firma;

    private boolean eliminarVisible;

    private ResolucionAbogadoDTO resolucionAbogado;

    @ManagedProperty("#{firmarBusiness}")
    private transient FirmarBusiness firmarBusiness;

    @ManagedProperty("#{autorizarRequerimientoCALBussines}")
    private transient AutorizarRequerimientoCALBussines autorizarRequerimientoCALBussines;

    @PostConstruct
    public void iniciar() {
        setListaAutorizadores(new ArrayList<CatalogoDTO>());
        setListaTiposRequerimiento(new ArrayList<CatalogoDTO>());
        requerimientoDataModel = new RequerimientoDataModel(requerimientos);
        setListaTiposRequerimiento(new ArrayList<CatalogoDTO>());
        setListaAutoridades(new ArrayList<CatalogoDTO>());
        setDocumentoOficialDataModel(new DocumentoOficialDataModel(getListaDocumentos()));
    }

    /**
     * 
     */
    public void prepararFirmaSolicitud() {
        setFirma(new FirmaDTO(new Date()));
        getFirma().setCadenaOriginal(
                getAutorizarRequerimientoCALBussines().generaCadenaOriginal(getRequerimiento().getIdRequerimiento(),
                        getFirma().getFechaFirma()));
    }

    /**
     * 
     * @return
     */
    public String procesaFirma() {
        getLogger().debug(
                "firma: "
                        + FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                                .get("firmaDigital"));
        setFirmaDigital(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                .get("firmaDigital").toString());
        // Hacer algo con la firma
        getLogger().debug(getFirmaDigital());
        // regresar pagina de firmado correcto
        return "";
    }

    /**
     * 
     * @param datosBandeja
     */
    public void prepararAutorizarRequerimiento(DatosBandejaTareaDTO datosBandeja) {
        setRequerimiento(new RequerimientoDTO());
        setDatosBandeja(datosBandeja);
        setListaTiposRequerimiento(getAutorizarRequerimientoCALBussines().obtenerTiposDeRequerimiento());
        setListaAutoridades(getAutorizarRequerimientoCALBussines().obtenerAutoridades());
        if (datosBandeja.getIdRequerimiento() != null) {
            setRequerimiento(getAutorizarRequerimientoCALBussines().prepararAutorizarRequerimientoByIdRequerimiento(
                    getDatosBandeja().getIdRequerimiento(), getUserProfile()));
        }
        else {
            setRequerimiento(getAutorizarRequerimientoCALBussines().prepararAutorizarRequerimiento(
                    getDatosBandeja().getNumeroAsunto(), getUserProfile(), getDatosBandeja().getTipoTramite()));
        }
    }

    /**
     * 
     * @param actionEvent
     */
    public void prepararDocumentosRequerimientos(ActionEvent actionEvent) {
        setListaDocumentos(new ArrayList<DocumentoOficialDTO>());
        getListaDocumentos().addAll(
                getAutorizarRequerimientoCALBussines().obtenerDocumentos(datosBandeja.getIdRequerimiento()));
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        configurableNavigationHandler.performNavigation(RequerimientoConstante.ANEXAR_DOCS_AUTORIZAR_REQUERIMIENTO_RRL
                + "?faces-redirect=true");
    }

    /**
     * M&eacute;todo para eliminar documentos seleccionados.
     */
    public void eliminarDocumentos(ActionEvent event) {
        if (getRegistrosEliminar() != null && getRegistrosEliminar().length > 0) {
            for (DocumentoOficialDTO documento : getRegistrosEliminar()) {
                getListaDocumentos().remove(documento);
                if (documento.getIdDocumentoOficial() != 0) {
                    getAutorizarRequerimientoCALBussines().eliminaDocumentoOficial(documento.getIdDocumentoOficial());
                }
            }
            setEliminarVisible(false);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "", messages.getString("vuj.documento.eliminar")));
        }
    }

    /**
     * M&eacute;todo para descargar documento.
     */
    public void descargarDocumento(ActionEvent event) {
        DocumentoOficialDTO documento =
                (DocumentoOficialDTO) event.getComponent().getAttributes().get("documentoParametro");
        File file = new File(documento.getRuta());
        InputStream input = getAutorizarRequerimientoCALBussines().descargarArchivoOficial(documento);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        setArchivoDescarga((new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()),
                documento.getNombreArchivo())));
    }

    /**
     * M&eacute;todo para guardar los documentos en la
     * autorizaci&oacute;n del requerimiento.
     */

    public void anexarDocumentosRequerimientos(ActionEvent actionEvent) {
        if (getListaDocumentos() != null && getListaDocumentos().size() > 0) {
            getAutorizarRequerimientoCALBussines().guardarDocumentosAutorizarRequerimiento(getListaDocumentos(),
                    getRequerimiento());
            ConfigurableNavigationHandler configurableNavigationHandler =
                    (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                            .getNavigationHandler();

            configurableNavigationHandler.performNavigation(UrlFirma.PAGINA_FIRMA_REQUERIMIENTO
                    + "?faces-redirect=true");
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se ha adjuntado el oficio de requerimiento"));
        }
    }

    /**
     * M&eacute;todo para firmar/avanzar la autorizaci&oacute;n del
     * requerimiento.
     */
    public String firmarAutorizarRequerimiento() throws BusinessException {

        if (!getAutorizarRequerimientoCALBussines().validaFiel()) {
            throw new BusinessException("FirmaException", "el usuario no es el que esta logeado");
        }

        // Actualiza el requerimiento y genera la notificacion del
        // requerimiento
        getAutorizarRequerimientoCALBussines().firmarAutorizarRequerimiento(getRequerimiento(), getDatosBandeja(),
                getUserProfile().getRfc());

        setFirmaDigital(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                .get("firmaDigital").toString());

        firma.setSello(getFirmaDigital());
        firma.setRfcUsuario(getUserProfile().getRfc());
        firma.setCveRol(getUserProfile().getRol());
        firmarBusiness.guardarFirmaAutorizarRequerimiento(firma, getRequerimiento().getIdRequerimiento());

        StringBuffer msgRequerimeinto =
                new StringBuffer().append("El asunto ").append(getDatosBandeja().getNumeroAsunto())
                        .append(" ha sido autorizado");
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", msgRequerimeinto.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return LoginConstante.BANDEA_JSF;
    }

    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;

    public RequerimientoDTO getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(RequerimientoDTO requerimiento) {
        this.requerimiento = requerimiento;
    }

    public DatosBandejaTareaDTO getDatosBandeja() {
        return datosBandeja;
    }

    public void setDatosBandeja(DatosBandejaTareaDTO datosBandeja) {
        this.datosBandeja = datosBandeja;
    }

    public List<CatalogoDTO> getListaTiposRequerimiento() {
        return listaTiposRequerimiento;
    }

    public void setListaTiposRequerimiento(List<CatalogoDTO> listaTiposRequerimiento) {
        this.listaTiposRequerimiento = listaTiposRequerimiento;
    }

    public DocumentoOficialDataModel getDocumentoOficialDataModel() {
        return documentoOficialDataModel;
    }

    public void setDocumentoOficialDataModel(DocumentoOficialDataModel documentoOficialDataModel) {
        this.documentoOficialDataModel = documentoOficialDataModel;
    }

    public DefaultStreamedContent getArchivoDescarga() {
        return archivoDescarga;
    }

    public void setArchivoDescarga(DefaultStreamedContent archivoDescarga) {
        this.archivoDescarga = archivoDescarga;
    }

    public DocumentoOficialDTO[] getRegistrosEliminar() {
        return registrosEliminar != null ? (DocumentoOficialDTO[]) registrosEliminar.clone() : null;
    }

    public void setRegistrosEliminar(DocumentoOficialDTO[] registrosEliminarArray) {
        if (registrosEliminarArray != null) {
            this.registrosEliminar = registrosEliminarArray.clone();
        }
        else {
            this.registrosEliminar = null;
        }
    }

    public String getFirmaDigital() {
        return firmaDigital;
    }

    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }

    public FirmaDTO getFirma() {
        return firma;
    }

    public void setFirma(FirmaDTO firma) {
        this.firma = firma;
    }

    public FirmarBusiness getFirmarBusiness() {
        return firmarBusiness;
    }

    public void setFirmarBusiness(FirmarBusiness firmarBusiness) {
        this.firmarBusiness = firmarBusiness;
    }

    public List<CatalogoDTO> getListaAutoridades() {
        return listaAutoridades;
    }

    public void setListaAutoridades(List<CatalogoDTO> listaAutoridades) {
        this.listaAutoridades = listaAutoridades;
    }

    public List<DocumentoOficialDTO> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<DocumentoOficialDTO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public ResourceBundle getMessages() {
        return messages;
    }

    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    public ResolucionAbogadoDTO getResolucionAbogado() {
        return resolucionAbogado;
    }

    public void setResolucionAbogado(ResolucionAbogadoDTO resolucionAbogado) {
        this.resolucionAbogado = resolucionAbogado;
    }

    /**
     * @return the autorizarRequerimientoCALBussines
     */
    public AutorizarRequerimientoCALBussines getAutorizarRequerimientoCALBussines() {
        return autorizarRequerimientoCALBussines;
    }

    /**
     * @param autorizarRequerimientoCALBussines
     *            the autorizarRequerimientoCALBussines to set
     */
    public void
            setAutorizarRequerimientoCALBussines(AutorizarRequerimientoCALBussines autorizarRequerimientoCALBussines) {
        this.autorizarRequerimientoCALBussines = autorizarRequerimientoCALBussines;
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

    public RequerimientoDataModel getRequerimientoDataModel() {
        return requerimientoDataModel;
    }

    public void setRequerimientoDataModel(RequerimientoDataModel requerimientoDataModel) {
        this.requerimientoDataModel = requerimientoDataModel;
    }

    public List<RequerimientoDTO> getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(List<RequerimientoDTO> requerimientos) {
        this.requerimientos = requerimientos;
    }

    public List<CatalogoDTO> getListaAutorizadores() {
        return listaAutorizadores;
    }

    public void setListaAutorizadores(List<CatalogoDTO> listaAutorizadores) {
        this.listaAutorizadores = listaAutorizadores;
    }

}
