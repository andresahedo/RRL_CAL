/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;

import java.util.Date;

/**
 * FiltroBitacoraAGDTO
 * 
 * @author softtek - EQG 28/09/2014
 */
public class FiltroBitacoraAGDTO extends BaseDataTransferObject {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 8142304169880406924L;

    private String idUniAdmin;
    private Date fechaInicial;
    private Date fechaFinal;
    private String realizadoPor;
    private String aplicadoA;
    private Long numeroEmpRealiza;
    private Long numeroEmpAplicado;
    private String rfcRealizaPor;
    private String rfcAplicadoA;

    public String getIdUniAdmin() {
        return idUniAdmin;
    }

    public void setIdUniAdmin(String idUniAdmin) {
        this.idUniAdmin = idUniAdmin;
    }

    public Date getFechaInicial() {
        return (null == fechaInicial ? null : (Date) fechaInicial.clone());
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = (null == fechaInicial ? null : (Date) fechaInicial.clone());
    }

    public Date getFechaFinal() {
        return (null == fechaFinal ? null : (Date) fechaFinal.clone());
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = (null == fechaFinal ? null : (Date) fechaFinal.clone());
    }

    public String getRealizadoPor() {
        return realizadoPor;
    }

    public void setRealizadoPor(String realizadoPor) {
        this.realizadoPor = realizadoPor;
    }

    public String getAplicadoA() {
        return aplicadoA;
    }

    public void setAplicadoA(String aplicadoA) {
        this.aplicadoA = aplicadoA;
    }

    public Long getNumeroEmpRealiza() {
        return numeroEmpRealiza;
    }

    public void setNumeroEmpRealiza(Long numeroEmpRealiza) {
        this.numeroEmpRealiza = numeroEmpRealiza;
    }

    public Long getNumeroEmpAplicado() {
        return numeroEmpAplicado;
    }

    public void setNumeroEmpAplicado(Long numeroEmpAplicado) {
        this.numeroEmpAplicado = numeroEmpAplicado;
    }

    public String getRfcRealizaPor() {
        return rfcRealizaPor;
    }

    public void setRfcRealizaPor(String rfcRealizaPor) {
        this.rfcRealizaPor = rfcRealizaPor;
    }

    public String getRfcAplicadoA() {
        return rfcAplicadoA;
    }

    public void setRfcAplicadoA(String rfcAplicadoA) {
        this.rfcAplicadoA = rfcAplicadoA;
    }

}
