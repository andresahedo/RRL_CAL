/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.comun.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface ObtenerUnidadesAdministrativasService extends Serializable {

    /**
     * Metodo para obtener un catalogo de unidades administrativas
     * 
     * @return Lista
     */
    List<UnidadAdministrativa> obtenerCatalogoUnidadesAdministrativas();

}
