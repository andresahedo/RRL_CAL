package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.LocalidadDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Localidad;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class LocalidadDaoImpl extends BaseJPADao implements LocalidadDao {

    /**
     * 
     */
    private static final long serialVersionUID = 7060322571315052402L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<Localidad> obtenerLocalidadPorClaveDelegacion(String claveDelegacion) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT DISTINCT loc from Localidad loc ");
        sb.append(" WHERE loc.delegacionMunicipio.clave = :claveDelegacion ");
        Query consulta = getEntityManager().createQuery(sb.toString());
        consulta.setParameter("claveDelegacion", claveDelegacion);
        List<Localidad> localidades = consulta.getResultList();
        if (localidades != null && !localidades.isEmpty()) {
            return localidades;
        }
        else {
            return null;
        }
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<Localidad> buscarLocalidadPorCP(String cp) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT DISTINCT loc FROM Localidad loc ");
        sb.append(" WHERE loc.codigoPostal = :cp ");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("cp", cp);
        List<Localidad> localidades = query.getResultList();
        if (localidades != null && !localidades.isEmpty()) {
            return localidades;

        }
        else {
            return null;
        }
    }

}
