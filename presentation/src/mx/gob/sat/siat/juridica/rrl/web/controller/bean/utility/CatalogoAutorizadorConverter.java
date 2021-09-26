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

@ManagedBean(name = "autorizadorConverter")
@SessionScoped
@FacesConverter("autorizadorConverter")
public class CatalogoAutorizadorConverter extends CatalogoConverter {

    private String numAsunto;

    @ManagedProperty("#{emitirResolucionBusiness}")
    private EmitirResolucionBusiness emitirResolucionBusiness;

    private List<CatalogoDTO> autorizadores = new ArrayList<CatalogoDTO>();

    @PostConstruct
    public void initFiltro() {
        if (numAsunto != null) {
            this.autorizadores = emitirResolucionBusiness.obtenerAutorizadores(this.numAsunto);
        }
    }

    @Override
    public List<CatalogoDTO> getListaCatalogos() {
        if (numAsunto != null && autorizadores.isEmpty()) {
            this.autorizadores = emitirResolucionBusiness.obtenerAutorizadores(this.numAsunto);
        }
        return this.autorizadores;
    }

    public void setNumAsunto(String numAsunto) {
        this.numAsunto = numAsunto;
    }

    public String getNumAsunto() {
        return this.numAsunto;
    }

    public EmitirResolucionBusiness getEmitirResolucionBusiness() {
        return emitirResolucionBusiness;
    }

    /**
     * @param emitirResolucionBusiness
     *            el(la) emitirResolucionBusiness a fijar
     */
    public void setEmitirResolucionBusiness(EmitirResolucionBusiness emitirResolucionBusiness) {
        this.emitirResolucionBusiness = emitirResolucionBusiness;
    }

    public List<CatalogoDTO> getAutorizadores() {
        return autorizadores;
    }

    public void setAutorizadores(List<CatalogoDTO> autorizadores) {
        this.autorizadores = autorizadores;
    }

}
