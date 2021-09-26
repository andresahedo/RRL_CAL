/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.model.BienResarcimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Resolucion;
import mx.gob.sat.siat.juridica.cal.dto.BienResarcimientoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase para transformar BienResarcimientoDTO y BienResarcimiento
 * 
 * @author softtek
 * 
 */
@Component
public class BienResarcimientoDTOTransformer extends
        DTOTransformer<List<BienResarcimiento>, List<BienResarcimientoDTO>> {

    /**
     * 
     */
    private static final long serialVersionUID = -2152749311117897234L;

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer
     * #transformarDTO(java.lang.Object)
     */
    @Override
    public List<BienResarcimientoDTO> transformarDTO(List<BienResarcimiento> bienResarcimientoList) {
        List<BienResarcimientoDTO> data = new ArrayList<BienResarcimientoDTO>();
        int idTable = 1;
        for (BienResarcimiento bienResarcimiento : bienResarcimientoList) {
            BienResarcimientoDTO dto = transformaDTO(bienResarcimiento, idTable++);
            dto.setBienRerarcimiento(bienResarcimiento.getIdBienRerarcimiento());
            data.add(dto);
        }
        return data;
    }

    /**
     * M&eacute;todo para trasformar ResolucionDatosGeneradosDTO a
     * ResolucionDatosGenerados.
     * 
     * @param resolucionDatosGeneradosDTO
     * @return
     */
    public BienResarcimiento transformarResolucionDatosGenerados(BienResarcimientoDTO bienResarcimientoDTO) {

        BienResarcimiento bienResarcimiento = new BienResarcimiento();

        if (bienResarcimientoDTO != null) {
            bienResarcimiento.setIdBienRerarcimiento(bienResarcimientoDTO.getBienRerarcimiento());
            bienResarcimiento.setDescripcionBien(bienResarcimientoDTO.getDescripcionBien());
            bienResarcimiento.setMedioTransporte(bienResarcimientoDTO.getMedioTransporte());
            bienResarcimiento.setResolucion(new Resolucion(bienResarcimientoDTO.getResolucion()));
            bienResarcimiento.setValor(new BigDecimal(bienResarcimientoDTO.getValor()));
        }
        else {
            bienResarcimiento = null;
        }
        return bienResarcimiento;
    }

    /**
     * M&ecute;todo para transformar ResolucionDatosGenerados a
     * ResolucionDatosGeneradosDTO
     * 
     * @param bienResarcimiento
     * @return
     */
    public BienResarcimientoDTO transformaDTO(BienResarcimiento bienResarcimiento) {

        BienResarcimientoDTO bienResarcimientoDTO = new BienResarcimientoDTO();

        if (bienResarcimiento != null) {
            bienResarcimientoDTO.setIdTableBien(new Date().getTime());
                                                                      // id
                                                                      // de
                                                                      // la
                                                                      // tabla
                                                                      // es
                                                                      // un
                                                                      // valor
                                                                      // unico
            bienResarcimientoDTO.setDescripcionBien(bienResarcimiento.getDescripcionBien());
            bienResarcimientoDTO.setMedioTransporte(bienResarcimiento.getMedioTransporte());
            bienResarcimientoDTO.setResolucion(bienResarcimiento.getResolucion().getIdResolucion());
            bienResarcimientoDTO.setValor(bienResarcimiento.getValor().longValue());

        }
        else {
            bienResarcimientoDTO = null;
        }

        return bienResarcimientoDTO;
    }

    /**
     * M&ecute;todo para transformar ResolucionDatosGenerados a
     * ResolucionDatosGeneradosDTO
     * 
     * @param bienResarcimiento
     * @param idTable
     * @return
     */
    public BienResarcimientoDTO transformaDTO(BienResarcimiento bienResarcimiento, int idTable) {

        BienResarcimientoDTO bienResarcimientoDTO = new BienResarcimientoDTO();

        if (bienResarcimiento != null) {
            bienResarcimientoDTO.setIdTableBien(new Date().getTime() + idTable);
                                                                                // id
                                                                                // de
                                                                                // la
                                                                                // tabla
                                                                                // es
                                                                                // un
                                                                                // valor
                                                                                // unico
            bienResarcimientoDTO.setDescripcionBien(bienResarcimiento.getDescripcionBien());
            bienResarcimientoDTO.setMedioTransporte(bienResarcimiento.getMedioTransporte());
            bienResarcimientoDTO.setResolucion(bienResarcimiento.getResolucion().getIdResolucion());
            bienResarcimientoDTO.setValor(bienResarcimiento.getValor().longValue());

        }
        else {
            bienResarcimientoDTO = null;
        }

        return bienResarcimientoDTO;
    }

}
