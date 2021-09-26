/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.util.helper;

import mx.gob.sat.siat.juridica.base.dao.*;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoClasificacionArancelaria;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import mx.gob.sat.siat.juridica.ca.dao.domain.model.SolicitudConsultaAutorizacion;
import mx.gob.sat.siat.juridica.ca.util.constants.CadenasConstants;
import mx.gob.sat.siat.juridica.rrl.dao.SolicitudDAO;
import mx.gob.sat.siat.juridica.rrl.util.recurso.ResolucionesModelComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 */
@Component
public class GenerarCadenasHelper extends BaseHelper {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 5003474672909187765L;

    /**
     * Atributo privado "hourFormat" tipo DateFormat
     */
    private DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * Atributo privado "dateFormat" tipo DateFormat
     */
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Atributo privado "personaDao" tipo PersonaDao
     */
    @Autowired
    private PersonaDao personaDao;

    /**
     * Atributo privado "tramiteDao" tipo TramiteDao
     */
    @Autowired
    private TramiteDao tramiteDao;

    @Autowired
    private DocumentoDao documentoDao;

    @Autowired
    private SolicitudDAO solicitudDao;

    @Autowired
    private ResolucionDao resolucionDao;

    @Autowired
    private CatalogoDDao catalogoDDao;

    @Autowired
    private EnumeracionDao enumeracionDao;

    @Autowired
    private NotificacionDao notificacionDao;

    @Autowired
    private RequerimientoDao requerimientoDao;

    @Autowired
    private UnidadAdministrativaDao unidadAdministrativaDao;

