package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.DelegacionMunicipioDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DelegacionMunicipio;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class DelegacionMunicipioDaoImpl extends BaseJPADao implements DelegacionMunicipioDao {

    /**
     * 
     */
    private static final long serialVersionUID = -7374060225074310246L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<DelegacionMunicipio> obtenerDelegacionesMunicipios() {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT dm from DelegacionMunicipio dm ");
        Query consulta = getEntityManager().createQuery(sb.toString());
        return consulta.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<DelegacionMunicipio> obtenerDelegacionMunicipioPorCveEstado(String claveEstado) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT dm from DelegacionMunicipio dm ");
        sb.append(" WHERE dm.entidadFederativa.clave = :claveEstado ");
        Query consulta = getEntityManager().createQuery(sb.toString());
        consulta.setParameter("claveEstado", claveEstado);
        List<DelegacionMunicipio> delegaciones = consulta.getResultList();
        if (delegaciones != null && !delegaciones.isEmpty()) {
            return delegaciones;
        }
        else {
            return null;
        }
    }

}
