package mx.gob.sat.siat.juridica.configuracion.usuarios.dao;

import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.ServiciosModel;

import java.util.List;

public interface ServiciosDisponiblesDAO {

    List<ServiciosModel> obtenerServiciosRol(String rol);

}
