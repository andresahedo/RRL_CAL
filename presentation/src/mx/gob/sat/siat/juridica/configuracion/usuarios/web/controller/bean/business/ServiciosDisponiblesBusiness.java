package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.ServiciosDisponiblesFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PermisosAUDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.ServiciosDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;

@ManagedBean(name = "serviciosDisponiblesBusiness")
@NoneScoped
public class ServiciosDisponiblesBusiness extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = -1185226966583175709L;

    @ManagedProperty("#{serviciosDisponiblesFacade}")
    private ServiciosDisponiblesFacade serviciosDisponiblesFacade;

    public List<ServiciosDTO> obtenerServiciosRol(String rol) {
        return serviciosDisponiblesFacade.obtenerServiciosRol(rol);
    }

    public ServiciosDisponiblesFacade getServiciosDisponiblesFacade() {
        return serviciosDisponiblesFacade;
    }

    public void setServiciosDisponiblesFacade(ServiciosDisponiblesFacade serviciosDisponiblesFacade) {
        this.serviciosDisponiblesFacade = serviciosDisponiblesFacade;
    }

    public List<PermisosAUDTO> obtenerPermisoUsuario() {

        return getServiciosDisponiblesFacade().obtenerPermisos();
    }

}
