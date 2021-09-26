/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.api.LoginFacade;
import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.LoginDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.Map;

/**
 * Bussiness que implementa la logica de negocio para el login.
 * 
 * @author softtek
 * 
 */
@ManagedBean(name = "loginBussinessBean")
@NoneScoped
public class LoginBussinessBean extends BaseBussinessBean {

    /**
     * Numero de serie.
     */
    private static final long serialVersionUID = -7774939480059154619L;

    /**
     * Facade utilizada para login.
     */
    @ManagedProperty("#{loginFacade}")
    private LoginFacade loginFacade;

    /**
     * Metodo para efectuar el login.
     * 
     * @param loginDTO
     * @return
     */
    public String login(LoginDTO loginDTO) {
        setUserProfile(loginFacade.buscarUsuario(loginDTO.getUserProfile()));
        return loginFacade.login();
    }

    /**
     * 
     * @return loginFacade
     */
    public LoginFacade getLoginFacade() {
        return loginFacade;
    }

    /**
     * 
     * @param loginFacade
     *            el loginFacade a fijar.
     */
    public void setLoginFacade(LoginFacade loginFacade) {
        this.loginFacade = loginFacade;
    }

    /**
     * 
     * @return Map de usuarios.
     */
    public Map<String, String> getUsuarios() {
        return loginFacade.getUsers();
    }

    /**
     * 
     * @return Map de roles.
     */
    public Map<String, String> getRoles() {
        return loginFacade.getRoles();
    }
}
