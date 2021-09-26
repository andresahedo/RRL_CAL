package mx.gob.sat.siat.juridica.base.api;

import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;

public interface FirmarFacade {

    void guardarFirma(FirmaDTO firmaDTO, Long idSolicitud);

    void guardarFirmaRequerimento(FirmaDTO firmaDTO, Long idRequerimiento, Long idSolicitud);

    void guardarFirmaAutorizarRequerimento(FirmaDTO firmaDTO, Long idRequerimiento);

}
