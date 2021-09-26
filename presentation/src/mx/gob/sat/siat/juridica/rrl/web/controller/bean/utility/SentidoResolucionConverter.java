package mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.emitirresolucion.EmitirResolucionBusiness;

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

@ManagedBean(name = "sentidoResolucionConverter")
@SessionScoped
@FacesConverter("sentidoResolucionConverter")
public class SentidoResolucionConverter implements Converter {

    @ManagedProperty("#{emitirResolucionBusiness}")
    private EmitirResolucionBusiness emitirResolucionBusiness;

    private List<CatalogoDTO> sentidosResolucion = new ArrayList<CatalogoDTO>();

    @PostConstruct
    public void initFiltro() {
        this.sentidosResolucion = emitirResolucionBusiness.obtenerSentidosResolucion();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent uiComponent, String nombre) {
        CatalogoDTO sentido = obtenerSentidoPorClave(nombre);
        return sentido != null ? sentido : new CatalogoDTO();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent uiComponent, Object value) {
        if (value != null && !value.toString().equals("")) {
            return ((CatalogoDTO) value).getDescripcion();
        }
        return "";
    }

    private CatalogoDTO obtenerSentidoPorClave(String nombre) {
        for (CatalogoDTO sentido : this.sentidosResolucion) {
            if (nombre.equals(sentido.getDescripcion())) {
                return sentido;
            }
        }
        return null;
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
