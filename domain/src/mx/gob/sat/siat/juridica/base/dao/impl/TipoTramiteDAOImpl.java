/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.TipoTramiteDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author softtek
 **************************************** 
 *         Modifica antonio.flores se agrega la implementaci&oacute;n
 *         de obtenerTiposDeTramitesPorId
 * @since 03/27/2014
 */
@Repository("tipoTramiteDAO")
public class TipoTramiteDAOImpl extends BaseJPADao implements TipoTramiteDAO {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 385330864128156500L;

    /**
     * Metodo para buscar un tramite por id
     */
    @Override
    public String findById(Integer idTipoTramite) {
        Query query =
                getEntityManager().createQuery(
                        "select t.descripcionModalidad from TipoTramite as t "
                                + "where t.idTipoTramite = :pIdTipoTramite");
        query.setParameter("pIdTipoTramite", idTipoTramite);

        return (String) query.getSingleResult();
    }

    /**
     * Metodo para obtener una descripcion de servicio por id de
     * tramite
     */
    public String obtenerDescripcionServicio(Integer idTipoTramite) {
        Query query =
                getEntityManager().createQuery(
                        "select t.descripcionServicio from TipoTramite as t "
                                + "where t.idTipoTramite = :pIdTipoTramite");
        query.setParameter("pIdTipoTramite", idTipoTramite);

        return (String) query.getSingleResult();
    }

    /**
     * Metodo para obtener una descripcion de servicio por id de
     * tramite
     */
    @Override
    public String obtenerDescripcionModalidad(Integer idTipoTramite) {
        Query query =
                getEntityManager().createQuery(
                        "select t.descripcionModalidad from TipoTramite as t "
                                + "where t.idTipoTramite = :pIdTipoTramite");
        query.setParameter("pIdTipoTramite", idTipoTramite);

        return (String) query.getSingleResult();
    }

    /**
     * metodo para obtener un alista de los tipos de tramite
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<TipoTramite> obtenerTiposDeTramitesPorFiltros(Integer idTipoTramite, String idServicio) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t FROM TipoTramite t ");
        sb.append("WHERE 1=1 ");
        if (idTipoTramite != null) {
            sb.append("AND t.idTipoTramite = :idTipoTramite");
        }
        if (idServicio != null) {
            sb.append("AND t.servicio = :idServicio");
        }
        Query query = getEntityManager().createQuery(sb.toString());
        if (idTipoTramite != null) {
            query.setParameter("idTipoTramite", idTipoTramite);
        }
        if (idServicio != null) {
            query.setParameter("idServicio", idServicio);
        }
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see mx.gob.sat.siat.juridica.base.dao.TipoTramiteDAO#
     * obtenerTipoTramiteModulo1()
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<TipoTramite> obtenerTipoTramiteModulo1() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t FROM TipoTramite t ");
        sb.append("WHERE 1=1 ");
        sb.append("AND t.claveModulo = 1");
        Query query = getEntityManager().createQuery(sb.toString());

        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see mx.gob.sat.siat.juridica.base.dao.TipoTramiteDAO#
     * obtenerTiposDeTramitesPorId(java.lang.Integer)
     */
    @Override
    public TipoTramite obtenerTiposDeTramitesPorId(Integer idTipoTramite) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t FROM TipoTramite t ");
        sb.append("WHERE 1=1 ");
        if (idTipoTramite != null) {
            sb.append("AND t.idTipoTramite = :idTipoTramite");
        }
        Query query = getEntityManager().createQuery(sb.toString());
        if (idTipoTramite != null) {
            query.setParameter("idTipoTramite", idTipoTramite);
        }
        return (TipoTramite) query.getSingleResult();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<TipoTramite> obtenerTodosTramites(String servicio) {
        StringBuffer consulta = new StringBuffer();
        consulta.append("FROM TipoTramite WHERE servicio = :servicio");
        Query query = getEntityManager().createQuery(consulta.toString());
        query.setParameter("servicio", servicio);
        return query.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<TipoTramite> buscarTramites() {
        List<TipoTramite> tramites = new ArrayList<TipoTramite>();
        StringBuffer consulta = new StringBuffer();
        consulta.append(" select distinct tramite.IDSERVICIO,tramite.DESCSERVICIO from RVCC_TIPOTRAMITE tramite ");
        Query query = getEntityManager().createNativeQuery(consulta.toString());
        List<Object[]> listaTramites = query.getResultList();
        Object[] objeto;
        for (int i = 0; i < listaTramites.size(); i++) {
            TipoTramite tramite = new TipoTramite();
            objeto = listaTramites.get(i);
            tramite.setServicio(objeto[0].toString());
            tramite.setDescripcionServicio(objeto[1].toString());
            tramites.add(tramite);
        }
        return tramites;
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<TipoTramite> todasModalidades() {
        StringBuffer consulta = new StringBuffer();
        consulta.append("FROM TipoTramite");
        Query query = getEntityManager().createQuery(consulta.toString());
        return query.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<String> buscarIdServicios() {
        StringBuffer consulta = new StringBuffer();
        consulta.append(" select distinct tramite.IDSERVICIO from RVCC_TIPOTRAMITE tramite ");
        Query query = getEntityManager().createNativeQuery(consulta.toString());
        List<String> listaIds = query.getResultList();
        if (listaIds != null && !listaIds.isEmpty()) {
            return listaIds;

        }
        else {

            return null;
        }
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<TipoTramite> buscarTramitesAsignadosUnidad(String unidadAdmin) {
        List<TipoTramite> tramites = new ArrayList<TipoTramite>();
        StringBuffer consulta = new StringBuffer();
        consulta.append(" SELECT DISTINCT tramite.IDSERVICIO," + "  tramite.DESCSERVICIO "
                + " FROM RVCA_TIPOTRAMITE_UNIADMIN tramAdmin " + " LEFT JOIN RVCC_TIPOTRAMITE tramite "
                + " ON tramAdmin.IDTIPOTRAMITE = tramite.IDTIPOTRAMITE "
                + " WHERE tramAdmin.IDUNIADMIN = :unidadAdmin " + " AND tramAdmin.BLNACTIVO    = 1 "
                + " AND tramite.BLNACTIVO      = 1 " + " ORDER BY tramAdmin.IDUNIADMIN DESC ");
        Query query = getEntityManager().createNativeQuery(consulta.toString());
        query.setParameter("unidadAdmin", unidadAdmin);
        List<Object[]> listaTramites = query.getResultList();
        Object[] objeto;
        for (int i = 0; i < listaTramites.size(); i++) {
            TipoTramite tramite = new TipoTramite();
            objeto = listaTramites.get(i);
            tramite.setServicio(objeto[0].toString());
            tramite.setDescripcionServicio(objeto[1].toString());
            tramites.add(tramite);
        }
        return tramites;
    }
}
