/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.PersonaDao;
import mx.gob.sat.siat.juridica.base.dao.TramiteDao;
import mx.gob.sat.siat.juridica.base.dao.UnidadAdministrativaDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.service.EnviarCorreoService;
import mx.gob.sat.siat.juridica.ca.util.helper.EnvioCorreosHelper;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.DatosCorreoNotificacion;
import mx.gob.sat.siat.juridica.rrl.dao.RegistroRecursoRevocacionDAO;
import mx.gob.sat.siat.juridica.rrl.dao.SolicitudDAO;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.MailRequestSender;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.model.MailMessageRequest;
import mx.sat.siat.juridica.bpm.constant.CorreoConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * 
 * @author softtek
 * 
 */
@Service("enviarCorreoService")
public class EnviarCorreoServiceImpl extends BaseSerializableBusinessServices implements EnviarCorreoService {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -7943289009482317863L;
    public static final String SUBJECT = "SUBJECT";
    public static final String TEMPLATE = "TEMPLATE";
    public static final String NOMBRE_CONTRIBUYENTE = "nombreContribuyente";
    public static final String LOGO_SAT = "logosat";
    public static final String LOGO_HACIENDA = "logoHacienda";

    public static final String RUTA_LOGO_SAT = File.separator + "resources" + File.separator + "css" + File.separator
            + "images" + File.separator + "SAT.jpg";
    public static final String RUTA_LOGO_HACIENDA = File.separator + "resources" + File.separator + "css"
            + File.separator + "images" + File.separator + "SHCP.jpg";
    public static final String PETICION_CORREO = "Se envio la peticion de correo.";

    /**
     * Atributo "personaDao" tipo PersonaDao
     */
    @Autowired
    private PersonaDao personaDao;

    @Autowired
    private RegistroRecursoRevocacionDAO registroRecursoRevocacionDAO;

    /**
     * Atributo "tramiteDao" tipo TramiteDao
     */
    @Autowired
    private TramiteDao tramiteDao;

    /**
     * Atributo "envioCorreosHelper" tipo EnvioCorreosHelper
     */
    @Autowired
    private EnvioCorreosHelper envioCorreosHelper;

    /**
     * Atributo "mailRequestSender" tipo MailRequestSender
     */
    @Autowired
    private MailRequestSender mailRequestSender;

    @Autowired
    private UnidadAdministrativaDao unidadAdministrativaDao;

    @Autowired
    private SolicitudDAO solicitudDao;
    
    @Value("${reproceso.mail.destinatario}")
    private String mailReprocesoDestinatario;
    
    @Value("${reproceso.mail.asunto}")
    private String mailReprocesoAsunto;

    /**
     * Metodo para enviar un correo
     */
    public void enviarCorreo(String numeroAsunto, String rfc, String tarea) {
        MailMessageRequest request =
                new MailMessageRequest(new HashSet<String>(), SUBJECT, TEMPLATE, new HashMap<String, String>());
        Persona persona = personaDao.obtenerPersonaPorRFC(rfc);
        Tramite tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        Solicitante sol = registroRecursoRevocacionDAO.getSolicituanteByRfc(tramite.getSolicitud().getIdSolicitud());
        HashSet<String> destinatarios = new HashSet<String>();
        destinatarios.add(persona.getCorreoElectronico());
        request.setDestinations(destinatarios);

        request.setParams(envioCorreosHelper.correoAvisoTareaPendiente(numeroAsunto, persona));

        if (sol.getRazonSocial() != null && !sol.getRazonSocial().equals("")) {
            request.getParams().put(NOMBRE_CONTRIBUYENTE, sol.getRazonSocial());
        }
        else {
            request.getParams().put(NOMBRE_CONTRIBUYENTE,
                    sol.getNombre() + " " + sol.getApellidoPaterno() + " " + sol.getApellidoMaterno());
        }

        Map<String, String> imagenes = new HashMap<String, String>();
        imagenes.put(LOGO_SAT, RUTA_LOGO_SAT);

        imagenes.put(LOGO_HACIENDA, RUTA_LOGO_HACIENDA);

        request.setImagenes(imagenes);

        request.getParams().put("rfcContribuyente", sol.getRfc());
        request.getParams().put("nombreTarea", tarea);
        request.setSubject("Aviso de Tarea Pendiente");
        request.setTemplate(CorreoConstant.CORREO_TAREA_PENDIENTE);
        mailRequestSender.sendMailRequest(request);

    }

