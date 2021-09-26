/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.CatalogoD;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface CatalogoDServices extends Serializable {

    /**
     * Metodo para obtener una lista de catalogos por id
     * 
     * @param idCatalogoH
     * @return List<CatalogoD>
     */
    List<CatalogoD> obtenerCatalogoPorIdH(String idCatalogoH);
    
    /**
     * Obtiene el catalogo de sentidos perdedores
     * @param tipoCatalogo
     * @param descGen2
     * @return 
     */
    List<CatalogoD> obtenerSentidosResolucionPerdedores(String tipoCatalogo, String descGen2);

    /**
     * Metodo para obtener un catalogo por clave
     * 
     * @param clave
     * @return
     */
    CatalogoD obtenerCatalogoPorClave(String clave);

    Date obtenerFechaRequerimiento(String numeroAsunto);
}