    public String generaCadenaRegistroCAL(SolicitudConsultaAutorizacion sol, List<PersonaSolicitud> listaPersonas,
            String descAutHechos, String descAutSujetoEjercicio, List<FraccionDuda> fraccionDuda) {

        StringBuffer cadena = new StringBuffer();

        cadena.append(sol.getGranContribuyente() != null ? sol.getGranContribuyente() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(sol.getMonto() != null ? sol.getMonto() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(sol.getDescribirActividad() != null ? sol.getDescribirActividad() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        cadena.append(""); 
                           // Hechos (dejar el espacio en blanco)
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        cadena.append(""); 
                           // Razones de Negocio (dejar el espacio en
                           // blanco)
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        for (PersonaSolicitud personaSolicitud : listaPersonas) {
            if (personaSolicitud instanceof PersonaOirRecibirNotificaciones) {
                cadena.append(personaSolicitud.getNombre() != null ? personaSolicitud.getNombre() : "");
                cadena.append(CadenasConstants.SEPARADOR_CADENA);
                cadena.append(personaSolicitud.getApellidoPaterno() != null ? personaSolicitud.getApellidoPaterno()
                        : "");
                cadena.append(CadenasConstants.SEPARADOR_CADENA);
                cadena.append(personaSolicitud.getApellidoMaterno() != null ? personaSolicitud.getApellidoMaterno()
                        : "");
                cadena.append(CadenasConstants.SEPARADOR_CADENA);
                cadena.append(personaSolicitud.getRfc() != null ? personaSolicitud.getRfc() : "");
                cadena.append(CadenasConstants.SEPARADOR_CADENA);
                cadena.append(personaSolicitud.getTelefono() != null ? personaSolicitud.getTelefono() : "");
                cadena.append(CadenasConstants.SEPARADOR_CADENA);
            }
            else if (personaSolicitud instanceof PersonaResidenteExtranjero) {
                if (personaSolicitud.getRazonSocial() != null) {
                    cadena.append("Moral"); 
                    // Obtener tipo persona
                    cadena.append(CadenasConstants.SEPARADOR_CADENA);
                    cadena.append(personaSolicitud.getRazonSocial());
                    cadena.append(CadenasConstants.SEPARADOR_CADENA);
                }
                else {
                    cadena.append("F\u00EDsica");
                                                  // Obtener tipo
                                                  // persona
                    cadena.append(CadenasConstants.SEPARADOR_CADENA);
                    cadena.append(personaSolicitud.getNombre() != null ? personaSolicitud.getNombre() : "");
                    cadena.append(CadenasConstants.SEPARADOR_CADENA);
                    cadena.append(personaSolicitud.getApellidoPaterno() != null ? personaSolicitud.getApellidoPaterno()
                            : "");
                    cadena.append(CadenasConstants.SEPARADOR_CADENA);
                    cadena.append(personaSolicitud.getApellidoMaterno() != null ? personaSolicitud.getApellidoMaterno()
                            : "");
                    cadena.append(CadenasConstants.SEPARADOR_CADENA);
                }
                cadena.append(personaSolicitud.getNss() != null ? personaSolicitud.getNss() : "");
                cadena.append(CadenasConstants.SEPARADOR_CADENA);
                cadena.append(personaSolicitud.getDomicilio() != null
                        && personaSolicitud.getDomicilio().getDescripcionUbicacion() != null ? personaSolicitud
                        .getDomicilio().getDescripcionUbicacion() : "");
                cadena.append(CadenasConstants.SEPARADOR_CADENA);
            }

        }

        // RVCD_SOL_DATOSGEN -- hechos planeados previamente
        cadena.append(sol.getHecho() != null ? sol.getHecho() : ""); 
                                                                     // Hechos
                                                                     // planteados
                                                                     // previamente
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(descAutHechos != null ? descAutHechos : "");
                                                                   // Autoridad
                                                                   // que
                                                                   // conocio
                                                                   // el
                                                                   // asunto
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(sol.getFechHecho() != null ? dateFormat.format(sol.getFechHecho()) : "");
                                                                                                // Fecha
                                                                                                // de
                                                                                                // la
                                                                                                // promocion
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        // RVCD_SOL_DATOSGEN/MEDIODEFENSA -- Medios ante la misma
        // autoridad
        cadena.append(sol.getMedioDefensa() != null ? sol.getMedioDefensa() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(sol.getMedioDefensaHelper() != null && sol.getMedioDefensaHelper().getTipoMedioDefensa() != null
                ? sol.getMedioDefensaHelper().getTipoMedioDefensa().getDescripcion() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(sol.getMedioDefensaHelper() != null ? sol.getMedioDefensaHelper().getNumeroAsunto() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        CatalogoD catd = null;

        String cadenaCatD = "";
        if (sol.getMedioDefensaHelper() != null && sol.getMedioDefensaHelper().getSentidoResolucion() != null) {
            catd = catalogoDDao.obtenerCatalogoPorClave(sol.getMedioDefensaHelper().getSentidoResolucion());
            if (catd != null) {
                cadenaCatD = catd.getDescripcionGenerica1();
            }
        }
        cadena.append(cadenaCatD);
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(sol.getMedioDefensaHelper() != null ? sol.getMedioDefensaHelper().getDescAutoridad() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        // Sujeto a comprobacion
        cadena.append(sol.getSujetoEjercicio() != null ? sol.getSujetoEjercicio() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        cadena.append(""); 
                           // Periodos y contribuciones(dejar el
                           // espacio en blanco)
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(sol.getDentroPlazo() != null ? sol.getDentroPlazo() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(descAutSujetoEjercicio != null ? descAutSujetoEjercicio : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        cadena.append(sol.getIdeClasifArancelaria() != null
                && TipoClasificacionArancelaria.parse(sol.getIdeClasifArancelaria()) != null
                && TipoClasificacionArancelaria.parse(sol.getIdeClasifArancelaria()).getDescripcion() != null
                ? TipoClasificacionArancelaria.parse(sol.getIdeClasifArancelaria()).getDescripcion() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(sol.getDescNombreMercancia() != null ? sol.getDescNombreMercancia() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(sol.getFraccionAplicable() != null ? sol.getFraccionAplicable() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        for (FraccionDuda fraccion : fraccionDuda) {
            cadena.append(fraccion.getFraccionDuda() != null ? fraccion.getFraccionDuda() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
        }
        return cadena.toString();
    }

    /**
     * Metodo para generar la cadena de un documento
     * 
     * @param documento
     * @return cadena
     */
    public String generaCadenaDocumento(List<Documento> listaDocumentos) {
        StringBuffer cadena = new StringBuffer();

        for (Documento documento : listaDocumentos) {
            TipoDocumento docto = documentoDao.obtenerTipoDocumentoPorId(documento.getIdTipoDocumento());
            cadena.append((docto != null && docto.getNombre() != null) ? docto.getNombre() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            cadena.append(documento.getHash() != null ? documento.getHash() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
        }
        return cadena.toString();
    }

    /**
     * Metodo para generar la cadena de documentos oficiales
     * 
     * @param documento
     * @return cadena
     */
    public String generaCadenaDocumentoOficial(List<DocumentoOficial> listaDocumentos) {
        StringBuffer cadena = new StringBuffer();

        for (DocumentoOficial documento : listaDocumentos) {
            EnumeracionTr enumTipoDoc = enumeracionDao.obtenerEnumeracionPorCveEnum(documento.getIdeTipoDocOficial());
            cadena.append((enumTipoDoc != null && enumTipoDoc.getDescripcion() != null) ? enumTipoDoc.getDescripcion()
                    : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            cadena.append(documento.getHash() != null ? documento.getHash() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
        }
        return cadena.toString();
    }

    /**
     * Metodo para generar las cadenas del registro de una solicitud
     * 
     * @param sol
     * @return Cadena comun de solicitante, parte comun: |inicia
     *         cadena|...|termina cadena|
     */
    public String generarCadenasRegistroSolicitud(long idSolicitud, Date fecha) {
        StringBuffer cadena = new StringBuffer();

        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(idSolicitud);
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(generarCadenaComunRegistroPromocion(idSolicitud, fecha));

        return cadena.toString();
    }

    /**
     * Metodo para generar las cadenas del registro de una solicitud
     * cuando firma el SIAT - Juridica
     * 
     * @param sol
     * @return
     */
    public String generarCadenasRegistroPromocion(String numAsunto, Date fecha) {
        StringBuffer cadena = new StringBuffer();
        Tramite tramite = null;

        if (numAsunto != null) {
            tramite = tramiteDao.obtenerTramitePorId(numAsunto);
        }
        if (tramite != null) {
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            cadena.append(numAsunto != null ? numAsunto : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            cadena.append(generarCadenaComunRegistroPromocion(tramite.getSolicitud().getIdSolicitud(), fecha));
        }

        return cadena.toString();

    }

    /**
     * Metodo para generar el segmento de la cadena original con datos
     * de la promocion de RRL
     * 
     * @param idSolicitud
     * @return segmento de la cadena original con datos de la
     *         promocion de RRL datosPromocion|
     */
    public String generarCadenaDatosPromocionRRL(long idSolicitud) {
        StringBuffer cadena = new StringBuffer();
        SolicitudDatosGenerales sol = solicitudDao.obtenerSolicitudDatosGeneralesPorId(idSolicitud);

        UnidadAdministrativa uaBalanceo = null;
        if (sol.getUnidadAdminBalanceo() != null) {
            uaBalanceo = unidadAdministrativaDao.obtenerUnidadPorId(sol.getUnidadAdminBalanceo());
        }

        cadena.append(uaBalanceo != null && uaBalanceo.getNombre() != null ? uaBalanceo.getNombre() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        return cadena.toString();
    }

    /**
     * Metodo para generar las parte comun de la cadena de registro de
     * promocion/solicitud
     * 
     * Fecha de Firma (dd/mm/aaaa) Hora Firma (hh:mm:ss) Persona
     * Fisica (solo si el solicitante es una Persona Fisica) Nombre
     * Apellido Paterno Apellido Materno Persona Moral (solo si el
     * solicitante es una Persona Moral) Denominacion o razon social
     * Registro Federal del Contribuyente Correo electronico Telefono
     * Representante Legal Administracion Local Domicilio fiscal del
     * Contribuyente Calle Numero exterior Numero interior Entidad
     * federativa Delegacion o municipio Colonia Codigo postal
     * 
     * @param idSolicitud
     *            Id de la solicitud
     * @return Cadena original comun de registro sin pipe de inicio ni
     *         de terminacion campo1|campo2|...|campoN
     */
    private String generarCadenaComunRegistroPromocion(long idSolicitud, Date fecha) {
        StringBuffer cadena = new StringBuffer();
        Solicitud sol = solicitudDao.obtenerSolicitudporId(idSolicitud);
        Solicitante persona = (Solicitante) personaDao.buscarPersonaSolicitante(sol.getIdSolicitud());
        RepresentanteLegal representanteLegal = personaDao.buscarPersonaRepresentanteLegal(sol.getIdSolicitud());
        /* Falta el dato relacionado con el domicilio y la persona */
        DomicilioSolicitud domicilioSolicitud = persona.getDomicilio();

        cadena.append(fecha != null ? dateFormat.format(fecha) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(fecha != null ? hourFormat.format(fecha) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        if (persona.getRazonSocial() != null) {
            cadena.append(persona.getRazonSocial());
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
        }
        else {
            cadena.append(persona.getNombre() != null ? persona.getNombre() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            cadena.append(persona.getApellidoPaterno() != null ? persona.getApellidoPaterno() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            cadena.append(persona.getApellidoMaterno() != null ? persona.getApellidoMaterno() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
        }
        cadena.append(persona.getRfc() != null ? persona.getRfc() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        if (persona.getCorreoElectronico() != null) {

            String[] correos = persona.getCorreoElectronico().split("\n");
            for (int i = 0; i < correos.length; i++) {
                if (i != 0) {
                    cadena.append(CadenasConstants.SEPARADOR_CORREO);
                }
                cadena.append(correos[i]);

            }

        }

        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona.getTelefono() != null ? persona.getTelefono() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        // Representante Legal
        cadena.append(representanteLegal != null && representanteLegal.getNombre() != null ? representanteLegal
                .getNombre() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(representanteLegal != null && representanteLegal.getApellidoPaterno() != null
                ? representanteLegal.getApellidoPaterno() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(representanteLegal != null && representanteLegal.getApellidoMaterno() != null
                ? representanteLegal.getApellidoMaterno() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        // Administracion Local (se guarda a nivel del domicilio del
        // solicitante)
        UnidadAdministrativa uaLocalRecaudadora = null;
        if (domicilioSolicitud != null && domicilioSolicitud.getClaveAdministracionLocalRecaudadora() != null) {
            uaLocalRecaudadora =
                    unidadAdministrativaDao.obtenerUnidadPorClaveLocal(domicilioSolicitud
                            .getClaveAdministracionLocalRecaudadora());
        }
        cadena.append(uaLocalRecaudadora != null && uaLocalRecaudadora.getNombre() != null ? uaLocalRecaudadora
                .getNombre() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        // Domicilio fiscal del Contribuyente (Nacional, persona
        // fisica o moral).
        cadena.append(domicilioSolicitud != null && domicilioSolicitud.getCalle() != null ? domicilioSolicitud
                .getCalle() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(domicilioSolicitud != null && domicilioSolicitud.getNumeroExterior() != null ? domicilioSolicitud
                .getNumeroExterior() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(domicilioSolicitud != null && domicilioSolicitud.getNumeroInterior() != null ? domicilioSolicitud
                .getNumeroInterior() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        // Entidad federativa
        if (domicilioSolicitud != null && domicilioSolicitud.getEntidadFederativa() != null) {
            cadena.append(domicilioSolicitud.getEntidadFederativa().getNombre() != null ? domicilioSolicitud
                    .getEntidadFederativa().getNombre() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
        }
        else {
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
        }
        // Delegacion o municipio
        if (domicilioSolicitud != null && domicilioSolicitud.getDelegacionMunicipio() != null) {
            cadena.append(domicilioSolicitud.getDelegacionMunicipio().getNombre() != null ? domicilioSolicitud
                    .getDelegacionMunicipio().getNombre() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
        }
        else {
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
        }
        // Colonia
        if (domicilioSolicitud != null && domicilioSolicitud.getColonia() != null) {
            cadena.append(domicilioSolicitud.getColonia().getNombre() != null ? domicilioSolicitud.getColonia()
                    .getNombre() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
        }
        else {
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
        }
        cadena.append(domicilioSolicitud != null && domicilioSolicitud.getCodigoPostal() != null ? domicilioSolicitud
                .getCodigoPostal() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        return cadena.toString();

    }

    /**
     * Metodo para generar las cadenas de atender requerimiento
     * 
     * @param solicitud
     * @return
     */
    public String generarCadenaAtenderRequerimiento(long solicitud, Date fechaFirma) {
        StringBuffer cadena = new StringBuffer();

        Tramite tramite = tramiteDao.obtenerTramitePorIdSolicitud(Long.valueOf(solicitud));

        if (tramite != null) {
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            cadena.append(tramite.getNumeroAsunto() != null ? tramite.getNumeroAsunto() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            cadena.append(generarCadenaComunRegistroPromocion(tramite.getSolicitud().getIdSolicitud(), fechaFirma));
        }

        return cadena.toString();

    }

    /**
     * Metodo para generar la cadena de atenci&oacute;n del
     * requerimiento a autoridad interna (Retroalimentacion)
     * 
     * @param numeroAsunto
     *            N&uacute;mero de asunto
     * @param fechaFirma
     *            Fecha y hora en que se firma la atenci&oacute;n del
     *            requerimiento
     * @param rfcFuncionario
     *            RFC del funcionario que atiende la
     *            retroalimentacion/requerimiento
     * @return Cadena con los datos de la
     *         retroalimentaci&oacute;n/requerimiento a autoridad
     *         |valor1|valor2|...|valorN|
     * 
     *         N&uacute;mero de Asunto SIAT-Jur&iacute;dica (13
     *         posiciones) Fecha de firma de la atenci&oacute;n del
     *         requerimiento (dd/mm/aaaa) Hora de firma de la
     *         atenci&oacute;n del requerimiento (hh:mm:ss) Datos
     *         Funcionario Registro Federal del Contribuyente del
     *         Funcionario que firma Nombre del Funcionario Apellido
     *         paterno del Funcionario Apellido materno del
     *         Funcionario
     */
    public String generarCadenaDatosAtencionRetroalimentacion(String numeroAsunto, Date fechaFirma,
            String rfcFuncionario) {
        StringBuffer cadena = new StringBuffer();

        Persona persona = personaDao.obtenerPersona(rfcFuncionario, DiscriminadorConstants.PERSONA_INTERNA);

        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(numeroAsunto != null ? numeroAsunto : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(fechaFirma != null ? dateFormat.format(fechaFirma) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(fechaFirma != null ? hourFormat.format(fechaFirma) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(rfcFuncionario != null ? rfcFuncionario : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona != null && persona.getNombre() != null ? persona.getNombre() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona != null && persona.getApellidoPaterno() != null ? persona.getApellidoPaterno() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona != null && persona.getApellidoMaterno() != null ? persona.getApellidoMaterno() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        return cadena.toString();

    }

    /**
     * Metodo para generar las dadenas de la confirmacion de
     * notificacion
     * 
     * @param solicitud
     * @return
     */
    public String generarCadenaConfirmacionNotificacionRetroalimentacion(Solicitud solicitud, Long idRequerimiento,
            Date fechaFirma, String rfcUsuario) {
        StringBuffer cadena = new StringBuffer();
        Persona persona = personaDao.obtenerPersona(rfcUsuario, DiscriminadorConstants.PERSONA_INTERNA);

        Tramite tramite = tramiteDao.obtenerTramitePorIdSolicitud(solicitud.getIdSolicitud());
        NotificacionRequerimiento notificacion = notificacionDao.obtenerNotificacionByIdRequerimiento(idRequerimiento);
        Requerimiento requerimiento = requerimientoDao.obtenerRequerimientoPorId(idRequerimiento);

        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(tramite.getNumeroAsunto() != null ? tramite.getNumeroAsunto() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(notificacion != null && notificacion.getFechaEnvioNotificacion() != null ? dateFormat
                .format(notificacion.getFechaEnvioNotificacion()) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(notificacion != null && notificacion.getFechaEnvioNotificacion() != null ? hourFormat
                .format(notificacion.getFechaEnvioNotificacion()) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(fechaFirma != null ? dateFormat.format(fechaFirma) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(fechaFirma != null ? hourFormat.format(fechaFirma) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(rfcUsuario != null ? rfcUsuario : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona != null && persona.getNombre() != null ? persona.getNombre() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona != null && persona.getApellidoPaterno() != null ? persona.getApellidoPaterno() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona != null && persona.getApellidoMaterno() != null ? persona.getApellidoMaterno() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(requerimiento != null && requerimiento.getUnidadAdministrativa() != null
                && requerimiento.getUnidadAdministrativa().getNombre() != null ? requerimiento
                .getUnidadAdministrativa().getNombre() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(DiscriminadorConstants.ASUNTO_NOTIFICACION_REQUERIMIENTO);
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        return cadena.toString();
    }

    /**
     * Metodo para generar una cadena de autorizacion de una
     * resolucion de CAL
     * 
     * @param solicitud
     * @return
     */
    public String generarCadenaGenerarAutorizarResolucionCAL(Solicitud solicitud, ResolucionDatosGenerados datosRes,
            String rfcFuncionario, Date fechaFirma) {
        StringBuffer cadena = new StringBuffer();
        Persona persona = personaDao.obtenerPersona(rfcFuncionario, DiscriminadorConstants.PERSONA_INTERNA);
        /* Falta el dato relacionado con el domicilio y la persona */

        Tramite tramite = tramiteDao.obtenerTramitePorIdSolicitud(solicitud.getIdSolicitud());
        Resolucion resolucion = resolucionDao.obtenerResolucionIdTramite(tramite.getNumeroAsunto());

        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(tramite.getNumeroAsunto() != null ? tramite.getNumeroAsunto() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(resolucion.getFechaResolucion() != null ? dateFormat.format(fechaFirma) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(resolucion.getFechaResolucion() != null ? hourFormat.format(fechaFirma) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona != null && persona.getRfc() != null ? persona.getRfc() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona != null && persona.getNombre() != null ? persona.getNombre() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona != null && persona.getApellidoPaterno() != null ? persona.getApellidoPaterno() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona != null && persona.getApellidoMaterno() != null ? persona.getApellidoMaterno() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        // Sentido de la resolucion
        CatalogoD catd = null;
        String cadenaCatD = "";
        if (resolucion.getIdeSentidoResolucion() != null) {
            catd = catalogoDDao.obtenerCatalogoPorClave(resolucion.getIdeSentidoResolucion());
            if (catd != null) {
                cadenaCatD = catd.getDescripcionGenerica1();
            }
        }
        cadena.append(cadenaCatD);

        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        cadena.append(datosRes.getFechaOficio() != null ? dateFormat.format(datosRes.getFechaOficio()) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(datosRes.getNumeroOficio() != null ? datosRes.getNumeroOficio() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        return cadena.toString();
    }

    public String generarCadenaGenerarDatosClasif(ResolucionDatosGenerados datos) {
        StringBuffer cadena = new StringBuffer();
        cadena.append(datos.getFraccionArancelaria() != null ? datos.getFraccionArancelaria() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(datos.isConfirmaFraccion() ? "1" : "0");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(datos.getNumeroMinuta() != null ? datos.getNumeroMinuta() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(datos.getFechaSesion() != null ? dateFormat.format(datos.getFechaSesion()) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        return cadena.toString();
    }

    public String generarCadenaGenerarDatosResarcimiento(List<BienResarcimiento> bienesResarcimiento,
            ResolucionDatosGenerados datos, Resolucion resolucion) {
        StringBuffer cadena = new StringBuffer();

        EnumeracionTr datosEnum = null;
        for (BienResarcimiento bienes : bienesResarcimiento) {
            cadena.append(bienes.getValor() != null ? bienes.getValor() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            cadena.append(bienes.getDescripcionBien() != null ? bienes.getDescripcionBien() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            cadena.append(bienes.getMedioTransporte() != null ? bienes.getMedioTransporte() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
        }
        cadena.append(datos.getFechaEmbargo() != null ? dateFormat.format(datos.getFechaEmbargo()) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(datos.getUnidadAdministrativa() != null && datos.getUnidadAdministrativa().getNombre() != null
                ? datos.getUnidadAdministrativa().getNombre() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(resolucion.getMonto() != null ? resolucion.getMonto() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(datos.getMontoTotal() != null ? datos.getMontoTotal() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        datosEnum = null; 
        // medio de defensa
        if (datos.getTipoMedioPago() != null) {
            datosEnum = enumeracionDao.obtenerEnumeracionPorCveEnum(datos.getTipoMedioPago());
        }
        cadena.append(datosEnum != null ? datosEnum.getDescripcion() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        cadena.append(datos.getNumeroAsunto() != null ? datos.getNumeroAsunto() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(datos.isCumplimentacion() ? "1" : "0");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(datos.getMontoSentencia() != null ? datos.getMontoSentencia() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        return cadena.toString();
    }

    /**
     * Metodo para generar una cadena del oficio de una resolucion
     * 
     * @param solicitud
     * @return
     */
    public String generarCadenaOficioResolucion(Solicitud solicitud) {
        StringBuffer cadena = new StringBuffer();
        Persona persona = personaDao.obtenerPersonaPorId(solicitud.getIdPersonaSolicitante());
        /* Falta el dato relacionado con el domicilio y la persona */

        Solicitante personaSol = (Solicitante) personaDao.buscarPersonaSolicitante(solicitud.getIdSolicitud());

        Tramite tramite = tramiteDao.obtenerTramitePorIdSolicitud(solicitud.getIdSolicitud());
        Resolucion resolucion = new Resolucion();

        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(tramite.getNumeroAsunto());
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(solicitud.getTipoTramite().getModalidad());
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(dateFormat.format(tramite.getFechaRecepcion()));
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(hourFormat.format(tramite.getFechaRecepcion()));
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(personaSol.getRfc());
        if (persona.getRazonSocial() != null) {
            cadena.append(persona.getRazonSocial());
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            cadena.append(personaSol.getNss());
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
        }
        else {
            cadena.append(persona.getNombre());
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            cadena.append(persona.getApellidoPaterno());
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            cadena.append(persona.getApellidoMaterno());
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            cadena.append(persona.getCurp() != null ? persona.getCurp() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
        }
        cadena.append(solicitud.getUnidadAdministrativaRepresentacionFederal().getNombre());
        cadena.append(persona.getRfc());
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona.getNombre());
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona.getApellidoPaterno());
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona.getApellidoMaterno());
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(resolucion.getIdeSentidoResolucion());
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(resolucion.getDescripcion());
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        return cadena.toString();
    }

    /**
     * Metodo para generar una cadena de autorizacion de una
     * resolucion de CAL
     * 
     * @param solicitud
     * @return
     */
    public String
            generarCadenaGenerarAutorizarResolucionRRL(Solicitud solicitud, String rfcFuncionario, Date fechaFirma) {
        StringBuffer cadena = new StringBuffer();
        Persona persona = personaDao.obtenerPersona(rfcFuncionario, DiscriminadorConstants.PERSONA_INTERNA);
        /* Falta el dato relacionado con el domicilio y la persona */

        Tramite tramite = tramiteDao.obtenerTramitePorIdSolicitud(solicitud.getIdSolicitud());
        Resolucion resolucion = resolucionDao.obtenerResolucionIdTramite(tramite.getNumeroAsunto());
        List<ResolucionImpugnada> resolucionesImpugnadas =
                resolucionDao.obtenerResolucionesImpugnadas(resolucion.getIdResolucion());

        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(tramite.getNumeroAsunto() != null ? tramite.getNumeroAsunto() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(fechaFirma != null ? dateFormat.format(fechaFirma) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(fechaFirma != null ? hourFormat.format(fechaFirma) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona != null && persona.getRfc() != null ? persona.getRfc() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona != null && persona.getNombre() != null ? persona.getNombre() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona != null && persona.getApellidoPaterno() != null ? persona.getApellidoPaterno() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(persona != null && persona.getApellidoMaterno() != null ? persona.getApellidoMaterno() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        // Detalle de los datos de la resolucion
        ResolucionesModelComparator resolucionesComparator = new ResolucionesModelComparator();
        Collections.sort(resolucionesImpugnadas, resolucionesComparator);
        StringBuffer cadenaResImpugnadas = new StringBuffer();
        BigDecimal montoTotal = BigDecimal.ZERO;
        String sentidoResImportante = null;
        for (int i = 0; i < resolucionesImpugnadas.size(); i++) {
            ResolucionImpugnada resolucionImpugnada = resolucionesImpugnadas.get(i);
            if (i == 0 && resolucionImpugnada.getSentidoResolucionImpugnada() != null) {
                sentidoResImportante = resolucionImpugnada.getSentidoResolucionImpugnada().getDescripcionGenerica1();
            }
            montoTotal =
                    montoTotal.add(resolucionImpugnada.getMonto() != null ? resolucionImpugnada.getMonto()
                            : BigDecimal.ZERO);

            cadenaResImpugnadas.append(resolucionImpugnada.getNumeroFolioResolucionImpugnada() != null
                    ? resolucionImpugnada.getNumeroFolioResolucionImpugnada() : "");
            cadenaResImpugnadas.append(CadenasConstants.SEPARADOR_CADENA);
            cadenaResImpugnadas.append(resolucionImpugnada.getBlnDeterminanteCredito() != null ? (Integer.valueOf(1)
                    .equals(resolucionImpugnada.getBlnDeterminanteCredito()) ? "0" : "1") : "");
            cadenaResImpugnadas.append(CadenasConstants.SEPARADOR_CADENA);
            cadenaResImpugnadas.append(resolucionImpugnada.getMonto() != null ? resolucionImpugnada.getMonto() : "");
            cadenaResImpugnadas.append(CadenasConstants.SEPARADOR_CADENA);
            cadenaResImpugnadas.append(resolucionImpugnada.getSentidoResolucionImpugnada() != null
                    ? resolucionImpugnada.getSentidoResolucionImpugnada().getDescripcionGenerica1() : "");
            cadenaResImpugnadas.append(CadenasConstants.SEPARADOR_CADENA);
        }
        cadena.append(sentidoResImportante != null ? sentidoResImportante : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(!montoTotal.equals(BigDecimal.ZERO) ? montoTotal : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(cadenaResImpugnadas.toString());

        return cadena.toString();
    }

    public String generarCadenaAutorizarRemision(Remision remision, Solicitud solicitud, String rfcAutorizador) {
        StringBuffer cadena = new StringBuffer();

        Persona personaAutorizador = personaDao.obtenerPersona(rfcAutorizador, DiscriminadorConstants.PERSONA_INTERNA);
        Tramite tramite = tramiteDao.obtenerTramitePorIdSolicitud(solicitud.getIdSolicitud());

        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(tramite.getNumeroAsunto() != null ? tramite.getNumeroAsunto() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(remision.getFechaAutorizacion() != null ? dateFormat.format(remision.getFechaAutorizacion()) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(remision.getFechaAutorizacion() != null ? hourFormat.format(remision.getFechaAutorizacion()) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(rfcAutorizador != null ? rfcAutorizador : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(personaAutorizador != null && personaAutorizador.getNombre() != null ? personaAutorizador
                .getNombre() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(personaAutorizador != null && personaAutorizador.getApellidoPaterno() != null
                ? personaAutorizador.getApellidoPaterno() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(personaAutorizador != null && personaAutorizador.getApellidoMaterno() != null
                ? personaAutorizador.getApellidoMaterno() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(remision.getUnidadAdminQueRemite() != null ? remision.getUnidadAdminQueRemite().getNombre() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(remision.getUnidadAdminNueva() != null ? remision.getUnidadAdminNueva().getNombre() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        return cadena.toString();
    }

    public String generarCadenaGenerarAutorizarRequerimientoCAL(Requerimiento requerimiento, Tramite tramite,
            String rfcAutorizador) {
        StringBuffer cadena = new StringBuffer();

        Persona personaAutorizador = personaDao.obtenerPersona(rfcAutorizador, DiscriminadorConstants.PERSONA_INTERNA);

        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(tramite.getNumeroAsunto());
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(dateFormat.format(new Date()));
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(hourFormat.format(new Date()));
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(rfcAutorizador != null ? rfcAutorizador : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(personaAutorizador != null && personaAutorizador.getNombre() != null ? personaAutorizador
                .getNombre() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(personaAutorizador != null && personaAutorizador.getApellidoPaterno() != null
                ? personaAutorizador.getApellidoPaterno() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(personaAutorizador != null && personaAutorizador.getApellidoMaterno() != null
                ? personaAutorizador.getApellidoMaterno() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        return cadena.toString();
    }

    public String generarCadenaDocumentosAdicionales(Tramite tramite, Solicitud solicitud, Date fechaFirma) {
        StringBuffer cadena = new StringBuffer();

        Solicitante persona = (Solicitante) personaDao.buscarPersonaSolicitante(solicitud.getIdSolicitud());
        List<DocumentoSolicitud> documentos = documentoDao.obtenerDocumentosAnexados(solicitud.getIdSolicitud());

        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(tramite.getNumeroAsunto() != null ? tramite.getNumeroAsunto() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(fechaFirma != null ? dateFormat.format(fechaFirma) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);
        cadena.append(fechaFirma != null ? hourFormat.format(fechaFirma) : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        if (persona.getRazonSocial() != null) {
            cadena.append(persona.getRazonSocial());
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
        }
        else {
            cadena.append(persona.getNombre() != null ? persona.getNombre() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            cadena.append(persona.getApellidoPaterno() != null ? persona.getApellidoPaterno() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            cadena.append(persona.getApellidoMaterno() != null ? persona.getApellidoMaterno() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
        }

        cadena.append(persona.getRfc() != null ? persona.getRfc() : "");
        cadena.append(CadenasConstants.SEPARADOR_CADENA);

        if (documentos != null && !documentos.isEmpty()) {
            for (DocumentoSolicitud documentoSol : documentos) {
                TipoDocumento documento = documentoDao.obtenerTipoDocumentoPorId(documentoSol.getIdTipoDocumento());
                cadena.append(documento != null && documento.getNombre() != null ? documento.getNombre() : "");
                cadena.append(CadenasConstants.SEPARADOR_CADENA);
                cadena.append(documentoSol.getDocumento() != null && documentoSol.getDocumento().getHash() != null
                        ? documentoSol.getDocumento().getHash() : "");
                cadena.append(CadenasConstants.SEPARADOR_CADENA);
            }
        }

        return cadena.toString();
    }
}
