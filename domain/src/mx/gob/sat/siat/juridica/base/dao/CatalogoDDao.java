/*
 *  Todos los Derechos Reservados © 2013 SAT
 *  Servicio de Administracion Tributaria (SAT).
 *  
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.CatalogoD;

import java.util.Date;
import java.util.List;

/**
 * Interface para obtener una lista de catalogos
 * 
 * ya sea por clave o por Id
 * 
 * @author Softtek
 */
public interface CatalogoDDao {

    List<CatalogoD> obtenerCatalogoPorIdH(String idCatalogoH);

    CatalogoD obtenerCatalogoPorClave(String clave);

    List<CatalogoD> obtenerCatalogoPorCodGen1(String codGen1);

    List<CatalogoD> comboTipoPersona();

    List<CatalogoD> obtenerSentidosModH(String tipoInv);
    
    List<CatalogoD> obtenerSentidosResolucionPerdedores(String tipoCatalogo, String descGen2);

    Date obtenerFechaRequerimiento(String numeroAsunto);

}
