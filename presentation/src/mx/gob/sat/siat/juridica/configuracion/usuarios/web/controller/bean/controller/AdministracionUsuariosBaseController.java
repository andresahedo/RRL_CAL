package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.ca.util.constants.RegistroSolicitudConstants;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.AdminUsuariosDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business.EncabezadoBusiness;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.constantes.UrlsAdminConstantes;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class AdministracionUsuariosBaseController extends BaseControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = 6850070970142985620L;
    private static final String NO_CACHE = "no-cache";

    @ManagedProperty(value = "#{encabezadoBusiness}")
    private transient EncabezadoBusiness encabezadoBusiness;

    private AdminUsuariosDTO adminUsuariosDTO;
    private boolean limpiar;
    private List<String> urlsRegreso = new LinkedList<String>();
    private boolean inicial;

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().setResponseCharacterEncoding("UTF-8");
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.setHeader("Cache-Control", NO_CACHE); 
                                                       // Prevents
                                                       // HTTP 1.1
                                                       // caching.
        response.setHeader("Pragma", NO_CACHE); 
                                                // Prevents HTTP 1.0
                                                // caching.
        response.setDateHeader("Expires", -1); 
                                                // Prevents proxy
                                               // caching.
        String show = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("limpiar");
        setLimpiar(show != null);
        if (getLimpiar()) {
            getFlash().remove(VistaConstantes.DATOS_ADMINUSUARIO_DTO);
        }
        setAdminUsuariosDTO((AdminUsuariosDTO) getFlash().get(VistaConstantes.DATOS_ADMINUSUARIO_DTO));

        setUrlsRegreso((LinkedList<String>) getFlash().get(VistaConstantes.BACK));
        setInicial(getUrlsRegreso() != null && !getUrlsRegreso().isEmpty());
        if (getAdminUsuariosDTO() == null) {
            adminUsuariosDTO = new AdminUsuariosDTO();
            adminUsuariosDTO.setRfcUsuario(getUserProfile().getRfc());
            adminUsuariosDTO.setNombreEmpleado(getEncabezadoBusiness().obtenerNombreAdministrador(
                    getUserProfile().getRfc()));
            adminUsuariosDTO.setPermiso(obtenerPermisoUsuario());
            adminUsuariosDTO.setBlnIsAdminGlobal(verificarTipoFuncionario());
        }

    }

    public boolean verificarTipoFuncionario() {
        return adminUsuariosDTO.getPermiso().equals("Administrador Global");
    }

    public void borrarUnidad() {
        getAdminUsuariosDTO().setUnidadAdministrativa(null);
    }

    public void back() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().setResponseCharacterEncoding("UTF-8");
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.setHeader("Cache-Control", NO_CACHE);
                                                       // Prevents
                                                       // HTTP 1.1
                                                       // caching.
        response.setHeader("Pragma", NO_CACHE); 
                                                 // Prevents HTTP 1.0
                                                // caching.
        response.setDateHeader("Expires", -1); 
                                                // Prevents proxy
                                               // caching.
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        if (getUrlsRegreso() != null && !getUrlsRegreso().isEmpty()) {
            UrlsAdminConstantes url = UrlsAdminConstantes.parse(((LinkedList<String>) getUrlsRegreso()).pollLast());
            redireccionarPagina(url.getUrl());
            getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
        }
    }

    public String obtenerPermisoUsuario() {
        Collection<String> rolesNovell = getUserProfile().getRolesNovell();
        String permiso = "";
        if (rolesNovell.contains(RolesConstantes.ROL_ADMINISTRADOR_GLOBAL)) {
            permiso = "Administrador Global";
            adminUsuariosDTO.setIdPermiso("AdministradorGlobal");
        }
        else if (rolesNovell.contains(RolesConstantes.ROL_ADMINISTRADOR_UNIDAD_ADMINISTRATIVA)) {
            permiso = "Administrador Unidad Administrativa";
            adminUsuariosDTO.setIdPermiso("AdministradorUA");
        }
        return permiso;
    }

    protected void redireccionarPagina(String url) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().setResponseCharacterEncoding("UTF-8");
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) facesContext.getApplication().getNavigationHandler();
        configurableNavigationHandler.performNavigation(url + RegistroSolicitudConstants.FACES_REDIRECT);
    }

    public EncabezadoBusiness getEncabezadoBusiness() {
        return encabezadoBusiness;
    }

    public void setEncabezadoBusiness(EncabezadoBusiness encabezadoBusiness) {
        this.encabezadoBusiness = encabezadoBusiness;
    }

    public AdminUsuariosDTO getAdminUsuariosDTO() {
        return adminUsuariosDTO;
    }

    public void setAdminUsuariosDTO(AdminUsuariosDTO adminUsuariosDTO) {
        this.adminUsuariosDTO = adminUsuariosDTO;
    }

    /**
     * @return the limpiar
     */
    public Boolean getLimpiar() {
        return limpiar;
    }

    /**
     * @param limpiar
     *            the limpiar to set
     */
    public void setLimpiar(Boolean limpiar) {
        this.limpiar = limpiar;
    }

    /**
     * @return the inicial
     */
    public boolean isInicial() {
        return inicial;
    }

    /**
     * @param inicial
     *            the inicial to set
     */
    public void setInicial(boolean inicial) {
        this.inicial = inicial;
    }

    /**
     * @return the urlsRegreso
     */
    public List<String> getUrlsRegreso() {
        return urlsRegreso;
    }

    /**
     * @param urlsRegreso
     *            the urlsRegreso to set
     */
    public void setUrlsRegreso(List<String> urlsRegreso) {
        this.urlsRegreso = urlsRegreso;
    }
}
