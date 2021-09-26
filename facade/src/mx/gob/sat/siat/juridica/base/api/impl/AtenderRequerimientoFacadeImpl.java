package mx.gob.sat.siat.juridica.base.api.impl;

import mx.gob.sat.siat.juridica.base.api.AtenderRequerimientoFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoMensajeBPM;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.FirmaTransformer;
import mx.gob.sat.siat.juridica.base.service.ConsultaSolicitudServices;
import mx.gob.sat.siat.juridica.base.service.RequerimientoServices;
import mx.gob.sat.siat.juridica.bpm.constante.TipoTransicion;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.RequerimientoDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.service.FirmaTareaService;
import mx.gob.sat.siat.juridica.rrl.service.RegistroRecursoRevocacionServices;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;
import mx.gob.sat.siat.juridica.rrl.util.constante.AnexarDocumentosConstantes;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import mx.gob.sat.siat.juridica.rrl.util.exception.FechaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("atenderRequerimientoFacade")
public class AtenderRequerimientoFacadeImpl extends BaseCloudFacadeImpl implements AtenderRequerimientoFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -9049592343198922060L;

    @Autowired
    private transient RequerimientoServices requerimientoServices;

    @Autowired
    private transient FirmaTareaService firmaTareaService;

    @Autowired
    private transient SolicitudService solicitudService;

    @Autowired
    private transient ConsultaSolicitudServices consultaSolicitudServices;

    @Autowired
    private transient RequerimientoDTOTransformer requerimientoTransformer;

    @Autowired
    private transient RegistroRecursoRevocacionServices registroRecursoRevocacionServices;

    @Autowired
    private transient DocumentoDTOTransformer documentoDTOTransformer;

    @Autowired
    private transient FirmaTransformer firmaTransformer;

    @Override
    public InputStream descargarDocumento(DocumentoDTO documento) {

        return registroRecursoRevocacionServices.descargarDocumento(documento.getRuta());
    }

    @Override
    public String generarCadenaOriginal(long idSolicitud, Date fechaFirma) {
        return solicitudService.generarCadenaOriginalAtenderReq(idSolicitud, fechaFirma);
    }

    @Override
    public String generarCadenaOriginalPromocion(long idSolicitud, Date fechaFirma) {
        return solicitudService.generarCadenaOriginal(idSolicitud, fechaFirma);
    }

    @Override
    public RequerimientoDTO prepararAtenderRequerimiento(String numeroAsunto, String modalidad) {

        RequerimientoDTO requerimiento =
                requerimientoTransformer.transformarDTO(requerimientoServices.obtenerUltimoRequerimientoPorTramite(
                        numeroAsunto, modalidad));
        return requerimiento;
    }

    public void firmaAtender(DatosBandejaTareaDTO datosBandeja, RequerimientoDTO requerimiento, String rfcUsuario) {
        Requerimiento req = requerimientoTransformer.transformarGenerarRequerimiento(requerimiento);
        req.setEstadoRequerimiento(EstadoRequerimiento.ATENDIDO);

        try {
            requerimientoServices.actualizaRequerimiento(req, false);
            getLogger().debug("Se atiende el requerimeinto por el contribuyente");
        }
        catch (FechaInvalidaException fie) {
            getLogger().error("Error al atender el requerimiento", fie);
        }
        requerimientoServices.actualizarFecha(datosBandeja.getNumeroAsunto(), null, req);
        firmaTareaService.completarTareaAtenderRequerimiento(datosBandeja.getNumeroAsunto(),
                TipoMensajeBPM.COMPLETAR_TAREA, datosBandeja.getIdTareaUsuario().toString(),
                TipoTransicion.PRINCIPAL.getId(), false, rfcUsuario, datosBandeja.isOficialia());

    }

    @Override
    public void guardarDocumentosSolicitud(long idSolicitud, long idRequerimiento, List<DocumentoDTO> documentos,
            String tipoDocumento, String rfc) throws ArchivoNoGuardadoException {
        List<Documento> listaDocumento =
                documentoDTOTransformer.transdormarModelDocumento(documentos,
                        registroRecursoRevocacionServices.obtenerPersonaPorRFC(rfc));
        List<DocumentoSolicitud> listaDocumentoSol =
                documentoDTOTransformer.transformarModelDocumentoSolicitud(idSolicitud,
                        registroRecursoRevocacionServices.obtenerPersonaPorRFC(rfc), listaDocumento);
        List<DocumentoSolicitudRequerimiento> documentosRequerimiento =
                documentoDTOTransformer.transformarDocumentoSolicitudRequerimiento(listaDocumentoSol, idRequerimiento);

        registroRecursoRevocacionServices.guardaDocumentoRequerimiento(listaDocumento, listaDocumentoSol,
                documentosRequerimiento);

    }

    @Override
    public List<DocumentoDTO> obtenerDocumentosSolicitudRequerimiento(long idRequerimiento) {
        List<Documento> listaDocumentos =
                consultaSolicitudServices.obtenerDocumentoAnexadosRequerimiento(idRequerimiento);
        List<DocumentoDTO> listaDocumentosDTO = new ArrayList<DocumentoDTO>();
        DocumentoDTO documentoDTO = null;
        for (Documento documento : listaDocumentos) {
            documentoDTO = documentoDTOTransformer.transformarDocumentoDTO(documento);
            documentoDTO.setTipoDocumento(AnexarDocumentosConstantes.DESC_TIPO_DOCUMENTO_REQUERIMIENTO);

            listaDocumentosDTO.add(documentoDTO);
        }
        return listaDocumentosDTO;
    }

    @Override
    public void eliminaDocumento(long idDocumento) {
        registroRecursoRevocacionServices.eliminaDocumento(idDocumento);
    }

    @Override
    public FirmaDTO obtenSelloAtenderRequerimientoSIAT(String numAsunto, long idSolicitud, Date fechaFirma) {
        Firma firma = solicitudService.generaFirmaPromocionSIAT(numAsunto, idSolicitud, fechaFirma);
        FirmaDTO firmaDTO = firmaTransformer.transformarFirma(firma);

        return firmaDTO;
    }

    @Override
    public void eliminaDocumentosSolicitudRequerimiento(Long idRequerimiento) {
        consultaSolicitudServices.eliminaDocumentosSolicitudRequerimiento(idRequerimiento);
    }

    @Override
    public RequerimientoDTO obtenerRequerimientoporId(Long idRequerimiento) {
        RequerimientoDTO requerimiento =
                requerimientoTransformer.transformarDTO(requerimientoServices.obtenerRequerimientoById(idRequerimiento));
        return requerimiento;
    }

}
