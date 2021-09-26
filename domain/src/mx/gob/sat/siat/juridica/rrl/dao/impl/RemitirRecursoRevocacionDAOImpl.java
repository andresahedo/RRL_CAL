/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.impl.BaseJPADao;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.rrl.dao.RemitirRecursoRevocacionDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
 @Repository
 public class RemitirRecursoRevocacionDAOImpl extends BaseJPADao implements RemitirRecursoRevocacionDAO {

    /**
     * 
     */
    private static final long serialVersionUID = -5770504596542702293L;

    /**
     * Metodo para obtener una lista de unidades administrativas
     * 
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<UnidadAdministrativa> obtenerUnidadesAdministrativas() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ua FROM UnidadAdministrativa ua where ");
        sql.append("     ua.vigencia.blnActivo =:activo ");
        sql.append(" AND (sysdate between ua.vigencia.fechaInicioVigencia and ua.vigencia.fechaFinVigencia or ua.vigencia.fechaFinVigencia is null) ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("activo", Integer.valueOf(1));
        return query.getResultList();
    }
}
