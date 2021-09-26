package mx.gob.sat.siat.juridica.configuracion.usuarios.api;

import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosPersonaTareaDTO;

import java.util.List;

public interface ReasignarTareaFacade {


    PersonaInterna obtenerEmpleado(Long numeroEmpleado);

    List<CatalogoDTO> getTiposDeTramite();

    List<CatalogoDTO> getModalidadByTipoTramite(String idServicio);

    List<CatalogoDTO> getRoles(List<String> roles);

    void reasignar(List<DatosPersonaTareaDTO> idAsuntos, String permiso, String rfcAsignar, String reasignador);
}
