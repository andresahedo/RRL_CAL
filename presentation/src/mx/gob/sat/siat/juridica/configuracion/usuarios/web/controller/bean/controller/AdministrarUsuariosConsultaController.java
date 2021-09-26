/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.dto.InformacionUsuarioDTO;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business.AdministrarUsuarioUABusiness;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.constantes.AdministracionConstantes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Controller para la consulta de permisos de usuarios
 * 
 * @author Softtek - EQG
 * @since 30/09/2014
 */
@ViewScoped
@ManagedBean(name = "administrarUsuariosConsultaController")
public class AdministrarUsuariosConsultaController extends ConsultarUsuariosController {

    /**
     * 
     */
    private static final long serialVersionUID = -3060310038878681557L;

    private PersonaInternaDTO datosEmpleado;

    private boolean blnActivarReasignador;

    private boolean renderedMensajeTarea;

    @ManagedProperty("#{administrarUsuarioUABusiness}")
    private transient AdministrarUsuarioUABusiness administrarUsuarioUABusiness;
    /**
     * Propiedades de pantallas anteriores.
     */
    private boolean disabled;
    private int botonCancelar;
    private List<InformacionUsuarioDTO> rolesUsuario;

    /**
     * Metodo para inciiar.
     * 
     * @param actionEvent
     */
    @SuppressWarnings("unchecked")
    @Override
    public void init() {
        super.init();
        inicializaDatosComunes();
        setDatosEmpleado((PersonaInternaDTO) getFlash().get("empleado"));
        getFiltroUsuarios().setIdUnidadAdmin(getAdminUsuariosDTO().getIdUnidadAmin());
        getFiltroUsuarios().setRfcEmpleado(getDatosEmpleado().getRfc());
        getFiltroUsuarios().setIdUnidadAdmin(getAdminUsuariosDTO().getIdUnidadAmin());
        getFiltroUsuarios().setNumeroEmpleado(getDatosEmpleado().getNumeroEmpleado().toString());
        setDisabled((Boolean) getFlash().get("disabled"));
        if (getFlash().get("accionBotonCancelar") != null) {
            setBotonCancelar((Integer) getFlash().get("accionBotonCancelar"));
        }
        else {
            setBotonCancelar(0);
        }
        if (getFlash().get("rolesUsuario") == null) {
            setRolesUsuario((List<InformacionUsuarioDTO>) getFlash().get("rolesUsuario"));
        }
        else {
            setRolesUsuario(new ArrayList<InformacionUsuarioDTO>());
        }
        consultarPermisos();

     

        if (getListaUsuarios() != null && !getListaUsuarios().isEmpty()) {
            blnActivarReasignador = false;
        }
        else {
            blnActivarReasignador = true;
        }

    }

    /**
     * @return the datosEmpleado
     */
    public PersonaInternaDTO getDatosEmpleado() {
        return datosEmpleado;
    }

    /**
     * @param datosEmpleado
     *            the datosEmpleado to set
     */
    public void setDatosEmpleado(PersonaInternaDTO datosEmpleado) {
        this.datosEmpleado = datosEmpleado;
    }

    /**
     * 
     */
    public void asignarTramites() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("rfc", getDatosEmpleado().getRfc());
        getFlash().put("empleado", getDatosEmpleado());
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        getFlash().put("disabled", isDisabled());
        getUrlsRegreso().add("consultaAdminUsuarios");
        getFlash().put("accionBotonCancelar", getBotonCancelar());
        getFlash().put("rolesUsuario", getRolesUsuario());
        getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
        redireccionarPagina(AdministracionConstantes.ASIGNAR_TRAMITES);
    }

    public void reasignarTareas() {
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        getFlash().put("empleado", getDatosEmpleado());
        getFlash().put("disabled", isDisabled());
        getUrlsRegreso().add("consultaAdminUsuarios");
        getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
        redireccionarPagina(AdministracionConstantes.REASIGNAR_TAREA);
    }

    /**
     * @param datosEmpleado
     *            the datosEmpleado to set
     */
    public void back() {
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
        super.back();
    }

    /**
     * @return the blnActivarReasignador
     */
    public boolean isBlnActivarReasignador() {
        return blnActivarReasignador;
    }

    /**
     * @param blnActivarReasignador
     *            the blnActivarReasignador to set
     */
    public void setBlnActivarReasignador(boolean blnActivarReasignador) {
        this.blnActivarReasignador = blnActivarReasignador;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getBotonCancelar() {
        return botonCancelar;
    }

    public void setBotonCancelar(int botonCancelar) {
        this.botonCancelar = botonCancelar;
    }

    public List<InformacionUsuarioDTO> getRolesUsuario() {
        return rolesUsuario;
    }

    public void setRolesUsuario(List<InformacionUsuarioDTO> rolesUsuario) {
        this.rolesUsuario = rolesUsuario;
    }

    public AdministrarUsuarioUABusiness getAdministrarUsuarioUABusiness() {
        return administrarUsuarioUABusiness;
    }

    public void setAdministrarUsuarioUABusiness(AdministrarUsuarioUABusiness administrarUsuarioUABusiness) {
        this.administrarUsuarioUABusiness = administrarUsuarioUABusiness;
    }

    public boolean isRenderedMensajeTarea() {
        return renderedMensajeTarea;
    }

    public void setRenderedMensajeTarea(boolean renderedMensajeTarea) {
        this.renderedMensajeTarea = renderedMensajeTarea;
    }

}
