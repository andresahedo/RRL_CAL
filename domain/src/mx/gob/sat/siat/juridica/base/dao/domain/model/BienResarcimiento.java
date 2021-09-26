/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "RVCT_BIENRESARCIM")
public class BienResarcimiento extends BaseModel {

    /**
     * Nuacute;mero de serie
     */
    private static final long serialVersionUID = -6035187747851786678L;

    /**
     * Atributo privado "idBienRerarcimiento" tipo Long
     */
    @Id
    @Column(name = "IDBIENRESARCIM")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_BIENRESARCIM")
    @SequenceGenerator(name = "RVCQ_BIENRESARCIM", sequenceName = "RVCQ_BIENRESARCIM", allocationSize = 1)
    private Long idBienRerarcimiento;

    /**
     * Atributo privado "resolucion" tipo Resolucion
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDRESOLUCION", nullable = false, referencedColumnName = "IDRESOLUCION")
    private Resolucion resolucion;

    /**
     * Atributo privado "valor" tipo Resolucion
     */
    @Column(name = "valor")
    private BigDecimal valor;

    /**
     * Atributo privado "descripcionBien" tipo Resolucion
     */
    @Column(name = "DESCBIEN")
    private String descripcionBien;

    /**
     * Atributo privado "medioTransporte" tipo Resolucion
     * 
     */
    @Column(name = "IDEMEDIOTRANSPORTE")
    private String medioTransporte;

    /**
     * Atributo privado "blnactivo"
     * 
     */
    @Column(name = "BLNACTIVO")
    private boolean activo;

    /**
     * @return the idBienRerarcimiento
     */
    public Long getIdBienRerarcimiento() {
        return idBienRerarcimiento;
    }

    /**
     * @param idBienRerarcimiento
     *            the idBienRerarcimiento to set
     */
    public void setIdBienRerarcimiento(Long idBienRerarcimiento) {
        this.idBienRerarcimiento = idBienRerarcimiento;
    }

    /**
     * @return the resolucion
     */
    public Resolucion getResolucion() {
        return resolucion;
    }

    /**
     * @param resolucion
     *            the resolucion to set
     */
    public void setResolucion(Resolucion resolucion) {
        this.resolucion = resolucion;
    }

    /**
     * @return the valor
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * @param valor
     *            the valor to set
     */
    public void setValor(BigDecimal valor) {
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
