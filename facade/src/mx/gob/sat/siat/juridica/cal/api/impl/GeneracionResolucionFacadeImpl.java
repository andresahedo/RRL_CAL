/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoResolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoUnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.UnidadAdministrativaDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.*;
import mx.gob.sat.siat.juridica.base.util.constante.EnumeracionConstantes;
import mx.gob.sat.siat.juridica.ca.service.RegistroSolicitudConsultaAutorizacionServices;
import mx.gob.sat.siat.juridica.cal.api.GeneracionResolucionFacade;
import mx.gob.sat.siat.juridica.cal.dto.BienResarcimientoDTO;
import mx.gob.sat.siat.juridica.cal.dto.ResolucionDatosGeneradosDTO;
import mx.gob.sat.siat.juridica.cal.dto.transformer.BienResarcimientoDTOTransformer;
import mx.gob.sat.siat.juridica.cal.dto.transformer.ResolucionDatosGeneradosDTOTransformer;
import mx.gob.sat.siat.juridica.cal.dto.transformer.TipoTramiteDTOTransformer;
import mx.gob.sat.siat.juridica.cal.service.GenerarResolucionCALService;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.TramiteDTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa los metodos de la interfaz
 * GeneracionResolucionFacade
 * 
 * @author antonio.flores Softtek
 * @since 03/27/2014
 */
