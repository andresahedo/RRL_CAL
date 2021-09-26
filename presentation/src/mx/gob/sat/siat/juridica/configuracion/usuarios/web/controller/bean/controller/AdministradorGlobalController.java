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
import mx.gob.sat.siat.juridica.base.dto.InformacionUsuarioDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.UsuarioRolUnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business.AdministradorGlobalBusiness;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.model.PermisosAsignadosDataModel;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.util.validator.PermisosValidator;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 
 * @author softtek
 * 
 */
@ManagedBean(name = "administradorGlobalController")
@ViewScoped
public class AdministradorGlobalController extends AdministracionUsuariosBaseController {

    /**
     * 
     */
    private static final long serialVersionUID = -2176520460396923000L;

    private static final String ADMINISTRADOR_DE_UA = "Administrador de Unidad Administrativa";

    /**
     * Propiedad para manejar los mensajes en pantalla.
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    /**
     * Propiedad para manejar los mensajes de error en pantalla.
     */
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    @ManagedProperty("#{administradorGlobalBusiness}")
    private transient AdministradorGlobalBusiness administradorGlobalBusiness;

    @ManagedProperty("#{permisosValidator}")
    private PermisosValidator permisosValidator;

    private PersonaInternaDTO datosEmpleado;

    private List<UsuarioRolUnidadAdministrativaDTO> empleadoRol;

    private String numeroEmpleado;

    private boolean blnPanelConsulta;

    private boolean blnPanelAdminUnidad;

    private PermisosAsignadosDataModel permisosDataModel;

    private List<UnidadAdministrativaDTO> unidades;

    private UnidadAdministrativaDTO unidadAdminSelected;

    private List<UsuarioRolUnidadAdministrativaDTO> unidadesAdminSelected;

    private boolean blnRevocar;

    private boolean checkGlobal;

    private boolean blnCheck;

    private boolean disable;

    private String msg;

    private String msgRevocar;

    private boolean blnGlobal;

    private List<InformacionUsuarioDTO> permisosUsuario;

    private boolean disablePanel;

    private String msgAsignar;

    private boolean usuarioInactivo;

    private boolean bnlActivarDialogo;

    private String msgAsignarGlobal;

    private boolean confirmGlobal;

    @PostConstruct
    public void inicio() {
        super.init();
        blnGlobal = false;
        blnPanelConsulta = false;
        blnPanelAdminUnidad = false;
        blnRevocar = false;
        unidades = obtenerCatalogoUnidades();
        disable = false;
        disablePanel = false;
        bnlActivarDialogo = false;
        confirmGlobal = false;
    }

    public void buscarEmpleado() {
        blnPanelConsulta = false;
        blnGlobal = false;
        blnPanelAdminUnidad = false;
        datosEmpleado = new PersonaInternaDTO();
        datosEmpleado.setNumeroEmpleado(Long.parseLong(getNumeroEmpleado()));
        datosEmpleado = administradorGlobalBusiness.obtenerInformacionEmpleado(datosEmpleado.getNumeroEmpleado());
        if (datosEmpleado != null) {
            blnPanelConsulta = true;
            if (administradorGlobalBusiness.verificarUsuarioActivo(datosEmpleado.getRfc())) {
                setDisable(false);
                setUsuarioInactivo(false);
            }
            else {
                setBlnGlobal(false);
                setDisable(true);
                setUsuarioInactivo(true);
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "", messages
                                .getString("usuarios.mensaje.empleado.inactivo")));
            }
            empleadoRol = administradorGlobalBusiness.obtenerRolesEmpleado(datosEmpleado.getRfc());

            if (empleadoRol != null && !empleadoRol.isEmpty()) {
                blnRevocar = true;
                permisosDataModel = new PermisosAsignadosDataModel(empleadoRol);
            }
            else {
                empleadoRol = new ArrayList<UsuarioRolUnidadAdministrativaDTO>();
                permisosDataModel = new PermisosAsignadosDataModel(empleadoRol);
            }

