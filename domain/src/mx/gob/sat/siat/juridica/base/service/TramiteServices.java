/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface TramiteServices extends Serializable {

    /**
     * Metodo para buscar un tramite por numero de asunto y id de
     * solicitud
     * 
     * @param numeroAsunto
     * @param idSolicitud
     * @return Tramite
     */
    Tramite buscarTramite(String numeroAsunto, Long idSolicitud);

    /**
     * Metodo para buscar un tramite por numero de asunto
     *
     * @param numeroAsunto
     * @return Tramite
     */
    Tramite buscarTramiteTarea(String numeroAsunto);

    /**
     * Metodo para modificar un estado procesal por numero de asunto y
     * clave del estado
     * 
     * @param numeroAsunto
     * @param cveEstado
     * @throws SolicitudNoGuardadaException
     */
    void modificaEstadoProcesal(String numeroAsunto, String cveEstado) throws SolicitudNoGuardadaException;

    void modificarTramite(Tramite tramite);

    /**
     * Metodo para validar un estado procesal por clave de estado y
     * clave del estado de tramite
     * 
     * @param cveEstado
     * @param cveEstadoTramite
     * @throws SolicitudNoGuardadaException
     */
    void validaEstadoProcesal(String cveEstado, String cveEstadoTramite) throws SolicitudNoGuardadaException;

    /**
     * Metodo para obtener los tipos de tramite por filtros (id del
     * tipo de tramite y id de servicio)
     * 
     * @param idTipoTramite
     * @param idServicio
     * @return Lista de tipos de tramite
     */
    List<TipoTramite> obtenerTiposDeTramitesPorFiltros(Integer idTipoTramite, String idServicio);

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
    List<Tramite> obtenerTramitesPorFiltros(String numeroAsunto, Integer idTipoTramite, String estadoProcesal,
            Date fechaInicio, Date fechaFin, String rfc);

    List<Tramite> obtenerTramitesPorAsuntosConcluidos(String numeroAsunto, String estadoProcesal,
            Date fechaInicio, Date fechaFin, String rfc, String rfcPromovente);

    Tramite obtenerTramitePorIdSolicitud(Long idSolicitud);

    List<Tramite> obtenerTramitesRechazadosPorFiltros(String numeroAsunto, String estadoProcesal, Date fechaIni,
            Date fechaFin, String rfc);

    List<Tramite> obtenerTramitesDoctosRechazadosPorFiltros(String numeroAsunto, String estadoDocto, Date fechaIni,
            Date fechaFin, String rfc);

    List<DocumentoSolicitud> obtenerDocumentosTramitesPorFiltrosOficialia(String numeroAsunto, String estadoProcesal,
            Date fechaInicio, Date fechaFin, String rfc, String folio);

    Tramite obtenerTramitePorFiltrosAndIdSolicitud(String numeroAsunto, String estadoProcesal, Date fechaInicio,
            Date fechaFin, String rfc, Long idSolicitud);

    List<Tramite> obtenerTramitesPorFiltrosOficialia(String numeroAsunto, String estadoProcesal, Date fechaInicio,
            Date fechaFin, String rfc);

    void actualizaDatosBP(int idInstancia, String numAsunto, String usuario);

    boolean verificarAdminResponsable(String rfcFuncionario);

	boolean tieneDocumentosAnexados(String idSolicitud);

    List<Long> obtenerTipoTramitePorAdministrador(String administrador);

    List<String> obtenerUnidadPorAdministrador(String administrador);
}
