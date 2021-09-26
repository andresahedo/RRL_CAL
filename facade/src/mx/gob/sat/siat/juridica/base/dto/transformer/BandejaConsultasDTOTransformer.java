package mx.gob.sat.siat.juridica.base.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dao.domain.model.MotivoRechazo;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.BandejaConsultaRechazoDTO;
import mx.gob.sat.siat.juridica.base.dto.BandejaConsultasDTO;
import mx.gob.sat.siat.juridica.base.dto.BandejaConsultasDescarga;
import mx.gob.sat.siat.juridica.base.service.CatalogoDServices;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BandejaConsultasDTOTransformer extends DTOTransformer<List<Tramite>, List<BandejaConsultasDTO>> {

    private static final long serialVersionUID = 9064019243339290107L;

    @Autowired
    private CatalogoDServices catalogoServices;

    @Override
    public List<BandejaConsultasDTO> transformarDTO(List<Tramite> source) {

        List<BandejaConsultasDTO> bandejaConsultasDTOs = new ArrayList<BandejaConsultasDTO>();
        for (Tramite tramite : source) {
            BandejaConsultasDTO dto = new BandejaConsultasDTO();
            dto.setEstadoProcesal(tramite.getEstadoTramite());
            dto.setFechaInicio(tramite.getFechaInicioTramite());

            dto.setNumAsunto(tramite.getNumeroAsunto());
            dto.setUrl("/resources/pages/consultas/consultaPromovente.jsf");
            dto.setIdSolicitud(tramite.getSolicitud().getIdSolicitud());
            dto.setTipoTramite(tramite.getSolicitud().getDiscriminatorValue());
            dto.setFechaRecepcion(tramite.getFechaRecepcion());
            dto.setEdoToUser(catalogoServices.obtenerCatalogoPorClave(tramite.getEstadoTramite().getClave())
                    .getDescripcionGenerica1());
            if (tramite.getSolicitud().getTipoTramiteSolicitud() != null) {
                dto.setDescripcionTipoTramite(tramite.getSolicitud().getTipoTramiteSolicitud()
                        .getDescripcionModalidad());
            }
            bandejaConsultasDTOs.add(dto);
        }
        return bandejaConsultasDTOs;
    }
    
    
  public DataPage transformarDTODescargaLazy(List<Tramite> source) {
      DataPage dataPage = new DataPage();
        List<BandejaConsultasDescarga> bandejaConsultaDescarga = new ArrayList<BandejaConsultasDescarga>();
        
        for (Tramite tramite : source) {
            BandejaConsultasDescarga dto = new BandejaConsultasDescarga();
            dto.setEstadoProcesal(tramite.getEstadoTramite());
            dto.setFechaInicio(tramite.getFechaInicioTramite());
            dto.setNumAsunto(tramite.getNumeroAsunto());
            dto.setUrl("/resources/pages/consultas/consultaAsuntosConcluidos.jsf");
            dto.setIdSolicitud(tramite.getSolicitud().getIdSolicitud());
            dto.setTipoTramite(tramite.getSolicitud().getDiscriminatorValue());
            dto.setFechaRecepcion(tramite.getFechaRecepcion());            
            dto.setEdoToUser(catalogoServices.obtenerCatalogoPorClave(tramite.getEstadoTramite().getClave())
                    .getDescripcionGenerica1());
           dto.setRfcSolicitante(tramite.getSolicitud().getCveUsuarioCapturista());
            
            if (tramite.getSolicitud().getTipoTramiteSolicitud() != null) {
                dto.setDescripcionTipoTramite(tramite.getSolicitud().getTipoTramiteSolicitud()
                        .getDescripcionModalidad());
            }
            bandejaConsultaDescarga.add(dto);
        }
        dataPage.setData(bandejaConsultaDescarga);
         return dataPage;
    }

    public BandejaConsultasDTO transformarDTOTramite(Tramite tramite) {

        BandejaConsultasDTO dto = new BandejaConsultasDTO();
        dto.setEstadoProcesal(tramite.getEstadoTramite());
        dto.setFechaInicio(tramite.getFechaInicioTramite());
        if (tramite.getSolicitud().getSolicitante().getNombre() != null) {
            dto.setNombre(tramite.getSolicitud().getSolicitante().getNombre() + " "
                    + tramite.getSolicitud().getSolicitante().getApellidoPaterno() + " "
                    + tramite.getSolicitud().getSolicitante().getApellidoMaterno());
        }
        else {
            dto.setNombre(tramite.getSolicitud().getSolicitante().getRazonSocial());
        }
        dto.setNumAsunto(tramite.getNumeroAsunto());
        dto.setIdSolicitud(tramite.getSolicitud().getIdSolicitud());
        dto.setTipoTramite(tramite.getSolicitud().getDiscriminatorValue());
        dto.setFechaRecepcion(tramite.getFechaRecepcion());
        dto.setRfc(tramite.getSolicitud().getSolicitante().getRfc());
        dto.setDescripcionTipoTramite(tramite.getSolicitud().getTipoTramiteSolicitud().getDescripcionModalidad());
        return dto;
    }

    public BandejaConsultaRechazoDTO transformarDTORechazos(MotivoRechazo motivoRechazo) {

        BandejaConsultaRechazoDTO dto = new BandejaConsultaRechazoDTO();
        dto.setEstadoProcesal(motivoRechazo.getTramite().getEstadoTramite());
        dto.setFechaInicio(motivoRechazo.getTramite().getFechaInicioTramite());
        dto.setNumAsunto(motivoRechazo.getTramite().getNumeroAsunto());
        dto.setRfcPromovente(motivoRechazo.getTramite().getSolicitud().getSolicitante().getRfc());
        dto.setTipoTramite(motivoRechazo.getTramite().getSolicitud().getDiscriminatorValue());
        dto.setCveRechazo(motivoRechazo.getCveRechazo());
        dto.setIdUsuario(motivoRechazo.getIdUsuario());
        dto.setFechaCreacion(motivoRechazo.getFechaRechazo());

        return dto;
    }

}
