package mx.gob.sat.siat.juridica.base.api.impl;

import mx.gob.sat.siat.juridica.base.api.FirmarFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.FirmaTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.FirmarServices;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("firmarFacade")
public class FirmarFacadeImpl extends BaseFacadeImpl implements FirmarFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 3202641946819032425L;

    @Autowired
    private transient FirmarServices firmarServices;

    @Autowired
    private transient FirmaTransformer firmaTransformer;

    @Autowired
    private transient SolicitudService solicitudService;

    public void guardarFirma(FirmaDTO firmaDTO, Long idSolicitud) {
        Firma firma = firmaTransformer.transformarDTO(firmaDTO);
        firmarServices.guardarFrima(firma);
        FirmaSolicitud firmaSol = new FirmaSolicitud();
        firmaSol.setFirmaSolicitudPK(new FirmaSolicitudPK());
        firmaSol.getFirmaSolicitudPK().setIdFirma(firma.getIdFirma());
        firmaSol.getFirmaSolicitudPK().setIdSolicitud(idSolicitud);
        firmarServices.guardarFirmaSolicitud(firmaSol);
    }

    @Override
    public void guardarFirmaRequerimento(FirmaDTO firmaDTO, Long idRequerimiento, Long idSolicitud) {

        Firma firma = firmaTransformer.transformarDTO(firmaDTO);
        firmarServices.guardarFrima(firma);
        FirmaRequerimiento firmaReq = new FirmaRequerimiento();
        firmaReq.setFirmaRequerimientoPK(new FirmaRequerimientoPK());
        firmaReq.getFirmaRequerimientoPK().setIdFirma(firma.getIdFirma());
        firmaReq.getFirmaRequerimientoPK().setIdRequerimiento(idRequerimiento);
        firmarServices.guardarFirmaRequerimientoAutorizacion(firmaReq);
        solicitudService.firmarDocumentos(idSolicitud, firma, false);
    }

    @Override
    public void guardarFirmaAutorizarRequerimento(FirmaDTO firmaDTO, Long idRequerimiento) {

        Firma firma = firmaTransformer.transformarDTO(firmaDTO);
        firmarServices.guardarFrima(firma);
        FirmaRequerimiento firmaReq = new FirmaRequerimiento();
        firmaReq.setFirmaRequerimientoPK(new FirmaRequerimientoPK());
        firmaReq.getFirmaRequerimientoPK().setIdFirma(firma.getIdFirma());
        firmaReq.getFirmaRequerimientoPK().setIdRequerimiento(idRequerimiento);
        firmarServices.guardarFirmaRequerimientoAutorizacion(firmaReq);
    }
}
