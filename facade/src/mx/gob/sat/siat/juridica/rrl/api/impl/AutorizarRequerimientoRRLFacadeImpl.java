/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.api.impl;

import mx.gob.sat.siat.juridica.base.api.impl.BaseCloudFacadeImpl;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.EnumeracionTr;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.*;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DatosBandejaTarea;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Requerimiento;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoOficialDTOTransformer;
import mx.gob.sat.siat.juridica.base.service.ConsultaSolicitudServices;
import mx.gob.sat.siat.juridica.base.service.DocumentosServices;
import mx.gob.sat.siat.juridica.base.service.RequerimientoServices;
import mx.gob.sat.siat.juridica.base.service.UnidadAdministrativaServices;
import mx.gob.sat.siat.juridica.base.util.constante.EnumeracionConstantes;
import mx.gob.sat.siat.juridica.bpm.constante.TipoTransicion;
import mx.gob.sat.siat.juridica.rrl.api.AutorizarRequerimientoRRLFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.RequerimientoDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.TramiteDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.service.AsignarTareaCorreoService;
import mx.gob.sat.siat.juridica.rrl.service.FirmaTareaService;
import mx.gob.sat.siat.juridica.rrl.util.exception.FechaInvalidaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Facade para atender la autorizaci&oacute;n del requerimiento.
 * 
 * @author Softtek
 * 
 */
@Component("autorizarRequerimientoRRLFacade")
public class AutorizarRequerimientoRRLFacadeImpl extends BaseCloudFacadeImpl implements AutorizarRequerimientoRRLFacade {

    private transient Logger logger = LoggerFactory.getLogger(this.getClass());

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = -8409917155906803323L;
    /** Servicio para atender las peticiones del requerimiento */
    @Autowired
    private transient RequerimientoServices requerimientoServices;
    @Autowired
    private transient ConsultaSolicitudServices consultaSolicitudServices;
    /** Servicio para firmar la autorizaci&oacute;n del requerimiento */
    @Autowired
    private transient FirmaTareaService firmaTareaService;
    /** Objeto para transformaciones de requerimiento */
    @Autowired
    private transient RequerimientoDTOTransformer requerimientoTransformer;
    /** Objeto para transformaciones de cat&aacute;logos */
    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;
    @Autowired
    private transient DocumentoDTOTransformer documentoDTOTransformer;
    /** Objeto para transformaciones de tramite */
    @Autowired
    private transient TramiteDTOTransformer tramiteDTOTransformer;
    /** Objeto para transformaciones de documentos oficiales */
    @Autowired
    private transient DocumentoOficialDTOTransformer documentoOficialDTOTransformer;

    @Autowired
    private transient UnidadAdministrativaServices unidadAdministrativaServices;

    @Autowired
    private transient AsignarTareaCorreoService asignarTareaCorreoService;

    @Autowired
    private transient DocumentosServices documentosServices;

    @Autowired
    private transient RequerimientoServices requerimientoService;

    /**
     * M&eacute;todo para obtener los tipos de requerimiento.
     */
    public List<CatalogoDTO> obtenerTiposDeRequerimiento() {
        List<EnumeracionTr> listaEnumeraciones =
                requerimientoServices.obtenerTiposDeRequerimiento(EnumeracionConstantes.TIPOS_REQUERIMIENTO);
        return catalogoDTOTransformer.transformarEnumeracionTrDTO(listaEnumeraciones);
    }

    /**
     * M&eacute;todo para obtener los tipos de requerimiento.
     */
    @Override
    public List<CatalogoDTO> obtenerTiposDeRequerimiento(String tipoReq) {
        List<EnumeracionTr> listaEnumeraciones = requerimientoServices.obtenerTiposDeRequerimiento(tipoReq);
        return catalogoDTOTransformer.transformarEnumeracionTrDTO(listaEnumeraciones);
    }

    /**
     * M&eacute;todo para obtener las autoridades a las que se
     * solicitar&aacute; el requerimiento.
     */
    public List<CatalogoDTO> obtenerAutoridades() {
        List<UnidadAdministrativa> listaUnidades =
                requerimientoServices.obtenerUnidadesPorTipo(TipoUnidadAdministrativa.ADMINISTRACION_EXTERNA);
        return catalogoDTOTransformer.transformarDTO(listaUnidades);
    }

    /**
     * M&eacute;todo para obtener las autoridades a las que se
     * solicitar&aacute; el requerimiento.
     */
    public List<CatalogoDTO> obtenerAutoridadesTodas() {
        List<UnidadAdministrativa> listaUnidades = requerimientoServices.obtenerUnidadesTodas();
        return catalogoDTOTransformer.transformarDTO(listaUnidades);
    }

