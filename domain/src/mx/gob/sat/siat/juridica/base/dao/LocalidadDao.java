package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Localidad;

import java.util.List;

public interface LocalidadDao {

    List<Localidad> obtenerLocalidadPorClaveDelegacion(String claveDelegacion);

    List<Localidad> buscarLocalidadPorCP(String cp);

}
