package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import java.util.Collection;

public class DataPage {

    private int totalOfRecords;

    private long totalOfPages;

    private long currentPage;

    private int pageSize;

    private Collection<? extends BaseModel> data;

    public int getTotalOfRecords() {
        return totalOfRecords;
    }

    public void setTotalOfRecords(int totalOfRecords) {
        this.totalOfRecords = totalOfRecords;
    }

    public long getTotalOfPages() {
        return totalOfPages;
    }

    public void setTotalOfPages(long totalOfPages) {
        this.totalOfPages = totalOfPages;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Collection<? extends BaseModel> getData() {
        return data;
    }

    public void setData(Collection<? extends BaseModel> data) {
        this.data = data;
    }

}
