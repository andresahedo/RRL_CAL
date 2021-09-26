/**
 * 
 */
package mx.gob.sat.siat.juridica.base.api;

import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dto.BandejaConsultasDTO;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;

import java.util.List;

/**
 * @author Softtek
 * 
 */
public interface BandejaConsultasFacade extends BaseFacade {

    List<BandejaConsultasDTO> obtenerRecursos(FiltroBandejaTareaDTO filtroBandejaTareaDTO);
   
    DataPage obtenerRecursosConcluidosLazy(FiltroBandejaTareaDTO filtroBandejaTareaDTO);
    
    List<CatalogoDTO> obtenerCatalogoEstados();

    List<CatalogoDTO> obtenerTiposTramites();

    List<BandejaConsultasDTO> obtenerRecursosOficialia(FiltroBandejaTareaDTO filtroBandejaTareaDTO);

    boolean verificarAdminResponsable(String rfcFuncionario);

}
