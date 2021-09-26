/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.dto;

import java.util.Date;

public class ResolucionCALDTO {

    /**
     * 
     */
    private String resolucion;
    /**
     * 
     */
    private String nombreFirma;
    /**
     * 
     */
    private String fraccionArancel;
    /**
     * 
     */
    private String numeroMinuta;
    /**
     * 
     */
    private String fechaSesion;
    /**
     * 
     */
    private boolean confirmaFraccion;
    /**
     * 
     */
    private Double montoTotal;
    /**
     * 
     */
    private Date fechaEmbargo;
    /**
     * 
     */
    private Double montoTotalAprovado;
    /**
     * 
     */
    private String autoridadEmbargo;
    /**
     * 
     */
    private String medioDefensa;
    /**
     * 
     */
    private String dictamen;
    /**
     * 
     */
    private String montoCumplimiento;

    /**
     * 
     */
    private String numeroAsunto;

    /**
     * @return the resolucion
     */
    public String getResolucion() {
        return resolucion;
    }

    /**
     * @param resolucion
     *            the resolucion to set
     */
    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    /**
     * @return the nombreFirma
     */
    public String getNombreFirma() {
        return nombreFirma;
    }

    /**
     * @param nombreFirma
     *            the nombreFirma to set
     */
    public void setNombreFirma(String nombreFirma) {
        this.nombreFirma = nombreFirma;
    }

    /**
     * @return the fraccionArancel
     */
    public String getFraccionArancel() {
        return fraccionArancel;
    }

    /**
     * @param fraccionArancel
     *            the fraccionArancel to set
     */
    public void setFraccionArancel(String fraccionArancel) {
        this.fraccionArancel = fraccionArancel;
    }

    /**
     * @return the numeroMinuta
     */
    public String getNumeroMinuta() {
        return numeroMinuta;
    }

    /**
     * @param numeroMinuta
     *            the numeroMinuta to set
     */
    public void setNumeroMinuta(String numeroMinuta) {
        this.numeroMinuta = numeroMinuta;
    }

    /**
     * @return the fechaSesion
     */
    public String getFechaSesion() {
        return fechaSesion;
    }

    /**
     * @param fechaSesion
     *            the fechaSesion to set
     */
    public void setFechaSesion(String fechaSesion) {
        this.fechaSesion = fechaSesion;
    }

    /**
     * @return the confirmaFraccion
     */
    public boolean isConfirmaFraccion() {
        return confirmaFraccion;
    }

    /**
     * @param confirmaFraccion
     *            the confirmaFraccion to set
     */
    public void setConfirmaFraccion(boolean confirmaFraccion) {
        this.confirmaFraccion = confirmaFraccion;
    }

    /**
     * @return the montoTotal
     */
    public Double getMontoTotal() {
        return montoTotal;
    }

    /**
     * @param montoTotal
     *            the montoTotal to set
     */
    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    /**
     * @return the fechaEmbargo
     */
    public Date getFechaEmbargo() {
        return fechaEmbargo != null ? (Date) fechaEmbargo.clone() : null;
    }

    /**
     * @param fechaEmbargo
     *            the fechaEmbargo to set
     */
    public void setFechaEmbargo(Date fechaEmbargo) {
        this.fechaEmbargo = fechaEmbargo != null ? (Date) fechaEmbargo.clone() : null;
    }

    /**
     * @return the montoTotalAprovado
     */
    public Double getMontoTotalAprovado() {
        return montoTotalAprovado;
    }

    /**
     * @param montoTotalAprovado
     *            the montoTotalAprovado to set
     */
    public void setMontoTotalAprovado(Double montoTotalAprovado) {
        this.montoTotalAprovado = montoTotalAprovado;
    }

    /**
     * @return the autoridadEmbargo
     */
    public String getAutoridadEmbargo() {
        return autoridadEmbargo;
    }

    /**
     * @param autoridadEmbargo
     *            the autoridadEmbargo to set
     */
    public void setAutoridadEmbargo(String autoridadEmbargo) {
        this.autoridadEmbargo = autoridadEmbargo;
    }

    /**
     * @return the medioDefensa
     */
    public String getMedioDefensa() {
        return medioDefensa;
    }

    /**
     * @param medioDefensa
     *            the medioDefensa to set
     */
    public void setMedioDefensa(String medioDefensa) {
        this.medioDefensa = medioDefensa;
    }

    /**
     * @return the dictamen
     */
    public String getDictamen() {
        return dictamen;
    }

    /**
     * @param dictamen
     *            the dictamen to set
     */
    public void setDictamen(String dictamen) {
        this.dictamen = dictamen;
    }

    /**
     * @return the montoCumplimiento
     */
    public String getMontoCumplimiento() {
        return montoCumplimiento;
    }

    /**
     * @param montoCumplimiento
     *            the montoCumplimiento to set
     */
    public void setMontoCumplimiento(String montoCumplimiento) {
        this.montoCumplimiento = montoCumplimiento;
    }

    /**
     * @return the numeroAsunto
     */
    public String getNumeroAsunto() {
        return numeroAsunto;
    }

    /**
     * @param numeroAsunto
     *            the numeroAsunto to set
     */
    public void setNumeroAsunto(String numeroAsunto) {
        this.numeroAsunto = numeroAsunto;
    }
}
