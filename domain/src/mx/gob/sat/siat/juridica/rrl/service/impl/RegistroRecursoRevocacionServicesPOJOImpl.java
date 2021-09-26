/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service.impl;

import mx.gob.sat.siat.juridica.base.dao.PersonaDao;
import mx.gob.sat.siat.juridica.base.dao.RequerimientoDao;
import mx.gob.sat.siat.juridica.base.dao.UnidadAdministrativaDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Pais;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.rrl.dao.RegistroRecursoRevocacionDAO;
import mx.gob.sat.siat.juridica.rrl.dao.domain.model.SolicitudRecursoRevocacion;
import mx.gob.sat.siat.juridica.rrl.service.RegistroRecursoRevocacionServices;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import mx.gob.sat.siat.juridica.rrl.util.validador.RegistroSolicitudValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Service
public class RegistroRecursoRevocacionServicesPOJOImpl extends BaseSerializableBusinessServices implements
        RegistroRecursoRevocacionServices {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -7943289009482317863L;

    /**
     * Atributo registroRecursoRevocacionDAO tipo
     * RegistroRecursoRevocacionDAO
     */
    @Autowired
    private RegistroRecursoRevocacionDAO registroRecursoRevocacionDAO;

    /**
     * Atributo registroSolicitudValidator tipo
     * RegistroSolicitudValidator
     */
    @Autowired
    private RegistroSolicitudValidator registroSolicitudValidator;

    /**
     * Atributo personaDao tipo PersonaDao
     */
    @Autowired
    private PersonaDao personaDao;

    /**
     * Atributo requerimientoDao tipo RequerimientoDao
     */
    @Autowired
    private RequerimientoDao requerimientoDao;
    
    @Autowired
    private UnidadAdministrativaDao unidadesDao;

    /**
     * Metodo para obtener una lista de documentos por tipo de tramite
     * 
     * @param idTipoTramite
     * @param opcional
     * @return Lista
     */
    @Override
    public List<DocumentoTramite> obtenerDocumentosPorTipoTramite(Integer idTipoTramite, Integer opcional) {
        getLogger().debug("RegistroSolicitudServicesPOJOImpl: obtenerDocumentosPorTipoTramite");

        return registroRecursoRevocacionDAO.buscarDocumentosTramitePorTipoTramite(idTipoTramite, opcional);
    }

    /**
     * Metodo para obtener datos de idc por rfc
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
        solicitante.setRfc(rfc);

        domicilio.setCalle("23");
        domicilio.setNumeroExterior("3");
        domicilio.setNumeroInterior("5");
        domicilio.setTelefono("234512765");
        solicitante.setDomicilio(domicilio);

        solicitante.setCorreoElectronico("123@liv.com");

        return solicitante;

    }

    /**
     * Metodo para guardar una solicitud de recurso de revocacion
     * 
     * @param solicitud
     * @param solicitante
     * @return Solicitante
     */
    @Override
    public SolicitudRecursoRevocacion guardarSolicitud(SolicitudRecursoRevocacion solicitud, Solicitante solicitante)
            throws SolicitudNoGuardadaException {
        SolicitudRecursoRevocacion solicitudNueva = null;
        String mensajeError = "Error al guardar la solicitud";
        if (solicitud != null) {
            Persona persona = personaDao.obtenerPersonaPorRFC(solicitud.getCveUsuarioCapturista());
            if (solicitud.getIdSolicitud() == null && solicitud.getUnidadAdministrativaRepresentacionFederal() != null) {
                try {
                    // guardar la solicitud
                    solicitud.setIdPersonaSolicitante(persona.getIdPersona());
                    solicitud.setFechaCreacion(new Date());
                    solicitud.setFechaEstatus(new Date());
                    solicitud.setFechaActualizacion(new Date());
                    solicitud.setFechaInicioTramite(new Date());
                    solicitud.setCondicionesUso(true);
                    solicitud.setUnidadAdminBalanceo(solicitud.getUnidadAdministrativaRepresentacionFederal()
                            .getClave());
                    solicitud.setEstadoSolicitud(EstadoSolicitud.REGISTRADA);
                    solicitud.setCveUsuarioCapturista(solicitante.getRfc());
                    solicitudNueva = registroRecursoRevocacionDAO.crearSolicitud(solicitud);
                    solicitante.setIdSolicitud(solicitudNueva.getIdSolicitud());
                    solicitante.setRazonSocial(persona.getRazonSocial());
                    registroRecursoRevocacionDAO.crearSolicitante(solicitante);
                }
                catch (RuntimeException e) {

                    getLogger().error(mensajeError, e);
                    throw new SolicitudNoGuardadaException(mensajeError, e);
                }
            }
            else if (solicitud.getUnidadAdministrativaRepresentacionFederal() != null) {
                try {
                    solicitud.setIdPersonaSolicitante(persona.getIdPersona());
                    solicitud.setFechaEstatus(new Date());
                    solicitud.setFechaActualizacion(new Date());
                    solicitud.setCveUsuarioCapturista(solicitante.getRfc());
                    solicitud.setUnidadAdminBalanceo(solicitud.getUnidadAdministrativaRepresentacionFederal()
                            .getClave());
                    solicitud.setEstadoSolicitud(EstadoSolicitud.REGISTRADA);
                    solicitud.setCveUsuarioCapturista(solicitante.getRfc());
                    solicitudNueva = registroRecursoRevocacionDAO.crearSolicitud(solicitud);
                }
                catch (RuntimeException e) {
                    getLogger().error(mensajeError, e);
                    throw new SolicitudNoGuardadaException(mensajeError, e);
                }
            }
        }
        return solicitudNueva;
    }

    /**
     * Metodo para obtener un catalogo de unidades administrativas
     * 
     * @return Lista
     */
    @Override
    public List<UnidadAdministrativa> obtenerCatalogo() {

        return unidadesDao.obtenerUnidAdmvasTodas();
    }

    /**
     * Metodo para generar fecha
     */
    @Override
    public Date generarFecha() {

        return new Date();
    }

    /**
     * Metodo para descargar un documento
     * 
     * @param ruta
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
     * Metodo para guardar documentos de solicitud
     * 
     * @param DocumentoSol
     */
    @Override
    public void guardarDocumentosSolicitud(DocumentoSolicitud documentoSol) {

    }

    /**
     * Metodo para obtener una persona por RFC
     * 
     * @param rfc
     * @return Persona
     */
    @Override
    public Persona obtenerPersonaPorRFC(String rfc) {

        return personaDao.obtenerPersonaPorRFC(rfc);
    }

    /**
     * Metodo para guardar un documento de requerimiento
     * 
     * @param documento
     * @param documentoSol
     * @param docsReq
     */
    @Override
    public void guardaDocumentoRequerimiento(List<Documento> documento, List<DocumentoSolicitud> documentoSol,
            List<DocumentoSolicitudRequerimiento> docsReq) {
        // TODO Auto-generated method stub
        for (int i = 0; i < documento.size(); i++) {
            if (documento.get(i).getIdDocumento() == null) {
                Documento doc = new Documento();
                DocumentoSolicitud doc2 = new DocumentoSolicitud();
                DocumentoSolicitudRequerimiento docSolRequerimiento = new DocumentoSolicitudRequerimiento();

                doc.setIdTipoDocumento(documento.get(i).getIdTipoDocumento());
                doc.setNombre(documento.get(i).getNombre());
                doc.setHash(documento.get(i).getHash());
                doc.setFechaCreacion(documento.get(i).getFechaCreacion());
                doc.setPersona(documento.get(i).getPersona());
                doc.setUrlDocumento(documento.get(i).getUrlDocumento());
                doc.setEstadoDocumento(EstadoDocumento.ANEXADO.getClave());
                doc.setLongitud(documento.get(i).getLongitud());
                doc.setBlnActivo(1);
                registroRecursoRevocacionDAO.guardaDocumentoSolicitud(doc);
                doc2.setDocumento(doc);

                doc2.setIdTipoDocumento(documentoSol.get(i).getIdTipoDocumento());
                doc2.setFechaAsociacion(documentoSol.get(i).getFechaAsociacion());
                doc2.setIdSolicitud(documentoSol.get(i).getIdSolicitud());
                doc2.setCvePersona(documentoSol.get(i).getCvePersona());
                doc2.setIdDocumento(doc.getIdDocumento());
                doc2.setEstadoDocumentoSolicitud(EstadoDocumento.ANEXADO.getClave());
                doc2.setBlnActivo(1);
                registroRecursoRevocacionDAO.guardaDocumento(doc2);

                docSolRequerimiento.setDocumentoSolicitud(doc2);
                docSolRequerimiento.setTipoDocumento(docsReq.get(i).getTipoDocumento());
                docSolRequerimiento.setBlnActivo(1);
                docSolRequerimiento.setRequerimiento(requerimientoDao.obtenerRequerimientoPorId(docsReq.get(i)
                        .getRequerimiento().getIdRequerimiento()));
                registroRecursoRevocacionDAO.guardaDocumentoSolicitudRequerimiento(docSolRequerimiento);

                doc = null;
                doc2 = null;
                docSolRequerimiento = null;
            }
        }
    }

    /**
     * Metodo para guardar un documento
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
                doc.setEstadoDocumento(EstadoDocumento.ANEXADO.getClave());
                doc.setBlnActivo(1);
                doc.setComplementario(documento.get(i).getComplementario());
                doc.setLongitud(documento.get(i).getLongitud());
                registroRecursoRevocacionDAO.guardaDocumentoSolicitud(doc);
                doc2.setDocumento(doc);

                doc2.setIdTipoDocumento(documentoSol.get(i).getIdTipoDocumento());
                doc2.setFechaAsociacion(documentoSol.get(i).getFechaAsociacion());
                doc2.setIdSolicitud(documentoSol.get(i).getIdSolicitud());
                doc2.setCvePersona(documentoSol.get(i).getCvePersona());
                doc2.setIdDocumento(doc.getIdDocumento());
                doc2.setEstadoDocumentoSolicitud(EstadoDocumento.ANEXADO.getClave());
                doc2.setComplementario(documentoSol.get(i).getComplementario());
                registroRecursoRevocacionDAO.guardaDocumento(doc2);

                doc = null;
                doc2 = null;
            }
        }
    }

    /**
     * Metodo para validar un documento
     * 
     * @param tipoTramite
     * @param documentos
     * @throws ArchivoNoGuardadoException
     */
    @Override
    public void validarDocumentos(String tipoTramite, List<Documento> documentos) throws ArchivoNoGuardadoException {

        Integer idTipoTramite = null;
        if (tipoTramite != null) {
            idTipoTramite = Integer.parseInt(tipoTramite);
        }
        List<DocumentoTramite> listaObligatorios = obtenerDocumentosPorTipoTramite(idTipoTramite, 1);
        registroSolicitudValidator.validarDocumentos(documentos, listaObligatorios);
    }

    /**
     * Metodo para obtener una lista de documentos oficiales por
     * numero de asunto
     * 
     * @param numAsunto
     * @return Lista
     */
    @Override
    public List<DocumentoOficial> obtenerDocumentosOficiales(String numAsunto) {
        return registroRecursoRevocacionDAO.obtenerDocumentosOficiales(numAsunto);
    }

    /**
     * Metodo para obtener una lista de documentos oficiales por ids
     * de documento
     * 
     * @param idsDoc
     * @return Lista
     */
    @Override
    public List<DocumentoOficial> obtenerDocumentosOficialesById(List<Long> idsDoc) {
        List<DocumentoOficial> documentos = new ArrayList<DocumentoOficial>();
        for (Long idDoc : idsDoc) {
            DocumentoOficial doc = registroRecursoRevocacionDAO.obtenerDocumentosOficialesById(idDoc);
            documentos.add(doc);
        }
        return documentos;
    }

    @Override
    public void eliminaDocumento(long idDoc) {
        Documento docEliminar = registroRecursoRevocacionDAO.obtenerDocumentoCompleto(idDoc);
        docEliminar.setBlnActivo(0);
        docEliminar.setEstadoDocumento(EstadoDocumento.ELIMINADO.getClave());
    }

    @Override
    public void eliminaDocumentoOficial(long idDoc) {
        DocumentoOficial docOficialEliminar = registroRecursoRevocacionDAO.obtenerDocumentosOficialesById(idDoc);
        docOficialEliminar.setBlnActivo(0);
    }

    /**
     * Metodo para guardar una solicitud de recurso de revocacion
     * 
     * @param solicitud
     * @param solicitante
     * @return Solicitante
     */
    @Override
    public SolicitudRecursoRevocacion guardarSolicitud(SolicitudRecursoRevocacion solicitud, Solicitante solicitante,
            String rfcCapturista) throws SolicitudNoGuardadaException {
        Persona persona = personaDao.obtenerPersonaPorRFC(rfcCapturista);
        SolicitudRecursoRevocacion solicitudNueva = null;
        if (solicitud != null && solicitud.getIdSolicitud() == null
                && solicitud.getUnidadAdministrativaRepresentacionFederal() != null) {

            // guardar la solicitud
            solicitud.setIdPersonaSolicitante(persona.getIdPersona());
            solicitud.setFechaCreacion(new Date());
            solicitud.setFechaEstatus(new Date());
            solicitud.setFechaActualizacion(new Date());
            solicitud.setFechaInicioTramite(new Date());
            solicitud.setUnidadAdminBalanceo(solicitud.getUnidadAdministrativaRepresentacionFederal().getClave());
            solicitud.setEstadoSolicitud(EstadoSolicitud.REGISTRADA);
            solicitud.setCveUsuarioCapturista(rfcCapturista);
            solicitud.setCondicionesUso(true);
            solicitudNueva = registroRecursoRevocacionDAO.crearSolicitud(solicitud);
            solicitante.setIdSolicitud(solicitudNueva.getIdSolicitud());
            solicitante.getDomicilio().setPais(new Pais("MEX"));
            registroRecursoRevocacionDAO.crearSolicitante(solicitante);

        }
        else if (solicitud != null && solicitud.getUnidadAdministrativaRepresentacionFederal() != null) {

            solicitud.setIdPersonaSolicitante(persona.getIdPersona());
            solicitud.setFechaEstatus(new Date());
            solicitud.setFechaActualizacion(new Date());
            solicitud.setCveUsuarioCapturista(solicitante.getRfc());
            solicitud.setUnidadAdminBalanceo(solicitud.getUnidadAdministrativaRepresentacionFederal().getClave());
            solicitud.setEstadoSolicitud(EstadoSolicitud.REGISTRADA);
            solicitud.setCveUsuarioCapturista(solicitante.getRfc());
            solicitudNueva = registroRecursoRevocacionDAO.crearSolicitud(solicitud);
        }

        return solicitudNueva;
    }

    @Override
    public List<DocumentoOficial> obtenerDocOficialesGenerados(String numAsunto) {

        return registroRecursoRevocacionDAO.obtenerDocumentosAsuntoPromovente(numAsunto);
    }

}
