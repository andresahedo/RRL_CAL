/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.service.impl;

import mx.gob.sat.siat.juridica.base.dao.CatalogoDDao;
import mx.gob.sat.siat.juridica.base.dao.EnumeracionDao;
import mx.gob.sat.siat.juridica.base.dao.PersonaDao;
import mx.gob.sat.siat.juridica.base.dao.UnidadAdministrativaDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoAutoridad;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.service.DocumentosServices;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.base.util.helper.SelladoraHelper;
import mx.gob.sat.siat.juridica.ca.dao.MedioDefensaDAO;
import mx.gob.sat.siat.juridica.ca.dao.PersonaSolicitudDAO;
import mx.gob.sat.siat.juridica.ca.dao.RegistroSolicitudConsultaAutorizacionDAO;
import mx.gob.sat.siat.juridica.ca.dao.domain.model.SolicitudConsultaAutorizacion;
import mx.gob.sat.siat.juridica.ca.util.helper.GenerarCadenasHelper;
import mx.gob.sat.siat.juridica.ca.util.helper.RegistroSolicitudConsultaAutorizacionHelper;
import mx.gob.sat.siat.juridica.ca.util.validador.RegistroSolicitudConsultaAutorizacionValidator;
import mx.gob.sat.siat.juridica.cal.dao.FraccionDudaDAO;
import mx.gob.sat.siat.juridica.cal.service.RegistroSolicitudCALCommonServices;
import mx.gob.sat.siat.juridica.rrl.dao.RegistroRecursoRevocacionDAO;
import mx.gob.sat.siat.juridica.rrl.dao.SolicitudDAO;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import mx.gob.sat.siat.selladora.SelladoraException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Servicio para atender las peticiones del registro de la solicitud.
 * 
 * @author Softtek
 * 
 */
