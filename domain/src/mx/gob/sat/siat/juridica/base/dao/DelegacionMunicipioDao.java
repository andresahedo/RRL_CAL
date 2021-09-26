package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DelegacionMunicipio;

import java.util.List;

public interface DelegacionMunicipioDao {

    List<DelegacionMunicipio> obtenerDelegacionesMunicipios();

    List<DelegacionMunicipio> obtenerDelegacionMunicipioPorCveEstado(String claveEstado);

}
