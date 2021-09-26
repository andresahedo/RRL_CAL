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

import java.text.MessageFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.sat.sgi.SgiCripto.SgiARAException;
import mx.gob.sat.siat.juridica.ara.constantes.AraConstantes;
import mx.gob.sat.siat.juridica.ara.util.AraValidadorHelper;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.ObservacionDTO;
import mx.gob.sat.siat.juridica.base.dto.PersonaSolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.AtenderObservacionBussines;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;

/**
 * Bean que implementan la l&oacute;gica de negocio para atender una observacion
 * 
 * @author Softtek
 * 
 */
@ManagedBean
@ViewScoped
public abstract class AtenderObservacionController<S extends SolicitudDTO> extends BaseControllerBean {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ManagedProperty(value = "#{datosBandejaTareaDTO}")
    private DatosBandejaTareaDTO datosBandejaTareaDTO;

    @ManagedProperty(value = "#{atenderObservacionBussines}")
    private AtenderObservacionBussines atenderObservacionBussines;

    @ManagedProperty(value = "#{araValidadorHelper}")
    private AraValidadorHelper araValidadorHelper;

    /** DTO que representa lo firma de la solicitud */
    private FirmaDTO firma;

    private String firmaDigital;

    private String userID;

    private ObservacionDTO observacion = new ObservacionDTO();

    private String nombreFuncionario;

    private TramiteDTO tramite;

    private boolean banderaRazonSocial;

    private PersonaSolicitudDTO solicitante;

    /**
     * Controller para manejar la operacion de atender requerimiento en pantalla.
     */
    public AtenderObservacionController() {
    }

    /**
     * M&eacute;todo para preparar la cadena original de la firma de la solicitud
     */

    public void prepararFirmaSolicitud() {
        setFirma(new FirmaDTO(new Date()));
        getFirma().setCadenaOriginal(getAtenderObservacionBussines()
                .generaCadenaOriginal(getDatosBandejaTareaDTO().getIdSolicitud(), getFirma().getFechaFirma()));
    }

