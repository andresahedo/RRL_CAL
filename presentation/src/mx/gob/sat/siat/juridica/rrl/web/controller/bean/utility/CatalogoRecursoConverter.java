package mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.AbogadoBussines;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.convert.FacesConverter;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "recursoConverter")
@SessionScoped
@FacesConverter("recursoConverter")
public class CatalogoRecursoConverter extends CatalogoConverter {

    @ManagedProperty(value = "#{abogadoBussines}")
    private transient AbogadoBussines abogadoBussines;

    private List<CatalogoDTO> catalogoRecurso = new ArrayList<CatalogoDTO>();

    @PostConstruct
    public void initFiltro() {
        this.catalogoRecurso = abogadoBussines.obtenerCatalogos("recurso");
    }

    @Override
    public List<CatalogoDTO> getListaCatalogos() {
        return this.catalogoRecurso;
    }

    public AbogadoBussines getAbogadoBussines() {
        return abogadoBussines;
    }

    public void setAbogadoBussines(AbogadoBussines abogadoBussines) {
        this.abogadoBussines = abogadoBussines;
    }

    public List<CatalogoDTO> getCatalogoRecurso() {
        return catalogoRecurso;
    }

    public void setCatalogoRecurso(List<CatalogoDTO> catalogoRecurso) {
        this.catalogoRecurso = catalogoRecurso;
    }

}
