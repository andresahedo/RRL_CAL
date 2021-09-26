package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Colonia;

import java.util.List;

public interface ColoniaDao {

    List<Colonia> obtenerColoniaPorCodigoPostal(String cp);

    List<Colonia> obtenerColonias();

    List<Colonia> buscarColoniaPorClaveDelegacion(String claveDelegacion);

    List<Colonia> buscarColoniaPorCveLocalidad(String claveLocalidad);
}
