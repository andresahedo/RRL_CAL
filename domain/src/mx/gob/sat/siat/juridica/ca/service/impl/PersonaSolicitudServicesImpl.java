/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.service.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaSolicitud;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.ca.dao.PersonaSolicitudDAO;
import mx.gob.sat.siat.juridica.ca.service.PersonaSolicitudServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ivan.guzman
 * 
 */
@Service
public class PersonaSolicitudServicesImpl extends BaseSerializableBusinessServices implements PersonaSolicitudServices {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "personaSolicitudDAO" tipo PersonaSolicitudDAO
     */
    @Autowired
    private PersonaSolicitudDAO personaSolicitudDAO;

    /**
     * Metodo para obtener una lista de solicitudes por id
     * 
     * @param idSolicitud
     * @return
     */
    public List<PersonaSolicitud> obtenerPersonasSolicitudByIdSol(Long idSolicitud) {
        return personaSolicitudDAO.obtenerPersonasSolicitudByIdSol(idSolicitud);
    }

}
