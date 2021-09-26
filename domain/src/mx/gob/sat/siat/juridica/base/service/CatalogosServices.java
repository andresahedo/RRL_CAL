package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Descripcion;

import java.io.Serializable;

public interface CatalogosServices extends Serializable {
    Descripcion buscarDescripcionPorId(Long idDescripcion);

    Descripcion buscarDescripcionPorTipoIdentificador(String tipo, String identificador);
}
