/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.UnidadAdministrativaDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoUnidadAdministrativa;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Repository
public class UnidadAdministrativaDaoImpl extends BaseJPADao implements UnidadAdministrativaDao {

    /**
     * 
     */
    private static final long serialVersionUID = -1996619997265258189L;

    /**
     * Metodo para obtener una lista de unidades administrativas
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<UnidadAdministrativa> obtenerUnidadesAdministrativas() {
        Query query =
                getEntityManager()
                        .createQuery(
                                " SELECT ua FROM UnidadAdministrativa ua WHERE ua.ideTipoUnidadAdministrativa <> :tipoUA and ua.vigencia.blnActivo = 1 ");
        query.setParameter("tipoUA", TipoUnidadAdministrativa.ADMINISTRACION_EXTERNA.getClave());
        return query.getResultList();
    }

    /**
     * Metodo para obtener una lista de unidades administrativas de un
     * catalogo
     */
    public List<UnidadAdministrativa> obtenerCatalogo() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select u from UnidadAdministrativa u  WHERE u.vigencia.blnActivo = 1 ");
        sb.append(" AND current_date() > u.vigencia.fechaInicioVigencia ");
        sb.append(" AND (current_date() < u.fechaExtincion OR u.fechaExtincion is null)");
        Query createQuery = getEntityManager().createQuery(sb.toString());

        List<UnidadAdministrativa> data = createQuery.getResultList();

        return data;
    }

    /**
     * Metodo para obtener una lista de unidades administrativas por
     * tipo de unidad
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<UnidadAdministrativa> obtenerUnidadesPorTipo(TipoUnidadAdministrativa tipoUnidad) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT u from UnidadAdministrativa u ");
        sb.append(" WHERE u.ideTipoUnidadAdministrativa = :tipoUnidad ");
        sb.append(" AND u.vigencia.blnActivo = 1 ");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("tipoUnidad", tipoUnidad.getClave());

        return query.getResultList();
    }

    /**
     * Metodo para obtener una unidad administrativa por id de tramite
     */
    @Override
    public UnidadAdministrativa obtenerUnidadPorTramite(String idTramite) {

        Query query =
                getEntityManager()
                        .createQuery(
                                " select uni from UnidadAdministrativa uni where uni IN (select sol.unidadAdministrativa from Solicitud sol where sol IN (select tra.solicitud from Tramite tra where tra.numeroAsunto = :numeroAsunto)) "
                                        + " and uni.vigencia.blnActivo = 1 ", UnidadAdministrativa.class);
        query.setParameter("numeroAsunto", idTramite);
        List<UnidadAdministrativa> resultados = query.getResultList();
        if (resultados != null && !resultados.isEmpty()) {
            return resultados.get(0);
        }
        return null;
    }

    @Override
    public UnidadAdministrativa obtenerUnidadPorClaveLocal(String claveLocal) {
        Query query =
                getEntityManager().createQuery(
                        " select uni from UnidadAdministrativa uni where claveAdministracionLocalRecaudadora = :claveLocal "
                                + " and uni.vigencia.blnActivo = 1 ", UnidadAdministrativa.class);
        query.setParameter("claveLocal", claveLocal);
        List<UnidadAdministrativa> resultados = query.getResultList();
        if (resultados != null && !resultados.isEmpty()) {
            return resultados.get(0);
        }
        return null;
    }

    public UnidadAdministrativa obtenerUnidadPorId(String clave) {
        return getEntityManager().find(UnidadAdministrativa.class, clave);
    }

    @Override
    public List<UnidadAdministrativa> obtenerUnidadesTodas() {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT u from UnidadAdministrativa u ");
        sb.append(" WHERE current_date() >= u.vigencia.fechaInicioVigencia ");
        sb.append(" AND (current_date() <= u.fechaExtincion OR u.fechaExtincion is null)");
        sb.append(" ORDER BY u.nombre");
        Query query = getEntityManager().createQuery(sb.toString());

        return query.getResultList();
    }
    
    @Override
    public List<UnidadAdministrativa> obtenerUnidAdmvasTodas(){
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT u FROM UnidadAdministrativa u ");
        sb.append(" WHERE current_date() >= u.vigencia.fechaInicioVigencia ");
        sb.append(" AND (current_date() <= u.fechaExtincion OR u.fechaExtincion is null)");
        sb.append(" ORDER BY u.nombre");
        Query query = getEntityManager().createQuery(sb.toString());
        return query.getResultList();
    }
    
    @Override
    public List<UnidadAdministrativa> obtenerUnidAdmvasVigentes(){
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT u FROM UnidadAdministrativa u ");
        sb.append(" WHERE u.vigencia.blnActivo = 1 ");
        sb.append(" AND current_date() >= u.vigencia.fechaInicioVigencia ");
        sb.append(" AND (current_date() <= u.vigencia.fechaFinVigencia OR u.vigencia.fechaFinVigencia is null)");
        sb.append(" ORDER BY u.nombre");
        Query query = getEntityManager().createQuery(sb.toString());
        return query.getResultList();
    }

}
