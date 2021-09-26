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
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.BuscarFuncionarioFacade;
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
@ManagedBean(name = "buscarFuncionarioBusiness")
public class BuscarFuncionarioBusiness extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = 4846691996756164173L;

    @ManagedProperty("#{buscarFuncionarioFacade}")
    private BuscarFuncionarioFacade buscarFuncionarioFacade;

    public PersonaInternaDTO buscarFuncionario(Long numEmpleado) {
        return buscarFuncionarioFacade.buscarEmpleado(numEmpleado);

    }

    public boolean validarUsuarioActivo(String idUsuario) {
        return buscarFuncionarioFacade.verificarUsuarioActivo(idUsuario);
    }

    public List<UsuarioRolUnidadAdministrativaDTO> obtenerTodosRolesUsuario(String idUsuario) {
        return buscarFuncionarioFacade.obtenerTodosRolesUsuario(idUsuario);
    }

    public List<InformacionUsuarioDTO> consultarRolesNovell(String idUsuario) {
        return buscarFuncionarioFacade.consultarRolesNovell(idUsuario);
    }

    /**
     * @return the buscarFuncionarioFacade
     */
    public BuscarFuncionarioFacade getBuscarFuncionarioFacade() {
        return buscarFuncionarioFacade;
    }

    /**
     * @param buscarFuncionarioFacade
     *            the buscarFuncionarioFacade to set
     */
    public void setBuscarFuncionarioFacade(BuscarFuncionarioFacade buscarFuncionarioFacade) {
        this.buscarFuncionarioFacade = buscarFuncionarioFacade;
    }

}
