/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.DocumentoDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Documento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoResolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoSolicitud;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Repository
public class DocumentoDaoImpl extends BaseJPADao implements DocumentoDao {

    /**
     * 
     */
    private static final long serialVersionUID = 7649481696653701067L;

    @Value("${parametro.estadoDocumento}")
    private String parametroEstadoDocumento;

    @Value("${documentodaoimpl.select.documentosolicitud}")
    private String selectDocSol;
    @Value("${documentodaoimpl.condicion.estado.documento}")
    private String condicionEdoDocto;

    /**
     * Metodo para guardar un documento oficial
     */
    @Override
    public void guardarDocumentoOficial(DocumentoOficial doc) {
        try {
            if (doc != null) {
                if (doc.getIdDocumentoOficial() == null) {
                    getEntityManager().persist(doc);
                }
                else {
                    getEntityManager().merge(doc);
                }
            }
        }
        catch (Exception e) {
            getLog().error("", e);
        }
    }

    /**
     * Metodo para obtener documentos oficiales
     * 
     * @param numAsunto
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<DocumentoOficial> obtenerDocumentosPorTipo(String numAsunto, String tipoDocumento) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT do from DocumentoOficial do ");
        sb.append("where do.tramite.numeroAsunto = :numAsunto1 ");
        sb.append("and do.ideTipoDocOficial = :tipoDocumento1 ");
        sb.append("and do.blnActivo = 1 and do.estadoDocumento = :estado1");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("numAsunto1", numAsunto);
        query.setParameter("tipoDocumento1", tipoDocumento);
        query.setParameter("estado1", EstadoDocumento.FIRMADO.getClave());
        return query.getResultList();
    }

    /**
     * Metodo para obtener documentos oficiales
     * 
     * @param numAsunto
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<DocumentoOficial> obtenerDocumentosOficialesAnexadosPorTipo(String numAsunto, String tipoDocumento) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT do from DocumentoOficial do ");
        sb.append("where do.tramite.numeroAsunto = :numAsunto2 ");
        sb.append("and do.ideTipoDocOficial = :tipoDocumento2 ");
        sb.append("and do.blnActivo = 1 and do.estadoDocumento = :estado2");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("numAsunto2", numAsunto);
        query.setParameter("tipoDocumento2", tipoDocumento);
        query.setParameter("estado2", EstadoDocumento.ANEXADO.getClave());
        return query.getResultList();
    }
    
    /**
     * Metodo para obtener documentos oficiales
     * 
     * @param numAsunto
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<DocumentoOficial> obtenerDocumentosOficialesAnexadosExclusivoFondo(String numAsunto) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT do from DocumentoOficial do ");
        sb.append("where do.tramite.numeroAsunto = :numAsunto3 ");
        sb.append("and do.numFolioOficio = :numFolioOficio ");
        sb.append("and do.blnActivo = 1 and do.estadoDocumento = :estado3 ");
        sb.append("ORDER BY do.idDocumentoOficial ASC");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("numAsunto3", numAsunto);
        query.setParameter("numFolioOficio", EstadoDocumento.EXCLUSIVO_FONDO.getClave());
        query.setParameter("estado3", EstadoDocumento.ANEXADO.getClave());
        return query.getResultList();
    }
    
    /**
     * Metodo para obtener el ultimo documento oficial
     * 
     * @param numAsunto
     * @param tipoDocumento
     * @return
     */
    @Override
    public DocumentoOficial obtenerUltimoDocumentoOficialesAnexadosPorTipo(String numAsunto, String tipoDocumento) {
        DocumentoOficial objDocOf=null;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT do from DocumentoOficial do ");
        sb.append("where do.tramite.numeroAsunto = :numAsunto ");
        sb.append("and do.ideTipoDocOficial = :tipoDocumento ");
        sb.append("and do.blnActivo = 1 and do.estadoDocumento = :estado ");
        sb.append("and fechaCreacion = (");
        sb.append("SELECT min(dof.fechaCreacion) from DocumentoOficial dof ");
        sb.append("where dof.tramite.numeroAsunto = :numAsunto ");
        sb.append("and dof.ideTipoDocOficial = :tipoDocumento ");
        sb.append("and dof.blnActivo = 1 and dof.estadoDocumento = :estado )");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("numAsunto", numAsunto);
        query.setParameter("tipoDocumento", tipoDocumento);
        query.setParameter("estado", EstadoDocumento.ANEXADO.getClave());
        try{
            objDocOf=(DocumentoOficial)query.getSingleResult();
        }
        catch (NoResultException nre){
               getLog().error("No se encontraron Resultados Para obtenerUltimoDocumentoOficialesAnexadosPorTipo numAsunto:"+numAsunto+" tipoDocumento:"+tipoDocumento, nre.getMessage());
        }
        return objDocOf;
    }

