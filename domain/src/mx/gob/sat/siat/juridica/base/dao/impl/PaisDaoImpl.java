package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.PaisDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Pais;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class PaisDaoImpl extends BaseJPADao implements PaisDao {

    /**
     * 
     */
    private static final long serialVersionUID = -2459921259327479486L;

    @Override
    public Pais buscarPaisMexico() {
        String claveMexico = "MEX";
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT p from Pais p ");
        sb.append(" WHERE p.clave = :claveMexico");
        Query consulta = getEntityManager().createQuery(sb.toString());
        consulta.setParameter("claveMexico", claveMexico);
        return (Pais) consulta.getResultList().get(0);
    }

}
