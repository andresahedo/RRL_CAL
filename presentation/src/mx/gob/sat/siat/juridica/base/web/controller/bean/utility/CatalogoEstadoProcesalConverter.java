package mx.gob.sat.siat.juridica.base.web.controller.bean.utility;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BandejaConsultasBussines;
import mx.gob.sat.siat.juridica.ca.web.controller.bean.bussiness.BandejaConsultasAutorizacionesBussines;
import mx.gob.sat.siat.juridica.ca.web.controller.bean.bussiness.DetallePromocionBusiness;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.BandejaBussines;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility.CatalogoConverter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.convert.FacesConverter;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Converter que nos sirve para hacer la conversion del catalogo
 * estado procesal.
 * 
 * @author softtek
 * 
 */
@ManagedBean(name = "estadoProcesalConverter")
@SessionScoped
@FacesConverter("estadoProcesalConverter")
public class CatalogoEstadoProcesalConverter extends CatalogoConverter {

    @ManagedProperty(value = "#{bandejaBussiness}")
    private BandejaBussines bandejaBussines;

    @ManagedProperty("#{detallePromocionBusiness}")
    private transient DetallePromocionBusiness detallePromocionBusiness;

    @ManagedProperty(value = "#{bandejaConsultasAutorizacionesBussines}")
    private transient BandejaConsultasAutorizacionesBussines bandejaConsultasAutorizacionesBussines;

    @ManagedProperty("#{bandejaConsultasBussines}")
    private BandejaConsultasBussines bandejaConsultasBussines;

    private List<CatalogoDTO> catalogoEstados = new ArrayList<CatalogoDTO>();
    private List<CatalogoDTO> catalogoEstadosDetallePromocion = new ArrayList<CatalogoDTO>();
    private List<CatalogoDTO> catalogoEstadosAutorizaciones = new ArrayList<CatalogoDTO>();
    private List<CatalogoDTO> catalogoEstadosConsultas = new ArrayList<CatalogoDTO>();

    /**
     * Metodo abstracto que se implementa para obtener en una lista
     * los objetos del catalogo de estados.
     */
    @Override
    public List<CatalogoDTO> getListaCatalogos() {

        if (!this.catalogoEstados.isEmpty()) {
            return this.catalogoEstados;
        }
        else if (!this.catalogoEstadosAutorizaciones.isEmpty()) {
            return this.catalogoEstadosAutorizaciones;
        }
        else if (!this.catalogoEstadosDetallePromocion.isEmpty()) {
            return this.catalogoEstadosDetallePromocion;
        }
        else if (!this.catalogoEstadosConsultas.isEmpty()) {
            return this.catalogoEstadosConsultas;
        }
        return new ArrayList<CatalogoDTO>();
    }

    @PostConstruct
    public void initFiltro() {

        this.catalogoEstados = bandejaBussines.obtenerCatalogoEstados();
        this.catalogoEstadosDetallePromocion = detallePromocionBusiness.obtenerCatalogoEstados();
        this.catalogoEstadosAutorizaciones = bandejaConsultasAutorizacionesBussines.obtenerCatalogoEstados();
        this.catalogoEstadosConsultas = bandejaConsultasBussines.obtenerCatalogoEstados();

    }

    public BandejaBussines getBandejaBussines() {
        return bandejaBussines;
    }

    public void setBandejaBussines(BandejaBussines bandejaBussines) {
        this.bandejaBussines = bandejaBussines;
    }

    /**
     * 
     * @return detallePromocionBusiness
     */
    public DetallePromocionBusiness getDetallePromocionBusiness() {
        return detallePromocionBusiness;
    }

    /**
     * 
     * @param detallePromocionBusiness
     *            el detallePromocionBusiness a fijar.
     */
    public void setDetallePromocionBusiness(DetallePromocionBusiness detallePromocionBusiness) {
        this.detallePromocionBusiness = detallePromocionBusiness;
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
}
