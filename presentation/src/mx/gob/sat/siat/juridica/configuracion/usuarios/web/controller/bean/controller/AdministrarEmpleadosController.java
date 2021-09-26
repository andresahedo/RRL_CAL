/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.dto.InformacionUsuarioDTO;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.AdminUsuariosDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PermisosDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.UsuarioRolUnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business.AdministrarEmpleadosBusiness;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.constantes.AdministracionConstantes;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.model.InformacionUsuarioDataModel;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.UsuariosDataModel;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Clase Controller para la administracion de empleados
 * 
 * @author Softtek
 * 
 */
@ViewScoped
@ManagedBean(name = "administrarEmpleadosController")
public class AdministrarEmpleadosController extends AdministracionUsuariosBaseController {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1894746095549036425L;

    private static final String ACCION_BTN_CANCEL = "accionBotonCancelar";

    /**
     * DTO que contiene los datos del Empleado
     */
    private PersonaInternaDTO empleadoDTO = new PersonaInternaDTO();

    /**
     * DTO que contiene los permisos del Empleado
     */
    private List<PermisosDTO> listaPermisos = new ArrayList<PermisosDTO>();

    /**
     * Mensaje para la bandeja vacia tipo String
     */
    private String mensajeBandejaVacia;

    /**
     * Estructura para mantener la infromacion de la tabla de
     * resoluciones.
     */
    private UsuariosDataModel usuariosDataModel;

    /**
     * Bean que implementa la logica de negocio.
     */
    @ManagedProperty("#{administrarEmpleadosBusiness}")
    private AdministrarEmpleadosBusiness administrarEmpleadosBusiness;

    /**
     * Propiedad para manejar los mensajes en pantalla.
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;

    private Long numeroEmpleado;

    private boolean blnEmpleadoActivo;

    private InformacionUsuarioDataModel informacionUsuarioDataModel;

    private List<InformacionUsuarioDTO> rolesUsuario = new ArrayList<InformacionUsuarioDTO>();

    private List<InformacionUsuarioDTO> roles;

    private List<InformacionUsuarioDTO> rolesSelected;

    private InformacionUsuarioDTO rolSelected;

    private boolean rolReplica;

    private boolean blnAsignado;

    private boolean blnAsignar;

    private boolean blnUnidadBase;

    private String msgActualizar;

    private String msg;

    private boolean blnActivaDialogo;

    private boolean esUnidadBase;

    private boolean blnBase;

    private int accionBoton;

    private List<InformacionUsuarioDTO> listaRolReplica;

    /**
     * Metodo para identificar una fila que fue elegido en la tabla.
     * 
     * @param event
     */
    public void onRowSelect(SelectEvent event) {

        for (InformacionUsuarioDTO rolUsu : getRolesUsuario()) {
            rolUsu.setRolReplica(false);
        }
        InformacionUsuarioDTO rolsel = (InformacionUsuarioDTO) event.getObject();
        for (InformacionUsuarioDTO rolUsu : getRolesUsuario()) {
            if (rolUsu.getIdRol().equals(rolsel.getIdRol())) {
                rolUsu.setRolReplica(true);
            }

        }
        setInformacionUsuarioDataModel(new InformacionUsuarioDataModel(getRolesUsuario()));
    }

    public void rowSelected() {
        if (getListaRolReplica() != null && !getListaRolReplica().isEmpty()) {
            for (InformacionUsuarioDTO rolUsuario : getListaRolReplica()) {
                setRolSelected(rolUsuario);
            }
        }

    }

