package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedProperty;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.OrdenBandeja;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.BandejaReasignarRecursoRevocacionBussines;

public class BandejaReasignacionLazyList extends LazyDataModel<DatosBandejaTareaDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<DatosBandejaTareaDTO> datasource;

    @ManagedProperty("#{bandejaReasignarRecursoRevocacionBussines}")
    private BandejaReasignarRecursoRevocacionBussines bandejaReasignarRecursoRevocacionBussines;

    public BandejaReasignacionLazyList(List<DatosBandejaTareaDTO> datasource,
            BandejaReasignarRecursoRevocacionBussines bandejaBussines2) {
        this.datasource = datasource;
        this.bandejaReasignarRecursoRevocacionBussines = bandejaBussines2;
    }

    @Override
    public DatosBandejaTareaDTO getRowData(String rowKey) {
        for (DatosBandejaTareaDTO datosBandejaDataModel : datasource) {
            if (datosBandejaDataModel.getIdTareaUsuario().equals(new Long(rowKey))) {
                return datosBandejaDataModel;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        return datosBandejaTareaDTO.getIdTareaUsuario();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<DatosBandejaTareaDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, String> filters) {

        FiltroBandejaTareaDTO filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
        List<DatosBandejaTareaDTO> resultado = null;
        if (!filters.isEmpty()) {
            if (sortField != null) {
                OrdenBandeja orden = null;
                orden = OrdenBandeja.parse(sortField);
                String tipOrden = (sortOrder == SortOrder.DESCENDING ? " DESC" : " ASC");
                StringBuffer ord = new StringBuffer();
                ord.append(orden.getDescripcion());
                ord.append(tipOrden);
                filtroBandejaTareaDTO.setOrden(ord.toString());
            }else {
                filtroBandejaTareaDTO.setOrden("DUE DESC");
            }
            
            if (filters.get("rfcFuncionario") != null) {
                filtroBandejaTareaDTO.setRfcFuncionario(filters.get("rfcFuncionario"));
            }
            if (filters.get("numeroAsunto") != null) {
                filtroBandejaTareaDTO.setNumeroAsunto(filters.get("numeroAsunto"));
            }
            if (filters.get("usuario") != null) {
                filtroBandejaTareaDTO.setUsuario(filters.get("usuario"));
            }
            DataPage dataPage = (DataPage) getBandejaReasignarRecursoRevocacionBussines().obtenerTareasReasignar(filtroBandejaTareaDTO, filters.get("rol")); 
            List<DatosBandejaTareaDTO> data = (List<DatosBandejaTareaDTO>) dataPage.getData();
            this.setRowCount(dataPage.getTotalOfRecords());
            datasource = data;
            this.setRowCount(datasource.size());
            this.setPageSize(pageSize);
            if(datasource.size() > pageSize) {
                try {
                    resultado =  datasource.subList(first, first + pageSize);
                }
                catch(IndexOutOfBoundsException e) {
                    resultado =  datasource.subList(first, first + (datasource.size() % pageSize));
                }    
            }else {                        
                resultado = datasource;
            }

        }
        else {
            this.setRowCount(0);
        }
        return resultado;
    }

    public BandejaReasignarRecursoRevocacionBussines getBandejaReasignarRecursoRevocacionBussines() {
        return bandejaReasignarRecursoRevocacionBussines;
    }

    public void setBandejaReasignarRecursoRevocacionBussines(
            BandejaReasignarRecursoRevocacionBussines bandejaReasignarRecursoRevocacionBussines) {
        this.bandejaReasignarRecursoRevocacionBussines = bandejaReasignarRecursoRevocacionBussines;
    }

}
