package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Descripcion;

/**
 * Interface para obtener una registros de diversos catalogos
 * 
 * @author Softtek
 */
public interface CatalogosDao {
    Descripcion buscarDescripcionPorId(Long idDescripcion);

    Descripcion buscarDescripcionPorTipoIdentificador(String tipo, String identificador);
}
