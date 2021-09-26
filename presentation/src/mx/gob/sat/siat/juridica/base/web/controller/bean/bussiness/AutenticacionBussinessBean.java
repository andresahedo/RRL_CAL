/*
 *  Todos los Derechos Reservados © 2013 SAT
 *  Servicio de Administracion Tributaria (SAT).
 *  
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */

package mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.UserProfileDTOTransformer;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

/**
 * Bean para la autenticacion.
 * 
 * @author softtek
 * 
 */
@ManagedBean(name = "autenticacionBussinessBean")
@NoneScoped
public class AutenticacionBussinessBean extends BaseBussinessBean {

    private static final long serialVersionUID = -8826868579067710476L;

    /**
     * Propiedad de una Clase de utilidad que transforma un perfil de
     * usuario a UserProfileDTO
     */
    @ManagedProperty("#{userProfileDTOTransformer}")
    private UserProfileDTOTransformer userProfileDTOTransformer;

    /** M�todo que regresa un perfil de usuario transformado */
    public UserProfileDTO extrarDatos(AccesoUsr accesoUsr) {
        return userProfileDTOTransformer.transformarDTO(accesoUsr);
    }

    /**
     * 
     * @return userProfileDTOTransformer
     */
    public UserProfileDTOTransformer getUserProfileDTOTransformer() {
        return userProfileDTOTransformer;
    }

    /**
     * 
     * @param userProfileDTOTransformer
     *            el userProfileDTOTransformer a fijar.
     */
    public void setUserProfileDTOTransformer(UserProfileDTOTransformer userProfileDTOTransformer) {
        this.userProfileDTOTransformer = userProfileDTOTransformer;
    }

}
