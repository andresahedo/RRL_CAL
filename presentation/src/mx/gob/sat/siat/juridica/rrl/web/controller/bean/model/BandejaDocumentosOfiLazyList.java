package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;


import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.ConsultasBussines;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BandejaDocumentosOfiLazyList extends LazyDataModel<DocumentoOficialDTO> implements SelectableDataModel<DocumentoOficialDTO>{

    private static final long serialVersionUID = 1L;

    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<DocumentoOficialDTO> datasource = new ArrayList<DocumentoOficialDTO>();

    /**
     * Bussiness que implementa las reglas de negocio de consultas.
     */
    @ManagedProperty(value = "#{consultasBussines}")
    private ConsultasBussines consultasBussines;


    public BandejaDocumentosOfiLazyList(ConsultasBussines consultasBussines2 ) {        
        this.consultasBussines = consultasBussines2;
    }

    @Override
    public DocumentoOficialDTO getRowData(String rowKey) {

        for (DocumentoOficialDTO documento : datasource) {
            if (documento.getCveTipoDocumento().toString().equals(rowKey)) {
                return documento;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(DocumentoOficialDTO documento) { 
        if (documento != null && documento.getCveTipoDocumento() != null) {
            return documento.getCveTipoDocumento();
        }
        else {
            return "";
        }     
    }       


    @Override 
    public void setRowIndex(int rowIndex) {        
        if (rowIndex == -1 || getPageSize() == 0) { 
            super.setRowIndex(-1); 
        } 
        else { 
            super.setRowIndex(rowIndex % getPageSize());        
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<DocumentoOficialDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, String> filters) {

        datasource = new ArrayList<DocumentoOficialDTO>();

        if (filters.size() > 0) {

            DataPage dataPage = (DataPage)  getConsultasBussines().obtenerDocumentoslazy(filters.get("numFolio").toString());                     
            List<DocumentoOficialDTO> data = (List<DocumentoOficialDTO>) dataPage.getData();            
            datasource = data;

            int dataSize = datasource.size(); 
            this.setRowCount(dataSize);
            this.setPageSize(pageSize);

            if(dataSize > pageSize) {
                try {                                         
                    return datasource.subList(first, first + pageSize);
                }
                catch(IndexOutOfBoundsException e) {            
                    return datasource.subList(first, first + (dataSize % pageSize));
                }
            }else {                        
                return data;
            }           
        }else{            
            this.setRowCount(0);
            return null;
        }

    }

    public ConsultasBussines getConsultasBussines() {
        return consultasBussines;
    }

    public void setConsultasBussines(ConsultasBussines consultasBussines) {
        this.consultasBussines = consultasBussines;
    }

    public Logger getLogger() {
        return logger;
    }



}