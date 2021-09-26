package mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer;

import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.TipoTramiteConfigurar;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.TipoTramiteConfigurarDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

@Component
public class TipoTramiteConfigurarDTOTransformer extends
        DTOTransformer<TipoTramiteConfigurarDTO, TipoTramiteConfigurar> {

    /**
     * 
     */
    private static final long serialVersionUID = -6582813885647492365L;

    @Override
    public TipoTramiteConfigurar transformarDTO(TipoTramiteConfigurarDTO source) {

        TipoTramiteConfigurar tram = new TipoTramiteConfigurar();
        tram.setIdUnidadAdministrativa(source.getIdUnidadAdministrativa());
        tram.setBlnSeleccionado(source.getBlnSeleccionado());
        tram.setIdServicio(source.getIdServicio());
        tram.setDescripcionServicio(source.getDescripcionServicio());

        return tram;
    }

    public TipoTramiteConfigurarDTO transformarModel(TipoTramiteConfigurar source) {
        TipoTramiteConfigurarDTO tramDTO = new TipoTramiteConfigurarDTO();
        tramDTO.setBlnSeleccionado(source.getBlnSeleccionado());
        tramDTO.setIdServicio(source.getIdServicio());
        tramDTO.setIdUnidadAdministrativa(source.getIdUnidadAdministrativa());
        tramDTO.setDescripcionServicio(source.getDescripcionServicio());

        return tramDTO;

    }

}
