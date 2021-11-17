package mx.gob.sat.siat.juridica.base.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.BaseJPARepository;
import mx.gob.sat.siat.juridica.base.dao.TareaDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tarea;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;

@Repository("tareaDao")
public class TareaDaoImpl extends BaseJPARepository implements TareaDao {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -7697792771349217694L;

    @Value("${parametro.idTramite}")
    private String parametroIdTramite;
    @Value("${parametro.idTarea}")
    private String parametroIdTarea;
    @Value("${parametro.tarea}")
    private String parametroTarea;

    @Override
    public void guardarTarea(Tarea tarea) {
        if (tarea.getIdTarea() == null) {
            getEntityManager().persist(tarea);
        } else {
            getEntityManager().merge(tarea);
        }
        getEntityManager().flush();
    }

    @Override
    public Tarea obtenerTareaPorTramiteActivo(String idTramite, Long idTarea) {
        StringBuffer sql = new StringBuffer(NumerosConstantes.CINCUENTA);
        sql.append("select t from Tarea t WHERE t.numeroAsunto = :idTramite AND t.idTarea = :idTarea");
        Query query = getEntityManager().createQuery(sql.toString(), Tarea.class);
        query.setParameter(this.parametroIdTramite, idTramite);
        query.setParameter(this.parametroIdTarea, idTarea);
        return (Tarea) query.getSingleResult();
    }

