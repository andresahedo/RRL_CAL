package mx.gob.sat.siat.juridica.rrl.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FiltroBandejaTareaDTO extends BaseDataTransferObject {
    /**
     * 
     */
    private static final long serialVersionUID = -7820867418150107347L;
    private String usuario;
    private String numeroAsunto;
    private Date fechaInicio;
    private Date fechaFin;
    private CatalogoDTO estadoProcesal;
    private String rfcPromovente;
    private String rfcFuncionario;
    private CatalogoDTO tipoTramite;
    private Integer first;
    private Integer pageSize;
    private Integer page;
    private boolean esContribuyente;
    private String folio;
    private boolean banderaFolio;
    private String orden;

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public boolean isBanderaFolio() {
        return banderaFolio;
    }

    public void setBanderaFolio(boolean banderaFolio) {
        this.banderaFolio = banderaFolio;
    }

    public boolean getEsContribuyente() {
        return esContribuyente;
    }

    public void setEsContribuyente(boolean esContribuyente) {
        this.esContribuyente = esContribuyente;
    }

    public String getNumeroAsunto() {
        return numeroAsunto;
    }

    public void setNumeroAsunto(String numeroAsunto) {
        this.numeroAsunto = numeroAsunto;
    }

    public Date getFechaInicio() {
        return fechaInicio != null ? (Date) fechaInicio.clone() : null;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio != null ? (Date) fechaInicio.clone() : null;
    }

    public Date getFechaFin() {
        return fechaFin != null ? (Date) fechaFin.clone() : null;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin != null ? (Date) fechaFin.clone() : null;
    }

    public CatalogoDTO getEstadoProcesal() {
        return estadoProcesal;
    }

    public void setEstadoProcesal(CatalogoDTO estadoProcesal) {
        this.estadoProcesal = estadoProcesal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRfcPromovente() {
        return rfcPromovente;
    }

    public void setRfcPromovente(String rfcPromovente) {
        this.rfcPromovente = rfcPromovente;
    }

    public String getRfcFuncionario() {
        return rfcFuncionario;
    }

    public void setRfcFuncionario(String rfcFuncionario) {
        this.rfcFuncionario = rfcFuncionario;
    }

    public CatalogoDTO getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(CatalogoDTO tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    /**
     * @return the first
     */
    public Integer getFirst() {
        return first;
    }

    /**
     * @param first
     *            the first to set
     */
    public void setFirst(Integer first) {
        this.first = first;
    }

    /**
     * @return the pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getCadenaBitacora() {
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        StringBuffer observaciones = new StringBuffer();
        observaciones.append("Filtros: |");

        if (this.getNumeroAsunto() != null && !"".equals(this.getNumeroAsunto())) {
            observaciones.append("Numero de Asunto: ").append(this.getNumeroAsunto());
        }
        observaciones.append("|");
        if (this.getFechaInicio() != null) {
            observaciones.append("Fecha Inicio: ").append(formateador.format(this.getFechaInicio()));
        }
        observaciones.append("|");
        if (this.getFechaFin() != null) {
            observaciones.append("Fecha Fin: ").append(formateador.format(this.getFechaFin()));
        }
        observaciones.append("|");
        if (this.getRfcPromovente() != null && !"".equals(this.getRfcPromovente())) {
            observaciones.append("RFC Contribuyente: ").append(this.getRfcPromovente());
        }
        observaciones.append("|");
        if (this.getEstadoProcesal() != null) {
            observaciones.append("Estado Procesal: ").append(this.getEstadoProcesal().getDescripcion());
        }
        observaciones.append("|");

        return observaciones.toString();
    }

    /**
     * @return the orden
     */
    public String getOrden() {
        return orden;
    }

    /**
     * @param orden
     *            the orden to set
     */
    public void setOrden(String orden) {
        this.orden = orden;
    }

}
