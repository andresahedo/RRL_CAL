/**
 * 
 */
package mx.gob.sat.siat.juridica.base.api;

import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;

/**
 * @author Softtek
 * 
 */
public interface BandejaReasignarRecursoRevocacionFacade {

    DataPage obtenerTareasReasignar(FiltroBandejaTareaDTO filtroBandejaTareaDTO, String cveRol);

}