    /**
     * Metodo para enviar un correo
     * 
     * @param numeroAsunto
     * @param rfc
     */
    @Override
    public void enviarCorreoTareaPendiente(String numeroAsunto, String rfc, String tarea) {
        MailMessageRequest request =
                new MailMessageRequest(new HashSet<String>(), SUBJECT, TEMPLATE, new HashMap<String, String>());
        Persona persona = personaDao.obtenerPersonaPorRFC(rfc);
        Tramite tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        Solicitante sol = registroRecursoRevocacionDAO.getSolicituanteByRfc(tramite.getSolicitud().getIdSolicitud());
        HashSet<String> destinatarios = new HashSet<String>();
        destinatarios.add(persona.getCorreoElectronico());
        request.setDestinations(destinatarios);
        request.setParams(envioCorreosHelper.correoAvisoTareaPendiente(numeroAsunto, persona));
        request.setSubject("Aviso de Tarea Pendiente");
        request.setTemplate(CorreoConstant.CORREO_TAREA_PENDIENTE);
        if (sol.getRazonSocial() != null && !sol.getRazonSocial().equals("")) {
            request.getParams().put(NOMBRE_CONTRIBUYENTE, sol.getRazonSocial());
        }
        else {
            request.getParams().put(NOMBRE_CONTRIBUYENTE,
                    sol.getNombre() + " " + sol.getApellidoPaterno() + " " + sol.getApellidoMaterno());
        }

        Map<String, String> imagenes = new HashMap<String, String>();
        imagenes.put(LOGO_SAT, RUTA_LOGO_SAT);

        imagenes.put(LOGO_HACIENDA, RUTA_LOGO_HACIENDA);
        request.setImagenes(imagenes);

        request.getParams().put("rfcContribuyente", sol.getRfc());
        request.getParams().put("nombreTarea", tarea);
        mailRequestSender.sendMailRequest(request);
        getLogger().debug(PETICION_CORREO);
    }

    /**
     * Metodo para enviar un correo
     * 
     * @param numeroAsunto
     * @param rfc
     */
    @Override
    public void enviarCorreoAdministracion(String numeroAsunto, String admin) {
        MailMessageRequest request =
                new MailMessageRequest(new HashSet<String>(), SUBJECT, TEMPLATE, new HashMap<String, String>());

        UnidadAdministrativa unidadAdmin = unidadAdministrativaDao.obtenerUnidadPorId(admin);
        HashSet<String> destinatarios = new HashSet<String>();
        if (unidadAdmin != null && unidadAdmin.getCorreoElectronico() != null) {
            destinatarios.add(unidadAdmin.getCorreoElectronico());
            request.setDestinations(destinatarios);
            request.setParams(envioCorreosHelper.correoAutoridad(numeroAsunto));
            request.setSubject("AVISO DE RECEPCI\u00D3N DE PROMOCI\u00D3N");
            request.setTemplate(CorreoConstant.CORREO_AUTORIDAD);

            Map<String, String> imagenes = new HashMap<String, String>();
            imagenes.put(LOGO_SAT, RUTA_LOGO_SAT);

            imagenes.put(LOGO_HACIENDA, RUTA_LOGO_HACIENDA);
            request.setImagenes(imagenes);
            mailRequestSender.sendMailRequest(request);
            getLogger().debug(PETICION_CORREO);
        }
    }

