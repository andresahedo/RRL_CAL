package mx.gob.sat.siat.juridica.rrl.api.impl;

import mx.gob.sat.siat.juridica.base.api.impl.AtenderObservacionFacadeImpl;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.SolicitudDatosGenerales;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.rrl.api.AtenderObservacionRRLFacade;
import mx.gob.sat.siat.juridica.rrl.dao.domain.model.SolicitudRecursoRevocacion;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DatosSolicitudDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.service.RegistroRecursoRevocacionServices;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("atenderObservacionRRLFacade")
public class AtenderObservacionRRLFacadeImpl extends AtenderObservacionFacadeImpl implements
        AtenderObservacionRRLFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -2618487078338702338L;

    @Autowired
    private transient RegistroRecursoRevocacionServices registroRecursoRevocacionServices;

    @Autowired
    private transient DatosSolicitudDTOTransformer datosSolicitudDTOTransformer;
    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;
    @Autowired
    private transient SolicitudService solicitudService;

    @Override
    public void modificarSolicitud(DatosSolicitudDTO datosSolicitud) throws SolicitudNoGuardadaException {
        SolicitudRecursoRevocacion solicitud = datosSolicitudDTOTransformer.transformarSolicitudModel(datosSolicitud);
        solicitudService.modificarSolicitudRRL(solicitud);
    }

    @Override
    public List<CatalogoDTO> obtenerAutoridadesEmisoras() {
        List<UnidadAdministrativa> listaUA = registroRecursoRevocacionServices.obtenerCatalogo();

        return catalogoDTOTransformer.transformarDTO(listaUA);
    }

    @Override
    public DatosSolicitudDTO obtenerSolicitud(Long idSolicitud) {
        SolicitudDatosGenerales solicitud = solicitudService.obtenerSolicitudConsultaAutorizacionporId(idSolicitud);
        return datosSolicitudDTOTransformer.transformarDTO(solicitud);
    }

}
