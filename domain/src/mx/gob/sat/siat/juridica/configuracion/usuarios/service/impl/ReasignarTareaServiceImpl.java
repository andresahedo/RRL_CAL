/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sat.siat.juridica.base.dao.PersonaDao;
import mx.gob.sat.siat.juridica.base.dao.TipoTramiteDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.base.service.AsignacionTramiteServices;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.RolesDao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.ReasignarTareaService;



/**
 * 
 * @author softtek
 * 
 */
@Service("reasignarTareaService")
public class ReasignarTareaServiceImpl extends BaseSerializableBusinessServices implements ReasignarTareaService {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 728557768371111733L;

    /**
     * Atributo asignacionTramiteServices tipo
     * asignacionTramiteServices
     */
    @Autowired
    private transient AsignacionTramiteServices asignacionTramiteServices;

    /**
 

    /**
     * Atributo personaDao tipo PersonaDao
     */
    @Autowired
    private PersonaDao personaDao;

    @Autowired
    private RolesDao rolesDao;

    /**
     * Inyeccion de Dao de enumeracion
     */


    @Autowired
    private TipoTramiteDAO tipoTramiteDAO;

    /**
     * Metodo para firmar y reasignar un asunto
     * 
     * @param idTarea
     * @param rfcAsignar
     */
    @Override
    public void reasignarTareas(List<String> idAsuntos, List<String> idTareas, String permiso, String rfcAsignar,
            String reasignador) {
        for (String idAsunto : idAsuntos) {
            asignacionTramiteServices.guardarAsignacionTramite(idAsunto, rfcAsignar, permiso);
        }
    }

    @Override
    public void cambiaVariableRol(List<String> idAsuntos, List<Long> idInstancias, String permiso, String rfcAsignar) {
        for (String idAsunto : idAsuntos) {
            asignacionTramiteServices.guardarAsignacionTramite(idAsunto, rfcAsignar, permiso);
        }
    }


    public List<TipoTramite> obtenerTiposDeTramite() {
        return tipoTramiteDAO.buscarTramites();
    }

    public List<TipoTramite> obtenerTiposDeModalidadByTipoTramite(String idServicio) {
        return tipoTramiteDAO.obtenerTodosTramites(idServicio);
    }

    public List<Role> obtenerRolesByIdRol(List<String> roles) {
        List<Role> role = new ArrayList<Role>();
        for (String claveRol : roles) {
            role.add(rolesDao.obtenerRolePorIdRol(claveRol));
        }
        return role;
    }

    public List<Persona> obtenerPersonaAbogados(String idTipoTramite, String claveUsuario, String cveUniAdmin,
            PersonaInterna empleado) {
        return personaDao.buscarPersonasByRolReasignador(idTipoTramite, claveUsuario, cveUniAdmin, empleado);
    }


}
