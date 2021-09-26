/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.EnumeracionTr;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Documento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.ModalidadesDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.util.constante.EnumeracionConstantes;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.ca.api.RegistroSolicitudConsultaAutorizacionFacade;
import mx.gob.sat.siat.juridica.ca.dao.domain.model.SolicitudConsultaAutorizacion;
import mx.gob.sat.siat.juridica.ca.dto.transformer.SolicitudConsultaAutorizacionDTOTransformer;
import mx.gob.sat.siat.juridica.ca.service.RegistroSolicitudConsultaAutorizacionServices;
import mx.gob.sat.siat.juridica.ca.util.constants.RegistroSolicitudConstants;
import mx.gob.sat.siat.juridica.rrl.dto.SolicitudConsultaAutorizacionDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.FirmasSolicitudTransformer;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Facade para atender el registro de la solicitud.
 * 
 * @author Softtek
 * 
 */
@Component("registroSolicitudConsultaAutorizacionFacade")
public class RegistroSolicitudConsultaAutorizacionFacadeImpl extends BaseFacadeImpl implements
        RegistroSolicitudConsultaAutorizacionFacade {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = 1514140449969098245L;
    @Autowired
    private transient RegistroSolicitudConsultaAutorizacionServices registroSolicitudConsultaAutorizacionServices;
    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;
    @Autowired
    private transient DocumentoDTOTransformer documentoDTOTransformer;
    @Autowired
    private transient ModalidadesDTOTransformer modalidadesDTOTransformer;
    @Autowired
    private transient SolicitudConsultaAutorizacionDTOTransformer solicitudConsultaAutorizacionDTOTransformer;
    @Autowired
    private transient SolicitudService solicitudService;
    @Autowired
    private transient FirmasSolicitudTransformer firmasSolicitudTransformer;

    /**
     * M&eacute;todo para preparar la solicitud que se desea capturar.
     */
    public SolicitudConsultaAutorizacionDTO iniciarRegistroSolicitud(String rfc, String modalidad)
            throws SolicitudNoGuardadaException {

        // TODO Pendiente integraci�n con IDC
        Persona persona = registroSolicitudConsultaAutorizacionServices.obtenerPersonaPorRFC(rfc);
        registroSolicitudConsultaAutorizacionServices.validarPersonaExistente(persona);

        SolicitudConsultaAutorizacionDTO solicitud = new SolicitudConsultaAutorizacionDTO();
        PersonaSolicitudDTO solicitante = new PersonaSolicitudDTO();
        DomicilioSolicitudDTO domicilio = new DomicilioSolicitudDTO();

        domicilio.setCalle("Calle");
        domicilio.setNumeroExterior("A-567");
        domicilio.setNumeroInterior("5");
        domicilio.setTelefono("55000000");
        domicilio.setCorreoElectronico("correoDummy@stk.com");
        domicilio.setRepresentanteLegal("Representante Legal");
        domicilio.setAdministracionLocal("Administracion Local");

        solicitante.setRfc(persona.getRfc());
        solicitante.setNombre(persona.getNombre());
        solicitante.setApellidoPaterno(persona.getApellidoPaterno());
        solicitante.setApellidoMaterno(persona.getApellidoMaterno());
        solicitante.setDomicilio(domicilio);

        solicitud.setSolicitante(solicitante);
        solicitud.setTipoTramite(modalidad);

        return solicitud;
    }

    /**
     * M&eacute;todo para obtener las autorizades emisoras.
     */
    public List<CatalogoDTO> obtenerAutoridadesEmisoras() {
        List<CatalogoDTO> listaCatalogo =
                catalogoDTOTransformer.transformarDTO(registroSolicitudConsultaAutorizacionServices
                        .obtenerAutoridadesEmisoras());
        return listaCatalogo;
    }

    /**
     * M&eacute;todo para obtener lista comun de desici&oacute;n SI o
     * NO.
     */
    public List<CatalogoDTO> obtenerListaSiNo() {
        List<CatalogoDTO> listaCatalogo =
                catalogoDTOTransformer.transformarCatalogo(registroSolicitudConsultaAutorizacionServices
                        .obtenerListaSiNo());
        return listaCatalogo;
    }

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     */
    public List<CatalogoDTO> obtenerMediosDeDefensa() {
        List<EnumeracionTr> listaEnumeraciones =
                registroSolicitudConsultaAutorizacionServices
                        .obtenerEnumeracionPorId(EnumeracionConstantes.TIPOS_MEDIOS_DEFENSA);
        return catalogoDTOTransformer.transformarEnumeracionTrDTO(listaEnumeraciones);
    }

    /**
     * M&eacute;todo para obtener lista de sentidos de
     * resolcui&oacute;n.
     */
    public List<CatalogoDTO> obtenerSentidosResolucion() {
        List<EnumeracionTr> listaEnumeraciones =
                registroSolicitudConsultaAutorizacionServices
                        .obtenerEnumeracionPorId(EnumeracionConstantes.SENTIDOS_RESOLCUION);
        return catalogoDTOTransformer.transformarEnumeracionTrDTO(listaEnumeraciones);
    }

    /**
     * M&eacute;todo para obtener modalidades de consulta.
     */
    public List<ModalidadesDTO> obtenerModalidadesConsulta(String tipoPersona) {
        return obtenerModalidadesPorTipo(RegistroSolicitudConstants.MODALIDADES_CONSULTA, tipoPersona);
    }

    /**
     * M&eacute;todo para obtener modalidades de autorizaci&oacute;n.
     */
    public List<ModalidadesDTO> obtenerModalidadesAutorizacion(String tipoPersona) {
        return obtenerModalidadesPorTipo(RegistroSolicitudConstants.MODALIDADES_AUTORIZACION, tipoPersona);
    }

    /**
     * M&eacute;todo para obtener documentos obligatorios por tipo
     * tramite.
     */
    public List<DocumentoDTO> obtenerDocumentosObligatorios(Integer idTipoTramite, String tipoClasificacion) {
        return obtenerdocumentosTramite(idTipoTramite, RegistroSolicitudConstants.DOCUMENTOS_OBLIGATORIOS,
                tipoClasificacion);
    }

    /**
     * M&eacute;todo para obtener documentos opcionales por tipo
     * tramite.
     */
    public List<DocumentoDTO> obtenerDocumentosOpcionales(Integer idTipoTramite, String tipoClasificacion) {
        return obtenerdocumentosTramite(idTipoTramite, RegistroSolicitudConstants.DOCUMENTOS_OPCIONALES,
                tipoClasificacion);
    }

    /**
     * M&eacute;todo para guardar la solicitud.
     */
    public SolicitudConsultaAutorizacionDTO guardarSolicitud(SolicitudConsultaAutorizacionDTO solicitud)
            throws SolicitudNoGuardadaException {
        SolicitudConsultaAutorizacion solicitudCA =
                solicitudConsultaAutorizacionDTOTransformer.transformarDTO(solicitud);
        solicitudCA = registroSolicitudConsultaAutorizacionServices.guardarSolicitud(solicitudCA);
        solicitud.setIdSolicitud(solicitudCA.getIdSolicitud());
        return solicitud;
    }

    /**
     * M&eacute;todo para obtener las modalidades por id de
     * subservicio.
     * 
     * @param idSubservicio
     * @return Lista ModalidadesDTO
     */
    private List<ModalidadesDTO> obtenerModalidadesPorTipo(String idSubservicio, String tipoPersona) {
        List<TipoTramite> listaTipoTramite =
                registroSolicitudConsultaAutorizacionServices.obtenerModalidadesPorSubservicio(idSubservicio,
                        tipoPersona);

        return modalidadesDTOTransformer.transformarDTO(listaTipoTramite);
    }

    /**
     * M&eacute;todo para obtener los documentos del tramite por tipo
     * de documento.
     * 
     * @param idTipoTramite
     * @param opcional
     * @return Lista DocumentoDTO
     */
    private List<DocumentoDTO> obtenerdocumentosTramite(Integer idTipoTramite, Integer opcional,
            String tipoClasificacion) {
        List<DocumentoDTO> listaDocumentos = new ArrayList<DocumentoDTO>();
        List<DocumentoTramite> listaDocumentoTramite =
                registroSolicitudConsultaAutorizacionServices.obtenerTiposDocumentosPorTramite(idTipoTramite, opcional);
        DocumentoTramite docTra =
                registroSolicitudConsultaAutorizacionServices.obtenerDocumentoRazonesLogicoJuridicas();
        if (idTipoTramite.toString().equals(DiscriminadorConstants.T2_CLASIFICACION_ARANCELARIA)) {
            if (tipoClasificacion.equals("CLA.CON")) {
                if (opcional == 0) {
                    listaDocumentoTramite.add(docTra);
                    for (DocumentoTramite documento : listaDocumentoTramite) {
                        listaDocumentos.add(documentoDTOTransformer.transformarDTO(documento));
                    }
                }
                else if (opcional == 1) {
                    for (DocumentoTramite documento : listaDocumentoTramite) {
                        if (documento.getDocumentoTramitePK().getIdTipoDoc() != docTra.getDocumentoTramitePK()
                                .getIdTipoDoc()) {
                            listaDocumentos.add(documentoDTOTransformer.transformarDTO(documento));
                        }
                    }
                }
            }
            else {
                if (opcional == 0) {
                    listaDocumentoTramite.add(docTra);
                    for (DocumentoTramite documento : listaDocumentoTramite) {
                        if (!documento.getDocumentoTramitePK().getIdTipoDoc()
                                .equals(docTra.getDocumentoTramitePK().getIdTipoDoc())) {
                            listaDocumentos.add(documentoDTOTransformer.transformarDTO(documento));
                        }
                    }
                }
                else if (opcional == 1) {
                    for (DocumentoTramite documento : listaDocumentoTramite) {
                        listaDocumentos.add(documentoDTOTransformer.transformarDTO(documento));
                    }
                }

            }
        }
        else {
            for (DocumentoTramite documentoTramite : listaDocumentoTramite) {
                DocumentoDTO documento = documentoDTOTransformer.transformarDTO(documentoTramite);
                listaDocumentos.add(documento);
            }
        }
        return listaDocumentos;

    }

    public InputStream descargarArchivo(String ruta) {
        return registroSolicitudConsultaAutorizacionServices.descargarArchivo(ruta);
    }

    public void guardarDocumentosSolicitud(SolicitudConsultaAutorizacionDTO solicitud,
            List<DocumentoDTO> listaDocumentos) throws ArchivoNoGuardadoException {
        Persona persona =
                registroSolicitudConsultaAutorizacionServices.obtenerPersonaPorRFC(solicitud.getSolicitante().getRfc());
        List<Documento> listaDeDocumento = documentoDTOTransformer.transdormarModelDocumento(listaDocumentos, persona);
        registroSolicitudConsultaAutorizacionServices.validarDocumentos(solicitud.getTipoTramite(), listaDeDocumento);
        List<DocumentoSolicitud> listaDocumentoSolicitud =
                documentoDTOTransformer.transformarModelDocumentoSolicitud(solicitud.getIdSolicitud(), persona,
                        listaDeDocumento);
        registroSolicitudConsultaAutorizacionServices.guardarDocumentos(listaDeDocumento, listaDocumentoSolicitud);
    }

    public String generaCadenaOriginal(long idSolicitud, Date fechaFirma) {
        return registroSolicitudConsultaAutorizacionServices.generaCadenaOriginal(idSolicitud, fechaFirma);
    }

    public String firmaSolicitud(SolicitudConsultaAutorizacionDTO solicitud, FirmaDTO firma) {
        Tramite tramite =
                solicitudService.generarTramite(solicitud.getIdSolicitud(),
                        firmasSolicitudTransformer.transformarDTO(firma), null, DiscriminadorConstants.PREFIJO_CA,
                        DiscriminadorConstants.T2_BASE);
        Solicitud solicitudBD = solicitudService.obtenerSolicitudporId(solicitud.getIdSolicitud());
        solicitudBD.setEstadoSolicitud(EstadoSolicitud.RECIBIDA);
        solicitudService.guardarTramite(tramite);

        return tramite.getNumeroAsunto();
    }

    public String generarCadenaOriginal(long idSolicitud, Date fechaFirma) {
        return solicitudService.generarCadenaOriginal(idSolicitud, fechaFirma);
    }

}
