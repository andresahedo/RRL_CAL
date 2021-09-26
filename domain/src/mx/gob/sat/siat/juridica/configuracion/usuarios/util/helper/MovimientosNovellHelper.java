package mx.gob.sat.siat.juridica.configuracion.usuarios.util.helper;

import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.dao.InformacionUsuarioDao;
import mx.gob.sat.siat.juridica.base.dao.PersonaDao;
import mx.gob.sat.siat.juridica.base.dao.UserStateDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.AccionesBitacoraConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.ActualizarMovimientosConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.BitacoraAUDAO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.MovimientosDao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.UsuarioRolUnidadAdministrativaDao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.DatosCorreoNotificacion;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.Movimiento;
import mx.gob.sat.siat.juridica.configuracion.usuarios.util.constants.ExcepcionesEnum;
import mx.gob.sat.siat.juridica.configuracion.usuarios.util.constants.RolEnum;
import mx.gob.sat.siat.juridica.configuracion.usuarios.util.constants.TipoMovimientoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MovimientosNovellHelper extends BaseHelper {

    /**
     * 
     */
    private static final long serialVersionUID = -3228362397006015802L;

    @Autowired
    private PersonaDao personaDao;

    @Autowired
    private InformacionUsuarioDao informacionUsuarioDao;

    @Autowired
    private UserStateDao userStateDao;

    @Autowired
    private BitacoraAUDAO bitacoraDao;

    @Autowired
    private MovimientosDao movimientosDao;

    @Autowired
    private UsuarioRolUnidadAdministrativaDao usuarioRolUnidadAdministrativaDao;

    public DatosCorreoNotificacion validarTipoMovIndicadorRol(TipoMovimientoEnum tipoMov, boolean indicador,
            String rol, DatosCorreoNotificacion datos) {
        switch (tipoMov) {
        case ALTA_ROL:
        case BAJA_ROL:
            if (!indicador) {
                datos.setExcepcion(ExcepcionesEnum.EX_MOV_INDICADOR);
            }
            else {
                if (rol != null && !rol.equals("")) {
                    RolEnum descripcionRol = RolEnum.obtenerRolPorClave(rol);
                    if (descripcionRol != null) {
                        datos.setDescripcionPermiso(descripcionRol.getDescripcion());
                    }
                    else {
                        datos.setExcepcion(ExcepcionesEnum.EX_ROL_NO_RECONOCIDO);
                    }
                }
                else {
                    datos.setExcepcion(ExcepcionesEnum.EX_SIN_ROL_OBLIGATORIO);
                }
            }
            break;
        case ALTA_EMPLEADO:
        case MODIFICACION:
            if (!indicador) {
                datos.setExcepcion(ExcepcionesEnum.EX_MOV_INDICADOR);
            }
            else {
                if (rol != null && !rol.equals("")) {
                    RolEnum descripcionRol = RolEnum.obtenerRolPorClave(rol);
                    if (descripcionRol != null) {
                        datos.setDescripcionPermiso(descripcionRol.getDescripcion());
                    }
                    else {
                        datos.setExcepcion(ExcepcionesEnum.EX_ROL_NO_RECONOCIDO);
                    }
                }
            }
            break;
        case BAJA_TEMPORAL:
        case BAJA_DEFINITIVA:
            if (indicador) {
                datos.setExcepcion(ExcepcionesEnum.EX_MOV_INDICADOR);
            }
            else {
                if (rol != null && !rol.equals("")) {
                    RolEnum descripcionRol = RolEnum.obtenerRolPorClave(rol);
                    if (descripcionRol != null) {
                        datos.setDescripcionPermiso(descripcionRol.getDescripcion());
                    }
                    else {
                        datos.setExcepcion(ExcepcionesEnum.EX_ROL_NO_RECONOCIDO);
                    }
                }
            }
            break;
        case ERROR:
            break;
        }
        return datos;
    }

    public List<String> obtenerCorreosAltaRol(String rfc) {
        List<String> correos = new ArrayList<String>();
        String correoAdministradorUAPrincipal = obtenerCorreoAdministradorUAPrincipal(rfc);
        if (correoAdministradorUAPrincipal != null) {
            correos.add(correoAdministradorUAPrincipal);
        }
        else {
            correos = obtenerCorreosAdminGlobal();
        }
        return correos;
    }

    public List<String> obtenerCorreosBajaModificacion(String rfc) {
        List<String> correos = obtenerCorreosAdministradoresRelacionados(rfc);
        if (correos.isEmpty()) {
            correos = obtenerCorreosAdminGlobal();
        }
        return correos;
    }

    public String obtenerCorreoAdministradorUAPrincipal(String rfc) {
        String correo = null;
        List<UsuarioRolUnidadAdministrativa> uniAdminPrincipal =
                getUsuarioRolUnidadAdministrativaDao().obtenerRolesUsuario(rfc);
        if (!uniAdminPrincipal.isEmpty()) {
            UnidadAdministrativa unidadAdministrativa =
                    getUsuarioRolUnidadAdministrativaDao().obtenerRolesUsuario(rfc).get(0).getUnidadAdministrativa();
            String rfcAdministrador =
                    getUsuarioRolUnidadAdministrativaDao().obtenerIdAdministradorUA(unidadAdministrativa).getUsuario()
                            .getUserName();
            correo = getPersonaDao().obtenerPersonaPorRFC(rfcAdministrador).getCorreoElectronico();
        }
        return correo;
    }

    public List<String> obtenerCorreosAdminGlobal() {
        List<InformacionUsuario> adminsGlobales =
                getInformacionUsuarioDao().buscarUsuarioPorRol(RolesConstantes.ROL_INTERNO_ADMINISTRADOR_GLOBAL);
        List<String> direcciones = new ArrayList<String>();
        for (InformacionUsuario admin : adminsGlobales) {
            Persona pers = getPersonaDao().obtenerPersonaPorRFC(admin.getInformacionUsuarioPK().getIdUsuario());
            direcciones.add(pers.getCorreoElectronico());
        }
        return direcciones;
    }

    public List<String> obtenerCorreosAdministradoresRelacionados(String rfc) {
        List<String> correos = new ArrayList<String>();
        List<UnidadAdministrativa> unidadesAdministrativas = new ArrayList<UnidadAdministrativa>();
        List<String> idAdministradoresUA = new ArrayList<String>();
        List<UsuarioRolUnidadAdministrativa> listaRoles =
                getUsuarioRolUnidadAdministrativaDao().obtenerTodosRolesFuncionario(rfc);
        if (!listaRoles.isEmpty()) {
            for (UsuarioRolUnidadAdministrativa usuario : listaRoles) {
                unidadesAdministrativas.add(usuario.getUnidadAdministrativa());
            }
            for (UnidadAdministrativa unidad : unidadesAdministrativas) {
                idAdministradoresUA.add(getUsuarioRolUnidadAdministrativaDao().obtenerIdAdministradorUA(unidad)
                        .getUsuario().getUserName());
            }
            for (String persona : idAdministradoresUA) {
                correos.add(getPersonaDao().obtenerPersonaPorRFC(persona).getCorreoElectronico());
            }
        }
        return correos;
    }

    public void actualizarMovimiento(Movimiento mov, List<String> correos, ExcepcionesEnum excepcion) {
        if (!mov.getIdeTipoMovto().equals(TipoMovimientoEnum.ERROR.getClave())) {
            if (correos != null) {
                if (correos.isEmpty()) {
                    mov.setIdeEstadoMovto(ActualizarMovimientosConstants.EDO_PROCESADO);
                }
                else {
                    mov.setIdeEstadoMovto(ActualizarMovimientosConstants.EDO_NOTIFICADO);
                    mov.setFechaNotificado(new Date());
                }
            }
            if (excepcion != null) {
                mov.setIdeExMovto(excepcion.getClave());
            }
        }
        mov.setFechaProcesadoEx(new Date());
        getMovimientosDao().modificarMovimiento(mov);
    }

    public String obtenerNombreCompleto(String nom, String apPaterno, String apMaterno) {
        StringBuffer nombre = new StringBuffer();
        nombre.append(nom);
        nombre.append(" ");
        nombre.append(apPaterno);
        nombre.append(" ");
        nombre.append(apMaterno);
        return nombre.toString();
    }

    public DatosCorreoNotificacion procesarAltaEmpleado(DatosCorreoNotificacion datosCorreo) {
        UserState usr = getUserStateDao().obtenerUsuario(datosCorreo.getRfc());
        if (getPersonaDao().obtenerPersonaPorRFC(datosCorreo.getRfc()) == null) {

            UserCredentials credenciales = new UserCredentials();
            credenciales.setUserName(datosCorreo.getRfc());

            UserState usuario = new UserState(credenciales);
            usuario.setActive(true);

            Date fecha = new Date();

            PersonaInterna persona = new PersonaInterna();
            persona.setNumeroEmpleado(datosCorreo.getNumeroEmpleado());
            persona.setNombre(datosCorreo.getNombre());
            persona.setApellidoPaterno(datosCorreo.getApPaterno());
            persona.setApellidoMaterno(datosCorreo.getApMaterno());
            persona.setRfc(datosCorreo.getRfc());
            persona.setRfcCorto(datosCorreo.getRfcCorto());
            persona.setCorreoElectronico(datosCorreo.getMail());
            persona.setFechaRegistro(fecha);
            persona.setClaveUsuario(datosCorreo.getRfc());

            BitacoraAU bitacora = new BitacoraAU();
            bitacora.setFechaAccion(fecha);
            bitacora.setIdRealizadoPor(ActualizarMovimientosConstants.ACTOR_SISTEMA);
            bitacora.setIdAccion(AccionesBitacoraConstants.ALTA_EMPLEADO);
            bitacora.setIdAplicadoA(ActualizarMovimientosConstants.ACTOR_APLICADO_EMPLEADO);
            bitacora.setNumEmpleadoAA(datosCorreo.getNumeroEmpleado().toString());
            bitacora.setRfcAA(datosCorreo.getRfc());
            bitacora.setNombreAA(this.obtenerNombreCompleto(datosCorreo.getNombre(), datosCorreo.getApPaterno(),
                    datosCorreo.getApPaterno()));

            if (getUserStateDao().insertarUsuario(usuario) != null && getPersonaDao().insertarPersona(persona) != null
                    && getBitacoraDao().insertarMovimientoBitacora(bitacora) != null) {
                datosCorreo.setDescripcionEdoActualizacion(ActualizarMovimientosConstants.EDO_EXITOSO_DESC);
            }
            else {
                datosCorreo.setDescripcionEdoActualizacion(ActualizarMovimientosConstants.EDO_EXCEPCION_DESC);
            }
        }
        else if (usr != null && !usr.isActive()) {
            usr.setActive(true);
            BitacoraAU bitacora = new BitacoraAU();
            bitacora.setFechaAccion(new Date());
            bitacora.setIdRealizadoPor(ActualizarMovimientosConstants.ACTOR_SISTEMA);
            bitacora.setIdAccion(AccionesBitacoraConstants.ALTA_EMPLEADO);
            bitacora.setIdAplicadoA(ActualizarMovimientosConstants.ACTOR_APLICADO_EMPLEADO);
            bitacora.setNumEmpleadoAA(datosCorreo.getNumeroEmpleado().toString());
            bitacora.setRfcAA(datosCorreo.getRfc());
            bitacora.setNombreAA(this.obtenerNombreCompleto(datosCorreo.getNombre(), datosCorreo.getApPaterno(),
                    datosCorreo.getApPaterno()));

            getUserStateDao().modificarUsuario(usr);
            if (getBitacoraDao().insertarMovimientoBitacora(bitacora) != null) {
                datosCorreo.setDescripcionEdoActualizacion(ActualizarMovimientosConstants.EDO_EXITOSO_DESC);
            }
            else {
                datosCorreo.setDescripcionEdoActualizacion(ActualizarMovimientosConstants.EDO_EXCEPCION_DESC);
            }
        }
        else {
            datosCorreo.setExcepcion(ExcepcionesEnum.EX_USUARIO_REGISTRADO);
        }
        datosCorreo.setCorreos(this.obtenerCorreosAdminGlobal());
        return datosCorreo;
    }

    public DatosCorreoNotificacion procesarAltaRol(DatosCorreoNotificacion datosCorreo, String idRol) {
        Date fecha = new Date();
        UserState usuario = getUserStateDao().obtenerUsuario(datosCorreo.getRfc());
        Persona personaSIAT = getPersonaDao().obtenerPersonaPorRFC(datosCorreo.getRfc());
        if (personaSIAT != null && usuario.isActive()) {
            List<InformacionUsuario> roles = getInformacionUsuarioDao().buscarRolesPorId(datosCorreo.getRfc());
            InformacionUsuario rolInactivo = null;
            for (InformacionUsuario info : roles) {
                if (info.getInformacionUsuarioPK().getIdRol().equals(idRol) && info.getBlnActivo().equals(true)) {
                    datosCorreo.setExcepcion(ExcepcionesEnum.EX_ROL_DUPLICADO);
                }
                else if (info.getInformacionUsuarioPK().getIdRol().equals(idRol) && info.getBlnActivo().equals(false)) {
                    rolInactivo = info;
                }
            }
            if (datosCorreo.getExcepcion() == null) {
                if (rolInactivo == null) {
                    InformacionUsuario info = new InformacionUsuario();
                    InformacionUsuarioPK infoPK = new InformacionUsuarioPK();
                    infoPK.setIdRol(idRol);
                    infoPK.setIdUsuario(datosCorreo.getRfc());
                    info.setInformacionUsuarioPK(infoPK);
                    info.setBlnActivo(true);
                    info.setFechaInicio(fecha);
                    info.setRolReplica(false);
                    getInformacionUsuarioDao().insertarRolUsuario(info);
                }
                else {
                    rolInactivo.setBlnActivo(true);
                    getInformacionUsuarioDao().modificarRolUsuario(rolInactivo);
                }

                BitacoraAU bitacora = new BitacoraAU();
                bitacora.setFechaAccion(fecha);
                bitacora.setIdRealizadoPor(ActualizarMovimientosConstants.ACTOR_SISTEMA);
                bitacora.setIdAccion(AccionesBitacoraConstants.ALTA_ROL);
                bitacora.setDescripcion(datosCorreo.getDescripcionPermiso());
                bitacora.setIdAplicadoA(ActualizarMovimientosConstants.ACTOR_APLICADO_EMPLEADO);
                bitacora.setNumEmpleadoAA(datosCorreo.getNumeroEmpleado().toString());
                bitacora.setRfcAA(datosCorreo.getRfc());
                bitacora.setNombreAA(this.obtenerNombreCompleto(datosCorreo.getNombre(), datosCorreo.getApPaterno(),
                        datosCorreo.getApPaterno()));

                if (getBitacoraDao().insertarMovimientoBitacora(bitacora).getIdBitacora() != null) {
                    datosCorreo.setDescripcionEdoActualizacion(ActualizarMovimientosConstants.EDO_EXITOSO_DESC);
                }
                else {
                    datosCorreo.setDescripcionEdoActualizacion(ActualizarMovimientosConstants.EDO_EXCEPCION_DESC);
                }
            }
        }
        else {
            datosCorreo.setExcepcion(ExcepcionesEnum.EX_USUARIO_NO_ACTIVO_REGISTRADO);
            datosCorreo.setCorreos(obtenerCorreosAdminGlobal());
        }

        datosCorreo.setCorreos(obtenerCorreosAltaRol(datosCorreo.getRfc()));

        return datosCorreo;
    }

    public DatosCorreoNotificacion procesarBajaRol(DatosCorreoNotificacion datosCorreo, String rol,
            Integer tareasPendientes) {
        UserState usuario = getUserStateDao().obtenerUsuario(datosCorreo.getRfc());
        Persona personaSIAT = getPersonaDao().obtenerPersonaPorRFC(datosCorreo.getRfc());
        if (personaSIAT != null && usuario.isActive()) {
            InformacionUsuario infoRolUsuario = null;
            for (InformacionUsuario rolUsu : getInformacionUsuarioDao().buscarRolesActivosPorId(datosCorreo.getRfc())) {
                if (rolUsu.getInformacionUsuarioPK().getIdRol().equals(rol)) {
                    infoRolUsuario = rolUsu;
                }
            }
            if (infoRolUsuario != null) {
                infoRolUsuario.setBlnActivo(false);
                if (tareasPendientes != null) {
                    getInformacionUsuarioDao().modificarRolUsuario(infoRolUsuario);
                    if (tareasPendientes > 0) {
                        datosCorreo.setExcepcion(ExcepcionesEnum.EX_TAREAS_PENDIENTES);
                    }
                }
                else {
                    datosCorreo.setExcepcion(ExcepcionesEnum.EX_DETERMINAR_TAREAS);
                }
            }
            else {
                datosCorreo.setExcepcion(ExcepcionesEnum.EX_ROL_NO_ASOCIADO);
            }

            BitacoraAU bitacora = new BitacoraAU();
            bitacora.setFechaAccion(new Date());
            bitacora.setIdRealizadoPor(ActualizarMovimientosConstants.ACTOR_SISTEMA);
            bitacora.setIdAccion(AccionesBitacoraConstants.BAJA_ROL_EMPLEADO);
            bitacora.setDescripcion(datosCorreo.getDescripcionPermiso());
            bitacora.setIdAplicadoA(ActualizarMovimientosConstants.ACTOR_APLICADO_EMPLEADO);
            bitacora.setNumEmpleadoAA(datosCorreo.getNumeroEmpleado().toString());
            bitacora.setRfcAA(datosCorreo.getRfc());
            bitacora.setNombreAA(this.obtenerNombreCompleto(datosCorreo.getNombre(), datosCorreo.getApPaterno(),
                    datosCorreo.getApPaterno()));

            if (getBitacoraDao().insertarMovimientoBitacora(bitacora).getIdBitacora() != null) {
                datosCorreo.setDescripcionEdoActualizacion(ActualizarMovimientosConstants.EDO_EXITOSO_DESC);
            }
            else {
                datosCorreo.setDescripcionEdoActualizacion(ActualizarMovimientosConstants.EDO_EXCEPCION_DESC);
            }

            datosCorreo.setCorreos(obtenerCorreosBajaModificacion(datosCorreo.getRfc()));
        }
        else {
            datosCorreo.setExcepcion(ExcepcionesEnum.EX_USUARIO_NO_ACTIVO_REGISTRADO);
            datosCorreo.setCorreos(obtenerCorreosAdminGlobal());
        }
        return datosCorreo;
    }

    public DatosCorreoNotificacion procesarBajaEmpleado(DatosCorreoNotificacion datosCorreo, String idRol,
            Integer tareasPendientes, String accion) {
        UserState usuario = getUserStateDao().obtenerUsuario(datosCorreo.getRfc());
        Persona personaSIAT = getPersonaDao().obtenerPersonaPorRFC(datosCorreo.getRfc());
        if (personaSIAT != null && usuario.isActive()) {
            usuario.setActive(false);

            if (tareasPendientes != null) {
                if (tareasPendientes > 0) {
                    datosCorreo.setExcepcion(ExcepcionesEnum.EX_TAREAS_PENDIENTES);
                }
            }
            else {
                datosCorreo.setExcepcion(ExcepcionesEnum.EX_DETERMINAR_TAREAS);
            }

            BitacoraAU bitacora = new BitacoraAU();
            bitacora.setFechaAccion(new Date());
            bitacora.setIdRealizadoPor(ActualizarMovimientosConstants.ACTOR_SISTEMA);
            bitacora.setIdAccion(accion);
            bitacora.setDescripcion(datosCorreo.getDescripcionPermiso());
            bitacora.setIdAplicadoA(ActualizarMovimientosConstants.ACTOR_APLICADO_EMPLEADO);
            bitacora.setNumEmpleadoAA(datosCorreo.getNumeroEmpleado().toString());
            bitacora.setRfcAA(datosCorreo.getRfc());
            bitacora.setNombreAA(this.obtenerNombreCompleto(datosCorreo.getNombre(), datosCorreo.getApPaterno(),
                    datosCorreo.getApPaterno()));

            getUserStateDao().modificarUsuario(usuario);
            if (getBitacoraDao().insertarMovimientoBitacora(bitacora).getIdBitacora() != null) {
                datosCorreo.setDescripcionEdoActualizacion(ActualizarMovimientosConstants.EDO_EXITOSO_DESC);
            }
            else {
                datosCorreo.setDescripcionEdoActualizacion(ActualizarMovimientosConstants.EDO_EXCEPCION_DESC);
            }
        }
        else {
            datosCorreo.setExcepcion(ExcepcionesEnum.EX_USUARIO_NO_ACTIVO_REGISTRADO);
            datosCorreo.setCorreos(obtenerCorreosAdminGlobal());
        }

        datosCorreo.setCorreos(obtenerCorreosBajaModificacion(datosCorreo.getRfc()));
        return datosCorreo;
    }

    public DatosCorreoNotificacion
            procesarModificacionDatos(Movimiento movimiento, DatosCorreoNotificacion datosCorreo) {
        UserState usuario = getUserStateDao().obtenerUsuario(datosCorreo.getRfc());
        Persona personaSIAT = getPersonaDao().obtenerPersonaPorRFC(datosCorreo.getRfc());
        if (personaSIAT != null && usuario.isActive()) {
            if (movimiento.getRfcCorto().equals(datosCorreo.getRfcCorto())
                    && movimiento.getNumEmpleado().equals(datosCorreo.getNumeroEmpleado())) {

                personaSIAT.setNombre(datosCorreo.getNombre());
                personaSIAT.setApellidoPaterno(datosCorreo.getApPaterno());
                personaSIAT.setApellidoMaterno(datosCorreo.getApMaterno());
                personaSIAT.setCorreoElectronico(datosCorreo.getMail());

                BitacoraAU bitacora = new BitacoraAU();
                bitacora.setFechaAccion(new Date());
                bitacora.setIdRealizadoPor(ActualizarMovimientosConstants.ACTOR_SISTEMA);
                bitacora.setIdAccion(AccionesBitacoraConstants.MODIFICACION_DATOS_EMPLEADO);
                bitacora.setDescripcion(datosCorreo.getDescripcionPermiso());
                bitacora.setIdAplicadoA(ActualizarMovimientosConstants.ACTOR_APLICADO_EMPLEADO);
                bitacora.setNumEmpleadoAA(datosCorreo.getNumeroEmpleado().toString());
                bitacora.setRfcAA(datosCorreo.getRfc());
                bitacora.setNombreAA(this.obtenerNombreCompleto(datosCorreo.getNombre(), datosCorreo.getApPaterno(),
                        datosCorreo.getApPaterno()));

                getPersonaDao().modificarPersona(personaSIAT);
                if (getBitacoraDao().insertarMovimientoBitacora(bitacora).getIdBitacora() != null) {
                    datosCorreo.setDescripcionEdoActualizacion(ActualizarMovimientosConstants.EDO_EXITOSO_DESC);
                }
                else {
                    datosCorreo.setDescripcionEdoActualizacion(ActualizarMovimientosConstants.EDO_EXCEPCION_DESC);
                }
            }
            else {
                datosCorreo.setExcepcion(ExcepcionesEnum.EX_INFO_NO_PERMITIDA);
            }
            datosCorreo.setCorreos(obtenerCorreosBajaModificacion(datosCorreo.getRfc()));
        }
        else {
            datosCorreo.setExcepcion(ExcepcionesEnum.EX_USUARIO_NO_ACTIVO_REGISTRADO);
            datosCorreo.setCorreos(obtenerCorreosAdminGlobal());
        }
        return datosCorreo;
    }

    public DatosCorreoNotificacion notificarMovimientosProcesados(Movimiento movimiento,
            DatosCorreoNotificacion datosCorreo) {
        datosCorreo.setDescripcionEdoActualizacion(ActualizarMovimientosConstants.EDO_EXITOSO_DESC);
        TipoMovimientoEnum tipoMovimiento = TipoMovimientoEnum.parse(movimiento.getIdeTipoMovto());
        if (!datosCorreo.getExcepcion().equals(ExcepcionesEnum.EX_ROL_NO_RECONOCIDO) && movimiento.getRol() != null) {
            datosCorreo.setDescripcionPermiso(RolEnum.obtenerRolPorClave(movimiento.getRol()).getDescripcion());
        }
        if (datosCorreo.getExcepcion().equals(ExcepcionesEnum.EX_USUARIO_NO_ACTIVO_REGISTRADO)) {
            datosCorreo.setCorreos(obtenerCorreosAdminGlobal());
        }
        else {
            switch (tipoMovimiento) {
            case ALTA_EMPLEADO:
                datosCorreo.setCorreos(obtenerCorreosAdminGlobal());
                break;
            case ALTA_ROL:
                datosCorreo.setCorreos(obtenerCorreosAltaRol(movimiento.getRfc()));
                break;
            case BAJA_DEFINITIVA:
            case BAJA_ROL:
            case BAJA_TEMPORAL:
            case MODIFICACION:
                datosCorreo.setCorreos(obtenerCorreosBajaModificacion(movimiento.getRfc()));
                break;
            case ERROR:
                break;
            }
        }
        return datosCorreo;
    }

    public PersonaDao getPersonaDao() {
        return personaDao;
    }

    public void setPersonaDao(PersonaDao personaDao) {
        this.personaDao = personaDao;
    }

    public InformacionUsuarioDao getInformacionUsuarioDao() {
        return informacionUsuarioDao;
    }

    public void setInformacionUsuarioDao(InformacionUsuarioDao informacionUsuarioDao) {
        this.informacionUsuarioDao = informacionUsuarioDao;
    }

    public UsuarioRolUnidadAdministrativaDao getUsuarioRolUnidadAdministrativaDao() {
        return usuarioRolUnidadAdministrativaDao;
    }

    public void
            setUsuarioRolUnidadAdministrativaDao(UsuarioRolUnidadAdministrativaDao usuarioRolUnidadAdministrativaDao) {
        this.usuarioRolUnidadAdministrativaDao = usuarioRolUnidadAdministrativaDao;
    }

    public UserStateDao getUserStateDao() {
        return userStateDao;
    }

    public void setUserStateDao(UserStateDao userStateDao) {
        this.userStateDao = userStateDao;
    }

    public BitacoraAUDAO getBitacoraDao() {
        return bitacoraDao;
    }

    public void setBitacoraDao(BitacoraAUDAO bitacoraDao) {
        this.bitacoraDao = bitacoraDao;
    }

    public MovimientosDao getMovimientosDao() {
        return movimientosDao;
    }

    public void setMovimientosDao(MovimientosDao movimientosDao) {
        this.movimientosDao = movimientosDao;
    }

}
