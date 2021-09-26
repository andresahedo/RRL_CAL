/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.AsignacionTramiteDao;
import mx.gob.sat.siat.juridica.base.dao.PersonasInternasDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.model.AsignacionTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.service.AsignacionTramiteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Service("asignacionTramiteServices")
public class AsignacionTramiteServicesImpl extends BaseSerializableBusinessServices implements
        AsignacionTramiteServices {

    /**
     * Id de la version
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private PersonasInternasDAO personaDao;

    @Autowired
    private AsignacionTramiteDao asignacionTramiteDao;

    @Override
    public AsignacionTramite guardarAsignacionTramite(String idTramite, String rfc, String rol) {
        AsignacionTramite asignacionTramite = new AsignacionTramite();
        PersonaInterna persona = personaDao.getPersonaInternaByRFC(rfc);
        Tramite tramite = new Tramite(idTramite);
        asignacionTramite.setPersona(persona);
        asignacionTramite.setTramite(tramite);
        asignacionTramite.setTipoRol(rol);
        asignacionTramite.setBlnActivo(true);
        return asignacionTramiteDao.guardarAsignacion(asignacionTramite);
    }

    @Override
    public AsignacionTramite buscarAsignacionesRolAsunto(String idTramite, String rol) {

        return asignacionTramiteDao.buscarAsignacionesRolAsunto(idTramite, rol);
    }

    public List<AsignacionTramite> buscarAsignacionesByRFC(String rfc) {
        return asignacionTramiteDao.buscarAsignacionesByRFC(rfc);
    }

    public List<AsignacionTramite> buscarAsignacionesByIdAsunto(String idAsunto) {
        return asignacionTramiteDao.buscarAsignacionesByIdAsunto(idAsunto);
    }

}
