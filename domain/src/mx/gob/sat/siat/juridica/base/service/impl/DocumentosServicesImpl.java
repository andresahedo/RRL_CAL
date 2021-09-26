/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.DocumentoDao;
import mx.gob.sat.siat.juridica.base.dao.RequerimientoDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Documento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoSolicitud;
import mx.gob.sat.siat.juridica.base.service.DocumentosServices;
import mx.gob.sat.siat.juridica.base.util.helper.DocumentosHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Service
public class DocumentosServicesImpl extends BaseSerializableBusinessServices implements DocumentosServices {

    /**
     * Id de la version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "documentoDao" tipo DocumentoDao
     */
    @Autowired
    private DocumentoDao documentoDao;
    @Autowired
    private RequerimientoDao requerimientoDao;
    @Autowired
    private DocumentosHelper documentosHelper;

    /**
     * Devuelve una lista con los DocumentoSolicitud con estado
     * ANEXADO (ESTDOC.AN) asociados a la solicitud identificada con
     * idSolicitud
     * 
     * @param idSolicitud
     * @return lista con los DocumentoSolicitud asociados a la
     *         solicitud identificada con idSolicitud o null en caso
     *         de no existir documentos
     */
    @Override
    public List<DocumentoSolicitud> obtenerDocumentosAnexados(long idSolicitud) {
        return documentoDao.obtenerDocumentosAnexados(idSolicitud);
    }

    /**
     * Marca como eliminado un documento de la solicitud
     * 
     * @param idSolicitud
     *            Identificador del documento de la solicitud
     */
    @Override
    public void eliminaDocumentoSol(long idDocumentoSol) {
        DocumentoSolicitud docSol = documentoDao.obtenerDocumentoSolPorId(idDocumentoSol);
        if (docSol != null) {
            docSol.setBlnActivo(0);
            docSol.setEstadoDocumentoSolicitud(EstadoDocumento.ELIMINADO.getClave());
            docSol.getDocumento().setBlnActivo(0);
            docSol.getDocumento().setEstadoDocumento(EstadoDocumento.ELIMINADO.getClave());
            // TODO: revisar si no es necesario usar merge para
            // persistir los cambios
        }
    }

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
    @Override
    public List<DocumentoSolicitud> filtraDocumentosSeleccionados(long idSolicitud,
            List<Documento> documentosSeleccionados) {

        // Obtiene los documentos previamente guardados con la
        // solicitud
        // Por la relacion tipo EAGER entre DocumentoSolicitud y
        // Documento, se espera que cada DocumentoSolicitud
        // tenga instanciado el objeto Documento relacionado.
        List<DocumentoSolicitud> documentosSolicitud = documentoDao.obtenerDocumentosAnexados(idSolicitud);
        // Lista de documentos que seran devueltos
        List<DocumentoSolicitud> documentosAnexados = null;

        if (documentosSolicitud != null && documentosSolicitud.size() > 0) {  
                                                                             // hay
                                                                             // documentos
                                                                             // previamente
                                                                             // persistidos
            documentosAnexados = new ArrayList<DocumentoSolicitud>();

            // Determina los documentos ya anexados a la solicitud que
            // ya no estan seleccionados para adjuntar
            for (DocumentoSolicitud docAnx : documentosSolicitud) {
                boolean eliminarDoc = true;
                for (int i = 0; i < documentosSeleccionados.size() && eliminarDoc; i++) {
                    Documento docSel = documentosSeleccionados.get(i);
                    if (docSel.getIdTipoDocumento() != null
                            && docSel.getIdTipoDocumento().equals(docAnx.getIdTipoDocumento())) {
                        eliminarDoc = false;
                    }
                }
                // Si el documento anexado previamente ya no esta en
                // la seleccion del usuario para adjuntar documentos,
                // lo elimina
                if (eliminarDoc) {
                    docAnx.setBlnActivo(0);
                    docAnx.setEstadoDocumentoSolicitud(EstadoDocumento.ELIMINADO.getClave());
                    docAnx.getDocumento().setBlnActivo(0);
                    docAnx.getDocumento().setEstadoDocumento(EstadoDocumento.ELIMINADO.getClave());
                    // TODO: es necesario mandar a persistir estos
                    // cambios de estado, o por estar en la
                    // transaccion lo hace de manera implicita?
                }
                else { 
                       // De lo contrario lo agrega a la lista de
                       // documentos anexados a devolver
                    documentosAnexados.add(docAnx);
                }
            }
        }

        return documentosAnexados;
    }

