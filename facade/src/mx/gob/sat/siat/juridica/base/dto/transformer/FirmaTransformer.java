package mx.gob.sat.siat.juridica.base.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Firma;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

@Component
public class FirmaTransformer extends DTOTransformer<FirmaDTO, Firma> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Firma transformarDTO(FirmaDTO firmaDTO) {
        Firma firma = new Firma(firmaDTO.getFechaFirma());
        firma.setCadenaOriginal(firmaDTO.getCadenaOriginal());
        firma.setCertificado(firmaDTO.getCertificado());
        firma.setFirmaElectronica(firmaDTO.getSello());
        firma.setClaveUsuario(firmaDTO.getRfcUsuario());
        firma.setClaveRol(firmaDTO.getCveRol());
        firma.setIdeProcesoFirma(firmaDTO.getCveProceso());
        return firma;
    }

    public FirmaDTO transformarFirma(Firma firma) {
        FirmaDTO firmaDTO = new FirmaDTO();
        firmaDTO.setCadenaOriginal(firma.getCadenaOriginal());
        firmaDTO.setCertificado(firma.getCertificado());
        firmaDTO.setSello(firma.getFirmaElectronica());
        firmaDTO.setRfcUsuario(firma.getClaveUsuario());
        firmaDTO.setCveRol(firma.getClaveRol());

        return firmaDTO;
    }

}
