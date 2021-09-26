package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.ca.util.constants.RegistroSolicitudConstants;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.AdminUsuariosDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PermisosAUDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.ServiciosDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business.EncabezadoBusiness;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business.ServiciosDisponiblesBusiness;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.constantes.UrlsAdminConstantes;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@ViewScoped
@ManagedBean(name = "serviciosDisponiblesController")
public class ServiciosDisponiblesController extends BaseControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = 3929030464745433996L;

    @ManagedProperty(value = "#{serviciosDisponiblesBusiness}")
    private transient ServiciosDisponiblesBusiness servDisponiblesBusiness;

    private List<ServiciosDTO> listaServicios;

    @ManagedProperty(value = "#{encabezadoBusiness}")
    private transient EncabezadoBusiness encabezadoBusiness;

    private String headerMenu;

    private AdminUsuariosDTO adminUsuariosDTO;
    private boolean limpiar;
    private List<String> urlsRegreso = new LinkedList<String>();
    private boolean inicial;

    @PostConstruct
    public void init() {
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

        if (getAdminUsuariosDTO().isBlnGlobalUnidadAdmin()) {
            setHeaderMenu("Men&uacute Administrador Unidad Administrativa");
            setListaServicios(getServDisponiblesBusiness().obtenerServiciosRol("AdministradorUA"));
        }
        else {
            if (getAdminUsuariosDTO().getIdPermiso().equals("AdministradorGlobal")) {
                setHeaderMenu("Men&uacute Administrador Global");
                setListaServicios(getServDisponiblesBusiness()
                        .obtenerServiciosRol(getAdminUsuariosDTO().getIdPermiso()));
            }
            else {
                setHeaderMenu("Men&uacute Administrador Unidad Administrativa");
                setListaServicios(getServDisponiblesBusiness().obtenerServiciosRol("AdministradorUA"));
            }
        }

    }

    public boolean verificarTipoFuncionario() {
        return adminUsuariosDTO.getPermiso().equals("Administrador Global");
    }

    public void back() {
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        if (getUrlsRegreso() != null && !getUrlsRegreso().isEmpty()) {
            UrlsAdminConstantes url = UrlsAdminConstantes.parse(((LinkedList<String>) getUrlsRegreso()).pollLast());
            redireccionarPagina(url.getUrl());
            getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
        }
    }

    public void siguienteMenu(ActionEvent event) {
        String url = (String) event.getComponent().getAttributes().get("url");
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());

        if (getAdminUsuariosDTO().isBlnGlobalUnidadAdmin()) {

            getUrlsRegreso().add("menu");
        }
        else {
            if (getUrlsRegreso() == null) {
                LinkedList<String> urls = new LinkedList<String>();
                urls.add("indexAdmin");
                setUrlsRegreso(urls);

            }
            else {
                getUrlsRegreso().add("menu");

            }

        }

        getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
        redireccionarPagina(url);
    }

    public ServiciosDisponiblesBusiness getServDisponiblesBusiness() {
        return servDisponiblesBusiness;
    }

    public void setServDisponiblesBusiness(ServiciosDisponiblesBusiness servDisponiblesBusiness) {
        this.servDisponiblesBusiness = servDisponiblesBusiness;
    }

    public List<ServiciosDTO> getListaServicios() {
        return listaServicios;
    }

    public void setListaServicios(List<ServiciosDTO> listaServicios) {
        this.listaServicios = listaServicios;
    }

    public String getHeaderMenu() {
        return headerMenu;
    }

    public void setHeaderMenu(String headerMenu) {
        this.headerMenu = headerMenu;
    }

    public String obtenerPermisoUsuario() {
        Collection<String> rolesNovell = getUserProfile().getRolesNovell();
        List<PermisosAUDTO> rolesBase = servDisponiblesBusiness.obtenerPermisoUsuario();
        String permiso = "";
        for (PermisosAUDTO rol : rolesBase) {
            if (rol.getIdRol().equals(RolesConstantes.ROL_INTERNO_ADMINISTRADOR_GLOBAL)
                    && rolesNovell.contains(rol.getPermisoNovell())) {

                permiso = rol.getPermiso();
                adminUsuariosDTO.setIdPermiso(rol.getIdRol());
                return permiso;
            }
            else if (rol.getIdRol().equals(RolesConstantes.ROL_INTERNO_ADMINISTRADOR_UNIDAD_ADMINISTRATIVA)
                    && rolesNovell.contains(rol.getPermisoNovell())) {

                permiso = rol.getPermiso();
                adminUsuariosDTO.setIdPermiso(rol.getIdRol());
            }
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