            setBlnGlobal(validarAdministradorGlobal());
            if (isBlnGlobal()) {
                disablePanel = true;
                disable = false;

            }
            if (isUsuarioInactivo()) {
                setBlnGlobal(false);
            }
            setCheckGlobal(administradorGlobalBusiness.verificarAdministradorGlobalActivo(getDatosEmpleado().getRfc()));
            if (isBlnGlobal() || validarAdministradorUnidad()) {
                setBlnPanelAdminUnidad(true);
            }
            if (isCheckGlobal()) {
                setDisable(true);
            }
            else {
                if (isUsuarioInactivo()) {
                    setDisable(true);
                }
                else {
                    setDisable(false);
                    setDisablePanel(false);
                }
            }

            validaAdmin();
        }
        else {

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", messages
                            .getString("usuarios.mensaje.empleado.noExiste")));
        }

    }

    protected void validaAdmin() {
        if (!blnPanelAdminUnidad && !blnGlobal) {

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                            .getString("usuarios.mensaje.empleado.noRoles")));
        }

    }

    public void activarPanelAdminUA() {
        if (isCheckGlobal()) {
            disable = true;
            disablePanel = true;
        }
        else {
            disable = false;
            disablePanel = false;
        }
    }

    public void validarAsignarGlobal() {
        if (isCheckGlobal()) {
            confirmGlobal = isCheckGlobal();
        }
        else {
            confirmGlobal = false;
            disable = false;
            disablePanel = false;
        }
        String[] param = new String[2];
        param[0] = "Administrador Global";
        param[1] =
                getDatosEmpleado().getNombre() + " " + getDatosEmpleado().getApellidoPaterno() + " "
                        + getDatosEmpleado().getApellidoMaterno();
        msgAsignarGlobal = MessageFormat.format(messages.getString("usuarios.asignar.confirmacion"), (Object[]) param);

    }

    public void unselectGlobal() {
        confirmGlobal = false;
    }

    public void asignar() {
        administradorGlobalBusiness.activarAdministradorGlobal(getDatosEmpleado().getRfc(), isCheckGlobal());
        if (isCheckGlobal()) {
            disablePanel = true;
            disable = true;
            blnRevocar = false;
            String[] param = new String[2];
            param[0] = "Administrador Global";
            param[1] =
                    getDatosEmpleado().getNombre() + " " + getDatosEmpleado().getApellidoPaterno() + " "
                            + getDatosEmpleado().getApellidoMaterno();
            msg = MessageFormat.format(messages.getString("usuarios.asignar.permisos.mensaje"), (Object[]) param);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", msg));
        }
        else {
            disablePanel = true;
            disable = false;
            blnRevocar = true;
        }
        confirmGlobal = false;
    }

    public void asignarUnidad() {
        boolean resp = permisosValidator.asignarPermiso(getEmpleadoRol(), getUnidadAdminSelected());

        if (resp) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", messages
                            .getString("usuarios.mensaje.unidad.repetida")));

        }
        else {
            UsuarioRolUnidadAdministrativaDTO nuevoPermiso = new UsuarioRolUnidadAdministrativaDTO();
            nuevoPermiso.setIdUsuario(getDatosEmpleado().getRfc());
            nuevoPermiso.setRol("AdministradorUA");
            nuevoPermiso.setUnidadAdministrativa(getUnidadAdminSelected());
            nuevoPermiso.setUniadminPrincipal(false);
            nuevoPermiso.setBlnActivo(true);
            getAdministradorGlobalBusiness().asignarPermiso(nuevoPermiso);
            getEmpleadoRol().add(nuevoPermiso);
            setPermisosDataModel(new PermisosAsignadosDataModel(getEmpleadoRol()));
            String[] param = new String[2];
            param[0] = ADMINISTRADOR_DE_UA;
            param[1] =
                    getDatosEmpleado().getNombre() + " " + getDatosEmpleado().getApellidoPaterno() + " "
                            + getDatosEmpleado().getApellidoMaterno();
            msg = MessageFormat.format(messages.getString("usuarios.asignar.permisos.mensaje"), (Object[]) param);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", msg));
        }
        blnRevocar = true;
        bnlActivarDialogo = false;
        administradorGlobalBusiness.activarAdministradorGlobal(getDatosEmpleado().getRfc(), isCheckGlobal());

    }

    public void revocarPermiso() {
        bnlActivarDialogo = false;
        List<UsuarioRolUnidadAdministrativaDTO> revocados = new ArrayList<UsuarioRolUnidadAdministrativaDTO>();
        if (!getUnidadesAdminSelected().isEmpty()) {
            for (UsuarioRolUnidadAdministrativaDTO revocado : getUnidadesAdminSelected()) {
                revocado.setBlnActivo(false);
                revocados.add(revocado);
            }
            administradorGlobalBusiness.revocarPermiso(revocados, getUserProfile().getRfc());
            empleadoRol = administradorGlobalBusiness.obtenerRolesEmpleado(datosEmpleado.getRfc());
            setPermisosDataModel(new PermisosAsignadosDataModel(getEmpleadoRol()));
            String[] param = new String[2];
            param[0] = ADMINISTRADOR_DE_UA;
            param[1] =
                    getDatosEmpleado().getNombre() + " " + getDatosEmpleado().getApellidoPaterno() + " "
                            + getDatosEmpleado().getApellidoMaterno();
            msg = MessageFormat.format(messages.getString("usuarios.revocar.permisos.mensaje"), (Object[]) param);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", msg));
        }
        else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", messages
                            .getString("usuarios.revocar.tramites.unidadAdministrativa")));
        }

    }

    public void activarConfirm() {
        String[] param = new String[NumerosConstantes.TRES];
        param[0] = ADMINISTRADOR_DE_UA;
        param[1] =
                getDatosEmpleado().getNombre() + " " + getDatosEmpleado().getApellidoPaterno() + " "
                        + getDatosEmpleado().getApellidoMaterno();
        param[2] = getDatosEmpleado().getRfc();
        setMsgRevocar(MessageFormat.format(messages.getString("usuarios.revocar.confirmacion"), (Object[]) param));
    }

    public void confirmarAsignar() {
        if (getUnidadAdminSelected() != null) {
            bnlActivarDialogo = true;
            String[] parametros = new String[2];
            parametros[0] = ADMINISTRADOR_DE_UA;
            parametros[1] =
                    getDatosEmpleado().getNombre() + " " + getDatosEmpleado().getApellidoPaterno() + " "
                            + getDatosEmpleado().getApellidoMaterno();
            setMsgAsignar(MessageFormat.format(messages.getString("usuarios.asignar.confirmacion"),
                    (Object[]) parametros));
        }

    }

    public boolean validarAdministradorGlobal() {
        boolean resultado = false;
        setPermisosUsuario(administradorGlobalBusiness.consultarPermisosUsuario(getDatosEmpleado().getRfc()));
        for (InformacionUsuarioDTO info : getPermisosUsuario()) {
            if (info.getIdRol().equals("AdministradorGlobal")) {
                resultado = true;
            }
        }
        return resultado;
    }

    public boolean validarAdministradorUnidad() {
        boolean admin = false;
        setPermisosUsuario(administradorGlobalBusiness.consultarPermisosUsuario(getDatosEmpleado().getRfc()));
        for (InformacionUsuarioDTO info : getPermisosUsuario()) {
            if (info.getIdRol().equals("AdministradorUA")) {
                admin = true;
            }
        }
        return admin;
    }

    public boolean verificarUsuarioActivo(String idUsuario) {
        return administradorGlobalBusiness.verificarUsuarioActivo(idUsuario);
    }

    public List<UnidadAdministrativaDTO> obtenerCatalogoUnidades() {
        return administradorGlobalBusiness.obtenerCatalogoUnidades();
    }

    public void activarPanelAdministradorUA() {
        disablePanel = false;

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
     * @return the administradorGlobalBusiness
     */
    public AdministradorGlobalBusiness getAdministradorGlobalBusiness() {
        return administradorGlobalBusiness;
    }

    /**
     * @param administradorGlobalBusiness
     *            the administradorGlobalBusiness to set
     */
    public void setAdministradorGlobalBusiness(AdministradorGlobalBusiness administradorGlobalBusiness) {
        this.administradorGlobalBusiness = administradorGlobalBusiness;
    }

    /**
     * @return the datosEmpleado
     */
    public PersonaInternaDTO getDatosEmpleado() {
        return datosEmpleado;
    }

    /**
     * @param datosEmpleado
     *            the datosEmpleado to set
     */
    public void setDatosEmpleado(PersonaInternaDTO datosEmpleado) {
        this.datosEmpleado = datosEmpleado;
    }

    /**
     * @return the numeroEmpleado
     */
    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    /**
     * @param numeroEmpleado
     *            the numeroEmpleado to set
     */
    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    /**
     * @return the blnPanelConsulta
     */
    public boolean isBlnPanelConsulta() {
        return blnPanelConsulta;
    }

    /**
     * @param blnPanelConsulta
     *            the blnPanelConsulta to set
     */
    public void setBlnPanelConsulta(boolean blnPanelConsulta) {
        this.blnPanelConsulta = blnPanelConsulta;
    }

    /**
     * @return the blnPanelAdminUnidad
     */
    public boolean isBlnPanelAdminUnidad() {
        return blnPanelAdminUnidad;
    }

    /**
     * @param blnPanelAdminUnidad
     *            the blnPanelAdminUnidad to set
     */
    public void setBlnPanelAdminUnidad(boolean blnPanelAdminUnidad) {
        this.blnPanelAdminUnidad = blnPanelAdminUnidad;
    }

    /**
     * @return the empleadoRol
     */
    public List<UsuarioRolUnidadAdministrativaDTO> getEmpleadoRol() {
        return empleadoRol;
    }

    /**
     * @param empleadoRol
     *            the empleadoRol to set
     */
    public void setEmpleadoRol(List<UsuarioRolUnidadAdministrativaDTO> empleadoRol) {
        this.empleadoRol = empleadoRol;
    }

    /**
     * @return the permisosDataModel
     */
    public PermisosAsignadosDataModel getPermisosDataModel() {
        return permisosDataModel;
    }

    /**
     * @param permisosDataModel
     *            the permisosDataModel to set
     */
    public void setPermisosDataModel(PermisosAsignadosDataModel permisosDataModel) {
        this.permisosDataModel = permisosDataModel;
    }

    /**
     * @return the unidades
     */
    public List<UnidadAdministrativaDTO> getUnidades() {
        return unidades;
    }

    /**
     * @param unidades
     *            the unidades to set
     */
    public void setUnidades(List<UnidadAdministrativaDTO> unidades) {
        this.unidades = unidades;
    }

    /**
     * @return the unidadAdminSelected
     */
    public UnidadAdministrativaDTO getUnidadAdminSelected() {
        return unidadAdminSelected;
    }

    /**
     * @param unidadAdminSelected
     *            the unidadAdminSelected to set
     */
    public void setUnidadAdminSelected(UnidadAdministrativaDTO unidadAdminSelected) {
        this.unidadAdminSelected = unidadAdminSelected;
    }

    /**
     * @return the blnRevocar
     */
    public boolean isBlnRevocar() {
        return blnRevocar;
    }

    /**
     * @param blnRevocar
     *            the blnRevocar to set
     */
    public void setBlnRevocar(boolean blnRevocar) {
        this.blnRevocar = blnRevocar;
    }

    /**
     * @return the checkGlobal
     */
    public boolean isCheckGlobal() {
        return checkGlobal;
    }

    /**
     * @param checkGlobal
     *            the checkGlobal to set
     */
    public void setCheckGlobal(boolean checkGlobal) {
        this.checkGlobal = checkGlobal;
    }

    /**
     * @return the blnCheck
     */
    public boolean isBlnCheck() {
        return blnCheck;
    }

    /**
     * @param blnCheck
     *            the blnCheck to set
     */
    public void setBlnCheck(boolean blnCheck) {
        this.blnCheck = blnCheck;
    }

    /**
     * @return the disable
     */
    public boolean isDisable() {
        return disable;
    }

    /**
     * @param disable
     *            the disable to set
     */
    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    /**
     * @return the permisosValidator
     */
    public PermisosValidator getPermisosValidator() {
        return permisosValidator;
    }

    /**
     * @param permisosValidator
     *            the permisosValidator to set
     */
    public void setPermisosValidator(PermisosValidator permisosValidator) {
        this.permisosValidator = permisosValidator;
    }

    /**
     * @return the unidadesAdminSelected
     */
    public List<UsuarioRolUnidadAdministrativaDTO> getUnidadesAdminSelected() {
        return unidadesAdminSelected;
    }

    /**
     * @param unidadesAdminSelected
     *            the unidadesAdminSelected to set
     */
    public void setUnidadesAdminSelected(List<UsuarioRolUnidadAdministrativaDTO> unidadesAdminSelected) {
        this.unidadesAdminSelected = unidadesAdminSelected;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg
     *            the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the msgRevocar
     */
    public String getMsgRevocar() {
        return msgRevocar;
    }

    /**
     * @param msgRevocar
     *            the msgRevocar to set
     */
    public void setMsgRevocar(String msgRevocar) {
        this.msgRevocar = msgRevocar;
    }

    /**
     * @return the permisosUsuario
     */
    public List<InformacionUsuarioDTO> getPermisosUsuario() {
        return permisosUsuario;
    }

    /**
     * @param permisosUsuario
     *            the permisosUsuario to set
     */
    public void setPermisosUsuario(List<InformacionUsuarioDTO> permisosUsuario) {
        this.permisosUsuario = permisosUsuario;
    }

    /**
     * @return the blnGlobal
     */
    public boolean isBlnGlobal() {
        return blnGlobal;
    }

    /**
     * @param blnGlobal
     *            the blnGlobal to set
     */
    public void setBlnGlobal(boolean blnGlobal) {
        this.blnGlobal = blnGlobal;
    }

    /**
     * @return the disablePanel
     */
    public boolean isDisablePanel() {
        return disablePanel;
    }

    /**
     * @param disablePanel
     *            the disablePanel to set
     */
    public void setDisablePanel(boolean disablePanel) {
        this.disablePanel = disablePanel;
    }

    /**
     * @return the msgAsignar
     */
    public String getMsgAsignar() {
        return msgAsignar;
    }

    /**
     * @param msgAsignar
     *            the msgAsignar to set
     */
    public void setMsgAsignar(String msgAsignar) {
        this.msgAsignar = msgAsignar;
    }

    public boolean isUsuarioInactivo() {
        return usuarioInactivo;
    }

    public void setUsuarioInactivo(boolean usuarioInactivo) {
        this.usuarioInactivo = usuarioInactivo;
    }

    public boolean isBnlActivarDialogo() {
        return bnlActivarDialogo;
    }

    public void setBnlActivarDialogo(boolean bnlActivarDialogo) {
        this.bnlActivarDialogo = bnlActivarDialogo;
    }

    /**
     * @return the msgAsignarGlobal
     */
    public String getMsgAsignarGlobal() {
        return msgAsignarGlobal;
    }

    /**
     * @param msgAsignarGlobal
     *            the msgAsignarGlobal to set
     */
    public void setMsgAsignarGlobal(String msgAsignarGlobal) {
        this.msgAsignarGlobal = msgAsignarGlobal;
    }

    /**
     * @return the confirmGlobal
     */
    public boolean isConfirmGlobal() {
        return confirmGlobal;
    }

    /**
     * @param confirmGlobal
     *            the confirmGlobal to set
     */
    public void setConfirmGlobal(boolean confirmGlobal) {
        this.confirmGlobal = confirmGlobal;
    }

}
