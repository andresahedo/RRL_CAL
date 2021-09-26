package mx.gob.sat.siat.juridica.configuracion.usuarios.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.TipoTramiteUnidadAdministrativaCatalogo;

import java.io.Serializable;
import java.util.List;

public interface TipoTramiteUnidadAdministrativaDao extends Serializable {

    /**
     * Metodo para buscar los tramites que administra la unidad
     * administrativa seleccionada.
     * 
     * @param claveUnidad
     * @return Lista de objetos
     *         TipoTramiteUnidadAdministrativaCatalogo
     */
    List<TipoTramiteUnidadAdministrativaCatalogo> buscarTramitesUnidad(String claveUnidad);

    List<TipoTramiteUnidadAdministrativaCatalogo> buscarTramitesAsignadosModificar(String idUnidad,
            int idTipoTramite);

    void actualizarTramitesUnidad(TipoTramiteUnidadAdministrativaCatalogo tipoTramite);
}
