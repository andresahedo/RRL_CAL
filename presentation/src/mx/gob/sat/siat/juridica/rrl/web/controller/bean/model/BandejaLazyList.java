package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedProperty;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.OrdenBandeja;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.BandejaBussines;

public class BandejaLazyList extends LazyDataModel<DatosBandejaTareaDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<DatosBandejaTareaDTO> datasource;

    @ManagedProperty("#{bandejaBussines}")
    private BandejaBussines bandejaBussines;

    public BandejaLazyList(List<DatosBandejaTareaDTO> datasource, BandejaBussines bandejaBussines2) {
        this.datasource = datasource;
        this.bandejaBussines = bandejaBussines2;
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
       List<DatosBandejaTareaDTO> resultado = null;
        FiltroBandejaTareaDTO filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
        if (filters.size() > 0) {

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

            filtroBandejaTareaDTO.setFirst(first + 1);
            filtroBandejaTareaDTO.setPageSize(pageSize);
            filtroBandejaTareaDTO.setUsuario(filters.get("rfc"));
            filtroBandejaTareaDTO.setEsContribuyente(Boolean.parseBoolean(filters.get("esContribuyente")));

            if (filters.get("numeroAsunto") != null) {
                filtroBandejaTareaDTO.setNumeroAsunto(filters.get("numeroAsunto"));
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
            if (filters.get("busqueda") != null) {
                filters.remove("busqueda");
                filtroBandejaTareaDTO.setPage(0);
            }
            else {
                filtroBandejaTareaDTO.setPage(first);
            }
            DataPage dataPage = (DataPage) getBandejaBussines().obtenerDatosBandejaDB(filtroBandejaTareaDTO);
            List<DatosBandejaTareaDTO> data = (List<DatosBandejaTareaDTO>) dataPage.getData();
            this.setRowCount(dataPage.getTotalOfRecords());
            datasource = data;
            this.setRowCount(datasource.size());
            this.setPageSize(pageSize);
            if(datasource.size() > pageSize) {
                try{
                    resultado = datasource.subList(first, first + pageSize);
                }catch(IndexOutOfBoundsException e) {
                    resultado = datasource.subList(first, first + (datasource.size() % pageSize));
                }
            }else{
                resultado = datasource;
            }

        }
        else {
            this.setRowCount(0);
        }
        return resultado;
    }

    /**
     * @return the bandejaBussines
     */
    public BandejaBussines getBandejaBussines() {
        return bandejaBussines;
    }

    /**
     * @param bandejaBussines
     *            the bandejaBussines to set
     */
    public void setBandejaBussines(BandejaBussines bandejaBussines) {
        this.bandejaBussines = bandejaBussines;
    }

}
