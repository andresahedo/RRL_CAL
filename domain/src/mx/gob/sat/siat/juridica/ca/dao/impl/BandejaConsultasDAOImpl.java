/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.dao.impl;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.BaseJPARepository;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.ca.dao.BandejaConsultasDAO;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * @author ivan.guzman
 */
@Repository
public class BandejaConsultasDAOImpl extends BaseJPARepository implements BandejaConsultasDAO {

    /**
     * Metodo que obtiene una lista de tramites segun los atributos
     * correspondientes
     * 
     * @param numAsunto
     * @param fechaInicio
     * @param fechaFin
     * @param estadoProcesal
     * @param tipoTramite
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<Tramite> obtenerTramites(String numAsunto, Date fechaInicio, Date fechaFin, String estadoProcesal,
            String tipoTramite) {
        StringBuffer sql = new StringBuffer(NumerosConstantes.CINCUENTA);
        sql.append("SELECT t FROM Solicitud s , Tramite t ");
        sql.append("WHERE s.idSolicitud = t.solicitud.idSolicitud ");
        if (!numAsunto.isEmpty()) {
            sql.append("AND t.numeroAsunto = :pNumeroAsunto ");
        }
        if (fechaInicio != null && fechaFin != null) {
            sql.append(" AND to_char(s.fechaCreacion, 'dd/MM/yyyy') ");
            sql.append(" BETWEEN to_char(:pFechaIni, 'dd/MM/yyyy') AND to_char(:pFechaFin, 'dd/MM/yyyy') ");
        }
        if (!estadoProcesal.isEmpty()) {
            sql.append("AND t.estadoTramite = :pEstadoProcesal ");
        }
        if (!tipoTramite.isEmpty()) {
            sql.append("AND s.claveModalidad = :pTipoTramite ");
        }
        else {
            sql.append("AND (s.claveModalidad BETWEEN :pIdInicial AND :pIdFinal) ");
        }
        Query query = getEntityManager().createQuery(sql.toString(), Tramite.class);

        if (!numAsunto.isEmpty()) {
            query.setParameter("pNumeroAsunto", numAsunto);
        }
        if (fechaInicio != null && fechaFin != null) {
            query.setParameter("pFechaIni", fechaInicio);
            query.setParameter("pFechaFin", fechaFin);
        }
        if (!estadoProcesal.isEmpty()) {
            query.setParameter("pEstadoProcesal", estadoProcesal);
        }
        if (!tipoTramite.isEmpty()) {
            query.setParameter("pTipoTramite", new Long(tipoTramite));
        }
        else {
            query.setParameter("pIdInicial", new Long(DiscriminadorConstants.T2_CLASIFICACION_ARANCELARIA));
            query.setParameter("pIdFinal", new Long(DiscriminadorConstants.T2_AUTORIZACIONES_DEDUCIR_PAGOS_COMEDORES));
        }

        return query.getResultList();

    }

    /**
     * M&eacute;todo para obtener las modalidades del tr&aacute;mite
     * por id de servicio.
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<TipoTramite> obtenerModalidadesPorServicio(String idServicio) {
        StringBuffer sql = new StringBuffer(NumerosConstantes.CINCUENTA);
        sql.append("SELECT t FROM TipoTramite t ");
        sql.append("WHERE t.servicio = :idServicio ORDER BY  t.idTipoTramite");
        Query query = getEntityManager().createQuery(sql.toString(), TipoTramite.class);
        query.setParameter("idServicio", idServicio);

        return query.getResultList();
    }
}