    /**
     * M&eacute;todo para rechazar el requerimiento.
     */
    @Override
    public void rechazarRequerimiento(String numAsunto, Long idTareaUsuario, String rfc, Long idRequerimiento,
            Long idSolicitud) {
        Requerimiento requerimiento = requerimientoServices.obtenerRequerimientoById(idRequerimiento);
        requerimientoServices.rechazarRequerimiento(numAsunto, idTareaUsuario.toString(), rfc, idRequerimiento);
        asignarTareaCorreoService.enviarCorreo(numAsunto, requerimiento.getIdUsuario(), "Atender promoci&oacute;n");
        requerimientoServices.enviarCorreoRechazoAsuntoRequerimiento(requerimiento.getIdUsuario(), numAsunto,
                idSolicitud, "Requerimiento", requerimiento.getFechaCreacion(), rfc);

    }

    @Override
    public RequerimientoDTO prepararAutorizarRequerimiento(String numeroAsunto, UserProfileDTO userProfile,
            String modalidad) {
        RequerimientoDTO requerimiento =
                requerimientoTransformer.transformarDTO(requerimientoServices.obtenerUltimoRequerimientoPorTramite(
                        numeroAsunto, modalidad));
        requerimiento.setTramite(tramiteDTOTransformer.transformarDTO(requerimientoServices
                .buscarTramitePorAsunto(numeroAsunto)));
        if (userProfile != null) {
            requerimiento.setNombrePersona(userProfile.getNombreCompleto());
            requerimiento.setRfc(userProfile.getRfc());
        }
        return requerimiento;
    }

    @Override
    public RequerimientoDTO prepararAutorizarRequerimientoByIdRequerimiento(Long idRequerimiento,
            UserProfileDTO userProfile) {

        Requerimiento req = requerimientoServices.obtenerRequerimientoById(idRequerimiento);
        RequerimientoDTO requerimiento = requerimientoTransformer.transformarDTO(req);
        requerimiento.setTramite(tramiteDTOTransformer.transformarDTO(requerimientoServices.buscarTramitePorAsunto(req
                .getTramite().getNumeroAsunto())));
        if (userProfile != null) {
            requerimiento.setNombrePersona(userProfile.getNombreCompleto());
            requerimiento.setRfc(userProfile.getRfc());
        }
        return requerimiento;
    }

    /**
     * M&eacute;todo para descargar el archivo asociado al documento
     * oficial.
     */
    public InputStream descargarArchivoOficial(DocumentoOficialDTO documento) {
        return requerimientoServices.descargarDocumento(documento.getRuta());
    }

    /**
     * M&eacute;todo para guardar los documentos asociadas al
     * requerimiento (autorizaci&oacute;n).
     */
    public void guardarDocumentosAutorizarRequerimiento(List<DocumentoOficialDTO> documentos,
            RequerimientoDTO requerimiento) {
        List<DocumentoOficial> listaDocumentoOficial =
                documentoOficialDTOTransformer.transdormarModelDocumento(documentos, requerimiento.getTramite()
                        .getNumeroAsunto());
        requerimientoServices.guardaDocumentosOficiales(listaDocumentoOficial, requerimiento.getIdRequerimiento(),
                requerimiento.getTramite().getNumeroAsunto());
    }

