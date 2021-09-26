/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Documento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoSolicitud;

import java.io.Serializable;
import java.util.List;

/***
 * 
 * @author softtek
 * 
 */
public interface DocumentosServices extends Serializable {

    /**
     * Devuelve una lista con los DocumentoSolicitud con estado
     * ANEXADO (ESTDOC.AN) asociados a la solicitud identificada con
     * idSolicitud
     * 
     * @param idSolicitud
     * @return lista con los DocumentoSolicitud con estado ANEXADO
     *         (ESTDOC.AN) asociados a la solicitud identificada con
     *         idSolicitud
     */
    List<DocumentoSolicitud> obtenerDocumentosAnexados(long idSolicitud);

    /**
     * Marca como eliminado un documento de la solicitud
     * 
     * @param idDocumentoSol
     *            Identificador del documento de la solicitud
     */
    void eliminaDocumentoSol(long idDocumentoSol);

    /**
     * Marca como eliminado un documento de la solicitud
     * 
     * @param idDocumentoSol
     *            Identificador del documento de la solicitud
     */
    void eliminaDocumentoNoFirmadosSol(long idSolicitud);

    /**
     * Devuelve la lista de documentos previamente anexados a la
     * solicitud que a&uacute;n est&eacute;n en la lista de
     * selecci&oacute;n del usuario.
     * 
     * @param idSolicitud
     *            Id de solicitud
     * @param documentosSeleccionados
     *            Lista de documentos seleccionados para anexar,
     * @return Lista de documentos anexados a la solicitud que se
     *         mantienen en la lista de documentos a anexar
     *         especificada por el usuario o null en caso de no haber
     *         documentos anexados previamente a la solicitud.
     */
    List<DocumentoSolicitud> filtraDocumentosSeleccionados(long idSolicitud,
            List<Documento> documentosSeleccionados);

    /**
     * Metodo para guardar documentos de solicitud
     * 
     * @param documento
     * @param documentoSol
     */
    void guardaDocumento(List<Documento> documento, List<DocumentoSolicitud> documentoSol);

    /**
     * Busca el TipoDocumento identificado por idTipoDocumento. No
     * filtra por campos de registro activo, s&oacute;lo por Id
     * 
     * @param idTipoDocumento
     *            Identificador del tipo de documento
     * @return TipoDocumento o null si no existe un TipoDocumento con
     *         el id especificado
     */
    TipoDocumento obtenerTipoDocumentoPorId(int idTipoDocumento);

    /**
     * Genera cadena de tama&ntilde;o de archivo en Kb, Mb, Gb
     * 
     * @param tamanioBytes
     *            Tama&ntilde;o de archivo en bytes
     * @return Cadena en bytes "NN.NN Kb", "NN.NN Mb", "NN.NN Gb",
     *         donde NN.NN es el n&uacute;mero que representa el
     *         tama&ntilde;o del archivo en la unidad especificada
     */
    String obtenerCadenaTamanioArchivo(long tamanioBytes);

    List<DocumentoSolicitud> obtenerDocumentosComplementariosAutorizacion(Long idSolicitud);

    /**
     * Borrado logico de documentos oficiales vinculados a un
     * requerimiento
     * 
     * @param idRequerimiento
     */
    void eliminaDocumentosOficialesPorIdRequerimiento(Long idRequerimiento);

    /**
     * Borrado logico de documentos oficiales del tipo especificado
     * vinculados a un asunto
     * 
     * @param numFolio
     * @param cveDocumentos
     */
    void eliminarDocumentosOficialesAnexadosPorTipo(String numAsunto, String tipoDocumento);

    /**
     * Firma los documentos oficiales vinculados a un requerimiento
     * 
     * @param idRequerimiento
     */
    void firmaDocumentosOficialesPorIdRequerimiento(Long idRequerimiento);

    List<Long> obtenerDocumentosFirmadosOfialia(Long idSolicitud, Long folio);

    List<Long> obtenerIdSolicitudPorFolio(Long folio);

    /**
     * Metodo para obtener todos los documentos de la solicitud, sin
     * importar su estado con la finalidad de validar que ya tiene
     * documentos y no guardarlos nuevamente durante el registro de la
     * solicitud cuando se regresa con el wizard a pasos anteriores y
     * finalmente se firma.
     */

    List<DocumentoSolicitud> obtenerDocumentosPorIdSolicitud(Long idSolicitud);

    List<DocumentoOficial> obtenerDocumentosOficialesAnexadosPorTipo(String numeroAsunto, String clave);
    
    void guardarDocumentoOficial(DocumentoOficial documentoOficial);
    
    List<DocumentoOficial> obtenerDocumentosOficialesTipo(String idtramite, String idTipoDocume0toOficial);

}
