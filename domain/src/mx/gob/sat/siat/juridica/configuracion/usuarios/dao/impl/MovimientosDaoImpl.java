package mx.gob.sat.siat.juridica.configuracion.usuarios.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.ActualizarMovimientosConstants;
import mx.gob.sat.siat.juridica.base.dao.impl.BaseJPADao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.MovimientosDao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.Movimiento;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class MovimientosDaoImpl extends BaseJPADao implements MovimientosDao {

    /**
     * 
     */
    private static final long serialVersionUID = -536114747985881800L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<Movimiento> obtenerMovtosNoNotificados() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT m FROM Movimiento m ");
        sb.append("WHERE m.ideEstadoMovto = :ideMovInsertados ");
        sb.append("OR m.ideEstadoMovto = :ideMovProcesados ");
        sb.append("order by m.idPeticion");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("ideMovInsertados", ActualizarMovimientosConstants.EDO_INSERTADO);
        query.setParameter("ideMovProcesados", ActualizarMovimientosConstants.EDO_PROCESADO);
        return (List<Movimiento>) query.getResultList();
    }

    public void modificarMovimiento(Movimiento movimiento) {
        getEntityManager().merge(movimiento);
    }

}
