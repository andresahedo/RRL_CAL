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

@ManagedBean(name = "procedimientoConverter")
@SessionScoped
@FacesConverter("procedimientoConverter")
public class CatalogoProcedimientoConverter extends CatalogoConverter {

    @ManagedProperty(value = "#{abogadoBussines}")
    private transient AbogadoBussines abogadoBussines;

    private List<CatalogoDTO> catalogoProcedimiento = new ArrayList<CatalogoDTO>();

    @PostConstruct
    public void initFiltro() {
        this.catalogoProcedimiento = abogadoBussines.obtenerCatalogos("procedimiento");
    }

    @Override
    public List<CatalogoDTO> getListaCatalogos() {
        return this.catalogoProcedimiento;
    }

    public AbogadoBussines getAbogadoBussines() {
        return abogadoBussines;
    }

    public void setAbogadoBussines(AbogadoBussines abogadoBussines) {
        this.abogadoBussines = abogadoBussines;
    }

    public List<CatalogoDTO> getCatalogoProcedimiento() {
        return catalogoProcedimiento;
    }

    public void setCatalogoProcedimiento(List<CatalogoDTO> catalogoProcedimiento) {
        this.catalogoProcedimiento = catalogoProcedimiento;
    }

}
