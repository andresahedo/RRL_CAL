/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.dto.emitirresolucion;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;

/**
 * DTO para mantener la infroamcion de las resoluciones impuganadas y
 * su resoluci&oacute;n.
 * 
 * @author Softtek
 * 
 */
public class EmitirResolucionesDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = -5896840174160971242L;
    /**
     * 
     */
    private ResolucionImpugnadaDTO resolucionImpugnadaDTO;

    /**
     * 
     * @param resolucionImpugnadaDTO
     */
    public void setResolucionImpugnadaDTO(ResolucionImpugnadaDTO resolucionImpugnadaDTO) {
        this.resolucionImpugnadaDTO = resolucionImpugnadaDTO;
    }

    /**
     * 
     * @return
     */
    public ResolucionImpugnadaDTO getResolucionImpugnadaDTO() {
        return resolucionImpugnadaDTO;
    }

}
