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
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.bpm.excepcion.BPMException;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.RemisionDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionAbogadoDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.GeneralConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;
import mx.gob.sat.siat.juridica.rrl.util.exception.RemitirAsuntoException;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.RemitirRecursoRevocacionBussines;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Bean que implementan la l&oacute;gica de negocio para turnar la
 * solicitud
 * 
 * @author Softtek
 * 
 */

@ViewScoped
@ManagedBean(name = "remitirRecursoRevocacionController")
public class RemitirRecursoRevocacionController extends BaseControllerBean {

    /**
     * 
     */

    @ManagedProperty(value = "#{remitirRecursoRevocacionBussines}")
    private RemitirRecursoRevocacionBussines remitirRecursoRevocacionBussines;

    /** Bean para obtener los mensajes de error */
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    private static final long serialVersionUID = 6716291048521758856L;

    private List<CatalogoDTO> listaUnidadesEmisoras = new ArrayList<CatalogoDTO>();

    private List<CatalogoDTO> administradoresAsignar = new ArrayList<CatalogoDTO>();

    private RemisionDTO datosRemision = new RemisionDTO();

    private String numAsunto;

    private String rfcAsignar;

    private DatosBandejaTareaDTO datosBandejaTareaDTO;

    private TramiteDTO tramiteDto;

    private String nombreFuncionario;

    private ResolucionAbogadoDTO abogadoDTO = new ResolucionAbogadoDTO();

    private String ideTareaOrigen;

    private boolean nuevoDocumento;

    /**
     * Constructor
     */
    public RemitirRecursoRevocacionController() {

    }

