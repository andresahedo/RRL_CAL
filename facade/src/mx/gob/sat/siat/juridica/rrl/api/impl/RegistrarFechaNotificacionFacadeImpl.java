package mx.gob.sat.siat.juridica.rrl.api.impl;

import mx.gob.sat.siat.juridica.base.api.impl.BaseCloudFacadeImpl;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Notificacion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoOficialDTOTransformer;
import mx.gob.sat.siat.juridica.base.service.DocumentosServices;
import mx.gob.sat.siat.juridica.base.service.TipoTramiteServices;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.rrl.api.RegistrarFechaNotificacionFacade;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.TramiteDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.service.RegistrarFechaNotificacionService;
import mx.gob.sat.siat.juridica.rrl.service.RegistroRecursoRevocacionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("registrarFechaNotificacionFacade")
public class RegistrarFechaNotificacionFacadeImpl extends BaseCloudFacadeImpl implements
        RegistrarFechaNotificacionFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private transient RegistrarFechaNotificacionService registrarFechaNotificacionService;

    @Autowired
    private transient TramiteServices tramiteServices;

    @Autowired
    private transient TipoTramiteServices tipoTramiteServices;
    
    @Autowired
    private transient TramiteDTOTransformer tramiteDTOTransformer;

    @Autowired
    private transient RegistroRecursoRevocacionServices registroRecursoRevocacionServices;

    @Autowired
    private transient DocumentosServices documentosServices;

    @Autowired
    private transient DocumentoOficialDTOTransformer documentoOficialDTOTransformer;

    @Override
    public Long guardarFechaNotificacion(String numAsunto, Boolean blnAutorizacion, Date fechaNotificacion,
            Long idNotificacion) {
        return registrarFechaNotificacionService.guardarFechaNotificacion(numAsunto, blnAutorizacion,
                fechaNotificacion, idNotificacion);
    }

    @Override
    public TramiteDTO obtenerTramiteIdAsunto(String numeroAsunto) {
        Tramite tramite = tramiteServices.buscarTramite(numeroAsunto, null);
        tramite.getSolicitud()
                .getTipoTramite()
                .setDescripcionModalidad(
                        tipoTramiteServices.obtenerTipoTramite(tramite.getSolicitud().getTipoTramite()
                                .getIdTipoTramite()));
        return tramiteDTOTransformer.transformarDTO(tramite);
    }

    @Override
    public Boolean validarFechaNotificacion(Date fechaNotificacion, String numeroAsunto) {
        return registrarFechaNotificacionService.validarFechaNotificacion(fechaNotificacion, numeroAsunto);
    }

    @Override
    public Date buscarFechaNotificacionByIdTramite(String numeroAsunto) {
        return registrarFechaNotificacionService.buscarFechaNotificacionByIdTramite(numeroAsunto);
    }

    @Override
    public Date buscarFechaNotificacionByIdRequerimiento(Long idRequerimiento) {
        return registrarFechaNotificacionService.buscarFechaNotificacionByIdRequerimiento(idRequerimiento);
    }
    
    @Override
    public List<DocumentoOficialDTO> obtenerDocumentosFecha(String numAsunto) {
        List<DocumentoOficialDTO> listaDocsDTO = new ArrayList<DocumentoOficialDTO>();
        List<DocumentoOficial> listaDocs =
                registrarFechaNotificacionService.buscarTiposDocumentosAnexadosPorIdTramite(numAsunto);
        for (DocumentoOficial documentoOficial : listaDocs) {
            DocumentoOficialDTO doc = documentoOficialDTOTransformer.transformarDTO(documentoOficial);

            if (doc.getCveTipoDocumento().equals(TipoDocumentoOficial.ACTA_NOTIFICACION.getClave())) {
                doc.setDescripcionTipoDocumento(TipoDocumentoOficial.ACTA_NOTIFICACION.getDescripcion());
            }

            listaDocsDTO.add(doc);
        }
        return listaDocsDTO;
    }

    /**
     * @return the registrarFechaNotificacionService
     */
    public RegistrarFechaNotificacionService getRegistrarFechaNotificacionService() {
        return registrarFechaNotificacionService;
    }

    /**
     * @param registrarFechaNotificacionService
     *            the registrarFechaNotificacionService to set
     */
    public void
            setRegistrarFechaNotificacionService(RegistrarFechaNotificacionService registrarFechaNotificacionService) {
        this.registrarFechaNotificacionService = registrarFechaNotificacionService;
    }

    /**
     * @return the tramiteServices
     */
    public TramiteServices getTramiteServices() {
        return tramiteServices;
    }

    /**
     * @param tramiteServices
     *            the tramiteServices to set
     */
    public void setTramiteServices(TramiteServices tramiteServices) {
        this.tramiteServices = tramiteServices;
    }

    /**
     * @return the tipoTramiteServices
     */
    public TipoTramiteServices getTipoTramiteServices() {
        return tipoTramiteServices;
    }

    /**
     * @param tipoTramiteServices
     *            the tipoTramiteServices to set
     */
    public void setTipoTramiteServices(TipoTramiteServices tipoTramiteServices) {
        this.tipoTramiteServices = tipoTramiteServices;
    }

    /**
     * @return the tramiteDTOTransformer
     */
    public TramiteDTOTransformer getTramiteDTOTransformer() {
        return tramiteDTOTransformer;
    }

    /**
     * @param tramiteDTOTransformer
     *            the tramiteDTOTransformer to set
     */
    public void setTramiteDTOTransformer(TramiteDTOTransformer tramiteDTOTransformer) {
        this.tramiteDTOTransformer = tramiteDTOTransformer;
    }

    /**
     * @return the registroRecursoRevocacionServices
     */
    public RegistroRecursoRevocacionServices getRegistroRecursoRevocacionServices() {
        return registroRecursoRevocacionServices;
    }

    /**
     * @param registroRecursoRevocacionServices
     *            the registroRecursoRevocacionServices to set
     */
    public void
            setRegistroRecursoRevocacionServices(RegistroRecursoRevocacionServices registroRecursoRevocacionServices) {
        this.registroRecursoRevocacionServices = registroRecursoRevocacionServices;
    }

    /**
     * @return the documentoOficialDTOTransformer
     */
    public DocumentoOficialDTOTransformer getDocumentoOficialDTOTransformer() {
        return documentoOficialDTOTransformer;
    }

    /**
     * @param documentoOficialDTOTransformer
     *            the documentoOficialDTOTransformer to set
     */
    public void setDocumentoOficialDTOTransformer(DocumentoOficialDTOTransformer documentoOficialDTOTransformer) {
        this.documentoOficialDTOTransformer = documentoOficialDTOTransformer;
    }

    @Override
    public void eliminaDocumentosNotificacionAnexados(String numAsunto) {
        documentosServices.eliminarDocumentosOficialesAnexadosPorTipo(numAsunto,
                TipoDocumentoOficial.ACTA_NOTIFICACION.getClave());
    }

    @Override
    public Notificacion buscarNotifParcial(String numAsunto, Boolean blnAutorizacion) {
        return registrarFechaNotificacionService.buscarNotificacionParcial(numAsunto, blnAutorizacion);
    }

}
