package mx.gob.sat.siat.juridica.cal.api;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;

import java.io.InputStream;
import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface AutorizarRequerimientoCALFacade {

    /**
     * M&eacute;todo para obtener los tipos de requerimiento.
     * 
     * @return Lista de CatalogoDTO
     */
    List<CatalogoDTO> obtenerTiposDeRequerimiento();

    /**
     * M&eacute;todo para obtener las autoridades a las que se
     * solicitar&aacute; el requerimiento.
     * 
     * @return Lista de CatalogoDTO
     */
    List<CatalogoDTO> obtenerAutoridades();

    /**
     * M&eacute;todo para descargar el archivo asociado al documento
     * oficial.
     * 
     * @param documento
     * @return InputStream
     */
    InputStream descargarArchivoOficial(DocumentoOficialDTO documento);

    /**
     * M&eacute;todo para guardar los documentos asociadas al
     * requerimiento (autorizaci&oacute;n).
     * 
     * @param documentos
     * @param requerimiento
     */
    void guardarDocumentosAutorizarRequerimiento(List<DocumentoOficialDTO> documentos, RequerimientoDTO requerimiento);

    /**
     * M&eacute;todo para firmar la autorizaci&oacute;n del
     * requerimiento.
     * 
     * @param requerimiento
     * @param datosBandeja
     */
    void firmarAutorizarRequerimiento(RequerimientoDTO requerimiento, DatosBandejaTareaDTO datosBandeja,
            String rfcUsuario);

    RequerimientoDTO prepararAutorizarRequerimientoByIdRequerimiento(Long idRequerimiento, UserProfileDTO userProfile);

    List<DocumentoOficialDTO> obtenerDocumentosPorRequerimiento(Long idRequerimiento);

    void eliminaDocumento(long idDocumento);

    /**
     * M&eacute;todo para preparar la autorizaci&oacute;n del
     * requerimiento.
     * 
     * @param numeroAsunto
     * @param userProfile
     * @return RequerimientoDTO
     */

    RequerimientoDTO prepararAutorizarRequerimiento(String numeroAsunto, UserProfileDTO userProfile, String modalidad);
}
