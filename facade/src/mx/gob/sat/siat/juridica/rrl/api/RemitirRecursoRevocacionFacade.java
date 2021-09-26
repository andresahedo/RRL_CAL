package mx.gob.sat.siat.juridica.rrl.api;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.rrl.util.exception.RemitirAsuntoException;

import java.util.List;

 public interface RemitirRecursoRevocacionFacade extends BaseFacade {

    List<CatalogoDTO> obtenerAutoridadesEmisoras();

    void remitir(String numAsunto, String unidadAdministrativa, Long idTarea, String rfcAsignar, String rfcUsuario,
            String ideTareaOrigen) throws RemitirAsuntoException;

    List<CatalogoDTO> obtenerPersonasAsignar(String numAsunto);

    void enviarCorreo(String numeroAsunto, String rfc, String nombreTarea);

    TramiteDTO obtenerTramite(String numAsunto);

    String getDefaultIdeTareaOrigen();

    List<DocumentoDTO> obtenerDocumentosComplementarios(Long idSolicitud);

}
