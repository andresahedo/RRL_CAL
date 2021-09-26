
/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.TramiteReprocesar;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface TramiteReprocesarDAO extends Serializable {

    /**
     * Metodo para obtener un tramite por id
     * 
     * @param idTramite
     * @return Tramite
     */
    TramiteReprocesar obtenerTramitePorId(String idTramite);

    /**
     * Metodo para crear un tramite
     * 
     * @param tramite
     */
    void guardarTramite(TramiteReprocesar tramite);

    /**
     * Metodo para modificar un tramite
     * 
     * @param tramite
     */
    void modificarTramite(TramiteReprocesar tramite);


    /**
     * Metodo para obtener una lista de tramites por folios
     * 
     * @param numeroAsunto
     *            , idTipoTramite, estadoProcesal, fechaInicio,
     *            fechaFin, rfc
     * @return Lista de Tramite
     */
    List<TramiteReprocesar> obtenerTramitesPorFiltros(String numeroAsunto, String tipoError, String estatusEnvio,
            Integer reintentos, Date fechaRegistro);
    
    
    List<TramiteReprocesar> obtenerTramitesAReprocesar();
    
    List<TramiteReprocesar> obtenerTramitesReprocesadosFecha(Date date);

   
}
