package mx.gob.sat.siat.juridica.base.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UnidadAdministrativaDTOTransformer extends
        DTOTransformer<List<UnidadAdministrativa>, List<UnidadAdministrativaDTO>> {

    /**
     * 
     */
    private static final long serialVersionUID = 5468108378827763199L;

    @Override
    public List<UnidadAdministrativaDTO> transformarDTO(List<UnidadAdministrativa> listaUnidades) {
        List<UnidadAdministrativaDTO> listaCatalogo = new ArrayList<UnidadAdministrativaDTO>();
        for (UnidadAdministrativa ua : listaUnidades) {
            UnidadAdministrativaDTO nuevoCatalogo = new UnidadAdministrativaDTO();
            nuevoCatalogo.setClave(ua.getClave());
            nuevoCatalogo.setDescripcion(ua.getDescripcion());
            nuevoCatalogo.setNivel(ua.getNivel());
            nuevoCatalogo.setTipoUnidad(ua.getIdeTipoUnidadAdministrativa() != null ? ua
                    .getIdeTipoUnidadAdministrativa().getClave() : null);
            nuevoCatalogo.setNombre(ua.getNombre());
            listaCatalogo.add(nuevoCatalogo);
        }
        return listaCatalogo;
    }

    public UnidadAdministrativa transformarModel(UnidadAdministrativaDTO unidadDTO) {

        UnidadAdministrativa unidad = new UnidadAdministrativa();
        unidad.setClave(unidadDTO.getClave());
        unidad.setNombre(unidadDTO.getNombre());
        unidad.setDescripcion(unidadDTO.getDescripcion());

        return unidad;
    }

}
