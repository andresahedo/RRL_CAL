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
import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.AutenticacionBussinessBean;
import mx.gob.sat.siat.juridica.base.web.util.ApplicationContextHelper;
import mx.gob.sat.siat.juridica.rrl.util.constante.GeneralConstantes;
import mx.gob.sat.siat.juridica.rrl.util.recurso.sesion.SesionHelper;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Controller para autenticacion.
 * 
 * @author softtek
 * 
 */
@SessionScoped
@ManagedBean(name = "autenticacionController")
public class AutenticacionController extends BaseControllerBean {

    /**
     * Numero de serie.
     */
    private static final long serialVersionUID = -5560034263210776094L;

    /**
     * Bussines para la autenticacion.
     */
    @ManagedProperty(value = "#{autenticacionBussinessBean}")
    private AutenticacionBussinessBean autenticacionBussinessBean;

    @ManagedProperty(value = "#{sesionHelper}")
    private SesionHelper sesionHelper;

    private String tiempoAvisoSesion;
    private String tiempoEsperaAviso;

    public String getTiempoAvisoSesion() {

        return this.tiempoAvisoSesion;
    }

    public void setTiempoAvisoSesion(String tiempoAvisoSesion) {
        this.tiempoAvisoSesion = tiempoAvisoSesion;
    }

    public String getTiempoEsperaAviso() {

        return this.tiempoEsperaAviso;
    }

    public void setTiempoEsperaAviso(String tiempoEsperaAviso) {
        this.tiempoEsperaAviso = tiempoEsperaAviso;
    }

    public SesionHelper getSesionHelper() {
        return sesionHelper;
    }

    public void setSesionHelper(SesionHelper sesionHelper) {
        this.sesionHelper = sesionHelper;
    }

    @PostConstruct
    public void init() {
        this.tiempoAvisoSesion = sesionHelper.getTiempoAvisoSesion();
        this.tiempoEsperaAviso = sesionHelper.getTiempoEsperaAviso();
    }

    /**
     * Metodo para validar la sesion haya sido iniciada por un
     * usuario.
     * 
     * @throws IOException
     */
    public void validar() throws IOException {
        String pagina = "home.jsf";
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        AccesoUsr accesoUsr = null;
        try {
            accesoUsr = (AccesoUsr) session.getAttribute("acceso");
            if (accesoUsr == null) {
                accesoUsr = (AccesoUsr) session.getAttribute("accesoEF");
            }
        }
        catch (Exception e) {
            getLogger().error("Error al obtener el accesoUsr", e);
            getLogger().error("", e);
        }
        if (accesoUsr == null) {
            getLogger().error("No se obtuvo el usuario firmado de la sesion");
            pagina = "error.jsf";
        }
        else {
            session.setAttribute("tamanioApp", GeneralConstantes.TAMANIO_APP);
            session.setAttribute("tipoTamanioApp", GeneralConstantes.TIPO_TAMANIO_APP);
            session.setAttribute("tamanioBuzon", GeneralConstantes.TAMANIO_BUZON);
            session.setAttribute("tipoTamanioBuzon", GeneralConstantes.TIPO_TAMANIO_BUZON);
            getLogger().debug("accesoUsr {}", accesoUsr);
            UserProfileDTO userProfileDTO = getAutenticacionBussinessBean().extrarDatos(accesoUsr);
            getLogger().debug("Se coloca el usuario en sesion en userProfile {}", userProfileDTO);
            session.setAttribute("userProfile", userProfileDTO);
            session.setAttribute("fielMode", ApplicationContextHelper.getApplicationContext().getBean("appletMode"));

        }
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        configurableNavigationHandler.performNavigation(pagina + "?faces-redirect=true");

    }

    /**
     * Metodo GET que regresa un objeto de la clase
     * AutenticacionBussinesBean
     * 
     * @return autenticacionBussinesBean
     */
    public AutenticacionBussinessBean getAutenticacionBussinessBean() {
        return autenticacionBussinessBean;
    }

    /**
     * Metodo set que recibe como parametro un objeto de la clase
     * AutenticacionBussinesBean
     * 
     * @param autenticacionBussinessBean
     */
    public void setAutenticacionBussinessBean(AutenticacionBussinessBean autenticacionBussinessBean) {
        this.autenticacionBussinessBean = autenticacionBussinessBean;
    }

}
