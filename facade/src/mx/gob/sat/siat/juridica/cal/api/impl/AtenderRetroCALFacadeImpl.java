package mx.gob.sat.siat.juridica.cal.api.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.sat.siat.juridica.base.api.impl.BaseCloudFacadeImpl;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.EnumeracionTr;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoMensajeBPM;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Documento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoSolicitudRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Firma;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FirmaRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FirmaRequerimientoPK;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Requerimiento;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoOficialDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.FirmaTransformer;
import mx.gob.sat.siat.juridica.base.service.ConsultaSolicitudServices;
import mx.gob.sat.siat.juridica.base.service.DocumentosServices;
import mx.gob.sat.siat.juridica.base.service.FirmarServices;
import mx.gob.sat.siat.juridica.base.service.RequerimientoServices;
import mx.gob.sat.siat.juridica.base.util.helper.SelladoraHelper;
import mx.gob.sat.siat.juridica.ca.util.constants.CadenasConstants;
import mx.gob.sat.siat.juridica.cal.api.AtenderRetroCALFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.service.FirmaTareaService;
import mx.gob.sat.siat.juridica.rrl.service.RegistroRecursoRevocacionServices;
import mx.gob.sat.siat.juridica.rrl.util.constante.AnexarDocumentosConstantes;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import mx.gob.sat.siat.juridica.rrl.util.exception.FechaInvalidaException;

@Component("atenderRetroCALFacade")
public class AtenderRetroCALFacadeImpl extends BaseCloudFacadeImpl implements AtenderRetroCALFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -8540351860605359767L;
    
    @Autowired
    private transient DocumentosServices documentosServices;
    
    @Autowired
    private transient RequerimientoServices requerimientoServices;

    @Autowired
    private transient FirmaTareaService firmaTareaService;
    @Autowired
    private transient FirmarServices firmarServices;
    @Autowired
    private transient ConsultaSolicitudServices consultaSolicitudServices;

    @Autowired
    private transient RegistroRecursoRevocacionServices registroRecursoRevocacionServices;

    @Autowired
    private transient DocumentoDTOTransformer documentoDTOTransformer;

    @Autowired
    private transient DocumentoOficialDTOTransformer documentoOficialDTOTransformer;

    @Autowired
    private transient FirmaTransformer firmaTransformer;
    
    @Autowired
    private SelladoraHelper selladoraHelper;

    @Override
    public InputStream descargarDocumento(DocumentoDTO documento) {

        return registroRecursoRevocacionServices.descargarDocumento(documento.getRuta());
    }

    @Override
    public void firmaAtender(DatosBandejaTareaDTO datosBandeja, Long idRequerimiento, String rfcUsuario,
            String numAsunto) {
        Requerimiento req = requerimientoServices.obtenerRequerimientoById(idRequerimiento);
        req.setEstadoRequerimiento(EstadoRequerimiento.ATENDIDO);
        try {
            requerimientoServices.actualizaRequerimiento(req, false);
        }
        catch (FechaInvalidaException fie) {
            getLogger().error("Falla al actualizar el requerimiento", fie);
        }
        firmaTareaService.completarTareaAtenderRequerimientoAutoridad(TipoMensajeBPM.COMPLETAR_TAREA, datosBandeja
                .getIdTareaUsuario().toString(), rfcUsuario, numAsunto, datosBandeja.getNombreTarea());
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
    public void eliminaDocumentoOficial(long idDocumento) {
        registroRecursoRevocacionServices.eliminaDocumentoOficial(idDocumento);
    }

    /**
     * M&eacute;todo para guardar los documentos asociadas al
     * requerimiento (autorizaci&oacute;n).
     */
    @Override
    public void guardarDocumentosAutorizarRequerimiento(List<DocumentoOficialDTO> documentos, String numAsunto,
            Long idRequerimiento) {
        List<DocumentoOficial> listaDocumentoOficial =
                documentoOficialDTOTransformer.transdormarModelDocumento(documentos, numAsunto);
        requerimientoServices.guardaDocumentosOficialesAtencion(listaDocumentoOficial, idRequerimiento, numAsunto);
    }

    @Override
    public FirmaDTO obtenSelloAtenderRetroSIAT(String numeroAsunto, Date fechaFirma, String rfcFuncionario) {
        Firma firma = requerimientoServices.generaFirmaRetroalimentacionSIAT(numeroAsunto, fechaFirma, rfcFuncionario);
        FirmaDTO firmaDTO = firmaTransformer.transformarFirma(firma);

        return firmaDTO;
    }

    @Override
    public void guardarFirmaAtender(FirmaDTO firmaDTO, Long idRequerimiento) {

        Firma firma = firmaTransformer.transformarDTO(firmaDTO);
        firmarServices.guardarFrima(firma);
        FirmaRequerimiento firmaReq = new FirmaRequerimiento();
        firmaReq.setFirmaRequerimientoPK(new FirmaRequerimientoPK());
        firmaReq.getFirmaRequerimientoPK().setIdFirma(firma.getIdFirma());
        firmaReq.getFirmaRequerimientoPK().setIdRequerimiento(idRequerimiento);
        firmarServices.guardarFirmaRequerimientoAutorizacion(firmaReq);
    }

    @Override
    public String generarCadenaOriginal(String numeroAsunto, Date fechaFirma, String rfcFuncionario) {
        return requerimientoServices.generaCadenaOriginalRetroalimentacion(numeroAsunto, fechaFirma, rfcFuncionario);
    }

    @Override
    public void sellarDocumentosAtencion(String numeroAsunto) {
        List<DocumentoOficial> docs = documentosServices.obtenerDocumentosOficialesAnexadosPorTipo(numeroAsunto,
                TipoDocumentoOficial.OFICIO_ATENCION_RETRO.getClave());
        for (DocumentoOficial doc : docs) {
            StringBuffer cadena = new StringBuffer();
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            cadena.append(doc.getHash() != null ? doc.getHash() : "");
            cadena.append(CadenasConstants.SEPARADOR_CADENA);
            doc.setSello(selladoraHelper.getSelloCadena(cadena.toString()));
            documentosServices.guardarDocumentoOficial(doc);
        }
        
    }
}
