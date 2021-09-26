/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.UsuarioRolUnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.util.exception.TreasPendientesException;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business.AdministrarUsuarioUABusiness;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.model.ArbolTramites;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.model.TramitesAsignarByServicioDataModel;
import org.primefaces.model.TreeNode;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 
 * @author softtek
 * 
 */
@ManagedBean(name = "administrarUsuarioUAController")
@ViewScoped
public class AdministrarUsuarioUAController extends AdministracionUsuariosBaseController {

    /**
     * 
     */
    private static final long serialVersionUID = 8465203759755368242L;

    /**
     * Propiedad para manejar los mensajes en pantalla.
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;

    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    /**
     * Propiedad para manejar los mensajes de error en pantalla.
     */

    @ManagedProperty("#{administrarUsuarioUABusiness}")
    private transient AdministrarUsuarioUABusiness administrarUsuarioUABusiness;

    private List<InformacionUsuarioDTO> comboPermisos;

    private InformacionUsuarioDTO permisoSelected;

    private TramitesAsignarByServicioDataModel tramitesDataModel;

    private List<ServicioDTO> servicios;

    private List<TipoTramiteDTO> modalidadesSelected;

    private TreeNode tramitesTree;

    private PersonaInternaDTO usuarioDTO;

    private List<TipoTramiteDTO> modCAL;

    private List<InfRoleUnidadAdminDTO> modalidadesAsignadas;

    private List<UsuarioRolUnidadAdministrativaDTO> comboPermisosUA;

    private boolean blnAbogado;

    private UnidadAdministrativaDTO unidad;

    private String msgConfirmacion;

    private String idTreeDinamico;

    private boolean visibleGrid;

    /**
     * Propiedades de pantallas anteriores.
     */
    private boolean disabled;
    private int botonCancelar;
    private List<InformacionUsuarioDTO> rolesUsuario;

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void inicio() {
        super.init();
        usuarioDTO = new PersonaInternaDTO();
        usuarioDTO = (PersonaInternaDTO) getFlash().get("empleado");
        if (usuarioDTO == null) {
            usuarioDTO = (PersonaInternaDTO) getFlash().get("datosEmpleado");
        }
        usuarioDTO.setNombre(this.obtenerNombreCompleto(usuarioDTO.getNombre(), usuarioDTO.getApellidoPaterno(),
                usuarioDTO.getApellidoMaterno()));

        List<InformacionUsuarioDTO> permisosUsuario = administrarUsuarioUABusiness.comboPermisos(usuarioDTO.getRfc());
        setComboPermisosUA(administrarUsuarioUABusiness.obtenerPermisosDeUsuarioPorUnidad(getAdminUsuariosDTO()
                .getIdUnidadAmin(), getUsuarioDTO().getRfc()));
        comboPermisos = new ArrayList<InformacionUsuarioDTO>();

        for (UsuarioRolUnidadAdministrativaDTO permisoUA : comboPermisosUA) {
            for (InformacionUsuarioDTO infoUsuarioDTO : permisosUsuario) {
                if (permisoUA.getRol().equals(infoUsuarioDTO.getIdRol())
                        && permisoUA.getIdUsuario().equals(infoUsuarioDTO.getIdUsuario())) {
                    if (!(infoUsuarioDTO.getIdRol().equals("AdministradorUA") || infoUsuarioDTO.getIdRol().equals(
                            "AdministradorGlobal"))) {
                        comboPermisos.add(infoUsuarioDTO);
                    }
                }
            }
        }

        setDisabled((Boolean) getFlash().get("disabled"));
        setBotonCancelar((Integer) getFlash().get("accionBotonCancelar"));
        setRolesUsuario((List<InformacionUsuarioDTO>) getFlash().get("rolesUsuario"));
        meterId();
    }

    private void meterId() {
        Date hora = new Date();
        Long id = hora.getTime();
        StringBuffer idCompleto = new StringBuffer();
        idCompleto.append("idTree");
        idCompleto.append(id);

        setIdTreeDinamico(idCompleto.toString());

    }

