package mx.gob.sat.siat.juridica.cal.api;

import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public interface AtenderRetroCALFacade extends BaseCloudFacade {

    InputStream descargarDocumento(DocumentoDTO documento);

    void guardarDocumentosSolicitud(long idSolicitud, long idRequerimiento, List<DocumentoDTO> documentos,
            String tipoDocumento, String rfc) throws ArchivoNoGuardadoException;

    List<DocumentoDTO> obtenerDocumentosSolicitudRequerimiento(long idRequerimiento);

    void eliminaDocumento(long idDocumento);

    void eliminaDocumentoOficial(long idDocumento);

    void guardarDocumentosAutorizarRequerimiento(List<DocumentoOficialDTO> documentos, String numAsunto,
            Long idRequerimiento);

    void firmaAtender(DatosBandejaTareaDTO datosBandeja, Long idRequerimiento, String rfcUsuario,
            String numAsunto);

    void guardarFirmaAtender(FirmaDTO firmaDTO, Long idRequerimiento);

    FirmaDTO obtenSelloAtenderRetroSIAT(String numeroAsunto, Date fechaFirma, String rfcFuncionario);

    String generarCadenaOriginal(String numeroAsunto, Date fechaFirma, String rfcFuncionario);

    void sellarDocumentosAtencion(String numeroAsunto);
}
