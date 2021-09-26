package mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.util.List;

public abstract class CatalogoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        return obtenerCatalogoPorClave(arg2);

    }

    private CatalogoDTO obtenerCatalogoPorClave(String arg2) {
        List<CatalogoDTO> catalogoDTOs = getListaCatalogos();
        for (CatalogoDTO catalogoDTO : catalogoDTOs) {
            if (catalogoDTO.getClave().equals(arg2)) {
                return catalogoDTO;
            }
        }
        return new CatalogoDTO();
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        if (arg2 != null && !arg2.equals("")) {
            CatalogoDTO catalogoDTO = (CatalogoDTO) arg2;
            return catalogoDTO.getClave();
        }

        return "";
    }

    public abstract List<CatalogoDTO> getListaCatalogos();

}