    public void checkAll(ValueChangeEvent event) {

        boolean val = Boolean.valueOf(event.getNewValue().toString());
        Integer idTipoTramite = ((Integer) event.getComponent().getAttributes().get("idTipoTramite"));
        String idServicio = (String) event.getComponent().getAttributes().get("idServicio");

        if (idTipoTramite == 0) {
            List<TreeNode> listaServicios = gettramitesTree().getChildren();
            for (TreeNode servicio : listaServicios) {
                ArbolTramites serv = (ArbolTramites) servicio.getData();
                if (serv.getIdServicio().equals(idServicio)) {

                    for (TreeNode tramite : servicio.getChildren()) {
                        ArbolTramites tram = (ArbolTramites) tramite.getData();
                        tram.setTramite(val);

                    }

                }
            }
        }
        else {

            List<TreeNode> listaServicios = gettramitesTree().getChildren();
            for (TreeNode servicio : listaServicios) {
                ArbolTramites serv = (ArbolTramites) servicio.getData();
                if (serv.getIdServicio().equals(idServicio)) {
                    serv.setTramite(true);

                    for (TreeNode tramite : servicio.getChildren()) {
                        ArbolTramites tr = (ArbolTramites) tramite.getData();

                        if (tr.getIdTipoTramite().intValue() != idTipoTramite.intValue() && !tr.getTramite()) {
                            serv.setTramite(false);
                        }
                        else if (!val) {
                            serv.setTramite(false);
                        }
                    }
                }

            }
        }

    }

    public void checkAllResponsable(ValueChangeEvent event) {

        boolean val = Boolean.valueOf(event.getNewValue().toString());
        Integer idTipoTramite = ((Integer) event.getComponent().getAttributes().get("idTipoTramite"));
        String idServicio = (String) event.getComponent().getAttributes().get("idServicio");

        if (idTipoTramite == 0) {
            List<TreeNode> listaServicios = gettramitesTree().getChildren();
            for (TreeNode servicio : listaServicios) {
                ArbolTramites serv = (ArbolTramites) servicio.getData();
                if (serv.getIdServicio().equals(idServicio)) {

                    for (TreeNode tramite : servicio.getChildren()) {
                        ArbolTramites tram = (ArbolTramites) tramite.getData();
                        tram.setResponsable(val);

                    }

                }
            }
        }
        else {

            List<TreeNode> listaServicios = gettramitesTree().getChildren();
            for (TreeNode servicio : listaServicios) {
                ArbolTramites serv = (ArbolTramites) servicio.getData();
                if (serv.getIdServicio().equals(idServicio)) {
                    serv.setResponsable(true);

                    for (TreeNode tramite : servicio.getChildren()) {
                        ArbolTramites tr = (ArbolTramites) tramite.getData();

                        if (tr.getIdTipoTramite().intValue() != idTipoTramite.intValue() && !tr.getResponsable()) {

                            serv.setResponsable(false);
                        }
                        else if (!val) {
                            serv.setResponsable(false);
                        }
                    }
                }

            }
        }

    }

    public void mostrarTramitesAsignados(String rol) {
        List<InfRoleUnidadAdminDTO> listaModalidadesAsignadas =
                administrarUsuarioUABusiness.obtenerPermisosPorFuncionario(usuarioDTO.getRfc(), getAdminUsuariosDTO()
                        .getIdUnidadAmin(), rol);
        settramitesTree(null);
        settramitesTree(administrarUsuarioUABusiness.createTree(getAdminUsuariosDTO().getIdUnidadAmin()));
        List<TreeNode> listaServicios = gettramitesTree().getChildren();
        if (listaModalidadesAsignadas != null && !listaModalidadesAsignadas.isEmpty()) {

            for (InfRoleUnidadAdminDTO modAsignada : listaModalidadesAsignadas) {
                for (TreeNode servicio : listaServicios) {

                    for (TreeNode tramite : servicio.getChildren()) {
                        ArbolTramites tr = (ArbolTramites) tramite.getData();

                        if (modAsignada.getIdTipoTramite().equals(tr.getIdTipoTramite())) {
                            tr.setTramite(modAsignada.getBlnActivo() == 1);
                            tr.setResponsable(modAsignada.getResponsable());

                        }

                    }
                }
            }
        }

    }

