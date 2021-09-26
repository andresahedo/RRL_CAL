package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;


import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.business.ConsultasAutorizacionesCALBusiness;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DocumentoLazyModelCall extends LazyDataModel<DocumentoDTO>{

    private static final long serialVersionUID = 1L;

    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<DocumentoDTO> datasource = new ArrayList<DocumentoDTO>();

    /**
     * Bussiness que implementa las reglas de negocio de consultas.
     */
    @ManagedProperty("#{consultasAutorizacionesCALBusiness}")
    private ConsultasAutorizacionesCALBusiness consultasAutorizacionesCALBusiness;


    public DocumentoLazyModelCall(ConsultasAutorizacionesCALBusiness consultasBussines2) {
        this.consultasAutorizacionesCALBusiness = consultasBussines2;
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
    public Object getRowKey(DocumentoDTO documento) {         
        if (documento != null && documento.getIdTipoDocumento() != null) {
            return documento.getIdTipoDocumento();
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
    public List<DocumentoDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, String> filters) {
        datasource = new ArrayList<DocumentoDTO>();
        if (filters.size() > 0) {
            DataPage dataPage = (DataPage) getConsultasAutorizacionesCALBusiness().obtenerDocumentosObligatoriosLazy(filters.get("idSolicitud").toString());
            List<DocumentoDTO> data = (List<DocumentoDTO>) dataPage.getData();            
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
                return datasource;
            }           
        }else{            
            this.setRowCount(0);
            return null;
        }
    }


    public ConsultasAutorizacionesCALBusiness getConsultasAutorizacionesCALBusiness() {
        return consultasAutorizacionesCALBusiness;
    }

    public void setConsultasAutorizacionesCALBusiness(
            ConsultasAutorizacionesCALBusiness consultasAutorizacionesCALBusiness) {
        this.consultasAutorizacionesCALBusiness = consultasAutorizacionesCALBusiness;
    }

    public Logger getLogger() {
        return logger;
    }

}