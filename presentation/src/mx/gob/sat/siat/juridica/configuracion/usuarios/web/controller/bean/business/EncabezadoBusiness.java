package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.EncabezadoFacade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

@ManagedBean(name = "encabezadoBusiness")
@NoneScoped
public class EncabezadoBusiness extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = -5521946357114254059L;

    @ManagedProperty("#{encabezadoFacade}")
    private EncabezadoFacade encabezadoFacade;

    public String obtenerNombreAdministrador(String rfc) {
        return encabezadoFacade.obtenerNombre(rfc);
    }

    public EncabezadoFacade getEncabezadoFacade() {
        return encabezadoFacade;
    }

    public void setEncabezadoFacade(EncabezadoFacade encabezadoFacade) {
        this.encabezadoFacade = encabezadoFacade;
    }

}
