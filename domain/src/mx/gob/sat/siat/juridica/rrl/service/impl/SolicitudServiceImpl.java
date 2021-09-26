/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service.impl;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.dao.*;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.*;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.service.*;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.base.util.helper.SelladoraHelper;
import mx.gob.sat.siat.juridica.bpm.dao.domain.ParametroTramiteBPM;
import mx.gob.sat.siat.juridica.bpm.dao.domain.ParticipantesBPM;
import mx.gob.sat.siat.juridica.bpm.excepcion.TareaInicialException;
import mx.gob.sat.siat.juridica.bpm.excepcion.TereaSinUsuarioAsignadoException;
import mx.gob.sat.siat.juridica.ca.util.helper.GenerarCadenasHelper;
import mx.gob.sat.siat.juridica.rrl.dao.RegistroRecursoRevocacionDAO;
import mx.gob.sat.siat.juridica.rrl.dao.SolicitudDAO;
import mx.gob.sat.siat.juridica.rrl.dao.domain.model.SolicitudRecursoRevocacion;
import mx.gob.sat.siat.juridica.rrl.service.GenerarNumeroAsuntoService;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;
import mx.gob.sat.siat.juridica.rrl.util.helper.FechaCapturaHelper;
import mx.gob.sat.siat.juridica.rrl.util.helper.TramiteHelper;
import mx.gob.sat.siat.selladora.SelladoraException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 */
@Service("solicitudService")
public class SolicitudServiceImpl extends BaseSerializableBusinessServices implements SolicitudService {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 728557768371111733L;
    private static final String FORMATO_FECHA = "dd/MM/yyyy HH:mm:ss:SS";

    /**
     * Atributo tramiteHelper tipo TramiteHelper
     */
    @Autowired
    private TramiteHelper tramiteHelper;

    @Autowired
    private FechaCapturaHelper fechaCapturaHelper;

    /**
     * Atributo tramiteDao tipo TramiteDao
     */
    @Autowired
    private TramiteDao tramiteDao;

    /**
     * Atributo diaInhabilDAO tipo DiaInhabilDAO
     */
    @Autowired
    private DiaInhabilDAO diaInhabilDAO;

    /**
     * Atributo generarNumeroAsuntoService tipo
     * GenerarNumeroAsuntoService
     */
    @Autowired
    private GenerarNumeroAsuntoService generarNumeroAsuntoService;

    /**
     * Atributo solicitudDAO tipo SolicitudDAO
     */
    @Autowired
    private SolicitudDAO solicitudDAO;

    /**
     * Atributo personaDao tipo PersonaDao
     */
    @Autowired
    private PersonaDao personaDao;

    /**
     * Atributo registroRecursoRevocacionDAO tipo
     * RegistroRecursoRevocacionDAO
     */
    @Autowired
    private RegistroRecursoRevocacionDAO registroRecursoRevocacionDAO;

    /**
     * Atributo generarCadenasHelper tipo GenerarCadenasHelper
     */
    @Autowired
    private GenerarCadenasHelper generarCadenasHelper;

    @Autowired
    private FirmarDAO firmarDAO;

    @Autowired
    private DocumentoDao documentoDao;

    @Autowired
    private EnviarCorreoService enviarCorreoService;

    @Autowired
    private SelladoraHelper selladoraHelper;

    @Autowired
    private AsignacionTramiteServices asignacionTramiteServices;
    
    @Autowired
    private transient SolicitudService solicitudService;
    @Autowired
    private transient TramiteServices tramiteServices;
    @Autowired
    private transient TareaServices tareaServices;
    @Autowired
    private transient UnidadAdministrativaServices unidadAdministrativaServices;
    @Autowired
    private transient SolicitanteServices solicitanteServices;
    @Autowired
    private transient BalanceadorServices balanceadorServices;
    @Autowired
    private transient TramiteReprocesarServices tramiteReprocesarServices;

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

