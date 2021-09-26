/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;

import java.util.List;

/**
 * 
 * @author Softtek ***************************************** Modifica
 *         antonio.flores se agrega obtenerTiposDeTramitesPorId
 * @since 03/27/2014
 */
public interface TipoTramiteDAO {

    /**
     * Metodo para buscar un tramite por id
     * 
     * @param idTipoTramite
     */
    String findById(Integer idTipoTramite);

    /**
     * Metodo para obtener la descripcion del servicio de un tramite
     * 
     * @param idTipoTramite
     */
    String obtenerDescripcionServicio(Integer idTipoTramite);

    /**
     * Metodo para obtener una lista de los tipos de tramite
     * 
     * @param idTipoTramite
     *            , idServicio
     * @return Lista de tipos de tramite
     */
    List<TipoTramite> obtenerTiposDeTramitesPorFiltros(Integer idTipoTramite, String idServicio);

    /**
     * M&eacute;todo para obtener los tipo tramite donde el modulo es
     * 1
     * 
     * @return
     */
    List<TipoTramite> obtenerTipoTramiteModulo1();

    /**
     * Metodo para obtener un tipo tramite con el idTipoTramite,
     * regresa solo un registro.
     * 
     * @param idTipoTramite
     * @since 03/27/2014
     * @return tipos de tramite del id asociado.
     */
    TipoTramite obtenerTiposDeTramitesPorId(Integer idTipoTramite);

    /**
     * Metodo para obtener todas las modalidades de los tramites.
     */

    List<TipoTramite> obtenerTodosTramites(String servicio);

    /**
     * Metodo para obtener los tramites
     */
    List<TipoTramite> buscarTramites();

    List<TipoTramite> todasModalidades();

    String obtenerDescripcionModalidad(Integer idTipoTramite);

    List<String> buscarIdServicios();

    List<TipoTramite> buscarTramitesAsignadosUnidad(String unidadAdmin);
}
