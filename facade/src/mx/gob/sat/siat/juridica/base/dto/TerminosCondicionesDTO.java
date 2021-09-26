package mx.gob.sat.siat.juridica.base.dto;

import java.util.Date;

public class TerminosCondicionesDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1090854841460836243L;
    private int idCondicionesUso;
    private Date fechaInicioVigencia;
    private Date fechaFinVigencia;
    private String descripcion;
    private boolean blnactivo;
    private String descripcionHtml;
    private Date fechaBaja;

    /**
     * @return the idCondicionesUso
     */
    public int getIdCondicionesUso() {
        return idCondicionesUso;
    }

    /**
     * @param idCondicionesUso
     *            the idCondicionesUso to set
     */
    public void setIdCondicionesUso(int idCondicionesUso) {
        this.idCondicionesUso = idCondicionesUso;
    }

    /**
     * @return the fechaInicioVigencia
     */
    public Date getFechaInicioVigencia() {
        return fechaInicioVigencia != null ? (Date) fechaInicioVigencia.clone() : null;
    }

    /**
     * @param fechaInicioVigencia
     *            the fechaInicioVigencia to set
     */
    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia != null ? (Date) fechaInicioVigencia.clone() : null;
    }

    /**
     * @return the fechaFinVigencia
     */
    public Date getFechaFinVigencia() {
        return fechaFinVigencia != null ? (Date) fechaFinVigencia.clone() : null;
    }

    /**
     * @param fechaFinVigencia
     *            the fechaFinVigencia to set
     */
    public void setFechaFinVigencia(Date fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia != null ? (Date) fechaFinVigencia.clone() : null;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion
     *            the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the blnactivo
     */
    public boolean isBlnactivo() {
        return blnactivo;
    }

    /**
     * @param blnactivo
     *            the blnactivo to set
     */
    public void setBlnactivo(boolean blnactivo) {
        this.blnactivo = blnactivo;
    }

    /**
     * @return the descripcionHtml
     */
    public String getDescripcionHtml() {
        return descripcionHtml;
    }

    /**
     * @param descripcionHtml
     *            the descripcionHtml to set
     */
    public void setDescripcionHtml(String descripcionHtml) {
        this.descripcionHtml = descripcionHtml;
    }

    /**
     * @return the fechaBaja
     */
    public Date getFechaBaja() {
        return fechaBaja != null ? (Date) fechaBaja.clone() : null;
    }

    /**
     * @param fechaBaja
     *            the fechaBaja to set
     */
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja != null ? (Date) fechaBaja.clone() : null;
    }

}
