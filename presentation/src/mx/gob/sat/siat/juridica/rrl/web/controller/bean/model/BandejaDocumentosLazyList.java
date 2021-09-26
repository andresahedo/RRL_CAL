package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;


import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.ConsultasBussines;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BandejaDocumentosLazyList extends LazyDataModel<DocumentoDTO>{

    private static final long serialVersionUID = 1L;


    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());


    private List<DocumentoDTO> datasource = new ArrayList<DocumentoDTO>();

    /**
     * Bussiness que implementa las reglas de negocio de consultas.
     */
    @ManagedProperty(value = "#{consultasBussines}")
    private ConsultasBussines consultasBussines;


    public BandejaDocumentosLazyList(ConsultasBussines consultasBussines2) {
        this.consultasBussines = consultasBussines2;
    }

    @Override
    public DocumentoDTO getRowData(String rowKey) {
        for (DocumentoDTO documento : datasource) {
            if (documento.getIdTipoDocumento().toString().equals(rowKey)) {
                return documento;
            }
        }
        return null;
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


    @Override
    public Object getRowKey(DocumentoDTO documento) { 
        if (documento != null && documento.getIdTipoDocumento() != null) {
            return documento.getIdTipoDocumento();
        }
        else {
            return "";
        }
    }     


    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<DocumentoDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, String> filters) {
        datasource = new ArrayList<DocumentoDTO>();
        if (filters.size() > 0) {
            DataPage dataPage = (DataPage) getConsultasBussines().obtenerDocumentosRegistroLazy(filters.get("IdSolicitud").toString());
            List<DocumentoDTO> data = (List<DocumentoDTO>) dataPage.getData();
            datasource = data;
            this.setRowCount(datasource.size());
            this.setPageSize(pageSize);
            if(datasource.size() > pageSize) {
                try {                                    
                    return datasource.subList(first, first + pageSize);
                }
                catch(IndexOutOfBoundsException e) {                            
                    return datasource.subList(first, first + (datasource.size() % pageSize));
                }    
            }else {                        
                return datasource;
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