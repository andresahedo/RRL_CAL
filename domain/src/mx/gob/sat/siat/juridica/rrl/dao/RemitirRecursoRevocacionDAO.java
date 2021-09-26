/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;

import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface RemitirRecursoRevocacionDAO {

    /**
     * Metodo para obtener una lista de unidades administrativas
     * 
     * @return
     */
     List<UnidadAdministrativa> obtenerUnidadesAdministrativas();

}
