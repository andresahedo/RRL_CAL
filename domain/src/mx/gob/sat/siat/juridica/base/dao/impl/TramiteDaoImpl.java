/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import mx.gob.sat.siat.juridica.base.dao.TramiteDao;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Secuencia;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;

/**
 * 
 * @author softtek
 * 
 */
@Repository("tramiteDao")
public class TramiteDaoImpl extends BaseJPADao implements TramiteDao {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -7631295177523389577L;
    @Value("${parametro.numeroAsunto}")
    private String parametroNumeroAsunto;
    @Value("${parametro.rfc}")
    private String parametroRfc;
    @Value("${parametro.estadoProcesal}")
    private String parametroEstadoProcesal;
    @Value("${parametro.fechaInicio}")
    private String parametroFechaInicio;
    @Value("${parametro.fechaFin}")
    private String parametroFechaFin;
    @Value("${tramitedaoimpl.condicion.numeroasunto}")
    private String condicionNumAsunto;
    @Value("${tramitedaoimpl.condicion.estadoTramite}")
    private String condicionEdoTramite;
    @Value("${tramitedaoimpl.condicion.tramite}")
    private String condicionTramite;
    @Value("${tramitedaoimpl.condicion.fechaRecepcion}")
    private String condicionFechaRecepcion;
    @Value("${tramitedaoimpl.condicion.order.fechaRecepcion}")
    private String orderFechaRecepcion;

    /**
     * Metodo para obtener un tramite por id
     */
    public Tramite obtenerTramitePorId(String idTramite) {

        TypedQuery<Tramite> typedQuery = getEntityManager().createNamedQuery("Tramite.findById", Tramite.class)
                .setParameter(this.parametroNumeroAsunto, idTramite);
        List<Tramite> resultados = typedQuery.getResultList();
        if (resultados != null && !resultados.isEmpty()) {
            return resultados.get(0);
        }
        return null;
    }

    /**
     * Metodo para obtener un tramite por numero de folio de resolucion
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public Tramite obtenerTramitePorResolucion(String numFolio, String numAsunto) {
        Query query = getEntityManager().createQuery("select t from Tramite as t "
                + "where t IN (SELECT  r.tramite from Resolucion r "
                + "where r IN (select resol.resolucion from ResolucionImpugnada resol where resol.numeroFolioResolucionImpugnada= :numeroFolioResolucionImpugnada and resol.fechaBaja is null and resol.resolucion.tramite.numeroAsunto <> :numAsunto ))",
                Tramite.class);
        query.setParameter("numeroFolioResolucionImpugnada", numFolio);
        query.setParameter("numAsunto", numAsunto);
        List<Tramite> resultados = query.getResultList();
        if (resultados != null && !resultados.isEmpty()) {
            return resultados.get(0);
        }
        return null;
    }

    /**
     * Metodo para obtener una secuencia de folios
     */
    public Secuencia obtenerSecuenciaFolio(Secuencia secuencia) {

        return getEntityManager().find(Secuencia.class, secuencia.getSecuenciaPK());
    }

    /**
     * Metodo para guardas la secuencia de folios
     */
    public void guardarSecuenciaFolio(Secuencia secuencia) {
        getEntityManager().persist(secuencia);
    }

    /**
     * Metodo para actualizar una secuencia de folios
     */
    public void actualizarSecuenciaFolio(Secuencia secuencia) {
        getEntityManager().merge(secuencia);
    }

    /**
     * metodo para crear un tramite
     */
    public void crearTramite(Tramite tramite) {

        Solicitud solicitud = null;
        if (tramite != null) {

            solicitud = tramite.getSolicitud();
            if (tramite.getSolicitud() != null && tramite.getSolicitud().getIdSolicitud() != null) {
                tramite.setSolicitud(solicitud);
                getEntityManager().persist(tramite);
                getEntityManager().flush();
            }
        }

    }

