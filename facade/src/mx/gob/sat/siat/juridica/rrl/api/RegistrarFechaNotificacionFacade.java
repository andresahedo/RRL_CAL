package mx.gob.sat.siat.juridica.rrl.api;

import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Notificacion;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;

import java.util.Date;
import java.util.List;

public interface RegistrarFechaNotificacionFacade extends BaseCloudFacade {

    Long
            guardarFechaNotificacion(String numAsunto, Boolean blnAutorizacion, Date fechaNotificacion,
                    Long idNotificacion);

    TramiteDTO obtenerTramiteIdAsunto(String numeroAsunto);

    Boolean validarFechaNotificacion(Date fechaNotificacion, String numeroAsunto);

    List<DocumentoOficialDTO> obtenerDocumentosFecha(String numAsunto);

    Date buscarFechaNotificacionByIdTramite(String numeroAsunto);

    Date buscarFechaNotificacionByIdRequerimiento(Long idRequerimiento);
    
    void eliminaDocumentosNotificacionAnexados(String numAsunto);
    
    Notificacion buscarNotifParcial(String numAsunto, Boolean blnAutorizacion);
}
