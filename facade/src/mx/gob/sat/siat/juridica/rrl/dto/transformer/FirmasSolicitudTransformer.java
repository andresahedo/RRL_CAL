package mx.gob.sat.siat.juridica.rrl.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Firma;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import org.springframework.stereotype.Component;

@Component
public class FirmasSolicitudTransformer extends DTOTransformer<FirmaDTO, Firma> {

    /**
     * 
     */
    private static final long serialVersionUID = 5529243463392531894L;

    @Override
    public Firma transformarDTO(FirmaDTO firmaDTO) {
        Firma firma = new Firma(firmaDTO.getFechaFirma());
        firma.setCadenaOriginal(firmaDTO.getCadenaOriginal());

        firma.setCertificado(firmaDTO.getCertificado());
        firma.setCertificado(firmaDTO.getTipoFirma());
        return firma;
    }

}