    @Override
    public Tarea obtenerTareaPorTramiteRequerimientoAtendido(String idTramite, String claveTarea) {
        Query query = getEntityManager().createQuery(
                "select t from Tarea t WHERE t.numeroAsunto = :idTramite AND t.tarea = :claveTarea ORDER BY t.fechaCreacion desc",
                Tarea.class);
        query.setParameter(this.parametroIdTramite, idTramite);
        query.setParameter(this.parametroTarea, claveTarea);
        List<Tarea> lista = query.getResultList();
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    /**
     * 1 Metodo que obtiene una lista de tramites segun los atributos
     * correspondientes
     *
     * @param numAsunto
     * @param fechaInicio
     * @param fechaFin
     * @param estadoProcesal
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<Tarea> obtenerTareasporUsuario(String numAsunto, String idusuario, String clavePromovente,
            Date fechaInicio, Date fechaFin, String estadoProcesal) {
        StringBuffer sql = new StringBuffer(NumerosConstantes.CINCUENTA);

        numAsunto = numAsunto != null ? numAsunto.toUpperCase(Locale.ROOT) : "";
        clavePromovente = clavePromovente != null ? clavePromovente.toUpperCase(Locale.ROOT) : "";

        sql.append("SELECT t FROM Tarea t, Tramite tr, Solicitud s, TipoTramite tt");
        sql.append(" WHERE t.numeroAsunto = tr.numeroAsunto AND tr.solicitud = s.idSolicitud AND"
                + " s.claveModalidad = tt.idTipoTramite" + " AND t.estadoTarea = 'PROCESO'");
        if (!numAsunto.isEmpty()) {
            sql.append(" AND t.numeroAsunto = :pNumeroAsunto ");
        }
        if (!idusuario.isEmpty()) {
            sql.append(" AND t.claveAsignado = :pIdUsuario");
        }
        if (!clavePromovente.isEmpty()) {
            sql.append(" AND s.cveUsuarioCapturista = :pClavePromovente");
        }
        if (fechaInicio != null && fechaFin != null) {
            sql.append(" AND t.fechaCreacion BETWEEN :pFechaIni AND :pFechaFin");
        }
        if (!estadoProcesal.isEmpty()) {
            sql.append(" AND tr.estadoTramite = :pEstadoProcesal ");
        }
        sql.append(" Order by t.fechaAct desc");
        Query query = getEntityManager().createQuery(sql.toString(), Tarea.class);

        if (!numAsunto.isEmpty()) {
            query.setParameter("pNumeroAsunto", numAsunto);
        }
        if (!idusuario.isEmpty()) {
            query.setParameter("pIdUsuario", idusuario);
        }
        if (!clavePromovente.isEmpty()) {
            query.setParameter("pClavePromovente", clavePromovente);
        }
        if (fechaInicio != null && fechaFin != null) {
            query.setParameter("pFechaIni", fechaInicio, TemporalType.TIMESTAMP);
            query.setParameter("pFechaFin", fechaFin, TemporalType.TIMESTAMP);
        }
        if (!estadoProcesal.isEmpty()) {
            query.setParameter("pEstadoProcesal", estadoProcesal);
        }

        return query.getResultList();
    }

    public List<Tarea> obtenerTareasporUsuarioReasignacion(String numAsunto, String idusuario, String abogado) {
        numAsunto = numAsunto != null ? numAsunto.toUpperCase(Locale.ROOT) : "";
        abogado = abogado != null ? abogado.toUpperCase(Locale.ROOT) : "";
        StringBuffer sql = new StringBuffer(NumerosConstantes.CINCUENTA);
        sql.append("SELECT t FROM Tarea t, Tramite tr, Solicitud s, TipoTramite tt");
        sql.append(" WHERE t.numeroAsunto = tr.numeroAsunto AND tr.solicitud = s.idSolicitud AND"
                + " s.claveModalidad = tt.idTipoTramite"
                + " AND (t.estadoTarea = 'PROMOVIDO' or t.estadoTarea = 'EN_ESTUDIO')");
        if (!numAsunto.isEmpty()) {
            sql.append(" AND t.numeroAsunto = :pNumeroAsunto ");
        }
        if (!idusuario.isEmpty()) {
            sql.append(" AND t.claveAsignado = :pIdUsuario");
        }

        sql.append(" Order by t.fechaAct desc");
        Query query = getEntityManager().createQuery(sql.toString(), Tarea.class);

        if (!numAsunto.isEmpty()) {
            query.setParameter("pNumeroAsunto", numAsunto);
        }
        if (!idusuario.isEmpty()) {
            query.setParameter("pIdUsuario", idusuario);
        }
        if (!abogado.isEmpty()) {
            query.setParameter("pClaveAsignado", abogado);
        }

        return query.getResultList();
    }

    public Long obtenerNumeroTareasEmpleado(String idUsuario) {
        Long maxResults;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM Tarea t");
        sql.append(" WHERE t.estadoTarea = 'PROCESO'");

        if (!idUsuario.isEmpty()) {
            sql.append(" AND t.claveAsignado = :pIdUsuario");
        }

        Query query = getEntityManager().createQuery(sql.toString());

        if (!idUsuario.isEmpty()) {
            query.setParameter("pIdUsuario", idUsuario);
        }

        maxResults = Long.parseLong(query.getSingleResult().toString());

        return maxResults;

    }

    @Override
    public List<Tarea> buscarTareasReasignar(String numeroAsunto, String administrador, String abogado,
            List<String> listaTramites) {
        numeroAsunto = numeroAsunto != null ? numeroAsunto.toUpperCase(Locale.ROOT) : "";
        abogado = abogado != null ? abogado.toUpperCase(Locale.ROOT) : "";
        administrador = administrador != null ? administrador.toUpperCase(Locale.ROOT) : "";
        StringBuffer sql = new StringBuffer(NumerosConstantes.DOSCIENTOS_CINCUENTA);
        sql.append("SELECT t FROM Tarea t ");
        sql.append("WHERE t.tarea = 'TITAR.ATR' AND t.estadoTarea = 'PROCESO' ");
        sql.append("AND t.numeroAsunto IN :pListaTramites ");

        if (!numeroAsunto.isEmpty()) {
            sql.append(" AND t.numeroAsunto LIKE :pNumeroAsunto");
        }

        if (!abogado.isEmpty()) {
            sql.append(" AND t.claveAsignado LIKE :pAbogado");
        }

        Query query = getEntityManager().createQuery(sql.toString(), Tarea.class);

        query.setParameter("pListaTramites", listaTramites);

        if (!numeroAsunto.isEmpty()) {
            query.setParameter("pNumeroAsunto", numeroAsunto + "%");
        }

        if (!abogado.isEmpty()) {
            query.setParameter("pAbogado", abogado);
        }

        return query.getResultList().isEmpty() ? new ArrayList<Tarea>() : query.getResultList();
    }

    @Override
    public int obtenerTareasPorAsuntoAbogado(String numeroAsunto, String claveAsignado) {
        numeroAsunto = numeroAsunto != null ? numeroAsunto.toUpperCase(Locale.ROOT) : "";
        claveAsignado = claveAsignado != null ? claveAsignado.toUpperCase(Locale.ROOT) : "";
        int maxResults = 0;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM Tarea t");
        sql.append(" WHERE t.estadoTarea = 'PROCESO'");
        sql.append(" AND t.claveAsignado = :pclaveAsignado");
        sql.append(" AND t.numeroAsunto = :pnumeroAsunto");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("pclaveAsignado", claveAsignado);
        query.setParameter("pnumeroAsunto", numeroAsunto);
        maxResults = Integer.parseInt(query.getSingleResult().toString());
        return maxResults;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tarea> obtenerTareaPorRequerimientoNoAtendido() {
        Query query = getEntityManager().createQuery("select t from Tarea t WHERE t.estadoTarea = 'PROCESO' "
                + "AND t.fechaFin IS NOT NULL " + "AND t.idRequerimeinto IS NOT NULL " + "AND t.fechaFin <= SYSDATE "
                + " ORDER BY t.fechaCreacion desc", Tarea.class);
        return (List<Tarea>) query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tarea> obtenerTareasPorNumAsunto(String numAsunto) {
        Query query = getEntityManager().createQuery("select t from Tarea t WHERE t.numeroAsunto = :idTramite ",
                Tarea.class);
        query.setParameter(this.parametroIdTramite, numAsunto);
        return (List<Tarea>) query.getResultList() != null ? query.getResultList() : new ArrayList();
    }

}