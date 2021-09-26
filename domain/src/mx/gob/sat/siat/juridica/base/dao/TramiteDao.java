/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Secuencia;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface TramiteDao extends Serializable {

    /**
     * Metodo para obtener un tramite por id
     * 
     * @param idTramite
     * @return Tramite
     */
    Tramite obtenerTramitePorId(String idTramite);

    /**
     * Metodo para obtener una secuencia de folios
     * 
     * @param secuencia
     * @return Secuencia
     */
    Secuencia obtenerSecuenciaFolio(Secuencia secuencia);

    /**
     * Metodo para obtener un tramite por resolucion
     * 
     * @param numResolucion
     * @return Tramite
     */
    Tramite obtenerTramitePorResolucion(String numResolucion, String numAsunto);

    /**
     * Metodo para guardar una secuencia de folios
     * 
     * @param secuencia
     */
    void guardarSecuenciaFolio(Secuencia secuencia);

    /**
     * Metodo para actualizar una secuencia de folios
     * 
     * @param secuencia
     */
    void actualizarSecuenciaFolio(Secuencia secuencia);

    /**
     * Metodo para crear un tramite
     * 
     * @param tramite
     */
    void crearTramite(Tramite tramite);

    /**
     * Metodo para modificar un tramite
     * 
     * @param tramite
     */
    void modificarTramite(Tramite tramite);

    /**
     * Metodo para obtener un tramite por el id de solicitud
     * 
     * @param idSolicitud
     * @return Tramite
     */
    Tramite obtenerTramitePorIdSolicitud(Long idSolicitud);

    /**
     * Metodo para obtener una lista de tramites por folios
     * 
     * @param numeroAsunto
     *            , idTipoTramite, estadoProcesal, fechaInicio,
     *            fechaFin, rfc
     * @return Lista de Tramite
     */
    List<Tramite> obtenerTramitesPorFiltros(String numeroAsunto, Integer idTipoTramite, String estadoProcesal,
            Date fechaInicio, Date fechaFin, String rfc);

    List<Tramite> obtenerTramitesPorAsuntosConcluidos(String numeroAsunto, String estadoProcesal,
            Date fechaInicio, Date fechaFin, String rfc, String rfcPromovente);

    List<Tramite> obtenerTramitesRechazosPorFiltros(String numeroAsunto, String estadoProcesal, Date fechaInicio,
            Date fechaFin, String rfc);

    List<Tramite> obtenerTramitesDoctosRechazosPorFiltros(String numeroAsunto, String estadoDocto, Date fechaInicio,
            Date fechaFin, String rfc);

    List<DocumentoSolicitud> obtenerDocumentosPorFiltrosOficialia(String numeroAsunto, String estadoProcesal,
            Date fechaInicio, Date fechaFin, String rfc, String folio);

    Tramite obtenerTramitePorFiltrosAndIdSolicitud(String numeroAsunto, String estadoProcesal, Date fechaInicio,
            Date fechaFin, String rfc, Long idSolicitud);

    List<Tramite> obtenerTramitesPorFiltrosOficialia(String numeroAsunto, String estadoProcesal, Date fechaInicio,
            Date fechaFin, String rfc);

    String obtenerModalidadPorIdTipoTramite(Integer tipoTramite);

    Integer verificarAdminResponsable(String rfcFuncionario);

    List<String> obtenerUnidadPorAdministrador(String administrador);

    List<Long> obtenerTipoTramitePorAdministrador(String administrador);

    List<Tramite> obtenerTramitesPorUnidadTipo(List<String> listaUnidades, List<Long> listaTipoTramite, List<String> listaEstados);

}
