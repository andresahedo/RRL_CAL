package mx.gob.sat.siat.juridica.configuracion.usuarios.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;

import java.util.List;

public interface ReasignarTareaService {

    void reasignarTareas(List<String> idAsuntos, List<String> idTareas, String permiso, String rfcAsignar,
            String reasignador);

    void cambiaVariableRol(List<String> idAsuntos, List<Long> idInstancias, String permiso, String rfcAsignar);

   
    List<TipoTramite> obtenerTiposDeTramite();

    List<TipoTramite> obtenerTiposDeModalidadByTipoTramite(String idServicio);

    List<Role> obtenerRolesByIdRol(List<String> roles);

    List<Persona> obtenerPersonaAbogados(String idTipoTramite, String claveUsuario, String cveUniAdmin,
            PersonaInterna empleado);
}