    /**
     * Metodo que inicializa los datos
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @PostConstruct
    public void init() {
        String mensaje = "mensaje";
        setAccionBoton((Integer) getFlash().get(ACCION_BTN_CANCEL));
        empleadoDTO = (PersonaInternaDTO) getFlash().get("datosEmpleado");
        if (getFlash().get("blnAsignar") != null) {
            blnAsignar = (Boolean) getFlash().get("blnAsignar");
        }
        blnActivaDialogo = false;
        roles = (List<InformacionUsuarioDTO>) getFlash().get("rolesUsuario");
        for (InformacionUsuarioDTO rol : roles) {
            if (!rol.getDescripcionRol().equals("Administrador Global")) {
                rolesUsuario.add(rol);
            }
        }
        setAdminUsuariosDTO((AdminUsuariosDTO) getFlash().get(VistaConstantes.DATOS_ADMINUSUARIO_DTO));
        if (getFlash().get(mensaje) != null) {
            String msgInicio = getFlash().get(mensaje).toString();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msgInicio, ""));
        }
        validarUnidadBase();
        setUrlsRegreso((LinkedList<String>) getFlash().get(VistaConstantes.BACK));
        this.rowSelected();
    }

    public List<InformacionUsuarioDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<InformacionUsuarioDTO> roles) {
        this.roles = roles;
    }

    public void validarUnidadBase() {
        List<UsuarioRolUnidadAdministrativaDTO> rolesUnidades =
                administrarEmpleadosBusiness.validarUnidadBase(getAdminUsuariosDTO().getIdUnidadAmin(),
                        getEmpleadoDTO().getRfc());
        getEmpleadoDTO().setUnidadAdmin(getAdminUsuariosDTO().getUnidadAdministrativa());
        if (rolesUnidades.size() > 1) {
            esUnidadBase = true;
            blnBase = true;
            listaRolReplica = new ArrayList<InformacionUsuarioDTO>();
            for (InformacionUsuarioDTO info : getRolesUsuario()) {
                if (info.isRolReplica()) {
                    listaRolReplica.add(info);
                }
            }
            setInformacionUsuarioDataModel(new InformacionUsuarioDataModel(getRolesUsuario()));
        }
        else {
            esUnidadBase = false;
            blnBase = false;
            listaRolReplica = new ArrayList<InformacionUsuarioDTO>();
            for (InformacionUsuarioDTO info : getRolesUsuario()) {
                if (info.isRolReplica()) {
                    listaRolReplica.add(info);
                }
            }
            if (listaRolReplica != null && !listaRolReplica.isEmpty()) {
                setInformacionUsuarioDataModel(new InformacionUsuarioDataModel(listaRolReplica));
            }
            else {
                esUnidadBase = true;
                setInformacionUsuarioDataModel(new InformacionUsuarioDataModel(getRolesUsuario()));
            }
        }

    }

    public void reasignarTramites() {
        String mensaje = "mensaje";
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("rfc", getEmpleadoDTO().getRfc());
        getFlash().put(mensaje, getFlash().get(mensaje));
        getFlash().put("datosEmpleado", getEmpleadoDTO());
        getFlash().put("rolesUsuario", getRolesUsuario());
        getFlash().put(ACCION_BTN_CANCEL, getAccionBoton());
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        getUrlsRegreso().add("administrarEmpleados");
        getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
        getFlash().put("disabled", true);
        redireccionarPagina(AdministracionConstantes.ASIGNAR_TRAMITES.toString());
    }

    public void reasignar() {
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        getUrlsRegreso().add("administrarEmpleados");
        getFlash().put("datosEmpleado", getEmpleadoDTO());
        getFlash().put("rolesUsuario", getRolesUsuario());
        getFlash().put(ACCION_BTN_CANCEL, getAccionBoton());
        getFlash().put("disabled", true);
        getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
        redireccionarPagina(AdministracionConstantes.REASIGNAR_TAREA.toString());

    }

    public void actualizar() {
        if (validaPermisos()) {
            if (getListaRolReplica() != null && !getListaRolReplica().isEmpty()) {
                if (!isEsUnidadBase()) {
                    administrarEmpleadosBusiness.actualizarPermisosUsuario(getListaRolReplica(), getAdminUsuariosDTO()
                            .getIdUnidadAmin(), getEmpleadoDTO());
                }
                else {
                    administrarEmpleadosBusiness.actualizarPermisosUsuario(getRolesUsuario(), getAdminUsuariosDTO()
                            .getIdUnidadAmin(), getEmpleadoDTO());
                }
            }
            else {
                administrarEmpleadosBusiness.actualizarPermisosUsuario(getRolesUsuario(), getAdminUsuariosDTO()
                        .getIdUnidadAmin(), getEmpleadoDTO());
            }
            String[] param = new String[2];
            param[0] = getAdminUsuariosDTO().getUnidadAdministrativa();
            param[1] =
                    getEmpleadoDTO().getNombre() + " " + getEmpleadoDTO().getApellidoPaterno() + " "
                            + getEmpleadoDTO().getApellidoMaterno();
            msg = MessageFormat.format(messages.getString("usuarios.adminEmpleado.actualizar"), (Object[]) param);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", msg));
        }
        else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", messages
                            .getString("usuarios.asignar.noHayPermisos")));
        }
    }

    public boolean validaPermisos() {
        boolean permisoReplicar = false;
        int indicadorReplicar = 0;
        for (InformacionUsuarioDTO rolesUsrDTO : rolesUsuario) {
            if (rolesUsrDTO.isRolReplica()) {
                indicadorReplicar++;
            }
        }
        if (indicadorReplicar != 0) {
            permisoReplicar = true;
        }
        return permisoReplicar;

    }

    public void activarConfirm() {
        blnActivaDialogo = true;
        String[] parametro = new String[2];
        parametro[0] = getAdminUsuariosDTO().getUnidadAdministrativa();
        parametro[1] =
                getEmpleadoDTO().getNombre() + " " + getEmpleadoDTO().getApellidoPaterno() + " "
                        + getEmpleadoDTO().getApellidoMaterno();
        setMsgActualizar(MessageFormat.format(messages.getString("usuarios.adminEmpleado.confirmacion"),
                (Object[]) parametro));

    }

    public void back() {
        getFlash().put(ACCION_BTN_CANCEL, getAccionBoton());
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
        super.back();
    }

    public List<PermisosDTO> getListaPermisos() {
        return listaPermisos;
    }

    public void setListaPermisos(List<PermisosDTO> listaPermisos) {
        this.listaPermisos = listaPermisos;
    }

    public String getMensajeBandejaVacia() {
        return mensajeBandejaVacia;
    }

    public void setMensajeBandejaVacia(String mensajeBandejaVacia) {
        this.mensajeBandejaVacia = mensajeBandejaVacia;
    }

    public PersonaInternaDTO getEmpleadoDTO() {
        return empleadoDTO;
    }

    public void setEmpleadoDTO(PersonaInternaDTO empleadoDTO) {
        this.empleadoDTO = empleadoDTO;
    }

    public UsuariosDataModel getUsuariosDataModel() {
        return usuariosDataModel;
    }

    public void setUsuariosDataModel(UsuariosDataModel usuariosDataModel) {
        this.usuariosDataModel = usuariosDataModel;
    }

    /**
     * @return the administrarEmpleadosBusiness
     */
    public AdministrarEmpleadosBusiness getAdministrarEmpleadosBusiness() {
        return administrarEmpleadosBusiness;
    }

