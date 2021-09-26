/*
 *  Todos los Derechos Reservados © 2013 SAT
 *  Servicio de Administracion Tributaria (SAT).
 *  
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */

package mx.gob.sat.siat.juridica.base.controller;

import mx.gob.sat.siat.controlador.AccesoProceso;
import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;

/**
 * Clase Base para los ControllerBean
 * 
 * Bean que implementan la logica de negocio, consumiendo objetos del modelo por
 * medio de ModelBeans
 * 
 * @author Softtek
 */
public abstract class BaseControllerBean implements Serializable {
    /**
     * Propiedad para llevar a cabo un registro de eventos.
     */
    private final transient Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -7509780772730323110L;

    public static final String PROFILE = "userProfile";

    public static final String ACCESO = "/resources/pages/error/accesoDenegado.jsf";

    /** Constructor vacio */
    protected BaseControllerBean() {
        super();
    }

    /**
     * 
     * @return UserProfileDTO
     */
    public UserProfileDTO getUserProfile() {
        return (UserProfileDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(PROFILE);
    }

    public HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    /**
     * 
     * @param userProfile el userProfile a fijar
     */
    public void setUserProfile(UserProfileDTO userProfile) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(PROFILE, userProfile);
    }

    /**
     * 
     * @return Flash
     */
    public Flash getFlash() {
        return FacesContext.getCurrentInstance().getExternalContext().getFlash();
    }

    protected String buildRealPath(final String file) {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        return null == file || file.trim().isEmpty() ? "" : ctx.getRealPath("/") + file;
    }

    protected void validaAccesoProcesoMenu(String identificador, int idMenu) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();
        try {

            AccesoProceso.validaAccesoProcesoMenu(session, identificador, idMenu);
        } catch (Exception e) {
            try {

                session.setAttribute("mensaje", e);

                FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ACCESO);
            } catch (IOException ioe) {
                logger.error("Error al valida acceso al menu: ", ioe);
            }
        }
    }

    protected void validaAcceso(String rolAdmin, String rolAdminGlobal) {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();

        UserProfileDTO userProfile = (UserProfileDTO) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(PROFILE);

        if (!(userProfile.getRolesNovell().contains(rolAdmin)
                || userProfile.getRolesNovell().contains(rolAdminGlobal))) {

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ACCESO);
            } catch (IOException e) {
                logger.error("Error al valida acceso del contribuyente: ", e);
            }

        }
    }
    
    protected Boolean validaRolEmpleado(String rol) {
        UserProfileDTO userProfile = (UserProfileDTO) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(PROFILE);
        return (userProfile.getRolesNovell().contains(rol));
    }
    
    protected void validaAccesoContribuytente() {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();
        UserProfileDTO userProfile = (UserProfileDTO) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(PROFILE);

        if (!userProfile.getEsContribuyente()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ACCESO);
            } catch (IOException e) {
                logger.error("Error al valida acceso del contribuyente: ", e);
            }
        }
    }

    protected void validaAccesoRolEmpleado(String rol) {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();
        UserProfileDTO userProfile = (UserProfileDTO) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(PROFILE);

        logger.debug("rol: {}", rol);
        logger.debug("userProfile.getRolesNovell(): {}", userProfile.getRolesNovell());
        if (!userProfile.getRolesNovell().contains(rol)) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ACCESO);
            } catch (IOException e) {
                logger.error("Error al valida acceso del rol: ", e);
            }
        }
    }

    public Logger getLogger() {
        return this.logger;
    }
}
