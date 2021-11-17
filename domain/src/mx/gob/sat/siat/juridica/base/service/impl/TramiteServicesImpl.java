/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.TipoTramiteDAO;
import mx.gob.sat.siat.juridica.base.dao.TramiteDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.rrl.dao.RegistroRecursoRevocacionDAO;

/**
 * 
 * @author softtek
 * 
 */
@Service("tramiteServices")
public class TramiteServicesImpl extends BaseBusinessServices implements TramiteServices {

    /**
     * Id de la version
     */
    private static final long serialVersionUID = -1554188860334033540L;

    /**
     * Atributo privado "tramiteDao" tipo TramiteDao
     */
    @Autowired
    private TramiteDao tramiteDao;

    /**
     * Atributo privado "tipoTramiteDAO" tipo TipoTramiteDAO
     */
    @Autowired
    private TipoTramiteDAO tipoTramiteDAO;

    @Autowired
    private RegistroRecursoRevocacionDAO registroRecursoRevocacionDAO;

    /**
     * Atributo privado "bpmjmsDao" tipo TipoTramiteDAO
     */

    /**
     * Metodo para buscar un tramite por numero de asunto y id de solicitud
     * 
     * @param numeroAsunto
     * @param idSolicitud
     * @return Tramite
     */
    @Override
    public Tramite buscarTramite(String numeroAsunto, Long idSolicitud) {
        getLogger().debug("TramiteServicesImpl : buscarTramite");
        Tramite tramite = null;
        if (numeroAsunto != null && !numeroAsunto.isEmpty()) {
            tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        } else {
            tramite = tramiteDao.obtenerTramitePorIdSolicitud(idSolicitud);
        }
        return tramite;
    }

    @Override
    public Tramite buscarTramiteTarea(String numeroAsunto) {
        getLogger().debug("TramiteServicesImpl : buscarTramiteTarea");
        Tramite tramite = null;
        if (numeroAsunto != null && !numeroAsunto.isEmpty()) {
            tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        }
        return tramite;
    }

    /**
     * Metodo para buscar un tramite por numero de asunto y id de solicitud
     * 
     * @param numeroAsunto
     * @param idSolicitud
     * @return Tramite
     */
    @Override
    public Tramite obtenerTramitePorIdSolicitud(Long idSolicitud) {

        Tramite tramite = null;

        tramite = tramiteDao.obtenerTramitePorIdSolicitud(idSolicitud);

        return tramite;
    }

    /**
     * Metodo para obtener los tipos de tramite por filtros (id del tipo de tramite
     * y id de servicio)
     * 
     * @param idTipoTramite
     * @param idServicio
     * @return Lista de tipos de tramite
     */
    public List<TipoTramite> obtenerTiposDeTramitesPorFiltros(Integer idTipoTramite, String idServicio) {
        return tipoTramiteDAO.obtenerTiposDeTramitesPorFiltros(idTipoTramite, idServicio);
    }

    /**
     * Metodo para obtener los tipos de tramite por filtros
     * 
     * @param numeroAsunto
     * @param idTipoTramite
     * @param estadoProcesal
     * @param fechaInicio
     * @param fechaFin
     * @param rfc
     * @return
     */
    public List<Tramite> obtenerTramitesPorFiltros(String numeroAsunto, Integer idTipoTramite, String estadoProcesal,
            Date fechaInicio, Date fechaFin, String rfc) {
        List<Tramite> tramites = tramiteDao.obtenerTramitesPorFiltros(numeroAsunto, idTipoTramite, estadoProcesal,
                fechaInicio, fechaFin, rfc);
        for (Tramite tramite : tramites) {
            if (tramite.getSolicitud().getTipoTramiteSolicitud() != null) {
                tramite.getSolicitud().getTipoTramiteSolicitud().getIdTipoTramite();
                tramite.getSolicitud().getTipoTramiteSolicitud().getDescripcionModalidad();
            }
        }
        return tramites;
    }

