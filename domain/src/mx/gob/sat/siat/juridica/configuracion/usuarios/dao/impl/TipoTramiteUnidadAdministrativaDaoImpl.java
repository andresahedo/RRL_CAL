/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.TipoTramiteUnidadAdministrativaCatalogo;
import mx.gob.sat.siat.juridica.base.dao.impl.BaseJPADao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.TipoTramiteUnidadAdministrativaDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */

@Repository
public class TipoTramiteUnidadAdministrativaDaoImpl extends BaseJPADao implements TipoTramiteUnidadAdministrativaDao {

    /**
     * 
     */
    private static final long serialVersionUID = 8773329253829835945L;

    @SuppressWarnings("unchecked")
    @Override
    public List<TipoTramiteUnidadAdministrativaCatalogo> buscarTramitesUnidad(String claveUnidad) {
        StringBuffer consulta = new StringBuffer();
        consulta.append("FROM TipoTramiteUnidadAdministrativaCatalogo WHERE unidadAdministrativa.clave = :claveUnidad");
        Query query = getEntityManager().createQuery(consulta.toString());
        query.setParameter("claveUnidad", claveUnidad);
        return query.getResultList();
    }

    @Override
    public void actualizarTramitesUnidad(TipoTramiteUnidadAdministrativaCatalogo tipoTramite) {
        if (tipoTramite.getIdTipoTramiteUniAdmin() != null) {
            getEntityManager().merge(tipoTramite);
        }
        else {
            if (tipoTramite.getTipoTramite().getIdTipoTramite() != null) {
                getEntityManager().persist(tipoTramite);
            }
        }
        getEntityManager().flush();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TipoTramiteUnidadAdministrativaCatalogo> buscarTramitesAsignadosModificar(String idUnidad,
            int idTipoTramite) {
        StringBuffer consulta = new StringBuffer();
        consulta.append("FROM TipoTramiteUnidaAdministrativaCatalogo WHERE unidadAdministrativa.clave = :claveUnidad AND tipoTramite.idTipoTramite = :idTipoTramite");
        Query query = getEntityManager().createQuery(consulta.toString());
        query.setParameter("claveUnidad", idUnidad);
        query.setParameter("idTipoTramite", idTipoTramite);
        List<TipoTramiteUnidadAdministrativaCatalogo> tramites = query.getResultList();

        return tramites;
    }

}