    /**
     * Metodo para enviar un correo
     * 
     * @param numeroAsunto
     * @param rfc
     */
    @Override
    public void enviarCorreoDocAdicionalesAdministracion(String numeroAsunto, String admin) {
        MailMessageRequest request =
                new MailMessageRequest(new HashSet<String>(), SUBJECT, TEMPLATE, new HashMap<String, String>());

        UnidadAdministrativa unidadAdmin = unidadAdministrativaDao.obtenerUnidadPorId(admin);
        Tramite tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        Solicitante sol = registroRecursoRevocacionDAO.getSolicituanteByRfc(tramite.getSolicitud().getIdSolicitud());
        Persona persona = personaDao.obtenerPersonaPorRFC(sol.getRfc());
        if (persona != null) {
            HashSet<String> destinatarios = new HashSet<String>();
            if (unidadAdmin != null && unidadAdmin.getCorreoElectronico() != null) {
                destinatarios.add(unidadAdmin.getCorreoElectronico());
                request.setDestinations(destinatarios);
                request.setParams(envioCorreosHelper.correoRecepcionDocumentos(numeroAsunto, persona));
                request.setSubject("CORREO DE AVISO DE RECEPCI\u00D3N DE DOCUMENTOS");
                request.setTemplate(CorreoConstant.CORREO_RECEPCION_DOCUMENTOS);

                Map<String, String> imagenes = new HashMap<String, String>();
                imagenes.put(LOGO_SAT, RUTA_LOGO_SAT);

                imagenes.put(LOGO_HACIENDA, RUTA_LOGO_HACIENDA);
                request.setImagenes(imagenes);
                mailRequestSender.sendMailRequest(request);
                getLogger().debug(PETICION_CORREO);
            }
        }
    }

    /**
     * Metodo para enviar un correo
     * 
     * @param numeroAsunto
     * @param rfc
     */
    @Override
    public void enviarCorreoDocAdicionalesAbogado(String numeroAsunto, String rfc) {
        MailMessageRequest request =
                new MailMessageRequest(new HashSet<String>(), SUBJECT, TEMPLATE, new HashMap<String, String>());

        Persona persona = personaDao.obtenerPersonaPorRFC(rfc);
        HashSet<String> destinatarios = new HashSet<String>();
        if (persona != null && persona.getCorreoElectronico() != null) {
            destinatarios.add(persona.getCorreoElectronico());
            request.setDestinations(destinatarios);
            request.setParams(envioCorreosHelper.correoAutoridad(numeroAsunto));
            request.setSubject("AVISO DE RECEPCI\u00D3N DE PROMOCI\u00D3N");
            request.setTemplate(CorreoConstant.CORREO_AUTORIDAD);

            Map<String, String> imagenes = new HashMap<String, String>();
            imagenes.put(LOGO_SAT, RUTA_LOGO_SAT);

            imagenes.put(LOGO_HACIENDA, RUTA_LOGO_HACIENDA);
            request.setImagenes(imagenes);
            mailRequestSender.sendMailRequest(request);
            getLogger().debug(PETICION_CORREO);
        }
    }

    public void enviarCorreoNotificacionActualizarMovimiento(DatosCorreoNotificacion datosCorreo) {
        MailMessageRequest request =
                new MailMessageRequest(new HashSet<String>(), SUBJECT, TEMPLATE, new HashMap<String, String>());
        HashSet<String> destinatarios = new HashSet<String>();
        for (String para : datosCorreo.getCorreos()) {
            destinatarios.add(para);
        }
        request.setDestinations(destinatarios);
        request.setParams(envioCorreosHelper.correoNotificacionActualizacionMovimientos(datosCorreo));

        Map<String, String> imagenes = new HashMap<String, String>();
        imagenes.put(LOGO_SAT, RUTA_LOGO_SAT);
        imagenes.put(LOGO_HACIENDA, RUTA_LOGO_HACIENDA);
        request.setImagenes(imagenes);

        request.setSubject("Notificaci\u00f3n de Movimiento de Usuario");
        request.setTemplate(CorreoConstant.CORREO_ACTUALIZACION_USUARIOS);
        mailRequestSender.sendMailRequest(request);

    }

