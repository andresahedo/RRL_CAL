package mx.gob.sat.siat.juridica.configuracion.usuarios.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.impl.BaseJPADao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.ServiciosDisponiblesDAO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.ServiciosModel;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServiciosDisponiblesDAOImpl extends BaseJPADao implements ServiciosDisponiblesDAO {

    /**
     * 
     */
    private static final long serialVersionUID = -3213655374390949570L;

    @SuppressWarnings("unchecked")
    @Override
    public List<ServiciosModel> obtenerServiciosRol(String rol) {
        List<ServiciosModel> servicios = new ArrayList<ServiciosModel>();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT c.IDCOMPONENTE,  c.URL, cd.DESCRIPCION FROM ");
        sb.append("RVCA_ROL_COMPONENTE rc, RVCP_COMPONENTE c, RVCA_COMPONENTE_DESCRIPCION cd ");
        sb.append("WHERE rc.IDCOMPONENTE = c.IDCOMPONENTE ");
        sb.append("AND c.IDCOMPONENTE    = cd.IDCOMPONENTE ");
        sb.append("AND (rc.IDROL         = :rol AND c.BLNACTIVO  = 1  ) ORDER BY c.ORDENCOMPONENTE ");

        Query query = getEntityManager().createNativeQuery(sb.toString());
        query.setParameter("rol", rol);

        List<Object[]> lista = (List<Object[]>) query.getResultList();

        Object[] objeto;
        for (int i = 0; i < lista.size(); i++) {
            ServiciosModel serv = new ServiciosModel();
            objeto = lista.get(i);
            serv.setIdComponente((BigDecimal) objeto[0]);
            serv.setUrl((String) objeto[1]);
            serv.setDescripcion((String) objeto[2]);

            servicios.add(serv);
        }

        return servicios;
    }

}
