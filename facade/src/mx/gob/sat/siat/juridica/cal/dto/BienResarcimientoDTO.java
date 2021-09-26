/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;

/**
 * Clase DTO para el armado de los bienes resarcimeinto
 * 
 * @author Softtek
 * @since 03/27/2014
 */
public class BienResarcimientoDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 7317738471073851263L;
    /**
     * Para ser agregado dentro de la tabla y poder ser seleccionado
     */
    private Long idTableBien;

    /**
     * Atributo privado "idBienRerarcimiento" tipo Long
     */
    private Long bienRerarcimiento;

    /**
     * Atributo privado "resolucion" tipo Resolucion
     */
    private Long resolucion;

    /**
     * Atributo privado "valor" tipo Resolucion
     */
    private Long valor;

    /**
     * Atributo privado "descripcionBien" tipo Resolucion
     */
    private String descripcionBien;

    /**
     * Atributo privado "medioTransporte" tipo Resolucion
     * 
     */
    private String medioTransporte;

    /**
     * @return the bienRerarcimiento
     */
    public Long getBienRerarcimiento() {
        return bienRerarcimiento;
    }

    /**
     * @param bienRerarcimiento
     *            the bienRerarcimiento to set
     */
    public void setBienRerarcimiento(Long bienRerarcimiento) {
        this.bienRerarcimiento = bienRerarcimiento;
    }

    /**
     * @return the resolucion
     */
    public Long getResolucion() {
        return resolucion;
    }

    /**
     * @param resolucion
     *            the resolucion to set
     */
    public void setResolucion(Long resolucion) {
        this.resolucion = resolucion;
    }

    /**
     * @return the valor
     */
    public Long getValor() {
        return valor;
    }

    /**
     * @param valor
     *            the valor to set
     */
    public void setValor(Long valor) {
        this.valor = valor;
    }

    /**
     * @return the descripcionBien
     */
    public String getDescripcionBien() {
        return descripcionBien;
    }

    /**
     * @param descripcionBien
     *            the descripcionBien to set
     */
    public void setDescripcionBien(String descripcionBien) {
        this.descripcionBien = descripcionBien;
    }

    /**
     * @return the medioTransporte
     */
    public String getMedioTransporte() {
        return medioTransporte;
    }

    /**
     * @param medioTransporte
     *            the medioTransporte to set
     */
    public void setMedioTransporte(String medioTransporte) {
        this.medioTransporte = medioTransporte;
    }

    /**
     * @return the idTableBien
     */
    public Long getIdTableBien() {
        return idTableBien;
    }

    /**
     * @param idTableBien
     *            the idTableBien to set
     */
    public void setIdTableBien(Long idTableBien) {
        this.idTableBien = idTableBien;
    }

}
