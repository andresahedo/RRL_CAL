/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitante;
import mx.gob.sat.siat.juridica.base.service.SolicitanteServices;
import mx.gob.sat.siat.juridica.rrl.dao.RegistroRecursoRevocacionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author softtek
 * 
 */
@Service
public class SolicitanteServicesImpl extends BaseSerializableBusinessServices implements SolicitanteServices {

    /**
     * Id de la version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "registroRecursoRevocacionDAO" tipo
     * RegistroRecursoRevocacionDAO
     */
    @Autowired
    private RegistroRecursoRevocacionDAO registroRecursoRevocacionDAO;

    /**
     * Metodo para obtener un dolicitante por id de solicitud
     * 
     * @param idSolicitud
     * @return Solicitante
     */
    public Solicitante obtenerSolicitanteByIdSol(Long idSolicitud) {
        Solicitante solicitante = registroRecursoRevocacionDAO.getSolicituanteByRfc(idSolicitud);
        if (null != solicitante) {
            if (null != solicitante.getDomicilio()) {
                solicitante.getDomicilio().getClaveAdministracionLocalRecaudadora();
            }
        }

        return registroRecursoRevocacionDAO.getSolicituanteByRfc(idSolicitud);
    }

}
