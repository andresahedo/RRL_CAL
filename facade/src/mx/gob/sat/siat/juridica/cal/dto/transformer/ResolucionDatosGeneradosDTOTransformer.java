/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Resolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.ResolucionDatosGenerados;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.cal.dto.ResolucionDatosGeneradosDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Clase para transformar objetos del tipo ResolucionDatosGenerados y
 * ResolucionDatosGeneradosDTO.
 * 
 * @author Softtek.
 * 
 */
@Component
public class ResolucionDatosGeneradosDTOTransformer extends
        DTOTransformer<List<ResolucionDatosGenerados>, List<ResolucionDatosGeneradosDTO>> {

    /**
     * 
     */
    private static final long serialVersionUID = -1649462546658560287L;

    /**
     * M&eacute;todo para trasformar ResolucionDatosGeneradosDTO a
     * ResolucionDatosGenerados.
     * 
     * @param resolucionDatosGeneradosDTO
     * @return
     */
    public ResolucionDatosGenerados transformarResolucionDatosGenerados(
            ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO) {

        ResolucionDatosGenerados resolucionDatosGenerados = new ResolucionDatosGenerados();

        resolucionDatosGenerados.setConfirmaFraccion(resolucionDatosGeneradosDTO.isConfirmaFraccion());
        resolucionDatosGenerados.setCumplimentacion(resolucionDatosGeneradosDTO.isCumplimentacion());
        resolucionDatosGenerados.setFechaEmbargo(resolucionDatosGeneradosDTO.getFechaEmbargo());
        resolucionDatosGenerados.setFechaSesion(resolucionDatosGeneradosDTO.getFechaSesion());
        resolucionDatosGenerados.setFraccionArancelaria(resolucionDatosGeneradosDTO.getFraccionArancelaria());
        resolucionDatosGenerados.setMontoSentencia(resolucionDatosGeneradosDTO.getMontoSentencia());
        resolucionDatosGenerados.setMontoTotal(resolucionDatosGeneradosDTO.getMontoTotalOrdenado());
        resolucionDatosGenerados.setNumeroAsunto(resolucionDatosGeneradosDTO.getNumeroAsunto());
        resolucionDatosGenerados.setNumeroMinuta(resolucionDatosGeneradosDTO.getNumeroMinuta());
        resolucionDatosGenerados.setTipoMedioPago(resolucionDatosGeneradosDTO.getMedioDefensa());
        resolucionDatosGenerados.setResolucion(new Resolucion(resolucionDatosGeneradosDTO.getResolucion()));
        resolucionDatosGenerados.setNumeroOficio(resolucionDatosGeneradosDTO.getNumeroOficio());
        resolucionDatosGenerados.setFechaOficio(resolucionDatosGeneradosDTO.getFechaResolucion());
        if (resolucionDatosGeneradosDTO.getUnidadAdministrativa() != null) {
            resolucionDatosGenerados.setUnidadAdministrativa(new UnidadAdministrativa(resolucionDatosGeneradosDTO
                    .getUnidadAdministrativa()));
        }
        else {
            resolucionDatosGenerados.setUnidadAdministrativa(null);
        }

        return resolucionDatosGenerados;
    }

    /**
     * M&ecute;todo para transformar ResolucionDatosGenerados a
     * ResolucionDatosGeneradosDTO
     * 
     * @param resolucionDatosGenerados
     * @return
     */
    public ResolucionDatosGeneradosDTO transformaDTO(ResolucionDatosGenerados resolucionDatosGenerados,
            Resolucion resolucion) {

        ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO = new ResolucionDatosGeneradosDTO();

        if (resolucionDatosGenerados != null) {
            resolucionDatosGeneradosDTO.setConfirmaFraccion(resolucionDatosGenerados.isConfirmaFraccion());
            resolucionDatosGeneradosDTO.setCumplimentacion(resolucionDatosGenerados.isCumplimentacion());
            resolucionDatosGeneradosDTO.setFechaSesion(resolucionDatosGenerados.getFechaSesion());
            resolucionDatosGeneradosDTO.setFechaEmbargo(resolucionDatosGenerados.getFechaEmbargo());
            resolucionDatosGeneradosDTO.setFraccionArancelaria(resolucionDatosGenerados.getFraccionArancelaria());
            resolucionDatosGeneradosDTO.setMontoSentencia(resolucionDatosGenerados.getMontoSentencia());
            resolucionDatosGeneradosDTO.setMontoTotalOrdenado(resolucionDatosGenerados.getMontoTotal());
            resolucionDatosGeneradosDTO.setNumeroAsunto(resolucionDatosGenerados.getNumeroAsunto());
            resolucionDatosGeneradosDTO.setNumeroMinuta(resolucionDatosGenerados.getNumeroMinuta());
            resolucionDatosGeneradosDTO.setResolucion(resolucionDatosGenerados.getResolucion().getIdResolucion());
            resolucionDatosGeneradosDTO.setTipoMedioPago(resolucionDatosGenerados.getTipoMedioPago());
            resolucionDatosGeneradosDTO.setNumeroOficio(resolucionDatosGenerados.getNumeroOficio());
            resolucionDatosGeneradosDTO.setFechaResolucion(resolucionDatosGenerados.getFechaOficio());
            if (resolucionDatosGenerados.getUnidadAdministrativa() != null) {
                resolucionDatosGeneradosDTO.setUnidadAdministrativa(resolucionDatosGenerados.getUnidadAdministrativa()
                        .getClave() == null ? null : resolucionDatosGenerados.getUnidadAdministrativa().getClave());
            }
            else {
                resolucionDatosGeneradosDTO.setUnidadAdministrativa(null);
            }
            if (resolucion != null) {
                resolucionDatosGeneradosDTO.setResolucionSelected(resolucion.getDescripcion());
                resolucionDatosGeneradosDTO.setMontoTotal(resolucion.getMonto());
            }
        }
        else {
            resolucionDatosGeneradosDTO = null;
        }

        return resolucionDatosGeneradosDTO;
    }

    /**
     * 
     * @param resolucionDatosGeneradosDTO
     * @return
     */
    public Resolucion getResolucionFromResolucionDatosGeneradosDTO(
            ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO) {
        Resolucion resolucion = new Resolucion();
        Tramite tramite = new Tramite(resolucionDatosGeneradosDTO.getTramiteDTO().getNumeroAsunto());
        resolucion.setTramite(tramite);
        resolucion.setMonto(resolucionDatosGeneradosDTO.getMontoTotal());
        resolucion.setDescripcion(resolucionDatosGeneradosDTO.getResolucionSelected());
        resolucion.setIdeSentidoResolucion(resolucionDatosGeneradosDTO.getIdEsentreSolucion());
        resolucion.setFechaResolucion(resolucionDatosGeneradosDTO.getFechaResolucion());
        resolucion.setIdeEstadoResolucion(resolucionDatosGeneradosDTO.getIdeEstadoResolucion());
        return resolucion;
    }

    public ResolucionDatosGenerados transformarResolDatosGeneradosDTO(Resolucion resolucion) {
        ResolucionDatosGenerados resolucionDatosGenerados = new ResolucionDatosGenerados();
        if (resolucion != null) {
            resolucionDatosGenerados.setFechaOficio(resolucion.getFechaEmision() != null ? resolucion.getFechaEmision()
                    : null);
            resolucionDatosGenerados.setNumeroOficio(resolucion.getNumeroOficio() != null ? resolucion
                    .getNumeroOficio() : null);
            resolucionDatosGenerados.setResolucion(resolucion);
            resolucionDatosGenerados.setUnidadAdministrativa(resolucion.getResolucionesImpugnadas().get(0)
                    .getUnidadAdministrativa() != null ? resolucion.getResolucionesImpugnadas().get(0)
                    .getUnidadAdministrativa() : null);
        }
        else {
            Resolucion resolucionNueva = new Resolucion();
            resolucionDatosGenerados.setFechaOficio(resolucionNueva.getFechaEmision() != null ? resolucionNueva
                    .getFechaEmision() : null);
            resolucionDatosGenerados.setNumeroOficio(resolucionNueva.getNumeroOficio() != null ? resolucionNueva
                    .getNumeroOficio() : null);
            resolucionDatosGenerados.setResolucion(resolucionNueva);

        }
        return resolucionDatosGenerados;
    }

    @Override
    public List<ResolucionDatosGeneradosDTO> transformarDTO(List<ResolucionDatosGenerados> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
