package mx.gob.sat.siat.juridica.base.web.controller.bean.model;

import mx.gob.sat.siat.juridica.base.dto.BandejaConsultasDescarga;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

/**
 * DataModel para manipular los datos de la bandeja de consultas.
 * 
 * @author softtek
 * 
 */
public class DatosBandejaConsultasDataModelDesc extends ListDataModel<BandejaConsultasDescarga> implements
        SelectableDataModel<BandejaConsultasDescarga>, Serializable {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1484217680466191423L;

    /**
     * Metodo que realiza una busqueda de acuerdo al parametro
     * recibido.
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public BandejaConsultasDescarga getRowData(String arg0) {
        List<BandejaConsultasDescarga> bandejaDataModels = (List<BandejaConsultasDescarga>) getWrappedData();
        if (arg0 != null) {
            for (BandejaConsultasDescarga datosBandejaDataModel : bandejaDataModels) {
                if (datosBandejaDataModel.getNumAsunto().equals(arg0)) {
                    return datosBandejaDataModel;
                }
            }
        }
        return null;
    }

    /**
     * Metodo que devuelve el numero de asunto correspondiente al
     * objeto BandejaConsultasDTO del parametro recibido.
     */
    @Override
    public Object getRowKey(BandejaConsultasDescarga arg0) {

        return arg0.getNumAsunto();
    }

    /**
     * Constructor
     * 
     * @param datosBandejaTareaDTOs
     */
    public DatosBandejaConsultasDataModelDesc(List<BandejaConsultasDescarga> datosBandejaTareaDTOs) {
        super(datosBandejaTareaDTOs);
    }

}
