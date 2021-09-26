/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoUnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dao.impl.BaseJPADao;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.rrl.dao.RegistroRecursoRevocacionDAO;
import mx.gob.sat.siat.juridica.rrl.dao.domain.model.SolicitudRecursoRevocacion;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * 
 * @author softtek
 */
 @Repository
 public class RegistroRecursoRevocacionDAOImpl extends BaseJPADao implements RegistroRecursoRevocacionDAO {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 8728357069436523296L;
    private static final String PARAMETRO_NUM_ASUNTO = "numAsunto";

    /**
     * Metodo para obtener una lista de documentos por tramite
     * 
     * @param idTipoTramite
     * @param opcional
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<DocumentoTramite> buscarDocumentosTramitePorTipoTramite(Integer idTipoTramite, Integer opcional) {
        Query query =
                getEntityManager()
                        .createQuery(
                                "SELECT dt from DocumentoTramite dt"
                                        + " where dt.especifico = :especifico "
                                        + " and dt.tipoTramite.idTipoTramite = :idTipoTramite1 and dt.vigencia.blnActivo= 1 ORDER BY dt.orden");
        query.setParameter("especifico", opcional);
        query.setParameter("idTipoTramite1", idTipoTramite);
        return query.getResultList();
    }

    /**
     * Metodo para buscar documentos por tipo de tramite
     * 
     * @param idTramite
     * @param reglaAnexado
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<DocumentoTramite> buscarDocumentosTramitePorTipoTramite(String idTipoTramite, String reglaAnexado) {
        Query query =
                getEntityManager().createQuery(
                        "SELECT dt from DocumentoTramite dt " + "where dt.tipoTramite.idTipoTramite = :idTipoTramite "
                                + "and dt.reglaAnexado = :reglaAnexado and dt.vigencia.blnActivo= 1 ");
        query.setParameter("idTipoTramite", Integer.parseInt(idTipoTramite));
        query.setParameter("reglaAnexado", reglaAnexado);
        return query.getResultList();
    }

    /**
     * Metodo para buscar documentos por tramite por su tipo
     * 
     * @param idTipoTramite
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<DocumentoTramite> buscarDocumentosTramitePorTipoTramite(String idTipoTramite) {
        Query query =
                getEntityManager().createQuery(
                        "SELECT dt from DocumentoTramite dt "
                                + "where dt.tipoTramite.idTipoTramite = :idTipoTramite and dt.vigencia.blnActivo= 1");
        query.setParameter("idTipoTramite", Integer.parseInt(idTipoTramite));
        return query.getResultList();
    }

    /**
     * Metodo para buscar una lista de requerimientos por numero de
     * folio
     * 
     * @param numAsunto
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<Requerimiento> buscarRequerimientosPorNumFolio(String numAsunto) {
        Query query =
                getEntityManager().createQuery(
                        "SELECT r from Requerimiento r " + "where r.tramite.numeroAsunto = :numAsunto ");
        query.setParameter(PARAMETRO_NUM_ASUNTO, numAsunto);
        return query.getResultList();
    }

    /**
     * Metodo para buscar un tramite por numero de folio
     * 
     * @param numAsunto
     * @return
     */
    public Tramite buscarTramitePorNumFolio(String numAsunto) {
        Query query = getEntityManager().createQuery("SELECT t from Tramite t " + "where t.numeroAsunto = :numAsunto ");
        query.setParameter(PARAMETRO_NUM_ASUNTO, numAsunto);

        return (Tramite) query.getResultList().get(0);

    }

    /**
     * Metodo para obtener doumentos de una solicitud
     * 
     * @param idSol
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<DocumentoSolicitud> obtenerDocumentoSolicitud(String idSol) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ds from DocumentoSolicitud ds where");
        sb.append(" ds.solicitud.idSolicitud = :idSol2 and ds.documento.blnActivo = 1");
        sb.append(" and ds.estadoDocumentoSolicitud='"
                + EstadoDocumento.FIRMADO.getClave() + "' order by ds.fechaAsociacion desc ");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("idSol2", Long.parseLong(idSol));
        return query.getResultList();
    }
    
    /**
     * Metodo para obtener doumentos de una solicitud
     * 
     * @param idSol
     * @param idTipoDocumento
     * @return DocumentoSolicitud
     */
    public DocumentoSolicitud obtenerDocumentoSolicitudTipoDoc(String idSol, Integer idTipoDocumento) {
        DocumentoSolicitud objDocSol=null;
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ds from DocumentoSolicitud ds ");
        sb.append("where ds.solicitud.idSolicitud = :idSol2 ");
        sb.append("and ds.documento.idTipoDocumento=:idTipoDocumento ");
        sb.append("and rownum <= 1 ");
        sb.append("and ds.documento.blnActivo = 1 and ds.estadoDocumentoSolicitud='"
                + EstadoDocumento.FIRMADO.getClave() + "' order by ds.fechaAsociacion desc");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("idSol2", Long.parseLong(idSol));
        query.setParameter("idTipoDocumento", idTipoDocumento);
        
        try{
            objDocSol=(DocumentoSolicitud)query.getSingleResult();
        }
        catch (NoResultException nre){
               getLog().error("No se encontraron Resultados Para obtenerDocumentoSolicitud idSol:"+idSol+" idTipoDocumento:"+idTipoDocumento, nre.getMessage());
        }
        return objDocSol;
        
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<DocumentoSolicitud> obtenerDocumentoSolicitudAnexado(String idSol) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ds from DocumentoSolicitud ds ");
        sb.append("where ds.solicitud.idSolicitud = :idSol ");
        sb.append("and ds.documento.blnActivo = 1 and ds.estadoDocumentoSolicitud='"
                + EstadoDocumento.ANEXADO.getClave() + "' order by ds.fechaAsociacion desc");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("idSol", Long.parseLong(idSol));
        return query.getResultList();
    }

    /**
     * Metodo para obtener un documento completo
     * 
     * @param idDoc
     * @return
     */
    public Documento obtenerDocumentoCompleto(Long idDoc) {
        Query query = getEntityManager().createQuery("SELECT d from Documento d " + "where d.idDocumento = :idDoc");
        query.setParameter("idDoc", idDoc);
        if (query.getResultList() != null && !query.getResultList().isEmpty()) {
            return (Documento) query.getResultList().get(0);
        }
        return null;

    }

    /**
     * Metodo para obtener una lista por tipo de documento
     * 
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<TipoDocumento> obtenerListaTipoDocumento() {
        Query query = getEntityManager().createQuery("SELECT td from TipoDocumento td ");
        return query.getResultList();
    }

    /**
     * Metodo para obtener documentos oficiales
     * 
     * @param numAsunto
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<DocumentoOficial> obtenerDocumentosOficiales(String numAsunto) {
        Query query =
                getEntityManager()
                        .createQuery(
                                " SELECT do from DocumentoOficial do "
                                        + "where do.tramite.numeroAsunto = :numAsunto and do.blnActivo = 1 and do.estadoDocumento = :estado3 order by do.fechaCreacion desc ");
        query.setParameter(PARAMETRO_NUM_ASUNTO, numAsunto);
        query.setParameter("estado3", EstadoDocumento.FIRMADO.getClave());
        return query.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<DocumentoOficial> obtenerDocumentosOficialesPromovente(String numAsunto) {
        Query query =
                getEntityManager()
                        .createQuery(
                                " SELECT do from DocumentoOficial do "
                                        + "where do.tramite.numeroAsunto = :numAsunto and do.blnActivo = 1 and do.estadoDocumento = :estado and do.sello is null order by do.fechaCreacion desc ");
        query.setParameter(PARAMETRO_NUM_ASUNTO, numAsunto);
        query.setParameter("estado", EstadoDocumento.FIRMADO.getClave());
        return query.getResultList();
    }
    
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<DocumentoOficial> obtenerDocumentosAsuntoPromovente(String numAsunto) {
        StringBuffer consulta = new StringBuffer();
        consulta.append(" SELECT doc FROM DocumentoOficial doc ");
        consulta.append(" WHERE doc.tramite.numeroAsunto = :numAsunto AND doc.blnActivo = 1 AND  doc.estadoDocumento = :estado AND doc.numFolioOficio = :estDoc order by doc.fechaCreacion desc ");
        Query query = getEntityManager().createQuery(consulta.toString());
        query.setParameter(PARAMETRO_NUM_ASUNTO, numAsunto);
        query.setParameter("estado", EstadoDocumento.FIRMADO.getClave());
        query.setParameter("estDoc", EstadoDocumento.GENERADO.getClave());
        return query.getResultList();
    }

    /**
     * Metodo para crear una solicitud de revocacion
     */
    public SolicitudRecursoRevocacion crearSolicitud(SolicitudRecursoRevocacion solicitud) {
        if (solicitud != null && solicitud.getIdSolicitud() == null) {
            getEntityManager().persist(solicitud);
        }
        else {
            getEntityManager().merge(solicitud);
        }
        return solicitud;
    }

    /**
     * Metodo para crear un solicitante
     * 
     * @param solicitante
     */
    public void crearSolicitante(Solicitante solicitante) {
        getEntityManager().persist(solicitante);

    }

    /**
     * Metodo para obtener un soliccitante por rfc
     * 
     * @param idSolicitud
     * @return
     */
    @Override
    public Solicitante getSolicituanteByRfc(Long idSolicitud) {
        Query query = getEntityManager().createQuery("select p from Solicitante p where p.idSolicitud = :idSolicitud ");

        query.setParameter("idSolicitud", idSolicitud);
        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        List<Object> resultado = query.getResultList();
        if (resultado != null && !resultado.isEmpty()) {
            return (Solicitante) resultado.get(0);
        }
        else {
            return null;
        }
    }

    /**
     * Metodo para obtener una lista de unidades administrativas
     * 
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<UnidadAdministrativa> obtenerUnidadesAdministrativas() {
        Query query =
                getEntityManager()
                        .createQuery(
                                "SELECT ua FROM UnidadAdministrativa ua where ua.ideTipoUnidadAdministrativa <> :tipoUA "
                                        + " and ua.vigencia.blnActivo = 1 "
                                        + " and not (ua.vigencia.fechaFinVigencia is not null and ua.vigencia.fechaFinVigencia < current_date() ) ");
        query.setParameter("tipoUA", TipoUnidadAdministrativa.ADMINISTRACION_EXTERNA.getClave());
        return query.getResultList();
    }

    /**
     * Metodo para guardar un documento
     * 
     * @param documentoSol
     */
    public void guardaDocumento(DocumentoSolicitud documentoSol) {
        if (documentoSol.getIdDocumentoSolicitud() == null) {
            getEntityManager().persist(documentoSol);
        }
        else {
            getEntityManager().merge(documentoSol);
        }
    }

    /**
     * Metodo para documento de solicitud
     * 
     * @param documento
     */
    public void guardaDocumentoSolicitud(Documento documento) {
        if (documento.getIdDocumento() == null) {
            getEntityManager().persist(documento);
        }
        else {
            getEntityManager().merge(documento);
        }
        getEntityManager().flush();
    }

    /**
     * Metodo para guardar un documento de solicitud de
     * requerimiento@param docSolReq
     */
    public void guardarDocumentoSolicitudRequerimiento(DocumentoSolicitudRequerimiento documento) {
        getEntityManager().persist(documento);
    }

    /**
     * Metodo para obtener documentos por ide de solicitud y tipo de
     * documento
     * 
     * @param idSol
     * @param tipoDoc
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<DocumentoSolicitud> obtenerDocumentoSolicitudTipoDocumento(long idSol, int tipoDoc) {
        Query query =
                getEntityManager().createQuery(
                        "SELECT ds from DocumentoSolicitud ds " + "where ds.solicitud.idSolicitud = :idSol "
                                + "and ds.documento.idTipoDocumento = :idTipoDocumento");
        query.setParameter("idSol", idSol);
        query.setParameter("idTipoDocumento", tipoDoc);
        return query.getResultList();
    }

    /**
     * Metodo para obtener documentos de solicitud por id de
     * requerimiento
     * 
     * @param idreq
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<DocumentoSolicitud> obtenerDocumentoSolicitudByIdReq(long idreq) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT dsr.documentoSolicitud from DocumentoSolicitudRequerimiento dsr ");
        sb.append("where dsr.requerimiento.idRequerimiento = :idreq ");
        sb.append("and dsr.documentoSolicitud.documento.blnActivo = 1 ");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("idreq", idreq);
        return query.getResultList();
    }

    /**
     * Metodo para obtener documentos oficiales por id
     * 
     * @param idDoc
     * @return
     */
    @Override
    public DocumentoOficial obtenerDocumentosOficialesById(Long idDoc) {
        Query query =
                getEntityManager().createQuery(
                        "SELECT do from DocumentoOficial do " + "where do.idDocumentoOficial = :idDoc");
        query.setParameter("idDoc", idDoc);
        return (DocumentoOficial) query.getSingleResult();
    }

    /**
     * Metodo para guardar un documento de solicitud de requerimiento
     * 
     * @param docSolReq
     */
    @Override
    public void guardaDocumentoSolicitudRequerimiento(DocumentoSolicitudRequerimiento docSolReq) {
        getEntityManager().persist(docSolReq);
    }

}