public abstract class RegistroSolicitudCALCommonServicesImpl extends BaseSerializableBusinessServices implements
        RegistroSolicitudCALCommonServices {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = -2146798797163477960L;

    @Autowired
    private SolicitudDAO solicitudDAO;

    @Autowired
    private MedioDefensaDAO medioDefensaDAO;

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

    @Autowired
    private GenerarCadenasHelper generarCadenasHelper;

    @Autowired
    private SelladoraHelper selladoraHelper;

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

    @Autowired
    private PersonaSolicitudDAO personaSolicitudDAO;

    @Autowired
    private FraccionDudaDAO fraccionDudaDAO;

    @Autowired
    private CatalogoDDao catalogoDDao;

    @Autowired
    private DocumentosServices documentosServices;

    @Autowired
    private RegistroRecursoRevocacionDAO registroRecursoRevocacionDAO;

    /**
     * Metodo par aobtener una lista de unidades administrativas
     * emisoras
     * 
     * @return List<UnidadAdministrativa>
     */
    public List<UnidadAdministrativa> obtenerAutoridadesEmisoras() {
        return unidadAdministrativaDao.obtenerUnidadesTodas();
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
    public List<TipoTramite> obtenerModalidadesPorSubservicio(String idSubservicio, String tipoPersona) {
        return registroSolicitudConsultaAutorizacionDAO.obtenerModalidadesPorSubservicio(idSubservicio, tipoPersona);
    }

    public TipoTramite obtenerTipoTramitePorId(String idTIpoTramite) {
        return registroSolicitudConsultaAutorizacionDAO.obtenerTipoTramitePorId(idTIpoTramite);
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

    public DocumentoTramite obtenerDocumentoRazonesLogicoJuridicas() {
        return registroSolicitudConsultaAutorizacionDAO.obtenerDocumentoRazonesLogicoJuridicas();
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
    public SolicitudConsultaAutorizacion
            guardarSolicitud(SolicitudConsultaAutorizacion solicitud, String rfcSolicitante)
                    throws SolicitudNoGuardadaException {
        MedioDefensa medioDefensa = solicitud.getMedioDefensaHelper();
        Solicitante solicitante = solicitud.getSolicitanteHelper();
        RepresentanteLegal representante = null;
        if (null != solicitud.getRepresentanteLegalHelper()) {
            representante = solicitud.getRepresentanteLegalHelper();
        }
        SolicitudDatosGenerales solBase = solicitudDAO.obtenerSolicitudDatosGeneralesPorId(solicitud.getIdSolicitud());
        getLogger().debug("rfcSolicitante {}", rfcSolicitante);
        Persona persona = obtenerPersonaPorRFC(rfcSolicitante);
        getLogger().debug("solicitud.getUnidadAdminBalanceo() {}", solicitud.getUnidadAdminBalanceo());
        registroSolicitudConsultaAutorizacionValidator.validarUnidadAdministrativaSolicitud(solicitud
                .getUnidadAdminBalanceo());
        getLogger().debug("solicitud.getIdeClasifArancelaria() {}", solicitud.getIdeClasifArancelaria());
        registroSolicitudConsultaAutorizacionValidator.validarFraccionesArancelarias(
                solicitud.getIdeClasifArancelaria(), solicitud.getFraccionAplicable(),
                solicitud.getFraccionDudaHelperList());
        getLogger().debug("paso las validaciones");
        solicitud.setIdPersonaSolicitante(persona.getIdPersona());

        SolicitudConsultaAutorizacion solicitudNueva = null;
        if (solBase == null) {
            UnidadAdministrativa unidadA =
                    unidadAdministrativaDao.obtenerUnidadPorId(solicitud.getUnidadAdministrativaRepresentacionFederal()
                            .getClave());

            solicitud.setUnidadAdministrativaRepresentacionFederal(unidadA);
            solicitud.setCondicionesUso(true);

            solicitud.setCveUsuarioCapturista(rfcSolicitante);
            getLogger().debug("va a crear la solicitud {}", solicitud);
            solicitudNueva = registroSolicitudConsultaAutorizacionDAO.crearSolicitud(solicitud);

            solicitante.setIdSolicitud(solicitudNueva.getIdSolicitud());
            getLogger().debug("va a crear la persona solicitante {}", solicitante);
            registroSolicitudConsultaAutorizacionDAO.crearPersonaSolicitud(solicitante);
            if (representante != null) {
                representante.setIdSolicitud(solicitudNueva.getIdSolicitud());
                getLogger().debug("va a crear la persona representanteLegal {}", representante);
                registroSolicitudConsultaAutorizacionDAO.crearPersonaSolicitud(representante);
            }
        }
        else {
            getLogger().debug("va a crear la solicitud {}", solicitud);
            solBase.setCosto(solicitud.getCosto());
            solBase.setDentroPlazo(solicitud.getDentroPlazo());
            solBase.setDepuracionDocProcesada(solicitud.getDepuracionDocProcesada());
            solBase.setDescNombreMercancia(solicitud.getDescNombreMercancia());
            solBase.setDescribirActividad(solicitud.getDescribirActividad());
            solBase.setDescribirHecho(solicitud.getDescribirHecho());
            solBase.setDescribirPeriodoContribucion(solicitud.getDescribirPeriodoContribucion());
            solBase.setDescribirRazon(solicitud.getDescribirRazon());
            solBase.setDescripcion(solicitud.getDescripcion());
            solBase.setFechaActualizacion(new Date());
            solBase.setFechHecho(solicitud.getFechHecho());
            solBase.setFraccionAplicable(solicitud.getFraccionAplicable());
            solBase.setGranContribuyente(solicitud.getGranContribuyente());
            solBase.setHecho(solicitud.getHecho());
            solBase.setIdeClasifArancelaria(solicitud.getIdeClasifArancelaria());
            solBase.setIdPeticionWS(solicitud.getIdPeticionWS());
            solBase.setMedioDefensa(solicitud.getMedioDefensa());
            solBase.setMonto(solicitud.getMonto());
            solBase.setNumeroSerieCertificado(solicitud.getNumeroSerieCertificado());
            solBase.setSujetoEjercicio(solicitud.getSujetoEjercicio());
            
            SolicitudConsultaAutorizacion temp = (SolicitudConsultaAutorizacion)solBase;
            temp.setEstadosManifiesto(solicitud.getEstadosManifiesto());
            
            solicitudNueva =  registroSolicitudConsultaAutorizacionDAO.crearSolicitud(temp);
        }
        if (medioDefensa != null) {
            medioDefensa.setIdSolicitud(solicitudNueva.getIdSolicitud());
            getLogger().debug("va a crear el medio de defensa{}", medioDefensa);
            solicitudNueva.setMedioDefensaHelper(registroSolicitudConsultaAutorizacionDAO.crearMedioDefensa(medioDefensa));
        }
        return solicitudNueva;
    }

    public void guardaAutoridad(Autoridad autoridad) {

        if (autoridad.getIdSolicitud() != null && autoridad.getIdTipoAutoridad() != null) {
            synchronized (this) {
                // Busca si hay autoridad del tipo actual y que sea la
                // misma
                Autoridad autBD =
                        registroSolicitudConsultaAutorizacionDAO.obtenerAutoridad(autoridad.getIdSolicitud(),
                                TipoAutoridad.parse(autoridad.getIdTipoAutoridad()));
                // Si no hay autoridad del tipo de "autoridad", o si
                // existe del mismo tipo pero con diferente UA
                if (autBD == null || !autoridad.getIdUniAdmin().equals(autBD.getIdUniAdmin())) {
                    if (autBD != null) {
                        autBD.setBlnActivo(false); 
                                                   // Baja logica, si
                                                   // hay autoridad en
                                                   // BD
                    }

                    autoridad.setBlnActivo(true);
                    registroSolicitudConsultaAutorizacionDAO.crearAutoridad(autoridad);

                }
            }
        }
    }

    public FraccionDuda guardarFraccionDuda(FraccionDuda fraccionDuda) {

        if (fraccionDuda.getIdFraccionDuda() == null) {
            registroSolicitudConsultaAutorizacionDAO.crearFraccionDuda(fraccionDuda);
        }
        else {
            registroSolicitudConsultaAutorizacionDAO.actualizaFraccionDuda(fraccionDuda);
        }
        return fraccionDuda;
    }

    public Autoridad obtenerAutoridad(Long idSolicitud, TipoAutoridad tipoAutoridad) {
        return registroSolicitudConsultaAutorizacionDAO.obtenerAutoridad(idSolicitud, tipoAutoridad);
    }

    public PersonaOirRecibirNotificaciones guardaPersonaOirNot(
            PersonaOirRecibirNotificaciones personaOirRecibirNotificaciones) {
        return (PersonaOirRecibirNotificaciones) registroSolicitudConsultaAutorizacionDAO
                .crearPersonaSolicitud(personaOirRecibirNotificaciones);
    }

    public PersonaResidenteExtranjero guardaResidenteExtranjero(PersonaResidenteExtranjero personaResidenteExtranjero) {
        return (PersonaResidenteExtranjero) registroSolicitudConsultaAutorizacionDAO
                .crearPersonaSolicitud(personaResidenteExtranjero);
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

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.service.RegistroSolicitudCALServices
     * #validarDocumentosRequeridos(java.util.List, java.util.List)
     */
    @Override
    public void validarDocumentosRequeridos(List<Documento> documentosS, List<Documento> documentosR)
            throws ArchivoNoGuardadoException {
        registroSolicitudConsultaAutorizacionValidator.validarDocumentosPorAnexarRequeridos(documentosS, documentosR);
    }

    /**
     * Metodo para guardar documentos
     * 
     * @param listaDocumentos
     * @param listaDocumentosSolicitud
     */
    public void guardarDocumentos(List<Documento> listaDocumentos, List<DocumentoSolicitud> listaDocumentosSolicitud) {

        documentosServices.guardaDocumento(listaDocumentos, listaDocumentosSolicitud);
    }

    /**
     * Metodo para guardado de documentos devolviendo la lista de
     * Documento persistida
     * 
     * @param listaDocumentos
     * @param listaDocumentosSolicitud
     */
    public List<DocumentoSolicitud> guardarParcialDocumentos(long idSolicitud, List<Documento> listaDocumentos,
            List<DocumentoSolicitud> listaDocumentosSolicitud) {
        documentosServices.guardaDocumento(listaDocumentos, listaDocumentosSolicitud);
        return documentosServices.obtenerDocumentosAnexados(idSolicitud);
    }

    /**
     * Metodo para generar una cadena
     * 
     * @param idSolicitud
     * @param fechaFirma
     * @return
     */
    public String generaCadenaOriginal(long idSolicitud, Date fechaFirma) {
        StringBuffer cadenaOriginal = new StringBuffer();
        cadenaOriginal.append(generarCadenasHelper.generarCadenasRegistroSolicitud(idSolicitud, fechaFirma));
        cadenaOriginal.append(generaCadenaDatosPromocion(idSolicitud));
        return cadenaOriginal.toString();
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
        cadenaOriginal.append(generarCadenasHelper.generarCadenasRegistroPromocion(numAsunto, fechaFirma));
        cadenaOriginal.append(generaCadenaDatosPromocion(idSolicitud));

        String strCadena = cadenaOriginal.toString();
        Firma firmaSIAT = null;
        try {
            firmaSIAT = selladoraHelper.getSello(strCadena);
        }
        catch (SelladoraException se) {
            firmaSIAT = selladoraHelper.getSelloDefault(strCadena);
            getLogger().error("Sella promocion CAL:  " + se.getMessage());
        }

        return firmaSIAT;
    }

    private String generaCadenaDatosPromocion(long idSolicitud) {
        StringBuffer cadenaOriginal = new StringBuffer();
        Solicitud sol = solicitudDAO.obtenerSolicitudporId(idSolicitud);
        ((SolicitudConsultaAutorizacion) sol).setMedioDefensaHelper(medioDefensaDAO
                .obtenerMedioDefensaById(idSolicitud));
        String descAutHechos = "";
        String descAutSujetoEjercicio = "";
        List<PersonaSolicitud> listaPersonaSolicitud = personaSolicitudDAO.obtenerPersonasSolicitudByIdSol(idSolicitud);
        Autoridad autoridadDeHechos =
                registroSolicitudConsultaAutorizacionDAO.obtenerAutoridad(idSolicitud, TipoAutoridad.AUTORIDAD_HCH);
        Autoridad autoridadSujetoEjercicio =
                registroSolicitudConsultaAutorizacionDAO.obtenerAutoridad(idSolicitud, TipoAutoridad.AUTORIDAD_SUJETO);

        List<FraccionDuda> fraccionesDuda = fraccionDudaDAO.obtenerFraccionesDudaBySolicitudId(idSolicitud);

        String idSol = sol.getIdSolicitud().toString();
        List<Documento> listaDocumentos = new ArrayList<Documento>();
        List<DocumentoSolicitud> listaDocumentoSolicitud =
                registroRecursoRevocacionDAO.obtenerDocumentoSolicitudAnexado(idSol);
        for (DocumentoSolicitud documentoSolicitud : listaDocumentoSolicitud) {
            Documento doc = documentoSolicitud.getDocumento();
            listaDocumentos.add(doc);
        }

        if (autoridadDeHechos != null) {
            descAutHechos = unidadAdministrativaDao.obtenerUnidadPorId(autoridadDeHechos.getIdUniAdmin()).getNombre();
        }
        if (autoridadSujetoEjercicio != null) {
            descAutSujetoEjercicio =
                    unidadAdministrativaDao.obtenerUnidadPorId(autoridadSujetoEjercicio.getIdUniAdmin()).getNombre();
        }
        cadenaOriginal.append(generarCadenasHelper.generaCadenaRegistroCAL((SolicitudConsultaAutorizacion) sol,
                listaPersonaSolicitud, descAutHechos, descAutSujetoEjercicio, fraccionesDuda));
        cadenaOriginal.append(generarCadenasHelper.generaCadenaDocumento(listaDocumentos));
        return cadenaOriginal.toString();
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

    public List<CatalogoD> obtenerCatalogoDCodGen1(String codGen1) {
        return catalogoDDao.obtenerCatalogoPorCodGen1(codGen1);
    }

    public List<EnumeracionTr> obtenerTipoClasificacion() {
        return enumeracionDao.obtenerEnumeracionPorId("ENU_TIPO_CLASIF_ARANCELARIA");
    }

    public void eliminaPersona(Long idPersona) {
        PersonaSolicitud personaSol = personaSolicitudDAO.obtenerPersonaSolicitudByIdSol(idPersona);
        personaSol.setBlnActivo(false);
    }

    @Override
    public void eliminaFraccion(Long idFraccion) {
        FraccionDuda fraccionDuda = fraccionDudaDAO.obtenerFraccionById(idFraccion);
        fraccionDuda.setBlnActivo(false);
    }

    /**
     * @return the personaDao
     */
    public PersonaDao getPersonaDao() {
        return personaDao;
    }

    public boolean tieneDocumentosAnexados(String idSolicitud) {
        List<DocumentoSolicitud> docs = registroRecursoRevocacionDAO.obtenerDocumentoSolicitudAnexado(idSolicitud);
        boolean tieneDocumentos = true;
        if (docs.isEmpty()) {
            tieneDocumentos = false;
        }
        return tieneDocumentos;
    }

    public List<String> obtenerUnidadesAdministrativas(String rfc, String cveRol, String tipoTramite) {
        return null;
    }

    public void eliminarFraccionesDuda(Long idSolicitud) {
        List<FraccionDuda> listaFraccionesDuda = fraccionDudaDAO.obtenerFraccionesDudaBySolicitudId(idSolicitud);
        if (listaFraccionesDuda != null) {
            for (FraccionDuda frac : listaFraccionesDuda) {
                frac.setBlnActivo(false);
            }
        }
    }

}
