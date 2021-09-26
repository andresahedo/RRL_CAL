package mx.gob.sat.siat.juridica.rrl.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.CatalogoD;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.AgraviosDTO;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.base.util.constante.CatalogoConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.ConceptosDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionAbogadoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResolucionDTOTransformer extends DTOTransformer<Resolucion, ResolucionAbogadoDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = -4463413691871567638L;
    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ResolucionAbogadoDTO transformarDTO(Resolucion resolucion) {

        ResolucionAbogadoDTO resolucionAbogadoDTO = new ResolucionAbogadoDTO();
        resolucionAbogadoDTO.setIdResolucion(resolucion.getIdResolucion());
        resolucionAbogadoDTO.setIdTramite(resolucion.getTramite().getNumeroAsunto());
        resolucionAbogadoDTO.setIdeSentidoResolucion(resolucion.getIdeSentidoResolucion() != null ? resolucion
                .getIdeSentidoResolucion().toString() : null);
        resolucionAbogadoDTO.setFechaInicioVigencia(resolucion.getFechaInicioVigencia());
        resolucionAbogadoDTO.setFechaResolucion(resolucion.getFechaResolucion());
        resolucionAbogadoDTO.setFechaFinVigencia(resolucion.getFechaFinVigencia());
        resolucionAbogadoDTO.setMontoTotalControvertido(resolucion.getMonto() != null ? resolucion.getMonto() : null);
        resolucionAbogadoDTO
                .setFechaEmision(resolucion.getFechaEmision() != null ? resolucion.getFechaEmision() : null);
        resolucionAbogadoDTO
                .setNumeroOficio(resolucion.getNumeroOficio() != null ? resolucion.getNumeroOficio() : null);

        List<ResolucionImpugnadaDTO> resolucionesDTO = new ArrayList<ResolucionImpugnadaDTO>();

        if (resolucion.getResolucionesImpugnadas() != null && !resolucion.getResolucionesImpugnadas().isEmpty()) {

            for (ResolucionImpugnada resolucionImpugnada : resolucion.getResolucionesImpugnadas()) {

                ResolucionImpugnadaDTO resolucionImpugnadaDTO = new ResolucionImpugnadaDTO();
                resolucionImpugnadaDTO.setId(resolucionImpugnada.getIdResolucionImpugnada());
                UnidadAdministrativaDTO unidad = new UnidadAdministrativaDTO();
                unidad.setClave(resolucionImpugnada.getUnidadAdministrativa() != null ? (resolucionImpugnada
                        .getUnidadAdministrativa().getClave()) : null);
                unidad.setNivel(resolucionImpugnada.getUnidadAdministrativa() != null ? resolucionImpugnada
                        .getUnidadAdministrativa().getNivel() : null);
                resolucionImpugnadaDTO.setAutoridadEmisora(unidad);
                unidad.setNombre(resolucionImpugnada.getUnidadAdministrativa() != null ? (resolucionImpugnada
                        .getUnidadAdministrativa().getNombre()) : null);
                resolucionImpugnadaDTO.setAutoridadEmisora(unidad);
                CatalogoDTO procedimientoDTO = new CatalogoDTO();
                procedimientoDTO.setClave(resolucionImpugnada.getProcedimientoDeriva() != null ? resolucionImpugnada
                        .getProcedimientoDeriva().getClave() : null);
                procedimientoDTO.setDescripcion(resolucionImpugnada.getProcedimientoDeriva() != null
                        ? resolucionImpugnada.getProcedimientoDeriva().getDescripcionGenerica1() : null);
                resolucionImpugnadaDTO.setProcedimiento(procedimientoDTO);
                resolucionImpugnadaDTO.setResImpugnada(resolucionImpugnada.getNumeroFolioResolucionImpugnada());
                resolucionImpugnadaDTO.setFechaEmision(resolucionImpugnada.getFechaEmision());
                resolucionImpugnadaDTO.setDeterminanteCred(resolucionImpugnada.getBlnDeterminanteCredito() != null
                        ? (resolucionImpugnada.getBlnDeterminanteCredito().intValue() == 1 ? true : false) : false);
                if (resolucionImpugnada.getMonto() != null) {
                    resolucionImpugnadaDTO.setMonto(resolucionImpugnada.getMonto());
                }
                if (resolucionImpugnada.getSentidoResolucionImpugnada() != null) {
                    CatalogoDTO sentidoResolucionDTO = new CatalogoDTO();
                    sentidoResolucionDTO.setClave(resolucionImpugnada.getSentidoResolucionImpugnada().getClave());
                    sentidoResolucionDTO.setDescripcion(resolucionImpugnada.getSentidoResolucionImpugnada()
                            .getDescripcionGenerica1());
                    resolucionImpugnadaDTO.setIdeSentidoResolucion(sentidoResolucionDTO);
                }

                List<ConceptosDTO> listaconceptosDTO = new ArrayList<ConceptosDTO>();

                if (resolucionImpugnada.getConceptos() != null && !resolucionImpugnada.getConceptos().isEmpty()) {
                    Long i = Long.valueOf(0);
                    for (ConceptoDetalle concepto : resolucionImpugnada.getConceptos()) {
                        ConceptosDTO conceptosDTO = new ConceptosDTO();
                        CatalogoDTO conceptoValor = new CatalogoDTO();
                        conceptoValor.setClave(concepto.getCatalogoD().getClave());
                        conceptoValor.setDescripcion(concepto.getCatalogoD().getDescripcionGenerica1());
                        conceptosDTO.setConceptoValor(conceptoValor);
                        conceptosDTO.setImporte(concepto.getMontoConcepto());
                        conceptosDTO.setId(i);
                        listaconceptosDTO.add(conceptosDTO);
                        resolucionImpugnadaDTO.setConceptos(listaconceptosDTO);
                        i++;
                    }
                }

                List<AgraviosDTO> listaAgraviosDTO = new ArrayList<AgraviosDTO>();

                if (resolucionImpugnada.getAgravios() != null && !resolucionImpugnada.getAgravios().isEmpty()) {
                    for (AgravioDetalle agravio : resolucionImpugnada.getAgravios()) {
                        AgraviosDTO agraviosDTO = new AgraviosDTO();
                        agraviosDTO.setClave(agravio.getCatalogoD().getClave());
                        agraviosDTO.setDescripcion(agravio.getCatalogoD().getDescripcionGenerica1());
                        if (agravio.getIdResolucionImpugnadaCatD() != null) {
                            agraviosDTO.setIdAgravio(agravio.getIdResolucionImpugnadaCatD().toString());
                        }
                        listaAgraviosDTO.add(agraviosDTO);
                    }
                    resolucionImpugnadaDTO.setAgravios(listaAgraviosDTO);
                }

                resolucionesDTO.add(resolucionImpugnadaDTO);
            }
        }

        if (resolucion.getResolucionesCatalogoD() != null && !resolucion.getResolucionesCatalogoD().isEmpty()) {
            List<CatalogoDTO> listaRecursos = new ArrayList<CatalogoDTO>();
            for (ResolucionCatalogoD resolucionCatalogoD : resolucion.getResolucionesCatalogoD()) {
                CatalogoDTO catalogoDTO = new CatalogoDTO();
                catalogoDTO.setClave(resolucionCatalogoD.getCatalogoD().getClave());
                catalogoDTO.setDescripcion(resolucionCatalogoD.getCatalogoD().getDescripcionGenerica1());
                listaRecursos.add(catalogoDTO);
            }
            resolucionAbogadoDTO.setRecursos(listaRecursos);
        }

        resolucionAbogadoDTO.setResolucionesDTO(resolucionesDTO);

        return resolucionAbogadoDTO;
    }

    public Resolucion transformarModel(ResolucionAbogadoDTO resolucionAbogadoDTO, boolean guardarResolucion) {
        Resolucion resolucion = new Resolucion();
        resolucion.setIdResolucion(resolucionAbogadoDTO.getIdResolucion() != null ? resolucionAbogadoDTO
                .getIdResolucion() : null);
        Tramite tramite = new Tramite();
        tramite.setNumeroAsunto(resolucionAbogadoDTO.getTramiteDTO().getNumeroAsunto());
        resolucion.setTramite(tramite);
        resolucion.setIdeSentidoResolucion(resolucionAbogadoDTO.getIdeSentidoResolucion() != null
                ? resolucionAbogadoDTO.getIdeSentidoResolucion() : null);
        resolucion.setFechaInicioVigencia(resolucionAbogadoDTO.getFechaInicioVigencia() != null ? resolucionAbogadoDTO
                .getFechaInicioVigencia() : null);
        resolucion.setFechaResolucion(resolucionAbogadoDTO.getFechaResolucion() != null ? resolucionAbogadoDTO
                .getFechaResolucion() : null);
        resolucion.setFechaFinVigencia(resolucionAbogadoDTO.getFechaFinVigencia() != null ? resolucionAbogadoDTO
                .getFechaFinVigencia() : null);
        resolucion.setMonto(resolucionAbogadoDTO.getMontoTotalControvertido() != null ? resolucionAbogadoDTO
                .getMontoTotalControvertido() : null);
        resolucion.setFechaEmision(resolucionAbogadoDTO.getFechaEmision() != null ? resolucionAbogadoDTO
                .getFechaEmision() : null);
        resolucion.setNumeroOficio(resolucionAbogadoDTO.getNumeroOficio() != null ? resolucionAbogadoDTO
                .getNumeroOficio() : null);

        if (guardarResolucion) {
            if (resolucionAbogadoDTO.getResolucionesDTO() != null
                    && !resolucionAbogadoDTO.getResolucionesDTO().isEmpty()) {
                List<ResolucionImpugnada> listaResolucionImpugnada = new ArrayList<ResolucionImpugnada>();
                for (ResolucionImpugnadaDTO resolucionImpugnadaDTO : resolucionAbogadoDTO.getResolucionesDTO()) {
                    // Copiar campos de resoluciones impugnadas
                    ResolucionImpugnada resolucionImpugnada = new ResolucionImpugnada();

                    resolucionImpugnada
                            .setNumeroFolioResolucionImpugnada(resolucionImpugnadaDTO.getResImpugnada() != null
                                    ? resolucionImpugnadaDTO.getResImpugnada() : null);
                    UnidadAdministrativa unidadAdministrativa = new UnidadAdministrativa();
                    unidadAdministrativa.setClave(resolucionImpugnadaDTO.getAutoridadEmisora() != null
                            ? resolucionImpugnadaDTO.getAutoridadEmisora().getClave() : null);
                    resolucionImpugnada.setUnidadAdministrativa(unidadAdministrativa);
                    resolucionImpugnada.setMonto(resolucionImpugnadaDTO.getMonto() != null ? resolucionImpugnadaDTO
                            .getMonto() : null);
                    CatalogoD procedimiento = new CatalogoD();
                    procedimiento.setClave(resolucionImpugnadaDTO.getProcedimiento() != null ? resolucionImpugnadaDTO
                            .getProcedimiento().getClave() : null);
                    resolucionImpugnada.setProcedimientoDeriva(procedimiento);
                    if (resolucionImpugnadaDTO.getIdeSentidoResolucion() != null) {
                        CatalogoD sentidoResolucionImpugnada = new CatalogoD();
                        sentidoResolucionImpugnada
                                .setClave(resolucionImpugnadaDTO.getIdeSentidoResolucion().getClave());
                        resolucionImpugnada.setSentidoResolucionImpugnada(sentidoResolucionImpugnada);
                    }
                    resolucionImpugnada.setBlnDeterminanteCredito(resolucionImpugnadaDTO.getDeterminanteCred() ? 1 : 0);
                    resolucionImpugnada.setImportante(resolucionImpugnadaDTO.isImportante());

                    if (resolucionImpugnadaDTO.getConceptos() != null
                            && !resolucionImpugnadaDTO.getConceptos().isEmpty()) {
                        List<ConceptoDetalle> conceptos = new ArrayList<ConceptoDetalle>();
                        for (ConceptosDTO conceptosDTO : resolucionImpugnadaDTO.getConceptos()) {
                            ConceptoDetalle conceptoDetalle = new ConceptoDetalle();
                            CatalogoD catalogoD = new CatalogoD();
                            catalogoD.setClave(conceptosDTO.getConceptoValor().getClave());
                            conceptoDetalle.setCatalogoD(catalogoD);
                            conceptoDetalle.setMontoConcepto(conceptosDTO.getImporte());
                            conceptoDetalle.setResolucionImpugnada(resolucionImpugnada);
                            conceptos.add(conceptoDetalle);
                        }

                        resolucionImpugnada.setConceptos(conceptos);

                    }
                    if (resolucionImpugnadaDTO.getAgravios() != null && !resolucionImpugnadaDTO.getAgravios().isEmpty()) {
                        List<AgravioDetalle> agravios = new ArrayList<AgravioDetalle>();
                        for (AgraviosDTO agravio : resolucionImpugnadaDTO.getAgravios()) {
                            if (agravio.getIdAgravio() == null) {
                                CatalogoD catalogoD = new CatalogoD();
                                catalogoD.setClave(agravio.getClave());
                                AgravioDetalle agravioDetalle = new AgravioDetalle();
                                agravioDetalle.setCatalogoD(catalogoD);
                                // Copiar campos agravio
                                agravios.add(agravioDetalle);
                            }
                        }
                        resolucionImpugnada.setAgravios(agravios);
                    }
                    listaResolucionImpugnada.add(resolucionImpugnada);
                }
                resolucion.setResolucionesImpugnadas(listaResolucionImpugnada);
            }
            if (resolucionAbogadoDTO.getRecursos() != null && !resolucionAbogadoDTO.getRecursos().isEmpty()) {
                List<ResolucionCatalogoD> listaCaracteristicas = new ArrayList<ResolucionCatalogoD>();
                for (CatalogoDTO catalogoDTO : resolucionAbogadoDTO.getRecursos()) {
                    ResolucionCatalogoD resolucionCatalogoD = new ResolucionCatalogoD();
                    resolucionCatalogoD.setIdCatalogoH(CatalogoConstantes.CATALOGO_CARACTERISTICAS);
                    CatalogoD catalogoD = new CatalogoD();
                    catalogoD.setClave(catalogoDTO.getClave());
                    resolucionCatalogoD.setCatalogoD(catalogoD);

                    listaCaracteristicas.add(resolucionCatalogoD);
                }
                resolucion.setResolucionesCatalogoD(listaCaracteristicas);
            }

        }
        else {
            if (resolucionAbogadoDTO.getResolucionesDTO() != null
                    && !resolucionAbogadoDTO.getResolucionesDTO().isEmpty()) {
                List<ResolucionImpugnada> listaResolucionImpugnada = new ArrayList<ResolucionImpugnada>();
                for (ResolucionImpugnadaDTO resolucionImpugnadaDTO : resolucionAbogadoDTO.getResolucionesDTO()) {
                    // Copiar campos de resoluciones impugnadas
                    ResolucionImpugnada resolucionImpugnada = new ResolucionImpugnada();
                    resolucionImpugnada.setIdResolucionImpugnada(resolucionImpugnadaDTO.getId());

                    CatalogoD sentidoResolucionImpugnada = new CatalogoD();
                    sentidoResolucionImpugnada.setClave(resolucionImpugnadaDTO.getIdeSentidoResolucion().getClave());
                    resolucionImpugnada.setSentidoResolucionImpugnada(sentidoResolucionImpugnada);
                    resolucionImpugnada.setImportante(resolucionImpugnadaDTO.isImportante());
                    UnidadAdministrativa unidad = new UnidadAdministrativa();
                    unidad.setClave(resolucionImpugnadaDTO.getAutoridadEmisora() != null ? resolucionImpugnadaDTO
                            .getAutoridadEmisora().getClave() : null);
                    unidad.setDescripcion(resolucionImpugnadaDTO.getAutoridadEmisora() != null ? resolucionImpugnadaDTO
                            .getAutoridadEmisora().getDescripcion() : null);
                    unidad.setClaveUnidadAdminR(resolucionImpugnadaDTO.getAutoridadEmisora()
                            .getClaveUnidadAdministrativaR() != null ? resolucionImpugnadaDTO.getAutoridadEmisora()
                            .getClaveUnidadAdministrativaR() : null);
                    unidad.setNombre(resolucionImpugnadaDTO.getAutoridadEmisora().getNombre() != null
                            ? resolucionImpugnadaDTO.getAutoridadEmisora().getNombre() : null);
                    unidad.setNivel(resolucionImpugnadaDTO.getAutoridadEmisora().getNivel() != null
                            ? resolucionImpugnadaDTO.getAutoridadEmisora().getNivel() : null);
                    resolucionImpugnada.setUnidadAdministrativa(unidad);
                    if (resolucionImpugnadaDTO.getAgravios() != null && !resolucionImpugnadaDTO.getAgravios().isEmpty()) {
                        List<AgravioDetalle> agravios = new ArrayList<AgravioDetalle>();
                        for (AgraviosDTO agravio : resolucionImpugnadaDTO.getAgravios()) {
                            if (agravio.getIdAgravio() == null) {
                                CatalogoD catalogoD = new CatalogoD();
                                catalogoD.setClave(agravio.getClave());
                                AgravioDetalle agravioDetalle = new AgravioDetalle();
                                agravioDetalle.setCatalogoD(catalogoD);
                                // Copiar campos agravio
                                agravios.add(agravioDetalle);
                            }
                        }
                        resolucionImpugnada.setAgravios(agravios);
                    }

                    listaResolucionImpugnada.add(resolucionImpugnada);
                }
                resolucion.setResolucionesImpugnadas(listaResolucionImpugnada);
            }

        }

        return resolucion;
    }

    public Resolucion transformarModelCopia(ResolucionAbogadoDTO resolucionAbogadoDTO, boolean guardarResolucion) {
        Resolucion resolucion = new Resolucion();
        resolucion.setIdResolucion(resolucionAbogadoDTO.getIdResolucion() != null ? resolucionAbogadoDTO
                .getIdResolucion() : null);
        Tramite tramite = new Tramite();
        tramite.setNumeroAsunto(resolucionAbogadoDTO.getTramiteDTO().getNumeroAsunto());
        resolucion.setTramite(tramite);
        resolucion.setIdeSentidoResolucion(resolucionAbogadoDTO.getIdeSentidoResolucion() != null
                ? resolucionAbogadoDTO.getIdeSentidoResolucion() : null);
        resolucion.setFechaInicioVigencia(resolucionAbogadoDTO.getFechaInicioVigencia() != null ? resolucionAbogadoDTO
                .getFechaInicioVigencia() : null);
        resolucion.setFechaResolucion(resolucionAbogadoDTO.getFechaResolucion() != null ? resolucionAbogadoDTO
                .getFechaResolucion() : null);
        resolucion.setFechaFinVigencia(resolucionAbogadoDTO.getFechaFinVigencia() != null ? resolucionAbogadoDTO
                .getFechaFinVigencia() : null);
        resolucion.setMonto(resolucionAbogadoDTO.getMontoTotalControvertido() != null ? resolucionAbogadoDTO
                .getMontoTotalControvertido() : null);
        resolucion.setFechaEmision(resolucionAbogadoDTO.getFechaEmision() != null ? resolucionAbogadoDTO
                .getFechaEmision() : null);
        resolucion.setNumeroOficio(resolucionAbogadoDTO.getNumeroOficio() != null ? resolucionAbogadoDTO
                .getNumeroOficio() : null);

        if (guardarResolucion) {
            if (resolucionAbogadoDTO.getResolucionesDTO() != null
                    && !resolucionAbogadoDTO.getResolucionesDTO().isEmpty()) {
                List<ResolucionImpugnada> listaResolucionImpugnada = new ArrayList<ResolucionImpugnada>();
                for (ResolucionImpugnadaDTO resolucionImpugnadaDTO : resolucionAbogadoDTO.getResolucionesDTO()) {
                    // Copiar campos de resoluciones impugnadas
                    ResolucionImpugnada resolucionImpugnada = new ResolucionImpugnada();

                    resolucionImpugnada
                            .setNumeroFolioResolucionImpugnada(resolucionImpugnadaDTO.getResImpugnada() != null
                                    ? resolucionImpugnadaDTO.getResImpugnada() : null);
                    UnidadAdministrativa unidadAdministrativa = new UnidadAdministrativa();
                    unidadAdministrativa.setClave(resolucionImpugnadaDTO.getAutoridadEmisora() != null
                            ? resolucionImpugnadaDTO.getAutoridadEmisora().getClave() : null);
                    resolucionImpugnada.setUnidadAdministrativa(unidadAdministrativa);
                    resolucionImpugnada.setMonto(resolucionImpugnadaDTO.getMonto() != null ? resolucionImpugnadaDTO
                            .getMonto() : null);
                    CatalogoD procedimiento = new CatalogoD();
                    procedimiento.setClave(resolucionImpugnadaDTO.getProcedimiento() != null ? resolucionImpugnadaDTO
                            .getProcedimiento().getClave() : null);
                    resolucionImpugnada.setProcedimientoDeriva(procedimiento);
                    if (resolucionImpugnadaDTO.getIdeSentidoResolucion() != null) {
                        CatalogoD sentidoResolucionImpugnada = new CatalogoD();
                        sentidoResolucionImpugnada
                                .setClave(resolucionImpugnadaDTO.getIdeSentidoResolucion().getClave());
                        resolucionImpugnada.setSentidoResolucionImpugnada(sentidoResolucionImpugnada);
                    }
                    resolucionImpugnada.setBlnDeterminanteCredito(resolucionImpugnadaDTO.getDeterminanteCred() ? 1 : 0);
                    resolucionImpugnada.setImportante(resolucionImpugnadaDTO.isImportante());
                    resolucionImpugnada.setFechaEmision(resolucionImpugnadaDTO.getFechaEmision());

                    if (resolucionImpugnadaDTO.getConceptos() != null
                            && !resolucionImpugnadaDTO.getConceptos().isEmpty()) {
                        List<ConceptoDetalle> conceptos = new ArrayList<ConceptoDetalle>();
                        for (ConceptosDTO conceptosDTO : resolucionImpugnadaDTO.getConceptos()) {
                            ConceptoDetalle conceptoDetalle = new ConceptoDetalle();
                            CatalogoD catalogoD = new CatalogoD();
                            catalogoD.setClave(conceptosDTO.getConceptoValor().getClave());
                            conceptoDetalle.setCatalogoD(catalogoD);
                            conceptoDetalle.setMontoConcepto(conceptosDTO.getImporte());
                            conceptoDetalle.setResolucionImpugnada(resolucionImpugnada);
                            conceptos.add(conceptoDetalle);
                        }

                        resolucionImpugnada.setConceptos(conceptos);

                    }
                    if (resolucionImpugnadaDTO.getAgravios() != null && !resolucionImpugnadaDTO.getAgravios().isEmpty()) {
                        List<AgravioDetalle> agravios = new ArrayList<AgravioDetalle>();
                        for (AgraviosDTO agravio : resolucionImpugnadaDTO.getAgravios()) {
                            CatalogoD catalogoD = new CatalogoD();
                            catalogoD.setClave(agravio.getClave());
                            AgravioDetalle agravioDetalle = new AgravioDetalle();
                            agravioDetalle.setCatalogoD(catalogoD);
                            // Copiar campos agravio
                            agravios.add(agravioDetalle);
                        }
                        resolucionImpugnada.setAgravios(agravios);
                    }
                    logger.debug("resolucionImpugnada {}", resolucionImpugnada);
                    listaResolucionImpugnada.add(resolucionImpugnada);
                }
                resolucion.setResolucionesImpugnadas(listaResolucionImpugnada);
            }
            if (resolucionAbogadoDTO.getRecursos() != null && !resolucionAbogadoDTO.getRecursos().isEmpty()) {
                List<ResolucionCatalogoD> listaCaracteristicas = new ArrayList<ResolucionCatalogoD>();
                for (CatalogoDTO catalogoDTO : resolucionAbogadoDTO.getRecursos()) {
                    ResolucionCatalogoD resolucionCatalogoD = new ResolucionCatalogoD();
                    resolucionCatalogoD.setIdCatalogoH(CatalogoConstantes.CATALOGO_CARACTERISTICAS);
                    CatalogoD catalogoD = new CatalogoD();
                    catalogoD.setClave(catalogoDTO.getClave());
                    resolucionCatalogoD.setCatalogoD(catalogoD);

                    listaCaracteristicas.add(resolucionCatalogoD);
                }
                resolucion.setResolucionesCatalogoD(listaCaracteristicas);
            }

        }
        else {
            if (resolucionAbogadoDTO.getResolucionesDTO() != null
                    && !resolucionAbogadoDTO.getResolucionesDTO().isEmpty()) {
                List<ResolucionImpugnada> listaResolucionImpugnada = new ArrayList<ResolucionImpugnada>();
                for (ResolucionImpugnadaDTO resolucionImpugnadaDTO : resolucionAbogadoDTO.getResolucionesDTO()) {
                    // Copiar campos de resoluciones impugnadas
                    ResolucionImpugnada resolucionImpugnada = new ResolucionImpugnada();
                    resolucionImpugnada.setIdResolucionImpugnada(resolucionImpugnadaDTO.getId());

                    CatalogoD sentidoResolucionImpugnada = new CatalogoD();
                    sentidoResolucionImpugnada.setClave(resolucionImpugnadaDTO.getIdeSentidoResolucion().getClave());
                    resolucionImpugnada.setSentidoResolucionImpugnada(sentidoResolucionImpugnada);
                    resolucionImpugnada.setImportante(resolucionImpugnadaDTO.isImportante());
                    resolucionImpugnada.setFechaEmision(resolucionImpugnadaDTO.getFechaEmision());
                    UnidadAdministrativa unidad = new UnidadAdministrativa();
                    unidad.setClave(resolucionImpugnadaDTO.getAutoridadEmisora() != null ? resolucionImpugnadaDTO
                            .getAutoridadEmisora().getClave() : null);
                    unidad.setDescripcion(resolucionImpugnadaDTO.getAutoridadEmisora() != null ? resolucionImpugnadaDTO
                            .getAutoridadEmisora().getDescripcion() : null);
                    unidad.setClaveUnidadAdminR(resolucionImpugnadaDTO.getAutoridadEmisora()
                            .getClaveUnidadAdministrativaR() != null ? resolucionImpugnadaDTO.getAutoridadEmisora()
                            .getClaveUnidadAdministrativaR() : null);
                    unidad.setNombre(resolucionImpugnadaDTO.getAutoridadEmisora().getNombre() != null
                            ? resolucionImpugnadaDTO.getAutoridadEmisora().getNombre() : null);
                    unidad.setNivel(resolucionImpugnadaDTO.getAutoridadEmisora().getNivel() != null
                            ? resolucionImpugnadaDTO.getAutoridadEmisora().getNivel() : null);
                    resolucionImpugnada.setUnidadAdministrativa(unidad);
                    if (resolucionImpugnadaDTO.getAgravios() != null && !resolucionImpugnadaDTO.getAgravios().isEmpty()) {
                        List<AgravioDetalle> agravios = new ArrayList<AgravioDetalle>();
                        for (AgraviosDTO agravio : resolucionImpugnadaDTO.getAgravios()) {
                            CatalogoD catalogoD = new CatalogoD();
                            catalogoD.setClave(agravio.getClave());
                            AgravioDetalle agravioDetalle = new AgravioDetalle();
                            agravioDetalle.setCatalogoD(catalogoD);
                            // Copiar campos agravio
                            agravios.add(agravioDetalle);
                        }
                        resolucionImpugnada.setAgravios(agravios);
                    }
                    logger.debug("resolucionImpugnada {}", resolucionImpugnada);
                    listaResolucionImpugnada.add(resolucionImpugnada);
                }
                resolucion.setResolucionesImpugnadas(listaResolucionImpugnada);
            }

        }

        return resolucion;
    }

    public Resolucion transformarModel(ResolucionAbogadoDTO resolucionAbogadoDTO) {
        Resolucion resolucion = new Resolucion();
        resolucion.setIdResolucion(resolucionAbogadoDTO.getIdResolucion() != null ? resolucionAbogadoDTO
                .getIdResolucion() : null);
        Tramite tramite = new Tramite();
        tramite.setNumeroAsunto(resolucionAbogadoDTO.getTramiteDTO().getNumeroAsunto());
        resolucion.setTramite(tramite);
        resolucion.setIdeSentidoResolucion(resolucionAbogadoDTO.getIdeSentidoResolucion() != null
                ? resolucionAbogadoDTO.getIdeSentidoResolucion() : null);
        resolucion.setFechaInicioVigencia(resolucionAbogadoDTO.getFechaInicioVigencia() != null ? resolucionAbogadoDTO
                .getFechaInicioVigencia() : null);
        resolucion.setFechaResolucion(resolucionAbogadoDTO.getFechaResolucion() != null ? resolucionAbogadoDTO
                .getFechaResolucion() : null);
        resolucion.setFechaFinVigencia(resolucionAbogadoDTO.getFechaFinVigencia() != null ? resolucionAbogadoDTO
                .getFechaFinVigencia() : null);
        resolucion.setMonto(resolucionAbogadoDTO.getMontoTotalControvertido() != null ? resolucionAbogadoDTO
                .getMontoTotalControvertido() : null);
        resolucion.setNumeroOficio(resolucionAbogadoDTO.getNumeroOficio() != null ? resolucionAbogadoDTO
                .getNumeroOficio() : null);
        resolucion.setFechaEmision(resolucionAbogadoDTO.getFechaEmision() != null ? resolucionAbogadoDTO
                .getFechaEmision() : null);

        if (resolucionAbogadoDTO.getResolucionesDTO() != null && !resolucionAbogadoDTO.getResolucionesDTO().isEmpty()) {
            List<ResolucionImpugnada> listaResolucionImpugnada = new ArrayList<ResolucionImpugnada>();
            for (ResolucionImpugnadaDTO resolucionImpugnadaDTO : resolucionAbogadoDTO.getResolucionesDTO()) {
                // Copiar campos de resoluciones impugnadas
                ResolucionImpugnada resolucionImpugnada = new ResolucionImpugnada();

                resolucionImpugnada.setNumeroFolioResolucionImpugnada(resolucionImpugnadaDTO.getResImpugnada() != null
                        ? resolucionImpugnadaDTO.getResImpugnada() : null);
                UnidadAdministrativa unidadAdministrativa = new UnidadAdministrativa();
                unidadAdministrativa.setClave(resolucionImpugnadaDTO.getAutoridadEmisora().getClave() != null
                        ? resolucionImpugnadaDTO.getAutoridadEmisora().getClave() : null);
                resolucionImpugnada.setUnidadAdministrativa(unidadAdministrativa);
                resolucionImpugnada.setMonto(resolucionImpugnadaDTO.getMonto() != null ? resolucionImpugnadaDTO
                        .getMonto() : null);
                CatalogoD procedimiento = new CatalogoD();
                procedimiento.setClave(resolucionImpugnadaDTO.getProcedimiento() != null ? resolucionImpugnadaDTO
                        .getProcedimiento().getClave() : null);
                resolucionImpugnada.setProcedimientoDeriva(procedimiento);
                if (resolucionImpugnadaDTO.getIdeSentidoResolucion() != null) {
                    CatalogoD sentidoResolucionImpugnada = new CatalogoD();
                    sentidoResolucionImpugnada.setClave(resolucionImpugnadaDTO.getIdeSentidoResolucion().getClave());
                    resolucionImpugnada.setSentidoResolucionImpugnada(sentidoResolucionImpugnada);
                }
                resolucionImpugnada.setBlnDeterminanteCredito(resolucionImpugnadaDTO.getDeterminanteCred() ? 1 : 0);
                resolucionImpugnada.setImportante(resolucionImpugnadaDTO.isImportante());

                if (resolucionImpugnadaDTO.getConceptos() != null && !resolucionImpugnadaDTO.getConceptos().isEmpty()) {
                    List<ConceptoDetalle> conceptos = new ArrayList<ConceptoDetalle>();
                    for (ConceptosDTO conceptosDTO : resolucionImpugnadaDTO.getConceptos()) {
                        ConceptoDetalle conceptoDetalle = new ConceptoDetalle();
                        CatalogoD catalogoD = new CatalogoD();
                        catalogoD.setClave(conceptosDTO.getConceptoValor().getClave());
                        conceptoDetalle.setCatalogoD(catalogoD);

                        conceptoDetalle.setMontoConcepto(conceptosDTO.getImporte());
                        conceptoDetalle.setResolucionImpugnada(resolucionImpugnada);
                        conceptos.add(conceptoDetalle);
                    }

                    resolucionImpugnada.setConceptos(conceptos);

                }

                CatalogoD sentidoResolucionImpugnada = new CatalogoD();
                sentidoResolucionImpugnada.setClave(resolucionImpugnadaDTO.getIdeSentidoResolucion().getClave());
                resolucionImpugnada.setSentidoResolucionImpugnada(sentidoResolucionImpugnada);

                if (resolucionImpugnadaDTO.getAgravios() != null && !resolucionImpugnadaDTO.getAgravios().isEmpty()) {
                    List<AgravioDetalle> agravios = new ArrayList<AgravioDetalle>();
                    for (AgraviosDTO agravio : resolucionImpugnadaDTO.getAgravios()) {
                        CatalogoD catalogoD = new CatalogoD();
                        catalogoD.setClave(agravio.getClave());
                        AgravioDetalle agravioDetalle = new AgravioDetalle();
                        agravioDetalle.setCatalogoD(catalogoD);
                        // Copiar campos agravio
                        agravios.add(agravioDetalle);
                    }
                    resolucionImpugnada.setAgravios(agravios);
                }

                listaResolucionImpugnada.add(resolucionImpugnada);
            }
            resolucion.setResolucionesImpugnadas(listaResolucionImpugnada);
        }
        if (resolucionAbogadoDTO.getRecursos() != null && !resolucionAbogadoDTO.getRecursos().isEmpty()) {
            List<ResolucionCatalogoD> listaCaracteristicas = new ArrayList<ResolucionCatalogoD>();
            for (CatalogoDTO catalogoDTO : resolucionAbogadoDTO.getRecursos()) {
                ResolucionCatalogoD resolucionCatalogoD = new ResolucionCatalogoD();
                resolucionCatalogoD.setIdCatalogoH(CatalogoConstantes.CATALOGO_CARACTERISTICAS);
                CatalogoD catalogoD = new CatalogoD();
                catalogoD.setClave(catalogoDTO.getClave());
                resolucionCatalogoD.setCatalogoD(catalogoD);
                listaCaracteristicas.add(resolucionCatalogoD);
            }
            resolucion.setResolucionesCatalogoD(listaCaracteristicas);
        }

        return resolucion;
    }

}
