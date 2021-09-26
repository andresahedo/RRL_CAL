package mx.gob.sat.siat.juridica.base.api.impl;

import mx.gob.sat.siat.juridica.base.api.BandejaConsultasFacade;
import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.OrdenBandeja;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRol;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.BandejaConsultasDTO;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.BandejaConsultasDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.service.DocumentosServices;
import mx.gob.sat.siat.juridica.base.service.EnumeracionServices;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.base.util.constante.CatalogoConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.EnumeracionDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component("bandejaConsultasFacade")
public class BandejaConsultasFacadeImpl implements BandejaConsultasFacade {

    private static final long serialVersionUID = 6152518838474579127L;
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";
    private transient Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private transient TramiteServices tramiteServices;
    @Autowired
    private transient BandejaConsultasDTOTransformer bandejaConsultasDTOTransformer;
    @Autowired
    private transient EnumeracionServices enumeracionServices;
    @Autowired
    private transient EnumeracionDTOTransformer enumeracionDTOTransformer;
    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;
    @Autowired
    private DocumentosServices documentoServices;
    @Autowired
    private SolicitudService solicitudService;

    public List<BandejaConsultasDTO> obtenerRecursos(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {

        Calendar fechaIni = null;
        Calendar fechaFin = null;

        if (filtroBandejaTareaDTO.getFechaInicio() != null && filtroBandejaTareaDTO.getFechaFin() != null) {
            fechaIni = Calendar.getInstance();
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

        List<Tramite> tramites =
                tramiteServices.obtenerTramitesPorFiltros(
                        (!filtroBandejaTareaDTO.getNumeroAsunto().equals("") ? filtroBandejaTareaDTO.getNumeroAsunto()
                                : null),
                        (filtroBandejaTareaDTO.getTipoTramite() != null ? Integer.valueOf(filtroBandejaTareaDTO
                                .getTipoTramite().getClave()) : null),
                        (filtroBandejaTareaDTO.getEstadoProcesal() != null ? filtroBandejaTareaDTO.getEstadoProcesal()
                                .getClave() : null), fechaIni != null ? fechaIni.getTime() : null, fechaFin != null
                                ? fechaFin.getTime() : null, filtroBandejaTareaDTO.getUsuario());
        return bandejaConsultasDTOTransformer.transformarDTO(tramites);
    }

    
    
    public DataPage obtenerRecursosConcluidosLazy(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {

        Calendar fechaIni = null;
        Calendar fechaFin = null;

        if (filtroBandejaTareaDTO.getFechaInicio() != null && filtroBandejaTareaDTO.getFechaFin() != null) {
            fechaIni = Calendar.getInstance();
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

        List<Tramite> tramites =
                tramiteServices.obtenerTramitesPorAsuntosConcluidos(
                    (!filtroBandejaTareaDTO.getNumeroAsunto().equals("") ? filtroBandejaTareaDTO.getNumeroAsunto() : null),
                    (filtroBandejaTareaDTO.getEstadoProcesal() != null ? filtroBandejaTareaDTO.getEstadoProcesal() .getClave() : null), 
                    (fechaIni != null ? fechaIni.getTime() : null),
                    (fechaFin != null ? fechaFin.getTime() : null), 
                    filtroBandejaTareaDTO.getUsuario(),
                    (!filtroBandejaTareaDTO.getRfcPromovente().equals("") ? filtroBandejaTareaDTO.getRfcPromovente(): null)
                );
         
        logger.debug("Orden:"+filtroBandejaTareaDTO.getOrden());
        tramites = ordenaTramites(tramites,filtroBandejaTareaDTO.getOrden());
        return bandejaConsultasDTOTransformer.transformarDTODescargaLazy(tramites);
    } 
    
    private List<Tramite> ordenaTramites(List<Tramite> tramites, String orden){
        
        if(orden.equals(OrdenBandeja.BDESTADO_PROCESAL+" "+ASC) ) {
            Collections.sort(tramites, new Comparator<Tramite>() {
                @Override
                public int compare(Tramite t1, Tramite t2) {
                  return   t1.getEstadoTramite().getDescripcion().compareTo(t2.getEstadoTramite().getDescripcion());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDESTADO_PROCESAL+" "+DESC) ) {
            Collections.sort(tramites, new Comparator<Tramite>() {
                @Override
                public int compare(Tramite t1, Tramite t2) {
                  return t2.getEstadoTramite().getDescripcion().compareTo(t1.getEstadoTramite().getDescripcion());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDFOLIO_TRAMITE+" "+ASC) ) {
            Collections.sort(tramites, new Comparator<Tramite>() {
                @Override
                public int compare(Tramite t1, Tramite t2) {
                  return t1.getNumeroAsunto().compareTo(t2.getNumeroAsunto());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDFOLIO_TRAMITE+" "+DESC) ) {
            Collections.sort(tramites, new Comparator<Tramite>() {
                @Override
                public int compare(Tramite t1, Tramite t2) {
                    return t2.getNumeroAsunto().compareTo(t1.getNumeroAsunto());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDRFC_PROMOVENTE+" "+ASC) ) {
            Collections.sort(tramites, new Comparator<Tramite>() {
                @Override
                public int compare(Tramite t1, Tramite t2) {
                  return t1.getSolicitud().getCveUsuarioCapturista().compareTo(t2.getSolicitud().getCveUsuarioCapturista());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDRFC_PROMOVENTE+" "+DESC) ) {
            Collections.sort(tramites, new Comparator<Tramite>() {
                @Override
                public int compare(Tramite t1, Tramite t2) {
                    return t2.getSolicitud().getCveUsuarioCapturista().compareTo(t1.getSolicitud().getCveUsuarioCapturista());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDDESCRIPCION_TIPO_TRAMITE+" "+ASC) ) {
            logger.debug("BDDESCRIPCION_TIPO_TRAMITE ASC");
            Collections.sort(tramites, new Comparator<Tramite>() {
                @Override
                public int compare(Tramite t1, Tramite t2) {
                    String t1Desc = "";
                    String t2Desc = "";
                    if (t1.getSolicitud().getTipoTramiteSolicitud() != null) {
                        t1Desc =t1.getSolicitud().getTipoTramiteSolicitud().getDescripcionModalidad();
                    }
                    if (t2.getSolicitud().getTipoTramiteSolicitud() != null) {
                        t2Desc =t2.getSolicitud().getTipoTramiteSolicitud().getDescripcionModalidad();
                    }
                  return t1Desc.compareTo(t2Desc);
                }
            });
        }else if(orden.equals(OrdenBandeja.BDDESCRIPCION_TIPO_TRAMITE+" "+DESC) ) {
            logger.debug("BDDESCRIPCION_TIPO_TRAMITE DESC");
            Collections.sort(tramites, new Comparator<Tramite>() {
                @Override
                public int compare(Tramite t1, Tramite t2) {
                    String t1Desc = "";
                    String t2Desc = "";
                    if (t1.getSolicitud().getTipoTramiteSolicitud() != null) {
                        t1Desc =t1.getSolicitud().getTipoTramiteSolicitud().getDescripcionModalidad();
                    }
                    if (t2.getSolicitud().getTipoTramiteSolicitud() != null) {
                        t2Desc =t2.getSolicitud().getTipoTramiteSolicitud().getDescripcionModalidad();
                    }
                  return t2Desc.compareTo(t1Desc);
                }
            });
        }else {
            Collections.sort(tramites, new Comparator<Tramite>() {
                @Override
                public int compare(Tramite t1, Tramite t2) {
                    return t2.getFechaRecepcion().compareTo(t1.getFechaRecepcion());
                }
            });
        }
        return tramites;
    }
    

    
    public List<BandejaConsultasDTO> obtenerRecursosOficialia(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {

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

        List<BandejaConsultasDTO> listaTramites = new ArrayList<BandejaConsultasDTO>();
        BandejaConsultasDTO bandejaConsultasDTO = null;

        if (filtroBandejaTareaDTO.isBanderaFolio()) {
            List<Long> idSolicitud =
                    documentoServices.obtenerIdSolicitudPorFolio(Long.valueOf(filtroBandejaTareaDTO.getFolio()));
            for (Long idSol : idSolicitud) {
                Tramite tramite =
                        tramiteServices.obtenerTramitePorFiltrosAndIdSolicitud(
                                (!filtroBandejaTareaDTO.getNumeroAsunto().equals("") ? filtroBandejaTareaDTO
                                        .getNumeroAsunto() : null),
                                (filtroBandejaTareaDTO.getEstadoProcesal() != null ? filtroBandejaTareaDTO
                                        .getEstadoProcesal().getClave() : null),
                                (fechaIni != null ? fechaIni.getTime() : null),
                                (fechaFin != null ? fechaFin.getTime() : null),
                                (!filtroBandejaTareaDTO.getRfcPromovente().equals("") ? filtroBandejaTareaDTO
                                        .getRfcPromovente() : null), idSol);

                if (TipoRol.OFICIAL_PARTES.getClave().equals(
                        solicitudService.obtenerSolicitudConsultaAutorizacionporId(idSol).getCveRolCapturista())) {
                    bandejaConsultasDTO = bandejaConsultasDTOTransformer.transformarDTOTramite(tramite);
                    bandejaConsultasDTO.setFolio(filtroBandejaTareaDTO.getFolio());
                    listaTramites.add(bandejaConsultasDTO);
                }
            }

        }
        else {
            List<Tramite> tramites =
                    tramiteServices.obtenerTramitesPorFiltrosOficialia((!filtroBandejaTareaDTO.getNumeroAsunto()
                            .equals("") ? filtroBandejaTareaDTO.getNumeroAsunto() : null),
                            (filtroBandejaTareaDTO.getEstadoProcesal() != null ? filtroBandejaTareaDTO
                                    .getEstadoProcesal().getClave() : null), fechaIni != null ? fechaIni.getTime()
                                    : null, fechaFin != null ? fechaFin.getTime() : null, (!filtroBandejaTareaDTO
                                    .getRfcPromovente().equals("") ? filtroBandejaTareaDTO.getRfcPromovente() : null));

            for (Tramite tramite : tramites) {
                if (TipoRol.OFICIAL_PARTES.getClave().equals(
                        solicitudService.obtenerSolicitudConsultaAutorizacionporId(
                                tramite.getSolicitud().getIdSolicitud()).getCveRolCapturista())) {
                    List<Long> listaDocumentoSol =
                            documentoServices.obtenerDocumentosFirmadosOfialia(
                                    tramite.getSolicitud().getIdSolicitud(),
                                    !filtroBandejaTareaDTO.getFolio().equals("") ? Long.valueOf(filtroBandejaTareaDTO
                                            .getFolio()) : 0);
                    for (Long documentoSolicitud : listaDocumentoSol) {
                        bandejaConsultasDTO = bandejaConsultasDTOTransformer.transformarDTOTramite(tramite);
                        bandejaConsultasDTO.setFolio(documentoSolicitud != null ? documentoSolicitud.toString() : "");
                        listaTramites.add(bandejaConsultasDTO);
                    }
                }
            }
        }
        return listaTramites;
    }

    public List<CatalogoDTO> obtenerCatalogoEstados() {
        return enumeracionDTOTransformer.transformarDTO(enumeracionServices
                .obtenerEnumeracionPorId(CatalogoConstantes.ENU_ESTADO_TRAMITE));
    }

    public List<CatalogoDTO> obtenerTiposTramites() {
        return catalogoDTOTransformer.transformarTipoTramite(tramiteServices.obtenerTiposDeTramitesPorFiltros(null,
                null));
    }



    @Override
    public boolean verificarAdminResponsable(String rfcFuncionario) {
        return tramiteServices.verificarAdminResponsable(rfcFuncionario);
    }

}
