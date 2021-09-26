package mx.gob.sat.siat.juridica.base.dto.transformer;

import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserProfileDTOTransformer extends DTOTransformer<AccesoUsr, UserProfileDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = -3532878764009002852L;

    private transient Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserProfileDTO transformarDTO(AccesoUsr accesoUsr) {
        UserProfileDTO userProfileDTO = new UserProfileDTO();

        logger.debug("User: {}", userProfileDTO.getUsuario());
        logger.debug("accesoUsr.getUsuario: {}", accesoUsr.getUsuario());

        userProfileDTO.setUsuario(accesoUsr.getUsuario());
        // El rfc viene en el atributo Usuario en el bean que
        // obtenemos del usuario firmado
        userProfileDTO.setRfc(accesoUsr.getUsuario());
        userProfileDTO.setNombreCompleto(accesoUsr.getNombreCompleto());
        userProfileDTO.setNombres(accesoUsr.getNombres());
        userProfileDTO.setPrimerApellido(accesoUsr.getPrimerApellido());
        userProfileDTO.setSegundoApellido(accesoUsr.getSegundoApellido());
        userProfileDTO.setIp(accesoUsr.getIp());
        userProfileDTO.setEsContribuyente("1".equals(accesoUsr.getMenu()));
        Collection<String> roles = new ArrayList<String>();
        if (null != accesoUsr.getRoles()) {
            for (String rol : accesoUsr.getRoles().split(",")) {
                roles.add(rol);
            }
        }
        userProfileDTO.setRolesNovell(roles);
        return userProfileDTO;
    }
}
