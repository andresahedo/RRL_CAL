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
package mx.gob.sat.siat.juridica.ca.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.ca.web.controller.bean.bussiness.DetallePromocionBusiness;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.LoginConstante;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean que implementan la l&oacute;gica de negocio para detalle de la
 * promocion
 * 
 * @author Softtek
 * 
 */
@SessionScoped
@ManagedBean
public class DetallePromocionController extends BaseControllerBean {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = 1L;
    /**
     * Bussines que implementa la logica de negocio para las
     * promociones.
     */
    @ManagedProperty("#{detallePromocionBusiness}")
    private transient DetallePromocionBusiness detallePromocionBusiness;
    /**
     * Lista de los estados procesales.
     */
    private List<CatalogoDTO> estadosProcesales = new ArrayList<CatalogoDTO>();

    /** DTO que representa los datos obtenidos en la bandeja */
    private DatosBandejaTareaDTO datosBandejaTareaDTO;
    /**
     * DTO que representa los datos de un tramite.
     */
    private TramiteDTO tramite;

    /**
     * Metodo constructor vacio.
     */
    public DetallePromocionController() {}

    /**
     * Metodo que se ejecuta despues de la creacion del bean.
     */
    @PostConstruct
    public void init() {
        estadosProcesales.addAll(detallePromocionBusiness.obtenerCatalogoEstados());
    }

    /**
     * Metodo para modificar un tramite.
     */
    public void modificarTramite() {
        try {
            getDetallePromocionBusiness().modificarTramite(tramite);
            ConfigurableNavigationHandler configurableNavigationHandler =
                    (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                            .getNavigationHandler();

            configurableNavigationHandler.performNavigation(LoginConstante.BANDEJA_CONSULTAS_AUTORIZACIONES_JSF
                    + "?faces-redirect=true");
        }
        catch (SolicitudNoGuardadaException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, e.getMessage(), ""));
        }

    }

    /**
     * 
     * @return detallePromocionBusiness
     */
    public DetallePromocionBusiness getDetallePromocionBusiness() {
        return detallePromocionBusiness;
    }

    /**
     * 
     * @param detallePromocionBusiness
     *            el detallePromocionBusiness a fijar.
     */
    public void setDetallePromocionBusiness(DetallePromocionBusiness detallePromocionBusiness) {
        this.detallePromocionBusiness = detallePromocionBusiness;
    }

    /**
     * 
     * @return estadosProcesales.
     */
    public List<CatalogoDTO> getEstadosProcesales() {
        return estadosProcesales;
    }

    /**
     * 
     * @param estadosProcesales
     *            los estadosProcesales a fijar.
     */
    public void setEstadosProcesales(List<CatalogoDTO> estadosProcesales) {
        this.estadosProcesales = estadosProcesales;
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
     * @param datosBandejaTareaDTO
     *            el/los datosBandejaTareaDTO a fijar.
     */
    public void setDatosBandejaTareaDTO(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        this.datosBandejaTareaDTO = datosBandejaTareaDTO;
    }

    /**
     * 
     * @param tramite
     *            el tramite a fijar.
     */
    public void setTramite(TramiteDTO tramite) {
        this.tramite = tramite;
    }

    /**
     * 
     * @return tramite
     */
    public TramiteDTO getTramite() {
        tramite = detallePromocionBusiness.obtenerTramite(datosBandejaTareaDTO.getNumeroAsunto());
        return tramite;
    }

    /**
     * 
     * @return boolean
     */
    public boolean getValidaUsr() {
        if (getUserProfile().getRoles().contains("Abogado")) {
            return true;
        }
        return false;
    }

}
