/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.service.impl;

import mx.gob.sat.siat.juridica.base.dao.EnumeracionDao;
import mx.gob.sat.siat.juridica.base.dao.PersonaDao;
import mx.gob.sat.siat.juridica.base.dao.UnidadAdministrativaDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.ca.dao.RegistroSolicitudConsultaAutorizacionDAO;
import mx.gob.sat.siat.juridica.ca.dao.domain.model.SolicitudConsultaAutorizacion;
import mx.gob.sat.siat.juridica.ca.service.RegistroSolicitudConsultaAutorizacionServices;
import mx.gob.sat.siat.juridica.ca.util.helper.GenerarCadenasHelper;
import mx.gob.sat.siat.juridica.ca.util.helper.RegistroSolicitudConsultaAutorizacionHelper;
import mx.gob.sat.siat.juridica.ca.util.validador.RegistroSolicitudConsultaAutorizacionValidator;
import mx.gob.sat.siat.juridica.rrl.service.RegistroRecursoRevocacionServices;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Servicio para atender las peticiones del registro de la solicitud.
 * 
 * @author Softtek
 * 
 */
@Service("registroSolicitudConsultaAutorizacionServices")
public class RegistroSolicitudConsultaAutorizacionServicesImpl extends BaseSerializableBusinessServices implements
        RegistroSolicitudConsultaAutorizacionServices {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = -2146798797163477960L;

    /**
     * Atributo privado "registroSolicitudConsultaAutorizacionDAO"
     * tipo RegistroSolicitudConsultaAutorizacionDAO
     */
    @Autowired
    private RegistroSolicitudConsultaAutorizacionDAO registroSolicitudConsultaAutorizacionDAO;

    /**
     * Atributo privado
     * "registroSolicitudConsultaAutorizacionValidator" tipo
     * RegistroSolicitudConsultaAutorizacionValidator
     */
    @Autowired
    private RegistroSolicitudConsultaAutorizacionValidator registroSolicitudConsultaAutorizacionValidator;

    /**
     * Atributo privado "RegistroSolicitudConsultaAutorizacionHelper"
     * tipo registroSolicitudConsultaAutorizacionHelper
     */
    @Autowired
    private RegistroSolicitudConsultaAutorizacionHelper registroSolicitudConsultaAutorizacionHelper;

    /**
     * DAO para atender las solicitudes a BD de las unidades
     * administrativas
     */
    @Autowired
    private UnidadAdministrativaDao unidadAdministrativaDao;

    /**
     * Atributo privado "EnumeracionDao" tipo enumeracionDao
     */
    @Autowired
    private EnumeracionDao enumeracionDao;

    /**
     * Atributo privado "PersonaDao" tipo personaDao
     */
    @Autowired
    private PersonaDao personaDao;

    /**
     * Atributo privado "generarCadenasHelper" tipo
     * generarCadenasHelper
     */
    @Autowired
    private GenerarCadenasHelper generarCadenasHelper;

    /**
     * Atributo privado "registroRecursoRevocacionServices" tipo
     * RegistroRecursoRevocacionServices
     */
    @Autowired
    private RegistroRecursoRevocacionServices registroRecursoRevocacionServices;

    /**
     * Metodo par aobtener una lista de unidades administrativas
     * emisoras
     * 
     * @return List<UnidadAdministrativa>
     */
    public List<UnidadAdministrativa> obtenerAutoridadesEmisoras() {
        return unidadAdministrativaDao.obtenerUnidadesAdministrativas();
    }

    /**
     * Metodo para obtener una lista de catalogos
     * 
     * @return List<Catalogo>
     */
    public List<Catalogo> obtenerListaSiNo() {
        return registroSolicitudConsultaAutorizacionHelper.obtenerCatalogoSiNo();
    }

    /**
     * Metodo para obtener una enumeracion por id
     * 
     * @param ideEnumeracionH
     * @return List<EnumeracionTr>
     */
    public List<EnumeracionTr> obtenerEnumeracionPorId(String ideEnumeracionH) {
        return enumeracionDao.obtenerEnumeracionPorId(ideEnumeracionH);
    }

    /**
     * Metodo para obtener una lista de modalidades de tramite por
     * subservicio
     * 
     * @param idSubservicio
     * @return List<TipoTramite>
     */
    public List<TipoTramite> obtenerModalidadesPorSubservicio(String idSubservicio, String tipoPerosna) {
        List<TipoTramite> modalidades =
                registroSolicitudConsultaAutorizacionDAO.obtenerModalidadesPorSubservicio(idSubservicio, tipoPerosna);
        List<TipoTramite> modalidadesReturn = modalidades;
        if (modalidades != null) {
            modalidadesReturn = new LinkedList<TipoTramite>();
            // Elimina la modalidad de control para generar las
            // secuencias (20200)
            for (TipoTramite modalidad : modalidades) {
                if (!modalidad.getIdTipoTramite().toString()
                        .equals(DiscriminadorConstants.T2_MODALIDAD_SECUENCIA_AUTORIZACIONES)) {
                    modalidadesReturn.add(modalidad);
                }
            }
        }
        return modalidadesReturn;
    }

    /**
     * Metodo para obtener una lista de tipos de documento por tramite
     * 
     * @param idTipoTramite
     * @param opcional
     * @return List<DocumentoTramite>
     */
    public List<DocumentoTramite> obtenerTiposDocumentosPorTramite(Integer idTipoTramite, Integer opcional) {
        return registroSolicitudConsultaAutorizacionDAO.obtenerTiposDocumentosPorTramite(idTipoTramite, opcional);
    }

    /**
     * Metodo para obtener una persona por RFC
     * 
     * @param rfc
     * @return Persona
     */
    public Persona obtenerPersonaPorRFC(String rfc) {
        return personaDao.obtenerPersonaPorRFC(rfc);
    }

    /**
     * Metodo para validar la existencia de una persona
     * 
     * @param persona
     * @throws SolicitudNoGuardadaException
     */
    public void validarPersonaExistente(Persona persona) throws SolicitudNoGuardadaException {
        registroSolicitudConsultaAutorizacionValidator.validarSolicitante(persona);
    }

    /**
     * Metodo para guardar una solicitud
     * 
     * @param solicitud
     * @return SolicitudConsultaAutorizacion
     * @throws SolicitudNoGuardadaException
     */
    public SolicitudConsultaAutorizacion guardarSolicitud(SolicitudConsultaAutorizacion solicitud)
            throws SolicitudNoGuardadaException {
        MedioDefensa medioDefensa = solicitud.getMedioDefensaHelper();
        Solicitante solicitante = solicitud.getSolicitanteHelper();
        PersonaOirRecibirNotificaciones personaOirRN = solicitud.getPersonaORNHelper();
        PersonaResidenteExtranjero resideteExtranjero = solicitud.getPersonaExtranjeroHelper();

        Persona persona = obtenerPersonaPorRFC(solicitud.getSolicitanteHelper().getRfc());
        registroSolicitudConsultaAutorizacionValidator.validarUnidadAdministrativaSolicitud(solicitud
                .getUnidadAdminBalanceo());
        solicitud.setIdPersonaSolicitante(persona.getIdPersona());
        SolicitudConsultaAutorizacion solicitudNueva =
                registroSolicitudConsultaAutorizacionDAO.crearSolicitud(solicitud);

        if (medioDefensa != null) {
            medioDefensa.setIdSolicitud(solicitudNueva.getIdSolicitud());
            registroSolicitudConsultaAutorizacionDAO.crearMedioDefensa(medioDefensa);
        }
        solicitante.setIdSolicitud(solicitudNueva.getIdSolicitud());
        registroSolicitudConsultaAutorizacionDAO.crearPersonaSolicitud(solicitante);
        if (personaOirRN != null) {
            personaOirRN.setIdSolicitud(solicitudNueva.getIdSolicitud());
            registroSolicitudConsultaAutorizacionDAO.crearPersonaSolicitud(personaOirRN);
        }
        if (resideteExtranjero != null) {
            resideteExtranjero.setIdSolicitud(solicitudNueva.getIdSolicitud());
            registroSolicitudConsultaAutorizacionDAO.crearPersonaSolicitud(resideteExtranjero);
        }

        return solicitudNueva;
    }

    /**
     * Metodo para descargar un archivo
     * 
     * @param ruta
     * @return InputStream
     */
    public InputStream descargarArchivo(String ruta) {
        return registroSolicitudConsultaAutorizacionHelper.descargarArchivo(ruta);
    }

    /**
     * Metodo para validar documentos
     * 
     * @param tipoTramite
     * @param documentos
     * @throws ArchivoNoGuardadoException
     */
    public void validarDocumentos(String tipoTramite, List<Documento> documentos) throws ArchivoNoGuardadoException {
        List<DocumentoTramite> documentosObligatorios =
                obtenerTiposDocumentosPorTramite(Integer.parseInt(tipoTramite), 1);
        registroSolicitudConsultaAutorizacionValidator.validarDocumentosPorAnexar(documentos, documentosObligatorios);

    }

    /**
     * Metodo para guardar documentos
     * 
     * @param listaDocumentos
     * @param listaDocumentosSolicitud
     */
    public void guardarDocumentos(List<Documento> listaDocumentos, List<DocumentoSolicitud> listaDocumentosSolicitud) {

        registroRecursoRevocacionServices.guardaDocumento(listaDocumentos, listaDocumentosSolicitud);
    }

    /**
     * Metodo para generar una cadena
     * 
     * @param idSolicitud
     * @param fechaFirma
     * @return
     */
    public String generaCadenaOriginal(long idSolicitud, Date fechaFirma) {
        return generarCadenasHelper.generarCadenasRegistroSolicitud(idSolicitud, fechaFirma);

    }

    /**
     * Metodo para validar un documento anexado
     * 
     * @param documento
     * @param listaDocumentos
     * @param tamanioArchivo
     * @throws ArchivoNoGuardadoException
     */
    public void validarDocumentosAnexar(Documento documento, List<Documento> listaDocumentos, long tamanioArchivo)
            throws ArchivoNoGuardadoException {
        registroSolicitudConsultaAutorizacionValidator.validarNoDuplicidadDocumento(documento, listaDocumentos);
        registroSolicitudConsultaAutorizacionValidator.validarTamanioMaximo(documento, tamanioArchivo);
    }

    @Override
    public List<Catalogo> obtenerTipoPersona() {
        return registroSolicitudConsultaAutorizacionHelper.obtenerTipoPersona();
    }

    @Override
    public DocumentoTramite obtenerDocumentoRazonesLogicoJuridicas() {
        return registroSolicitudConsultaAutorizacionDAO.obtenerDocumentoRazonesLogicoJuridicas();
    }

}
