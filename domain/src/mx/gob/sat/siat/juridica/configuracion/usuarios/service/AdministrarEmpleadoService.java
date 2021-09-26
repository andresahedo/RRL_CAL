/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.InformacionUsuario;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioRolUnidadAdministrativa;

import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface AdministrarEmpleadoService {

    void actualizarPermisos(List<InformacionUsuario> permisos, String idUnidad, String idUsuario);

    List<UsuarioRolUnidadAdministrativa> validarUnidadBase(String idUniAdmin, String idUsuario);

    void asignarUnidadBase();

    boolean buscarTareasPendientes();

    void actualizarRolReplica(List<InformacionUsuario> permisos);

    InformacionUsuario buscarRolReplica(List<InformacionUsuario> permisos);

    String validarUnidadBase(String idUsuario);
}
