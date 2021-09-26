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
import mx.gob.sat.siat.juridica.base.dao.TramiteDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitante;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.ca.util.helper.EnvioCorreosHelper;
import mx.gob.sat.siat.juridica.rrl.dao.RegistroRecursoRevocacionDAO;
import mx.gob.sat.siat.juridica.rrl.service.AsignarTareaCorreoService;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.MailRequestSender;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.model.MailMessageRequest;
import mx.sat.siat.juridica.bpm.constant.CorreoConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 
 * @author softtek
 * 
 */
@Service("asignarTareaCorreo")
public class AsignarTareaCorreoServiceImpl implements AsignarTareaCorreoService {

    private static final String CAMPO_NOMBRE_CONTRIBUYENTE = "nombreContribuyente";
    private static final String DIR_IMAGES = "images";
    private static final String DIR_CSS = "css";
    private static final String DIR_RESOURCES = "resources";

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -7943289009482317863L;

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

    /**
     * Metodo para enviar un correo
     */
    public void enviarCorreo(String numeroAsunto, String rfc, String tarea) {

        MailMessageRequest request =
                new MailMessageRequest(new HashSet<String>(), "SUBJECT", "TEMPLATE", new HashMap<String, String>());
        Persona persona = personaDao.obtenerPersonaPorRFC(rfc);
        Tramite tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        Solicitante sol = registroRecursoRevocacionDAO.getSolicituanteByRfc(tramite.getSolicitud().getIdSolicitud());
        HashSet<String> destinatarios = new HashSet<String>();
        if (persona != null) {
            destinatarios.add(persona.getCorreoElectronico());
            request.setDestinations(destinatarios);

            request.setParams(envioCorreosHelper.correoAvisoTareaPendiente(numeroAsunto, persona));

            if (sol.getRazonSocial() != null && !sol.getRazonSocial().equals("")) {
                request.getParams().put(CAMPO_NOMBRE_CONTRIBUYENTE, sol.getRazonSocial());
            }
            else {
                request.getParams().put(CAMPO_NOMBRE_CONTRIBUYENTE,
                        sol.getNombre() + " " + sol.getApellidoPaterno() + " " + sol.getApellidoMaterno());
            }

            String logoSat =
                    File.separator + DIR_RESOURCES + File.separator + DIR_CSS + File.separator + DIR_IMAGES
                            + File.separator + "SAT.jpg";
            Map<String, String> imagenes = new HashMap<String, String>();
            imagenes.put("logosat", logoSat);
            String logoHacienda =
                    File.separator + DIR_RESOURCES + File.separator + DIR_CSS + File.separator + DIR_IMAGES
                            + File.separator + "SHCP.jpg";
            imagenes.put("logoHacienda", logoHacienda);
            request.setImagenes(imagenes);

            request.getParams().put("rfcContribuyente", sol.getRfc());
            request.getParams().put("nombreTarea", tarea);
            request.setSubject("Aviso de Tarea Pendiente");
            request.setTemplate(CorreoConstant.CORREO_TAREA_PENDIENTE);
            mailRequestSender.sendMailRequest(request);
        }

    }

    /**
     * Metodo para enviar un correo
     */
    public void enviarCorreoDocumentos(String numeroAsunto, String rfc, String tarea) {
        MailMessageRequest request =
                new MailMessageRequest(new HashSet<String>(), "SUBJECT", "TEMPLATE", new HashMap<String, String>());
        Persona persona = personaDao.obtenerPersonaPorRFC(rfc);
        Tramite tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        Solicitante solicitante =
                registroRecursoRevocacionDAO.getSolicituanteByRfc(tramite.getSolicitud().getIdSolicitud());
        HashSet<String> destinatarios = new HashSet<String>();
        destinatarios.add(persona.getCorreoElectronico());
        request.setDestinations(destinatarios);

        request.setParams(envioCorreosHelper.correoAvisoTareaPendiente(numeroAsunto, persona));

        if (solicitante.getRazonSocial() != null && !solicitante.getRazonSocial().equals("")) {
            request.getParams().put(CAMPO_NOMBRE_CONTRIBUYENTE, solicitante.getRazonSocial());
        }
        else {
            request.getParams().put(
                    CAMPO_NOMBRE_CONTRIBUYENTE,
                    solicitante.getNombre() + " " + solicitante.getApellidoPaterno() + " "
                            + solicitante.getApellidoMaterno());
        }

        String logoSat =
                File.separator + DIR_RESOURCES + File.separator + DIR_CSS + File.separator + DIR_IMAGES
                        + File.separator + "SAT.jpg";
        Map<String, String> imagenes = new HashMap<String, String>();
        imagenes.put("logosat", logoSat);
        String logoHacienda =
                File.separator + DIR_RESOURCES + File.separator + DIR_CSS + File.separator + DIR_IMAGES
                        + File.separator + "SHCP.jpg";
        imagenes.put("logoHacienda", logoHacienda);
        request.setImagenes(imagenes);

        request.getParams().put("rfcContribuyente", solicitante.getRfc());
        request.getParams().put("nombreTarea", tarea);
        request.setSubject("AVISO DE RECEPCI&Oacute;N DE DOCUMENTOS");
        request.setTemplate(CorreoConstant.CORREO_RECEPCION_DOCUMENTOS);
        mailRequestSender.sendMailRequest(request);

    }

}
