package mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.emitirresolucion.EmitirResolucionBusiness;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.convert.FacesConverter;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "sentidoConverter")
@SessionScoped
@FacesConverter("sentidoConverter")
public class CatalogoSentidoConverter extends CatalogoConverter {

    @ManagedProperty("#{emitirResolucionBusiness}")
    private EmitirResolucionBusiness emitirResolucionBusiness;

    private List<CatalogoDTO> sentidosResolucion = new ArrayList<CatalogoDTO>();

    @Override
    public List<CatalogoDTO> getListaCatalogos() {
        return this.sentidosResolucion;
    }

    @PostConstruct
    public void initFiltro() {
        this.sentidosResolucion = emitirResolucionBusiness.obtenerSentidosResolucion();
    }

    /**
     * @return the emitirResolucionBusiness
     */
    public EmitirResolucionBusiness getEmitirResolucionBusiness() {
        return emitirResolucionBusiness;
    }

    /**
     * @param emitirResolucionBusiness
     *            the emitirResolucionBusiness to set
     */
    public void setEmitirResolucionBusiness(EmitirResolucionBusiness emitirResolucionBusiness) {
        this.emitirResolucionBusiness = emitirResolucionBusiness;
    }

    public List<CatalogoDTO> getSentidosResolucion() {
        return sentidosResolucion;
    }

    public void setSentidosResolucion(List<CatalogoDTO> sentidosResolucion) {
        this.sentidosResolucion = sentidosResolucion;
    }

}
