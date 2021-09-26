/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dto.ObservacionDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.GenerarObservacionBussines;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.AbogadoConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.GeneralConstantes;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * Bean que implementan la l&oacute;gica de negocio para turnar la
 * solicitud
 * 
 * @author Softtek
 * 
 */

@ViewScoped
@ManagedBean(name = "generarObservacionController")
public class GenerarObservacionController extends BaseControllerBean {

    /**
     * 
     */

    @ManagedProperty(value = "#{generarObservacionBussines}")
    private GenerarObservacionBussines generarObservacionBussines;

    private static final long serialVersionUID = 6716291048521758856L;

    private String numAsunto;

    private String nombreFuncionario;

    private DatosBandejaTareaDTO datosBandejaTareaDTO;

    private ObservacionDTO observacion;

    private TramiteDTO tramite;

    /**
     * Constructor
     */
    public GenerarObservacionController() {

    }

    @PostConstruct
    public void iniciar() {

        DatosBandejaTareaDTO bandejaTareaDTO = (DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO);

        setDatosBandejaTareaDTO(bandejaTareaDTO);
        setObservacion(new ObservacionDTO());
        setTramite(getGenerarObservacionBussines().obtenerTramite(getDatosBandejaTareaDTO().getNumeroAsunto()));

    }

    /**
     * Metodo para regresar a la pagina anterior
     */
    public void anterior() {
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        configurableNavigationHandler.performNavigation(AbogadoConstantes.ATENDER_ASUNTO + "?faces-redirect=true");

    }

    /**
     * Metodo para observar y reasignar al Administrador
     */
    public void observar() {
        try {
            getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
            getObservacion().setNumeroAsunto(getDatosBandejaTareaDTO().getNumeroAsunto());

            setObservacion(getGenerarObservacionBussines().observar(getObservacion(),
                    getDatosBandejaTareaDTO().getIdTareaUsuario(), getUserProfile().getRfc()));

            StringBuffer url = new StringBuffer(LoginConstante.BANDEA_JSF);

            getFlash().put(GeneralConstantes.MENSAJE_REDIRECT, ("El asunto ha sido observado exitosamente."));

            ConfigurableNavigationHandler configurableNavigationHandler =
                    (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                            .getNavigationHandler();

            configurableNavigationHandler.performNavigation(url.toString() + "?faces-redirect=true");

        }
        catch (SolicitudNoGuardadaException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Error:", e.getMessage()));

        }

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
     * @return the observacion
     */
    public ObservacionDTO getObservacion() {
        return observacion;
    }

    /**
     * @param observacion
     *            the observacion to set
     */
    public void setObservacion(ObservacionDTO observacion) {
        this.observacion = observacion;
    }

    /**
     * @return the generarObservacionBussiness
     */
    public GenerarObservacionBussines getGenerarObservacionBussines() {
        return generarObservacionBussines;
    }

    /**
     * @param generarObservacionBussiness
     *            the generarObservacionBussiness to set
     */
    public void setGenerarObservacionBussines(GenerarObservacionBussines generarObservacionBussines) {
        this.generarObservacionBussines = generarObservacionBussines;
    }

    /**
     * @return the tramite
     */
    public TramiteDTO getTramite() {
        return tramite;
    }

    /**
     * @param tramite
     *            the tramite to set
     */
    public void setTramite(TramiteDTO tramite) {
        this.tramite = tramite;
    }

    /**
     * @return the nombreFuncionario
     */
    public String getNombreFuncionario() {
        return nombreFuncionario;
    }

    /**
     * @param nombreFuncionario
     *            the nombreFuncionario to set
     */
    public void setNombreFuncionario(String nombreFuncionario) {
        this.nombreFuncionario = nombreFuncionario;
    }

}
