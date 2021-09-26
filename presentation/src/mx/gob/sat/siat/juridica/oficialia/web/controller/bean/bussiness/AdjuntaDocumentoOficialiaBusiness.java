package mx.gob.sat.siat.juridica.oficialia.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitud;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.oficialia.api.AdjuntaDocumentoOficialiaFacade;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.io.InputStream;
import java.util.List;

@NoneScoped
@ManagedBean(name = "adjuntaDocumentoOficialiaBussines")
public class AdjuntaDocumentoOficialiaBusiness extends BaseCloudBusiness {

    /**
     * 
     */
    private static final long serialVersionUID = 6507914040021186433L;

    @ManagedProperty(value = "#{adjuntaDocumentoOficialiaFacade}")
    private AdjuntaDocumentoOficialiaFacade adjuntaDocumentoOficialiaFacade;

    public InputStream descargarArchivo(DocumentoDTO documento) {
        return getAdjuntaDocumentoOficialiaFacade().descargarArchivo(documento.getRuta());
    }

    public void guardarDocumentosSolicitud(Solicitud solicitud, List<DocumentoDTO> listaDocumentos,
            List<DocumentoDTO> listDocumentosRequeridos, String rfcCapturista, DocumentoDTO documentoDto)
            throws ArchivoNoGuardadoException {
        getAdjuntaDocumentoOficialiaFacade().guardarDocumentosSolicitud(solicitud, listaDocumentos, rfcCapturista,
                documentoDto);
    }

    public List<DocumentoDTO> obtenerDocumentosTramite(String idTipoTramite, String numAsunto) {
        return getAdjuntaDocumentoOficialiaFacade().obtenerTiposDocumentos(idTipoTramite, numAsunto);
    }

    public Solicitud obtenerDatos(Long idSolicitud) {
        return getAdjuntaDocumentoOficialiaFacade().obtenerDatosSolicitud(idSolicitud);
    }

    public void eliminaDocumento(long idDocumentoSol) {
        getAdjuntaDocumentoOficialiaFacade().eliminaDocumento(idDocumentoSol);
    }

    public AdjuntaDocumentoOficialiaFacade getAdjuntaDocumentoOficialiaFacade() {
        return adjuntaDocumentoOficialiaFacade;
    }

    public void setAdjuntaDocumentoOficialiaFacade(AdjuntaDocumentoOficialiaFacade adjuntaDocumentoOficialiaFacade) {
        this.adjuntaDocumentoOficialiaFacade = adjuntaDocumentoOficialiaFacade;
    }

    @Override
    public BaseCloudFacade getCloudFacade() {
        return adjuntaDocumentoOficialiaFacade;
    }

}
