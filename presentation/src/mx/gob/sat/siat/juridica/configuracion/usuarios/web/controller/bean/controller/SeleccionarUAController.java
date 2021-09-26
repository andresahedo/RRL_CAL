/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.AdminUsuariosDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.FiltroUsuariosDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business.ConsultarUsuariosBusiness;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.constantes.AdministracionConstantes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase Controller para la consulta de permisos de usuarios
 * 
 * @author Softtek - EQG
 * @since 30/09/2014
 */
@ViewScoped
@ManagedBean(name = "seleccionarUAController")
public class SeleccionarUAController extends AdministracionUsuariosBaseController {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 5462141189847179872L;

    private FiltroUsuariosDTO filtroUsuarios;

    /**
     * Lista tipo UnidadAdministrativa
     */
    private List<CatalogoDTO> listaUnidadesAdministrativas;

    private List<UnidadAdministrativaDTO> listaUnidadesAUA;

    private String idUnidadAdmin;
    private String unidadAdminDescripcion;

    /**
     * Propiedad que representan un mensaje.
     */

    @ManagedProperty(value = "#{consultarUsuariosBusiness}")
    private ConsultarUsuariosBusiness consultarUsuariosBusiness;

    // TODO Verificar unidad anministrativa que se trae desde el menu
    // de seleccion
    // y pasarla como parameto al metodo
    // getConsultarUsuariosBusiness().obtenerUnidadAdministrativa(cveUnidad))

    @Override
    public void init() {
        getFlash().remove(VistaConstantes.DATOS_ADMINUSUARIO_DTO);

        setUrlsRegreso((LinkedList<String>) getFlash().get(VistaConstantes.BACK));
        setInicial(getUrlsRegreso() != null && !getUrlsRegreso().isEmpty());

        setAdminUsuariosDTO(new AdminUsuariosDTO());
        getAdminUsuariosDTO().setRfcUsuario(getUserProfile().getRfc());
        getAdminUsuariosDTO().setNombreEmpleado(
                getEncabezadoBusiness().obtenerNombreAdministrador(getUserProfile().getRfc()));
        getAdminUsuariosDTO().setPermiso(obtenerPermisoUsuario());
        getAdminUsuariosDTO().setBlnIsAdminGlobal(verificarTipoFuncionario());
        getAdminUsuariosDTO().setBlnGlobalUnidadAdmin(false);
        inicializaDatosComunes();
        listaUnidadesAUA = new ArrayList<UnidadAdministrativaDTO>();

    }

    public void inicializaDatosComunes() {
        listaUnidadesAdministrativas();
    }

    public void listaUnidadesAdministrativas() {
        if (this.getAdminUsuariosDTO().isBlnIsAdminGlobal()) {
            setListaUnidadesAdministrativas(getConsultarUsuariosBusiness().obtenerUnidadesAdministrativas());
        }
        else {
            setListaUnidadesAdministrativas((getConsultarUsuariosBusiness()
                    .obtenerUnidadAdministrativa(getUserProfile().getRfc())));
        }
    }

    public void back() {
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
        super.back();
    }

    /**
     * Metodo par emitir siguiente resolucion.
     * 
     * @param actionEvent
     */
    public void siguiente(ActionEvent actionEvent) {
        if (getAdminUsuariosDTO().getIdPermiso().equals("AdministradorGlobal")) {
            getAdminUsuariosDTO().setBlnGlobalUnidadAdmin(true);
        }
        getAdminUsuariosDTO().setIdUnidadAmin(getIdUnidadAdmin());
        getAdminUsuariosDTO().setUnidadAdministrativa(getUnidadAdminDescripcion());
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        if (getUrlsRegreso() == null) {
            LinkedList<String> urls = new LinkedList<String>();
            urls.add("indexAdmin");
            setUrlsRegreso(urls);

        }
        else {
            getUrlsRegreso().add("seleccionarUA");

        }

        getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
        redireccionarPagina(AdministracionConstantes.MENU_ADMIN);

    }

    /**
     * M&eacute;todo para cambiar autoridades
     */
    public void setNombreUnidad(ValueChangeEvent event) {
        for (CatalogoDTO unidad : getListaUnidadesAdministrativas()) {
            if (unidad.getClave().equals(event.getNewValue())) {
                setUnidadAdminDescripcion(unidad.getDescripcion());
            }
        }

    }

    public List<CatalogoDTO> getListaUnidadesAdministrativas() {
        return listaUnidadesAdministrativas;
    }

    public void setListaUnidadesAdministrativas(List<CatalogoDTO> listaUnidadesAdministrativas) {
        this.listaUnidadesAdministrativas = listaUnidadesAdministrativas;
    }

    public List<UnidadAdministrativaDTO> getListaUnidadesAUA() {
        return listaUnidadesAUA;
    }

    public void setListaUnidadesAUA(List<UnidadAdministrativaDTO> listaUnidadesAUA) {
        this.listaUnidadesAUA = listaUnidadesAUA;
    }

    /**
     * @return the consultarUsuariosBusiness
     */
    public ConsultarUsuariosBusiness getConsultarUsuariosBusiness() {
        return consultarUsuariosBusiness;
    }

    /**
     * @param consultarUsuariosBusiness
     *            the consultarUsuariosBusiness to set
     */
    public void setConsultarUsuariosBusiness(ConsultarUsuariosBusiness consultarUsuariosBusiness) {
        this.consultarUsuariosBusiness = consultarUsuariosBusiness;
    }

    /**
     * @return the filtroUsuarios
     */
    public FiltroUsuariosDTO getFiltroUsuarios() {
        return filtroUsuarios;
    }

    /**
     * @param filtroUsuarios
     *            the filtroUsuarios to set
     */
    public void setFiltroUsuarios(FiltroUsuariosDTO filtroUsuarios) {
        this.filtroUsuarios = filtroUsuarios;
    }

    /**
     * @return the idUnidadAdmin
     */
    public String getIdUnidadAdmin() {
        return idUnidadAdmin;
    }

    /**
     * @param idUnidadAdmin
     *            the idUnidadAdmin to set
     */
    public void setIdUnidadAdmin(String idUnidadAdmin) {
        this.idUnidadAdmin = idUnidadAdmin;
    }

    /**
     * @return the unidadAdminDescripcion
     */
    public String getUnidadAdminDescripcion() {
        return unidadAdminDescripcion;
    }

    /**
     * @param unidadAdminDescripcion
     *            the unidadAdminDescripcion to set
     */
    public void setUnidadAdminDescripcion(String unidadAdminDescripcion) {
        this.unidadAdminDescripcion = unidadAdminDescripcion;
    }

}
