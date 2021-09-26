package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.EntidadFederativaDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.EntidadFederativa;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class EntidadFederativaDaoImpl extends BaseJPADao implements EntidadFederativaDao {

    /**
     * 
     */
    private static final long serialVersionUID = -1837930808536071113L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<EntidadFederativa> obtenerEstados() {
        String clavePais = "MEX";
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT est from EntidadFederativa est ");
        sb.append(" WHERE est.pais.clave = :clavePais ");
        Query consulta = getEntityManager().createQuery(sb.toString());
        consulta.setParameter("clavePais", clavePais);
        List<EntidadFederativa> estados = consulta.getResultList();
        if (estados != null && !estados.isEmpty()) {
            return estados;
        }
        else {
            return null;
        }
    }

}
