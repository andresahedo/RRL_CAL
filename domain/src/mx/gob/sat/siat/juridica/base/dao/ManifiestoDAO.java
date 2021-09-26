package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Manifiesto;

import java.io.Serializable;
import java.util.List;

public interface ManifiestoDAO extends Serializable {

    List<Manifiesto> obtenerManifiestos();

    List<Manifiesto> obtenerManifiestosPorIdModalidad(Integer idModalidad);

    Manifiesto obtenerManifiestoPorId(Integer idManifiesto);
}