    /**
     * Metodo para obtener documentos oficiales
     * 
     * @param numAsunto
     * @param tipoDocument
     * @param idRequerimiento
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<DocumentoOficial>
            obtenerDocumentosPorTipo(String numAsunto, String tipoDocumento, Long idRequerimiento) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT do from DocumentoOficial do, DocumentoRequerimiento dr ");
        sb.append("where do.tramite.numeroAsunto = :numAsunto ");
        sb.append("and do.ideTipoDocOficial = :tipoDocumento ");
        sb.append("and dr.requerimiento.idRequerimiento = :idRequerimiento ");
        sb.append("and dr.idDocumentoOficial = do.idDocumentoOficial ");
        sb.append("and do.blnActivo = 1 and do.estadoDocumento = :estado ");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("numAsunto", numAsunto);
        query.setParameter("tipoDocumento", tipoDocumento);
        query.setParameter("idRequerimiento", idRequerimiento);
        query.setParameter("estado", EstadoDocumento.FIRMADO.getClave());
        return query.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<DocumentoSolicitud> obtenerDocumentosComplementarios(Long idSolicitud) {
        List<DocumentoSolicitud> documentos = null;
        StringBuffer consulta = new StringBuffer();
        consulta.append("SELECT docs FROM DocumentoSolicitud docs ");
        consulta.append("WHERE docs.idSolicitud = :idSolicitud ");
        consulta.append("and docs.complementario = '1' ");
        Query query = getEntityManager().createQuery(consulta.toString());
        query.setParameter("idSolicitud", idSolicitud);
        documentos = query.getResultList();

        return documentos;
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<DocumentoSolicitud> obtenerDocumentosAnexados(Long idSolicitud) {
        StringBuffer sb = new StringBuffer();
        sb.append(this.selectDocSol);
        sb.append("where ds.solicitud.idSolicitud = :idSoli ");
        sb.append("and (ds.complementario IS NULL OR ds.complementario = 0 ) ");
        sb.append(this.condicionEdoDocto);
        sb.append("ORDER BY ds.fechaAsociacion ASC");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("idSoli", idSolicitud);
        query.setParameter(this.parametroEstadoDocumento, EstadoDocumento.ANEXADO.getClave());
        return query.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<DocumentoSolicitud> obtenerDocumentosFirmados(Long idSolicitud) {
        StringBuffer sb = new StringBuffer();
        sb.append(this.selectDocSol);
        sb.append("where ds.solicitud.idSolicitud = :idSol ");
        sb.append("and (ds.complementario IS NULL OR ds.complementario = 0 ) ");
        sb.append(this.condicionEdoDocto);
        sb.append("ORDER BY ds.fechaAsociacion ASC");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("idSol", idSolicitud);
        query.setParameter(this.parametroEstadoDocumento, EstadoDocumento.FIRMADO.getClave());
        return query.getResultList();
    }

    @Override
    public DocumentoSolicitud obtenerDocumentoSolPorId(long idDocumentoSol) {
        DocumentoSolicitud ds = null;
        Query query =
                getEntityManager().createQuery(this.selectDocSol + " where ds.idDocumentoSolicitud = :idDocumentoSol");
        query.setParameter("idDocumentoSol", idDocumentoSol);
        try {
            ds = (DocumentoSolicitud) query.getSingleResult();
        }
        catch (Exception e) {
            getLog().error("Error al obtener el documento de la solicitud por id:", e); 
        }

        return ds;
    }

    /**
     * Metodo para guardar Documento
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
     * Metodo para guardar un DocumentoSolicitud
     * 
     * @param documentoSol
     */
    public void guardaDocumento(DocumentoSolicitud documentoSol) {
        if (documentoSol.getIdDocumentoSolicitud() == null) {
            getEntityManager().persist(documentoSol);
        } else {
            getEntityManager().merge(documentoSol);
        }
    }

    /**
     * Obtiene el idDocumento del documento relacionado al
     * DocumentoSolicitud identificado por idDocumentoSol o null en
     * caso de no existir.
     */
    public Long obtenIdDocumentoPorIdDocSol(long idDocumentoSol) {
        Long id = null;
        Query query =
                getEntityManager().createQuery(
                        "SELECT ds.documento.idDocumento from DocumentoSolicitud ds "
                                + " where ds.idDocumentoSolicitud = :idDocumentoSol");
        query.setParameter("idDocumentoSol", idDocumentoSol);
        try {
            id = (Long) query.getSingleResult();
        }
        catch (Exception e) {
            getLog().error("Error al obtener el id del documento:", e); 
        }

        return id;
    }

