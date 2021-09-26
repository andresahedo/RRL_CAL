/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.SolicitudDatosGenerales;
import mx.gob.sat.siat.juridica.base.util.constante.LoggerConstants;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.rrl.dao.SolicitudDAO;
import mx.gob.sat.siat.juridica.rrl.dao.domain.model.SolicitudRecursoRevocacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Repository
public class SolicitudDAOImpl implements SolicitudDAO {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo protegido "logger" tipo Logger
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(LoggerConstants.DOMAIN_REPOSITORY_LOGGER);

    /**
     * M&eacute;todo para obtener una solicitud por id. Incluye
     * TipoTramite embebido para recuperar su discriminador y
     * descripcion de modalidad por el servicio llamador.
     * 
     * @param idSolicitud
     * @return
     */
    @Override
    public Solicitud obtenerSolicitudporId(Long idSolicitud) {
        Query query =
                entityManager.createQuery("select s from Solicitud s where s.idSolicitud = :idSolicitud",
                        Solicitud.class);
        query.setParameter("idSolicitud", idSolicitud);
        Solicitud sol = (Solicitud) query.getSingleResult();
        if (sol != null) {
            sol.getTipoTramiteSolicitud();
            sol.getTipoTramiteSolicitud().getIdTipoTramite();
            sol.getTipoTramiteSolicitud().getDescripcionModalidad();
        }

        return sol;
    }

    /**
     * Atributo entityManager
     */
     @PersistenceContext
     private transient EntityManager entityManager;

    /**
     * 
     * @return entityManager
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Metodo para guardar una solicitud
     * 
     * @return solicitud
     */
    @Override
    public Solicitud guardarSolicitud(Solicitud solicitud) {
        if (solicitud.getIdSolicitud() == null) {
            entityManager.persist(solicitud);
        }
        else {
            entityManager.merge(solicitud);
        }
        return solicitud;
    }

    /**
     * Metodo para guardar una solicitud
     * 
     * @return solicitud
     */
    @Override
    public SolicitudRecursoRevocacion guardarSolicitudRRL(SolicitudRecursoRevocacion solicitud) {
        if (solicitud.getIdSolicitud() == null) {
            entityManager.persist(solicitud);
        }
        else {
            entityManager.merge(solicitud);
        }
        return solicitud;
    }

    /**
     * Metodo para consultar uns solicitud de autorizacion por id
     * 
     * @param idSolicitud
     * @return
     */
    @Override
    public SolicitudDatosGenerales obtenerSolicitudDatosGeneralesPorId(Long idSolicitud) {
        Query query =
                entityManager.createQuery("select s from SolicitudDatosGenerales s where s.idSolicitud = :idSolicitud",
                        SolicitudDatosGenerales.class);
        query.setParameter("idSolicitud", idSolicitud);
        try {
            return (SolicitudDatosGenerales) query.getSingleResult();
        }
        catch (Exception e) {
            return null;
            // no encontro el registro, no hace nada
        }

    }

    /**
     * Metodo para obtener solicitudes por filtros
     * 
     * @param idSolicitud
     * @param fechaInicio
     * @param fechaFin
     * @param rfc
     * @return
     */
    @Override
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<Solicitud> obtenerSolicitudesPorFiltros(Long idSolicitud, Date fechaInicio, Date fechaFin, String rfc) {
        StringBuilder queryString = new StringBuilder();
        queryString
                .append("Select s From Solicitud s where s.estadoSolicitud = :pEstadoSolicitud and s.cveUsuarioCapturista = :pRfc ");
        queryString.append("and s.fechaCreacion ");
        queryString.append("between :pFechaInicio and :pFechaFin ");
        if (idSolicitud != null) {
            queryString.append("and s.idSolicitud = :pIdSolicitud ");
        }
        queryString.append("and NVL(s.cveRolCapturista,'-') <> 'OficialDePartes' ");
        queryString.append("order by s.fechaCreacion desc ");
        Query query = getEntityManager().createQuery(queryString.toString());
        query.setParameter("pEstadoSolicitud", EstadoSolicitud.REGISTRADA.getClave());
        query.setParameter("pRfc", rfc);
        query.setParameter("pFechaInicio", fechaInicio);
        query.setParameter("pFechaFin", fechaFin);
        if (idSolicitud != null) {
            query.setParameter("pIdSolicitud", idSolicitud);
        }
        return query.getResultList();
    }

}
