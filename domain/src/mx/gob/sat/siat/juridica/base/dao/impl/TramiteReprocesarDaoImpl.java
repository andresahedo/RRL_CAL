/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.TramiteReprocesarDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoReproceso;
import mx.gob.sat.siat.juridica.base.dao.domain.model.TramiteReprocesar;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Repository("tramiteReprocesarDao")
public class TramiteReprocesarDaoImpl extends BaseJPADao implements TramiteReprocesarDAO {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    @Override
    public TramiteReprocesar obtenerTramitePorId(String idTramite) {
        String q = "FROM TramiteReprocesar AS tr WHERE tr.numeroAsunto = :numeroAsunto";
        Query query = getEntityManager()
                .createQuery(q);
        query.setParameter("numeroAsunto", idTramite);

        return (TramiteReprocesar) query.getSingleResult();
    }

    @Override
    public void guardarTramite(TramiteReprocesar tramite) {
        TramiteReprocesar tramiteBD = null;
        try {
            tramiteBD = obtenerTramitePorId(tramite.getNumeroAsunto());
        } catch(Exception ex) {
            tramiteBD = null;
        }

        if (tramiteBD == null) {
            getEntityManager().persist(tramite);
            getEntityManager().flush();
        }
        else {
            modificarTramite(tramite);
        }
    }

    @Override
    public void modificarTramite(TramiteReprocesar tramite) {
        getEntityManager().merge(tramite);
        getEntityManager().flush();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<TramiteReprocesar> obtenerTramitesPorFiltros(String numeroAsunto, String tipoError, String estatusEnvio,
            Integer reintentos, Date fechaRegistro) {
        StringBuilder sb = new StringBuilder();
        sb.append("FROM TramiteReprocesar AS tr WHERE 1=1 ");
        if (numeroAsunto != null) {
            sb.append("AND tr.numeroAsunto= :numeroAsunto");
        }
        if (tipoError != null) {
            sb.append("AND tr.tipoError= :tipoError");
        }
        if (estatusEnvio != null) {
            sb.append("AND tr.estatusEnvio= :estatusEnvio");
        }
        if (reintentos != null) {
            sb.append("AND tr.reintentos= :reintentos");
        }
        if (fechaRegistro != null) {
            sb.append("AND tr.fechaRegistro= :fechaRegistro");
        }
        Query query = getEntityManager()
                .createQuery(sb.toString());
        if (numeroAsunto != null) {
            query.setParameter("numeroAsunto", numeroAsunto);
        }
        if (tipoError != null) {
            query.setParameter("tipoError", tipoError);
        }
        if (estatusEnvio != null) {
            query.setParameter("estatusEnvio", estatusEnvio);
        }
        if (reintentos != null) {
            query.setParameter("reintentos", reintentos);
        }
        if (fechaRegistro != null) {
            query.setParameter("fechaRegistro", fechaRegistro, TemporalType.DATE);
        }
        return query.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<TramiteReprocesar> obtenerTramitesAReprocesar() {
        StringBuilder sb = new StringBuilder();
        sb.append("FROM TramiteReprocesar AS tr WHERE tr.estatusEnvio != :estatusEnvio1 AND tr.estatusEnvio != :estatusEnvio2 AND tr.reintentos < 2");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("estatusEnvio1", EstadoReproceso.EXITOSO.getClave());
        query.setParameter("estatusEnvio2", EstadoReproceso.EXITOSO_NO_REPROCESO.getClave());
        return query.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<TramiteReprocesar> obtenerTramitesReprocesadosFecha(Date date) {
        StringBuilder sb = new StringBuilder();
        sb.append("FROM TramiteReprocesar AS tr WHERE tr.estatusEnvio != :estatusEnvio2"
                + " AND tr.fechaRegistro = :fechaRegistro");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("estatusEnvio2", EstadoReproceso.EXITOSO_NO_REPROCESO.getClave());
        query.setParameter("fechaRegistro", date, TemporalType.DATE);
        return query.getResultList();
    }
   
}
