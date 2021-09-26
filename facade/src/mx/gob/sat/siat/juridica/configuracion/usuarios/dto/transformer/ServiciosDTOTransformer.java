package mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer;

import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.ServiciosModel;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.ServiciosDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

@Component
public class ServiciosDTOTransformer extends DTOTransformer<ServiciosModel, ServiciosDTO> {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    @Override
    public ServiciosDTO transformarDTO(ServiciosModel servicio) {
        ServiciosDTO servicioDto = new ServiciosDTO();
        servicioDto.setIdComponente(Long.getLong(servicio.getIdComponente().toString()));
        servicioDto.setDescripcion(servicio.getDescripcion());
        servicioDto.setUrl(servicio.getUrl() + "?faces-redirect=true");
        return servicioDto;
    }

}