    /**
     * M&eacute;todo para firmar la autorizaci&oacute;n del
     * requerimiento.
     */
    public void firmarAutorizarRequerimiento(RequerimientoDTO requerimiento, DatosBandejaTareaDTO datosBandeja,
            String rfcUsuario) {
        requerimiento.setFechaAutorizacion(new Date());
        Requerimiento req = requerimientoTransformer.transformarGenerarRequerimiento(requerimiento);

        String tipoReq = "";
        if (!req.getTipoRequerimiento().equals(TipoRequerimiento.CONTRIBUYENTE)
                && !req.getTipoRequerimiento().equals(TipoRequerimiento.PROMOVENTE_CAL)) {
            req.setEstadoRequerimiento((EstadoRequerimiento.CONCLUIDO));
            UnidadAdministrativa unidadAdministrativa =
                    unidadAdministrativaServices.obtenerUnidadPorId(req.getUnidadAdministrativa().getClave());
            if (unidadAdministrativa != null && datosBandeja.getTipoTramite() != null
                    && datosBandeja.getTipoTramite().startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                TipoUnidadAdministrativa idUnidad = unidadAdministrativa.getIdeTipoUnidadAdministrativa();
                if (!idUnidad.getClave().equals(TipoUnidadAdministrativa.ADMINISTRACION_INTERNA.getClave())
                        && !idUnidad.getClave().equals(TipoUnidadAdministrativa.ADMINISTRACION_EXTERNA.getClave())
                        && !idUnidad.getClave().equals(TipoUnidadAdministrativa.ADMINISTRACION_GENERAL.getClave())) {
                    tipoReq = TipoRequerimiento.AUTORIDAD_INTERNA.getClave();
                    req.setEstadoRequerimiento((EstadoRequerimiento.AUTORIZADO));

                }
                else {
                    tipoReq = TipoRequerimiento.AUTORIDAD.getClave();
                }
            }
            else {
                tipoReq = TipoRequerimiento.AUTORIDAD.getClave();
            }
        }
        else {
            tipoReq = TipoRequerimiento.CONTRIBUYENTE.getClave();
            req.setEstadoRequerimiento((EstadoRequerimiento.AUTORIZADO));
            requerimientoServices.actualizaTramitePorTarea(datosBandeja.getNumeroAsunto(), EstadoTramite.REQUERIDO,
                    null);
        }

        if (tipoReq != null && tipoReq.equals(TipoRequerimiento.CONTRIBUYENTE.getClave())) {
            DatosBandejaTarea datosBandejaTarea = new DatosBandejaTarea();
            datosBandejaTarea.setIdTareaUsuario(datosBandeja.getIdTareaUsuario());
            datosBandejaTarea.setIdRequerimiento(datosBandeja.getIdRequerimiento());
            
            firmaTareaService.completarTareaRequerimientoPromovente(TipoMensajeBPM.COMPLETAR_TAREA, datosBandejaTarea,
                    TipoTransicion.PRINCIPAL.getId(), null, true, rfcUsuario, tipoReq);
       }
        else {

            DatosBandejaTarea datosBandejaTarea = new DatosBandejaTarea();
            datosBandejaTarea.setIdTareaUsuario(datosBandeja.getIdTareaUsuario());
            datosBandejaTarea.setIdRequerimiento(datosBandeja.getIdRequerimiento());
            datosBandejaTarea.setIdSolicitud(datosBandeja.getIdSolicitud());

            firmaTareaService.completarTareaRequerimientoAutoridad(datosBandejaTarea, TipoTransicion.PRINCIPAL.getId(),
                    null, true, tipoReq, rfcUsuario, requerimiento.getClaveUnidadAdministrativa());
        }
        try {
            req.setDescripcion(tipoReq);
            requerimientoServices.actualizaRequerimiento(req, true);
            documentosServices.firmaDocumentosOficialesPorIdRequerimiento(req.getIdRequerimiento());

        }
        catch (FechaInvalidaException fie) {
            logger.error("", fie);
        }

    }

    /**
     * M&eacute;todo para firmar la autorizaci&oacute;n del
     * requerimiento.
     */
    public void actualizarRequerimiento(RequerimientoDTO requerimiento) {
        Requerimiento req = requerimientoTransformer.transformarGenerarRequerimiento(requerimiento);
        try {
            requerimientoServices.actualizaRequerimiento(req, false);
        }
        catch (FechaInvalidaException fie) {
            logger.error("", fie);
        }

    }

    @Override
    public List<DocumentoOficialDTO> obtenerDocumentosPorRequerimiento(Long idRequerimiento) {
        List<DocumentoOficialDTO> listaDocumentoDTO = new ArrayList<DocumentoOficialDTO>();
        List<DocumentoOficial> listaDocumentoOficial =
                consultaSolicitudServices.obteneDocumentosOficialesPorIdRequerimiento(idRequerimiento);
        for (DocumentoOficial documentoOficial : listaDocumentoOficial) {
            DocumentoOficialDTO doc = documentoOficialDTOTransformer.transformarDTO(documentoOficial);
            doc.setDescripcionTipoDocumento(consultaSolicitudServices.obtenerDescripcionTipoDoc(doc
                    .getCveTipoDocumento()));
            listaDocumentoDTO.add(doc);
        }
        return listaDocumentoDTO;
    }

    @Override
    public void eliminaDocumento(long idDocumento) {
        requerimientoServices.eliminaDocumentoOficial(idDocumento);
    }

    @Override
    public String generaCadenaOriginalAutorizarRequerimiento(Long idRequerimiento, Date fechaFirma,
            String rfcFuncionario) {

        return requerimientoService.generaCadenaOriginalAutorizarRequerimiento(idRequerimiento, fechaFirma,
                rfcFuncionario);

    }

    @Override
    public List<DocumentoDTO> obtenerDocumentosComplementarios(Long idSolicitud) {
        return documentoDTOTransformer.tranformarDocSolicitud(requerimientoService
                .obtenerDocumentosComplementarios(idSolicitud));
    }

    /**
     * Borrado logico de documentos oficiales vinculados a un
     * requerimiento
     * 
     * @param idRequerimiento
     */
    @Override
    public void eliminaDocumentosRequerimiento(Long idRequerimiento) {
        documentosServices.eliminaDocumentosOficialesPorIdRequerimiento(idRequerimiento);
    }

}