    /**
     * Metodo para consultar la autorizacion de una solicitud
     * 
     * @param idSolicitud
     * @return Solicitud
     */
    @Override
    public SolicitudDatosGenerales obtenerSolicitudConsultaAutorizacionporId(Long idSolicitud) {
        getLogger().info("obteniendo solicitud por id {}", idSolicitud);
        return solicitudDAO.obtenerSolicitudDatosGeneralesPorId(idSolicitud);
    }

    /**
     * Metodo para iniciar un tramite
     * 
     * @param mensajeBPM
     */
    public void iniciaTramite(MensajeBPM mensajeBPM, SolicitudDatosGenerales sol, String rfcResponsable) {
        Tramite tramite = new Tramite();
        try {
            JSONObject json = null;
            if (mensajeBPM.getbDoc().contains("<root>")) {
                json = XML.toJSONObject(mensajeBPM.getbDoc()
                        .replace("<root>", "").replace("</root>", ""));
            } else {
                json = new JSONObject(mensajeBPM.getbDoc());
            }
            tramiteReprocesarServices.guardarTramiteAReprocesar(json, mensajeBPM.getBpdId(), mensajeBPM.getProcessAppId(), TipoErrorEnvioBPM.INEXISTENTE);
            tramite = tramiteDao.obtenerTramitePorIdSolicitud(sol.getIdSolicitud());
        } catch (Exception ex) {
            getLogger().debug("Error al guardar tramite a reproceso", ex);
        }

        getLogger().debug("Inicio service iniciaTramite {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
        try {
            Tarea tarea = new Tarea();
            if (tramite != null) {
                tarea.setNumeroAsunto(tramite.getNumeroAsunto());
            } else {
                tramite = tramiteDao.obtenerTramitePorIdSolicitud(sol.getIdSolicitud());
                tarea.setNumeroAsunto(tramite.getNumeroAsunto());
            }
            tarea.setClaveAdm(rfcResponsable);
            tarea.setClaveAsignado(rfcResponsable);
            tarea.setTarea(EnumeracionBitacora.TURNAR_ASUNTO.getClave());
            tarea.setDescripcion(EnumeracionBitacora.TURNAR_ASUNTO.getDescripcion());
            tarea.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
            tarea.setFechaCreacion(new Date());
            tarea.setFechaAct(new Date());
            tareaServices.guardarTarea(tarea);
        }
        catch (Exception ex){
            getLogger().error("Error en la tarea al generar la tarea", ex);
        }
    }

    /**
     * Metodo para generar la cadena original
     * 
     * @param idSolicitud
     * @param fechaFirma
     * @return cadena
     */
    public String generarCadenaOriginal(long idSolicitud, Date fechaFirma) {
        StringBuffer cadenaOriginal = new StringBuffer();

        List<Documento> listaDocumentos = new ArrayList<Documento>();
        List<DocumentoSolicitud> listaDocumentoSolicitud = documentoDao.obtenerDocumentosAnexados(idSolicitud);
        for (DocumentoSolicitud documentoSolicitud : listaDocumentoSolicitud) {
            Documento doc = documentoSolicitud.getDocumento();
            listaDocumentos.add(doc);
        }
     // |cadena|datos|remision|
        cadenaOriginal.append(generarCadenasHelper.generarCadenasRegistroSolicitud(idSolicitud, fechaFirma)); 
     // doc1|hash1|doc2|hash2|
        cadenaOriginal.append(generarCadenasHelper.generaCadenaDocumento(listaDocumentos)); 
        

        return cadenaOriginal.toString();
    }

    public SolicitudDatosGenerales obtenerSolicitudDatosGeneralesPorId(long idSolicitud){
        return solicitudDAO.obtenerSolicitudDatosGeneralesPorId(idSolicitud);
    }

    /**
     * Metodo para generar la cadena original del registro de
     * promocion RRL
     * 
     * @param idSolicitud
     * @param fechaFirma
     * @return cadena
     */
    @Override
    public String generarCadenaOriginalRegistroPromocionRRL(long idSolicitud, Date fechaFirma) {
        StringBuffer cadenaOriginal = new StringBuffer();

        List<Documento> listaDocumentos = new ArrayList<Documento>();
        List<DocumentoSolicitud> listaDocumentoSolicitud = documentoDao.obtenerDocumentosAnexados(idSolicitud);
        for (DocumentoSolicitud documentoSolicitud : listaDocumentoSolicitud) {
            Documento doc = documentoSolicitud.getDocumento();
            listaDocumentos.add(doc);
        }
        cadenaOriginal.append(generarCadenasHelper.generarCadenasRegistroSolicitud(idSolicitud, fechaFirma));
        cadenaOriginal.append(generarCadenasHelper.generarCadenaDatosPromocionRRL(idSolicitud));
        cadenaOriginal.append(generarCadenasHelper.generaCadenaDocumento(listaDocumentos)); 

        return cadenaOriginal.toString();
    }

    /**
     * Metodo para generar la cadena original de atencion de
     * requerimiento
     * 
     * @param idSolicitud
     * @param fechaFirma
     * @return cadena
     */
    @Override
    public String generarCadenaOriginalAtenderReq(long idSolicitud, Date fechaFirma) {
        StringBuffer cadenaOriginal = new StringBuffer();

        List<Documento> listaDocumentos = new ArrayList<Documento>();
        List<DocumentoSolicitud> listaDocumentoSolicitud = documentoDao.obtenerDocumentosAnexados(idSolicitud);
        for (DocumentoSolicitud documentoSolicitud : listaDocumentoSolicitud) {
            Documento doc = documentoSolicitud.getDocumento();
            listaDocumentos.add(doc);
        }
        cadenaOriginal.append(generarCadenasHelper.generarCadenaAtenderRequerimiento(idSolicitud, fechaFirma));
        cadenaOriginal.append(generarCadenasHelper.generaCadenaDocumento(listaDocumentos)); 

        return cadenaOriginal.toString();
    }

    /**
     * Metodo para generar la cadena original
     * @param idSolicitud
     * @param fechaFirma
     * @return cadena
     */
    @Override
    public String generarCadenaOriginalDocumentosAdicionales(long idSolicitud, Date fechaFirma) {
        Solicitud sol = solicitudDAO.obtenerSolicitudporId(idSolicitud);
        Tramite tramite = tramiteDao.obtenerTramitePorIdSolicitud(idSolicitud);
        return generarCadenasHelper.generarCadenaDocumentosAdicionales(tramite, sol, fechaFirma);
    }

    @Override
    public mx.gob.sat.siat.juridica.bpm.dao.domain.Tramite generarMensajeInicio(SolicitudDatosGenerales sol,
            Tramite tramite, String usuario, String promovente, String administrador) {
        mx.gob.sat.siat.juridica.bpm.dao.domain.Tramite inicioTramite =
                new mx.gob.sat.siat.juridica.bpm.dao.domain.Tramite();
        inicioTramite.setFolioTramite(tramite.getNumeroAsunto());
        SolicitudDatosGenerales solDatosGen = solicitudDAO.obtenerSolicitudDatosGeneralesPorId(sol.getIdSolicitud());
        inicioTramite.setTipoTramite(solDatosGen.getTipoTramiteSolicitud().getIdTipoTramite().longValue());
        inicioTramite.setDescripcionTipoTramite(solDatosGen.getTipoTramiteSolicitud().getDescripcionModalidad());
        inicioTramite.setIdSolicitud(tramite.getSolicitud().getIdSolicitud());

        inicioTramite.setAmparada(true);
        inicioTramite.setTipoPersona(sol.getSolicitante().getPersonaMoral() ? "Moral" : "Fisica");
        
        ParticipantesBPM participantesBPM = new ParticipantesBPM();
        String rfcCortoAdministrador = personaDao.obtenerRfcCorto(administrador);
        participantesBPM.setAdministrador(rfcCortoAdministrador);
        if (solDatosGen.getCveRolCapturista().equals(TipoRol.OFICIAL_PARTES.getClave())) {
            participantesBPM.setPromovente(personaDao.obtenerRfcCorto(usuario));
            participantesBPM.setOficialDePartes(personaDao.obtenerRfcCorto(usuario));
        }
        else {
            participantesBPM.setPromovente(usuario);
        }
        participantesBPM.setRfcPromovente(promovente);
        inicioTramite.setParticipantes(participantesBPM);

        inicioTramite.setEstadoProcesal(tramite.getEstadoTramite().getClave());

        inicioTramite.setUnidadAdmin(sol.getUnidadAdminBalanceo());
        agregaDatos(inicioTramite);

        Calendar fechaFinAtenderRequerimiento = Calendar.getInstance();
        fechaFinAtenderRequerimiento.add(Calendar.YEAR, NumerosConstantes.CIEN);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        ParametroTramiteBPM parametroTramiteBPM = inicioTramite.getParametroTramite();
        if (parametroTramiteBPM == null) {
            parametroTramiteBPM = new ParametroTramiteBPM();
        }
        parametroTramiteBPM.setFechaFinAtenderRequerimiento(format.format(fechaFinAtenderRequerimiento.getTime()));
        inicioTramite.setParametroTramite(parametroTramiteBPM);

        return inicioTramite;
    }

    public void agregaDatos(mx.gob.sat.siat.juridica.bpm.dao.domain.Tramite tramite) {

    }

    /**
     * Metodo para generar un tramite
     * @param idSolicitud
     * @param firma
     * @param usuario
     * @param ceritifcadoUtilizado
     * @param prefijoTramite
     * @param idTipoTramite
     * @return tramite
     */
    @Override
    public Tramite generarTramite(long idSolicitud, Firma firmas, Object ceritifcadoUtilizado, String prefijoTramite,
            String idTipoTramite) {
        Solicitud solicitud = solicitudDAO.obtenerSolicitudporId(idSolicitud);
        String idTipoTramiteLocal = idTipoTramite;

        String anio = Integer.toString((Calendar.getInstance().get(Calendar.YEAR)));

        if (idTipoTramiteLocal == null) {
            idTipoTramiteLocal = solicitud.getTipoTramite().getIdTipoTramite().toString();
        }
        String secuencia =
                generarNumeroAsuntoService.obtenerSecuencia(TipoSecuencia.SOLICITUD.getClave(), anio,
                        idTipoTramiteLocal);
        String numeroAsunto = tramiteHelper.generarNumeroAsunto(prefijoTramite, anio, secuencia);

        Tramite tramite = new Tramite(numeroAsunto);
        tramite.setSolicitud(solicitud);

        try {
            tramite.setFechaInicioTramite(fechaCapturaHelper.calcularFechaCaptura(new Date()));
        }
        catch (ParseException e) {
            tramite.setFechaInicioTramite(new Date());
            getLogger().error("error al generar el tramite: ", e);

        }
        if (solicitud.getTipoTramite().getIdTipoTramite().toString().equals(DiscriminadorConstants.T2_CLASIFICACION_ARANCELARIA)) {
            tramite.setFechaCalculoTranscurridos(tramiteHelper.fechaVencimientoTramiteClasifArancelaria(
                    tramite.getFechaInicioTramite(), diaInhabilDAO.obtenerDiasInhabiles()));
        }
        else {
            tramite.setFechaCalculoTranscurridos(tramiteHelper.fechaVencimientoTramite(tramite.getFechaInicioTramite(),
                    diaInhabilDAO.obtenerDiasInhabiles()));
        }
        tramite.setEstadoTramite(EstadoTramite.PROMOVIDO);
        tramite.setFechaRecepcion(tramite.getFechaInicioTramite());

        tramite.setFechaEstatus(new Date());
        actualizaEstadoSolicitud(solicitud.getIdSolicitud(), EstadoSolicitud.RECIBIDA);
        return tramite;

    }

    /**
     * Metodo para guardar un tramite
     * 
     * @param tramite
     */
    @Override
    public void guardarTramite(Tramite tramite) {
        tramiteDao.crearTramite(tramite);

    }

    @Override
    public void sellaDocumentos(Long idSolicitud) {
        getLogger().debug("inicia Sellado");
        List<DocumentoSolicitud> docs =
                registroRecursoRevocacionDAO.obtenerDocumentoSolicitudAnexado(idSolicitud.toString());
        Firma selloFirma;
        for (DocumentoSolicitud doc : docs) {
            selloFirma = null; 

            try {
                selloFirma = selladoraHelper.getSello(doc.getDocumento().getHash());
                getLogger().debug("Id de la selladora: " + selloFirma.getCertificado());
                getLogger().debug(
                        "sello doc '" + doc.getDocumento().getNombre() + "': " + selloFirma.getFirmaElectronica());
            }
            catch (SelladoraException se) {
                selloFirma = selladoraHelper.getSelloDefault(doc.getDocumento().getHash());
                getLogger().error(se.getMessage());
            }
            doc.getDocumento().setSello(selloFirma.getFirmaElectronica());
            registroRecursoRevocacionDAO.guardaDocumento(doc);
            registroRecursoRevocacionDAO.guardaDocumentoSolicitud(doc.getDocumento());
        }

    }

    /**
     * Metodo para firmar documentos
     * 
     * @param idSolicitud
     */
    @Override
    public void firmarDocumentos(Long idSolicitud, Firma firma, boolean complementario) {
        SolicitudDatosGenerales solicitudDG = solicitudDAO.obtenerSolicitudDatosGeneralesPorId(idSolicitud);
        Tramite tramite = tramiteDao.obtenerTramitePorIdSolicitud(idSolicitud);
        if (firma.getIdFirma() == null) {
            firmarDAO.guardarFirma(firma);
        }
        FirmaSolicitud firmaSol = new FirmaSolicitud();
        firmaSol.setFirmaSolicitudPK(new FirmaSolicitudPK());
        firmaSol.getFirmaSolicitudPK().setIdFirma(firma.getIdFirma());
        firmaSol.getFirmaSolicitudPK().setIdSolicitud(idSolicitud);
        firmarDAO.guardarFirmaSolicitud(firmaSol);
        List<DocumentoSolicitud> docs =
                registroRecursoRevocacionDAO.obtenerDocumentoSolicitudAnexado(idSolicitud.toString());
        for (DocumentoSolicitud doc : docs) {
            doc.setEstadoDocumentoSolicitud(EstadoDocumento.FIRMADO.getClave());
            doc.setComplementario(complementario);
            doc.getDocumento().setEstadoDocumento(EstadoDocumento.FIRMADO.getClave());
            if (solicitudDG.getFolio() != null) {
                doc.setFolio(solicitudDG.getFolio());
            }
            registroRecursoRevocacionDAO.guardaDocumento(doc);
            registroRecursoRevocacionDAO.guardaDocumentoSolicitud(doc.getDocumento());
        }
        AsignacionTramite abogado =
                asignacionTramiteServices.buscarAsignacionesRolAsunto(tramite.getNumeroAsunto(),
                        RolesConstantes.ROL_INTERNO_EMPLEADO_ABOGADO);
        if (abogado != null) {
            enviarCorreoService.enviarCorreoDocAdicionalesAbogado(tramite.getNumeroAsunto(), abogado.getPersona()
                    .getRfc());
        }
        if (solicitudDG.getUnidadAdminBalanceo() != null) {
            enviarCorreoService.enviarCorreoDocAdicionalesAdministracion(tramite.getNumeroAsunto(),
                    solicitudDG.getUnidadAdminBalanceo());
        }
    }

    /**
     * Metodo para firmar documentos
     * 
     * @param idSolicitud
     */
    @Override
    public void firmarDocumentosRegistro(Long idSolicitud, Firma firma, boolean complementario) {
        SolicitudDatosGenerales solicitudDG = solicitudDAO.obtenerSolicitudDatosGeneralesPorId(idSolicitud);

        if (firma.getIdFirma() == null) {
            firmarDAO.guardarFirma(firma);
        }
        FirmaSolicitud firmaSol = new FirmaSolicitud();
        firmaSol.setFirmaSolicitudPK(new FirmaSolicitudPK());
        firmaSol.getFirmaSolicitudPK().setIdFirma(firma.getIdFirma());
        firmaSol.getFirmaSolicitudPK().setIdSolicitud(idSolicitud);
        firmarDAO.guardarFirmaSolicitud(firmaSol);
        List<DocumentoSolicitud> docs =
                registroRecursoRevocacionDAO.obtenerDocumentoSolicitudAnexado(idSolicitud.toString());
        for (DocumentoSolicitud doc : docs) {
            doc.setEstadoDocumentoSolicitud(EstadoDocumento.FIRMADO.getClave());
            doc.setComplementario(complementario);
            doc.getDocumento().setEstadoDocumento(EstadoDocumento.FIRMADO.getClave());
            if (solicitudDG.getFolio() != null) {
                doc.setFolio(solicitudDG.getFolio());
            }
            registroRecursoRevocacionDAO.guardaDocumento(doc);
            registroRecursoRevocacionDAO.guardaDocumentoSolicitud(doc.getDocumento());
        }

    }

    /**
     * Metodo para guardar una solicitud
     * 
     * @param solicitud
     * @return Solicitud
     */
    @Override
    public Solicitud guardarSolicitud(Solicitud solicitud) {
        return solicitudDAO.guardarSolicitud(solicitud);
    }

    /**
     * Metodo para actualizar una unidad administrativa
     * 
     * @param sol
     * @param claveUnidad
     * @return Solicitud
     */
    @Override
    public Solicitud actualizarUnidadAdminBalanceo(Solicitud sol, String claveUnidad) {

        Solicitud solLocal = this.obtenerSolicitudporId(sol.getIdSolicitud());
        ((SolicitudDatosGenerales) solLocal).setUnidadAdminBalanceo(claveUnidad);
        return solLocal;
    }

    /**
     * Metodo para obtener una lista de solicitudes por filtros
     * 
     * @param idSolicitud
     * @param fechaIni
     * @param fechaFin
     * @param rfc
     * @return Lista de Solicitudes
     */
    @Override
    public List<Solicitud> obtenerSolicitudesPorFiltros(Long idSolicitud, Date fechaInicio, Date fechaFin, String rfc) {
        return solicitudDAO.obtenerSolicitudesPorFiltros(idSolicitud, fechaInicio, fechaFin, rfc);
    }

    public void actualizaEstadoSolicitud(Long idSolicitud, EstadoSolicitud estadoSolicitud) {
        Solicitud solicitudBD = solicitudDAO.obtenerSolicitudporId(idSolicitud);
        solicitudBD.setEstadoSolicitud(estadoSolicitud);
    }

    /**
     * Genera una Firma con el sello, cadena y certificado de la
     * selladora del SIAT
     * 
     * @param numAsunto
     * @param idSolicitud
     * @param fechaFirma
     * @return Una firma generada con los datos devueltos por el SIAT,
     *         con la cadena del registro de promocion cuando firma el
     *         SIAT, o una Firma con la cadena sin sellio ni serie de
     *         certificado si ocurre una excepcion
     */
    @Override
    public Firma generaFirmaPromocionSIAT(String numAsunto, long idSolicitud, Date fechaFirma) {
        StringBuffer cadenaOriginal = new StringBuffer();
        List<Documento> listaDocumentos = new ArrayList<Documento>();
        List<DocumentoSolicitud> listaDocumentoSolicitud = documentoDao.obtenerDocumentosAnexados(idSolicitud);
        for (DocumentoSolicitud documentoSolicitud : listaDocumentoSolicitud) {
            Documento doc = documentoSolicitud.getDocumento();
            listaDocumentos.add(doc);
        }

        cadenaOriginal.append(generarCadenasHelper.generarCadenasRegistroPromocion(numAsunto, fechaFirma));
        cadenaOriginal.append(generarCadenasHelper.generaCadenaDocumento(listaDocumentos)); 


        String strCadena = cadenaOriginal.toString();
        Firma firmaSIAT = null;
        try {
            firmaSIAT = selladoraHelper.getSello(strCadena);
        }
        catch (SelladoraException se) {
            firmaSIAT = selladoraHelper.getSelloDefault(strCadena);
            getLogger().error("Sella promocion RRL:  " + se.getMessage());
        }

        return firmaSIAT;
    }

    /**
     * Genera una Firma con el sello, cadena y certificado de la
     * selladora del SIAT con los datos del registro de promocioni RRL
     * 
     * @param numAsunto
     * @param idSolicitud
     * @param fechaFirma
     * @return Una firma generada con los datos devueltos por el SIAT,
     *         con la cadena del registro de promocion cuando firma el
     *         SIAT, o una Firma con la cadena sin sellio ni serie de
     *         certificado si ocurre una excepcion
     */
    @Override
    public Firma generaFirmaRegistroPromocionSIAT(String numAsunto, long idSolicitud, Date fechaFirma) {
        StringBuffer cadenaOriginal = new StringBuffer();
        List<Documento> listaDocumentos = new ArrayList<Documento>();
        List<DocumentoSolicitud> listaDocumentoSolicitud = documentoDao.obtenerDocumentosAnexados(idSolicitud);
        for (DocumentoSolicitud documentoSolicitud : listaDocumentoSolicitud) {
            Documento doc = documentoSolicitud.getDocumento();
            listaDocumentos.add(doc);
        }

        cadenaOriginal.append(generarCadenasHelper.generarCadenasRegistroPromocion(numAsunto, fechaFirma));
        cadenaOriginal.append(generarCadenasHelper.generarCadenaDatosPromocionRRL(idSolicitud)); 
        cadenaOriginal.append(generarCadenasHelper.generaCadenaDocumento(listaDocumentos)); 

        String strCadena = cadenaOriginal.toString();
        Firma firmaSIAT = null;
        try {
            firmaSIAT = selladoraHelper.getSello(strCadena);
        }
        catch (SelladoraException se) {
            firmaSIAT = selladoraHelper.getSelloDefault(strCadena);
            getLogger().error("Sella promocion RRL:  " + se.getMessage());
        }

        return firmaSIAT;
    }

    @Override
    public void modificarSolicitudRRL(SolicitudDatosGenerales solicitud) {
        SolicitudRecursoRevocacion solicitudBase =
                (SolicitudRecursoRevocacion) solicitudDAO.obtenerSolicitudDatosGeneralesPorId(solicitud
                        .getIdSolicitud());
        solicitudBase.setUnidadAdministrativaRepresentacionFederal(solicitud
                .getUnidadAdministrativaRepresentacionFederal());
        solicitudBase.setFechaEstatus(new Date());
        solicitudDAO.guardarSolicitudRRL(solicitudBase);
    }

    public List<Object>  firmarSolicitud(long idSolicitud, Firma firma, String usuario, String rfcContribuyente,
            Object ceritifcadoUtilizado) throws TareaInicialException, TereaSinUsuarioAsignadoException {
            
        MensajeBPM mensajeBPM = new MensajeBPM();
        mensajeBPM.setTipoMensajeBPM(TipoMensajeBPM.INICIAR_TRAMITE_JSON);
        mensajeBPM.setBpdId(ProcesosBPM.RECURSOS_REVOCACION.getBpdId());
        mensajeBPM.setProcessAppId(ProcesosBPM.RECURSOS_REVOCACION.getProcessAppId());

        getLogger().debug("Profiling inicio service obtenerTramitePorIdSolicitud {}", new SimpleDateFormat(FORMATO_FECHA).format(new Date()) );
        Tramite tramite = tramiteServices.obtenerTramitePorIdSolicitud(idSolicitud);
        getLogger().debug("Profiling fin service obtenerTramitePorIdSolicitud {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
        if (tramite == null) {

            getLogger().debug("Profiling inicio service generarTramite {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
            tramite =
                    solicitudService.generarTramite(idSolicitud, firma,
                            ceritifcadoUtilizado, DiscriminadorConstants.PREFIJO_RRL, null);
            getLogger().debug("Profiling fin service generarTramite {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
            
            getLogger().debug("Profiling inicio service guardarTramite {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
            solicitudService.guardarTramite(tramite);
            getLogger().debug("Profiling fin service guardarTramite {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
        }
        
        SolicitudDatosGenerales solDat = (SolicitudDatosGenerales) tramite.getSolicitud();

        getLogger().debug("Profiling inicio service obtenerUnidadPorId {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
        UnidadAdministrativa unidadLocal =
                unidadAdministrativaServices.obtenerUnidadPorId(tramite.getSolicitud()
                        .getUnidadAdministrativaRepresentacionFederal().getClave());
        getLogger().debug("Profiling fin service obtenerUnidadPorId {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
        String administradorResponsable = null;
        if (unidadLocal.isBlnLocal()) { 

            Solicitante solicitante = solicitanteServices.obtenerSolicitanteByIdSol(idSolicitud);
            String claveAdministracionLocalRecaudadora =
                    solicitante.getDomicilio().getClaveAdministracionLocalRecaudadora();
            UnidadAdministrativa unidadAdmin =
                    unidadAdministrativaServices.obtenerUnidadPorClaveLocal(claveAdministracionLocalRecaudadora);
            tramite.setSolicitud(solicitudService.actualizarUnidadAdminBalanceo(tramite.getSolicitud(),
                    unidadAdmin.getClave()));

            getLogger().debug("Profiling inicio service obtenerAutorizadorResponsableUnidadLocalRecaudadora {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
            ResultadoAdminResponsable resultado1 =
                    balanceadorServices
                            .obtenerAutorizadorResponsableUnidadLocalRecaudadora((SolicitudDatosGenerales) tramite
                                    .getSolicitud());
            getLogger().debug("Profiling fin service obtenerAutorizadorResponsableUnidadLocalRecaudadora {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );

            if (resultado1 != null) {
                administradorResponsable = resultado1.getRfcAdministrador();

                solDat.setUnidadAdminBalanceo(resultado1.getUnidadAdmin());
                solicitudService.actualizarUnidadAdminBalanceo(solDat, resultado1.getUnidadAdmin());
            }
        }
        else {

            getLogger().debug("Profiling inicio service obtenerAutorizadorResponsable {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
            ResultadoAdminResponsable resultado = balanceadorServices.obtenerAutorizadorResponsable(solDat);
            getLogger().debug("Profiling fin service obtenerAutorizadorResponsable {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );

            if (resultado != null) {
                administradorResponsable = resultado.getRfcAdministrador();
                solDat.setUnidadAdminBalanceo(resultado.getUnidadAdmin());
                solicitudService.actualizarUnidadAdminBalanceo(solDat, resultado.getUnidadAdmin());
            }

        }

        /*Arma datos para el mensaje de bpm */
        mx.gob.sat.siat.juridica.bpm.dao.domain.Tramite inicioTramite =
                solicitudService.generarMensajeInicio(solDat, tramite, usuario, rfcContribuyente,
                        administradorResponsable);
        JSONObject dDoc = new JSONObject();
        dDoc.put("tramite", new JSONObject(inicioTramite));
        mensajeBPM.setbDoc(dDoc.toString());
        
        List<Object> resultados = new ArrayList<Object>();
        resultados.add(mensajeBPM);
        resultados.add(solDat);
        resultados.add(administradorResponsable);
        resultados.add(inicioTramite);
        resultados.add(tramite);
        return resultados;
    }
}