    /**
     * metodo para modificar un tramite
     */
    @Override
    public void modificarTramite(Tramite tramite) {
        if (null != tramite) {
            getEntityManager().merge(tramite);
        }
    }

    /**
     * Metodo para obtener un tramite por id de solicitud
     */
    public Tramite obtenerTramitePorIdSolicitud(Long idSolicitud) {
        TypedQuery<Tramite> typedQuery = getEntityManager().createNamedQuery("Tramite.findBySolicitud", Tramite.class)
                .setParameter("idSolicitud", idSolicitud);
        List<Tramite> resultados = typedQuery.getResultList();
        if (resultados != null && !resultados.isEmpty()) {
            return resultados.get(0);
        }
        return null;
    }

    /**
     * Metodo para obtener una lista de tramites
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<Tramite> obtenerTramitesPorFiltros(String numeroAsunto, Integer idTipoTramite, String estadoProcesal,
            Date fechaInicio, Date fechaFin, String rfc) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t FROM Tramite t WHERE 1=1 ");

        if (numeroAsunto != null) {
            sb.append(this.condicionNumAsunto);
        }
        if (idTipoTramite != null) {
            sb.append("AND t.solicitud.claveModalidad =  :idTipoTramite ");
        }
        if (rfc != null) {
            rfc = rfc.toUpperCase(Locale.ROOT);
            sb.append("AND t.solicitud.cveUsuarioCapturista =  :rfc ");
        }
        if (estadoProcesal != null) {
            sb.append(this.condicionEdoTramite);
        }
        if (fechaInicio != null && fechaFin != null) {
            sb.append(this.condicionFechaRecepcion);
        }
        sb.append(this.orderFechaRecepcion);
        Query query = getEntityManager().createQuery(sb.toString());
        if (numeroAsunto != null) {
            query.setParameter(this.parametroNumeroAsunto, numeroAsunto.toUpperCase(Locale.ROOT) + "%");
        }
        if (idTipoTramite != null) {
            query.setParameter("idTipoTramite", idTipoTramite.longValue());
        }
        if (rfc != null) {
            query.setParameter(this.parametroRfc, rfc.toUpperCase(Locale.ROOT));
        }
        if (estadoProcesal != null) {
            query.setParameter(this.parametroEstadoProcesal, estadoProcesal);
        }
        if (fechaInicio != null && fechaFin != null) {
            query.setParameter(this.parametroFechaInicio, fechaInicio);
            query.setParameter(this.parametroFechaFin, fechaFin);
        }
        return query.getResultList();
    }

    /**
     * Metodo para obtener una lista de tramites
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<Tramite> obtenerTramitesPorAsuntosConcluidos(String numeroAsunto, String estadoProcesal,
            Date fechaInicio, Date fechaFin, String rfc, String rfcPromovente) {
        StringBuilder sb = new StringBuilder();
        List<Tramite> listaTramite = new ArrayList<Tramite>();
        UserProfileDTO userProfile = (UserProfileDTO) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("userProfile");

        numeroAsunto = numeroAsunto != null ? numeroAsunto.toUpperCase(Locale.ROOT) : null;
        rfc = rfc != null ? rfc.toUpperCase(Locale.ROOT) : null;
        rfcPromovente = rfcPromovente != null ? rfcPromovente.toUpperCase(Locale.ROOT) : null;

        if (userProfile.getRolesNovell().contains("SAT_USU_ADMG_ACJ_ALJ")) {
            sb.append(validaAdminGlobal(numeroAsunto, estadoProcesal, fechaInicio, fechaFin, rfcPromovente));
            sb.append(this.orderFechaRecepcion);
            Query query = getEntityManager().createQuery(sb.toString());
            insertoParametroGlobal(numeroAsunto, estadoProcesal, fechaInicio, fechaFin, rfcPromovente, query);
            listaTramite = query.getResultList();
        } else if (userProfile.getRolesNovell().contains("SAT_RRL_ADM_ACJ_ALJ")) {
            listaTramite.addAll(
                    this.asuntosConcluidos(numeroAsunto, estadoProcesal, fechaInicio, fechaFin, rfc, rfcPromovente));
        }
        return listaTramite;
    }

    private List<Tramite> asuntosConcluidos(String numeroAsunto, String estadoProcesal, Date fechaInicio, Date fechaFin,
            String rfc, String rfcPromovente) {
        StringBuilder sb = new StringBuilder();
        numeroAsunto = numeroAsunto != null ? numeroAsunto.toUpperCase(Locale.ROOT) : null;
        rfc = rfc != null ? rfc.toUpperCase(Locale.ROOT) : null;
        rfcPromovente = rfcPromovente != null ? rfcPromovente.toUpperCase(Locale.ROOT) : null;
        List<Tramite> asuntosConcluidos = null;
        List<Long> tipoTramite = this.obtenerTipoTramitePorAdministradorRes(rfc);
        List<String> unidadesResponsable = this.obtenerUnidadPorAdministradorRes(rfc);
        List<String> listaEstados = new ArrayList<String>();
        if (estadoProcesal != null) {
            switch (EstadoTramite.parse(estadoProcesal)) {
            case REMITIDO: {
                asuntosConcluidos = concluidosRemitidos(numeroAsunto, fechaInicio, fechaFin, rfcPromovente, tipoTramite,
                        unidadesResponsable);
                break;
            }
            case RESUELTO: {
                listaEstados.add(EstadoTramite.RESUELTO.getClave());
                asuntosConcluidos = concluidosResueltos(numeroAsunto, fechaInicio, fechaFin, rfcPromovente, tipoTramite,
                        unidadesResponsable, listaEstados);
                break;
            }
            case RESUELTO_NOTIFICADO: {
                listaEstados.add(EstadoTramite.RESUELTO_NOTIFICADO.getClave());
                asuntosConcluidos = concluidosResueltos(numeroAsunto, fechaInicio, fechaFin, rfcPromovente, tipoTramite,
                        unidadesResponsable, listaEstados);
                break;
            }
            default:
                break;
            }
        } else {
            listaEstados.add(EstadoTramite.RESUELTO.getClave());
            listaEstados.add(EstadoTramite.RESUELTO_NOTIFICADO.getClave());
            asuntosConcluidos = concluidosResueltos(numeroAsunto, fechaInicio, fechaFin, rfcPromovente, tipoTramite,
                    unidadesResponsable, listaEstados);
            asuntosConcluidos.addAll(concluidosRemitidos(numeroAsunto, fechaInicio, fechaFin, rfcPromovente,
                    tipoTramite, unidadesResponsable));
        }

        sb.append(this.orderFechaRecepcion);
        return asuntosConcluidos != null ? asuntosConcluidos : new ArrayList<Tramite>();
    }

    private List<Tramite> concluidosResueltos(String numeroAsunto, Date fechaInicio, Date fechaFin,
            String rfcPromovente, List<Long> tipoTramite, List<String> unidadesResponsable, List<String> listaEstados) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT t FROM Tramite t  WHERE ");
        sb.append("t.estadoTramite IN :plistaEstados ");
        sb.append("AND t.solicitud.claveModalidad IN :plistaTipoTramite ");
        sb.append(" AND t.solicitud.solicitudDatosGenerales.unidadAdminBalanceo IN :plistaUnidades ");
        sb.append(validaSolicitud(numeroAsunto, rfcPromovente, fechaInicio, fechaFin));
        sb.append(this.orderFechaRecepcion);
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("plistaEstados", listaEstados);
        query.setParameter("plistaTipoTramite", tipoTramite);
        query.setParameter("plistaUnidades", unidadesResponsable);
        insertoParametro(numeroAsunto, fechaInicio, fechaFin, rfcPromovente, query);
        return query.getResultList();
    }

    private List<Tramite> concluidosRemitidos(String numeroAsunto, Date fechaInicio, Date fechaFin,
            String rfcPromovente, List<Long> tipoTramite, List<String> unidadesResponsable) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT t FROM Tramite t  WHERE ");
        sb.append("t.estadoTramite = '");
        sb.append(EstadoTramite.REMITIDO.getClave());
        sb.append("' AND t.solicitud.claveModalidad IN :plistaTipoTramite ");
        sb.append(" AND t.numeroAsunto IN ( SELECT DISTINCT r.tramite.numeroAsunto ");
        sb.append("FROM Remision r WHERE r.unidadAdminQueRemite.clave IN ( :plistaUnidades ) ");
        sb.append("AND r.tipoRemision = 'TIPREM.AUEX'  AND r.estadoRemision = 'ESTREM.AU' )");
        sb.append(validaSolicitud(numeroAsunto, rfcPromovente, fechaInicio, fechaFin));
        sb.append(this.orderFechaRecepcion);
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("plistaTipoTramite", tipoTramite);
        query.setParameter("plistaUnidades", unidadesResponsable);
        insertoParametro(numeroAsunto, fechaInicio, fechaFin, rfcPromovente, query);
        return query.getResultList();
    }

    public void insertoParametroGlobal(String numeroAsunto, String estadoProcesal, Date fechaInicio, Date fechaFin,
            String rfcPromovente, Query query) {
        if (numeroAsunto != null) {
            query.setParameter(this.parametroNumeroAsunto, numeroAsunto.toUpperCase() + "%");
        }

        if (estadoProcesal != null) {
            query.setParameter(this.parametroEstadoProcesal, estadoProcesal);
        }

        if (rfcPromovente != null) {
            query.setParameter("rfcPromovente", rfcPromovente);
        }

        if (fechaInicio != null && fechaFin != null) {
            query.setParameter(this.parametroFechaInicio, fechaInicio);
            query.setParameter(this.parametroFechaFin, fechaFin);
        }
    }

    public String validaAdminGlobal(String numeroAsunto, String estadoProcesal, Date fechaInicio, Date fechaFin,
            String rfcPromovente) {
        StringBuilder sb = new StringBuilder();
        numeroAsunto = numeroAsunto != null ? numeroAsunto.toUpperCase(Locale.ROOT) : null;
        rfcPromovente = rfcPromovente != null ? rfcPromovente.toUpperCase(Locale.ROOT) : null;
        sb.append(this.condicionTramite);
        if (numeroAsunto != null) {
            sb.append(this.condicionNumAsunto);
        }
        if (estadoProcesal != null) {
            sb.append(this.condicionEdoTramite);
        } else {
            sb.append("AND t.estadoTramite IN ('ESTTR.RM','ESTTR.RS','ESTTR.RN') ");
        }
        if (rfcPromovente != null) {
            sb.append("AND t.solicitud.cveUsuarioCapturista = :rfcPromovente ");
        }
        if (fechaInicio != null && fechaFin != null) {
            sb.append(this.condicionFechaRecepcion);
        }
        return sb.toString();
    }

    public void insertoParametro(String numeroAsunto, Date fechaInicio, Date fechaFin, String rfcPromovente,
            Query query) {
        if (numeroAsunto != null) {
            query.setParameter(this.parametroNumeroAsunto, numeroAsunto.toUpperCase() + "%");
        }
        if (rfcPromovente != null) {
            query.setParameter("rfcPromovente", rfcPromovente);
        }
        if (fechaInicio != null && fechaFin != null) {
            query.setParameter(this.parametroFechaInicio, fechaInicio);
            query.setParameter(this.parametroFechaFin, fechaFin);
        }
    }

    public String validaSolicitud(String numeroAsunto, String rfcPromovente, Date fechaInicio, Date fechaFin) {
        StringBuilder sb = new StringBuilder();
        if (numeroAsunto != null) {
            sb.append(this.condicionNumAsunto);
        }
        if (rfcPromovente != null) {
            sb.append("AND t.solicitud.cveUsuarioCapturista = :rfcPromovente ");
        }
        if (fechaInicio != null && fechaFin != null) {
            sb.append(this.condicionFechaRecepcion);
        }
        return sb.toString();
    }

    private List<String> obtenerUnidadPorAdministradorRes(String administrador) {
        StringBuilder sb = new StringBuilder();
        administrador = administrador != null ? administrador.toUpperCase(Locale.ROOT) : null;
        sb.append("SELECT DISTINCT ruat.unidadAdminPK.claveUnidadAdministrativa FROM InfRoleUnidadAdmin ruat WHERE ");
        sb.append("ruat.vigencia.blnActivo = 1 AND ruat.responsable = 1 ");
        sb.append(" AND ruat.unidadAdminPK.claveRol = 'administrador'");
        sb.append(" AND ruat.unidadAdminPK.claveUsuario = :padministrador");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("padministrador", administrador);
        return query.getResultList();
    }

    private List<Long> obtenerTipoTramitePorAdministradorRes(String administrador) {
        StringBuilder sb = new StringBuilder();
        administrador = administrador != null ? administrador.toUpperCase(Locale.ROOT) : null;
        sb.append("SELECT DISTINCT ruat.unidadAdminPK.ideTipoTramite FROM InfRoleUnidadAdmin ruat WHERE ");
        sb.append("ruat.vigencia.blnActivo = 1 AND ruat.responsable = 1 ");
        sb.append(" AND ruat.unidadAdminPK.claveRol = 'administrador'");
        sb.append(" AND ruat.unidadAdminPK.claveUsuario = :padministrador");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("padministrador", administrador);
        return query.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<Tramite> obtenerTramitesPorFiltrosOficialia(String numeroAsunto, String estadoProcesal,
            Date fechaInicio, Date fechaFin, String rfc, String folio) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t FROM Tramite t, DocumentoSolicitud doc, Solicitud sol WHERE ");
        sb.append("t.solicitud.idSolicitud = sol.idSolicitud ");
        sb.append("AND sol.idSolicitud = doc.solicitud.idSolicitud ");
        if (numeroAsunto != null) {
            sb.append(this.condicionNumAsunto);
        }
        if (rfc != null) {
            sb.append("AND t.solicitud.cveUsuarioCapturista LIKE :rfc ");
        }
        if (estadoProcesal != null) {
            sb.append(this.condicionEdoTramite);
        }
        if (fechaInicio != null && fechaFin != null) {
            sb.append(this.condicionFechaRecepcion);
        }
        if (folio != null) {
            sb.append("AND doc.folio = :folio ");
        }
        sb.append(this.orderFechaRecepcion);
        Query query = getEntityManager().createQuery(sb.toString());
        if (numeroAsunto != null) {
            query.setParameter(this.parametroNumeroAsunto, numeroAsunto.toUpperCase() + "%");
        }
        if (rfc != null) {
            query.setParameter(this.parametroRfc, rfc);
        }
        if (estadoProcesal != null) {
            query.setParameter(this.parametroEstadoProcesal, estadoProcesal);
        }
        if (fechaInicio != null && fechaFin != null) {
            query.setParameter(this.parametroFechaInicio, fechaInicio);
            query.setParameter(this.parametroFechaFin, fechaFin);
        }
        if (folio != null) {
            query.setParameter("folio", Long.valueOf(folio));
        }
        return query.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<DocumentoSolicitud> obtenerDocumentosPorFiltrosOficialia(String numeroAsunto, String estadoProcesal,
            Date fechaInicio, Date fechaFin, String rfc, String folio) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT doc FROM Tramite t, DocumentoSolicitud doc, Solicitud sol WHERE ");
        sb.append("t.solicitud.idSolicitud = sol.idSolicitud ");
        sb.append("AND sol.idSolicitud = doc.solicitud.idSolicitud ");
        if (numeroAsunto != null) {
            sb.append(this.condicionNumAsunto);
        }
        if (rfc != null) {
            sb.append("AND t.solicitud.cveUsuarioCapturista LIKE :rfc ");
        }
        if (estadoProcesal != null) {
            sb.append(this.condicionEdoTramite);
        }
        if (fechaInicio != null && fechaFin != null) {
            sb.append(this.condicionFechaRecepcion);
        }
        if (folio != null) {
            sb.append("AND doc.folio = :folio ");
        }
        sb.append(this.orderFechaRecepcion);
        Query query = getEntityManager().createQuery(sb.toString());
        if (numeroAsunto != null) {
            query.setParameter(this.parametroNumeroAsunto, numeroAsunto.toUpperCase() + "%");
        }
        if (rfc != null) {
            query.setParameter(this.parametroRfc, rfc);
        }
        if (estadoProcesal != null) {
            query.setParameter(this.parametroEstadoProcesal, estadoProcesal);
        }
        if (fechaInicio != null && fechaFin != null) {
            query.setParameter(this.parametroFechaInicio, fechaInicio);
            query.setParameter(this.parametroFechaFin, fechaFin);
        }
        if (folio != null) {
            query.setParameter("folio", Long.valueOf(folio));
        }
        return query.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<Tramite> obtenerTramitesRechazosPorFiltros(String numeroAsunto, String estadoProcesal, Date fechaInicio,
            Date fechaFin, String rfc) {
        numeroAsunto = numeroAsunto != null ? numeroAsunto.toUpperCase(Locale.ROOT) : null;
        rfc = rfc != null ? rfc.toUpperCase(Locale.ROOT) : null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t FROM Tramite t WHERE 1=1 ");
        if (numeroAsunto != null) {
            sb.append(this.condicionNumAsunto);
        }
        if (rfc != null) {
            sb.append("AND t.solicitud.solicitante.rfc =  :rfc ");
        }
        if (fechaInicio != null && fechaFin != null) {
            sb.append(this.condicionFechaRecepcion);
        }
        sb.append(this.condicionEdoTramite);
        sb.append(this.orderFechaRecepcion);
        Query query = getEntityManager().createQuery(sb.toString());
        if (numeroAsunto != null) {
            query.setParameter(this.parametroNumeroAsunto, numeroAsunto.toUpperCase() + "%");
        }
        if (rfc != null) {
            query.setParameter(this.parametroRfc, rfc);
        }
        if (estadoProcesal != null) {
            query.setParameter(this.parametroEstadoProcesal, estadoProcesal);
        }
        if (fechaInicio != null && fechaFin != null) {
            query.setParameter(this.parametroFechaInicio, fechaInicio);
            query.setParameter(this.parametroFechaFin, fechaFin);
        }
        return query.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<Tramite> obtenerTramitesDoctosRechazosPorFiltros(String numeroAsunto, String estadoDocumentoSolicitud,
            Date fechaInicio, Date fechaFin, String rfc) {
        numeroAsunto = numeroAsunto != null ? numeroAsunto.toUpperCase(Locale.ROOT) : null;
        rfc = rfc != null ? rfc.toUpperCase(Locale.ROOT) : null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t FROM Tramite t, Solicitud sol, DocumentoSolicitud doc, Persona per WHERE 1=1 ");
        sb.append("AND t.solicitud.idSolicitud = sol.idSolicitud ");
        sb.append("AND doc.idSolicitud = sol.idSolicitud ");
        sb.append("AND sol.cveUsuarioCapturista = per.rfc ");
        if (numeroAsunto != null) {
            sb.append(this.condicionNumAsunto);
        }
        if (rfc != null) {
            sb.append("AND t.solicitud.solicitante.rfc =  :rfc ");
        }
        if (fechaInicio != null && fechaFin != null) {
            sb.append(this.condicionFechaRecepcion);
        }
        if (estadoDocumentoSolicitud != null) {
            sb.append("AND doc.estadoDocumentoSolicitud =:estadoDocumentoSolicitud ");
        }
        sb.append(this.orderFechaRecepcion);
        Query query = getEntityManager().createQuery(sb.toString());
        if (numeroAsunto != null) {
            query.setParameter(this.parametroNumeroAsunto, numeroAsunto.toUpperCase() + "%");
        }
        if (rfc != null) {
            query.setParameter(this.parametroRfc, rfc);
        }
        if (estadoDocumentoSolicitud != null) {
            query.setParameter("estadoDocumentoSolicitud", estadoDocumentoSolicitud);
        }
        if (fechaInicio != null && fechaFin != null) {
            query.setParameter(this.parametroFechaInicio, fechaInicio);
            query.setParameter(this.parametroFechaFin, fechaFin);
        }
        return query.getResultList();
    }

    @Override
    public Tramite obtenerTramitePorFiltrosAndIdSolicitud(String numeroAsunto, String estadoProcesal, Date fechaInicio,
            Date fechaFin, String rfc, Long idSolicitud) {
        numeroAsunto = numeroAsunto != null ? numeroAsunto.toUpperCase(Locale.ROOT) : null;
        rfc = rfc != null ? rfc.toUpperCase(Locale.ROOT) : null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t FROM Tramite t WHERE 1=1 ");
        sb.append("AND t.solicitud.idSolicitud = :idSolicitud ");
        if (numeroAsunto != null) {
            sb.append(this.condicionNumAsunto);
        }
        if (rfc != null) {
            sb.append("AND t.solicitud.solicitante.rfc LIKE :rfc ");
        }
        if (fechaInicio != null && fechaFin != null) {
            sb.append(this.condicionFechaRecepcion);
        }
        sb.append(this.orderFechaRecepcion);
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("idSolicitud", idSolicitud);
        if (numeroAsunto != null) {
            query.setParameter(this.parametroNumeroAsunto, numeroAsunto.toUpperCase() + "%");
        }
        if (rfc != null) {
            query.setParameter(this.parametroRfc, rfc.toUpperCase() + "%");
        }
        if (fechaInicio != null && fechaFin != null) {
            query.setParameter(this.parametroFechaInicio, fechaInicio);
            query.setParameter(this.parametroFechaFin, fechaFin);
        }
        return (Tramite) query.getSingleResult();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<Tramite> obtenerTramitesPorFiltrosOficialia(String numeroAsunto, String estadoProcesal,
            Date fechaInicio, Date fechaFin, String rfc) {
        StringBuilder sb = new StringBuilder();
        numeroAsunto = numeroAsunto != null ? numeroAsunto.toUpperCase(Locale.ROOT) : null;
        rfc = rfc != null ? rfc.toUpperCase(Locale.ROOT) : null;
        sb.append("SELECT t FROM Tramite t, Solicitud sol, Solicitante solicitante WHERE ");
        sb.append("t.solicitud.idSolicitud = sol.idSolicitud ");
        sb.append("AND solicitante.idSolicitud = sol.idSolicitud ");
        if (numeroAsunto != null) {
            sb.append(this.condicionNumAsunto);
        }
        if (rfc != null) {
            sb.append("AND solicitante.rfc LIKE :rfc ");
        }
        if (estadoProcesal != null) {
            sb.append(this.condicionEdoTramite);
        }
        if (fechaInicio != null && fechaFin != null) {
            sb.append(this.condicionFechaRecepcion);
        }
        sb.append(this.orderFechaRecepcion);

        Query query = getEntityManager().createQuery(sb.toString());
        if (numeroAsunto != null) {
            query.setParameter(this.parametroNumeroAsunto, numeroAsunto.toUpperCase() + "%");
        }
        if (rfc != null) {
            query.setParameter(this.parametroRfc, rfc.toUpperCase() + "%");
        }
        if (estadoProcesal != null) {
            query.setParameter(this.parametroEstadoProcesal, estadoProcesal);
        }
        if (fechaInicio != null && fechaFin != null) {
            query.setParameter(this.parametroFechaInicio, fechaInicio);
            query.setParameter(this.parametroFechaFin, fechaFin);
        }
        return query.getResultList();
    }

    @Override
    public String obtenerModalidadPorIdTipoTramite(Integer tipoTramite) {
        String descModalidad = "";
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT descripcionModalidad FROM TipoTramite tt WHERE ");
        sb.append("tt.idTipoTramite = :idTipoTramite ");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("idTipoTramite", tipoTramite);
        descModalidad = (String) query.getSingleResult();
        return descModalidad;
    }

    @Override
    public Integer verificarAdminResponsable(String rfcFuncionario) {
        Integer maxResults = 0;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM InfRoleUnidadAdmin rolunitt");
        sql.append(" WHERE rolunitt.responsable = 1");
        sql.append(" AND rolunitt.vigencia.blnActivo = 1");
        sql.append(" AND rolunitt.unidadAdminPK.claveRol = 'administrador'");
        sql.append(" AND rolunitt.unidadAdminPK.claveUsuario = :prfcFuncionario");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("prfcFuncionario", rfcFuncionario);
        maxResults = Integer.parseInt(query.getSingleResult().toString());
        return maxResults;
    }

    @Override
    public List<String> obtenerUnidadPorAdministrador(String administrador) {
        StringBuilder sb = new StringBuilder();
        administrador = administrador != null ? administrador.toUpperCase(Locale.ROOT) : null;
        sb.append("SELECT DISTINCT ruat.unidadAdminPK.claveUnidadAdministrativa FROM InfRoleUnidadAdmin ruat WHERE ");
        sb.append("ruat.vigencia.blnActivo = 1 ");
        sb.append(" AND ruat.unidadAdminPK.claveRol = 'administrador'");
        sb.append(" AND ruat.unidadAdminPK.claveUsuario = :padministrador");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("padministrador", administrador);
        return query.getResultList();
    }

    @Override
    public List<Long> obtenerTipoTramitePorAdministrador(String administrador) {
        StringBuilder sb = new StringBuilder();
        administrador = administrador != null ? administrador.toUpperCase(Locale.ROOT) : null;
        sb.append("SELECT DISTINCT ruat.unidadAdminPK.ideTipoTramite FROM InfRoleUnidadAdmin ruat WHERE ");
        sb.append("ruat.vigencia.blnActivo = 1 ");
        sb.append(" AND ruat.unidadAdminPK.claveRol = 'administrador'");
        sb.append(" AND ruat.unidadAdminPK.claveUsuario = :padministrador");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("padministrador", administrador);
        return query.getResultList() != null ? query.getResultList() : new ArrayList<Long>();
    }

    @Override
    public List<Tramite> obtenerTramitesPorUnidadTipo(List<String> listaUnidades, List<Long> listaTipoTramite,
            List<String> listaEstados) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT t FROM Tramite t  WHERE ");
        sb.append("t.estadoTramite IN :plistaEstados ");
        sb.append("AND t.solicitud.claveModalidad IN :plistaTipoTramite ");
        sb.append(" AND t.solicitud.solicitudDatosGenerales.unidadAdminBalanceo IN :plistaUnidades ");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("plistaEstados", listaEstados);
        query.setParameter("plistaTipoTramite", listaTipoTramite);
        query.setParameter("plistaUnidades", listaUnidades);
        return query.getResultList() != null ? query.getResultList() : new ArrayList<Tramite>();
    }
}
