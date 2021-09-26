package mx.gob.sat.siat.juridica.oficialia.api.impl;

import mx.gob.sat.siat.juridica.base.api.impl.BaseCloudFacadeImpl;
import mx.gob.sat.siat.juridica.base.dao.RemisionDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoUnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoDTOTransformer;
import mx.gob.sat.siat.juridica.base.service.ConsultaSolicitudServices;
import mx.gob.sat.siat.juridica.base.service.DocumentosServices;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.cal.service.RegistroSolicitudCALServices;
import mx.gob.sat.siat.juridica.oficialia.api.AdjuntaDocumentoOficialiaFacade;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component("adjuntaDocumentoOficialiaFacade")
public class AdjuntarDocumentosOficialiaFacadeImpl extends BaseCloudFacadeImpl implements
        AdjuntaDocumentoOficialiaFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 5439375064127053669L;

    @Autowired
    private transient DocumentoDTOTransformer documentoDTOTransformer;

    @Autowired
    private transient DocumentosServices documentosServices;

    @Autowired
    private transient RegistroSolicitudCALServices registroSolicitudCALServices;

    @Autowired
    private transient ConsultaSolicitudServices consultaSolicitudServices;

    @Autowired
    private transient TramiteServices tramiteServices;

    @Autowired
    private transient RemisionDao remisionDao;

    public InputStream descargarArchivo(String ruta) {
        return registroSolicitudCALServices.descargarArchivo(ruta);
    }

    public void guardarDocumentosSolicitud(Solicitud solicitud, List<DocumentoDTO> listaDocumentos,
            String rfcCapturista, DocumentoDTO documentoDto) throws ArchivoNoGuardadoException {
        Persona persona = registroSolicitudCALServices.obtenerPersonaPorRFC(rfcCapturista);
        List<Documento> listaDeDocumento = documentoDTOTransformer.transdormarModelDocumento(listaDocumentos, persona);
        List<DocumentoSolicitud> listaDocumentoSolicitud =
                documentoDTOTransformer.transformarDocumentoSolicitud(solicitud.getIdSolicitud(), persona,
                        listaDeDocumento, documentoDto);
        documentosServices.guardaDocumento(listaDeDocumento, listaDocumentoSolicitud);
    }

    public Solicitud obtenerDatosSolicitud(Long idSolicitud) {
        return consultaSolicitudServices.obtenerSolicitudporId(idSolicitud);
    }

    @Override
    public List<DocumentoDTO> obtenerTiposDocumentos(String idTipoTramite, String numAsunto) {

        Tramite tramite = tramiteServices.buscarTramite(numAsunto, null);
        List<DocumentoDTO> listaDocDTO = new ArrayList<DocumentoDTO>();
        boolean remisionExterna = false;

        List<DocumentoTramite> listaDocumentos =
                consultaSolicitudServices.obtenerDocumentosTramite(idTipoTramite, numAsunto);
        for (DocumentoTramite doc : listaDocumentos) {
            DocumentoDTO nuevoDocumento = documentoDTOTransformer.transformarDTO(doc);
            listaDocDTO.add(nuevoDocumento);
        }

        Remision remision = remisionDao.obtenerUltimaRemisionPorIdTramite(tramite.getNumeroAsunto());
        if (remision != null) {
            UnidadAdministrativa unidadAdministrativa = remision.getUnidadAdminNueva();
            if (unidadAdministrativa.getIdeTipoUnidadAdministrativa().getClave()
                    .equals(TipoUnidadAdministrativa.ADMINISTRACION_EXTERNA.getClave())
                    || unidadAdministrativa.getIdeTipoUnidadAdministrativa().getClave()
                            .equals(TipoUnidadAdministrativa.ADMINISTRACION_INTERNA.getClave())) {
                remisionExterna = true;
            }
        }

        if (!tramite.getEstadoTramite().getClave().equals(EstadoTramite.RESUELTO.getClave())
                && !tramite.getEstadoTramite().getClave().equals(EstadoTramite.RESUELTO_NOTIFICADO.getClave())
                && !remisionExterna) {
            return listaDocDTO;
        }
        else {
            return null;
        }
    }

    public void eliminaDocumento(long idDocumentoSol) {
        documentosServices.eliminaDocumentoSol(idDocumentoSol);
    }

    public DocumentoDTOTransformer getDocumentoDTOTransformer() {
        return documentoDTOTransformer;
    }

    public void setDocumentoDTOTransformer(DocumentoDTOTransformer documentoDTOTransformer) {
        this.documentoDTOTransformer = documentoDTOTransformer;
    }

    public DocumentosServices getDocumentosServices() {
        return documentosServices;
    }

    public void setDocumentosServices(DocumentosServices documentosServices) {
        this.documentosServices = documentosServices;
    }

    public RegistroSolicitudCALServices getRegistroSolicitudCALServices() {
        return registroSolicitudCALServices;
    }

    public void setRegistroSolicitudCALServices(RegistroSolicitudCALServices registroSolicitudCALServices) {
        this.registroSolicitudCALServices = registroSolicitudCALServices;
    }

}
