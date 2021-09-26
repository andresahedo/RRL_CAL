package mx.gob.sat.siat.juridica.ca.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.TipoTramiteServices;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.ca.api.DetallePromocionFacade;
import mx.gob.sat.siat.juridica.ca.service.DetallePromocionServices;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.TramiteDTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("detallePromocionFacade")
public class DetallePromocionFacadeImpl extends BaseFacadeImpl implements DetallePromocionFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private transient DetallePromocionServices detallePromocionServices;

    @Autowired
    private transient TipoTramiteServices tipoTramiteServices;

    @Autowired
    private transient TramiteDTOTransformer tramiteDTOTransformer;

    public TramiteDTO obtenerTramite(String numAasunto) {
        Tramite tramite = detallePromocionServices.buscarTramite(numAasunto, null);
        tramite.getSolicitud()
                .getTipoTramite()
                .setDescripcionModalidad(
                        tipoTramiteServices.obtenerTipoTramite(tramite.getSolicitud().getTipoTramite()
                                .getIdTipoTramite()));
        return tramiteDTOTransformer.transformarDTO(tramite);
    }

    public void modificarTramite(TramiteDTO tramiteDTO) throws SolicitudNoGuardadaException {
        detallePromocionServices.modificaEstadoProcesal(tramiteDTO.getNumeroAsunto(), tramiteDTO.getCatEstadoProcesal()
                .getClave());
    }

}
