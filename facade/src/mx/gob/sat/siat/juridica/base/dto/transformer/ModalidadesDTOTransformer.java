package mx.gob.sat.siat.juridica.base.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dto.ModalidadesDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModalidadesDTOTransformer extends DTOTransformer<List<TipoTramite>, List<ModalidadesDTO>> {

    /**
     * 
     */
    private static final long serialVersionUID = -3224185820375626364L;

    @Override
    public List<ModalidadesDTO> transformarDTO(List<TipoTramite> listaModalidades) {
        List<ModalidadesDTO> lista = new ArrayList<ModalidadesDTO>();
        for (TipoTramite tipoTramite : listaModalidades) {
            ModalidadesDTO modalidad = new ModalidadesDTO();

            modalidad.setId(tipoTramite.getIdTipoTramite().toString());
            modalidad.setDescripcion(tipoTramite.getDescripcionModalidad());
            String asignacion = "0";
            if (tipoTramite.getAsignado() != null && tipoTramite.getAsignado()) {
                asignacion = "1";
            }
            modalidad.setAsignacion(asignacion);
            modalidad.setUnidadAdminResponsable(tipoTramite.getCveUnidadAdmResponsable() != null ? tipoTramite
                    .getCveUnidadAdmResponsable().getClave() : null);
            modalidad.setIdModulo(tipoTramite.getClaveModulo() != null ? tipoTramite.getClaveModulo().toString() : "");
            modalidad.setTooltip(tipoTramite.getTooltip());
            lista.add(modalidad);
        }
        return lista;
    }

}
