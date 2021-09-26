package mx.gob.sat.siat.juridica.cal.web.controller.bean.model;

import mx.gob.sat.siat.juridica.cal.dto.BienResarcimientoDTO;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

public class BienResarcimientoDataModel extends ListDataModel<BienResarcimientoDTO> implements
        SelectableDataModel<BienResarcimientoDTO>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6887497062496361580L;

    /**
     * 
     */
    @Override
    public BienResarcimientoDTO getRowData(String arg0) {
        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        List<BienResarcimientoDTO> bienResarcimiento = (List<BienResarcimientoDTO>) getWrappedData();

        for (BienResarcimientoDTO bienresarcimientoData : bienResarcimiento) {
            if (bienresarcimientoData.getBienRerarcimiento() != null
                    && bienresarcimientoData.getBienRerarcimiento().equals(new Long(arg0))) {
                return bienresarcimientoData;
            }
        }
        return null;
    }

    /**
     * 
     */
    @Override
    public Object getRowKey(BienResarcimientoDTO arg0) {
        if (arg0 != null && arg0.getBienRerarcimiento() != null) {
            return arg0.getBienRerarcimiento().toString();
        }
        else {
            return "";
        }
    }

    public BienResarcimientoDataModel() {}

    public BienResarcimientoDataModel(List<BienResarcimientoDTO> bienResarcimientoDTOList) {
        super(bienResarcimientoDTOList);
    }

}
