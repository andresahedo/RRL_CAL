package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.emitirresolucion;

import mx.gob.sat.siat.juridica.rrl.dto.emitirresolucion.EmitirResolucionesDTO;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

public class EmitirResolucionDataModel extends ListDataModel<EmitirResolucionesDTO> implements
        SelectableDataModel<EmitirResolucionesDTO> {

    public EmitirResolucionDataModel(List<EmitirResolucionesDTO> emitirResolucionesDTO) {
        super(emitirResolucionesDTO);
    }

    @Override
    public EmitirResolucionesDTO getRowData(String arg0) {
        return null;
    }

    @Override
    public Object getRowKey(EmitirResolucionesDTO dto) {
        return dto.getResolucionImpugnadaDTO().getId();
    }

}
