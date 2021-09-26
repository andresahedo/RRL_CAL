/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.dto.InformacionUsuarioDTO;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.AdminUsuariosDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.UsuarioRolUnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business.BuscarFuncionarioBusiness;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.constantes.AdministracionConstantes;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.*;

/**
 * 
 * @author softtek
 * 
 */
@ViewScoped
@ManagedBean(name = "buscarFuncionarioController")
public class BuscarFuncionarioController extends AdministracionUsuariosBaseController {

    /**
     * 
     */
    private static final long serialVersionUID = -6732466075449317515L;

    private PersonaInternaDTO datosEmpleado;

    /**
     * Propiedad numeroEmpleado tipo Long
     */
    private String numeroEmpleado;

    /**
     * Propiedad para manejar los mensajes en pantalla.
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;

    /**
     * Propiedad Business para la busqueda de empleados
     */
    @ManagedProperty("#{buscarFuncionarioBusiness}")
    private transient BuscarFuncionarioBusiness buscarFuncionarioBusiness;

    private String mensaje;

    private boolean blnAsignar;

    private List<InformacionUsuarioDTO> rolesUsuario;

    private List<UsuarioRolUnidadAdministrativaDTO> rolesAsignados;

    /**
     * Metodo que incializa datos
     */
    @PostConstruct
    public void inicio() {
        setAdminUsuariosDTO((AdminUsuariosDTO) getFlash().get(VistaConstantes.DATOS_ADMINUSUARIO_DTO));
        setUrlsRegreso((LinkedList<String>) getFlash().get(VistaConstantes.BACK));
    }

    public void buscarFuncionario() {
        setDatosEmpleado(buscarFuncionarioBusiness.buscarFuncionario(Long.parseLong(getNumeroEmpleado())));
        if (getDatosEmpleado() != null) {
            this.mostarRolesAsignados();
            if (buscarFuncionarioBusiness.validarUsuarioActivo(getDatosEmpleado().getRfc())) {
                blnAsignar = true;

                getFlash().put("blnAsignar", blnAsignar);
            }
            else {
                blnAsignar = false;
                mensaje = messages.getString("usuarios.mensaje.empleado.inactivo");
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "", messages
                                .getString("usuarios.mensaje.empleado.inactivo")));
                getFlash().put("blnAsignar", blnAsignar);
            }
        }
        else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", messages
                            .getString("usuarios.mensaje.empleado.noExiste")));

            return;
        }
        int accionBoton = 1;
        getFlash().put("mensaje", mensaje);
        getFlash().put("datosEmpleado", datosEmpleado);
        getFlash().put("rolesUsuario", getRolesUsuario());
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        getFlash().put("accionBotonCancelar", accionBoton);
        getUrlsRegreso().add("consultarEmpleado");
        getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
        redireccionarPagina(AdministracionConstantes.ADMINISTRAR_EMPLEADO);

    }

    public void back() {
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
        super.back();
    }

    public void mostarRolesAsignados() {
        Map<String, String> clavesRolesAsignados = new HashMap<String, String>();
        rolesAsignados = buscarFuncionarioBusiness.obtenerTodosRolesUsuario(getDatosEmpleado().getRfc());
        rolesUsuario = buscarFuncionarioBusiness.consultarRolesNovell(getDatosEmpleado().getRfc());
        if (rolesAsignados != null && !rolesAsignados.isEmpty()) {
            for (UsuarioRolUnidadAdministrativaDTO rol : rolesAsignados) {
                if (rol.getUnidadAdministrativa().getClave().equals(getAdminUsuariosDTO().getIdUnidadAmin())) {
                    clavesRolesAsignados.put(rol.getRol(), rol.getIdUsuario());
                }
            }

            for (InformacionUsuarioDTO info : rolesUsuario) {
                if (!info.getIdRol().equals(AdministracionConstantes.ADMIN_GLOBAL)
                        || info.getIdRol().equals(AdministracionConstantes.ADMIN_UA)) {
                    if (clavesRolesAsignados.containsKey(info.getIdRol())) {
                        info.setAsignado(true);
                    }
                    else {
                        info.setAsignado(false);
                    }
                }
            }

            List<InformacionUsuarioDTO> rolesEliminar = new ArrayList<InformacionUsuarioDTO>();
            for (InformacionUsuarioDTO inf : rolesUsuario) {
                if (inf.getIdRol().equals(AdministracionConstantes.ADMIN_GLOBAL)
                        || inf.getIdRol().equals(AdministracionConstantes.ADMIN_UA)) {
                    rolesEliminar.add(inf);
                }
            }

            rolesUsuario.removeAll(rolesEliminar);
        }
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
     * @return the buscarFuncionarioBusiness
     */
    public BuscarFuncionarioBusiness getBuscarFuncionarioBusiness() {
        return buscarFuncionarioBusiness;
    }

    /**
     * @param buscarFuncionarioBusiness
     *            the buscarFuncionarioBusiness to set
     */
    public void setBuscarFuncionarioBusiness(BuscarFuncionarioBusiness buscarFuncionarioBusiness) {
        this.buscarFuncionarioBusiness = buscarFuncionarioBusiness;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje
     *            the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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
     * @return the rolesAsignados
     */
    public List<UsuarioRolUnidadAdministrativaDTO> getRolesAsignados() {
        return rolesAsignados;
    }

    /**
     * @param rolesAsignados
     *            the rolesAsignados to set
     */
    public void setRolesAsignados(List<UsuarioRolUnidadAdministrativaDTO> rolesAsignados) {
        this.rolesAsignados = rolesAsignados;
    }
}
