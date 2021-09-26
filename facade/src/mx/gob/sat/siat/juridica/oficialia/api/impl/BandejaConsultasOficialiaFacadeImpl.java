package mx.gob.sat.siat.juridica.oficialia.api.impl;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.MotivoRechazo;
import mx.gob.sat.siat.juridica.base.dto.BandejaConsultaRechazoDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.BandejaConsultasDTOTransformer;
import mx.gob.sat.siat.juridica.base.service.CatalogoDServices;
import mx.gob.sat.siat.juridica.base.service.DocumentosServices;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.cal.op.service.MotivosRechazoServices;
import mx.gob.sat.siat.juridica.oficialia.api.BandejaConsultasOficialiaFacade;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component("bandejaConsultasOficialiaFacade")
public class BandejaConsultasOficialiaFacadeImpl implements BandejaConsultasOficialiaFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 7988743328425499837L;

    @Autowired
    private transient TramiteServices tramiteServices;
    @Autowired
    private transient BandejaConsultasDTOTransformer bandejaConsultasDTOTransformer;
    @Autowired
    private transient MotivosRechazoServices motivoServices;
    @Autowired
    private transient CatalogoDServices catalogoService;
    @Autowired
    private transient DocumentosServices documentoServices;

    @Override
    public List<BandejaConsultaRechazoDTO> obtenerTareasRechazo(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {

        Calendar fechaIni = null;
        Calendar fechaFin = null;

        if (filtroBandejaTareaDTO.getFechaInicio() != null && filtroBandejaTareaDTO.getFechaFin() != null) {
            fechaIni = Calendar.getInstance();
            fechaFin = Calendar.getInstance();
            fechaIni.setTime(filtroBandejaTareaDTO.getFechaInicio());
            fechaIni.set(Calendar.HOUR_OF_DAY, 0);
            fechaIni.set(Calendar.MINUTE, 0);
            fechaIni.set(Calendar.SECOND, 0);
            fechaIni.set(Calendar.MILLISECOND, 0);
            fechaFin = Calendar.getInstance();
            fechaFin.setTime(filtroBandejaTareaDTO.getFechaFin());
            fechaFin.set(Calendar.HOUR_OF_DAY, NumerosConstantes.VEINTITRES);
            fechaFin.set(Calendar.MINUTE, NumerosConstantes.CINCUENTA_NUEVE);
            fechaFin.set(Calendar.SECOND, NumerosConstantes.CINCUENTA_NUEVE);
            fechaFin.set(Calendar.MILLISECOND, NumerosConstantes.CINCUENTA_NUEVE);
        }

        List<MotivoRechazo> tramitesRechazados =
                motivoServices.obtenerTramitesDoctosRechazadosPorFiltros((!filtroBandejaTareaDTO.getNumeroAsunto()
                        .equals("") ? filtroBandejaTareaDTO.getNumeroAsunto() : null), EstadoTramite.RECHAZADO
                        .getClave(), fechaIni != null ? fechaIni.getTime() : null,
                        fechaFin != null ? fechaFin.getTime() : null, (!filtroBandejaTareaDTO.getRfcFuncionario()
                                .equals("") ? filtroBandejaTareaDTO.getRfcFuncionario() : null));
        List<BandejaConsultaRechazoDTO> listaRechazos = new ArrayList<BandejaConsultaRechazoDTO>();
        for (MotivoRechazo motivoRechazo : tramitesRechazados) {
            BandejaConsultaRechazoDTO bandejaConsultaRechazoDTO =
                    bandejaConsultasDTOTransformer.transformarDTORechazos(motivoRechazo);
            bandejaConsultaRechazoDTO.setDescripcionRechazo(catalogoService.obtenerCatalogoPorClave(
                    motivoRechazo.getCveRechazo()).getDescripcionGenerica1());
            bandejaConsultaRechazoDTO.setDescripcionTipoTramite(motivoServices
                    .obtenerModalidadTramitePorClave(motivoRechazo.getTramite().getSolicitud().getTipoTramite()
                            .getIdTipoTramite()));
            listaRechazos.add(bandejaConsultaRechazoDTO);
        }
        return listaRechazos;
    }

    public List<BandejaConsultaRechazoDTO> obtenerTareasDoctoRechazo(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {
        Calendar fechaIni = null;
        Calendar fechaFin = null;

        if (filtroBandejaTareaDTO.getFechaInicio() != null && filtroBandejaTareaDTO.getFechaFin() != null) {
            fechaIni = Calendar.getInstance();
            fechaFin = Calendar.getInstance();
            fechaIni.setTime(filtroBandejaTareaDTO.getFechaInicio());
            fechaIni.set(Calendar.HOUR_OF_DAY, 0);
            fechaIni.set(Calendar.MINUTE, 0);
            fechaIni.set(Calendar.SECOND, 0);
            fechaIni.set(Calendar.MILLISECOND, 0);
            fechaFin = Calendar.getInstance();
            fechaFin.setTime(filtroBandejaTareaDTO.getFechaFin());
            fechaFin.set(Calendar.HOUR_OF_DAY, NumerosConstantes.VEINTITRES);
            fechaFin.set(Calendar.MINUTE, NumerosConstantes.CINCUENTA_NUEVE);
            fechaFin.set(Calendar.SECOND, NumerosConstantes.CINCUENTA_NUEVE);
            fechaFin.set(Calendar.MILLISECOND, NumerosConstantes.CINCUENTA_NUEVE);
        }

        List<MotivoRechazo> tramitesRechazados =
                motivoServices.obtenerTramitesDoctosRechazadosPorFiltros((!filtroBandejaTareaDTO.getNumeroAsunto()
                        .equals("") ? filtroBandejaTareaDTO.getNumeroAsunto() : null), EstadoDocumento.RECHAZADO
                        .getClave(), fechaIni != null ? fechaIni.getTime() : null,
                        fechaFin != null ? fechaFin.getTime() : null, (!filtroBandejaTareaDTO.getRfcFuncionario()
                                .equals("") ? filtroBandejaTareaDTO.getRfcFuncionario() : null));
        List<BandejaConsultaRechazoDTO> listaRechazos = new ArrayList<BandejaConsultaRechazoDTO>();
        for (MotivoRechazo motivoRechazo : tramitesRechazados) {
            BandejaConsultaRechazoDTO bandejaConsultaRechazoDTO =
                    bandejaConsultasDTOTransformer.transformarDTORechazos(motivoRechazo);
            bandejaConsultaRechazoDTO.setDescripcionRechazo(catalogoService.obtenerCatalogoPorClave(
                    motivoRechazo.getCveRechazo()).getDescripcionGenerica1());
            bandejaConsultaRechazoDTO.setDescripcionTipoTramite(motivoServices
                    .obtenerModalidadTramitePorClave(motivoRechazo.getTramite().getSolicitud().getTipoTramite()
                            .getIdTipoTramite()));
            bandejaConsultaRechazoDTO.setTipoDoc(documentoServices.obtenerTipoDocumentoPorId(
                    motivoRechazo.getDocumentoSolicitud().getIdTipoDocumento()).getNombre());
            listaRechazos.add(bandejaConsultaRechazoDTO);
        }
        return listaRechazos;
    }

    public TramiteServices getTramiteServices() {
        return tramiteServices;
    }

    public void setTramiteServices(TramiteServices tramiteServices) {
        this.tramiteServices = tramiteServices;
    }

    public BandejaConsultasDTOTransformer getBandejaConsultasDTOTransformer() {
        return bandejaConsultasDTOTransformer;
    }

    public void setBandejaConsultasDTOTransformer(BandejaConsultasDTOTransformer bandejaConsultasDTOTransformer) {
        this.bandejaConsultasDTOTransformer = bandejaConsultasDTOTransformer;
    }

}