    /**
     * @param administrarEmpleadosBusiness
     *            the administrarEmpleadosBusiness to set
     */
    public void setAdministrarEmpleadosBusiness(AdministrarEmpleadosBusiness administrarEmpleadosBusiness) {
        this.administrarEmpleadosBusiness = administrarEmpleadosBusiness;
    }

    /**
     * @return the numeroEmpleado
     */
    public Long getNumeroEmpleado() {
        return numeroEmpleado;
    }

    /**
     * @param numeroEmpleado
     *            the numeroEmpleado to set
     */
    public void setNumeroEmpleado(Long numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    /**
     * @return the blnEmpleadoActivo
     */
    public boolean isBlnEmpleadoActivo() {
        return blnEmpleadoActivo;
    }

    /**
     * @param blnEmpleadoActivo
     *            the blnEmpleadoActivo to set
     */
    public void setBlnEmpleadoActivo(boolean blnEmpleadoActivo) {
        this.blnEmpleadoActivo = blnEmpleadoActivo;
    }

    /**
     * @return the informacionUsuarioDataModel
     */
    public InformacionUsuarioDataModel getInformacionUsuarioDataModel() {
        return informacionUsuarioDataModel;
    }

    /**
     * @param informacionUsuarioDataModel
     *            the informacionUsuarioDataModel to set
     */
    public void setInformacionUsuarioDataModel(InformacionUsuarioDataModel informacionUsuarioDataModel) {
        this.informacionUsuarioDataModel = informacionUsuarioDataModel;
    }

    /**
     * @return the rolesUsuario
     */
    public List<InformacionUsuarioDTO> getRolesUsuario() {
        return rolesUsuario;
    }

    /**
     * @param rolesUsuario
     *            the rolesUsuario to set
     */
    public void setRolesUsuario(List<InformacionUsuarioDTO> rolesUsuario) {
        this.rolesUsuario = rolesUsuario;
    }

    /**
     * @return the rolesSelected
     */
    public List<InformacionUsuarioDTO> getRolesSelected() {
        return rolesSelected;
    }

    /**
     * @param rolesSelected
     *            the rolesSelected to set
     */
    public void setRolesSelected(List<InformacionUsuarioDTO> rolesSelected) {
        this.rolesSelected = rolesSelected;
    }

    /**
     * @return the rolReplica
     */
    public boolean isRolReplica() {
        return rolReplica;
    }

    /**
     * @param rolReplica
     *            the rolReplica to set
     */
    public void setRolReplica(boolean rolReplica) {
        this.rolReplica = rolReplica;
    }

    /**
     * @return the blnAsignado
     */
    public boolean isBlnAsignado() {
        return blnAsignado;
    }

    /**
     * @param blnAsignado
     *            the blnAsignado to set
     */
    public void setBlnAsignado(boolean blnAsignado) {
        this.blnAsignado = blnAsignado;
    }

    /**
     * @return the blnAsignar
     */
    public boolean isBlnAsignar() {
        return blnAsignar;
    }

    /**
     * @param blnAsignar
     *            the blnAsignar to set
     */
    public void setBlnAsignar(boolean blnAsignar) {
        this.blnAsignar = blnAsignar;
    }

    /**
     * @return the blnUnidadBase
     */
    public boolean isBlnUnidadBase() {
        return blnUnidadBase;
    }

    /**
     * @param blnUnidadBase
     *            the blnUnidadBase to set
     */
    public void setBlnUnidadBase(boolean blnUnidadBase) {
        this.blnUnidadBase = blnUnidadBase;
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
     * @return the msgActualizar
     */
    public String getMsgActualizar() {
        return msgActualizar;
    }

    /**
     * @param msgActualizar
     *            the msgActualizar to set
     */
    public void setMsgActualizar(String msgActualizar) {
        this.msgActualizar = msgActualizar;
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

    public boolean isBlnActivaDialogo() {
        return blnActivaDialogo;
    }

    public void setBlnActivaDialogo(boolean blnActivaDialogo) {
        this.blnActivaDialogo = blnActivaDialogo;
    }

    /**
     * @return the esUnidadBase
     */
    public boolean isEsUnidadBase() {
        return esUnidadBase;
    }

    /**
     * @param esUnidadBase
     *            the esUnidadBase to set
     */
    public void setEsUnidadBase(boolean esUnidadBase) {
        this.esUnidadBase = esUnidadBase;
    }

    /**
     * @return the rolSelected
     */
    public InformacionUsuarioDTO getRolSelected() {
        return rolSelected;
    }

    /**
     * @param rolSelected
     *            the rolSelected to set
     */
    public void setRolSelected(InformacionUsuarioDTO rolSelected) {
        this.rolSelected = rolSelected;
    }

    /**
     * @return the blnBase
     */
    public boolean isBlnBase() {
        return blnBase;
    }

    /**
     * @param blnBase
     *            the blnBase to set
     */
    public void setBlnBase(boolean blnBase) {
        this.blnBase = blnBase;
    }

    /**
     * @return the accionBoton
     */
    public int getAccionBoton() {
        return accionBoton;
    }

    /**
     * @param accionBoton
     *            the accionBoton to set
     */
    public void setAccionBoton(int accionBoton) {
        this.accionBoton = accionBoton;
    }

    /**
     * @return the listaRolReplica
     */
    public List<InformacionUsuarioDTO> getListaRolReplica() {
        return listaRolReplica;
    }

    /**
     * @param listaRolReplica
     *            the listaRolReplica to set
     */
    public void setListaRolReplica(List<InformacionUsuarioDTO> listaRolReplica) {
        this.listaRolReplica = listaRolReplica;
    }

}
