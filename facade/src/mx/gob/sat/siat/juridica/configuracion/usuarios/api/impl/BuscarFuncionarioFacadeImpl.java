/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.InformacionUsuario;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UserState;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioRolUnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dto.InformacionUsuarioDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.InformacionUsuarioDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.BuscarFuncionarioFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.UsuarioRolUnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer.PersonaInternaDTOTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer.UsuarioRolUnidadAdministrativaDTOTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Component("buscarFuncionarioFacade")
public class BuscarFuncionarioFacadeImpl extends BaseFacadeImpl implements BuscarFuncionarioFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -7182131950610265043L;

    @Autowired
    private transient EmpleadoService empleadoService;

    @Autowired
    private PersonaInternaDTOTransformer personaInternaDTOTransformer;

    @Autowired
    private UsuarioRolUnidadAdministrativaDTOTransformer usuarioRolDTOTransformer;

    @Autowired
    private InformacionUsuarioDTOTransformer infoUsuarioDTOTransformer;

    @Override
    public PersonaInternaDTO buscarEmpleado(Long numEmpleado) {
        PersonaInternaDTO personaDTO = null;
        PersonaInterna empleado = empleadoService.buscarEmpleado(numEmpleado);
        if (empleado != null) {
            personaDTO = personaInternaDTOTransformer.transformarDTO(empleado);
        }
        return personaDTO;
    }

    @Override
    public boolean verificarUsuarioActivo(String idUsuario) {
        UserState usuarioActivo = empleadoService.verificarUsuarioActivo(idUsuario);
        return usuarioActivo == null ? false : usuarioActivo.isActive();
    }

    @Override
    public List<UsuarioRolUnidadAdministrativaDTO> obtenerTodosRolesUsuario(String idUsuario) {
        List<UsuarioRolUnidadAdministrativaDTO> listDTO = new ArrayList<UsuarioRolUnidadAdministrativaDTO>();
        List<UsuarioRolUnidadAdministrativa> listaRoles = empleadoService.obtenerTodosRolesFuncionario(idUsuario);
        if (listaRoles != null && !listaRoles.isEmpty()) {
            for (UsuarioRolUnidadAdministrativa rol : listaRoles) {
                UsuarioRolUnidadAdministrativaDTO usuarioRolDTO = usuarioRolDTOTransformer.transformarDTO(rol);
                listDTO.add(usuarioRolDTO);
            }
        }
        return listDTO;
    }

    @Override
    public List<InformacionUsuarioDTO> consultarRolesNovell(String idUsuario) {
        List<InformacionUsuarioDTO> listaRolesDTO = new ArrayList<InformacionUsuarioDTO>();
        List<InformacionUsuario> listaRoles = empleadoService.consultarRolesNovell(idUsuario);
        if (listaRoles != null && !listaRoles.isEmpty()) {
            for (InformacionUsuario info : listaRoles) {
                InformacionUsuarioDTO infRol = infoUsuarioDTOTransformer.transformarModel(info);
                listaRolesDTO.add(infRol);
            }
        }
        return listaRolesDTO;
    }

}
