package mx.gob.sat.siat.juridica.cal.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.EnumeracionTr;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoMensajeBPM;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoUnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DatosBandejaTarea;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Requerimiento;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoOficialDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.ConsultaSolicitudServices;
import mx.gob.sat.siat.juridica.base.service.RequerimientoServices;
import mx.gob.sat.siat.juridica.base.util.constante.EnumeracionConstantes;
import mx.gob.sat.siat.juridica.bpm.constante.TipoTransicion;
import mx.gob.sat.siat.juridica.cal.api.AutorizarRequerimientoCALFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.RequerimientoDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.TramiteDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.service.FirmaTareaService;
import mx.gob.sat.siat.juridica.rrl.util.exception.FechaInvalidaException;
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
@Component("autorizarRequerimientoCALFacade")
public class AutorizarRequerimientoCALFacadeImpl extends BaseFacadeImpl implements AutorizarRequerimientoCALFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -8173788570626320445L;
    /** Servicio para atender las peticiones del requerimiento */
    @Autowired
    private transient RequerimientoServices requerimientoServices;
    @Autowired
    private ConsultaSolicitudServices consultaSolicitudServices;
    /** Servicio para firmar la autorizaci&oacute;n del requerimiento */
    @Autowired
    private transient FirmaTareaService firmaTareaService;
    /** Objeto para transformaciones de requerimiento */
    @Autowired
    private transient RequerimientoDTOTransformer requerimientoTransformer;
    /** Objeto para transformaciones de cat&aacute;logos */
    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;

    /** Objeto para transformaciones de tramite */
    @Autowired
    private transient TramiteDTOTransformer tramiteDTOTransformer;
    /** Objeto para transformaciones de documentos oficiales */
    @Autowired
    private transient DocumentoOficialDTOTransformer documentoOficialDTOTransformer;

    /**
     * M&eacute;todo para obtener los tipos de requerimiento.
     */
    public List<CatalogoDTO> obtenerTiposDeRequerimiento() {
        List<EnumeracionTr> listaEnumeraciones =
                requerimientoServices.obtenerTiposDeRequerimiento(EnumeracionConstantes.TIPOS_REQUERIMIENTO);
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
     * M&eacute;todo para preparar la autorizaci&oacute;n del
     * requerimiento.
     */
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
        // TODO quitar valores en hardcode
        List<DocumentoOficial> listaDocumentoOficial =
                documentoOficialDTOTransformer.transdormarModelDocumento(documentos, "RRL2014000617");

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
        req.setEstadoRequerimiento((EstadoRequerimiento.AUTORIZADO));
        try {
            requerimientoServices.actualizaRequerimiento(req, true);
        }
        catch (FechaInvalidaException fie) {
            getLogger().error("", fie);
        }
        DatosBandejaTarea datosBandejaTarea = new DatosBandejaTarea();
        datosBandejaTarea.setIdTareaUsuario(datosBandeja.getIdTareaUsuario());
        datosBandejaTarea.setIdRequerimiento(datosBandeja.getIdRequerimiento());

        requerimientoServices.actualizaTramitePorTarea(datosBandeja.getNumeroAsunto(), EstadoTramite.REQUERIDO, null);
        firmaTareaService.completarTareaRequerimientoPromovente(TipoMensajeBPM.COMPLETAR_TAREA, datosBandejaTarea, 
                TipoTransicion.PRINCIPAL.getId(), null, false, rfcUsuario, "");
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

}
