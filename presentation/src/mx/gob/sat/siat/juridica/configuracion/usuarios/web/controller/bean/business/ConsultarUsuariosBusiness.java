/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.InfRoleUnidadAdminDTO;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.ConsultarUsuariosFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.FiltroUsuariosDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PermisosAUDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;

/**
 * Business para la consulta de los usuarios
 * 
 * @author softtek - EQG 08/10/2014
 */
@ManagedBean(name = "consultarUsuariosBusiness")
@NoneScoped
public class ConsultarUsuariosBusiness extends BaseBussinessBean {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 2838095268207095230L;

    @ManagedProperty(value = "#{consultarUsuariosFacade}")
    private ConsultarUsuariosFacade consultarUsuariosFacade;

    public List<CatalogoDTO> obtenerUnidadesAdministrativas() {
        return consultarUsuariosFacade.obtenerUnidadesAdministrativas();
    }

    public List<TipoTramiteDTO> obtenerTiposDeTramites() {
        return consultarUsuariosFacade.obtenerTiposDeTramites();
    }

    public List<PermisosAUDTO> obtenerPermisos() {
        return consultarUsuariosFacade.obtenerPermisos();
    }

    public List<InfRoleUnidadAdminDTO> consultarPermisosUsuarios(FiltroUsuariosDTO filtroUsuarios) {
        return consultarUsuariosFacade.consultarPermisosUsuarios(filtroUsuarios);
    }

    public List<CatalogoDTO> obtenerUnidadAdministrativa(String rfcAdmin) {
        return consultarUsuariosFacade.obtenerUnidadAdministrativasUAByRfc(rfcAdmin);
    }

    public ConsultarUsuariosFacade getConsultarUsuariosFacade() {
        return consultarUsuariosFacade;
    }

    public void setConsultarUsuariosFacade(ConsultarUsuariosFacade consultarUsuariosFacade) {
        this.consultarUsuariosFacade = consultarUsuariosFacade;
    }

}
