package mx.gob.sat.siat.juridica.configuracion.usuarios.service;

import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.ServiciosModel;

import java.util.List;

public interface ServiciosDisponiblesService {

    List<ServiciosModel> obtenerServiciosRol(String rol);

}
