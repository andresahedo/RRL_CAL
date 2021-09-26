package mx.gob.sat.siat.juridica.base.api;

import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public interface AtenderRequerimientoFacade extends BaseCloudFacade {

    InputStream descargarDocumento(DocumentoDTO documento);

    void guardarDocumentosSolicitud(long idSolicitud, long idRequerimiento, List<DocumentoDTO> documentos,
            String tipoDocumento, String rfc) throws ArchivoNoGuardadoException;

    void firmaAtender(DatosBandejaTareaDTO datosBandeja, RequerimientoDTO requerimiento, String rfcUsuario);

    List<DocumentoDTO> obtenerDocumentosSolicitudRequerimiento(long idRequerimiento);

    void eliminaDocumento(long idDocumento);

    RequerimientoDTO prepararAtenderRequerimiento(String numeroAsunto, String modalidad);

    String generarCadenaOriginal(long idSolicitud, Date fechaFirma);

    String generarCadenaOriginalPromocion(long idSolicitud, Date fechaFirma);

    FirmaDTO obtenSelloAtenderRequerimientoSIAT(String numAsunto, long idSolicitud, Date fechaFirma);

    void eliminaDocumentosSolicitudRequerimiento(Long idRequerimiento);

    RequerimientoDTO obtenerRequerimientoporId(Long idRequerimiento);
}
