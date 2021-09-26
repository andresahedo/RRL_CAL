package mx.gob.sat.siat.juridica.base.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CatalogoDTOTransformer extends DTOTransformer<List<UnidadAdministrativa>, List<CatalogoDTO>> {

    /**
     * 
     */
    private static final long serialVersionUID = -6401401828939772448L;

    @Override
    public List<CatalogoDTO> transformarDTO(List<UnidadAdministrativa> listaUnidades) {
        List<CatalogoDTO> listaCatalogo = new ArrayList<CatalogoDTO>();
        for (UnidadAdministrativa ua : listaUnidades) {
            CatalogoDTO nuevoCatalogo = new CatalogoDTO();
            nuevoCatalogo.setClave(ua.getClave());
            nuevoCatalogo.setDescripcion(ua.getNombre());
            listaCatalogo.add(nuevoCatalogo);
        }
        return listaCatalogo;
    }

    public List<CatalogoDTO> transformarCatalogoDDTO(List<CatalogoD> lista) {
        List<CatalogoDTO> listaCatalogo = new ArrayList<CatalogoDTO>();
        for (CatalogoD cd : lista) {
            CatalogoDTO nuevoCatalogo = new CatalogoDTO();
            nuevoCatalogo.setClave(cd.getClave());
            nuevoCatalogo.setDescripcion(cd.getDescripcionGenerica1());
            listaCatalogo.add(nuevoCatalogo);
        }
        return listaCatalogo;
    }

    public List<CatalogoDTO> transformarEnumeracionTrDTO(List<EnumeracionTr> lista) {
        List<CatalogoDTO> listaCatalogo = new ArrayList<CatalogoDTO>();
        for (EnumeracionTr e : lista) {
            CatalogoDTO nuevoCatalogo = new CatalogoDTO();
            nuevoCatalogo.setClave(e.getId().getCveEnumeracion());
            nuevoCatalogo.setDescripcion(e.getDescripcion());
            listaCatalogo.add(nuevoCatalogo);
        }
        return listaCatalogo;
    }

    public List<CatalogoDTO> transformaCatalogoDDTO(List<CatalogoD> lista) {
        List<CatalogoDTO> listaCatalogo = new ArrayList<CatalogoDTO>();
        for (CatalogoD e : lista) {
            CatalogoDTO nuevoCatalogo = new CatalogoDTO();
            nuevoCatalogo.setClave(e.getClave());
            nuevoCatalogo.setDescripcion(e.getDescripcionGenerica1());
            listaCatalogo.add(nuevoCatalogo);
        }
        return listaCatalogo;
    }

    public List<CatalogoDTO> transformarPersonas(List<Persona> listaPersonas) {
        List<CatalogoDTO> listaCatalogo = new ArrayList<CatalogoDTO>();
        for (Persona p : listaPersonas) {
            CatalogoDTO nuevoCatalogo = new CatalogoDTO();
            nuevoCatalogo.setClave(p.getRfc());
            nuevoCatalogo.setDescripcion(p.getNombre() + " " + p.getApellidoPaterno() + " " + p.getApellidoMaterno());
            listaCatalogo.add(nuevoCatalogo);
        }
        return listaCatalogo;
    }

    public List<CatalogoDTO> transformarCatalogo(List<Catalogo> listaCatalogo) {
        List<CatalogoDTO> lista = new ArrayList<CatalogoDTO>();
        for (Catalogo c : listaCatalogo) {
            CatalogoDTO nuevoCatalogo = new CatalogoDTO();
            nuevoCatalogo.setClave(c.getClave());
            nuevoCatalogo.setDescripcion(c.getDescripcion());
            lista.add(nuevoCatalogo);
        }
        return lista;
    }

    public List<CatalogoDTO> transformarTipoTramite(List<TipoTramite> listaCatalogo) {
        List<CatalogoDTO> lista = new ArrayList<CatalogoDTO>();
        for (TipoTramite c : listaCatalogo) {
            CatalogoDTO nuevoCatalogo = new CatalogoDTO();
            nuevoCatalogo.setClave(c.getIdTipoTramite().toString());
            nuevoCatalogo.setDescripcion(c.getDescripcionModalidad());
            lista.add(nuevoCatalogo);
        }
        return lista;
    }

    public List<CatalogoDTO> transformarTiposPersona(List<CatalogoD> comboTiposPersona) {

        List<CatalogoDTO> comboTiposPersonaDTO = new ArrayList<CatalogoDTO>();
        for (CatalogoD cat : comboTiposPersona) {
            CatalogoDTO tipoPersona = new CatalogoDTO();
            tipoPersona.setClave(cat.getClave());
            tipoPersona.setDescripcion(cat.getDescripcionGenerica1());
            comboTiposPersonaDTO.add(tipoPersona);

        }
        return comboTiposPersonaDTO;
    }

}
