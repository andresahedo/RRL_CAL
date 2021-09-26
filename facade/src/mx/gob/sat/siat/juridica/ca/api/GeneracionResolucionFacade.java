/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.api;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;

import java.util.List;

public interface GeneracionResolucionFacade extends BaseFacade {

    List<CatalogoDTO> obtenerResolucionCatalog(String type);

    List<CatalogoDTO> obtenerAutorizadores(String type);

}
