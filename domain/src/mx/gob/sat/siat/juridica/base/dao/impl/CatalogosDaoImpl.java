package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.CatalogosDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Descripcion;
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
public class CatalogosDaoImpl extends BaseJPADao implements CatalogosDao {

    private static final long serialVersionUID = -4492165245043160031L;

    /**
     * @param idDescripcion
     *            Id del registro de Descripcion buscado
     * @return Descrcipcion que corresponde al idDescripcion
     *         proporcionado y que sea registro activo
     *         (blnActivo=true)
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public Descripcion buscarDescripcionPorId(Long idDescripcion) {
        Descripcion descripcion = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select d from Descripcion d ");
        sb.append(" where d.idDescripcion = :idDescripcion ");
        sb.append(" and d.blnActivo = 1 ");

        Query createQuery = getEntityManager().createQuery(sb.toString());
        createQuery.setParameter("idDescripcion", idDescripcion);

        List<Descripcion> data = createQuery.getResultList();
        if (data != null && !data.isEmpty()) {
            descripcion = (Descripcion) data.get(0);
        }
        return descripcion;
    }

    /**
     * @param idDescripcion
     *            Id del registro de Descripcion buscado
     * @return Descrcipcion que corresponde al idDescripcion
     *         proporcionado y que sea registro activo
     *         (blnActivo=true)
     */

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public Descripcion buscarDescripcionPorTipoIdentificador(String tipo, String identificador) {
        Descripcion descripcion = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select d from Descripcion d ");
        sb.append(" where d.ideTipoDescripcion = :tipo ");
        sb.append(" and d.identificador = :identificador ");
        sb.append(" and d.blnActivo = 1 ");

        Query createQuery = getEntityManager().createQuery(sb.toString());
        createQuery.setParameter("tipo", tipo);
        createQuery.setParameter("identificador", identificador);

        List<Descripcion> data = createQuery.getResultList();
        if (data != null && !data.isEmpty()) {
            descripcion = (Descripcion) data.get(0);
        }
        return descripcion;
    }

}