    @PostConstruct
    public void iniciar() {
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));
        DatosBandejaTareaDTO bandejaTareaDTO = (DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO);
        String tareaOrigenFlash = (String) getFlash().get(VistaConstantes.TAREA_ORIGEN);
        getLogger().debug("bandejaTareaDTO {}", bandejaTareaDTO);
        getLogger().debug("tareaOrigen {}", tareaOrigenFlash);
        setDatosBandejaTareaDTO(bandejaTareaDTO);
        setIdeTareaOrigen(getRemitirRecursoRevocacionBussines().getDefaultIdeTareaOrigen()); 
                                                                                             // Asume
                                                                                             // la
                                                                                             // remision
                                                                                             // desde
                                                                                             // la
                                                                                             // tarea
                                                                                             // de
                                                                                             // Atender
                                                                                             // asunto
        if (tareaOrigenFlash != null && !tareaOrigenFlash.isEmpty()) {
            setIdeTareaOrigen(tareaOrigenFlash);
        }
        setListaUnidadesEmisoras(getRemitirRecursoRevocacionBussines().obtenerAutoridadesEmisoras());
        setAdministradoresAsignar(getRemitirRecursoRevocacionBussines().obtenerPersonasAsignar(
                getDatosBandejaTareaDTO().getNumeroAsunto()));
        setTramiteDTO(remitirRecursoRevocacionBussines.obtenerTramite(getDatosBandejaTareaDTO().getNumeroAsunto()));
    }

    public void regresarPagina() {
        nuevoDocumento = false;

    }

    /**
     * M&eacute;todo para guardar las resoluciones y agravios.
     */
    public void siguienteValidacion() {
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        List<DocumentoDTO> documents =
                remitirRecursoRevocacionBussines.obtenerDocumentosComplementarios(getDatosBandejaTareaDTO()
                        .getIdSolicitud());
        if (documents == null || documents.isEmpty()) {

            setNuevoDocumento(false);

            remitir();

        }
        else {
            setNuevoDocumento(true);

        }
    }

    /**
     * Metodo para remitir y reasignar al Administrador
     */
    public void remitir() {
        try {
            getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
            getFlash().put(VistaConstantes.TAREA_ORIGEN, getIdeTareaOrigen());

            getRemitirRecursoRevocacionBussines().remitir(getDatosBandejaTareaDTO().getNumeroAsunto(),
                    getDatosRemision().getIdAtuoridadEmisora(), getDatosBandejaTareaDTO().getIdTareaUsuario(),
                    getRfcAsignar(), getUserProfile().getRfc(), getIdeTareaOrigen());

            getRemitirRecursoRevocacionBussines().envioCorreoAsignarTarea(getDatosBandejaTareaDTO().getNumeroAsunto(),
                    getRfcAsignar(), "Autorizar Remisi&oacute;n");
            StringBuffer url = new StringBuffer(LoginConstante.BANDEA_JSF);

            getFlash().put(
                    GeneralConstantes.MENSAJE_REDIRECT,
                    ("La Remisi\u00F3n del asunto ") + getDatosBandejaTareaDTO().getNumeroAsunto()
                            + " ha sido enviado para su autorizaci\u00F3n");

            ConfigurableNavigationHandler configurableNavigationHandler =
                    (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                            .getNavigationHandler();

            configurableNavigationHandler.performNavigation(url.toString() + "?faces-redirect=true");

        }
        catch (BPMException e) {
            setNuevoDocumento(false);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, null, e.getMessage()));

        }
        catch (RemitirAsuntoException rae) {
            setNuevoDocumento(false);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", errorMsg
                            .getString("vuj.remision.mismaUnidad")));
        }

    }

    public void cargaDatos() {
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());

        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();
        if (getDatosBandejaTareaDTO().getTipoTramite().equals(DiscriminadorConstants.T1_CLASIFICACION_ARANCELARIA)) {
            configurableNavigationHandler
                    .performNavigation("/resources/pages/abogado/abogadoCaptura.jsf?faces-redirect=true");
        }
        else if (getDatosBandejaTareaDTO().getNombreTarea().equals("Turnar asunto")) {
            configurableNavigationHandler
                    .performNavigation("/resources/pages/cal/seguimiento/turnarAsunto.jsf?faces-redirect=true");
        }
        else {
            configurableNavigationHandler
                    .performNavigation("/resources/pages/cal/atencion/atenderPromocion.jsf?faces-redirect=true");
        }
    }

    public List<CatalogoDTO> getListaUnidadesEmisoras() {
        return listaUnidadesEmisoras;
    }

    public void setListaUnidadesEmisoras(List<CatalogoDTO> listaUnidadesEmisoras) {
        this.listaUnidadesEmisoras = listaUnidadesEmisoras;
    }

    public RemitirRecursoRevocacionBussines getRemitirRecursoRevocacionBussines() {
        return remitirRecursoRevocacionBussines;
    }

    public void setRemitirRecursoRevocacionBussines(RemitirRecursoRevocacionBussines remitirRecursoRevocacionBussines) {
        this.remitirRecursoRevocacionBussines = remitirRecursoRevocacionBussines;
    }

    /**
     * @return the datosRemision
     */
    public RemisionDTO getDatosRemision() {
        return datosRemision;
    }

    /**
     * @param datosRemision
     *            the datosRemision to set
     */
    public void setDatosRemision(RemisionDTO datosRemision) {
        this.datosRemision = datosRemision;
    }

    /**
     * @return the numAsunto
     */
    public String getNumAsunto() {
        return numAsunto;
    }

    /**
     * @param numAsunto
     *            the numAsunto to set
     */
    public void setNumAsunto(String numAsunto) {
        this.numAsunto = numAsunto;
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
     * @return the rfcAsignar
     */
    public String getRfcAsignar() {
        return rfcAsignar;
    }

    /**
     * @param rfcAsignar
     *            the rfcAsignar to set
     */
    public void setRfcAsignar(String rfcAsignar) {
        this.rfcAsignar = rfcAsignar;
    }

    /**
     * @return the administradoresAsignar
     */
    public List<CatalogoDTO> getAdministradoresAsignar() {
        return administradoresAsignar;
    }

    /**
     * @param administradoresAsignar
     *            the administradoresAsignar to set
     */
    public void setAdministradoresAsignar(List<CatalogoDTO> administradoresAsignar) {
        this.administradoresAsignar = administradoresAsignar;
    }

    /**
     * 
     * @return nombreFuncionario
     */
    public String getNombreFuncionario() {
        return nombreFuncionario;
    }

    /**
     * 
     * @param nombreFuncionario
     *            el nombreFuncionario a fijar.
     */
    public void setNombreFuncionario(String nombreFuncionario) {
        this.nombreFuncionario = nombreFuncionario;
    }

    /**
     * 
     * @return abogadoDTO
     */
    public ResolucionAbogadoDTO getAbogadoDTO() {
        return abogadoDTO;
    }

    /**
     * 
     * @param abogadoDTO
     *            el abogadoDTO a fijar.
     */
    public void setAbogadoDTO(ResolucionAbogadoDTO abogadoDTO) {
        this.abogadoDTO = abogadoDTO;
    }

    public TramiteDTO getTramiteDTO() {
        return tramiteDto;
    }

    public void setTramiteDTO(TramiteDTO tramiteDto) {
        this.tramiteDto = tramiteDto;
    }

    public String getIdeTareaOrigen() {
        return ideTareaOrigen;
    }

    public void setIdeTareaOrigen(String ideTareaOrigen) {
        this.ideTareaOrigen = ideTareaOrigen;
    }

    /**
     * @return the nuevoDocumento
     */
    public boolean isNuevoDocumento() {
        return nuevoDocumento;
    }

    /**
     * @param nuevoDocumento
     *            the nuevoDocumento to set
     */
    public void setNuevoDocumento(boolean nuevoDocumento) {
        this.nuevoDocumento = nuevoDocumento;
    }

}
