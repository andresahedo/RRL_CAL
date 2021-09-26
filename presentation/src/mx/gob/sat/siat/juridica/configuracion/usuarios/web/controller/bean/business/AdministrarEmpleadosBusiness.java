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
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.AdministrarEmpleadosFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.UsuarioRolUnidadAdministrativaDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */

@SessionScoped
@ManagedBean(name = "administrarEmpleadosBusiness")
public class AdministrarEmpleadosBusiness extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = -2652860238924075226L;

    @ManagedProperty("#{administrarEmpleadosFacade}")
    private AdministrarEmpleadosFacade administrarEmpleadosFacade;

    public boolean verificarUsuarioActivo(String idUsuario) {
        return administrarEmpleadosFacade.verificarUsuarioActivo(idUsuario);
    }

    public PersonaInternaDTO obtenerInformacionEmpleado(Long numEmpleado) {
        return administrarEmpleadosFacade.buscarEmpleado(numEmpleado);
    }

    public List<UsuarioRolUnidadAdministrativaDTO> obtenerTodosRolesUsuario(String idUsuario) {
        return administrarEmpleadosFacade.obtenerTodosRolesUsuario(idUsuario);
    }

    public List<InformacionUsuarioDTO> consultarRolesNovell(String idUsuario) {
        return administrarEmpleadosFacade.consultarRolesNovell(idUsuario);
    }

    public void actualizarPermisosUsuario(List<InformacionUsuarioDTO> permisos, String idUnidad,
            PersonaInternaDTO usuario) {
        administrarEmpleadosFacade.actualizarPermisosUsuario(permisos, idUnidad, usuario);

    }

    public List<UsuarioRolUnidadAdministrativaDTO> validarUnidadBase(String idUniAdmin, String idUsuario) {
        return administrarEmpleadosFacade.validarUnidadBase(idUniAdmin, idUsuario);
    }

    /**
     * @return the administrarEmpleadosFacade
     */
    public AdministrarEmpleadosFacade getAdministrarEmpleadosFacade() {
        return administrarEmpleadosFacade;
    }

    /**
     * @param administrarEmpleadosFacade
     *            the administrarEmpleadosFacade to set
     */
    public void setAdministrarEmpleadosFacade(AdministrarEmpleadosFacade administrarEmpleadosFacade) {
        this.administrarEmpleadosFacade = administrarEmpleadosFacade;
    }

}
