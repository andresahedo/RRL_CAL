package mx.gob.sat.siat.juridica.configuracion.usuarios.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.AccionesBitacoraConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.InformacionUsuarioDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.InformacionUsuarioDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.AdministrarEmpleadosFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.BitacoraDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.UsuarioRolUnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer.BitacoraAUDTOTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer.PersonaInternaDTOTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer.UsuarioRolUnidadAdministrativaDTOTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.AdministrarEmpleadoService;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.BitacorAUService;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component("administrarEmpleadosFacade")
public class AdministrarEmpleadosFacadeImpl extends BaseFacadeImpl implements AdministrarEmpleadosFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -6434839818347006167L;

    @Autowired
    private transient EmpleadoService empleadoService;

    @Autowired
    private PersonaInternaDTOTransformer personaDTOTransformer;

    @Autowired
    private UsuarioRolUnidadAdministrativaDTOTransformer usuarioRolDTOTransformer;

    @Autowired
    private InformacionUsuarioDTOTransformer infoUsuarioDTOTransformer;

    @Autowired
    private transient AdministrarEmpleadoService administrarEmpleadoService;

    @Autowired
    private BitacorAUService bitacorAUService;

    @Autowired
    private BitacoraAUDTOTransformer bitacoraTransformer;

    @Override
    public boolean verificarUsuarioActivo(String idUsuario) {
        UserState usuarioActivo = empleadoService.verificarUsuarioActivo(idUsuario);
        return usuarioActivo == null ? false : usuarioActivo.isActive();
    }

    @Override
    public PersonaInternaDTO buscarEmpleado(Long numEmpleado) {
        PersonaInterna personaInterna = empleadoService.buscarEmpleado(numEmpleado);
        if (personaInterna != null) {
            PersonaInternaDTO personaDTO = personaDTOTransformer.transformarDTO(personaInterna);

            return personaDTO;
        }
        else {
            return null;
        }
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

    @Override
    public void actualizarPermisosUsuario(List<InformacionUsuarioDTO> permisos, String idUnidad,
            PersonaInternaDTO usuario) {
        List<InformacionUsuario> permisosModel = new ArrayList<InformacionUsuario>();
        for (InformacionUsuarioDTO permiso : permisos) {
            InformacionUsuario per = infoUsuarioDTOTransformer.transformarInfoDTO(permiso);
            permisosModel.add(per);

            // Guardado en bitacora de AU
            BitacoraDTO bitacoraDTO = new BitacoraDTO();

            bitacoraDTO.setDescripcion(permiso.getDescripcionRol());
            bitacoraDTO.setModalidad(null);
            bitacoraDTO.setRfcEmpleadoRealiza(getUserProfile().getRfc());
            bitacoraDTO.setRfcAplicadoA(permiso.getIdUsuario());
            bitacoraDTO.setIdUniAdmin(idUnidad);
            bitacoraDTO.setIdRol(getUserProfile().getRol());
            bitacoraDTO.setRfcAplicadoA(permiso.getIdUsuario());
            bitacoraDTO.setIdRealizadoPor(AccionesBitacoraConstants.ACCION_REALIZA_EMPLEADO);
            bitacoraDTO.setIdAplicadoA(AccionesBitacoraConstants.ACCION_REALIZA_EMPLEADO_APLICA);
            bitacoraDTO.setIdAccion(AccionesBitacoraConstants.ALTA_ROL);

            BitacoraAU bitacora = bitacoraTransformer.tranformar(bitacoraDTO);
            bitacorAUService.guardarDatosBitacora(bitacora);

        }
        administrarEmpleadoService.actualizarPermisos(permisosModel, idUnidad, usuario.getRfc());
    }



    @Override
    public List<UsuarioRolUnidadAdministrativaDTO> validarUnidadBase(String idUniAdmin, String idUsuario) {
        List<UsuarioRolUnidadAdministrativaDTO> listaRolesUnidadDTO =
                new ArrayList<UsuarioRolUnidadAdministrativaDTO>();
        List<UsuarioRolUnidadAdministrativa> listaRolesUnidad =
                administrarEmpleadoService.validarUnidadBase(idUniAdmin, idUsuario);
        if (listaRolesUnidad != null && !listaRolesUnidad.isEmpty()) {
            for (UsuarioRolUnidadAdministrativa rol : listaRolesUnidad) {
                UsuarioRolUnidadAdministrativaDTO rolDTO = usuarioRolDTOTransformer.transformarDTO(rol);
                listaRolesUnidadDTO.add(rolDTO);
            }
        }
        return listaRolesUnidadDTO;
    }
}
