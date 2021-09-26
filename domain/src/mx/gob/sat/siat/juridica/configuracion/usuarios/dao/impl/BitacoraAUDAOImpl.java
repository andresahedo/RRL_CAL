package mx.gob.sat.siat.juridica.configuracion.usuarios.dao.impl;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.model.BitacoraAU;
import mx.gob.sat.siat.juridica.base.dao.impl.BaseJPADao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.BitacoraAUDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class BitacoraAUDAOImpl extends BaseJPADao implements BitacoraAUDAO {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 4584573848292828370L;

    @SuppressWarnings("unchecked")
    @Override
    public List<BitacoraAU> obtenerDatosBitacora(BitacoraAU modelBitacora, Date fechaInicial, Date fechaFinal) {

        Calendar fechaAux = Calendar.getInstance();
        fechaAux.setTime(fechaFinal);
        fechaAux.set(Calendar.HOUR_OF_DAY, NumerosConstantes.VEINTITRES);
        fechaAux.set(Calendar.MINUTE, NumerosConstantes.CINCUENTA_NUEVE);
        fechaAux.set(Calendar.SECOND, NumerosConstantes.CINCUENTA_NUEVE);
        fechaAux.getTime();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT bau FROM BitacoraAU bau ");
        sb.append(" WHERE ");
        if (modelBitacora.getUnidadAdministrativa().getClave() != null) {
            sb.append("bau.unidadAdministrativa.clave =:idUniAdmin ");
            sb.append("AND bau.fechaAccion BETWEEN :fechaInicial AND :fechaFinal ");
        }
        else {
            sb.append("bau.fechaAccion BETWEEN :fechaInicial AND :fechaFinal ");
        }
        if (modelBitacora.getNumEmpleadoAA() != null) {
            sb.append("AND bau.numEmpleadoAA =:numeroEmpAplicado ");
        }
        if (modelBitacora.getRfcAA() != null) {
            sb.append("AND bau.rfcAA =:rfcAplicadoA ");
        }
        if (modelBitacora.getIdAplicadoA() != null) {
            sb.append("AND bau.idAplicadoA =:aplicadoA ");
        }
        if (modelBitacora.getNumEmpleadoRP() != null) {
            sb.append("AND bau.numEmpleadoRP =:numeroEmpRealiza ");
        }
        if (modelBitacora.getRfcRP() != null) {
            sb.append("AND bau.rfcRP =:rfcRealizaPor ");
        }
        if (modelBitacora.getIdRealizadoPor() != null) {
            sb.append("AND bau.idRealizadoPor =:realizadoPor ");
        }
        sb.append("ORDER BY fechaAccion DESC ");
        Query query = getEntityManager().createQuery(sb.toString());
        if (modelBitacora.getUnidadAdministrativa().getClave() != null) {
            query.setParameter("idUniAdmin", modelBitacora.getUnidadAdministrativa().getClave());
        }
        query.setParameter("fechaInicial", fechaInicial);
        query.setParameter("fechaFinal", fechaAux.getTime());

        if (modelBitacora.getNumEmpleadoAA() != null) {
            query.setParameter("numeroEmpAplicado", modelBitacora.getNumEmpleadoAA());
        }
        if (modelBitacora.getRfcAA() != null) {
            query.setParameter("rfcAplicadoA", modelBitacora.getRfcAA());
        }
        if (modelBitacora.getIdAplicadoA() != null) {
            query.setParameter("aplicadoA", modelBitacora.getIdAplicadoA());
        }
        if (modelBitacora.getNumEmpleadoRP() != null) {
            query.setParameter("numeroEmpRealiza", modelBitacora.getNumEmpleadoRP().toString());
        }
        if (modelBitacora.getRfcRP() != null) {
            query.setParameter("rfcRealizaPor", modelBitacora.getRfcRP());
        }
        if (modelBitacora.getIdRealizadoPor() != null) {
            query.setParameter("realizadoPor", modelBitacora.getIdRealizadoPor());
        }
        return query.getResultList();
    }

    @Override
    public void guardarDatosBitacora(BitacoraAU bitacora) {
        try {
            if (bitacora.getIdBitacora() != null) {
                getEntityManager().merge(bitacora);
            }
            else {
                getEntityManager().persist(bitacora);
            }
            getEntityManager().flush();
        }
        catch (Exception e) {
            getLog().error("", e);
        }
    }

    public BitacoraAU insertarMovimientoBitacora(BitacoraAU bitacora) {
        if (bitacora != null) {
            getEntityManager().persist(bitacora);
        }
        return bitacora;
    }
}
