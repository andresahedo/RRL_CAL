/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.AdministrarUsuarioUAFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.UsuarioRolUnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.util.exception.TreasPendientesException;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.model.ArbolTramites;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@SessionScoped
@ManagedBean(name = "administrarUsuarioUABusiness")
public class AdministrarUsuarioUABusiness extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = -7248917917706779036L;

    @ManagedProperty("#{administrarUsuarioUAFacade}")
    private AdministrarUsuarioUAFacade administrarUsuarioUAFacade;

    public List<InformacionUsuarioDTO> comboPermisos(String idUsuario) {
        return administrarUsuarioUAFacade.obtenerTodosRolesFuncionario(idUsuario);
    }

    public List<TipoTramiteDTO> obtenerModalidadesAsignar() {
        return administrarUsuarioUAFacade.obtenerModalidadesAsignar();
    }

    public List<InfRoleUnidadAdminDTO> obtenerPermisosPorFuncionario(String rfc, String unidadAdmin, String rol) {
        return administrarUsuarioUAFacade.obtenerPermisosPorFuncionario(rfc, unidadAdmin, rol);
    }

    public List<String> guardarModalidadesAsignadas(List<TipoTramiteDTO> listaSelected, UnidadAdministrativaDTO unidad,
            InformacionUsuarioDTO funcionario) throws TreasPendientesException {
        return administrarUsuarioUAFacade.guardarModalidadesAsignadas(listaSelected, unidad, funcionario);
    }

    /**
     * @return the administrarUsuarioUAFacade
     */
    public AdministrarUsuarioUAFacade getAdministrarUsuarioUAFacade() {
        return administrarUsuarioUAFacade;
    }

    /**
     * @param administrarUsuarioUAFacade
     *            the administrarUsuarioUAFacade to set
     */
    public void setAdministrarUsuarioUAFacade(AdministrarUsuarioUAFacade administrarUsuarioUAFacade) {
        this.administrarUsuarioUAFacade = administrarUsuarioUAFacade;
    }

    public List<ServicioDTO> obtenerServiciosAsignar(String idAdmin) {

        return administrarUsuarioUAFacade.obtenerServiciosAsignar(idAdmin);
    }

    public List<UsuarioRolUnidadAdministrativaDTO>
            obtenerPermisosDeUsuarioPorUnidad(String idUniAdmin, String idUsuario) {
        return administrarUsuarioUAFacade.obtenerPermisosDeUsuarioPorUnidad(idUniAdmin, idUsuario);
    }


    public TreeNode createTree(String idUnidadAmin) {
        List<ServicioDTO> servicios = obtenerServiciosAsignar(idUnidadAmin);
        TreeNode tree = new DefaultTreeNode(new ArbolTramites(null, null, null, "", false, false), null);

        for (ServicioDTO servicio : servicios) {
            TreeNode servicioTitulo =
                    new DefaultTreeNode(new ArbolTramites("ui-panel-titlebar ui-widget-header", 0,
                            servicio.getServicio(), servicio.getDescripcionServicio(), null, null), tree);

            for (TipoTramiteDTO tram : servicio.getModalidades()) {
                new DefaultTreeNode(new ArbolTramites(null, tram.getIdTipoTramite(), servicio.getServicio(),
                        tram.getDescripcionModalidad(), tram.getBlnAsignado(), tram.getBlnResponsable()),
                        servicioTitulo);

            }

        }
        return tree;
    }
}
