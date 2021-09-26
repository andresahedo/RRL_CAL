/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.api;

import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface AutorizarRequerimientoRRLFacade extends BaseCloudFacade {

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

    void actualizarRequerimiento(RequerimientoDTO requerimiento);

    List<CatalogoDTO> obtenerAutoridadesTodas();

    List<CatalogoDTO> obtenerTiposDeRequerimiento(String tipoReq);

    /**
     * M&eacute;todo para preparar la autorizaci&oacute;n del
     * requerimiento.
     * 
     * @param numeroAsunto
     * @param userProfile
     * @return RequerimientoDTO
     */

    RequerimientoDTO prepararAutorizarRequerimiento(String numeroAsunto, UserProfileDTO userProfile, String modalidad);

    void
            rechazarRequerimiento(String numAsunto, Long idTareaUsuario, String rfc, Long idRequerimiento,
                    Long idSolicitud);

    String generaCadenaOriginalAutorizarRequerimiento(Long idRequerimiento, Date fechaFirma, String rfcFuncionario);

    List<DocumentoDTO> obtenerDocumentosComplementarios(Long idSolicitud);

    /**
     * Borrado logico de documentos oficiales vinculados a un
     * requerimiento
     * 
     * @param idRequerimiento
     */
    void eliminaDocumentosRequerimiento(Long idRequerimiento);

}
