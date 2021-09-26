/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;

import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaPendientesDTO;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

public class DatosBandejaPendientesDataModel extends ListDataModel<DatosBandejaPendientesDTO> implements
        SelectableDataModel<DatosBandejaPendientesDTO>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public DatosBandejaPendientesDTO getRowData(String arg0) {
        List<DatosBandejaPendientesDTO> bandejaDataModels = (List<DatosBandejaPendientesDTO>) getWrappedData();
        if (arg0 != null) {
            for (DatosBandejaPendientesDTO datosBandejaPendientesDataModel : bandejaDataModels) {
                if (datosBandejaPendientesDataModel.getIdSolicitud().equals(new Long(arg0))) {
                    return datosBandejaPendientesDataModel;
                }
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(DatosBandejaPendientesDTO arg0) {

        return arg0.getIdSolicitud();
    }

    public DatosBandejaPendientesDataModel(List<DatosBandejaPendientesDTO> datosBandejaPendientesDTOs) {
        super(datosBandejaPendientesDTOs);
    }

}
