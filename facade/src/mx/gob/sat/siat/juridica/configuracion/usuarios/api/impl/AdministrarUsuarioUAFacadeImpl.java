/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.InfRoleUnidadAdmin;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.AccionesBitacoraConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.model.BitacoraAU;
import mx.gob.sat.siat.juridica.base.dao.domain.model.InformacionUsuario;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioRolUnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.base.dto.transformer.InformacionUsuarioDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.UnidadAdministrativaDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.cal.dto.transformer.TipoTramiteDTOTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.AdministrarUsuarioUAFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.BitacoraDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.UsuarioRolUnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer.BitacoraAUDTOTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer.UsuarioRolUnidadAdministrativaDTOTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.AdministrarUsuarioUAService;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.BitacorAUService;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.UsuarioRolUnidadAdministrativaServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.util.exception.TreasPendientesException;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.InfRoleUnidadAdminDTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */

@Component("administrarUsuarioUAFacade")
public class AdministrarUsuarioUAFacadeImpl extends BaseFacadeImpl implements AdministrarUsuarioUAFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -639401726012088299L;

    @Autowired
    private transient UsuarioRolUnidadAdministrativaServices usuarioRolService;

    @Autowired
    private TipoTramiteDTOTransformer tipoTramiteDTOTransformer;

    @Autowired
    private transient AdministrarUsuarioUAService administrarUsuarioUAService;

    @Autowired
    private InfRoleUnidadAdminDTOTransformer infRoleUnidadAdminDTOTransformer;

    @Autowired
    private InformacionUsuarioDTOTransformer infoUsuarioDTOTransformer;

    @Autowired
    private UnidadAdministrativaDTOTransformer unidadTransformer;

    @Autowired
    private BitacorAUService bitacorAUService;

    @Autowired
    private BitacoraAUDTOTransformer bitacoraTransformer;

    @Autowired
    private UsuarioRolUnidadAdministrativaDTOTransformer usuarioRolUnidadAdministrativaDTOTransformer;

    @Override
    public List<InformacionUsuarioDTO> obtenerTodosRolesFuncionario(String idUsuario) {
        List<InformacionUsuario> rolesUsuario = administrarUsuarioUAService.obtenerRolesFuncionario(idUsuario);
        List<InformacionUsuarioDTO> comboRolesDTO = new ArrayList<InformacionUsuarioDTO>();
        if (rolesUsuario != null && !rolesUsuario.isEmpty()) {
            for (InformacionUsuario rol : rolesUsuario) {
                InformacionUsuarioDTO rolDTO = infoUsuarioDTOTransformer.transformarModel(rol);
                comboRolesDTO.add(rolDTO);
            }
        }

        return comboRolesDTO;
    }

    @Override
    public List<TipoTramiteDTO> obtenerModalidadesAsignar() {
        List<TipoTramiteDTO> modalidades = new ArrayList<TipoTramiteDTO>();
        List<TipoTramite> servs = usuarioRolService.obtenerModalidadesAsignar();
        for (TipoTramite tram : servs) {
            TipoTramiteDTO tramDTO = tipoTramiteDTOTransformer.transformarDTO(tram);
            modalidades.add(tramDTO);
        }
        return modalidades;
    }

    @Override
    public List<InfRoleUnidadAdminDTO> obtenerPermisosPorFuncionario(String rfc, String unidadAdmin, String rol) {
        List<InfRoleUnidadAdminDTO> modalidadesAsignadas = new ArrayList<InfRoleUnidadAdminDTO>();
        List<InfRoleUnidadAdmin> modalidadesModel =
                administrarUsuarioUAService.obtenerPermisosPorFuncionario(rfc, unidadAdmin, rol);
        for (InfRoleUnidadAdmin modalidad : modalidadesModel) {
            InfRoleUnidadAdminDTO infDTO = infRoleUnidadAdminDTOTransformer.transformarDTO(modalidad);
            modalidadesAsignadas.add(infDTO);
        }
        return modalidadesAsignadas;
    }

    @Override
    public List<String> guardarModalidadesAsignadas(List<TipoTramiteDTO> listaSelected, UnidadAdministrativaDTO unidad,
            InformacionUsuarioDTO funcionario) throws TreasPendientesException {
        List<TipoTramite> modalidadesModel = tipoTramiteDTOTransformer.transformarModels(listaSelected);
        UnidadAdministrativa unid = unidadTransformer.transformarModel(unidad);
        InformacionUsuario usuario = infoUsuarioDTOTransformer.transformarDTO(funcionario);
        List<String> tramitesResponsablesRepetidos = new ArrayList<String>();
        try {
            tramitesResponsablesRepetidos =
                    administrarUsuarioUAService.actualizarTramitesAsignadosFuncionario(modalidadesModel, unid, usuario);
            if (tramitesResponsablesRepetidos == null) {
                // Guardado en bitacora de AU
                for (TipoTramiteDTO tipoTramite : listaSelected) {
                    BitacoraDTO bitacoraDTO = new BitacoraDTO();
                    bitacoraDTO.setRfcEmpleadoRealiza(getUserProfile().getRfc());
                    bitacoraDTO.setRfcAplicadoA(funcionario.getIdUsuario());
                    bitacoraDTO.setIdUniAdmin(unidad.getClave());
                    bitacoraDTO.setNomUniAdmin(unidad.getNombre());
                    bitacoraDTO.setIdRol(getUserProfile().getRol());
                    bitacoraDTO.setIdTipoTramite(tipoTramite.getIdTipoTramite());
                    if (tipoTramite.getModalidad() != null) {
                        bitacoraDTO.setModalidad(Long.valueOf(tipoTramite.getModalidad()));
                    }
                    bitacoraDTO.setIdRealizadoPor(AccionesBitacoraConstants.ACCION_REALIZA_EMPLEADO);
                    bitacoraDTO.setIdAplicadoA(AccionesBitacoraConstants.ACCION_REALIZA_EMPLEADO_APLICA);
                    bitacoraDTO.setIdAccion(AccionesBitacoraConstants.ASIGNAR_PERMISO_ADMINISTRADOR_GLOBAL);
                    bitacoraDTO.setDescripcion(tipoTramite.getDescripcionModalidad());
                    bitacoraDTO.setIdAccion(AccionesBitacoraConstants.ASIGNAR_PERMISO_TRAMITE);
                    BitacoraAU bitacora = bitacoraTransformer.tranformar(bitacoraDTO);
                    bitacorAUService.guardarDatosBitacora(bitacora);
                }
            }
        }
        catch (TreasPendientesException e) {
            getLogger().debug(e.toString());
        }
        return tramitesResponsablesRepetidos;
    }


    @Override
    public List<ServicioDTO> obtenerServiciosAsignar(String idUnidad) {
        List<ServicioDTO> servicios = new ArrayList<ServicioDTO>();
        List<TipoTramite> tiposTram = usuarioRolService.buscarTramitesAsignadosUnidad(idUnidad);

        for (TipoTramite ser : tiposTram) {
            ServicioDTO servicio = new ServicioDTO();
            List<TipoTramite> servs = usuarioRolService.obtenerTiposDeModalidadByTipoTramite(ser.getServicio());
            List<TipoTramiteDTO> modalidades = new ArrayList<TipoTramiteDTO>();
            for (TipoTramite tram : servs) {

                TipoTramiteDTO tramDTO = tipoTramiteDTOTransformer.transformarDTO(tram);
                modalidades.add(tramDTO);
            }
            servicio.setModalidades(modalidades);
            servicio.setServicio(ser.getServicio());
            servicio.setDescripcionServicio(ser.getDescripcionServicio());
            servicios.add(servicio);

        }
        return servicios;
    }

    @Override
    public List<UsuarioRolUnidadAdministrativaDTO>
            obtenerPermisosDeUsuarioPorUnidad(String idUniAdmin, String idUsuario) {
        List<UsuarioRolUnidadAdministrativaDTO> permisosUADTO = new ArrayList<UsuarioRolUnidadAdministrativaDTO>();
        List<UsuarioRolUnidadAdministrativa> rolesUA =
                administrarUsuarioUAService.obtenerPermisosDeUsuarioPorUnidad(idUniAdmin, idUsuario);
        if (null != rolesUA) {
            for (UsuarioRolUnidadAdministrativa rolUA : rolesUA) {
                permisosUADTO.add(usuarioRolUnidadAdministrativaDTOTransformer.transformarDTO(rolUA));
            }
        }
        return permisosUADTO;
    }

}