    /**
     * Metodo para preparar y atender el requerimiento.
     * 
     * @param datosBandeja
     */
    @PostConstruct
    public void iniciar() {

        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));
        setFirma((FirmaDTO) getFlash().get("firma"));
        setObservacion(
                getAtenderObservacionBussines().obternerObservacion(getDatosBandejaTareaDTO().getNumeroAsunto()));
        setSolicitud(obtenerSolicitud());
        if (getSolicitud() != null && getSolicitud().getSolicitante() != null) {
            setBanderaRazonSocial(getSolicitud().getSolicitante().getRazonSocial() != null);
        }

    }

    protected abstract S obtenerSolicitud();

    /**
     * Metodo para siguiente.
     * 
     * @return
     * @throws SolicitudNoGuardadaException
     * @throws Exception
     */
    public void siguiente() {
        try {
            guardar();
        } catch (SolicitudNoGuardadaException e) {
            logger.error("", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
            return;
        }
        prepararFirmaSolicitud();

        ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext
                .getCurrentInstance().getApplication().getNavigationHandler();
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        getFlash().put("firma", getFirma());
        configurableNavigationHandler.performNavigation(getDireccionFirma() + "?faces-redirect=true");

    }

    /**
     * Metodo para guardado.
     * 
     * @return
     * @throws SolicitudNoGuardadaException
     * @throws Exception
     */
    public abstract void guardar() throws SolicitudNoGuardadaException;

    /**
     * Metodo para la firma.
     * 
     * @return
     * @throws Exception
     */
    public String firmar() {
        String sNumSerie;
        String urlforward = null;

        setUserID(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userID")
                .toString());

        try {
            if (getUserID().equalsIgnoreCase(getUserProfile().getRfc())) {
                sNumSerie = getAraValidadorHelper().getNumSerie(FacesContext.getCurrentInstance().getExternalContext()
                        .getRequestParameterMap().get("numeroSerie"));

                if (getAraValidadorHelper().validarRfcNumSerie(sNumSerie, getUserProfile().getRfc())
                        && getAraValidadorHelper().getEdoCertificado(sNumSerie)) {
                    setFirmaDigital(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                            .get("firmaDigital").toString());

                    getFirma().setSello(getFirmaDigital());

                    getFirma().setRfcUsuario(getUserProfile().getRfc());
                    getFirma().setCveRol(getUserProfile().getRol());
                    getFirma().setCertificado(sNumSerie);

                    getAtenderObservacionBussines().firmaAtender(getDatosBandejaTareaDTO().getNumeroAsunto(),
                            getObservacion().getIdObservacion(), this.getUserProfile().getRfc(), getFirma(),
                            getDatosBandejaTareaDTO().getIdTareaUsuario().toString());

                    FacesMessage msg = new FacesMessage("",
                            "Se han registrado los cambios de la solicitud "
                                    + getDatosBandejaTareaDTO().getNumeroAsunto()
                                    + ". La solicitud se ha asignado al Abogado.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    urlforward = LoginConstante.BANDEA_JSF;

                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error:",
                        MessageFormat.format(AraConstantes.MENSAJE_LLAVE_SERIE_INCORRECTO, getUserProfile().getRfc())));
            }
        } catch (SolicitudNoGuardadaException ex) {
            getLogger().error("Ocurrio un error AtenderObservacionController.firmar {0}", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Su observacion no pudo ser firmada."));
        } catch (SgiARAException ex) {
            getLogger().error("Ocurrio un error al firmar AtenderObservacionController.firmar {0}", ex);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } catch (Exception ex) {
            getLogger().error("Ocurrio un error AtenderObservacionController.firmar {0}", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurri\u00F3 un error al firmar.", ""));
        }

        return urlforward;
    }

    /**
     * 
     * @return datosBandejaTareaDTO
     */
    public DatosBandejaTareaDTO getDatosBandejaTareaDTO() {
        return datosBandejaTareaDTO;
    }

    /**
     * 
     * @param datosBandejaTareaDTO los datosBandejaTareaDTO a fijar.
     */
    public void setDatosBandejaTareaDTO(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        this.datosBandejaTareaDTO = datosBandejaTareaDTO;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the firmaDigital
     */
    public String getFirmaDigital() {
        return firmaDigital;
    }

    /**
     * @param firmaDigital the firmaDigital to set
     */
    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }

    /**
     * @return the firma
     */
    public FirmaDTO getFirma() {
        return firma;
    }

    /**
     * @param firma the firma to set
     */
    public void setFirma(FirmaDTO firma) {
        this.firma = firma;
    }

    /**
     * @return the observacion
     */
    public ObservacionDTO getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(ObservacionDTO observacion) {
        this.observacion = observacion;
    }

    /**
     * @return the nombreFuncionario
     */
    public String getNombreFuncionario() {
        return nombreFuncionario;
    }

    /**
     * @param nombreFuncionario the nombreFuncionario to set
     */
    public void setNombreFuncionario(String nombreFuncionario) {
        this.nombreFuncionario = nombreFuncionario;
    }

    /**
     * @return the tramite
     */
    public TramiteDTO getTramite() {
        return tramite;
    }

    /**
     * @param tramite the tramite to set
     */
    public void setTramite(TramiteDTO tramite) {
        this.tramite = tramite;
    }

    /**
     * @return the solicitud
     */
    public abstract S getSolicitud();

    /**
     * @param solicitud the solicitud to set
     */
    public abstract void setSolicitud(S solicitud);

    /**
     * @return the banderaRazonSocial
     */
    public boolean isBanderaRazonSocial() {
        return banderaRazonSocial;
    }

    /**
     * @param banderaRazonSocial the banderaRazonSocial to set
     */
    public void setBanderaRazonSocial(boolean banderaRazonSocial) {
        this.banderaRazonSocial = banderaRazonSocial;
    }

    /**
     * @return the solicitante
     */
    public PersonaSolicitudDTO getSolicitante() {
        return solicitante;
    }

    /**
     * @param solicitante the solicitante to set
     */
    public void setSolicitante(PersonaSolicitudDTO solicitante) {
        this.solicitante = solicitante;
    }

    public abstract String getDireccionFirma();

    /**
     * @return the atenderObservacionBussines
     */
    public AtenderObservacionBussines getAtenderObservacionBussines() {
        return atenderObservacionBussines;
    }

    /**
     * @param atenderObservacionBussines the atenderObservacionBussines to set
     */
    public void setAtenderObservacionBussines(AtenderObservacionBussines atenderObservacionBussines) {
        this.atenderObservacionBussines = atenderObservacionBussines;
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
}
