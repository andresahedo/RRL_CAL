package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.utility;

import mx.gob.sat.siat.juridica.base.dto.InformacionUsuarioDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business.AdministrarUsuarioUABusiness;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "informacionUsuarioConverter")
@ViewScoped
@FacesConverter("informacionUsuarioConverter")
public class InformacionUsuarioConverter implements Converter {

    @ManagedProperty(value = "#{administrarUsuarioUABusiness}")
    private transient AdministrarUsuarioUABusiness administrarUsuarioUABusiness;

    private List<InformacionUsuarioDTO> comboRoles = new ArrayList<InformacionUsuarioDTO>();

    @PostConstruct
    public void initFiltro() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String rfc = (String) session.getAttribute("rfc");
        session.removeAttribute("rfc");
        this.comboRoles = administrarUsuarioUABusiness.comboPermisos(rfc);
    }

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        return obtenerRolPorClave(arg2);

    }

    private InformacionUsuarioDTO obtenerRolPorClave(String arg2) {
        List<InformacionUsuarioDTO> catalogoDTOs = this.comboRoles;
        for (InformacionUsuarioDTO catalogoDTO : catalogoDTOs) {
            if (catalogoDTO.getIdRol().equals(arg2)) {
                return catalogoDTO;
            }
        }
        return new InformacionUsuarioDTO();
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        if (arg2 != null && !arg2.equals("")) {
            InformacionUsuarioDTO catalogoDTO = new InformacionUsuarioDTO();
            catalogoDTO.setIdRol((arg2.toString()));
            return catalogoDTO.getIdRol();
        }

        return "";
    }

    /**
     * @return the comboRoles
     */
    public List<InformacionUsuarioDTO> getComboRoles() {
        return comboRoles;
    }

    /**
     * @param comboRoles
     *            the comboRoles to set
     */
    public void setComboRoles(List<InformacionUsuarioDTO> comboRoles) {
        this.comboRoles = comboRoles;
    }

    /**
     * @return the administrarUsuarioUABusiness
     */
    public AdministrarUsuarioUABusiness getAdministrarUsuarioUABusiness() {
        return administrarUsuarioUABusiness;
    }

    /**
     * @param administrarUsuarioUABusiness
     *            the administrarUsuarioUABusiness to set
     */
    public void setAdministrarUsuarioUABusiness(AdministrarUsuarioUABusiness administrarUsuarioUABusiness) {
        this.administrarUsuarioUABusiness = administrarUsuarioUABusiness;
    }

}
