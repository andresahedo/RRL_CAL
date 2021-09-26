/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;

/**
 * 
 * @author softtek
 */
@Entity
@DiscriminatorColumn(name = "IDETIPOUNIADMINREL", discriminatorType = DiscriminatorType.STRING)
@Table(name = "RVCC_UNIADMINREL")
public class UnidadAdministrativaRelacionada extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -183572299990992373L;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * Atributo privado "clave" tipo String
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDUNIADMIN")
    private String clave;

    /**
     * Atributo privado "claveUnidadAdminR" tipo String
     */
    @Column(name = "IDUNIADMINREL")
    private String claveUnidadAdminR;

    /**
     * Atributo privado "tipoRelacion" tipo String
     */
    @Column(name = "IDETIPOUNIADMINREL")
    private String tipoRelacion;

    /**
     * Constructor vacio
     */
    public UnidadAdministrativaRelacionada() {}

    /**
     * Sobrecarga de Constructor
     * 
     * @param clave
     */
    public UnidadAdministrativaRelacionada(String clave) {
        this.clave = clave;
    }

    /**
     * 
     * @return clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * 
     * @param clave
     *            a fijar
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * 
     * @return claveUnidadAdminR
     */
    public String getClaveUnidadAdminR() {
        return claveUnidadAdminR;
    }

    /**
     * 
     * @param claveUnidadAdminR
     *            a fijar
     */
    public void setClaveUnidadAdminR(String claveUnidadAdminR) {
        this.claveUnidadAdminR = claveUnidadAdminR;
    }

    /**
     * 
     * @return vigencia
     */
    public Vigencia getVigencia() {
        return vigencia;
    }

    /**
     * 
     * @param vigencia
     *            a fijar
     */
    public void setVigencia(Vigencia vigencia) {
        this.vigencia = vigencia;
    }

    /**
     * @return the tipoRelacion
     */
    public String getTipoRelacion() {
        return tipoRelacion;
    }

    /**
     * @param tipoRelacion
     *            the tipoRelacion to set
     */
    public void setTipoRelacion(String tipoRelacion) {
        this.tipoRelacion = tipoRelacion;
    }

}
