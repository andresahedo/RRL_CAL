package mx.gob.sat.siat.juridica.oficialia.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.MotivoRechazo;
import mx.gob.sat.siat.juridica.base.dao.impl.BaseJPADao;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.oficialia.dao.MotivosDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class MotivosDaoImpl extends BaseJPADao implements MotivosDao {

    /**
     * 
     */
    private static final long serialVersionUID = -6616352070240955366L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<MotivoRechazo> obtenerMotivosRechazo() {
        Query query = getEntityManager().createQuery("select cd from CatalogoD cd ");
        return query.getResultList();
    }

    @Override
    public MotivoRechazo guardarMotivo(MotivoRechazo motivo) {
        try {
            if (motivo != null) {
                if (motivo.getIdMotivo() != null) {
                    getEntityManager().merge(motivo);
                }
                else {
                    getEntityManager().persist(motivo);
                }
            }
            getEntityManager().flush();
        }
        catch (Exception e) {
            getLog().error("", e);
        }
        return motivo;
    }

    @Override
    public MotivoRechazo obtenermotivoPorNumAsunto(String numeroAsunto) {
        TypedQuery<MotivoRechazo> typedQuery =
                getEntityManager().createNamedQuery("MotivoRechazo.findByIdTramite", MotivoRechazo.class).setParameter(
                        "numeroAsunto", numeroAsunto);
        List<MotivoRechazo> resultados = typedQuery.getResultList();
        if (resultados != null && !resultados.isEmpty()) {
            return resultados.get(0);
        }
        return null;
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<MotivoRechazo> obtenerListaMotivoPorFiltros(String numeroAsunto, String estado, Date fechaInicio,
            Date fechaFin, String rfc) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT mr FROM MotivoRechazo mr WHERE mr.idEstRechazo = :estado ");
        if (numeroAsunto != null) {
            sb.append("AND mr.idTramite LIKE :numeroAsunto ");
        }
        if (rfc != null) {
            sb.append("AND mr.idUsuario LIKE :rfc ");
        }
        if (fechaInicio != null && fechaFin != null) {
            sb.append("AND mr.fechaRechazo ");
            sb.append(" BETWEEN :fechaInicio AND :fechaFin ");
        }
        sb.append(" ORDER BY mr.fechaRechazo DESC ");
        Query query = getEntityManager().createQuery(sb.toString());
        if (numeroAsunto != null) {
            query.setParameter("numeroAsunto", numeroAsunto.toUpperCase() + "%");
        }
        if (rfc != null) {
            query.setParameter("rfc", rfc);
        }
        if (fechaInicio != null && fechaFin != null) {
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
        }
        query.setParameter("estado", estado);
        return query.getResultList();
    }
}
