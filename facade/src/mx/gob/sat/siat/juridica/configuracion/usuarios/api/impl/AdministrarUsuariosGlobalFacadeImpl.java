/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.AccionesBitacoraConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.InformacionUsuarioDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.InformacionUsuarioDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.UnidadAdministrativaDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.UnidadAdministrativaServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.AdministrarUsuariosGlobalFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.BitacoraDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.UsuarioRolUnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer.BitacoraAUDTOTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer.PersonaInternaDTOTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer.UsuarioRolUnidadAdministrativaDTOTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.BitacorAUService;
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

@Component("administrarUsuariosGlobalFacade")
public class AdministrarUsuariosGlobalFacadeImpl extends BaseFacadeImpl implements AdministrarUsuariosGlobalFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -1394840116231961465L;

    @Autowired
    private transient EmpleadoService empleadoService;

    @Autowired
    private UsuarioRolUnidadAdministrativaDTOTransformer usuarioRolTransformer;

    @Autowired
    private PersonaInternaDTOTransformer personaTransformer;

    @Autowired
    private transient UnidadAdministrativaServices unidadService;

    @Autowired
    private UnidadAdministrativaDTOTransformer unidadTransformer;

    @Autowired
    private InformacionUsuarioDTOTransformer informacionUsuarioDTOTransformer;

    @Autowired
    private BitacorAUService bitacorAUService;

    @Autowired
    private BitacoraAUDTOTransformer bitacoraTransformer;

    @Override
    public List<UsuarioRolUnidadAdministrativaDTO> buscarRolesUsuario(String idUsuario) {
        List<UsuarioRolUnidadAdministrativa> roles = empleadoService.buscarRolesUsuario(idUsuario);
        List<UsuarioRolUnidadAdministrativaDTO> rolesDTO = new ArrayList<UsuarioRolUnidadAdministrativaDTO>();
        if (roles != null && !roles.isEmpty()) {
            for (UsuarioRolUnidadAdministrativa rol : roles) {
                UsuarioRolUnidadAdministrativaDTO rolDTO = usuarioRolTransformer.transformarDTO(rol);
                rolesDTO.add(rolDTO);
            }
        }
        return rolesDTO;
    }

    @Override
    public PersonaInternaDTO buscarEmpleado(Long numEmpleado) {
        PersonaInterna personaInterna = empleadoService.buscarEmpleado(numEmpleado);
        if (personaInterna != null) {
            PersonaInternaDTO personaDTO = personaTransformer.transformarDTO(personaInterna);

            return personaDTO;
        }
        else {
            return null;
        }
    }

    public List<UnidadAdministrativaDTO> obtenerCatalogoUnidades() {
        List<UnidadAdministrativa> listaUnidades = unidadService.obtenerCatalogo();
        return unidadTransformer.transformarDTO(listaUnidades);

    }

    @Override
    public boolean verificarUsuarioActivo(String idUsuario) {
        UserState usuarioActivo = empleadoService.verificarUsuarioActivo(idUsuario);
        return usuarioActivo == null ? false : usuarioActivo.isActive();
    }

    @Override
    public void asignarPermiso(UsuarioRolUnidadAdministrativaDTO permisoUsuario) {
        if (permisoUsuario != null) {
            UsuarioRolUnidadAdministrativa permiso = usuarioRolTransformer.transformarModel(permisoUsuario);
            if (permiso != null) {
                empleadoService.asignarPermiso(permiso);

                // Guardado en bitacora de AU
                BitacoraDTO bitacoraDTO = new BitacoraDTO();
                bitacoraDTO.setRfcEmpleadoRealiza(getUserProfile().getRfc());
                bitacoraDTO.setRfcAplicadoA(permisoUsuario.getIdUsuario() != null ? permisoUsuario.getIdUsuario()
                        : null);
                bitacoraDTO.setIdUniAdmin(permiso.getUnidadAdministrativa().getClave());
                bitacoraDTO.setNomUniAdmin(permiso.getUnidadAdministrativa().getNombreUnidad());
                bitacoraDTO.setIdRol(getUserProfile().getRol());
                if (permisoUsuario.getTipoTramite() != null) {
                    bitacoraDTO.setIdTipoTramite(Integer.valueOf(permisoUsuario.getTipoTramite()));
                }
                bitacoraDTO.setIdRealizadoPor(AccionesBitacoraConstants.ACCION_REALIZA_EMPLEADO);
                bitacoraDTO.setIdAplicadoA(AccionesBitacoraConstants.ACCION_REALIZA_EMPLEADO_APLICA);
                bitacoraDTO.setIdAccion(AccionesBitacoraConstants.ASIGNAR_PERMISO_ADMINISTRADOR_GLOBAL);

                bitacoraDTO.setDescripcion(permiso.getRol().getDescription());
                bitacoraDTO.setIdAccion(AccionesBitacoraConstants.ASIGNAR_PERMISO_EMPLEADO);

                BitacoraAU bitacora = bitacoraTransformer.tranformar(bitacoraDTO);
                bitacorAUService.guardarDatosBitacora(bitacora);
            }
        }
    }

    @Override
    public void revocarPermiso(List<UsuarioRolUnidadAdministrativaDTO> permisos, String rfc) {
        BitacoraDTO bitacoraDTO = new BitacoraDTO();
        List<UsuarioRolUnidadAdministrativa> permisosModel = new ArrayList<UsuarioRolUnidadAdministrativa>();
        for (UsuarioRolUnidadAdministrativaDTO per : permisos) {
            UsuarioRolUnidadAdministrativa permiso = usuarioRolTransformer.transformarModel(per);
            permisosModel.add(permiso);

            // Guardado en bitacora de AU
            bitacoraDTO.setRfcEmpleadoRealiza(rfc);
            bitacoraDTO.setRfcAplicadoA(per.getIdUsuario());
            bitacoraDTO.setIdUniAdmin(permiso.getUnidadAdministrativa().getClave());
            bitacoraDTO.setNomUniAdmin(permiso.getUnidadAdministrativa().getNombreUnidad());
            bitacoraDTO.setIdRol(getUserProfile().getRol());
            bitacoraDTO.setIdTipoTramite(per.getTipoTramite() != null ? Integer.valueOf(per.getTipoTramite()) : null);
            bitacoraDTO.setIdRealizadoPor(AccionesBitacoraConstants.ACCION_REALIZA_EMPLEADO);
            bitacoraDTO.setIdAplicadoA(AccionesBitacoraConstants.ACCION_REALIZA_EMPLEADO_APLICA);

            bitacoraDTO.setDescripcion(per.getUnidadAdministrativa().getNombre());
            bitacoraDTO.setIdAccion(AccionesBitacoraConstants.REVOCAR_PERMISO_EMPLEADO);

            BitacoraAU bitacora = bitacoraTransformer.tranformar(bitacoraDTO);
            bitacorAUService.guardarDatosBitacora(bitacora);

        }
        empleadoService.revocarPermiso(permisosModel);
    }

    @Override
    public List<InformacionUsuarioDTO> buscarPermisosUsuario(String idUsuario) {
        List<InformacionUsuario> rolesUsuario = empleadoService.consultarRolesNovell(idUsuario);
        List<InformacionUsuarioDTO> roles = new ArrayList<InformacionUsuarioDTO>();
        if (rolesUsuario != null && !rolesUsuario.isEmpty()) {
            for (InformacionUsuario info : rolesUsuario) {
                InformacionUsuarioDTO inf = informacionUsuarioDTOTransformer.transformarModel(info);
                roles.add(inf);
            }
        }
        return roles;
    }

    @Override
    public void activarAdministradorGlobal(String idUsuario, boolean checkGlobal) {
        empleadoService.activarAdministradorGlobal(idUsuario, checkGlobal);
    }

    @Override
    public boolean verificarAdministradorGlobalActivo(String idUsuario) {
        UserState administradorGlobal = empleadoService.verificarUsuarioActivo(idUsuario);
        if (administradorGlobal.getBlnActivoGlobal() != null) {
            return administradorGlobal.getBlnActivoGlobal();
        }
        else {
            return false;
        }

    }

}
