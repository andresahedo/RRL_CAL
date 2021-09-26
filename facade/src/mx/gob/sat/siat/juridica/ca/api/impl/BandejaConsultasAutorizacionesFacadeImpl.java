package mx.gob.sat.siat.juridica.ca.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.TipoTramiteServices;
import mx.gob.sat.siat.juridica.ca.api.BandejaConsultasAutorizacionesFacade;
import mx.gob.sat.siat.juridica.ca.dto.transformer.SolicitudConsultaAutorizacionDTOTransformer;
import mx.gob.sat.siat.juridica.ca.service.BandejaConsultasServices;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("bandejaConsultasAutorizacionesFacade")
public class BandejaConsultasAutorizacionesFacadeImpl extends BaseFacadeImpl implements
        BandejaConsultasAutorizacionesFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 1628047101535111930L;

    @Autowired
    private transient BandejaConsultasServices bandejaConsultasServices;

    @Autowired
    private transient TipoTramiteServices tipoTramiteServices;

    @Autowired
    private transient SolicitudConsultaAutorizacionDTOTransformer solicitudConsultaAutorizacionDTOTransformer;

    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;

    public List<DatosBandejaTareaDTO> obtenerTramites(FiltroBandejaTareaDTO filtroBandeja) {
        List<Tramite> listaTramite =
                bandejaConsultasServices.obtenerTramites(filtroBandeja.getNumeroAsunto(),
                        filtroBandeja.getFechaInicio(), filtroBandeja.getFechaFin(),
                        filtroBandeja.getEstadoProcesal() != null ? filtroBandeja.getEstadoProcesal().getClave() : "",
                        filtroBandeja.getTipoTramite() != null ? filtroBandeja.getTipoTramite().getClave() : "");
        List<DatosBandejaTareaDTO> listDatosBandejaDTO = new ArrayList<DatosBandejaTareaDTO>();

        for (Tramite tramite : listaTramite) {
            tramite.getSolicitud()
                    .getTipoTramite()
                    .setDescripcionModalidad(
                            tipoTramiteServices.obtenerTipoTramite(tramite.getSolicitud().getTipoTramite()
                                    .getIdTipoTramite()));
            DatosBandejaTareaDTO datosBandejaDTO =
                    solicitudConsultaAutorizacionDTOTransformer.transformarTramiteBandeja(tramite);
            listDatosBandejaDTO.add(datosBandejaDTO);
        }
        return listDatosBandejaDTO;
    }

    @Override
    public List<CatalogoDTO> obtenerCatalogoTipoTramites(String idServicio) {
        List<CatalogoDTO> listaModalidades;
        listaModalidades =
                catalogoDTOTransformer.transformarTipoTramite(bandejaConsultasServices
                        .obtenerModalidadesPorServicio(idServicio));
        return listaModalidades;
    }

}
