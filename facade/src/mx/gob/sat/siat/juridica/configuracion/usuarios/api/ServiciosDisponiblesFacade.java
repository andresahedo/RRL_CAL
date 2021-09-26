package mx.gob.sat.siat.juridica.configuracion.usuarios.api;

import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PermisosAUDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.ServiciosDTO;

import java.util.List;

public interface ServiciosDisponiblesFacade extends BaseFacade {

    List<ServiciosDTO> obtenerServiciosRol(String rol);

    List<PermisosAUDTO> obtenerPermisos();

}
