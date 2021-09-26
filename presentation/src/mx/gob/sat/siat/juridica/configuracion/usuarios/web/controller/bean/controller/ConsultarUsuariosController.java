/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.InfRoleUnidadAdminDTO;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.FiltroUsuariosDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PermisosAUDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business.ConsultarUsuariosBusiness;
import org.primefaces.context.RequestContext;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Clase Controller para la consulta de permisos de usuarios
 * 
 * @author Softtek - EQG
 * @since 30/09/2014
 */
@ViewScoped
@ManagedBean(name = "consultarUsuariosController")
public class ConsultarUsuariosController extends AdministracionUsuariosBaseController {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 5462141189847179872L;

    private FiltroUsuariosDTO filtroUsuarios;

    private List<InfRoleUnidadAdminDTO> listaUsuarios;

    private List<TipoTramiteDTO> listatipoTramite;

    private List<PermisosAUDTO> listaPermisos;

    private int tamanioPanel;

    /**
     * Mensaje para la bandeja vacia tipo String
     */
    private String mensajeBandejaVacia;

    /**
     * Lista tipo UnidadAdministrativa
     */
    private List<CatalogoDTO> listaUnidadesAdministrativas;

    private List<UnidadAdministrativa> listaUnidadesAUA;

    private boolean activaBtn;
    /**
     * Propiedad que representan un mensaje.
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;

    @ManagedProperty(value = "#{consultarUsuariosBusiness}")
    private ConsultarUsuariosBusiness consultarUsuariosBusiness;

    @Override
    public void init() {
        super.init();
        iniciar();
    }

    /**
     * Metodo para inciiar.
     * 
     * @param actionEvent
     */
    @SuppressWarnings("unchecked")
    public void iniciar() {
        inicializaDatosComunes();
        listaUsuarios = new ArrayList<InfRoleUnidadAdminDTO>();
        listatipoTramite = new ArrayList<TipoTramiteDTO>();
        listaPermisos = new ArrayList<PermisosAUDTO>();
        listaUnidadesAUA = new ArrayList<UnidadAdministrativa>();
        setListaUnidadesAdministrativas(getConsultarUsuariosBusiness().obtenerUnidadesAdministrativas());
        setListatipoTramite(getConsultarUsuariosBusiness().obtenerTiposDeTramites());
        setListaPermisos(getConsultarUsuariosBusiness().obtenerPermisos());
        filtroUsuarios.setIdUnidadAdmin(getAdminUsuariosDTO().getIdUnidadAmin());
        setUrlsRegreso((LinkedList<String>) getFlash().get(VistaConstantes.BACK));
    }

    /**
     * @param datosEmpleado
     *            the datosEmpleado to set
     */
    public void back() {
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        super.back();
    }

    /**
     * Metodo para limpiar el filtro de busqueda.
     * 
     * @param actionEvent
     */
    public void limpiarBandeja(ActionEvent actionEvent) {
        inicializaDatosComunes();
        listaUsuarios.clear();
        setMensajeBandejaVacia("");
        RequestContext.getCurrentInstance().reset("form");

    }

    public void inicializaDatosComunes() {
        filtroUsuarios = new FiltroUsuariosDTO();
        if (!getAdminUsuariosDTO().isBlnIsAdminGlobal()) {
            getFiltroUsuarios().setIdUnidadAdmin(getAdminUsuariosDTO().getIdUnidadAmin());
        }
        setActivaBtn(false);
        setTamanioPanel(NumerosConstantes.CUARENTA_SEIS);
    }

    /**
     * Metodo para la consulta de datos de la bitacora
     */
    public void consultarPermisos() {
        setListaUsuarios(getConsultarUsuariosBusiness().consultarPermisosUsuarios(getFiltroUsuarios()));
        if (getListaUsuarios() == null || getListaUsuarios().isEmpty()) {
            setMensajeBandejaVacia(messages.getString("vuj.grid.busquedaVacia"));
            setActivaBtn(false);
        }
        else {
            for (InfRoleUnidadAdminDTO lista : listaUsuarios) {
                if (lista.getResponsable()) {
                    lista.setResponsableMsg(messages.getString("administracion.usuarios.consulta.responsableTrue"));
                }
            }
            if (getListaUsuarios().size() <= NumerosConstantes.CINCO) {
                setTamanioPanel(NumerosConstantes.CIENTO_CINCUENTA);
            }
            else if (getListaUsuarios().size() > NumerosConstantes.CINCO) {
                setTamanioPanel(NumerosConstantes.TRESCIENTOS);
            }
            setActivaBtn(true);
        }
    }

    public FiltroUsuariosDTO getFiltroUsuarios() {
        return filtroUsuarios;
    }

    public void setFiltroUsuarios(FiltroUsuariosDTO filtroUsuarios) {
        this.filtroUsuarios = filtroUsuarios;
    }

    public String getMensajeBandejaVacia() {
        return mensajeBandejaVacia;
    }

    public void setMensajeBandejaVacia(String mensajeBandejaVacia) {
        this.mensajeBandejaVacia = mensajeBandejaVacia;
    }

    public List<CatalogoDTO> getListaUnidadesAdministrativas() {
        return listaUnidadesAdministrativas;
    }

    public void setListaUnidadesAdministrativas(List<CatalogoDTO> listaUnidadesAdministrativas) {
        this.listaUnidadesAdministrativas = listaUnidadesAdministrativas;
    }

    public ConsultarUsuariosBusiness getConsultarUsuariosBusiness() {
        return consultarUsuariosBusiness;
    }

    public void setConsultarUsuariosBusiness(ConsultarUsuariosBusiness consultarUsuariosBusiness) {
        this.consultarUsuariosBusiness = consultarUsuariosBusiness;
    }

    public List<TipoTramiteDTO> getListatipoTramite() {
        return listatipoTramite;
    }

    public void setListatipoTramite(List<TipoTramiteDTO> listatipoTramite) {
        this.listatipoTramite = listatipoTramite;
    }

    public ResourceBundle getMessages() {
        return messages;
    }

    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    public List<PermisosAUDTO> getListaPermisos() {
        return listaPermisos;
    }

    public void setListaPermisos(List<PermisosAUDTO> listaPermisos) {
        this.listaPermisos = listaPermisos;
    }

    public List<InfRoleUnidadAdminDTO> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<InfRoleUnidadAdminDTO> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public boolean isActivaBtn() {
        return activaBtn;
    }

    public void setActivaBtn(boolean activaBtn) {
        this.activaBtn = activaBtn;
    }

    public List<UnidadAdministrativa> getListaUnidadesAUA() {
        return listaUnidadesAUA;
    }

    public void setListaUnidadesAUA(List<UnidadAdministrativa> listaUnidadesAUA) {
        this.listaUnidadesAUA = listaUnidadesAUA;
    }

    /**
     * @return the tamanioPanel
     */
    public int getTamanioPanel() {
        return tamanioPanel;
    }

    /**
     * @param tamanioPanel
     *            the tamanioPanel to set
     */
    public void setTamanioPanel(int tamanioPanel) {
        this.tamanioPanel = tamanioPanel;
    }

}
