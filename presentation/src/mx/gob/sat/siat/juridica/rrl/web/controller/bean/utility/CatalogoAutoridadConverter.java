package mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility;

import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AbogadoBussines;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "autoridadConverter")
@SessionScoped
@FacesConverter("autoridadConverter")
public class CatalogoAutoridadConverter implements Converter {

    @ManagedProperty(value = "#{abogadoBussines}")
    private transient AbogadoBussines abogadoBussines;

    private List<UnidadAdministrativaDTO> catalogoAutoridad = new ArrayList<UnidadAdministrativaDTO>();

    @PostConstruct
    public void initFiltro() {
        this.catalogoAutoridad = abogadoBussines.obtenerCatalogoAutoridad();
    }

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        return obtenerCatalogoPorClave(arg2);

    }

    private UnidadAdministrativaDTO obtenerCatalogoPorClave(String arg2) {
        List<UnidadAdministrativaDTO> catalogoDTOs = this.catalogoAutoridad;
        for (UnidadAdministrativaDTO catalogoDTO : catalogoDTOs) {
            if (catalogoDTO.getClave().equals(arg2)) {
                return catalogoDTO;
            }
        }
        return new UnidadAdministrativaDTO();
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        if (arg2 != null && !arg2.equals("")) {
            UnidadAdministrativaDTO catalogoDTO = (UnidadAdministrativaDTO) arg2;
            return catalogoDTO.getClave();
        }

        return "";
    }

    public AbogadoBussines getAbogadoBussines() {
        return abogadoBussines;
    }

    public void setAbogadoBussines(AbogadoBussines abogadoBussines) {
        this.abogadoBussines = abogadoBussines;
    }

    public List<UnidadAdministrativaDTO> getCatalogoAutoridad() {
        return catalogoAutoridad;
    }

    public void setCatalogoAutoridad(List<UnidadAdministrativaDTO> catalogoAutoridad) {
        this.catalogoAutoridad = catalogoAutoridad;
    }

}
