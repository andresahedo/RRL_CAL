/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.InformacionUsuarioDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.AdministrarUsuariosGlobalFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.UsuarioRolUnidadAdministrativaDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * @author softtek
 * 
 */
@SessionScoped
@ManagedBean(name = "administradorGlobalBusiness")
public class AdministradorGlobalBusiness extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = -7583633467684516410L;

    @ManagedProperty("#{administrarUsuariosGlobalFacade}")
    private AdministrarUsuariosGlobalFacade administrarUsuariosGlobalFacade;

    public List<UsuarioRolUnidadAdministrativaDTO> obtenerRolesEmpleado(String idUsuario) {
        return administrarUsuariosGlobalFacade.buscarRolesUsuario(idUsuario);
    }

    public PersonaInternaDTO obtenerInformacionEmpleado(Long numEmpleado) {
        return administrarUsuariosGlobalFacade.buscarEmpleado(numEmpleado);
    }

    public List<UnidadAdministrativaDTO> obtenerCatalogoUnidades() {
        return administrarUsuariosGlobalFacade.obtenerCatalogoUnidades();
    }

    public boolean verificarUsuarioActivo(String idUsuario) {
        return administrarUsuariosGlobalFacade.verificarUsuarioActivo(idUsuario);
    }

    public void asignarPermiso(UsuarioRolUnidadAdministrativaDTO permisoUsuario) {
        administrarUsuariosGlobalFacade.asignarPermiso(permisoUsuario);
    }

    public void revocarPermiso(List<UsuarioRolUnidadAdministrativaDTO> permisos, String rfc) {

        administrarUsuariosGlobalFacade.revocarPermiso(permisos, rfc);
    }

    public List<InformacionUsuarioDTO> consultarPermisosUsuario(String idUsuario) {
        return administrarUsuariosGlobalFacade.buscarPermisosUsuario(idUsuario);
    }

    public void activarAdministradorGlobal(String idUsuario, boolean checkGlobal) {
        administrarUsuariosGlobalFacade.activarAdministradorGlobal(idUsuario, checkGlobal);
    }

    public boolean verificarAdministradorGlobalActivo(String idUsuario) {
        return administrarUsuariosGlobalFacade.verificarAdministradorGlobalActivo(idUsuario);
    }

    /**
     * @return the administrarUsuariosGlobalFacade
     */
    public AdministrarUsuariosGlobalFacade getAdministrarUsuariosGlobalFacade() {
        return administrarUsuariosGlobalFacade;
    }

    /**
     * @param administrarUsuariosGlobalFacade
     *            the administrarUsuariosGlobalFacade to set
     */
    public void setAdministrarUsuariosGlobalFacade(AdministrarUsuariosGlobalFacade administrarUsuariosGlobalFacade) {
        this.administrarUsuariosGlobalFacade = administrarUsuariosGlobalFacade;
    }

}
