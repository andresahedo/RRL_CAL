/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.DocumentoDao;
import mx.gob.sat.siat.juridica.base.dao.FirmarDAO;
import mx.gob.sat.siat.juridica.base.dao.NotificacionDao;
import mx.gob.sat.siat.juridica.base.dao.PersonaDao;
import mx.gob.sat.siat.juridica.base.dao.RecursoRevocacionDAO;
import mx.gob.sat.siat.juridica.base.dao.RequerimientoDao;
import mx.gob.sat.siat.juridica.base.dao.ResolucionDao;
import mx.gob.sat.siat.juridica.base.dao.TramiteDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Documento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoResolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DomicilioSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FirmaNotificacion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.NotificacionRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Requerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Resolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitante;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.SolicitudDatosGenerales;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.service.ConsultaSolicitudServices;
import mx.gob.sat.siat.juridica.ca.util.helper.ConsultaAnexarDocumentosHelper;
import mx.gob.sat.siat.juridica.ca.util.helper.GenerarCadenasHelper;
import mx.gob.sat.siat.juridica.rrl.dao.RegistroRecursoRevocacionDAO;
import mx.gob.sat.siat.juridica.rrl.dao.SolicitudDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author softtek
 * 
 */
@Service("consultaSolicitudServices")
public class ConsultaSolicitudServicesImpl extends BaseSerializableBusinessServices implements
        ConsultaSolicitudServices {
    
    private final transient Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * Id de la version
     */
    private static final long serialVersionUID = -1698427240088630978L;

    /**
     * Atributo privado "recursoRevocacionDAO" tipo
     * RecursoRevocacionDAO
     */
    @Autowired
    private RecursoRevocacionDAO recursoRevocacionDAO;

    /**
     * Atributo privado "PersonaDao" tipo personaDao
     */
    @Autowired
    private PersonaDao personaDao;

    @Autowired
    private FirmarDAO firmarDAO;
    /**
     * Atributo privado "PersonaDao" tipo personaDao
     */
    @Autowired
    private ResolucionDao resolucionDao;

    @Autowired
    private DocumentoDao documentoDao;
    /**
     * Atributo privado "registroRecursoRevocacionDAO" tipo
     * RegistroRecursoRevocacionDAO
     */
    @Autowired
    private RegistroRecursoRevocacionDAO registroRecursoRevocacionDAO;

    /**
     * Atributo privado "requerimientoDao" tipo RequerimientoDao
     */
    @Autowired
    private RequerimientoDao requerimientoDao;

    /**
     * Atributo privado "notificacionDao" tipo notificacionDao
     */
    @Autowired
    private NotificacionDao notificacionDao;

    /**
     * Atributo solicitudDAO tipo SolicitudDAO
     */
    @Autowired
    private SolicitudDAO solicitudDAO;
    /**
     * Atributo solicitudDAO tipo SolicitudDAO
     */
    @Autowired
    private TramiteDao tramiteDao;

    /**
     * Atributo privado "consultaAnexarDocumentosHelper" tipo
     * ConsultaAnexarDocumentosHelper
     */
    @Autowired
    private ConsultaAnexarDocumentosHelper consultaAnexarDocumentosHelper;

    @Autowired
    private GenerarCadenasHelper generarCadenasHelper;

    /**
     * Metodo para obtener datos de un solicitante por rfc
     * 
     * @param rfc
     * @return Solicitante
     */
    @Override
    public Solicitante obtenerDatosIdc(String rfc) {
        Solicitante solicitante = new Solicitante();
        DomicilioSolicitud domicilio = new DomicilioSolicitud();
        solicitante.setApellidoMaterno("Perez");
        solicitante.setApellidoPaterno("perez");
        solicitante.setNombre("artur");
        solicitante.setRfc("RAFA860512MR3");

        domicilio.setCalle("23");
        domicilio.setNumeroExterior("3");
        domicilio.setNumeroInterior("5");
        domicilio.setTelefono("234512765");
        solicitante.setDomicilio(domicilio);

        solicitante.setCorreoElectronico("123@liv.com");

        return solicitante;

    }

    /**
     * Metodo para obtener Documentos oficiales
     * 
     * @param numFolio
     * @return lista de documentos oficiales
     */
    @Override
    public List<DocumentoOficial> obtenerDocumentosOficiales(String numFolio) {
        return registroRecursoRevocacionDAO.obtenerDocumentosOficiales(numFolio);
    }

    @Override
    public List<DocumentoOficial> obtenerDocumentosOficialesEmpleado(String numFolio) {
        List<DocumentoOficial> documentosOficiales = registroRecursoRevocacionDAO.obtenerDocumentosOficiales(numFolio);

        return documentosOficiales;
    }

    /**
     * Metodo para obtener Documentos oficiales
     * 
     * @param numFolio
     * @return lista de documentos oficiales
     */
    @Override
    public List<DocumentoOficial> obtenerDocumentosOficialesPromovente(String numFolio, Long idRequerimiento) {
        logger.debug("Documentos Oficiales promovente:");
        Long idReq = idRequerimiento;
        if (idReq == null) {
            Requerimiento req = requerimientoDao.obtenerUltimoRequerimientoPorTramiteRRLCAL(numFolio);
            if (req != null) {
                idReq = req.getIdRequerimiento();
            }
        }
        List<DocumentoOficial> docsRequerimitento =
                requerimientoDao.obtenerDocumentosOficialesPorIdRequerimiento(idReq);
        NotificacionRequerimiento notificacion = notificacionDao.obtenerNotificacionByIdRequerimiento(idReq);
        List<DocumentoOficial> docs = registroRecursoRevocacionDAO.obtenerDocumentosOficialesPromovente(numFolio);
        Resolucion resolucion = resolucionDao.obtenerResolucionIdTramite(numFolio);

        if (resolucion != null) {
            List<DocumentoResolucion> docsResolucion =
                    documentoDao.obtenerDocumentoResolucionPorIdRes(resolucion.getIdResolucion());
            List<DocumentoOficial> docsEliminar = new ArrayList<DocumentoOficial>();
            for (DocumentoResolucion docRes : docsResolucion) {
                if (docRes.getEstado().equals(EstadoDocumento.POR_APROBAR.getClave())) {
                    docsEliminar.add(docRes.getDocumentoOficial());
                }

            }
            docs.removeAll(docsEliminar);

        }
        List<Requerimiento> requerimientosAutoridad = requerimientoDao.obtenerRequermientosAutoridad(numFolio);
        for (Requerimiento requerimientoA : requerimientosAutoridad) {
            List<DocumentoOficial> docsRequerimientoA =
                    requerimientoDao.obtenerDocumentosOficialesPorIdRequerimiento(requerimientoA.getIdRequerimiento());
            docs.removeAll(docsRequerimientoA);
        }
        FirmaNotificacion firmaNotificacion =
                firmarDAO.obtenerFirmaNotificacion(notificacion != null ? notificacion.getIdNotificacion() : null);
        if (firmaNotificacion == null) {
            docs.removeAll(docsRequerimitento);

        }
        List<DocumentoOficial> docsEliminar = new ArrayList<DocumentoOficial>();
        for(DocumentoOficial doc : docs) {
            if(doc.getUrlDocumento() != null) {
                if(doc.getUrlDocumento().substring(NumerosConstantes.CERO, NumerosConstantes.TRECE).equals("autConstancia") || doc.getUrlDocumento().substring(NumerosConstantes.CERO, NumerosConstantes.SIETE).equals("autProm") ) {
                    docsEliminar.add(doc);
                    logger.debug("Se oculta del documento al promovente:"+doc.getUrlDocumento());
                }
            }
        }
        docs.removeAll(docsEliminar);
        return docs;
    }

    /**
     * Metodo para obtener la descripcion de un tipo de documento por
     * id
     * 
     * @param idTipoDoc
     * @return String
     */
    @Override
    public String obtenerDescripcionTipoDoc(String cveTipoDoc) {
        String descripcion = "";
        if (TipoDocumentoOficial.ACUSE_RECEPCION_TRAMITE.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.ACUSE_RECEPCION_TRAMITE.getDescripcion();
        }
        else if (TipoDocumentoOficial.ACUSE_RECEPCION_PROMOCION.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.ACUSE_RECEPCION_PROMOCION.getDescripcion();
        }
        else if (TipoDocumentoOficial.ACUSE_NOTIFICACION.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.ACUSE_NOTIFICACION.getDescripcion();
        }
        else if (TipoDocumentoOficial.AVISE_NOTIFICACION_REQUERIMIENTO.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.AVISE_NOTIFICACION_REQUERIMIENTO.getDescripcion();
        }
        else if (TipoDocumentoOficial.OFICIO_RESOLUCION.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.OFICIO_RESOLUCION.getDescripcion();
        }
        else if (TipoDocumentoOficial.OFICIO_REQUERIMIENTO.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.OFICIO_REQUERIMIENTO.getDescripcion();
        }
        else if (TipoDocumentoOficial.CONSTANCIA_RECEPCION_DOCUMENTO.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.CONSTANCIA_RECEPCION_DOCUMENTO.getDescripcion();
        }
        else if (TipoDocumentoOficial.OFICIO_REMISION.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.OFICIO_REMISION.getDescripcion();
        }
        else if (TipoDocumentoOficial.FORMATO_SOLICITUD_PROMOCION.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.FORMATO_SOLICITUD_PROMOCION.getDescripcion();
        }
        else if (TipoDocumentoOficial.OFICIO_ATENCION_RETRO.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.OFICIO_ATENCION_RETRO.getDescripcion();
        }
        else if (TipoDocumentoOficial.ACTA_NOTIFICACION.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.ACTA_NOTIFICACION.getDescripcion();
        }
        else if (TipoDocumentoOficial.OFICIO_TERMINOS_CONDICIONES.getClave().equals(cveTipoDoc)) {
            descripcion = TipoDocumentoOficial.OFICIO_TERMINOS_CONDICIONES.getDescripcion();
        }

        return descripcion;
    }

    /**
     * Metodo para obtener los datos de un un funcionario por rfc
     * 
     * @param rfc
     * @return PersonaInterna
     */
    @Override
    public PersonaInterna obtenerDatosFuncionarioIdc(String rfc) {
        PersonaInterna personaInterna = new PersonaInterna();

        personaInterna.setApellidoMaterno("Perez");
        personaInterna.setApellidoPaterno("perez");
        personaInterna.setNombre("artur");
        personaInterna.setRfc("RAFA860512MR3");

        return personaInterna;

    }

    /**
     * Metodo para buscar una solicitud
     * 
     * @param idSolicitud
     * @return solRegistro
     */
    @Override
    public SolicitudDatosGenerales buscar(long idSolicitud) {

        SolicitudDatosGenerales solRegistro =
                (SolicitudDatosGenerales) recursoRevocacionDAO.obtenerSolicitudRRPorId(idSolicitud);

        return solRegistro;
    }

    /**
     * Metodo para buscar usuarios
     * 
     * @return Lista de personas
     */
    @Override
    public List<Persona> obtenerUsuarios() {

        return personaDao.obtenerUsuarios();
    }

    /**
     * Metodo para obtener los documentos de un tramite
     * 
     * @param idTipoTramite
     * @param numAsunto
     * @return Lista de documentos del tramite
     */
    @Override
    public List<DocumentoTramite> obtenerDocumentosTramite(String idTipoTramite, String numAsunto) {

        Tramite tramite = registroRecursoRevocacionDAO.buscarTramitePorNumFolio(numAsunto);

        List<DocumentoSolicitud> listaDocumentos =
                registroRecursoRevocacionDAO.obtenerDocumentoSolicitud(tramite.getSolicitud().getIdSolicitud()
                        .toString());
        if (idTipoTramite.startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
            Requerimiento requerimientosActivos =
                    requerimientoDao.obtenerUltimoRequerimientoPorTramiteActivoCAL(numAsunto);
            if (tramite.getEstadoTramite().getClave().equals(EstadoTramite.PROMOVIDO.getClave())
                    || tramite.getEstadoTramite().getClave().equals(EstadoTramite.EN_ESTUDIO.getClave())
                    || tramite.getEstadoTramite().getClave().equals(EstadoTramite.REMITIDO.getClave())) {
                // Se valida si tiene Requerimientos

                if (requerimientosActivos == null) {
                    return registroRecursoRevocacionDAO.buscarDocumentosTramitePorTipoTramite(idTipoTramite);
                }
                else {
                    return null;
                }
            }
        }
        List<Requerimiento> listaRequerimientos = requerimientoDao.obtenerListaRequerimientosPorIdTramite(numAsunto);
        String reglaAnexado=consultaAnexarDocumentosHelper.validaDocumentosDisponibles(tramite, listaRequerimientos,listaDocumentos);
        List<DocumentoTramite> lstDocTra=registroRecursoRevocacionDAO.buscarDocumentosTramitePorTipoTramite(idTipoTramite, reglaAnexado);
        consultaAnexarDocumentosHelper.documentosExclusivoFondo(idTipoTramite,tramite,numAsunto,lstDocTra); 
        return lstDocTra;
    }

    /**
     * Metodo para obtener documentos por tramite
     * 
     * @param idTipoTramite
     * @return Lista de Documento de tramite
     */
    @Override
    public List<DocumentoTramite> obtenerDocumentosTramite(String idTipoTramite) {
        return registroRecursoRevocacionDAO.buscarDocumentosTramitePorTipoTramite(idTipoTramite);
    }

    /**
     * Metodo para obtener los documentos de una solicitud
     * 
     * @param idSol
     * @return Lista de documentos
     */
    @Override
    public List<Documento> obtenerDocumentoSolicitud(String idSol) {
        List<Documento> listaDocumentos = new ArrayList<Documento>();
        List<DocumentoSolicitud> listaDocumentoSolicitud =
                registroRecursoRevocacionDAO.obtenerDocumentoSolicitud(idSol);
        for (DocumentoSolicitud documentoSolicitud : listaDocumentoSolicitud) {
            Documento doc = documentoSolicitud.getDocumento();
            listaDocumentos.add(doc);
        }
        return listaDocumentos;
    }
    
    /**
     * Metodo para obtener los documentos de una solicitud
     * 
     * @param idSol
     * @param idTipoDocumento
     * @return Documento
     */
    @Override
    public Documento obtenerDocumentoSolicitudTipoDoc(String idSol, Integer idTipoDocumento){
        Documento objDocumento=null;
        DocumentoSolicitud objDocumentoSolicitud =registroRecursoRevocacionDAO.obtenerDocumentoSolicitudTipoDoc(idSol,idTipoDocumento);
        if(objDocumentoSolicitud!=null){
            objDocumento = objDocumentoSolicitud.getDocumento();
        }
        return objDocumento;
    }

    /**
     * Metodo para obtener un documento de requerimento por id de
     * solicitud
     * 
     * @param idSol
     * @return
     */
    @Override
    public List<Documento> obtenerDocumentoRequerimientoSolicitud(String idSol) {
        List<Documento> listaDocumentos = new ArrayList<Documento>();
        List<DocumentoSolicitud> listaDocumentoSolicitud =
                registroRecursoRevocacionDAO.obtenerDocumentoSolicitud(idSol);
        for (DocumentoSolicitud documentoSolicitud : listaDocumentoSolicitud) {
            Documento doc = documentoSolicitud.getDocumento();
            listaDocumentos.add(doc);
        }
        return listaDocumentos;
    }

    /**
     * Metodo para obtener los tipos de documentos
     * 
     * @return Lista de los tipos de documento
     */
    @Override
    public List<TipoDocumento> tiposDocumentos() {
        return registroRecursoRevocacionDAO.obtenerListaTipoDocumento();
    }

    /**
     * Metodo para obtener un documento completo
     * 
     * @param idDoc
     * @return Documento
     */
    @Override
    public Documento obtenerDocumentoCompleto(Long idDoc) {
        return registroRecursoRevocacionDAO.obtenerDocumentoCompleto(idDoc);
    }

    /**
     * Metodo para realizar la descarga de un documento
     * 
     * @param ruta
     * @return InputStream
     */
    @Override
    public InputStream descargarDocumento(String ruta) {
        InputStream input = null;
        try {
            File file = new File(ruta);
            input = new FileInputStream(file);
            return input;
        }
        catch (FileNotFoundException e) {
            getLogger().error("", e);
        }
        return input;

    }

    /**
     * Metodo para obtener requerimientos por asunto
     * 
     * @param asunto
     * @return Lista de requerimiento
     */
    @Override
    public List<Requerimiento> obteneRequerimientosPorAsunto(String asunto) {
        return requerimientoDao.obtenerListaRequerimientosPorIdTramite(asunto);
    }

    /**
     * Metodo para obtener documentos oficiales por id de
     * requerimientos
     * 
     * @param idRequerimiento
     * @return Lista de documentos oficiales
     */
    @Override
    public List<DocumentoOficial> obteneDocumentosOficialesPorIdRequerimiento(Long idRequerimiento) {
        return requerimientoDao.obtenerDocumentosOficialesPorIdRequerimiento(idRequerimiento);
    }

    /**
     * Metodo para obtener una notificacion por id de requerimiento
     * 
     * @param idRequerimiento
     * @return NotificacionRequerimiento
     */
    @Override
    public NotificacionRequerimiento obtenerNotificacionPorIdRequerimiento(Long idRequerimiento) {
        return requerimientoDao.obtenerNotificacionPorIdRequerimiento(idRequerimiento);
    }

    /**
     * Metodo para obtener un autorizador por id de requerimiento
     * 
     * @param idRequerimiento
     * @return Persona
     */
    @Override
    public Persona obtenerAutorizadorPorIdRequerimiento(Long idRequerimiento) {
        return requerimientoDao.obtenerAutorizadorPorIdRequerimiento(idRequerimiento);
    }

    /**
     * Metodo para obtener un abogado por id de tramite
     * 
     * @param folioTramite
     * @return Persona
     */
    @Override
    public Persona obtenerAbogadoPorIdTramite(final String folioTramite) {
        return requerimientoDao.obtenerAbogadoPorIdTramite(folioTramite);
    }

    /**
     * Metodo para obtener un documento anexado por id de
     * requerimiento
     * 
     * @param idReq
     * @return Lista de Documento
     */
    @Override
    public List<Documento> obtenerDocumentoAnexadosRequerimiento(long idReq) {

        List<Documento> listaDocumentos = new ArrayList<Documento>();
        List<DocumentoSolicitud> listaDocumentoSolicitud =
                registroRecursoRevocacionDAO.obtenerDocumentoSolicitudByIdReq(idReq);
        for (DocumentoSolicitud documentoSolicitud : listaDocumentoSolicitud) {
            Documento doc = documentoSolicitud.getDocumento();
            listaDocumentos.add(doc);
        }
        return listaDocumentos;
    }

    /**
     * Metodo para obtener una solicitud por id
     * 
     * @param idSolicitud
     * @return Solicitud
     */
    @Override
    public Solicitud obtenerSolicitudporId(Long idSolicitud) {
        getLogger().info("obteniendo solicitud por id {}", idSolicitud);
        return solicitudDAO.obtenerSolicitudporId(idSolicitud);
    }

    @Override
    public String generaCadenaOriginalDocumentosAdicionales(Long idSolicitud, Date fechaFirma) {
        Solicitud sol = solicitudDAO.obtenerSolicitudporId(idSolicitud);
        Tramite tramite = tramiteDao.obtenerTramitePorIdSolicitud(idSolicitud);

        return generarCadenasHelper.generarCadenaDocumentosAdicionales(tramite, sol, fechaFirma);
    }

    @Override
    public void eliminaDocumentosSolicitudRequerimiento(Long idRequerimiento) {
        List<DocumentoSolicitud> listaDocumentoSolicitud =
                registroRecursoRevocacionDAO.obtenerDocumentoSolicitudByIdReq(idRequerimiento);
        if (listaDocumentoSolicitud != null) {
            for (DocumentoSolicitud documentoSolicitud : listaDocumentoSolicitud) {
                documentoSolicitud.setBlnActivo(0);
                documentoSolicitud.setEstadoDocumentoSolicitud(EstadoDocumento.ELIMINADO.getClave());
                Documento doc = documentoSolicitud.getDocumento();
                doc.setBlnActivo(0);
                doc.setEstadoDocumento(EstadoDocumento.ELIMINADO.getClave());
            }
        }
    }

          @Override
          public List<Requerimiento> obtenerRequermientosAutoridad(String numFolio) {
                    return requerimientoDao.obtenerRequermientosAutoridad(numFolio);
          }

}
