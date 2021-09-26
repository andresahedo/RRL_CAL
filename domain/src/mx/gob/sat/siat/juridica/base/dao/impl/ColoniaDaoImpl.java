package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.ColoniaDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Colonia;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ColoniaDaoImpl extends BaseJPADao implements ColoniaDao {

    /**
     * 
     */
    private static final long serialVersionUID = -6636197311475492828L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<Colonia> obtenerColoniaPorCodigoPostal(String cp) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT DISTINCT c from Colonia c ");
        sb.append(" WHERE c.cp = :codigo ");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("codigo", cp);
        List<Colonia> colonias = query.getResultList();
        if (colonias != null && !colonias.isEmpty()) {
            return colonias;
        }
        else {
            return null;
        }
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<Colonia> obtenerColonias() {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT c from Colonia c ");
        Query query = getEntityManager().createQuery(sb.toString());
        List<Colonia> colonias = query.getResultList();
        if (colonias != null && !colonias.isEmpty()) {
            return colonias;
        }
        else {
            return null;
        }

    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<Colonia> buscarColoniaPorClaveDelegacion(String claveDelegacion) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT DISTINCT col from Colonia col ");
        sb.append(" WHERE col.delegacionMunicipio.clave = :claveDelegacion ");
        Query consulta = getEntityManager().createQuery(sb.toString());
        consulta.setParameter("claveDelegacion", claveDelegacion);
        List<Colonia> colonias = consulta.getResultList();
        if (colonias != null && !colonias.isEmpty()) {
            return colonias;
        }
        return null;
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<Colonia> buscarColoniaPorCveLocalidad(String claveLocalidad) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT col from Colonia col ");
        sb.append(" WHERE col.localidad.clave = :claveLocalidad ");
        Query consulta = getEntityManager().createQuery(sb.toString());
        consulta.setParameter("claveLocalidad", claveLocalidad);
        List<Colonia> colonias = consulta.getResultList();
        if (colonias != null && !colonias.isEmpty()) {
            return colonias;
        }
        else {
            return null;
        }
    }

}
