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
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TareaOrigen;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRol;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitante;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.ca.util.helper.EnvioCorreosHelper;
import mx.gob.sat.siat.juridica.rrl.dao.RegistroRecursoRevocacionDAO;
import mx.gob.sat.siat.juridica.rrl.service.TurnarRecursoRevocacionServices;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.MailRequestSender;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.model.MailMessageRequest;
import mx.sat.siat.juridica.bpm.constant.CorreoConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author softtek
 * 
 */
@Service
public class TurnarRecursoRevocacionServicesPOJOImpl extends BaseSerializableBusinessServices implements
        TurnarRecursoRevocacionServices {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -7943289009482317863L;

    /**
     * Atributo personaDao tipo PersonaDao
     */
    @Autowired
    private PersonaDao personaDao;

    /**
     * Atributo tramiteDao tipo TramiteDao
     */
    @Autowired
    private TramiteDao tramiteDao;

    @Autowired
    private RegistroRecursoRevocacionDAO registroRecursoRevocacionDAO;

    /**
     * Atributo envioCorreosHelper tipo EnvioCorreosHelper
     */
    @Autowired
    private EnvioCorreosHelper envioCorreosHelper;

    /**
     * Atributo mailRequestSender tipo MailRequestSender
     */
    @Autowired
    private MailRequestSender mailRequestSender;

    /**
     * Metodo para obtener una lista de abogados
     * 
     * @param numAsunto
     * @return Persona
     */
    @Override
    public List<Persona> obtenerPersonaAbogados(String numAsunto) {

        Tramite tramite = tramiteDao.obtenerTramitePorId(numAsunto);

        return personaDao.buscarPersonaSinUARByRol(tramite, TipoRol.ABOGADO.getClave());
    }

    /**
     * Metodo para obtener un tramite por id
     * 
     * @param numAsunto
     * @return Tramite
     */
    @Override
    public Tramite obtenerTramitePorId(String numAsunto) {
        return tramiteDao.obtenerTramitePorId(numAsunto);
    }

    /**
     * Metodo para enviar un correo
     * 
     * @param numeroAsunto
     * @param idPersona
     */
    public void enviarCorreo(String numeroAsunto, Long idPersona, String tarea) {

        MailMessageRequest request =
                new MailMessageRequest(new HashSet<String>(), "SUBJECT", "TEMPLATE", new HashMap<String, String>());
        Persona persona = personaDao.obtenerPersonaPorId(idPersona);
        Tramite tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        Solicitante sol = registroRecursoRevocacionDAO.getSolicituanteByRfc(tramite.getSolicitud().getIdSolicitud());
        HashSet<String> destinatarios = new HashSet<String>();
        destinatarios.add(persona.getCorreoElectronico());
        request.setDestinations(destinatarios);
        request.setParams(envioCorreosHelper.correoAvisoTareaPendiente(numeroAsunto, persona));
        request.setSubject("Aviso de Tarea Pendiente");
        request.setTemplate(CorreoConstant.CORREO_TAREA_PENDIENTE);
        if (sol.getRazonSocial() != null && !sol.getRazonSocial().equals("")) {
            request.getParams().put("nombreContribuyente", sol.getRazonSocial());
        }
        else {
            request.getParams().put("nombreContribuyente",
                    sol.getNombre() + " " + sol.getApellidoPaterno() + " " + sol.getApellidoMaterno());
        }
        request.getParams().put("rfcContribuyente", sol.getRfc());
        request.getParams().put("nombreTarea", tarea);

        String logoSat =
                File.separator + "resources" + File.separator + "css" + File.separator + "images" + File.separator
                        + "SAT.jpg";
        Map<String, String> imagenes = new HashMap<String, String>();
        imagenes.put("logosat", logoSat);
        String logoHacienda =
                File.separator + "resources" + File.separator + "css" + File.separator + "images" + File.separator
                        + "SHCP.jpg";
        imagenes.put("logoHacienda", logoHacienda);
        request.setImagenes(imagenes);

        mailRequestSender.sendMailRequest(request);
        getLogger().debug("Se envio la peticion de correo.");

    }

    @Override
    public String getIdeTareaOrigen() {
        return TareaOrigen.TURNAR_ASUNTO.getClave();
    }

}
