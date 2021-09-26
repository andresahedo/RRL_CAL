package mx.gob.sat.siat.juridica.base.api.impl;

import mx.gob.sat.siat.juridica.base.api.BandejaReasignarRecursoRevocacionFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.OrdenBandeja;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tarea;
import mx.gob.sat.siat.juridica.base.service.TareaServices;
import mx.gob.sat.siat.juridica.base.service.TipoTramiteServices;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component("bandejaReasignarRecursoRevocacionFacade")
public class BandejaReasignarRecursoRevocacionFacadePOJOImpl implements BandejaReasignarRecursoRevocacionFacade {

    private static final String ASC = "ASC";
    private static final String DESC = "DESC";

    @Autowired
    private transient TareaServices tareaServices;
    
    @Autowired
    private transient TramiteServices tramiteServices;
    
    @Autowired
    private transient TipoTramiteServices tipoTramiteServices;
    
    private transient Logger logger = LoggerFactory.getLogger(this.getClass());

    public DataPage obtenerTareasReasignar(FiltroBandejaTareaDTO filtroBandejaTareaDTO, String cveRol) {
        DataPage dataPage = new DataPage();
        List<DatosBandejaTareaDTO> datosBandejaTareaDTOs = new ArrayList<DatosBandejaTareaDTO>();
        String administrador = (filtroBandejaTareaDTO.getUsuario() != null && !filtroBandejaTareaDTO.getUsuario().isEmpty()) ? filtroBandejaTareaDTO.getUsuario() : null;
        String numeroAsunto = (filtroBandejaTareaDTO.getNumeroAsunto() != null && !filtroBandejaTareaDTO.getNumeroAsunto().isEmpty()) ? filtroBandejaTareaDTO.getNumeroAsunto() : null;
        String abogado = (filtroBandejaTareaDTO.getRfcFuncionario() != null && !filtroBandejaTareaDTO.getRfcFuncionario().isEmpty()) ? filtroBandejaTareaDTO.getRfcFuncionario() : null; 
        List<Tarea> listaTarea = tareaServices.buscarTareasReasignar(numeroAsunto, administrador, abogado);
        for(Tarea tarea : listaTarea) {
            DatosBandejaTareaDTO bandejaTareaDTO = new DatosBandejaTareaDTO();
            String estadoProcesal = tramiteServices.buscarTramiteTarea(tarea.getNumeroAsunto()).getEstadoTramite().getClave();
            bandejaTareaDTO.setCveEstadoProcesal(estadoProcesal);
            bandejaTareaDTO.setEstadoProcesal(EstadoTramite.parse(estadoProcesal).getDescripcion());
            bandejaTareaDTO.setIdTareaUsuario(tarea.getIdTarea());
            bandejaTareaDTO.setIdInstancia(tarea.getIdTarea());
            bandejaTareaDTO.setIdSolicitud(tramiteServices.buscarTramiteTarea(tarea.getNumeroAsunto()).getSolicitud().getIdSolicitud());
            bandejaTareaDTO.setNombreTarea(tarea.getDescripcion());
            bandejaTareaDTO.setFechaAsignacion(tarea.getFechaCreacion());
            int idtipotramite = tramiteServices.buscarTramiteTarea(tarea.getNumeroAsunto()).getSolicitud().getTipoTramite().getIdTipoTramite();
            bandejaTareaDTO.setTipoTramite(Integer.toString(idtipotramite));
            bandejaTareaDTO.setDescripcionTipoTramite(tipoTramiteServices.obtenerTipoTramiteId(idtipotramite).getDescripcionModalidad());
            bandejaTareaDTO.setNumeroAsunto(tarea.getNumeroAsunto());
            bandejaTareaDTO.setRfcSolicitante(tramiteServices.buscarTramiteTarea(tarea.getNumeroAsunto()).getSolicitud().getCveUsuarioCapturista());
            bandejaTareaDTO.setRfcUsuarioAsignado(tarea.getClaveAsignado());
            setRedirectInfo(bandejaTareaDTO);
            datosBandejaTareaDTOs.add(bandejaTareaDTO);
        }
        logger.debug("Orden:"+filtroBandejaTareaDTO.getOrden());
        datosBandejaTareaDTOs = ordenaTareas(datosBandejaTareaDTOs, filtroBandejaTareaDTO.getOrden());
        dataPage.setData(datosBandejaTareaDTOs);
        dataPage.setTotalOfRecords(datosBandejaTareaDTOs.size());
        return dataPage;
    }
    
    private List<DatosBandejaTareaDTO> ordenaTareas(List<DatosBandejaTareaDTO> datosBandejaTareaDTOs, String orden){
        if(orden.equals(OrdenBandeja.BDESTADO_PROCESAL+" "+ASC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d1.getCveEstadoProcesal().compareTo(d2.getCveEstadoProcesal());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDESTADO_PROCESAL+" "+DESC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d2.getCveEstadoProcesal().compareTo(d1.getCveEstadoProcesal());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDFOLIO_TRAMITE+" "+ASC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d1.getNumeroAsunto().compareTo(d2.getNumeroAsunto());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDFOLIO_TRAMITE+" "+DESC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d2.getNumeroAsunto().compareTo(d1.getNumeroAsunto());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDDESCRIPCION_TIPO_TRAMITE+" "+ASC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d1.getTipoTramite().compareTo(d2.getTipoTramite());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDDESCRIPCION_TIPO_TRAMITE+" "+DESC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d2.getTipoTramite().compareTo(d1.getTipoTramite());
                }
            });
        }else if(orden.equals(OrdenBandeja.FUNCIONARIO+" "+ASC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d1.getRfcUsuarioAsignado().compareTo(d2.getRfcUsuarioAsignado());
                }
            });
        }else if(orden.equals(OrdenBandeja.FUNCIONARIO+" "+DESC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                    return d2.getRfcUsuarioAsignado().compareTo(d1.getRfcUsuarioAsignado());
                }
            });
        }else {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d2.getFechaAsignacion().compareTo(d1.getFechaAsignacion()); 
                }
            });
        }
        return datosBandejaTareaDTOs;
    } 
    
    /*
     * Asigna datos de "redireccionamiento" adicionales. Debe asignar
     * la URL
     */
    protected void setRedirectInfo(DatosBandejaTareaDTO bandejaTareaDTO) {
        bandejaTareaDTO.setUrl("/resources/pages/rrl/turnarRecursoRevocacion.jsf"); 
        if (bandejaTareaDTO.getTipoTramite() != null
                && bandejaTareaDTO.getTipoTramite().startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
            bandejaTareaDTO.setUrl("/resources/pages/cal/seguimiento/turnarAsunto.jsf");
        }
    }

}