    /**
     * Metodo para obtener los tipos de tramite por filtros
     *
     * @param numeroAsunto
     * @param idTipoTramite
     * @param estadoProcesal
     * @param fechaInicio
     * @param fechaFin
     * @param rfc
     * @param rfc            promovente
     * @return
     */
    public List<Tramite> obtenerTramitesPorAsuntosConcluidos(String numeroAsunto, String estadoProcesal,
            Date fechaInicio, Date fechaFin, String rfc, String rfcPromovente) {
        List<Tramite> tramites = tramiteDao.obtenerTramitesPorAsuntosConcluidos(numeroAsunto, estadoProcesal,
                fechaInicio, fechaFin, rfc, rfcPromovente);
        for (Tramite tramite : tramites) {
            if (tramite.getSolicitud().getTipoTramiteSolicitud() != null) {
                tramite.getSolicitud().getTipoTramiteSolicitud().getIdTipoTramite();
                tramite.getSolicitud().getTipoTramiteSolicitud().getDescripcionModalidad();
            }
        }
        return tramites;
    }

    public List<DocumentoSolicitud> obtenerDocumentosTramitesPorFiltrosOficialia(String numeroAsunto,
            String estadoProcesal, Date fechaInicio, Date fechaFin, String rfc, String folio) {
        List<DocumentoSolicitud> documentosTramite = tramiteDao.obtenerDocumentosPorFiltrosOficialia(numeroAsunto,
                estadoProcesal, fechaInicio, fechaFin, rfc, folio);
        return documentosTramite;
    }

    /**
     * Metodo para modificar un estado procesal por numero de asunto y clave del
     * estado
     * 
     * @param numeroAsunto
     * @param cveEstado
     * @throws SolicitudNoGuardadaException
     */
    public void modificaEstadoProcesal(String numeroAsunto, String cveEstado) throws SolicitudNoGuardadaException {
        Tramite tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        validaEstadoProcesal(cveEstado, tramite.getEstadoTramite().getClave());
        tramite.setEstadoTramite(EstadoTramite.parse(cveEstado));
        tramiteDao.modificarTramite(tramite);
    }

    @Override
    public void modificarTramite(Tramite tramite) {
        tramiteDao.modificarTramite(tramite);
    }

    /**
     * Metodo para validar un estado procesal por clave de estado y clave del estado
     * de tramite
     * 
     * @param cveEstado
     * @param cveEstadoTramite
     * @throws SolicitudNoGuardadaException
     */
    public void validaEstadoProcesal(String cveEstado, String cveEstadoTramite) throws SolicitudNoGuardadaException {
        // TODO Auto-generated method stub
    }

    @Override
    public List<Tramite> obtenerTramitesRechazadosPorFiltros(String numeroAsunto, String estadoProcesal,
            Date fechaInicio, Date fechaFin, String rfc) {
        List<Tramite> tramites = tramiteDao.obtenerTramitesRechazosPorFiltros(numeroAsunto, estadoProcesal, fechaInicio,
                fechaFin, rfc);
        for (Tramite tramite : tramites) {
            if (tramite.getSolicitud().getTipoTramiteSolicitud() != null) {
                tramite.getSolicitud().getTipoTramiteSolicitud().getIdTipoTramite();
                tramite.getSolicitud().getTipoTramiteSolicitud().getDescripcionModalidad();
            }
        }
        return tramites;
    }

    @Override
    public List<Tramite> obtenerTramitesDoctosRechazadosPorFiltros(String numeroAsunto, String estadoDocumentoSolicitud,
            Date fechaInicio, Date fechaFin, String rfc) {
        List<Tramite> tramites = tramiteDao.obtenerTramitesDoctosRechazosPorFiltros(numeroAsunto,
                estadoDocumentoSolicitud, fechaInicio, fechaFin, rfc);
        for (Tramite tramite : tramites) {
            if (tramite.getSolicitud().getTipoTramiteSolicitud() != null) {
                tramite.getSolicitud().getTipoTramiteSolicitud().getIdTipoTramite();
                tramite.getSolicitud().getTipoTramiteSolicitud().getDescripcionModalidad();
            }
        }
        return tramites;
    }

