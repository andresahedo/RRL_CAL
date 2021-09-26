package mx.gob.sat.siat.juridica.rrl.api;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.rrl.util.exception.RemitirAsuntoException;

import java.util.List;

public interface AutorizarRemisionFacade extends BaseFacade {

    String obtenerNombre(String rfc);

    TramiteDTO obtenerTramite(String numAsunto);

    List<CatalogoDTO> obtenerAutoridadesEmisoras();

    String obtenerUnidad(String numAsunto);

    void actualizarRemision(String numeroAsunto, String unidad) throws RemitirAsuntoException;

    void rechazarRemision(String numAsunto, String idTarea, String rfc, Long idSolicitud);

    List<DocumentoDTO> obtenerDocumentosComplementariosAutorizacion(long idSolicitud);
}
