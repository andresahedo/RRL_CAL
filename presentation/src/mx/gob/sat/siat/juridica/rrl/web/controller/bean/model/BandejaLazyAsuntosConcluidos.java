package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.OrdenBandeja;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dto.BandejaConsultasDescarga;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BandejaConsultasBussines;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BandejaLazyAsuntosConcluidos extends LazyDataModel<BandejaConsultasDescarga> {

    private static final long serialVersionUID = 1L;

    private transient Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<BandejaConsultasDescarga> datasource;


    @ManagedProperty("#{bandejaConsultasBussines}")
    private BandejaConsultasBussines bandejaConsultasBussines;

    public BandejaLazyAsuntosConcluidos(List<BandejaConsultasDescarga> datasource, BandejaConsultasBussines bandejaConsultasBussines2) {
        this.datasource = datasource;
        this.bandejaConsultasBussines = bandejaConsultasBussines2;
    }

    @Override
    public BandejaConsultasDescarga getRowData(String rowKey) {
        for (BandejaConsultasDescarga asunto : datasource) {
            if (asunto.getNumAsunto().equals(rowKey)) {
                return asunto;
            }
        }
 
        return null;
    }

    @Override
    public Object getRowKey(BandejaConsultasDescarga datosBandejaTareaDTO) {
        return datosBandejaTareaDTO.getNumAsunto();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<BandejaConsultasDescarga> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, String> filters) {      
        List<BandejaConsultasDescarga> resultado = null;
        if (!filters.isEmpty()) {   
            FiltroBandejaTareaDTO filtroBandejaTareaDTO = validarFiltros(filters);
            if (sortField != null) {
                OrdenBandeja orden = null;
                orden = OrdenBandeja.parse(sortField);
                String tipOrden = (sortOrder == SortOrder.DESCENDING ? " DESC" : " ASC");
                StringBuffer ord = new StringBuffer();
                ord.append(orden.getDescripcion());
                ord.append(tipOrden);
                filtroBandejaTareaDTO.setOrden(ord.toString());
            }
            else {
                filtroBandejaTareaDTO.setOrden("DUE DESC");
            }

            DataPage dataPage = (DataPage) getBandejaConsultasBussines().obtenerAsuntosConcluidosLazy(filtroBandejaTareaDTO);
            List<BandejaConsultasDescarga> data = (List<BandejaConsultasDescarga>) dataPage.getData();
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
        }else{            
            this.setRowCount(0);
        }
        return resultado;
    }

    private FiltroBandejaTareaDTO validarFiltros(Map<String, String> filters) {
        FiltroBandejaTareaDTO filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
        if (filters.get("fechIni") != null && !filters.get("fechIni").equals("")) {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            try {
                Date fechIniParse = df.parse(filters.get("fechIni"));
                Date fechFinParse = df.parse(filters.get("fechFin"));
                filtroBandejaTareaDTO.setFechaInicio(fechIniParse);
                filtroBandejaTareaDTO.setFechaFin(fechFinParse);
            }
            catch (ParseException e) {
                logger.error("", e);
            }
        }

        if (filters.get("numeroAsunto") != null) {
            filtroBandejaTareaDTO.setNumeroAsunto(filters.get("numeroAsunto"));
            
        }
        
        if(filters.get("rfc") != null ) {
            filtroBandejaTareaDTO.setUsuario(filters.get("rfc"));
        }
        
        if (filters.get("rfcPromovente") != null) {
            filtroBandejaTareaDTO.setRfcPromovente(filters.get("rfcPromovente"));
        }
        String estadoProc = filters.get("estadoProcesal");
        if (estadoProc != null && !estadoProc.isEmpty()) {
            CatalogoDTO estadoProcesal = new CatalogoDTO();
            estadoProcesal.setClave(estadoProc);
            filtroBandejaTareaDTO.setEstadoProcesal(estadoProcesal);
        }
        return filtroBandejaTareaDTO;
    }

    public BandejaConsultasBussines getBandejaConsultasBussines() {
        return bandejaConsultasBussines;
    }

    public void setBandejaConsultasBussines(BandejaConsultasBussines bandejaConsultasBussines) {
        this.bandejaConsultasBussines = bandejaConsultasBussines;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }



}