    @Override
    public Tramite obtenerTramitePorFiltrosAndIdSolicitud(String numeroAsunto, String estadoProcesal, Date fechaInicio,
            Date fechaFin, String rfc, Long idSolicitud) {
        Tramite tramite = tramiteDao.obtenerTramitePorFiltrosAndIdSolicitud(numeroAsunto, estadoProcesal, fechaInicio,
                fechaFin, rfc, idSolicitud);
        if (tramite.getSolicitud().getTipoTramiteSolicitud() != null) {
            tramite.getSolicitud().getTipoTramiteSolicitud().getDescripcionModalidad();
        }
        return tramite;
    }

    public List<Tramite> obtenerTramitesPorFiltrosAndIdSolicitud(String numeroAsunto, String estadoProcesal,
            Date fechaInicio, Date fechaFin, String rfc) {
        List<Tramite> tramites = tramiteDao.obtenerTramitesPorFiltrosOficialia(numeroAsunto, estadoProcesal,
                fechaInicio, fechaFin, rfc);
        for (Tramite tramite : tramites) {
            if (tramite.getSolicitud().getTipoTramiteSolicitud() != null) {
                tramite.getSolicitud().getTipoTramiteSolicitud().getDescripcionModalidad();

            }
        }
        return tramites;
    }

    @Override
    public List<Tramite> obtenerTramitesPorFiltrosOficialia(String numeroAsunto, String estadoProcesal,
            Date fechaInicio, Date fechaFin, String rfc) {
        List<Tramite> tramites = tramiteDao.obtenerTramitesPorFiltrosOficialia(numeroAsunto, estadoProcesal,
                fechaInicio, fechaFin, rfc);
        for (Tramite tramite : tramites) {
            if (tramite.getSolicitud().getTipoTramiteSolicitud() != null) {
                tramite.getSolicitud().getTipoTramiteSolicitud().getDescripcionModalidad();

            }
        }
        return tramites;
    }

    @Override
    public void actualizaDatosBP(int idInstancia, String numAsunto, String usuario) {
        Tramite tramite = tramiteDao.obtenerTramitePorId(numAsunto);
        if (tramite.getEstadoTramite() == EstadoTramite.PROMOVIDO
                || tramite.getEstadoTramite() == EstadoTramite.REMITIDO) {
            tramite.setEstadoTramite(EstadoTramite.EN_ESTUDIO);
            tramite.setFechaEstatus(new Date());
            tramiteDao.modificarTramite(tramite);
        }
    }

    @Override
    public boolean verificarAdminResponsable(String rfcFuncionario) {
        Integer admins = tramiteDao.verificarAdminResponsable(rfcFuncionario);
        return admins > NumerosConstantes.CERO;
    }

    @Override
    public List<String> obtenerAsuntosPorAdministrador(String administrador) {
        getLogger().debug("TramiteServicesImpl : obtenerAsuntosPorAdministrador");
        List<String> listaUnidades = tramiteDao.obtenerUnidadPorAdministrador(administrador);
        List<String> resultados = new ArrayList<String>();
        List<Long> listaTipoTramite = tramiteDao.obtenerTipoTramitePorAdministrador(administrador);

        List<String> listaEstados = new ArrayList<String>();
        listaEstados.add(EstadoTramite.PROMOVIDO.getClave());
        listaEstados.add(EstadoTramite.EN_ESTUDIO.getClave());
        listaEstados.add(EstadoTramite.REMITIDO.getClave());
        if (validarListas(listaUnidades, listaTipoTramite)) {
            List<Tramite> listaTramiteGen = tramiteDao.obtenerTramitesPorUnidadTipo(listaUnidades, listaTipoTramite,
                    listaEstados);
            for (Tramite tr : listaTramiteGen) {
                resultados.add(tr.getNumeroAsunto());
            }
        }
        return resultados;
    }

    private boolean validarListas(List<String> listaUnidades, List<Long> listaTipoTramite) {
        return !listaUnidades.isEmpty() && !listaTipoTramite.isEmpty();
    }

    @Override
    public boolean tieneDocumentosAnexados(String idSolicitud) {
        return !registroRecursoRevocacionDAO.obtenerDocumentoSolicitudAnexado(idSolicitud).isEmpty();
    }

}