    public TipoDocumento obtenerTipoDocumentoPorId(int idTipoDocumento) {
        StringBuffer sqlB = new StringBuffer("SELECT td FROM TipoDocumento td ");
        sqlB.append("WHERE td.idTipoDocumento = :idTipoDoc");
        Query query = getEntityManager().createQuery(sqlB.toString());
        query.setParameter("idTipoDoc", idTipoDocumento);

        TipoDocumento td = null;
        try {
            td = (TipoDocumento) query.getSingleResult();
        }
        catch (Exception e) {
            getLog().error("Error al obtener el tipo de documento por id:", e);
        }

        return td;
    }

    @Override
    public DocumentoSolicitud obtenerDocumentoSolPorIdDocumento(long idDocumentoSol) {
        DocumentoSolicitud ds = null;
        Long idDocumento = idDocumentoSol;
        Query query =
                getEntityManager().createQuery(this.selectDocSol + " where ds.documento.idDocumento = :idDocumento");
        query.setParameter("idDocumento", idDocumento);
        try {
            ds = (DocumentoSolicitud) query.getSingleResult();
        }
        catch (Exception e) {
            getLog().error("Error al obtener el documento por id de documento:", e); 
        }

        return ds;
    }

    @Override
    public Documento obtenerDocumentoPorId(Long idDocumento) {
        Documento doc = null;
        Query query =
                getEntityManager().createQuery(
                        "SELECT doc from Documento doc " + " where doc.idDocumento = :idDocumento");
        query.setParameter("idDocumento", idDocumento);
        try {
            doc = (Documento) query.getSingleResult();
        } catch (Exception e) {
            getLog().error("Error al obtener el documento por id:", e); 
        }

        return doc;
    }

    /**
     * Metodo que devuleve el Id de solicitud por medio del folio
     * 
     * @param folio
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<Long> obtenerDocumentosFirmadosOfialia(Long idSolicitud, Long folio) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT distinct ds.folio from DocumentoSolicitud ds ");
        sb.append("where ds.solicitud.idSolicitud = :idSol ");
        sb.append(this.condicionEdoDocto);
        if (folio != null && folio != 0) {
            sb.append("and ds.folio = :folio");
        }
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("idSol", idSolicitud);
        query.setParameter(this.parametroEstadoDocumento, EstadoDocumento.FIRMADO.getClave());
        if (folio != null && folio != 0) {
            query.setParameter("folio", folio);
        }
        return query.getResultList();
    }

    /**
     * Metodo que obtiene el id de solicitud por folio
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<Long> obtenerIdSolicitudPorFolio(Long folio) {
        List<Long> id = null;

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT distinct ds.idSolicitud from DocumentoSolicitud ds ");
        sb.append("where ds.folio = :folio ");
        sb.append(this.condicionEdoDocto);
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("folio", folio);
        query.setParameter(this.parametroEstadoDocumento, EstadoDocumento.FIRMADO.getClave());

        try {
            id = query.getResultList();
        } catch (Exception e) {
            getLog().error("Error al obtener la solicitud por folio:", e);
        }
        return id;
    }

    @Override
    public void guardarDocumentResolucion(DocumentoResolucion docRes) {

        getEntityManager().persist(docRes);

    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<DocumentoResolucion> obtenerDocumentoResolucionPorIdRes(Long idResolucion) {

        Query query =
                getEntityManager().createQuery(
                        "SELECT doc from DocumentoResolucion doc " + " where doc.idResolucion = :idResolucion");
        query.setParameter("idResolucion", idResolucion);
        try {
            return query.getResultList();
        } catch (Exception e) {
            getLog().error("Error al obtener el documento por id:", e);
            return null;
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DocumentoSolicitud> obtenerDocumentosPorIdSolicitud(Long idSolicitud) {
        StringBuffer consulta = new StringBuffer();
        consulta.append(" SELECT doc FROM DocumentoSolicitud doc ");
        consulta.append("WHERE doc.idSolicitud = :idSolicitud ");
        Query query = getEntityManager().createQuery(consulta.toString());
        query.setParameter("idSolicitud", idSolicitud);
        try {
            return query.getResultList();
        }
        catch (Exception e) {
            return null;
        }

    }

	@Override
	public List<DocumentoOficial> obtenerDocumentoOficialTipo(String idtramite, String idTipoDocume0toOficial) {
		StringBuffer queryConsulta = new StringBuffer();
		queryConsulta.append("SELECT do FROM DocumentoOficial do ");
		queryConsulta.append("WHERE do.tramite.numeroAsunto = :idTramite and do.ideTipoDocOficial =:idTipoDocume0toOficial");
		Query query = getEntityManager().createQuery(queryConsulta.toString());
		query.setParameter("idTramite", idtramite);
		query.setParameter("idTipoDocume0toOficial", idTipoDocume0toOficial);
		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<DocumentoOficial>();
		}
		
	}

	

}