    public void guardarTramitesAsignados(ActionEvent event) {
        unidad = new UnidadAdministrativaDTO();
        unidad.setClave(getAdminUsuariosDTO().getIdUnidadAmin());
        unidad.setNombre(getAdminUsuariosDTO().getUnidadAdministrativa());
        List<TipoTramiteDTO> modalidades = new ArrayList<TipoTramiteDTO>();
        List<TreeNode> listaServicios = gettramitesTree().getChildren();

        for (TreeNode servicio : listaServicios) {
            for (TreeNode tramite : servicio.getChildren()) {
                ArbolTramites tr = (ArbolTramites) tramite.getData();
                TipoTramiteDTO mod = new TipoTramiteDTO();
                mod.setBlnResponsable(tr.getResponsable());
                mod.setIdTipoTramite(tr.getIdTipoTramite());
                mod.setAsignado(tr.getTramite());
                mod.setBlnAsignado(tr.getTramite());

                modalidades.add(mod);
            }

        }

        try {
            List<String> listaTramitesResponsableDuplicado =
                    administrarUsuarioUABusiness.guardarModalidadesAsignadas(modalidades, getUnidad(),
                            getPermisoSelected());

            if (listaTramitesResponsableDuplicado != null) {
                for (String tramite : listaTramitesResponsableDuplicado) {
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "", MessageFormat.format(
                                    errorMsg.getString("usuarios.validacion.responsableRepetido"), tramite)));
                }
            }
            else {
                String[] param = new String[2];
                param[0] = getPermisoSelected().getDescripcionRol();
                param[1] = getUsuarioDTO().getNombre();

                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "", MessageFormat.format(
                                messages.getString("usuarios.asignar.guardado"), (Object[]) param)));
            }
        }
        catch (TreasPendientesException e) {
            getLogger().error(e.getMessage());
        }

    }

    public void ocultarResponsable(AjaxBehaviorEvent event) {
        UISelectOne valor = (UISelectOne) event.getComponent().getParent().findComponent("comboPermisos");
        String rol = valor.getSubmittedValue().toString();
        if (rol != null && !rol.equals("")) {
            this.mostrarTramitesAsignados(rol);
            setVisibleGrid(true);

            setBlnAbogado(!rol.equals("abogado"));

        }
    }

    public String obtenerNombreCompleto(String nom, String apPaterno, String apMaterno) {
        StringBuffer nombre = new StringBuffer();
        nombre.append(nom);
        nombre.append(" ");
        nombre.append(apPaterno);
        nombre.append(" ");
        nombre.append(apMaterno);
        return nombre.toString();
    }

    public void back() {
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        getFlash().put("empleado", getUsuarioDTO());
        getFlash().put("datosEmpleado", getUsuarioDTO());
        getFlash().put("disabled", isDisabled());
        getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
        getFlash().put("accionBotonCancelar", getBotonCancelar());
        getFlash().put("rolesUsuario", getRolesUsuario());
        super.back();
    }

    public void activarConfirm() {
        String[] param = new String[NumerosConstantes.TRES];
        param[0] = getUsuarioDTO().getNombre();
        param[1] = getUsuarioDTO().getRfc();
        param[2] = getPermisoSelected().getDescripcionRol();
        setMsgConfirmacion(MessageFormat.format(messages.getString("usuarios.administradorUA.asignar"),
                (Object[]) param));
    }

    /**
     * @return the comboPermisos
     */
    public List<InformacionUsuarioDTO> getComboPermisos() {
        return comboPermisos;
    }

    /**
     * @param comboPermisos
     *            the comboPermisos to set
     */
    public void setComboPermisos(List<InformacionUsuarioDTO> comboPermisos) {
        this.comboPermisos = comboPermisos;
    }

    /**
     * @return the permisoSelected
     */
    public InformacionUsuarioDTO getPermisoSelected() {
        return permisoSelected;
    }

    /**
     * @param permisoSelected
     *            the permisoSelected to set
     */
    public void setPermisoSelected(InformacionUsuarioDTO permisoSelected) {
        this.permisoSelected = permisoSelected;
    }

    /**
     * @return the modalidadesSelected
     */
    public List<TipoTramiteDTO> getModalidadesSelected() {
        return modalidadesSelected;
    }

    /**
     * @param modalidadesSelected
     *            the modalidadesSelected to set
     */
    public void setModalidadesSelected(List<TipoTramiteDTO> modalidadesSelected) {
        this.modalidadesSelected = modalidadesSelected;
    }

    /**
     * @return the tramitesDataModel
     */
    public TramitesAsignarByServicioDataModel getTramitesDataModel() {
        return tramitesDataModel;
    }

    /**
     * @param tramitesDataModel
     *            the tramitesDataModel to set
     */
    public void setTramitesDataModel(TramitesAsignarByServicioDataModel tramitesDataModel) {
        this.tramitesDataModel = tramitesDataModel;
    }

    /**
     * @return the usuarioDTO
     */
    public PersonaInternaDTO getUsuarioDTO() {
        return usuarioDTO;
    }

    /**
     * @param usuarioDTO
     *            the usuarioDTO to set
     */
    public void setUsuarioDTO(PersonaInternaDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
    }

    /**
     * @return the messages
     */
    public ResourceBundle getMessages() {
        return messages;
    }

    /**
     * @param messages
     *            the messages to set
     */
    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
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

    /**
     * @return the modCAL
     */
    public List<TipoTramiteDTO> getModCAL() {
        return modCAL;
    }

    /**
     * @param modCAL
     *            the modCAL to set
     */
    public void setModCAL(List<TipoTramiteDTO> modCAL) {
        this.modCAL = modCAL;
    }

    /**
     * @return the modalidadesAsignadas
     */
    public List<InfRoleUnidadAdminDTO> getModalidadesAsignadas() {
        return modalidadesAsignadas;
    }

    /**
     * @param modalidadesAsignadas
     *            the modalidadesAsignadas to set
     */
    public void setModalidadesAsignadas(List<InfRoleUnidadAdminDTO> modalidadesAsignadas) {
        this.modalidadesAsignadas = modalidadesAsignadas;
    }

    /**
     * @return the blnAbogado
     */
    public boolean isBlnAbogado() {
        return blnAbogado;
    }

    /**
     * @param blnAbogado
     *            the blnAbogado to set
     */
    public void setBlnAbogado(boolean blnAbogado) {
        this.blnAbogado = blnAbogado;
    }

    /**
     * @return the unidad
     */
    public UnidadAdministrativaDTO getUnidad() {
        return unidad;
    }

    /**
     * @param unidad
     *            the unidad to set
     */
    public void setUnidad(UnidadAdministrativaDTO unidad) {
        this.unidad = unidad;
    }

    /**
     * @return the msgConfirmacion
     */
    public String getMsgConfirmacion() {
        return msgConfirmacion;
    }

    /**
     * @param msgConfirmacion
     *            the msgConfirmacion to set
     */
    public void setMsgConfirmacion(String msgConfirmacion) {
        this.msgConfirmacion = msgConfirmacion;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * @return the servicios
     */
    public List<ServicioDTO> getServicios() {
        return servicios;
    }

    /**
     * @param servicios
     *            the servicios to set
     */
    public void setServicios(List<ServicioDTO> servicios) {
        this.servicios = servicios;
    }

    /**
     * @return the errorMsg
     */
    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg
     *            the errorMsg to set
     */
    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * @return the visibleGrid
     */
    public boolean isVisibleGrid() {
        return visibleGrid;
    }

    /**
     * @param visibleGrid
     *            the visibleGrid to set
     */
    public void setVisibleGrid(boolean visibleGrid) {
        this.visibleGrid = visibleGrid;
    }

    public List<UsuarioRolUnidadAdministrativaDTO> getComboPermisosUA() {
        return comboPermisosUA;
    }

    public void setComboPermisosUA(List<UsuarioRolUnidadAdministrativaDTO> comboPermisosUA) {
        this.comboPermisosUA = comboPermisosUA;
    }

    public int getBotonCancelar() {
        return botonCancelar;
    }

    public void setBotonCancelar(int botonCancelar) {
        this.botonCancelar = botonCancelar;
    }

    public List<InformacionUsuarioDTO> getRolesUsuario() {
        return rolesUsuario;
    }

    public void setRolesUsuario(List<InformacionUsuarioDTO> rolesUsuario) {
        this.rolesUsuario = rolesUsuario;
    }

    /**
     * @return the tramitesTree
     */
    public TreeNode gettramitesTree() {
        return tramitesTree;
    }

    /**
     * @param tramitesTree
     *            the tramitesTree to set
     */
    public void settramitesTree(TreeNode tramitesTree) {
        this.tramitesTree = tramitesTree;
    }

    /**
     * @return the idTreeDinamico
     */
    public String getIdTreeDinamico() {
        return idTreeDinamico;
    }

    /**
     * @param idTreeDinamico
     *            the idTreeDinamico to set
     */
    public void setIdTreeDinamico(String idTreeDinamico) {
        this.idTreeDinamico = idTreeDinamico;
    }

}
