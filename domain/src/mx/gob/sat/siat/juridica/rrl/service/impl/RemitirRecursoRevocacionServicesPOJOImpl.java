/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service.impl;

import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.dao.PersonaDao;
import mx.gob.sat.siat.juridica.base.dao.RemisionDao;
import mx.gob.sat.siat.juridica.base.dao.TramiteDao;
import mx.gob.sat.siat.juridica.base.dao.UnidadAdministrativaDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.*;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.service.TareaServices;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.base.util.helper.DocumentosHelper;
import mx.gob.sat.siat.juridica.ca.util.helper.EnvioCorreosHelper;
import mx.gob.sat.siat.juridica.rrl.dao.RegistroRecursoRevocacionDAO;
import mx.gob.sat.siat.juridica.rrl.dao.SolicitudDAO;
import mx.gob.sat.siat.juridica.rrl.service.RemitirRecursoRevocacionServices;
import mx.gob.sat.siat.juridica.rrl.util.exception.RemitirAsuntoException;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.MailRequestSender;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.model.MailMessageRequest;
import mx.sat.siat.juridica.bpm.constant.CorreoConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * 
 * @author softtek
 * 
 */
@Service
public class RemitirRecursoRevocacionServicesPOJOImpl extends BaseSerializableBusinessServices implements
        RemitirRecursoRevocacionServices {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -7943289009482317863L;

    /**
     * Atributo tramiteDao tipo TramiteDao
     */
    @Autowired
    private TramiteDao tramiteDao;

    /**
     * Atributo tramiteDao tipo TramiteDao
     */
    @Autowired
    private SolicitudDAO solicitudDao;

    /**
     * Atributo personaDao tipo PersonaDao
     */
    @Autowired
    private PersonaDao personaDao;

    @Autowired
    private RegistroRecursoRevocacionDAO registroRecursoRevocacionDAO;

    /**
     * Atributo remisionDao tipo RemisionDao
     */
    @Autowired
    private RemisionDao remisionDao;

    @Autowired
    private DocumentosHelper documentosHelper;

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

    @Autowired
    private UnidadAdministrativaDao unidadAdministrativaDao;

    @Autowired
    private transient TareaServices tareaServices;

    /**
     * Metodo para obtener una lista de unidades administrativas
     * 
     * @return Lista
     */
    @Override
    public List<UnidadAdministrativa> obtenerCatalogo() {

        return unidadAdministrativaDao.obtenerUnidAdmvasVigentes();
    }

    @Override
    public Tramite obtenerTramitePorId(String idTramite) {
        return tramiteDao.obtenerTramitePorId(idTramite);
    }

    /**
     * Metoodo para remitir, comun entre T1 y T2
     * 
     * @param NumAsunto
     * @param unidadAdministrativa
     * @param idTarea
     * @param rfcAsignar
     */
    @Override
    public void remitir(String numAsunto, String unidadAdministrativa, Long idTarea, String rfcAsignar,
            String rfcUsuario, String ideTareaOrigen) throws RemitirAsuntoException {
        Tramite tramite = tramiteDao.obtenerTramitePorId(numAsunto);
        SolicitudDatosGenerales solicitud = ((SolicitudDatosGenerales) tramite.getSolicitud());
        if (unidadAdministrativa != null && unidadAdministrativa.equals(solicitud.getUnidadAdminBalanceo())) {
            throw new RemitirAsuntoException();
        }

        documentosHelper.complementarDocumentos(solicitud.getIdSolicitud());
        Remision remision = new Remision();

        remision.setTramite(tramite);
        UnidadAdministrativa unidadAdminNueva = unidadAdministrativaDao.obtenerUnidadPorId(unidadAdministrativa);
        remision.setUnidadAdminNueva(unidadAdminNueva);

        UnidadAdministrativa uaRemite = unidadAdministrativaDao.obtenerUnidadPorId(solicitud.getUnidadAdminBalanceo());
        remision.setUnidadAdminQueRemite(uaRemite);
        remision.setEstadoRemision(EstadoRemision.SOLICITADA.getClave());
        if (unidadAdminNueva.getIdeTipoUnidadAdministrativa().getClave()
                .equals(TipoUnidadAdministrativa.ADMINISTRACION_EXTERNA.getClave())
                || unidadAdminNueva.getIdeTipoUnidadAdministrativa().getClave()
                        .equals(TipoUnidadAdministrativa.ADMINISTRACION_INTERNA.getClave())) {
            remision.setTipoRemision(TipoRemision.AUTORIDAD_INT_EXT.getClave());
        } else {
            remision.setTipoRemision(TipoRemision.AUTORIDAD_PROPIA.getClave());
        }
        remision.setRfcAbogado(rfcUsuario);
        remision.setIdeTareaOrigen(ideTareaOrigen);
        remisionDao.guardarRemision(remision);

        Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, idTarea);
        tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
        tareaAct.setFechaAct(new Date());
        tareaServices.guardarTarea(tareaAct);

        Tarea tarea = new Tarea();
        tarea.setNumeroAsunto(tramite.getNumeroAsunto());
        tarea.setClaveAdm(tareaAct.getClaveAdm());
        tarea.setClaveAbogado(tareaAct.getClaveAbogado());
        tarea.setClaveAsignado(rfcAsignar);
        tarea.setTarea(EnumeracionBitacora.FIRMAR_REMISION.getClave());
        tarea.setDescripcion(EnumeracionBitacora.FIRMAR_REMISION.getDescripcion());
        tarea.setFechaCreacion(new Date());
        tarea.setFechaAct(new Date());
        tarea.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
        tareaServices.guardarTarea(tarea);
    }

    /**
     * Metodo para obtener una lista de personas por rol
     * 
     * @param tramite
     * @return Lista de personas
     */
    @Override
    public List<Persona> obtenerPersonaByRol(Tramite tramite) {
        List<Persona> abogados = null;

        abogados = personaDao.buscarPersonaSinUARByRol(tramite, TipoRol.ADMINISTRADOR.getClave());

        return abogados;
    }

    /**
     * Metodo para enviar un correo
     * 
     * @param numeroAsunto
     * @param rfc
     */
    public void enviarCorreo(String numeroAsunto, String rfc, String tarea) {
        MailMessageRequest request =
                new MailMessageRequest(new HashSet<String>(), "SUBJECT", "TEMPLATE", new HashMap<String, String>());

        Tramite tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        Solicitante sol = registroRecursoRevocacionDAO.getSolicituanteByRfc(tramite.getSolicitud().getIdSolicitud());

        Persona persona = personaDao.obtenerPersonaPorRFC(rfc);
        HashSet<String> destinatarios = new HashSet<String>();
        destinatarios.add(persona.getCorreoElectronico());
        request.setDestinations(destinatarios);
        request.setParams(envioCorreosHelper.correoAvisoTareaPendiente(numeroAsunto, persona));
        request.setSubject("Aviso de Tarea Pendiente");
        if (sol.getRazonSocial() != null && !sol.getRazonSocial().equals("")) {
            request.getParams().put("nombreContribuyente", sol.getRazonSocial());
        }
        else {
            request.getParams().put("nombreContribuyente",
                    sol.getNombre() + " " + sol.getApellidoPaterno() + " " + sol.getApellidoMaterno());
        }
        request.setTemplate(CorreoConstant.CORREO_TAREA_PENDIENTE);
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

    public SolicitudDAO getSolicitudDao() {
        return solicitudDao;
    }

    public void setSolicitudDao(SolicitudDAO solicitudDao) {
        this.solicitudDao = solicitudDao;
    }

    @Override
    public String getDefaultIdeTareaOrigen() {
        return TareaOrigen.ATENDER_ASUNTO.getClave();
    }

}
