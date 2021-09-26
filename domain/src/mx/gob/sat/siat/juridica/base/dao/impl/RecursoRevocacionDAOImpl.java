/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.RecursoRevocacionDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.model.SolicitudDatosGenerales;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author softtek
 * 
 */
@Repository
public class RecursoRevocacionDAOImpl extends BaseJPADao implements RecursoRevocacionDAO {

    /**
     * 
     */
    private static final long serialVersionUID = -8555517916251442369L;

    /**
     * Metodo para obtener una solicitud de recurso de revocacion por
     * id de solicitud
     */
    @Override
    public SolicitudDatosGenerales obtenerSolicitudRRPorId(Long idSolicitud) {

        return getEntityManager().find(SolicitudDatosGenerales.class, idSolicitud);
    }

}
