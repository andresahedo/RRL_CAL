/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BandejaConsultasBussines;
import mx.gob.sat.siat.juridica.ca.web.controller.bean.bussiness.BandejaConsultasAutorizacionesBussines;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.convert.FacesConverter;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "tipoTramitesConverter")
@SessionScoped
@FacesConverter("tipoTramitesConverter")
public class CatalogoTipoTramitesConverter extends CatalogoConverter {

    @ManagedProperty(value = "#{bandejaConsultasAutorizacionesBussines}")
    private transient BandejaConsultasAutorizacionesBussines bandejaConsultasAutorizacionesBussines;

    @ManagedProperty("#{bandejaConsultasBussines}")
    private BandejaConsultasBussines bandejaConsultasBussines;

    private List<CatalogoDTO> catalogoTipoTramitesAutorizaciones = new ArrayList<CatalogoDTO>();
    private List<CatalogoDTO> catalogoTipoTramitesConsultas = new ArrayList<CatalogoDTO>();

    @Override
    public List<CatalogoDTO> getListaCatalogos() {
        if (this.catalogoTipoTramitesConsultas.isEmpty()) {
            this.catalogoTipoTramitesConsultas = bandejaConsultasBussines.obtenerTiposTramites();
        }
        return this.catalogoTipoTramitesConsultas;

    }

    @PostConstruct
    public void initFiltro() {
        this.catalogoTipoTramitesAutorizaciones = bandejaConsultasAutorizacionesBussines.obtenerCatalogoTipoTramites();
        this.catalogoTipoTramitesConsultas = bandejaConsultasBussines.obtenerTiposTramites();

    }

    /**
     * 
     * @return bandejaConsultarAutorizacionesBussines
     */
    public BandejaConsultasAutorizacionesBussines getBandejaConsultasAutorizacionesBussines() {
        return bandejaConsultasAutorizacionesBussines;
    }

    /**
     * 
     * @param bandejaConsultasAutorizacionesBussines
     *            el bandejaConsultasAutorizacionesBussines a fijar.
     */
    public void setBandejaConsultasAutorizacionesBussines(
            BandejaConsultasAutorizacionesBussines bandejaConsultasAutorizacionesBussines) {
        this.bandejaConsultasAutorizacionesBussines = bandejaConsultasAutorizacionesBussines;
    }

    /**
     * 
     * @return bandejaConsultasBussines
     */
    public BandejaConsultasBussines getBandejaConsultasBussines() {
        return bandejaConsultasBussines;
    }

    /**
     * 
     * @param bandejaConsultasBussines
     */
    public void setBandejaConsultasBussines(BandejaConsultasBussines bandejaConsultasBussines) {
        this.bandejaConsultasBussines = bandejaConsultasBussines;
    }

    public List<CatalogoDTO> getCatalogoTipoTramitesAutorizaciones() {
        return catalogoTipoTramitesAutorizaciones;
    }

    public void setCatalogoTipoTramitesAutorizaciones(List<CatalogoDTO> catalogoTipoTramitesAutorizaciones) {
        this.catalogoTipoTramitesAutorizaciones = catalogoTipoTramitesAutorizaciones;
    }

    public List<CatalogoDTO> getCatalogoTipoTramitesConsultas() {
        return catalogoTipoTramitesConsultas;
    }

    public void setCatalogoTipoTramitesConsultas(List<CatalogoDTO> catalogoTipoTramitesConsultas) {
        this.catalogoTipoTramitesConsultas = catalogoTipoTramitesConsultas;
    }

}
