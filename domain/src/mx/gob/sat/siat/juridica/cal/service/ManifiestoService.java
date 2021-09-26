package mx.gob.sat.siat.juridica.cal.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Manifiesto;

import java.util.List;

public interface ManifiestoService {

    List<Manifiesto> obtenerManifiestosPorIdModalidad(Integer idModalidad);

    Manifiesto obtenerManifiestoPorId(Integer idManifiesto);

}