@Component("generacionResolucionFacade")
public class GeneracionResolucionFacadeImpl extends BaseFacadeImpl implements GeneracionResolucionFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 1229723192102321956L;
    /**
     * 
     */
    @Autowired
    private transient CatalogoDServices catalogoDServices;

    /**
     * 
     */
    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;
    /**
     * 
     */
    @Autowired
    private transient RegistroSolicitudConsultaAutorizacionServices registroSolicitudConsultaAutorizacionServices;
    /**
     * 
     */
    @Autowired
    private transient BienResarcimientoDTOTransformer bienResarcimientoDTOTransformer;
    /**
     * 
     */
    @Autowired
    private transient GenerarResolucionCALService generarResolucionCALService;
    /**
     * 
     */
    @Autowired
    private transient ResolucionDatosGeneradosDTOTransformer resolucionDatosGeneradosDTOTransformer;
    /**
     * 
     */
    @Autowired
    private transient UnidadAdministrativaServices unidadAdministrativaServices;
    /**
     * 
     */
    @Autowired
    private transient UnidadAdministrativaDTOTransformer unidadAdministrativaDTOTransformer;
    /**
     * 
     */
    @Autowired
    private transient TipoTramiteServices tipoTramiteServices;
    /**
     * 
     */
    @Autowired
    private transient TramiteServices tramiteServices;
    /**
     * 
     */
    @Autowired
    private transient TramiteDTOTransformer tramiteDTOTransformer;
    /**
     * 
     */
    @Autowired
    private transient TipoTramiteDTOTransformer tipoTramiteDTOTransformer;

    /**
     * 
     */
    @Autowired
    private transient RequerimientoServices requerimientoServices;
    
    @Autowired
    private transient ConsultaSolicitudServices consultaSolicitudServices;

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.ca.api.GeneracionResolucionFacade#
     * obtenerResolucionCatalog()
     */
    @Override
    public List<CatalogoDTO> obtenerResolucionCatalog(String type) {
        List<CatalogoD> listaCatalogo = catalogoDServices.obtenerCatalogoPorIdH(type);

        return catalogoDTOTransformer.transformarCatalogoDDTO(listaCatalogo);
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.ca.api.GeneracionResolucionFacade#
     * obtenerAutorizadores(java.lang.String)
     */

    public List<CatalogoDTO> obtenerAutorizadores(String type) {
        List<Persona> listaAutorizadores = requerimientoServices.obtenerAutorizadores(type);
        return catalogoDTOTransformer.transformarPersonas(listaAutorizadores);
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.api.GeneracionResolucionFacade
     * #obtenerMediosDeDefensa()
     */
    @Override
    public List<CatalogoDTO> obtenerMediosDeDefensa() {
        List<EnumeracionTr> listaEnumeraciones =
                registroSolicitudConsultaAutorizacionServices
                        .obtenerEnumeracionPorId(EnumeracionConstantes.TIPOS_MEDIOS_DEFENSA);
        return catalogoDTOTransformer.transformarEnumeracionTrDTO(listaEnumeraciones);
    }

    @Override
    public void rechazarResolucion(String numAsunto, String idTarea, String rfc, Long idSolicitud) {
        generarResolucionCALService.rechazarResolucion(numAsunto, idTarea, rfc, idSolicitud);
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.api.GeneracionResolucionFacade
     * #obtenerMediosDeTransporte()
     */
    @Override
    public List<CatalogoDTO> obtenerMediosDeTransporte() {
        List<EnumeracionTr> listaEnumeraciones =
                registroSolicitudConsultaAutorizacionServices
                        .obtenerEnumeracionPorId(EnumeracionConstantes.MEDIOS_TRANSPORTE);
        return catalogoDTOTransformer.transformarEnumeracionTrDTO(listaEnumeraciones);
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.api.GeneracionResolucionFacade
     * #guardaBienResolucion
     * (mx.gob.sat.siat.juridica.cal.dto.BienResarcimientoDTO)
     */
    @Override
    public void guardaBienResolucion(BienResarcimientoDTO bienResarcimientoDTO) {
        generarResolucionCALService.guardarBienResarcimiento(bienResarcimientoDTOTransformer
                .transformarResolucionDatosGenerados(bienResarcimientoDTO));
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.api.GeneracionResolucionFacade
     * #guardarResolucionclasificacion
     * (mx.gob.sat.siat.juridica.cal.dto.ResolucionCALDTO)
     */
    @Override
    public void guardarResolucionclasificacion(ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO,
            String rfcAbogado) {
        resolucionDatosGeneradosDTO.setIdeEstadoResolucion(EstadoResolucion.EN_FIRMA.getClave()); 
                                                                                                  // La
                                                                                                  // resolucion
                                                                                                  // permanece
                                                                                                  // en
                                                                                                  // FIRMA

        Resolucion resolucion =
                this.resolucionDatosGeneradosDTOTransformer
                        .getResolucionFromResolucionDatosGeneradosDTO(resolucionDatosGeneradosDTO);
        resolucion.setRfcAbogado(rfcAbogado);
        resolucion = generarResolucionCALService.guardarResolucion(resolucion);
        resolucionDatosGeneradosDTO.setResolucion(resolucion.getIdResolucion());
        ResolucionDatosGenerados resolucionDatosGenerados =
                this.resolucionDatosGeneradosDTOTransformer
                        .transformarResolucionDatosGenerados(resolucionDatosGeneradosDTO);
        generarResolucionCALService.guardaDatosGenerados(resolucionDatosGenerados);
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.api.GeneracionResolucionFacade
     * #obtenerAutoridades()
     */
    @Override
    public List<UnidadAdministrativaDTO> obtenerAutoridades() {
        List<UnidadAdministrativa> listaUnidadAdministrativa = unidadAdministrativaServices.obtenerCatalogo();
        return unidadAdministrativaDTOTransformer.transformarDTO(listaUnidadAdministrativa);
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.api.GeneracionResolucionFacade
     * #obtenerBienesResolucion
     * (mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO)
     */
    @Override
    public List<BienResarcimientoDTO> obtenerBienesResolucion(ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO) {
        List<BienResarcimiento> listBienResarcimiento =
                generarResolucionCALService.obtenerBienesResolucion(resolucionDatosGeneradosDTO.getResolucion());
        if (listBienResarcimiento != null) {
            return bienResarcimientoDTOTransformer.transformarDTO(listBienResarcimiento);
        }
        else {
            return new ArrayList<BienResarcimientoDTO>();
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.api.GeneracionResolucionFacade
     * #obtenerTipoSolicitud(java.lang.String)
     */
    @Override
    public TramiteDTO obtenerSolicitud(String numero) {
        Tramite tramite = tramiteServices.buscarTramite(numero, null);
        TramiteDTO tramiteDTO = tramiteDTOTransformer.transformarDTO(tramite);
        tramiteDTO.setTipoTramite(tramite.getSolicitud().getTipoTramite().getIdTipoTramite().toString());
        return tramiteDTO;
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.api.GeneracionResolucionFacade
     * #getTipoTramiteModulo()
     */
    @Override
    public List<TipoTramiteDTO> getTipoTramiteModulo(String idTipoTramite) {
        List<TipoTramite> tiposTramite = tipoTramiteServices.obtenerTipoTramiteModulo(idTipoTramite);
        return tipoTramiteDTOTransformer.transformarDTOList(tiposTramite);
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.api.GeneracionResolucionFacade
     * #guardarResolucionResarcimiento
     * (mx.gob.sat.siat.juridica.cal.dto.ResolucionDatosGeneradosDTO)
     */
    @Override
    public void guardarResolucionResarcimiento(ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO,
            String rfcAbogado) {
        resolucionDatosGeneradosDTO.setIdeEstadoResolucion(EstadoResolucion.EN_FIRMA.getClave()); 
                                                                                                  // La
                                                                                                  // resolucion
                                                                                                  // se
                                                                                                  // guarda
                                                                                                  // con
                                                                                                  // el
                                                                                                  // estado
                                                                                                  // EN
                                                                                                  // FIRMA

        Resolucion resolucion =
                this.resolucionDatosGeneradosDTOTransformer
                        .getResolucionFromResolucionDatosGeneradosDTO(resolucionDatosGeneradosDTO);
        resolucion.setRfcAbogado(rfcAbogado);
        resolucion = generarResolucionCALService.guardarResolucion(resolucion);
        resolucionDatosGeneradosDTO.setResolucion(resolucion.getIdResolucion());
        resolucionDatosGeneradosDTO.setUnidadAdministrativa(resolucionDatosGeneradosDTO.getAutoridadEmbargo());
        ResolucionDatosGenerados resolucionDatosGenerados =
                this.resolucionDatosGeneradosDTOTransformer
                        .transformarResolucionDatosGenerados(resolucionDatosGeneradosDTO);
        generarResolucionCALService.guardaDatosGenerados(resolucionDatosGenerados);
        guardaBienResarcimiento(resolucionDatosGeneradosDTO.getBienesResolucion(), resolucion);
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.api.GeneracionResolucionFacade
     * #obtenerResolucionDatosGenerados(java.lang.String)
     */
    @Override
    public ResolucionDatosGeneradosDTO obtenerResolucionDatosGenerados(String numeroAsunto) {
        Resolucion resolucion = generarResolucionCALService.obtenerResolucionNumeroAsunto(numeroAsunto);
        if (null != resolucion) {
            ResolucionDatosGenerados resolucionDatosGenerados =
                    generarResolucionCALService.obtenerResolucionDatosGenerados(resolucion.getIdResolucion());
            return resolucionDatosGeneradosDTOTransformer.transformaDTO(resolucionDatosGenerados,
                    resolucionDatosGenerados.getResolucion());
        }
        else {
            return new ResolucionDatosGeneradosDTO();
        }
    }

    /**
     */
    public void actualizaDatosBP(DatosBandejaTareaDTO datosBandejaTareaDTO,
            ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO) {
        generarResolucionCALService.actualizaDatosBP(datosBandejaTareaDTO.getIdTareaUsuario().toString(),
                datosBandejaTareaDTO.getNumeroAsunto(), datosBandejaTareaDTO.getRfcUsuarioAsignado(),
                resolucionDatosGeneradosDTO.getRfcFirmante());
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.api.GeneracionResolucionFacade
     * #actualizarResolucion
     * (mx.gob.sat.siat.juridica.cal.dto.ResolucionDatosGeneradosDTO,
     * mx.gob.sat.siat.juridica.cal.dto.ResolucionDatosGeneradosDTO)
     */
    @Override
    public void actualizarResolucion(ResolucionDatosGeneradosDTO dto,
            ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO) {
        Resolucion resolucion =
                generarResolucionCALService.obtenerResolucionNumeroAsunto(dto.getTramiteDTO().getNumeroAsunto());
        dto.setIdeEstadoResolucion(resolucion.getIdeEstadoResolucion()); 
        Resolucion resolucionWC =
                resolucionDatosGeneradosDTOTransformer.getResolucionFromResolucionDatosGeneradosDTO(dto);
        resolucionWC.setIdResolucion(resolucion.getIdResolucion());
        resolucionWC.setRfcAbogado(resolucion.getRfcAbogado());
        generarResolucionCALService.actualizaResolucion(resolucionWC);
        ResolucionDatosGenerados resolucionDatosGenerados =
                resolucionDatosGeneradosDTOTransformer.transformarResolucionDatosGenerados(resolucionDatosGeneradosDTO);

        resolucionDatosGenerados.setResolucion(resolucion);
        resolucionDatosGenerados.setUnidadAdministrativa(dto.getAutoridadEmbargo() == null ? null
                : new UnidadAdministrativa(dto.getAutoridadEmbargo()));
        resolucionDatosGenerados.setTipoMedioPago(dto.getMedioDefensa());
        resolucionDatosGenerados.setNumeroOficio(dto.getNumeroOficio());
        resolucionDatosGenerados.setFechaEmbargo(dto.getFechaEmbargo());
        resolucionDatosGenerados.setMontoTotal(dto.getMontoTotalOrdenado());
        resolucionDatosGenerados.setCumplimentacion(dto.isCumplimentacion());
        resolucionDatosGenerados.setNumeroAsunto(dto.getNumeroAsuntoResarcimiento());
        resolucionDatosGenerados.setMontoSentencia(dto.getMontoSentencia());
        resolucionDatosGenerados.setFechaOficio(dto.getFechaResolucion());

        resolucionDatosGenerados.setConfirmaFraccion(dto.isConfirmaFraccion());
        resolucionDatosGenerados.setFraccionArancelaria(dto.getFraccionArancelaria());
        resolucionDatosGenerados.setNumeroMinuta(dto.getNumeroMinuta());
        resolucionDatosGenerados.setFechaSesion(dto.getFechaSesion());

        generarResolucionCALService.actualizarResolucionDatosGenerados(resolucionDatosGenerados);
        // Guarda los bienes de la resolucion de resarcimeinto.
        if (dto.getBienesResolucion() != null && !dto.getBienesResolucion().isEmpty()) {
            guardaBienResarcimiento(dto.getBienesResolucion(), resolucion);
        }
        // Borrado de los bienes resolucion que el usuario elimino.
        if (dto.getBienesResolucionDelete() != null && !dto.getBienesResolucionDelete().isEmpty()) {
            borraBienesResolucion(dto.getBienesResolucionDelete(), resolucion);
        }
    }

    /**
     * M&eacute;todo para guardar los bienes del resarcimiento.
     * 
     * @param bienesResolucionList
     * @param resolucion
     */
    private void guardaBienResarcimiento(List<BienResarcimientoDTO> bienesResolucionList, Resolucion resolucion) {
        List<BienResarcimiento> bienesBorrar =
                generarResolucionCALService.obtenerBienesResolucion(resolucion.getIdResolucion());
        if (bienesBorrar != null && !bienesBorrar.isEmpty()) {
            for (BienResarcimiento bn : bienesBorrar) {
                bn.setActivo(false);
                generarResolucionCALService.guardarBienResarcimiento(bn);
            }
        }

        for (BienResarcimientoDTO bienes : bienesResolucionList) {

            bienes.setResolucion(resolucion.getIdResolucion());
            BienResarcimiento bien = bienResarcimientoDTOTransformer.transformarResolucionDatosGenerados(bienes);
            bien.setActivo(true);
            generarResolucionCALService.guardarBienResarcimiento(bien);

        }
    }

    /**
     * M&eacute;todo para borrar los bienes del resarcimiento
     * 
     * @param bienesResolucionDelete
     * @param resolucion
     */
    private void borraBienesResolucion(List<BienResarcimientoDTO> bienesResolucionDelete, Resolucion resolucion) {
        for (BienResarcimientoDTO bienesDelete : bienesResolucionDelete) {
            if (bienesDelete.getBienRerarcimiento() != null) {
                bienesDelete.setResolucion(resolucion.getIdResolucion());
                BienResarcimiento bien =
                        bienResarcimientoDTOTransformer.transformarResolucionDatosGenerados(bienesDelete);
                bien.setActivo(false);
                generarResolucionCALService.guardarBienResarcimiento(bien);
            }
        }
    }

          @Override
          public boolean obtenerBlnAvanzarResolucion(String numeroAsunto) {
                    List<Requerimiento> requerimientosAutoridad = 
                                        consultaSolicitudServices.obtenerRequermientosAutoridad(numeroAsunto);
                    if(requerimientosAutoridad == null || requerimientosAutoridad.isEmpty()){
                              return true;
                    } else {
                              List<Requerimiento> requerimientosNoNotificados = new ArrayList<Requerimiento>();
                              for(Requerimiento requerimiento : requerimientosAutoridad){
                                        if((requerimiento.getUnidadAdministrativa().getIdeTipoUnidadAdministrativa().getClave().equals(TipoUnidadAdministrativa.ADMINISTRACION_EXTERNA.getClave())
                                                            || requerimiento.getUnidadAdministrativa().getIdeTipoUnidadAdministrativa().getClave().equals(TipoUnidadAdministrativa.ADMINISTRACION_GENERAL.getClave())
                                                            || requerimiento.getUnidadAdministrativa().getIdeTipoUnidadAdministrativa().getClave().equals(TipoUnidadAdministrativa.ADMINISTRACION_INTERNA.getClave()))
                                                            && requerimiento.getFechaVerificacion() == null){
                                                  requerimientosNoNotificados.add(requerimiento);
                                        }
                              }
                              return requerimientosNoNotificados.isEmpty();
                    }
          }

}
