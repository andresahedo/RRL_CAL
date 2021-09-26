package mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility;

import mx.gob.sat.siat.juridica.base.dto.AgraviosDTO;
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

@ManagedBean(name = "agravioConverter")
@SessionScoped
@FacesConverter("agravioConverter")
public class AgravioConverter implements Converter {
    @ManagedProperty("#{emitirResolucionBusiness}")
    private EmitirResolucionBusiness emitirResolucionBusiness;

    private List<AgraviosDTO> agravios = new ArrayList<AgraviosDTO>();

    @PostConstruct
    public void initFiltro() {
        this.agravios = emitirResolucionBusiness.obtenerAgravios();
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String nombre) {
        AgraviosDTO sentido = obtenerSentidoPorClave(nombre);
        return sentido != null ? sentido : "";
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value != null && !value.equals("")) {
            return ((AgraviosDTO) value).getDescripcion();
        }
        return "";
    }

    private AgraviosDTO obtenerSentidoPorClave(String descripcion) {
        for (AgraviosDTO agravio : this.agravios) {
            if (descripcion.equals(agravio.getDescripcion())) {
                return agravio;
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

    public List<AgraviosDTO> getAgravios() {
        return agravios;
    }

    public void setAgravios(List<AgraviosDTO> agravios) {
        this.agravios = agravios;
    }

}