    /**
     * Metodo para guardar documentos de solicitud
     * 
     * @param documento
     * @param documentoSol
     */
    @Override
    public void guardaDocumento(List<Documento> documento, List<DocumentoSolicitud> documentoSol) {

        for (int i = 0; i < documento.size(); i++) {
            if (documento.get(i).getIdDocumento() == null) {
                Documento doc = new Documento();
                DocumentoSolicitud doc2 = new DocumentoSolicitud();

                doc.setIdTipoDocumento(documento.get(i).getIdTipoDocumento());
                doc.setNombre(documento.get(i).getNombre());
                doc.setFechaCreacion(documento.get(i).getFechaCreacion());
                doc.setPersona(documento.get(i).getPersona());
                doc.setHash(documento.get(i).getHash());
                doc.setUrlDocumento(documento.get(i).getUrlDocumento());
                doc.setLongitud(documento.get(i).getLongitud());
                doc.setEstadoDocumento(EstadoDocumento.ANEXADO.getClave());
                doc.setBlnActivo(1);
                doc.setComplementario(documento.get(i).getComplementario());
                doc.setLongitud(documento.get(i).getLongitud());
                if (documento.get(i).getIdDocumento() != null) { 
                                                                 // IdDocumento
                                                                 // trae
                                                                 // como
                                                                 // valor
                                                                 // el
                                                                 // idDocumentoSol
                                                                 // del
                                                                 // objeto
                                                                 // DocumentoSolicitud
                                                                 // relacionado
                    // Busca el idDocumento correspondiente
                    Long idDocumento = documentoDao.obtenIdDocumentoPorIdDocSol(documento.get(i).getIdDocumento());
                    doc.setIdDocumento(idDocumento);
                }
                documentoDao.guardaDocumentoSolicitud(doc);
                doc2.setDocumento(doc);

                doc2.setIdTipoDocumento(documentoSol.get(i).getIdTipoDocumento());
                doc2.setFechaAsociacion(documentoSol.get(i).getFechaAsociacion());
                doc2.setIdSolicitud(documentoSol.get(i).getIdSolicitud());
                doc2.setCvePersona(documentoSol.get(i).getCvePersona());
                doc2.setIdDocumento(doc.getIdDocumento());
                doc2.setEstadoDocumentoSolicitud(EstadoDocumento.ANEXADO.getClave());
                doc2.setComplementario(documentoSol.get(i).getComplementario());
                doc2.setBlnActivo(1);
                if (documentoSol.get(i).getFolio() != null) {
                    doc2.setFolio(documentoSol.get(i).getFolio());
                }
                if (documentoSol.get(i).getFechaApertura() != null) {
                    doc2.setFechaApertura(documentoSol.get(i).getFechaApertura());
                }
                if (documento.get(i).getIdDocumento() != null) {
                                                                 // IdDocumento
                                                                 // trae
                                                                 // como
                                                                 // valor
                                                                 // el
                                                                 // idDocumentoSol
                                                                 // del
                                                                 // objeto
                                                                 // DocumentoSolicitud
                                                                 // relacionado
                    doc2.setIdDocumentoSolicitud(documento.get(i).getIdDocumento());
                }
                documentoDao.guardaDocumento(doc2);

                doc = null;
                doc2 = null;
            }
        }
    }

    /**
     * Busca el TipoDocumento identificado por idTipoDocumento. No
     * filtra por campos de registro activo, s&oacute;lo por Id
     * 
     * @param idTipoDocumento
     *            Identificador del tipo de documento
     * @return TipoDocumento o null si no existe un TipoDocumento con
     *         el id especificado
     */
    @Override
    public TipoDocumento obtenerTipoDocumentoPorId(int idTipoDocumento) {
        return documentoDao.obtenerTipoDocumentoPorId(idTipoDocumento);
    }

