package mx.gob.sat.siat.juridica.base.api;

import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;

import java.io.Serializable;
import java.util.Map;

public interface LoginFacade extends Serializable {

    Map<String, String> getUsers();

    Map<String, String> getRoles();

    UserProfileDTO buscarUsuario(UserProfileDTO user);

    String login();

}
