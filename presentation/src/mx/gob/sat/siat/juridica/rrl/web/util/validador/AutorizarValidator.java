package mx.gob.sat.siat.juridica.rrl.web.util.validador;

import mx.gob.sat.siat.juridica.base.web.util.validador.BaseValidator;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "autorizarValidator")
@NoneScoped
public class AutorizarValidator extends BaseValidator {

    /**
     * 
     */
    private static final long serialVersionUID = -3583347569186479765L;

    public String guardadoValido(List<ResolucionImpugnadaDTO> resolucionesDTOList, String errorMessage,
            ResourceBundle errorMsg) {
        String message = "";
        if (resolucionesDTOList.isEmpty()) {
            message = errorMsg.getString("vuj.resolucion.vacio");
        }
        for (ResolucionImpugnadaDTO resolucionImpugnadaDTO : resolucionesDTOList) {
            if (!(resolucionImpugnadaDTO.getDeterminanteCred()) && resolucionImpugnadaDTO.getConceptos().isEmpty()) {
                message = errorMsg.getString("vuj.resolucion.resolCred");
            }
        }

        return message;

    }

    public String resolucionNueva(ResolucionImpugnadaDTO resolDto, String resolImpugnada) {
        if (resolDto.getIdTramite() != null && resolDto.getIdTramite().length() != 0
                && resolDto.getAutoridadEmisora() != null) {
            return "La resoluci\u00F3n " + resolImpugnada + " est\u00E1 relacionada con el " + resolDto.getIdTramite()
                    + " perteneciente a la " + resolDto.getAutoridadEmisora().getNombre();
        }
        return "";
    }
}
