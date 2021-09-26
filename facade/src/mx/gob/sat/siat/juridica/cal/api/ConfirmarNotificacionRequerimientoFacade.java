package mx.gob.sat.siat.juridica.cal.api;

import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;

public interface ConfirmarNotificacionRequerimientoFacade extends BaseFacade {

    Long guardarDocOficial(String numFolio, DocumentoOficial documentoOficial);

    void firmarConfirmarNotificacion(String numAsunto, String idTarea, Long idRequerimiento, String rfcUsuario);

    /**
     * Guarda la firma de notificacion de requerimiento a autoridad
     * 
     * @param firma
     * @param idRequerimiento
     */
    void guardarFirmaConfirmarNotificacion(FirmaDTO firmaDTO, Long idRequerimiento);

}