    @Override
    public void enviarCorreoCumplimentacionRequerimiento(Requerimiento requerimiento, String unidadAdmin,
            String numAsunto) {
        MailMessageRequest request =
                new MailMessageRequest(new HashSet<String>(), SUBJECT, TEMPLATE, new HashMap<String, String>());
        HashSet<String> destinatarios = new HashSet<String>();
        Persona persona = personaDao.obtenerPersonaPorRFC(requerimiento.getIdUsuario());
        if (persona.getCorreoElectronico() != null) {
            destinatarios.add(persona.getCorreoElectronico());

            UnidadAdministrativa unidadAdministrativa = unidadAdministrativaDao.obtenerUnidadPorId(unidadAdmin);

            request.setDestinations(destinatarios);
            request.setParams(envioCorreosHelper.correoCumplimentacionRequerimiento(requerimiento,
                    envioCorreosHelper.concatenarNombreFuncionario(persona), unidadAdministrativa.getNombreUnidad(),
                    numAsunto));

            Map<String, String> imagenes = new HashMap<String, String>();
            imagenes.put(LOGO_SAT, RUTA_LOGO_SAT);
            imagenes.put(LOGO_HACIENDA, RUTA_LOGO_HACIENDA);
            request.setImagenes(imagenes);

            request.setSubject("AVISO DE CUMPLIMENTACI\u00D3N DE REQUERIMIENTO");
            request.setTemplate(CorreoConstant.CORREO_CUMPLIMENTACION_REQUERIMIENTO);
            mailRequestSender.sendMailRequest(request);
        }

    }

    @Override
    public void enviarCorreoRechazoAsunto(String rfc, String numAsunto, Long idSolicitud, String elemento,
            Date fechaRecepcion, String nombreRechazo) {
        MailMessageRequest request =
                new MailMessageRequest(new HashSet<String>(), SUBJECT, TEMPLATE, new HashMap<String, String>());
        HashSet<String> destinatarios = new HashSet<String>();
        Persona persona = personaDao.obtenerPersonaPorRFC(rfc);
        if (persona != null) {
            destinatarios.add(persona.getCorreoElectronico());

            Persona personaRechazo = personaDao.obtenerPersonaPorRFC(nombreRechazo);
            SolicitudDatosGenerales datosGenerales = solicitudDao.obtenerSolicitudDatosGeneralesPorId(idSolicitud);

            UnidadAdministrativa unidadAdministrativa =
                    unidadAdministrativaDao.obtenerUnidadPorId(datosGenerales.getUnidadAdminBalanceo());

            request.setDestinations(destinatarios);
            request.setParams(envioCorreosHelper.correoRechazoAsunto(
                    envioCorreosHelper.concatenarNombreFuncionario(persona), unidadAdministrativa.getNombre(),
                    numAsunto, elemento, fechaRecepcion, envioCorreosHelper.concatenarNombreFuncionario(personaRechazo)));

            Map<String, String> imagenes = new HashMap<String, String>();
            imagenes.put(LOGO_SAT, RUTA_LOGO_SAT);
            imagenes.put(LOGO_HACIENDA, RUTA_LOGO_HACIENDA);
            request.setImagenes(imagenes);

            request.setSubject("AVISO DE RECHAZO DE ASUNTO");
            request.setTemplate(CorreoConstant.CORREO_RECHAZO_ASUNTO);
            mailRequestSender.sendMailRequest(request);
        }

    }
    
    public void enviarCorreoEstatusReproceso(List<TramiteReprocesar> tramites) {
        MailMessageRequest request =
                new MailMessageRequest(new HashSet<String>(), SUBJECT, TEMPLATE, new HashMap<String, String>());
    
        HashSet<String> destinatario = new HashSet<String>(Arrays.asList(mailReprocesoDestinatario.split(",")));
        destinatario.add(mailReprocesoDestinatario);
        request.setDestinations(destinatario);

        Map<String, String> imagenes = new HashMap<String, String>();
        imagenes.put(LOGO_SAT, RUTA_LOGO_SAT);
        imagenes.put(LOGO_HACIENDA, RUTA_LOGO_HACIENDA);
        request.setImagenes(imagenes);

        request.setSubject(mailReprocesoAsunto);
        request.setParams(envioCorreosHelper.correoTramitesReprocesados(tramites));
        request.setTemplate(CorreoConstant.CORREO_REPROCESO_TRAMITES);
        mailRequestSender.sendMailRequest(request);
    }

}
