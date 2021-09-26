package mx.gob.sat.siat.juridica.rrl.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.DocumentosServices;
import mx.gob.sat.siat.juridica.rrl.api.AutorizarRemisionFacade;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.TramiteDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.service.AutorizarRemitirService;
import mx.gob.sat.siat.juridica.rrl.util.exception.RemitirAsuntoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("autorizarRemisionFacade")
public class AutorizarRemisionFacadeImpl extends BaseFacadeImpl implements AutorizarRemisionFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private transient AutorizarRemitirService autorizarRemitirService;

    @Autowired
    private transient TramiteDTOTransformer tramiteDTOTransformer;

    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;

    @Autowired
    private transient DocumentoDTOTransformer documentoTransformer;

    @Autowired
    private transient DocumentosServices documentosServices;

    @Override
    public String obtenerNombre(String rfc) {
        return autorizarRemitirService.obtenerNombre(rfc);
    }

    @Override
    public TramiteDTO obtenerTramite(String numAsunto) {
        Tramite tramite = autorizarRemitirService.obtenerTramite(numAsunto);
        return tramiteDTOTransformer.transformarDTO(tramite);
    }

    @Override
    public List<CatalogoDTO> obtenerAutoridadesEmisoras() {
        List<UnidadAdministrativa> listaUA = autorizarRemitirService.obtenerCatalogo();

        return catalogoDTOTransformer.transformarDTO(listaUA);
    }

    @Override
    public String obtenerUnidad(String numAsunto) {
        return autorizarRemitirService.obtenerUnidad(numAsunto);
    }

    /**
     * M&eacute;todo para rechazar el requerimiento.
     */
    @Override
    public void rechazarRemision(String numAsunto, String idTarea, String rfc, Long idSolicitud) {

        autorizarRemitirService.rechazarRemision(numAsunto, idTarea, rfc, idSolicitud);
    }

    @Override
    public void actualizarRemision(String numeroAsunto, String unidad) throws RemitirAsuntoException {
        autorizarRemitirService.actualizarRemision(numeroAsunto, unidad);
    }

    @Override
    public List<DocumentoDTO> obtenerDocumentosComplementariosAutorizacion(long idSolicitud) {
        return documentoTransformer.tranformarDocSolicitud(documentosServices
                .obtenerDocumentosComplementariosAutorizacion(idSolicitud));
    }
}
