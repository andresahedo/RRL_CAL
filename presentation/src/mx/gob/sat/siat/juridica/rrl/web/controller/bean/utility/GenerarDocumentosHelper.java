package mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoAcuse;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRol;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.nube.azure.service.AzureCifradoService;
import mx.gob.sat.siat.juridica.nube.azure.service.CifradoService;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.service.AutorizarRemitirService;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;
import mx.gob.sat.siat.juridica.rrl.util.helper.FechaCapturaHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class GenerarDocumentosHelper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7494507110580165614L;

    @Autowired
    @Qualifier("acuseReciboPromociones")
    private MultiFormatReportVU acuseReciboPromociones;

    @Autowired
    @Qualifier("constanciaFirmado")
    private MultiFormatReportVU constanciaFirmado;

    @Autowired
    private FechaCapturaHelper fechaCapturaHelper;

    public static final String RESOURCES = "resources";

    @Autowired
    @Qualifier("acuseTerminosyCondiciones")
    private MultiFormatReportVU acuseTerminosyCondiciones;

    @Autowired
    @Qualifier("acuseTerminosyCondicionesCal")
    private MultiFormatReportVU acuseTerminosyCondicionesCal;
    
    public static final String PDF = ".pdf";

    /**
     * @return the acuseReciboPromocionesAutoridad
     */
    public MultiFormatReportVU getAcuseReciboPromocionesAutoridad() {
        return acuseReciboPromocionesAutoridad;
    }

    /**
     * @param acuseReciboPromocionesAutoridad
     *            the acuseReciboPromocionesAutoridad to set
     */
    public void setAcuseReciboPromocionesAutoridad(MultiFormatReportVU acuseReciboPromocionesAutoridad) {
        this.acuseReciboPromocionesAutoridad = acuseReciboPromocionesAutoridad;
    }

    /**
     * @return the constanciaFirmadoAutoridad
     */
    public MultiFormatReportVU getConstanciaFirmadoAutoridad() {
        return constanciaFirmadoAutoridad;
    }

    /**
     * @param constanciaFirmadoAutoridad
     *            the constanciaFirmadoAutoridad to set
     */
    public void setConstanciaFirmadoAutoridad(MultiFormatReportVU constanciaFirmadoAutoridad) {
        this.constanciaFirmadoAutoridad = constanciaFirmadoAutoridad;
    }

    @Autowired
    @Qualifier("acuseReciboPromocionesAutoridad")
    private MultiFormatReportVU acuseReciboPromocionesAutoridad;

    @Autowired
    @Qualifier("constanciaFirmadoAutoridad")
    private MultiFormatReportVU constanciaFirmadoAutoridad;

    @Value("${verificador.url}")
    private String urlVerificador;

    @Autowired
    private AutorizarRemitirService autorizarRemitirService;

    @Autowired
    private transient AzureCifradoService azureCifradoService;

    @Autowired
    private transient TramiteServices tramiteServices;

    @Autowired
    private transient CifradoService cifradoService;

    @Autowired
    private SolicitudService solicitudService;

    /**
     * Propiedad para llevar a cabo un registro de eventos.
     */
    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("notificacionCAL")
    private transient MultiFormatReportVU notificacionCAL;

    public List<Long> generarDocumentosRequerimientoAutoridad(DatosBandejaTareaDTO datosBandejaTareaDTO,
            String claveTipoAsunto, String cadenaOriginal, String sello, String cadenaOriginalSIAT, String selloSIAT)
            throws BusinessException {
        String numeroAsunto = datosBandejaTareaDTO.getNumeroAsunto();
        Long idRequerimiento = datosBandejaTareaDTO.getIdRequerimiento();
        String rfcSolicitante = datosBandejaTareaDTO.getRfcSolicitante();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

        Tramite tramite = tramiteServices.buscarTramite(numeroAsunto, null);

        final Map<String, Object> parameters = new LinkedHashMap<String, Object>();
        List<Long> idDocumentos = new ArrayList<Long>();

        MultiFormatReportVU oficio = null;
        MultiFormatReportVU oficio2 = null;

        oficio = getConstanciaFirmadoAutoridad();
        oficio2 = getAcuseReciboPromocionesAutoridad();

        Date fechaCalculada;

        String clvTipoAsunto = null;
        try {
            fechaCalculada = fechaCapturaHelper.calcularFechaCaptura(new Date());
        }
        catch (ParseException e1) {
            fechaCalculada = new Date();
            logger.error("ocurrio error al generar documento: ", e1);
        }

        if (claveTipoAsunto.equals(TipoAcuse.ATREQ.getClave())) {
            clvTipoAsunto = "8";
        }
        else {
            if (claveTipoAsunto.equals(TipoAcuse.RECPROM.getClave())) {
                clvTipoAsunto = "1";
            }
            else {
                if (claveTipoAsunto.equals(TipoAcuse.RDOCAD.getClave())) {
                    clvTipoAsunto = "0";
                }
            }
        }
        parameters.put("cadenaOriginal", cadenaOriginal);
        parameters.put("paramSello", sello);
        parameters.put("cadenaOriginalSIAT", cadenaOriginalSIAT);
        parameters.put("paramSelloSIAT", selloSIAT);
        parameters.put("tipoAcuse", clvTipoAsunto);
        parameters.put("URLVERIFICACION", urlVerificador);
        try {
            parameters.put("urlEncodedRfc", URLEncoder.encode(rfcSolicitante, "UTF-8"));
        }
        catch (UnsupportedEncodingException ue) {
            logger.error("ocurrio error al codificar el rfc: ", ue);
        }
        parameters.put("fecha", fechaCalculada);

        if (oficio != null && oficio2 != null) {
            oficio.setReportFormat(ReportFormat.PDF);
            oficio2.setReportFormat(ReportFormat.PDF);

            parameters.put("BASE_PATH", buildRealPath(File.separator + RESOURCES + File.separator + "css"
                    + File.separator + "images" + File.separator));
            parameters.put("idRequerimiento", idRequerimiento);
            parameters.put("FILE_SEPARATOR", File.separator);
            parameters.put("QR_VALUE", (format.format(tramite.getFechaRecepcion())));

            oficio.setParameters(parameters);
            oficio2.setParameters(parameters);
            final String urlJasper = buildRealPath(oficio.getUrl());
            final String urlJasper2 = buildRealPath(oficio2.getUrl());
            logger.debug("urlJasper[{}]", urlJasper);
            String nombreDoc = "autConstancia" + fechaCalculada.getTime() + PDF;
            String nombreDoc2 = "autProm" + fechaCalculada.getTime() + PDF;

            try {
                byte[] byteOficio1 = oficio.getByteArrayOutputStream(urlJasper).toByteArray();
                String hashArchivo1 = cifradoService.genrerarHASHAchivo(byteOficio1);
                byteOficio1 = cifradoService.cifrarAES(byteOficio1, nombreDoc);
                ByteArrayInputStream bai = new ByteArrayInputStream(byteOficio1);
                azureCifradoService.upload(bai, nombreDoc);

                idDocumentos.add(guardarDocOficial(numeroAsunto, nombreDoc,
                        TipoDocumentoOficial.CONSTANCIA_RECEPCION_DOCUMENTO.getDescripcion(),
                        TipoDocumentoOficial.CONSTANCIA_RECEPCION_DOCUMENTO.getClave(), hashArchivo1,
                        byteOficio1.length));

                bai.close();

                byte[] byteOficio2 = oficio2.getByteArrayOutputStream(urlJasper2).toByteArray();
                String hashArchivo2 = cifradoService.genrerarHASHAchivo(byteOficio2);
                byteOficio2 = cifradoService.cifrarAES(byteOficio2, nombreDoc2);
                ByteArrayInputStream bai2 = new ByteArrayInputStream(byteOficio2);
                azureCifradoService.upload(bai2, nombreDoc2);

                idDocumentos.add(guardarDocOficial(numeroAsunto, nombreDoc2,
                        TipoDocumentoOficial.ACUSE_RECEPCION_PROMOCION.getDescripcion(),
                        TipoDocumentoOficial.ACUSE_RECEPCION_PROMOCION.getClave(), hashArchivo2, byteOficio2.length));
                bai2.close();
            }
            catch (Exception e) {
                logger.error("", e);
            }

        }
        return idDocumentos;
    }

    public List<Long> generarDocumentosPromocion(DatosBandejaTareaDTO datosBandejaTareaDTO, String claveTipoAsunto,
            String cadenaOriginal, String sello, String cadenaOriginalSIAT, String selloSIAT) throws BusinessException {
        String numAsunto = datosBandejaTareaDTO.getNumeroAsunto();
        Long idSolicitud = datosBandejaTareaDTO.getIdSolicitud();
        String rfcSolicitante = datosBandejaTareaDTO.getRfcSolicitante();
        Date fechaAsignacion=datosBandejaTareaDTO.getFechaAsignacion();
        final Map<String, Object> parameters = new LinkedHashMap<String, Object>();
        List<Long> idDocumentos = new ArrayList<Long>();

        Date fechaCalculada;
        try {
            fechaCalculada = fechaCapturaHelper.calcularFechaCaptura(fechaAsignacion!=null ? fechaAsignacion : new Date());
        }
        catch (ParseException e1) {
            fechaCalculada = new Date();
            logger.error("ocurrio error al generar documento: ", e1);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        Tramite tramite = tramiteServices.buscarTramite(numAsunto, null);
        MultiFormatReportVU oficio = null;
        MultiFormatReportVU oficio2 = null;
        MultiFormatReportVU oficio4 = null;

        oficio = getConstanciaFirmado();
        oficio2 = getAcuseReciboPromociones();

        oficio4 = getAcuseTerminosyCondiciones();

        solicitudService.sellaDocumentos(idSolicitud);

        String clvTipoAsunto = null;

        if (claveTipoAsunto.equals(TipoAcuse.ATREQ.getClave())) {
            clvTipoAsunto = "8";
        }
        else {
            if (claveTipoAsunto.equals(TipoAcuse.RECPROM.getClave())) {
                clvTipoAsunto = "1";
            }
            else {
                if (claveTipoAsunto.equals(TipoAcuse.RDOCAD.getClave())) {
                    clvTipoAsunto = "0";
                }
            }
        }
        parameters.put("cadenaOriginal", cadenaOriginal);
        parameters.put("paramSello", sello);
        parameters.put("cadenaOriginalSIAT", cadenaOriginalSIAT);
        parameters.put("paramSelloSIAT", selloSIAT);
        parameters.put("tipoAcuse", clvTipoAsunto);
        parameters.put("URLVERIFICACION", urlVerificador);
        try {
            parameters.put("urlEncodedRfc", URLEncoder.encode(rfcSolicitante, "UTF-8"));
        }
        catch (UnsupportedEncodingException ue) {
            logger.error("ocurrio error al codificar el rfc: ", ue);
        }
        parameters.put("fecha", fechaCalculada);

        if (oficio != null && oficio2 != null) {
            oficio.setReportFormat(ReportFormat.PDF);
            oficio2.setReportFormat(ReportFormat.PDF);
            oficio4.setReportFormat(ReportFormat.PDF);

            parameters.put("BASE_PATH", buildRealPath(File.separator + RESOURCES + File.separator + "css"
                    + File.separator + "images" + File.separator));
            parameters.put("idSolicitud", idSolicitud);
            parameters.put("FILE_SEPARATOR", File.separator);
            parameters.put("QR_VALUE", (format.format(tramite.getFechaRecepcion())));

            oficio.setParameters(parameters);
            oficio2.setParameters(parameters);
            oficio4.setParameters(parameters);
            final String urlJasper = buildRealPath(oficio.getUrl());
            final String urlJasper2 = buildRealPath(oficio2.getUrl());
            final String urlJasper4 = buildRealPath(oficio4.getUrl());
            logger.debug("urlJasper[{}]", urlJasper);
            String nombreDoc = rfcSolicitante + "solConstancia" + fechaCalculada.getTime() + PDF;
            String nombreDoc2 = rfcSolicitante + "solProm" + fechaCalculada.getTime() + PDF;
            String nombreDoc4 = rfcSolicitante + "acuse" + fechaCalculada.getTime() + PDF;

            try {
                byte[] byteOficio1 = oficio.getByteArrayOutputStream(urlJasper).toByteArray();
                String hashArchivo1 = cifradoService.genrerarHASHAchivo(byteOficio1);
                byteOficio1 = cifradoService.cifrarAES(byteOficio1, nombreDoc);
                ByteArrayInputStream bai = new ByteArrayInputStream(byteOficio1);
                azureCifradoService.upload(bai, nombreDoc);

                idDocumentos.add(guardarDocOficial(numAsunto, nombreDoc,
                        TipoDocumentoOficial.CONSTANCIA_RECEPCION_DOCUMENTO.getDescripcion(),
                        TipoDocumentoOficial.CONSTANCIA_RECEPCION_DOCUMENTO.getClave(), hashArchivo1,
                        byteOficio1.length));

                bai.close();

                byte[] byteOficio2 = oficio2.getByteArrayOutputStream(urlJasper2).toByteArray();
                String hashArchivo2 = cifradoService.genrerarHASHAchivo(byteOficio2);
                byteOficio2 = cifradoService.cifrarAES(byteOficio2, nombreDoc2);
                ByteArrayInputStream bai2 = new ByteArrayInputStream(byteOficio2);
                azureCifradoService.upload(bai2, nombreDoc2);

                idDocumentos.add(guardarDocOficial(numAsunto, nombreDoc2,
                        TipoDocumentoOficial.ACUSE_RECEPCION_PROMOCION.getDescripcion(),
                        TipoDocumentoOficial.ACUSE_RECEPCION_PROMOCION.getClave(), hashArchivo2, byteOficio2.length));
                bai2.close();
                SolicitudDatosGenerales solicitud =
                        solicitudService.obtenerSolicitudConsultaAutorizacionporId(idSolicitud);
                if (claveTipoAsunto.equals(TipoAcuse.RECPROM.getClave())
                        && !TipoRol.OFICIAL_PARTES.getClave().equals(solicitud.getCveRolCapturista())) {
                    byte[] byteOficio4 = oficio4.getByteArrayOutputStream(urlJasper4).toByteArray();
                    String hashArchivo4 = cifradoService.genrerarHASHAchivo(byteOficio4);
                    byteOficio4 = cifradoService.cifrarAES(byteOficio4, nombreDoc4);
                    ByteArrayInputStream bai4 = new ByteArrayInputStream(byteOficio4);
                    azureCifradoService.upload(bai4, nombreDoc4);

                    idDocumentos.add(guardarDocOficial(numAsunto, nombreDoc4,
                            TipoDocumentoOficial.OFICIO_TERMINOS_CONDICIONES.getDescripcion(),
                            TipoDocumentoOficial.OFICIO_TERMINOS_CONDICIONES.getClave(), hashArchivo4,
                            byteOficio4.length));

                    bai4.close();
                }

            }
            catch (Exception e) {
                logger.error("", e);
            }

        }
        return idDocumentos;
    }

    public List<Long> generarDocumentosPromocionCAL(DatosBandejaTareaDTO datosBandejaTareaDTO, String claveTipoAsunto,
            FirmaDTO firma, String cadenaOriginalSIAT, String selloSIAT, boolean tieneDocsAnexados, String tipoContribuyente, String estadoContribuyente)
            throws BusinessException {
        String numAsunto = datosBandejaTareaDTO.getNumeroAsunto();
        Long idSolicitud = datosBandejaTareaDTO.getIdSolicitud();
        String rfcSolicitante = datosBandejaTareaDTO.getRfcSolicitante();

        final Map<String, Object> parameters = new LinkedHashMap<String, Object>();
        List<Long> idDocumentos = new ArrayList<Long>();
        String cadenaOriginal = firma.getCadenaOriginal();
        String sello = firma.getSello();
        Tramite tramite = tramiteServices.buscarTramite(numAsunto, null);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        MultiFormatReportVU oficio = null;
        MultiFormatReportVU oficio2 = null;
        MultiFormatReportVU oficio3 = null;
        MultiFormatReportVU oficio4 = null;
        logger.debug("generadoctos 1");

        oficio = getConstanciaFirmado();
        oficio2 = getAcuseReciboPromociones();
        oficio3 = getNotificacionCAL();
        oficio4 = getAcuseTerminosyCondicionesCal();

        Date fechaCalculada;
        try {
            fechaCalculada = fechaCapturaHelper.calcularFechaCaptura(new Date());
        }
        catch (ParseException e1) {
            fechaCalculada = new Date();
            logger.error("ocurrio error al generar documento: ", e1);
        }

        String clvTipoAsunto = null;
        if (claveTipoAsunto.equals(TipoAcuse.ATREQ.getClave())) {
            clvTipoAsunto = "8";
        }
        else {
            if (claveTipoAsunto.equals(TipoAcuse.RECPROM.getClave())) {
                clvTipoAsunto = "1";
            }
            else {
                if (claveTipoAsunto.equals(TipoAcuse.RDOCAD.getClave())) {
                    clvTipoAsunto = "0";
                }
            }
        }
        solicitudService.sellaDocumentos(idSolicitud);

        parameters.put("cadenaOriginal", cadenaOriginal);
        parameters.put("paramSello", sello);
        parameters.put("cadenaOriginalSIAT", cadenaOriginalSIAT);
        parameters.put("paramSelloSIAT", selloSIAT);
        parameters.put("URLVERIFICACION", urlVerificador);
        try {
            parameters.put("urlEncodedRfc", URLEncoder.encode(rfcSolicitante, "UTF-8"));
        }
        catch (UnsupportedEncodingException ue) {
            logger.error("ocurrio error al codificar el rfc: ", ue);
        }
        parameters.put("fecha", fechaCalculada);

        if (oficio != null && oficio2 != null) {
            oficio.setReportFormat(ReportFormat.PDF);
            oficio2.setReportFormat(ReportFormat.PDF);
            oficio3.setReportFormat(ReportFormat.PDF);
            oficio4.setReportFormat(ReportFormat.PDF);
            parameters.put("BASE_PATH", buildRealPath(File.separator + RESOURCES + File.separator + "css"
                    + File.separator + "images" + File.separator));
            parameters.put("idSolicitud", idSolicitud);
            parameters.put("FILE_SEPARATOR", File.separator);
            parameters.put("tipoAcuse", clvTipoAsunto);
            parameters.put("QR_VALUE", (format.format(tramite.getFechaRecepcion())));
            parameters.put("SUBREPORT_DIR", buildRealPath(File.separator + RESOURCES + File.separator + "util"
                    + File.separator + "reporte" + File.separator + "CAL" + File.separator));
            parameters.put("EDOCONTRIBUYENTE", estadoContribuyente);
            parameters.put("TIPOCONTRIBUYENTE", tipoContribuyente);
            oficio.setParameters(parameters);
            oficio2.setParameters(parameters);
            oficio3.setParameters(parameters);
            oficio4.setParameters(parameters);
            final String urlJasper = buildRealPath(oficio.getUrl());
            final String urlJasper2 = buildRealPath(oficio2.getUrl());
            final String urlJasper3 = buildRealPath(oficio3.getUrl());
            final String urlJasper4 = buildRealPath(oficio4.getUrl());
            logger.debug("urlJasper[{}]", urlJasper);
            String nombreDoc = rfcSolicitante + "solConstancia" + fechaCalculada.getTime() + PDF;
            String nombreDoc2 = rfcSolicitante + "solProm" + fechaCalculada.getTime() + PDF;
            String nombreDoc3 = rfcSolicitante + "formatoSolProm" + fechaCalculada.getTime() + PDF;
            String nombreDoc4 = rfcSolicitante + "acuse" + fechaCalculada.getTime() + PDF;

            try {

                if (tieneDocsAnexados) {
                    byte[] byteOficio1 = oficio.getByteArrayOutputStream(urlJasper).toByteArray();
                    String hashArchivo1 = cifradoService.genrerarHASHAchivo(byteOficio1);
                    byteOficio1 = cifradoService.cifrarAES(byteOficio1, nombreDoc);
                    ByteArrayInputStream bai = new ByteArrayInputStream(byteOficio1);
                    azureCifradoService.upload(bai, nombreDoc);
                    idDocumentos.add(guardarDocOficial(numAsunto, nombreDoc,
                            TipoDocumentoOficial.CONSTANCIA_RECEPCION_DOCUMENTO.getDescripcion(),
                            TipoDocumentoOficial.CONSTANCIA_RECEPCION_DOCUMENTO.getClave(), hashArchivo1,
                            byteOficio1.length));
                    bai.close();
                }

                byte[] byteOficio2 = oficio2.getByteArrayOutputStream(urlJasper2).toByteArray();

                String hashArchivo2 = cifradoService.genrerarHASHAchivo(byteOficio2);
                byteOficio2 = cifradoService.cifrarAES(byteOficio2, nombreDoc2);
                ByteArrayInputStream bai2 = new ByteArrayInputStream(byteOficio2);
                azureCifradoService.upload(bai2, nombreDoc2);
                idDocumentos.add(guardarDocOficial(numAsunto, nombreDoc2,
                        TipoDocumentoOficial.ACUSE_RECEPCION_PROMOCION.getDescripcion(),
                        TipoDocumentoOficial.ACUSE_RECEPCION_PROMOCION.getClave(), hashArchivo2, byteOficio2.length));
                bai2.close();

                byte[] byteOficio3 = oficio3.getByteArrayOutputStream(urlJasper3).toByteArray();
                String hashArchivo3 = cifradoService.genrerarHASHAchivo(byteOficio3);
                byteOficio3 = cifradoService.cifrarAES(byteOficio3, nombreDoc3);
                ByteArrayInputStream bai3 = new ByteArrayInputStream(byteOficio3);
                azureCifradoService.upload(bai3, nombreDoc3);
                idDocumentos.add(guardarDocOficial(numAsunto, nombreDoc3,
                        TipoDocumentoOficial.FORMATO_SOLICITUD_PROMOCION.getDescripcion(),
                        TipoDocumentoOficial.FORMATO_SOLICITUD_PROMOCION.getClave(), hashArchivo3, byteOficio3.length));
                bai3.close();

                SolicitudDatosGenerales solicitud =
                        solicitudService.obtenerSolicitudConsultaAutorizacionporId(idSolicitud);
                if (claveTipoAsunto.equals(TipoAcuse.RECPROM.getClave())
                        && !TipoRol.OFICIAL_PARTES.getClave().equals(solicitud.getCveRolCapturista())) {
                    byte[] byteOficio4 = oficio4.getByteArrayOutputStream(urlJasper4).toByteArray();
                    String hashArchivo4 = cifradoService.genrerarHASHAchivo(byteOficio4);
                    byteOficio4 = cifradoService.cifrarAES(byteOficio4, nombreDoc4);
                    ByteArrayInputStream bai4 = new ByteArrayInputStream(byteOficio4);
                    azureCifradoService.upload(bai4, nombreDoc4);
                    idDocumentos.add(guardarDocOficial(numAsunto, nombreDoc4,
                            TipoDocumentoOficial.OFICIO_TERMINOS_CONDICIONES.getDescripcion(),
                            TipoDocumentoOficial.OFICIO_TERMINOS_CONDICIONES.getClave(), hashArchivo4,
                            byteOficio4.length));
                    bai4.close();
                }

            }
            catch (Exception e) {
                logger.error("", e);
            }

        }
        return idDocumentos;
    }

    private Long guardarDocOficial(String numeroAsunto, String ruta, String nombreDoc, String tipo, String hash,
            long longitudBytes) {
        Long idDoc;
        DocumentoOficial documentoOficial = new DocumentoOficial();
        documentoOficial.setUrlDocumento(ruta);
        documentoOficial.setFechaCreacion(new Date());
        documentoOficial.setNumFolioOficio(numeroAsunto);
        documentoOficial.setDescripcionDocumento(nombreDoc);
        documentoOficial.setIdeTipoDocOficial(tipo);
        documentoOficial.setHash(hash);
        documentoOficial.setLongitud(longitudBytes);
        documentoOficial.setEstadoDocumento(EstadoDocumento.FIRMADO.getClave());
        documentoOficial.setBlnActivo(1);
        documentoOficial.setNumFolioOficio(EstadoDocumento.GENERADO.getClave());
        idDoc = autorizarRemitirService.guardarDoc(numeroAsunto, documentoOficial);
        return idDoc;
    }

    protected String buildRealPath(final String file) {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        return null == file || file.trim().isEmpty() ? "" : ctx.getRealPath("/") + file;
    }

    /**
     * @return the acuseReciboPromociones
     */
    public MultiFormatReportVU getAcuseReciboPromociones() {
        return acuseReciboPromociones;
    }

    /**
     * @param acuseReciboPromociones
     *            the acuseReciboPromociones to set
     */
    public void setAcuseReciboPromociones(MultiFormatReportVU acuseReciboPromociones) {
        this.acuseReciboPromociones = acuseReciboPromociones;
    }

    /**
     * @return the constanciaFirmado
     */
    public MultiFormatReportVU getConstanciaFirmado() {
        return constanciaFirmado;
    }

    /**
     * @param constanciaFirmado
     *            the constanciaFirmado to set
     */
    public void setConstanciaFirmado(MultiFormatReportVU constanciaFirmado) {
        this.constanciaFirmado = constanciaFirmado;
    }

    /**
     * @return the notificacionCAL
     */
    public MultiFormatReportVU getNotificacionCAL() {
        return notificacionCAL;
    }

    /**
     * @param notificacionCAL
     *            the notificacionCAL to set
     */
    public void setNotificacionCAL(MultiFormatReportVU notificacionCAL) {
        this.notificacionCAL = notificacionCAL;
    }

    public MultiFormatReportVU getAcuseTerminosyCondiciones() {
        return acuseTerminosyCondiciones;
    }

    public void setAcuseTerminosyCondiciones(MultiFormatReportVU acuseTerminosyCondiciones) {
        this.acuseTerminosyCondiciones = acuseTerminosyCondiciones;
    }

    public MultiFormatReportVU getAcuseTerminosyCondicionesCal() {
        return acuseTerminosyCondicionesCal;
    }

    public void setAcuseTerminosyCondicionesCal(MultiFormatReportVU acuseTerminosyCondicionesCal) {
        this.acuseTerminosyCondicionesCal = acuseTerminosyCondicionesCal;
    }

}
