package mx.gob.sat.siat.juridica.base.api;

import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;

import java.io.Serializable;

public interface UsuarioFacade extends Serializable {

    void generarContribuyente(UserProfileDTO userProfileDTO);

}
