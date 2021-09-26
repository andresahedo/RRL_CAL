/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.constantes.MenusConstantes;
import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dto.LoginDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.LoginBussinessBean;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller que implementa las reglas de negocio para el login.
 * 
 * @author softtek
 * 
 */
@ManagedBean(name = "loginControllerBean")
@RequestScoped
public class LoginControllerBean extends BaseControllerBean {
    /**
     * Numero de serie.
     */
    private static final long serialVersionUID = -2088216086284425905L;
    /**
     * Controller que implementa las reglas de negocio para la
     * autenticacion.
     */
    @ManagedProperty(value = "#{autenticacionController}")
    private AutenticacionController autenticacionController;
    /**
     * Bussiness que implementa la logica de negocio para login.
     */
    @ManagedProperty("#{loginBussinessBean}")
    private LoginBussinessBean loginBussinessBean;
    /**
     * DTO que representa el login.
     */
    private LoginDTO loginDTO = new LoginDTO();

    /**
     * Metodo que carga los catalogos despues de la creacion del bean.
     */
    @PostConstruct
    public void cargarCatalogos() {
        loginDTO.setUsers(loginBussinessBean.getUsuarios());
        loginDTO.setRoles(loginBussinessBean.getRoles());

        getLogger().debug(" url por ambiente: {}", UrlFirma.PAGINA_FIRMA_SOLICITUD.toString());
    }

    /**
     * Metodo que implementa la funcionalidad de login.
     * 
     * @throws IOException
     */
    public void login() throws IOException {
        AccesoUsr accesoUsr = new AccesoUsr();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("acceso", null);
        session.setAttribute("accesoEF", null);

        // Se coloca un valor de session id fijo cuando entramos por
        // el login de stk
        accesoUsr.setSessionID("123123");
        accesoUsr.setCentroDatos("22");

        accesoUsr.setUsuario(loginDTO.getUserProfile().getRfc());
        accesoUsr.setRoles(loginDTO.getUserProfile().getRol());
        // Simulamos el ingreso en el portal de contribuyentes, ya que
        // de eso depende
        // que al BPM se envien rfc cortos o largos.
        getLogger().debug("loginDTO.getUserProfile().getRol() {}", loginDTO.getUserProfile().getRol());
        if ("Contribuyente".equals(loginDTO.getUserProfile().getRol())) {
            accesoUsr.setMenu(String.valueOf(MenusConstantes.MENU_CONTRIBUYENTES));
            accesoUsr.setIdTipoPersona("1");
            session.setAttribute("acceso", accesoUsr);

        }
        else {
            if ("Abogado".equals(loginDTO.getUserProfile().getRol())) {
                accesoUsr.setMenu(String.valueOf(MenusConstantes.MENU_EMPLEADOS));
                accesoUsr.setRoles(RolesConstantes.ROL_EMPLEADO_ABOGADO);
                // LLenamos los procesos a los que tiene acceso un
                // contribuyente
                Map<Integer, String> procesos = new HashMap<Integer, String>();
                procesos.put(1, ProcesosConstantes.PROCESO_EMPLEADO_BANDEJA_TAREAS);
                accesoUsr.setProcesos(procesos);

            }
            else if ("Administrador".equals(loginDTO.getUserProfile().getRol())) {
                accesoUsr.setMenu(String.valueOf(MenusConstantes.MENU_EMPLEADOS));
                accesoUsr.setRoles(RolesConstantes.ROL_EMPLEADO_ADMINISTRADOR);
                // LLenamos los procesos a los que tiene acceso un
                // contribuyente
                Map<Integer, String> procesos = new HashMap<Integer, String>();
                procesos.put(1, ProcesosConstantes.PROCESO_EMPLEADO_REASIGNAR_RRL);
                procesos.put(2, ProcesosConstantes.PROCESO_EMPLEADO_REASIGNAR_CAL);
                procesos.put(NumerosConstantes.TRES, ProcesosConstantes.PROCESO_EMPLEADO_BANDEJA_TAREAS);

                accesoUsr.setProcesos(procesos);
            }
            else if ("OficialPartes".equals(loginDTO.getUserProfile().getRol())) {
                accesoUsr.setMenu(String.valueOf(MenusConstantes.MENU_EMPLEADOS));
                accesoUsr.setRoles(RolesConstantes.ROL_EMPLEADO_OFICIAL_PARTES);
                // LLenamos los procesos a los que tiene acceso un
                // contribuyente
                Map<Integer, String> procesos = new HashMap<Integer, String>();
                procesos.put(1, ProcesosConstantes.PROCESO_EMPLEADO_REGISTRO_OFICIALIA);
                procesos.put(2, ProcesosConstantes.PROCESO_EMPLEADO_RECHAZOS_OFICIALIA);
                procesos.put(NumerosConstantes.TRES, ProcesosConstantes.PROCESO_EMPLEADO_BANDEJA_TAREAS);

                accesoUsr.setProcesos(procesos);
            }
            else if ("AdministradorUnidadAdministrativa".equals(loginDTO.getUserProfile().getRol())) {
                accesoUsr.setRoles(RolesConstantes.ROL_ADMINISTRADOR_UNIDAD_ADMINISTRATIVA);
            }
            else if ("AdministradorGlobal".equals(loginDTO.getUserProfile().getRol())) {
                accesoUsr.setRoles(RolesConstantes.ROL_ADMINISTRADOR_GLOBAL);
            }
            accesoUsr.setIdTipoPersona("2");
            session.setAttribute("accesoEF", accesoUsr);
        }
        autenticacionController.validar();
    }

    /**
     * 
     * @return autenticacionController
     */
    public AutenticacionController getAutenticacionController() {
        return autenticacionController;
    }

    /**
     * 
     * @param autenticacionController
     *            el autenticacionController a fijar.
     */
    public void setAutenticacionController(AutenticacionController autenticacionController) {
        this.autenticacionController = autenticacionController;
    }

    /**
     * 
     * @return loginBussinessBean
     */
    public LoginBussinessBean getLoginBussinessBean() {
        return loginBussinessBean;
    }

    /**
     * 
     * @param loginBussinessBean
     *            el loginBussinesBean a fijar.
     */
    public void setLoginBussinessBean(LoginBussinessBean loginBussinessBean) {
        this.loginBussinessBean = loginBussinessBean;
    }

    /**
     * 
     * @return loginDTO.
     */
    public LoginDTO getLoginDTO() {
        return loginDTO;
    }

    /**
     * 
     * @param loginDTO
     *            el loginDTO a fijar.
     */
    public void setLoginDTO(LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
    }

}
