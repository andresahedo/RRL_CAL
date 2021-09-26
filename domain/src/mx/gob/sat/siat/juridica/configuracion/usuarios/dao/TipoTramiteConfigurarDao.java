package mx.gob.sat.siat.juridica.configuracion.usuarios.dao;

import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.TipoTramiteConfigurar;

import java.io.Serializable;
import java.util.List;

public interface TipoTramiteConfigurarDao extends Serializable {

    List<TipoTramiteConfigurar> obtenerServicios();

    Integer validarServicio(String idServicio, String idUnidadAdministrativa);

}
