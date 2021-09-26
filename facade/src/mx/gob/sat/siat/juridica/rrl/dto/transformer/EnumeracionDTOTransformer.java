package mx.gob.sat.siat.juridica.rrl.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.EnumeracionTr;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EnumeracionDTOTransformer extends DTOTransformer<List<EnumeracionTr>, List<CatalogoDTO>> {

    /**
     * 
     */
    private static final long serialVersionUID = 171589282950893492L;

    @Override
    public List<CatalogoDTO> transformarDTO(List<EnumeracionTr> listaEnumeraciones) {
        List<CatalogoDTO> listaCatalogo = new ArrayList<CatalogoDTO>();
        for (EnumeracionTr e : listaEnumeraciones) {
            CatalogoDTO nuevoCatalogo = new CatalogoDTO();
            nuevoCatalogo.setClave(e.getId().getCveEnumeracion());
            nuevoCatalogo.setDescripcion(e.getDescripcion());
            listaCatalogo.add(nuevoCatalogo);
        }
        return listaCatalogo;
    }

}
