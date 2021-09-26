package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.dto.InformacionUsuarioDTO;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.AdminUsuariosDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.constantes.UrlsAdminConstantes;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

@ManagedBean(name = "administradorUnidadController")
@ViewScoped
public class AdministradorUnidadController extends AdministradorGlobalController {

    /**
     * 
     */
    private static final long serialVersionUID = 8241977163205744281L;

    /**
     * DTO que contiene los datos del Empleado
     */
    private PersonaInternaDTO empleadoDTO = new PersonaInternaDTO();
    private List<InformacionUsuarioDTO> rolesUsuario;
    private int accionBoton;

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        setEmpleadoDTO((PersonaInternaDTO) getFlash().get("datosEmpleado"));
        rolesUsuario = (List<InformacionUsuarioDTO>) getFlash().get("rolesUsuario");
        setAdminUsuariosDTO((AdminUsuariosDTO) getFlash().get(VistaConstantes.DATOS_ADMINUSUARIO_DTO));

        if (getFlash().get("accionBotonCancelar") == null) {
            accionBoton = 0;
        }
        else {
            accionBoton = (Integer) getFlash().get("accionBotonCancelar");
        }
        setUrlsRegreso((LinkedList<String>) getFlash().get(VistaConstantes.BACK));
    }

    @Override
    public void buscarEmpleado() {
        super.buscarEmpleado();
        if (getDatosEmpleado() != null) {
            getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
            getFlash().put("empleado", getDatosEmpleado());
            getFlash().put("disabled", isUsuarioInactivo());
            getUrlsRegreso().add("administrarUsuariosUA");
            getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
            redireccionarPagina(UrlsAdminConstantes.CONSULTA_ADMIN_USUARIOS.getUrl());
        }
    }

    @Override
    protected void validaAdmin() {

    }

    public void back(ActionEvent e) {
        if (accionBoton == 1) {
            getFlash().put("mensaje", getFlash().get("mensaje"));
            getFlash().put("datosEmpleado", getEmpleadoDTO());
            getFlash().put("rolesUsuario", getRolesUsuario());
            getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        }
        else {
            getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        }
        getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
        super.back();
    }

    public PersonaInternaDTO getEmpleadoDTO() {
        return empleadoDTO;
    }

    public void setEmpleadoDTO(PersonaInternaDTO empleadoDTO) {
        this.empleadoDTO = empleadoDTO;
    }

    public List<InformacionUsuarioDTO> getRolesUsuario() {
        return rolesUsuario;
    }

    public void setRolesUsuario(List<InformacionUsuarioDTO> rolesUsuario) {
        this.rolesUsuario = rolesUsuario;
    }
}
