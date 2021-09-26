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

@ManagedBean(name = "conceptoConverter")
@SessionScoped
@FacesConverter("conceptoConverter")
public class CatalogoConceptoCovnerter extends CatalogoConverter {

    @ManagedProperty(value = "#{abogadoBussines}")
    private transient AbogadoBussines abogadoBussines;

    private List<CatalogoDTO> catalogoConcepto = new ArrayList<CatalogoDTO>();

    @PostConstruct
    public void initFiltro() {
        this.catalogoConcepto = abogadoBussines.obtenerCatalogos("concepto");
    }

    @Override
    public List<CatalogoDTO> getListaCatalogos() {
        return this.catalogoConcepto;
    }

    public AbogadoBussines getAbogadoBussines() {
        return abogadoBussines;
    }

    public void setAbogadoBussines(AbogadoBussines abogadoBussines) {
        this.abogadoBussines = abogadoBussines;
    }

    public List<CatalogoDTO> getCatalogoConcepto() {
        return catalogoConcepto;
    }

    public void setCatalogoConcepto(List<CatalogoDTO> catalogoConcepto) {
        this.catalogoConcepto = catalogoConcepto;
    }

}
