/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author softtek
 */
public interface UnidadAdministrativaServices extends Serializable {

    /**
     * Metodo para obtener un catalogo de unidades administrativas
     * 
     * @return Lista de unidades administrativas
     */
    List<UnidadAdministrativa> obtenerCatalogo();
    
    List<UnidadAdministrativa> obtenerCatalogoVig();

    UnidadAdministrativa obtenerUnidadPorClaveLocal(String claveLocal);

    UnidadAdministrativa obtenerUnidadPorId(String idUniAdmin);

    String obtenerResponsableDeUnidadAdministrativa(String unidadAdmin, String modalidad);

    String obtenerUnidadPorTipoRol(String tipoTramite, String tipoRolContribuyente);
}
