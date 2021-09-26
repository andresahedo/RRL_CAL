package mx.gob.sat.siat.juridica.configuracion.usuarios.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.impl.BaseJPADao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.TipoTramiteConfigurarDao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.TipoTramiteConfigurar;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TipoTramiteConfigurarDaoImpl extends BaseJPADao implements TipoTramiteConfigurarDao {

    /**
     * 
     */
    private static final long serialVersionUID = 8361344543003705029L;

    /**
     * Metodo para obtener los tramites(servicios) disponibles y si
     * estan asignados, la unidad administrativa a la que pertenecen y
     * si estan activos para la unidad administrativa.
     */

    @SuppressWarnings("unchecked")
    @Override
    public List<TipoTramiteConfigurar> obtenerServicios() {
        StringBuffer consulta = new StringBuffer();
        consulta.append("FROM TipoTramiteConfigurar");
        Query query = getEntityManager().createQuery(consulta.toString());

        List<TipoTramiteConfigurar> lista = query.getResultList();
        List<TipoTramiteConfigurar> listaSinNulos = new ArrayList<TipoTramiteConfigurar>();
        for (TipoTramiteConfigurar tr : lista) {
            if (tr != null) {
                listaSinNulos.add(tr);
            }
        }
        return listaSinNulos;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer validarServicio(String idServicio, String idUnidadAdministrativa) {
        StringBuffer consulta = new StringBuffer();
        consulta.append(" SELECT tua.BLNACTIVO FROM RVCA_TIPOTRAMITE_UNIADMIN tua ");
        consulta.append(" LEFT JOIN RVCC_TIPOTRAMITE tt ON tua.IDTIPOTRAMITE = tt.IDTIPOTRAMITE ");
        consulta.append(" WHERE tt.IDSERVICIO  = :idServicio AND tua.IDUNIADMIN   = :idUnidadAdministrativa ");
        Query query = getEntityManager().createNativeQuery(consulta.toString());
        query.setParameter("idServicio", idServicio);
        query.setParameter("idUnidadAdministrativa", idUnidadAdministrativa);
        List<BigDecimal> r = query.getResultList();
        if (r != null) {
            String val = r.get(0).toString();
            return Integer.valueOf(val);
        }
        return 0;
    }

}
