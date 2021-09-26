/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoUnidadAdministrativa;

import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface UnidadAdministrativaDao {

    /**
     * Metodo para obtener una lista de Unidades Administrativas
     * 
     * @return Lista de Unidades Administrativas
     */
    List<UnidadAdministrativa> obtenerUnidadesAdministrativas();

    /**
     * Metodo para obtener un catalogo de Unidades Administrativas
     * 
     * @return Lista de Unidades Administrativas
     */
    List<UnidadAdministrativa> obtenerCatalogo();

    /**
     * Metodo para obtener Unidades Administrativas por tipo de Unidad
     * Administrativa
     * 
     * @param tipoUnidad
     * @return Lista de Unidades Administrativas
     */
    List<UnidadAdministrativa> obtenerUnidadesPorTipo(TipoUnidadAdministrativa tipoUnidad);

    /**
     * Metodo para obtener una Unidad Administrativa por tramite
     * 
     * @param idTramite
     * @return Unidad Administrativa
     */
    UnidadAdministrativa obtenerUnidadPorTramite(String idTramite);

    /**
     * Metodo para obtener una Unidad Administrativa por clave local
     * 
     * @param claveLocal
     * @return Unidad Administrativa
     */
    UnidadAdministrativa obtenerUnidadPorClaveLocal(String claveLocal);

    UnidadAdministrativa obtenerUnidadPorId(String clave);

    List<UnidadAdministrativa> obtenerUnidadesTodas();
    
    /**
     * Obtiene la lista de Unidades Administrativas que se encuentran vigentes
     * @return  List<UnidadAdministrativa>
     */
    List<UnidadAdministrativa> obtenerUnidAdmvasTodas();
    List<UnidadAdministrativa> obtenerUnidAdmvasVigentes();
    

}