    /**
     * Genera cadena de tama&ntilde;o de archivo en Kb, Mb, Gb
     * 
     * @param tamanioBytes
     *            Tama&ntilde;o de archivo en bytes
     * @return Cadena en bytes "NN.NN Kb", "NN.NN Mb", "NN.NN Gb",
     *         donde NN.NN es el n&uacute;mero que representa el
     *         tama&ntilde;o del archivo en la unidad especificada
     */
    @Override
    public String obtenerCadenaTamanioArchivo(long tamanioBytes) {
        return documentosHelper.obtenerCadenaTamanioArchivo(tamanioBytes);
    }

    @Override
    public List<DocumentoSolicitud> obtenerDocumentosComplementariosAutorizacion(Long idSolicitud) {

        return documentoDao.obtenerDocumentosComplementarios(idSolicitud);
    }

    @Override
    /**
     * Borrado logico de documentos oficiales vinculados a un requerimiento
     * @param idRequerimiento
     */
    public void eliminaDocumentosOficialesPorIdRequerimiento(Long idRequerimiento) {
        List<DocumentoOficial> documentosAutReq =
                requerimientoDao.obtenerDocumentosOficialesPorIdRequerimiento(idRequerimiento);
        if (documentosAutReq != null) {
            for (DocumentoOficial documento : documentosAutReq) {
                documento.setBlnActivo(Integer.valueOf(0));
                documento.setEstadoDocumento(EstadoDocumento.ELIMINADO.getClave());
            }
        }
    }

    @Override
    /**
     * Firma los documentos oficiales vinculados a un requerimiento
     * @param idRequerimiento
     */
    public void firmaDocumentosOficialesPorIdRequerimiento(Long idRequerimiento) {
        List<DocumentoOficial> documentosAutReq =
                requerimientoDao.obtenerDocumentosOficialesPorIdRequerimiento(idRequerimiento);
        if (documentosAutReq != null) {
            for (DocumentoOficial documento : documentosAutReq) {
                documento.setEstadoDocumento(EstadoDocumento.FIRMADO.getClave());
            }
        }
    }

    /**
     * Borrado logico de documentos oficiales ANEXADOS del tipo
     * especificado vinculados a un asunto
     * 
     * @param numFolio
     * @param cveDocumentos
     */
    public void eliminarDocumentosOficialesAnexadosPorTipo(String numAsunto, String tipoDocumento) {
        List<DocumentoOficial> documentosOficiales =
                documentoDao.obtenerDocumentosOficialesAnexadosPorTipo(numAsunto, tipoDocumento);
        if (documentosOficiales != null) {
            for (DocumentoOficial doc : documentosOficiales) {
                doc.setBlnActivo(0);
                doc.setEstadoDocumento(EstadoDocumento.ELIMINADO.getClave());
            }
        }
    }

    @Override
    public void eliminaDocumentoNoFirmadosSol(long idSolicitud) {
        List<DocumentoSolicitud> docsSol = obtenerDocumentosAnexados(idSolicitud);
        for (DocumentoSolicitud docSol : docsSol) {
            docSol.setBlnActivo(0);
            docSol.setEstadoDocumentoSolicitud(EstadoDocumento.ELIMINADO.getClave());
            Documento doc = docSol.getDocumento();
            doc.setBlnActivo(0);
            doc.setEstadoDocumento(EstadoDocumento.ELIMINADO.getClave());
        }

    }

    @Override
    public List<Long> obtenerDocumentosFirmadosOfialia(Long idSolicitud, Long folio) {
        return documentoDao.obtenerDocumentosFirmadosOfialia(idSolicitud, folio);
    }

    @Override
    public List<Long> obtenerIdSolicitudPorFolio(Long folio) {
        return documentoDao.obtenerIdSolicitudPorFolio(folio);
    }

    @Override
    public List<DocumentoSolicitud> obtenerDocumentosPorIdSolicitud(Long idSolicitud) {

        return documentoDao.obtenerDocumentosPorIdSolicitud(idSolicitud);
    }

    @Override
    public List<DocumentoOficial> obtenerDocumentosOficialesAnexadosPorTipo(String numeroAsunto, String clave) {
        return documentoDao.obtenerDocumentosOficialesAnexadosPorTipo(numeroAsunto,clave);
    }

    @Override
    public void guardarDocumentoOficial(DocumentoOficial documentoOficial) {
        documentoDao.guardarDocumentoOficial(documentoOficial);
    }

}
