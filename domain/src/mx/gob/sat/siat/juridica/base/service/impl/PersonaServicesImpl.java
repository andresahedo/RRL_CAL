/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.PersonaDao;
import mx.gob.sat.siat.juridica.base.dao.PersonasInternasDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaSolicitud;
import mx.gob.sat.siat.juridica.base.service.PersonaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author softtek
 * 
 */
@Service("personaServices")
public class PersonaServicesImpl extends BaseBusinessServices implements PersonaServices {

    /**
     * Id de la version
     */
    private static final long serialVersionUID = -1554188860334033540L;

    /**
     * Atributo privado "userStateDao" tipo UserStateDao
     */
    @Autowired
    private PersonaDao personaDao;

    @Autowired
    private PersonasInternasDAO personasInternasDao;

    public Persona obtenerPersona(String rfc, String tipoPersona) {
        return personaDao.obtenerPersona(rfc, tipoPersona);
    }

    public void insertarPersona(Persona persona) {
        personaDao.insertarPersona(persona);
    }

    @Override
    public Persona obtenerPersonaPorRFC(String rfc) {
        return personaDao.obtenerPersonaPorRFC(rfc);
    }

    @Override
    public Persona obtenerPersonaPorRFCUsuarios(String rfc) {
        return personaDao.obtenerPersonaPorRFCUsuarios(rfc);
    }

    @Override
    public Persona obtenerPersonaPorRFCCorto(String rfc) {
        return personaDao.obtenerPersonaPorRFCCorto(rfc);
    }

    public String obtenerRfcCorto(String rfc) {
        return personaDao.obtenerRfcCorto(rfc);
    }

    /**
     * Metodo para buscar una persona solicitante
     */
    @Override
    public PersonaSolicitud buscarPersonaSolicitante(Long idSolicitud) {
        return personaDao.buscarPersonaSolicitante(idSolicitud);
    }

    @Override
    public String obtenerRfcCortoLike(String rfcFuncionario) {
        return personaDao.obtenerRfcCortoLike(rfcFuncionario);
    }

    @Override
    public PersonaInterna buscarPersonaPorRFC(String rfc) {
        return personasInternasDao.getPersonaInternaByRFC(rfc);
    }
}
