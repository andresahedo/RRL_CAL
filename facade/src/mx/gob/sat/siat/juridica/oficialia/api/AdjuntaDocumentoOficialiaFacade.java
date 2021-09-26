package mx.gob.sat.siat.juridica.oficialia.api;

import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitud;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;

import java.io.InputStream;
import java.util.List;

public interface AdjuntaDocumentoOficialiaFacade extends BaseCloudFacade {

    InputStream descargarArchivo(String ruta);

    void guardarDocumentosSolicitud(Solicitud solicitud, List<DocumentoDTO> listaDocumentos, String rfcCapturista,
            DocumentoDTO documentoDto) throws ArchivoNoGuardadoException;

    void eliminaDocumento(long idDocumentoSol);

    List<DocumentoDTO> obtenerTiposDocumentos(String idSolicitud, String numAsunto);

    Solicitud obtenerDatosSolicitud(Long idSolicitud);

}
